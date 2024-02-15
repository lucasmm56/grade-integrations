package br.edu.imepac.professores.models.services;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Testes de operações matemáticas")
class SimpleMathTest {
    SimpleMath math;

    @BeforeEach
    void beforeEachMethod(){
        math = new SimpleMath();
    }

    @Test
    @DisplayName("Test 6.2 + 2 = 8.2")
    void testSum(){
        //Given
        double firstNumber = 6.2D;
        double secundNumber = 2D;

        //When
        Double actual = math.sum(firstNumber, secundNumber);
        double expected = 8.2D;

        //Then
        assertEquals(expected, actual,() -> firstNumber+" + "+secundNumber+" did not produce "+expected+" !");
        assertNotEquals(9.2, actual);
        assertNotNull(actual);
    }

    @Test
    @DisplayName("Teste Division 6/3 = 2")
    void testDivision(){
        //Given
        double firstNumber = 6D;
        double secundNumber = 3D;
        Double actual = math.division(firstNumber, secundNumber);
        //When
        double expected = 2D;
        //Then
        assertEquals(expected,actual,() -> firstNumber+ " / "+ secundNumber+" dit not produce "+expected);
    }

    @Test
    @DisplayName("Teste  Division by Zero")
    void testDivisionByZero(){
        //Given
        double firstNumber = 3D;
        double secundNumber = 0D;

        String expectedMessage = "Can not divide by Zero!";
        //When & Then
        ArithmeticException actual = assertThrows(ArithmeticException.class, () -> {
            math.division(firstNumber, secundNumber);
        });
        assertEquals(expectedMessage, actual.getMessage());
    }


    @Test
    @DisplayName("Teste Square Root of 81 = 9 ")
    void testRaizQ(){
        //Given
        double raizNumber = 81D;
        //When
        Double actual = math.raizQuad(raizNumber);
        double expected = 9D;
        //Then
        assertEquals(expected, actual, () -> expected+ " did not raiz of "+ raizNumber + " your raiz is "+ actual);
    }

    @Test
    @DisplayName("Test 6.3 + 6.1 / 2 = 6.2")
    void testMedia(){
        //Given
        double firstNumber = 6.3D;
        double secundNumber = 6.1D;
        //When
        Double actual = math.media(firstNumber, secundNumber);
        double expected = 6.2D;
        //Then
        assertEquals(expected, actual,0.001, () -> "("+firstNumber+" + "+ secundNumber + ") / 2 did not produce "+ expected);
    }

    //test[System Under Test]_[Condition or State Change]_[Expected Result]
    @DisplayName("Display name")
    @Disabled("TO DO Find how to edit template in IntelliJ")
    @Test
    void testABCD_When_XYZ_Should(){
        //Given / Arrange
        //When / Act
        //Then / Assert
    }
}
