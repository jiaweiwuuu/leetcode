package dp;

import javafx.util.Pair;

import java.util.*;

public class DP {


    public String longestPalindrome(String s) {

        int start = 0;
        int length = 1;
        int len = s.length();
        if (len <= 1) {
            return s;
        }
        boolean[][] matrix = new boolean[len][len];
        for (int i = 0; i < len; i++) {
            matrix[i][i] = true;
        }

        for (int i = len - 1; i >= 0; i--) {
            for (int j = i + 1; j < len; j++) {
                if (s.charAt(i) == s.charAt(j)) {
                    boolean isPalidrome = matrix[i + 1][j - 1];
                    if (i + 1 > j - 1) {
                        isPalidrome = true;
                    }
                    matrix[i][j] = isPalidrome;
                    if (isPalidrome) {
                        if (j - i + 1 > length) {
                            length = j - i + 1;
                            start = i;
                        }
                    }
                }
            }
        }
        return s.substring(start, start + length);
    }

    public boolean isMatch1(String s, String p) {
        if (p.length() == 0) {
            if (s.length() == 0) {
                return true;
            } else {
                return false;
            }
        }
        if (p.length() == 1) {
            if (p.equals(".")) {
                return (s.length() == 1);
            } else {
                return s.equals(p);
            }
        }

        if (p.charAt(1) == '*') {
            if (s.length() == 0) {
                return p.length() == 2;
            }
            if (p.charAt(0) == '.') {

                return isMatch1(s, p.substring(2)) || isMatch1(s.substring(1), p.substring(2)) || isMatch1(s.substring(1), p);
            } else {
                if (s.charAt(0) == p.charAt(0)) {
                    return isMatch1(s, p.substring(2)) || isMatch1(s.substring(1), p.substring(2)) || isMatch1(s.substring(1), p);
                } else {
                    return isMatch1(s, p.substring(2));
                }
            }
        } else {
            if (s.length() == 0) {
                return false;
            }
            if (p.charAt(0) == '.') {

                return isMatch1(s.substring(1), p.substring(1));
            } else {
                if (s.charAt(0) != p.charAt(0)) {
                    return false;
                } else {
                    return isMatch1(s.substring(1), p.substring(1));
                }
            }
        }
    }

    public boolean isMatch(String s, String p) {
        char[] plist = p.toCharArray();
        int index = 0;
        List<String> column = new ArrayList<>();
        int count = 0;
        for (char c : plist) {
            if (c == '*') {
                String a = column.get(index - 1) + Character.toString(c);
                column.set(index - 1, a);
                count--;
            } else {
                column.add(Character.toString(c));
                index++;
                count++;
            }
        }


        System.out.println(column);
        int n = column.size() + 1;
        int m = s.length() + 1;

        Boolean[][] matrix = new Boolean[n][m];
        for (int j = 0; j < m - 1; j++) {
            matrix[n - 1][j] = false;
        }
        matrix[n - 1][m - 1] = true;

        for (int i = n - 2; i >= 0; i--) {
            String pattern = column.get(i);
            if (pattern.length() == 1) {
                matrix[i][m - 1] = false;
            } else {
                matrix[i][m - 1] = matrix[i + 1][m - 1];
            }
        }

        for (int i = n - 2; i >= 0; i--) {
            for (int j = m - 2; j >= 0; j--) {
                String pattern = column.get(i);
                String word = Character.toString(s.charAt(j));
                if (pattern.length() == 1) {
                    if (pattern.equals(word)) {
                        matrix[i][j] = true;
                    } else {
                        matrix[i][j] = false;
                    }
                } else {
                    if (pattern.charAt(0) == '.') {
                        matrix[i][j] = matrix[i + 1][j] || matrix[i][j + 1] || matrix[i + 1][j + 1];
                    } else {
                        if (pattern.charAt(0) == word.charAt(0)) {
                            matrix[i][j] = matrix[i + 1][j] || matrix[i][j + 1] || matrix[i + 1][j + 1];
                        } else {
                            matrix[i][j] = matrix[i + 1][j];
                        }
                    }

                }
            }
        }
        return matrix[0][0];
    }

