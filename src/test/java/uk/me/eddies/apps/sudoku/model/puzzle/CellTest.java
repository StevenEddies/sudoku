/* Copyright Steven Eddies, 2016. See the LICENCE file in the project root. */

package uk.me.eddies.apps.sudoku.model.puzzle;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.util.OptionalInt;

import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;

import uk.me.eddies.apps.sudoku.model.space.Coordinate2D;

public class CellTest {

	private static final Coordinate2D COORD = new Coordinate2D(1, 0);
	
	private Cell<Coordinate2D> systemUnderTest;
	
	@Before
	public void setUp() {
		systemUnderTest = new Cell<>(COORD, false, OptionalInt.of(5));
	}
	
	@Test(expected=NullPointerException.class)
	public void shouldFailToConstructWithoutCoordinate() {
		new Cell<Coordinate2D>(null, false, OptionalInt.of(5));
	}
	
	@Test(expected=NullPointerException.class)
	public void shouldFailToConstructWithNullValue() {
		new Cell<>(COORD, false, null);
	}
	
	@Test
	public void shouldConstructWithEmptyValue() {
		systemUnderTest = new Cell<>(COORD, false, OptionalInt.empty());
		assertThat(systemUnderTest.hasValue(), equalTo(false));
		assertThat(systemUnderTest.getValue(), equalTo(OptionalInt.empty()));
		assertThat(systemUnderTest.isGiven(), equalTo(false));
	}
	
	@Test
	public void shouldConstructAsGiven() {
		systemUnderTest = new Cell<>(COORD, true, OptionalInt.of(5));
		assertThat(systemUnderTest.hasValue(), equalTo(true));
		assertThat(systemUnderTest.getValue(), equalTo(OptionalInt.of(5)));
		assertThat(systemUnderTest.isGiven(), equalTo(true));
	}
	
	@Test(expected=IllegalStateException.class)
	public void shouldFailToConstructAsGivenWithEmptyValue() {
		new Cell<>(COORD, true, OptionalInt.empty());
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
	public void shouldMakeGivenFlagAvailable() {
		assertThat(systemUnderTest.isGiven(), equalTo(false));
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
	
	@Test
	public void shouldFailToSetNewValueWhenGiven() {
		systemUnderTest.setGiven(true);
		Assume.assumeThat(systemUnderTest.isGiven(), equalTo(true));
		
		try {
			systemUnderTest.setValue(OptionalInt.of(0));
			fail();
		} catch (IllegalStateException expected) {
			// Expected
		}
		assertThat(systemUnderTest.hasValue(), equalTo(true));
		assertThat(systemUnderTest.getValue(), equalTo(OptionalInt.of(5)));
	}
	
	@Test
	public void shouldSetToGiven() {
		systemUnderTest.setGiven(true);
		assertThat(systemUnderTest.isGiven(), equalTo(true));
	}
	
	@Test
	public void shouldFailToSetToGivenWhenValueIsEmpty() {
		systemUnderTest.setValue(OptionalInt.empty());
		Assume.assumeThat(systemUnderTest.hasValue(), equalTo(false));
		
		try {
			systemUnderTest.setGiven(true);
			fail();
		} catch (IllegalStateException expected) {
			// Expected
		}
		assertThat(systemUnderTest.isGiven(), equalTo(false));
	}
}
