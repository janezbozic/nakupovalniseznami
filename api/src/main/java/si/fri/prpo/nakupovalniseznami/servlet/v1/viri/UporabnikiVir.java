package si.fri.prpo.nakupovalniseznami.servlet.v1.viri;

import com.kumuluz.ee.rest.beans.QueryParameters;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import si.fri.prpo.nakupovalniseznami.Data.UporabnikData;
import si.fri.prpo.nakupovalniseznami.entitete.Uporabnik;
import si.fri.prpo.nakupovalniseznami.zrno.UporabnikZrno;
import si.fri.prpo.nakupovalniseznami.zrno.UpravljanjeNakupovalnihSeznamovZrno;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@ApplicationScoped
@Path("uporabniki")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UporabnikiVir {

    @Context
    protected UriInfo uriInfo;

    @Inject
    private UporabnikZrno uporabnikZrno;

    @Inject
    private UpravljanjeNakupovalnihSeznamovZrno upravljanjeNakupovalnihSeznamovZrno;

    @Operation(description = "Vrne seznam uporabnikov", summary = "Seznam uporabnikov",
            tags = "uporabniki", responses = {
            @ApiResponse(responseCode = "200",
                    description = "Seznam uporabnikov",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Uporabnik.class))),
                    headers = {@Header(name = "X-Total-Count", description = "Število vrnjenih uporabnikov")})

    })

    @GET
    public Response pridobiUporabnike(){

        //return Response.ok(uporabnikZrno.pridobiUporabnike()).build();

        QueryParameters query = QueryParameters.query(uriInfo.getRequestUri().getQuery()).build();
        Long uporabnikiCount = uporabnikZrno.getUporabnikiCount(query);
        return Response
                .ok(uporabnikZrno.getUporabniki(query))
                .header("X-Total-Count", uporabnikiCount)
                .build();

    }

    @Operation(description = "Vrne podrobnosti uporabnika", summary = "Podrobnosti uporabnika",
            tags = "uporabniki", responses = {
            @ApiResponse(responseCode = "200",
                    description = "Podrobnosti uporabnika",
                    content = @Content(schema = @Schema(implementation = Uporabnik.class))
            )
    })

    @GET
    @Path("{id}")
    public Response pridobiUporabnika(@Parameter(
            description = "Identifikator uporabnika za vračanje", required = true) @PathParam("id") Integer id){

        Uporabnik uporabnik = uporabnikZrno.pridobiUporabnika(id);

        if (uporabnik != null)
            return Response.ok(uporabnik).build();
        else
            return Response.status(Response.Status.NOT_FOUND).build();

    }

    @Operation(description = "Dodaj uporabnika", summary = "Dodajanje uporabnika",
            tags = "uporabniki", responses = {
            @ApiResponse(responseCode = "201",
                    description = "Uporabnik uspešno dodan"
            ),
            @ApiResponse(responseCode = "405", description = "Validacijska napaka")
    })

    @POST
    public Response dodajUporabnika(@RequestBody(
            description = "DTO objekt za dodajanje uporabnikov.", required = true,
            content = @Content(schema = @Schema(implementation = UporabnikData.class))) UporabnikData uporabnik){

        return Response
                .status(Response.Status.CREATED)
                .entity(upravljanjeNakupovalnihSeznamovZrno.dodajUporabnik(uporabnik))
                .build();

    }

    @Operation(description = "Posodobi uporabnika", summary = "Posodabljanje uporabnika",
            tags = "uporabniki", responses = {
            @ApiResponse(responseCode = "201", description = "Uporabnik uspešno posodobljen"
            )
    })

    @PUT
    @Path("{id}")
    public Response posodobiUporabnika(@Parameter(
            description = "Identifikator uporabnika za posodabljanje", required = true) @PathParam("id") Integer id,
                                       @RequestBody(
                                               description = "DTO objekt za posodabljanje uporabnikov.", required = true,
                                               content = @Content(schema = @Schema(implementation = Uporabnik.class))) Uporabnik uporabnik){

        return Response
                .status(Response.Status.CREATED)
                .entity(uporabnikZrno.posodobiUporabnika(id, uporabnik))
                .build();

    }

    @Operation(description = "Odstrani uporabnika", summary = "Brisanje uporabnika",
            tags = "uporabniki",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Uporabnik uspešno odstranjen"),
                    @ApiResponse(responseCode = "404", description = "Uporabnik ne obstaja")

            })

    @DELETE
    @Path("{id}")
    public Response odstraniUporabnika(@Parameter(
            description = "Identifikator uporabnika za brisanje", required = true) @PathParam("id") Integer id){

        return Response
                .status(Response.Status.OK)
                .entity(uporabnikZrno.odstraniUporabnika(id))
                .build();

    }

}
