package org.example.toyroom.models.toys;

import org.example.toyroom.models.MyColor;
import org.example.toyroom.models.toys.Ball;
import org.example.toyroom.models.Size;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BallTest {

    @Test
    void testConstructorSetsCorrectFields() {
        MyColor color = new MyColor("#FFD700");
        Size size = Size.MEDIUM;
        String expectedMaterial = "Rubber";
        String expectedImagePath = "/images/ball.png";

        Ball ball = new Ball(size, color, expectedMaterial);

        assertEquals("ball", ball.getType());
        assertEquals(size, ball.getSize());
        assertEquals(color, ball.getColor());
        assertEquals(expectedMaterial, ball.getMaterial());
        assertEquals(expectedImagePath, ball.getImagePath());
    }

    @Test
    void testDefaultConstructorAllowsManualSet() {
        Ball ball = new Ball();

        ball.setType("ball");
        ball.setSize(Size.SMALL);
        ball.setColor(new MyColor("#00FF00"));
        ball.setMaterial("Plastic");
        ball.setImagePath("/images/ball.png");

        assertEquals("ball", ball.getType());
        assertEquals(Size.SMALL, ball.getSize());
        assertEquals("#00FF00", ball.getColor().getHexCode());
        assertEquals("Plastic", ball.getMaterial());
        assertEquals("/images/ball.png", ball.getImagePath());
    }

    @Test
    void testInheritanceFromToy() {
        Ball ball = new Ball(Size.LARGE, new MyColor("#0000FF"), "Leather");

        assertTrue(ball instanceof Toy);
    }
}
