import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.logging.Handler;

// clasa pentru interfata grafica
public class Window extends Frame {
    boolean open = true;
    Button b1, b2, b3, b4, b5;
    Panel buttonArea;
    Label label;
    TextField textbox;
    TextArea display;

    // orice modificare a comenzii sa fie vizibila
    volatile String command = "";

    public Window(String client) {
        super();

        // creeaza zona de output
        display = new TextArea();
        display.setVisible(true);

        setLayout(new BorderLayout());
        label = new Label("Bine ai venit, client " + client);
        textbox = new TextField(50);

        // zona de butoane
        b1 = new Button("Adauga activitate");
        b1.setBackground(new Color(215, 127, 68));
        b2 = new Button("Afiseaza lista");
        b2.setBackground(new Color(215, 127, 68));
        b3 = new Button("Sterge activitate");
        b3.setBackground(new Color(215, 127, 68));
        b4 = new Button("Finalizeaza activitate");
        b4.setBackground(new Color(215, 127, 68));
        b5 = new Button("Inchide");
        b5.setBackground(new Color(215, 127, 68));
        buttonArea = new Panel();
        buttonArea.setLayout(new FlowLayout());
        buttonArea.add(b1);
        buttonArea.add(b2);
        buttonArea.add(b3);
        buttonArea.add(b4);
        buttonArea.add(b5);


        add(label, BorderLayout.NORTH);
        add(buttonArea, BorderLayout.SOUTH);
        add(textbox, BorderLayout.CENTER);
        add(display, BorderLayout.EAST);

        // adauga evenimente butoanelor
        Handler handler = new Handler(this);
        b1.addActionListener(handler);
        b2.addActionListener(handler);
        b3.addActionListener(handler);
        b4.addActionListener(handler);
        b5.addActionListener(handler);
    }

    class Handler implements ActionListener, Serializable {
        Window w;
        Handler(Window w) { this.w = w; }
        public void actionPerformed(ActionEvent e) {
            String buttonPressed = e.getActionCommand();
            if("Adauga activitate".equals(buttonPressed)) {
                w.command = "1";
            }

            else if("Afiseaza lista".equals(buttonPressed)) {
                w.command = "5";
            }

            else if("Sterge activitate".equals(buttonPressed)) {
                w.command = "2";
            }

            else if("Finalizeaza activitate".equals(buttonPressed)) {
                w.command = "4";
            }

            else if("Inchide".equals(buttonPressed)) {
                w.command = "END";
            }
        }
    }
}
