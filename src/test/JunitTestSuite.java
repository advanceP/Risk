package test;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;


@RunWith(Suite.class)
@Suite.SuiteClasses({
        FileControllerTest.class,
        GameDriverTest.class,
        GraphTest.class,
        PlayerTest.class
})

public class JunitTestSuite {


}
