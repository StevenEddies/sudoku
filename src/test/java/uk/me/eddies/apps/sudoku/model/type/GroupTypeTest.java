/* Copyright Steven Eddies, 2016. See the LICENCE file in the project root. */

package uk.me.eddies.apps.sudoku.model.type;

import static java.util.Collections.singleton;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import uk.me.eddies.apps.sudoku.model.space.Coordinate2D;

@RunWith(JUnitParamsRunner.class)
public class GroupTypeTest {
	
	private static final Coordinate2D CELL1 = new Coordinate2D(0, 0);
	private static final Coordinate2D CELL2 = new Coordinate2D(0, 1);

	private Set<Coordinate2D> cells;
	
	private GroupType<Coordinate2D> systemUnderTest;
	
	@Before
	public void setUp() {
		cells = new LinkedHashSet<>(Arrays.asList(CELL1, CELL2));
		systemUnderTest = new GroupType<>(cells);
	}
	
	@Test
	public void shouldMakeCellsAvailable() {
		assertThat(systemUnderTest.getCells(), equalTo(cells));
	}
	
	@Test
	public void shouldMakeCellCountAvailable() {
		assertThat(systemUnderTest.getPuzzleSize(), equalTo(2));
	}
	
	@Test
	public void shouldNotReactToInputCollectionChanges() {
		cells.clear();
		assertThat(systemUnderTest.getPuzzleSize(), equalTo(2));
	}
	
	@Test(expected=UnsupportedOperationException.class)
	public void shouldFailToModifyCellCollection() {
		systemUnderTest.getCells().clear();
	}
	
	@Test(expected=NullPointerException.class)
	public void shouldFailToConstructWithNullCollection() {
		new GroupType<Coordinate2D>(null);
	}
	
	@Test(expected=NullPointerException.class)
	public void shouldFailToConstructWithNullCell() {
		new GroupType<Coordinate2D>(singleton(null));
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
		GroupType<Coordinate2D> other = new GroupType<>(cells);
		assertThat(systemUnderTest.equals(other), equalTo(true));
		assertThat(systemUnderTest.hashCode(), equalTo(other.hashCode()));
	}
	
	public Object[][] parametersForShouldNotEqualDifferentGroupType() {
		return new Object[][] {
			{ new GroupType<Coordinate2D>(Collections.emptySet()) }
		};
	}
	
	@Test
	@Parameters
	public void shouldNotEqualDifferentGroupType(GroupType<?> other) {
		assertThat(systemUnderTest.equals(other), equalTo(false));
	}
}
