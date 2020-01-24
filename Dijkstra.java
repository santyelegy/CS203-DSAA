import java.util.ArrayList;
import java.util.Comparator;

public class Dijkstra {
    private ArrayList<Vertex> vertices=new ArrayList<>();
    ArrayList<Struct> structs=new ArrayList<>();
    public void build(int vertexNum,int edgenum,int start,int[][] edgeinfor){
        for(int i=1;i<=vertexNum;i++){
            vertices.add(new Vertex(i));
        }
        int[] infor;
        for(int i=0;i<edgenum;i++){
            infor=edgeinfor[i];
            Edge e=new Edge(infor[2],vertices.get(infor[0]-1),vertices.get(infor[1]-1));
            vertices.get(infor[0]-1).connect.add(e);
        }
        for(Vertex v:vertices){
            Struct s=new Struct();
            s.dist=2147483647;
            s.v=v;
            v.self=s;
            structs.add(s);
        }
        dijikstra(start);
    }
    private void dijikstra(int startindex){
        Struct first=structs.get(startindex);
        first.visited=true;
        first.dist=0;
        Struct tar;
        ListHeap bh=new ListHeap(comparator);//structs
        for(Struct s:structs){
            bh.insert(s);
        }
        while (bh.getSize()>0){
            tar=bh.deletemin();
            tar.visited=true;
            for(Edge e:tar.v.connect){
                if(e.to.self.visited){
                    continue;
                }
                long length=e.to.self.dist;
                if(e.weight<length){
                    e.to.self.dist=e.weight;
                    e.to.self.parent=tar.v;
                    bh.upfix(e.to.self.place);
                }
            }
        }
    }
    private Comparator<Struct> comparator=new Comparator<Struct>() {
        @Override
        public int compare(Struct o1, Struct o2) {
            if(o1.dist>o2.dist){
                return 1;
            }else if(o1.dist==o2.dist){
                return 0;
            }else {
                return -1;
            }
        }
    };
    class Struct{
        Vertex v;
        long dist;
        Vertex parent;
        boolean visited=false;
        innerNode place;
    }
    class Vertex{
        int index;
        ArrayList<Edge> connect;
        Struct self;
        Vertex(int index){
            this.index=index;
            connect=new ArrayList<>();
        }
    }
    class Edge{
        Vertex from;
        Vertex to;
        int weight;
        Edge(int weight,Vertex from,Vertex to){
            this.weight=weight;
            this.from=from;
            this.to=to;
        }
    }
    class ListHeap {//基于链表实现小顶堆
        private innerNode first;
        private Comparator<Struct> compar;
        private int size;
        public ListHeap(Comparator<Struct> p){
            compar=p;
        }
        public Struct deletemin(){
            if(size>1){
                Struct deleter=first.key;
                innerNode lastp=getLastparent(size);
                if(size%2==0){
                    first.key=lastp.lchild.key;
                    first.key.place=first;
                    lastp.lchild.parent=null;
                    lastp.lchild=null;
                }else {
                    first.key=lastp.rchild.key;
                    first.key.place=first;
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
                Struct out=first.key;
                first=null;
                return out;
            }
        }
        public int getSize(){
            return size;
        }
        public Struct getmin(){
            return first.key;
        }
        public void insert(Struct in){
            if(size>0) {
                innerNode parent = getLastparent(size + 1);
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
        private innerNode getLastparent(int size){//是left是right可以通过奇偶判断
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
        public void upfix(innerNode in){
            if(in.parent==null){
                return;
            }
            if(compar.compare(in.key,in.parent.key)<0){
                Struct mid=in.key;
                in.key=in.parent.key;
                in.key.place=in;
                in.parent.key=mid;
                in.parent.key.place=in.parent;
                upfix(in.parent);
            }
        }
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
                    Struct mid=in.key;
                    in.key=in.rchild.key;
                    in.key.place=in;
                    in.rchild.key=mid;
                    in.rchild.key.place=in.rchild;
                    downfix(in.rchild);
                }
            }else {
                if(compar.compare(in.key,in.lchild.key)>0){
                    Struct mid=in.key;
                    in.key=in.lchild.key;
                    in.key.place=in;
                    in.lchild.key=mid;
                    in.lchild.key.place=in.lchild;
                    downfix(in.lchild);
                }
            }
        }
    }
    class innerNode{
        innerNode parent,lchild,rchild;
        Struct key;
        innerNode(Struct in){
            key=in;
            in.place=this;
        }
    }
}
