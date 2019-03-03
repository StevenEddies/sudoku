/* Copyright Steven Eddies, 2016-2019. See the LICENCE file in the project root. */

package uk.me.eddies.apps.sudoku.model.puzzle;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;

import uk.me.eddies.apps.sudoku.model.type.Coordinate;
import uk.me.eddies.apps.sudoku.model.type.PuzzleType;

/**
 * Responsible for construction of {@link Puzzle}s.
 */
public class PuzzleFactory <C extends Coordinate> {
	
	private final PuzzleType<C> type;

	public PuzzleFactory(PuzzleType<C> type) {
		this.type = type;
	}
	
	public PuzzleType<C> getType() {
		return type;
	}

	public Puzzle<C> blank() {
		return createBlank();
	}
	
	public Puzzle<C> withData(Map<C, Integer> givenValues, Map<C, Integer> userValues) {
		Puzzle<C> puzzle = createBlank();
		puzzle.getLock().writeLock().lock();
		try {
			populate(puzzle, givenValues, true);
			populate(puzzle, userValues, false);
			return puzzle;
		} finally {
			puzzle.getLock().writeLock().unlock();
		}
	}
	
	public Puzzle<C> clone(Puzzle<C> original) {
		if (!original.getType().equals(type))
			throw new IllegalStateException("Cannot clone puzzle of wrong type.");
		Puzzle<C> puzzle = createBlank();
		puzzle.getLock().writeLock().lock();
		original.getLock().readLock().lock();
		try {
			populate(puzzle, extract(original, true), true);
			populate(puzzle, extract(original, false), false);
			return puzzle;
		} finally {
			original.getLock().readLock().unlock();
			puzzle.getLock().writeLock().unlock();
		}
	}
	
	private void populate(Puzzle<C> puzzle, Map<C, Integer> values, boolean given) {
		values.entrySet().forEach(entry -> {
			Cell<C> cell = puzzle.getCell(entry.getKey());
			cell.setValue(Optional.of(entry.getValue()));
			cell.setGiven(given);
		});
	}
	
	private Map<C, Integer> extract(Puzzle<C> original, boolean given) {
		return original.getCells().stream()
				.filter(Cell::hasValue)
				.filter(c -> (c.isGiven() == given))
				.collect(Collectors.toMap(
						Cell::getCoordinate,
						c -> c.getValue().get()));
	}

	private Puzzle<C> createBlank() {
		ReadWriteLock lock = new ReentrantReadWriteLock(false);
		lock.writeLock().lock();
		try {
			CellLocator<C> cellLocator = new CellLocator<>(c -> new Cell<>(lock, c, type.getValueTokens(), false, Optional.empty()));
			return new Puzzle<>(lock, type, cellLocator, gt -> new Group<>(gt, cellLocator));
		} finally {
			lock.writeLock().unlock();
		}
	}
}
