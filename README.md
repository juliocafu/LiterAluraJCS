# LiterAlura (Proyecto Java Spring - Persistencia en PostgreSQL)

![Java Spring + PostgreSQL](https://cdn.jsdelivr.net/gh/devicons/devicon/icons/spring/spring-original-wordmark.svg)

Este proyecto es una aplicación Java Spring que utiliza persistencia de datos con **PostgreSQL** y permite la búsqueda y registro de libros desde la API pública de [Gutendex](https://gutendex.com). El objetivo principal es gestionar libros y autores mediante un menú interactivo que conecta a una base de datos relacional llamada **literalura**.

---

## Características principales

1. **Conexión a API externa (Gutendex):**
   - Permite buscar libros por título en la API y registrar los datos relevantes en la base de datos.
2. **Base de datos relacional:**
   - Dos tablas principales: **Libro** y **Autor**, relacionadas por un identificador único (ID).
3. **Menú interactivo:**
   - Ofrece múltiples opciones para gestionar y consultar libros y autores registrados.
4. **Validaciones robustas:**
   - Previene la duplicación de registros y muestra mensajes significativos en caso de errores.

---

## Menú principal

Al ejecutar la aplicación, se mostrará el siguiente menú interactivo:

```
Elige una opción a través de su número:
1.- Buscar libro por título
2.- Listar libros registrados
3.- Listar autores registrados
4.- Listar autores vivos en un determinado año
5.- Listar libros por idioma

0.- Salida
```

### Detalles de cada opción

#### 1. Buscar libro por título
- Se conecta a la API de Gutendex para buscar libros por título.
- Si se encuentra el libro, se registra en la base de datos con los siguientes atributos:
  - **Libro:** `titulo`, `autor`, `idioma`, `numeroDeDescargas`
  - **Autor:** `nombre`, `fechaNacimiento`, `fechaMuerte`
- Validaciones:
  - Si el libro no se encuentra en la API: muestra el mensaje `"Libro no encontrado"`.
  - Si el libro ya está registrado en la base de datos: muestra el mensaje `"Libro registrado en la base de datos"`.

#### 2. Listar libros registrados
- Muestra todos los libros almacenados en la base de datos con los siguientes campos:
  - `titulo`, `autor`, `idioma`, `numeroDeDescargas`

#### 3. Listar autores registrados
- Lista los autores registrados junto con sus características:
  - `nombre`, `fechaNacimiento`, `fechaMuerte`
- Considera que un libro posee **solo un autor** para simplificar la relación en la base de datos.

#### 4. Listar autores vivos en un determinado año
- Permite al usuario buscar autores vivos en un año específico.
  - `nombre`, `fechaNacimiento`, `fechaMuerte`, `libros`
- La lista de libros asociados al autor se muestra con un salto de línea entre cada registro.

#### 5. Listar libros por idioma
- Muestra un submenú con idiomas disponibles (abreviaturas y nombres completos):
  - `en - Inglés`, `es - Español`, `fr - Francés`, `pt - Portugués`, etc.
- Permite seleccionar un idioma por su abreviatura y lista todos los libros registrados en ese idioma, mostrando:
  - `titulo`, `autor`, `idioma`, `numeroDeDescargas`

#### 0. Salida
- Finaliza la ejecución de la aplicación.

---

## Diseño de la base de datos

### Tablas principales

1. **Tabla `Libro`:**
   - **ID:** Identificador único del libro.
   - **Titulo:** Nombre del libro.
   - **Autor:** Relación con la tabla `Autor`.
   - **Idioma:** Idioma del libro (e.g., `es`, `en`).
   - **NumeroDeDescargas:** Número total de descargas.

2. **Tabla `Autor`:**
   - **ID:** Identificador único del autor.
   - **NombreAutor:** Nombre del autor.
   - **FechaNacimiento:** Año de nacimiento del autor.
   - **FechaMuerte:** Año de fallecimiento (si aplica).

---

## Validaciones implementadas

- **Errores en la búsqueda de libros:**
  - Si no se encuentra el libro en la API, se devuelve `"Libro no encontrado"`.
- **Duplicación de registros:**
  - Si un libro ya está registrado en la base de datos, se muestra `"Libro registrado en la base de datos"` y no se permite la duplicación.

---

## Requisitos para ejecutar el proyecto

1. **Java Development Kit (JDK):**
   - Versión 17 o superior.
2. **Spring Boot:**
   - Framework para el desarrollo del backend.
3. **Base de datos PostgreSQL:**
   - Base de datos relacional para persistir los datos.
4. **Dependencias principales:**
   - Spring Data JPA
   - Spring Web
   - PostgreSQL Driver

---

## Ejecución del proyecto

1. **Configurar la base de datos:**
   - Crear una base de datos llamada `literalura` en PostgreSQL.
   - Configurar las credenciales en el archivo `application.properties` o `application.yml`:
     ```properties
     spring.datasource.url=jdbc:postgresql://localhost:8080/literalura
     spring.datasource.username=tu_usuario
     spring.datasource.password=tu_contraseña
     spring.jpa.hibernate.ddl-auto=update
     spring.jpa.show-sql=true
     ```

2. **Compilar y ejecutar el proyecto:**
   - Usar **Maven** o **Gradle** para compilar y ejecutar el proyecto.
   - Ejemplo con Maven:
     ```bash
     mvn spring-boot:run
     ```

---

## Imagen del Proyecto

![Menú Interactivo](https://cdn.jsdelivr.net/gh/devicons/devicon/icons/postgresql/postgresql-original-wordmark.svg)

---

## Autor Julio Santos

Este proyecto fue desarrollado como parte de un desafío técnico para gestionar libros y autores mediante Java Spring y PostgreSQL.

