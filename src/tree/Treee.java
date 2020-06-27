package tree;

import com.sun.source.tree.Tree;
import javafx.util.Pair;

import java.lang.reflect.Array;
import java.util.*;



class ListNode {
      int val;
      ListNode next;
      ListNode() {}
      ListNode(int val) { this.val = val; }
      ListNode(int val, ListNode next) { this.val = val; this.next = next; }
  }


class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode() {
    }

    TreeNode(int val) {
        this.val = val;
    }

    TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }
}
class NumArray {
    private int[] sums;
    public NumArray(int[] nums) {
        sums = new int[nums.length+1];
        sums[0] = 0;
        for(int i =1; i < sums.length; i++){
            sums[i] = sums[i-1] + nums[i-1];
        }
    }

    public void update(int i, int val){
        int before = sums[i+1] - sums[i];
        int delta = val-before;
        for(int start = i+1; start < sums.length; start++){
            sums[start] += delta;
        }
    }

    public int sumRange(int i, int j) {
        return sums[j+1]-sums[i];
    }
}
class NumMatrix {
    int [][] sums;
    public NumMatrix(int[][] matrix) {
        if(matrix.length == 0){
            sums = new int[1][1];
            sums[0][0] = 0;
            return;
        }
        int n = matrix.length+1;
        int m = matrix[0].length+1;
        sums = new int[n][m];

        for(int i =0; i < n; i++){
            sums[i][0] = 0;
        }
        for(int j = 0; j < m; j++){
            sums[0][j] = 0;
        }

        for(int i =1; i < n; i++){
            for(int j =1; j <m; j++){
                sums[i][j] = sums[i-1][j] + sums[i][j-1] -sums[i-1][j-1] + matrix[i-1][j-1];
            }
        }
    }

    public int sumRegion(int row1, int col1, int row2, int col2) {
        int result = 0;

        result = sums[row2+1][col2+1] + sums[row1][col1] - sums[row1][col2+1]-sums[row2+1][col1];
        return result;
    }
}

class Node {
    public int val;
    public List<Node> children;

    public Node() {
    }

    public Node(int _val) {
        val = _val;
    }

    public Node(int _val, List<Node> _children) {
        val = _val;
        children = _children;
    }
}


class BST {
    public int val;
    public int leftCount;
    public int dup;
    public BST left;
    public BST right;
    public BST(int val){
        this.val = val;
        leftCount = 0;
        dup = 1;
    }
}


public class Treee {


    public int sumOfLeftLeaves(TreeNode root) {
        if (root == null) {
            return 0;
        }
        if (root.left == null) {
            return sumOfLeftLeaves(root.right);
        } else {
            if (root.left.left == null && root.left.right == null) {
                return root.left.val + sumOfLeftLeaves(root.right);
            } else {
                return sumOfLeftLeaves(root.right) + sumOfLeftLeaves(root.left);
            }
        }
    }

    public List<List<Integer>> levelOrder(Node root) {
        Queue<Pair<Node, Integer>> q = new LinkedList<>();
        List<List<Integer>> result = new ArrayList<>();
        q.add(new Pair<>(root, 0));
        int curLevel = 0;
        ArrayList<Integer> temp = new ArrayList<>();
        while (!q.isEmpty()) {
            Pair<Node, Integer> p = q.poll();
            Node node = p.getKey();
            int level = p.getValue();

            if (level == curLevel) {
                temp.add(node.val);
            } else {
                result.add((List<Integer>) temp.clone());
                temp = new ArrayList<>();
                curLevel++;
            }

            for (Node descNode : node.children) {
                q.add(new Pair<>(descNode, level + 1));
            }
        }
        return result;
    }

    public TreeNode deleteNode(TreeNode root, int key) {
        if (root == null) {
            return null;
        }
        if (root.val == key) {
            if (root.right == null) {
                return root.left;
            } else if (root.left == null) {
                return root.right;
            } else {
                TreeNode right = root.right;
                while (right.left != null) {
                    right = right.left;
                }
                right.left = root.left;
                return root.right;
            }
        }

        root.left = deleteNode(root.left, key);
        root.right = deleteNode(root.right, key);
        return root;
    }

    public int[] findFrequentTreeSum(TreeNode root) {
        Map<Integer, Integer> map = new HashMap<>();
        traverse(root, map);
        List<Integer> result = new ArrayList<>();
        int max = 0;
        for (int sum : map.keySet()) {
            int count = map.get(sum);
            if (count > max) {
                result = new ArrayList<>();
                result.add(sum);
                max = count;
            } else if (count == max) {
                result.add(sum);
            }
        }
        int[] list = new int[result.size()];
        int count = 0;
        for (int i : result) {
            list[count] = i;
            count++;
        }
        return list;
    }

    public int traverse(TreeNode root, Map<Integer, Integer> map) {
        if (root == null) {
            return 0;
        }

        int left = traverse(root.left, map);
        int right = traverse(root.right, map);
        int sum = left + right + root.val;

        int count = map.getOrDefault(sum, 0);
        count++;
        map.put(sum, count);
        return sum;
    }


    public int findBottomLeftValue(TreeNode root) {
        return findBottomLeftValue1(root, 0).getKey();
    }

    public Pair<Integer, Integer> findBottomLeftValue1(TreeNode root, int level) {
        if (root == null) {
            return new Pair<>(0, 0);
        }
        if (root.left == null && root.right == null) {
            return new Pair<>(root.val, level);
        }
        Pair<Integer, Integer> rightResult = findBottomLeftValue1(root.right, level + 1);
        Pair<Integer, Integer> leftResult = findBottomLeftValue1(root.left, level + 1);

        if (rightResult.getValue() > leftResult.getValue()) {
            return rightResult;
        } else {
            return leftResult;
        }

    }

