# API Tienda – API REST con Spring Boot (Productos y Usuarios)

API REST desarrollada con **Spring Boot** que expone las operaciones CRUD (crear, consultar, actualizar y eliminar) para dos módulos: **Productos** y **Usuarios**. El proyecto sigue una arquitectura por capas (controlador, servicio, repositorio y modelo), usa **Lombok** para eliminar código repetitivo, valida los datos de entrada y devuelve los errores en formato JSON.
Profe en caso de necesitar un poco de claridad sucede que este repositorio tiene todos los endpoints y al abrilo en intellij idea le va a salir como api, luego dento esta otro folder tambien llamado api el cual tienen un src donde esta java y dentro de ello esta todo el codigo fuente del proyecto.
<img width="562" height="41" alt="image" src="https://github.com/user-attachments/assets/3e483831-3642-4f97-9177-8596817be8cf" />


Reglas de negocio del módulo de Usuarios:

- El **correo electrónico es único**: intentar registrar uno repetido devuelve `400`.
- La **contraseña nunca se devuelve** en las respuestas de la API.

---

## Tecnologías empleadas

| Tecnología | Versión | Uso |
|---|---|---|
| Java | 21 (definida en `pom.xml`) | Lenguaje |
| Spring Boot | 4.1.1 | Framework (Web MVC, Data JPA, Validation) |
| Hibernate ORM | 7.4.5.Final | Mapeo objeto-relacional |
| Apache Tomcat (embebido) | 11.0.24 | Servidor web |
| Lombok | 1.18.46 | Reducción de código repetitivo |
| PostgreSQL | 17 | Base de datos (en contenedor Docker) |
| Driver JDBC PostgreSQL | 42.7.13 | Conexión a la base de datos |
| Apache Maven | Maven Wrapper (`mvnw`) | Gestión de dependencias y compilación |
| Docker Compose | — | Despliegue de la base de datos |
| Postman | — | Pruebas de los endpoints |

---

## Requisitos previos

- JDK 21 o superior.
- Docker Desktop (para levantar PostgreSQL).
- Un IDE con soporte para Lombok (IntelliJ IDEA o VS Code) con el procesamiento de anotaciones habilitado.
- Postman (opcional, para probar la API).

---

## Configuración de la base de datos

La base de datos se levanta con Docker Compose usando el archivo `docker-compose.yml` de la raíz del proyecto:

```bash
docker compose up -d
```

Esto crea el contenedor `tiendaya-db` con PostgreSQL 17 y estos datos:

| Parámetro | Valor |
|---|---|
| Base de datos | `tiendaya` |
| Usuario | `tiendaya` |
| Contraseña | `tiendaya123` |
| Puerto en el equipo | `5433` |

Para verificar que el contenedor está activo:

```bash
docker ps
```

La conexión se configura en `src/main/resources/application.properties`:

```properties
spring.application.name=api
server.port=8080
spring.datasource.url=jdbc:postgresql://localhost:5433/tiendaya?sslmode=disable
spring.datasource.username=tiendaya
spring.datasource.password=tiendaya123
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

Con `ddl-auto=update`, Hibernate crea automáticamente las tablas `products` y `usuarios` al arrancar la aplicación.

> **Nota:** `ddl-auto=update` no modifica columnas que ya existen. Si cambia el tipo de una columna, elimine la tabla (por ejemplo `DROP TABLE products;`) y reinicie la aplicación.

---

## Ejecución de la aplicación

1. Levante la base de datos (`docker compose up -d`).
2. Compile y ejecute el proyecto:

   **Windows**
   ```bash
   mvnw.cmd spring-boot:run
   ```

   **Linux / macOS**
   ```bash
   ./mvnw spring-boot:run
   ```

   También puede ejecutar la clase `ApiApplication` desde el IDE.

3. Confirme en la consola el mensaje:

   ```
   Tomcat started on port 8080 (http) with context path '/'
   ```

4. La API queda disponible en `http://localhost:8080`.

Para compilar sin ejecutar las pruebas (útil si la base de datos está apagada):

```bash
mvnw.cmd clean install -DskipTests
```

> **Error frecuente:** `Port 8080 was already in use` indica que otra instancia de la API sigue en ejecución. Deténgala o cambie `server.port`.

---

## Estructura del proyecto

```
com.example.api
├── controller   ProductController, UsuarioController
├── service      ProductService, UsuarioService
├── repository   ProductRepository, UsuarioRepository
├── model        Product, ProductRequest, ProductResponse,
│                Usuario, UsuarioRequest, UsuarioResponse
└── utils        ProductNotFoundException, UsuarioNotFoundException,
                 GlobalExceptionHandler
```

---

## Endpoints

### Módulo de Productos

