package ontthreesix;

import com.sun.source.tree.Tree;
import javafx.util.Pair;

import java.util.*;

class Node {
    int val;
    Node next;
    Node random;

    public Node(int val) {
        this.val = val;
        this.next = null;
        this.random = null;
    }
}
class TrieNode{
    char c;
    TrieNode[] desc = new TrieNode[27];
    public TrieNode(char c){
        this.c = c;
    }

}
class Trie {
    /** Initialize your data structure here. */
    TrieNode root;
    public Trie() {
        root = new TrieNode('-');
    }

    /** Inserts a word into the trie. */
    public void insert(String word) {
        TrieNode p = root;
        for(char c : word.toCharArray()){
            int index = c-'a';
            if(p.desc[index] == null){
                p.desc[index] = new TrieNode(c);
            }
            p = p.desc[index];
        }
        p.desc[26] = new TrieNode('-');

    }

    /** Returns if the word is in the trie. */
    public boolean search(String word) {
        TrieNode p = root;
        for(char c : word.toCharArray()){
            int index = c-'a';
            if(p.desc[index] == null){
                return false;
            }
            else{
                p = p.desc[index];
            }
        }
        if(p.desc[26] != null){
            return true;
        }
        else{
            return false;
        }
    }

    /** Returns if there is any word in the trie that starts with the given prefix. */
    public boolean startsWith(String prefix) {
        TrieNode p = root;
        for(char c : prefix.toCharArray()){
            int index = c-'a';
            if(p.desc[index] == null){
                return false;
            }
            else{
                p = p.desc[index];
            }
        }
        return true;
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
class BSTIterator {
    List<Integer> result = new ArrayList<>();

    private List<Integer> inOrder(TreeNode root){
        if(root == null){
            return new ArrayList<>();
        }

        List<Integer> left = inOrder(root.left);
        left.add(root.val);
        List<Integer> right = inOrder(root.right);
        left.addAll(right);
        return left;
    }
    public BSTIterator(TreeNode root) {
        result = inOrder(root);
    }

    /** @return the next smallest number */
    public int next() {
        return result.remove(0);
    }

    /** @return whether we have a next smallest number */
    public boolean hasNext() {
        return (result.size() != 0);
    }
}

class ListNode {
    int val;
    ListNode next;

    ListNode() {
    }

    ListNode(int val) {
        this.val = val;
    }

    ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }
}

public class onethreesix {


    public static void main(String[] args) {
        int a = 1;
        char c = (char) ('A' + a);

        TreeNode node1 = new TreeNode(1);
        TreeNode node2 = new TreeNode(2);
        TreeNode node3 = new TreeNode(3);
        TreeNode node4 = new TreeNode(4);
        TreeNode node5 = new TreeNode(5);

        node1.left = node2;
        node1.right = node3;

        node2.right = node5;

        node3.right = node4;

        ListNode n1 = new ListNode(1);
        ListNode n2 = new ListNode(2);
        ListNode n3 = new ListNode(1);

        n1.next = n2;

        onethreesix obj = new onethreesix();
        System.out.println(obj.isPalindrome(n1));


        Trie trie = new Trie();
        trie.insert("app");
        trie.insert("apple");
        trie.insert("beer");
        trie.insert("add");
        trie.insert("jam");
        trie.insert("rental");
        System.out.println(trie.search("apps"));
        System.out.println(trie.search("app")) ;

        obj.moveZeroes(new int[]{0,0,1});

    }

    public Node copyRandomList(Node head) {
        if (head == null) {
            return null;
        }
        Map<Node, Node> map = new HashMap<>();

        Node pointer = head;

        while (pointer != null) {
            map.put(pointer, new Node(pointer.val));
            pointer = pointer.next;
        }

        Node p1 = head;

        while (p1 != null) {
            map.get(p1).next = map.get(p1.next);
            map.get(p1).random = map.get(p1.random);
            p1 = p1.next;
        }
        return map.get(head);
    }

