import Pociagi.*;

import java.io.IOException;
import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) {

        ArrayList<Pociag> Pociagi = new ArrayList<Pociag>();

        try{
            FileInputStream fis = new FileInputStream("Pociagi.txt");
            ObjectInputStream ois = new ObjectInputStream(fis);

            Pociagi = (ArrayList) ois.readObject();


        }catch (FileNotFoundException e){
            System.err.println("Nie ma takiego pliku");
        }catch (IOException e){
            e.printStackTrace();
        }catch (ClassNotFoundException e){
            System.out.println("Nie znaleziono takiej klasy");
            e.printStackTrace();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        Scanner scan = new Scanner(System.in);

        boolean korzystanieKonsola = true;

        while(korzystanieKonsola){

            System.out.println("Jaka operacje chciał byś wykonać \n 1) Dodać pociąg \n 2) Wypisać wszystkie pociągi \n 3) Zakupić bilet \n 4) Sprawdzić czy bilet istnieje \n 5) Skończyć korzystać z aplikacji");

            String wybor1 = scan.next();

            switch (wybor1){
                case "1":
                    try{
                        System.out.println("Podaj dane pociągu");
                        System.out.print("id: "); int id = scan.nextInt();
                        check(Pociagi, id);
                        System.out.print("Nazwa: "); String nazwa = scan.nextLine() + scan.nextLine();
                        check(Pociagi, nazwa);
                        System.out.print("ilość wagonów: "); int dlugoscSkladu = scan.nextInt();
                        System.out.print("maksymalna ilość miejsc: "); int ladownosc = scan.nextInt();
                        System.out.print("maksymalna prędkość: "); double predkoscMax = scan.nextDouble();

                        System.out.println("\n Jakiego typu ma być to pociąg \n 1) Nocny \n 2) Pospieszny \n 3) Prywatny \n 4) Regionalny \n 5) Sluzbowy");

                        String rodzajPociagu = scan.nextLine() + scan.nextLine();
                        switch (rodzajPociagu){
                        case "1", "Nocny" -> Pociagi.add(new Nocny(id, nazwa, dlugoscSkladu, ladownosc, predkoscMax));
                        case "2", "Pospieszny" -> Pociagi.add(new Pospieszny(id, nazwa, dlugoscSkladu, ladownosc, predkoscMax));
                        case "3", "Prywatny" -> Pociagi.add(new Prywatny(id, nazwa, dlugoscSkladu, ladownosc, predkoscMax));
                        case "4", "Regionalny" -> Pociagi.add(new Regionalny(id, nazwa, dlugoscSkladu, ladownosc, predkoscMax));
                        case "5", "Sluzbowy" -> Pociagi.add(new Sluzbowy(id, nazwa, dlugoscSkladu, ladownosc, predkoscMax));
                        default -> throw new IllegalStateException("Błędna dana " + rodzajPociagu);
                        }
                        System.out.println("Udało się dodać pociąg");
                    }catch(InputMismatchException e){
                        System.out.println("Podałeś błędne dane");
                    }catch (IllegalStateException e){
                        System.out.println(e.getMessage());
                    }catch(Exception e){
                        System.out.println(e.getMessage());
                    }
                    break;

                case "2":
                    System.out.println("\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
                    for (Pociag e : Pociagi) {
                        String typPociagu = String.valueOf(e.getClass());
                        typPociagu = typPociagu.substring(typPociagu.indexOf(".") + 1, typPociagu.length());
                        System.out.println("Pociąg " + typPociagu + ": " + e);
                    }
                    break;

                case "3":
                    System.out.println("\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
                    try {
                        HashMap<Pociag, Double> pociagiBilety = new HashMap<Pociag, Double>();
                        for (Pociag e : Pociagi) {

                            double zajeteBilety = 0;
                            String typPociagu = String.valueOf(e.getClass());
                            boolean sprSluzbowy = false;
                            switch (typPociagu) {
                                case "class Pociagi.Nocny" -> {
                                    zajeteBilety = ((Nocny) (e)).countZakupione();
                                    pociagiBilety.put(e, zajeteBilety / e.getLadownosc() * 100);
                                }
                                case "class Pociagi.Pospieszny" -> {
                                    zajeteBilety = ((Pospieszny) (e)).countZakupione();
                                    pociagiBilety.put(e, zajeteBilety / e.getLadownosc() * 100);
                                }
                                case "class Pociagi.Prywatny" -> {
                                    zajeteBilety = ((Prywatny) (e)).countRezerwacja();
                                    pociagiBilety.put(e, zajeteBilety / e.getLadownosc() * 100);
                                }
                                case "class Pociagi.Regionalny" -> {
                                    zajeteBilety = ((Regionalny) (e)).countZakupione();
                                    pociagiBilety.put(e, zajeteBilety / e.getLadownosc() * 100);
                                }
                                case "class Pociagi.Sluzbowy" -> sprSluzbowy = true;
                            }
                        }

                        ValueComparator sortowanie = new ValueComparator(pociagiBilety);
                        TreeMap<Pociag, Double> posortowanaMapa = new TreeMap<>(sortowanie);
                        posortowanaMapa.putAll(pociagiBilety);

                        System.out.println("W którym pociągu chciał byś zakupić bilet(Prosze o wpisanie pełnej nazwy):");

                        showHashMap(posortowanaMapa);
                        String nazwaIndeksu = scan.nextLine() + scan.nextLine();

                        System.out.println("Proszę o podanie swojego imienia i nazwiska");
                        String imieNazwisko = scan.nextLine();
                        imieNazwisko = imieNazwisko.replaceAll("\\s+","");

                        System.out.println("Jaką ilość biletów zostanie zakupiona?");
                        int iloscBiletow = scan.nextInt();

                        for (Map.Entry<Pociag, Double> e : pociagiBilety.entrySet()) {
                            String nazwaPociagu = String.valueOf(e.getKey().getNazwa());

                            if (nazwaIndeksu.equals(nazwaPociagu)) {

                                if(iloscBiletow > (e.getKey().getLadownosc() - e.getValue()/100*e.getKey().getLadownosc()))throw new brakMiejsc();

                                String typPociagu = String.valueOf(e.getKey().getClass());
                                switch (typPociagu) {
                                    case "class Pociagi.Nocny" -> {
                                        Nocny pociagKupnoBiletu = (Nocny) e.getKey();
                                        pociagKupnoBiletu.addZakupione(imieNazwisko, iloscBiletow);
                                    }
                                    case "class Pociagi.Pospieszny" -> {
                                        Pospieszny pociagKupnoBiletu = (Pospieszny) e.getKey();
                                        pociagKupnoBiletu.addZakupione(imieNazwisko, iloscBiletow);
                                    }
                                    case "class Pociagi.Prywatny" -> {
                                        Prywatny pociagKupnoBiletu = (Prywatny) e.getKey();
                                        pociagKupnoBiletu.addZakupione(imieNazwisko, iloscBiletow);
                                    }
                                    case "class Pociagi.Regionalny" -> {
                                        Regionalny pociagKupnoBiletu = (Regionalny) e.getKey();
                                        pociagKupnoBiletu.addZakupione(imieNazwisko, iloscBiletow);
                                    }
                                    default -> throw new IllegalStateException("Błędna dana " + nazwaPociagu);
                                }

                            }

                        }

                    }catch(InputMismatchException e){
                        System.out.println("Podałeś błędne dane");
                    }catch(brakMiejsc e){
                        System.out.println(e.getMessage());
                    }catch(Exception e){
                        System.out.println(e.getMessage());
                    }

                    break;

                case "4":
                    System.out.println("Podaj id biletu");
                    String idBiletu = scan.nextLine()+scan.nextLine();
                    String idPociagu = idBiletu.replaceAll("\\D+","");

                    for (Pociag e: Pociagi) {
                        if(e.getId()==Integer.parseInt(idPociagu)){
                            String klasaPociagu = String.valueOf(e.getClass());
                            switch (klasaPociagu) {
                                case "class Pociagi.Nocny" -> {
                                    Nocny nocny = (Nocny)e;
                                    nocny.checkBiletR(nocny.getRezerwacja(), idBiletu);
                                }
                                case "class Pociagi.Pospieszny" -> {
                                    ((Pospieszny) e).checkBiletR(((Pospieszny) e).getRezerwacja(), idBiletu);
                                }
                                case "class Pociagi.Prywatny" -> {
                                    ((Prywatny) e).checkBiletR(((Prywatny) e).getRezerwacja(), idBiletu);
                                }
                                case "class Pociagi.Regionalny" -> {
                                    ((Regionalny) e).checkBiletZ(((Regionalny) e).getZakupione(), idBiletu);
                                }

                            }
                        }
                    }
                    break;

                case "5":

                    korzystanieKonsola=false;
                    break;

                default:
                    System.out.println("Błędna dana");
            }

            System.out.println("\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");

        }

        try{
            FileOutputStream fos = new FileOutputStream("Pociagi.txt");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(Pociagi);
            oos.close();
            fos.close();
        }catch (FileNotFoundException e){
            System.err.println("Nie ma takiego pliku");
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    public static void check(ArrayList<Pociag> lista, int id) throws PowtorzenieWartosci {
        boolean spr = false;
        for (Pociag e : lista) {
            if(e.getId() == id) spr=true;
        }
        if(spr)throw new PowtorzenieWartosci("id");
    }

    public static void check(ArrayList<Pociag> lista, String name) throws PowtorzenieWartosci {
        boolean spr = false;
        for (Pociag e : lista) {
            if(e.getNazwa() == name) spr=true;
        }
        if(spr)throw new PowtorzenieWartosci("nazwa");
    }


    private static void showHashMap(Map<Pociag, Double> map) {
        for (Map.Entry<Pociag, Double> e : map.entrySet()) {
            System.out.println(e.getKey().getNazwa() + " - " + e.getValue() + "% zatłoczenia ; " + (e.getKey().getLadownosc() - e.getValue()/100*e.getKey().getLadownosc()) + " Wolne miejsca");
        }
    }

}