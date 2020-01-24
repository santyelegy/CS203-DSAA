package Tool;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * a minimal heap implement by a dynamic array
 * you can change it to a maximal heap by write the comparator in a different way
 * @param <T>
 */
class BinaryHeap<T> {
    private ArrayList<T> bh;
    private Comparator<T> cp;
    public BinaryHeap(Comparator<T> cp) {
        this.cp=cp;
        bh = new ArrayList<>();
    }
    public BinaryHeap(T[] in,Comparator<T> cp) {
        this.cp=cp;
        makeheap(in);
    }

    private void makeheap(T[] in) {
        for (T i : in) {
            bh.add(i);
        }
        for (int i = in.length; i > 0; i--) {
            rootfix(i);
        }
    }

    /**
     * do rootfix operation on minimal heap
     * @param rootindex the index start to do root fix(start with 1, not 0)
     */
    void rootfix(int rootindex) {
        if (rootindex * 2 > bh.size()) {
        } else if (rootindex * 2 + 1 > bh.size()) {
            T a = bh.get(rootindex - 1);
            T lchild = bh.get(rootindex * 2 - 1);
            if (cp.compare(a,lchild)>0) {
                bh.set(rootindex - 1, lchild);
                bh.set(rootindex * 2 - 1, a);
            }
        } else {
            T a = bh.get(rootindex - 1);
            T lchild = bh.get(rootindex * 2 - 1);
            T rchild = bh.get(rootindex * 2);
            if (cp.compare(a,lchild)<=0) {
                if (cp.compare(a,rchild)>0) {
                    bh.set(rootindex - 1, rchild);
                    bh.set(rootindex * 2, a);
                    rootfix(rootindex * 2 + 1);
                }
            } else {
                if (cp.compare(a,rchild)>0 && cp.compare(rchild,lchild)<0) {
                    bh.set(rootindex - 1, rchild);
                    bh.set(rootindex * 2, a);
                    rootfix(rootindex * 2 + 1);
                } else {
                    bh.set(rootindex - 1, lchild);
                    bh.set(rootindex * 2 - 1, a);
                    rootfix(rootindex * 2);
                }
            }
        }
    }

    public T deletemin() {
        T out = bh.get(0);
        if (bh.size() > 1) {
            bh.set(0, bh.get(bh.size() - 1));
            bh.remove(bh.size() - 1);
            rootfix(1);
        } else {
            bh = new ArrayList<>();
        }
        return out;
    }

    public void insert(T i) {
        bh.add(i);
        int index = bh.size();
        boolean run = true;
        while (index / 2 - 1 >= 0 && run) {
            T parent = bh.get(index / 2 - 1);
            if (cp.compare(parent,i)>0) {
                bh.set(index - 1, parent);
                bh.set(index / 2 - 1, i);
            } else {
                run = false;
            }
            index /= 2;
        }
    }
}