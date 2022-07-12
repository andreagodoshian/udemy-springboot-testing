package org.example;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.*;

public class ConditionalTest {

    @Test
    @Disabled("Don't run until JIRA #123 is resolved!")
    void basicDisabledTest() {
        System.out.println("This is a basic test.");
    }

    @Test
    @EnabledOnOs(OS.WINDOWS)
    void testForWindowsOnly() {
        System.out.println("Looks like you're using Windows.");
    }

    @Test
    @EnabledOnOs(OS.MAC)
    void testForMacOnly() {
        System.out.println("Looks like you're using Mac.");
    }

    @Test
    @EnabledOnOs({OS.MAC, OS.WINDOWS})
    void testForMacAndWindows() {
        System.out.println("Looks like you're using Mac or Windows.");
    }

    @Test
    @EnabledOnOs(OS.LINUX)
    void testForLinuxOnly() {
        System.out.println("Looks like you're using Linux.");
    }

    @Test
    @EnabledOnJre(JRE.JAVA_17)
    void testForJava17Only() {
        System.out.println("Looks like you're using Java 17.");
    }

    @Test
    @EnabledOnJre(JRE.JAVA_13)
    void testForJava13Only() {
        System.out.println("Looks like you're using Java 13.");
    }

    @Test
    @EnabledForJreRange(min = JRE.JAVA_13, max = JRE.JAVA_18)
    void testForJavaRange13to18() {
        System.out.println("Looks like you're between Java 13 and Java 18 (inclusive).");
    }

    @Test
    @EnabledForJreRange(min = JRE.JAVA_11)
    void testForJavaRange11min() {
        System.out.println("Looks like you're using Java 11 or higher.");
    }

    @Test
    @EnabledIfEnvironmentVariable(named="LUV2CODE_ENV", matches = "DEV")
    void testForDevEnvOnly() {
        System.out.println("Looks like you're using the Luv2Code Dev Environment.");
    }

    @Test
    @EnabledIfSystemProperty(named="LUV2CODE_SYS_PROP", matches = "CI_CD_DEPLOY")
    void testForSystemPropertyOnly() {
        System.out.println("Looks like you're using the Luv2Code System Properties.");
    }

}