package Tool;

import java.util.ArrayList;

public class ListNode<T> implements Node{
    private T value;
    private ListNode<T> next;
    private ListNode<T> former;

    public ListNode( T value, ListNode<T> former) {
        this.value = value;
        this.former = former;
    }
    public T getKey(){
        return value;
    }
    public ArrayList<ListNode<T>> getChild(){
        ArrayList<ListNode<T>> out=new ArrayList<>();
        out.add(next);
        return out;
    }
    public ListNode<T> getParent(){
        return former;
    }
    public void setFather(ListNode<T> father){
        former=father;
    }
    public void setNext(ListNode<T> l){
        next=l;
    }
}
