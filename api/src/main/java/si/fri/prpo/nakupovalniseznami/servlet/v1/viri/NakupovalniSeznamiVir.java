package si.fri.prpo.nakupovalniseznami.servlet.v1.viri;

import si.fri.prpo.nakupovalniseznami.Data.NakupovalniSeznamData;
import si.fri.prpo.nakupovalniseznami.entitete.NakupovalniSeznam;
import si.fri.prpo.nakupovalniseznami.zrno.NakupovalniSeznamZrno;
import si.fri.prpo.nakupovalniseznami.zrno.UpravljanjeNakupovalnihSeznamovZrno;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@ApplicationScoped
@Path("nakupovalniSeznami")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class NakupovalniSeznamiVir {

    @Inject
    private NakupovalniSeznamZrno nakupovalniSeznamZrno;

    @Inject
    private UpravljanjeNakupovalnihSeznamovZrno upravljanjeNakupovalnihSeznamovZrno;

    @GET
    public Response pridobiNakupovalneSezname(){

        return Response.ok(nakupovalniSeznamZrno.pridobiNakupovalneSezname()).build();

    }

    @GET
    @Path("{id}")
    public Response pridobiNakupovalniSeznam(@PathParam("id") Integer id){

        NakupovalniSeznam nakupovalniSeznam = nakupovalniSeznamZrno.pridobiNakupovalniSeznam(id);

        if (nakupovalniSeznam != null)
            return Response.ok(nakupovalniSeznam).build();
        else
            return Response.status(Response.Status.NOT_FOUND).build();

    }

    @POST
    public Response dodajNakupovalniSeznam(NakupovalniSeznamData ns){

        return Response
                .status(Response.Status.CREATED)
                .entity(upravljanjeNakupovalnihSeznamovZrno.ustvariNakupovalniSeznam(ns))
                .build();

    }

    @PUT
    @Path("{id}")
    public Response posodobiNakupovalniSeznam(@PathParam("id") Integer id, NakupovalniSeznam nakupovalniSeznam){

        return Response
                .status(Response.Status.CREATED)
                .entity(nakupovalniSeznamZrno.posodobiNakupovalniSeznam(id, nakupovalniSeznam))
                .build();

    }

    @DELETE
    @Path("{id}")
    public Response odstraniNakupovalniSeznam(@PathParam("id") Integer id){

        return Response
                .status(Response.Status.OK)
                .entity(nakupovalniSeznamZrno.odstraniNakupovalniSeznam(id))
                .build();

    }

}
