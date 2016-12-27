/* Copyright Steven Eddies, 2016. See the LICENCE file in the project root. */

package uk.me.eddies.apps.sudoku.model.type;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class TokensTest {

	private static final String TOKEN_0 = "A";
	private static final String TOKEN_1 = "B";
	private static final String TOKEN_2 = "C";
	private static final String TOKEN_X = "D";
	
	private List<String> tokens;
	
	private Tokens systemUnderTest;
	
	@Before
	public void setUp() {
		tokens = new ArrayList<>(Arrays.asList(TOKEN_0, TOKEN_1, TOKEN_2));
		systemUnderTest = new Tokens(tokens);
	}
	
	@Test(expected=IllegalStateException.class)
	public void shouldFailToConstructWithDuplicateTokens() {
		new Tokens(Arrays.asList(TOKEN_0, TOKEN_1, TOKEN_0));
	}
	
	@Test(expected=NullPointerException.class)
	public void shouldFailToConstructWithNullList() {
		new Tokens(null);
	}
	
	@Test(expected=NullPointerException.class)
	public void shouldFailToConstructWithNullToken() {
		new Tokens(Arrays.asList(TOKEN_0, TOKEN_1, null));
	}
	
	@Test
	public void shouldInsulateAgainstChangesToOriginalTokensList() {
		tokens.clear();
		assertThat(systemUnderTest.validateToken(TOKEN_0), equalTo(true));
	}
	
	@Test(expected=NullPointerException.class)
	public void shouldFailToValidateNullToken() {
		systemUnderTest.validateToken(null);
	}
	
	@Test
	public void shouldValidateValidToken() {
		assertThat(systemUnderTest.validateToken(TOKEN_0), equalTo(true));
	}
	
	@Test
	public void shouldValidateInvalidToken() {
		assertThat(systemUnderTest.validateToken(TOKEN_X), equalTo(false));
	}
	
	@Test(expected=NullPointerException.class)
	public void shouldFailToConvertNullTokenToValue() {
		systemUnderTest.toValue(null);
	}
	
	@Test(expected=IllegalStateException.class)
	public void shouldFailToConvertInvalidTokenToValue() {
		systemUnderTest.toValue(TOKEN_X);
	}
	
	@Test
	public void shouldConvertValidTokenToValue() {
		assertThat(systemUnderTest.toValue(TOKEN_0), equalTo(0));
		assertThat(systemUnderTest.toValue(TOKEN_2), equalTo(2));
	}
	
	@Test(expected=IllegalStateException.class)
	public void shouldFailToConvertInvalidLowValueToToken() {
		systemUnderTest.toToken(-1);
	}
	
	@Test(expected=IllegalStateException.class)
	public void shouldFailToConvertInvalidHighValueToToken() {
		systemUnderTest.toToken(3);
	}
	
	@Test
	public void shouldConvertValidLowValueToToken() {
		assertThat(systemUnderTest.toToken(0), equalTo(TOKEN_0));
	}
	
	@Test
	public void shouldConvertValidHighValueToToken() {
		assertThat(systemUnderTest.toToken(2), equalTo(TOKEN_2));
	}
	
	@Test
	public void shouldRequireSpecificCountWhenValid() {
		systemUnderTest.requireCount(3);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void shouldRequireSpecificCountWhenInalid() {
		systemUnderTest.requireCount(4);
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
		Tokens other = new Tokens(tokens);
		assertThat(systemUnderTest.equals(other), equalTo(true));
		assertThat(systemUnderTest.hashCode(), equalTo(other.hashCode()));
	}
	
	@Test
	public void shouldNotEqualOtherTokens() {
		Tokens other = new Tokens(Arrays.asList(TOKEN_2, TOKEN_1, TOKEN_0));
		assertThat(systemUnderTest.equals(other), equalTo(false));
	}
}
