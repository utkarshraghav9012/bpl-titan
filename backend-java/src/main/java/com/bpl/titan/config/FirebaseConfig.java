package com.bpl.titan.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Configuration
public class FirebaseConfig {

    private static final Logger log = LoggerFactory.getLogger(FirebaseConfig.class);

    @Value("${firebase.database-url}")
    private String databaseUrl;

    /**
     * Firebase initialize karo.
     *
     * 2 options hain:
     * Option A (Recommended): serviceAccountKey.json use karo
     *   - Firebase Console > Project Settings > Service Accounts > Generate new private key
     *   - File ko resources/ folder mein rakh do
     *
     * Option B: Client-side only mode (Firebase JS SDK frontend pe hi kaam karega)
     *   - Backend sirf AI proxy aur static files serve karega
     *   - Is case mein Firebase init skip karo
     */
    @PostConstruct
    public void initFirebase() {
        try {
            if (FirebaseApp.getApps().isEmpty()) {
                // Try to load service account key
                InputStream serviceAccount = getServiceAccountStream();

                if (serviceAccount != null) {
                    FirebaseOptions options = FirebaseOptions.builder()
                            .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                            .setDatabaseUrl(databaseUrl)
                            .build();
                    FirebaseApp.initializeApp(options);
                    log.info("✅ Firebase Admin SDK initialized successfully!");
                } else {
                    // Frontend-only mode — Firebase JS SDK handles everything
                    log.warn("⚠️  Firebase service account key not found.");
                    log.warn("    Frontend Firebase JS SDK will handle DB operations.");
                    log.warn("    For server-side Firebase: add serviceAccountKey.json to resources/");
                }
            }
        } catch (IOException e) {
            log.error("Firebase init failed: {}", e.getMessage());
            log.warn("Running in frontend-Firebase mode — app will still work!");
        }
    }

    private InputStream getServiceAccountStream() {
        // Try classpath first (resources/serviceAccountKey.json)
        InputStream stream = getClass().getClassLoader()
                .getResourceAsStream("serviceAccountKey.json");
        if (stream != null) return stream;

        // Try file system
        try {
            return new FileInputStream("serviceAccountKey.json");
        } catch (IOException e) {
            return null;
        }
    }
}
