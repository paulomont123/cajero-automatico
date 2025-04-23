package gpi;

import gpi.data.UserDatabase;
import gpi.menu.ATMMenu;

import java.io.IOException;

public class Main {

    private static final UserDatabase userDatabase = new UserDatabase();
    private static final ATMMenu atmMenu = new ATMMenu();

    public static void main(String[] args) {
        try {
            userDatabase.init();
            atmMenu.initMenu(userDatabase);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}