# Arquitectura de Seguridad - Chaskipro

## 📊 Diagrama de Flujo de Autenticación

```
┌─────────────────────────────────────────────────────────────────────┐
│                         REGISTRO DE USUARIO                         │
└─────────────────────────────────────────────────────────────────────┘

Cliente HTTP
   │
   │ POST /auth/register
   │ { email, password, nombreCompleto, rut, rol }
   ▼
AuthController
   │
   │ Validaciones (@Valid)
   ▼
AuthService
   │
   ├─► Verificar email único
   ├─► Verificar RUT único
   ├─► Encriptar password (BCrypt)
   ├─► Crear User
   ├─► Si PROFESIONAL → Crear ProfessionalProfile
   └─► Generar JWT Token
   │
   ▼
JwtUtils
   │
   └─► Token JWT con claims: { email, rol, userId }
   │
   ▼
AuthResponse
   │
   └─► { token, id, email, nombreCompleto, rol }


┌─────────────────────────────────────────────────────────────────────┐
│                         INICIO DE SESIÓN                            │
└─────────────────────────────────────────────────────────────────────┘

Cliente HTTP
   │
   │ POST /auth/login
   │ { email, password }
   ▼
AuthController
   │
   │ Validaciones (@Valid)
   ▼
AuthService
   │
   ├─► AuthenticationManager.authenticate()
   │   │
   │   ▼
   │   UserDetailsServiceImpl
   │   │
   │   ├─► Buscar User por email
   │   ├─► Verificar si está activo
   │   └─► Crear UserDetails con authorities
   │
   ├─► Validar credenciales
   └─► Generar JWT Token
   │
   ▼
AuthResponse
   │
   └─► { token, id, email, nombreCompleto, rol }


┌─────────────────────────────────────────────────────────────────────┐
│                    REQUEST A ENDPOINT PROTEGIDO                     │
└─────────────────────────────────────────────────────────────────────┘

Cliente HTTP
   │
   │ GET/POST/PUT/DELETE /api/...
   │ Header: Authorization: Bearer {token}
   ▼
JwtAuthenticationFilter (OncePerRequestFilter)
   │
   ├─► Extraer token del header "Authorization"
   ├─► Validar formato "Bearer {token}"
   │
   ▼
JwtUtils.validateToken()
   │
   ├─► Verificar firma del token
   ├─► Verificar expiración
   └─► Extraer email del token
   │
   ▼
UserDetailsServiceImpl.loadUserByUsername()
   │
   ├─► Buscar User por email
   ├─► Verificar activo
   └─► Crear UserDetails con ROLE_XXX
   │
   ▼
SecurityContextHolder
   │
   └─► Establecer Authentication en contexto
   │
   ▼
SecurityConfig.authorizeHttpRequests()
   │
   ├─► Verificar autorización por rol
   │   • /api/admin/** → ROLE_ADMIN
   │   • /api/profesional/** → ROLE_PROFESIONAL
   │   • /api/cliente/** → ROLE_CLIENTE
   │
   └─► Si autorizado → Continuar al Controller
       Si NO autorizado → 403 Forbidden
```

## 🔧 Componentes Principales

### 1. SecurityConfig
```java
- Configura filtros de seguridad
- Define reglas de autorización por URL y rol
- Configura CORS
- Deshabilita CSRF (API REST stateless)
- SessionCreationPolicy.STATELESS (sin sesiones)
```

### 2. JwtUtils
```java
- generateToken(email, rol, userId) → String
- validateToken(token) → Boolean
- validateToken(token, userDetails) → Boolean
- extractEmail(token) → String
- extractRol(token) → String
- extractUserId(token) → Long
```

### 3. JwtAuthenticationFilter
```java
- Intercepta TODAS las peticiones HTTP
- Extrae y valida el token JWT
- Establece la autenticación en SecurityContext
- Se ejecuta antes de UsernamePasswordAuthenticationFilter
```

### 4. UserDetailsServiceImpl
```java
- Implementa UserDetailsService de Spring Security
- Carga usuarios desde la base de datos
- Convierte User → UserDetails
- Asigna authorities (roles) con prefijo ROLE_
```

### 5. AuthService
```java
- Lógica de registro de usuarios
- Lógica de autenticación
- Creación automática de ProfessionalProfile
- Validaciones de negocio (email/RUT únicos)
```

### 6. GlobalExceptionHandler
```java
- Maneja MethodArgumentNotValidException (validaciones)
- Maneja RuntimeException
- Maneja UsernameNotFoundException
- Maneja BadCredentialsException
- Respuestas JSON estandarizadas con ErrorResponse
```

## 🔐 Flujo de Seguridad

1. **Usuario se registra** → Recibe token JWT
2. **Usuario hace login** → Recibe token JWT
3. **Usuario guarda token** (localStorage, sessionStorage, etc.)
4. **Usuario hace request** → Envía token en header `Authorization: Bearer {token}`
5. **JwtAuthenticationFilter** → Valida token y establece autenticación
6. **SecurityConfig** → Verifica autorización por rol
7. **Controller** → Procesa request si está autorizado

## 📋 Roles y Permisos

| Rol | Descripción | Endpoints |
|-----|-------------|-----------|
| **CLIENTE** | Usuario que solicita servicios | `/api/cliente/**` |
| **PROFESIONAL** | Proveedor de servicios | `/api/profesional/**` |
| **ADMIN** | Administrador del sistema | `/api/admin/**` |

## 🛡️ Características de Seguridad

- ✅ **Contraseñas encriptadas** con BCrypt
- ✅ **Tokens JWT firmados** con HS256
- ✅ **Validación de tokens** en cada request
- ✅ **Sesiones stateless** (sin estado en servidor)
- ✅ **CORS configurado** para frontend
- ✅ **Validaciones de entrada** con Jakarta Validation
- ✅ **Manejo centralizado de errores**
- ✅ **Roles y autorización** basada en endpoints
- ✅ **Tokens con expiración** (24 horas)

## 📝 Configuración JWT

```properties
jwt.secret=ChaskiproSuperSecretKeyForJWTTokenGeneration2024ChileMarketplace
jwt.expiration=86400000  # 24 horas en milisegundos
```

⚠️ **IMPORTANTE**: En producción, usar variables de entorno y una clave más segura.

## 🧪 Testing de Seguridad

### Probar autenticación
```bash
# 1. Registrar usuario
TOKEN=$(curl -s -X POST http://localhost:8080/auth/register \
  -H "Content-Type: application/json" \
  -d '{"email":"test@test.com","password":"test123","nombreCompleto":"Test","rut":"12345678-9","rol":"CLIENTE"}' \
  | jq -r '.token')

# 2. Usar token en request protegido
curl -H "Authorization: Bearer $TOKEN" \
  http://localhost:8080/api/protected-endpoint
```

### Probar autorización por rol
```bash
# Cliente intenta acceder a endpoint de admin (debe fallar con 403)
curl -H "Authorization: Bearer $CLIENTE_TOKEN" \
  http://localhost:8080/api/admin/users

# Admin accede correctamente
curl -H "Authorization: Bearer $ADMIN_TOKEN" \
  http://localhost:8080/api/admin/users
```
