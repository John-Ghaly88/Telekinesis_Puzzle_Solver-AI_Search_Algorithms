package Project;

import java.util.*;

public class Generic_Search {
	// ******* Breadth First Search **********
	public static void BFS(State root, boolean visualize) {
		ArrayList<State> q = new ArrayList<State>();
		HashSet<String> VisitedStates = new HashSet<String>(); // Use HashSet to ensure no duplicates

		q.add(root);

		while (!q.isEmpty()) {
			State current = q.remove(0);
			String currentStateKey = getStateKey(current); // Create a unique key for the current state

			// check if the state has been visited before
			if (VisitedStates.contains(currentStateKey)) {
				continue;
			}
			VisitedStates.add(currentStateKey);

			// check if the goal is reached
			if (current.grid.goal) {
				if (visualize) {
					PrintResults(current, VisitedStates);
				}
				break;
			}

			// Loop on the pieces of the current grid to expand accordingly
			for (int i = 0; i < current.grid.Pieces_List.size(); i++) {
				Piece p = current.grid.Pieces_List.get(i);

				if (p.horizontal) {
					// Moving Left
					Grid grid_copy = new Grid(current.grid);
					Piece p_copy = grid_copy.Pieces_List.get(i);

					if (grid_copy.moveLeft(p_copy)) {
						State next = new State(grid_copy, current, "MoveLeft", current.depth + 1, current.cost + 1,
								p_copy);
						q.add(next);
					}

					// Moving Right
					Grid grid_copy2 = new Grid(current.grid);
					Piece p_copy2 = grid_copy2.Pieces_List.get(i);

					if (grid_copy2.moveRight(p_copy2)) {
						State next = new State(grid_copy2, current, "MoveRight", current.depth + 1, current.cost + 1,
								p_copy2);
						q.add(next);
					}
				} else {
					// Moving Up
					Grid grid_copy = new Grid(current.grid);
					Piece p_copy = grid_copy.Pieces_List.get(i);

					if (grid_copy.moveUp(p_copy)) {
						State next = new State(grid_copy, current, "MoveUp", current.depth + 1, current.cost + 1,
								p_copy);
						q.add(next);
					}

					// Moving Down
					Grid grid_copy2 = new Grid(current.grid);
					Piece p_copy2 = grid_copy2.Pieces_List.get(i);

					if (grid_copy2.moveDown(p_copy2)) {
						State next = new State(grid_copy2, current, "MoveDown", current.depth + 1, current.cost + 1,
								p_copy2);
						q.add(next);
					}
				}
			}
		}
	}

	// ******* Depth First Search **********
	public static void DFS(State root, boolean visualize) {
		Stack<State> stack = new Stack<State>();
		HashSet<String> VisitedStates = new HashSet<String>(); // Use HashSet to ensure no duplicates

		stack.push(root);

		while (!stack.isEmpty()) {
			State current = stack.pop();
			String currentStateKey = getStateKey(current); // Create a unique key for the current state

			// check if the state has been visited before
			if (VisitedStates.contains(currentStateKey)) {
				continue;
			}
			VisitedStates.add(currentStateKey);

			// check if the goal is reached
			if (current.grid.goal) {
				if (visualize) {
					PrintResults(current, VisitedStates);
				}
				break;
			}

			// Loop on the pieces of the current grid to expand accordingly
			for (int i = 0; i < current.grid.Pieces_List.size(); i++) {
				Piece p = current.grid.Pieces_List.get(i);

				if (p.horizontal) {
					// Moving Left
					Grid grid_copy = new Grid(current.grid);
					Piece p_copy = grid_copy.Pieces_List.get(i);

					if (grid_copy.moveLeft(p_copy)) {
						State next = new State(grid_copy, current, "MoveLeft", current.depth + 1, current.cost + 1,
								p_copy);
						stack.push(next);
					}

					// Moving Right
					Grid grid_copy2 = new Grid(current.grid);
					Piece p_copy2 = grid_copy2.Pieces_List.get(i);

					if (grid_copy2.moveRight(p_copy2)) {
						State next = new State(grid_copy2, current, "MoveRight", current.depth + 1, current.cost + 1,
								p_copy2);
						stack.push(next);
					}
				} else {
					// Moving Up
					Grid grid_copy = new Grid(current.grid);
					Piece p_copy = grid_copy.Pieces_List.get(i);

					if (grid_copy.moveUp(p_copy)) {
						State next = new State(grid_copy, current, "MoveUp", current.depth + 1, current.cost + 1,
								p_copy);
						stack.push(next);
					}

					// Moving Down
					Grid grid_copy2 = new Grid(current.grid);
					Piece p_copy2 = grid_copy2.Pieces_List.get(i);

					if (grid_copy2.moveDown(p_copy2)) {
						State next = new State(grid_copy2, current, "MoveDown", current.depth + 1, current.cost + 1,
								p_copy2);
						stack.push(next);
					}
				}
			}
		}
	}

