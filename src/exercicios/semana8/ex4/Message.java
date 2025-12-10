package exercicios.semana8.ex4;

public class Message {

    private String type;
    private String sender;
    private String receiver;
    private String message;

    public Message(String type, String sender, String receiver, String message) {
        this.type = type;
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
    }

    public String getType() {
        return type;
    }
    public String getSender() {
        return sender;
    }
    public String getReceiver() {
        return receiver;
    }
    public String getMessage() {
        return message;
    }
}

