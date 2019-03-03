/* Copyright Steven Eddies, 2016. See the LICENCE file in the project root. */

package uk.me.eddies.apps.sudoku.model.puzzle;

import static java.util.Collections.unmodifiableSet;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toSet;

import java.util.Set;

import uk.me.eddies.apps.sudoku.model.type.Coordinate;
import uk.me.eddies.apps.sudoku.model.type.GroupType;

/**
 * Represents a group of cells in an actual puzzle, between which each possible
 * value must be represented exactly once.
 */
public class Group <C extends Coordinate> {
	
	private final GroupType<C> type;
	private final Set<Cell<C>> cells;
	
	Group(GroupType<C> type, CellLocator<C> cellLocator) {
		this.type = requireNonNull(type);
		this.cells = unmodifiableSet(
				this.type.getCells().stream()
				.map(cellLocator::getCell)
				.collect(toSet()));
	}

	public GroupType<C> getType() {
		return type;
	}

	public Set<Cell<C>> getCells() {
		return cells;
	}
}
