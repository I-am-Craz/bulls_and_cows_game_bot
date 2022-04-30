package game;

import java.util.ArrayList;

public class Game {

    public static int userAnswer = 0;//four-digit number
    public static int target =  0;//the number that a user must guess
    public static int tryCounter = 1;
    public static int hintCount = 4;
    public static StringBuilder hintStringBuilder = new StringBuilder("****");

    public static int getRandomNum(){
        ArrayList<Integer> numbers = new ArrayList<>();

        //The number must be four-digit
        while(numbers.size() < 4){

            int number = (int) Math.floor(Math.random() * 10);

            //The digits of number must not repeat
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
            stringBuilder.append(number).append("");
        }

        return Integer.valueOf(stringBuilder.toString());

    }

    /**
     * Cows - the number of digits guessed by the user
     * without matching their position in the
     * computer's guessed number
     */

    public static int getCows(int botNumber, int userNumber){
        int cows = 0;

        String botNumberAsString = String.valueOf(botNumber);
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
     *
     */

    public static int getBulls(int botNumber, int userNumber){
        int bulls = 0;

        String botNumberAsString = String.valueOf(botNumber);
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
}
