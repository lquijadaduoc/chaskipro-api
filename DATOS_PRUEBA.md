# 🧪 Datos de Prueba - CHASKIPRO Backend

## 📊 Información de Datos Inicializados

El sistema se inicializa automáticamente con datos de prueba al iniciar la aplicación si la base de datos está vacía.

---

## 👷 Profesionales Disponibles

### 1. Carlos Rojas Muñoz - Electricista ⚡
- **Email**: `carlos.rojas@chaskipro.cl`
- **Password**: `password123`
- **Especialidad**: Electricista certificado
- **Teléfono**: +56912345678
- **Calificación**: 4.8/5.0 (127 reviews)
- **Servicios Completados**: 145
- **Cobertura**: Santiago Centro, Providencia, Las Condes, Ñuñoa, Maipú
- **Descripción**: Más de 10 años de experiencia. Especializado en instalaciones residenciales y comerciales. Certificación SEC al día.

### 2. Miguel Torres Soto - Plomero 🔧
- **Email**: `miguel.torres@chaskipro.cl`
- **Password**: `password123`
- **Especialidad**: Maestro Gasfiter
- **Teléfono**: +56923456789
- **Calificación**: 4.9/5.0 (203 reviews)
- **Servicios Completados**: 218
- **Cobertura**: Providencia, Las Condes, Ñuñoa, Maipú, La Florida, Puente Alto
- **Descripción**: 15 años de experiencia. Especialista en reparaciones de urgencia, instalación de cañerías y solución de filtraciones. Disponible 24/7.

### 3. Roberto Silva Campos - Pintor 🎨
- **Email**: `roberto.silva@chaskipro.cl`
- **Password**: `password123`
- **Especialidad**: Pintor Profesional
- **Teléfono**: +56934567890
- **Calificación**: 4.7/5.0 (89 reviews)
- **Servicios Completados**: 95
- **Cobertura**: Santiago Centro, Providencia, Las Condes, Ñuñoa
- **Descripción**: Especializado en interiores y exteriores. Trabajo con pinturas ecológicas de alta calidad.

### 4. Luis Morales Vega - Cerrajero 🔑
- **Email**: `luis.morales@chaskipro.cl`
- **Password**: `password123`
- **Especialidad**: Cerrajero de Emergencia
- **Teléfono**: +56945678901
- **Calificación**: 4.6/5.0 (156 reviews)
- **Servicios Completados**: 167
- **Cobertura**: Las Condes, Ñuñoa, Maipú, La Florida, Puente Alto, San Miguel
- **Descripción**: Disponible 24 horas. Apertura de puertas, cambio de cerraduras, duplicado de llaves. Servicio rápido y garantizado.

### 5. Andrés González Ramírez - Técnico de Refrigeración ❄️
- **Email**: `andres.gonzalez@chaskipro.cl`
- **Password**: `password123`
- **Especialidad**: Técnico en Aire Acondicionado
- **Teléfono**: +56956789012
- **Calificación**: 4.9/5.0 (142 reviews)
- **Servicios Completados**: 158
- **Cobertura**: Santiago Centro, Providencia, Las Condes, Ñuñoa, Maipú, La Florida
- **Descripción**: Especializado en aire acondicionado y refrigeración. Instalación, mantención y reparación de equipos split y centrales. Certificado SEC.

### 6. Pedro Ramírez Castro - Carpintero 🪚
- **Email**: `pedro.ramirez@chaskipro.cl`
- **Password**: `password123`
- **Especialidad**: Maestro Carpintero
- **Teléfono**: +56967890123
- **Calificación**: 4.8/5.0 (98 reviews)
- **Servicios Completados**: 104
- **Cobertura**: Providencia, Las Condes, Ñuñoa, Maipú
- **Descripción**: 20 años de experiencia. Muebles a medida, reparaciones, closets, puertas. Trabajo garantizado con materiales de primera calidad.

### 7. Juan López Fernández - Jardinero 🌱
- **Email**: `juan.lopez@chaskipro.cl`
- **Password**: `password123`
- **Especialidad**: Jardinero Profesional
- **Teléfono**: +56978901234
- **Calificación**: 4.7/5.0 (76 reviews)
- **Servicios Completados**: 82
- **Cobertura**: Las Condes, Ñuñoa, Maipú, La Florida, Puente Alto
- **Descripción**: Mantención de jardines, poda de árboles, diseño paisajístico, sistemas de riego. Servicio mensual disponible.

