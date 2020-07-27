package Pociagi.Interface;

import Pociagi.Bilet;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.HashMap;

public interface Zakupione {
    public HashMap<String, Bilet> getZakupione();
    public void addZakupione(String name, int iloscMiejsc);
    public default void showZakupione(){
        HashMap<String, Bilet> zakupione = getZakupione();
        for (Map.Entry<String, Bilet> e : zakupione.entrySet()) {
            System.out.println(e.getKey() + " kupił - " + e.getValue().getIloscBiletow());
        }
    };
    public default int countZakupione(){
        HashMap<String, Bilet> zakupione = getZakupione();
        int ilosZakupionychBiletow = 0;
        for (Map.Entry<String, Bilet> e : zakupione.entrySet()) {
            ilosZakupionychBiletow += e.getValue().getIloscBiletow();
        }
        return ilosZakupionychBiletow;
    };

    public default void checkBiletZ(HashMap<String, Bilet> Zarezerwowane, String idBiletu){
        boolean istnieje = false;
        for (Map.Entry<String, Bilet> e : Zarezerwowane.entrySet()) {
            if(e.getValue().getIdBiletu().equals(idBiletu)) {
                System.out.println("Istnieje");
                istnieje = true;
            }
        }
        if(!istnieje)System.out.println("Podałeś złe dane lub bilet nie istnieje");
    };

    public default void writeTXTnew(String name, Bilet bilet, String nazwaPociagu){
            Scanner scan = new Scanner(System.in);
            System.out.println("Czy chciał byś wygenerować plik z danymi biletu?: \n 1) TAK \n 2) NIE");
            String biletTxt = scan.nextLine();
            if(biletTxt.equals("TAK")||biletTxt.equals("1")){
                String nazwaPliku =bilet.getIdBiletu();
                String daneBiletu = "IdBiletu:"+bilet.getIdBiletu()+"; Nazwa pociągu: "+nazwaPociagu+" ; Twoja ilosc miejsc: "+bilet.getIloscBiletow();

                try{
                    Files.createFile(Paths.get("Bilety/"+nazwaPliku+".txt"));
                    PrintWriter pw = new PrintWriter("Bilety/"+nazwaPliku+".txt");
                    pw.println(daneBiletu);
                    System.out.println("Bilet został wygenerowany");
                    pw.close();
                }catch(IOException e){
                    System.out.println(e.getMessage());
                }
            }

    }

    public default void writeTXTreplay(String name, Bilet bilet, String nazwaPociagu){
            for (Map.Entry<String, Bilet> e : getZakupione().entrySet()) {
                if(e.getKey().equals(name)){
                    String nazwaPliku =bilet.getIdBiletu();
                    String uszupelnienie = "IdBiletu:"+bilet.getIdBiletu()+"; Nazwa pociągu: "+nazwaPociagu+" ; Twoja ilosc miejsc: "+bilet.getIloscBiletow();

                    try{
                        PrintWriter pw = new PrintWriter("Bilety/"+nazwaPliku+".txt");
                        pw.println(uszupelnienie);
                        System.out.println("Bilet został zaktualizowany");
                        pw.close();
                    }catch(IOException io){
                        System.out.println(io.getMessage());
                    }
                }
            }
    }

    public default boolean checkNameZ(String name){
        HashMap<String, Bilet> zakupione = getZakupione();
        for (Map.Entry<String, Bilet> e : zakupione.entrySet()) {
            if(e.getKey().equals(name))return true;
        }
        return false;
    }

    public default void changeIloscBiletow(int iloscBiletow, String name){
        HashMap<String, Bilet> zakupione = getZakupione();
        for (Map.Entry<String, Bilet> e : zakupione.entrySet()) {
            if(e.getKey().equals(name)){
                e.getValue().setIloscBiletow(e.getValue().getIloscBiletow()+iloscBiletow);
            }
        }
    }
}
