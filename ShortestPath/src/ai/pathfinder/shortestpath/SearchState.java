package ai.pathfinder.shortestpath;

import java.util.PriorityQueue;

public class SearchState implements Comparable<SearchState> {
	private int[] coordinate = new int[2];
	private int intValue;
	
	public int fScore, gScore, hScore, parent;
	
	/*
	 * Blank constructor
	 */
	public SearchState() {
		coordinate[0] = coordinate[1] = intValue = fScore = gScore = hScore = parent = 0;
	}
	
	/*
	 * Constructor from start & goal state's coordinates
	 */
	public SearchState(int[] _coordinate) {
		coordinate = _coordinate;
		intValue = MapInfo.coordinateToIntValue(coordinate);
		fScore = gScore = parent = 0;
		hScore = heuristicScore(MapInfo.goalPos);
	}
	
	/*
	 * Print out current state's coordinate
	 */
	@Override
	public String toString() {
		return String.format("(%d, %d)", coordinate[0], coordinate[1]);
	}

	/*
	 * Compare function to sort elements in priority queue
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(SearchState state) {
		if (this.fScore < state.fScore)
			return -1;
		else if (this.fScore > state.fScore)
			return 1;
		else if (this.hScore < state.hScore)
			return -1;
		else if (this.hScore > state.hScore)
			return 1;
		return 0;
	}
	
	/*
	 * Get current state's coordinate
	 */
	public int[] getCoordinate() {
		return coordinate;
	}
	
	/*
	 * Get encoded value of current state's coordinate
	 */
	public int getIntValue() {
		return intValue;
	}
	
	/*
	 * Produce valid neighbor states
	 * which could be used to expand the path
	 */
	public PriorityQueue<SearchState> neighborStates() {
		PriorityQueue<SearchState> states = new PriorityQueue<SearchState>();
		for (int i = 0; i < MapInfo.MOVING_DIRECTIONS.length; i++) {
			int[] newCoord = new int[2];
			newCoord[0] = coordinate[0] + MapInfo.MOVING_DIRECTIONS[i][0];
			newCoord[1] = coordinate[1] + MapInfo.MOVING_DIRECTIONS[i][1];
			if (MapInfo.isValidPosition(newCoord))
				states.add(new SearchState(newCoord));
		}
		return states;
	}
	
	/*
	 * Calculate heuristic score of current state
	 * based on Manhattan distance
	 */
	private int heuristicScore(int[] toCoordinate) {
		return Math.abs(toCoordinate[0] - coordinate[0]) + Math.abs(toCoordinate[1] - coordinate[1]);
	}
}