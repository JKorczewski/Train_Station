public class PowtorzenieWartosci extends Exception {
    PowtorzenieWartosci(String rodzajZmiennej){
        super("Pociąg o takim " + rodzajZmiennej + " już istnieje");
    }
}
