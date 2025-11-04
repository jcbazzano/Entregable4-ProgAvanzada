#!/bin/bash

echo "========================================"
echo "🚀 DEPLOYMENT AUTOMÁTICO - MAC/LINUX"
echo "========================================"
echo "Fecha: $(date)"
echo "Commit: $GIT_COMMIT"
echo ""

echo "1. 🔍 Verificando entorno..."
java -version
mvn -version

echo "2. 🛑 Deteniendo Tomcat..."
pkill -f tomcat && echo "✅ Tomcat detenido" || echo "ℹ️  Tomcat no estaba en ejecución"

echo "3. ⏳ Esperando 5 segundos..."
sleep 5

echo "4. 📁 Copiando archivo WAR..."
if [ -f "target/mi-playlist.war" ]; then
    cp target/mi-playlist.war /usr/local/tomcat/webapps/
    echo "✅ WAR copiado correctamente"
else
    echo "❌ ERROR: No se encontró el archivo WAR"
    exit 1
fi

echo "5. 🚀 Iniciando Tomcat..."
/usr/local/tomcat/bin/startup.sh
if [ $? -eq 0 ]; then
    echo "✅ Tomcat iniciado correctamente"
else
    echo "❌ ERROR: No se pudo iniciar Tomcat"
    exit 1
fi

echo ""
echo "========================================"
echo "✅ DEPLOYMENT COMPLETADO EXITOSAMENTE"
echo "📍 URL: http://localhost:8080/mi-playlist/"
echo "========================================"