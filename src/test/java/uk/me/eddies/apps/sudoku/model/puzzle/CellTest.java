/* Copyright Steven Eddies, 2016. See the LICENCE file in the project root. */

package uk.me.eddies.apps.sudoku.model.puzzle;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.util.OptionalInt;

import org.junit.Before;
import org.junit.Test;

import uk.me.eddies.apps.sudoku.model.space.Coordinate2D;

public class CellTest {

	private static final Coordinate2D COORD = new Coordinate2D(1, 0);
	
	private Cell<Coordinate2D> systemUnderTest;
	
	@Before
	public void setUp() {
		systemUnderTest = new Cell<>(COORD, OptionalInt.of(5));
	}
	
	@Test(expected=NullPointerException.class)
	public void shouldFailToConstructWithoutCoordinate() {
		new Cell<Coordinate2D>(null, OptionalInt.of(5));
	}
	
	@Test(expected=NullPointerException.class)
	public void shouldFailToConstructWithNullValue() {
		new Cell<>(COORD, null);
	}
	
	@Test
	public void shouldMakeCoordinateAvailable() {
		assertThat(systemUnderTest.getCoordinate(), equalTo(COORD));
	}
	
	@Test
	public void shouldMakeValueAvailable() {
		assertThat(systemUnderTest.hasValue(), equalTo(true));
		assertThat(systemUnderTest.getValue(), equalTo(OptionalInt.of(5)));
	}
	
	@Test
	public void shouldFailToSetValueToNull() {
		try {
			systemUnderTest.setValue(null);
			fail();
		} catch (NullPointerException expected) {
			// Expected
		}
		assertThat(systemUnderTest.hasValue(), equalTo(true));
		assertThat(systemUnderTest.getValue(), equalTo(OptionalInt.of(5)));
	}
	
	@Test
	public void shouldSetNewValue() {
		systemUnderTest.setValue(OptionalInt.of(0));
		assertThat(systemUnderTest.hasValue(), equalTo(true));
		assertThat(systemUnderTest.getValue(), equalTo(OptionalInt.of(0)));
	}
	
	@Test
	public void shouldSetNewEmptyValue() {
		systemUnderTest.setValue(OptionalInt.empty());
		assertThat(systemUnderTest.hasValue(), equalTo(false));
		assertThat(systemUnderTest.getValue(), equalTo(OptionalInt.empty()));
	}
}
