package org.telegram;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class Bot extends TelegramLongPollingBot {

    int userAnswer = 0;
    int target =  0;
    int tryCounter = 1;
    int hintCount = 4;

    @Override
    public void onUpdateReceived(Update update){

        String command = update.getMessage().getText();

        SendMessage botMessageSender = new SendMessage();
        botMessageSender.setChatId(String.valueOf(update.getMessage().getChatId()));

        if(command.equals("/start")){

            sendMessageToUser(botMessageSender, "Привет! Чтобы начать новую игру введи команду /play. Хочешь посмотреть список всех команд - введи /help .");

        }
        else if(command.equals("/play")){

            userAnswer = 0;
            target = Game.getRandomNum();
            tryCounter = 0;
            hintCount = 4;

            sendMessageToUser(botMessageSender, "Хочешь начать новую игру? Поехали! Вводи четырёхзначное число.");

        }
        else if(command.equals("/give_up")){

            if(target != 0 ){
                sendMessageToUser(botMessageSender, "Что, сдаёшься так быстро?)) Было загадано число: " + target + "\n" + "Сыграем ещё?");
            } else {
                sendMessageToUser(botMessageSender, "Мы ёще даже не начали новую игру, а ты уже сдаёшься...Чтобы начать новую игру используй команду /play . ");
            }

        }
        else if(command.equals("/hint")){

            if(target != 0 ){
                getHint(botMessageSender, hintCount);
                hintCount--;
            } else {
                sendMessageToUser(botMessageSender, " Я не могу создать подсказку из ничего. Чтобы её получить нужно начать новую игру ( /play ) !");
            }

        }
        else if(command.equals("/help")){

            sendMessageToUser(botMessageSender, "/play - начать новую игру;" + "\n" + "/give_up - узнать, какое число было загадано;" + "\n" + "/hint - подсказка.");

        }
        else if(Integer.parseInt(command) > 999 & Integer.parseInt(command) < 10000){

            userAnswer = Integer.parseInt(command);

            if(Game.getBulls(target, userAnswer) == 4){
                sendMessageToUser(botMessageSender,"Поздравляю!" + "\n" + "Количество попыток : " + (tryCounter + 1) + "." + "\n" + "Использовано подсказок : " + (4 - hintCount) + ".");
                userAnswer = 0;
                target = Game.getRandomNum();
                tryCounter = 0;
                hintCount = 4;
            }
            else {
                sendMessageToUser(botMessageSender, "Коров : " + Game.getCows(target, userAnswer) + "\n" + "Быков : " + Game.getBulls(target, userAnswer));
                tryCounter += 1;
            }

        }
        else{
            sendMessageToUser(botMessageSender, "Я не понимаю тебя :( ." + "\n" + "Либо используй команду /help, чтобы посмотреть полный список команд, либо введи четырёхзначное число.");
        }
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

    @Override
    public String getBotToken(){
        return "2125237304:AAFA0VFT0n9jimPhsTKobrcsguWWEt4ZJ5c";
    }

    @Override
    public String getBotUsername(){
        return "myCowsBullsGameBot";
    }
}
