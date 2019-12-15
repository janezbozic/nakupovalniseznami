package si.fri.prpo.nakupovalniseznami.servlet.v1.AddonExtensions;

import si.fri.prpo.nakupovalniseznami.entitete.NakupovalniSeznam;
import si.fri.prpo.nakupovalniseznami.entitete.Popust;

import java.util.List;

public class ArtikelExtended {

    int id;
    String ime;
    double cena;
    int zaloga;
    List<NakupovalniSeznam> nakupovalniSeznami;
    Popust popust;
    String imePredloga;

    public ArtikelExtended(int i, String m, double c, int z, List<NakupovalniSeznam> ln, Popust p, String pr) {
        id = i;
        ime = m;
        cena = c;
        zaloga = z;
        nakupovalniSeznami = ln;
        popust = p;
        imePredloga = pr;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public double getCena() {
        return cena;
    }

    public void setCena(double cena) {
        this.cena = cena;
    }

    public int getZaloga() {
        return zaloga;
    }

    public void setZaloga(int zaloga) {
        this.zaloga = zaloga;
    }

    public List<NakupovalniSeznam> getNakupovalniSeznami() {
        return nakupovalniSeznami;
    }

    public void setNakupovalniSeznami(List<NakupovalniSeznam> nakupovalniSeznami) {
        this.nakupovalniSeznami = nakupovalniSeznami;
    }

    public Popust getPopust() {
        return popust;
    }

    public void setPopust(Popust popust) {
        this.popust = popust;
    }

    public String getImePredloga() {
        return imePredloga;
    }

    public void setImePredloga(String imePredloga) {
        this.imePredloga = imePredloga;
    }
}
