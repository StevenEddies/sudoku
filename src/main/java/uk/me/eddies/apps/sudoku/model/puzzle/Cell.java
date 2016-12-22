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
	private final int puzzleSize;
	private OptionalInt value;
	
	Cell(C coordinate, OptionalInt value, int puzzleSize) {
		this.coordinate = requireNonNull(coordinate);
		if (puzzleSize <= 0)
			throw new IllegalArgumentException(String.format("Invalid puzzle size %d (must be greater than 0).", puzzleSize));
		this.puzzleSize = puzzleSize;
		setValue(value);
	}

	public boolean hasValue() {
		return value.isPresent();
	}

	public OptionalInt getValue() {
		return value;
	}
	
	void setValue(OptionalInt value) {
		value.ifPresent(v -> {
			if ((v < 0) || (v >= puzzleSize))
				throw new IllegalArgumentException(String.format("Invalid value %d for puzzle size %d.", v, puzzleSize));
		});
		this.value = requireNonNull(value);
	}
	
	public C getCoordinate() {
		return coordinate;
	}
}
