package telegram.commands;

import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import telegram.handlers.CommandsHandler;

public class StartCommand extends BotCommand {
    public StartCommand(){
        super("start", "Start the bot.");
    }
    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings){
        CommandsHandler.sendMessageToUser(absSender,
                "Hi. To change the language, type /settings," +
                "\n/play - to start a new game," +
                "\n/help - to list all commands." +
                "\n\nПривет. Чтобы сменить язык  введи /settings," +
                "\n/play - чтобы начать новую игру," +
                "\n/help - чтобы посмотреть список всех команд.",
                chat.getId().toString());
    }
}
