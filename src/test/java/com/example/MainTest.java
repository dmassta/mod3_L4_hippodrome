package com.example;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MainTest {

    @Disabled
    @Test
    @DisplayName("Main method must execute faster than 22 seconds")
    @Timeout(22)
    void main() throws Exception {
        Main.main(null);
    }
}