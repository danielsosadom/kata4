package software.ulpgc.kata2.view;

import software.ulpgc.kata2.view.MessageDisplay;

import javax.swing.*;
import java.awt.*;

public class SwingMessageDisplay extends JPanel implements MessageDisplay {
    public SwingMessageDisplay() {
        setLayout(new BorderLayout());
    }

    @Override
    public void show(String text) {
        removeAll();
        add(new JTextField(text));
        revalidate();
    }
}
