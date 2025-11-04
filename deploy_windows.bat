@echo off
echo Iniciando deployment en Windows...
REM Detener Tomcat
net stop Tomcat9

REM Esperar
timeout /t 5

REM Copiar WAR a Tomcat
xcopy /Y target\miplaylist.war C:\Program Files\Apache Software Foundation\Tomcat 9.0\webapps\

REM Iniciar Tomcat
net start Tomcat9

echo Deployment completado en Windows