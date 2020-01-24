package Tool;
import java.util.ArrayList;
import java.util.Comparator;

public class MergeSort<T> {
    private Comparator<T> cp;
    public MergeSort(Comparator<T> cp){
        this.cp=cp;
    }
    public ArrayList<T> sort(ArrayList<T> input,int length){
        if(length>1){
            int p=length/2;
            ArrayList<T> B=new ArrayList<>();
            ArrayList<T> C=new ArrayList<>();
            for(int i=0;i<p;i++){
                B.add(input.get(i));
            }
            for(int i=0;i<length-p;i++){
                C.add(input.get(i+p));
            }
            B=sort(B,p);
            C=sort(C,length-p);
            input=merge(B,p,C,length-p);
        }
        return input;
    }
    private ArrayList<T> merge(ArrayList<T> B,int p,ArrayList<T> C,int l){
        int n=p+l;
        ArrayList<T> A=new ArrayList<>();
        int i=0;
        int j=0;
        for(int k=0;k<n;k++){
            if(i<p&&(j>=l||cp.compare(B.get(i),C.get(i))<=0)){
                A.add(B.get(i));
                i++;
            }else {
                A.add(C.get(j));
                j++;
            }
        }
        return A;
    }
}
