package application;

import jdbc.ConnectionFactory;

public class Teste {
    public static void main(String[] args) {
        new ConnectionFactory().getConnection();
    }
}
