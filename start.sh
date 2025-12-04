#!/bin/bash

# Script de inicio rápido para Chaskipro

echo "🚀 Iniciando Chaskipro..."
echo ""

# Verificar si Docker está ejecutándose
if ! docker info > /dev/null 2>&1; then
    echo "❌ Docker no está ejecutándose. Por favor inicia Docker Desktop."
    exit 1
fi

echo "✅ Docker está ejecutándose"
echo ""

# Opción 1: Iniciar todo con Docker Compose
echo "Opciones de inicio:"
echo "1) Iniciar con Docker Compose (PostgreSQL + App)"
echo "2) Solo PostgreSQL (para desarrollo local)"
echo "3) Detener todos los servicios"
echo "4) Ver logs de la aplicación"
echo ""

read -p "Selecciona una opción [1-4]: " option

case $option in
    1)
        echo ""
        echo "📦 Construyendo y levantando servicios..."
        docker-compose up --build -d
        echo ""
        echo "✅ Servicios iniciados!"
        echo "📍 API disponible en: http://localhost:8080"
        echo "🗄️  PostgreSQL en: localhost:5432"
        echo ""
        echo "Ver logs: docker-compose logs -f app"
        ;;
    2)
        echo ""
        echo "🗄️  Iniciando solo PostgreSQL..."
        docker-compose up -d postgres
        echo ""
        echo "✅ PostgreSQL iniciado!"
        echo "📍 PostgreSQL en: localhost:5432"
        echo "   Database: chaskipro_db"
        echo "   User: chaskipro"
        echo "   Password: chaskipro123"
        echo ""
        echo "Para iniciar la app localmente:"
        echo "   mvn spring-boot:run"
        ;;
    3)
        echo ""
        echo "🛑 Deteniendo servicios..."
        docker-compose down
        echo "✅ Servicios detenidos"
        ;;
    4)
        echo ""
        echo "📋 Mostrando logs (Ctrl+C para salir)..."
        docker-compose logs -f app
        ;;
    *)
        echo "❌ Opción inválida"
        exit 1
        ;;
esac
