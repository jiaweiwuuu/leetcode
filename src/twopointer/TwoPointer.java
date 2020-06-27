package twopointer;

import java.util.*;

public class TwoPointer {
    public static void main(String[] args) {
        Set<List<Integer>> set = new HashSet<>();
        System.out.println(set.size());

        TwoPointer twoPointer = new TwoPointer();
        List<List<Integer>> nums = new ArrayList<>();

        List<Integer> list1 = new ArrayList<>(Arrays.asList(10, 10));
        List<Integer> list2 = new ArrayList<>(Arrays.asList(11, 11));
        List<Integer> list3 = new ArrayList<>(Arrays.asList(5, 18, 22, 30));

        nums.add(list1);
        nums.add(list2);
        //nums.add(list3);

        System.out.println(twoPointer.longestOnes(new int[]{0,0,1,1,0,0,1,1,1,0,1,1,0,0,0,1,1,1,1,1,0,0,0,0,0},3));
        System.out.println(twoPointer.longestOnes1(new int[]{0,0,1,1,0,0,1,1,1,0,1,1,0,0,0,1,1,1,1,1,0,0,0,0,0},3));
    }

    public int findDuplicate(int[] nums) {
        int slow = nums[0];
        int fast = nums[nums[0]];

        if (nums.length == 1) {
            return -1;
        }

        while (slow != fast) {
            slow = nums[slow];
            fast = nums[nums[fast]];

        }

        slow = 0;

        while (slow != fast) {
            slow = nums[slow];
            fast = nums[fast];
        }
        return slow;
    }

    public void reverseString(char[] s) {
        if (s.length <= 1) {
            return;
        }

        int left = 0;
        int right = s.length - 1;

        while (left < right) {
            char temp = s[right];
            s[right] = s[left];
            s[left] = temp;
            left++;
            right--;
        }

    }

    public String reverseVowels(String s) {
        Set<Character> v = new HashSet<>();

        v.add('a');
        v.add('e');
        v.add('i');
        v.add('o');
        v.add('u');
        v.add('A');
        v.add('E');
        v.add('I');
        v.add('O');
        v.add('U');

        char[] array = s.toCharArray();

        int left = 0;
        int right = array.length - 1;

        while (left < right) {
            while (left < right && !v.contains(array[left])) {
                left++;
            }
            while (left < right && !v.contains(array[right])) {
                right--;
            }
            char temp = array[left];
            array[left] = array[right];
            array[right] = temp;
            left++;
            right--;
        }
        return new String(array);
    }

    public int[] intersection(int[] nums1, int[] nums2) {
        Set<Integer> s = new HashSet<>();
        Set<Integer> b = new HashSet<>();
        for (int i : nums1) {
            s.add(i);
        }


        for (int j : nums2) {
            if (s.contains(j)) {
                b.add(j);
            }
        }
        int[] result = new int[b.size()];
        int count = 0;
        for (int r : b) {
            result[count] = r;
            count++;
        }
        return result;
    }


