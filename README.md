![G1 banner](https://smlab.imd.ufrn.br/wp-content/uploads/2022/12/FIWARE.png)

Web: [Identity Manager
](http://46.17.108.45:3000/)

# FIWARE Keyrock [Identity Manager]
A continuación explicaremos brevemente la integracion con FIWARE a traves de __Keyrock__ , un habilitador de identidades que nos permite autenticarnos en la api. Explicaremos tanto la funcionalidad, cómo tambien la creación de usuarios, organizaciones y asignaciones de roles.

__Integrantes__\
Fernando Valls
Ezequiel Cherone
Moises Fuks
Juan Manuel Campagna
Joaquin Charovsky
Elyelin Carrasquero 

__Roles__
- Fernando Valls: 

    Mobile - configuracion endPoints - arquitectura de vistas - crud organizaciones / roles - logout

- Ezequiel Cherone: 

    Mobile - configuración nav lateral - login - crud usuarios / dockerizacion y montaje servidor

- Moises Fuks 
    
    Mobile - login - huella biometrica - crud user

- Juan Manuel Campagna 

    Mobile - crud organizaciones / roles

- Joaquin Charovsky 

    Front - login - endPoints - crud usuario - crud organizaciones

- Elyelin Carrasquero 

    Mobile - styles - testing - docuemntacion - manuales


__Funcionalidad__\

Nuesta app comienza con la pantalla de login, el cual previamente deberá haberse creado un usuario en la pagina de keyrock y debe ser habilitado por un administrador.
Luego del login dependiendo de los permisos que tenga el usaurio verá las vistas permitidas (hay restricciones para la vista de algunos fragmentos dependiendo del rol que tenga el usuario, si es o no "admin").

Dashboard principal:

- Lista de organizaciones en la cual esta involucrado el usuario

Perfil NO administrado:

- Creación, edición y eliminación de organizaciones (siempre y cuando sea "owner" de esa organización).

Perfil administrado: 

- Creación, edición y eliminación de organizaciones (siempre y cuando sea "owner" de esa organización).

- Creación, edición y eliminación de usuarios.

- Asignacion de usuarios con su correspondiente rol a una organización (siempre y cuando sea "owner" de esa organización).

- Visualización de usuarios y rol correspondientes a la organización seleccionada.

# Consultas
fernandodanielvalls@gmail.com\
campagnajuanmanuel@gmail.com\
chero2005@hotmail.com\
joaquincharovsky@gmail.com\
elyelin15@gmail.com
