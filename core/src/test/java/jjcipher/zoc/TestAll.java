package jjcipher.zoc;

import org.junit.runner.JUnitCore;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;


@RunWith(Suite.class)
@Suite.SuiteClasses({
    ZetaObjContainerTest.class
})
public class TestAll {
    public static void main(String[] args) {
        JUnitCore.runClasses(TestAll.class);
    }
}
