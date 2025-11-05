@echo off
echo ========================================
echo 🚀 DEPLOYMENT - TOMCAT PUERTO 8081
echo ========================================

echo 1. 🛑 Deteniendo Tomcat...
net stop Tomcat9
echo ✅ Tomcat detenido

echo 2. 📁 Limpiando despliegue anterior...
del "C:\tomcat9\webapps\miplaylist.war" 2>nul
rmdir /s /q "C:\tomcat9\webapps\miplaylist" 2>nul
echo ✅ Limpieza completada

echo 3. 📦 Copiando nuevo WAR...
if exist "target\miplaylist.war" (
    xcopy /Y "target\miplaylist.war" "C:\tomcat9\webapps\"
    echo ✅ WAR copiado correctamente
) else (
    echo ❌ ERROR: No se encontró target\miplaylist.war
    exit /b 1
)

echo 4. 🚀 Iniciando Tomcat...
net start Tomcat9
echo ✅ Tomcat iniciado

echo 5. ⏳ Esperando 10 segundos para despliegue...
timeout /t 10 /nobreak >nul

echo.
echo ========================================
echo ✅ DEPLOYMENT COMPLETADO EXITOSAMENTE
echo 📍 URL: http://localhost:8081/miplaylist/
echo ========================================