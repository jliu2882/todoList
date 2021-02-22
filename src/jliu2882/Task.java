package jliu2882;

public class Task {
    private String description;
    private int exp;
    private boolean repeatable;
    private int timesDone = 0;
    public Task(String description, int exp){
        this.description = description.contains("(repeatable)")?description.split("(repeatable)")[1].substring(1):description;
        this.exp = exp;
        this.repeatable = description.contains("(repeatable)")?true:false;
    }

    public String getDescription() {
        return description;
    }
    public int getExp() {
        return exp;
    }
    public boolean isRepeatable() {
        return repeatable;
    }
    public int getTimesDone(){
        timesDone++;
        return timesDone;
    }
    public void reset(){
        this.timesDone = 0;
    }
    public String toString(){
        return (repeatable?"(repeatable)":"")+description+"<>"+exp;
    }
}