| Método | Ruta | Descripción | Cuerpo de la petición (ejemplo) | Respuesta (ejemplo) |
|---|---|---|---|---|
| GET | `/api/productos` | Lista todos los productos | — | `200` `[{"id":1,"name":"Teclado mecánico","price":189900.00}]` |
| GET | `/api/productos/{id}` | Consulta un producto por id | — | `200` `{"id":1,"name":"Teclado mecánico","price":189900.00}` · `404` `{"error":"No encontramos ese ID"}` |
| POST | `/api/productos` | Crea un producto | `{"name":"Teclado mecánico","price":189900}` | `201` `{"id":1,"name":"Teclado mecánico","price":189900.00}` |
| PUT | `/api/productos/{id}` | Actualiza un producto | `{"name":"Teclado RGB","price":210000}` | `200` `{"id":1,"name":"Teclado RGB","price":210000.00}` · `404` |
| DELETE | `/api/productos/{id}` | Elimina un producto | — | `204` sin contenido · `404` |

### Módulo de Usuarios

| Método | Ruta | Descripción | Cuerpo de la petición (ejemplo) | Respuesta (ejemplo) |
|---|---|---|---|---|
| GET | `/api/usuarios` | Lista todos los usuarios | — | `200` `[{"id":1,"nombre":"Ana Pérez","correo":"ana@correo.com","rol":"ADMIN"}]` |
| GET | `/api/usuarios/{id}` | Consulta un usuario por id | — | `200` `{"id":1,"nombre":"Ana Pérez","correo":"ana@correo.com","rol":"ADMIN"}` · `404` `{"error":"No encontramos ese ID"}` |
| POST | `/api/usuarios` | Crea un usuario | `{"nombre":"Ana Pérez","correo":"ana@correo.com","contrasena":"clave123","rol":"ADMIN"}` | `201` `{"id":1,"nombre":"Ana Pérez","correo":"ana@correo.com","rol":"ADMIN"}` · `400` si el correo ya existe |
| PUT | `/api/usuarios/{id}` | Actualiza un usuario | `{"nombre":"Ana Gómez","correo":"ana@correo.com","contrasena":"nuevaClave789","rol":"ADMIN"}` | `200` `{"id":1,"nombre":"Ana Gómez","correo":"ana@correo.com","rol":"ADMIN"}` · `404` · `400` |
| DELETE | `/api/usuarios/{id}` | Elimina un usuario | — | `204` sin contenido · `404` |

> Las peticiones con cuerpo (POST y PUT) deben enviarse con `Content-Type: application/json`.

### Ejemplos con cURL

**Crear un producto**

```bash
curl -X POST http://localhost:8080/api/productos \
  -H "Content-Type: application/json" \
  -d '{"name":"Teclado mecánico","price":189900}'
```

Respuesta `201 Created`:

```json
{
  "id": 1,
  "name": "Teclado mecánico",
  "price": 189900.00
}
```

**Crear un usuario**

```bash
curl -X POST http://localhost:8080/api/usuarios \
  -H "Content-Type: application/json" \
  -d '{"nombre":"Ana Pérez","correo":"ana@correo.com","contrasena":"clave123","rol":"ADMIN"}'
```

Respuesta `201 Created` (observe que no incluye la contraseña):

```json
{
  "id": 1,
  "nombre": "Ana Pérez",
  "correo": "ana@correo.com",
  "rol": "ADMIN"
}
```

### Validaciones y errores

Todos los errores se devuelven en formato JSON con la clave `error`.

| Situación | Código | Respuesta de ejemplo |
|---|---|---|
| Id inexistente (GET, PUT o DELETE) | `404` | `{"error":"No encontramos ese ID"}` |
| Correo duplicado al crear o actualizar un usuario | `400` | `{"error":"Ya existe un usuario con el correo ana@correo.com"}` |
| Precio negativo o cero | `400` | `{"error":"price: El precio debe ser mayor que cero"}` |
| Nombre vacío | `400` | `{"error":"name: El nombre es obligatorio"}` |
| Correo con formato inválido | `400` | `{"error":"correo: El correo no tiene un formato válido"}` |
| Contraseña de menos de 6 caracteres | `400` | `{"error":"contrasena: La contraseña debe tener al menos 6 caracteres"}` |

---

## Pruebas con Postman

La colección **API Tienda** (archivo `API_Tienda.postman_collection.json`) contiene las carpetas **Productos** y **Usuarios** con las peticiones de la matriz de pruebas:

1. Importe el archivo en Postman (**Import**).
2. Verifique que la API esté en ejecución en `http://localhost:8080`.
3. Ejecute las peticiones en orden, ya que algunas dependen de los datos creados por las anteriores.

---

## Autor

**German Estevan Moreno Rojas**
Tecnología en Desarrollo de Software – Corporación Universitaria Minuto de Dios
Rectoría Oriente – Sede / Centro Tutorial Villavicencio (Meta) – NRC 75638
Docente: Brayan Camilo Clavijo Gomez
Octubre de 2026
