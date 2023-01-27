package finalproject;

import java.util.ArrayList;
import java.util.Arrays;


import finalproject.system.Tile;
import finalproject.tiles.DesertTile;
import finalproject.tiles.MetroTile;
import finalproject.tiles.MountainTile;
import finalproject.tiles.PlainTile;

public class TilePriorityQ {
	ArrayList<Tile> priorityQ;

	// TODO level 3: implement the constructor for the priority queue
	public TilePriorityQ(ArrayList<Tile> vertices) {
		ArrayList<Tile> priorityQ = new ArrayList<Tile>(vertices.size() + 1);
		priorityQ.add(null);
		this.priorityQ= priorityQ;
		for(Tile t: vertices) addTile(t);
	}

	public void addTile(Tile t){
		priorityQ.add(t);
		upHeap(priorityQ.size());
	}
	// TODO level 3: implement remove min as seen in class
	public Tile removeMin() {
		Tile tmp = this.priorityQ.get(1);
		this.priorityQ.set(1, this.priorityQ.get(priorityQ.size() - 1));
		this.priorityQ.remove(priorityQ.size() - 1);
		downHeap(1, this.priorityQ.size());
		return tmp;
	}

	private void downHeap(int startIndex, int maxIndex) {
		int i = startIndex;
		while (2 * i < maxIndex) { //left child
			int child = 2 * i;
			if (child < maxIndex) {
				if (this.priorityQ.size() - 1 != child) {
					if (this.priorityQ.get(child + 1).costEstimate < this.priorityQ.get(child).costEstimate) child++;
				}
				if (this.priorityQ.get(child).costEstimate < this.priorityQ.get(i).costEstimate) {
					Tile tmp = this.priorityQ.get(child);
					this.priorityQ.set(child, this.priorityQ.get(i));
					this.priorityQ.set(i, tmp);
					i = child;
				} else break;
			}
		}
	}

	private void upHeap(int size) {
		int i = size-1;
		while (i > 1 && priorityQ.get(i).costEstimate < priorityQ.get(i / 2).costEstimate) {
			Tile tmp = this.priorityQ.get(i);
			this.priorityQ.set(i, priorityQ.get(i / 2));
			priorityQ.set(i / 2, tmp);
			i = i / 2;
		}

	}

	// TODO level 3: implement updateKeys as described in the pdf
	public void updateKeys(Tile t, Tile newPred, double newEstimate) {
		if(this.priorityQ.contains(t)){
			t.costEstimate=newEstimate;
			t.predecessor=newPred;
			priorityQ.remove(t);
			this.addTile(t);
		}
			}

}


