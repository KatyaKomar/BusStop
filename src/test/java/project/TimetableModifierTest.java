package project;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TimetableModifierTest {
    private TimetableModifier timetableModifier;

    @Before
    public void initTest() {
        timetableModifier = new TimetableModifier();

    }

    @Test(timeout = 1000)
    public void run() {
        timetableModifier.run("src\\test\\resources\\input.txt");
    }

    @After
    public void afterTest() {
        timetableModifier = null;
    }
}