import java.util.ArrayList;

public class Graph<N> {
    ArrayList<Node> allNode;
    ArrayList<Node> toplogicalorder;
    Graph(){
        allNode=new ArrayList<>();
        toplogicalorder=new ArrayList<>();
    }
    void addVertex(N key){
        allNode.add(new Node(key));
    }
    void addEdge(int parentIndex,int childIndex){
        allNode.get(parentIndex).connect.add(allNode.get(childIndex));
    }
    private void paintWhite(){
        for(Node n:allNode){
            n.color=0;
        }
    }
    int getSize(){
        return allNode.size();
    }
    ArrayList<Node> BFSsearch(){
        paintWhite();
        ArrayList<Node> roots=new ArrayList<>();
        for(Node a:allNode) {
            if(a.color==0) {
                roots.add(a);
                BFSsearch(a);
            }
        }
        return roots;
    }
    Node findNoIn(){
        for(Node a:allNode){
            if(a.connect.size()==0){
                return a;
            }
        }
        return null;
    }
    void BFSsearch(int nodeIndex){
        paintWhite();
        BFSsearch(allNode.get(nodeIndex));
    }
    //这里为了在上一个search BFS tree 函数能正常运作，开始时没有paint white 单独使用要小心
    void BFSsearch(Node a){
        Stack<Node> queue=new Stack<>();
        if(a.color==0) {
            queue.push(a);
            a.color = 1;
            a.child = new ArrayList<>();
            while (queue.getTop() != null) {
                Node now = queue.getFirst();
                now.child = new ArrayList<>();
                for (Node i : now.connect) {
                    if (i.color == 0) {
                        i.depth=now.depth+1;
                        queue.push(i);
                        now.child.add(i);
                        i.color = 1;
                    }
                }
                now.color = 2;
                queue.removeFirst();
            }
        }
    }
    ArrayList<Node> DFSsearch(){
        paintWhite();
        ArrayList<Node> roots=new ArrayList<>();
        for(Node a:allNode) {
            if(a.color==0) {
                roots.add(a);
                toplogicalorder.addAll(DFSsearch(a));
            }
        }
        return roots;
    }
    ArrayList<Node> DFSsearch(Node tar){
        Stack<Node> stack=new Stack<>();
        ArrayList<Node> out=new ArrayList<>();
        if(tar.color==0) {
            stack.push(tar);
            tar.color = 1;
            tar.child=new ArrayList<>();
            while (stack.getTop() != null) {
                Node now = stack.getTop();
                boolean have=false;
                for (Node i : now.connect) {
                    if (i.color == 0) {
                        have=true;
                        stack.push(i);
                        now.child.add(i);
                        i.color = 1;
                        break;
                    }
                }
                if(!have){
                    stack.pop();
                    now.color=2;
                    out.add(now);
                }
            }
        }
        return out;
    }
    ArrayList<Node> SCCDFS(ArrayList<Node> toplogicalorder){
        paintWhite();
        ArrayList<Node> SCCs=new ArrayList<>();
        for(Node n:toplogicalorder){
            if(n.color==0){
                SCCs.add(n);
                DFSsearch(n);
            }
        }
        return SCCs;
    }
    class Node{
        int color;  //0=white,1=yellow,2=red
        int depth;
        N key;
        ArrayList<Node> connect;
        ArrayList<Node> child;
        int[] pair;
        int index;
        int scc;
        Node(N key){
            connect=new ArrayList<>();
            this.key=key;
            pair=new int[2];
            index=allNode.size();
        }
    }
    class Stack<T> {
        private innerNode first;
        private innerNode top;

        public Stack(T in) {
            innerNode first = new innerNode( in, null);
            this.first = first;
            top = first;
        }
        public Stack(){}

        public void push(T in) {
            if(top!=null) {
                innerNode node = new innerNode(in, top);
                top.next = node;
                top = node;
            }else {
                top=new innerNode( in, null);
                first=top;
            }
        }

        public T getTop() {
            if(top!=null) {
                return top.value;
            }else {
                return null;
            }
        }

        public void pop() {
            if(top.former!=null) {
                top = top.former;
                top.next.former = null;
                top.next = null;
            }else {
                first=null;
                top=null;
            }
        }

        private T getFirst(){
            if(first!=null) {
                return first.value;
            }
            return null;
        }
        private void removeFirst(){
            if(first.next!=null) {
                first = first.next;
                first.former.next=null;
                first.former=null;
            }else {
                first=null;
                top=null;
            }
        }

        private class innerNode {
            T value;
            innerNode next;
            innerNode former;

            private innerNode( T value, innerNode former) {
                this.value = value;
                this.former = former;
            }
        }
    }
}