import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class Connection extends Thread {
    int clientNumber;
    Socket clientSocket = null;
    DataInputStream in = null;
    ObjectOutputStream out = null;
    ArrayList<Task> taskList;
    String text;
    int command;


    public Connection(Socket s, int nr){
        this.clientSocket = s;
        this.clientNumber = nr;
        this.text = "";

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
        taskList = new ArrayList<>();

        while(true) {
            try {
                text = in.readUTF();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            command  = text.charAt(0);
            text = text.substring(1).trim();
            int id = 0;

            switch(command) {
                case '1':
                    addTask();
                    break;
                case '2':
                    deleteTask();
                    break;

                case '3':
                    finalizeTask();
                    break;

                case '4':
                    showList();
                    break;

                case '5':
                    // inchide conexiunea
                    System.out.println("Clientul " + clientNumber + " deconectat\n");
                    return;

                default:
                    System.out.println("Comanda gresita\n");
                    break;
            }
        }
    }


    // adauga un task in lista
    public synchronized void addTask() {
        System.out.println("Clientul " + clientNumber + " : adaugare");
        // adauga task in lista
        taskList.add(new Task(text));
    }


    // sterge un task din lista
    public synchronized void deleteTask() {
        System.out.println("Clientul " + clientNumber + " : stergere");

        // sterge task din lista
        if(!text.matches("[0-9]+")) {
            try {
                out.writeUTF("Numarul activitatii introdus gresit");
                out.flush();
                return;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

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
    }

    // finalizeaza task
    public synchronized void finalizeTask() {
        System.out.println("Clientul " + clientNumber + " : finalizare\n");

        // finalizeaza task
        if(!text.matches("[0-9]+")) {
            try {
                out.writeUTF("Numarul activitatii introdus gresit");
                out.flush();
                return;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        int statusId = Integer.parseInt(text) - 1;

        if(statusId < 0 || statusId > taskList.size() -1) {
            try {
                out.writeUTF("Numarul activitatii introdus gresit\n");
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
    }


    // afiseaza lista
    public synchronized  void showList() {
        System.out.println("Clientul " + clientNumber + " : afisare\n");
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
    }
}