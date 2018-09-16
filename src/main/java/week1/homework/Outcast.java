package week1.homework;

/**
 * @author Renat Kaitmazov
 */

public final class Outcast {

    /*--------------------------------------------------------*/
    /* Fields                                                 */
    /*--------------------------------------------------------*/

    private final WordNet wordNet;

    /*--------------------------------------------------------*/
    /* Constructors                                           */
    /*--------------------------------------------------------*/

    public Outcast(WordNet wordNet) {
        Utils.checkNotNull(wordNet);
        this.wordNet = wordNet;
    }

    /*--------------------------------------------------------*/
    /* API                                                    */
    /*--------------------------------------------------------*/

    public String outcast(String[] nouns) {
        Utils.checkNotNull(nouns);
        int maxDistanceSum = Integer.MIN_VALUE;
        int outcastIndex = 0;
        final int size = nouns.length;
        for (int i = 0; i < size; ++i) {
            final String nounA = nouns[i];
            int currentDistanceSum = 0;
            for (int j = 0; j < size; ++j) {
                if (i == j) continue;
                final String nounB = nouns[j];
                currentDistanceSum += wordNet.distance(nounA, nounB);
            }
            if (currentDistanceSum > maxDistanceSum) {
                maxDistanceSum = currentDistanceSum;
                outcastIndex = i;
            }
        }
        return nouns[outcastIndex];
    }

    public static void main(String[] args) {
        if (args.length != 2) {
            return;
        }
        final String synsets = args[0];
        final String hypernyms = args[1];
        final WordNet wordNet = new WordNet(synsets, hypernyms);
        final Outcast outcast = new Outcast(wordNet);
        final String[] nouns = {
                "horse", "zebra", "cat", "bear", "table"
        };
        System.out.println(outcast.outcast(nouns));
    }
}
