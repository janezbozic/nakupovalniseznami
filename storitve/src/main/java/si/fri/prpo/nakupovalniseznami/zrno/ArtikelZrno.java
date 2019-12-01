package si.fri.prpo.nakupovalniseznami.zrno;

import si.fri.prpo.nakupovalniseznami.anotacije.BeleziKlice;
import si.fri.prpo.nakupovalniseznami.entitete.Artikel;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.logging.Logger;

@ApplicationScoped
public class ArtikelZrno {

    private Logger log = Logger.getLogger(ArtikelZrno.class.getName());

    @PostConstruct
    private void init(){
        log.info("Inicializacija zrna: " + ArtikelZrno.class.getSimpleName());
    }

    @PreDestroy
    private void destros(){
        log.info("Deinicializacija zrna: " + ArtikelZrno.class.getSimpleName());
    }

    @PersistenceContext(unitName = "nakupovalni-seznami-jpa")
    private EntityManager em;

    @Interceptors(BelezenjeKlicevZrno.class)
    public List<Artikel> pridobiArtikle(){
        List<Artikel> artikli = em.createNamedQuery("Artikel.getAll").getResultList();
        return artikli;
    }

    @Interceptors(BelezenjeKlicevZrno.class)
    public Artikel pridobiArtikel(int artikelId){

        Artikel artikel = em.find(Artikel.class, artikelId);

        return artikel;

    }

    @Interceptors(BelezenjeKlicevZrno.class)
    @Transactional
    public Artikel dodajArtikel(Artikel artikel){

        if (artikel != null) {
            em.getTransaction().begin();
            em.persist(artikel);
            em.getTransaction().commit();
        }

        return artikel;

    }

    @Interceptors(BelezenjeKlicevZrno.class)
    @Transactional
    public Artikel posodobiArtikel(int artikelId, Artikel artikel){

        Artikel starArtikel = pridobiArtikel(artikelId);

        artikel.setId(starArtikel.getId());
        em.merge(artikel);

        return artikel;

    }

    @Interceptors(BelezenjeKlicevZrno.class)
    @Transactional
    public Integer odstraniArtikel(int artikelId){

        Artikel artikel = pridobiArtikel(artikelId);

        if (artikel != null)
            em.remove(artikel);

        return artikelId;

    }

}
