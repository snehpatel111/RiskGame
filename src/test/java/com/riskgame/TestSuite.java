package com.riskgame;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.riskgame.controller.*;
import com.riskgame.model.*;
import com.riskgame.utility.MapValidatorTest;
import com.riskgame.utility.UtilTest;
// import com.riskgame.view.GameLoggerTest;

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
        AdvanceTest.class,
        AirliftTest.class,
        BlockadeTest.class,
        BombTest.class,
        DeployTest.class,
        DiplomacyTest.class,
        GameStateTest.class,
        LogEntryBufferTest.class,
        OrderExecutionPhaseTest.class,
        UtilTest.class,
        CheaterStrategyTest.class,
        ConnectedContinentTest.class,
        ConnectedMapTest.class,
        ConquestMapTest.class,
        DominationMapTest.class,
        LoadMapTest.class,
        SaveGameTest.class,
        ValidateMapTest.class,
        // IssueOrderPhaseTest.class,
        ReinforcementArmiesTest.class,
// GameLoggerTest.class,
})

/**
 * It runs a collection of test cases.
 */
public class TestSuite {
    // Driver for All Test Cases
}
