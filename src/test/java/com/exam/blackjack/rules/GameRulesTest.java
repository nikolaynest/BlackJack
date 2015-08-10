package com.exam.blackjack.rules;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;

/**
 * Created on 09.08.15.
 */
public class GameRulesTest {

    private GameRules rules;

    @BeforeClass
    public void init() {
        rules = new GameRules();
    }

    @Test
    public void testBlackJackWinCalculation() {
        int money = 5;
        double win = rules.blackJackWinCalculation(money);
        assertEquals(win, 7.5);
    }

    @Test
    public void testBlackJackWinCalculation2() {
        int money = 20;
        double win = rules.blackJackWinCalculation(money);
        assertEquals(win, 30.0);
    }

    @Test
    public void testBlackJackWinCalcNotEquals() {
        int money = 30;
        double win = rules.blackJackWinCalculation(money);
        assertNotEquals(win, 50);
    }
}
