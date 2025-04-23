package gpi.menu;

import gpi.data.UserDatabase;
import gpi.exceptions.InvalidAmountException;
import gpi.exceptions.NotEnoughBalanceException;

import java.io.IOException;
import java.util.Scanner;

public class ATMMenu {

    /**
     * Creacion y ejecucion del menu por consola
     *
     * @param userDatabase
     * @throws IOException
     */

    public void initMenu(UserDatabase userDatabase) throws IOException {
        /*
         * Variables utilizadas para la lectura de datos del programa
         */

        int execute = 1;
        String keepExecuting = "";
        Scanner sc = new Scanner(System.in);

        while (execute == 1) {
            if (!keepExecuting.equalsIgnoreCase("si")) {
                System.out.println("Bienvenido al menu de ATM");
            } else {
                System.out.println();
            }

            System.out.println("Digite una de las siguientes opciones: ");
            System.out.println("\n1. Consultar balance");
            System.out.println("2. Retiro de efectivo");
            System.out.println("3. Salir");

            int option = sc.nextInt();
            clearScreen();

            /*
             * Condicional para la ejecucion de opciones
             */

            switch (option) {
                case 1: {
                    System.out.println("Su balance actual es de: " + userDatabase.getBalance());
                    break;
                }
                case 2: {
                    System.out.println("Digite la cantidad a retirar (SOLO MULTIPLOS DE 50):");
                    double amount = sc.nextDouble();

                    if (amount % 50 == 0) {
                        if (userDatabase.getBalance() >= amount) {
                            userDatabase.withdrawBalance(amount);
                            System.out.println("Se ha retirado " + amount + " de su cuenta");
                            System.out.println("Su balance actual es de: " + userDatabase.getBalance());
                        } else {
                            throw new NotEnoughBalanceException("Fondos insuficientes para realizar esta transaccion");
                        }
                    } else {
                        throw new InvalidAmountException("No se permiten cantidades que no sean multiplos de 50");
                    }

                    break;
                }
                case 3: {
                    System.out.println("Retire su tarjeta! ;)");
                    execute = 0;
                    break;
                }
                default:
                    System.out.println("Esa opcion no existe");
                    break;
            }

            /*
             * Pregunta al usuario si desea seguir con la ejecucion del ATM
             */

            if (option != 3) {
                System.out.println("Desea realizar alguna otra transaccion? (Digitar si/no)");
                keepExecuting = sc.next();
                if (keepExecuting.equalsIgnoreCase("no")) {
                    execute = 0;
                    System.out.println("Retire su tarjeta! ;)");
                } else {
                    if (!keepExecuting.equalsIgnoreCase("si")) {
                        execute = 0;
                    }
                }
            }
        }

        sc.close();
    }

    private void clearScreen() {
        System.out.println("\033[H\033[2J");
    }

}
