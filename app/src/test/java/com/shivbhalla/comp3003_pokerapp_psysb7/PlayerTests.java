package com.shivbhalla.comp3003_pokerapp_psysb7;

import org.junit.Test;

import static org.junit.Assert.*;

public class PlayerTests {

    @Test
    public void PlayerCreation(){
        Player player = new Player();
        assertEquals(0,player.getChipValue());
        assertEquals(0,player.getRaiseValue());
        assertEquals(0,player.getCard1());
        assertEquals(0,player.getCard2());
        assertFalse(player.isFolded());
        assertEquals("",player.getUsername());
        assertEquals("",player.getEmail());

        player = new Player(500,"shiv","test@gmail.com");
        assertEquals(500,player.getChipValue());
        assertEquals(0,player.getRaiseValue());
        assertEquals(0,player.getCard1());
        assertEquals(0,player.getCard2());
        assertFalse(player.isFolded());
        assertEquals("shiv",player.getUsername());
        assertEquals("test@gmail.com",player.getEmail());
    }

    @Test
    public void PlayerSetters(){
        Player player = new Player();
        assertEquals(0,player.getChipValue());
        assertEquals(0,player.getRaiseValue());
        assertEquals(0,player.getCard1());
        assertEquals(0,player.getCard2());
        assertFalse(player.isFolded());
        assertEquals("",player.getUsername());
        assertEquals("",player.getEmail());

        player.setChipValue(200);
        assertEquals(200,player.getChipValue());
        player.setRaiseValue(250);
        assertEquals(250,player.getRaiseValue());
        player.setCard1(2);
        assertEquals(2,player.getCard1());
        player.setCard2(3);
        assertEquals(3,player.getCard2());
        player.setFolded(true);
        assertTrue(player.isFolded());
        player.setUsername("shivam");
        assertEquals("shivam",player.getUsername());
        player.setEmail("shiv@gmail.com");
        assertEquals("shiv@gmail.com",player.getEmail());
    }
}

