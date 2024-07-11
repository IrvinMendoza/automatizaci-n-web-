package util;

import au.com.bytecode.opencsv.CSVReader;
import modal.EscenarioDataCsv;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.*;

public class MetodosComunesCsv {

    private MetodosComunesCsv() {
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(MetodosComunesCsv.class);
    private static String[] fila;

    private static void getIdCasoPrueba(String idCaso) {
        String rutaArchivoCsv = "src/test/resources/Datadriven/CsvApisBanbif.csv";
        try {
            CSVReader reader = new CSVReader(new BufferedReader(new InputStreamReader(new FileInputStream(rutaArchivoCsv), "UTF-8")));
            List<String[]> filasCsv = reader.readAll();

            for (String[] fil : filasCsv) {
                boolean bandera = fil[1].trim().equalsIgnoreCase(idCaso);
                if (bandera) {
                    fila = fil;
                    break;
                }
            }
        } catch (IOException e) {
            LOGGER.error("ERROR EN LECTURA DE ARCHIVO CSV", e);
            Assert.fail("ERROR EN LECTURA DE ARCHIVO CSV");
        } catch (IndexOutOfBoundsException e) {
            Assert.fail("ID DE CASO INGRESADO NO EXISTE");
        }
    }

    private static String getUrl() {

        String endpoint = fila[2];
        String parametros = getNombreParametros();
        if (!parametros.equalsIgnoreCase("")) {
            endpoint = endpoint.concat("?").concat(parametros);
        }
        return endpoint;
    }

    private static String getNombreParametros() {

        String respuestaConParametros = "";
        String nombresParametros = fila[8];

        if (!nombresParametros.equalsIgnoreCase("no tiene parametros")) {

            String valoresParametros = fila[9];
            Object[] parametros = valoresParametros.split("#");
            respuestaConParametros = String.format(nombresParametros, parametros);
        }
        return respuestaConParametros;
    }

    public static Map<String, String> getHeaders() {

        HashMap<String, String> headers = new HashMap<>();

        List<String[]> valores = new ArrayList<>();
        valores.add(fila[4].split("#"));
        valores.add(fila[5].split("#"));

        int j = 0;
        for (String head : valores.get(0)) {
            headers.put(head, valores.get(1)[j].replace("\"", ""));
            j++;
        }
        return headers;
    }

    private static String getBody() {

        String respuesta;
        String bodyParametros = fila[6];

        if (!bodyParametros.equalsIgnoreCase("No incluye body")) {

            String bodyValues = fila[7];
            Object[] values = bodyValues.split("#");
            bodyParametros = String.format(bodyParametros, values);
        }
        respuesta = bodyParametros.replace("#", ",");
        return respuesta;
    }

    private static List<String> getPaths() {
        String[] arrayQueContieneLosPaths = fila[10].split("#");
        return Arrays.asList(arrayQueContieneLosPaths);
    }

    private static List<String> getValorResponseEsperado() {
        String[] arrayQueContieneElResponseEsperado = fila[11].split("#");
        return Arrays.asList(arrayQueContieneElResponseEsperado);
    }

    public static String getTipoMetodoPeticion() {
        return fila[3];
    }

    public static String getDescripcion() {
        return fila[12];
    }

    public static EscenarioDataCsv getDataEscenario(String idCaso) {
        getIdCasoPrueba(idCaso);
        return new EscenarioDataCsv(
                getUrl(),
                getBody(),
                getHeaders(),
                getPaths(),
                getValorResponseEsperado()
        );
    }
}

