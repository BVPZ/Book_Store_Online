package com.bookshop.service;

import com.bookshop.dao.BookDao;
import com.bookshop.model.Book;
import com.google.gson.*;
import okhttp3.*;

import java.util.ArrayList;
import java.util.List;

public class AiRecommendService {

    private static final String HF_TOKEN = "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";
    private static final String API_URL = "https://router.huggingface.co/novita/v3/openai/chat/completions";

    public static ArrayList<Book> getAiSuggestions(
            List<Book> viewedBooks,
            List<Book> allBooks) {

        ArrayList<Book> result = new ArrayList<>();

        try {
            List<Integer> viewedIds = new ArrayList<>();
            for (Book b : viewedBooks) viewedIds.add(b.getBookId());

            List<Book> coPurchasedBooks = BookDao.getCourchasedBooks(viewedIds);

            StringBuilder prompt = new StringBuilder();
            prompt.append("Bạn là hệ thống gợi ý sách thông minh. Chỉ trả về JSON, không giải thích.\n\n");

            prompt.append("SÁCH USER ĐÃ XEM GẦN ĐÂY:\n");
            for (Book b : viewedBooks) {
                prompt.append("ID:").append(b.getBookId())
                      .append(" Ten:").append(b.getTitle())
                      .append(" TacGia:").append(b.getAuthorName() != null ? b.getAuthorName() : "")
                      .append(" DanhMuc:").append(b.getGenres() != null ? b.getGenres() : "")
                      .append(" MoTa:").append(b.getDescription() != null ? b.getDescription() : "")
                      .append("\n");
            }

            if (!coPurchasedBooks.isEmpty()) {
                prompt.append("\nSÁCH NGƯỜI DÙNG KHÁC HAY MUA CÙNG:\n");
                for (Book b : coPurchasedBooks) {
                    prompt.append("ID:").append(b.getBookId())
                          .append(" Ten:").append(b.getTitle())
                          .append("\n");
                }
            }

            prompt.append("\nTOÀN BỘ SÁCH TRONG CỬA HÀNG:\n");
            for (Book b : allBooks) {
                prompt.append("ID:").append(b.getBookId())
                      .append(" Ten:").append(b.getTitle())
                      .append(" TacGia:").append(b.getAuthorName() != null ? b.getAuthorName() : "")
                      .append(" DanhMuc:").append(b.getGenres() != null ? b.getGenres() : "")
                      .append(" MoTa:").append(b.getDescription() != null ? b.getDescription() : "")
                      .append("\n");
            }

            prompt.append("\nDựa trên các tiêu chí sau để chọn 6 cuốn sách phù hợp nhất:\n");
            prompt.append("1. ƯU TIÊN CAO NHẤT: Cùng danh mục với sách user đã xem\n");
            prompt.append("2. Cùng tác giả với sách user đã xem\n");
            prompt.append("3. Nội dung/mô tả tương tự\n");
            prompt.append("4. Sách người dùng khác hay mua cùng\n");
            prompt.append("LƯU Ý: Ít nhất 4 trong 6 cuốn PHẢI cùng danh mục với sách user đã xem.\n");
            prompt.append("Chỉ trả về JSON: {\"book_ids\": [1, 2, 3, 4, 5, 6]}");

            JsonObject requestBody = new JsonObject();
            requestBody.addProperty("model", "meta-llama/llama-3.1-8b-instruct");
            requestBody.addProperty("max_tokens", 100);

            JsonArray messages = new JsonArray();
            JsonObject message = new JsonObject();
            message.addProperty("role", "user");
            message.addProperty("content", prompt.toString());
            messages.add(message);
            requestBody.add("messages", messages);

            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(60, java.util.concurrent.TimeUnit.SECONDS)
                    .readTimeout(60, java.util.concurrent.TimeUnit.SECONDS)
                    .build();

            Request httpRequest = new Request.Builder()
                    .url(API_URL)
                    .post(RequestBody.create(
                            requestBody.toString(),
                            MediaType.parse("application/json")))
                    .addHeader("Authorization", "Bearer " + HF_TOKEN)
                    .addHeader("Content-Type", "application/json")
                    .build();

            Response httpResponse = client.newCall(httpRequest).execute();
            String responseBody = httpResponse.body().string();
            System.out.println("HF Status: " + httpResponse.code());
            System.out.println("HF Response: " + responseBody);

            if (httpResponse.code() != 200) {
                throw new Exception("HF API lỗi status " + httpResponse.code());
            }

            JsonObject json = JsonParser.parseString(responseBody).getAsJsonObject();
            String text = json.getAsJsonArray("choices")
                              .get(0).getAsJsonObject()
                              .getAsJsonObject("message")
                              .get("content").getAsString().trim();

            System.out.println("AI text: " + text);

            int start = text.indexOf("{");
            int end = text.lastIndexOf("}") + 1;
            if (start >= 0 && end > start) {
                String jsonStr = text.substring(start, end);
                JsonObject aiResult = JsonParser.parseString(jsonStr).getAsJsonObject();
                JsonArray bookIds = aiResult.getAsJsonArray("book_ids");

                String[] ids = new String[bookIds.size()];
                for (int i = 0; i < bookIds.size(); i++) {
                    ids[i] = String.valueOf(bookIds.get(i).getAsInt());
                }
                result = BookDao.getBooksByIds(ids);
                System.out.println("AI gợi ý thành công: " + result.size() + " cuốn");
            } else {
                throw new Exception("Không tìm thấy JSON: " + text);
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("AI lỗi, fallback về random");
            result = BookDao.getSuggestedBooks(0, 0, 0);
        }

        return result;
    }
}