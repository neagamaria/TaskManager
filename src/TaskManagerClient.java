import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class TaskManagerClient {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Socket clientSocket = null;
        Window clientWindow = null;
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
            clientWindow = new Window("test");
            clientWindow.setSize(800, 300);
            clientWindow.setVisible(true);
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
        }

        while(true) {
            String text = null;

            if(clientWindow.command != "") {
                // opreste temporar firul curent pentru a procesa comanda
                Thread.sleep(60);

                switch(clientWindow.command) {
                    case "1":
                        text = clientWindow.textbox.getText().trim();
                        out.writeUTF("1"+text);
                        clientWindow.command = "";
                        out.flush();
                        break;

                    case "5":
                        out.writeUTF("5");
                        // scrie lista de task uri primite
                        ArrayList<Task> taskList= (ArrayList<Task>)in.readObject();

                        for(Task task: taskList) {
                            clientWindow.display.append(task.toString() + "\n");
                            System.out.println(task.toString());
                        }

                        clientWindow.command = "";
                        break;
                }


                if(clientWindow.command.equals("END"))
                    break;
            }

           /*System.out.println("Comanda: ");
            String text = sc.nextLine();
            out.writeUTF(text);*/



        }

        clientSocket.close(); out.close();
    }
}
