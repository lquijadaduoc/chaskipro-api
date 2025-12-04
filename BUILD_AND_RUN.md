# Guía de Compilación y Ejecución - Chaskipro

## 📋 Prerequisitos

Asegúrate de tener instalado:

- ✅ **Java 17** o superior
- ✅ **Maven 3.6+**
- ✅ **Docker** y **Docker Compose**
- ✅ **Git** (opcional, para control de versiones)

### Verificar instalaciones

```bash
# Verificar Java
java -version
# Debe mostrar: openjdk version "17.x.x" o superior

# Verificar Maven
mvn -version
# Debe mostrar: Apache Maven 3.6.x o superior

# Verificar Docker
docker --version
docker-compose --version
```

## 🚀 Opción 1: Inicio Rápido con Docker (Recomendado)

### Paso 1: Construir y levantar servicios

```bash
cd ~/Documents/chaskipro-backend
docker-compose up --build -d
```

Esto iniciará:
- PostgreSQL en puerto `5432`
- Aplicación Spring Boot en puerto `8080`

### Paso 2: Verificar que todo esté corriendo

```bash
# Ver estado de contenedores
docker-compose ps

# Ver logs de la aplicación
docker-compose logs -f app

# Ver logs de la base de datos
docker-compose logs -f postgres
```

### Paso 3: Probar la API

```bash
# Health check
curl http://localhost:8080/actuator/health

# Registrar un usuario de prueba
curl -X POST http://localhost:8080/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "email": "test@test.com",
    "password": "test123",
    "nombreCompleto": "Usuario Test",
    "rut": "12345678-9",
    "rol": "CLIENTE"
  }'
```

### Detener servicios

```bash
# Detener sin eliminar datos
docker-compose down

# Detener y eliminar volúmenes (resetear BD)
docker-compose down -v
```

## 💻 Opción 2: Desarrollo Local

Ideal para desarrollo con hot-reload.

### Paso 1: Iniciar solo PostgreSQL

```bash
docker-compose up -d postgres
```

### Paso 2: Compilar el proyecto

```bash
cd ~/Documents/chaskipro-backend

# Limpiar y compilar
mvn clean compile

# O compilar y generar JAR (saltando tests)
mvn clean package -DskipTests
```

### Paso 3: Ejecutar la aplicación

**Opción A: Con Maven (desarrollo)**
```bash
mvn spring-boot:run
```

**Opción B: Con JAR generado**
```bash
java -jar target/chaskipro-backend-1.0.0.jar
```

**Opción C: Con variables de entorno personalizadas**
```bash
SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/chaskipro_db \
SPRING_DATASOURCE_USERNAME=chaskipro \
SPRING_DATASOURCE_PASSWORD=chaskipro123 \
mvn spring-boot:run
```

### Paso 4: Verificar logs

La aplicación mostrará logs en la consola. Busca:

```
Started ChaskiproApplication in X.XXX seconds
```

## 🔧 Opción 3: Compilar solo con Docker

Si no tienes Maven instalado localmente.

### Construir imagen Docker

```bash
cd ~/Documents/chaskipro-backend
docker build -t chaskipro-backend:latest .
```

### Ejecutar contenedor

```bash
docker run -d \
  --name chaskipro-api-container \
  -p 8080:8080 \
  -e SPRING_DATASOURCE_URL=jdbc:postgresql://host.docker.internal:5432/chaskipro_db \
  -e SPRING_DATASOURCE_USERNAME=chaskipro \
  -e SPRING_DATASOURCE_PASSWORD=chaskipro123 \
  chaskipro-backend:latest
```

## 🧪 Ejecutar Tests

```bash
# Ejecutar todos los tests
mvn test

# Ejecutar tests con coverage
mvn clean test jacoco:report

# Ejecutar solo una clase de test
mvn test -Dtest=AuthServiceTest

# Saltar tests durante compilación
mvn clean package -DskipTests
```

## 📦 Generar JAR para Producción

