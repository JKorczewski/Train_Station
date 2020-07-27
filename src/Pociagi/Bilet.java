package Pociagi;

import java.io.Serializable;
import java.util.ArrayList;

public class Bilet implements Serializable {

    private String idBiletu;
    private String imie;
    private int idPociagu;
    private int iloscBiletow;
    private ArrayList<String> bilety;

    public Bilet(String imie, int idPociagu, int iloscBiletow) {
        this.imie = imie;
        this.idPociagu = idPociagu;
        this.iloscBiletow = iloscBiletow;
        this.idBiletu = Integer.toString(idPociagu)+imie;
    }

    public String getIdBiletu() {
        return idBiletu;
    }

    public ArrayList<String> getBilety() {
        return bilety;
    }

    public int getIloscBiletow() {
        return iloscBiletow;
    }

    public void setBilety(ArrayList<String> bilety) {
        this.bilety = bilety;
    }

    public void setIloscBiletow(int iloscBiletow) {
        this.iloscBiletow = iloscBiletow;
    }
}
