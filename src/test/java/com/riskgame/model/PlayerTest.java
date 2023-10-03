// package com.riskgame.model;

// import static org.junit.Assert.*;
// import org.junit.Before;
// import org.junit.Test;

// public class PlayerTest {

//     Player player;

//     @Before
//     public void setUp() {
//         player = new Player("Alice");
//     }

//     @Test
//     public void testPlayerName() {
//         assertEquals("Alice", player.getPlayerName());
//     }

//     @Test
//     public void testOwnedCountries() {
//         Country country = new Country();
//         player.getOwnedCountries().put("Canada", country);
//     }

//     @Test
//     public void testOwnedContinents() {
//         assertEquals(0, player.getOwnedContinents().size());
//         player.getOwnedContinents().put("Europe", continent);
//         assertEquals(1, player.getOwnedContinents().size());
//         assertTrue(player.getOwnedContinents().containsKey("Europe"));
//     }

//     @Test
//     public void testOwnedArmies() {
//         assertEquals(0, player.getOwnedArmies());
//         player.setOwnedArmies(10);
//         assertEquals(10, player.getOwnedArmies());
//     }
// }
