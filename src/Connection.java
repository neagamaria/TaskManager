import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class Connection extends Thread{
    int clientNumber;
    Socket clientSocket = null;
    DataInputStream in = null;
    ObjectOutputStream out = null;

    public Connection(Socket s, int nr) throws IOException {
        this.clientSocket = s;
        this.clientNumber = nr;

        // pornire fir curent
        start();
    }

    // operatiile pentru firul de executie curent
    @Override
    public void run() {
        try {
            in = new DataInputStream(clientSocket.getInputStream());
            out = new ObjectOutputStream(clientSocket.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // creeaza lista de task-uri
        ArrayList<Task> taskList = new ArrayList<>();
        String text = "";

        while(true) {
            try {
                text = in.readUTF();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if(text.equals("END"))
                break;

            int command  = text.charAt(0);
            text = text.substring(1);
            int id = 0;

            switch(command) {
                case '1':
                    // adauga task in lista
                    taskList.add(new Task(text, id++));
                    break;
                case '2':
                    // sterge task din lista
                    int removeId = text.charAt(0) - '1';
                    int index = 0;
                    for(Task task: taskList) {
                        if(index == removeId) {
                            System.out.println("Sterge in Connection " + text);
                            taskList.remove(task);
                            break;
                        }

                        index++;
                    }
                    break;

                case '3':
                    // editeaza un task
                    int editId = text.charAt(0) - '1';
                    text = text.substring(1);
                    for(int i=0; i < taskList.size(); i++) {
                        if(i == editId) {
                            taskList.get(i).setText(text);
                            break;
                        }
                    }
                    break;

                case '4':
                    // finalizeaza task
                   int statusId = text.charAt(0) - '1';
                    System.out.println(statusId + " " + taskList.size());

                    if(statusId < taskList.size())
                        taskList.get(statusId).setStatus("Finalizat");

                    break;

                case '5':
                    // trimite lista de task-uri catre client
                    synchronized(taskList) {
                        try {
                            out.writeObject(taskList);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }

                    }
                    break;

                default:
                    System.out.println("Comanda gresita");
                    break;
            }
        }
    }
}
