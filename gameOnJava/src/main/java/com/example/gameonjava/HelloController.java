package com.example.gameonjava;

import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;

import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

public class HelloController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ImageView bg1 , bg2 , player, enemy1, pause, wasted, playAgain, player1, enemyB;



    private TranslateTransition enemyTransition;

    private TranslateTransition enemyTransition2;

    private TranslateTransition enemyTransitionBorder;

    private final int BG_WIDTH = 421;

    private final double playerX = 2.0;
    private final double playerY = 214.0;

    private final double player1X = 73.0;
    private final double player1Y = 222.0;



    private ParallelTransition parallelTransition;

    public static boolean right = false;

    public static boolean jump = false;
    public static boolean left = false;

    public static boolean jPause = false;









    private int playerSpeed = 2, jumpSpeed = 3;




    AnimationTimer timer = new AnimationTimer() {
        @Override
        public void handle(long l) {



            if(jump && player.getLayoutY() > 90f){
                player.setLayoutY(player.getLayoutY() - playerSpeed);
                player1.setLayoutY(player1.getLayoutY() - playerSpeed);
                System.out.println(jump);
            }
            else if(player.getLayoutY() <= 210f) {
                jump = false;
                player.setLayoutY(player.getLayoutY() + jumpSpeed);
                player1.setLayoutY(player1.getLayoutY() + jumpSpeed);
            }

            if(right && player.getLayoutX() < 100f){
                player.setLayoutX(player.getLayoutX() + playerSpeed);
                player1.setLayoutX(player1.getLayoutX() + playerSpeed);

            }
            if(left && player.getLayoutX() > 28f){
                player.setLayoutX(player.getLayoutX() - playerSpeed);
                player1.setLayoutX(player1.getLayoutX() - playerSpeed);


            }



            if(jPause && !pause.isVisible()){
                pause.setVisible(true);
                playerSpeed = 0;
                jumpSpeed = 0;
                parallelTransition.pause();
                enemyTransition.pause();
                enemyTransitionBorder.pause();

            }
            else if(!jPause && pause.isVisible()){
                pause.setVisible(false);
                playerSpeed = 2;
                jumpSpeed = 5;
                parallelTransition.play();
                enemyTransition.play();
                enemyTransitionBorder.play();

            }


            boolean isCollisionWithEnemyB = player1.getBoundsInParent().intersects(enemyB.getBoundsInParent());


            if (isCollisionWithEnemyB){
                // Pause the game
                playerSpeed = 0;
                jumpSpeed = 0;
                player.setLayoutX(playerX);
                player.setLayoutY(playerY);
                player1.setLayoutX(player1X);
                player1.setLayoutY(player1Y);

                parallelTransition.pause();
                enemyTransition.pause();
                enemyTransitionBorder.pause();
                pause.setVisible(false);

                wasted.setVisible(true);
                playAgain.setVisible(true);

            }

            if (playAgain.isVisible()) {
                playAgain.setOnMouseClicked(e -> {

                    wasted.setVisible(false);
                    playAgain.setVisible(false);
                    jump = false;

                    parallelTransition.playFromStart();
                    enemyTransition.playFromStart();
                    enemyTransitionBorder.playFromStart();


                    playerSpeed = 2;
                    jumpSpeed = 5;


                    player.setLayoutX(playerX);
                    player.setLayoutY(playerY);
                    player1.setLayoutX(player1X);
                    player1.setLayoutY(player1Y);



                });
            }





        }
    };

    @FXML
    void initialize() {

        TranslateTransition bgOneTransition = new TranslateTransition(Duration.millis(5000), bg1);
        bgOneTransition.setFromX(0);
        bgOneTransition.setToX(BG_WIDTH * -1);
        bgOneTransition.setInterpolator(Interpolator.LINEAR);

        TranslateTransition bgTwoTransition = new TranslateTransition(Duration.millis(5000), bg2);
        bgTwoTransition.setFromX(0);
        bgTwoTransition.setToX(BG_WIDTH * -1);
        bgTwoTransition.setInterpolator(Interpolator.LINEAR);

        enemyTransition = new TranslateTransition(Duration.millis(3500), enemy1);
        enemyTransition.setFromX(0);
        enemyTransition.setToX(BG_WIDTH * -1 - 80);
        enemyTransition.setInterpolator(Interpolator.LINEAR);
        enemyTransition.setCycleCount(Animation.INDEFINITE);
        enemyTransition.play();

        enemyTransitionBorder = new TranslateTransition(Duration.millis(3500), enemyB);
        enemyTransitionBorder.setFromX(0);
        enemyTransitionBorder.setToX(BG_WIDTH * -1 - 80);
        enemyTransitionBorder.setInterpolator(Interpolator.LINEAR);
        enemyTransitionBorder.setCycleCount(Animation.INDEFINITE);
        enemyTransitionBorder.play();


        parallelTransition  = new ParallelTransition(bgOneTransition, bgTwoTransition);
        parallelTransition.setCycleCount(Animation.INDEFINITE);
        parallelTransition.play();

        timer.start();
    }

}