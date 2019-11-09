package si.fri.prpo.nakupovalniseznami.servlet;

import si.fri.prpo.nakupovalniseznami.entitete.Artikel;
import si.fri.prpo.nakupovalniseznami.entitete.NakupovalniSeznam;
import si.fri.prpo.nakupovalniseznami.entitete.Popust;
import si.fri.prpo.nakupovalniseznami.entitete.Uporabnik;
import si.fri.prpo.nakupovalniseznami.zrno.ArtikelZrno;
import si.fri.prpo.nakupovalniseznami.zrno.NakupovalniSeznamZrno;
import si.fri.prpo.nakupovalniseznami.zrno.PopustZrno;
import si.fri.prpo.nakupovalniseznami.zrno.UporabnikZrno;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/servlet")
public class JPAServlet extends HttpServlet {

    @Inject
    private UporabnikZrno uporabnikZrno;

    @Inject
    private ArtikelZrno artikelZrno;

    @Inject
    private NakupovalniSeznamZrno nakupovalniSeznamZrno;

    @Inject
    private PopustZrno popustZrno;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        List<Uporabnik> uporabniki = uporabnikZrno.pridobiUporabnike();
        List<Object> uporabnikiO = uporabnikZrno.pridobiUporabnikeCriteriaAPI();

        List<Artikel> artikli = artikelZrno.pridobiArtikle();
        List<Object> artikliO = artikelZrno.pridobiArtikleCriteriaAPI();

        List<NakupovalniSeznam> nakupovaniSeznami = nakupovalniSeznamZrno.pridobiNakupovalneSezname();
        List<Object> nakupovaniSeznamiO = nakupovalniSeznamZrno.pridobiNakupovalnseSeznameCriteriaAPI();

        List<Popust> popusti = popustZrno.pridobiPopuste();
        List<Object> popustiO = popustZrno.pridobiPopusteCriteriaAPI();

        // izpis uporabnikov na spletno stran
        resp.setContentType("text/html; charset-UTF-8");
        resp.setCharacterEncoding("UTF-8");

        PrintWriter writer = resp.getWriter();

        writer.append("Uporabniki:<br/>");
       // uporabniki.stream().forEach(u -> writer.append(u.getIme() + "<br/>" + u.getNakupovalniSeznami().get(0).getArtikli().get(0).getCena() + "<br/>" + u.getNakupovalniSeznami().get(0).getArtikli().get(0).getPopust().getVelikost() + "<br/>"));
       // seznami.stream().forEach(u -> writer.append(u.getId() + "<br/>" + u.getUporabnik().getIme() + "<br/>" + u.getOpravljeno() + "<br/>"));
        //artikli.stream().forEach(u -> writer.append(u.getId() + "<br/>" + u.getPopust().getVelikost()));

       // uporabniki.stream().forEach(u -> writer.append(u.getId() + " " + u.getIme() + " " + u.getPriimek() + "<br/>"));
        for(Object o:uporabnikiO) {
            Uporabnik e = (Uporabnik) o;
            writer.append(e.getId() + " " + e.getIme() + " " + e.getPriimek() + "<br/>");
        }

        writer.append("NakupovalniSeznami:<br/>");
       // nakupovaniSeznami.stream().forEach(u -> writer.append(u.getId() + " " + u.getOpravljeno() + "<br/>"));
        for(Object o:nakupovaniSeznamiO) {
            NakupovalniSeznam e = (NakupovalniSeznam) o;
            writer.append(e.getId() + "  " + e.getOpravljeno() + "<br/>");
        }

        writer.append("Artikli:<br/>");
        //artikli.stream().forEach(u -> writer.append(u.getId() + " " + u.getIme() + " " + u.getCena() + " " + u.getPopust().getVelikost() + " " + u.getZaloga() + "<br/>"));
        for(Object o:artikliO) {
            Artikel e = (Artikel) o;
            writer.append(e.getId() + " " + e.getIme() + " " + e.getCena() + " " + e.getPopust().getVelikost() + " " + e.getZaloga() + "<br/>");
        }

        writer.append("Popusti:<br/>");
        //popusti.stream().forEach(u -> writer.append(u.getId() + " " + u.getVelikost() +  "<br/>"));
        for(Object o:popustiO) {
            Popust e = (Popust) o;
            writer.append(e.getId() + " " + e.getVelikost() +  "<br/>");
        }
    }
}