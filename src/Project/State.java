package Project;

public class State {

	public Grid grid;
	public State previous_state;
	public String previous_operator;
	public int depth;
	public int cost;
	public Piece changed_piece;

	public State(Grid g, State ps, String o, int d, int c, Piece p) {
		this.grid = g;
		this.previous_state = ps;
		this.previous_operator = o;
		this.depth = d;
		this.changed_piece = p;
	}

	public int heuristic1() {
		// Getting the current location of the agent
		int agentY = 0;
		for (Piece piece : grid.Pieces_List) {
			if (piece.type == Piece.Type.AGENT) {
				agentY = piece.y;
				break;
			}
		}

		int goalY = grid.m - 2;

		// Estimate the distance between the agent's location and the goal
		return Math.abs(agentY - goalY); // Manhattan distance
	}

	public int heuristic2() {
		// Getting the current location of the agent
		int agentY = 0;
		for (Piece piece : grid.Pieces_List) {
			if (piece.type == Piece.Type.AGENT) {
				agentY = piece.y;
				break;
			}
		}

		int goalY = grid.m - 2;
		int nonEmptyCellsCount = 0;

		// Count the number of non-empty cells between the agent and the goal
		for (int i = Math.min(agentY, goalY); i <= Math.max(agentY, goalY); i++) {
			if (grid.grid[1][i] != null) {
				nonEmptyCellsCount++;
			}
		}
		return nonEmptyCellsCount;
	}

}
