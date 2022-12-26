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

public class PlayCommand extends BotCommand {
    public PlayCommand(){
        super("play", "Start a new game.");
    }
    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings){
        GameChat gameChat = ChatService.getGameChats().get(chat.getId());
        if(gameChat == null){
            CommandsHandler.sendMessageToUser(absSender,
            "You need to enter the /start command to initialize the game.\n\n" +
                    "Для инициализации игры необходимо ввести команду /start.",
                    chat.getId().toString());
        }
        else{
            Game game = gameChat.getGame();

            if(!game.isStarted() && !game.isFinished()) {
                gameChat.startGame();
                game.setStarted(true);
                CommandsHandler.sendMessageToUser(absSender,
                        LocalisationService.getString("gameStarts", gameChat.getCurrentLanguageCode()),
                        chat.getId().toString());
            }
            else if(game.isStarted()){
                game.reset();
                CommandsHandler.sendMessageToUser(absSender,
                        LocalisationService.getString("restarted", gameChat.getCurrentLanguageCode()),
                        chat.getId().toString());
            }
            else if(game.isFinished()){
                CommandsHandler.sendMessageToUser(absSender,
                        LocalisationService.getString("startCommand", gameChat.getCurrentLanguageCode()),
                        chat.getId().toString());
            }
            else CommandsHandler.sendMessageToUser(absSender,
                    LocalisationService.getString("gameHasStarted", gameChat.getCurrentLanguageCode()),
                    chat.getId().toString());
        }
    }
}
