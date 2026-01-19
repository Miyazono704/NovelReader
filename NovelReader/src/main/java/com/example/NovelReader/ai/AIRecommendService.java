package com.example.NovelReader.ai;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.example.NovelReader.javabean.Book;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * AI书籍推荐服务
 * 调用OpenAI兼容的API接口获取智能书籍推荐
 */
public class AIRecommendService {

    private static final String TAG = "AIRecommendService";
    
    private final ExecutorService executor;
    private final Handler mainHandler;

    public AIRecommendService() {
        executor = Executors.newSingleThreadExecutor();
        mainHandler = new Handler(Looper.getMainLooper());
    }

    /**
     * 回调接口
     */
    public interface AIRecommendCallback {
        void onSuccess(List<AIRecommendBook> recommendations, String aiMessage);
        void onError(String errorMessage);
    }

    /**
     * AI推荐的书籍数据类
     */
    public static class AIRecommendBook {
        private String name;
        private String author;
        private String category;
        private String reason;
        private String description;

        public AIRecommendBook() {}

        public AIRecommendBook(String name, String author, String category, String reason, String description) {
            this.name = name;
            this.author = author;
            this.category = category;
            this.reason = reason;
            this.description = description;
        }

        // Getters and Setters
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }

        public String getAuthor() { return author; }
        public void setAuthor(String author) { this.author = author; }

        public String getCategory() { return category; }
        public void setCategory(String category) { this.category = category; }

