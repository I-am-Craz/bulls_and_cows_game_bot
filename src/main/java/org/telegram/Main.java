package org.telegram;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class Main {
    public static void main(String[] args){
        try{
            TelegramBotsApi tgBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            tgBotsApi.registerBot(new Bot());
        } catch (TelegramApiException ex){
            System.out.println(ex.getMessage());
        }
    }
}
// Eclipse & External Jar File. It worked.
