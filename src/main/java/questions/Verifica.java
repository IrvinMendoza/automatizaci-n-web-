package questions;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.hamcrest.CoreMatchers.startsWith;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class Verifica implements Question<String> {
    private String listaPath;

    public Verifica(String listaPath) {
        this.listaPath = listaPath;
    }

    public static Verifica noVacio(String listaPath) {
        return new Verifica(listaPath);
    }

    @Override
    public String answeredBy(Actor actor) {
         Response lastResponse = SerenityRest.lastResponse();

        String resultado = "";

        try {
            for (String s : listaPath.split("#")) {
                Object path = lastResponse.path(s);
                System.out.println("path: " + path);
                if (path.toString().equals("")) {
                    resultado = resultado + "no tiene mensaje";
                } else {
                    resultado = resultado + path.toString();
                }
            }
        } catch (Exception e) {
            System.out.println(e + "No se realiza validación para este escenario");

        }
        return resultado;

    }

}
