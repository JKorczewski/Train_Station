package Pociagi;

import Pociagi.Interface.Zakupione;
import java.util.*;

public class Regionalny extends Pociag implements Zakupione {
    HashMap<String, Bilet> zakupione = new HashMap<String, Bilet>();

    public Regionalny(int id, String nazwa, int dlugoscSkladu, int ladownosc, double predkoscMax) {
        super(id, nazwa, dlugoscSkladu, ladownosc, predkoscMax, true, false);
    }

    public HashMap getZakupione(){
        return zakupione;
    }

    public void addZakupione(String name, int iloscMiejsc){
        if(checkNameZ(name)){
            changeIloscBiletow(iloscMiejsc, name);
            for (Map.Entry<String, Bilet> e : zakupione.entrySet()) {
                if(e.getKey().equals(name)){
                    writeTXTreplay(name, e.getValue(), getNazwa());
                }
            }

        }else{
            Bilet bilet = new Bilet(name, getId(), iloscMiejsc);
            System.out.println("ID twojego biletu to: " + bilet.getIdBiletu());
            getZakupione().put(name, bilet);
            writeTXTnew(name, bilet, getNazwa());
        }
    };
}
