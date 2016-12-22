/* Copyright Steven Eddies, 2016. See the LICENCE file in the project root. */

package uk.me.eddies.apps.sudoku.model.type;

import static java.util.Collections.emptySet;
import static java.util.Collections.singleton;
import static java.util.Collections.singletonList;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import uk.me.eddies.apps.sudoku.model.space.Coordinate2D;

@RunWith(JUnitParamsRunner.class)
public class PuzzleTypeTest {
	
	private static final String NAME = "PT";
	private static final Coordinate2D CELL1 = new Coordinate2D(0, 0);
	private static final Coordinate2D CELL2 = new Coordinate2D(0, 1);
	private static final Coordinate2D CELL3 = new Coordinate2D(0, 2);
	private static final Coordinate2D CELL4 = new Coordinate2D(0, 4);
	
	private Set<Coordinate2D> cells;
	private GroupType<Coordinate2D> group1;
	private GroupType<Coordinate2D> group2;
	private Set<GroupType<Coordinate2D>> groups;
	private List<String> tokens;

	private PuzzleType<Coordinate2D> systemUnderTest;
	
	@Before
	public void setUp() {
		cells = new LinkedHashSet<>(Arrays.asList(CELL1, CELL2, CELL3));
		group1 = new GroupType<>(new LinkedHashSet<>(Arrays.asList(CELL1, CELL2)));
		group2 = new GroupType<>(new LinkedHashSet<>(Arrays.asList(CELL2, CELL3)));
		groups = new LinkedHashSet<>(Arrays.asList(group1, group2));
		tokens = new ArrayList<>(Arrays.asList("0", "1"));
		systemUnderTest = new PuzzleType<>(NAME, 2, cells, groups, tokens);
	}
	
	@Test
	public void shouldMakeNameAvailable() {
		assertThat(systemUnderTest.getName(), equalTo(NAME));
	}
	
	@Test
	public void shouldMakeSizeAvailable() {
		assertThat(systemUnderTest.getSize(), equalTo(2));
	}
	
	@Test
	public void shouldMakeCellsAvailable() {
		assertThat(systemUnderTest.getCells(), equalTo(cells));
	}
	
	@Test
	public void shouldMakeGroupsAvailable() {
		assertThat(systemUnderTest.getGroups(), equalTo(groups));
	}
	
	@Test
	public void shouldMakeTokensAvailable() {
		assertThat(systemUnderTest.getValueTokens(), equalTo(tokens));
	}
	
	@Test(expected=NullPointerException.class)
	public void shouldFailToConstructWithoutName() {
		new PuzzleType<>(null, 2, cells, groups, tokens);
	}
	
	@Test(expected=NullPointerException.class)
	public void shouldFailToConstructWithoutCells() {
		new PuzzleType<>(NAME, 2, null, groups, tokens);
	}
	
	@Test(expected=NullPointerException.class)
	public void shouldFailToConstructWithNullCell() {
		new PuzzleType<>(NAME, 2, singleton(null), groups, tokens);
	}
	
	@Test(expected=NullPointerException.class)
	public void shouldFailToConstructWithoutGroups() {
		new PuzzleType<>(NAME, 2, cells, null, tokens);
	}
	
	@Test(expected=NullPointerException.class)
	public void shouldFailToConstructWithNullGroup() {
		new PuzzleType<>(NAME, 2, cells, singleton(null), tokens);
	}
	
	@Test(expected=NullPointerException.class)
	public void shouldFailToConstructWithoutTokens() {
		new PuzzleType<>(NAME, 2, cells, groups, null);
	}
	
	@Test(expected=NullPointerException.class)
	public void shouldFailToConstructWithNullToken() {
		new PuzzleType<>(NAME, 2, cells, groups, singletonList(null));
	}
	
	@Test
	public void shouldIgnoreChangeInOriginalCellsCollection() {
		cells.clear();
		assertThat(systemUnderTest.getCells(), hasSize(3));
	}
	
