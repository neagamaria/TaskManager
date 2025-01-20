import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.logging.Handler;

// clasa pentru interfata grafica
public class Window extends Frame {
    boolean open = true;
    Button b1, b2, b3;
    Panel buttonArea;
    Label label;
    TextField textbox;
    TextArea display;
    public Window(String client) {
        super();

        // creeaza zona de output
        display = new TextArea();
        display.setVisible(false);

        setLayout(new BorderLayout());
        label = new Label("Bine ai venit, client " + client);
        textbox = new TextField(50);

        // zona de butoane
        b1 = new Button("Adauga activitate");
        b1.setBackground(new Color(215, 127, 68));
        b2 = new Button("Afiseaza lista");
        b2.setBackground(new Color(215, 127, 68));
        b3 = new Button("Inchide");
        b3.setBackground(new Color(215, 127, 68));
        buttonArea = new Panel();
        buttonArea.setLayout(new FlowLayout());
        buttonArea.add(b1);
        buttonArea.add(b2);
        buttonArea.add(b3);

        add(label, BorderLayout.NORTH);
        add(buttonArea, BorderLayout.SOUTH);
        //add(textbox, BorderLayout.SOUTH);
       // add(display);

        // adauga evenimente butoanelor
        // Handler H = new Handler(this);


    }

    /*class Handler implements ActionListener, Serializable {
        Window w;
        Handler(Window w) { this.w = w; }
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();
            if("Adauga activitate".equals(command)) {

            }
            else if("Afiseaza lista".equals(command)) {

            }
        }
    }

    public void addCommand() {
        String text = textbox.getText();
        if(text.isEmpty()) {
            display.setVisible(true);
            display.append("Activitatea nu poate fi adaugata in lista");
            return;
        }

    }*/
}
