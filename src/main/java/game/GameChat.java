package game;

import org.telegram.telegrambots.meta.api.objects.*;

import java.util.logging.Logger;

public class GameChat extends Chat {
    private Game game;

    private String currentLanguageCode = "en";

    public GameChat(Chat chat) {
        super(chat.getId(), chat.getType(), chat.getTitle(), chat.getFirstName(),
            chat.getLastName(), chat.getUserName(), chat.getPhoto(), chat.getDescription(),
            chat.getInviteLink(), chat.getPinnedMessage(), chat.getStickerSetName(), chat.getCanSetStickerSet(),
            chat.getPermissions(), chat.getSlowModeDelay(), chat.getBio(), chat.getLinkedChatId(),
            chat.getLocation(), chat.getMessageAutoDeleteTime(), chat.getHasPrivateForwards(),
            chat.getHasProtectedContent(), chat.getJoinToSendMessages(), chat.getJoinByRequest(),
            chat.getHasRestrictedVoiceAndVideoMessages(), chat.getIsForum(), chat.getActiveUsernames(),
            chat.getEmojiStatusCustomEmojiId());
    }

    public Game getGame(){
        return this.game;
    }

    public void setGame(Game game){
        this.game = game;
    }

    public void startGame() {
        if(this.game != null){
            game.start();
            Logger.getLogger("main").info("A new game is started in the chat with ID " + super.getId());
        }
    }

    public void setLanguageCode(String code){
        this.currentLanguageCode = code;
    }

    public String getCurrentLanguageCode() {
        return currentLanguageCode;
    }
}
