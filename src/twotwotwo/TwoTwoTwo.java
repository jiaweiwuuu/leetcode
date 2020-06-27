package twotwotwo;

import com.sun.source.tree.Tree;
import javafx.util.Pair;

import java.util.*;


  class ListNode {
      int val;
      ListNode next;
      ListNode(int x) { val = x; }
  }

class MyQueue {
    Stack<Integer>s1;
    Stack<Integer>s2;
    /** Initialize your data structure here. */
    public MyQueue() {
        s1 = new Stack<>();
        s2 = new Stack<>();
    }

    /** Push element x to the back of queue. */
    public void push(int x) {
        while(!s1.isEmpty()){
            s2.push(s1.pop());
        }
        s1.push(x);
        while(!s2.isEmpty()){
            s1.push(s2.pop());
        }
    }

    /** Removes the element from in front of queue and returns that element. */
    public int pop() {
        return s1.pop();
    }

    /** Get the front element. */
    public int peek() {
        return s1.peek();
    }

    /** Returns whether the queue is empty. */
    public boolean empty() {
        return s1.isEmpty();
    }
}
class TrieNode{
    TrieNode[] next;
    String word;
    public TrieNode(){
        next = new TrieNode[26];
    }
}
class WordDictionary {

    TrieNode root;
    /** Initialize your data structure here. */
    public WordDictionary() {
        root = new TrieNode();
    }

    /** Adds a word into the data structure. */
    public void addWord(String word) {
        TrieNode p = root;
        char[] wordArray  = word.toCharArray();
        for(char c : wordArray){
            int index = c-'a';
            if(p.next[index] == null){
                p.next[index] = new TrieNode();
                p = p.next[index];
            }
            else{
                p = p.next[index];
            }
        }
        p.word = word;
    }

    /** Returns if the word is in the data structure. A word could contain the dot character '.' to represent any one letter. */
    public boolean search(String word) {
        return searchTrie(word,root);
    }
    public boolean searchTrie(String word, TrieNode root){
        if(word.equals("")){
            if(root.word != null){
                return true;
            }
            else{
                return false;
            }
        }
        TrieNode p = root;
        char c = word.charAt(0);
        if(c == '.'){
            boolean result = false;
            for(int i =0; i < 26; i++){
                if(result){
                    return true;
                }
                if(p.next[i] != null){
                    result = searchTrie(word.substring(1),p.next[i]);
                }
            }
            return result;
        }
        else{
            int index = c-'a';
            if(p.next[index] == null){
                return false;
            }
            else{
                return searchTrie(word.substring(1),p.next[index]);
            }
        }
    }
}
class MyStack {
    Queue<Integer> q1 = new LinkedList<>();
    Queue<Integer> q2 = new LinkedList<>();
    int cur = 1;
    /** Initialize your data structure here. */
    public MyStack() {

    }

    /** Push element x onto stack. */
    public void push(int x) {
        if(cur == 1){
            q2.add(x);
            while(!q1.isEmpty()){
                q2.add(q1.poll());
            }
            cur = 2;
        }
        else{
            q1.add(x);
            while(!q2.isEmpty()){
                q1.add(q2.poll());
            }
            cur = 1;
        }
    }

    /** Removes the element on top of the stack and returns that element. */
    public int pop() {
        if(cur == 1){
            return q1.poll();
        }
        else{
            return q2.poll();
        }
    }

    /** Get the top element. */
    public int top() {
        if(cur == 1){
            return q1.peek();
        }
        else{
            return q2.peek();
        }
    }

    /** Returns whether the stack is empty. */
    public boolean empty() {
        if(cur == 1){
            return q1.isEmpty();
        }
        else{
            return q2.isEmpty();
        }
    }
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

public class TwoTwoTwo {

