package telegram.commands;

import game.Game;
import game.GameChat;
import telegram.services.ChatService;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import telegram.handlers.CommandsHandler;
import telegram.services.LocalisationService;
import java.util.logging.Logger;

public class HintCommand extends BotCommand {

    public HintCommand(){
        super("hint", "Get a hint.");
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings){
        Long chatId = chat.getId();
        GameChat gameChat = ChatService.getGameChats().get(chat.getId());

        if(gameChat == null){
            CommandsHandler.sendMessageToUser(absSender,
                    "You need to enter the /start command to initialize the game.\n\n" +
                            "Для инициализации игры необходимо ввести команду /start.",
                    chat.getId().toString());
        }
        else{
            Game game = gameChat.getGame();

            if(game.isStarted() && game.getTarget() != 0 && game.getHintsCount() != 0){
                getHint(game, absSender, chatId.toString(),
                        getHintPosition(absSender, gameChat, strings));

                game.decrementHintsCount();
            }
            else if(game.isStarted() &&  game.getHintsCount() == 0){
                CommandsHandler.sendMessageToUser(absSender,
                        LocalisationService.getString("noHints", gameChat.getCurrentLanguageCode()),
                        chatId.toString());
            }
            else {
                CommandsHandler.sendMessageToUser(absSender,
                        LocalisationService.getString("hintGettingError", gameChat.getCurrentLanguageCode()),
                        chatId.toString());
            }
        }
    }

    public static void getHint(Game game, AbsSender absSender, String chatId, int position){
        try{
            game.getHintString().setCharAt(position - 1,
                    String.valueOf(game.getTarget()).charAt(position - 1));

            CommandsHandler.sendMessageToUser(absSender, game.getHintString().toString(), chatId);
        }
        catch(StringIndexOutOfBoundsException e){
            Logger.getLogger("main").finer(e.getMessage());
        }
    }

    public static int getHintPosition(AbsSender absSender, GameChat chat,  String[] strings){
        int position = -1;
        if(strings == null || strings.length == 0){
            CommandsHandler.sendMessageToUser(absSender,
                    LocalisationService.getString("positionNotEnteredError", chat.getCurrentLanguageCode()),
                    chat.getId().toString());
        }
        else if(Integer.parseInt(String.join("", strings)) <= 0
                || Integer.parseInt(String.join("", strings)) > 4){
            CommandsHandler.sendMessageToUser(absSender,
                    LocalisationService.getString("noSuchPosition", chat.getCurrentLanguageCode()),
                    chat.getId().toString());
        }
        else {
            position = Integer.parseInt(String.join("", strings));
        }
        return position;
    }
}
