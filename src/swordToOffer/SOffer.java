package swordToOffer;


import java.util.*;
import java.util.stream.Collectors;

public class SOffer {

    public int cutRope(int target) {
        int[] s = new int[target + 1];

        s[2] = 1;
        s[3] = 2;

        if (target == 2) {
            return 1;
        }
        if (target == 3) {
            return 2;
        }


        for (int i = 4; i <= target; i++) {
            int max = 0;
            for (int j = 2; j <= i / 2; j++) {
                int max1 = Math.max(j * (i - j), j * s[i - j]);
                max = Math.max(max, max1);
            }
            s[i] = max;
        }
        return s[target];
    }


    public int movingCount(int threshold, int rows, int cols) {
        if (threshold < 0) {
            return 0;
        }
        Queue<int[]> q = new LinkedList<>();
        boolean[][] map = new boolean[rows][cols];
        map[0][0] = true;

        int count = 0;
        q.add(new int[]{0, 0});
        while (!q.isEmpty()) {
            int[] position = q.poll();
            count++;
            List<int[]> decendents = getDecendentes(position[0], position[1], threshold, cols, rows, map);
            for (int[] next : decendents) {
                q.add(next);
            }
        }
        return count;
    }

    private List<int[]> getDecendentes(int i, int j, int threshold, int m, int n, boolean[][] map) {
        List<int[]> result = new ArrayList<>();

        if (i + 1 < n && !map[i + 1][j]) {
            map[i + 1][j] = true;
            if (underThreshold(threshold, i + 1, j)) {
                result.add(new int[]{i + 1, j});
            }
        }

        if (i - 1 >= 0 && !map[i - 1][j]) {
            map[i - 1][j] = true;
            if (underThreshold(threshold, i - 1, j)) {
                result.add(new int[]{i - 1, j});
            }
        }

        if (j + 1 < m && !map[i][j + 1]) {
            map[i][j + 1] = true;
            if (underThreshold(threshold, i, j + 1)) {
                result.add(new int[]{i, j + 1});
            }
        }

        if (j - 1 >= 0 && !map[i][j - 1]) {
            map[i][j - 1] = true;
            if (underThreshold(threshold, i, j - 1)) {
                result.add(new int[]{i, j - 1});
            }
        }
        return result;
    }

    private boolean underThreshold(int threshold, int i, int j) {
        String s = Integer.toString(i) + Integer.toString(j);
        char[] c = s.toCharArray();
        int sum = 0;
        for (char ch : c) {
            sum += Integer.parseInt(String.valueOf(ch));
        }
        return sum <= threshold;
    }

