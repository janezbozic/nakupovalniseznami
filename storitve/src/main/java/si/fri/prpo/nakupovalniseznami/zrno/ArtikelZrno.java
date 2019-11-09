package si.fri.prpo.nakupovalniseznami.zrno;

import si.fri.prpo.nakupovalniseznami.entitete.Artikel;
import si.fri.prpo.nakupovalniseznami.entitete.NakupovalniSeznam;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.logging.Logger;

@ApplicationScoped
public class ArtikelZrno {

    private Logger log = Logger.getLogger(ArtikelZrno.class.getName());

    @PersistenceContext(unitName = "nakupovalni-seznami-jpa")
    private EntityManager em;

    public List<Object> pridobiArtikleCriteriaAPI(){

        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Object> criteriaQuery = criteriaBuilder.createQuery();
        Root<Artikel> from = criteriaQuery.from(Artikel.class);
        CriteriaQuery<Object> select = criteriaQuery.select(from);
        TypedQuery<Object> typedQuery = em.createQuery(select);
        return typedQuery.getResultList();

    }

    public List<Artikel> pridobiArtikle(){
        List<Artikel> artikli = em.createNamedQuery("Artikel.getAll").getResultList();
        //List<Artikel> artikli = em.createNamedQuery("Artikel.getPopustFromId").setParameter("iskanId", 1).getResultList();
        return artikli;
    }
}
