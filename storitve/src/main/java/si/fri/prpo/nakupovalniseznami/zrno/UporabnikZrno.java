package si.fri.prpo.nakupovalniseznami.zrno;

import si.fri.prpo.nakupovalniseznami.entitete.Uporabnik;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
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

    public List<Uporabnik> pridobiUporabnike(){
        List<Uporabnik> uporabniki = em.createNamedQuery("Uporabnik.getAll").getResultList();
        return uporabniki;
    }

    public Uporabnik pridobiUporabnika(int uporabnikId){

        Uporabnik up = em.find(Uporabnik.class, uporabnikId);

        return up;

    }

    @Transactional
    public Uporabnik dodajUporabnika(Uporabnik up) {

        if (up != null)
            em.persist(up);

        return up;

    }

    @Transactional
    public void posodobiUporabnika (int uporabnikId, Uporabnik up){

        Uporabnik u = pridobiUporabnika(uporabnikId);

        up.setId(u.getId());
        em.merge(up);

    }

    @Transactional
    public Integer odstraniUporabnika(int uporabnikId){

        Uporabnik up = pridobiUporabnika(uporabnikId);

        if (up != null)
            em.remove(up);

        return uporabnikId;

    }

}
