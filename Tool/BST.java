package Tool;

import java.util.Comparator;

public class BST<T> {
    Comparator<T> cp;
    BinaNode<T> root;

    public BST(Comparator<T> cp){
        this.cp=cp;
    }

    public void buildtree(T[] in){
        for(T i:in){
            insert(i);
        }
    }

    public void insert(T tar){
        insert(new BinaNode<>(tar));
    }

    public void insert(BinaNode<T> b){
        insert(b,root);
    }

    public void insert(BinaNode<T> b,BinaNode<T> root){
        if(this.root==null){
            this.root=b;
            return;
        }
        BinaNode<T> p=search(b.getKey(),true,root);
        if(p!=null){
            if(cp.compare(p.getKey(),b.getKey())==0) {
                return;
            }
        }
        if(cp.compare(b.getKey(),root.getKey())<0){
            toNext(true,root,b);
        }else{
            toNext(false,root,b);
        }
    }

    public BinaNode<T> delete(T tar){
        BinaNode<T> deleter=search(tar,true,root);
        if(deleter==null){
            return null;
        }else {
            return delete(deleter);
        }
    }

    public BinaNode<T> delete(BinaNode<T> deleter){
        T tar=deleter.getKey();
        if(deleter.left==null&&deleter.right==null){  //case1
            if(deleter.father!=null) {
                if (deleter.isleft) {
                    deleter.father.left = null;
                } else {
                    deleter.father.right = null;
                }
            }else {
                clear();
            }
            return deleter;
        }else if(deleter.right==null){      //case3
            BinaNode<T> lchild=deleter.left;
            lchild.isleft=deleter.isleft;
            lchild.father=deleter.father;
            if(deleter.father!=null) {
                if (deleter.isleft) {
                    deleter.father.left = lchild;
                } else {
                    deleter.father.right = lchild;
                }
            }else {
                root=lchild;
            }
            return deleter;
        }else {                //case2
            BinaNode<T> successor=noRootSearch(tar,true,deleter);
            deleter.setKey(successor.getKey());
            if(successor.isleft) {
                successor.father.left = null;
                if(successor.right!=null){
                    successor.father.left=successor.right;
                    successor.right.father=successor.father;
                    successor.right.isleft=true;
                }
            }else {
                successor.father.right=null;
                if(successor.right!=null){
                    successor.father.right=successor.right;
                    successor.right.father=successor.father;
                    successor.right.isleft=false;
                }
            }
            return successor;
        }}

    public BinaNode<T> noRootSearch(T target,boolean returnSucce,BinaNode<T> r){
        return search(target,returnSucce,cp.compare(target,r.getKey())>=0?r.right:r.left);
    }

    public BinaNode<T> search(T target,boolean returnSucce,BinaNode<T> r){
        BinaNode<T> a=r;
        BinaNode<T> prede=null;
        BinaNode<T> succe=null;
        while (a!=null) {
            if (cp.compare(a.getKey(),target)<0) {
                prede=a;
                a=a.right;
            } else if (cp.compare(a.getKey(),target)>0) {
                succe=a;
                a=a.left;
            }else {
                return a;
            }
        }
        return returnSucce?succe:prede;
    }

    public T searchIndex(int index,BinaNode<T> subroot){
        if(subroot==null){
            return null;
        }
        if(subroot.left!=null){
            if(subroot.left.size==index-1){
                return subroot.getKey();
            }else if(index<=subroot.left.size){
                return searchIndex(index,subroot.left);
            }else {
                return searchIndex(index-subroot.left.size-1,subroot.right);
            }
        }else if(index==1){
            return subroot.getKey();
        }else {
            return searchIndex(index-1,subroot.right);
        }
    }

    void clear(){
        root=null;
    }

    private void toNext(boolean isleft,BinaNode<T> root,BinaNode<T> b){
        BinaNode<T> direction=isleft?root.left:root.right;
        if(direction==null){
            if(isleft)root.left=b;else root.right=b;
            b.father=root;
            b.isleft=isleft;
        }else {
            insert(b,direction);
        }
    }
}
