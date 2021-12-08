package org.telegram;
import java.util.ArrayList;

public class Game {

    public static int getRandomNum(){
        int num = (int) Math.floor(Math.random() * 10000);
        if(num < 1000){
            return num + 1000;
        } else {
            return num;
        }
    }

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