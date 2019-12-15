package si.fri.prpo.nakupovalniseznami.servlet.v1.viri;

import com.kumuluz.ee.configuration.utils.ConfigurationUtil;
import com.kumuluz.ee.rest.beans.QueryParameters;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import si.fri.prpo.nakupovalniseznami.Data.ArtikelData;
import si.fri.prpo.nakupovalniseznami.entitete.Artikel;
import si.fri.prpo.nakupovalniseznami.servlet.v1.AddonExtensions.ArtikelExtended;
import si.fri.prpo.nakupovalniseznami.zrno.ArtikelZrno;
import si.fri.prpo.nakupovalniseznami.zrno.UpravljanjeNakupovalnihSeznamovZrno;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.*;
import java.util.List;

@ApplicationScoped
@Path("artikli")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ArtikliVir {

    @Context
    protected UriInfo uriInfo;

    @Inject
    private ArtikelZrno artikelZrno;

    @Inject
    private UpravljanjeNakupovalnihSeznamovZrno upravljanjeNakupovalnihSeznamovZrno;

    private Client httpClient;

    private String baseUrl;

    @PostConstruct
    private void init() {
        httpClient = ClientBuilder.newClient();
        baseUrl = ConfigurationUtil.getInstance().get("baseURL").orElse("N/A");
    }

    @Operation(description = "Vrne seznam artiklov", summary = "Seznam artiklov",
            tags = "artikli", responses = {
            @ApiResponse(responseCode = "200",
                    description = "Seznam artiklov",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Artikel.class))),
                    headers = {@Header(name = "X-Total-Count", description = "Število vrnjenih artiklov")})
    })

    @GET
    public Response pridobiArtikle () {

        //return Response.ok(artikelZrno.pridobiArtikle()).build();

        QueryParameters query = QueryParameters.query(uriInfo.getRequestUri().getQuery()).build();
        Long uporabnikiCount = artikelZrno.getArtikeliCount(query);
        return Response
                .ok(artikelZrno.getArtikli(query))
                .header("X-Total-Count", uporabnikiCount)
                .build();

    }


    @Operation(description = "Vrne podrobnosti artikla", summary = "Podrobnosti artikla",
            tags = "artikli", responses = {
            @ApiResponse(responseCode = "200",
                    description = "Podrobnosti artikla",
                    content = @Content(schema = @Schema(implementation = Artikel.class))
            )
    })

    @GET
    @Path("{id}")
    public Response pridobiArtikel(@Parameter(
            description = "Identifikator artikla za vračanje", required = true) @PathParam("id") Integer id){

        Artikel artikel = artikelZrno.pridobiArtikel(id);

        ArtikelData predlog = httpClient
                .target(baseUrl + "priporocenArtikel/"+artikel.getId())
                .request().get(new GenericType<ArtikelData>() {
        });

        ArtikelExtended ae = new ArtikelExtended(artikel.getId(), artikel.getIme(), artikel.getCena(), artikel.getZaloga(), artikel.getNakupovalniSeznami(), artikel.getPopust(), predlog.getIme());

        if (artikel != null) {
            return Response.ok(ae).build();
        }
        else
            return Response.status(Response.Status.NOT_FOUND).build();

    }

    @Operation(description = "Dodaj artikel", summary = "Dodajanje artikla",
            tags = "artikli", responses = {
            @ApiResponse(responseCode = "201",
                    description = "Artikel uspešno dodan"
            ),
            @ApiResponse(responseCode = "405", description = "Validacijska napaka")
    })

    @POST
    public Response dodajArtikel (@RequestBody(
            description = "DTO objekt za dodajanje artiklov.", required = true,
            content = @Content(schema = @Schema(implementation = ArtikelData.class))) ArtikelData artikel){

        return Response
                .status(Response.Status.CREATED)
                .entity(upravljanjeNakupovalnihSeznamovZrno.dodajArtikel(artikel))
                .build();

    }

    @Operation(description = "Posodobi artikel", summary = "Posodabljanje artikla",
            tags = "artikli", responses = {
            @ApiResponse(responseCode = "201", description = "Artikel uspešno posodobljen"
            )
    })

    @PUT
    @Path("{id}")
    public Response posodobiArtikel(@Parameter(
            description = "Identifikator artikla za posodabljanje", required = true)
                                        @PathParam("id") Integer id,
                                    @RequestBody(description = "DTO objekt za posodabljanje artikla.", required = true,
                                            content = @Content(schema = @Schema(implementation = Artikel.class))) Artikel artikel){

        return Response
                .status(Response.Status.CREATED)
                .entity(artikelZrno.posodobiArtikel(id, artikel))
                .build();

    }

    @Operation(description = "Odstrani artikel", summary = "Brisanje artikla",
            tags = "artikli",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Artikel uspešno odstranjen"),
                    @ApiResponse(responseCode = "404", description = "Artikel ne obstaja")

            })

    @DELETE
    @Path("{id}")
    public Response odstraniArtikel(@Parameter(
            description = "Identifikator artikla za brisanje", required = true) @PathParam("id") Integer id){

        return Response
                .status(Response.Status.OK)
                .entity(artikelZrno.odstraniArtikel(id))
                .build();

    }

}
