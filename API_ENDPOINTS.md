# API Endpoints - Chaskipro

## 🔐 Autenticación

Base URL: `http://localhost:8080`

### Registro de Usuario

**Endpoint:** `POST /auth/register`

**Body (JSON):**
```json
{
  "email": "usuario@ejemplo.com",
  "password": "password123",
  "nombreCompleto": "Juan Pérez",
  "rut": "12345678-9",
  "rol": "CLIENTE"
}
```

**Registro de Profesional:**
```json
{
  "email": "profesional@ejemplo.com",
  "password": "password123",
  "nombreCompleto": "María González",
  "rut": "98765432-1",
  "rol": "PROFESIONAL",
  "biografia": "Gasfiter con 10 años de experiencia",
  "telefono": "56912345678"
}
```

**Roles disponibles:**
- `CLIENTE`
- `PROFESIONAL`
- `ADMIN`

**Respuesta exitosa (201 Created):**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "type": "Bearer",
  "id": 1,
  "email": "usuario@ejemplo.com",
  "nombreCompleto": "Juan Pérez",
  "rol": "CLIENTE"
}
```

### Inicio de Sesión

**Endpoint:** `POST /auth/login`

**Body (JSON):**
```json
{
  "email": "usuario@ejemplo.com",
  "password": "password123"
}
```

**Respuesta exitosa (200 OK):**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "type": "Bearer",
  "id": 1,
  "email": "usuario@ejemplo.com",
  "nombreCompleto": "Juan Pérez",
  "rol": "CLIENTE"
}
```

### Uso del Token JWT

Para endpoints protegidos, incluir el token en el header:

```
Authorization: Bearer {token}
```

**Ejemplo con curl:**
```bash
curl -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..." \
     http://localhost:8080/api/protected-endpoint
```

## 📋 Validaciones

### RUT Chileno
- Formato: `12345678-9` (7-8 dígitos, guión, dígito verificador)
- Ejemplos válidos: `12345678-9`, `1234567-K`

### Teléfono Chile
- Formato: `56912345678` (código país + número)
- También acepta: `+56912345678`

### Email
- Debe ser un email válido
- Único en el sistema

### Contraseña
- Mínimo 6 caracteres
- Máximo 50 caracteres

## 🛡️ Seguridad

### Endpoints Públicos (sin autenticación)
- `POST /auth/register`
- `POST /auth/login`
- `GET /api/public/**`

### Endpoints Protegidos por Rol

#### ADMIN
- `GET/POST/PUT/DELETE /api/admin/**`

#### PROFESIONAL
- `GET/POST/PUT /api/profesional/**`

#### CLIENTE
- `GET/POST /api/cliente/**`

### Cualquier usuario autenticado
- Todos los demás endpoints bajo `/api/**`

## 🧪 Ejemplos de Uso

### Registro de Cliente
```bash
curl -X POST http://localhost:8080/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "email": "cliente@test.com",
    "password": "test123",
    "nombreCompleto": "Test Cliente",
    "rut": "11111111-1",
    "rol": "CLIENTE"
  }'
```

### Registro de Profesional
```bash
curl -X POST http://localhost:8080/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "email": "profesional@test.com",
    "password": "test123",
    "nombreCompleto": "Test Profesional",
    "rut": "22222222-2",
    "rol": "PROFESIONAL",
    "biografia": "Electricista certificado",
    "telefono": "56987654321"
  }'
```

### Login
```bash
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "cliente@test.com",
    "password": "test123"
  }'
```

### Uso del Token
```bash
# Guardar el token en una variable
TOKEN="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."

# Usar en requests protegidos
curl -H "Authorization: Bearer $TOKEN" \
     http://localhost:8080/api/protected-endpoint
```

## ⚠️ Errores Comunes

