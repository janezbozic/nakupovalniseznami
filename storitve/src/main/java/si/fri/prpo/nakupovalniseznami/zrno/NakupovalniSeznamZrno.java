package si.fri.prpo.nakupovalniseznami.zrno;

import si.fri.prpo.nakupovalniseznami.entitete.NakupovalniSeznam;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.logging.Logger;

@ApplicationScoped
public class NakupovalniSeznamZrno {

    private Logger log = Logger.getLogger(NakupovalniSeznamZrno.class.getName());

    @PersistenceContext(unitName = "nakupovalni-seznami-jpa")
    private EntityManager em;

    public List<Object> pridobiNakupovalnseSeznameCriteriaAPI(){

        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Object> criteriaQuery = criteriaBuilder.createQuery();
        Root<NakupovalniSeznam> from = criteriaQuery.from(NakupovalniSeznam.class);
        CriteriaQuery<Object> select = criteriaQuery.select(from);
        TypedQuery<Object> typedQuery = em.createQuery(select);
        return typedQuery.getResultList();

    }

    public List<NakupovalniSeznam> pridobiNakupovalneSezname(){
        List<NakupovalniSeznam> nakupovalniSeznami = em.createNamedQuery("NakupovalniSeznam.getAll").getResultList();
        //List<NakupovalniSeznam> nakupovalniSeznami = em.createNamedQuery("NakupovalniSeznam.getFromId").setParameter("iskanId", 2).getResultList();
        return nakupovalniSeznami;
    }
}
