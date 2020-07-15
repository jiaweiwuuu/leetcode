package hardProblems;


import java.util.*;
import java.util.zip.CheckedInputStream;

public class HardProblems {

    /*
     *Given a non-empty array of integers,
     * every element appears three times except for one,
     * which appears exactly once. Find that single one.
     */
    public int singleNumber(int[] nums) {

        // k = 3;
        // p = 1;
        // so m > logk =2; we need two counters;
        // as k = 11 so mask = ~(x1&x2);

        int x1 = 0, x2 = 0, mask = 0;
        for (int i : nums) {
            // write x2 first
            x2 ^= x1 & i;
            x1 ^= i;
            mask = ~(x1 & x2);
            x1 &= mask;
            x2 &= mask;
        }

        // as p = 01 so we return x1

        return x1;
    }

    /*
     * Write a function that takes an unsigned integer and
     * return the number of '1' bits it has (also known as the Hamming weight).
     */
    public int hammingWeight(int n) {
        int ones = 0;
        while (n != 0) {
            ones = ones + (n & 1);
            n = n >>> 1;
        }
        return ones;
    }

    /*
     * Given a binary array, find the maximum number of consecutive 1s in this array.
     */
    public int longestConsecutiveBinary(byte i) {
        int count = 0;
        int max = 0;
        while (i != 0) {
            if ((i & 1) == 1) {
                count++;
            } else {
                max = Math.max(max, count);
                count = 0;
            }
            i = (byte) (i >>> 1);
        }
        return Math.max(count, max);
    }

    /*
     * Reverse bits of a given 32 bits unsigned integer.
     */
    public int reverseBits(int n) {
        if (n == 0) return 0;

        int result = 0;
        for (int i = 0; i < 32; i++) {
            result <<= 1;
            if ((n & 1) == 1) result++;
            n >>= 1;
        }
        return result;
    }

    /*
     * Implement a basic calculator to evaluate a simple expression string.
     * The expression string may contain open ( and closing parentheses ), the plus + or minus sign -, non-negative integers and empty spaces .
     */

    public int calculate1(String s) {
        int num = 0;
        int sign = 1;
        int result = 0;
        Stack<Integer> stack = new Stack<>();
        char[] sArray = s.toCharArray();
        for (char c : sArray) {
            if (c == ' ') {
                continue;
            } else if (Character.isDigit(c)) {
                num = 10 * num + (c - '0');
            } else if (c == '+') {
                result += sign * num;
                num = 0;
                sign = 1;
            } else if (c == '-') {
                result += sign * num;
                num = 0;
                sign = -1;
            } else if (c == '(') {
                stack.push(result);
                stack.push(sign);
                result = 0;
                sign = 1;
            } else if (c == ')') {
                result += sign * num;
                num = 0;
                sign = stack.pop();
                int val = stack.pop();
                result = val + sign * result;
            }
        }
        result += sign * num;
        return result;
    }


    /*
     * The expression string contains only non-negative integers, +, -, *, /
     * operators and empty spaces . The integer division should truncate toward zero.
     */
    public int calculate2(String s) {
        int sign = '+';
        int num = 0;
        Stack<Integer> stack = new Stack<>();
        char[] sArray = s.toCharArray();

        for (char c : sArray) {
            if (c == ' ') {
                continue;
            } else if (Character.isDigit(c)) {
                num = num * 10 + (c - '0');
            } else {
                if (sign == '+') {
                    stack.push(num);
                } else if (sign == '-') {
                    stack.push(-num);
                } else if (sign == '*') {
                    int val = stack.pop();
                    int result = val * num;
                    stack.push(result);
                } else {
                    int val = stack.pop();
                    int result = val / num;
                    stack.push(result);
                }

                sign = c;
                num = 0;
            }
        }

        if (sign == '+') {
            stack.push(num);
        } else if (sign == '-') {
            stack.push(-num);
        } else if (sign == '*') {
            int val = stack.pop();
            int result = val * num;
            stack.push(result);
        } else {
            int val = stack.pop();
            int result = val / num;
            stack.push(result);
        }


        int res = stack.stream().mapToInt(x -> x).sum();

        return res;
    }


