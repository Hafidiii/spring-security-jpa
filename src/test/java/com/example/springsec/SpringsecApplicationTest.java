package com.example.springsec;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringsecApplicationTest {

    @Test
    public void testNotNull() {
        assertFalse("Its not Equal", 1==2);
    }
}
