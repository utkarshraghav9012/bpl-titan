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
    // Frontend se @claude mention aane pe yahan call hoga
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
                        "reply", "Server error ho gaya! Thodi der baad try karo. 🔄",
                        "status", "error"
                )));
    }

    // =============================================
    // FIREBASE CONFIG (frontend ke liye)
    // API key ko .env se expose karo safely
    // =============================================
    @GetMapping("/config")
    public ResponseEntity<Map<String, String>> getPublicConfig() {
        // Sirf public/safe config return karo
        // KABHI BHAI anthropic.api-key yahan mat daalo!
        Map<String, String> config = new HashMap<>();
        config.put("firebaseApiKey", "AIzaSyC9IwhU6SKApi6ads_FC_3WpD4E0r3k-dQ");
        config.put("firebaseDatabaseUrl", "https://ipl-auction-8b916-default-rtdb.firebaseio.com/");
        config.put("firebaseProjectId", "ipl-auction-8b916");
        config.put("version", "V6 PRO");
        return ResponseEntity.ok(config);
    }
}
