"""
BPL TITAN — Python Flask AI Proxy Server
=========================================
This server acts as a proxy for Claude AI integration.
Can run independently from Java Spring Boot or together with it.

Usage:
  pip install flask flask-cors requests python-dotenv
  python app.py
"""

from flask import Flask, request, jsonify
from flask_cors import CORS
import requests
import os
import logging
from dotenv import load_dotenv

# Load environment variables from .env file
load_dotenv()

# =============================================
# APP SETUP
# =============================================
app = Flask(__name__, static_folder="static", static_url_path="")
CORS(app, origins="*")

logging.basicConfig(
    level=logging.INFO,
    format="%(asctime)s [%(levelname)s] %(message)s"
)
log = logging.getLogger(__name__)

# =============================================
# CONFIG
# =============================================
ANTHROPIC_API_KEY = os.getenv("ANTHROPIC_API_KEY", "YOUR_API_KEY_HERE")
ANTHROPIC_MODEL   = os.getenv("ANTHROPIC_MODEL", "claude-sonnet-4-20250514")
ANTHROPIC_MAX_TOKENS = int(os.getenv("ANTHROPIC_MAX_TOKENS", "200"))
ANTHROPIC_BASE_URL   = "https://api.anthropic.com/v1/messages"
PORT = int(os.getenv("PORT", 5000))


# =============================================
# HELPER: System Prompt Builder
# =============================================
def build_system_prompt(team_name: str, role: str, auction_context: str) -> str:
    """Define Mahaguru AI personality and context"""
    identity = f"{team_name or 'Unknown'} ({role or 'team'})"
    ctx = auction_context or "Auction not started yet"

    return f"""You are 'Mahaguru AI' — the witty, sharp auction strategist for a BPL (Bengali Premier League) Cricket Auction. Your personality:
- Speak in Hinglish (mix of Hindi and English naturally, like Indians actually talk)
- Be funny but smart — roast bad bids gently, celebrate smart ones
- Give concrete auction strategy advice: when to bid, when to hold back, player value
- Keep replies SHORT and punchy (2-4 sentences max)
- Use cricket lingo and auction drama
- Address users by their team name if known

Current auction context: {ctx}
My team/role: {identity}"""


# =============================================
# HELPER: Call Claude API
# =============================================
def call_claude(query: str, system_prompt: str, chat_history: str = "") -> str:
    """Call Claude API and return text response"""

    if not ANTHROPIC_API_KEY or ANTHROPIC_API_KEY == "YOUR_API_KEY_HERE":
        return "API key not configured! Set ANTHROPIC_API_KEY in .env file. 🔑"

    headers = {
        "Content-Type": "application/json",
        "x-api-key": ANTHROPIC_API_KEY,
        "anthropic-version": "2023-06-01"
    }

    # Build messages list
    messages = []

    # Add chat history context if available
    if chat_history:
        messages.append({
            "role": "user",
            "content": f"Recent context: {chat_history}"
        })
        messages.append({
            "role": "assistant",
            "content": "Got it, I'm up to speed."
        })

    # Add main query
    messages.append({
        "role": "user",
        "content": query
    })

    payload = {
        "model": ANTHROPIC_MODEL,
        "max_tokens": ANTHROPIC_MAX_TOKENS,
        "system": system_prompt,
        "messages": messages
    }

    try:
        response = requests.post(
            ANTHROPIC_BASE_URL,
            headers=headers,
            json=payload,
            timeout=30
        )
        data = response.json()

        # Check for errors
        if "error" in data:
            err_msg = data["error"].get("message", "Unknown error")
            log.error(f"Claude API error: {err_msg}")
            return f"AI Error: {err_msg}"

        # Extract text response
        content = data.get("content", [])
        if content and len(content) > 0:
            return content[0].get("text", "No response received!")

        return "No response from Claude. 🤔"

    except requests.exceptions.Timeout:
        log.error("Claude API request timed out")
        return "Request timeout! Try again after some time. ⏱️"
    except requests.exceptions.ConnectionError:
        log.error("Cannot connect to Claude API")
        return "Network error - Failed to connect to Claude. Check internet connection! 🔄"
    except Exception as e:
        log.error(f"Unexpected error calling Claude: {e}")
        return "Something went wrong! Try again. 🔄"


# =============================================
# ROUTES
# =============================================

@app.route("/")
def index():
    """Serve frontend (if available in static folder)"""
    return app.send_static_file("index.html")


@app.route("/api/health", methods=["GET"])
def health():
    """Health check endpoint"""
    return jsonify({
        "status": "UP",
        "app": "BPL TITAN V6 PRO",
        "backend": "Python Flask",
        "version": "1.0.0"
    })


@app.route("/api/ai/chat", methods=["POST"])
def ai_chat():
    """
    Main AI endpoint - Compatible with Java Spring Boot interface
    Frontend calls this endpoint when @claude tag is mentioned
    """
    try:
        data = request.get_json()
        if not data:
            return jsonify({"reply": "Invalid request data!", "status": "error"}), 400

        query         = data.get("query", "").strip()
        team_name     = data.get("teamName", "Unknown")
        role          = data.get("role", "team")
        chat_history  = data.get("chatHistory", "")
        auction_ctx   = data.get("auctionContext", "")

        if not query:
            return jsonify({"reply": "Query is empty!", "status": "error"}), 400

        log.info(f"AI request from {team_name} ({role}): {query[:50]}...")

        system_prompt = build_system_prompt(team_name, role, auction_ctx)
        reply = call_claude(query, system_prompt, chat_history)

        return jsonify({
            "reply": reply,
            "status": "success"
        })

    except Exception as e:
        log.error(f"Error in /api/ai/chat: {e}")
        return jsonify({
            "reply": "Server error occurred! Try again after some time. 🔄",
            "status": "error"
        }), 500


@app.route("/api/config", methods=["GET"])
def get_config():
    """Return public Firebase config (Anthropic key is never exposed)"""
    return jsonify({
        "firebaseApiKey": "AIzaSyC9IwhU6SKApi6ads_FC_3WpD4E0r3k-dQ",
        "firebaseDatabaseUrl": "https://ipl-auction-8b916-default-rtdb.firebaseio.com/",
        "firebaseProjectId": "ipl-auction-8b916",
        "version": "V6 PRO",
        "backend": "Python Flask"
    })


# =============================================
# ERROR HANDLERS
# =============================================

@app.errorhandler(404)
def not_found(e):
    return jsonify({"error": "Route not found", "status": 404}), 404

@app.errorhandler(500)
def server_error(e):
    return jsonify({"error": "Internal server error", "status": 500}), 500


# =============================================
# MAIN
# =============================================
if __name__ == "__main__":
    print("╔══════════════════════════════════════╗")
    print("║  BPL TITAN V6 PRO — Python Server ✅  ║")
    print(f"║  http://localhost:{PORT}               ║")
    print("╚══════════════════════════════════════╝")

    if ANTHROPIC_API_KEY == "YOUR_API_KEY_HERE":
        print("\n⚠️  WARNING: ANTHROPIC_API_KEY not set!")
        print("   Add ANTHROPIC_API_KEY=your_key_here to .env file\n")

    app.run(
        host="0.0.0.0",
        port=PORT,
        debug=os.getenv("FLASK_DEBUG", "false").lower() == "true"
    )
