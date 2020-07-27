package Pociagi.Interface;

public class brakDanegoMiejsca extends Exception {
    public brakDanegoMiejsca(String string) {
        super("Nie ma już miejsca " + string);
    }
}
