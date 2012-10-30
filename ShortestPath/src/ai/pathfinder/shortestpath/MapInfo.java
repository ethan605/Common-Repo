package ai.pathfinder.shortestpath;

import java.util.HashMap;

public class MapInfo {
	// We use 4 digit to represent the coordinates,
	// which means maximum dimension of the map is 10000x10000
	public static final int MAX_DIMENSION_VALUE = 10000;
	
	// Coordinate difference of 4 main directions (clockwise): up, right, down, left
	public static final int[][] MOVING_DIRECTIONS = {{0, -1}, {1, -1}, {1, 0}, {1, 1},
													 {0, 1}, {-1, 1}, {-1, 0}, {-1, -1}};
	
	// Default state-to-state distance
	public static final int STATE_DISTANCE = 1;
	
	public static int width;
	public static int height;
	
	public static int[] startPos;
	public static int[] goalPos;
	
	// We use HashMap of obstacles' coordinates to save memory
	public static HashMap<Integer, Boolean> obstacles;
	
	/*
	 * Initialize a map with essential values
	 * Last param is an array param, which represent obstacles' coordinates
	 */
	public static void initMap(int _width, int _height, int[] _start, int[] _goal, int... _obstacles) {
		width = _width;
		height = _height;
		startPos = _start;
		goalPos = _goal;
		obstacles = new HashMap<Integer, Boolean>();
		for (int i = 0; i < _obstacles.length; i += 2)
			obstacles.put(coordinateToIntValue(_obstacles[i], _obstacles[i+1]), true);
	}
	
	/*
	 * Encode a coordinate into integer value
	 * which we can use as key in HashMap of examined states
	 */
	public static int coordinateToIntValue(int x, int y) {
		return x * MAX_DIMENSION_VALUE + y;
	}
	
	/*
	 * Encode a coordinate into integer value
	 * which we can use as key in HashMap of examined states
	 */
	public static int coordinateToIntValue(int[] coordinate) {
		return coordinate[0] * MAX_DIMENSION_VALUE + coordinate[1];
	}
	
	/*
	 * Check if a position is valid
	 * which mean it's within the map's border
	 * and is not an obstacle
	 */
	public static boolean isValidPosition(int[] coordinate) {
		return (coordinate[0] >= 0 && coordinate[0] < width &&
				coordinate[1] >= 0 && coordinate[1] < height &&
				!obstacles.containsKey(coordinateToIntValue(coordinate)));
	}
}
