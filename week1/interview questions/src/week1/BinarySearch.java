/* *****************************************************************************
 *  Name: Mikhail Troshchenko
 *  Date: 09/27/2018
 *  Description: binary search
 **************************************************************************** */

public class BinarySearch {
    public static int binarySearch(int[] listOfInts, int key) {
        int low = 0, high = listOfInts.length - 1;
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
        return -1;
    }

    public static void main(String[] args) {
    }
}
