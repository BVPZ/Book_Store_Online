package com.bookshop.controller;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.time.Duration;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.bookshop.dao.AuthorDao;
import com.bookshop.dao.BookDao;
import com.bookshop.dao.CategoryDao;
import com.bookshop.model.Author;
import com.bookshop.model.Book;
import com.bookshop.model.Category;
import com.bookshop.service.RagService;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/chatbot")
public class ChatbotServlet extends HttpServlet {

    private static final String OLLAMA_URL      = "http://127.0.0.1:11434/v1/chat/completions";
    private static final String MODEL_BOOKBOT    = "bookbot";
    private static final String MODEL_BOOKBOTSQL = "bookbotsql";

    private static final String SQL_MODEL_SYSTEM_PROMPT = "Bạn là AI chuyên sinh câu lệnh SQL MySQL cho hệ thống cửa hàng sách. "
            + "Chỉ trả về đúng một câu lệnh SQL thuần hoặc đúng một từ: NONE nếu không thể tạo SQL. "
            + "Schema: books b, authors a, categories c, book_categories bc, orders o, order_details od, users u, cart c, cart_items ci, payments p. "
            + "books có author_id, không có category_id. categories phải join qua book_categories: b.book_id = bc.book_id và bc.category_id = c.category_id. "
            + "authors phải join qua b.author_id = a.author_id. Không dùng a.id, không dùng b.category_id. "
            + "Luôn chọn toàn bộ cột chính của sách bằng b.*, không chọn riêng một trường như b.title hoặc một trường rỗng khác trừ khi câu hỏi yêu cầu tính toán tổng hợp. "
            + "Nếu câu hỏi liên quan đến nội dung, mô tả, tóm tắt, cốt truyện hoặc 'nội dung cuốn sách', hãy dùng cột b.description trong điều kiện WHERE và dùng LIKE '%...%'. "
            + "Nếu câu hỏi liên quan tới tiêu đề, dùng b.title. Nếu liên quan đến tác giả, dùng a.author_name. "
            + "Nếu liên quan đến thể loại, dùng c.category_name. Luôn tuân thủ schema đã biết. KHÔNG giải thích, KHÔNG dùng markdown. ";

