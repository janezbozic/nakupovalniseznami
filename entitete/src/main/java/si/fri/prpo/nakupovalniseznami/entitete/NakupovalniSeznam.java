package si.fri.prpo.nakupovalniseznami.entitete;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "nakupovalni_seznam")
@NamedQueries(value =
        {
             @NamedQuery(name = "NakupovalniSeznam.getAll", query = "SELECT n from NakupovalniSeznam n")
        })
public class NakupovalniSeznam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "opravljeno")
    private String opravljeno;

    @ManyToOne
    @JoinColumn(name = "uporabnik_id")
    private Uporabnik uporabnik;

    @JoinTable(name = "artikel_seznama",
        joinColumns = @JoinColumn(name = "nakupovalni_seznam_id"),
        inverseJoinColumns = @JoinColumn(name = "artikel_id")
    )
    private List<Artikel> artikli;

    public Integer getId() {
        return id;
    }

    public List<Artikel> getArtikli() {
        return artikli;
    }

    public String getOpravljeno() {
        return opravljeno;
    }

    public Uporabnik getUporabnik() {
        return uporabnik;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setArtikli(List<Artikel> artikli) {
        this.artikli = artikli;
    }

    public void setOpravljeno(String status) {
        this.opravljeno = status;
    }

    public void setUporabnik(Uporabnik uporabnik) {
        this.uporabnik = uporabnik;
    }

}
