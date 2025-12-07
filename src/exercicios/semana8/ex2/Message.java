package exercicios.semana8.ex2;

import java.io.Serializable;

public class Message implements Serializable {
    private static final long serialVersionUID = 1L;

    private String text;
    private String sender;

    public Message(String text, String sender) {
        this.text = text;
        this.sender = sender;
    }

    public String getText() {
        return text;
    }

    public String getSender() {
        return sender;
    }

    @Override
    public String toString() {
        return sender + ": " + text;
    }
}
