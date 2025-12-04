-- Script para actualizar contraseñas de usuarios de prueba
-- Password: password123 (encriptado con BCrypt)
-- Hash BCrypt de "password123"

UPDATE users SET password = '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy' WHERE email = 'admin@chaskipro.com';
UPDATE users SET password = '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy' WHERE email = 'profesional@test.com';
UPDATE users SET password = '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy' WHERE email = 'cliente@test.com';

-- Verificar actualización
SELECT id, email, nombre_completo, rol FROM users;
