package week1.homework;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.In;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * @author Renat Kaitmazov
 */

public final class WordNet {

    /*--------------------------------------------------------*/
    /* Constants                                              */
    /*--------------------------------------------------------*/

    private static final String COMMA = ",";
    private static final String SPACE = " ";

    /*--------------------------------------------------------*/
    /* Fields                                                 */
    /*--------------------------------------------------------*/

    private final Map<String, List<Integer>> words = new HashMap<>();
    private final List<LinkedList<String>> indices = new ArrayList<>();
    private final SAP sap;

    /*--------------------------------------------------------*/
    /* Constructors                                           */
    /*--------------------------------------------------------*/

    public WordNet(String synsets, String hypernyms) {
        Utils.checkNotNull(synsets);
        Utils.checkNotNull(hypernyms);
        final int totalSynsetsCount = fillSynsetData(synsets);
        final Digraph digraph = new Digraph(totalSynsetsCount);
        final int synsetsWithAncestorsCount = fillHypernymsData(digraph, hypernyms);
        if (totalSynsetsCount - synsetsWithAncestorsCount > 1) {
            throw new IllegalArgumentException("The graph is not rooted");
        }
        checkNoCycles(digraph);
        sap = new SAP(digraph);

    }

    /*--------------------------------------------------------*/
    /* API                                                    */
    /*--------------------------------------------------------*/

    public boolean isNoun(String word) {
        Utils.checkNotNull(word);
        return words.containsKey(word);
    }

    public int distance(String nounA, String nounB) {
        checkValidNoun(nounA);
        checkValidNoun(nounB);
        final Iterable<Integer> nounAIndices = words.get(nounA);
        final Iterable<Integer> nounBIndices = words.get(nounB);
        return sap.length(nounAIndices, nounBIndices);
    }

    public String sap(String nounA, String nounB) {
        checkValidNoun(nounA);
        checkValidNoun(nounB);
        final Iterable<Integer> nounAIndices = words.get(nounA);
        final Iterable<Integer> nounBIndices = words.get(nounB);
        final int commonAncestor = sap.ancestor(nounAIndices, nounBIndices);
        final LinkedList<String> wordList = indices.get(commonAncestor);
        return toSap(wordList);
    }

    public Iterable<String> nouns() {
        return words.keySet();
    }

    /*--------------------------------------------------------*/
    /* Helper methods                                         */
    /*--------------------------------------------------------*/

    private void checkValidNoun(String noun) {
        if (!isNoun(noun)) {
            throw new IllegalArgumentException("Invalid noun");
        }
    }

    private int fillSynsetData(String synsets) {
        final In synsetsInput = new In(synsets);
        int totalSynsetsCount = 0;
        while (synsetsInput.hasNextLine()) {
            final String line = synsetsInput.readLine();
            final StringTokenizer tokens = new StringTokenizer(line, COMMA);
            final String stringId = (String) tokens.nextElement();
            final int id = Integer.parseInt(stringId);
            final StringTokenizer wordTokens = new StringTokenizer(tokens.nextToken(), SPACE);
            final LinkedList<String> wordsList = new LinkedList<>();
            while (wordTokens.hasMoreTokens()) {
                // Fill words
                final String word = wordTokens.nextToken();
                final List<Integer> ids = words.get(word);
                if (ids == null) {
                    final List<Integer> newIds = new ArrayList<>();
                    newIds.add(id);
                    words.put(word, newIds);
                } else {
                    ids.add(id);
                }
                // Fill indices
                wordsList.add(word);
            }
            indices.add(wordsList);
            ++totalSynsetsCount;
        }
        return totalSynsetsCount;
    }

    private int fillHypernymsData(Digraph digraph, String hypernyms) {
        int synsetsWithAncestorsCount = 0;
        final In hypernymsInput = new In(hypernyms);
        while (hypernymsInput.hasNextLine()) {
            final String line = hypernymsInput.readLine();
            final StringTokenizer tokens = new StringTokenizer(line, COMMA);
            final String stringSynsetId = tokens.nextToken();
            final int synsetId = Integer.parseInt(stringSynsetId);
            ++synsetsWithAncestorsCount;
            while (tokens.hasMoreTokens()) {
                final String stringHypernymId = tokens.nextToken();
                final int hypernymId = Integer.parseInt(stringHypernymId);
                digraph.addEdge(synsetId, hypernymId);
            }
        }
        return synsetsWithAncestorsCount;
    }

    private void checkNoCycles(Digraph digraph) {
        if (new DirectedCycle(digraph).hasCycle()) {
            throw new IllegalArgumentException("The graph has cycles");
        }
    }

    private String toSap(LinkedList<String> wordList) {
        final int size = wordList.size();
        final StringBuilder builder = new StringBuilder((size << 1) - 1);
        for (final String word : wordList) {
            builder.append(word).append(SPACE);
        }
        final int endIndex = builder.length();
        final int startIndex = endIndex - 1;
        return builder.replace(startIndex, endIndex, "").toString();
    }
}
