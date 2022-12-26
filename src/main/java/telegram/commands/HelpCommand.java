package telegram.commands;

import game.GameChat;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import telegram.handlers.CommandsHandler;
import telegram.services.ChatService;
import telegram.services.LocalisationService;

public class HelpCommand  extends BotCommand {
    public HelpCommand(){
        super("help", "Get a list of all commands.");
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
            CommandsHandler.sendMessageToUser(absSender,
                    LocalisationService.getString("helpCommand", gameChat.getCurrentLanguageCode()),
                    chat.getId().toString());
        }
    }
}