    public int countNodes(TreeNode root) {
        int result = 0;
        if(root == null){
            return 0;
        }

        int leftHeight = calHeight(root.left);
        int rightHeight = calHeight(root.right);

        if(leftHeight == rightHeight){
            result = result+(int)Math.pow(2,leftHeight) + countNodes(root.right);
        }
        else{
            result = result+(int)Math.pow(2,rightHeight)+countNodes(root.left);
        }
        return result;
    }
    public int calHeight(TreeNode root){
        int count = 0;
        while(root!= null ){
            count++;
            root = root.left;
        }
        return count;
    }
    public int computeArea(int A, int B, int C, int D, int E, int F, int G, int H) {
        int totalArea = Math.abs(A-C)*Math.abs(B-D) + Math.abs(E-G)*Math.abs(F-H);


        int startWidth1 = Math.min(A,C);
        int endWidth1 = Math.max(A,C);

        int startWidth2 = Math.min(E,G);
        int endWidth2 = Math.max(E,G);

        int startHeight1 = Math.min(B,D);
        int endHeight1 = Math.max(B,D);

        int startHeight2 = Math.min(F,H);
        int endHeight2 = Math.max(F,H);

        int startWidth = Math.max(startWidth1,startWidth2);
        int endWidth = Math.min(endWidth1,endWidth2);

        if(startWidth >= endWidth){
            return totalArea;
        }

        int startHeight = Math.max(startHeight1,startHeight2);
        int endHeight = Math.min(endHeight1,endHeight2);
        if(startHeight >= endHeight){
            return totalArea;
        }

        totalArea = totalArea - (endHeight-startHeight)*(endWidth-startWidth);
        return totalArea;


    }

    public int calculate(String s) {
        Stack<Integer> stack = new Stack<Integer>();
        int result = 0;
        int number = 0;
        int sign = 1;
        for(int i = 0; i < s.length(); i++){
            char c = s.charAt(i);
            if(Character.isDigit(c)){
                number = 10 * number + (int)(c - '0');
            }else if(c == '+'){
                result += sign * number;
                number = 0;
                sign = 1;
            }else if(c == '-'){
                result += sign * number;
                number = 0;
                sign = -1;
            }else if(c == '('){
                //we push the result first, then sign;
                stack.push(result);
                stack.push(sign);
                //reset the sign and result for the value in the parenthesis
                sign = 1;
                result = 0;
            }else if(c == ')'){
                result += sign * number;
                number = 0;
                result *= stack.pop();    //stack.pop() is the sign before the parenthesis
                result += stack.pop();   //stack.pop() now is the result calculated before the parenthesis

            }
        }
        if(number != 0) result += sign * number;
        return result;

    }

    public TreeNode invertTree(TreeNode root) {
        if(root == null){
            return null;
        }

        TreeNode temp = root.left;
        root.left = invertTree(root.right);
        root.right = invertTree(temp);
        return  root;

    }

    public List<String> summaryRanges(int[] nums) {
        int left = 0;
        List<String> result = new ArrayList<>();
        while(left < nums.length){
            int right = left+1;
            while(right < nums.length && nums[right-1] +1 == nums[right]){
                right++;
            }
            String item = "";
            if(right == left+1){
                item = String.format("%d",nums[left]);
            }
            else{
                item = String.format("%d->%d",nums[left],nums[right-1]);
            }

            result.add(item);
            left = right;
        }
        return  result;
    }

    public boolean isPowerOfTwo(int n) {
        return n > 0 && ((n & (n-1)) == 0);
    }

    public boolean dfsSearch(char[][] board, int i, int j , int m, int n, String word, Set<Pair<Integer,Integer>> visited){
        if(word.equals("")){
            return true;
        }
        boolean left = false;
        boolean up = false;
        boolean down = false;
        boolean right = false;
        if(j-1>=0 && !visited.contains(new Pair<>(i,j-1)) && board[i][j-1] == word.charAt(0)){
            visited.add(new Pair<>(i,j-1));
            left = dfsSearch(board,i,j-1,m,n,word.substring(1),visited);
        }

        if(i-1>=0 &&!visited.contains(new Pair<>(i-1,j))&& board[i-1][j] == word.charAt(0)){
            visited.add(new Pair<>(i-1,j));
            up = dfsSearch(board,i-1,j,m,n,word.substring(1),visited);
        }

        if(i+1 < n &&!visited.contains(new Pair<>(i+1,j))&& board[i+1][j] == word.charAt(0)){
            visited.add(new Pair<>(i+1,j));
            down = dfsSearch(board,i+1,j,m,n,word.substring(1),visited);
        }

        if(j +1 < m &&!visited.contains(new Pair<>(i,j+1))&& board[i][j+1] == word.charAt(0)){
            visited.add(new Pair<>(i,j+1));
            right = dfsSearch(board,i,j+1,m,n,word.substring(1),visited);
        }

        return left||right||up||down;

    }