### 400 Bad Request
```json
{
  "timestamp": "2025-12-04T12:00:00",
  "status": 400,
  "error": "Validation Error",
  "message": "Error de validación en los campos",
  "path": "/auth/register",
  "validationErrors": {
    "email": "Email debe ser válido",
    "rut": "RUT debe tener formato válido (ej: 12345678-9)"
  }
}
```

### 401 Unauthorized
```json
{
  "timestamp": "2025-12-04T12:00:00",
  "status": 401,
  "error": "Unauthorized",
  "message": "Credenciales inválidas",
  "path": "/auth/login"
}
```

### 409 Conflict (Usuario ya existe)
```json
{
  "message": "El email ya está registrado"
}
```

## 🔄 CORS

Configuración de CORS habilitada para:
- `http://localhost:3000` (React/Next.js)
- `http://localhost:4200` (Angular)

Métodos permitidos: `GET`, `POST`, `PUT`, `DELETE`, `PATCH`, `OPTIONS`

## 🏢 Gestión de Comunas

### Obtener todas las comunas

**Endpoint:** `GET /api/comunas`

**Público:** Sí

**Respuesta exitosa:**
```json
[
  {
    "id": 1,
    "nombre": "Santiago",
    "region": "Metropolitana"
  },
  {
    "id": 2,
    "nombre": "Providencia",
    "region": "Metropolitana"
  }
]
```

### Crear comuna (Admin)

**Endpoint:** `POST /api/comunas?nombre={nombre}&region={region}`

**Headers:** `Authorization: Bearer {token}`

**Rol requerido:** ADMIN

## 👨‍🔧 Gestión de Profesionales

### Buscar profesionales por comuna

**Endpoint:** `GET /api/profesionales/comuna/{comunaId}`

**Público:** Sí (solo muestra profesionales APROBADOS)

**Respuesta exitosa:**
```json
[
  {
    "id": 1,
    "userId": 2,
    "nombreCompleto": "Carlos Muñoz",
    "email": "gasfiter@test.com",
    "biografia": "Gasfiter con 10 años de experiencia",
    "telefono": "56912345678",
    "estadoValidacion": "APROBADO",
    "promedioCalificacion": 4.5,
    "totalCalificaciones": 10,
    "serviciosCompletados": 15,
    "coberturas": [
      {
        "id": 1,
        "nombre": "Santiago",
        "region": "Metropolitana"
      }
    ],
    "fechaCreacion": "2025-12-04T10:00:00"
  }
]
```

### Crear perfil profesional

**Endpoint:** `POST /api/profesionales/perfil/{userId}`

**Headers:** `Authorization: Bearer {token}`

**Rol requerido:** PROFESIONAL o ADMIN

**Body:**
```json
{
  "biografia": "Gasfiter con 10 años de experiencia",
  "telefono": "56912345678",
  "comunaIds": [1, 2, 3]
}
```

### Aprobar perfil profesional (Admin)

**Endpoint:** `PATCH /api/profesionales/perfil/{profileId}/aprobar`

**Headers:** `Authorization: Bearer {token}`

**Rol requerido:** ADMIN

**Respuesta exitosa:** Retorna el perfil actualizado con `estadoValidacion: "APROBADO"`

### Obtener profesionales pendientes (Admin)

**Endpoint:** `GET /api/profesionales/estado/PENDIENTE`

**Headers:** `Authorization: Bearer {token}`

**Rol requerido:** ADMIN

## 📝 Notas Adicionales

1. **Token JWT:** Válido por 24 horas (86400000 ms)
2. **Sesiones:** Stateless (no se mantienen sesiones en el servidor)
3. **Encriptación:** Contraseñas con BCrypt
4. **Profesionales:** Al registrarse con rol `PROFESIONAL`, automáticamente se crea un perfil profesional con estado `PENDIENTE`
5. **Búsqueda por comuna:** Solo muestra profesionales con estado APROBADO
6. **Coberturas:** Los profesionales pueden cubrir múltiples comunas
