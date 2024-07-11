package modal;


import java.util.List;
import java.util.Map;

public class EscenarioDataCsv {


    private String api;
    private String body;
    private Map<String, String> headers;
    private List<String> paths;
    private List<String> valorResponseEsperado;


    public EscenarioDataCsv(String url, String body, Map<String, String> headers, List<String> paths, List<String> valorResponseEsperado) {
        this.api = url;
        this.body = body;
        this.headers = headers;
        this.paths = paths;
        this.valorResponseEsperado = valorResponseEsperado;
    }


    public String getApi() {
        return api;
    }

    public String getBody() {
        return body;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public List<String> getPaths() {
        return paths;
    }

    public List<String> getValorResponseEsperado() {
        return valorResponseEsperado;
    }


}
