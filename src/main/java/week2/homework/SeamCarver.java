package week2.homework;

import edu.princeton.cs.algs4.Picture;

/**
 * See <a href="http://coursera.cs.princeton.edu/algs4/assignments/seam.html">description</a>
 *
 * @author Renat Kaitmazov
 */

public final class SeamCarver {

    /*--------------------------------------------------------*/
    /* Static variables                                       */
    /*--------------------------------------------------------*/

    /**
     * The width of borders in pixels.
     */
    private static final int BORDER_WIDTH = 1;
    /**
     * If the width or the height of an image is less than this number, it means
     * the is no any pixel whose energy is not 1000.
     */
    private static final int MIN_SIZE = 3;
    /**
     * The default energy of pixel that is located right on the boarder of an image.
     */
    private static final double DEFAULT_ENERGY = 1_000.0;

    /*--------------------------------------------------------*/
    /* Fields                                                 */
    /*--------------------------------------------------------*/

    /**
     * Used when performing transformations.
     */
    private final Point point = new Point();
    private Picture picture;

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

    /*--------------------------------------------------------*/
    /* API                                                    */
    /*--------------------------------------------------------*/

    public int width() {
        return picture.width();
    }

    public int height() {
        return picture.height();
    }

    public double energy(int col, int row) {
        checkPointInRange(col, row);
        final int leftX = col - 1;
        final int rightX = col + 1;
        final int topY = row - 1;
        final int bottomY = row + 1;
        if ((leftX < 0 || topY < 0) || (rightX == width() || bottomY == height())) return DEFAULT_ENERGY;
        final int leftPixelRgb = picture.getRGB(leftX, row);
        final int rightPixelRgb = picture.getRGB(rightX, row);
        final int horizontalDelta = delta(leftPixelRgb, rightPixelRgb);
        final int topPixelRgb = picture.getRGB(col, topY);
        final int bottomPixelRgb = picture.getRGB(col, bottomY);
        final int verticalDelta = delta(topPixelRgb, bottomPixelRgb);
        return Math.sqrt(horizontalDelta + verticalDelta);
    }

    public int[] findHorizontalSeam() {
        final int rows = height();
        final int cols = width();
        final int[] seam = new int[cols];
        if (noPixelsBetweenBorders(rows, cols)) {
            return seam;
        }
        return seam;
    }

    public int[] findVerticalSeam() {
        final int rows = height();
        final int cols = width();
        final int[] seam = new int[rows];
        if (noPixelsBetweenBorders(rows, cols)) {
            return seam;
        }
        // We have two vertical borders as well as two horizontal borders but the energy of pixels on the border
        // is 1000 so we do not include them to be a little bit efficient.
        final int borderCount = 2;
        final int energyRows = rows - borderCount;
        final int energyCols = cols - borderCount;
        final int size = energyRows * energyCols;
        final int[] route = new int[size];
        final double[] energy = newEnergyArray(size, energyCols);
        final int firstRow = 1;
        for (int col = 0; col < energyCols; ++col) {
            energy[col] = energy(col + BORDER_WIDTH, firstRow);
        }
        seam[0] = seam[1] - 1;
        seam[rows - 1] = seam[rows - 2] - 1;
        return seam;
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
            throw new IllegalArgumentException("x: " + x + "; y: " + y);
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

    private boolean noPixelsBetweenBorders(int rows, int columns) {
        return rows < MIN_SIZE || columns < MIN_SIZE;
    }

    private double[] newEnergyArray(int size, int cols) {
        final double[] energy = new double[size];
        for (int i = cols; i < size; ++i) {
            energy[i] = Double.POSITIVE_INFINITY;
        }
        return energy;
    }

    private int pointToIndex(int row, int col, int cols) {
        return row * cols + col;
    }

    private Point indexToPoint(int index, int cols) {
        point.row = index / cols;
        point.col = index % cols;
        return point;
    }

    /*--------------------------------------------------------*/
    /* Nested classes                                         */
    /*--------------------------------------------------------*/

    private static final class Point {
        private int row;
        private int col;
    }
}
