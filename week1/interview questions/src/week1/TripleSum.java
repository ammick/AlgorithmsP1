/* *****************************************************************************
 *  Name: Mikhail Troshchenko
 *  Date: 09/26/2018
 *  Description: trimple sum
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stopwatch;

/*
 3-SUM in quadratic time. Design an algorithm for the 3-SUM problem that takes time proportional to
 n^2n
2
  in the worst case. You may assume that you can sort the nn integers in time proportional to n^2n
2
  or better.
 */
public class TripleSum {

    public static int CountCubeSolution(int[] listOfInts) {
        int count = 0;
        int arrLength = listOfInts.length - 1;
        for (int i = 0; i < arrLength; i++) {
            for (int j = i + 1; j < arrLength; j++) {
                for (int k = j + 1; k < arrLength; k++) {
                    if (listOfInts[i] + listOfInts[j] + listOfInts[k] == 0) {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    public static int CountInSortedArray(int[] listOfIntsSorted) {
        int count = 0;
        int arrLength = listOfIntsSorted.length;
        for (int i = 0; i < arrLength; i++) {
            for (int j = i + 1; j < arrLength; j++) {
                if (binarySearch(listOfIntsSorted, -(listOfIntsSorted[i] + listOfIntsSorted[j]))) {
                    count++;
                }
            }
        }
        return count;
    }


    public static boolean binarySearch(int[] listOfInts, int key) {
        int low = 0, high = listOfInts.length;
        int middle;
        while (low <= high) {
            middle = low + (high - low) / 2;
            if (key < listOfInts[middle]) {
                high = middle - 1;
            }
            else if (key > listOfInts[middle]) {
                low = middle + 1;
            }
            else {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        In reader = new In(args[0]);
        int[] listOfInts = reader.readAllInts();
        Stopwatch stopwatch = new Stopwatch();
        System.out.println(CountInSortedArray(listOfInts));
        System.out.println(stopwatch.elapsedTime());
    }
}
