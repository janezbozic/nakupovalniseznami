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
import si.fri.prpo.nakupovalniseznami.entitete.Artikel;
import si.fri.prpo.nakupovalniseznami.entitete.Popust;
import si.fri.prpo.nakupovalniseznami.zrno.PopustZrno;
import si.fri.prpo.nakupovalniseznami.zrno.UpravljanjeNakupovalnihSeznamovZrno;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@ApplicationScoped
@Path("popusti")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PopustiVir {

    @Context
    protected UriInfo uriInfo;

    @Inject
    private PopustZrno popustZrno;

    @Inject
    private UpravljanjeNakupovalnihSeznamovZrno upravljanjeNakupovalnihSeznamovZrno;

    @Operation(description = "Vrne seznam artiklov", summary = "Seznam artiklov",
            tags = "artikli", responses = {
            @ApiResponse(responseCode = "200",
                    description = "Seznam artiklov",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Artikel.class))),
                    headers = {@Header(name = "X-Total-Count", description = "Število vrnjenih artiklov")})
    })

    @GET
    public Response pridobiPopuste (){

       // return Response.ok(popustZrno.pridobiPopuste()).build();

        QueryParameters query = QueryParameters.query(uriInfo.getRequestUri().getQuery()).build();
        Long uporabnikiCount = popustZrno.getPopustCount(query);
        return Response
                .ok(popustZrno.getPopust(query))
                .header("X-Total-Count", uporabnikiCount)
                .build();

    }

    @Operation(description = "Vrne podrobnosti popusta", summary = "Podrobnosti popusta",
            tags = "popusti", responses = {
            @ApiResponse(responseCode = "200",
                    description = "Podrobnosti popusta",
                    content = @Content(schema = @Schema(implementation = Popust.class))
            )
    })

    @GET
    @Path("{id}")
    public Response pridobiUporabnika(@Parameter(
            description = "Identifikator popusta za vračanje", required = true) @PathParam("id") Integer id){

        Popust popust = popustZrno.pridobiPopust(id);

        if (popust != null)
            return Response.ok(popust).build();
        else
            return Response.status(Response.Status.NOT_FOUND).build();

    }

    @Operation(description = "Dodaj popust", summary = "Dodajanje popusta",
            tags = "popusti", responses = {
            @ApiResponse(responseCode = "201",
                    description = "Popust uspešno dodan"
            ),
            @ApiResponse(responseCode = "405", description = "Validacijska napaka")
    })

    @POST
    public Response dodajPopust(@RequestBody(
            description = "DTO objekt za dodajanje popustov.", required = true,
            content = @Content(schema = @Schema(implementation = Popust.class))) Popust popust){

        return Response
                .status(Response.Status.CREATED)
                .entity(popustZrno.dodajPopust(popust))
                .build();

    }

    @Operation(description = "Posodobi popust", summary = "Posodabljanje popusta",
            tags = "popust", responses = {
            @ApiResponse(responseCode = "201", description = "Popust uspešno posodobljen"
            )
    })

    @PUT
    @Path("{id}")
    public Response posodiPopust(@Parameter(
            description = "Identifikator popusta za posodabljanje", required = true) @PathParam("id") Integer id,
                                 @RequestBody(
                                         description = "DTO objekt za posodabljanje popustov.", required = true,
                                         content = @Content(schema = @Schema(implementation = Popust.class))) Popust popust){

        return Response
                .status(Response.Status.CREATED)
                .entity(popustZrno.posodobiPopust(id, popust))
                .build();

    }

    @Operation(description = "Odstrani popust", summary = "Brisanje popusta",
            tags = "popusti",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Popust uspešno odstranjen"),
                    @ApiResponse(responseCode = "404", description = "Popust ne obstaja")

            })

    @DELETE
    @Path("{id}")
    public Response odstraniPopust(@Parameter(
            description = "Identifikator popusta za brisanje", required = true) @PathParam("id") Integer id){

        return Response
                .status(Response.Status.OK)
                .entity(popustZrno.odstraniPopust(id))
                .build();

    }

}
