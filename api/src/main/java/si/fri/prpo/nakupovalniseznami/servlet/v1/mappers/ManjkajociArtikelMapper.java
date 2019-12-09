package si.fri.prpo.nakupovalniseznami.servlet.v1.mappers;

import si.fri.prpo.nakupovalniseznami.izjeme.ManjkajociArtikelIzjema;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ManjkajociArtikelMapper implements ExceptionMapper<ManjkajociArtikelIzjema> {

    @Override
    public Response toResponse(ManjkajociArtikelIzjema exception) {
        return Response
                .status(Response.Status.BAD_REQUEST)
                .entity("{\"napaka\":\"" + exception.getMessage() + "\"}")
                .build();
    }

}