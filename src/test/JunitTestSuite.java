package test;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;


/**
 * @author Farid Omarzadeh
 *
 */
@RunWith(Suite.class)
@Suite.SuiteClasses(
		{
        FileControllerTest.class,
        GameDriverTest.class,
        GraphTest.class,
        PlayerTest.class,
        AgreessiveTest.class,
})

public class JunitTestSuite 
{


}
