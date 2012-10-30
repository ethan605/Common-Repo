package ai.pathfinder.shortestpath;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.PriorityQueue;

public class ShortestParth {
	public static void main(String args[]) {
		MapInfo.initMap(8, 8, new int[] {1, 6}, new int[] {4, 2}, 4, 3, 1, 5, 2, 5, 2, 6, 3, 6, 4, 5);
		
		ArrayList<SearchState> searchResult = AStar();
		
		if (searchResult != null)
			for (SearchState foundState : searchResult)
				System.out.println(foundState);
		else
			System.out.println("Not found");
	}
	
	public static ArrayList<SearchState> AStar() {
		// Initialize startState with start position's coordinate 
		SearchState startState = new SearchState(MapInfo.startPos);
				
		// Initialize containers
		// openMap contains unexamined states
		HashMap<Integer, SearchState> openMap = new HashMap<Integer, SearchState>();
		openMap.put(startState.getIntValue(), startState);
		
		// closedMap contains examined states
		HashMap<Integer, SearchState> closedMap = new HashMap<Integer, SearchState>();
		
		// stateQueue contains openMap's states with order of best fScore or gScore first
		PriorityQueue<SearchState> stateQueue = new PriorityQueue<SearchState>();
		stateQueue.add(startState);
		
		// Iterate the stateQueue until it's empty
		while (!stateQueue.isEmpty()) {
			// Pop stateQueue's head element
			SearchState u = stateQueue.remove();
			
			// In case of goal state reached
			// reconstruct found path
			if (u.getIntValue() == MapInfo.coordinateToIntValue(MapInfo.goalPos)) {
				ArrayList<SearchState> resultSequence = new ArrayList<SearchState>();
				SearchState tmp = u;
				while (tmp != null && tmp.getIntValue() != MapInfo.coordinateToIntValue(MapInfo.startPos)) {
					resultSequence.add(tmp);
					tmp = closedMap.get(tmp.parent);
				}
				
				// Default A* Algorithm does not contain start state
				// we have to manually include it
				resultSequence.add(new SearchState(MapInfo.startPos));
				
				// Reverse the found path into natural order
				Collections.reverse(resultSequence);
				return resultSequence;
			}
			
			// Update openMap & closedMap
			// a state to be examined will be removed from openMap and add to closedMap
			openMap.remove(u.getIntValue());
			closedMap.put(u.getIntValue(), u);
			
			PriorityQueue<SearchState> neighborStates = u.neighborStates();
			while (!neighborStates.isEmpty()) {
				SearchState v = neighborStates.remove();
				
				// Ignore if the state is in closedMap
				if (closedMap.containsKey(v.getIntValue()))
					continue;
				
				int tentative_gScore = u.gScore + MapInfo.STATE_DISTANCE;
				boolean isBetterTentative_gScore = false;
				
				// Calculate tentative gScore of neighbor state
				if (!closedMap.containsKey(v.getIntValue()))
					isBetterTentative_gScore = true;
				else
					isBetterTentative_gScore = (tentative_gScore < v.gScore);
				
				// In case of the neighborState has better tentative gScore
				// we update it and reference current state as its parent
				if (isBetterTentative_gScore) {
					v.parent = u.getIntValue();
					v.gScore = tentative_gScore;
					v.fScore = v.gScore + v.hScore;
				}
				
				if (!openMap.containsKey(v.getIntValue()))
					stateQueue.add(v);
				
				openMap.put(v.getIntValue(), v);
			} // end of neighborStates iteration
		} // end of stateQueue iteration
		
		return null;
	}
}
