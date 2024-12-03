package com.example.bluejayday;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class GeminiService {

    @Value("${gemini.api.key}")
    private String geminiApiKey;

    private final String geminiApiUrl = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash-latest:generateContent"; // Without the key in the URL
    
    private final RestTemplate restTemplate;

    public GeminiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getGeminiResponse(String input) {
        String requestBody = "{\"contents\":[{\"parts\":[{\"text\":\"" + input + "\"}]}]}";

        // Make the request
        ResponseEntity<String> response = restTemplate.exchange(
            geminiApiUrl + "?key=" + geminiApiKey,  // Adding the key dynamically
            HttpMethod.POST,
            new HttpEntity<>(requestBody, createHeaders()),
            String.class
        );

        // Extract and return the 'text' from the response
        String geminiText = extractTextFromResponse(response.getBody());
        return geminiText;
    }

    private HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    private String extractTextFromResponse(String responseBody) {
        try {
            // Parse the response JSON
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(responseBody);
            JsonNode candidates = rootNode.path("candidates");

            if (candidates.isArray() && candidates.size() > 0) {
                JsonNode content = candidates.get(0).path("content");
                JsonNode parts = content.path("parts");

                if (parts.isArray() && parts.size() > 0) {
                    return parts.get(0).path("text").asText();  // Extracting the text part
                }
            }
        } catch (Exception e) {
            e.printStackTrace();  // Log error for debugging
        }
        return "No response text available";  // Default response if parsing fails
    }
}





