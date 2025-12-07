package exercicios.semana8.ex3;

import java.io.Serializable;

public class ReceptionConfirmationMessage implements Serializable {
    private String clientName;

    public ReceptionConfirmationMessage(String clientName) {
        this.clientName = clientName;
    }

    public String getClientName() {
        return clientName;
    }
}