package interactions;

import io.restassured.http.ContentType;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.rest.interactions.RestInteraction;

import java.util.Map;

public class GetQueryWithParams extends RestInteraction {

    private String url;
    private Map<String, Object> params;
    private Map<String, Object> headers;
    private Map<String, Object> queryParams;

    public GetQueryWithParams(String url, Map<String, Object> headers, Map<String, Object> params, Map<String, Object> queryParams) {
        this.url = url;
        this.params = params;
        this.headers = headers;
        this.queryParams = queryParams;
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        rest().contentType(ContentType.JSON)
                .relaxedHTTPSValidation()
                .headers(headers)
                .queryParams(queryParams)
                .when()
                .get(url, params);
    }
}