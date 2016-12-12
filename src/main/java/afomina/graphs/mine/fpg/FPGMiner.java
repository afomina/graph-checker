package afomina.graphs.mine.fpg;

 /* This is an implementation of the FPGrowth algorithm.
 *
 * Copyright (c) 2016 Alexandra Fomina
 *
 * This file is part of the SPMF DATA MINING SOFTWARE
 * (http://www.philippe-fournier-viger.com/spmf).
 *
 * SPMF is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * SPMF is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with
 * SPMF. If not, see <http://www.gnu.org/licenses/>.
 */

import afomina.graphs.data.Graph;
import ca.pfv.spmf.patterns.itemset_array_integers_with_count.Itemsets;
import ca.pfv.spmf.tools.MemoryLogger;

import java.io.*;
import java.util.*;

/**
 * Modification of {@link AlgoFPGrowth} that accepts a {@link List} of {@Graph} instead of a text input
 */
public class FPGMiner extends AlgoFPGrowth {

    /**
     * Method to run the FPGRowth algorithm.
     *
     * @param graphs  the list of graphs to do data mining on
     * @param output  the output file path for saving the result (if null, the result
     *                will be returned by the method instead of being saved).
     * @param minsupp the minimum support threshold.
     * @return the result if no output file path is provided.
     * @throws IOException exception if error reading or writing files
     */
    public Itemsets runAlgorithm(List<Graph> graphs, String output, double minsupp) throws IOException {
        // number of itemsets found
        itemsetCount = 0;

        //initialize tool to record memory usage
        MemoryLogger.getInstance().reset();
        MemoryLogger.getInstance().checkMemory();

        // if the user want to keep the result into memory
        if (output == null) {
            writer = null;
            patterns = new Itemsets("FREQUENT ITEMSETS");
        } else { // if the user want to save the result to a file
            patterns = null;
            writer = new BufferedWriter(new FileWriter(output));
            itemsetOutputBuffer = new int[BUFFERS_SIZE];
        }

        // (1) PREPROCESSING: Initial database scan to determine the frequency of each item
        // The frequency is stored in a map:
        //    key: item   value: support
        final Map<Integer, Integer> mapSupport = scanDatabaseToDetermineFrequencyOfSingleItems(graphs);

        // convert the minimum support as percentage to a
        // relative minimum support
        this.minSupportRelative = (int) Math.ceil(minsupp * transactionCount);

        // (2) Scan the database again to build the initial FP-Tree
        // Before inserting a transaction in the FPTree, we sort the items
        // by descending order of support.  We ignore items that
        // do not have the minimum support.
        FPTree tree = new FPTree();

        for (Graph graph : graphs) {
            List<Integer> transaction = new ArrayList<>();

            List<Integer> invariants = graph.getAllInvariants();
            // for each item in the transaction
            for (Integer item : invariants) {
                // only add items that have the minimum support
                if (mapSupport.get(item) >= minSupportRelative) {
                    transaction.add(item);
                }
            }
            // sort item in the transaction by descending order of support
            Collections.sort(transaction, new Comparator<Integer>() {
                public int compare(Integer item1, Integer item2) {
                    // compare the frequency
                    int compare = mapSupport.get(item2) - mapSupport.get(item1);
                    // if the same frequency, we check the lexical ordering!
                    if (compare == 0) {
                        return (item1 - item2);
                    }
                    // otherwise, just use the frequency
                    return compare;
                }
            });
            // add the sorted transaction to the fptree.
            tree.addTransaction(transaction);
        }

        // We create the header table for the tree using the calculated support of single items
        tree.createHeaderList(mapSupport);

        // (5) We start to mine the FP-Tree by calling the recursive method.
        // Initially, the prefix alpha is empty.
        // if at least an item is frequent
        if (tree.headerList.size() > 0) {
            // initialize the buffer for storing the current itemset
            itemsetBuffer = new int[BUFFERS_SIZE];
            // and another buffer
            fpNodeTempBuffer = new FPNode[BUFFERS_SIZE];
            // recursively generate frequent itemsets using the fp-tree
            // Note: we assume that the initial FP-Tree has more than one path
            // which should generally be the case.
            fpgrowth(tree, itemsetBuffer, 0, transactionCount, mapSupport);
        }

        // close the output file if the result was saved to a file
        if (writer != null) {
            writer.close();
        }

        // check the memory usage
        MemoryLogger.getInstance().checkMemory();

        // return the result (if saved to memory)
        return patterns;
    }

    /**
     * This method scans the input database to calculate the support of single items
     *
     * @param graphs the list of graphs to do data mining on
     * @return a map for storing the support of each item (key: item, value: support)
     * @throws IOException exception if error while writing the file
     */
    private Map<Integer, Integer> scanDatabaseToDetermineFrequencyOfSingleItems(List<Graph> graphs)
            throws IOException {
        // a map for storing the support of each item (key: item, value: support)
        Map<Integer, Integer> mapSupport = new HashMap<Integer, Integer>();
        for (Graph graph : graphs) {
            List<Integer> invariants = graph.getAllInvariants();
            for (Integer item : invariants) {
                // increase the support count of the item
                Integer count = mapSupport.get(item);
                if (count == null) {
                    mapSupport.put(item, 1);
                } else {
                    mapSupport.put(item, ++count);
                }
            }
            // increase the transaction count
            transactionCount++;
        }
        return mapSupport;
    }

}
