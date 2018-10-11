/* *****************************************************************************
 *  Name: Mikhail Troshchenko
 *  Date: 09/27/2018
 *  Description: Bitonic array search
 **************************************************************************** */
/*
Search in a bitonic array. An array is bitonic if it is comprised of an increasing sequence of
integers followed immediately by a decreasing sequence of integers. Write a program that, given a
bitonic array of nn distinct integer values, determines whether a given integer is in the array.

Standard version: Use \sim 3 \lg n∼3lgn compares in the worst case.
Signing bonus: Use \sim 2 \lg n∼2lgn compares in the worst case (and prove that no algorithm can
guarantee to perform fewer than \sim 2 \lg n∼2lgn compares in the worst case).

 */
public class SearchBitonicArray {

    static Integer indexAscStart, indexAscEnd, indexDescStart, indexDescEnd, result;

    public static Integer FindElement(int[] arrayOfIntsBitonic, int find) {
        indexAscStart = null;
        indexAscEnd = null;
        indexDescStart = null;
        indexDescEnd = null;
        result = null;
        int fence = FindFence(arrayOfIntsBitonic, find);

        if (result != null) {
            return result;
        }

        if (indexAscStart == null) indexAscStart = 0;
        if (indexAscEnd == null) indexAscEnd = fence;
        if (indexDescStart == null) indexDescStart = fence + 1;
        if (indexDescEnd == null) indexDescEnd = arrayOfIntsBitonic.length - 1;


        result = binarySearchAscending(arrayOfIntsBitonic, indexAscStart, indexAscEnd,
                                       find);

        if (result == null && fence < arrayOfIntsBitonic.length - 1) {
            result = binarySearchDescending(arrayOfIntsBitonic, indexDescStart, indexDescEnd, find);
        }
        return result;
    }

    public static int FindFence(int[] arrayOfIntsBitonic, int find) {
        if (arrayOfIntsBitonic.length == 1) {
            return 0;
        }
        int lastElementIndex = arrayOfIntsBitonic.length - 1;
        int low = 0, high = lastElementIndex;
        int middle;
        while (low <= high) {
            middle = low + (high - low) / 2;

            if (middle == 0) {
                if (arrayOfIntsBitonic[0] > arrayOfIntsBitonic[1]) {
                    return 0;
                }
                else {
                    return 1;
                }
            }

            int midVal = arrayOfIntsBitonic[middle];
            if (midVal > arrayOfIntsBitonic[middle - 1]) {
                low = middle + 1;

                if (midVal > find) {
                    indexAscEnd = middle - 1 < 0 ? 0 : middle - 1;
                }
                else if (midVal < find) {
                    indexAscStart = middle + 1 > lastElementIndex ? lastElementIndex : middle + 1;
                }
                else {
                    result = middle;
                    return -1;
                }
            }
            else {
                high = middle - 1;
                if (midVal > find) {
                    indexDescStart = middle + 1 > lastElementIndex ? lastElementIndex :
                                     middle + 1;
                }
                else if (midVal < find) {
                    indexDescEnd = middle - 1 < 0 ? 0 : middle - 1;
                }
                else {
                    result = middle;
                    return -1;
                }
            }
        }
        return high;
    }

    public static Integer binarySearchAscending(int[] listOfInts, int low, int high, int key) {
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
                return middle;
            }
        }
        return null;
    }

    public static Integer binarySearchDescending(int[] listOfInts, int low, int high, int key) {
        int middle;
        while (low <= high) {
            middle = low + (high - low) / 2;
            if (key > listOfInts[middle]) {
                high = middle - 1;
            }
            else if (key < listOfInts[middle]) {
                low = middle + 1;
            }
            else {
                return middle;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        System.out.println("started");
        System.out.println(FindElement(new int[] { 1, 2, 3, 4, 5 }, 5));
        System.out.println(FindElement(new int[] { 0 }, 0));
        System.out.println(FindElement(new int[] { 1, 2, 15, 4, 2 }, 4));
        System.out.println(FindElement(new int[] { 5, 4, 3, 2, 1 }, 1));
    }
}

// 0 1 2
// 1 5 2

// 1 0 2 1
// 2 2 2 2
// 3 2 1

// 0 1 2 3 4 5 6 7 8 9
// 1 5 6 7 8 9 5 4 3 2
// s l h m
// 1 0 9 4
// 2 5 9 7
// 3 5 6 5
// 4 6 6 6
// 5 6 5


// 0 1 2 3 4 5
// 9 8 7 6 5 4
// s l h m
// 1 0 5 2
// 2 0 1 0
// 3 0 -1

// 0 1 2 3 4 5
// 0 1 2 3 4 5
// s l h m
// 1 0 5 2
// 2 3 5 4
// 3 5 5 5
// 4 6 5
