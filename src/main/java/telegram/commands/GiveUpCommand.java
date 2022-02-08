package telegram.commands;

import game.Game;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import telegram.handlers.CommandsHandler;
import telegram.services.LocalisationService;

public class GiveUpCommand extends BotCommand {
    public GiveUpCommand(){
        super("give_up", "Find out the number the bot guessed.");
    }
    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings){
        String chatId  = chat.getId().toString();
        if(Game.target != 0 ){
           CommandsHandler.sendMessageToUser(
                   absSender,
                   LocalisationService.getString("giveUp") + Game.target
                    + "\n" + LocalisationService.getString("letsPlayAgain"),
                   chatId);
        } else {
            CommandsHandler.sendMessageToUser(absSender,
                    LocalisationService.getString("giveUpError"),
                    chatId);
        }
    }
}
