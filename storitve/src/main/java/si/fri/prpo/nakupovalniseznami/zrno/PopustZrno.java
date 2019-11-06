package si.fri.prpo.nakupovalniseznami.zrno;

import si.fri.prpo.nakupovalniseznami.entitete.Popust;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.logging.Logger;

@ApplicationScoped
public class PopustZrno {

    private Logger log = Logger.getLogger(PopustZrno.class.getName());

    @PersistenceContext(unitName = "nakupovalni-seznami-jpa")
    private EntityManager em;

    public List<Popust> pridobiPopuste(){
        List<Popust> popusti = em.createNamedQuery("Popust.getAll").getResultList();
        return popusti;
    }
}