    public List<String> findWords1(char[][] board, String[] words) {
        if(board.length == 0){
            return new ArrayList<>();
        }
        if(board[0].length == 0){
            return new ArrayList<>();
        }
        List<String> result = new ArrayList<>();

        int m = board[0].length;
        int n = board.length;
        for(String word : words){
            boolean added = false;

            for(int i =0; i < n; i++){
                for(int j = 0; j <m; j++){
                    if(board[i][j] == word.charAt(0)){
                        Set<Pair<Integer,Integer>> visited = new HashSet<>();
                        visited.add(new Pair<>(i,j));
                        if(dfsSearch(board,i,j,m,n,word.substring(1),visited)){
                            result.add(word);
                            added = true;
                            break;
                        }
                    }
                }
                if(added){
                    break;
                }
            }
        }
        return result;
    }


    public List<String> findWords(char[][] board, String[] words) {
        TrieNode root = initTrieNode(words);
        List<String> result = new ArrayList<>();
        for(int i =0; i < board.length; i++){
            for(int j =0; j < board[0].length; j++){
                dfs(board,i,j,root,result);
            }
        }
        return result;
    }
    public void dfs(char[][]board,int i, int j,TrieNode root,List<String> result){
        TrieNode p = root;
        char c = board[i][j];
        if(c == '*'){
            return;
        }

        int index = c-'a';
        if(p.next[index] == null){
            return;
        }

        if(p.next[index].word != null){
            result.add(p.next[index].word);
            p.next[index].word = null;
        }

        p = p.next[index];
        board[i][j] = '*';

        if(i-1 >=0){
            dfs(board,i-1,j,p,result);
        }
        if(i+1 < board.length){
            dfs(board,i+1,j,p,result);
        }
        if(j-1 >=0){
            dfs(board,i,j-1,p,result);
        }
        if(j+1 <board[0].length){
            dfs(board,i,j+1,p,result);
        }
        board[i][j] = c;

    }
    public TrieNode initTrieNode(String[] words){
        TrieNode root = new TrieNode();
        for(String word : words){
            buildBranch(root,word);
        }
        return root;
    }
    public void buildBranch(TrieNode root,String word){
        TrieNode p = root;
        char[] wordArray = word.toCharArray();
        for(char c : wordArray){
            int index = c-'a';
            if(p.next[index] == null){
                p.next[index] = new TrieNode();
                p = p.next[index];
            }
            else{
                p = p.next[index];
            }
        }
        p.word = word;
    }


    class TrieNode{
        TrieNode[] next;
        String word;
        public TrieNode(){
            next = new TrieNode[26];
        }
    }


    public int rob(int[] nums) {

        if(nums.length == 1){
            return nums[0];
        }
        if(nums.length == 2){
            return Math.max(nums[0],nums[1]);
        }
        // rob first house


        int[] s=  new int[nums.length];
        s[0] = nums[0];
        s[1] = nums[0];
        for(int i =2;i<nums.length-1;i++){
            s[i] = Math.max(s[i-1],s[i-2]+nums[i]);
        }
        int max1 = s[nums.length-2];


        //no rob first house
        int[] s1 =new int[nums.length];
        s[0] = 0;
        s[1] = nums[1];
        for(int i = 2; i < nums.length; i++){
            s[i] = Math.max(s[i-1],s[i-2]+nums[i]);

        }
        int max2 = s[nums.length-1];
        return Math.max(max1,max2);
    }


    public int findKthLargest(int[] nums, int k) {
        return partition(nums,0,nums.length-1,k);
    }

