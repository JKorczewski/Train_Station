package Pociagi.Interface;

import Pociagi.Bilet;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public interface Rezerwacja {
    public HashMap<String, Bilet> getRezerwacja();
    public default void newRezerwacja(String name, int ilosMiejsc, HashMap<String, Bilet> Zarezerwowane, typRezerwacji typMiejsca, int ladownosc, int idPociagu, String nazwaPociagu){
        Scanner scan = new Scanner(System.in);
        try{
        ArrayList<Integer> zajete = new ArrayList<Integer>();
        for (Map.Entry<String, Bilet> e : Zarezerwowane.entrySet()) {
            for(int i = 0; i<e.getValue().getBilety().size() ; i++){
                String rezerwacja = e.getValue().getBilety().get(i);
                    zajete.add(Integer.parseInt(rezerwacja.substring(0, (rezerwacja.length()-1))));
            }
        }

        int miejsce = 0;
        switch(typMiejsca){
            case Okno:
                miejsce = 1;
                break;
            case Srodek:
                miejsce = 2;
                break;
            case Korytarz:
                miejsce = 3;
                break;
        }

        ArrayList<Integer> wolneMiejsca = new ArrayList<Integer>();

        boolean szukaneMiejsce = false;

        for(; miejsce<=ladownosc && !szukaneMiejsce ;){
            boolean szukanyDuplikat = false;
            for(int j = 0; j<zajete.size() && !szukanyDuplikat ;j++){
                if(zajete.get(j)==miejsce){
                    szukanyDuplikat=true;
                }
            }
            if(!szukanyDuplikat){
                szukaneMiejsce = true;
            }
            if(!szukaneMiejsce)miejsce+=3;
        }


        if(szukaneMiejsce){

            wolneMiejsca.add(miejsce);
            int duplikaty = 0;
            while(ilosMiejsc != wolneMiejsca.size()){

                boolean resztaMiejscUp = false;
                boolean resztaMiejscDown = false;

                for (int i = miejsce + wolneMiejsca.size() + duplikaty; i < ladownosc && !resztaMiejscUp; i++) {
                    boolean szukanyDuplikat = false;
                    for(int j = 0; j<zajete.size() && !szukanyDuplikat ;j++){
                        if(zajete.get(j)==i){
                            szukanyDuplikat=true;
                            duplikaty++;
                        }
                    }
                    if(!szukanyDuplikat){
                        resztaMiejscUp = true;
                        wolneMiejsca.add(i);
                    }
                }

                for (int i = miejsce - wolneMiejsca.size() - duplikaty; i < ladownosc && !resztaMiejscDown && ilosMiejsc != wolneMiejsca.size() && i>0; i--) {
                    boolean szukanyDuplikat = false;
                    for(int j = 0; j<zajete.size() && !szukanyDuplikat ;j++){
                        if(zajete.get(j)==i){
                            szukanyDuplikat=true;
                            duplikaty++;
                        }
                    }
                    if(!szukanyDuplikat && i!=0){
                        resztaMiejscDown = true;
                        wolneMiejsca.add(i);
                    }
                }
            }

            wolneMiejsca.sort(Integer::compareTo);

            ArrayList<String> nowyBilet = new ArrayList<>();

            for(int i = 0; i<wolneMiejsca.size() ;i++){
                if(wolneMiejsca.get(i)%3==1){
                    nowyBilet.add(wolneMiejsca.get(i)+"O");
                }else{
                    if(wolneMiejsca.get(i)%3==2){
                        nowyBilet.add(wolneMiejsca.get(i)+"S");
                    }else{
                        nowyBilet.add(wolneMiejsca.get(i)+"K");
                    }
                }

            }
            System.out.println("Zakupiłeś bilety z miejscami: ");
            for (String e: nowyBilet) {
                System.out.print(e + ", ");
            }
            System.out.println();


            Bilet tworzenieBiletu = new Bilet(name, idPociagu, ilosMiejsc);
            System.out.println("Id twojego biletu to: "+tworzenieBiletu.getIdBiletu());
            tworzenieBiletu.setBilety(nowyBilet);


            if(checkName(name)){
                for (Map.Entry<String, Bilet> e : getRezerwacja().entrySet()) {
                    if(e.getKey().equals(name)){
                        String nazwaPliku =e.getValue().getIdBiletu();
                        String uszupelnienie = "IdBiletu:"+e.getValue().getIdBiletu()+"; Nazwa pociągu: "+nazwaPociagu+" ; Twoje zarezerwowane miejsca: "+String.join("; ", e.getValue().getBilety())+
                                "\nDodane miejsca: "+String.join("; ", tworzenieBiletu.getBilety());
                        ArrayList<String> laczenie = e.getValue().getBilety();
                        laczenie.addAll(nowyBilet);
                        e.getValue().setBilety(laczenie);
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
            }else{
                Zarezerwowane.put(name, tworzenieBiletu);
                System.out.println("Czy chciał byś wygenerować plik z danymi biletu?: \n 1) TAK \n 2) NIE");
                String biletTxt = scan.nextLine();
                if(biletTxt.equals("TAK")||biletTxt.equals("1")){
                    String nazwaPliku =tworzenieBiletu.getIdBiletu();
                    String daneBiletu = "IdBiletu:"+tworzenieBiletu.getIdBiletu()+"; Nazwa pociągu: "+nazwaPociagu+" ; Twoje zarezerwowane miejsca: "+String.join("; ", tworzenieBiletu.getBilety());

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



        }else{
            throw new brakDanegoMiejsca(typMiejsca.getTypMiejsca());
        }
        }catch (brakDanegoMiejsca e){
            System.out.println(e.getMessage());
        }
    };

    public default void changeTxtR(String name, Bilet bilet, String nazwaPociagu){

    };

    public default void checkBiletR(HashMap<String, Bilet> Zarezerwowane, String idBiletu){
        boolean istnieje = false;
        for (Map.Entry<String, Bilet> e : Zarezerwowane.entrySet()) {
                if(e.getValue().getIdBiletu().equals(idBiletu)) {
                    System.out.println("Istnieje");
                    istnieje = true;
                }
        }
        if(!istnieje)System.out.println("Podałeś złe dane lub bilet nie istnieje");

    };

    public default int countRezerwacja(){

        HashMap<String, Bilet> zakupione = getRezerwacja();
        int ilosZarezerwowanychchBiletow = 0;
        for (Map.Entry<String, Bilet> e : zakupione.entrySet()) {
            ilosZarezerwowanychchBiletow += e.getValue().getBilety().size();
        }
        return ilosZarezerwowanychchBiletow;
    };

    public default boolean checkName(String name){
        HashMap<String, Bilet> zakupione = getRezerwacja();
        for (Map.Entry<String, Bilet> e : zakupione.entrySet()) {
            if(e.getKey().equals(name))return true;
        }
        return false;
    }

    public enum typRezerwacji{
        Okno("Okno"),
        Srodek("Srodek"),
        Korytarz("Korytarz");

        String typMiejsca;

        typRezerwacji(String typMiejsca) {
            this.typMiejsca = typMiejsca;
        }

        public String getTypMiejsca() {
            return typMiejsca;
        }
    }

}
