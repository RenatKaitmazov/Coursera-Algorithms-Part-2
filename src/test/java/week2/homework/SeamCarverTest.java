package week2.homework;

import edu.princeton.cs.algs4.Picture;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.*;

/**
 * @author Renat Kaitmazov
 */

@RunWith(JUnit4.class)
public class SeamCarverTest {

    private final String[] images = {
            "src/test/java/week2/homework/1x1.png", // 0
            "src/test/java/week2/homework/1x8.png", // 1
            "src/test/java/week2/homework/3x4.png", // 2
            "src/test/java/week2/homework/3x7.png", // 3
            "src/test/java/week2/homework/4x6.png", // 4
            "src/test/java/week2/homework/5x6.png", // 5
            "src/test/java/week2/homework/6x5.png", // 6
            "src/test/java/week2/homework/7x3.png", // 7
            "src/test/java/week2/homework/7x10.png", // 8
            "src/test/java/week2/homework/8x1.png", // 9
            "src/test/java/week2/homework/10x10.png", // 10
            "src/test/java/week2/homework/10x12.png", // 11
            "src/test/java/week2/homework/12x10.png" // 12
    };

    @Test
    public void img1x1Test() {
        final SeamCarver carver = newCarver(0);
        assertArrayEquals(new int[1], carver.findVerticalSeam());
        assertArrayEquals(new int[1], carver.findHorizontalSeam());
    }

    @Test
    public void img1x8Test() {
        final SeamCarver carver = newCarver(1);
        assertArrayEquals(new int[8], carver.findVerticalSeam());
        assertArrayEquals(new int[1], carver.findHorizontalSeam());
    }

    @Test
    public void img3x4Test() {
        final SeamCarver carver = newCarver(2);
        assertArrayEquals(new int[]{0, 1, 1, 0}, carver.findVerticalSeam());
        assertArrayEquals(new int[]{1, 2, 1}, carver.findHorizontalSeam());
    }

    @Test
    public void img3x7Test() {
        final SeamCarver carver = newCarver(3);
        assertArrayEquals(new int[]{0, 1, 1, 1, 1, 1, 0}, carver.findVerticalSeam());
        assertArrayEquals(new int[]{1, 2, 1}, carver.findHorizontalSeam());
    }

    @Test
    public void img4x6Test() {
        final SeamCarver carver = newCarver(4);
        assertArrayEquals(new int[]{1, 2, 1, 1, 2, 1}, carver.findVerticalSeam());
        assertArrayEquals(new int[]{1, 2, 1, 0}, carver.findHorizontalSeam());
    }

    @Test
    public void img5x6Test() {
        final SeamCarver carver = newCarver(5);
        assertArrayEquals(new int[]{1, 2, 2, 3, 2, 1}, carver.findVerticalSeam());
        assertArrayEquals(new int[]{2, 3, 2, 3, 2}, carver.findHorizontalSeam());
    }

    @Test
    public void img6x5Test() {
        final SeamCarver carver = newCarver(6);
        assertArrayEquals(new int[]{3, 4, 3, 2, 1}, carver.findVerticalSeam());
        assertArrayEquals(new int[]{1, 2, 1, 2, 1, 0}, carver.findHorizontalSeam());
    }

    @Test
    public void img7x3Test() {
        final SeamCarver carver = newCarver(7);
        assertArrayEquals(new int[]{2, 3, 2}, carver.findVerticalSeam());
        assertArrayEquals(new int[]{0, 1, 1, 1, 1, 1, 0}, carver.findHorizontalSeam());
    }

    @Test
    public void img7x10Test() {
        final SeamCarver carver = newCarver(8);
        assertArrayEquals(new int[]{2, 3, 4, 3, 4, 3, 3, 2, 2, 1}, carver.findVerticalSeam());
        assertArrayEquals(new int[]{6, 7, 7, 7, 8, 8, 7}, carver.findHorizontalSeam());
    }

    @Test
    public void img8x1Test() {
        final SeamCarver carver = newCarver(9);
        assertArrayEquals(new int[1], carver.findVerticalSeam());
        assertArrayEquals(new int[8], carver.findHorizontalSeam());
    }

    @Test
    public void img10x10Test() {
        final SeamCarver carver = newCarver(10);
        assertArrayEquals(new int[]{6, 7, 7, 7, 7, 7, 8, 8, 7, 6}, carver.findVerticalSeam());
        assertArrayEquals(new int[]{0, 1, 2, 3, 3, 3, 3, 2, 1, 0}, carver.findHorizontalSeam());
    }

    @Test
    public void img10x12Test() {
        final SeamCarver carver = newCarver(11);
        assertArrayEquals(new int[]{5, 6, 7, 8, 7, 7, 6, 7, 6, 5, 6, 5}, carver.findVerticalSeam());
        assertArrayEquals(new int[]{8, 9, 10, 10, 10, 9, 10, 10, 9, 8}, carver.findHorizontalSeam());
    }

    @Test
    public void img12x10Test() {
        final SeamCarver carver = newCarver(12);
        assertArrayEquals(new int[]{6, 7, 7, 6, 6, 7, 7, 7, 8, 7}, carver.findVerticalSeam());
        assertArrayEquals(new int[]{7, 8, 7, 8, 7, 6, 5, 6, 6, 5, 4, 3}, carver.findHorizontalSeam());
    }

    private SeamCarver newCarver(int imageIndex) {
        final String picturePath = images[imageIndex];
        final Picture picture = new Picture(picturePath);
        return new SeamCarver(picture);
    }
}