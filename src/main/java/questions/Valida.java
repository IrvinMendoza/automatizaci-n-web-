package questions;

import groovy.json.JsonParser;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.response.ResponseBodyData;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import org.apache.poi.hpsf.Decimal;
import org.json.JSONArray;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Valida implements Question<List<String>> {

    private List<String> listaPath;

    public Valida(List<String> listaPath) {
        this.listaPath = listaPath;
    }

    public static Valida bodyRespuesta(List<String> listaPath) {
        return new Valida(listaPath);
    }

    @Override
    public List<String> answeredBy(Actor actor) {

        Response lastResponse = SerenityRest.lastResponse();
        List<String> resultado = new ArrayList<>();

        for (String s : listaPath) {
            Object path = lastResponse.path(s);

            if (path.toString().equals("")) {
                resultado.add("no tiene mensaje");
            } else {
                if (s.contains("datos.clienteACR.ingresoNeto")) {
                    if (path.equals("")) {
                        resultado.add("Ingreso Neto Null");
                    } else {
                        JSONObject JSONResponseBody = new JSONObject(lastResponse.body().asString());
                        Double jArr = JSONResponseBody.getJSONObject("datos").getJSONObject("clienteACR").getDouble("ingresoNeto");
                        resultado.add(jArr.toString());
                    }

                } else if (s.contains("datos.tasa.desgravamenVida")) {
                    if (path.equals("")) {
                        resultado.add("tasa desgravamen Null");
                    } else {
                        JSONObject JSONResponseBody = new JSONObject(lastResponse.body().asString());
                        BigDecimal jArr = JSONResponseBody.getJSONObject("datos").getJSONObject("tasa").getBigDecimal("desgravamenVida");
                        resultado.add(jArr.toString());
                    }
                } else if (s.contains("datos.tasa.desgravamen")) {
                    if (path.equals("")) {
                        resultado.add("tasa desgravamen Null");
                    } else {
                        JSONObject JSONResponseBody = new JSONObject(lastResponse.body().asString());
                        BigDecimal jArr = JSONResponseBody.getJSONObject("datos").getJSONObject("tasa").getBigDecimal("desgravamen");
                        resultado.add(jArr.toString());
                    }
                } else if (s.contains("datos.tasa.interes")) {
                    if (path.equals("")) {
                        resultado.add("tasa interes Null");
                    } else {
                        JSONObject JSONResponseBody = new JSONObject(lastResponse.body().asString());
                        BigDecimal jArr = JSONResponseBody.getJSONObject("datos").getJSONObject("tasa").getBigDecimal("interes");
                        resultado.add(jArr.toString());
                    }
                } else if (s.contains("datos.tasa.seguroTodoRiesgo")) {
                    if (path.equals("")) {
                        resultado.add("tasa seguro todo riesgo Null");

                    } else {
                        JSONObject JSONResponseBody = new JSONObject(lastResponse.body().asString());
                        BigDecimal jArr = JSONResponseBody.getJSONObject("datos").getJSONObject("tasa").getBigDecimal("seguroTodoRiesgo");
                        resultado.add(jArr.toString());
                    }
                } else if (s.contains("datos.tasa.tasaCostoEfectivoAnual")) {
                    if (path.equals("")) {
                        resultado.add("tasa costo Efectivo Anual Null");
                    } else {
                        JSONObject JSONResponseBody = new JSONObject(lastResponse.body().asString());
                        BigDecimal jArr = JSONResponseBody.getJSONObject("datos").getJSONObject("tasa").getBigDecimal("tasaCostoEfectivoAnual");
                        resultado.add(jArr.toString());

                    }

                } else if (s.contains("enviarCorreoResponse.datos.id")) {
                    System.out.println(path.toString());
                    if (path.toString() != null) {
                        resultado.add("Se generó id correo");
                    } else {
                        resultado.add(path.toString());
                    }


                } else {
                    resultado.add(path.toString());
                }
            }

        }
        return resultado;
    }
}
