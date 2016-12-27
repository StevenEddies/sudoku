/* Copyright Steven Eddies, 2016. See the LICENCE file in the project root. */

package uk.me.eddies.apps.sudoku.model.puzzle;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.Optional;

import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;

import uk.me.eddies.apps.sudoku.model.space.Coordinate2D;
import uk.me.eddies.apps.sudoku.model.type.Tokens;

public class CellTest {

	private static final String TOKEN_0 = "A";
	private static final String TOKEN_1 = "B";
	private static final String TOKEN_2 = "C";
	private static final Coordinate2D COORD = new Coordinate2D(1, 0);
	
	private Tokens tokens;
	
	private Cell<Coordinate2D> systemUnderTest;
	
	@Before
	public void setUp() {
		tokens = new Tokens(Arrays.asList(TOKEN_0, TOKEN_1, TOKEN_2));
		systemUnderTest = new Cell<>(COORD, tokens, false, Optional.of(2));
	}
	
	@Test(expected=NullPointerException.class)
	public void shouldFailToConstructWithoutCoordinate() {
		new Cell<Coordinate2D>(null, tokens, false, Optional.of(2));
	}
	
	@Test(expected=NullPointerException.class)
	public void shouldFailToConstructWithoutTokens() {
		new Cell<Coordinate2D>(COORD, null, false, Optional.of(2));
	}
	
	@Test(expected=NullPointerException.class)
	public void shouldFailToConstructWithNullValue() {
		new Cell<>(COORD, tokens, false, null);
	}
	
	@Test
	public void shouldConstructWithEmptyValue() {
		systemUnderTest = new Cell<>(COORD, tokens, false, Optional.empty());
		assertThat(systemUnderTest.hasValue(), equalTo(false));
		assertThat(systemUnderTest.getValue(), equalTo(Optional.empty()));
		assertThat(systemUnderTest.isGiven(), equalTo(false));
	}
	
	@Test
	public void shouldConstructAsGiven() {
		systemUnderTest = new Cell<>(COORD, tokens, true, Optional.of(2));
		assertThat(systemUnderTest.hasValue(), equalTo(true));
		assertThat(systemUnderTest.getValue(), equalTo(Optional.of(2)));
		assertThat(systemUnderTest.isGiven(), equalTo(true));
	}
	
	@Test(expected=IllegalStateException.class)
	public void shouldFailToConstructAsGivenWithEmptyValue() {
		new Cell<>(COORD, tokens, true, Optional.empty());
	}
	
	@Test
	public void shouldMakeCoordinateAvailable() {
		assertThat(systemUnderTest.getCoordinate(), equalTo(COORD));
	}
	
	@Test
	public void shouldMakeValueAvailable() {
		assertThat(systemUnderTest.hasValue(), equalTo(true));
		assertThat(systemUnderTest.getValue(), equalTo(Optional.of(2)));
	}
	
	@Test
	public void shouldMakeValueAvailableByToken() {
		assertThat(systemUnderTest.getValueByToken(), equalTo(Optional.of(TOKEN_2)));
	}
	
	@Test
	public void shouldMakeEmptyValueAvailableByToken() {
		systemUnderTest.setValue(Optional.empty());
		Assume.assumeThat(systemUnderTest.hasValue(), equalTo(false));
		
		assertThat(systemUnderTest.getValueByToken(), equalTo(Optional.empty()));
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
		assertThat(systemUnderTest.getValue(), equalTo(Optional.of(2)));
	}
	
	@Test
	public void shouldSetNewValue() {
		systemUnderTest.setValue(Optional.of(0));
		assertThat(systemUnderTest.hasValue(), equalTo(true));
		assertThat(systemUnderTest.getValue(), equalTo(Optional.of(0)));
	}
	
	@Test
	public void shouldSetNewValueUsingToken() {
		systemUnderTest.setValueByToken(Optional.of(TOKEN_0));
		assertThat(systemUnderTest.getValue(), equalTo(Optional.of(0)));
	}
	
	@Test
	public void shouldFailToSetValueToNullToken() {
		try {
			systemUnderTest.setValueByToken(null);
			fail();
		} catch (NullPointerException expected) {
			// Expected
		}
		assertThat(systemUnderTest.hasValue(), equalTo(true));
		assertThat(systemUnderTest.getValue(), equalTo(Optional.of(2)));
	}
	
	@Test
	public void shouldSetNewEmptyValue() {
		systemUnderTest.setValue(Optional.empty());
		assertThat(systemUnderTest.hasValue(), equalTo(false));
		assertThat(systemUnderTest.getValue(), equalTo(Optional.empty()));
	}
	
	@Test
	public void shouldSetNewEmptyValueUsingToken() {
		systemUnderTest.setValueByToken(Optional.empty());
		assertThat(systemUnderTest.getValue(), equalTo(Optional.empty()));
	}
	
	@Test
	public void shouldFailToSetNewValueWhenGiven() {
		systemUnderTest.setGiven(true);
		Assume.assumeThat(systemUnderTest.isGiven(), equalTo(true));
		
		try {
			systemUnderTest.setValue(Optional.of(0));
			fail();
		} catch (IllegalStateException expected) {
			// Expected
		}
		assertThat(systemUnderTest.hasValue(), equalTo(true));
		assertThat(systemUnderTest.getValue(), equalTo(Optional.of(2)));
	}
	
	@Test
	public void shouldSetToGiven() {
		systemUnderTest.setGiven(true);
		assertThat(systemUnderTest.isGiven(), equalTo(true));
	}
	
	@Test
	public void shouldFailToSetToGivenWhenValueIsEmpty() {
		systemUnderTest.setValue(Optional.empty());
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
