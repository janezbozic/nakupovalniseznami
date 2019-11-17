package si.fri.prpo.nakupovalniseznami.zrno;

import si.fri.prpo.nakupovalniseznami.entitete.NakupovalniSeznam;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.RequestScoped;
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

    public List<NakupovalniSeznam> pridobiNakupovalneSezname(){
        List<NakupovalniSeznam> nakupovalniSeznami = em.createNamedQuery("NakupovalniSeznam.getAll").getResultList();
        return nakupovalniSeznami;
    }

    public NakupovalniSeznam pridobiNakupovalniSeznam(int nakupovalniSeznamId){

        NakupovalniSeznam ns = em.find(NakupovalniSeznam.class, nakupovalniSeznamId);

        return ns;

    }

    @Transactional
    public NakupovalniSeznam dodajNakupovalniSeznam(NakupovalniSeznam nakupovalniSeznam){

        if (nakupovalniSeznam != null) {
            em.getTransaction().begin();
            em.persist(nakupovalniSeznam);
            em.getTransaction().commit();
        }

        return nakupovalniSeznam;

    }

    @Transactional
    public void posodobiNakupovalniSeznam(int id, NakupovalniSeznam ns){

        NakupovalniSeznam starNS = pridobiNakupovalniSeznam(id);

        ns.setId(starNS.getId());
        em.merge(ns);

    }

    @Transactional
    public Integer odstraniNakupovalniSeznam(int id){

        NakupovalniSeznam ns = pridobiNakupovalniSeznam(id);

        if (ns != null)
            em.remove(ns);

        return id;

    }
}