    public int[] intersect(int[] nums1, int[] nums2) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int n1 : nums1) {
            if (map.containsKey(n1)) {
                int count = map.get(n1);
                count++;
                map.put(n1, count);
            } else {
                map.put(n1, 1);
            }
        }

        List<Integer> result = new ArrayList<>();

        for (int n2 : nums2) {
            if (map.containsKey(n2)) {
                int count = map.get(n2);
                if (count == 0) {
                    continue;
                } else {
                    count--;
                    result.add(n2);
                    map.put(n2, count);
                }
            }
        }

        int[] array = new int[result.size()];
        int count = 0;
        for (int n : result) {
            array[count] = n;
            count++;
        }
        return array;
    }

    public int characterReplacement(String s, int k) {
        char[] array = s.toCharArray();
        int right = 0;

        int result = 0;
        while (right < s.length()) {

            while (right + 1 < array.length && array[right] == array[right + 1]) {
                right++;
                if (right == s.length() - 1) {
                    break;
                }
            }
            int curRight = right;
            int left = right;
            char curChr = array[right];
            int modifications = 0;
            while (left >= 0 && modifications <= k) {
                if (array[left] != curChr) {
                    if (modifications == k) {
                        break;
                    }
                    modifications++;
                }
                left--;
            }


            while (right < array.length && modifications <= k) {
                if (array[right] != curChr) {
                    if (modifications == k) {
                        break;
                    }
                    modifications++;
                }
                right++;
            }


            result = Math.max(result, right - left - 1);
            if (right == curRight) {
                right++;
            }
        }
        return result;
    }

    public boolean circularArrayLoop(int[] nums) {

        for (int i = 0; i < nums.length; i++) {
            int last = -1;
            boolean success = true;
            int direction = nums[i];
            Set<Integer> visited = new HashSet<>();

            visited.add(i);
            int j = (i + nums[i]) % nums.length;
            if (j < 0) {
                j += nums.length;
            }
            last = i;
            while (!visited.contains(j)) {
                if (direction * nums[j] < 0) {
                    success = false;
                    break;
                }
                visited.add(j);
                last = j;
                j = (j + nums[j]) % nums.length;

                if (j < 0) {
                    j += nums.length;
                }
            }
            if (success && last != j) {
                return true;
            }
        }
        return false;
    }

    public int findPairs(int[] nums, int k) {
        if (k < 0) {
            return 0;
        }
        int count = 0;
        Set<Integer> set = new HashSet<>();
        Set<Integer> s = new HashSet<>();
        for (int i : nums) {
            if (!set.add(i)) {
                s.add(i);
            }
        }
        if (k == 0) {
            return s.size();
        }

        for (int i : set) {
            if (set.contains(i + k)) {
                count++;
            }
        }
        return count;

    }

    public boolean checkInclusion(String s1, String s2) {

        if (s1.length() > s2.length()) {
            return false;
        }
        char[] c1 = s1.toCharArray();
        char[] c2 = s2.toCharArray();

        Map<Character, Integer> map = new HashMap<>();
        Set<Character> negativeSet = new HashSet<>();
        for (char c : c1) {
            if (map.containsKey(c)) {
                int count = map.get(c);
                count++;
                map.put(c, count);
            } else {
                map.put(c, 1);
            }
        }

        int left = 0;
        int right = left + s1.length() - 1;
        int num = 0;
        for (int i = 0; i < c1.length; i++) {
            char c = c2[i];
            if (map.containsKey(c)) {
                num++;
                int count = map.get(c);
                count--;
                map.put(c, count);
                if (count < 0) {
                    negativeSet.add(c);
                }
            }
        }

        while (right < s2.length()) {
            if (num == s1.length() && negativeSet.size() == 0) {
                return true;
            }
            right++;
            if (right == s2.length()) {
                break;
            }
            if (map.containsKey(c2[right])) {
                int count = map.get(c2[right]);
                count--;
                if (count < 0) {
                    negativeSet.add(c2[right]);
                }
                map.put(c2[right], count);
                num++;
            }
            if (map.containsKey(c2[left])) {
                int count = map.get(c2[left]);
                count++;
                if (count == 0) {
                    negativeSet.remove(c2[left]);
                }
                map.put(c2[left], count);
                num--;
            }
            left++;
        }
        return false;
    }

    public int[] smallestRange(List<List<Integer>> nums) {
        List<Integer> combine = new ArrayList<>();
        Map<Integer, Set<Integer>> invertList = new HashMap<>();

        for (List<Integer> subList : nums) {
            for (int i : subList) {
                combine.add(i);
                invertList.put(i, new HashSet<>());
            }
        }
        for (int i = 0; i < nums.size(); i++) {
            for (int n : nums.get(i)) {
                Set<Integer> list = invertList.get(n);
                list.add(i);
                invertList.put(n, list);
            }
        }

        Collections.sort(combine);

        int left = 0;
        int right = 0;


        int[] result = new int[]{0, Integer.MAX_VALUE};

        Map<Integer, Integer> map = new HashMap<>();

        for (int i = 0; i < nums.size(); i++) {
            map.put(i, 0);
        }


        for (int i : invertList.get(combine.get(0))) {
            map.put(i, 1);
        }


        while (right < combine.size()) {
            while (!map.containsValue(0)) {
                if (combine.get(right) - combine.get(left) < result[1] - result[0]) {
                    result[0] = combine.get(left);
                    result[1] = combine.get(right);
                }
                int num = combine.get(left);
                for (int i : invertList.get(num)) {
                    int count = map.get(i);
                    count--;
                    map.put(i, count);
                }

                left++;
            }

            right++;
            if (right == combine.size()) {
                break;
            }
            for (int i : invertList.get(combine.get(right))) {
                int count = map.get(i);
                count++;
                map.put(i, count);
            }
        }
        return result;
    }

    public int numSubarrayProductLessThanK(int[] nums, int k) {
        int left = 0;
        int right = 0;
        long sum = (long) nums[0];
        int result = 0;


        while (right < nums.length) {
            if (sum < k) {
                result += (right - left + 1);
            } else {
                sum = sum / nums[left];
                if (left < right) {
                    left++;

                } else {
                    left++;
                    right++;
                    if (right == nums.length) {
                        break;
                    }
                    sum *= nums[right];
                }
                continue;

            }
            right++;
            if (right == nums.length) {
                break;
            }
            sum *= nums[right];
        }
        return result;
    }


    public List<Integer> partitionLabels(String S) {
        if (S.length() == 0) {
            return new ArrayList<>();
        }
        char[] array = S.toCharArray();
        List<Integer> result = new ArrayList<>();
        Map<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < S.length(); i++) {
            map.put(array[i], i);
        }

        int i = 0;
        int rightBound = map.get(array[i]);


        while (i < rightBound) {
            rightBound = Math.max(rightBound, map.get(array[i]));
            i++;
        }
        result.add(rightBound + 1);
        result.addAll(partitionLabels(S.substring(rightBound + 1)));
        return result;


    }

    public boolean backspaceCompare(String S, String T) {
        Stack<Character> s1 = new Stack<>();
        Stack<Character> s2 = new Stack<>();
        char[] c1s = S.toCharArray();
        char[] c2s = T.toCharArray();

        for (char c : c1s) {
            if (c == '#') {
                if (s1.isEmpty()) {
                    continue;
                }
                s1.pop();
            } else {
                s1.push(c);
            }
        }

        for (char c : c2s) {
            if (c == '#') {
                if (s2.isEmpty()) {
                    continue;
                }
                s2.pop();
            } else {
                s2.push(c);
            }
        }

        if (s1.size() != s2.size()) {
            return false;
        }

        while (!s1.isEmpty()) {
            if (s1.pop() != s2.pop()) {
                return false;
            }
        }
        return true;
    }

    public String pushDominoes(String dominoes) {
        int left = 0;
        int right = 0;

        char[] array = dominoes.toCharArray();

        while (right < array.length) {
            if (array[right] == 'L') {
                if (array[left] != 'R') {
                    for (int i = left; i <= right; i++) {
                        array[i] = 'L';
                    }
                } else {
                    int newLeft = left;
                    int newRight = right;
                    while (newLeft < newRight) {
                        array[newLeft] = 'R';
                        array[newRight] = 'L';
                        newLeft++;
                        newRight--;
                    }
                }
                right++;
                left = right;
            } else if (array[right] == 'R') {
                if (array[left] == 'R') {
                    for (int i = left; i <= right; i++) {
                        array[i] = 'R';
                    }
                }
                left = right;
                right++;

            } else {
                right++;
            }
        }
        if (left < array.length && array[left] == 'R') {
            for (int i = left; i < array.length; i++) {
                array[i] = 'R';
            }
        }
        return new String(array);
    }

    public int longestMountain(int[] A) {
        int left = 0;
        int right = 0;

        int max = 0;


        while (left < A.length) {
            int start = left;
            while (left < A.length - 1 && A[left] < A[left + 1]) {
                left++;
            }
            if (left == A.length - 1) {
                break;
            }
            if (left == start) {
                left++;
                continue;
            }

            right = left;
            while (right < A.length - 1 && A[right] > A[right + 1]) {
                right++;
            }
            if (right != left) {
                max = Math.max(max, right - start + 1);
            }


            left = right;

        }
        return max;
    }

    public int numRescueBoats(int[] people, int limit) {
        Arrays.sort(people);
        int count = 0;
        int left = 0;
        int right = people.length - 1;

        while (left <= right) {
            if (left == right) {
                count++;
                break;
            }
            if (people[left] + people[right] <= limit) {
                count++;
                left++;
                right--;
            } else {
                right--;
                count++;
            }
        }
        return count;
    }

    public int totalFruit(int[] tree) {
        Map<Integer, Integer> map = new HashMap<>();
        int max = 0;
        int left = 0;
        int right = 0;


        while (right < tree.length) {
            if (map.size() < 2) {
                if (map.containsKey(tree[right])) {
                    int count = map.get(tree[right]);
                    count++;
                    map.put(tree[right], count);
                } else {
                    map.put(tree[right], 1);
                }
                right++;
            } else {
                if (map.containsKey(tree[right])) {
                    int count = map.get(tree[right]);
                    count++;
                    map.put(tree[right], count);
                    right++;
                } else {
                    int curMax = 0;
                    for (int count : map.values()) {
                        curMax += count;
                    }
                    max = Math.max(max, curMax);

                    while (map.size() == 2) {
                        int count = map.get(tree[left]);
                        count--;
                        if (count == 0) {
                            map.remove(tree[left]);
                        } else {
                            map.put(tree[left], count);
                        }
                        left++;
                    }
                }
            }
        }
        int curMax = 0;
        for (int count : map.values()) {
            curMax += count;
        }
        max = Math.max(max, curMax);
        return max;
    }

    public boolean isLongPressedName(String name, String typed) {
        char[] n = name.toCharArray();
        char[] t = typed.toCharArray();

        int p1 = 0;
        int p2 = 0;

        int count = 0;
        char prev = n[0];
        while (p1 < n.length) {
            if (n[p1] == prev) {
                p1++;
                count++;
            } else {


                int tcount = 0;

                while (p2 < t.length && t[p2] == prev) {
                    tcount++;
                    p2++;
                }

                if (count > tcount) {
                    return false;
                }
                prev = n[p1];
                p1++;
                count = 1;


            }
        }

        int tcount = 0;
        while (p2 < t.length && t[p2] == prev) {
            tcount++;
            p2++;
        }
        if (p2 < t.length) {
            return false;
        }
        if (count > tcount) {
            return false;
        }
        return true;
    }


    public int numSubarraysWithSum(int[] A, int S) {
        int start = 0;
        int right = 0;
        int count = 0;
        int result = 0;
        while (right < A.length) {
            if (A[right] > S) {
                right++;
                start = right;
                count = 0;
                continue;
            }
            count += A[right];
            if (count < S) {
                right++;
            } else if (count == S) {
                int left = start;
                int subCount = S;
                while (left <= right && subCount == S) {
                    result++;
                    subCount -= A[left];
                    left++;
                }
                right++;
            } else {
                while (count > S) {
                    count -= A[start];
                    start++;
                }
                count -= A[right];
            }
        }
        return result;
    }

    public int[] sortedSquares(int[] A) {
        if (A.length == 0) {
            return A;
        }
        if (A.length == 1) {
            A[0] = A[0] * A[0];
            return A;
        }

        int right = 1;
        while (right < A.length && Math.abs(A[right]) <= Math.abs(A[right - 1])) {
            right++;
        }

        int left = right - 1;
        int count = 0;
        int[] result = new int[A.length];
        while (left >= 0 && right < A.length) {
            if (Math.abs(A[left]) < Math.abs(A[right])) {
                result[count] = (int) Math.pow(A[left], 2);
                left--;
                count++;
            } else {
                result[count] = (int) Math.pow(A[right], 2);
                right++;
                count++;
            }
        }

        if (left >= 0) {
            for (int i = left; i >= 0; i--) {
                result[count] = (int) Math.pow(A[i], 2);
                count++;
            }
        } else if (right < A.length) {
            for (int i = right; i < A.length; i++) {
                result[count] = (int) Math.pow(A[i], 2);
                count++;
            }
        }

        return result;


    }

    public int[][] intervalIntersection(int[][] A, int[][] B) {
        if(A.length == 0 || B.length == 0){
            return new int[0][0];
        }
        int i = 0;
        int j = 0;

        int p1 = A[0][0];
        int p2 = B[0][0];

        List<int[]> result = new ArrayList<>();



        while (i < A.length && j < B.length) {


            int count = 0;
//            while (p1 <= A[i][1] && p2 <= B[j][1]) {
//                p1++;
//                p2++;
//                count ++;
//            }
            int intervalMax = Math.min(A[i][1],B[j][1]);
            if(intervalMax >= p1 && intervalMax >= p2){
                p1 = Math.max(p1, p2);
                p2 = Math.max(p1, p2);

                int intervalMin = p1;
                result.add(new int[]{intervalMin, intervalMax});
                p1 = intervalMax+1;
                p2 = intervalMax+1;
            }
            else if(intervalMax < p1){
                p2 = intervalMax+1;
            }
            else if(intervalMax < p2){
                p1 = intervalMax+1;
            }

            if (p1 > A[i][1]) {
                i++;
                if(i < A.length){
                    p1 = A[i][0];
                }


            }

            if (p2 > B[j][1]) {
                j++;
                if(j < B.length){
                    p2 = B[j][0];
                }

            }
        }
        int[][] trueResult = new int[result.size()][2];
        int index = 0;
        for(int k =0; k < result.size(); k++){
            trueResult[index] = result.get(k);
            index++;
        }
        return trueResult;
    }


    public int subarraysWithKDistinct(int[] A, int K) {
        int start = 0;
        int right = 0;
        int result = 0;

        Map<Integer,Integer> map = new HashMap<>();

        map.put(A[right],1);

        while(right < A.length){

            if(map.size() == K){
                int left = start;
                Map<Integer,Integer> tempMap = new HashMap<>();
                for(Integer key : map.keySet()){
                    tempMap.put(key,map.get(key));
                }

                while(left <= right && tempMap.size() == K){
                    result++;
                    int curCount = tempMap.get(A[left]);
                    curCount--;
                    if(curCount == 0){
                        tempMap.remove(A[left]);
                    }
                    else{
                        tempMap.put(A[left],curCount);
                    }

                    left ++;
                }
                right++;
                if(right == A.length){
                    break;
                }
                int count = map.getOrDefault(A[right],0);
                count++;
                map.put(A[right],count);
            }
            else if (map.size() < K){
                right++;
                if(right == A.length){
                    break;
                }
                int count = map.getOrDefault(A[right],0);
                count++;
                map.put(A[right],count);
            }
            else{
                while(start <= right && map.size() > K){
                    int count = map.get(A[start]);
                    count--;
                    if(count == 0){
                        map.remove(A[start]);
                    }
                    else{
                        map.put(A[start],count);
                    }
                    start++;
                }
            }
        }
        return result;
    }


    public int longestOnes2(int[] A, int K) {
        int right = 0;
        int max = 0;

        while(right < A.length){
            right = findNextOne(A,right);
            int left = right;
            while(left >=0 && A[left] == 1){
                left--;
            }
            int count = K;
            while(left >= 0 && count >0){
                if(A[left] == 0){
                    count--;
                }
                left --;
            }
            while(left >=0 && A[left] == 1){
                left--;
            }
            left++;

            while(right < A.length && count > 0){
                if(A[right] == 0){
                    count--;
                }
                right++;
            }

            while(right < A.length && A[right] ==1){
                right++;
            }

            max = Math.max(max,right-left);
            right++;
        }
        return max;
    }
    public int findNextOne(int[]A, int right){
        while(right < A.length && A[right] == 0){
            right++;
        }
        while(right < A.length && A[right] == 1){
            right++;
        }
        right--;
        return right;
    }


    public int longestOnes1(int[] A, int K) {
        int i = 0, j;
        for (j = 0; j < A.length; ++j) {
            if (A[j] == 0) K--;
            if (K < 0 && A[i++] == 0) K++;
        }
        return j - i;
    }

    public int longestOnes(int[] A, int K) {
        int max = 0;
        int i =0;
        int count = 0;
        for(int right =0; right < A.length; right++){
            if(A[right] == 0){
                count++;
            }
            while(count > K){
                max = Math.max(max,right-i);
                if(A[i] == 0){
                    count--;
                }
                i++;
            }
        }
        max = Math.max(max,A.length-i);
        return max;
    }


}
