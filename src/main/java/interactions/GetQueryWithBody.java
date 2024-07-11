package interactions;

import io.restassured.http.ContentType;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.rest.interactions.RestInteraction;

import java.util.Map;

public class GetQueryWithBody extends RestInteraction {

    private String url;
    private String body;
    private Map<String, Object> headers;
    private Map<String, Object> queryParams;

    public GetQueryWithBody(String url, Map<String, Object> headers, String body, Map<String, Object> queryParams) {
        this.url = url;
        this.headers = headers;
        this.body = body;
        this.queryParams = queryParams;
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        rest().contentType(ContentType.JSON)
                .relaxedHTTPSValidation()
                .headers(headers)
                .when()
                .body(body)
                .queryParams(queryParams)
                .when()
                .get(url);
    }
}