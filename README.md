# testNttDataAcounts

## Import the project into Intellij

If you want to import these project into the local eclipse setup -

  1. Download the project as zip file into your computer
  2. Unzip the project at your desired location
  3. Open the project into intellij

## Commands

 CREA LOS TARGET DE PROYETO

    mvn clean install -DskipTests

CREAR EL BUILD DEL DOCKERFILE

    docker build -t mov .

CREAR LA CARPETA PARA LA DATA DE LA BDD

    mkdir -p shared/mysql_data

EJECUTAR EL COMPOSE

    sudo docker-compose up -d

TERMINAR LOS CONTENEDORES

    docker-compose down

## Test API in Postman

Lad file PruebaNttData.postman_collection.json in postman
       
## SWAGGER API

CLIENTES - PERSONA

http://localhost:5000/swagger-ui/index.html

CUENTA MOVIMIENTO

http://localhost:5001/swagger-ui/index.html
