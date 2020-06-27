package array;
import com.sun.source.tree.Tree;
import javafx.util.Pair;

import java.lang.reflect.Array;
import java.rmi.MarshalledObject;
import java.util.*;
class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    TreeNode(int x) { val = x; }
}

public class array {
    public static void main(String[] args){
        array obj = new array();
////        ArrayList<String> a = new ArrayList<>();
////        a.add("hot");
////        a.add("dog");
////        a.add("dot");
//
//
//        TreeNode node1 = new TreeNode(5);
//        TreeNode node2 = new TreeNode(4);
//        TreeNode node3 = new TreeNode(8);
//        TreeNode node4 = new TreeNode(11);
//        TreeNode node5 = new TreeNode(13);
//        TreeNode node6 = new TreeNode(4);
//        TreeNode node7 = new TreeNode(7);
//        TreeNode node8 = new TreeNode(2);
//        TreeNode node9 = new TreeNode(5);
//        TreeNode node10 = new TreeNode(1);
//
//        node1.left = node2;
//        node1.right = node3;
//        node2.left = node4;
//        node3.left = node5;
//        node3.right = node6;
//        node4.left = node7;
//        node4.right = node8;
//        node6.left = node9;
//        node6.right = node10;
//
//        List<String> wordList = new ArrayList<>();
//        wordList.add("hot");
//        wordList.add("dot");
//        wordList.add("dog");
//        wordList.add("lot");
//        wordList.add("log");
//        wordList.add("cog");
//        char[][]board = new char[][]{{'X','X','X','X'},{'X','O','O','X'},{'X','X','O','X'},{'X','O','X','X'}};
        System.out.println(obj.partition("aab"));
    }


    public List<List<Integer>> levelOrderBottom(TreeNode root) {
        ArrayList<Integer> temp = new ArrayList<>();
        Queue<Pair<TreeNode,Integer>> q = new LinkedList<>();
        Pair<TreeNode,Integer> initialNode = new Pair<>(root,0);
        List<List<Integer>> result = new ArrayList<List<Integer>>();
        int lastDepth = 0;
        q.add(initialNode);
        while(!q.isEmpty()){
            Pair<TreeNode,Integer> node = q.remove();
            TreeNode key = node.getKey();
            int depth = node.getValue();
            System.out.println(temp);
            if(lastDepth == depth){
                temp.add(key.val);
            }
            else{
                lastDepth = depth;
                result.add(0,(List<Integer>)temp.clone());
                temp = new ArrayList<Integer>();
                temp.add(key.val);
            }

            if(key.left != null){
                q.add(new Pair<TreeNode,Integer>(key.left,depth+1));
            }
            if(key.right != null){
                q.add(new Pair<TreeNode,Integer>(key.right,depth+1));
            }
        }
        return result;
    }
    public List<List<Integer>> generate(int numRows) {
        List<List<Integer>> result = new ArrayList<List<Integer>>();
        ArrayList<Integer> temp = new ArrayList<>();
        temp.add(1);


        result.add((List<Integer>)temp.clone());


        for(int i =1; i< numRows; i++){
            List<Integer> last = result.get(result.size()-1);
            ArrayList<Integer> line = new ArrayList<>();
            line.add(1);
            for(int j=0; j< last.size()-1; j++){
                line.add(last.get(j) + last.get(j+1));
            }
            line.add(1);
            result.add((List<Integer>)line.clone());
        }
        return result;
    }


    public List<List<String>> findLadders(String beginWord, String endWord, List<String> wordList) {
        if(!wordList.contains(endWord)){
            return new ArrayList<List<String>>();
        }
        List<List<String>> result = new ArrayList<>();
        ArrayList<String> temp = new ArrayList<>();
        temp.add(beginWord);
        backTrack(beginWord,endWord,wordList,result,temp);
        return result;

    }
    public void backTrack(String beginWord, String endWord, List<String>wordList,List<List<String>> result,ArrayList<String> temp){
        if(beginWord.equals(endWord)){
            if(result.size() == 0){
                result.add((List<String>) temp.clone());
            }
            else{
                if(temp.size() == result.get(0).size()){
                    result.add((List<String>) temp.clone());
                }
                else if(temp.size() > result.get(0).size()){
                    return;
                }
                else{
                    result.clear();
                    result.add((List<String>) temp.clone());
                }
            }


            return;
        }

        for(int i=0; i < wordList.size(); i++){
            if(oneDistance(beginWord,wordList.get(i))){
                String word = wordList.get(i);
                wordList.remove(i);
                temp.add(word);
                backTrack(word,endWord,wordList,result,temp);
                temp.remove(temp.size()-1);
                wordList.add(i,word);
            }
        }
    }