### 8. Francisco Herrera Díaz - Técnico de Computadores 💻
- **Email**: `francisco.herrera@chaskipro.cl`
- **Password**: `password123`
- **Especialidad**: Técnico en Computación
- **Teléfono**: +56989012345
- **Calificación**: 4.8/5.0 (134 reviews)
- **Servicios Completados**: 145
- **Cobertura**: Santiago Centro, Providencia, Las Condes, Ñuñoa, Maipú, La Florida, Puente Alto, San Miguel
- **Descripción**: Reparación de PC y notebooks, instalación de redes, formateo, respaldo de datos. Soporte técnico remoto disponible.

---

## 👤 Cliente de Prueba

### María González Pérez
- **Email**: `cliente@chaskipro.cl`
- **Password**: `password123`
- **RUT**: 15678901-2
- **Rol**: CLIENTE

---

## 🗺️ Comunas Disponibles

1. Santiago Centro
2. Providencia
3. Las Condes
4. Ñuñoa
5. Maipú
6. La Florida
7. Puente Alto
8. San Miguel
9. Estación Central
10. Recoleta

---

## 🔌 Endpoints para Consultar Profesionales

### Listar Todos los Profesionales
```http
GET http://localhost:8080/api/professionals
```

**Respuesta Ejemplo:**
```json
[
  {
    "id": 1,
    "nombreCompleto": "Carlos Rojas Muñoz",
    "email": "carlos.rojas@chaskipro.cl",
    "biografia": "Electricista certificado con más de 10 años de experiencia...",
    "telefono": "+56912345678",
    "promedioCalificacion": 4.8,
    "totalCalificaciones": 127,
    "serviciosCompletados": 145,
    "estadoValidacion": "APROBADO",
    "coberturas": [...]
  }
]
```

### Buscar Profesionales por Comuna
```http
GET http://localhost:8080/api/professionals/by-comuna?comunaId=1
```

### Buscar Profesionales por Nombre
```http
GET http://localhost:8080/api/professionals/search?query=electricista
```

### Obtener Detalle de un Profesional
```http
GET http://localhost:8080/api/professionals/{id}
```

---

## 🧪 Pruebas con Postman / Insomnia

### 1. Login como Cliente
```http
POST http://localhost:8080/api/auth/login
Content-Type: application/json

{
  "email": "cliente@chaskipro.cl",
  "password": "password123"
}
```

### 2. Login como Profesional
```http
POST http://localhost:8080/api/auth/login
Content-Type: application/json

{
  "email": "carlos.rojas@chaskipro.cl",
  "password": "password123"
}
```

### 3. Listar Profesionales (Sin Autenticación)
```http
GET http://localhost:8080/api/professionals
```

### 4. Buscar por Comuna (Santiago Centro = ID 1)
```http
GET http://localhost:8080/api/professionals/by-comuna?comunaId=1
```

---

## 🔄 Reiniciar Datos de Prueba

Si necesitas reiniciar los datos:

1. **Opción 1: Borrar base de datos**
   ```bash
   # Si usas H2 en memoria, simplemente reinicia la aplicación
   # Si usas MySQL/PostgreSQL, ejecuta:
   DROP DATABASE chaskipro;
   CREATE DATABASE chaskipro;
   ```

2. **Opción 2: Configurar en application.properties**
   ```properties
   # Recrear esquema en cada inicio (solo para desarrollo)
   spring.jpa.hibernate.ddl-auto=create-drop
   ```

---

## 📝 Notas Importantes

- ✅ Todos los profesionales tienen estado `APROBADO`
- ✅ Las contraseñas están encriptadas con BCrypt
- ✅ Los RUT tienen formato válido chileno
- ✅ Los teléfonos tienen formato chileno (+569...)
- ✅ Las calificaciones están entre 4.6 y 4.9/5.0
- ✅ Cada profesional tiene múltiples comunas de cobertura

---

## 🚀 Iniciar Backend con Datos de Prueba

```bash
# Navegar al directorio del backend
cd ~/Documents/chaskipro-backend

# Compilar y ejecutar
./mvnw spring-boot:run

# O si tienes Maven instalado
mvn spring-boot:run
```

**Logs esperados:**
```
🚀 Inicializando datos de prueba...
✅ Creadas 10 comunas de ejemplo
✅ Creado profesional: Carlos Rojas Muñoz
✅ Creado profesional: Miguel Torres Soto
...
✅ Datos de prueba cargados exitosamente
📊 Total usuarios: 9
👷 Total profesionales: 8
```

---

## 🔗 Integración con Frontend

Para conectar con el frontend React:

1. Asegúrate de que el backend esté corriendo en `http://localhost:8080`
2. En el frontend, configura axios para consumir los endpoints
3. Usa los datos de login para probar autenticación
4. Consulta la lista de profesionales para mostrar en la búsqueda

---

¡Listo para probar! 🎉
