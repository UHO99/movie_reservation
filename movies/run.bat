@echo off
cd /d "%~dp0"
if not exist bin mkdir bin

echo Compiling...
dir /s /b src\*.java > "%TEMP%\movie_sources.txt"
javac -encoding UTF-8 -d bin -cp "res\mysql-connector-java-8.0.13.jar" @"%TEMP%\movie_sources.txt"
if errorlevel 1 (
    echo Compile failed.
    exit /b 1
)

echo Running...
java -cp "bin;res\mysql-connector-java-8.0.13.jar" com.ureca.user.Main
