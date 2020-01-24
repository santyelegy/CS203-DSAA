import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class Prim {
    private ArrayList<Vertex> vertices=new ArrayList<>();
    private Edge[] edges;
    private ArrayList<Struct> structs=new ArrayList<>();
    private ListHeap pq;
    public void build(int vertexNum,int edgenum,int[][] edgeinfor){
        for(int i=0;i<vertexNum;i++){
            vertices.add(new Vertex(i+1));
        }
        edges=new Edge[edgenum];
        for(int i=0;i<edgenum;i++){
            Edge e=new Edge(edgeinfor[i][2],edgeinfor[i][0]-1,edgeinfor[i][1]-1);
            edges[i]=e;
        }
        Arrays.sort(edges,edgecp);
        for(Edge e:edges){
            e.first.connect.add(e);
            e.second.connect.add(e);
        }
        for(Vertex v:vertices){
            Struct s=new Struct(v);
            s.dist=2147483647;
            structs.add(s);
        }

    }
    public long prim(){
        pq=new ListHeap(comparator);
        Struct a=edges[0].first.self;
        Struct b=edges[0].second.self;
        long cost=edges[0].weight;
        a.visited=true;
        b.visited=true;
        for(Struct s:structs){
            if(s!=a&&s!=b){
                pq.insert(s);
            }
        }
        fit(a);
        fit(b);
        while(pq.size>0){
            Struct out=pq.deletemin();
            out.visited=true;
            if(out.dist==2147483647){
                return -1;
            }
            cost+=out.dist;
            fit(out);
        }
        return cost;
    }
    private void fit(Struct a){
        for(Edge e:a.v.connect){
            Struct s;
            if(e.first==a.v){
                s=e.second.self;
            }else {
                s=e.first.self;
            }
            if(s.visited){
                continue;
            }
            if(s.dist>e.weight){
                s.dist=e.weight;
                s.best=e;
            }
            pq.upfix(s.place);
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
    private Comparator<Edge> edgecp=new Comparator<Edge>() {
        @Override
        public int compare(Edge o1, Edge o2) {
            if(o1.weight>o2.weight){
                return 1;
            }else if(o1.weight==o2.weight){
                return 0;
            }else {
                return -1;
            }
        }
    };
    class Struct{
        Vertex v;
        long dist;
        Edge best;
        boolean visited=false;
        innerNode place;
        Struct(Vertex v){
            this.v=v;
            v.self=this;
        }
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
        Vertex first;
        Vertex second;
        int weight;
        Edge(int weight,int first,int second){
            this.weight=weight;
            this.first=vertices.get(first);
            this.second=vertices.get(second);
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
