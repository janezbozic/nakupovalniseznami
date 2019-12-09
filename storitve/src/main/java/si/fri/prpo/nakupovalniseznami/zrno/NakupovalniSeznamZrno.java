package si.fri.prpo.nakupovalniseznami.zrno;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;
import si.fri.prpo.nakupovalniseznami.entitete.NakupovalniSeznam;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.RequestScoped;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

@RequestScoped
public class NakupovalniSeznamZrno {

    private String idZrna;
    private Logger log = Logger.getLogger(NakupovalniSeznamZrno.class.getName());

    @PostConstruct
    private void init(){
        idZrna = UUID.randomUUID().toString();
        log.info("Inicializacija zrna: " +" "+ NakupovalniSeznamZrno.class.getSimpleName() + " id zrna: "+ idZrna);
    }

    @PreDestroy
    private void destros(){
        log.info("Deinicializacija zrna: " +" "+ NakupovalniSeznamZrno.class.getSimpleName() + " id zrna: "+ idZrna);
    }

    @PersistenceContext(unitName = "nakupovalni-seznami-jpa")
    private EntityManager em;

    @Interceptors(BelezenjeKlicevZrno.class)
    public List<NakupovalniSeznam> pridobiNakupovalneSezname(){
        List<NakupovalniSeznam> nakupovalniSeznami = em.createNamedQuery("NakupovalniSeznam.getAll").getResultList();
        return nakupovalniSeznami;
    }

    @Interceptors(BelezenjeKlicevZrno.class)
    public NakupovalniSeznam pridobiNakupovalniSeznam(int nakupovalniSeznamId){

        NakupovalniSeznam ns = em.find(NakupovalniSeznam.class, nakupovalniSeznamId);

        return ns;

    }

    @Interceptors(BelezenjeKlicevZrno.class)
    @Transactional
    public NakupovalniSeznam dodajNakupovalniSeznam(NakupovalniSeznam nakupovalniSeznam){

        if (nakupovalniSeznam != null) {
            em.getTransaction().begin();
            em.persist(nakupovalniSeznam);
            em.getTransaction().commit();
        }

        return nakupovalniSeznam;

    }

    @Interceptors(BelezenjeKlicevZrno.class)
    @Transactional
    public NakupovalniSeznam posodobiNakupovalniSeznam(int id, NakupovalniSeznam ns){

        NakupovalniSeznam starNS = pridobiNakupovalniSeznam(id);

        ns.setId(starNS.getId());
        em.merge(ns);

        return ns;

    }

    @Interceptors(BelezenjeKlicevZrno.class)
    @Transactional
    public Integer odstraniNakupovalniSeznam(int id){

        NakupovalniSeznam ns = pridobiNakupovalniSeznam(id);

        if (ns != null)
            em.remove(ns);

        return id;

    }

    public List<NakupovalniSeznam> getNakupovalniSeznami(QueryParameters query) {
        return JPAUtils.queryEntities(em, NakupovalniSeznam.class, query);
    }

    public Long getNakupovalniSeznamiCount(QueryParameters query) {
        return JPAUtils.queryEntitiesCount(em, NakupovalniSeznam.class, query);
    }
}
