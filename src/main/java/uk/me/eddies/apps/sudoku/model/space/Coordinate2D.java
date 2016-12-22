/* Copyright Steven Eddies, 2016. See the LICENCE file in the project root. */

package uk.me.eddies.apps.sudoku.model.space;

import uk.me.eddies.apps.sudoku.model.type.Coordinate;

/**
 * Represents a two-dimensional {@link Coordinate}.
 */
public final class Coordinate2D implements Coordinate {

	private final int x;
	private final int y;
	
	public Coordinate2D(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	@Override
	public int hashCode() {
		final int PRIME = 31;
		return (PRIME * x) + (PRIME * PRIME * y);
	}

	@Override
	public boolean equals(Object obj) {
		if ((obj == null) || (getClass() != obj.getClass())) return false;
		Coordinate2D other = (Coordinate2D) obj;
		return (x == other.x) && (y == other.y);
	}
	
	@Override
	public String toString() {
		return String.format("(%d, %d)", x, y);
	}
}
