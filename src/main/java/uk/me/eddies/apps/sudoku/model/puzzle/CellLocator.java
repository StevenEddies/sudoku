/* Copyright Steven Eddies, 2016. See the LICENCE file in the project root. */

package uk.me.eddies.apps.sudoku.model.puzzle;

import static java.util.Objects.requireNonNull;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import uk.me.eddies.apps.sudoku.model.type.Coordinate;

/**
 * Represents a mapping between locations and cells for a particular puzzle.
 */
class CellLocator <C extends Coordinate> {
	
	private final Function<C, Cell<C>> cellFactory;
	private final Map<C, Cell<C>> cells;
	
	CellLocator(Function<C, Cell<C>> cellFactory) {
		this.cellFactory = requireNonNull(cellFactory);
		this.cells = new HashMap<>();
	}
	
	Cell<C> getCell(C coordinate) {
		return cells.computeIfAbsent(coordinate, this::constructCell);
	}
	
	private Cell<C> constructCell(C coordinate) {
		Cell<C> cell = cellFactory.apply(coordinate);
		if (!cell.getCoordinate().equals(coordinate))
			throw new IllegalStateException("Constructed cell has wrong coordinate.");
		return cell;
	}
}
