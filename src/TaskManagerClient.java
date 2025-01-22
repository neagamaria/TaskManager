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
                        out.writeUTF("1" + text);
                        clientWindow.command = "";
                        out.flush();
                        clientWindow.textbox.setText("");
                        break;

                    case "2":
                        text = clientWindow.textbox.getText().trim();
                        out.writeUTF("2" + text);
                        clientWindow.command = "";
                        out.flush();
                        clientWindow.textbox.setText("");
                        break;

                    case "4":
                        text = clientWindow.textbox.getText().trim();
                        out.writeUTF("4" + text);
                        clientWindow.command = "";
                        out.flush();
                        clientWindow.textbox.setText("");
                        break;

                    case "5":
                        out.writeUTF("5");
                        // scrie lista de task uri primite
                        ArrayList<Task> taskList= (ArrayList<Task>)in.readObject();

                        int index = 0;
                        for(Task task: taskList) {
                            index++;
                            clientWindow.display.append(index + ". " + task.toString() + "\n");
                        }

                        System.out.println("\n\n");
                        clientWindow.command = "";
                        break;
                }


                if(clientWindow.command.equals("END")) {
                    clientWindow.dispose();
                    clientWindow.setVisible(false);
                    break;
                }
            }
        }

        clientSocket.close(); out.close();
    }
}
