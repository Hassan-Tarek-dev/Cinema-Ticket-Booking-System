@echo off
REM Maven Wrapper Batch Script for Windows
REM This script will download and run Maven

setlocal enabledelayedexpansion

set MAVEN_VERSION=3.9.6
set MAVEN_HOME=%USERPROFILE%\.m2\apache-maven-%MAVEN_VERSION%
set MAVEN_URL=https://archive.apache.org/dist/maven/maven-3/%MAVEN_VERSION%/binaries/apache-maven-%MAVEN_VERSION%-bin.zip

REM Check if Maven is already installed
if exist "%MAVEN_HOME%\bin\mvn.cmd" (
    echo Maven found at %MAVEN_HOME%
    "%MAVEN_HOME%\bin\mvn.cmd" %*
) else (
    echo Downloading Maven %MAVEN_VERSION%...
    if not exist "%USERPROFILE%\.m2" mkdir "%USERPROFILE%\.m2"
    
    powershell -Command "& { [Net.ServicePointManager]::SecurityProtocol = [Net.SecurityProtocolType]::Tls12; Invoke-WebRequest -Uri '%MAVEN_URL%' -OutFile '%USERPROFILE%\.m2\maven.zip' }"
    
    if exist "%USERPROFILE%\.m2\maven.zip" (
        echo Extracting Maven...
        powershell -Command "& { Expand-Archive -Path '%USERPROFILE%\.m2\maven.zip' -DestinationPath '%USERPROFILE%\.m2' }"
        del "%USERPROFILE%\.m2\maven.zip"
        
        echo Maven installed successfully
        "%MAVEN_HOME%\bin\mvn.cmd" %*
    ) else (
        echo Failed to download Maven
        exit /b 1
    )
)