	// ******* Iterative Deepening Search **********
	public static void IDS(State root, boolean visualize) {
		HashSet<String> VisitedStates;
		int maxDepth = 10000;
		int depthLimit = 0;

		while (depthLimit <= maxDepth) {
			VisitedStates = new HashSet<>(); // Reset visited states at each depth iteration
			boolean goalFound = DLS(root, visualize, depthLimit, VisitedStates);
			if (goalFound) {
				break;
			}
			depthLimit++;
		}
	}

	// ******* Depth Limited Search **********
	public static boolean DLS(State node, boolean visualize, int depthLimit, HashSet<String> VisitedStates) {
		// same as DFS but with checking the limit
		Stack<State> stack = new Stack<>();

		stack.push(node);

		while (!stack.isEmpty()) {
			State current = stack.pop();
			String currentStateKey = getStateKey(current);

			// check if the state has been visited before
			if (VisitedStates.contains(currentStateKey)) {
				continue;
			}
			VisitedStates.add(currentStateKey);

			// check if the goal is reached
			if (current.grid.goal) {
				if (visualize) {
					PrintResults(current, VisitedStates);
				}
				return true;
			}

			// check depth limit
			if (current.depth >= depthLimit) {
				continue;
			}

			// Loop on the pieces of the current grid to expand accordingly
			for (int i = 0; i < current.grid.Pieces_List.size(); i++) {
				Piece p = current.grid.Pieces_List.get(i);

				if (p.horizontal) {
					// Moving Left
					Grid grid_copy = new Grid(current.grid);
					Piece p_copy = grid_copy.Pieces_List.get(i);

					if (grid_copy.moveLeft(p_copy)) {
						State next = new State(grid_copy, current, "MoveLeft", current.depth + 1, current.cost + 1,
								p_copy);
						stack.push(next);
					}

					// Moving Right
					Grid grid_copy2 = new Grid(current.grid);
					Piece p_copy2 = grid_copy2.Pieces_List.get(i);

					if (grid_copy2.moveRight(p_copy2)) {
						State next = new State(grid_copy2, current, "MoveRight", current.depth + 1, current.cost + 1,
								p_copy2);
						stack.push(next);
					}
				} else {
					// Moving Up
					Grid grid_copy = new Grid(current.grid);
					Piece p_copy = grid_copy.Pieces_List.get(i);

					if (grid_copy.moveUp(p_copy)) {
						State next = new State(grid_copy, current, "MoveUp", current.depth + 1, current.cost + 1,
								p_copy);
						stack.push(next);
					}

					// Moving Down
					Grid grid_copy2 = new Grid(current.grid);
					Piece p_copy2 = grid_copy2.Pieces_List.get(i);

					if (grid_copy2.moveDown(p_copy2)) {
						State next = new State(grid_copy2, current, "MoveDown", current.depth + 1, current.cost + 1,
								p_copy2);
						stack.push(next);
					}
				}
			}
		}
		return false; // Goal not found within the depth limit
	}

