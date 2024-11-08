package brocodex.fbot.handler;

import org.telegram.abilitybots.api.db.DBContext;
import org.telegram.abilitybots.api.sender.SilentSender;

import java.util.Map;

public class ResponseHandler {
    private final SilentSender sender;
    private final Map<Long, String> chatStates;

    public ResponseHandler(SilentSender sender, DBContext dbContext) {
        this.sender = sender;
        this.chatStates = dbContext.getMap("CHAT_STATES");
    }
}
