package si.fri.prpo.nakupovalniseznami.Data;

import java.time.Instant;
import java.util.Date;
import java.util.List;

public class NakupovalniSeznamData {

    private String opravljeno;
    private Instant ustvarjen;
    private int uporabnik;
    private List<Integer> artikli;

    public NakupovalniSeznamData(){}

    public NakupovalniSeznamData(String op, Instant us, int u, List<Integer> a){
        opravljeno = op;
        ustvarjen = us;
        uporabnik = u;
        artikli = a;
    }

    public List<Integer> getArtikli() {
        return artikli;
    }

    public void setArtikli(List<Integer> artikli) {
        this.artikli = artikli;
    }

    public int getUporabnik() {
        return uporabnik;
    }

    public void setUporabnik(int uporabnik) {
        this.uporabnik = uporabnik;
    }

    public Instant getUstvarjen() {
        return ustvarjen;
    }

    public String getOpravljeno() {
        return opravljeno;
    }

    public void setUstvarjen(Instant ustvarjen) {
        this.ustvarjen = ustvarjen;
    }

    public void setOpravljeno(String opravljeno) {
        this.opravljeno = opravljeno;
    }
}