	// ******* Uniform Cost Search **********
	public static void UCS(State root, boolean visualize) {
		// the only difference between UCS & BFS is that we use priority queue based on
		// the cost instead of a normal queue
		PriorityQueue<State> pq = new PriorityQueue<>(new Comparator<State>() {
			@Override
			public int compare(State s1, State s2) {
				return Integer.compare(s1.cost, s2.cost);
			}
		});

		HashSet<String> VisitedStates = new HashSet<String>(); // Use HashSet to ensure no duplicates

		pq.add(root);

		while (!pq.isEmpty()) {
			State current = pq.poll();
			String currentStateKey = getStateKey(current); // Create a unique key for the current state

			// check if the state has been visited before
			if (VisitedStates.contains(currentStateKey)) {
				continue;
			}
			VisitedStates.add(currentStateKey);

			// check if the goal is reached
			if (current.grid.goal) {
				if (visualize) {
					PrintResults(current, VisitedStates);
				}
				break;
			}

			// Loop on the pieces of the current grid to expand accordingly
			for (int i = 0; i < current.grid.Pieces_List.size(); i++) {
				Piece p = current.grid.Pieces_List.get(i);

				if (p.horizontal) {
					// Moving Left
					Grid grid_copy = new Grid(current.grid);
					Piece p_copy = grid_copy.Pieces_List.get(i);

					if (grid_copy.moveLeft(p_copy)) {
						State next = new State(grid_copy, current, "MoveLeft", current.depth + 1, current.cost + 1,
								p_copy);
						pq.add(next);
					}

					// Moving Right
					Grid grid_copy2 = new Grid(current.grid);
					Piece p_copy2 = grid_copy2.Pieces_List.get(i);

					if (grid_copy2.moveRight(p_copy2)) {
						State next = new State(grid_copy2, current, "MoveRight", current.depth + 1, current.cost + 1,
								p_copy2);
						pq.add(next);
					}
				} else {
					// Moving Up
					Grid grid_copy = new Grid(current.grid);
					Piece p_copy = grid_copy.Pieces_List.get(i);

					if (grid_copy.moveUp(p_copy)) {
						State next = new State(grid_copy, current, "MoveUp", current.depth + 1, current.cost + 1,
								p_copy);
						pq.add(next);
					}

					// Moving Down
					Grid grid_copy2 = new Grid(current.grid);
					Piece p_copy2 = grid_copy2.Pieces_List.get(i);

					if (grid_copy2.moveDown(p_copy2)) {
						State next = new State(grid_copy2, current, "MoveDown", current.depth + 1, current.cost + 1,
								p_copy2);
						pq.add(next);
					}
				}
			}
		}
	}

	// ******* Greedy Search 1 **********
	// Heuristic: The distance between the agent’s location and the goal.
	public static void GS1(State root, boolean visualize) {
		// The priority queue is based on the heuristic function implemented in the
		// state class, which returns the difference between the agent's location and
		// the goal
		PriorityQueue<State> pq = new PriorityQueue<>(Comparator.comparingInt(s -> s.heuristic1()));
		HashSet<String> VisitedStates = new HashSet<>(); // Use HashSet to ensure no duplicates

		pq.add(root);

		while (!pq.isEmpty()) {
			State current = pq.poll();
			String currentStateKey = getStateKey(current); // Create a unique key for the current state

			// check if the state has been visited before
			if (VisitedStates.contains(currentStateKey)) {
				continue;
			}
			VisitedStates.add(currentStateKey);

			// check if the goal is reached
			if (current.grid.goal) {
				if (visualize) {
					PrintResults(current, VisitedStates);
				}
				break;
			}

			// Loop on the pieces of the current grid to expand accordingly
			for (int i = 0; i < current.grid.Pieces_List.size(); i++) {
				Piece p = current.grid.Pieces_List.get(i);

				if (p.horizontal) {
					// Moving Left
					Grid grid_copy = new Grid(current.grid);
					Piece p_copy = grid_copy.Pieces_List.get(i);

					if (grid_copy.moveLeft(p_copy)) {
						State next = new State(grid_copy, current, "MoveLeft", current.depth + 1, current.cost + 1,
								p_copy);
						pq.add(next);
					}

					// Moving Right
					Grid grid_copy2 = new Grid(current.grid);
					Piece p_copy2 = grid_copy2.Pieces_List.get(i);

					if (grid_copy2.moveRight(p_copy2)) {
						State next = new State(grid_copy2, current, "MoveRight", current.depth + 1, current.cost + 1,
								p_copy2);
						pq.add(next);
					}
				} else {
					// Moving Up
					Grid grid_copy = new Grid(current.grid);
					Piece p_copy = grid_copy.Pieces_List.get(i);

					if (grid_copy.moveUp(p_copy)) {
						State next = new State(grid_copy, current, "MoveUp", current.depth + 1, current.cost + 1,
								p_copy);
						pq.add(next);
					}

					// Moving Down
					Grid grid_copy2 = new Grid(current.grid);
					Piece p_copy2 = grid_copy2.Pieces_List.get(i);

					if (grid_copy2.moveDown(p_copy2)) {
						State next = new State(grid_copy2, current, "MoveDown", current.depth + 1, current.cost + 1,
								p_copy2);
						pq.add(next);
					}
				}
			}
		}
	}

