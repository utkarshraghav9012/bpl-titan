# 🧹 Hinglish Comment Remover - Usage Guide

## Quick Start

### Step 1: Run the Script
```bash
python hinglish-cleaner.py
```

### Step 2: Review Changes
```bash
git diff
```

### Step 3: Commit & Push
```bash
git add .
git commit -m "refactor: convert hinglish comments to english"
git push
```

---

## What the Script Does

✅ **Scans** all `.java`, `.py`, `.js`, `.html`, `.css`, `.ts` files  
✅ **Replaces** 50+ Hinglish patterns with professional English  
✅ **Skips** unnecessary directories (`.git`, `node_modules`, `venv`)  
✅ **Reports** which files were cleaned  

---

## Examples of Conversions

### Java Files
```java
// Before:
// REQUEST: Frontend se aane wala data
// Firebase initialize karo
// Kuch gadbad ho gayi bhai!

// After:
// REQUEST: Data incoming from Frontend
// Initialize Firebase
// Something went wrong!
```

### Python Files
```python
# Before:
# Try karo connection karo
# Nahi hua to error daalo

# After:
# Try connection
# If failed then throw error
```

### JavaScript
```javascript
// Before:
// API key configure nahi hua bhai
// Thodi der baad try karo

// After:
// API key not configured
// Try again after some time
```

---

## Supported Hinglish Conversions (50+ patterns)

| Hinglish | English |
|----------|---------|
| karo | do |
| nahi hua | not done |
| bhai | (removed) |
| ho gya | happened |
| gadbad | issue |
| se aane wala | incoming |
| sirf | only |
| kabhi | never |
| mat | don't |
| aur | and |
| ya | or |
| par/lekin | but |
| ab | now |
| initialize karo | Initialize |
| thodi der baad | after some time |
| And 35+ more... | ✅ |

---

## Full Workflow

### 1. **Before Running**
```bash
# Check your current branch
git branch

# Make sure you're on main or a feature branch
git checkout main
```

### 2. **Run the Cleaner**
```bash
python hinglish-cleaner.py
```

**Output:**
```
============================================================
🧹 BPL TITAN - Hinglish Comment Remover
============================================================

📁 Scanning directory: /path/to/bpl-titan
Looking for: .java, .py, .js, .html, .css, .ts files

✅ Cleaned: backend-java/src/main/java/com/bpl/titan/model/AiRequest.java
✅ Cleaned: backend-java/src/main/java/com/bpl/titan/service/ClaudeAiService.java
✅ Cleaned: backend-java/src/main/java/com/bpl/titan/controller/AuctionController.java
✅ Cleaned: ai-python/app.py
✅ Cleaned: frontend/index.js

============================================================
✨ Cleaning complete!
📊 Files cleaned: 5/47
============================================================
```

### 3. **Review Changes**
```bash
git diff
# or
git diff backend-java/src/main/java/com/bpl/titan/model/AiRequest.java
```

### 4. **Verify Files**
```bash
# See all modified files
git status
```

### 5. **Stage All Changes**
```bash
git add .
```

### 6. **Commit Changes**
```bash
git commit -m "refactor: convert hinglish comments to english for code quality"
```

### 7. **Push to GitHub**
```bash
git push origin main
```

---

## Customization

### Add More Hinglish Patterns

Edit `hinglish-cleaner.py` and add to the `HINGLISH_REPLACEMENTS` dictionary:

```python
HINGLISH_REPLACEMENTS = {
    # Your new patterns
    r"(?i)your_hinglish": "your_english",
    r"(?i)mera": "my",
    r"(?i)tera": "your",
}
```

### Exclude Specific Directories

Edit this line in the script:
```python
dirs[:] = [d for d in dirs if d not in {'.git', 'node_modules', 'venv', '__pycache__', '.idea', 'YOUR_DIR'}]
```

---

## Troubleshooting

### ❌ "Python not found"
```bash
# Install Python 3
# macOS:
brew install python3

# Windows: Download from python.org

# Ubuntu:
sudo apt-get install python3
```

### ❌ "Permission denied"
```bash
chmod +x hinglish-cleaner.py
python hinglish-cleaner.py
```

### ❌ "Git not found"
```bash
# Install Git
# https://git-scm.com/downloads
```

### ❌ "No files cleaned"
- Script ran successfully but found no Hinglish comments
- You can verify manually with: `git grep "bhai"`

---

## Advanced Usage

### Run Script Silently (Save Output)
```bash
python hinglish-cleaner.py > cleanup.log 2>&1
```

### Run Script in Dry-Run Mode (Check only)
Modify script to add a dry-run flag:
```python
DRY_RUN = True  # Don't actually write changes
```

### Run on Specific Directory
```bash
cd backend-java
python ../hinglish-cleaner.py
```

---

## After Cleanup Checklist

- [ ] Run the script: `python hinglish-cleaner.py`
- [ ] Review changes: `git diff`
- [ ] Test the application: `mvn spring-boot:run`
- [ ] Stage changes: `git add .`
- [ ] Commit: `git commit -m "refactor: convert hinglish comments"`
- [ ] Push: `git push`
- [ ] Create PR (if needed)
- [ ] Merge to main

---

## Benefits of Cleanup

✅ **Professional Code** - Better for code reviews and interviews  
✅ **Team Collaboration** - Non-Hindi speakers can understand comments  
✅ **Global Repository** - Better for open-source projects  
✅ **Better SEO** - GitHub search works with English comments  
✅ **Interview Ready** - Cleaner codebase for portfolio  

---

## One-Liner Commands

```bash
# Do everything at once
python hinglish-cleaner.py && git add . && git commit -m "refactor: clean hinglish comments" && git push

# Or step by step:
python hinglish-cleaner.py
git diff | head -50
git add .
git commit -m "refactor: convert hinglish comments to english"
git push
```

---

## Questions?

The script will:
- ✅ Handle encoding issues (UTF-8)
- ✅ Skip already-clean files
- ✅ Work with all file types
- ✅ Be case-insensitive (handles "KARO", "Karo", "karo")
- ✅ Preserve code formatting and indentation

---

**Happy cleaning! 🎉**