    public int partition(int[] nums, int start, int end, int k){
        int p = nums[start];
        int i = start+1;
        int j = end;

        while(i <= j){
            while(i <= j && nums[i] < p){
                i++;
            }
            while(i <= j && nums[j] >= p) {
                j--;
            }
            if(i < j){
                int temp = nums[i];
                nums[i] = nums[j];
                nums[j] = temp;
            }

        }
        int numsLarger = end-j+1;
        if(numsLarger == k){
            return p;
        }
        else if(numsLarger > k){
            start = j+1;
            return partition(nums,start,end,k);
        }
        else{
            end = j;
            start++;
            k = k-numsLarger;
            return partition(nums,start,end,k);
        }
    }

    public List<List<Integer>> combinationSum3(int k, int n) {
        List<List<Integer>> result = new ArrayList<>();
        ArrayList<Integer> temp = new ArrayList<>();
        backTrack(temp,result,1,n,k);
        return result;
    }

    public void backTrack(ArrayList<Integer> temp, List<List<Integer>> result, int start,int remainingN, int remainingK){
        if(remainingN == 0){
            if(remainingK != 0){
                return;
            }
            else{
                result.add((ArrayList<Integer>)temp.clone());
                return;
            }
        }

        for(int i = start; i <=9; i++){
            temp.add(i);
            backTrack(temp,result,i+1,remainingN-i,remainingK-1);
            temp.remove(temp.size()-1);
        }

    }

    public boolean containsDuplicate(int[] nums) {
        Set<Integer> set = new HashSet<>();

        for(int i =0; i < nums.length; i++){
            if(!set.add(nums[i])){
                return true;
            }
        }
        return false;
    }



    public TreeNode lowestCommonAncestor1(TreeNode root, TreeNode p, TreeNode q) {
        if(p.val == q.val){
            return p;
        }
        else if(p.val < q.val){
            return lCA(root,p,q);
        }
        else{
            return lCA(root,q,p);
        }
    }

    public TreeNode lCA(TreeNode root, TreeNode p, TreeNode q){


        if(root.val == p.val || root.val ==q.val){
            return root;
        }
        else if(root.val >p.val  && root.val < q.val){
            return root;
        }
        else if(root.val < p.val){
            return lCA(root.right,p,q);
        }
        else{
            return lCA(root.left,p,q);
        }
    }

    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if(root == null || root.val == p.val || root.val == q.val){
            return root;
        }

        TreeNode leftResult = lowestCommonAncestor(root.left, p, q);
        TreeNode rightResult = lowestCommonAncestor(root.right,p,q);

        if(leftResult == null && rightResult == null){
            return null;
        }
        else if (leftResult == null){
            return rightResult;
        }
        else if(rightResult == null){
            return leftResult;
        }
        else{
            return root;
        }
    }

    public int[] productExceptSelf(int[] nums) {
        int product = 1;
        int zeroCount = 0;

        for(int n:nums){
            if(n == 0){
                zeroCount ++;
                continue;

            }
            product = product*n;
        }

        for(int i =0; i < nums.length; i++){
            if(zeroCount == 0){
                nums[i] = product/nums[i];
            }
            else if(zeroCount == 1){
                if(nums[i] == 0){
                    nums[i] = product;
                }
                else{
                    nums[i] = 0;
                }
            }
            else{
                nums[i] = 0;
            }

        }
        return nums;
    }

    public int[] maxSlidingWindow(int[] nums, int k) {
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>();
        int[] result = new int[nums.length-k+1];
        for(int i =0; i < k; i ++){
            priorityQueue.add(-nums[i]);
        }
        int count = 1;

        result[0] = -priorityQueue.peek();

        for(int i =k; i < nums.length; i ++){
            priorityQueue.remove(-nums[i-k]);
            priorityQueue.add(-nums[i]);
            result[count] = -priorityQueue.peek();
            count++;
        }
        return result;
    }

    public boolean searchMatrix(int[][] matrix, int target) {
        if(matrix.length == 0){
            return false;
        }
        int row = 0;
        int column = matrix[0].length-1;
        while(row < matrix.length && column >=0){
            if(matrix[row][column] == target){
                return true;
            }
            else if(matrix[row][column] > target){
                column--;
            }
            else{
                row++;
            }
        }
        return false;
    }