        public String getReason() { return reason; }
        public void setReason(String reason) { this.reason = reason; }

        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
    }

    /**
     * 根据用户的阅读历史获取AI推荐
     * @param userBooks 用户书架上的书籍列表
     * @param callback 回调
     */
    public void getRecommendations(List<Book> userBooks, AIRecommendCallback callback) {
        executor.execute(() -> {
            try {
                String result = callAIApi(buildPrompt(userBooks));
                parseAndCallback(result, callback);
            } catch (Exception e) {
                Log.e(TAG, "AI推荐请求失败", e);
                mainHandler.post(() -> callback.onError("推荐服务暂时不可用：" + e.getMessage()));
            }
        });
    }

    /**
     * 根据用户输入的偏好获取AI推荐
     * @param userPreference 用户输入的偏好描述
     * @param callback 回调
     */
    public void getRecommendationsByPreference(String userPreference, AIRecommendCallback callback) {
        executor.execute(() -> {
            try {
                String result = callAIApi(buildPreferencePrompt(userPreference));
                parseAndCallback(result, callback);
            } catch (Exception e) {
                Log.e(TAG, "AI推荐请求失败", e);
                mainHandler.post(() -> callback.onError("推荐服务暂时不可用：" + e.getMessage()));
            }
        });
    }

    /**
     * 构建基于阅读历史的提示词
     */
    private String buildPrompt(List<Book> userBooks) {
        StringBuilder sb = new StringBuilder();
        sb.append("你是一个专业的小说推荐助手。根据用户的阅读历史，推荐5本可能感兴趣的小说。\n\n");
        
        if (userBooks != null && !userBooks.isEmpty()) {
            sb.append("用户的阅读历史：\n");
            for (Book book : userBooks) {
                sb.append("- 《").append(book.getName()).append("》");
                if (book.getDetail() != null && !book.getDetail().isEmpty()) {
                    sb.append("：").append(book.getDetail().length() > 50 ? 
                            book.getDetail().substring(0, 50) + "..." : book.getDetail());
                }
                sb.append("\n");
            }
        } else {
            sb.append("用户是新用户，请推荐一些热门的网络小说。\n");
        }
        
        sb.append("\n请按以下JSON格式返回推荐结果，不要返回其他内容：\n");
        sb.append("{\n");
        sb.append("  \"message\": \"给用户的推荐语\",\n");
        sb.append("  \"recommendations\": [\n");
        sb.append("    {\n");
        sb.append("      \"name\": \"书名\",\n");
        sb.append("      \"author\": \"作者\",\n");
        sb.append("      \"category\": \"类型\",\n");
        sb.append("      \"reason\": \"推荐理由\",\n");
        sb.append("      \"description\": \"简介\"\n");
        sb.append("    }\n");
        sb.append("  ]\n");
        sb.append("}");
        
        return sb.toString();
    }

    /**
     * 构建基于用户偏好的提示词
     */
    private String buildPreferencePrompt(String userPreference) {
        StringBuilder sb = new StringBuilder();
        sb.append("你是一个专业的小说推荐助手。根据用户的阅读偏好，推荐5本可能感兴趣的小说。\n\n");
        sb.append("用户的偏好描述：").append(userPreference).append("\n\n");
        sb.append("请按以下JSON格式返回推荐结果，不要返回其他内容：\n");
        sb.append("{\n");
        sb.append("  \"message\": \"给用户的推荐语\",\n");
        sb.append("  \"recommendations\": [\n");
        sb.append("    {\n");
        sb.append("      \"name\": \"书名\",\n");
        sb.append("      \"author\": \"作者\",\n");
        sb.append("      \"category\": \"类型\",\n");
        sb.append("      \"reason\": \"推荐理由\",\n");
        sb.append("      \"description\": \"简介\"\n");
        sb.append("    }\n");
        sb.append("  ]\n");
        sb.append("}");
        
        return sb.toString();
    }

    /**
     * 调用AI API
     */
    private String callAIApi(String prompt) throws IOException, JSONException {
        URL url = new URL(AIConfig.API_URL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        
        try {
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Authorization", "Bearer " + AIConfig.API_KEY);
            connection.setDoOutput(true);
            connection.setConnectTimeout(AIConfig.CONNECT_TIMEOUT);
            connection.setReadTimeout(AIConfig.READ_TIMEOUT);

            // 构建请求体
            JSONObject requestBody = new JSONObject();
            requestBody.put("model", AIConfig.MODEL);
            requestBody.put("temperature", AIConfig.TEMPERATURE);
            requestBody.put("max_tokens", AIConfig.MAX_TOKENS);
            
            JSONArray messages = new JSONArray();
            JSONObject userMessage = new JSONObject();
            userMessage.put("role", "user");
            userMessage.put("content", prompt);
            messages.put(userMessage);
            requestBody.put("messages", messages);

            // 发送请求
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = requestBody.toString().getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            // 读取响应
            int responseCode = connection.getResponseCode();
            Log.d(TAG, "API响应码: " + responseCode);
            
            if (responseCode == HttpURLConnection.HTTP_OK) {
                try (BufferedReader br = new BufferedReader(
                        new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        response.append(line);
                    }
                    return response.toString();
                }
            } else {
                // 读取错误信息
                try (BufferedReader br = new BufferedReader(
                        new InputStreamReader(connection.getErrorStream(), StandardCharsets.UTF_8))) {
                    StringBuilder error = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        error.append(line);
                    }
                    throw new IOException("API请求失败: " + responseCode + " - " + error.toString());
                }
            }
        } finally {
            connection.disconnect();
        }
    }

    /**
     * 解析AI响应并回调
     */
    private void parseAndCallback(String apiResponse, AIRecommendCallback callback) {
        try {
            JSONObject response = new JSONObject(apiResponse);
            JSONArray choices = response.getJSONArray("choices");
            
            if (choices.length() > 0) {
                JSONObject choice = choices.getJSONObject(0);
                JSONObject message = choice.getJSONObject("message");
                String content = message.getString("content");
                
                // 解析AI返回的JSON内容
                // 尝试提取JSON部分（AI可能会在JSON前后添加其他文本）
                int jsonStart = content.indexOf("{");
                int jsonEnd = content.lastIndexOf("}");
                if (jsonStart != -1 && jsonEnd != -1 && jsonEnd > jsonStart) {
                    content = content.substring(jsonStart, jsonEnd + 1);
                }
                
                JSONObject aiResult = new JSONObject(content);
                String aiMessage = aiResult.optString("message", "为您推荐以下书籍：");
                JSONArray recommendations = aiResult.getJSONArray("recommendations");
                
                List<AIRecommendBook> bookList = new ArrayList<>();
                for (int i = 0; i < recommendations.length(); i++) {
                    JSONObject bookJson = recommendations.getJSONObject(i);
                    AIRecommendBook book = new AIRecommendBook();
                    book.setName(bookJson.optString("name", "未知书名"));
                    book.setAuthor(bookJson.optString("author", "未知作者"));
                    book.setCategory(bookJson.optString("category", "未知类型"));
                    book.setReason(bookJson.optString("reason", ""));
                    book.setDescription(bookJson.optString("description", ""));
                    bookList.add(book);
                }
                
                mainHandler.post(() -> callback.onSuccess(bookList, aiMessage));
            } else {
                mainHandler.post(() -> callback.onError("AI未返回有效响应"));
            }
        } catch (JSONException e) {
            Log.e(TAG, "解析AI响应失败", e);
            mainHandler.post(() -> callback.onError("解析推荐结果失败"));
        }
    }

    /**
     * 释放资源
     */
    public void shutdown() {
        if (executor != null && !executor.isShutdown()) {
            executor.shutdown();
        }
    }
}
