# 🏏 BPL TITAN V6 PRO — Cricket Auction System

> **Same features as the original HTML version — now powered by Java Spring Boot + Python Flask backend!**

[![Java](https://img.shields.io/badge/Java-17+-orange?logo=java)](https://java.com)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2-green?logo=springboot)](https://spring.io/projects/spring-boot)
[![Python](https://img.shields.io/badge/Python-3.9+-blue?logo=python)](https://python.org)
[![Flask](https://img.shields.io/badge/Flask-3.0-black?logo=flask)](https://flask.palletsprojects.com)
[![Firebase](https://img.shields.io/badge/Firebase-Realtime%20DB-orange?logo=firebase)](https://firebase.google.com)

---

## 🚀 Features (Original HTML se same, sab kuch!)

| Feature | Status |
|--------|--------|
| 🔐 League Login / Create | ✅ |
| 🏏 Live Auction Stage + Timer | ✅ |
| 💰 Real-time Bidding | ✅ |
| 👥 Team Franchises + Purse | ✅ |
| 📋 Squad Modal | ✅ |
| 🔄 Player Trade System | ✅ |
| 🤖 Mahaguru AI (Claude) | ✅ Java Backend |
| 🎙️ WebRTC Voice Room | ✅ |
| 📱 Mobile Responsive | ✅ |
| 🔥 Firebase Realtime Sync | ✅ |

---

## 📁 Project Structure

```
bpl-titan/
├── backend-java/                  # ☕ Spring Boot Server
│   ├── pom.xml
│   └── src/main/
│       ├── java/com/bpl/titan/
│       │   ├── BplTitanApplication.java    # Entry point
│       │   ├── config/
│       │   │   ├── FirebaseConfig.java
│       │   │   └── WebConfig.java
│       │   ├── controller/
│       │   │   └── AuctionController.java  # REST APIs
│       │   ├── model/
│       │   │   └── AiRequest.java
│       │   └── service/
│       │       └── ClaudeAiService.java    # Claude AI calls
│       └── resources/
│           ├── application.properties
│           └── static/
│               └── index.html             # Frontend (same UI)
│
├── ai-python/                     # 🐍 Python Flask AI Server
│   ├── app.py                     # Flask server
│   └── requirements.txt
│
├── .env.example                   # Environment variables template
├── .gitignore
└── README.md
```

---

## ⚙️ Setup & Run

### Option A: Java Spring Boot (Recommended)

**Prerequisites:**
- Java 17+
- Maven 3.8+

```bash
# 1. Project mein jao
cd backend-java

# 2. API key set karo
# application.properties mein:
# anthropic.api-key=YOUR_KEY_HERE

# 3. Run karo
mvn spring-boot:run

# 4. Browser mein open karo
# http://localhost:8080
```

---

### Option B: Python Flask (AI-only proxy)

```bash
# 1. Python folder mein jao
cd ai-python

# 2. Virtual environment banao
python -m venv venv
source venv/bin/activate        # Linux/Mac
# ya: venv\Scripts\activate     # Windows

# 3. Dependencies install karo
pip install -r requirements.txt

# 4. .env file banao
cp ../.env.example .env
# .env mein ANTHROPIC_API_KEY=your_key daalo

# 5. Run karo
python app.py

# Server: http://localhost:5000
```

---

### Option C: Pure Frontend (Original mode — no backend needed)

`backend-java/src/main/resources/static/index.html` ko directly browser mein open karo — Firebase aur Claude API frontend se direct call hoga.

---

## 🔑 API Keys Setup

### Anthropic Claude AI Key
1. [console.anthropic.com](https://console.anthropic.com) pe jao
2. API Keys section mein naya key banao
3. Java: `application.properties` mein daalo: `anthropic.api-key=sk-ant-...`
4. Python: `.env` mein daalo: `ANTHROPIC_API_KEY=sk-ant-...`

### Firebase Setup
Firebase config already set hai (same as original HTML). Apna project use karna ho to:
1. [firebase.google.com](https://firebase.google.com) → Console
2. Naya project banao → Realtime Database enable karo
3. Config values update karo in `application.properties` aur `index.html`

---

## 🌐 API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/health` | Server status check |
| POST | `/api/ai/chat` | Mahaguru AI response |
| GET | `/api/config` | Public Firebase config |

### POST `/api/ai/chat` — Request Body:
```json
{
  "query": "Rohit Sharma ko kitne mein khareedun?",
  "teamName": "Mumbai Mavericks",
  "role": "team",
  "chatHistory": "...",
  "auctionContext": "Teams: MI: ₹45Cr, CSK: ₹32Cr"
}
```

---

## 🐙 GitHub Pe Upload Kaise Karein

### Step 1: Git Initialize
```bash
cd bpl-titan
git init
git add .
git commit -m "🏏 Initial commit: BPL TITAN V6 PRO"
```

### Step 2: GitHub pe repository banao
1. [github.com/new](https://github.com/new) pe jao
2. Repository name: `bpl-titan` (ya jo chahiye)
3. Public/Private choose karo
4. "Create repository" click karo

### Step 3: Push karo
```bash
# GitHub ne jo commands diye hain unhe copy karo, ya:
git remote add origin https://github.com/YOUR_USERNAME/bpl-titan.git
git branch -M main
git push -u origin main
```

### Step 4: Secrets protect karo ✅
`.gitignore` already configured hai — `.env` aur `serviceAccountKey.json` push nahi hoga!

---

## 🚀 Deploy Options

### Render.com (Free)
```bash
# Java
# Build Command: mvn clean package -DskipTests
# Start Command: java -jar target/bpl-titan-1.0.0.jar

# Python
# Build Command: pip install -r requirements.txt
# Start Command: gunicorn app:app
```

### Railway.app
```bash
# Java: Automatically detects pom.xml
# Python: Procfile banao: web: gunicorn app:app
```

---

## 🛠️ Tech Stack

- **Frontend**: HTML5, CSS3, Vanilla JS (same as original)
- **Backend (Java)**: Spring Boot 3.2, WebFlux (reactive HTTP), Maven
- **Backend (Python)**: Flask 3.0, Requests
- **Database**: Firebase Realtime Database
- **AI**: Anthropic Claude (claude-sonnet-4-20250514)
- **Voice**: WebRTC P2P + Firebase signaling
- **Auth**: Firebase client-side

---

## 📝 License

MIT License — Free to use, modify, and distribute!

---

*Made with ☕ Java, 🐍 Python & 🏏 Cricket spirit!*
