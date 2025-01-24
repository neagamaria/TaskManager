import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

// clasa pentru interfata grafica
public class Window extends JFrame {
    JButton b1, b2, b3, b4;
    JPanel buttonArea;
    JLabel label, errorMessage;
    JTextField textbox;
    JTextArea display;

    // orice modificare a comenzii sa fie vizibila
    volatile String command = "";

    public Window(String client) {
        super();

        // elimina comportamentul default al butonului de inchidere pt suprascriere
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                command = "5";
            }
        });

        setLayout(new GridLayout(5, 3));

        label = new JLabel("Bine ai venit, client " + client + "!", SwingConstants.CENTER);
        label.setBorder(BorderFactory.createEmptyBorder(10, 50, 10, 50));
        label.setForeground(new Color(255, 87, 51));
        label.setFont(new Font("Arial", Font.BOLD, 45));

        textbox = new JTextField(30);
        textbox.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.WHITE, 30),
                BorderFactory.createLineBorder(Color.black, 1)
        ));
        textbox.setBackground(Color.lightGray);
        textbox.setFont(new Font("Arial", Font.PLAIN, 20));

        b1 = new JButton("Adauga activitate");
        b1.setBackground(new Color(255, 181, 51));
        b2 = new JButton("Afiseaza lista");
        b2.setBackground(new Color(255, 181, 51));
        b3 = new JButton("Sterge activitate");
        b3.setBackground(new Color(255, 181, 51));
        b4 = new JButton("Finalizeaza activitate");
        b4.setBackground(new Color(255, 181, 51));

        buttonArea = new JPanel();
        buttonArea.setLayout(new FlowLayout());
        buttonArea.setBackground(Color.white);
        buttonArea.add(b1);
        buttonArea.add(b2);
        buttonArea.add(b3);
        buttonArea.add(b4);

        // creeaza zona de output
        display = new JTextArea();
        display.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.WHITE, 5),
                BorderFactory.createLineBorder(Color.black, 1)
        ));
        display.setBackground(new Color(218, 247, 166));
        display.setFont(new Font("Arial", Font.PLAIN, 20));

        errorMessage = new JLabel();
        errorMessage.setBorder(BorderFactory.createEmptyBorder(1, 5, 1, 5));
        errorMessage.setForeground(new Color(199, 0, 57));
        errorMessage.setFont(new Font("Arial", Font.PLAIN, 10));


        add(label);
        add(textbox);
        add(buttonArea);
        add(display);
        add(errorMessage);

        // adauga evenimente butoanelor
        Handler handler = new Handler(this);
        b1.addActionListener(handler);
        b2.addActionListener(handler);
        b3.addActionListener(handler);
        b4.addActionListener(handler);
    }
}

    class Handler implements ActionListener {
        Window w;
        Handler(Window w) { this.w = w; }
        public void actionPerformed(ActionEvent e) {
            String buttonPressed = e.getActionCommand();
            if("Adauga activitate".equals(buttonPressed)) {
                w.command = "1";
            }

            else if("Sterge activitate".equals(buttonPressed)) {
                w.command = "2";
            }

            else if("Finalizeaza activitate".equals(buttonPressed)) {
                w.command = "3";
            }
            else if("Afiseaza lista".equals(buttonPressed)) {
                w.command = "4";
            }
        }
    }

