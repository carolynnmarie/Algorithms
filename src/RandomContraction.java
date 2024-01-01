import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RandomContraction {
    Graph graph;

    public RandomContraction(Graph graph) {
        this.graph = graph;
    }

    public RandomContraction(){
        this.graph = new Graph();
    }

    public void setGraph(){
        List<List<Integer>> adjacencyList = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new FileReader("src/kargerMinCut.txt"))) {
            for(String numb; (numb = br.readLine()) != null; ) {
                String[] array = numb.split("\\s");
                Integer[] nArray = new Integer[array.length];
                for(int i = 0; i< array.length; i++)
                    nArray[i] = Integer.parseInt(array[i]);
                adjacencyList.add(List.of(nArray));
                int vertex;
                int count = 0;
                for(List<Integer> list: adjacencyList){
                    vertex = list.get(0);
                    count++;
                    for(int j = 1;j<list.size(); j++){
                        Graph.Edge e = new Graph.Edge(vertex, list.get(j));
                        if(!graph.getEdges().contains(e)){
                            graph.addEdge(vertex, list.get(j));
                        }
                    }
                }
                graph.setVertexes(count+1);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public int runMinCut(){
        StringBuilder builder = new StringBuilder();
        int y = 0;
        for(int i = 2; i< 21; i++){
            y = graph.kargerMinCut();
            builder.append(y).append(" ,");
        }
        System.out.println(builder);
        return y;
    }

    public static void main(String args[]){
        RandomContraction rc = new RandomContraction();
        rc.setGraph();
        int x = 0;
        for(int i = 2; i< 1000; i++){
            rc = new RandomContraction();
            rc.setGraph();
            int y = rc.runMinCut();
            if(x == 0 || y<x)
                x = y;
        }
        System.out.println(x);
    }
}

