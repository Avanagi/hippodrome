package org.example;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.concurrent.TimeUnit;

@ExtendWith(MockitoExtension.class)
class MainTest {

    @Test
    @Disabled
    @Timeout(value = 22, unit = TimeUnit.SECONDS)
    void mainTest() {
        try {
            Main.main(new String[]{});
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
