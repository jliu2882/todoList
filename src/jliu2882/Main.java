package jliu2882;

import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.geometry.Pos;
import javafx.util.Duration;
import javafx.css.PseudoClass;
import javafx.animation.Timeline;
import javafx.animation.KeyValue;
import javafx.animation.KeyFrame;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.GridPane;
import java.io.FileNotFoundException;
import javafx.application.Application;
import javafx.animation.FadeTransition;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Main extends Application {
    final int JACK = 0; /** tried to make it usable for more than one person but it doesn't really work lol*/
    final String TITLE = "Daily Tasks";
    final int WIDTH = 750;
    final int HEIGHT = 500;
    final int ANIMATIONSPEED = 1000; //bad var name but higher speed = slower lol

    private Label system = new Label("");
    private Label expText = new Label("  Exp: ?/?");
    private Label levelText = new Label("?? | Level: ? ");
    private CheckBox task1 = new CheckBox("(Empty)");
    private CheckBox task2 = new CheckBox("(Empty)");
    private CheckBox task3 = new CheckBox("(Empty)");
    private CheckBox task4 = new CheckBox("(Empty)");
    private CheckBox task5 = new CheckBox("(Empty)");
    private Button reset = new Button("Reset");
    private ProgressBar expBar = new ProgressBar(100);
    private Controller soundBoard = new Controller();
    @Override
    public void start(Stage primaryStage) throws FileNotFoundException {
        GridPane root = new GridPane(); Character character = new Character(JACK); character.checkForLevelUp();
        //borrowed code :D source in Controller; calling the method makes the intial opening not as smooth so i guess this happens
        int maxStatus = 12; IntegerProperty statusCountProperty = new SimpleIntegerProperty(1);
        Timeline task = new Timeline(new KeyFrame(Duration.ZERO,new KeyValue(expBar.progressProperty(), 0)), new KeyFrame(Duration.seconds(2),new KeyValue(expBar.progressProperty(),
                (double)(character.getExp()-character.getExpPoints()[character.getLevel()])/(character.getExpPoints()[character.getLevel()+1]-character.getExpPoints()[character.getLevel()])) ));
        Timeline timelineBar = new Timeline(new KeyFrame(Duration.millis(ANIMATIONSPEED),new KeyValue(statusCountProperty, maxStatus)));
        timelineBar.setCycleCount(Timeline.INDEFINITE);  timelineBar.play();
        statusCountProperty.addListener((ov, statusOld, statusNewNumber) -> {int statusNew = statusNewNumber.intValue();
            expBar.pseudoClassStateChanged(PseudoClass.getPseudoClass("status" + statusOld.intValue()), false);expBar.pseudoClassStateChanged(PseudoClass.getPseudoClass("status" + statusNew), true);
        }); task.playFromStart();
        //continuation of my code
        root.getStylesheets().add(getClass().getResource("Style.css").toExternalForm());
        expBar.getStyleClass().add("expBar"); root.setVgap(15);root.setAlignment(Pos.CENTER);
        FadeTransition fade = new FadeTransition();
        fade.setToValue(1); fade.setDuration(Duration.millis(500)); fade.setFromValue(10); fade.setToValue(0); fade.setNode(system);

        root.add(levelText,0,0); levelText.setStyle("-fx-font: 18 arial;");
        root.add(expBar,2,0); expBar.setStyle("-fx-font: 18 arial;");
        root.add(expText,2,0); expText.setStyle("-fx-font: 12 arial;");
        root.add(system,2,1); system.setStyle("-fx-font: 12 arial;");
        root.add(task1,0,1);root.add(task2,0,2);root.add(task3,0,3);root.add(task4,0,4);root.add(task5,0,5);root.add(reset,0,6);

        reset.setOnAction(e->{
            task1.setDisable(false);task2.setDisable(false);task3.setDisable(false);task4.setDisable(false);task5.setDisable(false);
            task1.setSelected(false);task2.setSelected(false);task3.setSelected(false);task4.setSelected(false);task5.setSelected(false);
            task1.setText(character.getTask1().getDescription());task2.setText(character.getTask2().getDescription());task3.setText(character.getTask3().getDescription());
            task4.setText(character.getTask4().getDescription());task5.setText(character.getTask5().getDescription());
            character.reset();
        });

        task1.setStyle("-fx-font: 24 arial;");task2.setStyle("-fx-font: 24 arial;");task3.setStyle("-fx-font: 24 arial;");task4.setStyle("-fx-font: 24 arial;");task5.setStyle("-fx-font: 24 arial;");
        expText.setText("  Exp: "+(character.getExp()-character.getExpPoints()[character.getLevel()])+"/"+(character.getExpPoints()[character.getLevel()+1]-character.getExpPoints()[character.getLevel()]));

        task1.setText(character.getTask1().getDescription());task2.setText(character.getTask2().getDescription());task3.setText(character.getTask3().getDescription());
        task4.setText(character.getTask4().getDescription());task5.setText(character.getTask5().getDescription());levelText.setText(character.getName()+" | Level " + character.getLevel() + " | Last Updated: "  + Controller.time());
        task1.setOnAction(e->{
            if(task1.isSelected()) {
                soundBoard.playAudio("ding.wav");
                character.completeTask(character.getTask1());
                task1.setDisable(!character.getTask1().isRepeatable());
                if(character.getTask1().isRepeatable()){
                    task1.setSelected(false);
                }
                if(character.getTask1().isRepeatable()){
                    task1.setText(character.getTask1().getDescription() + ": " + character.getTask1().getTimesDone());
                }else {
                    task1.setText(character.getTask1().getDescription() + "(Done)");
                }
            }
            system.setText("+"+character.getTask1().getExp()+"xp");
            fade.play();
            levelText.setText(character.getName()+" | Level " + character.getLevel() + " | Last Updated: "  + Controller.time());
            expText.setText("  Exp: "+(character.getExp()-character.getExpPoints()[character.getLevel()])+"/"+(character.getExpPoints()[character.getLevel()+1]-character.getExpPoints()[character.getLevel()]));
            Controller.moveExpBar(expBar,ANIMATIONSPEED,(double)(character.getExp()-
                    character.getExpPoints()[character.getLevel()])/(character.getExpPoints()[character.getLevel()+1]-character.getExpPoints()[character.getLevel()]));
        });
        task2.setOnAction(e->{
            if(task2.isSelected()) {
                soundBoard.playAudio("ding.wav");
                character.completeTask(character.getTask2());
                task2.setDisable(!character.getTask2().isRepeatable());
                if(character.getTask2().isRepeatable()){
                    task2.setSelected(false);
                }
                if(character.getTask2().isRepeatable()){
                    task2.setText(character.getTask2().getDescription() + ": " + character.getTask2().getTimesDone());
                }else {
                    task2.setText(character.getTask2().getDescription() + "(Done)");
                }
            }
            system.setText("+"+character.getTask2().getExp()+"xp");
            fade.play();
            levelText.setText(character.getName()+" | Level " + character.getLevel() + " | Last Updated: "  + Controller.time());
            expText.setText("  Exp: "+(character.getExp()-character.getExpPoints()[character.getLevel()])+"/"+(character.getExpPoints()[character.getLevel()+1]-character.getExpPoints()[character.getLevel()]));
            Controller.moveExpBar(expBar,ANIMATIONSPEED,(double)(character.getExp()-
                    character.getExpPoints()[character.getLevel()])/(character.getExpPoints()[character.getLevel()+1]-character.getExpPoints()[character.getLevel()]));
        });
        task3.setOnAction(e->{
            if(task3.isSelected()) {
                soundBoard.playAudio("ding.wav");
                character.completeTask(character.getTask3());
                task3.setDisable(!character.getTask3().isRepeatable());
                if(character.getTask3().isRepeatable()){
                    task3.setSelected(false);
                }
                if(character.getTask3().isRepeatable()){
                    task3.setText(character.getTask3().getDescription() + ": " + character.getTask3().getTimesDone());
                }else {
                    task3.setText(character.getTask3().getDescription() + "(Done)");
                }
            }
            system.setText("+"+character.getTask3().getExp()+"xp");
            fade.play();
            levelText.setText(character.getName()+" | Level " + character.getLevel() + " | Last Updated: "  + Controller.time());
            expText.setText("  Exp: "+(character.getExp()-character.getExpPoints()[character.getLevel()])+"/"+(character.getExpPoints()[character.getLevel()+1]-character.getExpPoints()[character.getLevel()]));
            Controller.moveExpBar(expBar,ANIMATIONSPEED,(double)(character.getExp()-
                    character.getExpPoints()[character.getLevel()])/(character.getExpPoints()[character.getLevel()+1]-character.getExpPoints()[character.getLevel()]));
        });
        task4.setOnAction(e->{
            if(task4.isSelected()) {
                soundBoard.playAudio("ding.wav");
                character.completeTask(character.getTask4());
                task4.setDisable(!character.getTask4().isRepeatable());
                if(character.getTask4().isRepeatable()){
                    task4.setSelected(false);
                }
                if(character.getTask4().isRepeatable()){
                    task4.setText(character.getTask4().getDescription() + ": " + character.getTask4().getTimesDone());
                }else {
                    task4.setText(character.getTask4().getDescription() + "(Done)");
                }
            }
            system.setText("+"+character.getTask4().getExp()+"xp");
            fade.play();
            levelText.setText(character.getName()+" | Level " + character.getLevel() + " | Last Updated: "  + Controller.time());
            expText.setText("  Exp: "+(character.getExp()-character.getExpPoints()[character.getLevel()])+"/"+(character.getExpPoints()[character.getLevel()+1]-character.getExpPoints()[character.getLevel()]));
            Controller.moveExpBar(expBar,ANIMATIONSPEED,(double)(character.getExp()-
                    character.getExpPoints()[character.getLevel()])/(character.getExpPoints()[character.getLevel()+1]-character.getExpPoints()[character.getLevel()]));
        });
        task5.setOnAction(e->{
            if(task5.isSelected()) {
                soundBoard.playAudio("ding.wav");
                character.completeTask(character.getTask5());
                task5.setDisable(!character.getTask5().isRepeatable());
                if(character.getTask5().isRepeatable()){
                    task5.setSelected(false);
                }
                if(character.getTask5().isRepeatable()){
                    task5.setText(character.getTask5().getDescription() + ": " + character.getTask5().getTimesDone());
                }else {
                    task5.setText(character.getTask5().getDescription() + "(Done)");
                }
            }
            system.setText("+"+character.getTask5().getExp()+"xp");
            fade.play();
            levelText.setText(character.getName()+" | Level " + character.getLevel() + " | Last Updated: "  + Controller.time());
            expText.setText("  Exp: "+(character.getExp()-character.getExpPoints()[character.getLevel()])+"/"+(character.getExpPoints()[character.getLevel()+1]-character.getExpPoints()[character.getLevel()]));
            Controller.moveExpBar(expBar,ANIMATIONSPEED,(double)(character.getExp()-
                    character.getExpPoints()[character.getLevel()])/(character.getExpPoints()[character.getLevel()+1]-character.getExpPoints()[character.getLevel()]));
        });


        primaryStage.setTitle(TITLE);
        primaryStage.setScene(new Scene(root, WIDTH, HEIGHT));
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