    public boolean wordBreak(String s, List<String> wordDict) {
        if (s.length() == 0) {
            return true;
        }
        if (wordDict.size() == 0) {
            return false;
        }

        for (int i = 0; i < s.length(); i++) {
            if (wordDict.contains(s.substring(0, i + 1))) {
                if (wordBreak(s.substring(i + 1), wordDict)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void reorderList(ListNode head) {
        if (head == null) {
            return;
        }
        ListNode pointer = head;
        Map<Integer, ListNode> map = new HashMap<>();
        int index = 0;
        while (pointer != null) {
            map.put(index, pointer);
            pointer = pointer.next;
            index++;
        }

        int left = 0;
        int right = index - 1;
        ListNode fake = new ListNode(0);
        ListNode result = fake;
        while (left < right) {
            fake.next = map.get(left);
            fake = fake.next;
            fake.next = map.get(right);
            fake = fake.next;
            left++;
            right--;
        }
        if (left == right) {
            fake.next = map.get(left);
        }
    }

    public List<Integer> postorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        Stack<TreeNode> s = new Stack<>();
        s.push(root);

        while(!s.isEmpty()){
            TreeNode node = s.pop();
            result.add(0,node.val);
            if(node.right != null){
                s.push(node.right);
            }
            if(node.left != null){
                s.push(node.left);
            }
        }
        return result;
    }

    public ListNode insertionSortList(ListNode head) {
        ListNode fakeHead= new ListNode(Integer.MIN_VALUE);
        ListNode p = head;
        while(p != null){
            ListNode pointer = fakeHead;
            ListNode temp = p.next;
            while(pointer.next != null){
                if(pointer.next.val > p.val){
                    p.next = pointer.next;
                    pointer.next = p;
                    break;
                }
                pointer = pointer.next;
            }
            if(pointer.next == null){
                pointer.next = p;
                p.next = null;
            }
            p = temp;


        }
        return fakeHead.next;
    }

    public int maxPoints(int[][] points) {
        return 0;
    }


    public int evalRPN(String[] tokens) {
        Stack<Integer> s = new Stack<>();

        for(int i =0; i< tokens.length; i++){
            if(tokens[i].equals("+")){
                Integer firstNum = s.pop();
                Integer secondNum = s.pop();
                Integer result = firstNum + secondNum;
                s.push(result);
            }
            else if(tokens[i].equals("-")){
                Integer firstNum = s.pop();
                Integer secondNum = s.pop();
                Integer result = firstNum - secondNum;
                s.push(result);
            }
            else if (tokens[i].equals("*")){
                Integer firstNum = s.pop();
                Integer secondNum = s.pop();
                Integer result = firstNum * secondNum;
                s.push(result);
            }
            else if (tokens[i].equals("/")){
                Integer firstNum = s.pop();
                Integer secondNum = s.pop();
                Integer result = secondNum/firstNum;
                s.push(result);
            }
            else{
                Integer num = Integer.parseInt(tokens[i]);
                s.push(num);
            }
        }
        Integer finalResult = s.pop();
        return finalResult;
    }

    public String reverseWords(String s) {
        String result = "";
        s.trim();
        String[] temp = s.split(" ");

        for(int i = temp.length-1;i>=0;i--){
            if(temp[i].equals("")){
                continue;
            }
            result += temp[i];
            result +=" ";
        }
        return result.trim();

    }
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        int lenA = listLength(headA);
        int lenB = listLength(headB);

        while (lenA > lenB){
            headA = headA.next;
            lenA--;
        }
        while (lenA < lenB){
            headB = headB.next;
            lenB--;
        }

        while(headA != null){
            if(headA == headB){
                return headA;
            }
            headA = headA.next;
            headB = headB.next;
        }
        return null;
    }
    public int listLength(ListNode head){

        int count = 0;
        while(head != null){
            count +=1;
            head = head.next;
        }
        return count;
    }
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
    public int compareVersion(String version1, String version2) {
        if(version1.equals("") && version2.equals("")){
            return 0;
        }
        else if(version1.equals("")){
            return -1;
        }
        else if(version2.equals("")){
            return 1;
        }

        int index1 = 0;
        int index2 = 0;
        String s1 = "";
        String s2 = "";
        while(index1 < version1.length()){
            if(version1.charAt(index1) == '.'){
                break;
            }
            s1+=Character.toString(version1.charAt(index1));
            index1++;
        }

        while(index2 < version2.length()){
            if(version2.charAt(index2) == '.'){
                break;
            }
            s2+=Character.toString(version2.charAt(index2));
            index2++;
        }

        int num1 = Integer.parseInt(s1);
        int num2 = Integer.parseInt(s2);

        if(num1 > num2){
            return 1;
        }
        else if (num1 < num2){
            return -1;
        }
        else{
            String subVersion1 = "";
            String subVersion2 = "";
            if(version1.length() != index1){
                subVersion1 = version1.substring(index1+1);
            }
            if(version2.length() != index2){
                subVersion2 = version2.substring(index2+1);
            }
            return compareVersion(subVersion1,subVersion2);
        }
    }

    public String convertToTitle(int n) {
        String result = "";
        while(n > 26){
            int num = 27;

            num = n/26;
            n = n % 26;

            if(n ==0){
                num -=1;
                n = 26;
            }

            char c = (char)('A'+ (num-1));
            result += Character.toString((char)c);
        }
        char c = (char)('A'+ (n-1));
        result += Character.toString((char)c);
        return result;
    }
    public int titleToNumber(String s) {
        char[] array = s.toCharArray();
        int result = 0;
        for(int i =0; i < array.length; i++){
            result = result*26 + (array[i]-'A'+1);
        }
        return result;
    }

    public long test(int n){
        int result = 1;
        for(int i =1; i <= n; i++){
            result *= i;
        }
        return result;
    }

    public List<String> findRepeatedDnaSequences(String s) {
        Set<String> visited = new HashSet<>();
        Set<String> meeted = new HashSet<>();
        for(int i =0; i< s.length()-10; i++){
            String subTen = s.substring(i,i+10);
            if(visited.contains(subTen)){
                meeted.add(subTen);
            }
            else{
                visited.add(subTen);
            }
        }
        return new ArrayList<>(meeted);
    }

    public void rotate(int[] nums, int k) {
        if(k == 0){
            return;
        }
        int length = nums.length;
        int pre = nums[length-1];
        for(int i =0; i < nums.length; i++){
            int temp = nums[i];
            nums[i] = pre;
            pre = temp;
        }

        rotate(nums,k-1);
    }


    public int reverseBits(int n) {
        String input= Integer.toString(n);
        String output = "";

        for(int i =input.length()-1; i>=0; i--){
            output += Character.toString(input.charAt(i));
        }
        return Integer.parseInt(output);
    }

    public int rob(int[] nums) {
        int[] s = new int[nums.length+1];
        s[0] = 0;
        s[1] = nums[0];


        for(int i =1; i< nums.length; i++){
            s[i+1] = Math.max(s[i-1]+nums[i],s[i]);

        }
        return s[nums.length+1];
    }

    public List<Integer> rightSideView(TreeNode root) {
        Stack<Pair<Integer, TreeNode>> s = new Stack<>();
        List<Integer> result = new ArrayList<>();

        int height = 0;
        Pair<Integer, TreeNode> initialNode = new Pair<>(height,root);
        s.push(initialNode);
        int resultHeight = -1;
        while(!s.empty()){
            Pair<Integer,TreeNode> node = s.pop();
            int curHeight = node.getKey();
            TreeNode curNode = node.getValue();
            if (curHeight > resultHeight) {
                result.add(curNode.val);
                resultHeight++;
            }
            int descHeight = curHeight + 1;

            if(curNode.left != null){
                s.push(new Pair<>(descHeight,curNode.left));
            }

            if(curNode.right != null){
                s.push(new Pair<>(descHeight,curNode.right));
            }


        }
        return result;
    }


    public ListNode removeElements(ListNode head, int val) {ListNode p = head;
        ListNode pre = head;

        if(head == null){
            return null;
        }

        if(head.next == null){
            if(head.val == val){
                return null;
            }
            else{
                return head;
            }
        }

        p = head.next;

        while(p != null){
            if(p.val == val){
                pre.next = p.next;
                p = pre.next;
            }
            else{
                pre = pre.next;
                p = p .next;
            }


        }
        if(head.val == val){
            return head.next;
        }
        return head;
    }


    public boolean isIsomorphic(String s, String t) {
        if(s.length() != t.length()){
            return false;
        }
        if(s.length() == 0){
            return true;
        }

        if(s.length() == 1){
            return true;
        }

        int i =0;
        while(i < s.length()-1){
            if(s.charAt(i) == s.charAt(i+1)){
                i++;
            }
            else{
                break;
            }
        }
        int j =0;
        while(j < t.length()-1){
            if(t.charAt(j) == t.charAt(j+1)){
                j++;
            }
            else{
                break;
            }
        }
        if(i != j){
            return false;
        }

        return isIsomorphic(s.substring(i+1),t.substring(i+1));


    }

    public ListNode reverseList(ListNode head) {
        if(head == null){
            return null;
        }
        ListNode main = head;
        ListNode pre = head;
        ListNode cur = head.next;

        while(cur != null){
            ListNode temp = cur.next;
            cur.next = main;
            pre.next = temp;
            main = cur;
            cur = pre.next;
        }
        return main;
    }

    public boolean canFinish(int numCourses, int[][] prerequisites) {
        Map<Integer,List<Integer>> map = new HashMap<>();
        Set<Integer>exist = new HashSet<>();
        for(int i = 0; i < numCourses; i++){
            map.put(i,new ArrayList<Integer>());
            exist.add(i);
        }

        for(int i =0; i< prerequisites.length; i++){

            int[] pre = prerequisites[i];
            List<Integer> list = map.get(pre[0]);
            list.add(pre[0]);
            map.put(pre[1],list);
        }

        while(map.size() != 0){
            Set<Integer> degree = new HashSet<>();
            for(int i:exist){
                if(!map.containsKey(i)){
                    continue;
                }

                if (map.get(i).size() == 0){
                    map.remove(i);
                    degree.add(i);
                }
                if(degree.size()==0 && map.size() != 0){
                    return false;
                }

                for(int j : degree){
                    for(int key : map.keySet()){
                        map.get(key).remove(j);
                    }
                }

            }
        }
        return true;

    }

    public int minSubArrayLen(int s, int[] nums) {
        int left =0;
        int right = 0;
        int sum = 0;
        int min = Integer.MAX_VALUE;
        while(right < nums.length){
            sum += nums[right++];
            while(sum >= s){
                min = Math.min(min,right-left);
                sum -= nums[left++];
            }

        }
        return min;
    }

    public boolean isPalindrome(ListNode head) {
        if(head == null){
            return true;
        }
        ListNode slow = head;
        ListNode fast = head;

        while(fast != null){
            fast = fast.next;
            slow= slow.next;
            if(fast == null){
                break;
            }
            fast = fast.next;

        }

        ListNode second = slow;

        ListNode reversedSecond = reverseList(second);

        while(head != null && reversedSecond != null){
            if(head.val != reversedSecond.val){
                return false;
            }
            head = head.next;
            reversedSecond = reversedSecond.next;
        }
        return true;
    }

    public void moveZeroes(int[] nums) {
        int cur = 0;
        int right = nums.length-1;

        while(cur < right){
            if(nums[cur] == 0){

                for(int i = cur+1; i<= right; i++){
                    nums[i-1] = nums[i];
                    nums[i] = 0;
                }
                right --;
            }
            else{
                cur ++;
            }


        }
    }


}

