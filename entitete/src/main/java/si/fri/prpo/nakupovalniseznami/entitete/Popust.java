package si.fri.prpo.nakupovalniseznami.entitete;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import java.util.List;

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

    @Column(name = "velikost")
    private Integer velikost;

    @JsonbTransient
    @OneToMany(mappedBy = "popust")
    private List<Artikel> artikel;

    public Integer getId() {
        return id;
    }

    public List<Artikel> getArtikel() {
        return artikel;
    }

    public Integer getVelikost() {
        return velikost;
    }

    public void setVelikost(Integer velikost) {
        this.velikost = velikost;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setArtikel(List<Artikel> artikel) {
        this.artikel = artikel;
    }
}
