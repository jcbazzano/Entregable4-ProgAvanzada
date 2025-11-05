@echo off
echo ========================================
echo 🚀 DEPLOYMENT - RUTA CORRECTA
echo ========================================

echo 1. 🛑 Deteniendo Tomcat...
net stop Tomcat9
echo ✅ Tomcat detenido

echo 2. 📁 Limpiando despliegue anterior...
del "C:\Program Files\Apache Software Foundation\Tomcat 9.0\webapps\miplaylist.war" 2>nul
rmdir /s /q "C:\Program Files\Apache Software Foundation\Tomcat 9.0\webapps\miplaylist" 2>nul
echo ✅ Limpieza completada

echo 3. 📦 Copiando nuevo WAR...
if exist "target\miplaylist.war" (
    xcopy /Y "target\miplaylist.war" "C:\Program Files\Apache Software Foundation\Tomcat 9.0\webapps\"
    echo ✅ WAR copiado correctamente
) else (
    echo ❌ ERROR: No se encontró target\miplaylist.war
    exit /b 1
)

echo 4. 🚀 Iniciando Tomcat...
net start Tomcat9
echo ✅ Tomcat iniciado

echo 5. ⏳ Esperando 10 segundos para despliegue...
ping -n 10 127.0.0.1 >nul

echo.
echo ========================================
echo ✅ DEPLOYMENT COMPLETADO EXITOSAMENTE
echo 📍 URL: http://localhost:8081/miplaylist/
echo ========================================