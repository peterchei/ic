@echo off
REM Quick build script for IC Project

echo ==================================
echo IC Project Build Script
echo ==================================
echo.

REM Set JAVA_HOME
set JAVA_HOME=D:\DevTools\jdk\azul-21.0.8
set PATH=%JAVA_HOME%\bin;%PATH%

echo Step 1: Checking Java version...
java -version
echo.

echo Step 2: Building project...
call gradlew.bat clean build -x test
echo.

if %ERRORLEVEL% EQU 0 (
    echo ==================================
    echo BUILD SUCCESSFUL!
    echo ==================================
    echo.
    echo You can now run: gradlew.bat run
    echo.
) else (
    echo ==================================
    echo BUILD FAILED
    echo ==================================
    echo.
    echo Please check the errors above.
    echo.
)

pause

