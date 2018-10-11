/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

/*
Egg drop. Suppose that you have an nn-story building (with floors 1 through nn) and plenty of eggs.
An egg breaks if it is dropped from floor TT or higher and does not break otherwise. Your goal is to
devise a strategy to determine the value of TT given the following limitations on the number
of eggs and tosses:

Version 0: 1 egg, \le T≤T tosses.
Version 1: \sim 1 \lg n∼1lgn eggs and \sim 1 \lg n∼1lgn tosses.
Version 2: \sim \lg T∼lgT eggs and \sim 2 \lg T∼2lgT tosses.
Version 3: 22 eggs and \sim 2 \sqrt n∼2
n
​	  tosses.
Version 4: 22 eggs and \le c \sqrt T≤c
T
​	  tosses for some fixed constant cc.
 */
public class EggDrop {
    static int floorHi;

    public static Boolean DoesBreak(int n) {
        return n >= floorHi ? true : false;
    }

    public static int V0(int n) {
        for (int i = 1; i <= n; i++) {
            if (DoesBreak(i)) {
                return i;
            }
        }
        return -1;
    }

    public static int V1(int n) {
        return Binary(1, n);
    }

    public static int Binary(int low, int high) {
        int mid = -1;
        while (low <= high) {
            mid = low + (high - low) / 2;
            if (DoesBreak(mid)) {
                high = mid - 1;
            }
            else {
                low = mid + 1;
            }
        }
        return low;
    }

    public static int V2(int n) {
        int offset = 0;
        int step = (int) Math.pow(2, Math.ceil(Math.log(n) / Math.log(2)));
        while (true) {
            if (DoesBreak(offset + step)) {
                if (step == 1) return offset + 1;
                step /= 2;
            }
            else {
                offset += step;
                step = step == 1 ? 1 : step / 2;
            }

        }
    }

    public static int V3(int n) {
        int sqrt = (int) Math.sqrt(n);
        int lowSearch = n - sqrt;
        for (int i = 1; i <= n; i += sqrt) {
            if (DoesBreak(i)) {
                lowSearch = i - sqrt;
                break;
            }
        }
        for (int j = lowSearch; j <= n; j++) {
            if (DoesBreak(j)) {
                return j;
            }
        }
        return -1;
    }

    public static int V4(int n) {
        int i = 1;
        while (!DoesBreak(i * i)) {
            i++;
        }
        i = (i - 1) * (i - 1);
        while (!DoesBreak(i)) {
            i++;
        }
        return i;
    }


    public static int V2hint(int n) {
        for (int i = 1; i < (int) Math.sqrt(n); i++) {
            if (DoesBreak(i * i)) {
                return Binary((i - 1) * (i - 1), i * i);
            }
        }
        return -1;
    }

    public static int V4hint(int n) {
        int i = 1, halfOfEstimateT = i, T;
        while (!DoesBreak(2 * halfOfEstimateT)) {
            halfOfEstimateT += ++i;
        }
        i = 2 * halfOfEstimateT - 2 * i;
        while (!DoesBreak(i)) {
            i++;
        }
        return i;
    }

    public static void main(String[] args) {
        floorHi = 548796;
        int count = 54684595;
        System.out.println(V0(count) + " version 0");
        System.out.println(V1(count) + " version 1");
        System.out.println(V2(count) + " version 2");
        System.out.println(V3(count) + " version 3");
        System.out.println(V4(count) + " version 4");

        System.out.println(V2hint(count) + " version 2 using hint");
        System.out.println(V4hint(count) + " version 4 using hint");
    }

}