```bash
# Generar JAR optimizado
mvn clean package -DskipTests -Dspring.profiles.active=prod

# El JAR se generará en:
# target/chaskipro-backend-1.0.0.jar
```

### Ejecutar JAR de producción

```bash
java -jar \
  -Dspring.profiles.active=prod \
  -Dserver.port=8080 \
  target/chaskipro-backend-1.0.0.jar
```

## 🐛 Troubleshooting

### Error: "Port 8080 is already in use"

```bash
# Encontrar proceso usando el puerto
lsof -i :8080

# Matar el proceso
kill -9 <PID>

# O cambiar el puerto
mvn spring-boot:run -Dserver.port=8081
```

### Error: "Connection refused" a PostgreSQL

```bash
# Verificar que PostgreSQL está corriendo
docker-compose ps postgres

# Ver logs de PostgreSQL
docker-compose logs postgres

# Reiniciar PostgreSQL
docker-compose restart postgres
```

### Error: "JVM out of memory"

```bash
# Aumentar memoria para Maven
export MAVEN_OPTS="-Xmx1024m"
mvn clean package

# Aumentar memoria para la aplicación
java -Xmx512m -jar target/chaskipro-backend-1.0.0.jar
```

### Limpiar cache de Maven

```bash
mvn clean
mvn dependency:purge-local-repository
```

### Resetear base de datos

```bash
# Detener y eliminar volúmenes
docker-compose down -v

# Volver a iniciar
docker-compose up -d
```

## 📊 Monitoreo y Logs

### Ver logs en tiempo real

```bash
# Docker Compose
docker-compose logs -f app

# Contenedor específico
docker logs -f chaskipro-backend-app

# Últimas 100 líneas
docker logs --tail 100 chaskipro-backend-app
```

### Acceder a la base de datos

```bash
# Conectar con psql
docker-compose exec postgres psql -U chaskipro -d chaskipro_db

# O usar un cliente SQL
# Host: localhost
# Port: 5432
# Database: chaskipro_db
# User: chaskipro
# Password: chaskipro123
```

### Comandos útiles SQL

```sql
-- Ver todas las tablas
\dt

-- Ver usuarios registrados
SELECT id, email, nombre_completo, rol, activo FROM users;

-- Ver profesionales
SELECT pp.id, u.nombre_completo, pp.biografia, pp.estado_validacion 
FROM professional_profiles pp 
JOIN users u ON pp.user_id = u.id;

-- Resetear base de datos (CUIDADO: elimina todos los datos)
DROP SCHEMA public CASCADE;
CREATE SCHEMA public;
```

## 🔄 Hot Reload durante Desarrollo

Maven Spring Boot DevTools está incluido para hot reload automático.

```bash
# Ejecutar con DevTools activo
mvn spring-boot:run

# Ahora cualquier cambio en los archivos .java será detectado automáticamente
```

## 📝 Variables de Entorno

### Crear archivo .env (opcional)

```bash
# En la raíz del proyecto
cat > .env << EOF
SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/chaskipro_db
SPRING_DATASOURCE_USERNAME=chaskipro
SPRING_DATASOURCE_PASSWORD=chaskipro123
JWT_SECRET=tu-secret-super-seguro-aqui
JWT_EXPIRATION=86400000
EOF
```

### Cargar variables de entorno

```bash
# En Linux/macOS
export $(cat .env | xargs)
mvn spring-boot:run
```

## 🎯 Perfiles de Spring

El proyecto soporta múltiples perfiles:

```bash
# Desarrollo (default)
mvn spring-boot:run

# Producción
mvn spring-boot:run -Dspring.profiles.active=prod

# Testing
mvn spring-boot:run -Dspring.profiles.active=test
```

## 📚 Recursos Adicionales

- [API_ENDPOINTS.md](./API_ENDPOINTS.md) - Documentación de endpoints
- [SECURITY_ARCHITECTURE.md](./SECURITY_ARCHITECTURE.md) - Arquitectura de seguridad
- [api-tests.http](./api-tests.http) - Colección de pruebas HTTP
- [start.sh](./start.sh) - Script de inicio rápido
