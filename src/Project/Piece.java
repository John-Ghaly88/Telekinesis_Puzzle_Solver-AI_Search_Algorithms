package Project;

public class Piece {
	public enum Type {
		AGENT, FURNITURE
	}

	public final int x;
	public final int y;
	public final boolean horizontal;
	public final int length;
	public final Type type;

	public Piece(int x, int y, boolean horizontal, int length, Type type) {
		this.x = x;
		this.y = y;
		this.horizontal = horizontal;
		this.length = length;
		this.type = type;
	}

	public Piece(Piece p) {
		this.x = p.x;
		this.y = p.y;
		this.horizontal = p.horizontal;
		this.length = p.length;
		this.type = p.type;
	}
}
