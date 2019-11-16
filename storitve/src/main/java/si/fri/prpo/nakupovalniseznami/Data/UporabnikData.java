package si.fri.prpo.nakupovalniseznami.Data;

public class UporabnikData {

    private String ime, priimek, uporabniskoIme, email;

    public UporabnikData(String i, String p, String up, String e){
        ime = i;
        priimek = p;
        uporabniskoIme = up;
        email = e;
    }

    public String getIme() {
        return ime;
    }

    public String getPriimek() {
        return priimek;
    }

    public String getUporabniskoIme() {
        return uporabniskoIme;
    }

    public String getEmail() {
        return email;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public void setPriimek(String priimek) {
        this.priimek = priimek;
    }

    public void setUporabniskoIme(String uporabniskoIme) {
        this.uporabniskoIme = uporabniskoIme;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
