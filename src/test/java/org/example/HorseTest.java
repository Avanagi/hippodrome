package org.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class HorseTest {

    private static final String NAME_CANNOT_BE_NULL = "Name cannot be null.";
    private static final String NAME_CANNOT_BE_BLANK = "Name cannot be blank.";
    private static final String SPEED_CANNOT_BE_NEGATIVE = "Speed cannot be negative.";
    private static final String DISTANCE_CANNOT_BE_NEGATIVE = "Distance cannot be negative.";

    @Mock
    Horse horse;


    @Test
    void constructorNullTest() {
        assertThrows(IllegalArgumentException.class, () -> {
            horse = new Horse(null, 0);
        });

        try {
            horse = new Horse(null, 0);
        } catch (Exception exception) {
            assertEquals(NAME_CANNOT_BE_NULL, exception.getMessage());
        }
    }


    @ParameterizedTest
    @ValueSource(strings = {"", "   ", "\t"})
    void constructorBlankTest(String name) {
        assertThrows(IllegalArgumentException.class, () -> {
            horse = new Horse(name, 0);
        });

        try {
            horse = new Horse(name, 0);
        } catch (Exception exception) {
            assertEquals(NAME_CANNOT_BE_BLANK, exception.getMessage());
        }
    }


    @Test
    void constructorNegativeSpeedTest() {
        assertThrows(IllegalArgumentException.class, () -> {
            horse = new Horse("John", -1);
        });

        try {
            horse = new Horse("John", 1);
        } catch (Exception exception) {
            assertEquals(SPEED_CANNOT_BE_NEGATIVE, exception.getMessage());
        }
    }

    @Test
    void constructorNegativeDistanceTest() {
        assertThrows(IllegalArgumentException.class, () -> {
            horse = new Horse("John", 1, -1);
        });

        try {
            horse = new Horse("John", 1, -1);
        } catch (Exception exception) {
            assertEquals(DISTANCE_CANNOT_BE_NEGATIVE, exception.getMessage());
        }
    }


    @Test
    void getNameTest() {
        String nameExpected = "Horse";

        var actual = new Horse(nameExpected, 15, 30).getName();

        assertEquals(nameExpected, actual);
    }

    @Test
    void getSpeedTest() {
        double speedExpected = 15;

        var actual = new Horse("Horse", speedExpected, 30).getSpeed();

        assertEquals(speedExpected, actual);
    }


    @Test
    void getDistanceTest() {
        double distanceExpected = 15;

        var actual = new Horse("Horse", 10, distanceExpected).getDistance();

        assertEquals(distanceExpected, actual);
    }

    @Test
    void getDistanceNullTest() {
        var actual = new Horse("Horse", 10).getDistance();

        assertEquals(0, actual);
    }

    @Test
    void moveTest() {
        try (MockedStatic<Horse> mockedHorse = Mockito.mockStatic(Horse.class)) {
            horse = new Horse("Horse", 10, 5);
            horse.move();
            mockedHorse.verify(() -> Horse.getRandomDouble(0.2, 0.9));
        } catch (Exception exception) {
            throw new AssertionError(exception);
        }
    }

    @ParameterizedTest
    @ValueSource(doubles = {1, 3, 5, 7})
    void getRandomDoubleTest(double number) {
        try (MockedStatic<Horse> mockedHorse = Mockito.mockStatic(Horse.class)) {
            horse = new Horse("Horse", 10, 5);
            double distanceExpected = horse.getDistance() + horse.getSpeed() * number;

            mockedHorse.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn(number);
            horse.move();

            assertEquals(distanceExpected, horse.getDistance());
        } catch (Exception exception) {
            throw new AssertionError(exception);
        }
    }
}
