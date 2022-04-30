package telegram.commands;

import game.Game;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import telegram.handlers.CommandsHandler;
import telegram.services.LocalisationService;

public class PlayCommand extends BotCommand {
    private static LocalisationService localisationService = LocalisationService.getInstance();
    public PlayCommand(){
        super("play", "Start a new game.");
    }
    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings){
        Game.userAnswer = 0;
        Game.target = Game.getRandomNum();
        Game.tryCounter = 0;
        Game.hintCount = 4;
        Game.hintStringBuilder = new StringBuilder("****");

        CommandsHandler.sendMessageToUser(absSender,
                localisationService.getString("gameStart"),
                chat.getId().toString());
    }

}
