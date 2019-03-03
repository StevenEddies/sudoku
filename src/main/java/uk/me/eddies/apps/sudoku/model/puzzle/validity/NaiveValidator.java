/* Copyright Steven Eddies, 2019. See the LICENCE file in the project root. */

package uk.me.eddies.apps.sudoku.model.puzzle.validity;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import uk.me.eddies.apps.sudoku.model.puzzle.Cell;
import uk.me.eddies.apps.sudoku.model.puzzle.Group;
import uk.me.eddies.apps.sudoku.model.puzzle.Puzzle;

/**
 * Produces a {@link Validity} for a {@link Puzzle} based solely on the puzzle itself and no known answers. This will
 * only detect invalid values if they contradict other placed values.
 */
public class NaiveValidator {
	
	// TODO: test
	
	public Validity validate(Puzzle<?> puzzle) {
		puzzle.getLock().readLock().lock();
		try {
		return puzzle.getGroups().stream()
				.map(this::validate)
				.min(Comparator.naturalOrder())
				.orElseThrow(() -> new IllegalStateException("Puzzle has no groups."));
		} finally {
			puzzle.getLock().readLock().unlock();
		}
	}

	private Validity validate(Group<?> group) {
		Set<Integer> values = new HashSet<>();
		boolean complete = true;
		for (Cell<?> eachCell : group.getCells()) {
			if (eachCell.getValue().isPresent()) {
				if (!values.add(eachCell.getValue().get())) {
					return Validity.INVALID;
				}
			} else {
				complete = false;
			}
		}
		return complete ? Validity.VALID : Validity.INCOMPLETE;
	}
}
