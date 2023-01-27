package finalproject;


import java.util.ArrayList;
import java.util.LinkedList;

import finalproject.system.Tile;
import finalproject.tiles.MetroTile;

public class SafestShortestPath extends ShortestPath {
	public int health;
	public Graph costGraph;
	public Graph damageGraph;
	public Graph aggregatedGraph;

	//TODO level 8: finish class for finding the safest shortest path with given health constraint
	public SafestShortestPath(Tile start, int health) {
		super(start);
		this.health = health;
		generateGraph();
	}


	public void generateGraph() {
		Tile t = super.source;
		ArrayList<Tile> reachableTiles = GraphTraversal.BFS(t);

		this.costGraph = new Graph(reachableTiles);
		this.aggregatedGraph= new Graph(reachableTiles);
		this.damageGraph=new Graph(reachableTiles);

		while (!reachableTiles.isEmpty()) {

			Tile tile = reachableTiles.get(0);

			reachableTiles.remove(tile);

			for (Tile j : tile.neighbors) {

				if (tile instanceof MetroTile && j instanceof MetroTile) {
					((MetroTile) j).fixMetro(tile);
					costGraph.addEdge(tile, j, ((MetroTile) j).metroDistanceCost);
				}

				else if (j.isWalkable()) {
					costGraph.addEdge(tile, j, j.distanceCost);
					aggregatedGraph.addEdge(tile, j, j.damageCost);
					damageGraph.addEdge(tile, j, j.damageCost);
				}
			}
		}

	}

	@Override
	public ArrayList<Tile> findPath(Tile start, LinkedList<Tile> waypoints) {
		super.g=costGraph;
		ArrayList<Tile> pathC= super.findPath(start,waypoints);

		super.g=damageGraph;
		ArrayList<Tile> pathD= super.findPath(start, waypoints);

		super.g=aggregatedGraph;
		ArrayList<Tile> pathR= super.findPath(start, waypoints);

		double damageCostC=damageGraph.computePathCost(pathC);
		double damageCostD=damageGraph.computePathCost(pathD);

		double distanceCostC=costGraph.computePathCost(pathC);
		double distanceCostD=costGraph.computePathCost(pathD);


		if(damageCostC<health) return pathC;

		if(damageCostD>health) return null;

		while(true){

			double lambda=(distanceCostC-distanceCostD)/(damageCostD-damageCostC);

			for(Graph.Edge e: aggregatedGraph.edges){
				e.weight=e.destination.distanceCost+lambda*e.destination.damageCost;
			}

			double aggregatedR=aggregatedGraph.computePathCost(pathR);
			double aggregatedC=aggregatedGraph.computePathCost(pathC);

			double damageCostR=damageGraph.computePathCost(pathR);

			if(aggregatedR==aggregatedC) return pathD;


			else if(damageCostR<=this.health){
				pathD=pathR;
			}

			else{
				pathC=pathR;
			}
		}

	}
}
