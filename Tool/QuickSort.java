package Tool;

import java.util.ArrayList;
import java.util.Comparator;

public class QuickSort<T> {
    Comparator<T> cp;
    QuickSort(Comparator<T> cp){
        this.cp=cp;
    }
    public ArrayList<T> sort(ArrayList<T> input, int lo, int hi){
        if(lo>hi){
            return input;
        }
        int p=partition(input,lo,hi);
        sort(input, lo, p-1 );
        sort(input, p+1 , hi);
        return input;
    }
    private int partition(ArrayList<T> input,int lo,int hi){
        int p=(int)(Math.random()*(hi-lo))+lo;
        T pivort=input.get(p);
        ArrayList<T> small=new ArrayList<>();
        ArrayList<T> big=new ArrayList<>();
        int L=0;
        for(int i=lo;i<=hi;i++){
            if(i==p){
                continue;
            }
            if(cp.compare(input.get(i),pivort)<=0){
                small.add(input.get(i));
            }else {
                big.add(input.get(i));
            }
        }
        small.add(pivort);
        small.addAll(big);
        for(int i=lo;i<=hi;i++){
            input.set(i,small.get(i-lo));
        }
        return L+lo;
    }
}
