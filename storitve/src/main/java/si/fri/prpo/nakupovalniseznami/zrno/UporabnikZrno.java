package si.fri.prpo.nakupovalniseznami.zrno;

import si.fri.prpo.nakupovalniseznami.entitete.Uporabnik;

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
public class UporabnikZrno {

    private Logger log = Logger.getLogger(UporabnikZrno.class.getName());

    @PostConstruct
    private void init(){
        log.info("Inicializacija zrna: " + UporabnikZrno.class.getSimpleName());
    }

    @PreDestroy
    private void destros(){
        log.info("Deinicializacija zrna: " + UporabnikZrno.class.getSimpleName());
    }

    @PersistenceContext(unitName = "nakupovalni-seznami-jpa")
    private EntityManager em;

    @Interceptors(BelezenjeKlicevZrno.class)
    public List<Uporabnik> pridobiUporabnike(){
        List<Uporabnik> uporabniki = em.createNamedQuery("Uporabnik.getAll").getResultList();
        return uporabniki;
    }

    @Interceptors(BelezenjeKlicevZrno.class)
    public Uporabnik pridobiUporabnika(int uporabnikId){

        Uporabnik up = em.find(Uporabnik.class, uporabnikId);

        return up;

    }

    @Interceptors(BelezenjeKlicevZrno.class)
    @Transactional
    public Uporabnik dodajUporabnika(Uporabnik up) {

        if (up != null) {
            em.getTransaction().begin();
            em.persist(up);
            em.getTransaction().commit();
        }
        return up;
    }

    @Interceptors(BelezenjeKlicevZrno.class)
    @Transactional
    public Uporabnik posodobiUporabnika (int uporabnikId, Uporabnik up){

        Uporabnik u = pridobiUporabnika(uporabnikId);

        up.setId(u.getId());
        em.merge(up);

        return up;

    }

    @Interceptors(BelezenjeKlicevZrno.class)
    @Transactional
    public Integer odstraniUporabnika(int uporabnikId){

        Uporabnik up = pridobiUporabnika(uporabnikId);

        if (up != null)
            em.remove(up);

        return uporabnikId;

    }

}
