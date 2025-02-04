import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class TaskManagerClient2 {

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Socket clientSocket = null;
        Window clientWindow = null;
        Scanner sc = new Scanner(System.in);
        System.out.println("Adresa si portul serverului: ");
        try {
            clientSocket = new Socket(sc.next(), sc.nextInt());
            System.out.println("Conectat la server\n");
            sc.nextLine();
        } catch (Exception e) {
            System.out.println("Conexiune esuata " + new RuntimeException(e));
            System.exit(1);
        }

        ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
        DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());


        // deschide fereastra pentru client
        try {
            // ia numarul clientului
            String number = in.readUTF();
            clientWindow = new Window(number);
            clientWindow.setSize(1000, 500);
            clientWindow.setVisible(true);
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
        }

        String text;

        while(true) {
            if(clientWindow.command != "") {
                // opreste temporar firul curent pentru a procesa comanda
                Thread.sleep(60);

                switch(clientWindow.command) {
                    case "1":
                        clientWindow.errorMessage.setText("");
                        text = clientWindow.textbox.getText().trim();
                        out.flush();
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
                        clientWindow.errorMessage.setText(in.readUTF());
                        break;

                    case "3":
                        text = clientWindow.textbox.getText().trim();
                        out.writeUTF("3" + text);
                        clientWindow.command = "";
                        out.flush();
                        clientWindow.textbox.setText("");
                        clientWindow.errorMessage.setText(in.readUTF());
                        break;

                    case "4":
                        clientWindow.errorMessage.setText("");
                        clientWindow.display.setText("");
                        out.writeUTF("4");
                        // scrie lista de task uri primite
                        ArrayList<Task> taskList= (ArrayList<Task>)in.readObject();

                        int index = 0;
                        for(Task task: taskList) {
                            index++;
                            clientWindow.display.append(index + ". " + task.toString() + "\n");
                        }

                        clientWindow.command = "";
                        break;
                }


                if(clientWindow.command.equals("5")) {
                    //comanda catre conexiune pentru inchidere
                    out.writeUTF("5");

                    // inchidere fereastra
                    clientWindow.dispose();
                    clientWindow.setVisible(false);

                    // inchidere socket
                    clientSocket.close();
                    out.close();
                    break;
                }
            }
        }

        clientSocket.close(); out.close();
    }
}
