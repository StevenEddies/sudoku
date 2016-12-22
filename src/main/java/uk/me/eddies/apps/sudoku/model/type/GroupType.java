/* Copyright Steven Eddies, 2016. See the LICENCE file in the project root. */

package uk.me.eddies.apps.sudoku.model.type;

import static java.util.Collections.unmodifiableSet;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Represents, as part of a {@link PuzzleType}, a group of cells which must
 * between them contain exactly one of each possible value.
 */
public final class GroupType <C extends Coordinate> {

	private final Set<C> cells;
	
	public GroupType(Set<C> cells) {
		this.cells = unmodifiableSet(new LinkedHashSet<>(cells));
		this.cells.forEach(Objects::requireNonNull);
	}
	
	public Set<C> getCells() {
		return cells;
	}
	
	int getPuzzleSize() {
		return cells.size();
	}

	@Override
	public int hashCode() {
		final int PRIME = 37;
		return (PRIME * cells.hashCode());
	}

	@Override
	public boolean equals(Object obj) {
		if ((obj == null) || (getClass() != obj.getClass())) return false;
		GroupType<?> other = (GroupType<?>) obj;
		return cells.equals(other.cells);
	}
}