    public int maxProduct(int[] nums) {
        int[] n1 = new int[nums.length];
        if(nums.length == 1){
            return nums[0];
        }
        n1[0] = nums[0];
        int max1 = Integer.MIN_VALUE;
        int max2 = Integer.MIN_VALUE;
        for(int i =1; i < nums.length; i++){
            int a = nums[i];


                nums[i] = Math.max(nums[i-1]*a,a);
                max1 = Math.max(max1,nums[i]);
                if(Math.abs(a) > Math.abs(a*n1[i-1])){
                    n1[i] = a;

                }
                else{
                    n1[i] = a*n1[i-1];
                    max2 = Math.max(max2,n1[i]);
                }


        }
        return Math.max(max1,max2);
    }

    public int longestConsecutive(int[] nums) {
        Map<Integer,Integer> map = new HashMap<>();
        int result = 0;
        for(int i=0; i < nums.length; i++){
            int num = nums[i];
            if(map.containsKey(num)){
                continue;
            }
            else{
                int left =0;
                int right = 0;
                if(map.containsKey(num-1)){
                    left = map.get(num-1);
                }
                if(map.containsKey(num+1)){
                    right = map.get(num+1);
                }
                int length = left + right +1;
                result = Math.max(result,length);

                map.put(num-left,length);
                map.put(num,length);
                map.put(num+right,length);

            }

        }
        return result;
    }

    public int findMin(int[] nums) {
        int first = nums[0];
        int left = 0;
        int right = nums.length-1;
        while(left<right){
            if(nums[left] < nums[right]){
                return nums[left];
            }

            int mid = (left + right)/2;
            if(nums[mid]>= nums[left]){
                left = mid +1;
            }
            else{
                right = mid;
            }
        }
        return nums[left];
    }

    public List<List<Integer>> pathSum(TreeNode root, int sum){
        List<List<Integer>> result = new ArrayList<>();
        Stack<Pair<TreeNode,ArrayList<Integer>>> s = new Stack<>();

        Pair<TreeNode,ArrayList<Integer>> initialNode = new Pair<TreeNode,ArrayList<Integer> >(root,new ArrayList<Integer>());
        s.push(initialNode);
        while(!s.isEmpty()){
            Pair<TreeNode,ArrayList<Integer>> node = s.pop();
            TreeNode treeNode = node.getKey();
            ArrayList<Integer> temp = node.getValue();
            temp.add(treeNode.val);
            int currentSum = sumArray(temp);

            if(currentSum == sum && treeNode.left == null && treeNode.right == null){
                result.add(temp);
            }

            if(treeNode.left != null){
                s.push(new Pair<TreeNode, ArrayList<Integer>>(treeNode.left,(ArrayList<Integer>)temp.clone()));
            }
            if(treeNode.right != null){
                s.push(new Pair<TreeNode, ArrayList<Integer>>(treeNode.right,(ArrayList<Integer>)temp.clone()));
            }

        }
        return result;
    }
    public Integer sumArray(List<Integer> array){
        int result = 0;
        for(int i =0; i < array.size(); i++){
            result += array.get(i);
        }
        return result;
    }

    public boolean isPalindrome(String s) {
        s = s.toLowerCase();
        s = s.trim();
        char[] array = s.toCharArray();
        int left = 0;
        int right = array.length-1;
        while(left < right){
            while(!Character.isLetter(array[left])){
                left ++;
            }
            while(!Character.isLetter(array[right])){
                right --;
            }
            char a = array[left];
            char b = array[right];
            if(array[left] != array[right]){
                return false;
            }
            left++;
            right--;
        }
        return true;
    }

    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        Queue<Pair<String,HashSet<String>>> q = new LinkedList<>();
        Pair<String,HashSet<String>> initialNode = new Pair<>(beginWord,new HashSet<String>());

