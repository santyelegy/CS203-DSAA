package Tool;
public class KMP {
    int[] nextArray(String input){
        int j=0;
        int k=-1;
        int length=input.length();
        int[] next=new int[length+1];
        next[0]=-1;
        while(j<length){
            if(k==-1||input.charAt(j)==input.charAt(k)){
                next[++j]=++k;
            }else {
                k=next[k];
            }
        }
        return next;
    }
    boolean KMPbool(String target,String search){
        int[] next=nextArray(target);
        int state=0;
        int length=search.length();
        for(int i=0;i<length;i++){
            while(state>0&&target.charAt(state)!=search.charAt(i)){
                state=next[state];
            }
            if(target.charAt(state)==search.charAt(i)) {
                state++;
            }
            if(state==target.length()){
                return true;
            }
        }
        return false;
    }
}
