package dp;

import javafx.util.Pair;

import java.util.*;

public class DP {



    public String longestPalindrome(String s) {

        int start  = 0;
        int length = 1;
        int len = s.length();
        if(len <= 1){
            return s;
        }
        boolean[][] matrix = new boolean[len][len];
        for(int i =0; i < len; i++){
            matrix[i][i] = true;
        }

        for(int i =len-1; i >=0; i--){
            for(int j =i+1; j < len; j++){
                if(s.charAt(i) == s.charAt(j)){
                    boolean isPalidrome = matrix[i+1][j-1];
                    if(i+1 > j-1){
                        isPalidrome = true;
                    }
                    matrix[i][j] = isPalidrome;
                    if(isPalidrome){
                        if(j-i+1 > length){
                            length = j-i+1;
                            start = i;
                        }
                    }
                }
            }
        }
        return s.substring(start,start+length);
    }

    public boolean isMatch1(String s, String p) {
        if(p.length() == 0){
            if(s.length() == 0){
                return true;
            }
            else{
                return false;
            }
        }
        if(p.length() == 1){
            if(p.equals(".")){
                return (s.length() == 1);
            }
            else{
                return s.equals(p);
            }
        }

        if(p.charAt(1) == '*'){
            if(s.length() == 0){
                return p.length() == 2;
            }
            if(p.charAt(0) == '.'){

                return isMatch1(s,p.substring(2)) || isMatch1(s.substring(1),p.substring(2)) || isMatch1(s.substring(1),p);
            }
            else{
                if(s.charAt(0) == p.charAt(0)){
                    return isMatch1(s,p.substring(2)) || isMatch1(s.substring(1),p.substring(2)) || isMatch1(s.substring(1),p);
                }
                else{
                    return isMatch1(s,p.substring(2));
                }
            }
        }
        else{
            if(s.length() == 0){
                return false;
            }
            if(p.charAt(0) == '.'){

                return isMatch1(s.substring(1),p.substring(1));
            }
            else{
                if(s.charAt(0) != p.charAt(0)){
                    return false;
                }
                else{
                    return isMatch1(s.substring(1),p.substring(1));
                }
            }
        }
    }

    public boolean isMatch(String s, String p) {
        char[] plist = p.toCharArray();
        int index = 0;
        List<String> column = new ArrayList<>();
        int count = 0;
        for(char c : plist){
            if(c == '*'){
                String a = column.get(index-1) + Character.toString(c);
                column.set(index-1,a);
                count--;
            }
            else{
                column.add(Character.toString(c));
                index ++;
                count++;
            }
        }


        System.out.println(column);
        int n = column.size()+1;
        int m = s.length()+1;

        Boolean[][] matrix = new Boolean[n][m];
        for(int j =0; j < m-1; j++){
            matrix[n-1][j] = false;
        }
        matrix[n-1][m-1] = true;

        for(int i = n-2; i >=0; i--){
            String pattern = column.get(i);
            if(pattern.length() == 1){
                matrix[i][m-1] = false;
            }
            else{
                matrix[i][m-1] = matrix[i+1][m-1];
            }
        }

        for(int i = n-2; i >=0; i-- ){
            for(int j = m -2; j >=0; j--){
                String pattern = column.get(i);
                String word = Character.toString(s.charAt(j));
                if(pattern.length() == 1){
                    if(pattern.equals(word)){
                        matrix[i][j] = true;
                    }
                    else{
                        matrix[i][j] =false;
                    }
                }
                else{
                    if(pattern.charAt(0) == '.'){
                        matrix[i][j] = matrix[i+1][j] || matrix[i][j+1] || matrix[i+1][j+1];
                    }
                    else{
                        if(pattern.charAt(0) == word.charAt(0)){
                            matrix[i][j] = matrix[i+1][j] || matrix[i][j+1] || matrix[i+1][j+1];
                        }
                        else{
                            matrix[i][j] = matrix[i+1][j];
                        }
                    }

                }
            }
        }
        return matrix[0][0];
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
    public int maxSubArray(int[] nums) {
        int[] sum = new int[nums.length];
        sum[0] = nums[0];

        for(int i =1; i < nums.length; i++){
            sum[i] = Math.max(nums[i],sum[i-1]+nums[i]);
        }
        int max = Integer.MIN_VALUE;
        for(int i : sum){
            max = Math.max(max,i);
        }
        return max;
    }

    public int uniquePaths(int m, int n) {
        int[][] matrix = new int[n][m];
        for(int i =0; i < n; i++){
            matrix[i][m-1] = 1;
        }
        for(int j = 0; j < m; j++){
            matrix[n-1][j] = 1;
        }

        for(int i =n-2; i >=0; i--){
            for(int j =m-2; j >=0; j--){
                matrix[i][j] = matrix[i+1][j] + matrix[i][j+1];
            }
        }
        return matrix[0][0];
    }

    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
        int m = obstacleGrid[0].length;
        int n = obstacleGrid.length;

        int[][] matrix = new int[n][m];

        matrix[n-1][m-1] = 1-obstacleGrid[n-1][m-1];

        for(int i = n-1; i >=0; i--){
            for(int j = m-1; j >=0; j--){
                if(i == n-1 && j == m-1){
                    continue;
                }
                if(obstacleGrid[i][j] == 1){
                    matrix[i][j] = 0;
                }
                else{
                    int right = 0;
                    int down = 0;
                    if(i+1 < n){
                        down = matrix[i+1][j];
                    }
                    if(j+1 <m){
                        right = matrix[i][j+1];
                    }
                    matrix[i][j] = down+right;
                }
            }
        }
        return matrix[0][0];
    }


