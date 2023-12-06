import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class kargersmincutex2 {

    public static class Edge
    {
        int src, dest;
        Edge(int v, int e){
            this.src = v;
            this.dest = e;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Edge edge)) return false;
            return src == edge.src && dest == edge.dest;
        }

        @Override
        public int hashCode() {
            return Objects.hash(src, dest);
        }
    }

    public static class Graph
    {
        // V-> Number of vertices, E-> Number of edges
        int V, E;

        // graph is represented as an array of edges.
        // Since the graph is undirected, the edge
        // from src to dest is also edge from dest
        // to src. Both are counted as 1 edge here.
        Edge edges[];
        Graph(int v, int e){
            this.V = v;
            this.E = e;
            this.edges = new Edge[e];
            /*for(int i=0;i<e;i++){
                this.edge[i]=new Edge(-1,-1);
            }*/
        }

        Graph(int v){
            this.V = v;
            this.E = 0;
            this.edges = new Edge[v];
            List<List<Integer>> adjacencyList = new ArrayList<>();
            StringBuilder builder = new StringBuilder();
            try(BufferedReader br = new BufferedReader(new FileReader("src/kargerMinCut.txt"))) {
                for(String numb; (numb = br.readLine()) != null; ) {
                    String[] array = numb.split("\\s");
                    Integer[] nArray = new Integer[array.length];
                    for(int i = 0; i< array.length; i++){
                        nArray[i] = Integer.parseInt(array[i]);
                    }
                    adjacencyList.add(List.of(nArray));
                    int vertex;
                    int count = 0;
                    for(List<Integer> list: adjacencyList){
                        vertex = list.get(0);
                        count++;
                        for(int j = 1;j<list.size(); j++){
                            builder.append(vertex).append(" ").append(list.get(j)).append(",");
                        }
                    }
                    //graph.setVertexes(count+1);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        void addEdge(int i, int v, int e){
            this.edges[i] = new Edge(v, e);
        }

        boolean includes(Edge edge) {
            boolean is = false;
            for (Edge each : edges) {
                if (each.equals(edge)) {
                    is = true;
                } else {
                    is = false;
                }
            }
            return is;
        }

        boolean isEmpty(){
            boolean empty = true;
            for(Edge each: edges){
                if(each != null){
                    empty = false;
                }
            }
            return empty;
        }

        void setVertexes(int vertexes){
            this.V = vertexes;
        }
    }

    public static class subset
    {
        int parent;
        int rank;
        subset(int p, int r){
            this.parent = p;
            this.rank = r;
        }
    }

    // A very basic implementation of Karger's randomized
    // algorithm for finding the minimum cut. Please note
    // that Karger's algorithm is a Monte Carlo Randomized algo
    // and the cut returned by the algorithm may not be
    // minimum always
    static Graph graph;

    kargersmincutex2(){
        this.graph = new Graph(200);
    }
    public static int kargerMinCut() {

        int V = graph.V, E = graph.E;
        Edge edge[] = graph.edges;
        subset subsets[] = new subset[V];
        for (int v = 0; v < V; ++v) {
            subsets[v] = new subset(v,0);
        }
        int vertices = V;

        while (vertices > 2)
        {
            Random random = new Random();
            int randomIndex = 1;
            if(graph.edges.length>0){
                randomIndex = random.nextInt(graph.edges.length);
            }
            int i = randomIndex;
            int subset1 = find(subsets, edge[i].src);
            int subset2 = find(subsets, edge[i].dest);

            if (subset1 != subset2){
                System.out.println("Contracting edge "+edge[i].src+"-"+edge[i].dest);
                vertices--;
                Union(subsets, subset1, subset2);
            }
        }

        int cutedges = 0;
        for (int i=0; i<E; i++)
        {
            int subset1 = find(subsets, edge[i].src);
            int subset2 = find(subsets, edge[i].dest);
            if (subset1 != subset2){
                cutedges++;
            }
        }
        return cutedges;
    }

    // A utility function to find set of an element i
    // (uses path compression technique)
    public static int find(subset subsets[], int i)
    {
        // find root and make root as parent of i
        // (path compression)
        if (subsets[i].parent != i){
            subsets[i].parent = find(subsets, subsets[i].parent);
        }
        return subsets[i].parent;
    }

    // A function that does union of two sets of x and y
    // (uses union by rank)
    public static void Union(subset subsets[], int x, int y)
    {
        int xroot = find(subsets, x);
        int yroot = find(subsets, y);

        // Attach smaller rank tree under root of high
        // rank tree (Union by Rank)
        if (subsets[xroot].rank < subsets[yroot].rank){
            subsets[xroot].parent = yroot;
        }else{
            if (subsets[xroot].rank > subsets[yroot].rank){
                subsets[yroot].parent = xroot;
            }
            // If ranks are same, then make one as root and
            // increment its rank by one
            else
            {
                subsets[yroot].parent = xroot;
                subsets[xroot].rank++;
            }
        }
    }

    public void setGraph(){
        List<List<Integer>> adjacencyList = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new FileReader("src/kargerMinCut.txt"))) {
            for(String numb; (numb = br.readLine()) != null; ) {
                StringBuilder builder = new StringBuilder();
                String[] array = numb.split("\\s");
                Integer[] nArray = new Integer[array.length];
                for(int i = 0; i< array.length; i++){
                    nArray[i] = Integer.parseInt(array[i]);
                }
                adjacencyList.add(List.of(nArray));
                int vertex;
                int count = 0;
                for(List<Integer> list: adjacencyList){
                    vertex = list.get(0);
                    count++;
                    for(int j = 1;j<list.size(); j++){
                        //Edge e = new Edge(vertex, list.get(j));
                        graph.addEdge(j,vertex, list.get(j));
                        builder.append(vertex).append(" ").append(list.get(j)).append("/");
                    }
                }
                graph.setVertexes(count+1);
                System.out.println(builder);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    public static void main(String[] args) {
        kargersmincutex2 two = new kargersmincutex2();
        two.setGraph();
//        int x = kargerMinCut();
//        System.out.println(x);


    }
}
