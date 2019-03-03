/* Copyright Steven Eddies, 2019. See the LICENCE file in the project root. */

package uk.me.eddies.apps.sudoku.model.puzzle;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import org.junit.Before;
import org.junit.Test;
import uk.me.eddies.apps.sudoku.model.space.Coordinate2D;
import uk.me.eddies.apps.sudoku.model.type.GroupType;
import uk.me.eddies.apps.sudoku.model.type.PuzzleType;
import uk.me.eddies.apps.sudoku.model.type.Tokens;

public class PuzzleFactoryTest {
	
	private PuzzleType<Coordinate2D> type;
	private GroupType<Coordinate2D> groupType1;
	
	private PuzzleFactory<Coordinate2D> systemUnderTest;
	
	@Before
	public void setUp() {
		groupType1 = new GroupType<>(new LinkedHashSet<>(Arrays.asList(new Coordinate2D(0, 0), new Coordinate2D(0, 1))));
		type = new PuzzleType<>(
				"Irrelevant",
				2,
				new LinkedHashSet<>(Arrays.asList(new Coordinate2D(0, 0), new Coordinate2D(0, 1))),
				Collections.singleton(groupType1),
				new Tokens(Arrays.asList("0", "1")));
		
		systemUnderTest = new PuzzleFactory<>(type);
	}
	
	@Test
	public void shouldCreateBlankPuzzleWithCorrectType() {
		assertThat(systemUnderTest.blank().getType(), equalTo(type));
	}
	
	@Test
	public void shouldCreateBlankPuzzleWithCorrectGroups() {
		Puzzle<Coordinate2D> puzzle = systemUnderTest.blank();
		assertThat(puzzle.getGroups(), hasSize(1));
		Group<Coordinate2D> group1 = puzzle.getGroups().iterator().next();
		assertThat(group1.getType(), equalTo(groupType1));
	}

	// TODO: further test cases
}