    public int longestValidParentheses(String s) {
        int[] matrix = new int[s.length() + 1];
        matrix[0] = 0;

        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                matrix[i + 1] = 0;
            } else {
                if (i == 0) {
                    matrix[i + 1] = 0;
                } else {
                    if (s.charAt(i - 1) == '(') {
                        matrix[i + 1] = matrix[i - 1] + 2;
                    } else {
                        if (i - matrix[i] - 1 < 0) {
                            matrix[i + 1] = 0;
                        } else {
                            if (s.charAt(i - matrix[i] - 1) == '(') {
                                matrix[i + 1] = matrix[i - matrix[i] - 1] + 2 + matrix[i];
                            } else {
                                matrix[i + 1] = 0;
                            }

                        }

                    }
                }
            }
        }
        int max = 0;
        for (int i : matrix) {
            max = Math.max(i, max);
        }
        return max;
    }

    public int maxSubArray(int[] nums) {
        int[] sum = new int[nums.length];
        sum[0] = nums[0];

        for (int i = 1; i < nums.length; i++) {
            sum[i] = Math.max(nums[i], sum[i - 1] + nums[i]);
        }
        int max = Integer.MIN_VALUE;
        for (int i : sum) {
            max = Math.max(max, i);
        }
        return max;
    }

    public int uniquePaths(int m, int n) {
        int[][] matrix = new int[n][m];
        for (int i = 0; i < n; i++) {
            matrix[i][m - 1] = 1;
        }
        for (int j = 0; j < m; j++) {
            matrix[n - 1][j] = 1;
        }

        for (int i = n - 2; i >= 0; i--) {
            for (int j = m - 2; j >= 0; j--) {
                matrix[i][j] = matrix[i + 1][j] + matrix[i][j + 1];
            }
        }
        return matrix[0][0];
    }

    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
        int m = obstacleGrid[0].length;
        int n = obstacleGrid.length;

        int[][] matrix = new int[n][m];

        matrix[n - 1][m - 1] = 1 - obstacleGrid[n - 1][m - 1];

        for (int i = n - 1; i >= 0; i--) {
            for (int j = m - 1; j >= 0; j--) {
                if (i == n - 1 && j == m - 1) {
                    continue;
                }
                if (obstacleGrid[i][j] == 1) {
                    matrix[i][j] = 0;
                } else {
                    int right = 0;
                    int down = 0;
                    if (i + 1 < n) {
                        down = matrix[i + 1][j];
                    }
                    if (j + 1 < m) {
                        right = matrix[i][j + 1];
                    }
                    matrix[i][j] = down + right;
                }
            }
        }
        return matrix[0][0];
    }


    public int climbStairs(int n) {
        if (n == 0) {
            return 0;
        }
        if (n == 1) {
            return 1;
        }
        int[] s = new int[n + 1];
        s[0] = 1;
        s[1] = 1;
        for (int i = 2; i <= n; i++) {
            s[i] = s[i - 2] + s[i - 1];
        }
        return s[n];
    }

    public int numDecodings(String s) {
        if (s.length() == 0) {
            return 0;
        }
        int[] map = new int[s.length() + 1];
        map[0] = 1;
        if (s.charAt(0) > '0' && s.charAt(0) <= '9') {
            map[1] = 1;
        } else {
            return 0;
        }
        for (int i = 1; i < s.length(); i++) {
            if (s.charAt(i) > '0' && s.charAt(i) <= '9') {
                if (Integer.parseInt(s.substring(i - 1, i + 1)) <= 26 && Integer.parseInt(s.substring(i - 1, i + 1)) >= 10) {
                    map[i + 1] = map[i - 1] + map[i];
                } else {
                    map[i + 1] = map[i];
                }
            } else {
                if (Integer.parseInt(s.substring(i - 1, i + 1)) <= 26 && Integer.parseInt(s.substring(i - 1, i + 1)) >= 10) {
                    map[i + 1] = map[i - 1];
                } else {
                    return 0;
                }
            }

        }
        return map[s.length()];
    }

    public Map<Pair<Integer, Integer>, List<TreeNode>> map = new HashMap<>();

    public List<TreeNode> generateTrees(int n) {
        List<TreeNode> result = backTrack(1, n);
        return result;
    }

    public List<TreeNode> backTrack(int start, int end) {
        List<TreeNode> result = new ArrayList<>();
        for (int i = start; i <= end; i++) {
            List<TreeNode> leftTrees;
            List<TreeNode> rightTrees;

            if (start > i - 1) {
                leftTrees = null;
            } else {
                if (map.containsKey(new Pair<>(start, i - 1))) {
                    leftTrees = map.get(new Pair<>(start, i - 1));
                } else {
                    leftTrees = backTrack(start, i - 1);
                }
            }

            if (i + 1 > end) {
                rightTrees = null;
            } else {
                if (map.containsKey(new Pair<>(i + 1, end))) {
                    rightTrees = map.get(new Pair<>(i + 1, end));
                } else {
                    rightTrees = backTrack(i + 1, end);
                }
            }
            List<TreeNode> trees = combineTrees(leftTrees, rightTrees, i);
            result.addAll(trees);
        }
        map.put(new Pair<>(start, end), result);
        return result;
    }

    public List<TreeNode> combineTrees(List<TreeNode> leftTrees, List<TreeNode> rightTrees, int root) {
        List<TreeNode> result = new ArrayList<>();
        if (leftTrees == null && rightTrees == null) {
            result.add(new TreeNode(root));
            return result;
        }

        if (leftTrees == null) {
            for (TreeNode right : rightTrees) {
                TreeNode node = new TreeNode(root);
                node.right = right;
                result.add(node);
            }
            return result;
        }

        if (rightTrees == null) {
            for (TreeNode left : leftTrees) {
                TreeNode node = new TreeNode(root);
                node.left = left;
                result.add(node);
            }
            return result;
        }

        for (TreeNode left : leftTrees) {
            for (TreeNode right : rightTrees) {
                TreeNode node = new TreeNode(root);
                node.left = left;
                node.right = right;
                result.add(node);
            }
        }
        return result;
    }

    public int numTrees(int n) {
        int[] map = new int[n + 1];
        Arrays.fill(map, 0);
        map[0] = 1;
        map[1] = 1;
        for (int i = 2; i < map.length; i++) {
            for (int j = 0; j < i; j++) {
                map[i] += map[j] * map[i - j - 1];
            }
        }
        return map[n];
    }

    public boolean isInterleave(String s1, String s2, String s3) {
        if (s1.equals("") && s2.equals("")) {
            return s3.equals("");
        }

        if (s1.equals("")) {
            return s3.equals(s2);
        }

        if (s2.equals("")) {
            return s3.equals(s1);
        }


        int n = s1.length() + 1;
        int m = s2.length() + 1;
        int len = s3.length();
        if (m + n - 2 != len) {
            return false;
        }
        boolean[][] matrix = new boolean[n][m];
        matrix[0][0] = true;
        for (int i = 1; i < n; i++) {
            matrix[i][0] = matrix[i - 1][0] && s3.charAt(i - 1) == s1.charAt(i - 1);
        }

        for (int j = 1; j < m; j++) {
            matrix[0][j] = matrix[0][j - 1] && s2.charAt(j - 1) == s3.charAt(j - 1);
        }

        for (int i = 1; i < n; i++) {
            for (int j = 1; j < m; j++) {
                matrix[i][j] = (matrix[i][j - 1] && s2.charAt(j - 1) == s3.charAt(i + j - 1)) || (matrix[i - 1][j] && s1.charAt(i - 1) == s3.charAt(i + j - 1));
            }
        }
        return matrix[n - 1][m - 1];

    }

    public Map<Pair<String, String>, Integer> map1 = new HashMap<>();

    public int numDistinct1(String s, String t) {
        if (map1.containsKey(new Pair<>(s, t))) {
            return map1.get(new Pair<>(s, t));
        } else {
            if (t.equals("") && s.equals("")) {
                map1.put(new Pair<>("", ""), 1);
                return 1;
            } else if (s.equals("")) {
                map1.put(new Pair<>(s, t), 0);
                return 0;
            } else if (t.equals("")) {
                map1.put(new Pair<>(s, t), 1);
                return 1;
            } else {
                if (s.charAt(0) == t.charAt(0)) {
                    int contains = numDistinct1(s.substring(1), t.substring(1));
                    int dontContains = numDistinct1(s.substring(1), t);
                    map1.put(new Pair<>(s, t), contains + dontContains);
                    return contains + dontContains;
                } else {
                    int count = numDistinct1(s.substring(1), t);
                    map1.put(new Pair<>(s, t), count);
                    return count;
                }
            }
        }
    }

    public int numDistinct(String s, String t) {
        int n = s.length() + 1;
        int m = t.length() + 1;

        int[][] matrix = new int[n][m];

        matrix[n - 1][m - 1] = 1;

        for (int i = 0; i < n - 1; i++) {
            matrix[i][m - 1] = 1;
        }

        for (int j = 0; j < m - 1; j++) {
            matrix[n - 1][j] = 0;
        }

        for (int i = n - 2; i >= 0; i--) {
            for (int j = 0; j < m - 1; j++) {
                if (s.charAt(i) == t.charAt(j)) {
                    matrix[i][j] = matrix[i + 1][j] + matrix[i + 1][j + 1];
                } else {
                    matrix[i][j] = matrix[i + 1][j];
                }
            }
        }
        return matrix[0][0];
    }

    public int minimumTotal(List<List<Integer>> triangle) {
        int n = triangle.size();
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < i + 1; j++) {
                if (j == 0) {
                    int previous = triangle.get(i - 1).get(j);
                    int current = triangle.get(i).get(j);
                    triangle.get(i).set(j, previous + current);
                } else if (j == i) {
                    int previous = triangle.get(i - 1).get(j - 1);
                    int current = triangle.get(i).get(j);
                    triangle.get(i).set(j, previous + current);
                } else {
                    int previous1 = triangle.get(i - 1).get(j);
                    int previous2 = triangle.get(i - 1).get(j - 1);
                    int current = triangle.get(i).get(j);
                    triangle.get(i).set(j, Math.min(previous1, previous2) + current);
                }
            }
        }
        int min = Integer.MAX_VALUE;
        for (int num : triangle.get(n - 1)) {
            min = Math.min(min, num);

        }
        return min;
    }

    public int maxProfit(int[] prices) {
        if (prices.length == 0) {
            return 0;
        }
        int max = 0;
        int[] buyAt = new int[prices.length];
        buyAt[0] = prices[0];
        for (int i = 1; i < buyAt.length; i++) {
            int previousBuyAt = buyAt[i - 1];
            if (prices[i] > previousBuyAt) {
                max = Math.max(prices[i] - previousBuyAt, max);
                buyAt[i] = previousBuyAt;
            } else {
                buyAt[i] = prices[i];
            }
        }
        return max;
    }

    public int maxProfit1(int[] prices) {
        if (prices.length == 0) {
            return 0;
        }
        int m = prices.length;
        int[][] matrix = new int[3][m];
        for (int j = 0; j < m; j++) {
            matrix[0][j] = 0;
        }

        for (int i = 0; i < 3; i++) {
            matrix[i][0] = 0;
        }

        for (int i = 1; i < 3; i++) {
            for (int j = 1; j < m; j++) {
                int value = Math.max(0, prices[j] - prices[0]);
                for (int k = 1; k < j; k++) {
                    value = Math.max(value, matrix[i - 1][k - 1] + prices[j] - prices[k]);
                }
                matrix[i][j] = Math.max(matrix[i][j - 1], value);
            }
        }
        return matrix[2][m - 1];
    }

    public int minCut(String s) {
        int len = s.length();
        if (len == 0 || len == 1) {
            return 0;
        }


        boolean[][] matrix = new boolean[len][len];
        int[] result = new int[len];

        for (int i = len - 1; i >= 0; i--) {
            int min = len - i - 1;
            for (int j = i; j < len; j++) {
                if (s.charAt(i) == s.charAt(j)) {
                    if (i + 1 > j - 1) {
                        matrix[i][j] = true;
                    } else {
                        matrix[i][j] = matrix[i + 1][j - 1];
                    }
                }

                if (matrix[i][j]) {
                    if (j == len - 1) {
                        min = 0;
                    } else {
                        min = Math.min(min, 1 + result[j + 1]);
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

        for (int i = len - 1; i >= 0; i--) {
            boolean match = false;
            for (String str : wordDict) {
                int l = str.length();
                String ss = s.substring(i);

                if (l > ss.length()) {
                    continue;
                } else {
                    boolean m = ss.substring(0, l).equals(str);
                    if (m) {
                        int remainingIndex = i + l;
                        if (remainingIndex == len) {
                            match = true;
                        } else {
                            match = result[remainingIndex];
                        }
                        if (match) {
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
        Map<Integer, List<String>> words = new HashMap<>();

        for (int i = len - 1; i >= 0; i--) {
            boolean match = false;
            List<String> matchedWords = new ArrayList<>();

            for (String str : wordDict) {
                int l = str.length();
                String ss = s.substring(i);
                if (l > ss.length()) {
                    continue;
                } else {
                    boolean m = ss.substring(0, l).equals(str);
                    if (m) {
                        int remainingIndex = i + l;
                        if (remainingIndex == len) {
                            match = true;
                            List<String> currentMatchedWorsList = words.getOrDefault(i, new ArrayList<>());
                            currentMatchedWorsList.add(str);
                            words.put(i, currentMatchedWorsList);
                        } else {
                            match = result[remainingIndex];
                            if (match) {
                                List<String> currentMatchedWordsList = words.getOrDefault(i, new ArrayList<>());
                                List<String> remainingMatchedWordsList = words.get(remainingIndex);
                                for (String remainingMatchedWords : remainingMatchedWordsList) {
                                    String newString = str + " " + remainingMatchedWords;
                                    currentMatchedWordsList.add(newString);
                                }
                                words.put(i, currentMatchedWordsList);
                            }
                        }
                    }
                }
            }
            result[i] = match;
        }
        return words.getOrDefault(0, new ArrayList<>());
    }

    public int maxProduct1(int[] nums) {
        int[][] maxMatrix = new int[nums.length][nums.length];
        int[][] minMatrix = new int[nums.length][nums.length];

        int max = Integer.MIN_VALUE;

        for (int i = 0; i < nums.length; i++) {
            maxMatrix[i][i] = nums[i];
            minMatrix[i][i] = nums[i];
            max = Math.max(nums[i], max);
        }

        for (int i = nums.length - 2; i >= 0; i--) {
            for (int j = i + 1; j < nums.length; j++) {
                int prevMin = minMatrix[i][j - 1];
                int prevMax = maxMatrix[i][j - 1];

                int currentNum = nums[j];

                int cur1 = prevMax * currentNum;
                int cur2 = prevMin * currentNum;
                int curMin = Math.min(currentNum, Math.min(cur1, cur2));
                int curMax = Math.max(currentNum, Math.max(cur1, cur2));

                minMatrix[i][j] = curMin;
                maxMatrix[i][j] = curMax;

                max = Math.max(curMax, max);
            }
        }
        return max;
    }

    public int maxProduct(int[] nums) {
        int max = nums[0];

        int curMax = max;
        int curMin = max;

        for (int i = 1; i < nums.length; i++) {
            if (nums[i] < 0) {

                int temp = curMax;
                curMax = curMin;
                curMin = temp;
            }

            curMax = Math.max(nums[i], nums[i] * curMax);
            curMin = Math.min(nums[i], nums[i] * curMin);

            max = Math.max(curMax, max);
        }
        return max;
    }

    public int calculateMinimumHP(int[][] dungeon) {
        int n = dungeon.length;
        int m = dungeon[0].length;

        int[][] health = new int[n][m];

        health[n - 1][m - 1] = dungeon[n - 1][m - 1] < 0 ? 1 - dungeon[n - 1][m - 1] : 1;

        for (int j = m - 2; j >= 0; j--) {
            int delta = dungeon[n - 1][j];
            if (delta < 0) {
                health[n - 1][j] = health[n - 1][j + 1] - delta;
            } else {
                if (health[n - 1][j + 1] - delta <= 0) {
                    health[n - 1][j] = 1;
                } else {
                    health[n - 1][j] = health[n - 1][j + 1] - delta;
                }
            }
        }

        for (int i = n - 2; i >= 0; i--) {
            int delta = dungeon[i][m - 1];
            if (delta < 0) {
                health[i][m - 1] = health[i + 1][m - 1] - delta;
            } else {
                if (health[i + 1][m - 1] - delta <= 0) {
                    health[i][m - 1] = 1;
                } else {
                    health[i][m - 1] = health[i + 1][m - 1] - delta;
                }
            }
        }


        for (int i = n - 2; i >= 0; i--) {
            for (int j = m - 2; j >= 0; j--) {
                int delta = dungeon[i][j];
                int minHealthNext = Math.min(health[i + 1][j], health[i][j + 1]);
                if (delta < 0) {
                    health[i][j] = minHealthNext - delta;
                } else {
                    if (minHealthNext - delta <= 0) {
                        health[i][j] = 1;
                    } else {
                        health[i][j] = minHealthNext - delta;
                    }
                }
            }
        }
        return health[0][0];
    }

    public int maxProfit3(int k, int[] prices) {
        if (prices.length == 0) {
            return 0;
        }
        int m = prices.length;
        int[][] matrix = new int[k + 1][m];
        for (int j = 0; j < m; j++) {
            matrix[0][j] = 0;
        }

        for (int i = 0; i < k + 1; i++) {
            matrix[i][0] = 0;
        }

        for (int i = 1; i < k + 1; i++) {
            for (int j = 1; j < m; j++) {
                int value = Math.max(0, prices[j] - prices[0]);
                for (int k1 = 1; k1 < j; k1++) {
                    value = Math.max(value, matrix[i - 1][k1 - 1] + prices[j] - prices[k1]);
                }
                matrix[i][j] = Math.max(matrix[i][j - 1], value);
            }
        }
        return matrix[2][m - 1];
    }

    public int rob1(int[] nums) {
        if (nums.length == 1) {
            return nums[0];
        }
        int[] s = new int[nums.length + 1];
        s[0] = 0;
        s[1] = nums[0];
        for (int i = 2; i < nums.length + 1; i++) {
            int moneyThisHome = nums[i - 1];
            s[i] = Math.max(s[i - 1], moneyThisHome + s[i - 2]);
        }
        return s[nums.length];
    }

    public int rob(int[] nums) {
        if (nums.length == 0) {
            return 0;
        }
        if (nums.length == 1) {
            return nums[0];
        }
        if (nums.length == 2) {
            return Math.max(nums[0], nums[1]);
        }

        int[] s1 = new int[nums.length];
        int[] s2 = new int[nums.length];
        s1[0] = 0;
        s2[0] = 0;
        s1[1] = nums[0];
        s2[1] = nums[1];


        for (int i = 2; i < nums.length; i++) {
            int moneyThisHome = nums[i - 1];
            s1[i] = Math.max(s1[i - 1], s1[i - 2] + moneyThisHome);
        }

        for (int i = 2; i < nums.length; i++) {
            int moneyThisHome = nums[i];
            s2[i] = Math.max(s2[i - 1], s2[i - 2] + moneyThisHome);
        }
        return Math.max(s1[nums.length - 1], s2[nums.length - 1]);

    }

    public int maximalSquare(char[][] matrix) {
        int n = matrix.length;
        if (n == 0) {
            return 0;
        }
        int m = matrix[0].length;
        int[][] dp = new int[n][m];
        int max = 0;
        for (int i = 0; i < n; i++) {
            dp[i][m - 1] = matrix[i][m - 1] == '0' ? 0 : 1;
            max = Math.max(max, dp[i][m - 1]);

        }

        for (int j = 0; j < m; j++) {
            dp[n - 1][j] = matrix[n - 1][j] == '0' ? 0 : 1;
            max = Math.max(max, dp[n - 1][j]);
        }

        for (int i = n - 2; i >= 0; i--) {
            for (int j = m - 2; j >= 0; j--) {
                int desc = dp[i + 1][j + 1];
                char cur = matrix[i][j];

                if (cur == '0') {
                    dp[i][j] = 0;
                } else {
                    if (desc == 0) {
                        dp[i][j] = 1;
                    } else {
                        int down = dp[i + 1][j];
                        int right = dp[i][j + 1];

                        int square = Math.min(Math.min(down, right), desc);

                        dp[i][j] = (int) Math.pow((Math.sqrt(square) + 1), 2);

                    }

                }
                max = Math.max(dp[i][j], max);
            }
        }
        return max;
    }

    public int nthUglyNumber(int n) {
        int[] uglyNumbers = new int[n];
        uglyNumbers[0] = 1;
        Queue<Integer> two = new LinkedList<>();
        Queue<Integer> three = new LinkedList<>();
        Queue<Integer> five = new LinkedList<>();

        two.add(1);
        three.add(1);
        five.add(1);

        for (int i = 1; i < n; i++) {
            int n2 = two.peek() * 2;
            int n3 = three.peek() * 3;
            int n5 = five.peek() * 5;

            int num = Math.min(Math.min(n2,n3),n5);
            uglyNumbers[i] = num;
            two.add(num);
            three.add(num);
            five.add(num);


            if(n2 == num){
                two.poll();
            }
            if(n3 == num){
                three.poll();
            }
            if(n5 == num){
                five.poll();
            }
        }
        return uglyNumbers[n-1];
    }

    public int numSquares(int n) {
        int[] s = new int[n+1];
        s[0] = 0;

        for(int i =1; i <= n; i++){
            int min = i;

            for(int j =1; j <= i; j++){
                if(j*j <= i){
                    min = Math.min(min,1+s[i-j*j]);
                }
                else{
                    break;
                }
            }
            s[i] = min;
        }
        return s[n];
    }

    public int lengthOfLIS(int[] nums) {
        int[] tails = new int[nums.length];
        int size = 0;
        for (int x : nums) {
            int i = 0, j = size;
            while (i != j) {
                int m = (i + j) / 2;
                if (tails[m] < x)
                    i = m + 1;
                else
                    j = m;
            }
            tails[i] = x;
            if (i == size) ++size;
        }
        return size;
    }

    public int maxProfit2(int[] prices) {
        if(prices.length <=1){
            return 0;
        }
        int[] s = new int[prices.length+2];
        for(int i=3; i < s.length; i++){
            int priceToday = prices[i-2];
            int maxPrices = 0;
            for(int j = 2; j < i; j++){
                maxPrices = Math.max(maxPrices,priceToday-prices[j-2]+s[j-2]);
            }
            s[i] = Math.max(s[i-1],maxPrices);
        }
        return s[prices.length+1];
    }

    public int maxCoins(int[] nums) {
        List<Integer> newNums = new ArrayList<>();
        for(int n : nums){
            newNums.add(n);
        }
        return maxCoinsHelp(newNums);
    }

    public int maxCoinsHelp(List<Integer> nums){
        if(nums.size() == 1){
            return nums.get(0);
        }

        if(sum.containsKey(nums.toString())){
            return sum.get(nums.toString());
        }

        int max = 0;
        for(int i =0; i < nums.size(); i++){
            int left = 1;
            int right = 1;
            int mid = nums.get(i);
            if(i != 0){
                left = nums.get(i-1);
            }

            if(i != nums.size()-1){
                right = nums.get(i+1);
            }
            nums.remove(i);
            max = Math.max(max,left*right*mid+maxCoinsHelp(nums));
            nums.add(i,mid);
        }
        sum.put(nums.toString(),max);
        return max;
    }

    Map<String,Integer> sum = new HashMap<>();

    public int coinChange(int[] coins, int amount) {
        Arrays.sort(coins);
        int[] s = new int[amount+1];
        Arrays.fill(s,-1);
        s[0] = 0;


        for(int i =1; i < s.length; i++){
            int min = i;
            for(int c : coins){
                if(c > i){
                    break;
                }
                if(s[i-c] == -1){
                    continue;
                }
                min = Math.min(min,1+s[i-c]);
                s[i] = min;
            }

        }
        return s[amount];
    }

    public static void main(String[] args) {
        DP dp = new DP();
        System.out.println(dp.coinChange(new int[]{2},11));
    }

    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }
}

