package com.example;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
        @ValueSource(doubles = {-1.7})
        @DisplayName("check thrown exception and exception.message when speed is negative")
        void speedIsNegative_HorseConstructor(double speed) {
            IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () ->
                    Mockito.spy(new Horse("Plotva", speed,25)));
            assertEquals("Speed cannot be negative.", thrown.getMessage());
        }

        @ParameterizedTest(name = "{index} - {0} cannot be distance")
        @ValueSource(doubles = {-8.5})
        @DisplayName("check thrown exception and exception.message when distance is negative")
        void distanceIsNegative_HorseConstructor(double distance) {
            IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () ->
                    Mockito.spy(new Horse("Plotva", 0,distance)));
            assertEquals("Distance cannot be negative.", thrown.getMessage());
        }
    }

    @Nested
    @DisplayName("Horse methods tests")
    class HorseMethodsTest {

        @Test
        @DisplayName("check if getName() returns the exact value of the name field")
        void getName() {
            assertEquals("Plotva", spyHorse.getName());
        }

        @Test
        @DisplayName("check if getSpeed() returns the exact value of the speed field")
        void getSpeed() {
            assertEquals(5, spyHorse.getSpeed());
        }

        @Test
        @DisplayName("check if getDistance() returns the exact value of the distance field")
        void getDistance() {
            Horse testHorse = new Horse("name", 7, 450);
            assertEquals(450, testHorse.getDistance());
            assertEquals(0, spyHorse.getDistance());
        }

        @ParameterizedTest
        @CsvSource({
                "0.2, 0.9"
        })
        @DisplayName("check if move() invokes inner method getRandomDouble()")
        void move(double x, double y) {//                                               не доделан
            spyHorse.move();
            verify(spyHorse).getRandomDouble(x, y);

            when(Horse.getRandomDouble(x, y)).thenAnswer(i -> (Math.random() * (y - x)) + x);

//            assertEquals(distance, spyHorse.move());
        }

        @Test
        void getRandomDouble() {
        }
    }
}