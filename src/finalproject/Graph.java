package finalproject;

import finalproject.system.Tile;
import finalproject.tiles.DesertTile;
import finalproject.tiles.PlainTile;

import java.util.ArrayList;

public class Graph {
    public ArrayList<Tile> vertices;

    public ArrayList<Edge> edges;

    // TODO level 2: Add fields that can help you implement this data type

    // TODO level 2: initialize and assign all variables inside the constructor
    public Graph(ArrayList<Tile> vertices) {
        this.edges=new ArrayList<>();
        this.vertices = vertices;
    }

    // TODO level 2: add an edge to the graph
    public void addEdge(Tile origin, Tile destination, double weight) {
        if(origin.isWalkable() && destination.isWalkable()) {
            Edge e = new Edge(origin, destination, weight);
            this.edges.add(e);
        }
    }

    // TODO level 2: return a list of all edges in the graph
    public ArrayList<Edge> getAllEdges() {
        return this.edges;
    }

    // TODO level 2: return list of tiles adjacent to t
    public ArrayList<Tile> getNeighbors(Tile t) {
        ArrayList<Tile> neighbors= new ArrayList<Tile>();
        for(Edge e: this.edges) if(t.equals(e.origin)) neighbors.add(e.destination);
        return neighbors;
    }

    // TODO level 2: return total cost for the input path
    public double computePathCost(ArrayList<Tile> path) {
        double cost = 0;
        for(int i=1; i<path.size(); i++){
            for(Edge e: this.edges) if(path.get(i-1)==e.origin && path.get(i)==e.destination) cost+=e.weight;
        }
        return cost;
    }


    public static class Edge {
        Tile origin;
        Tile destination;
        double weight;

        // TODO level 2: initialize appropriate fields
        public Edge(Tile s, Tile d, double cost) {
            this.origin = s;
            this.destination = d;
            this.weight = cost;
        }

        // TODO level 2: getter function 1
        public Tile getStart() {
            return this.origin;
        }


        // TODO level 2: getter function 2
        public Tile getEnd() {
            return this.destination;
        }

    }
}