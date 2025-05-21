# Sistema de Gestión de Biblioteca UPTC

Este proyecto implementa un sistema de gestión para la biblioteca de la UPTC Seccional Sogamoso que permite administrar préstamos de material bibliográfico, consultar usuarios con retrasos en devoluciones, y generar listados de préstamos y recursos.

## Tecnologías utilizadas

* Java 1.8
* Spring Boot 3.2.2
* Spring Data JPA
* PostgreSQL
* Swagger/OpenAPI para documentación

## Requisitos previos

* JDK 17+
* Maven 3.8+
* PostgreSQL 15+

## Configuración de la base de datos

1. Crear una base de datos PostgreSQL llamada "library_db"
2. Ajustar las credenciales en el archivo `application.properties` según sea necesario

## Instalación y ejecución

1. Clonar el repositorio:
```bash
https://github.com/Danieln416/sistema-de-gestion-de-biblioteca.git
```

2. Compilar y ejecutar el proyecto:
```bash
mvn clean install
mvn spring-boot:run
```

3. Acceder a la aplicación:
   * API REST: http://localhost:8080/api
   * Documentación Swagger: http://localhost:8080/api/swagger-ui.html

## Endpoints principales

### Usuarios
* `GET /api/users` - Listar todos los usuarios
* `GET /api/users/{id}` - Buscar usuario por ID
* `GET /api/users/overdue` - Listar usuarios con préstamos vencidos
* `POST /api/users` - Crear nuevo usuario

### Recursos
* `GET /api/resources` - Listar todos los recursos
* `GET /api/resources/search` - Buscar recursos por criterios
* `GET /api/resources/available` - Listar recursos disponibles
* `POST /api/resources` - Crear nuevo recurso

### Préstamos
* `GET /api/loans` - Listar todos los préstamos
* `GET /api/loans/overdue` - Listar préstamos vencidos
* `POST /api/loans` - Crear nuevo préstamo
* `PUT /api/loans/return/{id}` - Registrar devolución de préstamo

## Funcionalidades principales

* Gestión de usuarios (crear, modificar, eliminar)
* Gestión de recursos bibliográficos (crear, modificar, eliminar)
* Préstamos y devoluciones de material
* Consulta de préstamos activos y vencidos
* Búsqueda avanzada de recursos por múltiples criterios
* Identificación de usuarios con retrasos en devoluciones

## Autor

* Nombre: Daniel Camilo Rodriguez Ramirez
* Email: Daniel.rodriguez10@uptc.edu.co
* GitHub: https://github.com/