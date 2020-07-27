import Pociagi.Pociag;

import java.util.Comparator;
import java.util.Map;

public class ValueComparator implements Comparator<Pociag> {
    Map<Pociag, Double> mapa;
    public ValueComparator(Map<Pociag, Double> mapa) {
        this.mapa = mapa;
    }

    public int compare(Pociag a, Pociag b){
        if(mapa.get(a)>= mapa.get(b)){
            return 1;
        }else{
            return -1;
        }
    }
}
