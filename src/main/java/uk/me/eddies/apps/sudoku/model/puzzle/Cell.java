/* Copyright Steven Eddies, 2016. See the LICENCE file in the project root. */

package uk.me.eddies.apps.sudoku.model.puzzle;

import static java.util.Objects.requireNonNull;

import java.util.Optional;

import uk.me.eddies.apps.sudoku.model.type.Coordinate;
import uk.me.eddies.apps.sudoku.model.type.Tokens;

/**
 * Represents a cell in an actual puzzle.
 */
public class Cell <C extends Coordinate> {
	
	private static final String GIVEN_WITHOUT_VALUE_ERROR = "Given cell must have a value.";
	private static final String CHANGE_WHILE_GIVEN_ERROR = "Given cell's value cannot be changed.";
	
	private final C coordinate;
	private final Tokens tokens;
	private boolean given;
	private Optional<Integer> value;
	
	Cell(C coordinate, Tokens tokens, boolean given, Optional<Integer> value) {
		this.coordinate = requireNonNull(coordinate);
		this.tokens = requireNonNull(tokens);
		this.given = given;
		this.value = requireNonNull(value);
		if (isGiven() && !hasValue()) throw new IllegalStateException(GIVEN_WITHOUT_VALUE_ERROR);
	}
	
	public boolean hasValue() {
		return value.isPresent();
	}
	
	public boolean isGiven() {
		return given;
	}
	
	public C getCoordinate() {
		return coordinate;
	}
	
	void setGiven(boolean given) {
		if (given && !hasValue()) throw new IllegalStateException(GIVEN_WITHOUT_VALUE_ERROR);
		this.given = given;
	}

	public Optional<Integer> getValue() {
		return value;
	}
	
	public Optional<String> getValueByToken() {
		return value.map(tokens::toToken);
	}
	
	void setValue(Optional<Integer> value) {
		if (isGiven()) throw new IllegalStateException(CHANGE_WHILE_GIVEN_ERROR);
		this.value = requireNonNull(value);
	}
	
	void setValueByToken(Optional<String> value) {
		setValue(value.map(tokens::toValue));
	}
}
