# Book Catalog API

  [![Java](https://img.shields.io/badge/Java-21-ff8c00.svg)](https://adoptium.net/)
  [![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.6-6db33f.svg)](https://spring.io/projects/spring-boot)
  [![Maven](https://img.shields.io/badge/Maven-3.9+-c71a36.svg)](https://maven.apache.org/)
  [![MySQL](https://img.shields.io/badge/MySQL-8.0-00758f.svg)](https://www.mysql.com/)

  API REST construida con Spring Boot para gestionar un catálogo de libros y autores. Incluye lógica de negocio para validar datos editoriales, evita inconsistencias al
  eliminar autores con libros asociados y expone operaciones CRUD completas para ambos dominios.

  ---

  ## Características clave

  - **Dominio rico**: modelos `Author`, `Book`, `Edition` y `ContactInfo` con reglas de validación expresivas.
  - **Arquitectura hexagonal ligera**: controladores REST → servicios de dominio → adaptadores JPA, todo desacoplado mediante interfaces.
  - **Validaciones exhaustivas**: ISBN, fechas, copias vendidas, pseudónimos y datos de contacto se validan antes de persistir.
  - **Manejo centralizado de errores**: `ExceptionHandlerRegistry` traduce excepciones de dominio a respuestas HTTP claras.
  - **Listo para producción**: perfiles configurables, Dockerfile y `docker-compose` con MySQL 8 y healthcheck.

  ---

  ## Arquitectura

  ```mermaid
  graph TD
    Client[Cliente REST] -->|HTTP| Controllers[Adapters / Controller]
    Controllers --> Services[Domain Services]
    Services --> Facade[AuthorFacade]
    Services --> Repositories[Ports / Repository]
    Repositories --> Adapters[Adapters / JPA]
    Adapters --> DB[(MySQL 8)]
  ```
  ---

  ## Estructura del proyecto

  ```mermaid
  graph TD
    A[src/main/java/com/dataspartan/catalog]
    A --> B[CatalogApplication.java]
    A --> C[adapter]
    C --> C1[controller]
    C1 --> C11[AuthorController.java]
    C1 --> C12[BookController.java]
    C --> C2[persistence]
    C2 --> C21[entity]
    C2 --> C22[jpa]
    C22 --> C23[adapter / repository]
    C2 --> C24[mapper]
    A --> D[domain]
    D --> D1[author / book / facade]
    D --> D2[exception]
    A --> E[resources]
    E --> E1[application.properties]
  ```
  ---

  ## Requisitos previos

  - Java 21 (Temurin recomendado).
  - Maven 3.9+ o el wrapper ./mvnw.
  - MySQL 8.0 (o Docker para usar el docker-compose incluido).
  - Docker 24+ y Docker Compose v2 (opcional para despliegue en contenedores).

  ---

  ## Puesta en marcha

  ### Opción 1 · Local con Maven

  ./mvnw clean package
  ./mvnw spring-boot:run

  La aplicación escucha en http://localhost:8080.

  ### Opción 2 · Docker Compose (app + MySQL)

  docker compose up --build

  - API: http://localhost:8080
  - MySQL: localhost:3306 (usuario y contraseña)

  ---

  ## Configuración

  Parámetros por defecto (src/main/resources/application.properties):

  spring.datasource.url=jdbc:mysql://localhost:3306/catalog
  spring.datasource.username=luis.gf
  spring.datasource.password=provisional
  spring.jpa.hibernate.ddl-auto=update
  spring.jackson.date-format=yyyy-MM-dd
  spring.profiles.active=local

  Ajusta credenciales o perfiles mediante variables de entorno SPRING_DATASOURCE_* y SPRING_PROFILES_ACTIVE.

  ---

  ## API REST

  | Método | Endpoint                         | Descripción                                  |
  |--------|----------------------------------|----------------------------------------------|
  | GET    | /author                        | Lista todos los autores                      |
  | GET    | /author/{id}                   | Recupera un autor por ID                     |
  | POST   | /author                        | Crea un autor                                |
  | PUT    | /author/{id}                   | Actualiza un autor                           |
  | DELETE | /author/{id}                   | Elimina un autor (solo si no tiene libros)   |
  | POST   | /author/{id}/contact           | Añade info de contacto                       |
  | GET    | /author/{id}/contact           | Lista contactos de un autor                  |
  | GET    | /author/{id}/contact/{idx}     | Devuelve un contacto concreto                |
  | PUT    | /author/{id}/contact/{idx}     | Actualiza un contacto                        |
  | DELETE | /author/{id}/contact/{idx}     | Borra un contacto                            |
  | GET    | /book                          | Lista todos los libros                       |
  | GET    | /book/{id}                     | Recupera un libro                            |
  | POST   | /book                          | Crea un libro                                |
  | PUT    | /book/{id}                     | Actualiza un libro                           |
  | DELETE | /book/{id}                     | Elimina un libro                             |
  | POST   | /book/{id}/edition             | Añade una edición                            |
  | GET    | /book/{id}/edition             | Lista ediciones de un libro                  |
  | GET    | /book/{id}/edition/{idx}       | Recupera una edición                         |
  | PUT    | /book/{id}/edition/{idx}       | Actualiza una edición                        |
  | DELETE | /book/{id}/edition/{idx}       | Borra una edición                            |

  ### Ejemplo de creación de libro

  POST /book
  Content-Type: application/json

  {
    "title": "El Nombre del Viento",
    "authorIds": [1],
    "publicationYear": 2007,
    "authorPseudonyms": {
      "1": "Patrick Rothfuss"
    },
    "editions": [
      {
        "isbn": "9780765311788",
        "publisher": "DAW Books",
        "publishedDate": "2007-03-27",
        "language": "es",
        "pages": 662,
        "totalCopies": 10000,
        "soldCopies": 8500
      }
    ]
  }

  Respuestas de error coherentes:

  {
    "status": 412,
    "error": "Precondition Failed",
    "message": "Cannot delete author: author has associated books",
    "path": "/author/1",
    "timestamp": "2025-02-05T22:15:34.123"
  }

  ---

  ## Reglas de negocio destacadas

  - Un autor no puede eliminarse si existen libros que lo referencian (AuthorFacadeImpl).
  - Fechas futuras o inconsistentes (nacimiento > fallecimiento, publicación futura, etc.) disparan InvalidArgumentsException.
  - Validación de ISBN (10/13 dígitos), copias totales vs vendidas y páginas > 0.
  - Contactos y ediciones se gestionan como colecciones embebidas (element collections) en JPA.

  ---

  ## Contribuciones

  1. Haz un fork del repositorio.
  2. Crea una rama feature: git checkout -b feature/nueva-funcionalidad que cuelgue de la rama develop.
  3. Asegúrate de pasar los tests y respeta el estilo del proyecto.
  4. Envía un pull request explicando claramente el cambio.

