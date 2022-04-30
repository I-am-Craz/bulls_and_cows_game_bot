package telegram.handlers;

import telegram.commands.*;
import game.Game;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import telegram.services.LocalisationService;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;

import java.util.ArrayList;
import java.util.List;

public class CommandsHandler extends TelegramLongPollingCommandBot{
    private static LocalisationService localisationService = LocalisationService.getInstance();
    private static final String BOT_NAME = "bUlLs_AnD_cOwS_aNoThEr_gAmE_BoT";

    private List<BotCommand> commands = new ArrayList<>();

    {
        commands.add(new StartCommand());
        commands.add(new HelpCommand());
        commands.add(new HintCommand());
        commands.add(new PlayCommand());
        commands.add(new GiveUpCommand());
        commands.add(new SettingsCommand());
        commands.add(new StartCommand());
    }

    public CommandsHandler(){
        for(BotCommand command : commands){
            register(command);
        }

        registerDefaultAction(((absSender, message) -> {
            sendMessageToUser(
                    absSender,
                    localisationService.getString("userInputError"),
                    message.getChatId().toString());
        }));
    }

    @Override
    public void processNonCommandUpdate(Update update) {
        String userMessage = update.getMessage().getText();
        String chatId =  update.getMessage().getChatId().toString();

        SendMessage messageSender = new SendMessage();
        messageSender.setChatId(chatId);

        if(userMessage.equals("English")){
            changeLanguage("en", messageSender);
        }
        if(userMessage.equals("Russian")){
            changeLanguage("ru", messageSender);
        }

        if(getEnteredNumber(userMessage) > 999 && getEnteredNumber(userMessage) < 10000){
            Game.userAnswer = getEnteredNumber(userMessage);
            if(Game.getBulls(Game.target, Game.userAnswer) == 4){
                winTheGame(messageSender);
            }
            else {
                continueTheGame(messageSender);
            }
        } else {
            sendMessageToUser(messageSender, localisationService.getString("userInputError"));
        }
    }

    @Override
    public String getBotUsername() {
        return BOT_NAME;
    }

    @Override
    public String getBotToken() {
        return System.getenv("BOT_TOKEN");
    }

    public synchronized static void sendMessageToUser(AbsSender absSender, String message, String chatId){
        SendMessage  messageSender = new SendMessage();
        messageSender.setText(message);
        messageSender.setChatId(chatId);
        try{
            absSender.execute(messageSender);
        } catch(TelegramApiException ex){
            System.out.println(ex.getMessage());
        }
    }

    public synchronized void sendMessageToUser(SendMessage messageSender, String message){
        messageSender.setText(message);
        try{
            execute(messageSender);
        } catch(TelegramApiException ex){
            System.out.println(ex.getMessage());
        }
    }

    private int getEnteredNumber(String userMessage){
        int enteredNumber = -1;
        try{
            enteredNumber = Integer.parseInt(userMessage);
        } catch (NumberFormatException e){
            System.out.println(e.getMessage());
        }
        return enteredNumber;
    }

    private void changeLanguage(String languageCode, SendMessage  messageSender) {
        localisationService.setCurrentLanguage(languageCode);
        messageSender.setText(localisationService.getString("languageChange"));
        messageSender.setReplyMarkup(new ReplyKeyboardRemove(true));
        try {
            execute(messageSender);
        } catch (TelegramApiException e) {
        }
    }

    private void winTheGame(SendMessage messageSender){
        sendMessageToUser(messageSender,
                localisationService.getString("gameResults") +
                        (Game.tryCounter + 1) + ".\n" +
                        localisationService.getString("hintsUsed") +
                        (4 - Game.hintCount) + ".\n" +
                        localisationService.getString("letsPlayAgain"));
        Game.userAnswer = 0;
        Game.target = Game.getRandomNum();
        Game.tryCounter = 0;
        Game.hintCount = 4;
        Game.hintStringBuilder = new StringBuilder("****");
    }

    private void continueTheGame(SendMessage messageSender){
        sendMessageToUser(messageSender, localisationService.getString("cows") +
                Game.getCows(Game.target, Game.userAnswer) + "\n" +
                localisationService.getString("bulls") +
                Game.getBulls(Game.target, Game.userAnswer));
        Game.tryCounter += 1;
    }
}
