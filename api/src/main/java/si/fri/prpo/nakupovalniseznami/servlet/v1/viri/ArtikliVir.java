package si.fri.prpo.nakupovalniseznami.servlet.v1.viri;

import si.fri.prpo.nakupovalniseznami.Data.ArtikelData;
import si.fri.prpo.nakupovalniseznami.entitete.Artikel;
import si.fri.prpo.nakupovalniseznami.zrno.ArtikelZrno;
import si.fri.prpo.nakupovalniseznami.zrno.UpravljanjeNakupovalnihSeznamovZrno;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@ApplicationScoped
@Path("artikli")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ArtikliVir {

    @Inject
    private ArtikelZrno artikelZrno;

    @Inject
    private UpravljanjeNakupovalnihSeznamovZrno upravljanjeNakupovalnihSeznamovZrno;

    @GET
    public Response pridobiArtikle () {
        return Response.ok(artikelZrno.pridobiArtikle()).build();
    }

    @GET
    @Path("{id}")
    public Response pridobiArtikel(@PathParam("id") Integer id){

        Artikel artikel = artikelZrno.pridobiArtikel(id);

        if (artikel != null)
            return Response.ok(artikel).build();
        else
            return Response.status(Response.Status.NOT_FOUND).build();

    }

    @POST
    public Response dodajArtikel (ArtikelData artikel){

        return Response
                .status(Response.Status.CREATED)
                .entity(upravljanjeNakupovalnihSeznamovZrno.dodajArtikel(artikel))
                .build();

    }

    @PUT
    @Path("{id}")
    public Response posodobiArtikel(@PathParam("id") Integer id, Artikel artikel){

        return Response
                .status(Response.Status.CREATED)
                .entity(artikelZrno.posodobiArtikel(id, artikel))
                .build();

    }

    @DELETE
    @Path("{id}")
    public Response odstraniArtikel(@PathParam("id") Integer id){

        return Response
                .status(Response.Status.OK)
                .entity(artikelZrno.odstraniArtikel(id))
                .build();

    }

}
