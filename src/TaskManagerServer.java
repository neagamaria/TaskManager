import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class TaskManagerServer {
    // indexul clientului curent
    static int clientNumber = 0;
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket;

        // creare socket server
        Scanner sc = new Scanner(System.in);
        System.out.println("Portul pentru server: ");
        try {
            serverSocket = new ServerSocket(sc.nextInt());
        }
        catch(Exception e) {
            throw new RuntimeException(e);
        }

        sc.nextLine();
        System.out.println("Serverul ruleaza\n");

        while (true) {
            // acceptă conectarea cu un nou socket de client
            Socket clientSocket = serverSocket.accept();
            clientNumber++;
            System.out.println("Clientul " + clientNumber + " conectat\n");
            new Connection(clientSocket, clientNumber);
        }
    }
}