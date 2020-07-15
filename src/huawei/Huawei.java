package huawei;

import sun.tools.jmap.JMap;

import java.util.*;
import java.util.stream.Collectors;

public class Huawei {
    /*
     * 求解立方根
     */
    public static double getCubeRoot(double input) {
        int start = 0;
        int end = (int) Math.floor(input);

        int num = -1;

        while (start <= end) {
            int mid = (start + end) / 2;
            int mid1 = mid + 1;
            if (mid * mid * mid <= input && mid1 * mid1 * mid1 > input) {
                num = mid;
                break;
            } else if (mid1 * mid1 * mid1 <= input) {
                start = mid + 1;
            } else {
                end = mid - 1;
            }
        }

        double minDiff = Double.MAX_VALUE;
        double result = (double) num;

        for (double i = num; i < num + 1; i += 0.1) {
            double qubic = i * i * i;
            double diff = Math.abs(qubic - input);

            if (diff < minDiff) {
                minDiff = diff;
                result = i;
            }
        }
        return result;
    }


    /*
     * 梅花桩
     */
    public int Redraiment(List<Integer> nums){
        int[] tail = new int[nums.size()];
        int size = 0;
        for(int x : nums){
            int i = 0;
            int j = size;
            while(i != j){
                int m = (i+j)/2;
                if(tail[m] < x){
                    i = m+1;
                }
                else{
                    j = m;
                }
            }
            if(i == size){
                size++;
            }
            tail[i] = x;
        }
        return size;
    }

    public static String statistic(String input){
        char[] carray = input.toCharArray();
        Map<Character,Integer> dic = new HashMap<>();

        for(char c : carray)
        {
            int count = dic.getOrDefault(c,0);
            count++;
            dic.put(c,count);
        }

        List<Character> result = new ArrayList<>();
        int index= 0;
        for(char c : dic.keySet()){
            result.add(c);
        }


        Collections.sort(result, (o1, o2) -> {
            int c1 = dic.get(o1);
            int c2 = dic.get(o2);

            if(c1 == c2){
                return o1-o2;
            }
            else{
                return c2 - c1;
            }
        });

        StringBuilder sb = new StringBuilder();
        for(char c : result ){
            sb.append(c);
        }
        return sb.toString();
    }

    public static String reverseString(String input) {
        StringBuilder sb = new StringBuilder(input);
        return sb.reverse().toString();
    }

    public static void negativeCount() {
        Scanner keyboard = new Scanner(System.in);
        int negativeCount = 0;
        int positiveCount = 0;
        int positiveSum = 0;

        while (keyboard.hasNextInt()) {
            int n = keyboard.nextInt();

            if (n < 0) {
                negativeCount++;
            } else {
                positiveCount++;
                positiveSum += n;
            }
        }
        String mean;
        if (positiveCount == 0) {
            mean = String.format("%.1f", 0.0);
        }
        else{
            mean = String.format("%.1f", ((double) positiveSum) / positiveCount);
        }


        System.out.println(negativeCount);
        System.out.println(mean);
    }

    /*
     * 自守数
     */
    public static int countProtect(int n){
        int count = 0;
        for(int i = 0; i <n; i++){
            int square = i*i;
            int l = String.valueOf(i).length();
            int lastNum = (int) (square % (Math.pow(10,l)));
            if(lastNum == i){
                count++;
                System.out.println(i);
            }
        }
        return count;
    }


    public static int waysTOWalk(int n, int m){
        int[][] map = new int[n][m];
        map[0][0]= 1;
        for(int j = 0; j < m; j ++){
            map[0][j] = 1;

        }

        for(int i =0; i < n; i++){
            map[i][0] = 1;
        }

        for(int i =1; i < n; i++){
            for(int j = 1; j < m; j++){
                map[i][j] = map[i-1][j] + map[i][j-1];
            }
        }
        return map[n-1][m-1];
    }

    public String collectionCalculate(String collections){
        String[] multiplys = collections.split(",");
        String result = multiplyCal(multiplys[0]);
        for(int i =1; i < multiplys.length;i++){
            String subResult = multiplyCal(multiplys[i]);
            result = addCollections(result,subResult);
        }
        return result;
    }
    public String addCollections(String collection1, String collection2){
        String[] elements1 = collection1.substring(1,collection1.length()-1).split(",");
        String[] elements2 = collection2.substring(1,collection1.length()-1).split(",");
        Set<String> added = new HashSet<>();

        StringBuilder sb = new StringBuilder();
        sb.append('[');
        for(String e1 : elements1){
            sb.append(e1);
            added.add(e1);
            sb.append(',');
        }
        for(String e2 : elements2){
            if(added.contains(e2)){
                continue;
            }

            sb.append(e2);
            sb.append(',');
        }
        sb.deleteCharAt(sb.length()-1);
        sb.append(']');
        return sb.toString();

    }

