package telegram.commands;

import game.Game;
import game.GameChat;
import telegram.services.ChatService;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import telegram.handlers.CommandsHandler;
import telegram.services.KeyboardService;
import telegram.services.LocalisationService;

public class GiveUpCommand extends BotCommand {
    public GiveUpCommand(){
        super("give_up", "Find out the number the bot guessed.");
    }
    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings){
        GameChat gameChat = ChatService.getGameChats().get(chat.getId());

        if(gameChat == null) CommandsHandler.sendMessageToUser(absSender,
                    "You need to enter the /start command to initialize the game.\n\n" +
                            "Для инициализации игры необходимо ввести команду /start.",
                             chat.getId().toString());
        else{
            Game game = gameChat.getGame();
            String chatId  = chat.getId().toString();
            if(game.isStarted() &&  game.getTarget() != 0 ){
                CommandsHandler.sendMessageToUser(absSender,
                    LocalisationService.getString("giveUp", gameChat.getCurrentLanguageCode())
                            + " " + game.giveUp() +
                            "\n" + LocalisationService.getString("letsPlayAgain", gameChat.getCurrentLanguageCode()),
                            chatId);
                KeyboardService.createKeyBoard(absSender, chat.getId().toString(),
                        LocalisationService.getString("yes", gameChat.getCurrentLanguageCode()),
                        LocalisationService.getString("no", gameChat.getCurrentLanguageCode()));
            }
            else CommandsHandler.sendMessageToUser(absSender,
                        LocalisationService.getString("giveUpError", gameChat.getCurrentLanguageCode()),
                        chatId);
        }
    }
}
