package com.example;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HorseTest {

    @Spy
    Horse spyHorse = new Horse("Plotva", 5);

    @Nested
    @DisplayName("Horse Constructor tests")
    class HorseConstructorTest {

        @ParameterizedTest(name = "{index} - {0} is not a name")
        @NullSource
        @DisplayName("check thrown exception and exception.message when name is null")
        void nullNameException_HorseConstructor(String name) {
            IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () ->
                    Mockito.spy(new Horse(name, 5, 25)));
            assertEquals("Name cannot be null.", thrown.getMessage());
        }

        @ParameterizedTest(name = "{index} - {0} name is empty")
        @ValueSource(strings = {"", "\u0020", "\t"})
        @DisplayName("check thrown exception and exception.message when name is empty")
        void emptyNameException_HorseConstructor(String name) {
            IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () ->
                    Mockito.spy(new Horse(name, 5, 25)));
            assertEquals("Name cannot be blank.", thrown.getMessage());
        }

        @ParameterizedTest(name = "{index} - {0} cannot be speed")
        @ValueSource(doubles = {-1.7, -259305, -0.000005})
        @DisplayName("check thrown exception and exception.message when speed is negative")
        void speedIsNegative_HorseConstructor(double speed) {
            IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
                    () -> Mockito.spy(new Horse("Plotva", speed,25)));
            assertEquals("Speed cannot be negative.", thrown.getMessage());
        }

        @ParameterizedTest(name = "{index} - {0} cannot be distance")
        @ValueSource(doubles = {-1.7, -259305, -0.000005})
        @DisplayName("check thrown exception and exception.message when distance is negative")
        void distanceIsNegative_HorseConstructor(double distance) {
            IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
                    () -> Mockito.spy(new Horse("Plotva", 0, distance)));
            assertEquals("Distance cannot be negative.", thrown.getMessage());
        }
    }

    @Nested
    @DisplayName("Horse methods tests")
    class HorseMethodsTest {

        @Test
        @DisplayName("check if getName() returns the exact value of the name field")
        void getExactName() {
            try {
                Field field = Horse.class.getDeclaredField("name");
                field.setAccessible(true);
                String name = (String) field.get(spyHorse);

                assertEquals("Plotva", name);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        @Test
        @DisplayName("check if getSpeed() returns the exact value of the speed field")
        void getExactSpeed() {
            try {
                Field field = Horse.class.getDeclaredField("speed");
                field.setAccessible(true);
                double speed = (double) field.get(spyHorse);

                assertEquals(5, speed);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        @Test
        @DisplayName("check if getDistance() returns the exact value of the distance field " +
                "or 0 if constructor with 2 parameters")
        void getExactDistance() {
            Horse testHorse = new Horse("name", 7, 450);
            try {
                Field field = Horse.class.getDeclaredField("distance");
                field.setAccessible(true);
                double distance = (double) field.get(testHorse);

                assertEquals(450, distance);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }

            assertEquals(0, spyHorse.getDistance());
        }

        @ParameterizedTest
        @CsvSource({
                "0.2, 0.9"
        })
        @DisplayName("check if move() invokes inner method getRandomDouble() and uses correct algorithm")
        void moveUses_getRandomDouble(double x, double y) {

            try (MockedStatic<Horse> mocked = mockStatic(Horse.class)) {
                spyHorse.move();
                mocked.verify(() -> Horse.getRandomDouble(x, y));

                Horse testHorse = new Horse("plotva", 5, 25);
                double random = 0.7;
                double distance = testHorse.getDistance() + (testHorse.getSpeed() * random);
                mocked.when(() -> Horse.getRandomDouble(x, y)).thenReturn(random);

                testHorse.move();

                assertEquals(distance, testHorse.getDistance());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}