    public String multiplyCal(String collections){
        List<String> elements = getElements(collections);
        Stack<String> stack = new Stack<>();
        for(String e : elements){
            if(stack.isEmpty()){
                stack.push(e);
            }
            else{
                String pre = stack.pop();
                String cur = e;
                String result;
                if(pre.charAt(0) == '[' && cur.charAt(0) == '['){
                    result = collectionMultiplyCollections(pre,cur);
                }
                else if(pre.charAt(0) == '['){
                    result = collectionMultiplyNumber(pre,cur);
                }
                else if(cur.charAt(0) == '['){
                    result = numberMultiplyCollection(pre,cur);
                }
                else{
                    result = pre+cur;

                }
                stack.push(result);
            }
        }
        return stack.pop();
    }

    private String collectionMultiplyCollections(String collection1, String collection2){
        String[] elements1 = collection1.substring(1,collection1.length()-1).split(",");
        String[] elements2 = collection2.substring(1,collection1.length()-1).split(",");
        StringBuilder sb = new StringBuilder();
        sb.append('[');

        for(String pre : elements1){
            for(String cur : elements2){
                String result;

                if(pre.charAt(0) == '[' && cur.charAt(0) == '['){
                    result = collectionMultiplyCollections(pre,cur);
                }
                else if(pre.charAt(0) == '['){
                    result = collectionMultiplyNumber(pre,cur);
                }
                else if(cur.charAt(0) == '['){
                    result = numberMultiplyCollection(pre,cur);
                }
                else{
                    result = pre+cur;
                }
                sb.append(result);
                sb.append(',');
            }
        }
        sb.deleteCharAt(sb.length()-1);
        sb.append(']');
        return sb.toString();
    }

    private String collectionMultiplyNumber(String collection, String number){
        String[] elements1 = collection.substring(1,collection.length()-1).split(",");
        StringBuilder sb = new StringBuilder();
        sb.append('[');

        for(String e : elements1){
            String result;
            if(e.charAt(0) == '['){
                result = collectionMultiplyNumber(e,number);
            }
            else{
                result = e+number;
            }
            sb.append(result);
            sb.append(',');
        }
        sb.deleteCharAt(sb.length()-1);
        sb.append(']');
        return sb.toString();
    }

    private String numberMultiplyCollection(String collection, String number){
        String[] elements1 = collection.substring(1,collection.length()-1).split(",");
        StringBuilder sb = new StringBuilder();
        sb.append('[');

        for(String e : elements1){
            String result;
            if(e.charAt(0) == '['){
                result = collectionMultiplyNumber(number,e);
            }
            else{
                result = number + e;
            }
            sb.append(result);
            sb.append(',');
        }
        sb.deleteCharAt(sb.length()-1);
        sb.append(']');
        return sb.toString();
    }



    private List<String> getElements(String collections){
        List<String> elements = new ArrayList<>();
        Stack<Character> stack = new Stack<>();

        int start = -1;
        int end = -1;

        for(int i =0; i < collections.length(); i++){
            if(collections.charAt(i) == '['){
                if(stack.isEmpty()){
                    start = i;
                    if(start - end != 1){
                        elements.add(collections.substring(end+1,start));
                    }
                }
                stack.push(collections.charAt(i));
            }
            else if(collections.charAt(i) == ']'){
                stack.pop();
                if(stack.isEmpty()){
                    end = i;
                    elements.add(collections.substring(start,i+1));
                }
            }
        }
        if(end != collections.length()-1){
            elements.add(collections.substring(end+1,collections.length()));
        }
        return elements;
    }

    public static List<String> trainInAndOut(int[] trains){
        List<String> result = new ArrayList<>();
        Set<String >set = new HashSet<>();
        int remaining = trains.length;
        trainBackTrack(set,1,"","",trains.length+1);

        set.forEach(x -> result.add(x));
        Collections.sort(result);
        return result;

    }

    public List<String> analyzeInput(String input){
        String[] array = input.split(" ");
        List<String> result = new ArrayList<>();
        int i =0;
        while(i < array.length){
            StringBuilder temp = new StringBuilder(array[i]);
            if(temp.charAt(0) == '"' && temp.charAt(array[i].length()-1) != '"'){
                i++;
                while(array[i].charAt(array[i].length()-1) != '"'){
                    temp.append(array[i]);
                    i++;
                }
                temp.append(" ");
                temp.append(array[i]);
            }
            if(temp.charAt(0) == '"'){
                result.add(temp.substring(1,temp.length()-1));
            }
            else{
                result.add(temp.toString());
            }
            i++;
        }
        return result;
    }

