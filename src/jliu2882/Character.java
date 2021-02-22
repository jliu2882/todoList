package jliu2882;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.FileNotFoundException;
public class Character {
    private int[] expPoints;
    private String name;
    private int level,exp;
    private Task task1,task2,task3,task4,task5;
    public Character(int pos) throws FileNotFoundException{
        File f = new File(Main.fileName);
        Scanner in = new Scanner(f);
        ArrayList<String> arrList = new ArrayList<String>();
        while (in.hasNext()) {
            arrList.add(in.nextLine());
        }
        String[] character = arrList.get(pos).split("<>");
        this.name = character[0];
        this.level = Integer.parseInt(character[1]);
        this.exp = Integer.parseInt(character[2]);
        if(exp==0){
            exp+=3;//idk exp is funky at 0 and im not here to fix it since it works everyelse lol
        }
        this.task1 = new Task(character[3],Integer.parseInt(character[4]));
        this.task2 = new Task(character[5],Integer.parseInt(character[6]));
        this.task3 = new Task(character[7],Integer.parseInt(character[8]));
        this.task4 = new Task(character[9],Integer.parseInt(character[10]));
        this.task5 = new Task(character[11],Integer.parseInt(character[12]));
        this.expPoints = new int[100];
        for(int i = 1; i < 100; i++){
            expPoints[i] = (int)(Math.ceil(Math.pow(i,3)/3)+2);
        }
    }

    public String getName() {
        return name;
    }
    public int getLevel() {
        return level;
    }
    public int getExp() {
        return exp;
    }
    public Task getTask1() {
        return task1;
    }
    public Task getTask2() {
        return task2;
    }
    public Task getTask3() {
        return task3;
    }
    public Task getTask4() {
        return task4;
    }
    public Task getTask5() {
        return task5;
    }
    public int[] getExpPoints() {
        return expPoints;
    }

    public void completeTask(Task task){
        this.exp = exp+task.getExp();
        checkForLevelUp();
        updateCSV();
    }
    public void checkForLevelUp(){
        while(exp>=expPoints[level+1]){
            level++;
        }
    }
    public void updateCSV(){
        try {
            FileWriter csvWriter = new FileWriter(Main.fileName);
            csvWriter.write("");
            csvWriter.append(name+"<>"+level+"<>"+exp+"<>"+task1+"<>"+task2+"<>"+task3+"<>"+task4+"<>"+task5);
            csvWriter.flush();
            csvWriter.close();
        }catch(Exception e){
            e.printStackTrace();
        }

    }
    public void reset(){
        task1.reset();
        task2.reset();
        task3.reset();
        task4.reset();
        task5.reset();
    }
}
