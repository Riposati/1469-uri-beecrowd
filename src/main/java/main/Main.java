package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class Graph {
    private List<Integer> vis = new ArrayList<>();
    private List<List<Integer>> adj = new ArrayList<>();
    private List<Integer> id;
    private List<Integer> pos = new ArrayList<>();

    public Graph() {
    }

    public List<List<Integer>> getAdj() {
        return adj;
    }

    public List<Integer> getId() {
        return id;
    }

    public List<Integer> getPos() {
        return pos;
    }

    public List<Integer> getListAdj(int index) {
        return this.adj.get(index);
    }

    public void initializeVis(int N) {
        this.setVis(N);
    }

    public void setId(int N) {
        this.id = new ArrayList<>(N);
        for (int i = 0; i < N; i++) {
            id.add(0);
        }
    }

    public void setVis(int N) {
        this.vis = new ArrayList<>(N);
        for (int i = 0; i < N; i++) {
            vis.add(0);
        }
    }

    public void setPos(int N) {
        this.pos = new ArrayList<>(N);
        for (int i = 0; i < N; i++) {
            pos.add(0);
        }
    }

    public void setAdj(int M) {
        this.adj = new ArrayList<>(M);

        for (int i = 0; i < 500; i++) {
            adj.add(new ArrayList<>());
        }
    }

    int depthFirstSearch(int ini, int lowest) {
        vis.set(ini, 1);

        for (int i = 0; i < adj.get(ini).size(); ++i) {

            int v = adj.get(ini).get(i);

            if (vis.get(v) == 0) {

                if (id.get(v) < lowest)
                    lowest = id.get(v);

                lowest = depthFirstSearch(v, lowest);
            }
        }
        return lowest;
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String str;
        int N, M, I;
        Graph graph = new Graph();

        while ((str = in.readLine()) != null) {
            N = Integer.parseInt(str.split(" ")[0]);
            M = Integer.parseInt(str.split(" ")[1]);
            I = Integer.parseInt(str.split(" ")[2]);

            initializeGraphData(N, M, graph);
            readAges(in, N, graph);
            readAjacentList(in, M, graph);
            operations(in, N, I, graph);
        }
    }

    private static void initializeGraphData(int N, int M, Graph graph) {
        graph.setId(N);
        graph.setAdj(M);
        graph.setVis(N);
        graph.setPos(N);
    }

    private static void readAjacentList(BufferedReader in, int M, Graph graph) throws IOException {
        for (int i = 0; i < M; i++) {
            String aux = in.readLine();
            graph.getListAdj(Integer.parseInt(aux.split(" ")[1]) - 1).add(Integer.parseInt(aux.split(" ")[0]) - 1);
        }
    }

    private static void readAges(BufferedReader in, int N, Graph graph) throws IOException {
        String aux = in.readLine();
        for (int i = 0; i < N; i++) {
            graph.getId().set(i, Integer.parseInt(aux.split(" ")[i]));
            graph.getPos().set(i, i);
        }
    }

    private static void operations(BufferedReader in, int N, int I, Graph graph) throws IOException {
        for (int i = 0; i < I; i++) {
            String aux = in.readLine();
            String command = aux.split(" ")[0];

            if (command.equalsIgnoreCase("P")) {
                analyseLowestAge(N, graph, aux);
            } else {
                swapPlaces(graph, aux);
            }
        }
    }

    private static void swapPlaces(Graph graph, String aux2) {
        int vertexOne = Integer.parseInt(aux2.split(" ")[1]);
        int verticeTwo = Integer.parseInt(aux2.split(" ")[2]);

        Collections.swap(graph.getId(), graph.getPos().get(vertexOne - 1), graph.getPos().get(verticeTwo - 1));
        Collections.swap(graph.getPos(), vertexOne - 1, verticeTwo - 1);
    }

    private static void analyseLowestAge(int N, Graph graph, String aux2) {
        int vertex = Integer.parseInt(aux2.split(" ")[1]);

        if (graph.getAdj().get(graph.getPos().get(vertex - 1)).isEmpty())
            System.out.print("*\n");
        else {

            graph.initializeVis(N);

            int lowest = 999;
            lowest = graph.depthFirstSearch(graph.getPos().get(vertex - 1), lowest);

            System.out.print(lowest + "\n");
        }
    }
}