    // Dùng chung HttpClient để tận dụng connection pooling
    private static final HttpClient HTTP_CLIENT = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(10))
            .build();



    private static RagService ragService;

    // Ensure RAG is loaded on-demand (thread-safe)
    public static synchronized void ensureRagLoaded() {
        if (ragService == null) {
            ragService = new RagService();
            try {
                ragService.loadKnowledgeBase();
                System.out.println("[Chatbot] RAG Service initialized (on-demand).");
            } catch (SQLException e) {
                System.err.println("[Chatbot] Failed to initialize RAG: " + e.getMessage());
            }
        }
    }

    // ── Khởi tạo ─────────────────────────────────────────────────────────────

    @Override
    public void init() throws ServletException {
        super.init();
        if (ragService == null) {
            ragService = new RagService();
            try {
                ragService.loadKnowledgeBase();
                System.out.println("[Chatbot] RAG Service initialized successfully.");
            } catch (SQLException e) {
                System.err.println("[Chatbot] Failed to initialize RAG: " + e.getMessage());
            }
        }
    }

    // ── Entry point ───────────────────────────────────────────────────────────

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        resp.setContentType("application/json;charset=UTF-8");
        JsonObject json = new JsonObject();

        String userMessage = req.getParameter("message");
        if (userMessage == null || userMessage.isBlank()) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            json.addProperty("error", "Thiếu tin nhắn yêu cầu.");
            resp.getWriter().write(json.toString());
            return;
        }

        try {
            String queryType = classifyQuery(userMessage);
            System.out.println("[Chatbot] Query type = " + queryType);

            String reply;
            if (queryType.startsWith("RESPOND:")) {
                reply = queryType.substring("RESPOND:".length()).trim();
            } else {
                reply = switch (queryType) {
                    case "DataQuery"          -> handleDataQuery(userMessage);
                    case "ContentDetailQuery" -> handleContentDetailQuery(userMessage);
                    default                   -> handleGeneralQuery(userMessage);
                };
            }

            json.addProperty("reply", reply);

        } catch (IOException | InterruptedException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            json.addProperty("error", "Lỗi chatbot: " + e.getMessage());
        }

        resp.getWriter().write(json.toString());
    }

    // ── Phân loại câu hỏi ────────────────────────────────────────────────────

    /**
     * Gửi câu hỏi cho model bookbot để phân loại thành một trong 3 nhãn:
     * DataQuery | ContentDetailQuery | Unknown
     */
    private String classifyQuery(String userMessage) throws IOException, InterruptedException {
        String prompt = "Bạn là bộ phân loại câu hỏi đầu vào cho cửa hàng sách. "
                + "Nếu khách hàng chào hỏi đơn giản như 'xin chào', 'chào bạn', 'hi', hãy trả lời lại bằng lời chào thân thiện và bắt đầu với 'RESPOND:'. "
                + "Nếu khách hàng hỏi 'bạn có thể làm gì', hãy nêu rõ các chức năng: tìm kiếm sách, tìm thông tin sách có sẵn, tìm tác giả/thể loại, và tư vấn mua sách. "
                + "Nếu câu hỏi liên quan đến cửa hàng, trả lời gọn gàng. "
                + "Nếu câu hỏi thuộc về tìm kiếm sách hoặc tìm kiếm nội dung, chỉ trả về một trong hai nhãn sau: DataQuery hoặc ContentDetailQuery. "
                + "Nếu câu hỏi không liên quan đến những chức năng sau: Tìm kiếm sách theo yêu cầu, Tìm thông tin sách có sẵn, Tìm tác giả, thể loại, Tư vấn mua sách, thì trả lời chính xác: 'RESPOND: Không thể trả lời các câu hỏi của bạn'. "
                + "Khi trả lời trực tiếp, luôn bắt đầu bằng 'RESPOND:' và chỉ dùng cho câu trả lời tự nhiên, không dùng cho nhãn phân loại. "
                + "Khi phân loại, chỉ trả về đúng một chuỗi: DataQuery, ContentDetailQuery, hoặc Unknown. Không thêm ký tự nào khác.\n"
                + "Câu hỏi về cuốn sách có trong cửa hàng không, hỏi về giá tiền của cuốn sách, hỏi về nội dung cuốn sách là câu hỏi phân loại DataQuery.\n"
                + "Câu hỏi: " + userMessage;

        String result = callOllamaWithBookbot(prompt, 256).trim();
        result = result.lines().findFirst().orElse(result).trim();

        if (result.startsWith("RESPOND:")) {
            String payload = result.substring("RESPOND:".length()).trim();
            if (payload.equals("DataQuery") || payload.equals("ContentDetailQuery") || payload.equals("Unknown")) {
                System.out.println("[Chatbot] Misleading RESPOND payload treated as label: " + payload);
                return payload;
            }
            return "RESPOND:" + payload;
        }

        return switch (result) {
            case "ContentDetailQuery", "DataQuery", "Unknown" -> result;
            default -> {
                System.out.println("[Chatbot] Unexpected classification result: " + result);
                yield "Unknown";
            }
        };
    }

    // ── Xử lý theo loại câu hỏi ──────────────────────────────────────────────

    /**
     * DataQuery: bookbotsql sinh SQL (với schema) → trích xuất → validate → thực thi → bookbot diễn giải.
     * Nếu model không sinh được SQL hợp lệ → fallback sang RAG.
     */
    private String handleDataQuery(String userMessage) throws IOException, InterruptedException {
        // 1. Gửi thẳng câu hỏi cho bookbotsql
        String rawResponse = callOllamaForSql(userMessage).trim();
        System.out.println("[Chatbot][DataQuery] Raw SQL response: " + rawResponse);

        // Nếu model trả về NONE thì fallback sang RAG
        if (rawResponse.equalsIgnoreCase("NONE")) {
            System.out.println("[Chatbot][DataQuery] bookbotsql trả về NONE, fallback sang RAG");
            return handleRagQuery(userMessage);
        }

        // 2. Trích xuất SQL (model có thể bọc trong ```sql ... ```)
        String sql = extractSql(rawResponse);
        System.out.println("[Chatbot][DataQuery] Extracted SQL: " + sql);

        // 3. Kiểm tra an toàn — fallback sang RAG nếu không hợp lệ
        if (!isSafeSelectQuery(sql)) {
            System.out.println("[Chatbot][DataQuery] No valid SQL, falling back to RAG");
            return handleRagQuery(userMessage);
        }

        // 4. Thực thi SQL
        String data;
        try {
            data = executeSqlAndGetData(sql);
            System.out.println("[Chatbot][DataQuery] Data retrieved: " + data);
            // Nếu kết quả trả về không tìm thấy mục nào, fallback sang RAG
            if (data != null && data.contains("Không tìm thấy")) {
                System.out.println("[Chatbot][DataQuery] Query returned empty results, falling back to RAG");
                return handleRagQuery(userMessage);
            }
        } catch (Exception e) {
            System.err.println("[Chatbot][DataQuery] SQL execution error: " + e.getMessage());
            return handleRagQuery(userMessage); // fallback
        }

        // 5. bookbot diễn giải kết quả
        String prompt = "Dựa theo dữ liệu tôi gửi dưới đây, hãy đưa ra gợi ý cho người dùng, "
                + "trả lời tự nhiên vì giờ câu trả lời của bạn sẽ giao tiếp với người dùng."
                + "Không trả lời kiểu 'dựa theo dữ liệu bạn cung cấp' mà hãy trả lời :'Dựa trên câu hỏi của bạn cửa hàng chúng tôi tìm thấy 1 số sách như sau' "
                + "Nếu không có dữ liệu nào được gửi bên dưới hoặc 1 dòng dữ liệu có tên sách là null thì hệ thống không tìm thấy dữ liệu này, Trả lời là không có.\n"
                + "Dữ liệu từ hệ thống tìm được:\n" + data
                + ".Câu hỏi của khách hàng: " + userMessage;
        return callOllama(prompt, "", MODEL_BOOKBOT);
    }

    /**
     * ContentDetailQuery và Unknown đều dùng RAG + bookbot.
     */
    private String handleContentDetailQuery(String userMessage) throws IOException, InterruptedException {
        return handleRagQuery(userMessage);
    }

    private String handleGeneralQuery(String userMessage) throws IOException, InterruptedException {
        return handleRagQuery(userMessage);
    }

    /** RAG context → bookbot */
    private String handleRagQuery(String userMessage) throws IOException, InterruptedException {
        String ragContext = getRagContext(userMessage);
        return callOllama(userMessage, ragContext, MODEL_BOOKBOT);
    }

    // ── RAG ──────────────────────────────────────────────────────────────────

    private String getRagContext(String userMessage) {
        try {
            if (ragService != null) {
                return ragService.queryWithRag(userMessage);
            }
        } catch (Exception e) {
            System.err.println("[Chatbot] RAG retrieval error: " + e.getMessage());
        }
        return "";
    }

    // ── Ollama HTTP client ────────────────────────────────────────────────────

    /** Gọi Ollama cho chat thông thường (bookbot). */
    private String callOllama(String userMessage, String ragContext, String model)
            throws IOException, InterruptedException {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(OLLAMA_URL))
                .timeout(Duration.ofSeconds(100))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(
                        buildRequestBody(userMessage, ragContext, model), StandardCharsets.UTF_8))
                .build();

        HttpResponse<String> response = HTTP_CLIENT
                .send(request, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));

        if (response.statusCode() != 200) {
            throw new IOException("Ollama trả về HTTP " + response.statusCode() + ": " + response.body());
        }
        return parseReply(response.body());
    }

    /**
     * Gọi bookbotsql để sinh SQL và bổ sung hướng dẫn dùng b.description khi hỏi về nội dung sách.
     */
    private String callOllamaForSql(String userMessage) throws IOException, InterruptedException {
        JsonObject systemMsg = new JsonObject();
        systemMsg.addProperty("role", "system");
        systemMsg.addProperty("content", SQL_MODEL_SYSTEM_PROMPT);

        JsonObject userMsg = new JsonObject();
        userMsg.addProperty("role", "user");
        userMsg.addProperty("content", userMessage);

        JsonArray messages = new JsonArray();
        messages.add(systemMsg);
        messages.add(userMsg);

        JsonObject payload = new JsonObject();
        payload.addProperty("model", MODEL_BOOKBOTSQL);
        payload.add("messages", messages);
        payload.addProperty("temperature", 0.0);
        payload.addProperty("max_tokens", 512);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(OLLAMA_URL))
                .timeout(Duration.ofSeconds(100))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(payload.toString(), StandardCharsets.UTF_8))
                .build();

        HttpResponse<String> response = HTTP_CLIENT
                .send(request, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));

        if (response.statusCode() != 200) {
            throw new IOException("bookbotsql trả về HTTP " + response.statusCode() + ": " + response.body());
        }
        return parseReply(response.body());
    }

    private String callOllamaWithSqlModel(String content, int maxTokens) throws IOException, InterruptedException {
        JsonObject userMsg = new JsonObject();
        userMsg.addProperty("role", "user");
        userMsg.addProperty("content", content);

        JsonArray messages = new JsonArray();
        messages.add(userMsg);

        JsonObject payload = new JsonObject();
        payload.addProperty("model", MODEL_BOOKBOTSQL);
        payload.add("messages", messages);
        payload.addProperty("temperature", 0.0);
        payload.addProperty("max_tokens", maxTokens);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(OLLAMA_URL))
                .timeout(Duration.ofSeconds(100))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(payload.toString(), StandardCharsets.UTF_8))
                .build();

        HttpResponse<String> response = HTTP_CLIENT
                .send(request, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));

        if (response.statusCode() != 200) {
            throw new IOException("bookbotsql trả về HTTP " + response.statusCode() + ": " + response.body());
        }
        return parseReply(response.body());
    }

    /** Trích xuất câu SQL từ response của model (có thể bọc trong markdown). */
    private String extractSql(String raw) {
        if (raw == null || raw.isBlank()) return "";

        // Ưu tiên: trích từ ```sql ... ``` hoặc ``` ... ```
        Matcher mdMatcher = Pattern.compile(
                "```(?:sql)?\\s*([\\s\\S]*?)```", Pattern.CASE_INSENSITIVE).matcher(raw);
        if (mdMatcher.find()) {
            return mdMatcher.group(1).trim();
        }

        // Fallback: tìm từ từ khóa SELECT trở đi
        int selectIdx = raw.toUpperCase().indexOf("SELECT");
        if (selectIdx >= 0) {
            return raw.substring(selectIdx).trim();
        }

        return raw; // trả nguyên nếu không nhận ra
    }

    private String buildRequestBody(String userMessage, String ragContext, String model) {
        String userContent = (ragContext != null && !ragContext.isBlank())
                ? "Dựa theo dữ liệu tôi gửi dưới đây, hãy đưa ra gợi ý cho người dùng, "
                + "Không trả lời kiểu 'dựa theo dữ liệu bạn cung cấp' mà hãy trả lời :'Dựa trên câu hỏi của bạn cửa hàng chúng tôi tìm thấy 1 số sách như sau' "
                  + "trả lời tự nhiên vì giờ bạn đang giao tiếp với người dùng.\n\n"
                  + "Dữ liệu tham khảo từ cửa hàng:\n" + ragContext
                  + "\n\nCâu hỏi của khách hàng: " + userMessage
                : userMessage;

        JsonObject userMsg = new JsonObject();
        userMsg.addProperty("role", "user");
        userMsg.addProperty("content", userContent);

        JsonArray messages = new JsonArray();
        messages.add(userMsg);

        JsonObject payload = new JsonObject();
        payload.addProperty("model", model);
        payload.add("messages", messages);
        payload.addProperty("temperature", 0.2);
        payload.addProperty("max_tokens", 512);

        return payload.toString();
    }

    private String parseReply(String body) throws IOException {
        JsonObject json     = JsonParser.parseString(body).getAsJsonObject();
        JsonArray  choices  = json.getAsJsonArray("choices");
        if (choices == null || choices.size() == 0) {
            throw new IOException("Ollama trả về phản hồi rỗng.");
        }
        JsonObject message = choices.get(0).getAsJsonObject().getAsJsonObject("message");
        if (message == null || !message.has("content")) {
            throw new IOException("Không có nội dung trong phản hồi từ Ollama.");
        }
        return message.get("content").getAsString();
    }

    // ── SQL safety & execution ────────────────────────────────────────────────

    /**
     * Chỉ cho phép câu SELECT thuần.
     * Từ chối nếu là "NONE" (model báo không thể sinh SQL) hoặc chứa lệnh nguy hiểm.
     */
    private boolean isSafeSelectQuery(String sql) {
        if (sql == null || sql.isBlank()) return false;
        String upper = sql.trim().toUpperCase();
        if (upper.equals("NONE") || upper.startsWith("NONE")) return false;
        if (!upper.startsWith("SELECT")) return false;

        String[] blocked = {"INSERT", "UPDATE", "DELETE", "DROP", "CREATE",
                            "ALTER", "TRUNCATE", "EXEC", "--"};
        for (String kw : blocked) {
            if (upper.contains(kw)) return false;
        }
        return true;
    }

    private String executeSqlAndGetData(String sql) throws SQLException {
        String lower = sql.toLowerCase().trim();
        if (lower.contains("from books") || lower.contains("join books")) {
            return formatBooksData(BookDao.getBooksFromSql(sql));
        } else if (lower.contains("from categories") || lower.contains("join categories")) {
            return formatCategoriesData(CategoryDao.getCategoriesFromSql(sql));
        } else if (lower.contains("from authors") || lower.contains("join authors")) {
            return formatAuthorsData(AuthorDao.getAuthorsFromSql(sql));
        } else {
            // Mặc định thử với bảng books
            return formatBooksData(BookDao.getBooksFromSql(sql));
        }
    }

    // ── Data formatters ───────────────────────────────────────────────────────

    private String formatBooksData(List<Book> books) {
        if (books.isEmpty()) return "Không tìm thấy sách nào phù hợp.";
        StringBuilder sb = new StringBuilder("Danh sách sách:\n");
        int limit = Math.min(books.size(), 10);
        for (int i = 0; i < limit; i++) {
            Book b = books.get(i);
            sb.append(i + 1).append(". ").append(b.getTitle())
              .append(" - Giá: ").append(String.format("%,.0f", b.getPrice())).append("₫\n");
        }
        if (books.size() > 10) {
            sb.append("... và ").append(books.size() - 10).append(" cuốn khác.");
        }
        return sb.toString().trim();
    }

    private String formatCategoriesData(List<Category> categories) {
        if (categories.isEmpty()) return "Không tìm thấy thể loại nào.";
        StringBuilder sb = new StringBuilder("Danh sách thể loại:\n");
        for (int i = 0; i < categories.size(); i++) {
            sb.append(i + 1).append(". ")
              .append(categories.get(i).getCategoryName()).append("\n");
        }
        return sb.toString().trim();
    }

    private String formatAuthorsData(List<Author> authors) {
        if (authors.isEmpty()) return "Không tìm thấy tác giả nào.";
        StringBuilder sb = new StringBuilder("Danh sách tác giả:\n");
        int limit = Math.min(authors.size(), 10);
        for (int i = 0; i < limit; i++) {
            Author a = authors.get(i);
            sb.append(i + 1).append(". ").append(a.getAuthorName());
            if (a.getBio() != null && !a.getBio().isEmpty()) {
                sb.append(" - ").append(a.getBio());
            }
            sb.append("\n");
        }
        if (authors.size() > 10) {
            sb.append("... và ").append(authors.size() - 10).append((" tác giả khác."));
        }
        return sb.toString().trim();
    }
    private String callOllamaWithBookbot(String content, int maxTokens) throws IOException, InterruptedException {
        JsonObject userMsg = new JsonObject();
        userMsg.addProperty("role", "user");
        userMsg.addProperty("content", content);

        JsonArray messages = new JsonArray();
        messages.add(userMsg);

        JsonObject payload = new JsonObject();
        payload.addProperty("model", MODEL_BOOKBOT); // <-- dùng bookbot
        payload.add("messages", messages);
        payload.addProperty("temperature", 0.0);
        payload.addProperty("max_tokens", maxTokens);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(OLLAMA_URL))
                .timeout(Duration.ofSeconds(100))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(payload.toString(), StandardCharsets.UTF_8))
                .build();

        HttpResponse<String> response = HTTP_CLIENT
                .send(request, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));

        if (response.statusCode() != 200) {
            throw new IOException("bookbot trả về HTTP " + response.statusCode() + ": " + response.body());
        }
        return parseReply(response.body());
    }
}
