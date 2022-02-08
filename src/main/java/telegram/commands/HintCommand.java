package telegram.commands;

import game.Game;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import telegram.handlers.CommandsHandler;
import telegram.services.LocalisationService;

public class HintCommand extends BotCommand {
    public HintCommand(){
        super("hint", "Get a hint.");
    }
    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings){

        String chatId = chat.getId().toString();
        if(Game.target != 0 && Game.hintCount != 0){
            getHint(Game.hintStringBuilder, absSender, chatId, getNumIndex(absSender, chatId, strings));
            Game.hintCount--;
        } else if(Game.hintCount == 0){
            CommandsHandler.sendMessageToUser(absSender,
                    LocalisationService.getString("noHints"),
                    chatId);
        }
        else {
            CommandsHandler.sendMessageToUser(absSender,
                    LocalisationService.getString("hintGettingError"),
                    chatId);
        }

    }

    public static void getHint(StringBuilder sb, AbsSender absSender, String chatId, int position){
        try{
            sb.setCharAt(position - 1, String.valueOf(Game.target).charAt(position - 1));
            CommandsHandler.sendMessageToUser(absSender, sb.toString(), chatId);
        }
        catch (StringIndexOutOfBoundsException e){}
    }

    public static int getNumIndex(AbsSender absSender, String chatId,  String[] strings){
        if(strings == null || strings.length == 0){
            CommandsHandler.sendMessageToUser(absSender,
                    LocalisationService.getString("positionNotEnteredError"),
                    chatId);
            return -1;
        } else if(Integer.parseInt(String.join("", strings)) <= 0
                  || Integer.parseInt(String.join("", strings)) > 4){
            CommandsHandler.sendMessageToUser(absSender,
                    LocalisationService.getString("noSuchPosition"),
                    chatId);
            return -1;
        }
        else {
            return Integer.parseInt(String.join("", strings));
        }
    }
}