    public boolean isAnagram(String s, String t) {
        if(s.length() != t.length()){
            return false;
        }
        char[] s1 = s.toCharArray();
        char[] t1 = t.toCharArray();
        Map<Character,Integer> map = new HashMap<>();
        for(char c:s1){
            int count = map.getOrDefault(c,0);
            count++;
            map.put(c,count);
        }

        for(char c:t1){
            int count = map.getOrDefault(c,0);
            count--;
            if(count <0){
                return false;
            }
            map.put(c,count);
        }
        return true;
    }
    public List<String> binaryTreePaths(TreeNode root) {
        List<String> result = new ArrayList<>();
        List<Integer> temp = new ArrayList<>();
        backTrack1(root,temp,result);
        return result;
    }

    public void backTrack1(TreeNode root, List<Integer> temp, List<String> result){
        if(root.left == null && root.right == null){
            temp.add(root.val);
            String path = "";
            for(int val : temp){
                path += String.format("->%d",val);
            }
            result.add(path.substring(2));
            temp.remove(temp.size()-1);
            return;
        }

        temp.add(root.val);
        if(root.left != null){
            backTrack1(root.left,temp,result);
        }
        if(root.right != null){
            backTrack1(root.right,temp,result);
        }
        temp.remove(temp.size()-1);
    }

    public int addDigits(int num) {
        if(num < 10){
            return num;
        }
        int result = 0;
        while (num >=10){
            int n = num % 10;
            num = num / 10;
            result += n;
        }
        result += num;
        return addDigits(result);
    }

    public boolean isUgly(int num) {
        if(num == 0){
            return false;
        }
        if(num == 2 || num == 3 || num ==5){
            return true;
        }
        if(num %2 == 0){
            return isUgly(num/2);
        }

        if(num %3 == 0){
            return isUgly(num/3);
        }

        if(num % 5 ==0){
            return isUgly(num/5);
        }
        return false;
    }

    public int missingNumber(int[] nums) {
        int maxNum = nums.length;
        String max = Integer.toString(maxNum);
        int numOfZeros = max.length();
        int addNum = (int)Math.pow(10,numOfZeros);
        for(int i =0; i < nums.length; i++){
            if(nums[i] %addNum == nums.length){
                continue;
            }
            int index = nums[i] %addNum;
            nums[index] = addNum+nums[index];
        }

        for(int i = 0; i < nums.length; i++){
            if(nums[i] < addNum){
                return i;
            }
        }
        return nums.length;
    }

    public String numberToWords(int num) {
        String result = "";
        String[] reflect0 = new String[]{"Zero","One","Two","Three","Four","Five","Six","Seven","Eight","Nine"};
        if(num < 10){
            return reflect0[num];
        }
        int count = 0;
        String[] reflect = new String[]{"","Thousand","Million","Billion","Trillion","Quadrillion","Quintillion"};
        while(num >=1000){
            int n = num % 1000;
            num = num/1000;
            String subResult = parseThree(n);
            if(!subResult.equals("")){
                result = subResult +" "+ reflect[count]+" "+result;
            }

            count++;
        }
        String subResult = parseThree(num);
        result = subResult + " "+reflect[count]+" "+result;
        return result.trim();

    }
    public String parseThree(int num){
        String[] reflect = new String[]{"","One","Two","Three","Four","Five","Six","Seven","Eight","Nine"};
        String subResult = "";
        if(num < 10){
            return reflect[num];
        }
        else if(num < 100){
            return parseTwo(num);
        }
        else{
            int lastTwo = num % 100;
            int firstNum = num /100;

            String result = reflect[firstNum] + " Hundred "+parseTwo(lastTwo);
            return result.trim();
        }
    }
    public String parseTwo(int num){
        String[] reflect0 = new String[]{"","One","Two","Three","Four","Five","Six","Seven","Eight","Nine"};
        String[] reflect1 = new String[]{"Ten","Eleven","Twelve","Thirteen","Fourteen","Fifteen","Sixteen","Seventeen","Eighteen","Nineteen","Twenty"};
        String[] reflect2 = new String[]{"","One","Two","Three","Four","Five","Six","Seven","Eight","Nine"};
        String[] reflect3 = new String[]{"Twenty","Thirty","Forty","Fifty","Sixty","Seventy","Eighty","Ninety"};
        if(num <10){
            return reflect0[num];
        }
        else if(num <= 20){
            return reflect1[num-10];
        }
        else{
            int lastNum = num % 10;
            int firstNum = num/10;

            String result = reflect3[firstNum-2]+" "+reflect2[lastNum];
            return result.trim();
        }
    }


