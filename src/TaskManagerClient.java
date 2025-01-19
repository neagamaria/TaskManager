import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class TaskManagerClient {
    public static void main(String[] args) throws IOException {
        Socket clientSocket = null;
        Scanner sc = new Scanner(System.in);
        System.out.println("Adresa si portul serverului: ");
        try {
            clientSocket = new Socket(sc.next(), sc.nextInt());
            sc.nextLine();
        } catch (Exception e) {
            System.out.println("Conexiune esuata " + new RuntimeException(e));
            System.exit(1);
        }

        DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());

        while(true) {
            System.out.println("Comanda: ");
            String text = sc.nextLine();
            out.writeUTF(text);
            if(text.equals("END"))
                break;
        }

        clientSocket.close(); out.close();
    }
}
