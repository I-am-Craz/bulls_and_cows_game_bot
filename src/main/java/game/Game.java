package game;

import java.util.ArrayList;
import java.util.logging.Logger;

public class Game extends Thread{
    private final static Logger logger = Logger.getLogger("main");
    private final long id;
    private int playerAnswer;
    private int target;
    private int playerAttemptsCount;
    private int hintsCount;
    private StringBuilder hintString;
    private boolean playerGaveUp;
    private boolean isStarted;
    private boolean isFinished;

    public Game(long id) {
        this.id = id;
        this.playerAnswer = 0;
        this.target = generateRandomNum();
        this.playerAttemptsCount = 0;
        this.hintsCount = 4;
        this.hintString = new StringBuilder("****");
        this.isStarted = false;
        this.isFinished = false;
    }

    public int getPlayerAnswer() {
        return playerAnswer;
    }
    public void setPlayerAnswer(int playerAnswer) {
        this.playerAnswer = playerAnswer;
    }

    public int getPlayerAttemptsCount() {
        return playerAttemptsCount;
    }

    public void incrementPlayerAttemptsCount() {
        this.playerAttemptsCount++;
    }

    public StringBuilder getHintString() {
        return hintString;
    }

    public int getHintsCount(){
        return hintsCount;
    }

    public void decrementHintsCount(){
        this.hintsCount--;
    }

    public int getTarget() {
        return target;
    }

    public boolean isPlayerGaveUp() {
        return playerGaveUp;
    }

    public boolean isStarted() {
        return isStarted;
    }

    public void setStarted(boolean started) {
        isStarted = started;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }

    /**
     * Cows - the number of digits guessed by the user
     * without matching their position in the
     * computer's guessed number
     */
    public int getCows(int userNumber){
        int cows = 0;

        String botNumberAsString = String.valueOf(target);
        String userNumberAsString = String.valueOf(userNumber);

        for(int i = 0; i < userNumberAsString.length(); i++){
            String currentNumberAsString = String.valueOf(userNumberAsString.charAt(i));
            if(botNumberAsString.contains(currentNumberAsString) &&
                    botNumberAsString.indexOf(currentNumberAsString) !=
                            userNumberAsString.indexOf(currentNumberAsString)){
                cows++;
            }
        }

        return cows;
    }

    /**
     * Bulls - the number of digits guessed with
     * matching their position
     */
    public int getBulls(int userNumber){
        int bulls = 0;

        String botNumberAsString = String.valueOf(target);
        String userNumberAsString = String.valueOf(userNumber);

        for(int i = 0; i < botNumberAsString.length(); i++){
            String currentNumberAsString = String.valueOf(userNumberAsString.charAt(i));
            if(botNumberAsString.contains(currentNumberAsString) &&
                    botNumberAsString.indexOf(currentNumberAsString) ==
                            userNumberAsString.indexOf(currentNumberAsString)){
                bulls++;
            }
        }
        return bulls;
    }

    private int generateRandomNum() {
        ArrayList<Integer> numbers = new ArrayList<>();
        //The number must be four-digit
        while(numbers.size() < 4){
            int number = (int) Math.floor(Math.random() * 10);
            //The digits of number mustn't repeat
            if(!numbers.contains(number)){
                if(numbers.size() == 0 && number == 0){
                    numbers.add(1);
                } else {
                    numbers.add(number);
                }
            }
        }

        StringBuilder stringBuilder = new StringBuilder();

        for(int number : numbers){
            stringBuilder.append(number);
        }

        return Integer.parseInt(stringBuilder.toString());
    }

    public int giveUp(){
        if(this.target != 0 ){
            this.playerGaveUp = true;
            return this.target;
        } else {
            return -1;
        }
    }

    public void reset(){
        logger.info("The Game with ID " + this.id + " was restarted. The target is " + this.getTarget());
        this.playerAnswer = 0;
        this.target = generateRandomNum();
        this.playerAttemptsCount = 0;
        this.hintsCount = 4;
        this.hintString = new StringBuilder("****");
    }

    @Override
    public void run(){
        logger.info("The target of the Game with ID " + this.id + " is " + this.getTarget());
        while(this.playerAnswer != target && !this.playerGaveUp) {
            if (this.playerAnswer != 0) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    logger.finer(e.getMessage());
                }
            }
        }
    }
}
