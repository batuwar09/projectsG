import java.io.*;
import java.util.*;

public class Testing {

    public static void main(String[] args) throws Exception {

        //part 1
        Driver.readFiles();

        Driver.debug();
        testDebugFile("debug1.txt");

        //part 2
        Driver.createListEqual();
        Driver.createListDiff();
        testWriteListEqual("resultsListEqual.txt");
        testWriteListDifference("resultsListDiff.txt");

        //part3
        Driver.createEqual();
        Driver.createDiff();
        testWriteEqual("resultsEqual.txt");
        testWriteDifference("resultsDiff.txt");
    }


    /**
     * Checks format of debug.txt. Specifically, checks for alphabetical order, no repeated words, and correct punctuation.
     * Input: file path to debug.txt
     * Output: only output is the printed result of the test. Will give where it fails OR if it succeeds.
     * @param filePath
     * @throws Exception
     */
    public static void testDebugFile(String filePath) throws Exception{

        int pass = 1;

        BufferedReader in = new BufferedReader(new FileReader(filePath));

        String str;

        List<String> wordList = new ArrayList<String>();
        while((str = in.readLine()) != null){
            wordList.add(str);
        }

        String[] list = wordList.toArray(new String[0]);
        String[] sortedList = new String[list.length];
        System.arraycopy(list, 0, sortedList, 0, list.length);
        Arrays.sort(sortedList);

        for(int j=0; j<list.length; j++){
            if(!sortedList[j].equals(list[j])){
                System.out.println("debug.txt not in alphabetical order.");
                pass = 0;
                break;
            }
        }

        Set<String> set = new HashSet<>(wordList);
        if(set.size()<wordList.size()){
            System.out.println("There are repeated words");
            pass = 0;
        }

        for (String s : list) {
            if (s.matches("[a-zA-Z]*-*'*(:|\\?|[0-9]+|([0-9]+:[0-9]+:[0-9]+,[0-9]|-->|--|[0-9]+:[0-9]+:[0-9]+,[0-9]+)|!|@|#|%|&|<|>|,|\\.|\\[|\\])+[a-zA-Z]*-*'*")) {
                System.out.println("Contains punctuation that is not allowed");
                pass=0;
                break;
            }
        }

        if(!testIsPresent("syntactically", "debug1.txt")){
            System.out.println("debug.txt missing some words");
            pass = 0;
        }

        if(!testIsPresent("you're", "debug1.txt")){
            System.out.println("debug.txt missing some words");
        }

        if(!testIsPresent("zero", "debug1.txt")){
            System.out.println("debug.txt missing some words");
            pass = 0;
        }

        if(!testIsPresent("variables", "debug1.txt")){
            System.out.println("debug.txt missing some words");
            pass = 0;
        }

        if(!testIsPresent("variable", "debug1.txt")){
            System.out.println("debug.txt missing some words");
            pass = 0;
        }

        if(testIsPresent("00:50:18,870", "debug1.txt")){
            System.out.println("debug.txt contains timestamps that are not allowed");
            pass = 0;
        }

        if(pass==1){
            System.out.println("pass part 1");
        }
        in.close();
    }

    /**
     * Checks format of resultsListEqual.txt. Specifically, checks for alphabetical order, no repeated words, and correct punctuation.Also checks for certain words to be present
     * Input: file path to debug.txt
     * Output: only output is the printed result of the test. Will give where it fails OR if it succeeds.
     * @param filePath
     * @throws Exception
     */
    public static void testWriteListEqual(String filePath) throws IOException {

        int pass = 1;
        BufferedReader in = new BufferedReader(new FileReader(filePath));
        String str;

        List<String> wordList = new ArrayList<String>();
        while((str = in.readLine()) != null){
            wordList.add(str);
        }

        String[] list = wordList.toArray(new String[0]);
        String[] sortedList = new String[list.length];
        System.arraycopy(list, 0, sortedList, 0, list.length);
        Arrays.sort(sortedList);

        for(int j=0; j<list.length; j++){
            if(!sortedList[j].equals(list[j])){
                System.out.println(filePath + " is not in alphabetical order.");
                pass = 0;
                break;
            }
        }

        Set<String> set = new HashSet<>(wordList);
        if(set.size()<wordList.size()){
            System.out.println("There are repeated words");
            pass = 0;
        }

        if(!testIsPresent("otherwise\t\t1","resultsListEqual.txt")){
            System.out.println("resultsListEqual.txt missing words that are equal in both texts");
            pass = 0;
        }

        if(pass==1){
            System.out.println("pass part 2 - listEqual");
        }
        in.close();
    }