	@Test(expected=UnsupportedOperationException.class)
	public void shouldFailToModifyReturnedCellsCollection() {
		systemUnderTest.getCells().clear();
	}
	
	@Test
	public void shouldIgnoreChangeInOriginalGroupsCollection() {
		groups.clear();
		assertThat(systemUnderTest.getGroups(), hasSize(2));
	}
	
	@Test(expected=UnsupportedOperationException.class)
	public void shouldFailToModifyReturnedGroupsCollection() {
		systemUnderTest.getGroups().clear();
	}
	
	@Test
	public void shouldIgnoreChangeInOriginalTokensCollection() {
		tokens.clear();
		assertThat(systemUnderTest.getValueTokens(), hasSize(2));
	}
	
	@Test(expected=UnsupportedOperationException.class)
	public void shouldFailToModifyReturnedTokensCollection() {
		systemUnderTest.getValueTokens().clear();
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void shouldFailToConstructWithWrongSizeGroup() {
		GroupType<Coordinate2D> badGroup = new GroupType<>(new LinkedHashSet<>(Arrays.asList(CELL1, CELL2, CELL3)));
		new PuzzleType<>(NAME, 2, cells, singleton(badGroup), tokens);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void shouldFailToConstructWithWrongTokenCount() {
		List<String> badTokens = Arrays.asList("1", "3", "5");
		new PuzzleType<>(NAME, 2, cells, groups, badTokens);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void shouldFailToConstructWithWrongCellInGroup() {
		GroupType<Coordinate2D> badGroup = new GroupType<>(new LinkedHashSet<>(Arrays.asList(CELL1, CELL4)));
		new PuzzleType<>(NAME, 2, cells, singleton(badGroup), tokens);
	}
	
	@Test
	public void shouldRepresentAsString() {
		assertThat(systemUnderTest.toString(), equalTo(NAME));
	}
	
	@Test
	public void shouldNotEqualNull() {
		assertThat(systemUnderTest.equals(null), equalTo(false));
	}
	
	@Test
	public void shouldNotEqualOtherClass() {
		assertThat(systemUnderTest.equals(new Object()), equalTo(false));
	}
	
	@Test
	public void shouldEqualEquivalentObject() {
		PuzzleType<Coordinate2D> other = new PuzzleType<>(NAME, 2, cells, groups, tokens);
		assertThat(systemUnderTest.equals(other), equalTo(true));
		assertThat(systemUnderTest.hashCode(), equalTo(other.hashCode()));
	}
	
	public Object[][] parametersForShouldNotEqualDifferentType() {
		Set<Coordinate2D> sameCells = new LinkedHashSet<>(Arrays.asList(CELL1, CELL2, CELL3));
		Set<GroupType<Coordinate2D>> sameGroups = new LinkedHashSet<>(Arrays.asList(
				new GroupType<>(new LinkedHashSet<>(Arrays.asList(CELL1, CELL2))),
				new GroupType<>(new LinkedHashSet<>(Arrays.asList(CELL2, CELL3)))));
		List<String> sameTokens = Arrays.asList("0", "1");
		
		Set<Coordinate2D> moreCells = new LinkedHashSet<>(Arrays.asList(CELL1, CELL2, CELL3, CELL4));
		List<String> otherTokens = Arrays.asList("0", "2");
		List<String> moreTokens = Arrays.asList("0", "1", "2");
		
		return new Object[][] {
			{ new PuzzleType<>("", 2, sameCells, sameGroups, sameTokens) },
			{ new PuzzleType<>(NAME, 3, sameCells, emptySet(), moreTokens) },
			{ new PuzzleType<>(NAME, 2, moreCells, sameGroups, sameTokens) },
			{ new PuzzleType<>(NAME, 2, sameCells, emptySet(), sameTokens) },
			{ new PuzzleType<>(NAME, 2, sameCells, sameGroups, otherTokens) }
		};
	}
	
	@Test
	@Parameters
	public void shouldNotEqualDifferentType(PuzzleType<?> other) {
		assertThat(systemUnderTest.equals(other), equalTo(false));
	}
}