        q.add(initialNode);
        while (! q.isEmpty()){
            Pair<String,HashSet<String>> node = q.remove();
            String word = node.getKey();
            HashSet<String> visited = node.getValue();

            if(word.equals(endWord)){
                return visited.size()+1;
            }
            visited.add(word);

            for(String descWord : wordList){
                if(visited.contains(descWord)){
                    continue;
                }
                if(oneDistance(word,descWord)){
                    q.add(new Pair<String, HashSet<String>>(descWord,(HashSet<String>) visited.clone()));
                }
            }
        }
        return -1;

    }
    public boolean oneDistance(String beginWord, String endWord){
        char[] begin = beginWord.toCharArray();
        char[] end = endWord.toCharArray();
        int count = 0;
        for(int i = 0; i < begin.length; i++){
            if(begin[i] != end[i]){
                count ++;
            }
        }
        return count == 1;
    }

    public int sumNumbers(TreeNode root) {
        if(root == null){
            return 0;
        }
        Stack<Pair<TreeNode,Integer>> s = new Stack<>();
        Pair<TreeNode,Integer> initialNode = new Pair<>(root,0);
        s.push(initialNode);
        Integer result = 0;
        while(!s.isEmpty()){
            Pair<TreeNode,Integer> node = s.pop();
            TreeNode treeNode = node.getKey();
            Integer sum = node.getValue();

            if(treeNode == null){
                continue;
            }

            if(treeNode.left == null && treeNode.right == null){
                result += sum;
                continue;
            }

            sum = 10*sum + treeNode.val;

            if(treeNode.left != null){
                s.push(new Pair<TreeNode, Integer>(treeNode.left,sum));
            }

            if(treeNode.right != null){
                s.push(new Pair<TreeNode, Integer>(treeNode.right,sum));
            }

        }
        return result;
    }

    public void solve(char[][] board) {
        int m = board.length;
        int n = board[0].length;
        for(int j = 0; j < n; j++){
            if (board[0][j] == 'O') {
                unTrackedPoints(0, j, board);
            }
        }
        for(int j =0; j<n;j++){
            if (board[m-1][j] == 'O') {
                unTrackedPoints(m-1, j, board);
            }
        }

        for(int i =0; i<m;i++){
            if (board[i][0] == 'O') {
                unTrackedPoints(i, 0, board);
            }
        }

        for(int i =0; i<m;i++){
            if (board[i][n-1] == 'O') {
                unTrackedPoints(i, n-1, board);
            }
        }

        for(int i =0; i <m; i++){
            for(int j =0; j<n; j++){
                if(board[i][j] == 'O'){
                    board[i][j] = 'X';
                }
                if(board[i][j] == '1') {
                    board[i][j] = 'O';
                }
            }
        }
    }

    public void unTrackedPoints(int i, int j, char[][]board){
        if(i>=0 && i <board.length && j>=0 && j <board[0].length &&  board[i][j] == 'O'){
            board[i][j] = '1';
            unTrackedPoints(i-1,j,board);
            unTrackedPoints(i+1,j,board);
            unTrackedPoints(i,j-1,board);
            unTrackedPoints(i,j+1,board);
        }
        else{
            return;
        }

    }

    public List<List<String>> partition(String s) {
        List<List<String>> result = new ArrayList<>();
        ArrayList<String> temp = new ArrayList<>();
        backTrack1(result,temp,s);
        return result;

    }

    public void backTrack1(List<List<String>> result,ArrayList<String> temp, String s ) {
        if (s.length() == 0) {
            result.add((List<String>) temp.clone());
            return;
        }
        char firstChar = s.charAt(0);
        for (int i = 0; i < s.length(); i++) {
            char curChar = s.charAt(i);
            if (curChar == firstChar) {
                if (judgePa(s.substring(0, i + 1))) {
                    temp.add(s.substring(0,i+1));
                    backTrack1(result,temp,s.substring(i+1));
                    temp.remove(temp.size()-1);
                }
            }
        }
    }
    public boolean judgePa(String candidate) {
        int left = 0;
        int right = candidate.length() - 1;
        while (left < right) {
            if (candidate.charAt(left) != candidate.charAt(right)) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }
}
