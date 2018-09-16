package week1.homework;

/**
 * @author Renat Kaitmazov
 */

final class Utils {

    /*--------------------------------------------------------*/
    /* Constructors                                           */
    /*--------------------------------------------------------*/

    private Utils() {
    }

    /*--------------------------------------------------------*/
    /* API                                                    */
    /*--------------------------------------------------------*/

    static <T> void checkNotNull(T item) {
        if (item == null) {
            throw new IllegalArgumentException("Parameter is null");
        }
    }

}
