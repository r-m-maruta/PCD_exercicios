package exercicios.semana8.ex3;

import java.io.Serializable;

public class TimeMessage implements Serializable {
    private long timestamp;

    public TimeMessage(long timestamp) {
        this.timestamp = timestamp;
    }

    public long getTimestamp() {
        return timestamp;
    }
}


