package org.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class HippodromeTest {
    private static final ArrayList<Horse> horses = new ArrayList<>();
    private static final String HORSES_CANNOT_NE_NULL = "Horses cannot be null.";
    private static final String HORSES_CANNOT_NE_EMPTY = "Horses cannot be empty.";

    List<Horse> setHorses(int size) {
        horses.clear();
        for (int i = 0; i < size; i++) {
            horses.add(Mockito.spy(new Horse("Name - " + i, i, i)));
        }
        return horses;
    }

    @Test
    void constructorNullTest() {
        assertThrows(IllegalArgumentException.class, () -> new Hippodrome(null));
        try {
            new Hippodrome(null);
        } catch (Exception exception) {
            assertEquals(HORSES_CANNOT_NE_NULL, exception.getMessage());
        }
    }

    @Test
    void constructorEmptyTest() {
        assertThrows(IllegalArgumentException.class, () -> new Hippodrome(new ArrayList<>()));
        try {
            new Hippodrome(new ArrayList<>());
        } catch (Exception exception) {
            assertEquals(HORSES_CANNOT_NE_EMPTY, exception.getMessage());
        }
    }

    @ParameterizedTest
    @ValueSource(ints = {30})
    void getHorsesTest(int size) {
        Hippodrome hippodrome = new Hippodrome(setHorses(size));
        assertEquals(horses, hippodrome.getHorses());
    }

    @ParameterizedTest
    @ValueSource(ints = {50})
    void moveTest(int size) {
        Hippodrome hippodrome = new Hippodrome(setHorses(size));
        hippodrome.move();
        hippodrome.getHorses().forEach((horse) -> {
            Mockito.verify(horse, Mockito.atLeastOnce()).move();
        });
    }

    @Test
    void getWinnerTest() {
        Hippodrome hippodrome = new Hippodrome(setHorses(3));
        var winner = hippodrome.getWinner();
        assertEquals(hippodrome.getHorses().get(2), winner);
    }

}
