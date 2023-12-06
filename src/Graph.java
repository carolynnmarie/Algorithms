import java.util.*;

public class Graph {

    static class Edge {
        int u;
        int v;

        public Edge(int u, int v) {
            this.u = u;
            this.v = v;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Edge edge)) return false;
            return u == edge.u && v == edge.v;
        }

        @Override
        public int hashCode() {
            return Objects.hash(u, v);
        }
    }

    static class subset {
        int parent;
        int rank;

        subset(int p, int r) {
            this.parent = p;
            this.rank = r;

        }
    }

        private int V;
        private List<Edge> edges;

        public Graph(int vertices) {
            this.V = vertices;
            this.edges = new ArrayList<>();
        }

        public Graph(){
            this.edges = new ArrayList<>();
            this.V = 0;
        }

        public List<Edge> getEdges(){
            return edges;
        }

        public void setVertexes(int vertexes){
            this.V = vertexes;
        }

        public int getVertexes(){
            return V;
        }

        public void addEdge(int u, int v) {
            edges.add(new Edge(u, v));
        }

        public int find(int[] parent, int i) {
            int y = 0;
            for (int x = 0; x < parent.length; x++) {
                if (parent[x] == i) {
                    y = i;
                }
            }
            return y;
        }
//            if (parent[i] == i) {
//                return i;
//            } else{
//                return find(parent, parent[i]);
//            }




    void union(int[]parent, int[]rank, int u,int v){
        u=find(parent,u);
        v=find(parent,v);
        if(u!=v){
            if(rank[u]<rank[v]) {
                int temp=u;
                u=v;
                v=temp;
            }
            parent[v]=u;
            if(rank[u]==rank[v])
                rank[u]++;
        }
    }


        public String printGraph(){
            StringBuilder builder = new StringBuilder();
            for(Edge edge: edges){
                int x = edge.u;
                int y = edge.v;
                builder.append(x).append(" ").append(y).append(",");
            }
            return builder.toString();
        }


    public int kargerMinCut() {
        int[] parent = new int[V];
        int[] rank = new int[V];
        for (int i = 0; i < V; i++) {
            parent[i] = i;
            rank[i] = 0;
        }
        int v = V;
        while (v > 2) {
            Random rand = new Random();
            int randomIndex = 1;
            if(edges.size()>0){
                randomIndex = rand.nextInt(edges.size());
            }
            int u = edges.get(randomIndex).u;
            int w = edges.get(randomIndex).v;
            int setU = find(parent, u);
            int setW = find(parent, w);
            if (setU != setW) {
                v--;
                union(parent, rank, u, w);
            }
            edges.remove(randomIndex);
        }
        int minCut = 0;
//        for (Edge edge : edges) {
//            int setU = find(parent, edge.u);
//            int setW = find(parent, edge.v);
//            if (setW != setU){
//                minCut++;
//            }
//        }
        for(int i = 0; i<edges.size(); i++){
            int x = edges.get(i).u;
            int w = edges.get(i).v;
            int setU = find(parent, x);
            int setV = find(parent, w);
            if(setU != setV){
                minCut++;
            }
        }
        return minCut;
    }
}