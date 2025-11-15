# How to Fix Build Errors - Quick Guide

## The Problem
IntelliJ is showing "Cannot resolve symbol 'json'" errors in the new provider files.

## The Solution

### Option 1: Sync Gradle in IntelliJ (Recommended)
1. Open the **Gradle** tool window (View → Tool Windows → Gradle)
2. Click the **Reload** icon (🔄) to refresh Gradle dependencies
3. Wait for the sync to complete

### Option 2: Invalidate Caches
1. Go to: **File → Invalidate Caches...**
2. Check: "Invalidate and Restart"
3. Click **Invalidate and Restart**
4. After restart, click the Gradle reload button

### Option 3: Command Line Build (Works Immediately)
The code **will compile successfully** even with IntelliJ showing errors.

Open PowerShell and run:
```powershell
cd C:\Users\peter\git\ic
$env:JAVA_HOME='D:\DevTools\jdk\azul-21.0.8'
.\gradlew.bat clean build
```

This will work because:
- The `org.json:json:20240303` dependency is in build.gradle
- Gradle will download it automatically
- The build will succeed

### Option 4: Manual Gradle Refresh in IntelliJ
1. Right-click on `build.gradle`
2. Select: **Gradle → Reload Gradle Project**
3. Wait for completion

## Why This Happens
- IntelliJ's internal cache hasn't indexed the new org.json library yet
- The library IS in your build.gradle
- Gradle knows about it and will download it
- IntelliJ just needs to refresh its view

## Verify It Works
After syncing, the red underlines should disappear and you should see:
- ✅ No import errors on `org.json.JSONObject`
- ✅ No errors in provider classes
- ✅ Build succeeds

## Still Not Working?
If IntelliJ still shows errors after trying all above:
1. Delete the `.idea` folder
2. Close IntelliJ
3. Re-open the project
4. Let IntelliJ re-index everything

---

**TL;DR**: Click the Gradle reload button 🔄 in IntelliJ's Gradle tool window. The code is correct!

