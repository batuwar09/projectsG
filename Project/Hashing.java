import java.io.*;
import java.util.*;

public class Hashing {
    private ArrayList<BST> blist;
    ArrayList<ArrayList<Word>> arrlist;

    Hashing(){
        arrlist = new ArrayList<>();
        blist = new ArrayList<>();
        
        
        for(int i=0; i<26; i++){
            arrlist.add(new ArrayList<Word>());
            blist.add(new BST());
        }
    }
    void adding(ArrayList<String> strings, boolean yorp){ //youtube or ponopto
        for(String s:strings){
            if(s.length() > 1 && s.charAt(0) == 'a' && s.charAt(1) == 'c'){
            }
            
            boolean include = false;

            for(Word wrd: arrlist.get(s.charAt(0)-97) ){ // check if it contains
                if(wrd.getValue().equals(s)){
                    if(yorp){
                        wrd.incrementPT();
                        include=true;
                    }else{
                        wrd.incrementPT();
                        include=true;
                    }
                }
            }
            if(!include){ // if it doesnt and all is good then we get the youtube or ponopto list and add
                if(yorp){
                    arrlist.get(s.charAt(0)-97).add(new Word(s, 0, 1));

                }else{
                    arrlist.get(s.charAt(0)-97).add(new Word(s, 1, 0));
                }
            }
        }
        for(int i=0; i<26; i++){
            Collections.sort(arrlist.get(i));
        }
        for(int i=0; i<26; i++){
            for(Word wrd: arrlist.get(i)){
                blist.get(i).insert(wrd);
            }
        }
    }
    void WriteListDifference() throws IOException{
        FileWriter  fwriter = new FileWriter("resultsListDiff.txt");
        PrintWriter pwriter = new PrintWriter(fwriter);

        for(int i=0; i<26; i++){
            for(Word wrd: arrlist.get(i) ){

                if(wrd.getCountPT() > wrd.getCountYT()){
                    pwriter.print(wrd.getValue() + "\t\t+" + (wrd.getCountPT()-wrd.getCountYT()) + " PT"); 

                    if(wrd.getCountYT()==0){
                        pwriter.print(" - ZERO");
                    }

                    pwriter.println();

                }else if(wrd.getCountYT()>wrd.getCountPT()){

                    pwriter.print(wrd.getValue() + "\t\t+" + (wrd.getCountYT()-wrd.getCountPT()) + " YT");

                    if(wrd.getCountPT()==0){
                        pwriter.print(" - ZERO");
                    }
                    pwriter.println();
                }
            }
        }
        pwriter.close();
    }
    void WriteListEqual() throws IOException{
        FileWriter  fwriter = new FileWriter("resultsListEqual.txt");
        PrintWriter pwriter = new PrintWriter(fwriter);

        for(int i=0; i<26; i++){
            for(Word wrd: arrlist.get(i) ){
                if(wrd.getCountPT()==wrd.getCountYT()){
                    pwriter.println(wrd.getValue() + "\t\t" + wrd.getCountPT());
                }
            }
        }
        pwriter.close();
    }
    void WriteListDifferenceR(BST.Node node, PrintWriter pwriter){
        if(node==null){
            return;
        }
        WriteListDifferenceR(node.left, pwriter);
        Word wrd = node.key;
        if(wrd.getCountPT()>wrd.getCountYT()){
            pwriter.print(wrd.getValue() + "\t\t+" + (wrd.getCountPT()-wrd.getCountYT()) + " PT"); 
            if(wrd.getCountYT()==0){
                pwriter.print(" - ZERO");
            }
            pwriter.println();
        }else if(wrd.getCountYT()>wrd.getCountPT()){
            pwriter.print(wrd.getValue() + "\t\t+" + (wrd.getCountYT()-wrd.getCountPT()) + " YT");
            if(wrd.getCountPT()==0){
                pwriter.print(" - ZERO");
            }
            pwriter.println();
        }
        WriteListDifferenceR(node.right, pwriter);
    }
    void WriteListEqualR(BST.Node node, PrintWriter pwriter){
        if(node==null){
            return;
        }
        WriteListEqualR(node.left, pwriter);

        Word wrd = node.key;
        if(wrd.getCountPT()==wrd.getCountYT()){
            pwriter.println(wrd.getValue() + "\t\t" + wrd.getCountPT());
        }

        WriteListEqualR(node.right, pwriter);
    }
    void WriteEqual() throws IOException{
        FileWriter  fwriter = new FileWriter("resultsEqual.txt");
        PrintWriter pwriter = new PrintWriter(fwriter);

        for(int i=0; i<26; i++){
            WriteListEqualR(blist.get(i).root, pwriter);
        }

        pwriter.close();
    }
    void WriteDifference() throws IOException{
        FileWriter  fwriter = new FileWriter("resultsDiff.txt");
        PrintWriter pwriter = new PrintWriter(fwriter);

        for(int i=0; i<26; i++){
            WriteListDifferenceR(blist.get(i).root, pwriter);
        }

        pwriter.close();
    }

}
