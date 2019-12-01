package si.fri.prpo.nakupovalniseznami.servlet.v1.viri;

import si.fri.prpo.nakupovalniseznami.entitete.Popust;
import si.fri.prpo.nakupovalniseznami.zrno.PopustZrno;
import si.fri.prpo.nakupovalniseznami.zrno.UpravljanjeNakupovalnihSeznamovZrno;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@ApplicationScoped
@Path("popusti")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PopustiVir {

    @Inject
    private PopustZrno popustZrno;

    @Inject
    private UpravljanjeNakupovalnihSeznamovZrno upravljanjeNakupovalnihSeznamovZrno;

    @GET
    public Response pridobiPopuste (){

        return Response.ok(popustZrno.pridobiPopuste()).build();

    }

    @GET
    @Path("{id}")
    public Response pridobiUporabnika(@PathParam("id") Integer id){

        Popust popust = popustZrno.pridobiPopust(id);

        if (popust != null)
            return Response.ok(popust).build();
        else
            return Response.status(Response.Status.NOT_FOUND).build();

    }

    @POST
    public Response dodajPopust(Popust popust){

        return Response
                .status(Response.Status.CREATED)
                .entity(popustZrno.dodajPopust(popust))
                .build();

    }

    @PUT
    @Path("{id}")
    public Response posodiPopust(@PathParam("id") Integer id, Popust popust){

        return Response
                .status(Response.Status.CREATED)
                .entity(popustZrno.posodobiPopust(id, popust))
                .build();

    }

    @DELETE
    @Path("{id}")
    public Response odstraniPopust(@PathParam("id") Integer id){

        return Response
                .status(Response.Status.OK)
                .entity(popustZrno.odstraniPopust(id))
                .build();

    }

}
