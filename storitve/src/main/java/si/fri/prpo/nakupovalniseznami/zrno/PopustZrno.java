package si.fri.prpo.nakupovalniseznami.zrno;

import si.fri.prpo.nakupovalniseznami.entitete.Popust;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.logging.Logger;

@ApplicationScoped
public class PopustZrno {

    private Logger log = Logger.getLogger(PopustZrno.class.getName());

    @PostConstruct
    private void init(){
        log.info("Inicializacija zrna: " + PopustZrno.class.getSimpleName());
    }

    @PreDestroy
    private void destros(){
        log.info("Deinicializacija zrna: " + PopustZrno.class.getSimpleName());
    }

    @PersistenceContext(unitName = "nakupovalni-seznami-jpa")
    private EntityManager em;

    public List<Popust> pridobiPopuste(){
        List<Popust> popusti = em.createNamedQuery("Popust.getAll").getResultList();
        return popusti;
    }

    public Popust pridobiPopust(int popustId){

        Popust p = em.find(Popust.class, popustId);

        return p;

    }

    @Transactional
    public Popust dodajPopust(Popust p){

        if (p != null) {
            em.getTransaction().begin();
            em.persist(p);
            em.getTransaction().commit();
        }

        return p;

    }

    @Transactional
    public void posodobiPopust(int popustId, Popust popust){

        Popust starPopust = pridobiPopust(popustId);

        popust.setId(starPopust.getId());

        em.merge(popust);

    }

    @Transactional
    public Integer odstraniPopust(int popustId){

        Popust popust = pridobiPopust(popustId);

        if (popust != null)
            em.remove(popust);

        return popustId;

    }

}
