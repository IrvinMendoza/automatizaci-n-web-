package interactions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.rest.interactions.RestInteraction;
import net.thucydides.core.annotations.Step;

public class PostToken extends RestInteraction {
    @Step("{0} executes a POST on the resource #resource")
    @Override
    public <T extends Actor> void performAs(T actor) {
        rest().contentType("application/x-www-form-urlencoded")
                .relaxedHTTPSValidation()
                .formParam("grant_type", "client_credentials")
                .formParam("client_id", "f585f5d5")
                .formParam("client_secret", "3288c450db6212e04dad5d66c2ad08c6")
                .when()
                .post("https://sso-ocpuat.dombif.peru/auth/realms/Banbif-API/protocol/openid-connect/token");
    }
}
