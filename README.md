# Titulo
API RESTful de creación de usuarios

![alt text](docs/crea-usuario.png)

![Badge en Desarollo](https://img.shields.io/badge/STATUS-EN%20DESAROLLO-green)

# Descripción
Endpoint Rest de modificación tal que, dado los datos de un usuario, lo crea en la base de datos. retornando,
información del o los registros creados y el estatus adecuado.

# Estado
En contrucción.

# Diagrama de la Solución

## Backend for frontend - ms-creacion-usuario-core
Esta basado en el principio de Clean Architecture, con una arquitectura hexagonal.

![alt text](docs/clean.png)

### Estructura de paquetes

Se definió la siguiente taxonomía de paquetes:

* **application:** Encapsula toda la lógica de negocio y el modelo del dominio.
  * **domain:** Contiene entidades del dominio. Representa el nucleo de toda la aplicación.
  * **usescases:** Abstracción de los casos de uso del sistema. Contiene además la definición de los puertos y excepciones.
* **adapters:** Representa la capa de adaptadores (como su nombre indica) que se conectarán en los puertos expuestos por el sistema
* **config:** Capa transversal a toda la aplicación que contendrá las distintas configuraciones y aspectos del bff.

![alt text](docs/packages.png)

## Modelo de Dominio

![alt text](docs/diagrama-clases.png)

# Tecnologías Utilizadas

## Java Version
La version que java que se va a utilizar es la 11.0.5 basada en el OpenJDK.

Esta la pueden descargar [aqui](https://github.com/AdoptOpenJDK/openjdk11-upstream-binaries/releases/tag/jdk-11.0.5%2B10).
También se puede instalar usando [SdkMan](https://sdkman.io/), version 11.0.5-open.

## Springboot Version
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-2.7.1-brightgreen)

## Base de Datos
Se usa Base De datos en Memoria H2. Sin embargo, tambien se probó el funcionamiento sin dificultad en una Base de Datos
PostgreSQL

## Properties
Es muy importante revisar el archivo bootstrap.properties. Alli, estan las propiedades requeridas para levantar la
aplicación.

## Swagger
Suponiendo que nuestra aplicación se ejecuta en el puerto 8080 tendremos:

### OpenApi Json
http://localhost:8080/_ms-creacion-usuario-core/openapi
### Swagger UI
http://localhost:8080/_ms-creacion-usuario-core/swagger-ui.html

# Instrucciones Para ejecución en Ambiente local

1.) Clonar via git desde el repositorio hacia la máquina local. \
2.) Abrir con el IDE favorito, recomendable IntelliJ. Al abrir, esperar que realice la indexación. \
3.) Revisar el archivo bootstrap.properties. Alli, ajustar los properties que considere necesarios. \
4.) En el panel de gradle, desde el IDE, ejecutar bootRun de la carpeta application. \
![alt text](docs/gradle.png) \
5.) Realizar las pruebas desde postman o via cualquier CURL.

### CURL
La siguiente es un curl de ejemplo para utilizar la API, suponiendo que el contexto es localhost:8080:

curl --location 'http://localhost:8080/api/v1.0/cr/user/create' \
--header 'Content-Type: application/json' \
--data-raw ' { \
  "name": "Juan Rodriguez", \
  "email": "juan5@rodriguez.org", \
  "password": "hunter2", \
  "phones": [ \
    { \
      "number": "1234567", \
      "citycode": "1", \
      "countrycode": "57" \
    } \
  ] \
}'

Aunque no forma parte del alcance, se creó una operación consultar. Que es util para ver el funcionamiento de la
seguridad basada en JWT con Spring Security. Este CURL es el siguiente:

curl --location 'http://localhost:8080/api/v1.0/cr/user/get' \
--header 'user-email: juan5@rodriguez.org' \
--header 'Authorization: Bearer token_jwt_obtenido_al_crear_usuario'

### Cobertura
Se puede hacer analisis de la cobertura con jacocoTest. Para ello, ejecutar los tests y luego desde el panel de grandle ir a carpeta verification y ejecutar jacocoTestReport.
luego, el reporte puede ser encontrado dentro del proyecto en build/reports/jacoco/test/html/index.html

# Información Adicional
Recordar que no se requiere Base de Datos en la Máquina Local ya que se utiliza H2. No obstante, Dentro de la carpeta 
resources del proyecto se encuentra un script.sql por si se desea utilizar en postgreSQL. Tambien, está unacolección 
llamada cr.postman_collection.json que se puede importar en postman y tiene los curls necesarios para probar.
# Autor
[<img src="https://avatars.githubusercontent.com/u/37299779?s=400&u=e40bfe01a10c844ef8348b57774ffdb872a7d15a&v=4" width=115><br><sub>Juan Salas</sub>](https://github.com/jsalas87)