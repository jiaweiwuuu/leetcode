package binarysearch;

import javafx.util.Pair;

import java.util.*;

class SummaryRanges {
    List<int[]> summary = new ArrayList<>();

    /**
     * Initialize your data structure here.
     */
    public SummaryRanges() {

    }

    public void addNum(int val) {
        int left = 0;
        int right = summary.size() - 1;
        int position = -1;

        while (left <= right) {
            int mid = (left + right) / 2;
            int[] candidate = summary.get(mid);
            if (candidate[0] <= val && candidate[1] >= val) {
                position = mid;
                break;
            } else if (candidate[0] > val) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        if (position == -1) {
            int leftSubArrayIndex = left - 1;
            int rightSubArrayIndex = left;
            if (leftSubArrayIndex >= 0 && summary.get(leftSubArrayIndex)[1] == val - 1 && rightSubArrayIndex < summary.size() && summary.get(rightSubArrayIndex)[0] == val + 1) {
                summary.set(leftSubArrayIndex, new int[]{summary.get(leftSubArrayIndex)[0], summary.get(rightSubArrayIndex)[1]});
                summary.remove(rightSubArrayIndex);
            } else if (leftSubArrayIndex >= 0 && summary.get(leftSubArrayIndex)[1] == val - 1) {
                summary.set(leftSubArrayIndex, new int[]{summary.get(leftSubArrayIndex)[0], val});
            } else if (rightSubArrayIndex >= 0 && rightSubArrayIndex < summary.size() && summary.get(rightSubArrayIndex)[0] == val + 1) {
                summary.set(rightSubArrayIndex, new int[]{val, summary.get(rightSubArrayIndex)[1]});
            } else {
                summary.add(left, new int[]{val, val});
            }
        }
    }

    public int[][] getIntervals() {
        int[][] result = new int[summary.size()][2];
        for (int i = 0; i < summary.size(); i++) {
            result[i] = summary.get(i);
        }
        return result;
    }
}

public class BinarySearch {
    public static void main(String[] args) {
        SummaryRanges summaryRanges = new SummaryRanges();
        summaryRanges.addNum(1);
        summaryRanges.addNum(3);
        summaryRanges.addNum(7);
        summaryRanges.addNum(2);
        summaryRanges.addNum(6);
        summaryRanges.addNum(9);
        summaryRanges.addNum(4);
        summaryRanges.addNum(10);
        summaryRanges.addNum(5);
        BinarySearch binarySearch = new BinarySearch();
        System.out.print(binarySearch.isPerfectSquare(808201));
    }

    public boolean isPerfectSquare(int num) {
        if(num == 1){
            return true;
        }
        if(num <4){
            return false;
        }
        int maxCan = num/2;
        int left  = 0;
        int right = maxCan;
        while(left <= right){
            int mid = (left + right) /2;
            long mul = mid * mid;
            if(mul == num){
                return true;
            }
            else if(mul < num){
                left = mid + 1;
            }
            else{
                right = mid -1;
            }
        }
        return false;
    }

    public int[] findRightInterval(int[][] intervals) {
        Map<Pair<Integer,Integer>,Integer> map = new HashMap<>();
        for(int i =0; i < intervals.length; i ++){
            map.put(new Pair<>(intervals[i][0],intervals[i][1]),i);
        }
        Arrays.sort(intervals, new Comparator<int[]>() {
            @Override
            public int compare(int[] t1, int[] t2) {
                if(t1[0] == t2[0]){
                    return t1[1]-t2[1];
                }
                else{
                    return t1[0] - t2[0];
                }
            }
        });
        
        return null;
    }

}
