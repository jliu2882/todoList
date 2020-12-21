package jliu2882;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.css.PseudoClass;
import javafx.scene.control.ProgressBar;
import javafx.scene.media.AudioClip;
import javafx.animation.Timeline;
import javafx.animation.KeyValue;
import javafx.animation.KeyFrame;
import javafx.util.Duration;
import java.util.Calendar;

public class Controller {
    public Controller(){}
    public static String time(){
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(System.currentTimeMillis());
        return (cal.get(Calendar.HOUR)==0?"12":cal.get(Calendar.HOUR))+":"+((""+cal.get(Calendar.MINUTE)).length()>1?cal.get(Calendar.MINUTE):("0"+cal.get(Calendar.MINUTE)))+" "+(cal.get(Calendar.HOUR_OF_DAY)>=12?"PM ":"AM ");
    }
    public void playAudio(String fileName){
        AudioClip note = new AudioClip(this.getClass().getResource(fileName).toString());
        note.setVolume(0.25);
        note.play();
    }
    public static void moveExpBar(ProgressBar pb, int ANIMATIONSPEED, double newPos){
        //this snippet is borrowed :D https://stackoverflow.com/questions/18539642/progressbar-animated-javafx replaced old code with animated code ;D
        int maxStatus = 12; IntegerProperty statusCountProperty = new SimpleIntegerProperty(1);
        Timeline task = new Timeline(new KeyFrame(Duration.ZERO,new KeyValue(pb.progressProperty(), pb.getProgress())),new KeyFrame(Duration.seconds(2),new KeyValue(pb.progressProperty(), newPos)));
        Timeline timelineBar = new Timeline(new KeyFrame(Duration.millis(ANIMATIONSPEED),new KeyValue(statusCountProperty, maxStatus)));
        timelineBar.setCycleCount(Timeline.INDEFINITE); timelineBar.play();
        statusCountProperty.addListener((ov, statusOld, statusNewNumber) -> {int statusNew = statusNewNumber.intValue();
            pb.pseudoClassStateChanged(PseudoClass.getPseudoClass("status" + statusOld.intValue()), false); pb.pseudoClassStateChanged(PseudoClass.getPseudoClass("status" + statusNew), true);});
        task.playFromStart();
        if(pb.getProgress()>newPos){
            levelUpAnimation(pb);
        }
    }
    public static void levelUpAnimation(ProgressBar pb){
        Controller bruh = new Controller();
        bruh.playAudio("levelUp.wav");
        //TODO make something shiny for levelup, like a flash or smth
    }
}
