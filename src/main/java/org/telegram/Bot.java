package org.telegram;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

public class Bot extends TelegramLongPollingBot {

    private static final String BOT_NAME = "bUlLs_AnD_cOwS_aNoThEr_gAmE_BoT";
    private static String lang = "en";
    private static int userAnswer = 0;//four-digit number
    private static int target =  0;//the number that a user must guess
    private static int tryCounter = 1;
    private static int hintCount = 4;

    @Override
    public void onUpdateReceived(Update update){

        String command = update.getMessage().getText();

        SendMessage botMessageSender = new SendMessage();
        botMessageSender.setChatId(String.valueOf(update.getMessage().getChatId()));

        if(command.equals("/start")){

            sendMessageToUser(botMessageSender, "Hi. To change the language, type /settings,\n /play - to start a new game,\n /help - to list all commands.\n\nПривет. Чтобы сменить язык  введи /settings,\n /play - чтобы начать новую игру,\n /help - чтобы посмотреть список всех команд.");

        }
        else if(command.equals("/play")){

            userAnswer = 0;
            target = Game.getRandomNum();
            tryCounter = 0;
            hintCount = 4;

            sendMessageToUser(botMessageSender, Language.GAME_START.getPhrase(lang));

        }
        else if(command.equals("/give_up")){

            if(target != 0 ){
                sendMessageToUser(botMessageSender, Language.GIVE_UP.getPhrase(lang) + "\n" + Language.LETS_PLAY_AGAIN.getPhrase(lang));
            } else {
                sendMessageToUser(botMessageSender, Language.GETTING_HINT_ERROR1.getPhrase(lang));
            }

        }
        else if(command.equals("/hint")){

            if(target != 0 ){
                getHint(botMessageSender, hintCount);
                hintCount--;
            } else {
                sendMessageToUser(botMessageSender, Language.GETTING_HINT_ERROR2.getPhrase(lang));
            }

        }
        else if(command.equals("/help")){

            sendMessageToUser(botMessageSender, Language.HELP.getPhrase(lang));

        }
        else if(command.equals("/settings")){

            setLanguage(botMessageSender);

        }
        else if(command.equals("Russian")){

            lang = "ru";
            botMessageSender.setText("Язык изменен.");
            botMessageSender.setReplyMarkup(new ReplyKeyboardRemove(true));

            try{
                execute(botMessageSender);
            } catch (TelegramApiException ex){
                System.out.println(ex.getMessage());
            }

        }
        else if(command.equals("English")){

            lang = "en";
            botMessageSender.setText("The language has been changed.");
            botMessageSender.setReplyMarkup(new ReplyKeyboardRemove(true));

            try{
                execute(botMessageSender);
            } catch (TelegramApiException ex){
                System.out.println(ex.getMessage());
            }

        }
        else if(Integer.parseInt(command) > 999 & Integer.parseInt(command) < 10000){

            userAnswer = Integer.parseInt(command);

            if(Game.getBulls(target, userAnswer) == 4){
                sendMessageToUser(botMessageSender,Language.GAME_RESULT.getPhrase(lang) + (tryCounter + 1) + "." + Language.HINTS_USED.getPhrase(lang) + (4 - hintCount) + "." + "\n" + Language.LETS_PLAY_AGAIN.getPhrase(lang));
                userAnswer = 0;
                target = Game.getRandomNum();
                tryCounter = 0;
                hintCount = 4;
            }
            else {
                sendMessageToUser(botMessageSender, Language.COWS.getPhrase(lang) + Game.getCows(target, userAnswer) + "\n" + Language.BULLS.getPhrase(lang) + Game.getBulls(target, userAnswer));
                tryCounter += 1;
            }

        }
        else{
            sendMessageToUser(botMessageSender, Language.ERROR.getPhrase(lang));
        }

    }

    @Override
    public String getBotToken(){
        return System.getenv("BOT_TOKEN");
    }

    @Override
    public String getBotUsername(){
        return Bot.BOT_NAME;
    }

    public void sendMessageToUser(SendMessage sm, String message){
        sm.setText(message);
        try{
            execute(sm);
        } catch(TelegramApiException ex){
            System.out.println(ex.getMessage());
        }
    }

    public String getHint(SendMessage sm, int hintCount){
        String hint = null;
        if(hintCount != 0){
            sendMessageToUser(sm, String.valueOf(target).substring(0, 4 - (hintCount - 1)) + "****".substring(0, hintCount - 1));
        }
        return hint;
    }

    public void setLanguage(SendMessage sm){
        sm.setText("...");
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sm.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow keyboardFirstRow = new KeyboardRow();
        keyboardFirstRow.add(new KeyboardButton("English"));

        KeyboardRow keyboardSecondRow = new KeyboardRow();
        keyboardSecondRow.add(new KeyboardButton("Russian"));

        keyboard.add(keyboardFirstRow);
        keyboard.add(keyboardSecondRow);

        replyKeyboardMarkup.setKeyboard(keyboard);

        try{
            execute(sm);
        } catch (TelegramApiException ex){
            System.out.println(ex.getMessage());
        }

    }
}