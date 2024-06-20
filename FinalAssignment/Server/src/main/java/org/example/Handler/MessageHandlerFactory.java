package org.example.Handler;

public class MessageHandlerFactory {
    public static MessageHandler createHandler(String messageType, String[] headerParts, String payload) {
        switch (messageType) {
            case "LOGIN":
                return new LoginHandler(payload);
            case "COMMENT":
                return new CommentHandler(headerParts, payload);
            case "EXIT":
                return new ExitHandler();
            default:
                return null;
        }
    }
}
