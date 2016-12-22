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
		systemUnderTest = new Cell<>(COORD, OptionalInt.of(5), 9);
	}
	
	@Test(expected=NullPointerException.class)
	public void shouldFailToConstructWithoutCoordinate() {
		new Cell<Coordinate2D>(null, OptionalInt.of(5), 9);
	}
	
	@Test(expected=NullPointerException.class)
	public void shouldFailToConstructWithNullValue() {
		new Cell<>(COORD, null, 9);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void shouldFailToConstructWithNegativePuzzleSize() {
		new Cell<>(COORD, OptionalInt.of(5), -1);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void shouldFailToConstructWithZeroPuzzleSize() {
		new Cell<>(COORD, OptionalInt.of(5), 0);
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
	public void shouldFailToSetNegativeValue() {
		try {
			systemUnderTest.setValue(OptionalInt.of(-1));
			fail();
		} catch (IllegalArgumentException expected) {
			// Expected
		}
		assertThat(systemUnderTest.hasValue(), equalTo(true));
		assertThat(systemUnderTest.getValue(), equalTo(OptionalInt.of(5)));
	}
	
	@Test
	public void shouldFailToSetTooHighValue() {
		try {
			systemUnderTest.setValue(OptionalInt.of(9));
			fail();
		} catch (IllegalArgumentException expected) {
			// Expected
		}
		assertThat(systemUnderTest.hasValue(), equalTo(true));
		assertThat(systemUnderTest.getValue(), equalTo(OptionalInt.of(5)));
	}
	
	@Test
	public void shouldSetNewValueLowBorder() {
		systemUnderTest.setValue(OptionalInt.of(0));
		assertThat(systemUnderTest.hasValue(), equalTo(true));
		assertThat(systemUnderTest.getValue(), equalTo(OptionalInt.of(0)));
	}
	
	@Test
	public void shouldSetNewValueHighBorder() {
		systemUnderTest.setValue(OptionalInt.of(8));
		assertThat(systemUnderTest.hasValue(), equalTo(true));
		assertThat(systemUnderTest.getValue(), equalTo(OptionalInt.of(8)));
	}
	
	@Test
	public void shouldSetNewEmptyValue() {
		systemUnderTest.setValue(OptionalInt.empty());
		assertThat(systemUnderTest.hasValue(), equalTo(false));
		assertThat(systemUnderTest.getValue(), equalTo(OptionalInt.empty()));
	}
}
