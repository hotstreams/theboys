package com.theboys;

import org.junit.jupiter.api.BeforeEach;

public class BaseFirefoxTest extends BaseTest {
    @BeforeEach
    public void setup() {
        startFirefoxDriver();
    }
}
