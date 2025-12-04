# Chaskipro - Backend API

Marketplace de Servicios en Chile que conecta clientes con profesionales verificados en áreas como Gasfitería, Electricidad, y más.

## 🚀 Tecnologías

- **Java 17**
- **Spring Boot 3.2.0**
- **Spring Data JPA**
- **Spring Security + JWT**
- **PostgreSQL**
- **Maven**
- **Lombok**
- **Docker & Docker Compose**

## 📋 Requisitos Previos

- Java 17 o superior
- Maven 3.6+
- Docker y Docker Compose
- PostgreSQL 16 (si no usas Docker)

## 🏗️ Estructura del Proyecto

```
chaskipro-backend/
├── src/
│   ├── main/
│   │   ├── java/com/chaskipro/backend/
│   │   │   ├── config/          # Configuraciones de Spring
│   │   │   ├── controller/      # Controladores REST
│   │   │   ├── dto/             # Data Transfer Objects
│   │   │   ├── entity/          # Entidades JPA
│   │   │   ├── exception/       # Manejo de excepciones
│   │   │   ├── repository/      # Repositorios JPA
│   │   │   ├── security/        # Configuración de seguridad
│   │   │   ├── service/         # Lógica de negocio
│   │   │   └── ChaskiproApplication.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/
├── Dockerfile
├── docker-compose.yml
└── pom.xml
```

## 🗄️ Modelo de Datos

### Entidades Principales

1. **User**: Usuarios del sistema (CLIENTE, PROFESIONAL, ADMIN)
2. **ProfessionalProfile**: Perfil extendido para profesionales
3. **Comuna**: Comunas de Chile con región
4. **ServiceRequest**: Solicitudes de servicio
5. **Review**: Calificaciones y comentarios

### Relaciones

- `User` ↔ `ProfessionalProfile` (OneToOne)
- `ProfessionalProfile` ↔ `Comuna` (ManyToMany) - Cobertura de trabajo
- `User` → `ServiceRequest` (OneToMany) - Como cliente
- `ProfessionalProfile` → `ServiceRequest` (OneToMany) - Como profesional
- `ServiceRequest` ↔ `Review` (OneToOne)

## 🐳 Iniciar con Docker

### 1. Levantar la base de datos y la aplicación

```bash
docker-compose up -d
```

Esto iniciará:
- PostgreSQL en `localhost:5432`
- Aplicación Spring Boot en `localhost:8080`

### 2. Ver logs

```bash
docker-compose logs -f app
```

### 3. Detener servicios

```bash
docker-compose down
```

### 4. Detener y eliminar volúmenes (resetear BD)

```bash
docker-compose down -v
```

## 💻 Desarrollo Local (sin Docker)

### 1. Configurar PostgreSQL

Crear base de datos:
```sql
CREATE DATABASE chaskipro_db;
CREATE USER chaskipro WITH PASSWORD 'chaskipro123';
GRANT ALL PRIVILEGES ON DATABASE chaskipro_db TO chaskipro;
```

### 2. Compilar el proyecto

```bash
mvn clean install
```

### 3. Ejecutar la aplicación

```bash
mvn spring-boot:run
```

O ejecutar el JAR:
```bash
java -jar target/chaskipro-backend-1.0.0.jar
```

## 🔐 Seguridad

El proyecto utiliza Spring Security con JWT para autenticación y autorización.

### Configuración JWT

En `application.properties`:
```properties
jwt.secret=ChaskiproSuperSecretKeyForJWTTokenGeneration2025ChileMarketplace
jwt.expiration=86400000  # 24 horas
```

⚠️ **Importante**: Cambiar el secret en producción por una clave segura y usar variables de entorno.

## 📝 Variables de Entorno

Para producción, usar variables de entorno:

```bash
SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/chaskipro_db
SPRING_DATASOURCE_USERNAME=chaskipro
SPRING_DATASOURCE_PASSWORD=chaskipro123
JWT_SECRET=tu-secret-seguro-aqui
JWT_EXPIRATION=86400000
```

## 🧪 Testing

Ejecutar tests:
```bash
mvn test
```

## 📦 Build para Producción

### Crear JAR

```bash
mvn clean package -DskipTests
```

### Crear imagen Docker

```bash
docker build -t chaskipro-backend:latest .
```

## 🔐 Sistema de Autenticación Implementado

El proyecto incluye un sistema completo de autenticación JWT:

### Componentes de Seguridad

- ✅ **SecurityConfig**: Configuración de Spring Security con filtros JWT
- ✅ **JwtUtils**: Generación y validación de tokens JWT
- ✅ **JwtAuthenticationFilter**: Filtro para interceptar y validar tokens
- ✅ **UserDetailsServiceImpl**: Carga de usuarios para Spring Security
- ✅ **AuthController**: Endpoints de registro y login
- ✅ **AuthService**: Lógica de negocio de autenticación
- ✅ **GlobalExceptionHandler**: Manejo centralizado de errores

### Endpoints Disponibles

#### 🔓 Públicos (sin autenticación)
- `POST /auth/register` - Registrar nuevo usuario
- `POST /auth/login` - Iniciar sesión

#### 🔒 Protegidos por rol
- `GET/POST/PUT/DELETE /api/admin/**` - Solo ADMIN
- `GET/POST/PUT /api/profesional/**` - Solo PROFESIONAL
- `GET/POST /api/cliente/**` - Solo CLIENTE

Ver [API_ENDPOINTS.md](./API_ENDPOINTS.md) para documentación completa de la API.

## 🚀 Inicio Rápido

### Opción 1: Usar script de inicio (recomendado)
```bash
cd ~/Documents/chaskipro-backend
./start.sh
```

### Opción 2: Docker Compose manual
```bash
docker-compose up -d
```

### Opción 3: Desarrollo local
```bash
# Iniciar solo PostgreSQL
docker-compose up -d postgres

# Ejecutar la aplicación
mvn spring-boot:run
```

## 🧪 Probar la API

### Con curl
```bash
# Registrar usuario
curl -X POST http://localhost:8080/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "email": "test@test.com",
    "password": "test123",
    "nombreCompleto": "Test User",
    "rut": "12345678-9",
    "rol": "CLIENTE"
  }'

# Login
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "test@test.com",
    "password": "test123"
  }'
```

### Con archivo HTTP
Abrir `api-tests.http` en VS Code con la extensión REST Client instalada.

## 📁 Archivos Útiles

- `api-tests.http` - Colección de pruebas HTTP
- `start.sh` - Script de inicio rápido
- `API_ENDPOINTS.md` - Documentación completa de endpoints
- `docker-compose.yml` - Configuración de Docker

## 🚀 Próximos Pasos Sugeridos

1. ✅ ~~Implementar servicios de autenticación~~ **COMPLETADO**
2. ✅ ~~Crear controladores REST de autenticación~~ **COMPLETADO**
3. ✅ ~~Definir DTOs~~ **COMPLETADO**
4. ✅ ~~Configurar Spring Security y JWT~~ **COMPLETADO**
5. ✅ ~~Implementar manejo de excepciones~~ **COMPLETADO**
6. Crear servicios y controladores para las demás entidades
7. Agregar validador de RUT chileno personalizado
8. Documentación con Swagger/OpenAPI
9. Testing unitario e integración
10. Implementar paginación en endpoints de listado

## 📄 Licencia

Proyecto privado - Todos los derechos reservados

## 👥 Autor

Chaskipro Team
