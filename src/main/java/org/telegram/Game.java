package org.telegram;
import java.util.ArrayList;

public class Game {

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

        StringBuilder sb = new StringBuilder();

        for(int number : numbers){
            sb.append(number).append("");
        }

        return Integer.valueOf(sb.toString());

    }

    /*
     * Cows - the number of digits guessed by the user
     * without matching their position in the
     * computer's guessed number
     *
     */

    public static int getCows(int BotNumber, int userNumber){
        ArrayList<String> cows = new ArrayList<>();

        String BotNumStr = BotNumber + "";
        String userNumStr = userNumber + "";

        for(int i = 0; i < userNumStr.length(); i++){
            String currentNumInStr = "" + userNumStr.charAt(i);
            if(BotNumStr.contains(currentNumInStr) && BotNumStr.indexOf(currentNumInStr) != userNumStr.indexOf(currentNumInStr)){
                cows.add(currentNumInStr);
            }
        }

        return cows.size();
    }

    /*
     * Bulls - the number of digits guessed with
     * matching their position
     *
     */

    public static int getBulls(int BotNumber, int userNumber){
        ArrayList<String> bulls = new ArrayList<>();

        String BotNumStr = BotNumber + "";
        String userNumStr = userNumber + "";

        for(int i = 0; i < userNumStr.length(); i++){
            String currentNumInStr = "" + userNumStr.charAt(i);
            if(BotNumStr.contains(currentNumInStr) && BotNumStr.indexOf(currentNumInStr) == userNumStr.indexOf(currentNumInStr)){
                bulls.add(currentNumInStr);
            }
        }

        return bulls.size();
    }
}
