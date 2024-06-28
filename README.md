# TomCatReport
--JAVA--
Este proyecto fue creado en NetBeans 12.0, utilizando Java Web Application y un servidor Apache Tomcat/9.0.88 para su ejecución.

El proyecto se compone de tres carpetas principales:

1. Web Pages: Actualmente, solo se utiliza para el saludo inicial.

2. Source Packages: Contiene el paquete principal del proyecto, que incluye:

* GenerarInformeServlet.java: Este archivo contiene todo el código necesario para ejecutar reportes Jasper en la web, utilizando parámetros dinámicos.

Un ejemplo de como se debe componer la url y el envio de parametros es el siguiente; 
http://localhost:8080/TomCatReport/?reporte=R_Arqueo.jasper&usuario=JOSEGUERA&moneda=1

Paso 1: Este bloque sera obligatorio "http://localhost:8080/TomCatReport/?reporte="
seguidamente el nombre del reporte a ejecutar, notar que se coloca el nombre del reporte y la extension "R_Arqueo.jasper"

Paso 2: Componer la cadena (&usuario es el parametro) = (JOSEGUERA es el valor del parametro)
- Para este caso el proyecto se trabajo solo con variables tipo string ya sea un nombre de cliente, usuario etc.
  o tambien un valor numerico como el ejemplo mostrado "&moneda=1"
Nota para este ejemplo no se ha contenplado o probado datos de fecha, ya que para el proyecto requerido no fue necesario, de querer utilizarse habria que buscar el modo de convertir las fechas a string en el reporte y pasarle datos string o modificar el codigo fuente.


* Test.jasper y Test.jrxml: Son reportes de prueba que sirven para identificar la ubicación correcta donde se deben colocar los demás reportes necesarios. Una vez que el proyecto se compila en un archivo .war, este genera un repositorio donde se colocan la página web, los reportes y las librerías.

* config.properties: Este archivo es crucial para la conexión a la base de datos. El archivo "GenerarInformeServlet.java" lo utiliza para que las variables sean dinámicas, permitiendo cambiar los valores sin necesidad de recompilar el proyecto.

3. Libraries: Aquí se almacenan las librerías necesarias para la generación de archivos Jasper en el navegador web.


--TOMCAT--
Cuando tegamos el proyecto compliado nos quedara similar a "TomCatReport.war"
Este archivo debe ser copiado en la carpeta  de instalacion de tomcat en mi caso es este de forma local,
"C:\tomcat\apache-tomcat-9.0.88\webapps\" despues de copiar debemos reiniciar el servidor para que ejecute el archivo y nos cree el repositorio en la misma carpeta bajo el mismo nombre.

Creado el repositorio la carpeta mas importante sera "WEB-INF"
"A:\Tomcat 9.0\webapps\TomCatReport\WEB-INF"

- Aqui tendremos 2 carpetas importantes 
1. clasess 
	En esta ubicacion estaran los archivos de reporte jasper y los archivos del proyecto y su conexion a la base de datos que utiliza cada uno de los reportes.
2. lib 
	Las librerias necesarias estaran en esta carpeta.