    public int climbStairs(int n) {
        if(n == 0){
            return 0;
        }
        if(n ==1){
            return 1;
        }
        int[] s = new int[n+1];
        s[0] = 1;
        s[1] = 1;
        for(int i = 2; i <= n; i++){
            s[i] = s[i-2]+s[i-1];
        }
        return s[n];
    }

    public int numDecodings(String s) {
        if(s.length() == 0){
            return 0;
        }
        int[] map = new int[s.length()+1];
        map[0] = 1;
        if(s.charAt(0) > '0' && s.charAt(0) <='9'){
            map[1] = 1;
        }
        else{
            return 0;
        }
        for(int i =1; i < s.length(); i++){
            if(s.charAt(i) > '0' && s.charAt(i) <='9'){
                if(Integer.parseInt(s.substring(i-1,i+1))<=26 && Integer.parseInt(s.substring(i-1,i+1))>=10){
                    map[i+1] = map[i-1] + map[i];
                }
                else{
                    map[i+1] = map[i];
                }
            }
            else{
                if(Integer.parseInt(s.substring(i-1,i+1))<=26 && Integer.parseInt(s.substring(i-1,i+1))>=10){
                    map[i+1] = map[i-1];
                }
                else{
                    return 0;
                }
            }

        }
        return map[s.length()];
    }
    public Map<Pair<Integer,Integer>,List<TreeNode>> map = new HashMap<>();

    public List<TreeNode> generateTrees(int n) {
        List<TreeNode> result = backTrack(1,n);
        return result;
    }

    public List<TreeNode> backTrack(int start, int end){
        List<TreeNode> result = new ArrayList<>();
        for(int i =start; i<= end; i++){
            List<TreeNode> leftTrees;
            List<TreeNode> rightTrees;

            if(start > i-1){
                leftTrees = null;
            }
            else{
                if(map.containsKey(new Pair<>(start,i-1))){
                    leftTrees = map.get(new Pair<>(start,i-1));
                }
                else{
                    leftTrees = backTrack(start,i-1);
                }
            }

            if(i+1 > end){
                rightTrees = null;
            }
            else{
                if(map.containsKey(new Pair<>(i+1,end))){
                    rightTrees = map.get(new Pair<>(i+1,end));
                }
                else{
                    rightTrees = backTrack(i+1,end);
                }
            }
            List<TreeNode> trees = combineTrees(leftTrees,rightTrees,i);
            result.addAll(trees);
        }
        map.put(new Pair<>(start,end),result);
        return result;
    }
    public List<TreeNode> combineTrees(List<TreeNode> leftTrees, List<TreeNode> rightTrees,int root){
        List<TreeNode> result = new ArrayList<>();
        if(leftTrees == null && rightTrees == null){
            result.add(new TreeNode(root));
            return result;
        }

        if(leftTrees == null){
            for(TreeNode right : rightTrees){
                TreeNode node = new TreeNode(root);
                node.right = right;
                result.add(node);
            }
            return result;
        }

        if(rightTrees == null){
            for(TreeNode left : leftTrees){
                TreeNode node = new TreeNode(root);
                node.left = left;
                result.add(node);
            }
            return result;
        }

        for(TreeNode left : leftTrees){
            for(TreeNode right : rightTrees){
                TreeNode node = new TreeNode(root);
                node.left = left;
                node.right = right;
                result.add(node);
            }
        }
        return result;
    }