    /*
     * Implement a basic calculator to evaluate a simple expression string.
     * The expression string may contain open ( and closing parentheses ), the plus + or minus sign -, non-negative integers and empty spaces .
     */
    public int calculate(String s) {
        Stack<Integer> stack = new Stack<>();
        int num = 0;
        int result = 0;
        int sign = 0;

        char[] cArray = s.toCharArray();

        for (char c : cArray) {
            if (c == ' ') {
                continue;
            } else if (Character.isDigit(c)) {
                num = num * 10 + (c - '0');
            } else if (c == '+' || c == '-') {
                if (sign == 0) {
                    result += num;
                } else if (sign == 1) {
                    result -= num;
                } else if (sign == 2) {
                    result *= num;
                    if (!stack.isEmpty() && !(stack.peek() == -1)) {
                        sign = stack.pop();
                        int value = stack.pop();
                        if (sign == 0) {
                            result = value + result;
                        } else {
                            result = value - result;
                        }
                    }
                } else {
                    result /= num;
                    if (!stack.isEmpty() && !(stack.peek() == -1)) {
                        sign = stack.pop();
                        int value = stack.pop();
                        if (sign == 0) {
                            result = value + result;
                        } else {
                            result = value - result;
                        }
                    }
                }

                num = 0;
                sign = c == '+' ? 0 : 1;
            } else if (c == '*' || c == '/') {
                if (sign == 0) {
                    stack.push(result);
                    stack.push(sign);
                    result = num;
                    num = 0;
                } else if (sign == 1) {
                    stack.push(result);
                    stack.push(sign);
                    result = num;
                    num = 0;
                } else if (sign == 2) {
                    result = result * num;
                    num = 0;
                } else {
                    result = result / num;
                    num = 0;
                }
                sign = c == '*' ? 2 : 3;
            } else if (c == '(') {
                stack.push(result);
                stack.push(sign);
                stack.push(-1);
                result = 0;
                num = 0;
                sign = 0;
            } else {
                if (sign == 0) {
                    result += num;
                } else if (sign == 1) {
                    result -= num;
                } else if (sign == 2) {
                    result *= num;
                    if (!stack.isEmpty() && !(stack.peek() == -1)) {
                        sign = stack.pop();
                        int value = stack.pop();
                        if (sign == 0) {
                            result = value + result;
                        } else {
                            result = value - result;
                        }
                    }
                } else {
                    result /= num;
                    if (!stack.isEmpty() && !(stack.peek() == -1)) {
                        sign = stack.pop();
                        int value = stack.pop();
                        if (sign == 0) {
                            result = value + result;
                        } else {
                            result = value - result;
                        }
                    }
                }
                stack.pop();
                if (!stack.isEmpty() && !(stack.peek() == -1)) {
                    num = result;
                    sign = stack.pop();
                    result = stack.pop();
                }
            }

        }

        if (sign == 0) {
            result += num;
        } else if (sign == 1) {
            result -= num;
        } else if (sign == 2) {
            result *= num;
            if (!stack.isEmpty() && !(stack.peek() == -1)) {
                sign = stack.pop();
                int value = stack.pop();
                if (sign == 0) {
                    result = value + result;
                } else {
                    result = value - result;
                }
            }
        } else {
            result /= num;
            if (!stack.isEmpty() && !(stack.peek() == -1)) {
                sign = stack.pop();
                int value = stack.pop();
                if (sign == 0) {
                    result = value + result;
                } else {
                    result = value - result;
                }
            }
        }
        return result;
    }

    public boolean judgePoint24(int[] nums) {
        List<Double> cards = new ArrayList<>();
        for (int i : nums) {
            cards.add((double) i);
        }

        return cardBackTrack(cards);
    }

