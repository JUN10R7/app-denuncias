# 🛡️ Sistema de Denuncias Ciudadanas

Este proyecto es una aplicación web para la gestión de denuncias ciudadanas, desarrollada con Angular en el frontend y Spring Boot en el backend. Utiliza MySQL como base de datos y Docker/Docker Compose para el despliegue completo.

---

## 🚀 Tecnologías

- ⚙️ Backend: Spring Boot (Java 17)
- 🌐 Frontend: Angular 20 + Tailwind CSS + GSAP
- 🗄️ Base de datos: MySQL 8
- 🐳 Contenedores: Docker y Docker Compose

---

## 📦 Estructura del proyecto

.
├── backend/ → Proyecto Spring Boot
│ └── Dockerfile
├── frontend/ → Proyecto Angular
│ ├── Dockerfile
│ ├── nginx.conf
├── mysql/ → Inicialización de base de datos
│ └── init.sql
├── docker-compose.yml → Orquestación de contenedores
└── README.md → Este archivo

yaml
Copiar
Editar

---

## 🔧 Configuración y despliegue

### 1. Requisitos

- Docker
- Docker Compose
- Node.js (opcional, solo para desarrollo frontend)
- Java 17 (opcional, solo para desarrollo backend)

---

### 2. Variables por defecto

- **Frontend**: Puerto `4200` (Angular)
- **Backend**: Puerto `8080` (Spring Boot)
- **Base de datos**: Puerto `3307` (MySQL)

> ⚠️ ¡Asegúrate de que no haya conflictos de puertos en tu sistema!

---

### 3. Despliegue en producción (modo contenedor)

```bash
docker-compose up --build
Esto levantará 3 servicios:

Servicio	URL de acceso
Frontend	http://localhost:4200
Backend	http://localhost:8080/api
MySQL	mysql://root:root@localhost:3307/denunciasdb

4. Comandos útiles
Ver contenedores activos

bash
Copiar
Editar
docker ps
Detener todo

bash
Copiar
Editar
docker-compose down
Reconstruir todo

bash
Copiar
Editar
docker-compose up --build
Acceder a la base de datos

bash
Copiar
Editar
docker exec -it mysql-denuncias mysql -u root -p
# contraseña: root
🧪 Acceso durante desarrollo (opcional)
Frontend (Angular CLI)
bash
Copiar
Editar
cd frontend
npm install
ng serve
# Visitar: http://localhost:4200
Backend (Spring Boot)
bash
Copiar
Editar
cd backend
./mvnw spring-boot:run
# Visitar: http://localhost:8080/api
🌍 CORS Configurado
El backend acepta peticiones de: http://localhost:4200

Si cambias el puerto del frontend, también debes modificar la clase de configuración CORS en Spring Boot.

🛠️ Pendientes o mejoras sugeridas
 Agregar autenticación y autorización más robusta (JWT, roles).

 Mejorar control de errores en frontend/backend.

 Agregar pruebas unitarias y de integración.

 Implementar HTTPS en producción.

 Documentar la API con Swagger/OpenAPI.

📄 Licencia
Proyecto académico desarrollado por estudiantes de sistemas. Licencia educativa.