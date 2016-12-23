/* Copyright Steven Eddies, 2016. See the LICENCE file in the project root. */

package uk.me.eddies.apps.sudoku.model.puzzle;

import static java.util.Objects.requireNonNull;

import java.util.OptionalInt;

import uk.me.eddies.apps.sudoku.model.type.Coordinate;

/**
 * Represents a cell in an actual puzzle.
 */
public class Cell <C extends Coordinate> {

	private final C coordinate;
	private OptionalInt value;
	
	Cell(C coordinate, OptionalInt value) {
		this.coordinate = requireNonNull(coordinate);
		setValue(value);
	}

	public boolean hasValue() {
		return value.isPresent();
	}

	public OptionalInt getValue() {
		return value;
	}
	
	void setValue(OptionalInt value) {
		this.value = requireNonNull(value);
	}
	
	public C getCoordinate() {
		return coordinate;
	}
}
