import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.ArrayList;

public class Connection extends Thread{
    int clientNumber;
    Socket clientSocket = null;
    InputStream in = null;

    public Connection(Socket s, int nr) throws IOException {
        this.clientSocket = s;
        this.clientNumber = nr;
        in = clientSocket.getInputStream();
        // pornire fir curent
        start();
    }

    // operatiile pentru firul de executie curent
    @Override
    public void run() {
        // creeaza lista de task-uri
        ArrayList<Task> taskList = new ArrayList<>();
        String text = "";
        DataInputStream in = null;
        try {
            in = new DataInputStream(clientSocket.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

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
                    int removeId = text.charAt(0) - '0';
                    for(Task task: taskList) {
                        if(task.getId() == removeId) {
                            taskList.remove(task);
                            break;
                        }
                    }
                    break;

                case '3':
                    // editeaza un task
                    int editId = text.charAt(0) - '0';
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
                    int statusId = text.charAt(0) - '0';
                    for(int i = 0; i < taskList.size(); i++) {
                        if(i == statusId) {
                            taskList.get(i).setStatus("Finalizat");
                            break;
                        }
                    }
                    break;

                case '5':
                    // afiseaza lista
                    for(Task task: taskList) {
                        System.out.println(task.toString());
                    }
                    break;

                default:
                    System.out.println("Comanda gresita");
                    break;
            }
        }
    }
}
