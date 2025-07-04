# Guía de Pruebas con Postman - API Denuncias Ciudadanas

Este documento describe el uso del archivo `app-denuncias.postman_collection.json` para probar el backend del sistema de denuncias ciudadanas. La colección contiene solicitudes estructuradas para cubrir la mayor parte de funcionalidades del sistema, desde autenticación hasta el flujo de moderación y notificación.

---

## 📦 Requisitos previos

1. Tener el [backend](../../README.md) corriendo localmente o en un entorno accesible (por defecto en `http://localhost:8080`).
2. Tener Postman instalado o acceder a Postman Web.
3. Importar la colección desde el archivo JSON:
   - Ir a Postman > "Import"
   - Seleccionar el archivo `app-denuncias.postman_collection.json`

---

## 🔐 Autenticación JWT

- La colección está preparada para manejar tokens JWT.
- Después de realizar login, el token se guarda en una variable de entorno llamada `token`.
- Las demás peticiones usan esta variable en el header:
  ```http
  Authorization: Bearer {{token}}
  ```

> ✅ No olvides ejecutar primero el request **Login (auth/login)** con un usuario existente.

---

## 👤 Usuarios de prueba sugeridos

Puedes usar los siguientes usuarios al probar:

| Rol       | Username   | Password |
| --------- | ---------- | ------- |
| Admin     | bausami    | 123     |
| Moderador | mfernandez | 123     |
| Usuario   | jdoe       | 123     |

---

## 🔍 Estructura de la Colección

### 1. 🔓 Autenticación

- `POST /auth/login`
- `POST /registro`

### 2. 👤 Usuario

- Obtener perfil actual
- Listar todos
- Activar/desactivar
- Actualizar información

### 3. 🚧 Denuncias

- Crear nueva
- Editar denuncia (si está en estado pendiente)
- Listar por usuario o moderador
- Eliminar (si está en estado pendiente)
- Cambiar estado
- Asignar moderador

### 4. ✉️ Solicitudes

- Crear solicitud (mod/admin)
- Revisar solicitud (admin)
- Obtener solicitudes por usuario, por estado

### 5. 💬 Notificaciones

- Listar no leídas
- Marcar como leída

### 6. 🌐 Enumeraciones (datos estáticos)

- Roles
- Categorías
- Estados
- Tipos de solicitud

---

## 🚫 Reglas de Negocio importantes

- Solo usuarios autenticados pueden acceder a la mayoría de endpoints.
- Solo **admins** pueden:
  - Asignar moderadores
  - Aceptar/rechazar solicitudes
- Las denuncias solo se pueden eliminar si están **PENDIENTES**.
- Las solicitudes pueden tener distintos tipos: asignación, archivado, comunicación interna, etc.
- Las notificaciones son generadas automáticamente al realizar ciertas acciones.

---

## 📃 Recomendaciones de uso

- Ejecutar los requests en orden lógico: login → crear denuncia → solicitud/moderación → notificaciones.
- Usar entornos de Postman con variable `base_url` para mayor flexibilidad.
- Al revisar solicitudes, recuerda que el revisor es el admin autenticado.

---

## 📄 Posibles mejoras

- Agregar pruebas automatizadas usando Postman Collection Runner o Newman.
