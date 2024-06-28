
///////
// Creado 26/04/2024
// por Jonatan Oseguera Alonso - Analista Programador Finsol
// Esta app corre bajo el servicio de "apache-tomcat-9.0.88" para ejecutar reportes jasper
// consume los parametros desde una url (independiente de la cantidad y los nombres de los parametros),
// estos son enviados desde Oracle APEX en este caso app de contingencia.
// la conexion a base de datos se hace mediante archivo config.properties ubicado en "src/main/resources"
//////
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

@WebServlet(name = "GenerarInforme", urlPatterns = {"/"})
public class GenerarInformeServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/pdf");

        Connection conn = null;
        try {

            // Leer la configuración de la conexión desde el archivo properties
            Properties propiedades = new Properties();
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("config.properties");
            propiedades.load(inputStream);

            // Obtener el nombre del reporte, parametro statico esto debido a que siempre debemos saber el nombre del archivo a ajecutar
            String nombreReporte = request.getParameter("reporte");

            // Cargar el archivo .jasper del informe compilado
            InputStream jasperStream = this.getClass().getResourceAsStream(nombreReporte);
            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(jasperStream);

            // Establecer conexión con la base de datos Oracle datos en crudo
            Class.forName("oracle.jdbc.OracleDriver");
            //String url = "jdbc:oracle:thin:@192.168.40.140:1521/xepdb1"; // Ajusta la URL según tu configuración
            // String usuario = "JOSEGUERA"; // Reemplaza con tu nombre de usuario de Oracle
            //String contraseña = "FINSOL2024"; // Reemplaza con tu contraseña de Oracle
            //conn = DriverManager.getConnection(url, usuario, contraseña);

            // Establecer conexión con la base de datos Oracle leyendo archivo properties
            String url = propiedades.getProperty("db.url");
            String user = propiedades.getProperty("db.user");
            String pass = propiedades.getProperty("db.pass");
            conn = DriverManager.getConnection(url, user, pass);

            // Obtener los parámetros del formulario
            //String parameter1 = request.getParameter("id");
            //String parameter2 = request.getParameter("parameter2");
            // Crear un mapa de parámetros para el informe
            Map<String, Object> parametros = new HashMap<>();
            Map<String, String[]> parametrosURL = request.getParameterMap();
            for (String parametro : parametrosURL.keySet()) {
                // Agregar todos los parámetros a la lista de parámetros para el informe
                parametros.put(parametro, parametrosURL.get(parametro)[0]);
            }

            // Compilar y llenar el informe
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, conn);

            // Mostrar el informe en el navegador
            JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            // Cerrar la conexión con la base de datos
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    public String getServletInfo() {
        return "Generador de Informes";
    }

}
