package com.theboys;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MainTest {

    @Test
    public void test() {
        Assertions.assertDoesNotThrow(() -> Main.main(new String[]{}));
    }

}
