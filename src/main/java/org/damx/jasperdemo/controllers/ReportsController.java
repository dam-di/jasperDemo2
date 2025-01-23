package org.damx.jasperdemo.controllers;

import net.sf.jasperreports.engine.JRException;
import org.damx.jasperdemo.services.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

@RestController
// Indica que esta clase es un controlador REST, lo que significa que maneja solicitudes HTTP y devuelve respuestas.
@RequestMapping("/report") // Define la ruta base para todas las solicitudes manejadas por este controlador.

public class ReportsController {

    @Autowired // Inyecta automáticamente la dependencia del servicio de reportes (ReportService).
    private ReportService reportService;

    // Define un endpoint GET para obtener un informe.
    @GetMapping("/getReport") // La URL completa será "/report/getReport".
    public ResponseEntity<byte[]> getEvents() {
        System.out.println("Obteniendo informe"); // Mensaje en consola para indicar que se está procesando la solicitud.

        try {
            // Llama al servicio para generar el informe y lo almacena como un array de bytes.
            byte[] report = reportService.generarReport("PersonasReport");

            // Crea encabezados HTTP para especificar que la respuesta será un archivo PDF.
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF); // Indica que el contenido es un PDF.
            headers.add("Content-Disposition", "inline; filename=report.pdf"); // Especifica cómo se debe mostrar el archivo (en línea).

            // Retorna el informe con los encabezados y un código de estado HTTP 200 (OK).
            return new ResponseEntity<>(report, headers, HttpStatus.OK);

        } catch (JRException e) {
            // Maneja excepciones relacionadas con JasperReports.
            System.out.println(e.getMessage()); // Muestra el mensaje de error en consola.

        } catch (FileNotFoundException e) {
            // Maneja excepciones cuando no se encuentra el archivo del informe.
            System.out.println(e.getMessage()); // Muestra el mensaje de error en consola.

        } catch (Exception e) {
            // Maneja cualquier otra excepción no anticipada.
            throw new RuntimeException(e); // Lanza una excepción para que sea manejada por el framework.
        }

        // En caso de error, retorna una respuesta con estado 500 (Error interno del servidor) y un cuerpo nulo.
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    // Define un endpoint GET para obtener un informe.
    @GetMapping("/getReport2/{email_filter}") // La URL completa será "/report/getReport".
    public ResponseEntity<byte[]> getReport2(@PathVariable String email_filter) {
        System.out.println("Obteniendo informe2"); // Mensaje en consola para indicar que se está procesando la solicitud.

        try {
            // Llama al servicio para generar el informe y lo almacena como un array de bytes.
            byte[] report = reportService.generarReport2("PersonasReportNew", email_filter);

            // Crea encabezados HTTP para especificar que la respuesta será un archivo PDF.
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF); // Indica que el contenido es un PDF.
            headers.add("Content-Disposition", "inline; filename=report.pdf"); // Especifica cómo se debe mostrar el archivo (en línea).

            // Retorna el informe con los encabezados y un código de estado HTTP 200 (OK).
            return new ResponseEntity<>(report, headers, HttpStatus.OK);

        } catch (JRException e) {
            // Maneja excepciones relacionadas con JasperReports.
            System.out.println(e.getMessage()); // Muestra el mensaje de error en consola.

        } catch (FileNotFoundException e) {
            // Maneja excepciones cuando no se encuentra el archivo del informe.
            System.out.println(e.getMessage()); // Muestra el mensaje de error en consola.

        } catch (Exception e) {
            // Maneja cualquier otra excepción no anticipada.
            throw new RuntimeException(e); // Lanza una excepción para que sea manejada por el framework.
        }