	// ******* A* Search 1 **********
	// Heuristic: The distance between the agent’s location and the goal.
	public static void AS1(State root, boolean visualize) {
		// The priority queue is now based on the heuristic + the cost
		PriorityQueue<State> pq = new PriorityQueue<>(Comparator.comparingInt(s -> (s.cost + s.heuristic1())));
		HashSet<String> VisitedStates = new HashSet<>(); // Use HashSet to ensure no duplicates

		pq.add(root);

		while (!pq.isEmpty()) {
			State current = pq.poll();
			String currentStateKey = getStateKey(current); // Create a unique key for the current state

			// check if the state has been visited before
			if (VisitedStates.contains(currentStateKey)) {
				continue;
			}
			VisitedStates.add(currentStateKey);

			// check if the goal is reached
			if (current.grid.goal) {
				if (visualize) {
					PrintResults(current, VisitedStates);
				}
				break;
			}

			// Loop on the pieces of the current grid to expand accordingly
			for (int i = 0; i < current.grid.Pieces_List.size(); i++) {
				Piece p = current.grid.Pieces_List.get(i);

				if (p.horizontal) {
					// Moving Left
					Grid grid_copy = new Grid(current.grid);
					Piece p_copy = grid_copy.Pieces_List.get(i);

					if (grid_copy.moveLeft(p_copy)) {
						State next = new State(grid_copy, current, "MoveLeft", current.depth + 1, current.cost + 1,
								p_copy);
						pq.add(next);
					}

					// Moving Right
					Grid grid_copy2 = new Grid(current.grid);
					Piece p_copy2 = grid_copy2.Pieces_List.get(i);

					if (grid_copy2.moveRight(p_copy2)) {
						State next = new State(grid_copy2, current, "MoveRight", current.depth + 1, current.cost + 1,
								p_copy2);
						pq.add(next);
					}
				} else {
					// Moving Up
					Grid grid_copy = new Grid(current.grid);
					Piece p_copy = grid_copy.Pieces_List.get(i);

					if (grid_copy.moveUp(p_copy)) {
						State next = new State(grid_copy, current, "MoveUp", current.depth + 1, current.cost + 1,
								p_copy);
						pq.add(next);
					}

					// Moving Down
					Grid grid_copy2 = new Grid(current.grid);
					Piece p_copy2 = grid_copy2.Pieces_List.get(i);

					if (grid_copy2.moveDown(p_copy2)) {
						State next = new State(grid_copy2, current, "MoveDown", current.depth + 1, current.cost + 1,
								p_copy2);
						pq.add(next);
					}
				}
			}
		}
	}

	// ******* Greedy Search 2 **********
	// Heuristic: The number of non-empty cells between the agent and the goal.
	public static void GS2(State root, boolean visualize) {
		// The priority queue is based on the heuristic function 2 implemented in the
		// state class, which returns the number of non-empty cells between the agent
		// and the goal
		PriorityQueue<State> pq = new PriorityQueue<>(Comparator.comparingInt(s -> s.heuristic2()));
		HashSet<String> VisitedStates = new HashSet<>(); // Use HashSet to ensure no duplicates

		pq.add(root);

		while (!pq.isEmpty()) {
			State current = pq.poll();
			String currentStateKey = getStateKey(current); // Create a unique key for the current state

			// check if the state has been visited before
			if (VisitedStates.contains(currentStateKey)) {
				continue;
			}
			VisitedStates.add(currentStateKey);

			// check if the goal is reached
			if (current.grid.goal) {
				if (visualize) {
					PrintResults(current, VisitedStates);
				}
				break;
			}

			// Loop on the pieces of the current grid to expand accordingly
			for (int i = 0; i < current.grid.Pieces_List.size(); i++) {
				Piece p = current.grid.Pieces_List.get(i);

				if (p.horizontal) {
					// Moving Left
					Grid grid_copy = new Grid(current.grid);
					Piece p_copy = grid_copy.Pieces_List.get(i);

					if (grid_copy.moveLeft(p_copy)) {
						State next = new State(grid_copy, current, "MoveLeft", current.depth + 1, current.cost + 1,
								p_copy);
						pq.add(next);
					}

					// Moving Right
					Grid grid_copy2 = new Grid(current.grid);
					Piece p_copy2 = grid_copy2.Pieces_List.get(i);

					if (grid_copy2.moveRight(p_copy2)) {
						State next = new State(grid_copy2, current, "MoveRight", current.depth + 1, current.cost + 1,
								p_copy2);
						pq.add(next);
					}
				} else {
					// Moving Up
					Grid grid_copy = new Grid(current.grid);
					Piece p_copy = grid_copy.Pieces_List.get(i);

					if (grid_copy.moveUp(p_copy)) {
						State next = new State(grid_copy, current, "MoveUp", current.depth + 1, current.cost + 1,
								p_copy);
						pq.add(next);
					}

					// Moving Down
					Grid grid_copy2 = new Grid(current.grid);
					Piece p_copy2 = grid_copy2.Pieces_List.get(i);

					if (grid_copy2.moveDown(p_copy2)) {
						State next = new State(grid_copy2, current, "MoveDown", current.depth + 1, current.cost + 1,
								p_copy2);
						pq.add(next);
					}
				}
			}
		}
	}

