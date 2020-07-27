package Pociagi;

import Pociagi.Interface.Rezerwacja;

import java.util.*;

public class Prywatny extends Pociag implements Rezerwacja {
    HashMap<String, Bilet> rezerwacja = new HashMap<String, Bilet>();

    public Prywatny(int id, String nazwa, int dlugoscSkladu, int ladownosc, double predkoscMax) {
        super(id, nazwa, dlugoscSkladu, ladownosc, predkoscMax, false, true);
    }

    public HashMap getRezerwacja(){
        return rezerwacja;
    }

    public void addZakupione(String name, int iloscMiejsc){
        System.out.println("Jakiego typu chciał byś mieć przynajmniej jeden bilet: \n 1) Przy oknie \n 2) Na środku \n 3) Przy korytarzu");
        Scanner scan = new Scanner(System.in);

        typRezerwacji typMiejsca = typRezerwacji.Okno;
        boolean spr = true;
        while (spr){
            String typMiejscaUzytkownik = scan.nextLine();
            switch(typMiejscaUzytkownik){
                case "1":
                case "Przy oknie":
                    typMiejsca = typRezerwacji.Okno;
                    spr=false;
                    break;
                case "2":
                case "Na środku":
                    typMiejsca = typRezerwacji.Srodek;
                    spr=false;
                    break;
                case "3":
                case "Przy korytarzu":
                    typMiejsca = typRezerwacji.Korytarz;
                    spr=false;
                    break;
                default:
                    System.out.println("Błędna wartość");
            }}
        newRezerwacja(name, iloscMiejsc, getRezerwacja(), typMiejsca, getLadownosc(), getId(), getNazwa());
    };
}
