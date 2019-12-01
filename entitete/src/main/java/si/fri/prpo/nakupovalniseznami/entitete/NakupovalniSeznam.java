package si.fri.prpo.nakupovalniseznami.entitete;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "nakupovalni_seznam")
@NamedQueries(value =
        {
             @NamedQuery(name = "NakupovalniSeznam.getAll", query = "SELECT n from NakupovalniSeznam n"),
             @NamedQuery(name = "NakupovalniSeznam.getFromId", query = "select n from NakupovalniSeznam n where n.id = :iskanId"),
        })
public class NakupovalniSeznam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "opravljeno")
    private String opravljeno;

    @Column(name = "ustvarjen")
    private Instant ustvarjen;

    @ManyToOne
    @JoinColumn(name = "uporabnik_id")
    private Uporabnik uporabnik;

    @ManyToMany
    @JoinTable(name = "artikel_seznama",
        joinColumns = @JoinColumn(name = "nakupovalni_seznam"),
        inverseJoinColumns = @JoinColumn(name = "artikel")
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

    public Instant getUstvarjen() {
        return ustvarjen;
    }

    public void setUstvarjen(Instant ustvarjen) {
        this.ustvarjen = ustvarjen;
    }
}
