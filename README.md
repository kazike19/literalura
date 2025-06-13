# LiterAlura 📚

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)

![Spring Boot](https://img.shields.io/badge/Spring_Boot-F2F4F9?style=for-the-badge&logo=spring-boot)

![PostgreSQL](https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white)

Una aplicación de consola interactiva construida con **Spring Boot** que permite a los usuarios buscar y consultar información sobre libros y autores utilizando la **API de Gutendex**. Con LiterAlura, puedes explorar una vasta colección de obras literarias, registrar tus favoritos, y obtener estadísticas interesantes directamente desde tu terminal.

---

## 🌟 Características Principales

* **Búsqueda de Libros:** Busca libros por título directamente desde la API de Gutendex.
* **Gestión de Libros:** Registra libros en tu base de datos local para mantener un seguimiento de tus lecturas favoritas.
* **Listado de Libros y Autores:** Explora todos los libros y autores registrados en tu base de datos.
* **Autores Vivos por Año:** Encuentra autores que estaban vivos en un año específico.
* **Libros por Idioma:** Lista y filtra libros según el idioma.
* **Estadísticas de Idioma:** Consulta la cantidad de libros registrados en un idioma determinado.
* **Top 10 de Descargas:** Descubre los libros más populares por su número de descargas almacenados.
* **Búsqueda de Libros por Autor:** Encuentra todos los libros asociados a un autor específico en tu base de datos.
* **Estadísticas Avanzadas de Descargas:** Obtén un resumen estadístico (conteo, suma, promedio, mínimo, máximo) del número de descargas para libros en un idioma particular.

---

## 🛠️ Tecnologías Utilizadas

* **Java 17+:** Lenguaje de programación principal.
* **Spring Boot 3.x:** Framework para el desarrollo de la aplicación.
* **Spring Data JPA:** Para la persistencia de datos y la interacción con la base de datos.
* **PostgreSQL:** Base de datos relacional para almacenar libros y autores.
* **Hibernate:** Implementación de JPA.
* **Jackson:** Librería para el manejo de JSON (serialización/deserialización) con la API de Gutendex.
* **Maven:** Herramienta de gestión de proyectos y dependencias.
---

## 🚀 Cómo Empezar

Sigue estos pasos para configurar y ejecutar el proyecto en tu máquina local.

### **Requisitos Previos**

Asegúrate de tener instalado lo siguiente:

* **Java Development Kit (JDK) 17 o superior**
* **Apache Maven**
* **PostgreSQL** (un cliente como `pgAdmin` es recomendable)

### **Configuración de la Base de Datos**

1.  **Crea una base de datos** en PostgreSQL (por ejemplo, `literalura_db`).
2.  **Configura las credenciales** de tu base de datos en el archivo `src/main/resources/application.properties`:

    ```properties
    spring.datasource.url=jdbc:postgresql://${DB_HOST}/${DB_NOMBRE}
    spring.datasource.username=tu_usuario_postgresql
    spring.datasource.password=tu_contraseña_postgresql
    spring.jpa.hibernate.ddl-auto=update # Permite a Hibernate crear y actualizar las tablas automáticamente
    spring.jpa.show-sql=true # Muestra las sentencias SQL en la consola
    spring.jpa.properties.hibernate.format_sql=true # Formatea el SQL para mejor legibilidad
    ```

    > ### Nota: Realizar esto una vez clonado el repositorio

## Instalación y Ejecución

### 1. **Clonar el Repositorio**

```bash
git clone https://github.com/kazike19/literalura.git
cd LiterAlura
```

### 2. **Construir el Proyecto**
```bash
mvn clean install
```

Este comando descargará las dependencias necesarias y construirá el proyecto.

### 3. **Ejecutar la Aplicación**

```bash
mvn spring-boot:run
```

Una vez que la aplicación se inicie (verás logs de Spring Boot), el menú principal de LiterAlura aparecerá en la consola, listo para que interactúes con él.

## 📖 Uso

Puedes ver una captura de pantalla del menú en el archivo `Menu.png` que se encuentra en la raíz del repositorio.

![Menu](https://github.com/kazike19/literalura/tree/master/img))

**Elige una opción:**

* **Opciones 1:** Buscar Libro por titulo 
> Para buscar un libro dentro del Gutendex y guardarlo en la base de datos.
* **Opciones 2:** Listar libros registrados
> Para ver todos los libros guardados en la base de datos.
* **Opciones 3:** Listar autores registrados
> Para ver todos los autores guardados en la base de datos.
* **Opciones 4:** Listar autores vivos en un determinado año
> Para ver todos los autores vivos en un determinado año guardados en la base de datos.
* **Opciones 5:** Listar libros por idioma
> Para ver todos los libros guardados en un idioma especifico en la base de datos.
* **Opciones 6:** Mostrar el numero de libros de un idioma
> Para saber el numero de libros en un idioma guardados en la base de datos.
* **Opciones 7:** Top 10 de descargas
> Para saber el top 10 de los libros guardados rankeados por su numero de descargas.
* **Opciones 8:** Buscar libro por autor en la base de datos
> Para buscar un libro por el nombre de su autor guardado en la base de datos.
* **Opciones 9:** Estadisticas de un idioma
> Para saber las estadisticas de todos los libros en un idioma especifico.

* **Opción 0: Salir:** Cierra la aplicación.


## Estructura del Proyecto
```
├── README.md
├── src/
│   ├── models/
│   │   ├── Autor.java
│   │   ├── DatosAutor.java
│   │   ├── Datos.java
│   │   ├── DatosLibros.java
│   │   ├── Idioma.java
│   │   └── Libro.java
│   ├── principal/
│   │   └── Principal.java
│   ├── repositories/
│   │   ├── AutorRepository.java
│   │   └── LibroRepository.java
│   ├── services/
│   │   ├── AutorService.java
│   │   ├── ConsumoAPI.java
│   │   ├── ConvierteDatos.java
│   │   ├── IConvierteDatos.java
│   │   ├── LibroService.java
│   │   └── MostrarMenu.java
└── LiteraaluraApplication.java
```
## Demostración en Video

Puedes ver una breve demostración del funcionamiento de la aplicación en el siguiente enlace:

[Demostración de LiterAlura](https://drive.google.com/file/d/1qaZwId8RD0A_kO2yqBS81QoeC25WPpB2/view?usp=drive_link)

