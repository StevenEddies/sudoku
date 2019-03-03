/* Copyright Steven Eddies, 2017-2019. See the LICENCE file in the project root. */

package uk.me.eddies.apps.sudoku.model.puzzle;

import static java.util.Collections.emptySet;
import static java.util.Collections.singleton;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Function;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import uk.me.eddies.apps.sudoku.model.type.Coordinate;
import uk.me.eddies.apps.sudoku.model.type.GroupType;
import uk.me.eddies.apps.sudoku.model.type.PuzzleType;
import uk.me.eddies.apps.sudoku.model.type.Tokens;

public class PuzzleTest {
	
	@Mock private CellLocator<Coordinate> locator;
	@Mock private Function<GroupType<Coordinate>, Group<Coordinate>> groupFactory;
	@Mock private Coordinate coord1;
	@Mock private Coordinate coord2;
	@Mock private Cell<Coordinate> cell1;
	@Mock private Cell<Coordinate> cell2;
	@Mock private Group<Coordinate> group;

	private ReadWriteLock lock;
	private GroupType<Coordinate> groupType;
	private PuzzleType<Coordinate> type;

	private Puzzle<Coordinate> systemUnderTest;
	
	@Before
	public void setUp() {
		initMocks(this);

		lock = new ReentrantReadWriteLock(false);
		groupType = new GroupType<>(new LinkedHashSet<>(Arrays.asList(coord1, coord2)));
		type = new PuzzleType<>("Irrelevant", 2,
				new LinkedHashSet<>(Arrays.asList(coord1, coord2)),
				singleton(groupType),
				new Tokens(Arrays.asList("0", "1")));
		when(locator.getCell(coord1)).thenReturn(cell1);
		when(locator.getCell(coord2)).thenReturn(cell2);
		when(groupFactory.apply(groupType)).thenReturn(group);
		when(group.getType()).thenReturn(groupType);
		
		systemUnderTest = new Puzzle<>(lock, type, locator, groupFactory);
	}
	
	@Test(expected=NullPointerException.class)
	public void shouldFailToConstructWithNullType() {
		new Puzzle<>(lock, null, locator, groupFactory);
	}
	
	@Test(expected=NullPointerException.class)
	public void shouldFailToConstructWithNullCellLocator() {
		new Puzzle<>(lock, type, null, groupFactory);
	}
	
	@Test(expected=NullPointerException.class)
	public void shouldFailToConstructWithNullGroupFactory() {
		new Puzzle<>(lock, type, locator, null);
	}
	
	@Test
	public void shouldMakePuzzleTypeAvailable() {
		assertThat(systemUnderTest.getType(), equalTo(type));
	}
	
	@Test
	public void shouldMakeCellAvailableForCoordinate() {
		assertThat(systemUnderTest.getCell(coord1), sameInstance(cell1));
	}
	
	@Test
	public void shouldMakeAllCellsAvailable() {
		assertThat(systemUnderTest.getCells(), containsInAnyOrder(Arrays.asList(
				sameInstance(cell1), sameInstance(cell2))));
	}
	
	@Test(expected=UnsupportedOperationException.class)
	public void shouldFailToModifyCells() {
		systemUnderTest.getCells().clear();
	}
	
	@Test
	public void shouldConstructGroupsUsingFactory() {
		assertThat(systemUnderTest.getGroups(), contains(Arrays.asList(sameInstance(group))));
	}
	
	@Test(expected=UnsupportedOperationException.class)
	public void shouldFailToModifyGroups() {
		systemUnderTest.getGroups().clear();
	}
	
	@Test(expected=RuntimeException.class)
	public void shouldFailToConstructWhenFactoryFails() {
		when(groupFactory.apply(groupType)).thenThrow(new RuntimeException());
		new Puzzle<>(lock, type, locator, groupFactory);
	}
	
	@Test(expected=NullPointerException.class)
	public void shouldFailToConstructWhenFactoryReturnsNull() {
		when(groupFactory.apply(groupType)).thenReturn(null);
		new Puzzle<>(lock, type, locator, groupFactory);
	}
	
	@Test(expected=RuntimeException.class)
	public void shouldFailToConstructWhenFactoryReturnsInvalidGroup() {
		when(group.getType()).thenReturn(new GroupType<>(emptySet()));
		new Puzzle<>(lock, type, locator, groupFactory);
	}
}
