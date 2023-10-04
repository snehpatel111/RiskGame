package com.riskgame.utility;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import com.riskgame.model.GameMap;


public class MapValidatorTest {

GameMap d_gameMap;
GameMap d_invalidGameMap;
String d_gameMapName;
MapValidator d_mapValidator;

@Before
public void before() {
    this.d_gameMap = new GameMap("ameroki.map");
    this.d_invalidGameMap = new GameMap("invalidMapOne.map");
    this.d_gameMapName = d_gameMap.getMapName();
    this.d_mapValidator=new MapValidator();
}

/**
 *  to test the valid case of  map name
 */
@Test
public void testValidMapName() {
    
    assertTrue(this.d_mapValidator.isValidMapName(d_gameMapName));
    
}

/**
 *  to test the Invalid case of map name
 */
@Test
public void testInvalidMapName() {
    this.d_gameMapName ="11223hello.map";
    assertFalse(this.d_mapValidator.isValidMapName(this.d_gameMapName));
    
}

// /**
//  * to test the valid case of the game map
//  */
// @Test
// public void testMapValidity()
// {
//     boolean l_validMap = this.d_mapValidator.isValidMap(this.d_gameMap);
//     assertTrue(l_validMap);

// }

/**
 * to test the Invalid case of the game map
 */
@Test
public void testMapInValidity()
{
    boolean l_invalidMap =this.d_mapValidator.isValidMap(this.d_invalidGameMap);
    assertFalse(l_invalidMap);

}




}
