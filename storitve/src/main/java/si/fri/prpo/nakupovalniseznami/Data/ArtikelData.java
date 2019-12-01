package si.fri.prpo.nakupovalniseznami.Data;

public class ArtikelData {

    private String ime;
    private double cena;
    private int zaloga;
    private int popust;

    public ArtikelData(){}

    public ArtikelData (String i, double c, int z, int p){
        ime = i;
        cena = c;
        zaloga = z;
        popust = p;
    }

    public String getIme() {
        return ime;
    }

    public double getCena() {
        return cena;
    }

    public int getZaloga() {
        return zaloga;
    }

    public void setZaloga(int zaloga) {
        this.zaloga = zaloga;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public void setCena(double cena) {
        this.cena = cena;
    }

    public int getPopust() {
        return popust;
    }

    public void setPopust(int popust) {
        this.popust = popust;
    }
}
