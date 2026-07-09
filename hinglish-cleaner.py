#!/usr/bin/env python3
"""
BPL TITAN - Hinglish Comment Remover
Automatically converts Hinglish comments to English
Usage: python hinglish-cleaner.py
"""

import os
import re
from pathlib import Path

# Hinglish to English mapping
HINGLISH_REPLACEMENTS = {
    # Common Hinglish patterns
    r"(?i)initialize karo": "Initialize",
    r"(?i)karo\b": "do",
    r"(?i)ho gya": "happened",
    r"(?i)ho gayi": "happened",
    r"(?i)nahi hua": "not done",
    r"(?i)bhai\b": "",  # Remove "bhai"
    r"(?i)kabhi": "never",
    r"(?i)mat\b": "don't",
    r"(?i)se aane wala": "incoming",
    r"(?i)aane pe": "when",
    r"(?i)sirf": "only",
    r"(?i)thodi der baad": "after some time",
    r"(?i)kuch\b": "something",
    r"(?i)gadbad": "issue",
    r"(?i)hua": "was",
    r"(?i)hain\b": "are",
    r"(?i)mein\b": "in",
    r"(?i)da baad": "after",
    r"(?i)kuch nahi": "nothing",
    r"(?i)aur\b": "and",
    r"(?i)ya\b": "or",
    r"(?i)par\b": "but",
    r"(?i)lekin\b": "but",
    r"(?i)ab\b": "now",
    r"(?i)woh": "that",
    r"(?i)ye\b": "this",
    r"(?i)rakh do": "put",
    r"(?i)dekho": "see",
    r"(?i)samajh": "understand",
    
    # Specific patterns from your code
    r"REQUEST:\s*Frontend se aane wala data": "REQUEST: Data incoming from Frontend",
    r"HEALTH CHECK": "Health Check Endpoint",
    r"CLAUDE AI ENDPOINT": "Claude AI Chat Endpoint",
    r"FIREBASE CONFIG \(frontend ke liye\)": "Firebase Configuration (for frontend)",
    r"Firebase initialize karo": "Initialize Firebase",
    r"Firebase se connect nahi hua": "Failed to connect to Firebase",
    r"Network error.*Claude se connect nahi hua": "Network error - Failed to connect to Claude",
    r"Kuch gadbad ho gayi bhai": "Something went wrong",
    r"Try again karo": "Try again",
    r"Try again!": "Try again",
    r"No response": "No response from AI",
    r"API key configure nahi hua": "API key not configured",
    r"anthropic\.api-key set karo": "Set anthropic.api-key in configuration",
    r"Mahaguru AI response generate karo": "Generate Mahaguru AI response",
    r"API key check": "Check API key",
    r"Add chat history context": "Add chat history context if available",
    r"Recent context:": "Recent context:",
    r"Got it, I'm up to speed": "Got it, I'm up to speed",
    r"Calling Claude AI": "Calling Claude AI",
    r"Parse Claude API response": "Parse Claude API response and extract text",
    r"Check for error": "Check for error response",
    r"Extract content text": "Extract content text from response",
    r"Kuch response nahi mila": "No response received",
    r"AI Error:": "AI Error:",
    r"BPL TITAN V6 PRO — LIVE": "BPL TITAN V6 PRO - LIVE",
    r"KABHI BHAI.*mat daalo": "NEVER put API key here!",
    r"Sirf public/safe config return karo": "Return only public/safe configuration",
    r"Option A \(Recommended\):.*use karo": "Option A (Recommended): Use serviceAccountKey.json",
    r"Option B:.*is case mein": "Option B: Client-side only mode",
    r"Firebase init skip karo": "Skip Firebase initialization",
}

def clean_hinglish_comments(file_path):
    """
    Remove/replace Hinglish comments in a file
    """
    try:
        with open(file_path, 'r', encoding='utf-8') as f:
            content = f.read()
        
        original_content = content
        
        # Apply all replacements
        for hinglish_pattern, english_replacement in HINGLISH_REPLACEMENTS.items():
            content = re.sub(hinglish_pattern, english_replacement, content)
        
        # Only write if content changed
        if content != original_content:
            with open(file_path, 'w', encoding='utf-8') as f:
                f.write(content)
            return True
        return False
    
    except Exception as e:
        print(f"⚠️  Error processing {file_path}: {e}")
        return False

def scan_and_clean(root_dir):
    """
    Recursively scan and clean all code files
    """
    file_extensions = {'.java', '.py', '.js', '.html', '.css', '.ts', '.tsx', '.jsx'}
    cleaned_count = 0
    total_count = 0
    
    for root, dirs, files in os.walk(root_dir):
        # Skip certain directories
        dirs[:] = [d for d in dirs if d not in {'.git', 'node_modules', 'venv', '__pycache__', '.idea'}]
        
        for file in files:
            if any(file.endswith(ext) for ext in file_extensions):
                file_path = os.path.join(root, file)
                total_count += 1
                
                if clean_hinglish_comments(file_path):
                    cleaned_count += 1
                    print(f"✅ Cleaned: {file_path}")
    
    return cleaned_count, total_count

def main():
    print("=" * 60)
    print("🧹 BPL TITAN - Hinglish Comment Remover")
    print("=" * 60)
    
    root_directory = os.getcwd()
    
    print(f"\n📁 Scanning directory: {root_directory}")
    print("Looking for: .java, .py, .js, .html, .css, .ts files\n")
    
    cleaned, total = scan_and_clean(root_directory)
    
    print("\n" + "=" * 60)
    print(f"✨ Cleaning complete!")
    print(f"📊 Files cleaned: {cleaned}/{total}")
    print("=" * 60)
    
    if cleaned > 0:
        print("\n✅ Next steps:")
        print("1. Review the changes: git diff")
        print("2. Stage changes: git add .")
        print("3. Commit: git commit -m 'refactor: convert hinglish comments to english'")
        print("4. Push: git push")

if __name__ == "__main__":
    main()
