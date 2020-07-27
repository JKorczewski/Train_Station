package Pociagi;
import java.io.Serializable;

public abstract class Pociag implements Serializable{
    private int id;
    private String nazwa;
    private int dlugoscSkladu;
    private int ladownosc;
    private double predkoscMax;
    private boolean kupno;
    private boolean rezerwacja;

    public Pociag(int id, String nazwa, int dlugoscSkladu, int ladownosc, double predkoscMax, boolean kupno, boolean rezerwacja) {
        this.id = id;
        this.nazwa = nazwa;
        this.dlugoscSkladu = dlugoscSkladu;
        this.ladownosc = ladownosc;
        this.predkoscMax = predkoscMax;
        this.kupno = kupno;
        this.rezerwacja = rezerwacja;
    }

    @Override
    public String toString() {
        return "id=" + id +
                ", nazwa='" + nazwa + '\'' +
                ", dlugoscSkladu=" + dlugoscSkladu +
                ", ladownosc=" + ladownosc +
                ", predkoscMax=" + predkoscMax +
                ", kupno=" + kupno +
                ", rezerwacja=" + rezerwacja;
    }

    public boolean isKupno() {
        return kupno;
    }

    public boolean isRezerwacja() {
        return rezerwacja;
    }

    public int getId() {
        return id;
    }

    public String getNazwa() {
        return nazwa;
    }

    public int getDlugoscSkladu() {
        return dlugoscSkladu;
    }

    public int getLadownosc() {
        return ladownosc;
    }

    public double getPredkoscMax() {
        return predkoscMax;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public void setDlugoscSkladu(int dlugoscSkladu) {
        this.dlugoscSkladu = dlugoscSkladu;
    }

    public void setLadownosc(int ladownosc) {
        this.ladownosc = ladownosc;
    }

    public void setPredkoscMax(double predkoscMax) {
        this.predkoscMax = predkoscMax;
    }
}
