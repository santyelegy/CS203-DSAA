package Tool;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * implement a minimal heap using pointer(reference)
 * aim to use it in Dijkstra algorithm,change the vertex's position after their value changed
 * though after finish writing this I found out that it is also not so easy to use it in Dijkstra
 * You can change it into a maximal heap simply by rewrite the comparator to a reverse way
 * @param <T> T is a template you wan't to put and you need to prepare a comparator
 */
public class ListHeap<T> {
    private innerNode first;
    private Comparator<T> compar;
    private int size;
    public ListHeap(Comparator<T> p){
        compar=p;
    }
    public T deletemin(){
        if(size>1){
            T deleter=first.key;
            innerNode lastp=getparent(size);
            if(size%2==0){
                first.key=lastp.lchild.key;
                lastp.lchild.parent=null;
                lastp.lchild=null;
            }else {
                first.key=lastp.rchild.key;
                lastp.rchild.parent=null;
                lastp.rchild=null;
            }
            downfix(first);
            size--;
            return deleter;
        }else if(size==0){
            return null;
        }else {
            size--;
            T out=first.key;
            first=null;
            return out;
        }
    }
    public int getSize(){
        return size;
    }
    public T getmin(){
        return first.key;
    }
    public void insert(T in){
        if(size>0) {
            innerNode parent = getparent(size + 1);
            innerNode insert = new innerNode(in);
            insert.parent = parent;
            if ((size + 1) % 2 == 0) {
                parent.lchild = insert;
            } else {
                parent.rchild = insert;
            }
            upfix(insert);
            size++;
        }else {
            first=new innerNode(in);
            size++;
        }
    }

    /**
     * thanks to the property of full binary heap,it can find a node's parent by the node's index
     * convert the index to binary ,such that 9 -> 1001, in the sentence 1 and 0 represent how to find this node
     * how will you move start from the root.reverse it and ignore the first 1,the rest:1 means to left,0 means to right.
     * @param size the node's index you wan't to find it's parent(the index start with 1, not 0)
     * @return a node that is the given index's parent
     */
    private innerNode getparent(int size){
        ArrayList<Boolean> operation=new ArrayList<>();
        int copy=size;
        while (copy>0){
            operation.add(copy%2==1);
            copy/=2;
        }
        innerNode tar=first;
        for(int i=operation.size()-2;i>0;i--){
            if(operation.get(i)){
                tar=tar.lchild;
            }else {
                tar=tar.rchild;
            }
        }
        return tar;
    }

    /**
     * when a node's value change or it is not in the right place in minimal heap,move it to root
     * @param in the value-changed-node
     */
    public void upfix(innerNode in){
        if(in.parent==null){
            return;
        }
        if(compar.compare(in.key,in.parent.key)<0){
            T mid=in.key;
            in.key=in.parent.key;
            in.parent.key=mid;
            upfix(in.parent);
        }
    }

    /**
     * similar to upfix, move the node to leaf
     * @param in the value-changed-node
     */
    public void downfix(innerNode in){
        boolean leftbig;
        if(in.rchild!=null&&in.lchild!=null) {
            leftbig = compar.compare(in.lchild.key, in.rchild.key) >0;
        }else if(in.lchild!=null){
            leftbig=false;
        }else {
            return;
        }
        if(leftbig){
            if(compar.compare(in.key,in.rchild.key)>0){
                T mid=in.key;
                in.key=in.rchild.key;
                in.rchild.key=mid;
                downfix(in.rchild);
            }
        }else {
            if(compar.compare(in.key,in.lchild.key)>0){
                T mid=in.key;
                in.key=in.lchild.key;
                in.lchild.key=mid;
                downfix(in.lchild);
            }
        }
    }
    class innerNode{
        innerNode parent,lchild,rchild;
        T key;
        innerNode(T in){
            key=in;
        }
    }
}