import java.io.*;
import java.util.*;

public class Driver{

    static FileIO filename = new FileIO();
    static Hashing hash = new Hashing();
    static ArrayList<String> ytWords;
    static ArrayList<String> ptWords;


    static boolean readFiles(){
        //p thing
        if(!filename.openTextFile("PT1.txt")){
            return false;
        }
        ptWords = filename.readTextFile("PT1.txt");
        
        //youtube
        if(!filename.openTextFile("YT1.txt")){
            return false;
        }
        ytWords = filename.readTextFile("YT1.txt");
        
        hash.adding(ytWords, true);
        hash.adding(ptWords, false);
        // !if it gives an error later just close the file!
        return true;
    }
    static void debug() throws IOException{
        filename.writeData();
    }
    static void createListEqual() throws IOException{
        hash.WriteListEqual();
    }
    static void createListDiff() throws IOException{
        hash.WriteListDifference();
    }
    static void createEqual() throws IOException{
        hash.WriteEqual();
    }
    static void createDiff() throws IOException{
         hash.WriteDifference();
    }

}