    public int numTrees(int n) {
        int[] map = new int[n+1];
        Arrays.fill(map,0);
        map[0] = 1;
        map[1] = 1;
        for(int i =2; i < map.length; i++){
            for(int j =0; j < i; j++){
                map[i] += map[j]*map[i-j-1];
            }
        }
        return map[n];
    }

    public boolean isInterleave(String s1, String s2, String s3) {
        if(s1.equals("") && s2.equals("")){
            return s3.equals("");
        }

        if(s1.equals("")){
            return s3.equals(s2);
        }

        if(s2.equals("")){
            return s3.equals(s1);
        }


        int n = s1.length()+1;
        int m = s2.length()+1;
        int len = s3.length();
        if(m+n-2 != len){
            return false;
        }
        boolean[][] matrix = new boolean[n][m];
        matrix[0][0] = true;
        for(int i =1; i < n; i++){
            matrix[i][0] =  matrix[i-1][0] && s3.charAt(i-1) == s1.charAt(i-1) ;
        }

        for(int j =1; j < m; j++){
            matrix[0][j] = matrix[0][j-1] && s2.charAt(j-1) ==  s3.charAt(j-1);
        }

        for(int i =1; i < n; i++){
            for(int j =1; j < m; j++){
                matrix[i][j] = ( matrix[i][j-1] && s2.charAt(j-1) == s3.charAt(i+j-1) ) || (matrix[i-1][j] && s1.charAt(i-1) == s3.charAt(i+j-1));
            }
        }
        return matrix[n-1][m-1];

    }

    public Map<Pair<String,String>,Integer> map1 = new HashMap<>();

    public int numDistinct1(String s, String t) {
        if(map1.containsKey(new Pair<>(s,t))){
            return map1.get(new Pair<>(s,t));
        }
        else{
            if(t.equals("") && s.equals("")){
                map1.put(new Pair<>("",""),1);
                return 1;
            }
            else if(s.equals("")){
                map1.put(new Pair<>(s,t),0);
                return 0;
            }
            else if(t.equals("")){
                map1.put(new Pair<>(s,t),1);
                return 1;
            }
            else{
                if(s.charAt(0) == t.charAt(0)){
                    int contains = numDistinct1(s.substring(1),t.substring(1));
                    int dontContains = numDistinct1(s.substring(1),t);
                    map1.put(new Pair<>(s,t),contains+dontContains);
                    return contains+dontContains;
                }
                else{
                    int count = numDistinct1(s.substring(1),t);
                    map1.put(new Pair<>(s,t),count);
                    return count;
                }
            }
        }
    }

    public int numDistinct(String s, String t) {
        int n = s.length() +1;
        int m = t.length() +1;

        int[][] matrix = new int[n][m];

        matrix[n-1][m-1] = 1;

        for(int i =0; i < n-1; i++){
            matrix[i][m-1] = 1;
        }

        for(int j =0; j < m-1; j++){
            matrix[n-1][j] = 0;
        }

        for(int i = n-2; i >=0; i--){
            for(int j = 0; j < m-1; j++){
                if(s.charAt(i) == t.charAt(j)){
                    matrix[i][j] = matrix[i+1][j] + matrix[i+1][j+1];
                }
                else{
                    matrix[i][j] = matrix[i+1][j];
                }
            }
        }
        return matrix[0][0];
    }

    public int minimumTotal(List<List<Integer>> triangle) {
        int n = triangle.size();
        for(int i =1; i < n; i++){
            for(int j =0; j < i+1; j++){
                if(j == 0){
                    int previous = triangle.get(i-1).get(j);
                    int current = triangle.get(i).get(j);
                    triangle.get(i).set(j,previous+current);
                }
                else if(j == i){
                    int previous = triangle.get(i-1).get(j-1);
                    int current = triangle.get(i).get(j);
                    triangle.get(i).set(j,previous+current);
                }
                else{
                    int previous1 = triangle.get(i-1).get(j);
                    int previous2 = triangle.get(i-1).get(j-1);
                    int current = triangle.get(i).get(j);
                    triangle.get(i).set(j,Math.min(previous1,previous2)+current);
                }
            }
        }
        int min = Integer.MAX_VALUE;
        for(int num : triangle.get(n-1)){
            min = Math.min(min,num);

        }
        return min;
    }

