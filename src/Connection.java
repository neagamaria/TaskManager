import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class Connection extends Thread {
    int clientNumber;
    Socket clientSocket = null;
    DataInputStream in = null;
    ObjectOutputStream out = null;

    public Connection(Socket s, int nr){
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

        // trimite catre client numarul sau
        try {
            out.writeUTF(Integer.toString(clientNumber));
            out.reset();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // creeaza lista de task-uri
        ArrayList<Task> taskList = new ArrayList<>();
        String text = "";

        while(true) {

            int command  = text.charAt(0);
            text = text.substring(1).trim();
            int id = 0;

            switch(command) {
                case '1':
                    // adauga task in lista
                    taskList.add(new Task(text, id++));
                    break;
                case '2':
                    // sterge task din lista
                    int removeId = Integer.parseInt(text) - 1;

                    if(removeId < 0 || removeId > taskList.size() - 1) {
                        try {
                            out.writeUTF("Numarul activitatii introdus gresit");
                            out.flush();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    else {
                        taskList.remove(removeId);
                        try {
                            out.writeUTF("");
                            out.flush();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }

                    break;

                case '3':
                    // finalizeaza task
                    int statusId = Integer.parseInt(text) - 1;

                    if(statusId < 0 || statusId > taskList.size() -1) {
                        try {
                            out.writeUTF("Numarul activitatii introdus gresit");
                            out.flush();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    else {
                        taskList.get(statusId).setStatus("Finalizat");
                        try {
                            out.writeUTF("");
                            out.flush();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }

                    break;

                case '4':
                    // trimite lista de task-uri catre client
                    synchronized(taskList) {
                        try {
                            // curata output-ul de valorile precedente
                            out.reset();
                            out.writeObject(taskList);
                            out.flush();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }

                    }
                    break;

                case '5':
                    System.out.println("Inchide");

                    // inchide conexiunea
                    System.out.println("Clientul " + clientNumber + " deconectat");
                    return;

                default:
                    System.out.println("Comanda gresita");
                    break;
            }
        }
    }
}