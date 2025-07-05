
# Sistema de Gestión de Denuncias Ciudadanas 🛡️

Este proyecto backend forma parte de un sistema para gestionar denuncias ciudadanas en una municipalidad u organización. Está desarrollado en **Java** utilizando **Spring Boot** y sigue principios de diseño limpio, seguridad basada en JWT, y buenas prácticas REST.

---

## 📦 Estructura General

El backend está organizado en los siguientes paquetes principales:

- `controller`: Exposición de servicios RESTful.
- `service`: Lógica de negocio.
- `model`: Entidades JPA (Denuncia, Usuario, Solicitud, Notificación).
- `repository`: Interfaces JPA para acceso a datos.
- `dto`: Objetos de transferencia de datos (Request/Response).
- `security`: Seguridad JWT y configuración de roles.
- `enums`: Estados, roles, categorías y tipos de solicitud.

---

## ⚙️ Tecnologías

- Java 17+
- Spring Boot 3.x
- Spring Data JPA
- Spring Security + JWT
- Lombok
- JPA/Hibernate
- H2 (desarrollo) / MySQL (producción)

---

## 🚀 Ejecución del proyecto

1. Clonar el repositorio.
2. Configurar la base de datos en `application.properties`.
3. Ejecutar la clase principal con Spring Boot.
4. Acceder a Swagger o usar [Postman](./src/postman) para probar los endpoints.

---

## 🔐 Seguridad

- Uso de JWT para autenticación sin estado.
- Tres roles definidos: `USER`, `MOD`, `ADMIN`.
- Filtros de autorización para rutas protegidas:
  - `/usuario/**`, `/enum/**`: Acceso para cualquier usuario autenticado.
  - `/mod/**`: Solo moderadores y administradores.
  - `/admin/**`: Solo administradores.

---

## 📫 Principales Endpoints

### Autenticación
- `POST /auth/login`: Inicio de sesión con JWT.
- `POST /registro`: Registro de nuevo usuario.

### Usuarios y Roles
- `GET /usuario/roles`: Obtener roles disponibles.
- `GET /usuario/estados`: Estados de denuncia.
- `GET /usuario/categorias`: Categorías de denuncia.
- `GET /usuario/tiposSolicitud`: Tipos de solicitud posibles.

### Denuncias
- Crear, listar, modificar estado, archivar, etc.

### Solicitudes
- Crear solicitud (asignación, cambio de estado, eliminación...).
- Revisión y resolución de solicitudes.

### Notificaciones
- Notificación automática al resolver una solicitud.
- Lectura de notificaciones por usuario.

---

## 📋 Observaciones

- El sistema está preparado para integrarse con un frontend en Angular.
- Se usa un controlador exclusivo para exponer los valores de enums (para poblar selects).
- El enum `TipoSolicitud`, `Estado`, `Rol`, `Categoria` incluyen título y descripción, útiles para el frontend.
- La lógica de notificaciones se centraliza en el servicio `NotificationService`.

---

## ✍️ Autor

- `BAUTISTA SAMILLAN YVAN JUNIOR`

Desarrollado como proyecto universitario.