    public int hIndex(int[] citations) {
        int result = 0;
        for(int i =1; i < citations.length; i++){
            if(citations[i] >= result){
                result++;
            }
        }
        return result;
    }


    public boolean isBadVersion(int version){
        if(version >= 1702766719){
            return true;
        }
        else{
            return false;
        }
    }
    public int firstBadVersion(int n) {
        if(isBadVersion(1)){
            return 1;
        }
        int left = 1;
        int right = n;

        while(left <= right){
            System.out.printf("left : %d   right: %d %n",left,right);
            int mid = (int)(((long)left + (long)right)/2);
            if(isBadVersion(mid) && !isBadVersion(mid-1)){
                return mid;
            }
            else if(isBadVersion(mid)){
                right = mid-1;
            }
            else{
                left = mid+1;
            }
        }
        return -1;
    }

    public void gameOfLife(int[][] board) {
        int[][] formerBoard = new int[board.length][board[0].length];

        for(int i =0; i < board.length; i++){
            for(int j =0; j < board[0].length; j++){
                formerBoard[i][j] = board[i][j];
            }
        }

        for(int i =0; i < board.length; i++){
            for(int j =0; j < board[0].length; j++){
                int count = countAround(i,j,formerBoard);
                if(formerBoard[i][j] == 1){
                    if(count <2 || count >3){
                        board[i][j] = 0;
                    }
                }
                else{
                    if(count ==3){
                        board[i][j] = 1;
                    }
                }
            }
        }
    }

    public int countAround(int i, int j, int[][]board){
        int count = 0;
        int n = board.length;
        int m = board[0].length;
        if(i-1 >=0 && j-1 >=0){
            count += board[i-1][j-1];
        }

        if(i-1 >=0 && j >=0){
            count += board[i-1][j];
        }

        if(i-1 >=0 && j+1 < m){
            count += board[i-1][j+1];
        }
        if(i < n && j+1 < m){
            count += board[i][j+1];
        }
        if(i+1 < n && j+1 < m){
            count += board[i+1][j+1];
        }
        if(i+1 < n && j < m){
            count += board[i+1][j];
        }
        if(i +1 < n && j-1 >=0){
            count += board[i+1][j-1];
        }
        if(i >=0 && j-1 >=0){
            count += board[i][j-1];
        }

        return count;

    }

    public boolean wordPattern(String pattern, String str) {
        String[] p = pattern.split("");
        String[] s = str.split(" ");

        Map<String, String> map = new HashMap<>();

        if(p.length != s.length){
            return false;
        }
        if(p.length == 1){
            if(p[0].equals("")){

                return false;
            }
            }
        for(int i =0; i < p.length; i++){
            if(map.containsKey(p[i])){
                if(!map.get(p[i]).equals(s[i])){
                    return false;
                }
            }
            else{
                map.put(p[i],s[i]);
            }
        }
        if(map.keySet().size() != new HashSet<>(map.values()).size()){
            return false;
        }
        return true;
    }

    public static void main(String[] args) {
        TreeNode node1 = new TreeNode(1);
        TreeNode node2 = new TreeNode(2);
        TreeNode node3 = new TreeNode(3);
        TreeNode node4 = new TreeNode(4);
        TreeNode node5 = new TreeNode(5);

        node1.left = node2;
        node1.right = node3;
        node3.left = node4;
        node3.right = node5;

        TwoTwoTwo twoTwoTwo = new TwoTwoTwo();
        System.out.println(twoTwoTwo.height(node1));

        Codec codec = new Codec();
       System.out.println(codec.deserialize(codec.serialize(null)));


    }

    public boolean canWinNim(int n) {
        return((n-1)/4==0 || (n-2)/4 == 0 || (n-3)/4 ==0);
    }

