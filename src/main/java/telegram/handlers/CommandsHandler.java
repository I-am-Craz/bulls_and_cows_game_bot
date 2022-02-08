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

public class CommandsHandler extends TelegramLongPollingCommandBot {

    private static final String BOT_NAME = "bUlLs_AnD_cOwS_aNoThEr_gAmE_BoT";

    public CommandsHandler(){
        register(new StartCommand());
        register(new PlayCommand());
        register(new GiveUpCommand());
        register(new HintCommand());
        register(new HelpCommand());
        register(new SettingsCommand());

        registerDefaultAction(((absSender, message) -> {
            sendMessageToUser(
                    absSender,
                    LocalisationService.getString("userInputError"),
                    message.getChatId().toString());
        }));

    }

    @Override
    public void processNonCommandUpdate(Update update) {
        String userMessage = update.getMessage().getText();
        String chatId =  update.getMessage().getChatId().toString();

        SendMessage messageSender = new SendMessage();
        messageSender.setChatId(chatId);

        try{
            if(userMessage.equals("English")){
                LocalisationService.currentLanguage = "en";
                messageSender.setText("The language has been changed.");
                messageSender.setReplyMarkup(new ReplyKeyboardRemove(true));
                try{
                    execute(messageSender);
                }catch (TelegramApiException e){}
            }
            if(userMessage.equals("Russian")){
                LocalisationService.currentLanguage = "ru";
                messageSender.setText("Язык изменен.");
                messageSender.setReplyMarkup(new ReplyKeyboardRemove(true));
                try{
                    execute(messageSender);
                }catch (TelegramApiException e){}
            }
            if(Integer.parseInt(userMessage) > 999 && Integer.parseInt(userMessage) < 10000){
                Game.userAnswer = Integer.parseInt(userMessage);
                if(Game.getBulls(Game.target, Game.userAnswer) == 4){
                    sendMessageToUser(messageSender,
                            LocalisationService.getString("gameResults") +
                            (Game.tryCounter + 1) + ".\n" +
                            LocalisationService.getString("hintsUsed") +
                            (4 - Game.hintCount) + ".\n" +
                            LocalisationService.getString("letsPlayAgain"));
                    Game.userAnswer = 0;
                    Game.target = Game.getRandomNum();
                    Game.tryCounter = 0;
                    Game.hintCount = 4;
                    Game.hintStringBuilder = new StringBuilder("****");
                }
                else {
                    sendMessageToUser(messageSender, LocalisationService.getString("cows") +
                            Game.getCows(Game.target, Game.userAnswer) + "\n" +
                            LocalisationService.getString("bulls") +
                            Game.getBulls(Game.target, Game.userAnswer));
                    Game.tryCounter += 1;
                }
            } else {
                sendMessageToUser(messageSender, LocalisationService.getString("userInputError"));
            }

        }catch (NumberFormatException e){
            System.out.println(e.getMessage());
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

    public static void sendMessageToUser(AbsSender absSender, String message, String chatId){
        SendMessage  messageSender = new SendMessage();
        messageSender.setText(message);
        messageSender.setChatId(chatId);
        try{
            absSender.execute(messageSender);
        } catch(TelegramApiException ex){
            System.out.println(ex.getMessage());
        }
    }

    public void sendMessageToUser(SendMessage messageSender, String message){
        messageSender.setText(message);
        try{
            execute(messageSender);
        } catch(TelegramApiException ex){
            System.out.println(ex.getMessage());
        }
    }
}
