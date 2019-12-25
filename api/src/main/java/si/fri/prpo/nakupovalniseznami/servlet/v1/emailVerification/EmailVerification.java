package si.fri.prpo.nakupovalniseznami.servlet.v1.emailVerification;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.sun.research.ws.wadl.Response;

public class EmailVerification {

    public static Response verEmail (String email){
        try {
            HttpResponse<String> response = Unirest.get("https://mailcheck.p.rapidapi.com/?disable_test_connection=false&domain=" + email.replace("@", "%2540"))
                    .header("x-rapidapi-host", "mailcheck.p.rapidapi.com")
                    .header("x-rapidapi-key", "nu9CQsvbTkmshvF7zoTrlFPgKm9Fp1XWF9njsnlNsm7HBafZ3w")
                    .asString();
        } catch (UnirestException e) {
            e.printStackTrace();
        }
        return null;
    }

}
