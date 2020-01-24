import java.util.ArrayList;

public class SCC {
    Graph<Integer> graph=new Graph<>();
    Graph<Integer> reversegraph=new Graph<>();
    ArrayList<Graph<Integer>.Node> sccs;
    public void buildedge(int vertexNum,int edgenum,int[][] edgeinfor){
        for(int i=0;i<edgenum;i++){
            graph.addEdge(edgeinfor[i][0]-1,edgeinfor[i][1]-1);
            reversegraph.addEdge(edgeinfor[i][1]-1,edgeinfor[i][0]-1);
        }
        reversegraph.DFSsearch();
        ArrayList<Graph<Integer>.Node> toplogical=new ArrayList<>();
        for(int i=vertexNum-1;i>=0;i--){
            Graph<Integer>.Node vertex=graph.allNode.get(reversegraph.toplogicalorder.get(i).index);
            toplogical.add(vertex);
        }
        sccs=graph.SCCDFS(toplogical);
    }
    public void buildvertex(int[] vertexinfor){
        for(int i:vertexinfor){
            graph.addVertex(i);
            reversegraph.addVertex(i);
        }
    }
}
