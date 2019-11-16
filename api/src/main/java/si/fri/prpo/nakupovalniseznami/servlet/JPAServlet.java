package si.fri.prpo.nakupovalniseznami.servlet;

import si.fri.prpo.nakupovalniseznami.Data.NakupovalniSeznamData;
import si.fri.prpo.nakupovalniseznami.entitete.Artikel;
import si.fri.prpo.nakupovalniseznami.entitete.NakupovalniSeznam;
import si.fri.prpo.nakupovalniseznami.entitete.Popust;
import si.fri.prpo.nakupovalniseznami.entitete.Uporabnik;
import si.fri.prpo.nakupovalniseznami.zrno.*;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@WebServlet("/servlet")
public class JPAServlet extends HttpServlet {

    Logger log = Logger.getLogger(JPAServlet.class.getName());

    @Inject
    private UporabnikZrno uporabnikZrno;

    @Inject
    private ArtikelZrno artikelZrno;

    @Inject
    private NakupovalniSeznamZrno nakupovalniSeznamZrno;

    @Inject
    private PopustZrno popustZrno;

    @Inject
    private UpravljanjeNakupovalnihSeznamovZrno upravljanjeNakupovalnihSeznamovZrno;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        NakupovalniSeznam n = upravljanjeNakupovalnihSeznamovZrno.ustvariNakupovalniSeznam(new NakupovalniSeznamData("DA", Instant.now(), 1, list));

        List<Uporabnik> uporabniki = uporabnikZrno.pridobiUporabnike();

        List<Artikel> artikli = artikelZrno.pridobiArtikle();

        List<NakupovalniSeznam> nakupovaniSeznami = nakupovalniSeznamZrno.pridobiNakupovalneSezname();

        List<Popust> popusti = popustZrno.pridobiPopuste();

        // izpis uporabnikov na spletno stran
        resp.setContentType("text/html; charset-UTF-8");
        resp.setCharacterEncoding("UTF-8");

        PrintWriter writer = resp.getWriter();

        writer.append("Uporabniki:<br/>");

        uporabniki.stream().forEach(u -> writer.append(u.getId() + " " + u.getIme() + " " + u.getPriimek() + "<br/>"));

        writer.append("NakupovalniSeznami:<br/>");
        nakupovaniSeznami.stream().forEach(u -> writer.append(u.getId() + " " + u.getOpravljeno() + "<br/>"));

        writer.append("Artikli:<br/>");
        artikli.stream().forEach(u -> writer.append(u.getId() + " " + u.getIme() + " " + u.getCena() + " " + u.getPopust().getVelikost() + " " + u.getZaloga() + "<br/>"));

        writer.append("Popusti:<br/>");
        popusti.stream().forEach(u -> writer.append(u.getId() + " " + u.getVelikost() +  "<br/>"));

    }
}