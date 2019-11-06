package si.fri.prpo.nakupovalniseznami.entitete;

import javax.persistence.*;

@Entity
@Table(name = "popust")
@NamedQueries(value =
        {
                @NamedQuery(name = "Popust.getAll", query = "SELECT n from Popust n")
        })

public class Popust {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "popust")
    private Integer popust;

    @OneToMany(mappedBy = "popust_id")
    private Artikel artikel;

    public Integer getId() {
        return id;
    }

    public Artikel getArtikel() {
        return artikel;
    }

    public Integer getPopust() {
        return popust;
    }

    public void setPopust(Integer popust) {
        this.popust = popust;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setArtikel(Artikel artikel) {
        this.artikel = artikel;
    }
}