        // En caso de error, retorna una respuesta con estado 500 (Error interno del servidor) y un cuerpo nulo.
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    // Define un endpoint GET para obtener un informe.
    @GetMapping("/getReport3/{id_persona}") // La URL completa será "/report/getReport".
    public ResponseEntity<byte[]> getReport3(@PathVariable int id_persona) {
        System.out.println("Obteniendo informe actividades"); // Mensaje en consola para indicar que se está procesando la solicitud.

        try {
            // Llama al servicio para generar el informe y lo almacena como un array de bytes.
            byte[] report = reportService.generarActividesReport("Cherry", id_persona);
            if(report != null){
                // Crea encabezados HTTP para especificar que la respuesta será un archivo PDF.
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_PDF); // Indica que el contenido es un PDF.
                headers.add("Content-Disposition", "inline; filename=report.pdf"); // Especifica cómo se debe mostrar el archivo (en línea).

                // Retorna el informe con los encabezados y un código de estado HTTP 200 (OK).
                return new ResponseEntity<>(report, headers, HttpStatus.OK);
            }
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG); // Indica que el contenido es un PDF.
            headers.add("Content-Disposition", "inline; filename=imagen.jpg"); // Especifica cómo se debe mostrar el archivo (en línea).
            File fi = new File("src/main/resources/images/chulo.jpg");
            byte[] fileContent = Files.readAllBytes(fi.toPath());
            return new ResponseEntity<>(fileContent, headers, HttpStatus.OK);


        } catch (JRException e) {
            // Maneja excepciones relacionadas con JasperReports.
            System.out.println(e.getMessage()); // Muestra el mensaje de error en consola.

        } catch (FileNotFoundException e) {
            // Maneja excepciones cuando no se encuentra el archivo del informe.
            System.out.println(e.getMessage()); // Muestra el mensaje de error en consola.

        } catch (Exception e) {
            // Maneja cualquier otra excepción no anticipada.
            throw new RuntimeException(e); // Lanza una excepción para que sea manejada por el framework.
        }

        // En caso de error, retorna una respuesta con estado 500 (Error interno del servidor) y un cuerpo nulo.
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    // Define un endpoint GET para obtener un informe.
    @GetMapping("/gastos/persona/{id_persona}") // La URL completa será "/report/getReport".
    public ResponseEntity<byte[]> getGastos(@PathVariable long id_persona) {
        System.out.println("Obteniendo informe gastos"); // Mensaje en consola para indicar que se está procesando la solicitud.

        try {
            // Mapa para almacenar parámetros que se pasarán al informe
            Map<String, Object> parms = new HashMap<>();
            // Ejemplo de parámetro que se puede personalizar según la necesidad
            parms.put("id_persona", id_persona);
            // Llama al servicio para generar el informe y lo almacena como un array de bytes.
            byte[] report = reportService.generarReport("GastosReport1", parms);
            if(report != null){
                // Crea encabezados HTTP para especificar que la respuesta será un archivo PDF.
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_PDF); // Indica que el contenido es un PDF.
                headers.add("Content-Disposition", "inline; filename=report.pdf"); // Especifica cómo se debe mostrar el archivo (en línea).

                // Retorna el informe con los encabezados y un código de estado HTTP 200 (OK).
                return new ResponseEntity<>(report, headers, HttpStatus.OK);
            }
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG); // Indica que el contenido es un PDF.
            headers.add("Content-Disposition", "inline; filename=imagen.jpg"); // Especifica cómo se debe mostrar el archivo (en línea).
            File fi = new File("src/main/resources/images/chulo.jpg");
            byte[] fileContent = Files.readAllBytes(fi.toPath());
            return new ResponseEntity<>(fileContent, headers, HttpStatus.OK);


        } catch (JRException e) {
            // Maneja excepciones relacionadas con JasperReports.
            System.out.println(e.getMessage()); // Muestra el mensaje de error en consola.

        } catch (FileNotFoundException e) {
            // Maneja excepciones cuando no se encuentra el archivo del informe.
            System.out.println(e.getMessage()); // Muestra el mensaje de error en consola.

        } catch (Exception e) {
            // Maneja cualquier otra excepción no anticipada.
            throw new RuntimeException(e); // Lanza una excepción para que sea manejada por el framework.
        }

        // En caso de error, retorna una respuesta con estado 500 (Error interno del servidor) y un cuerpo nulo.
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }


    // Define un endpoint GET para obtener un informe.
    @GetMapping("/gastos/persona/{id_persona}/{total_filter}") // La URL completa será "/report/getReport".
    public ResponseEntity<byte[]> getGastos2(@PathVariable long id_persona, @PathVariable BigDecimal total_filter) {
        System.out.println("Obteniendo informe gastos"); // Mensaje en consola para indicar que se está procesando la solicitud.

        try {
            // Mapa para almacenar parámetros que se pasarán al informe
            Map<String, Object> parms = new HashMap<>();
            // Ejemplo de parámetro que se puede personalizar según la necesidad
            parms.put("id_persona", id_persona);
            parms.put("total_filter", total_filter);
            // Llama al servicio para generar el informe y lo almacena como un array de bytes.
            byte[] report = reportService.generarReport("GastosReport2", parms);
            if(report != null){
                // Crea encabezados HTTP para especificar que la respuesta será un archivo PDF.
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_PDF); // Indica que el contenido es un PDF.
                headers.add("Content-Disposition", "inline; filename=report.pdf"); // Especifica cómo se debe mostrar el archivo (en línea).

                // Retorna el informe con los encabezados y un código de estado HTTP 200 (OK).
                return new ResponseEntity<>(report, headers, HttpStatus.OK);
            }
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG); // Indica que el contenido es un PDF.
            headers.add("Content-Disposition", "inline; filename=imagen.jpg"); // Especifica cómo se debe mostrar el archivo (en línea).
            File fi = new File("src/main/resources/images/chulo.jpg");
            byte[] fileContent = Files.readAllBytes(fi.toPath());
            return new ResponseEntity<>(fileContent, headers, HttpStatus.OK);


        } catch (JRException e) {
            // Maneja excepciones relacionadas con JasperReports.
            System.out.println(e.getMessage()); // Muestra el mensaje de error en consola.

        } catch (FileNotFoundException e) {
            // Maneja excepciones cuando no se encuentra el archivo del informe.
            System.out.println(e.getMessage()); // Muestra el mensaje de error en consola.

        } catch (Exception e) {
            // Maneja cualquier otra excepción no anticipada.
            throw new RuntimeException(e); // Lanza una excepción para que sea manejada por el framework.
        }

        // En caso de error, retorna una respuesta con estado 500 (Error interno del servidor) y un cuerpo nulo.
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }


}
