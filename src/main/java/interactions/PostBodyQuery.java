package interactions;

import io.restassured.http.ContentType;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.rest.interactions.RestInteraction;
import net.thucydides.core.annotations.Step;

import java.util.Map;

public class PostBodyQuery extends RestInteraction {

    private String resource;
    private String body;
    private Map<String, Object> headers;
    private Map<String, Object> queryParams;

    public PostBodyQuery(String resource, String body, Map<String, Object> headers, Map<String, Object> queryParams) {
        this.resource = resource;
        this.body = body;
        this.headers = headers;
        this.queryParams = queryParams;
    }

    @Step("{0} executes a POST on the resource #resource")
    @Override
    public <T extends Actor> void performAs(T actor) {
        rest().contentType(ContentType.JSON)
                .relaxedHTTPSValidation()
                .headers(headers)
                .when()
                .body(body)
                .queryParams(queryParams)
                .when()
                .post(resource);
    }
}
