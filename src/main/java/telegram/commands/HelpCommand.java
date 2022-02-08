package telegram.commands;

import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import telegram.handlers.CommandsHandler;
import telegram.services.LocalisationService;

public class HelpCommand  extends BotCommand {
    public HelpCommand(){
        super("help", "Get a list of all commands.");
    }
    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings){
        CommandsHandler.sendMessageToUser(absSender,
                LocalisationService.getString("helpCommand"),
                chat.getId().toString());
    }
}
