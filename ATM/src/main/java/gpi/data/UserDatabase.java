package gpi.data;

import gpi.entity.User;

import javax.swing.filechooser.FileSystemView;
import java.io.*;
import java.util.UUID;

public class UserDatabase {

    /**
     * Inicializacion de la base de datos, aca, vamos a generar el archivo .dat PERO unicamente si no existe,
     * Este proceso se ejecuta cada que el programa se inicia
     */

    public void init() throws IOException {
        String desktopPath = FileSystemView.getFileSystemView().getHomeDirectory().getAbsolutePath();

        String fileName = "balance.dat";
        String filePath = desktopPath + "\\" + fileName;

        File file = new File(filePath);
        if (!file.exists()) {
            User user = new User();
            UUID uuid = UUID.randomUUID();
            double initialBalance = 100.00;

            user.setId(uuid);
            user.setBalance(initialBalance);

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                writer.write("uuid=" + user.getId() + "|balance=" + user.getBalance());
            }
        }
    }

    /**
     * Sobreescribir el archivo .dat
     * @param user
     * @throws IOException
     */

    private void reWriteUser(User user) throws IOException {
        String desktopPath = FileSystemView.getFileSystemView().getHomeDirectory().getAbsolutePath();

        String fileName = "balance.dat";
        String filePath = desktopPath + "\\" + fileName;

        File file = new File(filePath);
        if (file.exists()) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                writer.write("uuid=" + user.getId() + "|balance=" + user.getBalance());
            }
        }
    }

    /**
     * Obtener usuario contenido dentro del archivo .dat
     *
     * @return User
     */

    public User getUser() throws IOException {
        String desktopPath = FileSystemView.getFileSystemView().getHomeDirectory().getAbsolutePath();

        String fileName = "balance.dat";
        String filePath = desktopPath + "\\" + fileName;

        File file = new File(filePath);
        if (file.exists()) {
            User user = new User();
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                StringBuilder strUserBuilder = new StringBuilder();

                String line;
                while ((line = br.readLine()) != null) {
                    strUserBuilder.append(line);
                }

                String strUser = strUserBuilder.toString();
                if (strUser.contains("|")) {
                    String[] strUserParams = strUser.split("\\|");
                    user.setId(UUID.fromString(strUserParams[0].split("=")[1]));
                    user.setBalance(Double.parseDouble(strUserParams[1].split("=")[1]));
                    return user;
                }
            }
        }

        return null;
    }

    /**
     * Obtener balance
     *
     * @return double
     */

    public double getBalance() throws IOException {
        return this.getUser() != null ? this.getUser().getBalance() : 0.0;
    }

    /**
     * Retirar dinero de la cuenta
     */

    public void withdrawBalance(double amount) throws IOException {
        double newBalance = this.getUser() != null ? this.getUser().getBalance() - amount : 0.0;

        User newUser = new User();
        newUser.setId(this.getUser().getId());
        newUser.setBalance(newBalance);

        this.reWriteUser(newUser);
    }

}
