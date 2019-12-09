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
import si.fri.prpo.nakupovalniseznami.Data.NakupovalniSeznamData;
import si.fri.prpo.nakupovalniseznami.entitete.NakupovalniSeznam;
import si.fri.prpo.nakupovalniseznami.zrno.NakupovalniSeznamZrno;
import si.fri.prpo.nakupovalniseznami.zrno.UpravljanjeNakupovalnihSeznamovZrno;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@ApplicationScoped
@Path("nakupovalniSeznami")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class NakupovalniSeznamiVir {

    @Context
    protected UriInfo uriInfo;

    @Inject
    private NakupovalniSeznamZrno nakupovalniSeznamZrno;

    @Inject
    private UpravljanjeNakupovalnihSeznamovZrno upravljanjeNakupovalnihSeznamovZrno;

    @Operation(description = "Vrne seznam nakupovalnih seznamov", summary = "Seznam nakupovalnih seznamov",
            tags = "nakupovalni_seznami", responses = {
            @ApiResponse(responseCode = "200",
                    description = "Seznam nakupovalnih seznamov",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = NakupovalniSeznam.class))),
                    headers = {@Header(name = "X-Total-Count", description = "Število vrnjenih nakupovalnih seznamov")})
    })

    @GET
    public Response pridobiNakupovalneSezname(){

        //return Response.ok(nakupovalniSeznamZrno.pridobiNakupovalneSezname()).build();

        QueryParameters query = QueryParameters.query(uriInfo.getRequestUri().getQuery()).build();
        Long uporabnikiCount = nakupovalniSeznamZrno.getNakupovalniSeznamiCount(query);
        return Response
                .ok(nakupovalniSeznamZrno.getNakupovalniSeznami(query))
                .header("X-Total-Count", uporabnikiCount)
                .build();

    }

    @Operation(description = "Vrne podrobnosti nakupovalnega seznama", summary = "Podrobnosti nakupovalnega seznama",
            tags = "nakupovalni_seznami", responses = {
            @ApiResponse(responseCode = "200",
                    description = "Podrobnosti nakupovalnega seznama",
                    content = @Content(schema = @Schema(implementation = NakupovalniSeznam.class))
            )
    })

    @GET
    @Path("{id}")
    public Response pridobiNakupovalniSeznam(@Parameter(
            description = "Identifikator nakupovalnega seznama za vračanje", required = true) @PathParam("id") Integer id){

        NakupovalniSeznam nakupovalniSeznam = nakupovalniSeznamZrno.pridobiNakupovalniSeznam(id);

        if (nakupovalniSeznam != null)
            return Response.ok(nakupovalniSeznam).build();
        else
            return Response.status(Response.Status.NOT_FOUND).build();

    }

    @Operation(description = "Dodaj nakupovalni seznam", summary = "Dodajanje nakupovalnega seznama",
            tags = "nakupovalni_seznami", responses = {
            @ApiResponse(responseCode = "201",
                    description = "Nakupovalni seznam uspešno dodan"
            ),
            @ApiResponse(responseCode = "405", description = "Validacijska napaka")
    })

    @POST
    public Response dodajNakupovalniSeznam(@RequestBody(
            description = "DTO objekt za dodajanje artiklov.", required = true,
            content = @Content(schema = @Schema(implementation = NakupovalniSeznamData.class))) NakupovalniSeznamData ns){

        return Response
                .status(Response.Status.CREATED)
                .entity(upravljanjeNakupovalnihSeznamovZrno.ustvariNakupovalniSeznam(ns))
                .build();

    }

    @Operation(description = "Posodobi nakupovalni seznam", summary = "Posodabljanje nakupovalnega seznama",
            tags = "nakupovalni_seznami", responses = {
            @ApiResponse(responseCode = "201", description = "Nakupovalni seznam uspešno posodobljen"
            )
    })

    @PUT
    @Path("{id}")
    public Response posodobiNakupovalniSeznam(@Parameter(
            description = "Identifikator nakupovalnega seznama za posodabljanje", required = true) @PathParam("id") Integer id,
                                              @RequestBody(
                                                      description = "DTO objekt za posodabljanje nakupovalnega seznama.", required = true,
                                                      content = @Content(schema = @Schema(implementation = NakupovalniSeznam.class))) NakupovalniSeznam nakupovalniSeznam){

        return Response
                .status(Response.Status.CREATED)
                .entity(nakupovalniSeznamZrno.posodobiNakupovalniSeznam(id, nakupovalniSeznam))
                .build();

    }

    @Operation(description = "Odstrani nakupovalni seznam", summary = "Brisanje nakupovalnega seznama",
            tags = "nakupovalni_seznami",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Nakupovalni seznam uspešno odstranjen"),
                    @ApiResponse(responseCode = "404", description = "Nakupovalni seznam ne obstaja")

            })

    @DELETE
    @Path("{id}")
    public Response odstraniNakupovalniSeznam(@Parameter(
            description = "Identifikator nakupovalnega seznama za brisanje", required = true)
                                                  @PathParam("id") Integer id){

        return Response
                .status(Response.Status.OK)
                .entity(nakupovalniSeznamZrno.odstraniNakupovalniSeznam(id))
                .build();

    }

}
