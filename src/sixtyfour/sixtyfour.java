package sixtyfour;

import javafx.util.Pair;

import java.lang.reflect.Array;
import java.util.*;
import java.util.Stack;
class ListNode {
     int val;
     ListNode next;
     ListNode(int x) { val = x; }
 }
public class sixtyfour {
    public static void main(String[] args) {
        String s1 = "aabcc";
        String s2 = "dbbca";
        String s3 = "aadbbcbcac";
        System.out.println(inInterleave(s1,s2,s3));
    }

    public static int minPathSum(int[][] grid) {
        int m = grid.length;
        if (m == 0) {
            return 0;
        }
        int n = grid[0].length;

        int[][] map = new int[m][n];
        map[0][0] = grid[0][0];

        for (int j = 1; j < n; j++) {
            map[0][j] = map[0][j - 1] + grid[0][j];
        }

        for (int i = 1; i < m; i++) {
            map[i][0] = map[i - 1][0] + grid[i][0];
        }

        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                map[i][j] = Math.min(map[i - 1][j], map[i][j - 1]) + grid[i][j];
            }
        }
        return map[m - 1][n - 1];
    }

    public static boolean isNumber(String s) {
        String input = s.trim();
        int eIndex = -1;

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (Character.isLetter(c)) {
                if (c == 'e') {
                    if (eIndex == -1) {
                        eIndex = i;
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            }
        }

        if (eIndex == -1) {
            return plusMinusDotDigits(input);
        } else {
            return plusMinusDotDigits(s.substring(0, eIndex)) && noDotsNumber(s.substring(eIndex + 1));
        }
    }

    public static boolean allDigits(String s) {
        if (s.length() == 0) {
            return false;
        }
        for (int i = 1; i < s.length(); i++) {
            if (!Character.isDigit(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean plusMinusDotDigits(String s) {
        if (s.length() == 0) {
            return false;
        }
        if (s.charAt(0) == '+' || s.charAt(0) == '-') {
            if (s.length() == 1) {
                return false;
            }
            for (int i = 1; i < s.length(); i++) {
                if (!Character.isDigit(s.charAt(i))) {
                    if (s.charAt(i) == '.') {
                        if (s.substring(i + 1).length() == 0 && i != 1) {
                            return true;
                        }
                        return allDigits(s.substring(i + 1));
                    } else {
                        return false;
                    }
                }
            }
        } else {
            for (int i = 0; i < s.length(); i++) {
                if (!Character.isDigit(s.charAt(i))) {
                    if (s.charAt(i) == '.') {
                        if (s.substring(i + 1).length() == 0 && i != 0) {
                            return true;
                        }
                        return allDigits(s.substring(i + 1));
                    } else {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public static boolean noDotsNumber(String s) {
        if (s.length() == 0) {
            return false;
        }
        if (s.charAt(0) == '+' || s.charAt(0) == '-') {
            if (s.length() == 1) {
                return false;
            }
            for (int i = 0; i < s.length(); i++) {
                if (!Character.isDigit(s.charAt(i))) {
                    return false;
                }
            }
        }
        return true;
    }

    public static String addBinary(String a, String b) {
        int len = Math.max(a.length(), b.length());
        int addNum = 0;
        String result = "";
        for (int i = 0; i < len; i++) {
            int aNum = 0;
            int bNum = 0;
            if (a.length() - 1 - i < 0) {
                aNum = 0;
            } else {
                aNum = a.charAt(a.length() - 1 - i) - '0';
            }

            if (b.length() - 1 - i < 0) {
                bNum = 0;
            } else {
                bNum = b.charAt(b.length() - 1 - i) - '0';
            }

            int totalNum = addNum + aNum + bNum;
            int actualNum = totalNum % 2;
            addNum = totalNum / 2;
            result = Integer.toString(actualNum) + result;
        }

        if (addNum != 0) {
            result = "1" + result;
        }

        return result;
    }

    public static List<String> fullJustify(String[] words, int maxWidth) {
        int i = 0;
        List<String> result = new ArrayList<>();
        while (i < words.length) {
            List<String> line = new ArrayList<>();

            int currentLength = 0;

            int j = 0;
            String row = "";
            while (i + j < words.length) {
                currentLength += words[i + j].length() + 1;
                if (currentLength > maxWidth + 1) {
                    currentLength -= (words[i + j].length() + 1);
                    break;
                } else {
                    line.add(words[i + j] + " ");
                    j += 1;
                }
            }
            currentLength -= 1;
            line.set(line.size() - 1, words[i + j - 1]);


            if (j == 1) {
                row = line.get(0) + genSpaces(maxWidth - currentLength);
            } else if (i + j >= words.length) {
                currentLength = 0;

                for (String word : line) {
                    currentLength += word.length();
                    row += (word);
                }
                row += genSpaces(maxWidth - currentLength);
            } else {
                int spaceNum = j - 1;
                int averageSpace = (maxWidth - currentLength) / spaceNum;
                int extraSpace = (maxWidth - currentLength) % spaceNum;

                List<Integer> spaceList = new ArrayList<>();
                for (int k = 0; k < spaceNum; k++) {
                    if (extraSpace > 0) {
                        spaceList.add(averageSpace + 1);
                        extraSpace -= 1;
                    } else {
                        spaceList.add(averageSpace);
                    }
                }
                for (int l = 0; l < line.size(); l++) {
                    if (l < spaceList.size()) {
                        row += (line.get(l) + genSpaces(spaceList.get(l)));
                    } else {
                        row += line.get(l);
                    }

                }


            }
            result.add(row);
            i += j;


        }
        return result;
    }

    public static String genSpaces(int n) {
        String result = "";

        for (int i = 0; i < n; i++) {
            result += " ";
        }
        return result;
    }

    public static int mySqrt(int x) {
        if (x == 0) {
            return 0;
        }
        if (x == 1 || x == 2 || x == 3) {
            return 1;
        }
        int low = 0;
        int high = x;
        int last = -1;
        while (low != high) {
            int mid = (low + high) / 2;

            if (mid * mid == x) {
                return mid;
            } else if (mid * mid < x) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        if (low * low > x) {
            return (low - 1);
        }

        return low;
    }

    public static int minDistance(String word1, String word2) {
        int m = word1.length();
        int n = word2.length();

        int[][] matrix = new int[m][n];
        matrix[0][0] = 0;

        for (int j = 0; j < n; j++) {
            matrix[0][j] = j;
        }

        for (int i = 0; i < m; i++) {
            matrix[i][0] = i;
        }

        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                matrix[i][j] = Math.min((Math.min(matrix[i - 1][j], matrix[i][j - 1])), matrix[i - 1][j - 1]) + 1;
            }
        }
        return matrix[m - 1][n - 1];
    }

    public static boolean searchMatrix(int[][] matrix, int target) {
        int n = matrix[0].length;

        int up = 0;
        int down = matrix.length - 1;

        int line = -1;
        while (up <= down) {

            int mid = (up + down) / 2;

            if (matrix[mid][0] <= target && target <= matrix[mid][n - 1]) {
                line = mid;
                break;
            } else if (target < matrix[mid][0]) {
                down = mid - 1;
            } else {
                up = mid + 1;
            }
        }

        if (line == -1) {
            return false;
        }


        int left = 0;
        int right = n - 1;

        while (left <= right) {
            int mid2 = (left + right) / 2;

            if (matrix[line][mid2] == target) {
                return true;
            } else if (matrix[line][mid2] > target) {
                right = mid2 - 1;
            } else {
                left = mid2 + 1;
            }
        }
        return false;


    }

    public static void sortColors(int[] nums) {
        int red = -1;
        int white = -1;
        int blue = -1;

        for (int i = 0; i < nums.length; i++) {

            if (nums[i] == 0) {

                blue++;
                nums[blue] = 2;
                white++;
                nums[white] = 1;
                red++;
                nums[red] = 0;


            } else if (nums[i] == 1) {
                white++;

                blue++;
                nums[blue] = 2;


                nums[white] = 1;

            } else {
                blue++;
                nums[blue] = 2;

            }
        }
    }

    public static String minWindow(String s, String t) {
        Map<Character, Integer> d = new HashMap<Character, Integer>();
        for (Character c : t.toCharArray()) {
            if (d.containsKey(c)) {
                d.put(c, d.get(c) + 1);

            } else {
                d.put(c, 1);
            }
        }

        int left = 0;
        int right = 0;

        int start = 0;
        int minLength = Integer.MAX_VALUE;

        int count = t.length();

        while (right < s.length()) {
            char c = s.charAt(right);
            if (d.containsKey(c)) {

                if (d.get(c) > 0) {
                    d.put(c, d.get(c) - 1);
                    count -= 1;
                } else {
                    d.put(c, d.get(c) - 1);
                }

                while (count == 0) {
                    if (minLength > (right - left)) {
                        minLength = right - left;
                        start = left;
                    }

                    char c1 = s.charAt(left);

                    if (d.containsKey(c1)) {
                        if (d.get(c1) == 0) {
                            d.put(c1, d.get(c1) + 1);
                            count++;
                            left++;
                        } else {
                            d.put(c1, d.get(c1) + 1);
                            left++;
                        }
                    } else {
                        left += 1;
                    }

                }
            }
            right += 1;
        }
        if(minLength == Integer.MAX_VALUE){
            return "";
        }
        else{
            return s.substring(start,start+minLength+1);
        }
    }

    public static List<List<Integer>> combine(int n, int k){
        if(k == 0){
            return new ArrayList<>();
        }
        List<List<Integer>> result = new ArrayList<>();
        ArrayList<Integer> temp = new ArrayList<>();
        int start = 1;
        backTrack(result,temp,start,n,k);
        return result;

    }
    public static void backTrack(List<List<Integer>> result, ArrayList<Integer> temp, int start, int n, int k){
        if(temp.size() == k){

            List<Integer> tempClone = (List<Integer>)temp.clone();
            result.add(tempClone);
            return;
        }

        for(int i = start; i <= n; i++){
            temp.add(i);
            backTrack(result,temp,i+1,n,k);
            temp.remove(temp.size()-1);
        }
    }


    public static boolean exist(char[][] board, String word) {
        for(int i =0; i < board.length; i++){
            for(int j =0; j < board[0].length; j++){
                if(board[i][j] == word.charAt(0)){
                    char[][]boardClone = arrayCopy(board);
                    boardClone[i][j] = '-';
                    if(backTrack2(boardClone,word.substring(1),i,j)){
                        return true;
                    }
                }
            }
        }



        return false;
    }

    public static boolean backTrack2(char[][]board, String word, int row, int column){
        if(word.length() == 0){
            return true;
        }

        char c = word.charAt(0);
        boolean result = false;
        if(row -1 >=0 && board[row-1][column] == c){
            char[][]boardClone = arrayCopy(board);
            boardClone[row-1][column] = '-';
            result = result || backTrack2(boardClone,word.substring(1),row-1,column);
        }

        if(row +1 < board.length && board[row+1][column] == c){
            char[][]boardClone = arrayCopy(board);
            boardClone[row+1][column] = '-';
            result = result || backTrack2(boardClone,word.substring(1),row+1,column);
        }

        if(column -1 >=0 && board[row][column - 1] == c){
            char[][]boardClone = arrayCopy(board);
            boardClone[row][column-1] = '-';
            result = result || backTrack2(boardClone,word.substring(1),row,column-1);
        }

        if(column +1 < board[0].length && board[row][column+1] == c){
            char[][]boardClone = arrayCopy(board);
            boardClone[row][column+1] = '-';
            result = result || backTrack2(boardClone,word.substring(1),row,column+1);
        }
        return result;
    }

    public static char[][] arrayCopy(char[][] board){
        char[][] result = new char[board.length][board[0].length];
        for(int i =0; i < board.length; i++){
            result[i] = board[i].clone();
        }
        return result;
    }


    public static boolean exist2(char[][] board, String word){


        for(int i =0; i < board.length; i++){
            for(int j =0; j < board[0].length; j++){
                if(board[i][j] == word.charAt(0)){
                    Pair<int[],String> initialNode = new Pair<>(new int[]{i,j},word.substring(1));
                    Stack<Pair<int[],String>> s = new Stack<>();
                    s.push(initialNode);
                    Set<Pair<Integer,Integer>> visited = new HashSet<>();
                    while(!s.empty()){
                        Pair<int[],String> node = s.pop();
                        int row = node.getKey()[0];
                        int column = node.getKey()[1];

                        String leftWord = node.getValue();

                        if(leftWord.equals("")){
                            return true;
                        }

                        visited.add(new Pair<Integer, Integer>(row,column));

                        char c = leftWord.charAt(0);
                        if(row -1 >=0 && board[row-1][column] == c){
                            if (!visited.contains(new Pair<Integer, Integer>(row-1,column))){
                            s.add(new Pair<>(new int[]{row-1,column},leftWord.substring(1)));
                        }

                        }
                        if(row +1 <board.length && board[row+1][column] == c){
                            if (!visited.contains(new Pair<Integer, Integer>(row+1,column))){
                            s.add(new Pair<>(new int[]{row+1,column},leftWord.substring(1)));
                        }}
                        if(column -1 >=0 && board[row][column-1] == c){
                            if (!visited.contains(new Pair<Integer, Integer>(row,column-1))){
                            s.add(new Pair<>(new int[]{row,column-1},leftWord.substring(1)));
                        }}
                        if(column +1 <board[0].length && board[row][column+1] == c){
                            if (!visited.contains(new Pair<Integer, Integer>(row,column+1))){
                            s.add(new Pair<>(new int[]{row,column+1},leftWord.substring(1)));
                        }}
                    }


                }

            }
        }
        return false;
    }

    public static int largetRectangleArea(int[] heights){
        int[] heightsCopy = heights.clone();
        int result = -1;
        for(int i = heightsCopy.length-1; i >=0; i--){
            if(i != heightsCopy.length-1 && heightsCopy[i] == heightsCopy[i+1]){
                continue;
            }
            int height = heightsCopy[i];

            int left = 0;
            int right = 0;

            while(right < heights.length){
                while(left < heights.length && heights[left] < height){
                    left +=1;
                }

                right = left;
                while(right < heights.length && heights[right] >= height){
                    right +=1;
                }
                result = Math.max(result,(right-left)*height);


                left = right;


            }
        }
        return result;

    }

    public static int maximalRectangle(char[][] matrix){
        if(matrix.length == 0 || matrix[0].length == 0){
            return 0;
        }
        int m = matrix.length;
        int n = matrix[0].length;

        int[] leftMost = new int[n];

        int[] rightMost = new int[n];

        int[] height = new int[n];

        Arrays.fill(leftMost,0);
        Arrays.fill(rightMost,n-1);
        Arrays.fill(height,0);

        int maxArea = 0;

        for(int i =0; i< m; i++){

            int leftBoard = 0;
            int rightBoard = n-1;

            for(int j =n-1; j >= 0; j--){
                if(matrix[i][j] == '1'){

                    rightMost[j] = Math.min(rightMost[j],rightBoard);

                }
                else{

                    rightMost[j] = n-1;
                    rightBoard = j-1;
                }
            }

            for(int j =0; j < n; j++){
                if(matrix[i][j] == '1'){
                    leftMost[j] = Math.max(leftMost[j],leftBoard);



                    height[j] +=1;

                    maxArea = Math.max(maxArea,height[j]*(rightMost[j]-leftMost[j]+1));
                }
                else{
                    leftMost[j] = 0;
                    leftBoard = j+1;


                    height[j] = 0;
                }
        }
        }
        return maxArea;

    }

    public static List<Integer> grayCode(int n){
        if(n == 0){
            List<Integer> result0 = new ArrayList<>();
            result0.add(0);
            return result0;
        }


        List<Integer> temp = new ArrayList<>();
        List<Integer> subResult = grayCode(n-1);


        for(int i = subResult.size()-1; i >=0; i--){
            temp.add(subResult.get(i)+(int)Math.pow(2,n-1));
        }
        subResult.addAll(temp);
        return subResult;
    }

    public static List<List<Integer>> subsetsWithDup(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> result = new ArrayList<>();
        ArrayList<Integer> temp = new ArrayList<>();
        backTrack3(result,temp,nums,0);
        return result;

    }

    public static void backTrack3(List<List<Integer>> result, ArrayList<Integer> temp, int[] nums, int start){
        result.add((ArrayList<Integer>)temp.clone());
        for(int i = start; i < nums.length; i++){
            if(i != start && (nums[i] == nums[i-1])){
                continue;
            }
            temp.add(nums[i]);
            backTrack3(result,temp,nums,i+1);
            temp.remove(temp.size()-1);
        }
    }
    public static int numDecodings(String s) {
        int[] map = new int[s.length()+1];

        map[0] = 1;

        if(s.charAt(0) == '0'){
            return 0;
        }
        map[1] = 1;

        for(int i =2; i <=s.length(); i++){
            if(Integer.parseInt(s.substring(i-1,i)) >0 && Integer.parseInt(s.substring(i-1,i)) <=9){
                map[i] += map[i-1];
            }

            if(Integer.parseInt(s.substring(i-2,i)) >=10 && Integer.parseInt(s.substring(i-2,i)) <=26){
                map[i] += map[i-2];
            }

        }
        return map[s.length()];
    }
    public static ListNode reverseBetween(ListNode head, int m, int n) {
        if(head == null){
            return null;
        }
        Map<Integer,ListNode> map = new HashMap<Integer,ListNode>();

        ListNode fakeHead = new ListNode(0);
        ListNode pointer = fakeHead;
        int count = 1;
        while (head != null){
            map.put(count,head);
            head = head.next;
            count ++;
        }


        int i =1;

        while(i <= count){
            if(i == m){
                i = n;
                while(i >= m){
                    fakeHead.next = map.get(i);
                    i --;
                }
                i = n+1;
            }
            pointer.next = map.get(i);
            pointer = pointer.next;
            i ++;
        }
        return fakeHead.next;
    }

    public static int numTrees(int n ){
        int result = 0;
        if(n == 0){
            return 1;
        }
        if(n == 1){
            return 1;
        }
        for(int i = 1; i <=n; i ++){
            result += numTrees(i-1) * numTrees(n-i);
        }
        return result;

    }

    public static boolean inInterleave(String s1, String s2, String s3){
        if(s1.length() + s2.length() != s3.length()){
            return false;
        }
        if(s1.equals("")){
            return s2.equals(s3);
        }
        if(s2.equals("")){
            return s1.equals(s3);
        }

        char c1 = s1.charAt(0);
        char c2 = s2.charAt(0);
        char c3 = s3.charAt(0);



        if(c1 == c3 && c2 == c3){
            return inInterleave(s1.substring(1),s2,s3.substring(1)) || inInterleave(s1,s2.substring(1),s3.substring(1));
        }
        else if(c1 == c3){
            return inInterleave(s1.substring(1),s2,s3.substring(1));
        }
        else if(c2 == c3){
            return inInterleave(s1,s2.substring(1),s3.substring(1));
        }
        else{
            return false;
        }
    }

}
