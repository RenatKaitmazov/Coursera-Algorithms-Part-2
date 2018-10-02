package week2.homework;

import edu.princeton.cs.algs4.Picture;

/**
 * See <a href="http://coursera.cs.princeton.edu/algs4/assignments/seam.html">description</a>
 *
 * @author Renat Kaitmazov
 */

public final class SeamCarver {

    private static final double DEFAULT_ENERGY = 1_000.0;
    private static final int[] EMPTY_ARRAY = new int[0];

    private final Picture picture;

    /*--------------------------------------------------------*/
    /* Constructors                                           */
    /*--------------------------------------------------------*/

    public SeamCarver(Picture picture) {
        checkNotNull(picture);
        this.picture = picture;
    }

    /*--------------------------------------------------------*/
    /* API                                                    */
    /*--------------------------------------------------------*/

    public Picture picture() {
        return picture;
    }

    public int width() {
        return picture.width();
    }

    public int height() {
        return picture.height();
    }

    public double energy(int x, int y) {
        checkPointInRange(x, y);
        final int leftX = x - 1;
        final int rightX = x + 1;
        final int topY = y - 1;
        final int bottomY = y + 1;
        if ((leftX < 0 || topY < 0) || (rightX == width() || bottomY == height())) return DEFAULT_ENERGY;
        final int leftPixelRgb = picture.getRGB(leftX, y);
        final int rightPixelRgb = picture.getRGB(rightX, y);
        final int horizontalDelta = delta(leftPixelRgb, rightPixelRgb);
        final int topPixelRgb = picture.getRGB(x, topY);
        final int bottomPixelRgb = picture.getRGB(x, bottomY);
        final int verticalDelta = delta(topPixelRgb, bottomPixelRgb);
        return Math.sqrt(horizontalDelta + verticalDelta);
    }

    public int[] findHorizontalSeam() {
        // TODO 1) Add implementation
        return EMPTY_ARRAY;
    }

    public int[] findVerticalSeam() {
        // TODO 2) Add implementation
        return EMPTY_ARRAY;
    }

    public void removeHorizontalSeam(int[] seam) {
        // TODO 3) Add implementation
        checkNotNull(seam);
    }

    public void removeVerticalSeam(int[] seam) {
        // TODO 4) Add implementation
        checkNotNull(seam);
    }

    /*--------------------------------------------------------*/
    /* Helper methods                                         */
    /*--------------------------------------------------------*/

    private <T> void checkNotNull(T item) {
        if (item == null) {
            throw new IllegalArgumentException("Item is null");
        }
    }

    private void checkPointInRange(int x, int y) {
        if ((x < 0 || x >= width()) || (y < 0 || y >= height())) {
            throw new IllegalArgumentException();
        }
    }

    private int delta(int lhsRgb, int rhsRgb) {
        final int deltaR = red(rhsRgb) - red(lhsRgb);
        final int deltaG = green(rhsRgb) - green(lhsRgb);
        final int deltaB = blue(rhsRgb) - blue(lhsRgb);
        return (deltaR * deltaR) + (deltaG * deltaG) + (deltaB * deltaB);
    }

    private int red(int rgb) {
        return (rgb >> 16) & 0x000000FF;
    }

    private int green(int rgb) {
        return (rgb >> 8) & 0x000000FF;
    }

    private int blue(int rgb) {
        return rgb & 0x000000FF;
    }
}
