@echo off
set JAVA_HOME=D:\ProgramFiles\JDK6
echo %JAVA_HOME%
echo -DskipTests
D:\ProgramFiles\apache-maven-3.2.1\bin\mvn.bat clean package