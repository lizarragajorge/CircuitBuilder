package circuit.ui;

import circuit.Gate;
import circuit.Wire;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.util.List;

public class DrawingPanel extends JPanel
{
    private List<Connection> connections = new ArrayList<>();

    public void setConnections(JButton button1, JButton button2)
    {
        connections.add(new Connection(button1, button2));
    }

    public void updateConnections(List<Gate> gateList, List<JButton> buttons)
    {
        connections.clear();
        for (Gate sourceGate : gateList)
            for (Gate destinationGate : gateList)
                for (Wire sourceWire : sourceGate.getInputWires())
                    if (sourceWire == destinationGate.getOutputWire())
                        setConnections(buttons.get(gateList.indexOf(sourceGate)), buttons.get(gateList.indexOf(destinationGate)));
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(200, 200);
    }

    //Based off of MadProgrammer's implementation of a custom panel from StackOverflow
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setColor(Color.black);
        for (Connection connection : connections) {
            JButton source = connection.getSource();
            JButton dest = connection.getDestination();

            if (source.getX() == dest.getX())
            {
                g2d.drawLine(source.getX() + source.getWidth() / 2, source.getY(),
                        dest.getX() + source.getWidth() / 2, dest.getY());
            }

            else if (source.getY() == dest.getY())
            {
                g2d.drawLine(source.getX(), source.getY() + source.getHeight() / 2,
                        dest.getX(), dest.getY() + dest.getHeight() / 2);
            }

            else
            {
                Path2D path = new Path2D.Double();
                path.moveTo(horizontalCenter(source), verticalCenter(source));
                path.curveTo(horizontalCenter(source), verticalCenter(dest),
                        horizontalCenter(source), verticalCenter(dest),
                        horizontalCenter(dest), verticalCenter(dest));
                g2d.draw(path);
            }
        }
        g2d.dispose();
    }

    protected double horizontalCenter(JComponent bounds) { return bounds.getX() + bounds.getWidth() / 2d; }

    protected double verticalCenter(JComponent bounds) { return bounds.getY() + bounds.getHeight() / 2d; }

}
