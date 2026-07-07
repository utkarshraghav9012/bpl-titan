# 🏏 BPL TITAN V6 PRO

A real-time multi-user cricket auction platform built using modern web technologies. The application enables multiple franchises to participate in live player auctions with synchronized bidding, automated purse management, role-based access control, AI-powered assistance, and integrated voice communication.

---

# Overview

BPL TITAN V6 PRO is a web-based cricket auction management system designed to simulate a professional sports auction environment. The platform allows multiple users to participate simultaneously while maintaining real-time synchronization across all connected devices using Firebase Realtime Database.

The system provides complete auction management including live bidding, automatic purse updates, player trading, administrative controls, AI-powered auction assistance, and WebRTC-based voice communication.

---

# Key Features

- Real-time multi-user auction platform
- Live bid synchronization across all connected devices
- Role-Based Access Control (Admin & Franchise)
- Automatic purse calculation and budget management
- Live player trading between franchises
- Real-time auction timer synchronization
- Player Sold/Unsold management
- AI-powered auction assistant
- Integrated WebRTC voice communication
- Mobile responsive interface
- Progressive Web Application (PWA) support
- Firebase-powered real-time database synchronization

---

# Technology Stack

## Frontend

- HTML5
- CSS3
- JavaScript (ES6)

## Backend

- Java 17
- Spring Boot 3
- Python Flask

## Database

- Firebase Realtime Database

## AI Integration

- Google Gemini API *(or replace with Anthropic Claude API if that's what your project actually uses)*

## Voice Communication

- WebRTC
- Firebase Signaling

---

# Core Technical Concepts

- Real-Time State Management
- Event-Driven Architecture
- Role-Based Access Control (RBAC)
- REST API Integration
- Firebase Realtime Database
- Real-Time Data Synchronization
- Business Logic Automation
- Automated Budget Management
- Concurrent User Session Management
- WebRTC Audio Streaming
- NoSQL Database Design
- Progressive Web Application (PWA)

---

# Project Structure

```text
bpl-titan/
│
├── backend-java/
│   ├── controller/
│   ├── service/
│   ├── config/
│   ├── model/
│   └── resources/
│
├── ai-python/
│   ├── app.py
│   └── requirements.txt
│
├── README.md
├── .gitignore
└── .env.example
```

---

# Installation

## Clone Repository

```bash
git clone https://github.com/utkarshraghav9012/bpl.git
cd bpl
```

---

## Run Java Backend

```bash
cd backend-java
mvn spring-boot:run
```

Application:

```
http://localhost:8080
```

---

## Run Python AI Backend

```bash
cd ai-python

python -m venv venv

pip install -r requirements.txt

python app.py
```

Application:

```
http://localhost:5000
```

---

# API Endpoints

| Method | Endpoint | Description |
|---------|----------|-------------|
| GET | /api/health | Health Check |
| POST | /api/ai/chat | AI Assistant |
| GET | /api/config | Firebase Configuration |

---

# Future Enhancements

- Secure User Authentication
- Auction Analytics Dashboard
- Match Scheduling Module
- Advanced Player Statistics
- Push Notifications
- Multi-League Support
- Performance Optimization

---

# License

MIT License

---

## Developer

**Utkarsh Raghav**

If you found this project useful, consider giving the repository a ⭐ on GitHub.
