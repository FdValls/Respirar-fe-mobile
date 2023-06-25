![G1 banner](https://smlab.imd.ufrn.br/wp-content/uploads/2022/12/FIWARE.png)

Web: [Identity Manager
](http://46.17.108.45:3000/)

# Instrucciones para levantar el Proyecto con docker-compose

1. Crear el siguiente archivo docker-compose.yml 

```
version: "3.5"
services:
  keyrock:
    image: fiware/idm:7.8.1
    container_name: fiware-keyrock
    hostname: keyrock
    networks:
      default:
        ipv4_address: 172.18.1.5
    depends_on:
      - mysql-db
    ports:
      - "3000:3000"
      - "443:443"
    environment:
      - DEBUG=idm:*
      - IDM_DB_HOST=mysql-db
      - IDM_HOST=http://localhost:3000
      - IDM_PORT=3000
      # Development use only 
      # Use Docker Secrets for Sensitive Data
      - IDM_DB_PASS=secret 
      - IDM_DB_USER=root
      - IDM_ADMIN_USER=admin
      - IDM_ADMIN_EMAIL=admin@test.com
      - IDM_ADMIN_PASS=1234
      # If sending eMails point to any STMP server
      - IDM_EMAIL_HOST=mailer
      - IDM_EMAIL_PORT=25
      - IDM_CORS_ENABLED=true
      - IDM_CORS_EXPOSED_HEADERS=*

  mysql-db:
    restart: always
    image: mysql:5.7
    hostname: mysql-db
    container_name: db-mysql
    expose:
      - "3306"
    ports:
      - "3306:3306"
    networks:
      default:
        ipv4_address: 172.18.1.6
    environment:
      # Development use only 
      # Use Docker Secrets for Sensitive Data
      - "MYSQL_ROOT_PASSWORD=secret"
      - "MYSQL_ROOT_HOST=172.18.1.5"
    volumes:
      - mysql-db:/var/lib/mysql

  # Configure the SMTP settings below as necessary,
  # For example to use Gmail SMTP
  #
  # server address: smtp.gmail.com
  # username: Your Gmail address (for example, example@gmail.com)
  # password: Your Gmail password
  # port (TLS): 587
  # port (SSL): 465
  # TLS/SSL required: Yes

  mailer:
    restart: always
    image: mazdermind/docker-mail-relay
    hostname: mailer
    container_name: mailer
    ports:
      - "25:25"
    environment:
      - SMTP_LOGIN=<login> # Login to connect to the external relay
      - SMTP_PASSWORD=<password> # Password to connect to the external relay
      - EXT_RELAY_HOST=<hostname> # External relay DNS name
      - EXT_RELAY_PORT=25
      - ACCEPTED_NETWORKS=172.18.1.0/24
      - USE_TLS=no


networks:
  default:
    ipam:
      config:
        - subnet: 172.18.1.0/24
volumes:
  mysql-db: ~
```

2. Con el archivo docker-compose.yml ya generado, tendremos que elegir que servicio en la nube o Servidores fisicos vamos a desplegar nuestros contenedores.
Nosotros lo haremos en https://labs.play-with-docker.com/ que genera instancias gratuitas por 4 hs.

3. Dentro de https://labs.play-with-docker.com/ generamos una nueva instancia 

4. Accedemos a la terminal de dicha instancia y creamos el docker-compose.yml.

* Comando: cat >  docker-compose.yml
* Copiamos el codigo antes descripto
* Cerramos y guaramos con ctrl + d

5. Ejecutamos el comando docker-compose up y esperamos a que despliegue los contenedores.

6. Con la url del puerto 3000 que nos brinda el play with docker (Ejemplo: http://ip172-18-0-25-cibl0qssnmng00dloa4g-3000.direct.labs.play-with-docker.com/), nos dirigimos al proyecto de Kotlin y le asigamos a la variable global esta url.

7. La varialbe se encuentra en la capeta data => GlobalVariables y se llama url.

8. Con estos pasos ya tenemos el Proyecto corriendo en la nube y nuestra apk consumiendo del backend de Keyrock.

# FIWARE Keyrock [Identity Manager]
A continuación explicaremos brevemente la integracion con FIWARE a traves de __Keyrock__ , un habilitador de identidades que nos permite autenticarnos en la api. Explicaremos tanto la funcionalidad, cómo tambien la creación de usuarios, organizaciones y asignaciones de roles.

__Integrantes__\
Fernando Valls\
Ezequiel Cherone\
Moises Natan Fuks\
Juan Manuel Campagna\
Joaquin Charovsky\
Elyelin Carrasquero 

__Roles__
- Fernando Valls: 

    Mobile - configuracion endPoints - arquitectura de vistas - crud organizaciones / roles - logout

- Ezequiel Cherone: 

    Mobile - configuración nav lateral - login - crud usuarios / dockerizacion y montaje servidor

- Moises Natan Fuks 
    
    Mobile - login - estilos -  huella biométrica - crud user

- Juan Manuel Campagna 

    Mobile - crud organizaciones / roles

- Joaquin Charovsky 

    Front - login - endPoints - crud usuario - crud organizaciones

- Elyelin Carrasquero 

    Mobile - styles - testing - documentación - manuales


__Funcionalidad__

Nuesta app comienza con la pantalla de login, el cual previamente deberá haberse creado un usuario en la pagina de keyrock y debe ser habilitado por un administrador.
Luego del login dependiendo de los permisos que tenga el usaurio verá las vistas permitidas (hay restricciones para la vista de algunos fragmentos dependiendo del rol que tenga el usuario, si es o no "admin").

Dashboard principal:

- Lista de organizaciones en la cual esta involucrado el usuario

Perfil NO administrador:

- Creación, edición y eliminación de organizaciones (siempre y cuando sea "owner" de esa organización).

Perfil administrador: 

- Creación, edición y eliminación de organizaciones (siempre y cuando sea "owner" de esa organización).

- Creación, edición y eliminación de usuarios.

- Asignación de usuarios con su correspondiente rol a una organización (siempre y cuando sea "owner" de esa organización).

- Visualización de usuarios y rol correspondientes a la organización seleccionada.

# Consultas
fernandodanielvalls@gmail.com\
campagnajuanmanuel@gmail.com\
moshifuks2001@gmail.com
chero2005@hotmail.com\
joaquincharovsky@gmail.com\
elyelin15@gmail.com
