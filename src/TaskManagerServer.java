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

        // creare socket server
        Scanner sc = new Scanner(System.in);
        System.out.println("Portul pentru server: ");
        ServerSocket serverSocket = new ServerSocket(sc.nextInt());
        sc.nextLine();
        System.out.println("Serverul a pornit");

        // conectarea cu socketul clientului
        while(true) {
            Socket clientSocket = serverSocket.accept();
            clientNumber++;
            System.out.println("Clientul " + clientNumber + "  conectat");
            new Connection(clientSocket, clientNumber);
        }
    }
}