    public List<Integer> largestValues(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        Queue<Pair<TreeNode, Integer>> q = new LinkedList<>();
        q.add(new Pair<>(root, 0));
        result.add(root.val);
        while (!q.isEmpty()) {
            Pair<TreeNode, Integer> pair = q.poll();
            TreeNode node = pair.getKey();
            Integer level = pair.getValue();

            if (result.size() == level) {
                result.add(node.val);
            } else {
                int curMax = result.get(level);
                result.set(level, Math.max(curMax, node.val));
            }
            if (node.left != null) {
                q.add(new Pair<>(node.left, level + 1));
            }
            if (node.right != null) {
                q.add(new Pair<>(node.right, level + 1));
            }


        }
        return result;
    }

    public int longestIncreasingPath(int[][] matrix) {
        if (matrix.length == 0) {
            return 0;
        }
        int result = 0;
        Map<Pair<Integer, Integer>, Integer> map = new HashMap<>();
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                result = Math.max(result, DFS1(i, j, map, matrix));
            }
        }
        return result;
    }

    public int DFS1(int i, int j, Map<Pair<Integer, Integer>, Integer> map, int[][] matrix) {
        int m = matrix[0].length;
        int n = matrix.length;
        int result = 0;
        if (map.containsKey(new Pair<>(i, j))) {
            return map.get(new Pair<>(i, j));
        }

        if (i - 1 >= 0) {
            if (matrix[i - 1][j] > matrix[i][j]) {
                result = Math.max(result, DFS1(i - 1, j, map, matrix));
            }
        }

        if (i + 1 < n) {
            if (matrix[i + 1][j] > matrix[i][j]) {
                result = Math.max(result, DFS1(i + 1, j, map, matrix));
            }

        }

        if (j - 1 >= 0) {
            if (matrix[i][j - 1] > matrix[i][j]) {
                result = Math.max(result, DFS1(i, j - 1, map, matrix));
            }

        }

        if (j + 1 < m) {
            if (matrix[i][j + 1] > matrix[i][j]) {
                result = Math.max(result, DFS1(i, j + 1, map, matrix));
            }
        }
        result++;
        map.put(new Pair<>(i, j), result);
        return result;
    }

    public List<String> findItinerary(List<List<String>> tickets) {
        Stack<String> stack = new Stack<>();
        Map<String, List<String>> map = new HashMap<>();
        for (List<String> list1 : tickets) {
            String from = list1.get(0);
            String to = list1.get(1);
            if (map.containsKey(from)) {
                List<String> destinations = map.get(from);
                destinations.add(to);
            } else {
                List<String> destinations = new ArrayList<>();
                destinations.add(to);
                map.put(from, destinations);
            }
        }

        for (String key : map.keySet()) {
            Collections.sort(map.get(key));
        }


        stack.push("JFK");
        List<String> result = new ArrayList<>();

        while (!stack.isEmpty()) {
            String target = stack.peek();
            List<String> destinations = map.getOrDefault(target, new ArrayList<>());
            if (destinations.size() != 0) {
                stack.push(destinations.remove(0));
            } else {
                result.add(0, stack.pop());
            }
        }
        return result;
    }

    public String decodeString(String s) {
        String result = "";
        char[] array = s.toCharArray();
        Stack<String> stack = new Stack<>();

        for (char c : array) {
            if (c != ']') {
                stack.push(Character.toString(c));
            } else {
                String item = stack.pop();
                String subResult = "";
                while (!item.equals("[")) {
                    subResult = item + subResult;
                    item = stack.pop();
                }
                int count = 0;
                int digit = 0;
                String num = stack.pop();
                while (Character.isDigit(num.toCharArray()[0])) {
                    count = count + (int) Math.pow(10, digit) * Integer.parseInt(num);
                    digit++;
                    if (stack.isEmpty()) {
                        break;
                    }
                    num = stack.pop();
                }
                if (!Character.isDigit(num.toCharArray()[0])) {
                    stack.push(num);
                }

                String subsubResult = "";
                for (int i = 0; i < count; i++) {
                    subsubResult = subResult + subsubResult;
                }
                stack.push(subsubResult);
            }
        }

        while (!stack.isEmpty()) {
            String item = stack.pop();
            result = item + result;
        }
        return result;
    }

    public List<List<Integer>> pacificAtlantic(int[][] matrix) {
        List<List<Integer>> result = new ArrayList<>();
        int n = matrix.length;
        int m = matrix[0].length;
        int[][] pacific = new int[n][m];
        int[][] atlantic = new int[n][m];

        for (int i = 0; i < n; i++) {
            pacific[i][0] = 1;
            atlantic[i][m - 1] = 1;
        }

        for (int j = 0; j < m; j++) {
            pacific[0][j] = 1;
            atlantic[n - 1][j] = 1;
        }


        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                DFS2(i, j, matrix, pacific, new HashSet<>());
                DFS2(i, j, matrix, atlantic, new HashSet<>());
                System.out.println();
                printMatrix(atlantic);
                System.out.println();
                printMatrix(pacific);
            }
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (pacific[i][j] == 1 && atlantic[i][j] == 1) {
                    result.add(new ArrayList<>(Arrays.asList(i, j)));
                }
            }
        }
        return result;
    }

    public int DFS2(int i, int j, int[][] matrix, int[][] pacific, Set<Pair<Integer, Integer>> set) {
        if (pacific[i][j] == 1) {
            return pacific[i][j];
        }
        set.add(new Pair<>(i, j));
        int n = matrix.length;
        int m = matrix[0].length;
        int leftResult = 0;
        int rightResult = 0;
        int upResult = 0;
        int downResult = 0;
        if (i - 1 >= 0 && matrix[i - 1][j] <= matrix[i][j] && !set.contains(new Pair<>(i - 1, j))) {
            upResult = DFS2(i - 1, j, matrix, pacific, set);
        }

        if (i + 1 < n && matrix[i + 1][j] <= matrix[i][j] && !set.contains(new Pair<>(i + 1, j))) {
            downResult = DFS2(i + 1, j, matrix, pacific, set);
        }

        if (j - 1 >= 0 && matrix[i][j - 1] <= matrix[i][j] && !set.contains(new Pair<>(i, j - 1))) {
            leftResult = DFS2(i, j - 1, matrix, pacific, set);
        }

        if (j + 1 < m && matrix[i][j + 1] <= matrix[i][j] && !set.contains(new Pair<>(i, j + 1))) {
            rightResult = DFS2(i, j + 1, matrix, pacific, set);
        }

        if (leftResult == 1 || rightResult == 1 || upResult == 1 || downResult == 1) {
            pacific[i][j] = 1;
            return 1;
        } else {
            pacific[i][j] = -1;
            return -1;
        }

    }

    public void printMatrix(int[][] matrix) {
        for (int[] row : matrix) {
            System.out.println(Arrays.toString(row));
        }
    }

    public List<String> findAllConcatenatedWordsInADict(String[] words) {
        Set<String> wordSet = new HashSet<>();
        List<String> result = new ArrayList<>();
        for (String word : words) {
            wordSet.add(word);
        }

        Set<String> success = new HashSet<>();
        Set<String> failed = new HashSet<>();
        for (String word : words) {
            if (DFS3(word, wordSet, success, failed) && success.contains(word)) {
                result.add(word);
            }
        }
        return result;
    }

    public boolean DFS3(String word, Set<String> wordSet, Set<String> success, Set<String> failed) {
        boolean result = false;
        if (word.equals("")) {
            return false;
        }

        if (success.contains(word)) {
            return true;
        }
        if (failed.contains(word)) {
            if (wordSet.contains(word)) {
                return true;
            }
            return false;
        }
        for (int i = 1; i < word.length(); i++) {
            String subString = word.substring(0, i);
            if (wordSet.contains(subString)) {
                result = result || DFS3(word.substring(i), wordSet, success, failed);
            }
        }
        if (result) {
            success.add(word);
            return true;
        } else {
            failed.add(word);
            if (wordSet.contains(word)) {
                return true;
            }
            return false;
        }
    }

    public int firstUniqChar(String s) {
        Map<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            int count = map.getOrDefault(s.charAt(i), 0);
            count++;
            map.put(s.charAt(i), count);
        }

        for (int i = 0; i < s.length(); i++) {
            int count = map.get(s.charAt(i));
            if (count == 1) {
                return i;
            }
        }
        return -1;
    }

    public char findTheDifference(String s, String t) {
        Map<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            int count = map.getOrDefault(s.charAt(i), 0);
            count++;
            map.put(s.charAt(i), count);
        }

        for (int i = 0; i < t.length(); i++) {
            int count = map.getOrDefault(t.charAt(i), 0);
            if (count == 0) {
                return t.charAt(i);
            }

            count--;
            map.put(t.charAt(i), count);
        }
        return 'a';
    }

    public int longestPalindrome(String s) {
        Set<Character> set = new HashSet<>();
        int result = 0;
        char[] array = s.toCharArray();
        for (char c : array) {
            if(set.contains(c)){
                set.remove(c);
                result+=2;
            }
            else{
                set.add(c);
            }

        }

        if(set.size() !=0){
            return ++result;
        }
        else{
            return result;
        }
    }

    public List<Integer> findAnagrams(String s, String p) {
        Map<Character, Integer> map = new HashMap<>();
        List<Integer> result = new ArrayList<>();

        char[] arrayP = p.toCharArray();
        for(char c : arrayP){
            int count = map.getOrDefault(c,0);
            count++;
            map.put(c,count);
        }

        int left = 0;
        int right = p.length()-1;
        for(int i =0; i <=right; i++){
            char c = s.charAt(i);
            int count = map.getOrDefault(c,0);
            count --;
            if(count == 0){
                map.remove(c);
            }
            else{
                map.put(c,count);
            }
        }

        while(right < s.length()){
            if(map.size() == 0){
                result.add(left);
            }
            if(right == s.length()-1){
                break;
            }
            right++;
            char c = s.charAt(left);
            int count = map.getOrDefault(c,0);
            count++;
            if(count == 0){
                map.remove(c);
            }
            else{
                map.put(c,count);
            }
            left++;

            c= s.charAt(right);
            count = map.getOrDefault(c,0);
            count--;
            if(count == 0){
                map.remove(c);
            }
            else{
                map.put(c,count);
            }
        }
        return result;

    }

    public String frequencySort(String s) {
        Map<Character,Integer> map = new HashMap<>();
        String result = "";
        char[] sarray = s.toCharArray();
        for(char c : sarray){
            int count = map.getOrDefault(c,0);
            map.put(c,++count);
        }
        List<Character> subResult = new ArrayList<>(map.keySet());
        Collections.sort(subResult, new Comparator<Character>() {
            @Override
            public int compare(Character character, Character t1) {
                return map.get(character)-map.get(t1);
            }
        });
        for(Character c : subResult){
            int count = map.get(c);
            for(int i =0; i < count; i++){
                result += c;
            }
        }
        return result;
    }

    public List<Integer> findMinHeightTrees(int n, int[][] edges) {
        if(n == 0){
            return new ArrayList<>();
        }

        if(n == 1){
            return new ArrayList<>(Arrays.asList(0));
        }

        Map<Integer,Set<Integer>> degree = new HashMap();

        for(int[] edge : edges){
            Set<Integer> firstDegreeList = degree.getOrDefault(edge[0],new HashSet<>());
            firstDegreeList.add(edge[1]);
            degree.put(edge[0],firstDegreeList);
            Set<Integer> sencondDegreeList = degree.getOrDefault(edge[1],new HashSet<>());
            sencondDegreeList.add(edge[0]);
            degree.put(edge[1],sencondDegreeList);
        }

        while(degree.size() > 2){
            Set<Integer> leaves = new HashSet<>();
            for(int node: degree.keySet()){
                if(degree.get(node).size() == 1){
                    leaves.add(node);
                }
            }

            for(int leave:leaves){
                degree.remove(leave);
            }

            for(int leave:leaves){
                for(int node:degree.keySet()){
                    degree.get(node).remove(leave);
                }
            }
        }
        return new ArrayList<>(degree.keySet());
    }

    public List<Integer> countSmaller(int[] nums) {
        List<Integer> result = new ArrayList<>(Collections.singletonList(0));
        BST root = new BST(nums[nums.length-1]);
        for(int i = nums.length-2; i >=0; i--){
            BST pointer = root;
            int count = 0;
            while(true){
                if(pointer.val == nums[i]){
                    pointer.dup++;
                    count = count + pointer.leftCount;
                    break;
                }
                else if(pointer.val < nums[i]){
                    count = count + pointer.leftCount + pointer.dup;
                    if(pointer.right == null){
                        pointer.right = new BST(nums[i]);
                        break;
                    }
                    else {
                        pointer = pointer.right;
                    }
                }
                else{
                    pointer.leftCount++;
                    if(pointer.left == null){
                        pointer.left = new BST(nums[i]);
                        break;
                    }
                    else{
                        pointer = pointer.left;
                    }
                }
            }
            result.add(0,count);

        }
        return result;
    }

    public int coinChange(int[] coins, int amount) {
        Arrays.sort(coins);
        Stack<int[]> stack = new Stack<>();
        for(int i =0; i < coins.length; i++){
            stack.push(new int[]{coins[i],amount-coins[i],1});
        }
        while(!stack.isEmpty()){
            int[] node = stack.pop();
            int amountleft= node[1];
            int coinCount = node[2];
            if(amountleft == 0){
                return coinCount;
            }
            for(int i =0; i < coins.length; i++){
                int amountleftleft = amountleft - coins[i];
                if(amountleftleft >=0){
                    stack.push(new int[]{coins[i], amountleftleft,coinCount+1});
                }
                else{
                    break;
                }
            }
        }
        return -1;
    }

    public ListNode oddEvenList(ListNode head) {
        ListNode odd = head;
        ListNode even = head.next;
        if(even == null){
            return head;
        }
        ListNode firstEven = even;

        while(even != null && even.next != null ){
            odd.next = even.next;
            even.next = odd.next.next;
            odd.next.next = firstEven;
            odd = odd.next;
            even = even.next;
        }
        return head;
    }

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        Stack<ListNode> stack1 = new Stack<>();
        Stack<ListNode> stack2 = new Stack<>();

        ListNode p1 = l1;
        ListNode p2 = l2;

        while(p1 != null){
            stack1.push(p1);
            p1 = p1.next;
        }
        while(p2 != null){
            stack2.push(p2);
            p2 = p2.next;
        }


        int add = 0;
        while(!stack1.isEmpty() && !stack2.isEmpty()){
            ListNode node1 = stack1.pop();
            ListNode node2 = stack2.pop();
            int result = node1.val + node2.val + add;
            add = result/10;
            result = result % 10;

            node1.val = result;

        }
        if(stack1.isEmpty() && stack2.isEmpty()){
            if(add == 0){
                return l1;
            }
            else{
                ListNode head = new ListNode(add);
                head.next = l1;
                return head;
            }
        }
        else if(stack1.isEmpty()){
            ListNode tail = stack2.peek();
            tail.next = l1;
            while(!stack2.isEmpty()){
                ListNode node = stack2.pop();
                int result = node.val + add;
                add = result/10;
                result = result % 10;

                node.val = result;

            }
            if(add == 0){
                return l2;
            }
            else{
                ListNode head = new ListNode(add);
                head.next = l2;
                return head;
            }

        }
        else{
            while(!stack1.isEmpty()){
                ListNode node = stack1.pop();
                int result = node.val + add;
                add = result/10;
                result = result % 10;

                node.val = result;
            }
            if(add == 0){
                return l1;
            }
            else{
                ListNode head = new ListNode(add);
                head.next = l1;
                return head;
            }
        }
    }

    public ListNode[] splitListToParts(ListNode root, int k) {
        int count = 0;
        ListNode p = root;
        while(p != null){
            p = p.next;
            count++;
        }
        int remainingCount = count;
        int countPerSegment = (int)Math.ceil((double)count/(double)k);
        ListNode[] result = new ListNode[k];
        ListNode pointer = root;
        ListNode pre = pointer;
        for(int i =0; i < k; i++){
            if(pointer == null){
                break;
            }
            result[i] = pointer;
            for(int j =0; j < countPerSegment; j++){
                if(pointer == null){
                    break;
                }
                pre = pointer;
                pointer = pointer.next;
            }
            remainingCount -= countPerSegment;
            countPerSegment = (int)Math.ceil((double)(remainingCount)/(double)(k-i-1));
            pre.next = null;
        }
        return result;
    }


    public int numComponents(ListNode head, int[] G) {
        Map<Integer,Integer> indexToValue = new HashMap<>();
        Map<Integer,Integer> valueToIndex = new HashMap<>();
        int index = 0;

        while(head != null){
            indexToValue.put(index,head.val);
            valueToIndex.put(head.val,index);
            index++;
            head = head.next;
        }
        Set<Integer> set = new HashSet<>();
        for(int n : G){
            set.add(n);
        }
        Set<Integer> visited = new HashSet<>();

        int result = 0;
        for(int value : set){
            if(visited.contains(value)){
                continue;
            }
            result++;
            int i = valueToIndex.get(value);
            int left = i-1;
            int right = i+1;
            while(left >=0){
                int v = indexToValue.get(left);
                if(set.contains(v)){
                    visited.add(v);
                }
                else{
                    break;
                }
                left --;
            }
            while(right < index){
                int v = indexToValue.get(right);
                if(set.contains(v)){
                    visited.add(v);
                }
                else{
                    break;
                }
                right++;
            }
        }


        return result;
    }
    public ListNode middleNode(ListNode head) {
        if(head == null){
            return head;
        }
        if(head.next == null){
            return head;
        }

        ListNode slow = head;
        ListNode fast = head;

        while(true){
            if(fast == null){
                return slow;
            }

            fast = fast.next;

            if(fast == null){
                return slow;
            }
            slow = slow.next;
            fast = fast.next;
        }
    }

    public int[] nextLargerNodes(ListNode head) {

        List<Integer> list = new ArrayList<>();
        while(head != null){
            list.add(head.val);
            head = head.next;
        }
        Stack<Integer> stack = new Stack<>();

        for(int i =0;i < list.size(); i++){
            while(!stack.isEmpty() && list.get(stack.peek()) < list.get(i)){
                list.set(stack.pop(),list.get(i));
            }
            stack.push(i);
        }
        while(!stack.isEmpty()){
            list.set(stack.pop(),0);
        }
        int[] result = new int[list.size()];
        for(int i =0; i < list.size();i++){
            result[i] = list.get(i);
        }
        return result;
    }

    public ListNode removeZeroSumSublists(ListNode head) {
        Stack<ListNode> stack = new Stack<>();
        while(head != null){
            while (head != null && !stack.isEmpty() && stack.peek().val+head.val == 0){
                stack.pop();
                head = head.next;
            }
            if(head == null){
                break;
            }
            stack.push(head);
            head = head.next;
        }
        ListNode pre = null;
        while(!stack.isEmpty()){
            ListNode node = stack.pop();
            node.next = pre;
            pre = node;
        }
        return pre  ;
    }

    public int getDecimalValue(ListNode head) {
        Stack<Integer> stack = new Stack<>();
        while(head != null){
            stack.push(head.val);
            head = head.next;
        }
        int result = 0;
        int count = 0;
        while(!stack.isEmpty()){
            result += (int) ((int)stack.pop()*Math.pow(2,count));
            count ++;
        }
        return result;

    }

    public boolean isSubPath(ListNode head, TreeNode root) {
        if(head == null){
            return true;
        }
        if(root == null){
            return false;
        }
        if(head.val == root.val){
            if(isSubPath(head.next,root.left)|| isSubPath(head.next,root.left)){
                return true;
            }
            else{
                return isSubPath(head,root.left)|| isSubPath(head,root.right);
            }
        }
        else{
            return isSubPath(head,root.left)|| isSubPath(head,root.right);
        }
    }

    public boolean hasValidPath(int[][] grid) {
        int m = grid[0].length;
        int n = grid.length;
        Queue<Pair<Integer,Integer>> q = new LinkedList<>();
        q.add(new Pair<>(0,0));
        Set<Pair<Integer,Integer>> visited = new HashSet<>();
        while(!q.isEmpty()){
            Pair<Integer,Integer> node = q.poll();
            int i = node.getKey();
            int j = node.getValue();
            visited.add(node);

            if(i == n-1 && j == m-1){
                return true;
            }




            if(grid[i][j] ==1){
                if(inGrid(grid,i,j-1) && !visited.contains(new Pair<>(i,j-1)) && canVisit(grid,"left",i,j-1) ){
                    q.add(new Pair<>(i,j-1));
                }
                if(inGrid(grid,i,j+1) && !visited.contains(new Pair<>(i,j+1)) && canVisit(grid,"right",i,j+1)){
                    q.add(new Pair<>(i,j+1));
                }
            }
            else if(grid[i][j] == 2){
                if(inGrid(grid,i-1,j) && !visited.contains(new Pair<>(i-1,j)) && canVisit(grid,"up",i-1,j)){
                    q.add(new Pair<>(i-1,j));
                }
                if(inGrid(grid,i+1,j) && !visited.contains(new Pair<>(i+1,j))&& canVisit(grid,"down",i+1,j)){
                    q.add(new Pair<>(i+1,j));
                }
            }
            else if(grid[i][j] == 3){
                if(inGrid(grid,i,j-1) && !visited.contains(new Pair<>(i,j-1)) && canVisit(grid,"left",i,j-1)){
                    q.add(new Pair<>(i,j-1));
                }
                if(inGrid(grid,i+1,j) && !visited.contains(new Pair<>(i+1,j))&& canVisit(grid,"down",i+1,j)){
                    q.add(new Pair<>(i+1,j));
                }
            }
            else if(grid[i][j] == 4){
                if(inGrid(grid,i,j+1) && !visited.contains(new Pair<>(i,j+1)) && canVisit(grid,"right",i,j+1)){
                    q.add(new Pair<>(i,j+1));
                }
                if(inGrid(grid,i+1,j) && !visited.contains(new Pair<>(i+1,j))&& canVisit(grid,"down",i+1,j)){
                    q.add(new Pair<>(i+1,j));
                }
            }
            else if(grid[i][j] == 5){
                if(inGrid(grid,i-1,j) && !visited.contains(new Pair<>(i-1,j))&& canVisit(grid,"up",i-1,j)){
                    q.add(new Pair<>(i-1,j));
                }
                if(inGrid(grid,i,j-1) && !visited.contains(new Pair<>(i,j-1))&& canVisit(grid,"left",i,j-1)){
                    q.add(new Pair<>(i,j-1));
                }
            }
            else{
                if(inGrid(grid,i-1,j) && !visited.contains(new Pair<>(i-1,j))&& canVisit(grid,"up",i-1,j)){
                    q.add(new Pair<>(i-1,j));
                }
                if(inGrid(grid,i,j+1) && !visited.contains(new Pair<>(i,j+1))&& canVisit(grid,"right",i,j+1)){
                    q.add(new Pair<>(i,j+1));
                }
            }
        }
        return false;
    }
    private boolean inGrid(int[][] grid, int i, int j){
        int n = grid.length;
        int m = grid[0].length;
        return 0 <=i && i < n && 0 <=j && j < m;
    }

    private boolean canVisit(int[][] grid, String direction, int i, int j){
        int value = grid[i][j];
        if(direction.equals("up")){
            return value == 2 || value == 3 || value == 4;
        }
        else if(direction.equals("right")){
            return value ==1 || value == 5 || value ==3;
        }
        else if(direction.equals("left")){
            return value == 1 || value == 4 ||value == 6;
        }
        else{
            return value ==2 || value == 5 || value == 6;
        }
    }



    public char[][] updateBoard(char[][] board, int[] click) {
        int[][] directions = new int[][]{{-1,0},{-1,-1},{0,-1},{1,-1},{1,0},{1,1},{0,1},{-1,1}};
        Queue<int[]> q = new LinkedList<>();
        q.add(click);
        while(!q.isEmpty()){
            int[] thisClick = q.poll();
            int i = thisClick[0];
            int j = thisClick[1];

            if(isMine(board,i,j)){
                board[i][j] = 'X';
                break;
            }
            if(hasMineAround(board,i,j)){
                continue;
            }
            if(board[i][j] == 'E'){
                board[i][j] = 'B';
                for(int[] direction : directions){
                    int newI = i + direction[0];
                    int newJ = j + direction[1];
                    if(inBoard(board,newI,newJ)){
                        if(board[newI][newJ] == 'E'){
                            q.add(new int[]{newI,newJ});
                        }
                    }
                }
            }
        }
        return board;
    }
    private boolean isMine(char[][]board, int i, int j){
        return board[i][j] == 'M';
    }
    private boolean hasMineAround(char[][] board, int i, int j){
        int[][] directions = new int[][]{{-1,0},{-1,-1},{0,-1},{1,-1},{1,0},{1,1},{0,1},{-1,1}};
        int count = 0;
        for(int[] direction : directions){
            int newI = i + direction[0];
            int newJ = j + direction[1];
            if(inBoard(board,newI,newJ)){
                if(isMine(board,newI,newJ)){
                    count++;
                }
            }
        }
        if(count == 0){
            return false;
        }
        else{
            board[i][j] = (char)('0' + count);
            return  true;
        }
    }

    private boolean inBoard(char[][] grid, int i, int j){
        int n = grid.length;
        int m = grid[0].length;
        return 0 <=i && i < n && 0 <=j && j < m;
    }

    public int[][] updateMatrix(int[][] matrix) {
        int n = matrix.length;
        int m = matrix[0].length;
        Queue<int[]> queue = new LinkedList<>();
        for(int i =0; i < n; i++){
            for(int j = 0; j < m; j++){
                if(matrix[i][j] == 0){
                    queue.add(new int[]{i,j});
                }
                else{
                    matrix[i][j] = Integer.MAX_VALUE;
                }
            }
        }
        int[][] directions = new int[][]{{-1,0},{1,0},{0,1},{0,-1}};

        while (!queue.isEmpty()){
            int[] point = queue.poll();
            int i = point[0];
            int j = point[1];

            for(int[] direction : directions){
                int newI = i + direction[0];
                int newJ = j + direction[1];

                int distance = matrix[i][j] +1;
                if(0<= newI && 0 <=newJ && newI < n && newJ<m){
                    if(distance >= matrix[newI][newJ]){
                        continue;
                    }
                    else{
                        matrix[newI][newJ] = distance;
                        queue.add(new int[]{newI,newJ});
                    }
                }
            }
        }
        return matrix;
    }
    public int maxDepth(Node root) {
        if(root == null){
            return 0;
        }
        int result = 0;
        for(Node node : root.children){
            result = Math.max(result,maxDepth(node));
        }
        return result+1;
    }

    public int cutOffTree(List<List<Integer>> forest) {
        PriorityQueue<int[]> priorityQueue = new PriorityQueue<>(new Comparator<int[]>() {
            @Override
            public int compare(int[] ints, int[] t1) {
                int x1 = ints[0];
                int y1 = ints[1];
                int x2 = t1[0];
                int y2 = t1[1];
                return forest.get(x1).get(y1) - forest.get(x2).get(y2);
            }
        });


        int n = forest.size();
        int m = forest.get(0).size();
        for(int i =0; i< n; i++){
            for(int j =0; j < m; j++){
                int value = forest.get(i).get(j);
                if(value > 1){
                    priorityQueue.add(new int[]{i,j});
                }
            }
        }
        int[] start = new int[]{0,0};
        int count = 0;
        while(!priorityQueue.isEmpty()){
            int[] target = priorityQueue.poll();
            int steps = bfs(forest,start,target);
            if(steps == -1){
                return -1;
            }
            count += steps;
            start = target;
        }
        return count;

    }

    private int bfs(List<List<Integer>> forest, int[] start, int[] target){
        int[][] directions = new int[][]{{-1,0},{1,0},{0,1},{0,-1}};

        Queue<int[]> q = new LinkedList<>();
        q.add(new int[]{start[0],start[1],0});
        int n = forest.size();
        int m = forest.get(0).size();
        int[][] visited = new int[n][m];

        while(!q.isEmpty()){
            int[] node = q.poll();
            int i = node[0];
            int j = node[1];
            int count = node[2];
            if(i == target[0] && j == target[1]){
                return count;
            }
            visited[i][j] = 1;
            for(int[] direction : directions) {
                int newI = i + direction[0];
                int newJ = j + direction[1];
                if (0 <= newI && newI < n && 0 <= newJ && newJ < m) {
                    if (visited[newI][newJ] == 0 && forest.get(i).get(j) != 0) {
                        q.add(new int[]{newI, newJ, count + 1});
                    }
                }
            }
        }
        return -1;
    }

    public int getImportance(List<Employee> employees, int id) {

        Map<Integer,Employee> map = new HashMap<>();
        for(Employee employee1 : employees){
            map.put(employee1.id,employee1);
        }
        int count = 0;
        Set<Integer> visited = new HashSet<>();
        Queue<Employee> q = new LinkedList<>();
        q.add(map.get(id));
        while(!q.isEmpty()){
            Employee currentEmployee = q.poll();
            count += currentEmployee.importance;
            visited.add(currentEmployee.id);

            for(Integer nextId : currentEmployee.subordinates){
                if(!visited.contains(nextId)){
                    q.add(map.get(nextId));
                }
            }
        }
        return count;
    }

    public int networkDelayTime(int[][] times, int N, int K) {
        int[][] matrix = new int[N+1][N+1];

        for(int i =0; i < N+1; i++){
            for(int j =0; j < N+1; j++){
                matrix[i][j] = -1;
            }
        }
        for(int[] slot : times){
            int from = slot[0];
            int to = slot[1];
            int latency = slot[2];
            matrix[from][to] = latency;
        }

        Map<Integer,Integer> visited = new HashMap<>();
        int result = 0;
        PriorityQueue<int[]> q = new PriorityQueue<>(new Comparator<int[]>() {
            @Override
            public int compare(int[] ints, int[] t1) {
                return ints[1] - t1[1];
            }
        });
        q.add(new int[]{K,0});
        while(!q.isEmpty()){
            int[] item = q.poll();
            int node = item[0];
            int totalLatency = item[1];

            if(visited.containsKey(node)){
                if(totalLatency >= visited.get(node)){
                    continue;
                }
            }
            visited.put(node,totalLatency);
            result = totalLatency;
            for(int j =1; j < N+1; j++){
                if(matrix[node][j] != -1){
                    q.add(new int[]{j,totalLatency+matrix[node][j]});
                }
            }

        }
        if(visited.keySet().size() != N){
            return -1;
        }
        else{
            return result;
        }
    }

    public static void main(String[] args) {
        Treee treee = new Treee();
        System.out.print(treee.shortestPathLength(new int[][]{{1,2,3},{0},{0},{0}}));
    }

    public int openLock(String[] deadends, String target) {
        Set<String> set = new HashSet<>(Arrays.asList(deadends));
        Set<String> visited = new HashSet<>();

        int[][] directions = new int[][]{{1,0,0,0},{-1,0,0,0},{0,1,0,0},{0,-1,0,0},{0,0,1,0},{0,0,-1,0},{0,0,0,1},{0,0,0,-1}};
        Queue<int[]> q = new LinkedList<>();
        q.add(new int[]{0,0,0,0,0});
        while(! q.isEmpty()){
            int[] item = q.poll();
            int first = item[0];
            int second = item[1];
            int third = item[2];
            int fourth = item[3];
            int moves = item[4];
            String position = String.format("%d%d%d%d",first,second,third,fourth);
            if(position.equals(target)){
                return moves;
            }
            visited.add(position);

            for(int[] direction : directions){
                int newFirst = direction[0]+first;
                if(newFirst == -1){
                    newFirst = 9;
                }
                if(newFirst == 10){
                    newFirst = 0;
                }
                int newSecond = direction[1]+second;
                if(newSecond == -1){
                    newSecond = 9;
                }
                if(newSecond == 10){
                    newSecond = 0;
                }
                int newThird = direction[2]+third;
                if(newThird == -1){
                    newThird = 9;
                }
                if(newThird == 10){
                    newThird = 0;
                }
                int newFourth = direction[3]+fourth;
                if(newFourth == -1){
                    newFourth = 9;
                }
                if(newFourth == 10){
                    newFourth = 0;
                }

                String newPosition = String.format("%d%d%d%d",newFirst,newSecond,newThird,newFourth);
                if(!set.contains(newPosition) && !visited.contains(newPosition)){
                    q.add(new int[]{newFirst,newSecond,newThird,newFourth,moves+1});
                }
            }
        }
        return -1;
    }

    public boolean isBipartite(int[][] graph) {
        Set<Integer> set1 = new HashSet<>();
        Set<Integer> set2 = new HashSet<>();
        int n = graph.length;
        boolean[][] matrix = new boolean[n][n];

        for(int i =0; i < n; i++){
            for(int j : graph[i]){
                matrix[i][j] = true;
            }
        }

        Queue<int[]> q = new LinkedList<>();
        q.add(new int[]{0,1});
        set1.add(0);


        Set<Integer> allNodes = new HashSet<>();
        for(int i =0; i < n; i++){
            allNodes.add(i);
        }
        while(!q.isEmpty()){
            int[] item = q.poll();
            int node = item[0];
            int cls = item[1];
            allNodes.remove(node);
            for(int j = 0; j < n; j ++){
                if(matrix[node][j]){
                    if(cls == 1){
                        if(set1.contains(j)){
                            return false;
                        }
                        if(set2.contains(j)){
                            continue;
                        }
                        else{
                            set2.add(j);
                            q.add(new int[]{j,2});
                        }
                    }
                    else{
                        if(set2.contains(j)){
                            return false;
                        }
                        if(set1.contains(j)){
                            continue;
                        }
                        else{
                            set1.add(j);
                            q.add(new int[]{j,1});
                        }
                    }
                }
            }

            if(q.isEmpty() && !allNodes.isEmpty()){
                int count = 0;
                int newNode = 0;
                for(int nn : allNodes){
                    if(count == 1){
                        break;
                    }
                    count++;
                    newNode = nn;
                }
                q.add(new int[]{newNode,1});
                set1.add(newNode);
            }
        }
        return true;


    }
    public int findCheapestPrice(int n, int[][] flights, int src, int dst, int K) {
        int[][] matrix = new int[n][n];
        for(int i =0; i < n; i ++){
            for(int j =0; j < n; j ++){
                matrix[i][j] = -1;
            }
        }
        for(int[] flight : flights){
            matrix[flight[0]][flight[1]] = flight[2];
        }


        PriorityQueue<int[]> pq = new PriorityQueue<>(new Comparator<int[]>() {
            @Override
            public int compare(int[] ints, int[] t1) {
                return ints[1] - t1[1];
            }
        });

        pq.add(new int[]{src,0,0});

        while(! pq.isEmpty()){
            int[] item = pq.poll();
            int node = item[0];
            int cost = item[1];
            int stop = item[2];
            if(stop > K+1){
                continue;
            }
            if(node == dst){
                return cost;
            }

            for(int j = 0; j < n; j++){
                if(matrix[node][j] != -1){
                    pq.add(new int[]{j,cost+matrix[node][j],stop+1});
                }
            }

        }
        return -1;
    }

    public int numBusesToDestination(int[][] routes, int S, int T) {
        if(S == T){
            return 0;
        }
        Map<Integer,Set<Integer>> map = new HashMap<>();
        for(int i =0; i < routes.length; i++){
            Set<Integer> set = new HashSet<>();
            for(int j : routes[i]){
                set.add(j);
            }
            map.put(i,set);
        }

        Queue<int[]> queue = new LinkedList<>();
        Set<Integer> visited = new HashSet<>();
        Set<Integer> visitedRoute = new HashSet<>();
        for(int route : map.keySet()){
            if(map.get(route).contains(S)){
                if(visitedRoute.contains(route)){
                    continue;
                }
                visitedRoute.add(route);
                Set<Integer> stops = map.get(route);
                for(int stop : stops){
                    if(!visited.contains(stop)){
                        visited.add(stop);
                        queue.add(new int[]{stop,1});

                    }
                }
            }
        }
        while(!queue.isEmpty()){
            int[] node = queue.poll();
            int curStop = node[0];
            int stops = node[1];

            if(curStop == T){
                return stops;
            }

            for(int route : map.keySet()){
                if(map.get(route).contains(curStop)){
                    if(visitedRoute.contains(route)){
                        continue;
                    }
                    visitedRoute.add(route);
                    Set<Integer> stations = map.get(route);
                    for(int s : stations){
                        if(!visited.contains(s)){
                            visited.add(s);
                            queue.add(new int[]{s,stops+1});
                        }
                    }
                }
            }
        }
        return -1;
    }

    public int shortestPathLength(int[][] graph) {
        Queue<Pair<int[],HashSet<Integer>>> q = new LinkedList<>();
        Map<Integer,int[]> map = new HashMap<>();
        for(int i =0; i < graph.length; i++){
            map.put(i,graph[i]);
            q.add(new Pair<>(new int[]{i,0},new HashSet<>()));
        }

        while (!q.isEmpty()){
            Pair<int[],HashSet<Integer>> item = q.poll();
            int[] node = item.getKey();
            HashSet<Integer> curVisited = item.getValue();
            curVisited.add(node[0]);
            if(curVisited.size() == graph.length){
                return node[1];
            }
            for(int nextNode : map.get(node[0])){
                q.add(new Pair<>(new int[]{nextNode,node[1]+1}, (HashSet<Integer>) curVisited.clone()));
            }
        }
        return -1;
    }

    public int kSimilarity(String A, String B) {

        Map<String,Integer> map = new HashMap<>();
        map.put(A,0);
        Queue<String>q = new LinkedList<>();
        q.add(A);

        while(! q.isEmpty()){
            String curStr = q.poll();
            int steps = map.get(curStr);

            if(curStr.equals(B)){
                return steps;
            }

            List<String> decendents = getDecendents(curStr);
            for(String decendent : decendents){
                if(map.keySet().contains(decendent)){
                    continue;
                }
                map.put(decendent,steps+1);
                q.add(decendent);
            }
        }
        return -1;
    }
    private List<String> getDecendents(String A){
        List<String> result = new ArrayList<>();
        char[] a = A.toCharArray();
        for(int i =0; i < a.length; i++){
            for(int j = i+1; j < a.length; j++){
                char temp = a[i];
                a[i] = a[j];
                a[j] = temp;
                String decendent = new String(a);
                result.add(decendent);

                a[j] = a[i];
                a[i] = temp;
            }
        }
        return result;

    }

}

class Employee {
    public int id;
    public int importance;
    public List<Integer> subordinates;
};
