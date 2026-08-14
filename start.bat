@echo off
echo Starting stock-simulation project...

echo Starting backend (Spring Boot)...
start "Backend" cmd /k "cd C:\Users\MSI-NB\Documents\workspace-spring-tools-for-eclipse-5.0.1.RELEASE\stock-simulation && mvnw.cmd spring-boot:run"

echo Starting frontend (Vite)...
start "Frontend" cmd /k "cd C:\Users\MSI-NB\Documents\workspace-spring-tools-for-eclipse-5.0.1.RELEASE\stock-simulation\frontend && npm run dev"

echo Both services started in new windows. Closing this window will not stop them.
