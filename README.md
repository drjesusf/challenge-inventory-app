# challenge-inventory-app

## Integración con Mysql o ConcurrentHashMap como repositorio de la aplicación
En la clase ProductServiceImpl se tiene la siguiente anotación
@Qualifier("mysql")
Si se requiere que la aplicacion use ConcurrentHashMap se tendria que modificar por
@Qualifier("hashmap")

## El swagger se encuentra en el siguiente link
http://localhost:8080/swagger-ui/index.html

## El contrato de Open API se encuentra en la raiz del repositorio
api-docs-swagger.yaml

## El script de la BD en Mysql se encuentra en la raiz del repositorio
script_db.sql

## Configuracion de la BD de Mysql 
Se encuentra en /src/resources/application.properties