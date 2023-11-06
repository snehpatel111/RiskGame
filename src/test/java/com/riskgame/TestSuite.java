package com.riskgame;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.riskgame.controller.*;
import com.riskgame.model.*;
import com.riskgame.utility.MapValidatorTest;
import com.riskgame.utility.UtilTest;

@RunWith(Suite.class)

@Suite.SuiteClasses({
        GameEngineTest.class,
        CardTest.class,
        ContinentTest.class,
        CountryTest.class,
        GameMapTest.class,
        MapHelperTest.class,
        PlayerTest.class,
        StartUpPhaseTest.class,
        MapValidatorTest.class,
        UtilTest.class,
})

/**
 * It runs a collection of test cases.
 */
public class TestSuite {
    // Driver for All Test Cases
}
