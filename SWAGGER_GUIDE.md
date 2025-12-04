# Guía de Swagger UI - Chaskipro API

## 🎯 Acceso a la Documentación

La documentación interactiva de la API está disponible en:

**Swagger UI**: http://localhost:8080/swagger-ui/index.html

**JSON API Docs**: http://localhost:8080/v3/api-docs

## 📚 Características

### Endpoints Documentados

1. **Autenticación** (`/auth`)
   - `POST /auth/register` - Registrar nuevo usuario
   - `POST /auth/login` - Iniciar sesión y obtener JWT

2. **Comunas** (`/api/comunas`)
   - `GET /api/comunas` - Listar todas las comunas (público)
   - `GET /api/comunas/region/{region}` - Buscar por región (público)
   - `GET /api/comunas/{id}` - Obtener por ID (público)
   - `POST /api/comunas` - Crear comuna (admin)

3. **Profesionales** (`/api/profesionales`)
   - `POST /api/profesionales/perfil/{userId}` - Crear perfil (autenticado)
   - `PUT /api/profesionales/perfil/{profileId}` - Actualizar perfil (autenticado)
   - `GET /api/profesionales/comuna/{comunaId}` - Buscar por comuna (público)
   - `GET /api/profesionales` - Listar todos (admin)
   - `GET /api/profesionales/estado/{estado}` - Buscar por estado (admin)

## 🔐 Autenticación en Swagger

### Paso 1: Obtener Token JWT

1. Expande el endpoint `POST /auth/login`
2. Click en "Try it out"
3. Ingresa las credenciales:
   ```json
   {
     "email": "admin@chaskipro.com",
     "password": "Admin123!"
   }
   ```
4. Click en "Execute"
5. Copia el `token` de la respuesta

### Paso 2: Autorizar en Swagger

1. Click en el botón **"Authorize"** (🔒) en la parte superior
2. Ingresa el token en el campo "Value":
   ```
   Bearer eyJhbGciOiJIUzUxMiJ9...
   ```
3. Click en "Authorize"
4. Click en "Close"

Ahora puedes probar los endpoints protegidos.

## 🧪 Ejemplos de Uso

### Registrar un Cliente
```json
POST /auth/register
{
  "email": "cliente@example.com",
  "password": "Cliente123!",
  "nombreCompleto": "Juan Pérez",
  "rut": "12345678-9",
  "rol": "CLIENTE"
}
```

### Registrar un Profesional
```json
POST /auth/register
{
  "email": "profesional@example.com",
  "password": "Prof123!",
  "nombreCompleto": "María González",
  "rut": "98765432-1",
  "rol": "PROFESIONAL",
  "biografia": "Electricista con 10 años de experiencia",
  "telefono": "+56912345678"
}
```

### Crear Comuna (requiere admin)
```
POST /api/comunas?nombre=Providencia&region=Region Metropolitana
```

### Buscar Profesionales
```
GET /api/profesionales/comuna/1
```

## 📋 Códigos de Respuesta

- `200` - OK
- `201` - Created
- `400` - Bad Request (datos inválidos)
- `401` - Unauthorized (sin token o token inválido)
- `403` - Forbidden (sin permisos)
- `404` - Not Found
- `500` - Internal Server Error

## 🔧 Configuración

La documentación Swagger se configura en:
- **Config**: `src/main/java/com/chaskipro/backend/config/OpenApiConfig.java`
- **Properties**: `src/main/resources/application.properties`

```properties
springdoc.api-docs.path=/api-docs
springdoc.swagger-ui.path=/swagger-ui.html
springdoc.swagger-ui.operationsSorter=method
springdoc.swagger-ui.tagsSorter=alpha
springdoc.swagger-ui.tryItOutEnabled=true
```

## 🌐 Servidores Configurados

- **Desarrollo**: http://localhost:8080
- **Producción**: https://api.chaskipro.com

## 📝 Notas

- Todos los endpoints bajo `/swagger-ui/**` y `/v3/api-docs/**` son públicos
- Los endpoints protegidos requieren el header `Authorization: Bearer <token>`
- Los tokens JWT expiran después de 24 horas
- Los profesionales deben ser aprobados por un admin antes de aparecer en búsquedas públicas

