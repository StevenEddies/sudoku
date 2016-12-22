/* Copyright Steven Eddies, 2016. See the LICENCE file in the project root. */

package uk.me.eddies.apps.sudoku.model.type;

import static java.util.Collections.unmodifiableList;
import static java.util.Collections.unmodifiableSet;
import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Represents a type of Sudoku-like puzzle.
 */
public final class PuzzleType <C extends Coordinate> {

	private final String name;
	private final int size;
	private final Set<C> cells;
	private final Set<GroupType<C>> groups;
	private final List<String> valueTokens;
	
	public PuzzleType(
			String name,
			int size,
			Set<C> cells,
			Set<GroupType<C>> groups,
			List<String> valueTokens
	) {
		this.name = requireNonNull(name);
		this.size = size;

		this.cells = unmodifiableSet(new LinkedHashSet<>(cells));
		this.cells.forEach(Objects::requireNonNull);
		
		this.groups = unmodifiableSet(new LinkedHashSet<>(groups));
		this.groups.forEach(Objects::requireNonNull);
		this.groups.forEach(g -> g.requireSize(this.size));
		this.groups.forEach(g -> g.validateCells(this.cells));
		
		this.valueTokens = unmodifiableList(new ArrayList<>(valueTokens));
		this.valueTokens.forEach(Objects::requireNonNull);
		if (this.valueTokens.size() != this.size)
			throw new IllegalArgumentException("Invalid value tokens size.");
	}

	public String getName() {
		return name;
	}
	
	public int getSize() {
		return size;
	}
	
	public Set<C> getCells() {
		return cells;
	}
	
	public Set<GroupType<C>> getGroups() {
		return groups;
	}
	
	public List<String> getValueTokens() {
		return valueTokens;
	}
	
	@Override
	public String toString() {
		return getName();
	}
	
	@Override
	public int hashCode() {
		final int PRIME = 41;
		return (PRIME * name.hashCode())
				+ (PRIME * PRIME * size)
				+ (PRIME * PRIME * PRIME * cells.hashCode())
				+ (PRIME * PRIME * PRIME * PRIME * groups.hashCode())
				+ (PRIME * PRIME * PRIME * PRIME * PRIME * valueTokens.hashCode());
	}

	@Override
	public boolean equals(Object obj) {
		if ((obj == null) || (getClass() != obj.getClass())) return false;
		PuzzleType<?> other = (PuzzleType<?>) obj;
		return name.equals(other.name)
				&& (size == other.size)
				&& cells.equals(other.cells)
				&& groups.equals(other.groups)
				&& valueTokens.equals(other.valueTokens);
	}
}
