package Project;

import java.util.*;

public class Grid {
	public Piece[][] grid;
	public ArrayList<Piece> Pieces_List;
	public int n; // no. of rows
	public int m; // no. of columns
	boolean goal;

	// deep cloning of the grid
	public Grid(Grid g) {
		this.grid = new Piece[g.grid.length][g.grid[0].length];
		Pieces_List = new ArrayList<Piece>();

		for (Piece p : g.Pieces_List) {

			if (p.horizontal) {
				for (int i = 0; i < p.length; i++) {
					grid[p.x][p.y + i] = new Piece(p.x, p.y + i, p.horizontal, p.length, p.type);
					if (i == 0)
						Pieces_List.add(grid[p.x][p.y]);
				}
			} else {
				for (int i = 0; i < p.length; i++) {
					grid[p.x + i][p.y] = new Piece(p.x + i, p.y, p.horizontal, p.length, p.type);
					if (i == 0)
						Pieces_List.add(grid[p.x][p.y]);
				}
			}

		}

		this.m = g.m;
		this.n = g.n;
		this.goal = g.goal;
	}

	// random generation of the initial grid
	public Grid() {
		Random random = new Random();

		int minSize = 4; // 4 as specified in the description
		int maxSize = 8; // 8 is for instance, can be changed

		// Generate the grid with random n x m
		n = random.nextInt(maxSize - minSize + 1) + minSize; // Random number of rows
		m = random.nextInt(maxSize - minSize + 1) + minSize; // Random number of cols

		// initialize the attributes
		this.goal = false;
		this.grid = new Piece[n][m];
		this.Pieces_List = new ArrayList<Piece>();

		// Randomly place agent in the second row
		int agentX = 1;
		int agentY = random.nextInt(m - 1);
		// add the agent to the
		grid[agentX][agentY] = new Piece(agentX, agentY, true, 2, Piece.Type.AGENT); // 1st part
		grid[agentX][agentY + 1] = new Piece(agentX, agentY + 1, true, 2, Piece.Type.AGENT); // 2nd part

		if (agentY == m - 2)
			this.goal = true;

		Pieces_List.add(grid[agentX][agentY]);

		// Randomly place furniture pieces
		for (int attempt = 0; attempt < n * m * 2; attempt++) { // Arbitrary attempt limit to avoid infinite loops
			int x = random.nextInt(n);
			int y = random.nextInt(m);
			int length = random.nextInt(2) + 2; // Length 2 or 3
			boolean horizontal = random.nextBoolean();

			if (canPlacePiece(grid, x, y, horizontal, length, n, m)) {
				for (int i = 0; i < length; i++) {
					if (horizontal) {
						grid[x][y + i] = new Piece(x, y + i, true, length, Piece.Type.FURNITURE);
					} else {
						grid[x + i][y] = new Piece(x + i, y, false, length, Piece.Type.FURNITURE);
					}
				}
				Pieces_List.add(grid[x][y]);
			}
		}
	}

	// A pre-check to ensure that a piece can fit in the intended location without
	// overlapping other pieces or extending beyond the grid boundaries
	private static boolean canPlacePiece(Piece[][] grid, int x, int y, boolean horizontal, int length, int n, int m) {
		if (horizontal) {
			if (y + length > m)
				return false; // Check if it goes out of boundaries
			for (int i = 0; i < length; i++) {
				if (grid[x][y + i] != null)
					return false; // Check for an existing piece
			}
		} else {
			if (x + length > n)
				return false; // Check if it goes out of boundaries
			for (int i = 0; i < length; i++) {
				if (grid[x + i][y] != null)
					return false; // Check for an existing piece
			}
		}
		return true;
	}

	public void printGrid() {
		for (Piece[] row : grid) {
			for (Piece piece : row) {
				if (piece != null) {
					switch (piece.type) {
					case AGENT:
						System.out.print("A ");
						break;
					case FURNITURE:
						if (piece.horizontal) {
							System.out.print("H ");
						} else {
							System.out.print("V ");
						}
						break;
					}
				} else {
					System.out.print("- ");
				}
			}
			System.out.println();
		}
	}

	public void printPiecesList() {
		int counter = 0;
		System.out.println("List of Pieces:");
		for (Piece piece : Pieces_List) {
			counter++;
			if (piece != null) {
				System.out.printf("Type: %s, X: %d, Y: %d, Length: %d, Horizontal: %s\n", piece.type, piece.x, piece.y,
						piece.length, piece.horizontal ? "Yes" : "No");
			}
		}
		System.out.println("Total number of pieces: " + counter);
	}

	public boolean moveUp(Piece p) {
		if (p.horizontal)
			return false;
		if (p.x == 0)
			return false;
		if (!canPlacePiece(grid, p.x - 1, p.y, p.horizontal, 1, n, m)) // length 1 because I just need to check the up
																		// cell
			return false;
		else {
			for (int i = 0; i < p.length; i++) {
				int x = p.x + i;
				int y = p.y;
				grid[x - 1][y] = new Piece(x - 1, y, false, p.length, p.type);
				grid[x][y] = null;
				if (i == 0) {
					this.Pieces_List.remove(p);
					this.Pieces_List.add(grid[x - 1][y]);
				}
			}
			return true;
		}
	}

	public boolean moveDown(Piece p) {
		if (p.horizontal)
			return false;
		if (p.x == n)
			return false;
		if (!canPlacePiece(grid, p.x + p.length, p.y, p.horizontal, 1, n, m)) // length 1 because I just need to check
																				// the down cell
			return false;
		else {
			for (int i = p.length - 1; i >= 0; i--) {
				int x = p.x + i;
				int y = p.y;
				grid[x + 1][y] = new Piece(x + 1, y, false, p.length, p.type);
				grid[x][y] = null;
				if (i == 0) {
					this.Pieces_List.remove(p);
					this.Pieces_List.add(grid[x + 1][y]);
				}
			}
			return true;
		}
	}

	public boolean moveLeft(Piece p) {
		if (!p.horizontal)
			return false;
		if (p.y == 0)
			return false;
		if (!canPlacePiece(grid, p.x, p.y - 1, p.horizontal, 1, n, m)) // length 1 because I just need to check the left
																		// cell
			return false;
		else {
			for (int i = 0; i < p.length; i++) {
				int x = p.x;
				int y = p.y + i;
				grid[x][y - 1] = new Piece(x, y - 1, true, p.length, p.type);
				grid[x][y] = null;
				if (i == 0) {
					this.Pieces_List.remove(p);
					this.Pieces_List.add(grid[x][y - 1]);
				}
			}
			return true;
		}
	}

	public boolean moveRight(Piece p) {
		if (!p.horizontal)
			return false;
		if (p.y == m)
			return false;
		if (!canPlacePiece(grid, p.x, p.y + p.length, p.horizontal, 1, n, m)) // length 1 because I just need to check
																				// the left cell
			return false;
		else {
			for (int i = p.length - 1; i >= 0; i--) {
				int x = p.x;
				int y = p.y + i;
				grid[x][y + 1] = new Piece(x, y + 1, true, p.length, p.type);
				grid[x][y] = null;
				if (i == 0) {
					this.Pieces_List.remove(p);
					this.Pieces_List.add(grid[x][y + 1]);
				}
			}

			// goal test
			if ((p.type == Piece.Type.AGENT) && (p.y == m - 3)) // -3 because we check on the old p
				goal = true;

			return true;
		}
	}
}
