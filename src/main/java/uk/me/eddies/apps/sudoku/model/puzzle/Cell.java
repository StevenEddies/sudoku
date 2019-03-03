/* Copyright Steven Eddies, 2016-2019. See the LICENCE file in the project root. */

package uk.me.eddies.apps.sudoku.model.puzzle;

import static java.util.Objects.requireNonNull;

import java.util.Optional;
import java.util.concurrent.locks.ReadWriteLock;
import uk.me.eddies.apps.sudoku.model.type.Coordinate;
import uk.me.eddies.apps.sudoku.model.type.Tokens;

/**
 * Represents a cell in an actual puzzle.
 */
public class Cell <C extends Coordinate> {
	
	private static final String GIVEN_WITHOUT_VALUE_ERROR = "Given cell must have a value.";
	private static final String CHANGE_WHILE_GIVEN_ERROR = "Given cell's value cannot be changed.";
	
	private final ReadWriteLock lock;
	private final C coordinate;
	private final Tokens tokens;
	private boolean given;
	private Optional<Integer> value;
	
	Cell(ReadWriteLock lock, C coordinate, Tokens tokens, boolean given, Optional<Integer> value) {
		this.lock = requireNonNull(lock);
		this.coordinate = requireNonNull(coordinate);
		this.tokens = requireNonNull(tokens);
		this.given = given;
		this.value = requireNonNull(value);
		if (isGiven() && !hasValue()) throw new IllegalStateException(GIVEN_WITHOUT_VALUE_ERROR);
	}
	
	public boolean hasValue() {
		lock.readLock().lock();
		try {
			return value.isPresent();
		} finally {
			lock.readLock().unlock();
		}
	}
	
	public boolean isGiven() {
		lock.readLock().lock();
		try {
			return given;
		} finally {
			lock.readLock().unlock();
		}
	}
	
	public C getCoordinate() {
		return coordinate;
	}
	
	void setGiven(boolean given) {
		lock.writeLock().lock();
		try {
			if (given && !hasValue()) throw new IllegalStateException(GIVEN_WITHOUT_VALUE_ERROR);
			this.given = given;
		} finally {
			lock.writeLock().unlock();
		}
	}

	public Optional<Integer> getValue() {
		lock.readLock().lock();
		try {
			return value;
		} finally {
			lock.readLock().unlock();
		}
	}
	
	public Optional<String> getValueByToken() {
		lock.readLock().lock();
		try {
			return value.map(tokens::toToken);
		} finally {
			lock.readLock().unlock();
		}
	}
	
	void setValue(Optional<Integer> value) {
		lock.writeLock().lock();
		try {
			if (isGiven()) throw new IllegalStateException(CHANGE_WHILE_GIVEN_ERROR);
			this.value = requireNonNull(value);
		} finally {
			lock.writeLock().unlock();
		}
	}
	
	void setValueByToken(Optional<String> value) {
		lock.writeLock().lock();
		try {
			setValue(value.map(tokens::toValue));
		} finally {
			lock.writeLock().unlock();
		}
	}
}
