package com.tuandhpc05076.apiservice.demo;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class CallAPIDemo {
//    public static void main(String[] args) {
//        String apiUrl = "https://dummy.restapiexample.com/api/v1/employees";
//
//        // Tạo đối tượng HttpClient
//        HttpClient httpClient = HttpClients.createDefault();
//
//        // Tạo yêu cầu HTTP GET
//        HttpGet httpGet = new HttpGet(apiUrl);
//
//        try {
//            // Thực hiện yêu cầu và nhận phản hồi từ server
//            HttpResponse response = httpClient.execute(httpGet);
//
//            // Kiểm tra mã trạng thái của phản hồi
//            int statusCode = response.getStatusLine().getStatusCode();
//            System.out.println("Mã trạng thái: " + statusCode);
//
//            // Lấy nội dung của phản hồi
//            HttpEntity entity = response.getEntity();
//            if (entity != null) {
//                String responseBody = EntityUtils.toString(entity);
//                System.out.println("Nội dung phản hồi:");
//                System.out.println(responseBody);
//
//                // Xử lý dữ liệu phản hồi ở đây
//                // Ví dụ: Parse JSON, xử lý XML, ...
//            }
//
//        } catch (IOException e) {
//            System.err.println("Lỗi khi gọi API: " + e.getMessage());
//        } finally {
//            // Đóng kết nối và giải phóng tài nguyên
//            httpGet.releaseConnection();
//        }
//    }
public static String translateVietnameseToEnglish(String vietnameseText) {
    try {
        String encodedText = URLEncoder.encode(vietnameseText, StandardCharsets.UTF_8);
        String encodedLangpair = URLEncoder.encode("vi|en", StandardCharsets.UTF_8);
        String url = "https://api.mymemory.translated.net/get?q=" + encodedText + "&langpair=" + encodedLangpair;
        HttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        HttpResponse response = httpClient.execute(httpGet);
        int statusCode = response.getStatusLine().getStatusCode();
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            String responseBody = EntityUtils.toString(entity);
            JsonObject jsonObject = JsonParser.parseString(responseBody).getAsJsonObject();
            String translatedText = jsonObject.getAsJsonObject("responseData").get("translatedText").getAsString();

            return translatedText;
        }

    } catch (IOException e) {
        System.err.println("Lỗi khi gọi API: " + e.getMessage());
    }

    return null;
}
    public static void main(String[] args) {
        String vietnameseText = "Bác sĩ Nguyễn Văn A là một chuyên gia trong lĩnh vực Chuyên khoa Ngoại thần kinh. Hiện đang công tác tại Bệnh viện Đa khoa Trung ương.";
        String translatedText = translateVietnameseToEnglish(vietnameseText);
        System.out.println("Tiếng Việt: " + vietnameseText);
        System.out.println("Tiếng Anh: " + translatedText);
    }
}
