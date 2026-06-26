package software.ulpgc.kata2.view;

import software.ulpgc.kata2.control.Command;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class MainFrame extends JFrame {
    private final Map<String, Command> commands = new HashMap<>();
    private final JFreeChartDisplay chartDisplay = new JFreeChartDisplay();
    private final SwingMessageDisplay messageDisplay = new SwingMessageDisplay();

    public MainFrame() throws HeadlessException {
        setTitle("Kata 3");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        add(chartDisplay, BorderLayout.CENTER);
        add(toolbar(), BorderLayout.NORTH);
    }

    private Component toolbar() {
        JPanel panel = new JPanel();
        panel.add(toggle());
        panel.add(save());
        panel.add(messageDisplay);
        return panel;
    }

    private Component save() {
        JButton button = new JButton("Save to DB");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                commands.get("save").execute();
            }
        });
        return button;
    }

    private Component toggle() {
        JButton button = new JButton("Toggle");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    commands.get("toggle").execute();
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        return button;
    }

    public JFreeChartDisplay getChartDisplay() {
        return chartDisplay;
    }

    public SwingMessageDisplay getMessageDisplay() {
        return messageDisplay;
    }

    public void put(String key, Command value) {
        commands.put(key, value);
    }
}
