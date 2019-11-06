package si.fri.prpo.nakupovalniseznami.entitete;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "uporabnik")
@NamedQueries(value =
        {
                @NamedQuery(name = "Uporabnik.getAll", query = "SELECT u from Uporabnik u")
        })

public class Uporabnik {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "ime")
    private String ime;

    @Column(name = "priimek")
    private String priimek;

    @Column(name = "uporabniskoime")
    private String uporabniskoIme;

    @Column(name = "email")
    private String email;

    @OneToMany(mappedBy = "uporabnik_id")
    private List<NakupovalniSeznam> nakupovalniSeznami;

    public Integer getId() {
        return id;
    }

    public String getPriimek() {
        return priimek;
    }

    public String getIme() {
        return ime;
    }

    public List<NakupovalniSeznam> getNakupovalniSeznami() {
        return nakupovalniSeznami;
    }

    public void setPriimek(String priimek) {
        this.priimek = priimek;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setNakupovalniSeznami(List<NakupovalniSeznam> nakupovalniSeznami) {
        this.nakupovalniSeznami = nakupovalniSeznami;
    }
}
