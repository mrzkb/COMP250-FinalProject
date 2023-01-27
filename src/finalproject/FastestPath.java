package finalproject;

import finalproject.system.Tile;
import finalproject.tiles.MetroTile;

import java.util.ArrayList;

public class FastestPath extends PathFindingService {
    //TODO level 6: find time prioritized path
    public FastestPath(Tile start) {
        super(start);
        this.generateGraph();
    }

    @Override
    public void generateGraph() {
        Tile t = super.source;
        ArrayList<Tile> reachableTiles = GraphTraversal.BFS(t);
        this.g = new Graph(reachableTiles);
        while (!reachableTiles.isEmpty()) {
            Tile tile = reachableTiles.get(0);
            reachableTiles.remove(tile);
            for (Tile j : tile.neighbors) {
                if(tile instanceof MetroTile && j instanceof MetroTile) {
                    ((MetroTile) j).fixMetro(tile);
                    g.addEdge(tile, j, ((MetroTile) j).metroTimeCost);
                }
                else if (j.isWalkable()) g.addEdge(tile, j, j.timeCost);
            }

        }

    }
}
