package si.fri.prpo.nakupovalniseznami.zrno;

import si.fri.prpo.nakupovalniseznami.Data.ArtikelData;
import si.fri.prpo.nakupovalniseznami.Data.NakupovalniSeznamData;
import si.fri.prpo.nakupovalniseznami.Data.PopustData;
import si.fri.prpo.nakupovalniseznami.Data.UporabnikData;
import si.fri.prpo.nakupovalniseznami.entitete.Artikel;
import si.fri.prpo.nakupovalniseznami.entitete.NakupovalniSeznam;
import si.fri.prpo.nakupovalniseznami.entitete.Popust;
import si.fri.prpo.nakupovalniseznami.entitete.Uporabnik;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@ApplicationScoped
public class UpravljanjeNakupovalnihSeznamovZrno {

    private Logger log = Logger.getLogger(UpravljanjeNakupovalnihSeznamovZrno.class.getName());

    @Inject
    private UporabnikZrno uporabnikZrno;

    @Inject
    private NakupovalniSeznamZrno nakupovalniSeznamZrno;

    @Inject
    private ArtikelZrno artikelZrno;

    @Inject
    private PopustZrno popustZrno;

    @PostConstruct
    private void init(){

        log.info("Inicializacija zrna: " + UpravljanjeNakupovalnihSeznamovZrno.class.getSimpleName());

    }

    @PreDestroy
    private void destros(){

        log.info("Deinicializacija zrna: " + UpravljanjeNakupovalnihSeznamovZrno.class.getSimpleName());

    }

    public NakupovalniSeznam ustvariNakupovalniSeznam(NakupovalniSeznamData nsd){

        List<Artikel> artikli = new ArrayList<Artikel>();
        Uporabnik uporabnik = uporabnikZrno.pridobiUporabnika(nsd.getUporabnik());
        List<Integer> artikliId = nsd.getArtikli();

        if (uporabnik == null){
            log.info("Uporabnik ne obstaja, ne moremo vstaviti v bazo");
            return null;
        }

        for (int i = 0; i<artikliId.size(); i++){
            Artikel artikel = artikelZrno.pridobiArtikel(artikliId.get(i));
            if (artikel == null){
                log.info("Artikel ne obstaja, ne moremo vstaviti v bazo.");
                return null;
            }
            artikli.add(artikel);
        }

        NakupovalniSeznam nakupovalniSeznam = new NakupovalniSeznam();
        nakupovalniSeznam.setArtikli(artikli);
        nakupovalniSeznam.setUporabnik(uporabnik);
        nakupovalniSeznam.setOpravljeno(nsd.getOpravljeno());
        nakupovalniSeznam.setUstvarjen(Instant.now());

        return nakupovalniSeznamZrno.dodajNakupovalniSeznam(nakupovalniSeznam);

    }

    public Popust ustvariPopust(PopustData pd){

        Popust p = new Popust();
        p.setVelikost(pd.getVelikost());
        popustZrno.dodajPopust(p);

        return p;

    }

    public Uporabnik dodajUporabnik(UporabnikData ud){

        Uporabnik uporabnik = new Uporabnik();
        uporabnik.setIme(ud.getIme());
        uporabnik.setPriimek(ud.getPriimek());
        uporabnik.setEmail(ud.getEmail());
        uporabnik.setUporabniskoIme(ud.getUporabniskoIme());

        uporabnikZrno.dodajUporabnika(uporabnik);

        return uporabnik;

    }

    public Artikel dodajArtikel(ArtikelData ad){

        Popust popust = popustZrno.pridobiPopust(ad.getPopust());

        if (popust == null){
            log.info("Popusta ni mogoce     najti. Ni bilo mogoce vstaviti v bazo.");
            return null;
        }

        Artikel artikel = new Artikel();
        artikel.setCena(ad.getCena());
        artikel.setIme(ad.getIme());
        artikel.setPopust(popust);
        artikel.setZaloga(ad.getZaloga());

        artikelZrno.dodajArtikel(artikel);

        return artikel;

    }

}
