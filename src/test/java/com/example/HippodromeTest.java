package com.example;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class HippodromeTest {

    @Nested
    @DisplayName("Constructor tests")
    class HippodromeConstructorTest {

        @ParameterizedTest(name = "{index} - {0} list is null")
        @NullSource
        @DisplayName("check thrown exception and message if constructor argument is null")
        void nullHorsesConstructor(List nullList) {
            IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () ->
                    Mockito.mock(new Hippodrome(nullList)));
            assertEquals("Horses cannot be null.", thrown.getMessage());
        }

        @ParameterizedTest(name = "{index} - {0} list is empty")
        @EmptySource
        @DisplayName("check thrown exception and message if constructor argument is empty")
        void emptyHorsesConstructor(List list) {
            IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () ->
                    Mockito.mock(new Hippodrome(list)));
            assertEquals("Horses cannot be empty.", thrown.getMessage());
        }
    }

    @Nested
    @DisplayName("Methods tests")
    class HippodromeMethodsTest {

        private List<Horse> initHorses(int number) {
            var horsesList = new ArrayList<Horse>();
            for (int i = 0; i < number; i++) {
                horsesList.add(
                        Mockito.spy(
                                new Horse("horse"+i, i)));
            }
            return horsesList;
        }

        @Test
        @DisplayName("check if constructor argument is equal to getHorses() return")
        void getHorses() {
            var testList = initHorses(30);
            Hippodrome testHippodrome = new Hippodrome(testList);

            assertEquals(testList, testHippodrome.getHorses());
        }

        @Test
        @DisplayName("check if all horses move()")
        void move() {
            var testList = initHorses(50);
            Hippodrome testHippodrome = new Hippodrome(testList);

            testHippodrome.move();

            testList.forEach(horse -> verify(horse).move());
        }

        @Test
        @DisplayName("check if getWinner() returns the Horse with max distance")
        void getWinner() {
            var testList = initHorses(5);
            Hippodrome testHippodrome = new Hippodrome(testList);

            testHippodrome.move();

            var winner = testList.stream()
                    .max(Comparator.comparing(Horse::getDistance))
                    .get();

            assertEquals(winner, testHippodrome.getWinner());
        }
    }


}