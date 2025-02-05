package Project;

public class Telekinesis {
	public static Grid GenGrid() {
		Grid root = new Grid();
		return root;
	}

	public static void Search(Grid grid, String strategy, boolean visualize) {
		State s = new State(grid, null, "", 0, 0, null); // create the root state

		// switch and call methods genericSearch
		switch (strategy) {
		case "BF":
			Generic_Search.BFS(s, visualize);
			break;
		case "DF":
			Generic_Search.DFS(s, visualize);
			break;
		case "ID":
			Generic_Search.IDS(s, visualize);
			break;
		case "UC":
			Generic_Search.UCS(s, visualize);
			break;
		case "GR1":
			Generic_Search.GS1(s, visualize);
			break;
		case "AS1":
			Generic_Search.AS1(s, visualize);
			break;
		case "GR2":
			Generic_Search.GS2(s, visualize);
			break;
		case "AS2":
			Generic_Search.AS2(s, visualize);
			break;
		default:
			System.out.println("Invalid search algorithm type");
		}
	}

	public static void main(String[] args) {
		Grid g1 = GenGrid();
		g1.printGrid();
		// g1.printPiecesList();

		Search(g1, "AS2", true);
	}
}