    public boolean cardBackTrack(List<Double> cards) {
        if (cards.size() == 1) {
            double result = cards.get(0);
            if (Math.abs(result - 24.0) < 0.001) {
                return true;
            }
            return false;
        }


        for (int i = 0; i < cards.size(); i++) {
            for (int j = i + 1; j < cards.size(); j++) {
                double num1 = cards.get(i);
                double num2 = cards.get(j);
                Set<Double> allCombinations = getAllCombinations(num1, num2);

                for (double num3 : allCombinations) {
                    List<Double> nextGeneration = new ArrayList<>();
                    nextGeneration.add(num3);
                    for (int k = 0; k < cards.size(); k++) {
                        if (k != i && k != j) {
                            nextGeneration.add(cards.get(k));
                        }
                    }
                    if (cardBackTrack(nextGeneration)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public Set<Double> getAllCombinations(double num1, double num2) {
        Set<Double> set = new HashSet<>();
        set.add(num1 + num2);
        set.add(num1 - num2);
        set.add(num2 - num1);

        set.add(num1 * num2);
        set.add(num1 / num2);
        set.add(num2 / num1);
        return set;
    }


    public void solveSudoku(char[][] board) {
        List<int[]> unSolved = new ArrayList<>();
        int n = board.length;
        if (n == 0) {
            return;
        }
        int m = board[0].length;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (board[i][j] == '.') {
                    unSolved.add(new int[]{i, j});
                }
            }
        }
        sudokuBacktrack(board,unSolved);

    }

    private boolean sudokuBacktrack(char[][] board, List<int[]> unsolved) {
        if (unsolved.size() == 0) {
            if (judgeSudoku(board)) {
                return true;
            } else {
                return false;
            }
        }

        int[] position = unsolved.get(0);
        Set<Integer> candidate = getCandidates(position, board);
        unsolved.remove(0);
        for (int value: candidate) {
            board[position[0]][position[1]] = (char) ('0' + value);
            if (sudokuBacktrack(board,unsolved )){
                return true;
            }
            else{
                board[position[0]][position[1]] = '.';
            }
        }
        unsolved.add(0,position);
        return false;
    }

    private Set<Integer> getCandidates(int[] position, char[][] board) {
        Set<Integer> candidates = new HashSet<>();
        for (int i = 1; i < 10; i++) {
            candidates.add(i);
        }
        int n = board.length;
        int m = board[0].length;

        int row = position[0];
        int column = position[1];

        for (int i = 0; i < n; i++) {
            if (Character.isDigit(board[i][column])) {
                candidates.remove(board[i][column] - '0');
            }
        }

        for (int j = 0; j < m; j++) {
            if (Character.isDigit(board[row][j])) {
                candidates.remove(board[row][j] - '0');
            }
        }

        int blockColumn = column/3;
        int blockRow = row/3;

        for(int i = 3*blockRow; i < 3*blockRow+3; i++){
            for(int j =3*blockColumn; j < 3*blockColumn+3; j++){
                if (Character.isDigit(board[i][j])) {
                    candidates.remove(board[i][j] - '0');
                }
            }
        }

        return candidates;
    }


    private boolean judgeSudoku(char[][] board) {
        System.out.println(Arrays.deepToString(board));
        int n = board.length;
        for(int i = 0; i < n; i++){
            Set<Character> lineSet = new HashSet<>();
            Set<Character> columnSet = new HashSet<>();
            for(int j = 0; j < n; j++){
                if(lineSet.contains(board[i][j])){
                    return false;
                }
                else{
                    lineSet.add(board[i][j]);
                }

                if(columnSet.contains(board[j][i])){
                    return false;
                }
                else{
                    columnSet.add(board[j][i]);
                }
            }
        }

        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3 ;j ++){
                Set<Character> blockSet = new HashSet<>();
                for(int row = 3*i; row< 3*i+3; row ++){
                    for(int column = 3*j; column < 3*j+3; column++){
                        if(blockSet.contains(board[row][column])){
                            return false;
                        }
                        else{
                            blockSet.add(board[row][column]);
                        }
                    }
                }
            }
        }

        return true;
    }

    /*
        public void solveSudoku(char[][] board) {
        if(board == null || board.length == 0)
            return;
        solve(board);
    }

    public boolean solve(char[][] board){
        for(int i = 0; i < board.length; i++){
            for(int j = 0; j < board[0].length; j++){
                if(board[i][j] == '.'){
                    for(char c = '1'; c <= '9'; c++){//trial. Try 1 through 9
                        if(isValid(board, i, j, c)){
                            board[i][j] = c; //Put c for this cell

                            if(solve(board))
                                return true; //If it's the solution return true
                            else
                                board[i][j] = '.'; //Otherwise go back
                        }
                    }

                    return false;
                }
            }
        }
        return true;
    }

    private boolean isValid(char[][] board, int row, int col, char c){
        for(int i = 0; i < 9; i++) {
            if(board[i][col] != '.' && board[i][col] == c) return false; //check row
            if(board[row][i] != '.' && board[row][i] == c) return false; //check column
            if(board[3 * (row / 3) + i / 3][ 3 * (col / 3) + i % 3] != '.' &&
board[3 * (row / 3) + i / 3][3 * (col / 3) + i % 3] == c) return false; //check 3*3 block
        }
        return true;
    }
     */

    /*
     * Given an unsorted array of integers, find the length of longest increasing subsequence.
     */
    public int lengthOfLIS(int[] nums) {
        int[] tails = new int[nums.length];
        int size = 0;

        for(int n : nums){
            int i =0;
            int j = size;
            while(i < j){
                int mid = (i+j)/2;
                if(tails[mid] == n){
                    i = mid;
                    break;
                }
                else if(tails[mid] < n){
                    i = mid+1;
                }
                else{
                    j = mid;
                }
            }

            if(i == size){
                tails[i] = n;
                size++;
            }
            else{
                tails[i] = n;
            }
        }
        return size;
    }


    /*
     * Given two words word1 and word2, find the minimum number of operations required to convert word1 to word2.
     * You have the following 3 operations permitted on a word:
     * Insert a character
     * Delete a character
     * Replace a character
     */

    public int minDistance(String word1, String word2) {
        int n = word1.length()+1;
        int m = word2.length()+1;


        int[][] dp = new int[n][m];
        dp[0][0] = 0;
        for(int i =1; i < n; i++){
            dp[i][0] = dp[i-1][0]+1;
        }

        for(int j =1; j < m; j++){
            dp[0][j] = dp[0][j-1]+1;
        }


        for(int i =1; i < n; i++){
            for(int j =1; j < m; j++){
                if(word1.charAt(i-1) == word2.charAt(j-1)){
                    dp[i][j] = Math.min(Math.min(dp[i-1][j]+1,dp[i][j-1]+1),dp[i-1][j-1]);
                }
                else{
                    dp[i][j] = Math.min(Math.min(dp[i-1][j],dp[i][j-1]),dp[i-1][j-1]) +1;
                }

            }
        }
        return dp[n-1][m-1];
    }


//    public boolean canPartition(int[] nums) {
//        int sum = 0;
//        for(int i : nums){
//            sum  += i;
//        }
//
//        if((sum & 1) == 1){
//            return false;
//        }
//
//        int target = sum/2;
//
//        return canPartitionBacktrack(target,nums);
//    }
//
//    private boolean canPartitionBacktrack(int target, int[] nums){
//        if(nums.length == 0 && target != 0){
//            return false;
//        }
//        if (target == 0) {
//            return true;
//        }
//        for(int i =0; i < nums.length; i++){
//            if(nums[i] > target){
//                continue;
//            }
//            if(canPartitionBacktrack(target-nums[i],Arrays.copyOfRange(nums,i+1,nums.length))){
//                return true;
//            }
//        }
//        return false;
//    }

    public boolean canPartition(int[] nums) {
                int sum = 0;
        for(int i : nums){
            sum  += i;
        }

        if((sum & 1) == 1){
            return false;
        }

        int target = sum/2;

        boolean[][] dp = new boolean[target+1][nums.length+1];

        dp[0][0] = true;
        for(int j=1; j < nums.length+1; j++){
            dp[0][j] = true;
        }

        for(int i =1; i < target+1; i++){
            for(int j =1; j < nums.length+1; j++){
                int num = nums[j-1];

                if(num > i){
                    dp[i][j] = dp[i][j-1];
                }
                else{
                    dp[i][j] = dp[i][j-1] || dp[i-num][j-1];
                }
            }
        }
        return dp[target][nums.length];
    }


    public int lastStoneWeightII(int[] stones) {
        int sum = 0;
        for(int i : stones){
            sum  += i;
        }

        int largest = 0;

        int target = sum/2;

        boolean[][] dp = new boolean[target+1][stones.length+1];

        dp[0][0] = true;
        for(int j=1; j < stones.length+1; j++){
            dp[0][j] = true;
        }

        for(int i =1; i < target+1; i++){
            for(int j =1; j < stones.length+1; j++){
                int num = stones[j-1];

                if(num > i){
                    dp[i][j] = dp[i][j-1];
                }
                else{
                    dp[i][j] = dp[i][j-1] || dp[i-num][j-1];
                    if(dp[i][j]){
                        largest = Math.max(largest,i);
                    }
                }
            }
        }
        return sum-largest*2;
    }

    public int change(int amount, int[] coins) {
        int[][] dp = new int[amount+1][coins.length+1];
        dp[0][0] = 1;

        for(int j = 1; j <= coins.length; j++){
            dp[0][j] = 1;
        }

        for(int i = 1; i <= amount; i++){
            for(int j =1; j <= coins.length; j ++){
                if(coins[j-1] <= i){
                    dp[i][j] = dp[i][j-1] + dp[i-coins[j-1]][j];
                }
                else{
                    dp[i][j] = dp[i][j-1];
                }
            }
        }
        return dp[amount][coins.length];
    }

    public int findMaxForm(String[] strs, int m, int n) {
        int rows = (m+1)*(n+1);
        int columns = strs.length+1;

        int[][] dp = new int[rows][columns];

        for(int i =0; i < rows; i++){
            dp[i][0] = 0;
        }
        for(int j =0; j < columns; j++){
            dp[0][j] = 0;
        }


        for(int i =1; i < rows; i++){
            int mCount = i % (m+1);
            int nCount = i / (m+1);
            for(int j =1; j < columns; j++){
                String curStr = strs[j-1];
                int[] zeroOne = getOneAndZero(curStr);
                int zeroCount = zeroOne[0];
                int oneCount = zeroOne[1];

                if(zeroCount <= mCount && oneCount <= nCount){
                    int remainingZeros = mCount - zeroCount;
                    int remainingOnes = nCount - oneCount;
                    int iIndex = remainingOnes * (m+1) + remainingZeros;
                    dp[i][j] = Math.max(dp[i][j-1],dp[iIndex][j-1]+1);
                }
                else{
                    dp[i][j] = dp[i][j-1];
                }
            }
        }
        return dp[rows-1][columns-1];
    }

    private int[] getOneAndZero(String str){
        int one = 0;
        int zero = 0;
        for(int i =0; i < str.length(); i++){
            if(str.charAt(i) == '0'){
                zero++;
            }
            else{
                one++;
            }
        }
        return new int[]{zero,one};
    }

    public int findTargetSumWays(int[] nums, int S){
        int sum = 0;
        for(int i : nums){
            sum += i;
        }

        int m = 2*sum+1;
        int n = nums.length+1;

        if(S+sum >= m){
            return 0;
        }

        int[][]dp = new int[n][m];

        dp[n-1][0] = 1;
        dp[n-1][m-1] = 1;
        dp[0][sum] = 1;

        for(int i =1; i < n; i++){
            int num = nums[i-1];
            for(int j =1; j < m-1; j++){

                if(j-num >= 0){
                    dp[i][j] += dp[i-1][j-num];
                }
                if(j+num <m){
                    dp[i][j] += dp[i-1][j+num];
                }
            }
        }
        return dp[n-1][S+sum];
    }

    /*
     * topological sort
     */

    public boolean canFinish(int numCourses, int[][] prerequisites) {
        int count = 0;
        List<Integer>[] neighbours = new LinkedList[numCourses];
        int[] indegree = new int[numCourses];


        for(int i =0; i < numCourses; i++){
            neighbours[i] = new LinkedList<>();
        }

        for(int[] pres : prerequisites){
            indegree[pres[0]]++;
            neighbours[pres[1]].add(pres[0]);
        }

        Queue<Integer> q = new LinkedList<>();

        for(int i =0; i < numCourses; i++){
            if(indegree[i] == 0){
                q.add(i);
            }
        }

        while(! q.isEmpty()){
            int course = q.poll();
            count++;
            for(int nextCourse : neighbours[course]){
                indegree[nextCourse]--;
                if(indegree[nextCourse] == 0){
                    q.add(nextCourse);
                }
            }
        }

        return count == numCourses;

    }
    public int[] findOrder(int numCourses, int[][] prerequisites) {

        int count = 0;
        int[] order = new int[numCourses];
        List<Integer>[] neighbours = new LinkedList[numCourses];
        int[] indegree = new int[numCourses];


        for(int i =0; i < numCourses; i++){
            neighbours[i] = new LinkedList<>();
        }

        for(int[] pres : prerequisites){
            indegree[pres[0]]++;
            neighbours[pres[1]].add(pres[0]);
        }

        Queue<Integer> q = new LinkedList<>();

        for(int i =0; i < numCourses; i++){
            if(indegree[i] == 0){
                q.add(i);
            }
        }

        while(! q.isEmpty()){
            int course = q.poll();
            order[count] = course;
            count++;
            for(int nextCourse : neighbours[course]){
                indegree[nextCourse]--;
                if(indegree[nextCourse] == 0){
                    q.add(nextCourse);
                }
            }
        }

        if(count == numCourses){
            return order;
        }
        else{
            return new int[0];
        }
    }

    /*
     * Given an unsorted array, find the maximum difference between the successive elements in its sorted form.
     */

    public int maximumGap(int[] nums) {
        if(nums.length <2){
            return 0;
        }
        int max = nums[0];
        int min = nums[0];

        for(int i =0; i < nums.length; i++){
            min = Math.min(min,nums[i]);
            max = Math.max(max,nums[i]);
        }

        int gap = (int)Math.ceil(((double)(max-min))/(nums.length-1));

        int[] minBucket = new int[nums.length-1];
        int[] maxBucket = new int[nums.length-1];

        Arrays.fill(minBucket,Integer.MAX_VALUE);
        Arrays.fill(maxBucket,Integer.MIN_VALUE);

        for(int i =0; i < nums.length; i ++){
            if(nums[i] == min || nums[i] == max){
                continue;
            }

            int index=  (nums[i] - min)/gap ;
            minBucket[index] = Math.min(minBucket[index],nums[i]);
            maxBucket[index] = Math.max(maxBucket[index],nums[i]);
        }

        int maxGap = Integer.MIN_VALUE;
        int previous = min;
        for (int i = 0; i < nums.length - 1; i++) {
            if (minBucket[i] == Integer.MAX_VALUE && maxBucket[i] == Integer.MIN_VALUE)
                // empty bucket
                continue;
            // min value minus the previous value is the current gap
            maxGap = Math.max(maxGap, minBucket[i] - previous);
            // update previous bucket value
            previous = maxBucket[i];
        }
        maxGap = Math.max(maxGap, max - previous); // updata the final max value gap
        return maxGap;
    }

    public int maximalRectangle(char[][] matrix) {
        int n = matrix.length;
        int m = matrix[0].length;


        int[] height = new int[m];
        int[] leftMost = new int[m];
        int[] rightMost = new int[m];
        int leftBound = 0;
        int rightBound = m-1;

        Arrays.fill(rightMost,m-1);

        int maxArea = 0;

        for(int i =0; i < n; i++){
            leftBound = 0;
            rightBound = m-1;
            for(int j = m-1; j >=0; j--){
                if(matrix[i][j] == '1'){
                    rightMost[j] = Math.min(rightBound,rightMost[j]);
                    height[j] ++;
                }
                else{
                    rightBound = j-1;
                    rightMost[j] = m-1;
                    height[j]=0;
                }
            }

            for(int j = 0; j < m; j++){
                if(matrix[i][j] == '1'){
                    leftMost[j] = Math.max(leftBound,leftMost[j]);
                    int area = (rightMost[j]-leftMost[j]+1) * height[j];
                    maxArea = Math.max(area,maxArea);
                }
                else{
                    leftBound = j+1;
                    leftMost[j] = 0;
                }
            }
        }
        return maxArea;
    }


    public int minCut(String s) {
        int n = s.length();
        boolean[][] dp = new boolean[n][n];

        int[] cuts = new int[n];
        Arrays.fill(cuts,Integer.MAX_VALUE);

        for(int i =n-1; i >=0; i--){
            for(int j = i; j < n; j++){

                if(s.charAt(i) == s.charAt(j)){
                    if(i + 1 > j -1){
                        dp[i][j] = true;
                    }
                    else{
                        dp[i][j] = dp[i+1][j-1];
                    }
                }

                if(dp[i][j]){
                    if(j == n-1){
                        cuts[i] = 0;
                    }
                    else{
                        cuts[i] = Math.min(cuts[i],1+ cuts[j+1]);
                    }
                }
            }
        }
        return cuts[0];
    }


    public int longestValidParentheses(String s) {
        int[] matrix = new int[s.length()+1];
        matrix[0] = 0;

        for(int i =0; i < s.length(); i++){
            if(s.charAt(i) == '('){
                matrix[i+1] = 0;
            }
            else{
                if(i == 0){
                    matrix[i+1] = 0;
                }
                else{
                    if(s.charAt(i-1) == '('){
                        matrix[i+1] = matrix[i-1] +2;
                    }
                    else{
                        if(i-matrix[i]-1 < 0){
                            matrix[i+1] = 0;
                        }
                        else{
                            if(s.charAt(i-matrix[i]-1) == '('){
                                matrix[i+1] = matrix[i-matrix[i]-1]+2+matrix[i];
                            }
                            else{
                                matrix[i+1] = 0;
                            }

                        }

                    }
                }
            }
        }
        int max = 0;
        for(int i : matrix){
            max = Math.max(i,max);
        }
        return max;
    }


    public static void main(String[] args) {
        //new HardProblems().solveSudoku(new char[][]{{'5','3','.','.','7','.','.','.','.'},{'6','.','.','1','9','5','.','.','.'},{'.','9','8','.','.','.','.','6','.'},{'8','.','.','.','6','.','.','.','3'},{'4','.','.','8','.','3','.','.','1'},{'7','.','.','.','2','.','.','.','6'},{'.','6','.','.','.','.','2','8','.'},{'.','.','.','4','1','9','.','.','5'},{'.','.','.','.','8','.','.','7','9'}});
        //new HardProblems().solveSudoku(new char[][]{{'1','.','.'},{'.','3','1'},{'3','1','.'}});

        System.out.println(new HardProblems().canFinish(4,new int[][]{{3,0},{0,1}}));
    }

    /*
     * Design and implement a data structure for Least Recently Used (LRU) cache. It should support the following operations: get and put.
     * get(key) - Get the value (will always be positive) of the key if the key exists in the cache, otherwise return -1.
     * put(key, value) - Set or insert the value if the key is not already present. When the cache reached its capacity,
     * it should invalidate the least recently used item before inserting a new item.
     * The cache is initialized with a positive capacity.
     */
    public static class LRUCache {
        DlinkedNode head;
        DlinkedNode tail;
        Map<Integer, DlinkedNode> cache = new HashMap<>();
        int capacity;

        class DlinkedNode {
            int key;
            int value;
            DlinkedNode pre;
            DlinkedNode next;

            public DlinkedNode(int key, int value) {
                this.key = key;
                this.value = value;
            }
        }

        public LRUCache(int capacity) {
            head = new DlinkedNode(0, 0);
            tail = new DlinkedNode(0, 0);
            head.next = tail;
            tail.pre = head;
            this.capacity = capacity;
        }

        public int get(int key) {
            if (!cache.containsKey(key)) {
                System.out.println(-1);
                return -1;
            }

            DlinkedNode node = cache.get(key);
            removeNode(node);
            insertToFirst(node);
            System.out.println(node.value);
            return node.value;
        }

        public void put(int key, int value) {
            if (cache.containsKey(key)) {
                DlinkedNode node = cache.get(key);
                node.value = value;
                removeNode(node);
                insertToFirst(node);
            } else {
                if (cache.size() == capacity) {
                    int removeKey = removeLastOne();
                    cache.remove(removeKey);
                }

                DlinkedNode node = new DlinkedNode(key, value);
                cache.put(key, node);
                insertToFirst(node);
            }
        }

        private int removeLastOne() {
            int key = tail.pre.key;
            DlinkedNode lastOne = tail.pre.pre;
            lastOne.next = tail;
            tail.pre = lastOne;
            return key;
        }

        private void insertToFirst(DlinkedNode node) {
            DlinkedNode next = head.next;
            head.next = node;
            node.next = next;
            node.pre = head;
            next.pre = node;
        }

        private void removeNode(DlinkedNode node) {
            DlinkedNode left = node.pre;
            DlinkedNode right = node.next;

            left.next = right;
            right.pre = left;
        }
    }
}
