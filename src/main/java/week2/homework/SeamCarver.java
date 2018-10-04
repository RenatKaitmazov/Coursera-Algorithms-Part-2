package week2.homework;

import edu.princeton.cs.algs4.Picture;

import java.util.Arrays;

/**
 * See <a href="http://coursera.cs.princeton.edu/algs4/assignments/seam.html">description</a>
 *
 * @author Renat Kaitmazov
 */

public final class SeamCarver {

    /*--------------------------------------------------------*/
    /* Static variables                                       */
    /*--------------------------------------------------------*/

    private static final double DEFAULT_ENERGY = 1_000.0;
    private static final int[] EMPTY_ARRAY = new int[0];

    /*--------------------------------------------------------*/
    /* Fields                                                 */
    /*--------------------------------------------------------*/

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

    /*--------------------------------------------------------*/
    /* API                                                    */
    /*--------------------------------------------------------*/

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
        final int cols = width();
        final int rows = height();
        final int[] path = new int[rows];
        final double[] cost = new double[rows];
        for (int i = 0; i < rows; ++i) {
            cost[i] = Double.POSITIVE_INFINITY;
        }
        // Start finding the shortest path from the upper left corner
        cost[0] = energy(0, 0);
        findShortestVerticalPathFromOrigin(path, cost, rows, cols);
        final int[] auxPath = new int[rows];
        final double[] auxCost = new double[rows];
        for (int col = 1; col < cols; ++col) {
            findShortestVerticalPath(path, auxPath, cost, auxCost, col, rows, cols);
        }
        return path;
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

    private void findShortestVerticalPathFromOrigin(int[] path,
                                                    double[] cost,
                                                    final int rows,
                                                    final int cols) {
        int shortestCol = 0;
        for (int row = 1; row < rows; ++row) {
            final int colBound = shortestCol + 2;
            double minEnergy = Double.POSITIVE_INFINITY;
            int minCol = shortestCol - 1;
            for (int col = minCol; col < colBound; ++col) {
                // Out of bounds
                if (col < 0 || col >= cols) continue;
                final double energy = energy(col, row);
                // Find at which column the minimal energy is
                if (energy < minEnergy) {
                    minEnergy = energy;
                    minCol = col;
                    shortestCol = minCol;
                }
            }
            path[row] = minCol;
            cost[row] = cost[row - 1] + minEnergy;
        }
    }

    private void findShortestVerticalPath(int[] oldPath,
                                          int[] path,
                                          double[] oldCost,
                                          double[] cost,
                                          int startCol,
                                          final int rows,
                                          final int cols) {
        path[0] = startCol;
        cost[0] = energy(startCol, 0);
        int shortestCol = startCol;
        for (int row = 1; row < rows; ++row) {
            final int colBound = shortestCol + 2;
            double minEnergy = Double.POSITIVE_INFINITY;
            int minCol = shortestCol - 1;
            for (int col = minCol; col < colBound; ++col) {
                // Out of bounds
                if (col < 0 || col >= cols) continue;
                final double energy = energy(col, row);
                // Find at which column the minimal energy is
                if (energy < minEnergy) {
                    minEnergy = energy;
                    minCol = col;
                    shortestCol = minCol;
                }
            }
            if (oldPath[row] == minCol) {
                // There is an intersection between the old shortest path and the new one,
                // Just update everything up to the intersection point.
                if (oldCost[row] > cost[row - 1] + minEnergy) {
                    for (int i = 0; i < row; ++i) {
                        oldPath[i] = path[i];
                        oldCost[i] = cost[i];
                    }
                    // Update cost.
                    final double delta = oldCost[row] - (oldCost[row - 1] + minEnergy);
                    for (int i = row; i < rows; ++i) {
                        oldCost[i] -= delta;
                    }
                }
                return;
            } else {
                path[row] = minCol;
                cost[row] = cost[row - 1] + minEnergy;
            }
        }
        // At this point we know we found a new unique path and we need to check if the new path
        // is shorter than the old one.
        if (oldCost[rows - 1] > cost[rows - 1]) {
            for (int row = 0; row < rows; ++row) {
                oldPath[row] = path[row];
                oldCost[row] = cost[row];
            }
        }
    }

    public static void main(String[] args) {
        final String path = "/Users/RenatKaitmazov/Desktop/seam/7x10.png";
        final Picture picture = new Picture(path);
        final SeamCarver carver = new SeamCarver(picture);
        System.out.println();
        System.out.println(Arrays.toString(carver.findVerticalSeam()));
    }
}
