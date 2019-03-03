/* Copyright Steven Eddies, 2019. See the LICENCE file in the project root. */

package uk.me.eddies.apps.sudoku.model.type.standard;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import uk.me.eddies.apps.sudoku.model.space.Coordinate2D;
import uk.me.eddies.apps.sudoku.model.type.GroupType;
import uk.me.eddies.apps.sudoku.model.type.PuzzleType;
import uk.me.eddies.apps.sudoku.model.type.Tokens;

/**
 * Standard puzzle type for normal 9x9 Sudoku grids.
 */
public class StandardNinePuzzle {

	public static final PuzzleType<Coordinate2D> TYPE = build();
	
	private StandardNinePuzzle() { }
	
	private static final PuzzleType<Coordinate2D> build() {
		return new PuzzleType<>("Standard 9x9 grid", 9, buildCells(), buildGroups(), buildTokens());
	}
	
	private static Tokens buildTokens() {
		return new Tokens(Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9"));
	}
	
	private static Set<GroupType<Coordinate2D>> buildGroups() {
		return Stream.concat(buildRows(), Stream.concat(buildCols(), buildBoxes()))
				.collect(Collectors.toSet());
	}
	
	private static Stream<GroupType<Coordinate2D>> buildRows() {
		return IntStream.range(0, 9)
				.mapToObj(StandardNinePuzzle::buildRow);
	}
	
	private static GroupType<Coordinate2D> buildRow(int rowNum) {
		return new GroupType<>(IntStream.range(0, 9)
				.mapToObj(i -> new Coordinate2D(i, rowNum))
				.collect(Collectors.toSet()));
	}
	
	private static Stream<GroupType<Coordinate2D>> buildCols() {
		return IntStream.range(0, 9)
				.mapToObj(StandardNinePuzzle::buildCol);
	}
	
	private static GroupType<Coordinate2D> buildCol(int colNum) {
		return new GroupType<>(IntStream.range(0, 9)
				.mapToObj(i -> new Coordinate2D(colNum, i))
				.collect(Collectors.toSet()));
	}
	
	private static Stream<GroupType<Coordinate2D>> buildBoxes() {
		return IntStream.range(0, 9)
				.mapToObj(i -> buildBox(i % 3, i / 3));
	}
	
	private static GroupType<Coordinate2D> buildBox(int startX, int startY) {
		return new GroupType<>(IntStream.range(0, 9)
				.mapToObj(i -> new Coordinate2D((i % 3) + startX, (i / 3) + startY))
				.collect(Collectors.toSet()));
	}
	
	private static Set<Coordinate2D> buildCells() {
		return IntStream.range(0, 81)
				.mapToObj(i -> new Coordinate2D(i % 9, i / 9))
				.collect(Collectors.toSet());
	}
}
