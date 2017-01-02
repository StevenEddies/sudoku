/* Copyright Steven Eddies, 2017. See the LICENCE file in the project root. */

package uk.me.eddies.apps.sudoku.model.puzzle;

import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.function.Function;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import uk.me.eddies.apps.sudoku.model.type.Coordinate;

public class CellLocatorTest {

	@Mock private Coordinate coord1;
	@Mock private Coordinate coord2;
	@Mock private Cell<Coordinate> cell1;
	@Mock private Cell<Coordinate> cell2;
	@Mock private Function<Coordinate, Cell<Coordinate>> cellFactory;
	
	private CellLocator<Coordinate> systemUnderTest;
	
	@Before
	public void setUp() {
		initMocks(this);
		when(cellFactory.apply(coord1)).thenReturn(cell1);
		when(cellFactory.apply(coord2)).thenReturn(cell2);
		when(cell1.getCoordinate()).thenReturn(coord1);
		when(cell2.getCoordinate()).thenReturn(coord2);
		systemUnderTest = new CellLocator<>(cellFactory);
	}
	
	@Test(expected=NullPointerException.class)
	public void shouldFailToConstructWithoutCellFactory() {
		new CellLocator<>(null);
	}
	
	@Test
	public void shouldMapCoordinatesToCells() {
		assertThat(systemUnderTest.getCell(coord1), sameInstance(cell1));
		assertThat(systemUnderTest.getCell(coord2), sameInstance(cell2));
	}
	
	@Test
	public void shouldStoreCellInstanceForFutureCalls() {
		assertThat(systemUnderTest.getCell(coord1), sameInstance(cell1));
		when(cellFactory.apply(coord1)).thenThrow(new AssertionError("Invalid invocation."));
		assertThat(systemUnderTest.getCell(coord1), sameInstance(cell1));
	}
	
	@Test(expected=RuntimeException.class)
	public void shouldFailIfFactoryFails() {
		when(cellFactory.apply(coord1)).thenThrow(new RuntimeException());
		systemUnderTest.getCell(coord1);
	}
	
	@Test(expected=NullPointerException.class)
	public void shouldFailIfFactoryReturnsNull() {
		when(cellFactory.apply(coord1)).thenReturn(null);
		systemUnderTest.getCell(coord1);
	}
	
	@Test(expected=IllegalStateException.class)
	public void shouldFailIfFactoryReturnsWrongCoordinate() {
		when(cell1.getCoordinate()).thenReturn(coord2);
		systemUnderTest.getCell(coord1);
	}
}
