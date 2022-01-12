public class Word implements Comparable<Word>{
    @Override
    public int compareTo(Word w) {
        return value.compareTo(w.value);
    }
    // pt yt and value
    private int countPT;
    private int countYT;
    private String value;

    public Word(String v, int p, int y){
        countPT=p;
        countYT=y;
        value=v;
    }
    public Word(String v){
        countPT=0;
        countYT=0;
        value=v;
    }
    public int getCountPT(){
        return countPT;
    }
    public int getCountYT(){
        return countYT;
    }
    public String getValue(){
        return value;
    }
    public void setcountPT(int a){
        countPT=a;
    }
    public void setcountYT(int a){
        countYT=a;
    }
    public void setValue(String a){
        value=a;
    }
    public void incrementPT(){
        countPT++;
    }
    public void incrementYT(){
        countYT++;
    }
    public String toString(){
        return "----------\n"+value+"\n"+countPT+"\n"+countYT;

    }
}
