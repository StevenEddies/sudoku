/* Copyright Steven Eddies, 2016-17. See the LICENCE file in the project root. */

package uk.me.eddies.apps.sudoku.model.puzzle;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.Arrays;
import java.util.LinkedHashSet;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import uk.me.eddies.apps.sudoku.model.space.Coordinate2D;
import uk.me.eddies.apps.sudoku.model.type.GroupType;

public class GroupTest {

	private static final Coordinate2D COORD1 = new Coordinate2D(0, 0);
	private static final Coordinate2D COORD2 = new Coordinate2D(0, 1);
	
	@Mock private Cell<Coordinate2D> cell1;
	@Mock private Cell<Coordinate2D> cell2;
	@Mock private CellLocator<Coordinate2D> locator;
	
	private GroupType<Coordinate2D> type;
	
	private Group<Coordinate2D> systemUnderTest;
	
	@Before
	public void setUp() {
		initMocks(this);
		type = new GroupType<>(new LinkedHashSet<>(Arrays.asList(COORD1, COORD2)));
		
		when(locator.getCell(COORD1)).thenReturn(cell1);
		when(locator.getCell(COORD2)).thenReturn(cell2);
		
		systemUnderTest = new Group<>(type, locator);
	}
	
	@Test
	public void shouldProvideAccessToType() {
		assertThat(systemUnderTest.getType(), equalTo(type));
	}
	
	@Test
	public void shouldProvideAccessToCells() {
		assertThat(systemUnderTest.getCells(), containsInAnyOrder(
				Arrays.asList(equalTo(cell1), equalTo(cell2))));
	}
	
	@Test(expected=UnsupportedOperationException.class)
	public void shouldFailToModifyCells() {
		systemUnderTest.getCells().clear();
	}
	
	@Test(expected=NullPointerException.class)
	public void shouldFailToConstructWithoutType() {
		new Group<>(null, locator);
	}
	
	@Test(expected=NullPointerException.class)
	public void shouldFailToConstructWithoutCellLocator() {
		new Group<>(type, null);
	}
}
