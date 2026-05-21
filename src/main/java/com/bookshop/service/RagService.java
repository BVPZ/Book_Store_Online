package com.bookshop.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.bookshop.dao.BookDao;
import com.bookshop.dao.CategoryDao;
import com.bookshop.model.Book;
import com.bookshop.model.Category;
import com.bookshop.model.KnowledgeDocument;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class RagService {
    private static final String EMBEDDING_URL = "http://127.0.0.1:11434/v1/embeddings";
    private static final String EMBEDDING_MODEL = "nomic-embed-text";
    private static final int TOP_K = 5;  // Increased to capture description documents
    
    private List<KnowledgeDocument> knowledgeBase;

    public RagService() {
        this.knowledgeBase = new ArrayList<>();
    }

    // Tải knowledge base từ database
    public void loadKnowledgeBase() throws SQLException {
        knowledgeBase.clear();
        
        // Tải từ sách
        loadBooksAsDocuments();
        
        // Tải từ thể loại
        loadCategoriesAsDocuments();
        
        System.out.println("RAG: Loaded " + knowledgeBase.size() + " documents");
    }

    private void loadBooksAsDocuments() throws SQLException {
        List<Book> books = BookDao.getAllBooks();
        for (Book book : books) {
            // Document 1: Full book info (for general search)
            String fullContent = "Sách: " + book.getTitle() + 
                           ". Tác giả: " + book.getAuthorName() + 
                           ". Giá: " + String.format("%,.0f", book.getPrice()) + "₫" +
                           ". Mô tả: " + (book.getDescription() != null ? book.getDescription() : "");
            
            KnowledgeDocument fullDoc = new KnowledgeDocument(
                "book_" + book.getBookId(),
                fullContent,
                "book"
            );
            knowledgeBase.add(fullDoc);
            
            // Document 2+: Split description into smaller chunks for better semantic matching on content
            if (book.getDescription() != null && !book.getDescription().isBlank()) {
                List<String> chunks = splitIntoChunks(book.getDescription(), 70);
                for (int i = 0; i < chunks.size(); i++) {
                    String chunkContent = book.getTitle() + ". " + chunks.get(i);
                    KnowledgeDocument descDoc = new KnowledgeDocument(
                        "book_desc_" + book.getBookId() + "_" + (i + 1),
                        chunkContent,
                        "book_description"
                    );
                    knowledgeBase.add(descDoc);
                }
            }
        }
    }

    private List<String> splitIntoChunks(String text, int maxWords) {
        List<String> chunks = new ArrayList<>();
        if (text == null || text.isBlank()) {
            return chunks;
        }

        String cleaned = text.trim().replaceAll("\\s+", " ");
        String[] sentences = cleaned.split("(?<=[\\.!?])\\s+");

        StringBuilder current = new StringBuilder();
        int wordCount = 0;

        for (String sentence : sentences) {
            sentence = sentence.trim();
            if (sentence.isEmpty()) {
                continue;
            }

            String[] words = sentence.split(" ");
            int sentenceWordCount = words.length;

            if (wordCount + sentenceWordCount > maxWords && current.length() > 0) {
                chunks.add(current.toString().trim());
                current.setLength(0);
                wordCount = 0;
            }

            if (current.length() > 0) {
                current.append(' ');
            }
            current.append(sentence);
            wordCount += sentenceWordCount;

            if (wordCount >= maxWords) {
                chunks.add(current.toString().trim());
                current.setLength(0);
                wordCount = 0;
            }
        }

        if (current.length() > 0) {
            chunks.add(current.toString().trim());
        }

        return chunks;
    }

    private void loadCategoriesAsDocuments() throws SQLException {
        List<Category> categories = CategoryDao.getAllCategories();
        for (Category cat : categories) {
            String content = "Thể loại: " + cat.getCategoryName();
            
            KnowledgeDocument doc = new KnowledgeDocument(
                "category_" + cat.getCategoryId(),
                content,
                "category"
            );
            knowledgeBase.add(doc);
        }
    }

    // Gọi Ollama embedding API
    public List<Double> getEmbedding(String text) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newBuilder().build();
        
        JsonObject payload = new JsonObject();
        payload.addProperty("model", EMBEDDING_MODEL);
        payload.addProperty("input", text);
        
        String requestBody = payload.toString();
        
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(EMBEDDING_URL))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody, StandardCharsets.UTF_8))
                .build();
        
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
            if (response.statusCode() != 200) {
                System.err.println("Embedding error: " + response.statusCode() + " - " + response.body());
                return null;
            }
            
            JsonObject jsonResponse = JsonParser.parseString(response.body()).getAsJsonObject();
            JsonArray embeddings = jsonResponse.has("embeddings") ? jsonResponse.getAsJsonArray("embeddings") : null;
            if (embeddings == null && jsonResponse.has("data")) {
                embeddings = jsonResponse.getAsJsonArray("data");
            }
            if (embeddings != null && embeddings.size() > 0) {
                JsonObject firstItem = embeddings.get(0).getAsJsonObject();
                JsonArray firstEmbedding = firstItem.has("embedding") ? firstItem.getAsJsonArray("embedding") : null;
                if (firstEmbedding == null && firstItem.has("embeddings")) {
                    firstEmbedding = firstItem.getAsJsonArray("embeddings");
                }
                if (firstEmbedding != null) {
                    List<Double> result = new ArrayList<>();
                    for (int i = 0; i < firstEmbedding.size(); i++) {
                        result.add(firstEmbedding.get(i).getAsDouble());
                    }
                    return result;
                }
            }
            System.err.println("Embedding parse error: unexpected response body: " + response.body());
        } catch (Exception e) {
            System.err.println("Embedding request error: " + e.getMessage());
        }
        
        return null;
    }

    // Tính cosine similarity giữa hai vectors
    private double cosineSimilarity(List<Double> vec1, List<Double> vec2) {
        if (vec1 == null || vec2 == null || vec1.size() != vec2.size()) {
            return 0.0;
        }
        
        double dotProduct = 0.0;
        double normA = 0.0;
        double normB = 0.0;
        
        for (int i = 0; i < vec1.size(); i++) {
            dotProduct += vec1.get(i) * vec2.get(i);
            normA += vec1.get(i) * vec1.get(i);
            normB += vec2.get(i) * vec2.get(i);
        }
        
        if (normA == 0.0 || normB == 0.0) {
            return 0.0;
        }
        
        return dotProduct / (Math.sqrt(normA) * Math.sqrt(normB));
    }

    private double getDocumentBoost(KnowledgeDocument doc) {
        switch (doc.getType()) {
            case "book_description":
                return 0.18;
            case "book":
                return 0.10;
            case "category":
                return -0.05;
            default:
                return 0.0;
        }
    }

    // Tìm top-K documents tương tự
    public List<KnowledgeDocument> retrieve(String query) throws IOException, InterruptedException {
        if (knowledgeBase.isEmpty()) {
            System.out.println("RAG: Knowledge base is empty");
            return new ArrayList<>();
        }
        
        // Lấy embedding của query
        List<Double> queryEmbedding = getEmbedding(query);
        if (queryEmbedding == null || queryEmbedding.isEmpty()) {
            System.err.println("RAG: Failed to get query embedding");
            return new ArrayList<>();
        }
        
        // Tính similarity cho mỗi document
        for (KnowledgeDocument doc : knowledgeBase) {
            if (doc.getEmbedding().isEmpty()) {
                // Lấy embedding cho document nếu chưa có
                List<Double> docEmbedding = getEmbedding(doc.getContent());
                if (docEmbedding != null) {
                    doc.setEmbedding(docEmbedding);
                }
            }
            
            if (!doc.getEmbedding().isEmpty()) {
                double sim = cosineSimilarity(queryEmbedding, doc.getEmbedding());
                doc.setSimilarity(sim);
            }
        }
        
        // Sắp xếp theo similarity giảm dần, cộng điểm ưu tiên cho book docs
        List<KnowledgeDocument> results = knowledgeBase.stream()
                .filter(doc -> !doc.getEmbedding().isEmpty())
                .sorted((a, b) -> {
                    double scoreA = a.getSimilarity() + getDocumentBoost(a);
                    double scoreB = b.getSimilarity() + getDocumentBoost(b);
                    int compare = Double.compare(scoreB, scoreA);
                    if (compare != 0) {
                        return compare;
                    }
                    // Nếu bằng nhau, ưu tiên book_description
                    if ("book_description".equals(a.getType())) return -1;
                    if ("book_description".equals(b.getType())) return 1;
                    return 0;
                })
                .limit(TOP_K)
                .collect(Collectors.toList());
        
        System.out.println("RAG: Retrieved " + results.size() + " documents for query: " + query);
        for (KnowledgeDocument doc : results) {
            double boostedScore = doc.getSimilarity() + getDocumentBoost(doc);
            System.out.println("  - " + doc.getType() + ": " + doc.getContent().substring(0, Math.min(50, doc.getContent().length())) + "... (sim=" + String.format("%.3f", doc.getSimilarity()) + ", boosted=" + String.format("%.3f", boostedScore) + ")");
        }
        
        return results;
    }

    // Build context từ retrieved documents
    public String buildContext(List<KnowledgeDocument> documents) {
        if (documents.isEmpty()) {
            return "";
        }
        
        StringBuilder context = new StringBuilder();
        context.append("Thông tin từ cửa hàng:\n");
        
        for (int i = 0; i < documents.size(); i++) {
            KnowledgeDocument doc = documents.get(i);
            context.append((i + 1)).append(". ").append(doc.getContent()).append("\n");
        }
        
        return context.toString();
    }

    // Gọi RAG retrieval (load KB trước nếu cần)
    public String queryWithRag(String userMessage) throws IOException, InterruptedException, SQLException {
        // Nếu KB trống, load một lần
        if (knowledgeBase.isEmpty()) {
            loadKnowledgeBase();
        }
        
        // Retrieve documents
        List<KnowledgeDocument> documents = retrieve(userMessage);
        
        if (documents.isEmpty()) {
            return null;
        }
        
        // Nếu có sách/description trong kết quả, ưu tiên sử dụng chúng thay vì category
        boolean hasBookDocs = documents.stream()
                .anyMatch(doc -> "book".equals(doc.getType()) || "book_description".equals(doc.getType()));
        if (hasBookDocs) {
            documents = documents.stream()
                    .filter(doc -> "book".equals(doc.getType()) || "book_description".equals(doc.getType()))
                    .collect(Collectors.toList());
        }
        
        // Return context để sử dụng trong prompt
        return buildContext(documents);
    }

    // Getter để access knowledge base
    public List<KnowledgeDocument> getKnowledgeBase() {
        return knowledgeBase;
    }
}
