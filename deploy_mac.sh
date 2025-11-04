#!/bin/bash
echo "Iniciando deployment en Mac..."
# Detener Tomcat si está corriendo
pkill -f tomcat || true

# Esperar a que se detenga
sleep 5

# Copiar WAR a Tomcat
cp target/miplaylist.war /usr/local/tomcat/webapps/

# Iniciar Tomcat
/usr/local/tomcat/bin/startup.sh

echo "Deployment completado en Mac"