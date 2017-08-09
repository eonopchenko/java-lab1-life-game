package test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ TestCaseRule1.class, TestCaseRule2.class, TestCaseRule3.class, TestCaseRule4.class })
public class AllTests {

}
