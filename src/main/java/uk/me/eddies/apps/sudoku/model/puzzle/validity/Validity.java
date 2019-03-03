/* Copyright Steven Eddies, 2019. See the LICENCE file in the project root. */

package uk.me.eddies.apps.sudoku.model.puzzle.validity;


/**
 * Represents various states of validity that a puzzle or group can have.
 */
public enum Validity {

	/** Represents that some values in the puzzle must be invalid. */
	INVALID(false, false),
	/** Represents that no values in the puzzle are known to be invalid but some values are missing. */
	INCOMPLETE(true, false),
	/** Represents that the puzzle is complete and valid. */
	VALID(true, true);
	
	private final boolean validSoFar;
	private final boolean complete;
	
	private Validity(boolean validSoFar, boolean complete) {
		this.validSoFar = validSoFar;
		this.complete = complete;
	}
	
	public boolean isValidSoFar() {
		return validSoFar;
	}

	public boolean isComplete() {
		return complete;
	}
}
