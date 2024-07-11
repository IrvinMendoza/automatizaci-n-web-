package task;

import interactions.Servicio;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;

import java.util.HashMap;
import java.util.Map;

//import static jdk.nashorn.internal.runtime.GlobalFunctions.decodeURIComponent;


public class Consumo implements Task {

    String metodo, url, body, query;
    String[] pathFile, nameKeys;
    Map<String, String> headers;

    public Consumo(String metodo, String url, Map<String, String> headers, String body, String[] pathFile, String[] nameKeys, String query) {
        this.metodo = metodo;
        this.body = body;
        this.url = url;
        this.headers = headers;
        this.pathFile = pathFile;
        this.nameKeys = nameKeys;
        this.query = query;
    }

    public static Consumo elApi(String metodo, String url, Map<String, String> headers, String body, String[] pathFile, String[] nameKeys, String query) {
        return Tasks.instrumented(Consumo.class, metodo, url, headers, body, pathFile, nameKeys, query);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        Map<String, String> params = new HashMap<>();
        Map<String, String> queryParams = new HashMap<>();

        switch (metodo) {
            case "POST":
                actor.attemptsTo(
                        Servicio.restPost(url, body, headers)
                );
                break;
            case "DELETE":
                actor.attemptsTo(
                        Servicio.restDelete(url, body, headers)
                );
                break;
            case "GET":
                actor.attemptsTo(
                        Servicio.restGet(url, headers)
                );
                break;
            case "GET-PARAMS":
                for (String pair : body.split(",")) {
                    String[] entry = pair.split(":");
                    if (entry[1].trim().equals("vacio")) {
                        String ejemplo = entry[1];
                        params.put(entry[0].trim(), " ");
                    } else
                        params.put(entry[0].trim(), entry[1].trim());
                }
                actor.attemptsTo(
                        Servicio.restGetParams(url, headers, params)
                );
                break;
            case "GET-QUERY":
                for (String pair : body.split(",")) {
                    String[] entry = pair.split(":");
                    if (entry[1].trim().equals("vacio")) {
                        String ejemplo = entry[1];
                        params.put(entry[0].trim(), " ");
                    } else
                        params.put(entry[0].trim(), entry[1].trim());
                }
                actor.attemptsTo(
                        Servicio.restGetQuery(url, headers, params)
                );
                break;
            case "GET-QUERY-BODY":
                for (String pair : query.split(",")) {
                    String[] entry = pair.split(":");
                    if (entry[1].trim().equals("vacio")) {
                        String ejemplo = entry[1];
                        params.put(entry[0].trim(), " ");
                    } else
                        params.put(entry[0].trim(), entry[1].trim());
                }
                actor.attemptsTo(
                        Servicio.restGetQueryWithBody(url, headers, body, queryParams)
                );
                break;

            case "GET-BODY":
                actor.attemptsTo(
                        Servicio.restGetWithBody(url, headers, body)
                );
                break;

            case "GET-QUERY-PARAMS":
                for (String pair : body.split(",")) {
                    String[] entry = pair.split(":");
                    if (entry[1].trim().equals("vacio")) {
                        String ejemplo = entry[1];
                        params.put(entry[0].trim(), " ");
                    } else
                        params.put(entry[0].trim(), entry[1].trim());
                }
                for (String q : query.split(",")) {
                    String[] e = q.split(":");
                    if (e[1].trim().equals("vacio")) {
                        String sample = e[1];
                        queryParams.put(e[0].trim(), " ");
                    } else
                        queryParams.put(e[0].trim(), e[1].trim());
                }
                actor.attemptsTo(
                        Servicio.restGetQueryParams(url, headers, params, queryParams)
                );
                break;

            case "PUT":
                actor.attemptsTo(
                        Servicio.restPut(url, body, headers)
                );
                break;
            case "POSTWITHBODYPARAMETERS":
                actor.attemptsTo(
                        Servicio.restPostNew(url, body, headers, pathFile, nameKeys)
                );
                break;

            case "POST-BODY-QUERY":
                for (String pair : query.split(",")) {
                    String[] entry = pair.split(":");
                    if (entry[1].trim().equals("vacio")) {
                        String ejemplo = entry[1];
                        queryParams.put(entry[0].trim(), " ");
                    } else
                        queryParams.put(entry[0].trim(), entry[1].trim());
                }
                actor.attemptsTo(
                        Servicio.restPostBodyQuery(url, body, headers, queryParams)
                );

                break;
            case "TOKEN":
                actor.attemptsTo(
                        Servicio.restToken()
                );
                break;
            case "PUT-BODY-QUERY":
                for (String pair : query.split(",")) {
                    String[] entry = pair.split(":");
                    if (entry[1].trim().equals("vacio")) {
                        String ejemplo = entry[1];
                        queryParams.put(entry[0].trim(), " ");
                    } else
                        queryParams.put(entry[0].trim(), entry[1].trim());
                }
                actor.attemptsTo(
                        Servicio.restPutQueryBody(url, body, headers, queryParams)
                );
                break;
        }
    }
}