	// ******* A* Search 2 **********
	// Heuristic: The number of non-empty cells between the agent and the goal.
	public static void AS2(State root, boolean visualize) {
		// The priority queue is now based on the heuristic + the cost
		PriorityQueue<State> pq = new PriorityQueue<>(Comparator.comparingInt(s -> (s.cost + s.heuristic2())));
		HashSet<String> VisitedStates = new HashSet<>(); // Use HashSet to ensure no duplicates

		pq.add(root);

		while (!pq.isEmpty()) {
			State current = pq.poll();
			String currentStateKey = getStateKey(current); // Create a unique key for the current state

			// check if the state has been visited before
			if (VisitedStates.contains(currentStateKey)) {
				continue;
			}
			VisitedStates.add(currentStateKey);

			// check if the goal is reached
			if (current.grid.goal) {
				if (visualize) {
					PrintResults(current, VisitedStates);
				}
				break;
			}

			// Loop on the pieces of the current grid to expand accordingly
			for (int i = 0; i < current.grid.Pieces_List.size(); i++) {
				Piece p = current.grid.Pieces_List.get(i);

				if (p.horizontal) {
					// Moving Left
					Grid grid_copy = new Grid(current.grid);
					Piece p_copy = grid_copy.Pieces_List.get(i);

					if (grid_copy.moveLeft(p_copy)) {
						State next = new State(grid_copy, current, "MoveLeft", current.depth + 1, current.cost + 1,
								p_copy);
						pq.add(next);
					}

					// Moving Right
					Grid grid_copy2 = new Grid(current.grid);
					Piece p_copy2 = grid_copy2.Pieces_List.get(i);

					if (grid_copy2.moveRight(p_copy2)) {
						State next = new State(grid_copy2, current, "MoveRight", current.depth + 1, current.cost + 1,
								p_copy2);
						pq.add(next);
					}
				} else {
					// Moving Up
					Grid grid_copy = new Grid(current.grid);
					Piece p_copy = grid_copy.Pieces_List.get(i);

					if (grid_copy.moveUp(p_copy)) {
						State next = new State(grid_copy, current, "MoveUp", current.depth + 1, current.cost + 1,
								p_copy);
						pq.add(next);
					}

					// Moving Down
					Grid grid_copy2 = new Grid(current.grid);
					Piece p_copy2 = grid_copy2.Pieces_List.get(i);

					if (grid_copy2.moveDown(p_copy2)) {
						State next = new State(grid_copy2, current, "MoveDown", current.depth + 1, current.cost + 1,
								p_copy2);
						pq.add(next);
					}
				}
			}
		}
	}

	// helper for the VisitedStates HashSet
	public static String getStateKey(State state) {
		StringBuilder keyBuilder = new StringBuilder();
		for (Piece[] row : state.grid.grid) {
			for (Piece piece : row) {
				if (piece != null) {
					keyBuilder.append(piece.x).append(",").append(piece.y).append(",");
				} else {
					keyBuilder.append("null,");
				}
			}
		}
		return keyBuilder.toString();
	}

	// helper for the visualize boolean operator
	public static void PrintResults(State current, HashSet<String> visitedStates) {
		// De-comment to print all the expanded states
		/*
		 * for (State s : VisitedStates) { System.out.println(" ");
		 * System.out.println(" PARENTTTTT !!!"); s.previous_state.grid.printGrid();
		 * System.out.println(" "); System.out.println(" OPERATOR : ");
		 * System.out.println(s.previous_operator); System.out.println(" ");
		 * System.out.println(" PIECE !!!"); System.out.println(s.changed_piece.x + ","
		 * + s.changed_piece.y); System.out.println(" ");
		 * System.out.println(" CURRENT !!!"); s.grid.printGrid(); }
		 */

		// print path to goal
		// if it printed nothing then the goal is unreachable
		Stack<State> s = new Stack<State>();
		// push all states except root
		while (current.previous_state != null) {
			s.push(current);
			current = current.previous_state;
		}

		// push root
		s.push(current);
		current = current.previous_state;

		// pop and print
		while (!s.isEmpty()) {
			State x = s.pop();
			if (x.previous_state != null) { // if it is not root
				System.out.println("* Piece to be changed: ");
				System.out.println(x.changed_piece.x + "," + x.changed_piece.y);
				System.out.println(" ");
				System.out.println("* Operator to be applied:");
				System.out.println(x.previous_operator);
				System.out.println(" ");
			}
			System.out.println("* Current Grid:");
			x.grid.printGrid();
			System.out.println(" ");
		}
	}
}