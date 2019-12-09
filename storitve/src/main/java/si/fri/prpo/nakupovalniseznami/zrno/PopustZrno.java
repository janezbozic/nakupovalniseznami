package si.fri.prpo.nakupovalniseznami.zrno;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;
import si.fri.prpo.nakupovalniseznami.entitete.Popust;

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

    @Interceptors(BelezenjeKlicevZrno.class)
    public List<Popust> pridobiPopuste(){
        List<Popust> popusti = em.createNamedQuery("Popust.getAll").getResultList();
        return popusti;
    }

    @Interceptors(BelezenjeKlicevZrno.class)
    public Popust pridobiPopust(int popustId){

        Popust p = em.find(Popust.class, popustId);

        return p;

    }

    @Interceptors(BelezenjeKlicevZrno.class)
    @Transactional
    public Popust dodajPopust(Popust p){

        if (p != null) {
            em.getTransaction().begin();
            em.persist(p);
            em.getTransaction().commit();
        }

        return p;

    }

    @Interceptors(BelezenjeKlicevZrno.class)
    @Transactional
    public Popust posodobiPopust(int popustId, Popust popust){

        Popust starPopust = pridobiPopust(popustId);

        popust.setId(starPopust.getId());

        em.merge(popust);

        return popust;

    }

    @Transactional
    public Integer odstraniPopust(int popustId){

        Popust popust = pridobiPopust(popustId);

        if (popust != null)
            em.remove(popust);

        return popustId;

    }

    public List<Popust> getPopust(QueryParameters query) {
        return JPAUtils.queryEntities(em, Popust.class, query);
    }

    public Long getPopustCount(QueryParameters query) {
        return JPAUtils.queryEntitiesCount(em, Popust.class, query);
    }

}