    public boolean hasPath(char[] matrix, int rows, int cols, char[] str) {

        Map<Character, List<Integer>> nowway = new HashMap<>();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                boolean[] visited = new boolean[rows * cols];
                boolean res = dfsSearch(matrix, new int[]{i, j}, nowway, rows, cols, new String(str), visited);
                if (res) {
                    return true;
                }
            }
        }

        return false;

    }

    private boolean dfsSearch(char[] matrix, int[] position, Map<Character, List<Integer>> nowway, int rows, int cols, String str, boolean[] visited) {
        if (str.equals("")) {
            return true;
        }
        int i = position[0];
        int j = position[1];
        int index = i * cols + j;

        visited[index] = true;

        if (matrix[index] != str.charAt(0)) {
            return false;
        }
        boolean res = false;


        if (nowway.containsKey(str.charAt(0))) {
            List<Integer> possible = nowway.get(str.charAt(0));
            if (possible.contains(index)) {
                return false;
            }
        }


        if (i - 1 >= 0 && !visited[(i - 1) * cols + j]) {
            res = dfsSearch(matrix, new int[]{i - 1, j}, nowway, rows, cols, str.substring(1), visited);
            if (!res) {
                visited[(i - 1) * cols + j] = false;
            }
        }
        if (j - 1 >= 0 && !visited[(i) * cols + (j - 1)]) {
            res = res || dfsSearch(matrix, new int[]{i, j - 1}, nowway, rows, cols, str.substring(1), visited);
            if (!res) {
                visited[(i) * cols + (j - 1)] = false;
            }
        }
        if (i + 1 < rows && !visited[(i + 1) * cols + j]) {
            res = res || dfsSearch(matrix, new int[]{i + 1, j}, nowway, rows, cols, str.substring(1), visited);
            if (!res) {
                visited[(i + 1) * cols + j] = false;
            }
        }
        if (j + 1 < cols && !visited[(i) * cols + (j + 1)]) {
            res = res || dfsSearch(matrix, new int[]{i, j + 1}, nowway, rows, cols, str.substring(1), visited);
            if (!res) {
                visited[(i) * cols + (j + 1)] = false;
            }
        }

        if (!res) {
            List<Integer> impossible = nowway.getOrDefault(str.charAt(0), new ArrayList<>());
            impossible.add(index);
            nowway.put(str.charAt(0), impossible);
            return false;
        } else {
            return true;
        }
    }

    public boolean hasPath1(String matrix, int rows, int cols, String str) {
        return hasPath(matrix.toCharArray(), rows, cols, str.toCharArray());
    }

    public ArrayList<Integer> maxInWindows(int[] num, int size) {
        ArrayList<Integer> result = new ArrayList<>();
        if (size == 0) {
            return result;
        }
        if (size > num.length) {
            return result;
        }

        List<Integer> temp = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            if (temp.size() == 0) {
                temp.add(num[i]);
            } else {
                while (temp.size() != 0 && temp.get(temp.size() - 1) < num[i]) {
                    temp.remove(temp.size() - 1);
                }
                temp.add(num[i]);
            }
        }
        result.add(temp.get(0));

        for (int i = size; i < num.length; i++) {
            if (num[i - size] == temp.get(0)) {
                temp.remove(0);
            }

            while (temp.size() != 0 && temp.get(temp.size() - 1) < num[i]) {
                temp.remove(temp.size() - 1);
            }
            temp.add(num[i]);
            result.add(temp.get(0));
        }
        return result;
    }


    PriorityQueue<Integer> minQueue = new PriorityQueue<>();
    PriorityQueue<Integer> maxQueue = new PriorityQueue<>(new Comparator<Integer>() {
        @Override
        public int compare(Integer integer, Integer t1) {
            return -(integer - t1);
        }
    });


    public void Insert(Integer num) {
        minQueue.add(num);
        if (minQueue.size() - maxQueue.size() == 2) {
            maxQueue.add(minQueue.poll());
        }
        if (!maxQueue.isEmpty() && maxQueue.peek() > minQueue.peek()) {
            int val1 = maxQueue.poll();
            int val2 = minQueue.poll();
            minQueue.add(val1);
            maxQueue.add(val2);
        }
    }

    public Double GetMedian() {
        if (minQueue.size() == maxQueue.size()) {
            int value1 = minQueue.peek();
            int value2 = maxQueue.peek();
            return ((double) value1 + (double) value2) / 2;
        } else {
            int result = minQueue.peek();
            return (double) result;
        }
    }

    TreeNode KthNode(TreeNode pRoot, int k) {
        List<Integer> inorder = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();
        stack.push(pRoot);
        pRoot = pRoot.left;
        while (true) {
            while (pRoot != null) {
                stack.push(pRoot);
                pRoot = pRoot.left;
            }
            if (stack.isEmpty()) {
                break;
            }
            pRoot = stack.pop();
            inorder.add(pRoot.val);
            if (inorder.size() == k) {
                return pRoot;
            }
            pRoot = pRoot.right;
        }
        return null;
    }


    String Serialize(TreeNode root) {
        String serialization = "";
        Queue<TreeNode> q = new LinkedList<>();
        q.add(root);
        while (!q.isEmpty()) {
            TreeNode node = q.poll();
            if (node == null) {
                serialization = serialization + "#";
                continue;
            } else {
                serialization = serialization + node.val + "!";
                q.add(node.left);
                q.add(node.right);
            }
        }
        return serialization;
    }

    TreeNode Deserialize(String str) {
        int i = 0;
        List<String> processed = new ArrayList<>();

        while (i < str.length()) {
            if (str.charAt(i) == '#') {
                processed.add(String.valueOf(str.charAt(i)));
                i++;
            } else {
                StringBuilder value = new StringBuilder();
                while (Character.isDigit(str.charAt(i))) {
                    value.append(str.charAt(i));
                    i++;
                }
                processed.add(value.toString());
                i++;
            }
        }

        System.out.println(processed);


        if (processed.size() == 1) {
            return null;
        }
        processed.add(0, "");
        TreeNode root = new TreeNode(Integer.parseInt(processed.get(1)));

        TreeNode[] nodes = new TreeNode[processed.size()];
        for (int j = 1; j < processed.size(); j++) {
            if (processed.get(j).equals("#")) {
                nodes[j] = null;
            } else {
                nodes[j] = new TreeNode(Integer.parseInt(processed.get(j)));
            }
        }


        for (int j = 1; 2 * j + 1 < nodes.length; j++) {
            nodes[j].left = nodes[2 * j];
            nodes[j].right = nodes[2 * j + 1];
        }
        return nodes[1];

    }

    ArrayList<ArrayList<Integer>> Print(TreeNode pRoot) {
        ArrayList<ArrayList<Integer>> result = new ArrayList<>();
        Queue<TreeNode> q = new LinkedList();
        q.add(pRoot);
        while (!q.isEmpty()) {
            ArrayList<Integer> temp = new ArrayList<>();
            ArrayList<TreeNode> temp2 = new ArrayList<>();
            while (!q.isEmpty()) {
                TreeNode node = q.poll();
                temp.add(node.val);
                temp2.add(node);
            }
            result.add((ArrayList<Integer>) temp.clone());

            for (TreeNode n : temp2) {
                if (n.left != null) {
                    q.add(n.left);
                }

                if (n.right != null) {
                    q.add(n.right);
                }
            }
        }
        return result;
    }

    public ArrayList<ArrayList<Integer>> ZPrint(TreeNode pRoot) {

        ArrayList<ArrayList<Integer>> result = new ArrayList<>();
        if (pRoot == null) {
            return result;

        }
        Queue<TreeNode> q = new LinkedList<>();
        Stack<TreeNode> s = new Stack<>();

        q.add(pRoot);

        while (!q.isEmpty() || !s.isEmpty()) {
            if (!q.isEmpty()) {
                ArrayList<Integer> temp = new ArrayList<>();
                while (!q.isEmpty()) {
                    TreeNode node = q.poll();
                    temp.add(node.val);
                    if (node.left != null) {
                        s.push(node.left);
                    }

                    if (node.right != null) {
                        s.push(node.right);
                    }
                }
                result.add((ArrayList<Integer>) temp.clone());
            }
            if (!s.isEmpty()) {
                ArrayList<Integer> temp = new ArrayList<>();
                ArrayList<TreeNode> temp2 = new ArrayList<>();
                while (!s.isEmpty()) {
                    TreeNode node = s.pop();
                    temp.add(node.val);
                    temp2.add(0, node);
                }

                for (TreeNode n : temp2) {
                    if (n.left != null) {
                        q.add(n.left);
                    }
                    if (n.right != null) {
                        q.add(n.right);
                    }
                }
                result.add((ArrayList<Integer>) temp.clone());
            }
        }

        return result;
    }

    boolean isSymmetrical(TreeNode pRoot) {
        if (pRoot == null) {
            return false;
        }

        if (pRoot.left == null && pRoot.right == null) {
            return true;
        } else if (pRoot.left == null || pRoot.right == null) {
            return false;
        } else {
            reverseTree(pRoot.right);
            return compareSame(pRoot.left, pRoot.right);
        }

    }

    private void reverseTree(TreeNode root) {
        if (root == null) {
            return;
        }
        TreeNode temp = root.left;
        root.left = root.right;
        root.right = temp;
        reverseTree(root.left);
        reverseTree(root.right);
    }

    private boolean compareSame(TreeNode left, TreeNode right) {
        if (left == null && right == null) {
            return true;
        } else if (left == null || right == null) {
            return false;
        } else {
            if (left.val != right.val) {
                return false;
            } else {
                return compareSame(left.left, right.left) && compareSame(left.right, right.right);
            }
        }
    }

    public ListNode deleteDuplication(ListNode pHead) {
        ListNode fakeHead = new ListNode(0);
        fakeHead.next = pHead;
        ListNode prev = fakeHead;
        ListNode cur = prev.next;

        while (cur != null) {
            if (cur.next == null) {
                break;
            }

            if (cur.val != cur.next.val) {
                prev = cur;
                cur = cur.next;
                continue;
            }

            while (cur.next != null && cur.val == cur.next.val) {
                cur = cur.next;
            }
            prev.next = cur.next;

            cur = prev.next;

        }


        return fakeHead.next;
    }

    Set<Character> wordSet = new HashSet<>();

    List<Character> wordList = new ArrayList<>();

    public void Insert(char ch) {
        if (!wordSet.contains(ch)) {
            wordSet.add(ch);
            wordList.add(ch);
        } else {
            wordList.remove(new Character(ch));
        }
    }

    //return the first appearence once char in current stringstream
    public char FirstAppearingOnce() {
        if (wordList.size() == 0) {
            return '#';
        } else {
            return wordList.get(0);
        }
    }


    public boolean isNumeric(char[] str) {
        if (str.length == 0) {
            return false;
        }

        if (str[0] == '+' || str[0] == '-') {
            int eIndex = -1;
            for (int i = 1; i < str.length; i++) {
                char c = str[i];
                if (Character.isLetter(c)) {
                    if (c == 'e' || c == 'E') {
                        if (eIndex != -1) {
                            return false;
                        } else {
                            eIndex = i;
                        }
                    } else {
                        return false;
                    }
                }
            }
            if (eIndex == -1) {
                return isPositiveNumberNoE(Arrays.copyOfRange(str, 1, str.length));
            } else {
                return isPositiveNumberNoE(Arrays.copyOfRange(str, 1, eIndex)) && isNumberNoDigitAndE(Arrays.copyOfRange(str, eIndex + 1, str.length));
            }
        } else {
            int eIndex = -1;
            for (int i = 0; i < str.length; i++) {
                char c = str[i];
                if (Character.isLetter(c)) {
                    if (c == 'e' || c == 'E') {
                        if (eIndex != -1) {
                            return false;
                        } else {
                            eIndex = i;
                        }
                    } else {
                        return false;
                    }
                }
            }
            if (eIndex == -1) {
                return isPositiveNumberNoE(str);
            } else {
                return isPositiveNumberNoE(Arrays.copyOfRange(str, 0, eIndex)) && isNumberNoDigitAndE(Arrays.copyOfRange(str, eIndex + 1, str.length));
            }
        }
    }

    private boolean isNumberNoDigitAndE(char[] str) {
        if (str.length == 0) {
            return false;
        }
        if (str[0] == '+' || str[0] == '-') {
            for (int i = 1; i < str.length; i++) {
                if (!Character.isDigit(str[i])) {
                    return false;
                }
            }
            return true;
        } else {
            for (char c : str) {
                if (!Character.isDigit(c)) {
                    return false;
                }
            }
            return true;
        }

    }

    private boolean isPositiveNumberNoE(char[] str) {
        if (str.length == 0) {
            return false;
        }
        if (str[str.length - 1] == '.') {
            return false;
        }
        int digitCount = 0;
        for (char c : str) {
            if (c == '+' || c == '-') {
                return false;
            }
            if (c == '.') {
                if (digitCount == 0) {
                    digitCount = 1;
                } else {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isNumeric1(String str) {
        return isNumeric(str.toCharArray());
    }

    public boolean match(char[] str, char[] pattern) {

        if (str.length == 0 && pattern.length == 0) {
            return true;
        } else if (pattern.length == 0) {
            return false;
        }
        int i = 0;
        List<String> rows = new ArrayList<>();
        while (i < pattern.length - 1) {
            if (pattern[i + 1] == '*') {
                String row = "" + pattern[i] + pattern[i + 1];
                rows.add(row);
                i = i + 2;
            } else {
                String row = String.valueOf(pattern[i]);
                rows.add(row);
                i++;
            }
        }
        if (i == pattern.length - 1) {
            rows.add(String.valueOf(pattern[i]));
        }

        int n = rows.size() + 1;
        int m = str.length + 1;
        boolean[][] matrix = new boolean[n][m];

        matrix[n - 1][m - 1] = true;
        for (int j = 0; j < m - 1; j++) {
            matrix[n - 1][j] = false;
        }

        for (i = n - 2; i >= 0; i--) {
            if (rows.get(i).length() == 2) {
                matrix[i][m - 1] = matrix[i + 1][m - 1];
            } else {
                matrix[i][m - 1] = false;
            }
        }


        for (i = n - 2; i >= 0; i--) {
            for (int j = m - 2; j >= 0; j--) {
                String regex = rows.get(i);
                if (regex.length() == 1) {
                    if (regex.charAt(0) == '.') {
                        matrix[i][j] = matrix[i + 1][j + 1];
                    } else {
                        if (regex.charAt(0) == str[j]) {
                            matrix[i][j] = matrix[i + 1][j + 1];
                        } else {
                            matrix[i][j] = false;
                        }
                    }

                } else {
                    if (regex.charAt(0) == '.') {
                        matrix[i][j] = matrix[i + 1][j] || matrix[i + 1][j + 1] || matrix[i][j + 1];
                    } else {
                        if (regex.charAt(0) == str[j]) {
                            matrix[i][j] = matrix[i + 1][j] || matrix[i + 1][j + 1] || matrix[i][j + 1];
                        } else {
                            matrix[i][j] = matrix[i + 1][j];
                        }
                    }
                }
            }
        }

        return matrix[0][0];
    }

    public int[] multiply(int[] A) {
        int[] B = new int[A.length];
        int[] C = new int[A.length];
        B[0] = 1;

        for (int i = 1; i < B.length; i++) {
            B[i] = A[i - 1] * B[i - 1];
        }

        C[A.length - 1] = 1;
        for (int i = C.length - 2; i >= 0; i--) {
            C[i] = C[i + 1] * A[i + 1];
        }

        for (int i = 0; i < B.length; i++) {
            B[i] = B[i] * C[i];
        }
        return B;


    }

    public boolean duplicate(int numbers[], int length, int[] duplication) {
        if (length == 0) {
            return false;
        }
        for (int i = 0; i < numbers.length; i++) {
            int num = numbers[i] % length;
            if (numbers[num] >= length) {
                duplication[0] = num;
                return true;
            } else {
                numbers[num] = numbers[num] + num * length;
            }
        }
        return false;
    }

    public int StrToInt(String str) {
        if (str.length() == 0) {
            return 0;
        }
        int num = 0;
        if (str.charAt(0) == '+' || str.charAt(0) == '-') {
            for (int i = 1; i < str.length(); i++) {
                char c = str.charAt(i);
                if (!Character.isDigit(c)) {
                    return 0;
                } else {
                    num = 10 * num + (c - '0');
                }
            }

        } else {
            for (int i = 0; i < str.length(); i++) {
                char c = str.charAt(i);
                if (!Character.isDigit(c)) {
                    return 0;
                } else {
                    num = 10 * num + (c - '0');
                }
            }
        }
        if (str.charAt(0) == '-') {
            return -num;
        } else {
            return num;
        }
    }

    /*
     * 不用加减乘除实现加法
     */

    public int Add(int num1, int num2) {
        int result = 0;
        int carry = 0;
        do {
            result = num1 ^ num2;       //不带进位的加法
            carry = (num1 & num2) << 1; //进位
            num1 = result;
            num2 = carry;
        } while (carry != 0); // 进位不为0则继续执行加法处理进位
        return result;
    }


    int[] mask = {0x00000000, 0xFFFFFFFF};

    public int Sum_Solution(int n) {
        return production(n + 1, n) >> 1;
    }

    int production(int m, int n) {
        int result = 0;
        boolean isStop = (m != 0) &&
                (result = (n & mask[m & 1]) + production(m >> 1, n << 1)) != 0;
        return result;

    }

    public int LastRemaining_Solution(int n, int m) {
        ListNode prev = new ListNode(-1);
        ListNode fakeHead = prev;
        for (int i = 0; i < n; i++) {
            ListNode current = new ListNode(i);
            prev.next = current;
            prev = current;
        }

        prev.next = fakeHead.next;
        ListNode start = fakeHead.next;

        int count = 0;
        ListNode current = start;

        while (true) {
            count++;
            if (count == m - 1) {
                if (current == current.next.next) {
                    return current.val;
                }
                current.next = current.next.next;
                current = current.next;
                count = 0;
            } else {
                current = current.next;
            }
        }
    }

    public boolean isContinuous(int[] numbers) {
        int zeroCount = 0;
        Set<Integer> duplicate = new HashSet<>();
        int min = Integer.MAX_VALUE;
        int max = 0;

        for (int i : numbers) {
            if (i == 0) {
                zeroCount++;
            } else {
                if (duplicate.contains(i)) {
                    return false;
                } else {
                    duplicate.add(i);
                }
                min = Math.min(i, min);
                max = Math.max(i, max);
            }


        }

        if (max - min + 1 > 5) {
            return false;
        } else {
            return true;
        }
    }

    public String ReverseSentence(String str) {
        if (str.equals(" ")) {
            return "";
        }
        if (str.isEmpty()) {
            return "";
        }
        String[] wordList = str.split(" ");
        int len = wordList.length - 1;
        for (int i = 0; i < wordList.length / 2; i++) {
            String temp = wordList[i];
            wordList[i] = wordList[len - i];
            wordList[len - i] = temp;
        }
        StringBuilder sb = new StringBuilder();
        for (String s : wordList) {
            sb.append(s);
            sb.append(" ");
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    public String LeftRotateString(String str, int n) {
        if (str.isEmpty()) {
            return "";
        }
        n = n % (str.length());
        String s1 = str.substring(0, n);
        String s2 = str.substring(n);
        return s2 + s1;
    }

    public ArrayList<Integer> FindNumbersWithSum(int[] array, int sum) {
        if (array.length == 0 || array.length == 1) {
            return new ArrayList<>();
        }
        int left = 0;
        int right = array.length - 1;
        while (left < right) {
            if (array[left] + array[right] == sum) {
                return new ArrayList<>(Arrays.asList(array[left], array[right]));
            } else if (array[left] + array[right] > sum) {
                right--;
            } else {
                left++;
            }
        }
        return new ArrayList<>();
    }

    public ArrayList<ArrayList<Integer>> FindContinuousSequence(int sum) {
        ArrayList<ArrayList<Integer>> result = new ArrayList<>();
        if (sum == 0) {
            return new ArrayList<>();
        }
        if (sum == 1) {
            ArrayList<Integer> temp = new ArrayList<>(Arrays.asList(1));

            result.add(temp);
            return result;
        }
        int left = 1;
        int right = 2;
        int base = 3;
        while (right < sum) {
            if (base == sum) {
                ArrayList<Integer> temp = new ArrayList<>();
                for (int i = left; i <= right; i++) {
                    temp.add(i);
                }
                result.add(temp);

                base -= left;
                left++;
            } else if (base < sum) {
                right++;
                base += right;
            } else {
                base -= left;
                left++;

            }
        }
        return result;
    }

    public void FindNumsAppearOnce(int[] array, int num1[], int num2[]) {
        Set<Integer> set = new HashSet<>();
        boolean findOne = false;
        for (int i : array) {
            if (set.contains(i)) {
                set.remove(i);
            } else {
                set.add(i);
            }
        }
        for (int i : set) {
            if (findOne) {
                num2[0] = i;

            } else {
                num1[0] = i;
                findOne = true;
            }
        }
    }

    public boolean IsBalanced_Solution(TreeNode root) {
        int delta = maxHeight(root) - minHeight(root);
        return delta <= 1;
    }

    private int minHeight(TreeNode root) {
        if (root == null) {
            return 0;
        } else {
            return 1 + Math.min(minHeight(root.left), minHeight(root.right));
        }
    }

    private int maxHeight(TreeNode root) {
        if (root == null) {
            return 0;
        } else {
            return 1 + Math.max(maxHeight(root.left), maxHeight(root.right));
        }
    }

    public int GetNumberOfK(int[] array, int k) {
        int left = 0;
        int right = array.length - 1;
        int index = -1;
        while (left <= right) {
            int mid = (left + right) / 2;
            if (array[mid] == k) {
                index = mid;
                break;
            } else if (array[mid] < k) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        if (index == -1) {
            return 0;
        }

        int leftK = index;
        int rightK = index;
        while (leftK >= 0 && array[leftK] == k) {
            leftK--;
        }
        if (!(leftK == 0 && array[0] == k)) {
            leftK++;
        }

        while (rightK < array.length && array[rightK] == k) {
            rightK++;
        }
        if (!(rightK == array.length - 1 && array[array.length - 1] == k)) {
            rightK--;
        }
        return rightK - leftK + 1;
    }

    public ListNode FindFirstCommonNode(ListNode pHead1, ListNode pHead2) {
        ListNode p1 = pHead1;
        ListNode p2 = pHead2;
        int count1 = 0;
        int count2 = 0;
        while (p1 != null) {
            count1++;
            p1 = p1.next;
        }

        while (p2 != null) {
            count2++;
            p2 = p2.next;
        }

        if (count1 >= count2) {
            int delta = count1 - count2;
            p1 = pHead1;
            p2 = pHead2;
            for (int i = 0; i < delta; i++) {
                p1 = p1.next;
            }

            while (p1 != null) {
                if (p1 == p2) {
                    return p1;
                } else {
                    p1 = p1.next;
                    p2 = p2.next;
                }
            }
        } else {
            int delta = count2 - count1;
            p1 = pHead1;
            p2 = pHead2;
            for (int i = 0; i < delta; i++) {
                p2 = p2.next;
            }

            while (p1 != null) {
                if (p1 == p2) {
                    return p1;
                } else {
                    p1 = p1.next;
                    p2 = p2.next;
                }
            }
        }
        return null;
    }

    int count = 0;

    public int InversePairs(int[] array) {
        if (array.length == 0) {
            return 0;
        }
        mergeSort(array);
        count %= 1000000007;
        return count;
    }

    private int[] mergeSort(int[] array) {
        if (array.length == 1) {
            return array;
        }
        int mid = array.length / 2;
        int[] array1 = mergeSort(Arrays.copyOfRange(array, 0, mid));
        int[] array2 = mergeSort(Arrays.copyOfRange(array, mid, array.length));
        int[] array3 = merge(array1, array2);
        return array3;
    }


    private int[] merge(int[] array1, int[] array2) {
        int i = 0;
        int j = 0;
        int len1 = array1.length;
        int len2 = array2.length;
        int[] result = new int[len1 + len2];
        int k = 0;
        while (i < len1 && j < len2) {
            if (array2[j] < array1[i]) {
                result[k] = array2[j];
                count += (len1 - i);
                count %= 1000000007;
                k++;
                j++;
            } else {
                result[k] = array1[i];
                k++;
                i++;
            }
        }

        while (i < len1) {
            result[k] = array1[i];
            k++;
            i++;
        }

        while (j < len2) {
            result[k] = array2[j];
            k++;
            j++;
        }
        return result;

    }

    public int FirstNotRepeatingChar(String str) {

        int map[] = new int[52];
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (Character.isUpperCase(c)) {
                int index = c - 'A' + 26;
                map[index]++;
            }

            if (Character.isLowerCase(c)) {
                int index = c - 'a';
                map[index]++;
            }
        }

        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (Character.isUpperCase(c)) {
                int index = c - 'A' + 26;
                if (map[index] == 1) {
                    return i;
                }
            }

            if (Character.isLowerCase(c)) {
                int index = c - 'a';
                if (map[index] == 1) {
                    return i;
                }
            }
        }
        return -1;
    }

    public int GetUglyNumber_Solution(int index) {
        int[] uglyNumberList = new int[index];
        uglyNumberList[0] = 1;
        int two = 0;
        int three = 0;
        int five = 0;

        for (int i = 1; i < index; i++) {
            int min = Math.min(uglyNumberList[two] * 2, Math.min(uglyNumberList[three] * 3, uglyNumberList[five] * 5));
            uglyNumberList[i] = min;
            if (min == uglyNumberList[two] * 2) {
                two++;
            }
            if (min == uglyNumberList[three] * 3) {
                three++;
            }
            if (min == uglyNumberList[five] * 5) {
                five++;
            }
        }
        return uglyNumberList[index - 1];
    }

    public String PrintMinNumber(int[] numbers) {
        StringBuilder res = new StringBuilder();
        PriorityQueue<Integer> q = new PriorityQueue<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                String s1 = String.valueOf(o1);
                String s2 = String.valueOf(o2);

                Integer combine1 = Integer.valueOf(s1 + s2);
                Integer combine2 = Integer.valueOf(s2 + s1);
                return (combine1 - combine2);
            }
        });

        for (int i : numbers) {
            q.add(i);
        }

        while (!q.isEmpty()) {
            Integer a = q.poll();
            res.append(String.valueOf(a));
        }
        return res.toString();
    }


    // 1 到 n之间1的个数

    public int NumberOf1Between1AndN_Solution(int n) {
        if (n <= 0) return 0;
        int count = 0;
        for (int i = 1; i <= n; i *= 10) {
            //计算在第i位上总共有多少个1
            count = count + (n / (10 * i)) * i;
            //不足i的部分有可能存在1
            int mod = n % (10 * i);
            //如果超出2*i -1，则多出的1的个数是固定的
            if (mod > 2 * i - 1) count += i;
            else {
                //只有大于i的时候才能会存在1
                if (mod >= i)
                    count += (mod - i) + 1;
            }
        }
        return count;

    }

    public int FindGreatestSumOfSubArray(int[] array) {
        int[] s = new int[array.length];

        s[0] = array[0];

        for (int i = 1; i < array.length; i++) {
            int max = Math.max(s[i - 1] + array[i], array[i]);
            s[i] = max;
        }
        int max = Integer.MIN_VALUE;
        for (int i : s) {
            max = Math.max(max, i);
        }
        return max;
    }

    public ArrayList<Integer> GetLeastNumbers_Solution(int[] input, int k) {

        ArrayList<Integer> result = new ArrayList<>();
        if (k == 0) {
            return result;
        }
        if (input.length == k) {
            for (int i : input) {
                result.add(i);
            }
            return result;
        }

        if (input.length < k) {
            return result;
        }


        int p = input[0];

        int i = 1;
        int j = input.length - 1;
        while (i <= j) {
            while (i < j && input[i] <= p) {
                i++;
            }
            while (i <= j && input[j] > p) {
                j--;
            }
            int temp = input[i];
            input[i] = input[j];
            input[j] = temp;
        }

        int temp = input[i];
        input[i] = input[j];
        input[j] = temp;

        input[0] = input[j];
        input[j] = p;

        int smallerThanPCount = j;
        if (j + 1 == k) {
            for (int m = 0; m < i; m++) {
                result.add(input[m]);
            }
            return result;
        } else if (j + 1 < k) {
            int newK = k - j - 1;
            for (int m = 0; m < i; m++) {
                result.add(input[m]);
            }
            ArrayList<Integer> extraResult = GetLeastNumbers_Solution(Arrays.copyOfRange(input, i, input.length), newK);
            result.addAll(extraResult);
            return result;
        } else {
            return GetLeastNumbers_Solution(Arrays.copyOfRange(input, 0, j), k);
        }
    }

    public int MoreThanHalfNum_Solution(int[] array) {
        int count = 1;
        int value = array[0];

        for (int i = 1; i < array.length; i++) {
            if (array[i] == value) {
                count++;
            } else {
                count--;
                if (count == 0) {
                    value = array[i];
                    count = 1;
                }
            }
        }
        int newCount = 0;
        for (int i : array) {
            if (i == value) {
                newCount++;
            }
        }
        return newCount > array.length / 2 ? value : 0;
    }

    public ArrayList<String> Permutation(String str) {
        ArrayList<Character> input = new ArrayList<>();
        char[] array = str.toCharArray();
        for (char c : array) {
            input.add(c);
        }
        Collections.sort(input);
        ArrayList<String> result = new ArrayList<>();
        ArrayList<Character> temp = new ArrayList<>();
        if (str.isEmpty()) {
            return result;
        }
        permutationBackTrack(result, temp, input);
        return result;
    }

    public void permutationBackTrack(ArrayList<String> result, ArrayList<Character> temp, ArrayList<Character> input) {
        if (input.size() == 0) {
            StringBuilder sb = new StringBuilder();
            temp.forEach(c -> sb.append(c));
            result.add(sb.toString());
        }
        for (int i = 0; i < input.size(); i++) {
            if (i != 0 && input.get(i) == input.get(i - 1)) {
                continue;
            }
            Character c = input.get(i);
            temp.add(c);
            input.remove(i);
            permutationBackTrack(result, temp, input);
            input.add(i, c);
            temp.remove(temp.size() - 1);
        }

    }

    public TreeNode Convert(TreeNode pRootOfTree) {
        if (pRootOfTree == null) {
            return null;
        }


        Stack<TreeNode> s = new Stack<>();
        List<TreeNode> l = new ArrayList<>();

        TreeNode p = pRootOfTree;
        while (!s.isEmpty() || p != null) {
            while (p != null) {
                s.push(p);
                p = p.left;
            }
            p = s.pop();
            l.add(p);
            p = p.right;
        }
        if (l.size() == 1) {
            return l.get(0);
        }

        for (int i = 1; i < l.size() - 1; i++) {
            l.get(i).right = l.get(i + 1);
            l.get(i).left = l.get(i - 1);
        }
        l.get(0).left = null;
        l.get(0).right = l.get(1);
        l.get(l.size() - 1).right = null;
        l.get(l.size() - 1).left = l.get(l.size() - 2);

        return l.get(0);
    }

    public RandomListNode Clone(RandomListNode pHead) {
        Map<RandomListNode, RandomListNode> map = new HashMap<>();
        RandomListNode p = pHead;
        while (p != null) {
            RandomListNode node = new RandomListNode(p.label);
            map.put(p, node);
            p = p.next;
        }

        p = pHead;
        while (p != null) {
            RandomListNode copy = map.get(p);
            if (p.next != null) {
                copy.next = map.get(p.next);
            }

            if (p.random != null) {
                copy.random = map.get(p.random);
            }
            p = p.next;
        }
        return map.get(pHead);
    }

    public ArrayList<ArrayList<Integer>> FindPath(TreeNode root, int target) {

        ArrayList<ArrayList<Integer>> result = new ArrayList<>();
        if (root == null) {
            return result;
        }
        ArrayList<Integer> temp = new ArrayList<>();
        temp.add(root.val);
        findPathBackTrack(result, temp, root, target - root.val);
        return result;
    }

    private void findPathBackTrack(ArrayList<ArrayList<Integer>> result, ArrayList<Integer> temp, TreeNode root, int sum) {
        if (sum == 0) {
            if (root.left == null && root.right == null) {
                result.add((ArrayList<Integer>) temp.clone());
            }
            return;
        }

        if (sum < 0) {
            return;
        }

        if (root.left != null) {
            temp.add(root.left.val);
            findPathBackTrack(result, temp, root.left, sum - root.left.val);
            temp.remove(temp.size() - 1);
        }

        if (root.right != null) {
            temp.add(root.right.val);
            findPathBackTrack(result, temp, root.right, sum - root.right.val);
            temp.remove(temp.size() - 1);
        }
    }

    public boolean VerifySquenceOfBST(int[] sequence) {
        if (sequence.length == 0) {
            return true;
        }
        return VerifySquenceOfBST1(sequence);
    }

    public boolean VerifySquenceOfBST1(int[] sequence) {
        if (sequence.length == 0) {
            return true;
        }
        if (sequence.length == 1) {
            return true;
        }
        int rootVal = sequence[sequence.length - 1];

        int midIndex = -1;


        for (int i = 0; i < sequence.length - 1; i++) {
            if (sequence[i] > rootVal) {
                midIndex = i;
                break;
            }
        }

        if (midIndex == -1) {
            return VerifySquenceOfBST1(Arrays.copyOfRange(sequence, 0, sequence.length - 1));
        } else {
            for (int i = midIndex; i < sequence.length - 1; i++) {
                if (sequence[i] < rootVal) {
                    return false;
                }
            }
            return VerifySquenceOfBST1(Arrays.copyOfRange(sequence, 0, midIndex)) && VerifySquenceOfBST1(Arrays.copyOfRange(sequence, midIndex, sequence.length - 1));
        }
    }

    public ArrayList<Integer> PrintFromTopToBottom(TreeNode root) {
        Queue<TreeNode> q = new LinkedList<>();
        ArrayList<Integer> result = new ArrayList<>();
        if (root == null) {
            return result;
        }
        q.add(root);
        while (!q.isEmpty()) {
            TreeNode node = q.poll();
            result.add(node.val);
            if (node.left != null) {
                q.add(node.left);
            }
            if (node.right != null) {
                q.add(node.right);
            }
        }
        return result;
    }

    public boolean IsPopOrder(int[] pushA, int[] popA) {
        Stack<Integer> s = new Stack<>();
        int a = 0;
        int b = 0;
        Set<Integer> set = new HashSet<>();

        while (true) {

            while (set.contains(popA[b])) {
                int value = s.pop();
                if (value == popA[b]) {
                    b++;
                } else {
                    return false;
                }
                if (b == popA.length) {
                    return true;
                }
            }

            if (a == pushA.length) {
                return false;
            }
            while (pushA[a] != popA[b]) {
                s.push(pushA[a]);
                set.add(pushA[a]);
                a++;
                if (a == pushA.length) {
                    return false;
                }
            }
            b++;
            a++;
            if (b == popA.length) {
                return true;
            }
        }
    }

    List<Integer> minList = new LinkedList<>();
    List<Integer> dataList = new LinkedList<>();

    public void push(int node) {
        dataList.add(node);
        if (minList.isEmpty()) {
            minList.add(node);

        } else {
            int lastMin = minList.get(minList.size() - 1);
            if (node < lastMin) {
                minList.add(node);
            } else {
                minList.add(lastMin);
            }
        }
    }

    public void pop() {
        dataList.remove(dataList.size() - 1);
        minList.remove(minList.size() - 1);
    }

    public int top() {
        return dataList.get(dataList.size() - 1);
    }

    public int min() {
        return minList.get(minList.size() - 1);
    }

    public ArrayList<Integer> printMatrix(int[][] matrix) {

        ArrayList<Integer> result = new ArrayList<>();
        int n = matrix.length;
        if (n == 0) {
            return result;
        }
        int m = matrix[0].length;

        int i = 0;
        int j = 0;

        int gap = 0;
        while (true) {
            while (j < m - gap) {
                result.add(matrix[i][j]);
                j++;
            }
            if (result.size() == m * n) {
                return result;
            }
            j--;
            i++;

            while (i < n - gap) {
                result.add(matrix[i][j]);
                i++;
            }
            if (result.size() == m * n) {
                return result;
            }
            i--;
            j--;

            while (j >= gap) {
                result.add(matrix[i][j]);
                j--;
            }
            if (result.size() == m * n) {
                return result;
            }
            j++;
            i--;
            gap++;

            while (i >= gap) {
                result.add(matrix[i][j]);
                i--;
            }
            if (result.size() == m * n) {
                return result;
            }
            i++;
            j++;
        }
    }

    public void Mirror(TreeNode root) {
        if (root == null) {
            return;
        }
        TreeNode temp = root.left;
        root.left = root.right;
        root.right = temp;
        Mirror(root.left);
        Mirror(root.right);
    }

    public boolean HasSubtree(TreeNode root1, TreeNode root2) {
        if (root2 == null || root1 == null) {
            return false;
        }
        return isSubTree(root1, root2);
    }

    private boolean isSubTree(TreeNode root1, TreeNode root2) {
        if (root2 == null) {
            return true;
        }

        if (root1 == null) {
            return false;
        }

        if (root1.val == root2.val) {
            return isSubTree(root1.left, root2.left) && isSubTree(root1.right, root2.right) || isSubTree(root1.left, root2) || isSubTree(root1.right, root2);
        } else {
            return isSubTree(root1.left, root2) || isSubTree(root1.right, root2);
        }
    }

    public ListNode Merge(ListNode list1, ListNode list2) {
        ListNode p1 = list1;
        ListNode p2 = list2;
        ListNode fakeHead = new ListNode(0);
        ListNode p = fakeHead;
        while (p1 != null && p2 != null) {
            if (p1.val < p2.val) {
                p.next = p1;
                p1 = p1.next;
            } else {
                p.next = p2;
                p2 = p2.next;
            }
            p = p.next;
        }

        if (p1 != null) {
            p.next = p1;
        }

        if (p2 != null) {
            p.next = p2;
        }

        return fakeHead.next;

    }

    public ListNode ReverseList(ListNode head) {
        ListNode fakeHead = new ListNode(0);
        fakeHead.next = head;
        if (head == null) {
            return head;
        }

        ListNode after = head.next;

        while (after != null) {
            ListNode temp = fakeHead.next;
            fakeHead.next = after;
            head.next = after.next;
            after.next = temp;

            after = head.next;
        }
        return fakeHead.next;
    }

    public ListNode FindKthToTail(ListNode head, int k) {
        ListNode p = head;
        for (int i = 0; i < k; i++) {
            if (p == null) {
                return null;
            }
            p = p.next;
        }

        ListNode p1 = head;
        while(p != null){
            p = p.next;
            p1 = p1.next;

        }
        return p1;
    }

    public double Power(double base, int exponent) {
        if(exponent < 0){
            double newBase = 1/base;
            int newExp = -exponent;
            return Power(newBase,newExp);
        }

        if(exponent == 0){
            return 1;
        }

        if(exponent == 1){
            return base;
        }

        if(exponent % 2 == 1){
            return base * Power(base,exponent-1);
        }
        else{
            return Power(base*base,exponent/2);
        }
    }


    public int NumberOf1(int n) {
        int result = 0;
        while(n != 0){
            result++;
            n = n & (n-1);
        }
        return result;
    }

    public int RectCover(int target) {
        if(target == 0){
            return 0;
        }
        if(target == 1){
            return 1;
        }
        if(target == 2){
            return 2;
        }
        int[] s = new int[target];
        s[0] = 1;
        s[1] = 2;
        for(int i = 2; i < target; i++){
            s[i] = s[i-1] + s[i-2];

        }
        return s[target-1];
    }

    public int JumpFloorII(int target) {
        if(target == 0){
            return 0;
        }

        if(target == 1){
            return 1;
        }

        int[] s = new int[target];
        s[0] = 1;

        for(int i =1; i < target; i ++){
            s[i] = 1;
            for(int j = 0; j < i; j++){
                s[i] += s[j];
            }

        }
        return s[target-1];
    }

    public int JumpFloor(int target) {
        int[] s = new int[target];
        if(target == 0){
            return 0;
        }
        if(target == 1){
            return 1;
        }
        s[0] = 1;
        s[1] = 2;


        for(int i= 2; i < target; i++){
            s[i] = s[i-1] + s[i-2];
        }
        return s[target-1];

    }

    public int minNumberInRotateArray(int [] array) {
        if(array.length == 0){
            return 0;
        }
        int last = array[array.length-1];

        int left = 0;
        int right = array.length-1;

        while (left <= right) {

            int mid = (left + right) /2;
            if(array[mid] > array[mid+1]){
                return array[mid+1];
            }
            else if(array[mid] > last){
                left = mid+1;
            }
            else{
                right = mid-1;
            }
        }

        return array[0];
    }


    Stack<Integer> stack1 = new Stack<Integer>();
    Stack<Integer> stack2 = new Stack<Integer>();

    public void push1(int node) {
        stack1.push(node);
    }

    public int pop1() {
        if(stack2.isEmpty()){
            while (!stack1.isEmpty()){
                stack2.push(stack1.pop());
            }
            return stack2.pop();
        }
        else{
            return stack2.pop();
        }

    }

    public TreeNode reConstructBinaryTree(int [] pre,int [] in) {
        if(pre.length == 0){
            return null;
        }


        int rootVal = pre[0];
        int index = 0;

        for(int i = 0; i < in.length; i ++){
            if(in[i] == rootVal){
                index = i;
                break;
            }
        }


        TreeNode root = new TreeNode(rootVal);

        root.left = reConstructBinaryTree(Arrays.copyOfRange(pre,1,1+index),Arrays.copyOfRange(in,0,index));
        root.right = reConstructBinaryTree(Arrays.copyOfRange(pre,1+index,pre.length),Arrays.copyOfRange(in,1+index,in.length));
        return root;

    }
    public ArrayList<Integer> printListFromTailToHead(ListNode listNode) {
        ArrayList<Integer> result = new ArrayList<>();
        while(listNode != null){
            result.add(listNode.val);
            listNode = listNode.next;
        }
        Collections.reverse(result);
        return result;
    }

    public String replaceSpace(StringBuffer str) {
        StringBuilder sb = new StringBuilder();
        char[] strArray = str.toString().toCharArray();

        for(char c : strArray){
            if(c == ' '){
                sb.append("%20");
            }
            else{
                sb.append(c);
            }
        }
        return sb.toString();
    }


    public boolean Find(int target, int [][] array) {
        if(array.length == 0){
            return false;
        }


        int n = array.length;
        int m = array[0].length;

        int i = 0;
        int j = m-1;
        while(i < n && j >=0){
            if(array[i][j] == target){
                return true;
            }
            else if(array[i][j] < target){
                i++;
            }
            else{
                j--;
            }
        }
        return false;
    }


    public static void main(String[] args) {


        LinkedList<Integer> list = new LinkedList<>();

        SOffer sOffer = new SOffer();
        TreeNode root = new TreeNode(8);
        TreeNode node1 = new TreeNode(8);
        TreeNode node2 = new TreeNode(7);
        TreeNode node3 = new TreeNode(9);

        TreeNode node4 = new TreeNode(3);
        TreeNode node5 = new TreeNode(4);
        TreeNode node6 = new TreeNode(7);


        TreeNode root2 = new TreeNode(8);
        TreeNode node11 = new TreeNode(9);
        TreeNode node22 = new TreeNode(2);
        root2.left = node11;
        root2.right = node22;

        root.left = node1;
        root.right = node2;
        node1.left = node3;
        node1.right = node4;

        node4.left = node5;
        node4.right = node6;




        System.out.println(sOffer.minNumberInRotateArray(new int[]{3,4,5,6,2}));


    }


}

class TreeNode {
    int val = 0;
    TreeNode left = null;
    TreeNode right = null;

    public TreeNode(int val) {
        this.val = val;

    }

}


class TreeLinkNode {
    int val;
    TreeLinkNode left = null;
    TreeLinkNode right = null;
    TreeLinkNode next = null;

    TreeLinkNode(int val) {
        this.val = val;
    }
}


class ListNode {
    int val;
    ListNode next = null;

    ListNode(int val) {
        this.val = val;
    }
}

class RandomListNode {
    int label;
    RandomListNode next = null;
    RandomListNode random = null;

    RandomListNode(int label) {
        this.label = label;
    }
}

