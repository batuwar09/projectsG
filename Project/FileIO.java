import java.util.Scanner;
import java.util.*;
import java.io.*;

public class FileIO {
    private ArrayList<String> io;
    private ArrayList<String> iorep;
    private Scanner scnr;

    private String catchnon(String in){
        boolean clean = false;
        String io = in;
        while(clean == false){ //loop check for , . [] etc
            clean = true;
            int a = io.indexOf(",");
            if(a!=-1){ clean = false;
                if(a==in.length()-1){
                    io = io.substring(0, a);
                }else{
                    io = io.substring(0, a) + io.substring(a+1, io.length());
                }
            }
            // copy paste copy paste the rest
            a = io.indexOf('.');
            if(a!=-1){ clean = false;
                if(a==in.length()-1){
                    io = io.substring(0, a);
                }else{
                    io = io.substring(0, a) + io.substring(a+1, io.length());
                }        
            }
            a = io.indexOf("?");
            if(a!=-1){ clean = false;
                if(a==in.length()-1){
                    io = io.substring(0, a);
                }else{
                    io = io.substring(0, a) + io.substring(a+1, io.length());
                }        
            }
            a = io.indexOf("!");
            if(a!=-1){ clean = false;
                if(a==in.length()-1){
                    io = io.substring(0, a);
                }else{
                    io = io.substring(0, a) + io.substring(a+1, io.length());
                }        
            }
            a = io.indexOf(";");
            if(a!=-1){ clean = false;
                if(a==in.length()-1){
                    io = io.substring(0, a);
                }else{
                    io = io.substring(0, a) + io.substring(a+1, io.length());
                }        
            }
            a = io.indexOf(":");
            if(a!=-1){ clean = false;
                if(a==in.length()-1){
                    io = io.substring(0, a);
                }else{
                    io = io.substring(0, a) + io.substring(a+1, io.length());
                }        
            }
            a = io.indexOf("(");
            if(a!=-1){ clean = false;
                if(a==in.length()-1){
                    io = io.substring(0, a);
                }else{
                    io = io.substring(0, a) + io.substring(a+1, io.length());
                }        
            }
            a = io.indexOf(")");
            if(a!=-1){ clean = false;
                if(a==in.length()-1){
                    io = io.substring(0, a);
                }else{
                    io = io.substring(0, a) + io.substring(a+1, io.length());
                }        
            }
            a = io.indexOf("[");
            if(a!=-1){ clean = false;
                if(a==in.length()-1){
                    io = io.substring(0, a);
                }else{
                    io = io.substring(0, a) + io.substring(a+1, io.length());
                }        
            }
            a = io.indexOf("]");
            if(a!=-1){ clean = false;
                if(a==in.length()-1){
                    io = io.substring(0, a);
                }else{
                    io = io.substring(0, a) + io.substring(a+1, io.length());
                }       
            }
            a = io.indexOf("{");
            if(a!=-1){ clean = false;
                if(a==in.length()-1){
                    io = io.substring(0, a);
                }else{
                    io = io.substring(0, a) + io.substring(a+1, io.length());
                }
            }
            a = io.indexOf("}");
            if(a!=-1){ clean = false;
                if(a==in.length()-1){
                    io = io.substring(0, a);
                }else{
                    io = io.substring(0, a) + io.substring(a+1, io.length());
                }        
            }
        }
        return io;
    }

    FileIO(){
        io = new ArrayList<String>();
        iorep = new ArrayList<String>(); //for the repeating ones!
    }
    boolean openTextFile(String filename){
        try{
            scnr = new Scanner(new FileReader(filename));
        }catch(FileNotFoundException e){
            System.out.println("file cant be opened");
            return false;
        }
        return true;
    }
    ArrayList<String> readTextFile(String filename){
        io = new ArrayList<String>();
        iorep = new ArrayList<String>();

        while(scnr.hasNextLine()){ //goes on until the scanner runs out of lines
            String ln =scnr.nextLine();
            if(0 < ln.length() && !Character.isDigit(ln.charAt(0)) ){
                String[] words = ln.split(" ");
                for(String w: words){
                    String W = catchnon(w);
                    if(!Character.isDigit(W.charAt(0))){
                        io.add(W.toLowerCase());
                    }
                }
            }
        }
        Collections.sort(io);
        for(String w: io){
            if(!iorep.contains(w)){
                iorep.add(catchnon(w));
            }
        }
        return io;
    }
    public void writeData() throws IOException{

        FileWriter  fwriter = new FileWriter("debug1.txt");
        PrintWriter pwriter = new PrintWriter(fwriter);

        for(String w: iorep){
            pwriter.println(w);
        }
        pwriter.close();
    }

}
