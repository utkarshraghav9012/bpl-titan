package com.bpl.titan.controller;

import com.bpl.titan.model.AiRequest;
import com.bpl.titan.service.ClaudeAiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class AuctionController {

    private static final Logger log = LoggerFactory.getLogger(AuctionController.class);

    private final ClaudeAiService claudeAiService;

    public AuctionController(ClaudeAiService claudeAiService) {
        this.claudeAiService = claudeAiService;
    }

    // =============================================
    // HEALTH CHECK
    // =============================================
    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        Map<String, String> status = new HashMap<>();
        status.put("status", "UP");
        status.put("app", "BPL TITAN V6 PRO");
        status.put("version", "1.0.0");
        return ResponseEntity.ok(status);
    }

    // =============================================
    // CLAUDE AI ENDPOINT
    // Called when @claude mention is received from Frontend
    // =============================================
    @PostMapping("/ai/chat")
    public Mono<ResponseEntity<Map<String, String>>> aiChat(@RequestBody AiRequest request) {
        log.info("AI request from {} ({}): {}", request.getTeamName(), request.getRole(), request.getQuery());

        return claudeAiService.getMahaguruResponse(request)
                .map(reply -> {
                    Map<String, String> response = new HashMap<>();
                    response.put("reply", reply);
                    response.put("status", "success");
                    return ResponseEntity.ok(response);
                })
                .onErrorReturn(ResponseEntity.ok(Map.of(
                        "reply", "Server error occurred! Try again after some time. 🔄",
                        "status", "error"
                )));
    }

    // =============================================
    // FIREBASE CONFIG (for Frontend)
    // Expose Firebase config safely from .env
    // =============================================
    @GetMapping("/config")
    public ResponseEntity<Map<String, String>> getPublicConfig() {
        // Return only public/safe configuration
        // NEVER expose anthropic.api-key here!
        Map<String, String> config = new HashMap<>();
        config.put("firebaseApiKey", "AIzaSyC9IwhU6SKApi6ads_FC_3WpD4E0r3k-dQ");
        config.put("firebaseDatabaseUrl", "https://ipl-auction-8b916-default-rtdb.firebaseio.com/");
        config.put("firebaseProjectId", "ipl-auction-8b916");
        config.put("version", "V6 PRO");
        return ResponseEntity.ok(config);
    }
}
