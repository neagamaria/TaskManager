import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class TaskManagerClient {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
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

        ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
        DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());

        // ia numarul clientului
        //String number = in.readUTF();

        // deschide fereastra pentru client
        try {
            Window clientWindow = new Window("test");
            clientWindow.setSize(800, 300);
            clientWindow.setVisible(true);
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
        }


        while(true) {
            System.out.println("Comanda: ");
            String text = sc.nextLine();
            out.writeUTF(text);

            if(text.equals("END"))
                break;

            if(text.charAt(0) == '5') {
                // scrie lista de task uri primite
                ArrayList<Task> taskList= (ArrayList<Task>)in.readObject();

                for(Task task: taskList) {
                    System.out.println(task.toString());
                }
            }
        }

        clientSocket.close(); out.close();
    }
}
