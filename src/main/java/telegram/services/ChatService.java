package telegram.services;

import game.Game;
import game.GameChat;
import java.util.HashMap;
import java.util.Map;

public class ChatService{
    private static final Map<Long, GameChat> gameChats = new HashMap<>();

    public static void addGameChat(GameChat chat){
        if(chat.getGame() == null){
            chat.setGame(new Game(chat.getId()));
        }
        gameChats.put(chat.getId(), chat);
    }

    public static Map<Long, GameChat> getGameChats(){
        return gameChats;
    }
}
