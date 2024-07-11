package stepdefinitions;


import cucumber.api.DataTable;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.response.Response;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.actors.Cast;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.rest.questions.LastResponse;
import net.thucydides.core.util.EnvironmentVariables;
import okhttp3.*;
import org.openqa.selenium.WebDriver;
import questions.LastResponseStatusCode;
import questions.Valida;
import questions.Verifica;
import task.Consumo;
import util.GlobalKeyboardAutoit;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;

import static net.serenitybdd.rest.SerenityRest.lastResponse;
import static net.serenitybdd.rest.SerenityRest.rest;
import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class ApisBanbifStepDefinitions extends PageObject {
    WebDriver wd;
    private EnvironmentVariables environmentVariables;

    @Before
    public void setStage() {
        OnStage.setTheStage(new Cast());
    }

    @Given("^quiero ejecutar el API (.+) con la peticion (.+)$")
    public void quieroEjecutarElAPIConLaPeticion(String api, String peticion) {
    }

    @When("^preparar data:$")
    public void prepararData(DataTable datosPeticion) throws IOException {
        List<String> datos = datosPeticion.topCells();

        OnStage.theActorCalled("Token").wasAbleTo(
                Consumo.elApi("TOKEN", null, null, null, null, null, null));

        Map<String, String> headers = new HashMap<>();
        for (String pair : datos.get(0).split(",")) {
            String[] entry = pair.split(":");
            if (entry[0].contains("Authorization")) {
                headers.put(entry[0].trim(), "Bearer " + SerenityRest.lastResponse().path("access_token").toString());
                headers.put(entry[0].trim(), "Bearer ");
            } else {
                headers.put(entry[0].trim(), entry[1].trim());
            }
        }
        OnStage.theActorCalled("preparar data").wasAbleTo(
                Consumo.elApi("POST", "https://banbif-proveedor-transaccional.appsopsprvdes.dombif.peru/api-proveedor-transaccional/v1/registra/proveedor/temporal", headers, "{\"cliente\": {\"codigoIBS\": \"853476\",\"documento\": {\"tipo\": \"DNI_LE\",\"numero\": \"123123\"},\"entidad\": {\"nombre\": \"teste\",\"usuarios\": [{\"nombre\": \"teste\"}]}},\"proveedor\": {\"nombre\": \"nombre\",\"cuentaProveedor\": [{\"codigoBanco\": 12,\"numeroCuenta\": 123,\"moneda\": \"SOL\"},{\"codigoBanco\": 15,\"numeroCuenta\": 124,\"moneda\": \"USD\"}],\"documentoProveedor\": {\"tipoDocumento\": \"DNI_LE\",\"numeroDocumento\": \"123123\"},\"tipoProducto\": \"LVTO\"},\"registroProveedor\": {\"correlativo\": 7987451,\"tipoCarga\": \"M\",\"numeroReferencia\": \"15051109045311\",\"accion\": \"1\"}}", null, null, null));
    }

    @When("^consumo el api (.+) con la data data de prueba$")
    public void consumoElApiConLaDataDePrueba(String api, DataTable datosPeticion) throws IOException {
        List<String> datos = datosPeticion.topCells();
        String url, body, metodo, adjunto, query;
        String[] pathFile, nameKeys;
        String body2 = "";
        String body3 = "";
        //Generar token
        /*
        OnStage.theActorCalled("Token").wasAbleTo(
                Consumo.elApi("TOKEN", null, null, null, null, null, null));
        System.out.println("Value token");
        System.out.println(SerenityRest.lastResponse().path("access_token").toString());
         */
        //Preparar data para el api
        url = environmentVariables.getProperty(datos.get(0)) + datos.get(1);
        body = datos.get(4);
        pathFile = datos.get(5).split("#");
        nameKeys = datos.get(6).split("#");
        for (int i = 0; i < pathFile.length; i++) {
            pathFile[i] = pathFile[i].replace("vacio", "");
        }

        metodo = datos.get(2);

        if (datos.size() >= 8) {
            query = datos.get(7);
        } else {
            query = null;
        }
        System.out.println(url);
        Map<String, String> headers = new HashMap<>();
        if (!datos.get(3).isEmpty()) {                //si los header no estas vacios ejecute ese for
            for (String pair : datos.get(3).split(",")) {
                String[] entry = pair.split(":");
                if (entry[0].toLowerCase().contains("authorization")) {
                    if (entry[1].trim() != "" && entry[1].indexOf("vacio") <= 0)
                        //   headers.put(entry[0].trim(), entry[1] = "Bearer " + SerenityRest.lastResponse().path("access_token").toString());
                        /// headers.put(entry[0].trim(), entry[1].trim());
                        System.out.println(headers);
                } else {
                    headers.put(entry[0].trim(), entry[1].trim());
                    System.out.println(headers);
                }
            }
        }
        if (api.equals("Enviar correo")) {
            Map<String, String> bodyEnviarCorreo = new HashMap<>();
            adjunto = environmentVariables.getProperty("PATH_ADJUNTO");

            String content = new String(Files.readAllBytes(Paths.get("src/test/resources/Datadriven/ModuloTransferenciasAlExterior/Adjunto.txt")));

            for (String pair : datos.get(4).split(",")) {
                String[] entry = pair.split(":");
                int tamanoParticiones = entry.length;
                if (tamanoParticiones == 4) {
                    String valor = "";
                    for (int i = 0; i < 3; i++) {
                        if (i == 0 || i == 1) {
                            valor = valor + entry[i] + ":";
                        } else {
                            valor = valor + "" + entry[i];
                        }
                    }
                    bodyEnviarCorreo.put(valor.trim(), entry[3].trim());
                    body3 = body3 + valor.trim() + ":" + entry[3].trim() + ",";
                } else if (tamanoParticiones == 3) {
                    String valor = "";
                    for (int i = 0; i < 2; i++) {
                        if (i == 0) {
                            valor = entry[i] + ":";
                        } else {
                            valor = valor + "" + entry[i];
                        }
                    }
                    body3 = body3 + valor.trim() + ":" + entry[2].trim() + ",";
                } else {
                    if (entry[1].contains("URL")) {
                        bodyEnviarCorreo.put(entry[0].trim(), content);
                        body3 = body3 + entry[0].trim() + ":" + content + ",";
                        System.out.println("Este es el resultado" + body);
                    } else {
                        bodyEnviarCorreo.put(entry[0].trim(), entry[1].trim());
                        body3 = body3 + entry[0].trim() + ":" + entry[1].trim() + ",";
                    }
                }
                body = bodyEnviarCorreo.toString();
                body2 = body2 + "" + bodyEnviarCorreo.toString();
                System.out.println(body3);
            }
            body3 = body3.substring(0, body3.length() - 1);
            body = body3 + "}]}}}";
            System.out.println();

        }
        //Consumir API
        OnStage.theActorCalled("Operador/ambos").wasAbleTo(
                Consumo.elApi(metodo, url, headers, body, pathFile, nameKeys, query));
    }

    @Then("^verifico el status code (.+)$")
    public void verificoElStatusCode(int sc) {
        System.out.println(sc);
        OnStage.theActorInTheSpotlight().should(seeThat("El status code: ", LastResponseStatusCode.is(), equalTo(sc)));
        // System.out.println("El status code: "+ SerenityRest.lastResponse().prettyPeek());
    }

    @Then("^las respuestas esperadas (.+) en las rutas (.+) del response$")
    public void lasRespuestasEsperadasEnLasRutasDelResponse(String respuestasEsperadas, String rutas) {
        System.out.println(respuestasEsperadas);
        System.out.println(rutas);
        String[] respuestasE, rutasList;
        respuestasE = respuestasEsperadas.split("#");
        rutasList = rutas.split("#");
        List<String> re;
        List<String> ru;
        re = Arrays.asList(respuestasE);
        ru = Arrays.asList(rutasList);
        OnStage.theActorInTheSpotlight().should(seeThat("Respuesta esperada", Valida.bodyRespuesta(ru), equalTo(re)));
    }

    @Then("^valido los (.+) del response$")
    public void validoLosDatosDelResponse(String datosDinamicos) {
        //if (SerenityRest.lastResponse().path("meta.mensajes[0].tipo").toString().equals("info")) {
        try {
            OnStage.theActorInTheSpotlight().should(seeThat("Datos dinamicos", Verifica.noVacio(datosDinamicos), not(containsString("{}"))));
        } catch (Exception e) {
            System.out.println(e + "No se realiza validación para este escenario");
        }
    }

    @And("^valido los (.+) con url del response (.+)$")
    public void validoLosCamposVariablesConUrlDelResponseUrlEsperada(String datosDinamicos, String urlEsperada) {

        String response = "";

        try {

            Response lastResponse = SerenityRest.lastResponse();

            response = lastResponse.path(datosDinamicos).toString();

            assertThat(response, containsString(urlEsperada));

        } catch (Exception e) {
            System.out.println(e + "No se realiza validación para este escenario");

        }
    }
}