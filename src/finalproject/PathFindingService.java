package finalproject;

import finalproject.system.Tile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

public abstract class PathFindingService {
    Tile source;
    Graph g;

    public PathFindingService(Tile start) {
        this.source = start;
    }

    public abstract void generateGraph();

    private void initSingleSource(ArrayList<Tile> vertices, Tile source) {
        for (Tile t : vertices) {
            if (t != null) {
                t.costEstimate = Double.POSITIVE_INFINITY;
                t.predecessor = null;
            }
        }
        source.costEstimate = 0;
        if (!vertices.contains(source)) vertices.add(source);
    }

    private void relax(TilePriorityQ priorityQ, Tile origin, Tile destination, double weight) {
        if (destination.costEstimate > origin.costEstimate + weight) {
            double newEstimate = weight + origin.costEstimate;
            priorityQ.updateKeys(destination, origin, newEstimate);
        }

    }

    //TODO level 4: Implement basic dijkstra's algorithm to find a path to the final unknown destination
    public ArrayList<Tile> findPath(Tile startNode) {

        ArrayList<Tile> vertices = GraphTraversal.BFS(startNode);
        //TilePriorityQ priorityQ = new TilePriorityQ(vertices);
        ArrayList<Tile> path = new ArrayList<Tile>();

        initSingleSource(vertices, startNode);
        TilePriorityQ priorityQ = new TilePriorityQ(vertices);

        Tile dest = null;
        Tile t;
        while (!priorityQ.priorityQ.isEmpty()) {

            if (priorityQ.priorityQ.size() == 2) {
                t = priorityQ.priorityQ.get(1);
                priorityQ.priorityQ.clear();
                if (t.isDestination) dest = t;

            } else t = priorityQ.removeMin();

            for (Tile tile : g.getNeighbors(t)) {

                if(this instanceof SafestShortestPath && g.equals(((SafestShortestPath) this).aggregatedGraph)){
                    relax(priorityQ,t,tile,tile.damageCost);
                }

                else if(this instanceof SafestShortestPath && g.equals(((SafestShortestPath) this).damageGraph)){
                    relax(priorityQ, t, tile, tile.damageCost);
                }

                else if(this instanceof ShortestPath){
                relax(priorityQ, t, tile, tile.distanceCost);
                }

                else if(this instanceof FastestPath){
                    relax(priorityQ, t, tile, tile.timeCost);
                }

                if (t.isDestination) {
                    dest = t;
                    break;
                }
            }

        }
        while (dest != null) {
            path.add(0,dest);
            dest = dest.predecessor;
        }
        return path;
    }

    //TODO level 5: Implement basic dijkstra's algorithm to path find to a known destination
    public ArrayList<Tile> findPath(Tile start, Tile end) {
        ArrayList<Tile> vertices = GraphTraversal.BFS(start);
        //TilePriorityQ priorityQ = new TilePriorityQ(vertices);
        ArrayList<Tile> path = new ArrayList<Tile>();

        initSingleSource(vertices, start);
        TilePriorityQ priorityQ = new TilePriorityQ(vertices);

        Tile dest = null;
        Tile t;
        while (!priorityQ.priorityQ.isEmpty()) {

            if (priorityQ.priorityQ.size() == 2) {
                t = priorityQ.priorityQ.get(1);
                priorityQ.priorityQ.clear();
                if (t.equals(end)) dest = t;
            } else t = priorityQ.removeMin();

            for (Tile tile : g.getNeighbors(t)) {

                if(this instanceof SafestShortestPath && g.equals(((SafestShortestPath) this).aggregatedGraph)){
                    relax(priorityQ,t,tile,tile.damageCost);
                }

                else if(this instanceof SafestShortestPath && g.equals(((SafestShortestPath) this).damageGraph)){
                    relax(priorityQ, t, tile, tile.damageCost);
                }

                else if(this instanceof ShortestPath) {
                    relax(priorityQ, t, tile, tile.distanceCost);
                }

                else if(this instanceof FastestPath){
                    relax(priorityQ, t, tile, tile.timeCost);
                }

                if (t.equals(end)) {
                    dest = t;
                    break;
                }
            }

        }
        while (dest != null) {
            path.add(0,dest);
            dest = dest.predecessor;
        }
        return path;

    }

    //TODO level 5: Implement basic dijkstra's algorithm to path find to the final destination passing through given waypoints
    public ArrayList<Tile> findPath(Tile start, LinkedList<Tile> waypoints) {
        ArrayList<Tile> path = new ArrayList<>();
        ArrayList<Tile> tmpPath = new ArrayList<>();

        if (!waypoints.isEmpty()) {
            path.addAll(findPath(start, waypoints.get(0)));

            for (int i=0; i< waypoints.size(); i++) {

               if(waypoints.get(i).equals(waypoints.get(waypoints.size()-1))){
                    tmpPath.addAll(findPath(waypoints.get(i)));
                    tmpPath.remove(waypoints.get(i));
                    path.addAll(tmpPath);
                    return path;
                }
                else{
                    tmpPath.addAll(findPath(waypoints.get(i), waypoints.get(i+1)));
                    tmpPath.remove(waypoints.get(i));
                    path.addAll(tmpPath);
                }
                tmpPath.clear();
            }
              }
             else path=findPath(start);
        return path;
    }
}