    public int mp3Page(int count, int[] screen, String instructions){
        char[] ins = instructions.toCharArray();
        int current = 1;
        if(count <= 4){
            for(int i =1; i <= count; i++){
                screen[i-1] = i;
            }
            for(char c : ins){
                if(c == 'U'){
                    if(current == 1){
                        current = count;
                    }
                    else{
                        current--;
                    }
                }
                else{
                    if(current == count){
                        current =1;
                    }
                    else{
                        current++;
                    }
                }
            }
        }
        else{
            int start = 1;
            int end = 4;


            for(char c : ins){
                if(c == 'U'){
                    if(current == 1){
                        current = count;
                        start = count -3;
                        end = count;
                    }
                    else{
                        if(current == start){
                            current--;
                            start--;
                            end--;
                        }
                        else{
                            current--;
                        }
                    }
                }
                else{
                    if(current == count){
                        current =1;
                        start = 1;
                        end = 4;
                    }
                    else{
                        if(current == end){
                            current++;
                            end++;
                            start++;
                        }
                        else{
                            current++;
                        }
                    }
                }
            }
            int index = 0;
            for(int i = start; i<= end; i++){
                screen[index] = i;
                index++;
            }
        }
        return current;
    }

    private static void trainBackTrack(Set<String> set, int remaining, String inStation, String temp,int count){
        if(remaining == count){
            String sequence = temp + new StringBuilder(inStation).reverse().toString();
            set.add(sequence);
            return;
        }

        trainBackTrack(set,remaining+1,inStation + remaining,temp,count);

        if(!inStation.isEmpty()){
            trainBackTrack(set,remaining,inStation.substring(0,inStation.length()-1),temp+inStation.charAt(inStation.length()-1),count);
        }

    }

    public String stringSort(String str){
        List<Character> chars = new ArrayList<>();
        for(int i =0; i < str.length(); i++){
            if(Character.isLetter(str.charAt(i))){
                chars.add(str.charAt(i));
            }
        }

        Collections.sort(chars, new Comparator<Character>() {
            @Override
            public int compare(Character o1, Character o2) {
                return Character.toLowerCase(o1) - Character.toLowerCase(o2);
            }
        });

        for(int i =0; i < str.length(); i++){
            if(!Character.isLetter(str.charAt(i))){
                chars.add(i,str.charAt(i));
            }
        }
        StringBuilder sb = new StringBuilder();
        chars.forEach(x -> sb.append(x));
        return sb.toString();
    }

    public static int changeBottles(int n){
        if(n <= 1){
            return 0;
        }
        if(n == 2){
            return 1;
        }

        int count = n/3;
        n = n%3 + count;
        count += changeBottles(n);
        return count;
    }


    public int firstMissingPositive(int[] nums) {
        int positiveMin = Integer.MAX_VALUE;
        int positiveCount = 0;
        for(int i : nums){
            if(i <=0){
                continue;
            }
            else{
                positiveMin = Math.min(positiveMin,i);
                positiveCount++;
            }
        }

        if(positiveMin != 1){
            return 1;
        }
        else{
            int[] p = new int[positiveCount];
            for(int i : nums){
                if(i > 0 && i-positiveMin < positiveCount){
                    p[i-positiveMin] = i;
                }
            }
            for(int i = 0; i < p.length; i++){
                if(p[i] == 0){
                    return i+positiveMin;
                }
            }
        }
        return positiveCount+1;
    }



    public static void main(String[] args) throws InterruptedException {
        System.out.println(new Huawei().firstMissingPositive(new int[]{7,8,9,11,12}));
    }
}

class ABCD implements Runnable{
    private String s;
    private int index = 0;
    private int count = 0;
    public ABCD(String s, int index, int count){
        this.s = s;
        this.index = index;
        this.count = count;
    }
    @Override
    public void run() {
        System.out.println(index+" is runnning");
        int i =0;
        while(i < count){
            System.out.println(i);
            synchronized (s){
                if(index == 1){
                    if(s.length() == 0 || s.charAt(s.length()-1) == 'D'){
                        s = s+'A';
                        System.out.println(s);
                        i++;
                    }
                }
                else if(index == 2){
                    if(s.length() != 0 && s.charAt(s.length()-1) == 'A'){
                        s = s+'B';
                        System.out.println(s);
                        i++;
                    }
                }
                else if(index == 3){
                    if(s.length() != 0 && s.charAt(s.length()-1) == 'B'){
                        s = s+'C';
                        System.out.println(s);
                        i++;
                    }
                }
                else{
                    if(s.length() != 0 && s.charAt(s.length()-1) == 'C'){
                        s = s+'D';
                        System.out.println(s);
                        i++;
                    }
                }
            }
        }

    }
}
