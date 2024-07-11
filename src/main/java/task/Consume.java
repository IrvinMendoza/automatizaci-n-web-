package task;

import interactions.Servicio;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.MetodosComunesCsv;

import java.util.Map;


public class Consume implements Task {

    private String api;
    private String body;
    private Map<String, String> headers;
    private static final Logger LOGGER = LoggerFactory.getLogger(Consume.class);


    public Consume(String api, String body, Map<String, String> headers) {
        this.api = api;
        this.body = body;
        this.headers = headers;
    }

    public static Consume elServicio(String api, String body, Map<String, String> headers) {
        return Tasks.instrumented(Consume.class, api, body, headers);
    }

    public static Consume elServicio(String api, Map<String, String> headers) {
        return Tasks.instrumented(Consume.class, api, headers);
    }

    public static Consume elServicio(String api) {
        return Tasks.instrumented(Consume.class, api);
    }


    @Override
    public <T extends Actor> void performAs(T actor) {

        LOGGER.info("Se consume el Servicio: " + api);
        LOGGER.info("Descripcion de la API: " + MetodosComunesCsv.getDescripcion());

        switch (MetodosComunesCsv.getTipoMetodoPeticion()) {
            case "POST":
                actor.attemptsTo(
                        Servicio.restPost(api, body, headers)
                );
                break;
            case "DELETE":
                actor.attemptsTo(
                        Servicio.restDelete(api, body, headers)
                );
                break;
            case "GET":
                actor.attemptsTo(
                        Servicio.restGet(api)
                );
                break;
            case "GETWITHHEADER":
                actor.attemptsTo(
                        Servicio.restGet(api, headers)
                );
                break;
            default:

        }
    }
}
