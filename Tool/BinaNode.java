package Tool;

import java.util.ArrayList;

public class BinaNode<T> implements Node {
    private T key;
    int size;
    int[] height=new int[2];
    BinaNode<T> father;
    BinaNode<T> left;
    BinaNode<T> right;
    boolean isleft;
    BinaNode(T k){
        key=k;
    }
    public T getKey(){
        return key;
    }
    public void setKey(T key){
        this.key=key;
    }
    int getheight(){
        return height[0]>height[1]?height[0]:height[1];
    }
    public ArrayList<BinaNode<T>> getChild(){
        ArrayList<BinaNode<T>> out=new ArrayList<>();
        out.add(left);
        out.add(right);
        return out;
    }
    public BinaNode<T> getParent(){
        return father;
    }
}