    public int maxProfit(int[] prices) {
        if(prices.length == 0){
            return 0;
        }
        int max = 0;
        int[] buyAt = new int[prices.length];
        buyAt[0] = prices[0];
        for(int i =1; i < buyAt.length; i++){
            int previousBuyAt = buyAt[i-1];
            if(prices[i] > previousBuyAt){
                max = Math.max(prices[i]-previousBuyAt,max);
                buyAt[i] = previousBuyAt;
            }
            else{
                buyAt[i] = prices[i];
            }
        }
        return max;
    }

    public int maxProfit1(int[] prices) {
        if(prices.length == 0){
            return 0;
        }
        int m = prices.length;
        int[][] matrix = new int[3][m];
        for(int j =0; j < m; j++){
            matrix[0][j] = 0;
        }

        for(int i =0; i < 3; i++){
            matrix[i][0] = 0;
        }

        for(int i =1; i < 3; i++){
            for(int j=1; j < m; j++){
                int value = Math.max(0,prices[j]-prices[0]);
                for(int k = 1; k < j; k++){
                    value = Math.max(value,matrix[i-1][k-1] + prices[j]-prices[k]);
                }
                matrix[i][j] = Math.max(matrix[i][j-1],value);
            }
        }
        return matrix[2][m-1];
    }

    public int minCut(String s) {
        int len = s.length();
        if(len == 0 || len == 1){
            return 0;
        }


        boolean[][] matrix = new boolean[len][len];
        int[] result = new int[len];

        for(int i = len-1; i >=0; i--){
            int min = len-i-1;
            for(int j = i; j < len; j++){
                if(s.charAt(i) == s.charAt(j)){
                    if(i+1 > j-1){
                        matrix[i][j] = true;
                    }
                    else{
                        matrix[i][j] = matrix[i+1][j-1];
                    }
                }

                if(matrix[i][j]){
                    if(j == len-1){
                        min=0;
                    }
                    else{
                        min = Math.min(min,1 + result[j+1]);
                    }
                }
                result[i] = min;

            }
        }

        return result[0];
    }

    public boolean wordBreak1(String s, List<String> wordDict) {
        int len = s.length();
        boolean[] result = new boolean[len];

        for(int i = len-1; i >=0; i--){
            boolean match = false;
            for(String str : wordDict){
                int l = str.length();
                String ss = s.substring(i);

                if(l > ss.length()){
                    continue;
                }
                else{
                    boolean m = ss.substring(0,l).equals(str);
                    if(m){
                        int remainingIndex = i + l;
                        if(remainingIndex == len){
                            match = true;
                        }
                        else{
                            match = result[remainingIndex];
                        }
                        if(match){
                            break;
                        }
                    }
                }
            }
            result[i] = match;
        }
        return result[0];
    }

    public List<String> wordBreak(String s, List<String> wordDict) {
        int len = s.length();
        boolean[] result = new boolean[len];
        Map<Integer,List<String>> words = new HashMap<>();

        for(int i = len-1; i >=0; i--){
            boolean match = false;
            List<String> matchedWords = new ArrayList<>();

            for(String str : wordDict){
                int l = str.length();
                String ss = s.substring(i);
                if(l > ss.length()){
                    continue;
                }
                else{
                    boolean m = ss.substring(0,l).equals(str);
                    if(m){
                        int remainingIndex = i + l;
                        if(remainingIndex == len){
                            match = true;
                            List<String> currentMatchedWorsList = words.getOrDefault(i,new ArrayList<>());
                            currentMatchedWorsList.add(str);
                            words.put(i,currentMatchedWorsList);
                        }
                        else{
                            match = result[remainingIndex];
                            if(match){
                                List<String> currentMatchedWordsList = words.getOrDefault(i,new ArrayList<>());
                                List<String> remainingMatchedWordsList = words.get(remainingIndex);
                                for(String remainingMatchedWords : remainingMatchedWordsList){
                                    String newString = str + " "+ remainingMatchedWords;
                                    currentMatchedWordsList.add(newString);
                                }
                                words.put(i,currentMatchedWordsList);
                            }
                        }
                    }
                }
            }
            result[i] = match;
        }
        return words.getOrDefault(0,new ArrayList<>());
    }



    public static void main(String[] args) {
        DP dp = new DP();
        int[][]grid = new int[][]{{0,0,0},{0,1,0},{0,0,0}};
        System.out.println(dp.wordBreak("aaaaaaaa",Arrays.asList("aaaa","aaa","aa")));

    }



    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }

}
