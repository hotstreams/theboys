package com.theboys;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

public class MainTest {

    @Test
    public void test() {
        Assertions.assertDoesNotThrow(() -> Main.main(new String[]{}));
    }

}
