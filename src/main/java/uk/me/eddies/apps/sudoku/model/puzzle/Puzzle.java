/* Copyright Steven Eddies, 2016. See the LICENCE file in the project root. */

package uk.me.eddies.apps.sudoku.model.puzzle;

import static java.util.Collections.unmodifiableSet;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toSet;

import java.util.Set;
import java.util.function.Function;

import uk.me.eddies.apps.sudoku.model.type.Coordinate;
import uk.me.eddies.apps.sudoku.model.type.GroupType;
import uk.me.eddies.apps.sudoku.model.type.PuzzleType;

/**
 * Represents an actual Sudoku-like puzzle.
 */
public class Puzzle <C extends Coordinate> {
	
	private final PuzzleType<C> type;
	private final Set<Cell<C>> cells;
	private final Set<Group<C>> groups;
	private final CellLocator<C> cellLocator;
	
	Puzzle(PuzzleType<C> type, CellLocator<C> cellLocator, Function<GroupType<C>, Group<C>> groupFactory) {
		this.type = requireNonNull(type);
		this.cells = unmodifiableSet(
				type.getCells().stream()
				.map(cellLocator::getCell)
				.collect(toSet()));
		requireNonNull(groupFactory);
		this.groups = unmodifiableSet(
				type.getGroups().stream()
				.map(gt -> constructGroup(gt, groupFactory))
				.collect(toSet()));
		this.cellLocator = requireNonNull(cellLocator);
	}
	
	public PuzzleType<C> getType() {
		return type;
	}

	public Set<Cell<C>> getCells() {
		return cells;
	}
	
	public Cell<C> getCell(C coordinate) {
		return cellLocator.getCell(coordinate);
	}

	public Set<Group<C>> getGroups() {
		return groups;
	}

	private static <C extends Coordinate> Group<C> constructGroup(
			GroupType<C> type, Function<GroupType<C>, Group<C>> factory) {
		Group<C> group = factory.apply(type);
		if (!group.getType().equals(type))
			throw new IllegalStateException("Constructed group has wrong type.");
		return group;
	}
}
