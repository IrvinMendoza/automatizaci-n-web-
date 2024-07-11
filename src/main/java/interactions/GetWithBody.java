package interactions;

import io.restassured.http.ContentType;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.rest.interactions.RestInteraction;

import java.util.Map;

public class GetWithBody extends RestInteraction {

    private String url;
    private String body;
    private Map<String, Object> headers;


    public GetWithBody(String url, Map<String, Object> headers, String body) {
        this.url = url;
        this.headers = headers;
        this.body = body;
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        rest().contentType(ContentType.JSON)
                .relaxedHTTPSValidation()
                .headers(headers)
                .when()
                .body(body)
                .when()
                .get(url);
    }
}