package telegram.handlers;

import game.GameChat;
import telegram.commands.*;
import game.Game;
import telegram.services.ChatService;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import telegram.services.KeyboardService;
import telegram.services.LocalisationService;
import java.util.Objects;
import java.util.logging.Logger;

public class CommandsHandler extends TelegramLongPollingCommandBot{
    private static final String BOT_NAME = "bUlLs_AnD_cOwS_aNoThEr_gAmE_BoT";
    private static final Logger logger = Logger.getLogger("main");

    public CommandsHandler(){
        registerAll(new StartCommand(),
                    new HelpCommand(),
                    new HintCommand(),
                    new PlayCommand(),
                    new GiveUpCommand(),
                    new SettingsCommand());

        registerDefaultAction(((absSender, message) -> {
            GameChat gameChat = ChatService.getGameChats().get(message.getChatId());

            if(gameChat == null) sendMessageToUser(absSender,
                        "You need to enter the /start command to initialize the game.\n\n" +
                                "Для инициализации игры необходимо ввести команду /start.",
                                message.getChatId().toString());
            else sendMessageToUser(absSender,
                 LocalisationService.getString("userInputError", gameChat.getCurrentLanguageCode()),
                 message.getChatId().toString());
        }));
    }

    @Override
    public void processNonCommandUpdate(Update update){
        String userMessage = update.getMessage().getText();
        Long chatId =  update.getMessage().getChatId();

        logger.info("The user in the chat room with ID " + chatId +" entered the following:\n" + userMessage);

        SendMessage messageSender = new SendMessage();
        messageSender.setChatId(chatId.toString());

        int enteredNum = getEnteredNumber(userMessage);

        GameChat chat = ChatService.getGameChats().get(chatId);
        Game game = chat.getGame();

        if(userMessage.equals(LocalisationService.getString("english", chat.getCurrentLanguageCode()))){
            changeLanguage(chat, "en", messageSender);
        }
        else if(userMessage.equals(LocalisationService.getString("russian", chat.getCurrentLanguageCode()))){
            changeLanguage(chat, "ru", messageSender);
        }
        else if(userMessage.equals(LocalisationService.getString("yes", chat.getCurrentLanguageCode()))){
            if(game.isFinished()
                && ChatService.getGameChats().containsKey(chatId)){
                game.reset();
                game.setStarted(true);
                game.setFinished(false);
                sendMessageToUser(messageSender, LocalisationService.getString("restarted",
                        chat.getCurrentLanguageCode()));
            }
            else sendMessageToUser(messageSender, LocalisationService.getString("gameStartingError",
                    chat.getCurrentLanguageCode()));
        }
        else if(userMessage.equals(LocalisationService.getString("no", chat.getCurrentLanguageCode()))){
            if(game.isFinished()
                && ChatService.getGameChats().containsKey(chatId)){
                game.interrupt();
                ChatService.getGameChats().remove(chatId);
                sendMessageToUser(messageSender, LocalisationService.getString("startCommand",
                        chat.getCurrentLanguageCode()));
            }
            else sendMessageToUser(messageSender, LocalisationService.getString("gameStartingError",
                    chat.getCurrentLanguageCode()));
        }
        else if(enteredNum > 999 && enteredNum < 10000){
            game.setPlayerAnswer(enteredNum);
            if(game.getBulls(enteredNum) == 4){
                getGameResults(game, messageSender, chat);
                game.setFinished(true);
                game.setStarted(false);
            }
            else continueTheGame(chat, messageSender);
        }
        else sendMessageToUser(messageSender, LocalisationService.getString("userInputError",
                    chat.getCurrentLanguageCode()));
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
        } catch(TelegramApiException e){
            logger.finer(e.getMessage());
        }
    }

    public synchronized void sendMessageToUser(SendMessage messageSender, String message){
        if(!Objects.equals(message, ""))  messageSender.setText(message);
        try{
            execute(messageSender);
        } catch(TelegramApiException e){
            logger.finer(e.getMessage());
        }
    }

    private int getEnteredNumber(String userMessage){
        int enteredNumber = -1;
        try{
            enteredNumber = Integer.parseInt(userMessage);
        } catch (NumberFormatException e){
            logger.finer(e.getMessage());
        }
        return enteredNumber;
    }

    private void changeLanguage(GameChat chat, String languageCode, SendMessage  messageSender){
        chat.setLanguageCode(languageCode);
        messageSender.setText(LocalisationService.getString("languageChange",
                      chat.getCurrentLanguageCode()));
        try {
            execute(messageSender);
        } catch (TelegramApiException e){
            logger.finer(e.getMessage());
        }
    }

    private void getGameResults(Game game, SendMessage messageSender, GameChat chat){
        int playerAttemptsCount = game.getPlayerAttemptsCount() + 1;
        int hintCount = 4 - game.getHintsCount();

        sendMessageToUser(messageSender,
    LocalisationService.getString("gameResults", chat.getCurrentLanguageCode()) +
            " " + playerAttemptsCount + ".\n" +
            LocalisationService.getString("hintsUsed", chat.getCurrentLanguageCode()) +
            " " + hintCount + ".\n" +
            LocalisationService.getString("letsPlayAgain", chat.getCurrentLanguageCode()));

        sendMessageToUser(KeyboardService.createKeyboard(chat.getId().toString(),
                LocalisationService.getString("yes", chat.getCurrentLanguageCode()),
                LocalisationService.getString("no", chat.getCurrentLanguageCode())), "");
    }

    private void continueTheGame(GameChat chat, SendMessage messageSender){
        Game game = chat.getGame();

        if(!game.isPlayerGaveUp()){
            int playerAnswer = game.getPlayerAnswer();

            sendMessageToUser(messageSender,
        LocalisationService.getString("cows", chat.getCurrentLanguageCode()) +
                " " + game.getCows(playerAnswer) + "\n" +
                LocalisationService.getString("bulls", chat.getCurrentLanguageCode()) +
                " " + game.getBulls(playerAnswer));

            game.incrementPlayerAttemptsCount();
        }
        else sendMessageToUser(messageSender,
             LocalisationService.getString("playerGaveUp", chat.getCurrentLanguageCode()));
    }
}
