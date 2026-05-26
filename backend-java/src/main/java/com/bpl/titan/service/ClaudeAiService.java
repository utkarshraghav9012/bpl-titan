package com.bpl.titan.service;

import com.bpl.titan.model.AiRequest;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class ClaudeAiService {

    private static final Logger log = LoggerFactory.getLogger(ClaudeAiService.class);

    @Value("${anthropic.api-key:}")
    private String apiKey;

    @Value("${anthropic.model:claude-sonnet-4-20250514}")
    private String model;

    @Value("${anthropic.max-tokens:200}")
    private int maxTokens;

    private final WebClient webClient;
    private final ObjectMapper objectMapper;

    public ClaudeAiService() {
        this.webClient = WebClient.builder()
                .baseUrl("https://api.anthropic.com")
                .build();
        this.objectMapper = new ObjectMapper();
    }

    /**
     * Mahaguru AI response generate karo using Claude
     */
    public Mono<String> getMahaguruResponse(AiRequest request) {

        // API key check
        if (apiKey == null || apiKey.isEmpty() || apiKey.equals("YOUR_ANTHROPIC_API_KEY_HERE")) {
            log.warn("Anthropic API key not configured!");
            return Mono.just("API key configure nahi hua bhai! application.properties mein anthropic.api-key set karo. 🔑");
        }

        String systemPrompt = buildSystemPrompt(request);

        try {
            // Build request body
            ObjectNode requestBody = objectMapper.createObjectNode();
            requestBody.put("model", model);
            requestBody.put("max_tokens", maxTokens);
            requestBody.put("system", systemPrompt);

            // Messages array
            ArrayNode messages = objectMapper.createArrayNode();

            // Add chat history context if available
            if (request.getChatHistory() != null && !request.getChatHistory().isEmpty()) {
                ObjectNode contextMsg = objectMapper.createObjectNode();
                contextMsg.put("role", "user");
                contextMsg.put("content", "Recent context: " + request.getChatHistory());
                messages.add(contextMsg);

                ObjectNode contextReply = objectMapper.createObjectNode();
                contextReply.put("role", "assistant");
                contextReply.put("content", "Got it, I'm up to speed.");
                messages.add(contextReply);
            }

            // Main user query
            ObjectNode userMsg = objectMapper.createObjectNode();
            userMsg.put("role", "user");
            userMsg.put("content", request.getQuery());
            messages.add(userMsg);

            requestBody.set("messages", messages);

            log.info("Calling Claude AI for query: {}", request.getQuery());

            return webClient.post()
                    .uri("/v1/messages")
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .header("x-api-key", apiKey)
                    .header("anthropic-version", "2023-06-01")
                    .bodyValue(requestBody.toString())
                    .retrieve()
                    .bodyToMono(String.class)
                    .map(this::extractReplyText)
                    .onErrorReturn("Network error — Claude se connect nahi hua. Try again! 🔄");

        } catch (Exception e) {
            log.error("Error building AI request: {}", e.getMessage());
            return Mono.just("Kuch gadbad ho gayi bhai! Try again karo. 🔄");
        }
    }

    /**
     * Parse Claude API response and extract text
     */
    private String extractReplyText(String responseJson) {
        try {
            JsonNode root = objectMapper.readTree(responseJson);

            // Check for error
            if (root.has("error")) {
                String errMsg = root.path("error").path("message").asText("Unknown error");
                log.error("Claude API error: {}", errMsg);
                return "AI Error: " + errMsg;
            }

            // Extract content text
            JsonNode content = root.path("content");
            if (content.isArray() && content.size() > 0) {
                return content.get(0).path("text").asText("No response");
            }

            return "Kuch response nahi mila. 🤔";

        } catch (Exception e) {
            log.error("Error parsing Claude response: {}", e.getMessage());
            return "Response parse karne mein dikkat. Try again! 🔄";
        }
    }

    /**
     * Mahaguru AI system prompt — same personality as original HTML
     */
    private String buildSystemPrompt(AiRequest request) {
        String auctionCtx = (request.getAuctionContext() != null && !request.getAuctionContext().isEmpty())
                ? request.getAuctionContext()
                : "Auction not started yet";

        String myIdentity = String.format("%s (%s)",
                request.getTeamName() != null ? request.getTeamName() : "Unknown",
                request.getRole() != null ? request.getRole() : "team");

        return String.format("""
                You are 'Mahaguru AI' — the witty, sharp auction strategist for a BPL (Bengali Premier League) Cricket Auction. Your personality:
                - Speak in Hinglish (mix of Hindi and English naturally, like Indians actually talk)
                - Be funny but smart — roast bad bids gently, celebrate smart ones
                - Give concrete auction strategy advice: when to bid, when to hold back, player value
                - Keep replies SHORT and punchy (2-4 sentences max)
                - Use cricket lingo and auction drama
                - Address users by their team name if known
                
                Current auction context: %s
                My team/role: %s
                """, auctionCtx, myIdentity);
    }
}
