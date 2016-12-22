/* Copyright Steven Eddies, 2016. See the LICENCE file in the project root. */

package uk.me.eddies.apps.sudoku.model.space;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import junitparams.naming.TestCaseName;

@RunWith(JUnitParamsRunner.class)
public class Coordinate2DTest {

	private Coordinate2D systemUnderTest;
	
	@Before
	public void setUp() {
		systemUnderTest = new Coordinate2D(10, 20);
	}
	
	@Test
	public void shouldMakeXCoordinateAvailable() {
		assertThat(systemUnderTest.getX(), equalTo(10));
	}
	
	@Test
	public void shouldMakeYCoordinateAvailable() {
		assertThat(systemUnderTest.getY(), equalTo(20));
	}

	@Test
	public void shouldRepresentAsString() {
		assertThat(systemUnderTest.toString(), equalTo("(10, 20)"));
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
		Coordinate2D other = new Coordinate2D(systemUnderTest.getX(), systemUnderTest.getY());
		assertThat(systemUnderTest.equals(other), equalTo(true));
		assertThat(systemUnderTest.hashCode(), equalTo(other.hashCode()));
	}
	
	public Object[][] parametersForShouldNotEqualDifferentCoordinate2D() {
		return new Object[][] {
			{ new Coordinate2D(10, 21) },
			{ new Coordinate2D(9, 20) },
			{ new Coordinate2D(11, 19) }
		};
	}
	
	@Test
	@Parameters
	@TestCaseName("{0}")
	public void shouldNotEqualDifferentCoordinate2D(Coordinate2D other) {
		assertThat(systemUnderTest.equals(other), equalTo(false));
	}
}
