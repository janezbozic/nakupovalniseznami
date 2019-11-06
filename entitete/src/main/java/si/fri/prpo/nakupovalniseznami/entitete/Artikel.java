package si.fri.prpo.nakupovalniseznami.entitete;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "artikel")
@NamedQueries(value =
        {
                @NamedQuery(name = "Artikel.getAll", query = "SELECT n from Artikel n")
        })

public class Artikel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "ime")
    private String ime;

    @Column(name = "cena")
    private Double cena;

    @Column(name = "zaloga")
    private Integer zaloga;

    @ManyToMany(mappedBy = "artikel_id")
    private List<NakupovalniSeznam> nakupovalniSeznami;

    @ManyToOne
    @JoinColumn(name = "popust_id")
    private Popust popust;

    public Double getCena() {
        return cena;
    }

    public Integer getId() {
        return id;
    }

    public List<NakupovalniSeznam> getNakupovalniSeznami() {
        return nakupovalniSeznami;
    }

    public String getIme() {
        return ime;
    }

    public Integer getZaloga() {
        return zaloga;
    }

    public void setCena(Double cena) {
        this.cena = cena;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setNakupovalniSeznami(List<NakupovalniSeznam> nakupovalniSeznami) {
        this.nakupovalniSeznami = nakupovalniSeznami;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public void setZaloga(Integer zaloga) {
        this.zaloga = zaloga;
    }

    public Popust getPopust() {
        return popust;
    }

    public void setPopust(Popust popust) {
        this.popust = popust;
    }
}
