package circuit.ui;

import javax.swing.*;

//Based off of MadProgrammer's Panel from StackOverflow
public class Connection
{
    private final JButton source;
    private final JButton destination;

    public Connection(JButton sourceInput, JButton destinationInput)
    {
        source = sourceInput;
        destination = destinationInput;
    }

    public JButton getSource() {
        return source;
    }

    public JButton getDestination() {
        return destination;
    }
}
