import telegram.handlers.CommandsHandler;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Bot {
    static {
        System.setProperty("java.util.logging.config.file", "logging.properties");
        Logger logger = Logger.getLogger("main");
        logger.setLevel(Level.FINER);
        try {
            Handler handler = new FileHandler("game%u.log");
            handler.setLevel(Level.FINER);
            logger.addHandler(handler);
        } catch (IOException e) {
            logger.finer(e.getMessage());
            throw new RuntimeException(e);
        }
    }
    public static void main(String[] args){
        try{
            TelegramBotsApi tgBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            tgBotsApi.registerBot(new CommandsHandler());
        } catch (TelegramApiException e){
            Logger.getLogger("main").finer(e.getMessage());
        }
    }
}