    /**
     * Checks format of resultsListDiff.txt. Specifically, checks for alphabetical order, no repeated words, and correct punctuation.Also checks for certain words to be present
     * Input: file path to debug.txt
     * Output: only output is the printed result of the test. Will give where it fails OR if it succeeds.
     * @param filePath
     * @throws Exception
     */
    public static void testWriteListDifference(String filePath) throws IOException {
        int pass = 1;
        BufferedReader in = new BufferedReader(new FileReader(filePath));
        String str;

        List<String> wordList = new ArrayList<String>();
        while((str = in.readLine()) != null){
            wordList.add(str);
        }

        String[] list = wordList.toArray(new String[0]);
        String[] sortedList = new String[list.length];
        System.arraycopy(list, 0, sortedList, 0, list.length);
        Arrays.sort(sortedList);

        for(int j=0; j<list.length; j++){
            if(!sortedList[j].equals(list[j])){
                System.out.println(filePath + " is not in alphabetical order.");
                pass = 0;
                break;
            }
        }

        Set<String> set = new HashSet<>(wordList);
        if(set.size()<wordList.size()){
            System.out.println("There are repeated words");
            pass = 0;
        }

        if(!testIsPresent("clue\t\t+1 PT - ZERO", "resultsListDiff.txt")){
            System.out.println("resultsListDiff.txt is missing some words, or has incorrect numbers");
            pass = 0;
        }

        if(pass==1){
            System.out.println("pass part 2 - listDiff");
        }
        in.close();
    }

    /**
     * Checks format of resultsEqual.txt. Specifically, checks for alphabetical order, no repeated words, and correct punctuation.Also checks for certain words to be present
     * Input: file path to debug.txt
     * Output: only output is the printed result of the test. Will give where it fails OR if it succeeds.
     * @param filePath
     * @throws Exception
     */
    public static void testWriteEqual(String filePath) throws IOException {
        int pass = 1;
        BufferedReader in = new BufferedReader(new FileReader(filePath));
        String str;

        List<String> wordList = new ArrayList<String>();
        while((str = in.readLine()) != null){
            wordList.add(str);
        }

        String[] list = wordList.toArray(new String[0]);
        String[] sortedList = new String[list.length];
        System.arraycopy(list, 0, sortedList, 0, list.length);
        Arrays.sort(sortedList);

        for(int j=0; j<list.length; j++){
            if(!sortedList[j].equals(list[j])){
                System.out.println(filePath + " is not in alphabetical order.");
                pass = 0;
                break;
            }
        }

        Set<String> set = new HashSet<>(wordList);
        if(set.size()<wordList.size()){
            System.out.println("There are repeated words");
            pass = 0;
        }

        if(!testIsPresent("otherwise\t\t1","resultsEqual.txt")){
            System.out.println("resultsEqual.txt missing words that are equal in both texts");
            pass = 0;
        }

        if(pass==1){
            System.out.println("pass part 2 - equal");
        }
        in.close();
    }

    /**
     * Checks format of resultsDiff.txt. Specifically, checks for alphabetical order, no repeated words, and correct punctuation.Also checks for certain words to be present
     * Input: file path to debug.txt
     * Output: only output is the printed result of the test. Will give where it fails OR if it succeeds.
     * @param filePath
     * @throws Exception
     */
    public static void testWriteDifference(String filePath) throws IOException {
        int pass = 1;
        BufferedReader in = new BufferedReader(new FileReader(filePath));
        String str;

        List<String> wordList = new ArrayList<String>();
        while((str = in.readLine()) != null){
            wordList.add(str);
        }

        String[] list = wordList.toArray(new String[0]);
        String[] sortedList = new String[list.length];
        System.arraycopy(list, 0, sortedList, 0, list.length);
        Arrays.sort(sortedList);

        for(int j=0; j<list.length; j++){
            if(!sortedList[j].equals(list[j])){
                System.out.println(filePath + " is not in alphabetical order.");
                pass = 0;
                break;
            }
        }

        Set<String> set = new HashSet<>(wordList);
        if(set.size()<wordList.size()){
            System.out.println("There are repeated words");
            pass = 0;
        }

        if(!testIsPresent("clue\t\t+1 PT - ZERO", "resultsDiff.txt")){
            System.out.println("resultsDiff.txt is missing some words, or has incorrect numbers");
            pass = 0;
        }

        if(pass==1){
            System.out.println("pass part 2 - diff");
        }
        in.close();
    }

    public static Boolean testIsPresent(String word, String filePath) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader(filePath));
        ArrayList<String> fileLines = new ArrayList<>();
        String thisLine;
        while ((thisLine = in.readLine()) != null) {
            fileLines.add(thisLine);
        }
        if(fileLines.contains(word)){
            in.close();
            return true;
        }
        in.close();
        return false;
    }



}