    public int height(TreeNode root){
        if(root == null){
            return 0;
        }

        return Math.max(height(root.left),height(root.right))+1;
    }

    public String getHint(String secret, String guess) {
        Map<Character,Integer> map = new HashMap<>();
        char[] s = secret.toCharArray();
        char[] g = guess.toCharArray();
        for(char c : s){
            int count = map.getOrDefault(c,0);
            count++;
            map.put(c,count);
        }
        int A = 0;
        int B = 0;

        for(int i =0; i < g.length; i++){
            if(g[i] == s[i]){
                A++;
                int count = map.get(g[i]);
                count--;
                map.put(g[i],count);
            }
        }
        for(int i =0; i < g.length; i++){
            if(g[i] == s[i]){
               continue;
            }                    else{
                if(map.containsKey(g[i])){
                    int count = map.get(g[i]);
                    if(count <=0){
                        continue;
                    }
                    else{
                        count--;
                        B++;
                        map.put(g[i],count);
                    }

                }
            }

        }

        return String.format("%dA%dB",A,B);
    }
    public int rob(TreeNode root) {
        if(root == null){
            return 0;
        }
        if (root.left == null && root.right == null) {
            return root.val;
        }
        else if(root.left == null){
            return Math.max((root.val + rob(root.right.left) + rob(root.right.right)),rob(root.right));
        }
        else if(root.right == null){
            return Math.max((root.val + rob(root.left.left) + rob(root.left.right)),rob(root.left));
        }
        else{
            return Math.max((root.val + rob(root.left.left)+rob(root.left.right)+rob(root.right.left)+rob(root.right.right)),rob(root.left)+rob(root.right));
        }
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



}
class MedianFinder {
    private PriorityQueue<Integer> minHeap;
    private PriorityQueue<Integer> maxHeap;
    /** initialize your data structure here. */
    public MedianFinder() {
        minHeap = new PriorityQueue<>();
        maxHeap = new PriorityQueue<>();
    }

    public void addNum(int num) {
        maxHeap.add(num);
        if(maxHeap.size() > minHeap.size()){
            minHeap.add(-maxHeap.poll());
        }
        if(minHeap.size() > maxHeap.size()){
            maxHeap.add(-minHeap.poll());
        }
    }

    public double findMedian() {
        if(maxHeap.size() == minHeap.size()){
            double num1 = (double)maxHeap.peek();
            double num2 = -(double)minHeap.peek();
            return (num1+num2)/2;
        }
        else{
            return maxHeap.peek();
        }
    }
}



class Codec {
    private int height(TreeNode root){
        if(root == null){
            return 0;
        }

        return Math.max(height(root.left),height(root.right))+1;
    }
    private void levelOrder(TreeNode root,String[] result, int count){
        if(root == null){
            return;
        }
        result[count] = Integer.toString(root.val);

        levelOrder(root.left,result,count*2);
        levelOrder(root.right,result,count*2+1);
    }
    // Encodes a tree to a single string.
    public String serialize(TreeNode root) {
        int height = height(root);
        int slots = (int)Math.pow(2,height);
        String [] treeArray = new String[slots];
        Arrays.fill(treeArray,"null");

        levelOrder(root,treeArray,1);
        String [] newArray = Arrays.copyOfRange(treeArray,1,treeArray.length);
        String result = Arrays.toString(newArray);
        return result;


    }
    private TreeNode constructTree(int index, String[] treeArray){

        if(index >= treeArray.length){
            return null;
        }
        if(treeArray[index].equals("")){
            return null;
        }
        if(treeArray[index].trim().equals("null")){
            return null;
        }
        else{
            TreeNode node = new TreeNode(Integer.parseInt(treeArray[index].trim()));
            node.left = constructTree(index*2,treeArray);
            node.right = constructTree(index*2+1,treeArray);
            return node;
        }

    }
    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {
        String treeData = data.substring(1,data.length()-1);

        String[] newArray = treeData.split(",");

        String[] treeArray = new String[newArray.length+1];
        treeArray[0] = null;
        for(int i =1; i < treeArray.length; i++){
            treeArray[i] = newArray[i-1];
        }

        TreeNode root = constructTree(1,treeArray);
        return root;


    }



}
