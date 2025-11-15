#!/usr/bin/env pwsh
# Build verification script for Windows PowerShell
# This script tests if the project builds correctly

Write-Host "==================================" -ForegroundColor Cyan
Write-Host "IC Project Build Verification" -ForegroundColor Cyan
Write-Host "==================================" -ForegroundColor Cyan
Write-Host ""

# Set JAVA_HOME
$env:JAVA_HOME = "D:\DevTools\jdk\azul-21.0.8"
$env:Path = "$env:JAVA_HOME\bin;$env:Path"

Write-Host "Step 1: Checking Java version..." -ForegroundColor Yellow
java -version
Write-Host ""

Write-Host "Step 2: Cleaning previous build..." -ForegroundColor Yellow
.\gradlew.bat clean
Write-Host ""

Write-Host "Step 3: Downloading dependencies..." -ForegroundColor Yellow
.\gradlew.bat dependencies --configuration runtimeClasspath | Select-String "org.json"
Write-Host ""

Write-Host "Step 4: Compiling project..." -ForegroundColor Yellow
.\gradlew.bat compileJava
Write-Host ""

if ($LASTEXITCODE -eq 0) {
    Write-Host "==================================" -ForegroundColor Green
    Write-Host "✅ BUILD SUCCESSFUL!" -ForegroundColor Green
    Write-Host "==================================" -ForegroundColor Green
    Write-Host ""
    Write-Host "The project compiles correctly!" -ForegroundColor Green
    Write-Host "If IntelliJ shows errors, click the Gradle reload button." -ForegroundColor Yellow
    Write-Host ""
} else {
    Write-Host "==================================" -ForegroundColor Red
    Write-Host "❌ BUILD FAILED" -ForegroundColor Red
    Write-Host "==================================" -ForegroundColor Red
    Write-Host ""
    Write-Host "Check the error messages above." -ForegroundColor Red
    Write-Host ""
}

Write-Host "Press any key to continue..."
$null = $Host.UI.RawUI.ReadKey("NoEcho,IncludeKeyDown")

