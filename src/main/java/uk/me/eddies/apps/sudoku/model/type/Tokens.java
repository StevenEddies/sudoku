/* Copyright Steven Eddies, 2016. See the LICENCE file in the project root. */

package uk.me.eddies.apps.sudoku.model.type;

import static java.util.Collections.unmodifiableList;
import static java.util.Collections.unmodifiableMap;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.IntStream;

/**
 * Represents a set of tokens which can be used to refer to the values of cells.
 */
public final class Tokens {
	
	// TODO use

	private final List<String> tokens;
	private final Map<String, Integer> tokenIndices;
	
	public Tokens(List<String> tokens) {
		this.tokens = unmodifiableList(new ArrayList<>(tokens));
		this.tokens.forEach(Objects::requireNonNull);
		this.tokenIndices = unmodifiableMap(
				IntStream.range(0, this.tokens.size()).boxed()
				.collect(toMap(this.tokens::get, i -> i)));
		if (tokenIndices.size() != this.tokens.size()) throw new IllegalStateException("Cannot use duplicate tokens.");
	}
	
	void requireCount(int requiredCount) {
		if (tokens.size() != requiredCount)
			throw new IllegalArgumentException("Invalid token count.");
	}
	
	public boolean validateToken(String token) {
		return tokenIndices.containsKey(requireNonNull(token));
	}
	
	public int toValue(String token) {
		if (!validateToken(token)) throw new IllegalStateException("Invalid token \"" + token + "\".");
		return tokenIndices.get(token);
	}
	
	public String toToken(int value) {
		if ((value < 0) || (value >= tokens.size())) throw new IllegalStateException("Invalid value " + value + ".");
		return tokens.get(value);
	}

	@Override
	public int hashCode() {
		final int PRIME = 43;
		return (PRIME * tokens.hashCode());
	}

	@Override
	public boolean equals(Object obj) {
		if ((obj == null) || (getClass() != obj.getClass())) return false;
		Tokens other = (Tokens) obj;
		return tokens.equals(other.tokens);
	}
}
