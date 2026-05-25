/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.loginpage;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Student
 */
public class messageTest {
    
    public messageTest() {
    }

    @Test
public void testCellNumber() {

    Message msg = new Message(1);

    boolean result = msg.checkRecipientCell("+27831234567");

    assertTrue(result);

}

@Test
public void testInvalidCellNumber() {

    Message msg = new Message(1);

    boolean result = msg.checkRecipientCell("0831234567");

    assertFalse(result);

} 
} 