package Tool;

import java.util.Comparator;

public class AVLtree<T> extends BST<T>{

    public AVLtree(Comparator<T> cp){
        super(cp);
    }

    public void buildtree(T[] in){
        for(T i:in){
            insert(new BinaNode<>(i));
        }
    }

    /**
     * the following two aim to find successor& predecessor of a target already exist in the tree
     * different from the "search" method ,this function will return the targrt's pre|succ not itself
     * and will return null if target not found
     * @param tar a target already exist in tree
     * @return successor|predecessor
     */
    public BinaNode findSuccessor(T tar){
        BinaNode<T> a=search(tar,true,root);
        if(a==null){return null;}
        if(cp.compare(a.getKey(),tar)==0){
            if(a.right!=null){
                a=a.right;
                while(a.left!=null){
                    a=a.left;
                }
                return a;
            }else {
                while(a.father!=null){
                    if(cp.compare(a.father.getKey(),a.getKey())>0){
                        return a.father;
                    }
                    a=a.father;
                }
                return null;
            }
        }else {
            return a;
        }
    }

    public BinaNode findpredecessor(T tar){
        BinaNode<T> a=search(tar,false,root);
        if(a==null){return null;}
        if(cp.compare(a.getKey(),tar)==0){
            if(a.left!=null){
                a=a.left;
                while(a.right!=null){
                    a=a.right;
                }
                return a;
            }else {
                while(a.father!=null){
                    if(cp.compare(a.father.getKey(),a.getKey())<0){
                        return a.father;
                    }
                    a=a.father;
                }
                return null;
            }
        }else {
            return a;
        }
    }

    public void insert(T i){
        insert(new BinaNode<>(i));
    }

    public void insert(BinaNode<T> b){
        BinaNode copy=b;
        BinaNode<T> inbalan=b;
        super.insert(b,root);
        while(copy.father!=null){
            copy.father.size++;
            copy=copy.father;
        }
        while(b.father!=null){
            if(b.isleft){
                b.father.height[0]++;
                if(b.father.height[0]>b.father.height[1]) {
                    b = b.father;
                }else {break;}
            }else{
                b.father.height[1]++;
                if(b.father.height[1]>b.father.height[0]) {
                    b = b.father;
                }else {break;}
            }
        }
        rebalance(findinbalance(inbalan.father));
    }

    public BinaNode<T> delete(T tar){
        BinaNode<T> deleter=search(tar,true,root);
        if(deleter==null||cp.compare(deleter.getKey(),tar)!=0){
            return null;
        }
        return delete(deleter);
    }

    public BinaNode<T> delete(BinaNode<T> deleter){
        if(root.size==1&&cp.compare(deleter.getKey(),root.getKey())==0){
            clear();
            return deleter;
        }
        BinaNode<T> target=super.delete(deleter);
        BinaNode<T> out=target;
        BinaNode<T> copy=target;
        while(copy.father!=null){
            copy.father.size--;
            copy=copy.father;
        }
        while(target.father!=null){
            if(target.isleft){
                target.father.height[0]--;
                if(target.father.height[0]>=target.father.height[1]) {
                    target = target.father;
                }else {break;}
            }else{
                target.father.height[1]--;
                if(target.father.height[1]>=target.father.height[0]) {
                    target = target.father;
                }else {break;}
            }
        }
        rebalance(findinbalance(out.father));
        return out;
    }

    private BinaNode<T> findinbalance(BinaNode<T> changedroot){
        BinaNode<T> out=null;
        while (changedroot!=null){
            int inbalance=changedroot.height[0]-changedroot.height[1];
            if(inbalance<-1||inbalance>1){
                out=changedroot;
                break;
            }
            changedroot=changedroot.father;
        }
        return out;
    }

    private void rebalance(BinaNode<T> A){
        if(A==null){return;}
        BinaNode<T> parent=A.father;
        if(A.height[0]>A.height[1]){
            if(A.left.height[0]>=A.left.height[1]){
                rotationLeft(A.left);
            }else {
                rotationRight(A.left.right);
                rotationLeft(A.left);
            }
        }else {
            if(A.right.height[1]>=A.right.height[0]){
                rotationRight(A.right);
            }else {
                rotationLeft(A.right.left);
                rotationRight(A.right);
            }
        }
        while (parent!=null){
            int left=parent.left==null?0:parent.left.getheight()+1;
            int right=parent.right==null?0:parent.right.getheight()+1;
            if(parent.height[0]==left&&parent.height[1]==right){
                break;
            }else {
                parent.height[0]=left;
                parent.height[1]=right;
            }
            parent= parent.father;
        }
    }

    private void rotationLeft(BinaNode<T> b){
        BinaNode<T> a=b.father;
        a.left=b.right;
        b.father=a.father;
        if(b.right!=null){
            b.right.father=a;
            b.right.isleft=true;
        }
        rootChange(b,a);
        sizeAndheightChange(a,b,true);
    }

    private void rotationRight(BinaNode<T> b){
        BinaNode<T> a=b.father;
        a.right=b.left;
        b.father=a.father;
        if(b.left!=null){
            b.left.father=a;
            b.left.isleft=false;
        }
        rootChange(b,a);
        sizeAndheightChange(a,b,false);
    }

    private void rootChange(BinaNode<T> b,BinaNode<T> a){
        BinaNode<T> former=b.father;
        if(former!=null){
            if(a.isleft){
                former.left=b;
                b.isleft=true;
            }else {
                former.right=b;
                b.isleft=false;
            }
        }else {root=b;}
    }

    private void sizeAndheightChange(BinaNode a,BinaNode b,boolean isleft){
        if(isleft){
            b.right=a;
            a.father=b;
            a.isleft=false;
        }else {
            b.left=a;
            a.father=b;
            a.isleft=true;
        }
        a.size=(a.left==null?0:a.left.size)+(a.right==null?0:a.right.size)+1;
        b.size=(b.left==null?0:b.left.size)+(b.right==null?0:b.right.size)+1;
        if(a.left!=null){
            a.height[0]=a.left.getheight()+1;
        }else {
            a.height[0]=0;
        }
        if(a.right!=null){
            a.height[1]=a.right.getheight()+1;
        }else {
            a.height[1]=0;
        }
        if(isleft){
            if(b.left!=null){
                b.height[0]=b.left.getheight()+1;
            }else {
                b.height[0]=0;
            }
            b.height[1]=a.getheight()+1;
        }else {
            if(b.right!=null){
                b.height[1]=b.right.getheight()+1;
            }else {
                b.height[1]=0;
            }
            b.height[0]=a.getheight()+1;
        }
    }
}