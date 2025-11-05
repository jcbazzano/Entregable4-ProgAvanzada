@echo off
echo ========================================
echo 🚀 DEPLOYMENT AUTOMÁTICO - WINDOWS
echo ========================================

echo 1. 🔍 Verificando entorno...
java -version

echo 2. 🛑 Deteniendo Tomcat...
net stop Tomcat9 2>nul && echo ✅ Tomcat detenido || echo ℹ️  Tomcat no estaba en ejecución

echo 3. ⏳ Esperando 3 segundos...
timeout /t 3 /nobreak >nul

echo 4. 📁 Copiando archivo WAR...
if exist "target\miplaylist.war" (
    xcopy /Y "target\miplaylist.war" "C:\Program Files\Apache Software Foundation\Tomcat 9.0\webapps\" >nul
    echo ✅ WAR copiado correctamente
) else (
    echo ❌ ERROR: No se encontró el archivo WAR en target\miplaylist.war
    dir target\  # Para ver qué archivos hay
    exit /b 1
)

echo 5. 🚀 Iniciando Tomcat...
net start Tomcat9
if %errorlevel% equ 0 (
    echo ✅ Tomcat iniciado correctamente
) else (
    echo ❌ ERROR: No se pudo iniciar Tomcat
    echo 💡 ¿Está instalado Tomcat?
    exit /b 1
)

echo.
echo ========================================
echo ✅ DEPLOYMENT COMPLETADO EXITOSAMENTE
echo 📍 URL: http://localhost:8080/miplaylist/
echo ========================================