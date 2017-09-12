package circuit.ui;

import circuit.Circuit;
import circuit.Gate;
import circuit.GateClassRetriever;
import circuit.Wire;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;


public class CircuitBuilder extends JFrame
{
    public Circuit circuit = new Circuit();
    private DrawingPanel drawingPanel = new DrawingPanel();
    public List<JButton> gateButtons = new ArrayList<>();
    public List<Circuit> savedCircuits = new ArrayList<>();
    public List<List<JButton>> savedButtons = new ArrayList<>();

    @Override
    protected void frameInit()
    {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        circuit = new Circuit();
        super.frameInit();
        createMenuBar();
        createAddAndDeleteMenu();

        setTitle("Circuit Simulator");
        setSize(300, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void createMenuBar()
    {
       JPanel saveOpenPanel = new JPanel();
        JButton save = new JButton("Save");
            save.addActionListener((ActionEvent event) -> {

                Circuit circuitForSave = new Circuit();
                List<JButton> gateButtonsForSave = new ArrayList<>(gateButtons);
                List<Gate> componentsForSave = new ArrayList<>(circuit.getListOfComponents());
                circuitForSave.setComponentsOnBoard(componentsForSave);
                        savedCircuits.add(circuitForSave);
                        savedButtons.add(gateButtonsForSave);
                System.out.println(savedCircuits);
                    });

        JButton open = new JButton("Open");



        open.addActionListener((ActionEvent event) -> {
           JPopupMenu openPopup = new JPopupMenu("SD Rules!");

            for (int index = 0; index < savedButtons.size(); index++) {
                JMenuItem savedCircuit = new JMenuItem("Circuit " + (index + 1));

                int finalIndex = index;

                savedCircuit.addActionListener(action ->
                {
                    drawingPanel.removeAll();
                    System.out.println("Working!");
                    System.out.println(gateButtons.size());
                    System.out.println(savedCircuits.get(Integer.parseInt(savedCircuit.getText().substring(8))-1));
                    circuit = savedCircuits.get(Integer.parseInt(savedCircuit.getText().substring(8))-1);
                    gateButtons = savedButtons.get(Integer.parseInt(savedCircuit.getText().substring(8))-1);
                    System.out.println(gateButtons.size());

                    for(JButton gateButtonToAdd : savedButtons.get(Integer.parseInt(savedCircuit.getText().substring(8))-1)){
                        drawingPanel.add(gateButtonToAdd);
                    }
                    drawingPanel.updateConnections(circuit.getListOfComponents(), gateButtons);
                    drawingPanel.updateUI();
                    revalidate();
                    repaint();
                    });

                openPopup.add(savedCircuit);
            }

            openPopup.show(open, open.getWidth()/2, open.getHeight()/2);

        });

            saveOpenPanel.add(save);
            saveOpenPanel.add(open);
            super.add(BorderLayout.NORTH, saveOpenPanel);

    }

    private void createAddAndDeleteMenu()
    {
        JPanel circuitOptions = new JPanel();

        circuitOptions.add(createAddMenu());
        circuitOptions.add(createSimulateButton());

        super.add(circuitOptions, BorderLayout.SOUTH);
    }

    private JButton createSimulateButton()
    {
        JButton simulate = new JButton("Simulate");

        simulate.addActionListener(ActionEvent ->
        {
            circuit.evaluate();

            for (Gate gate : circuit.getListOfComponents())
            {
                if (gate.getOutputWire().getState())
                    gateButtons.get(circuit.getListOfComponents().indexOf(gate)).setBackground(Color.GREEN);
                else
                    gateButtons.get(circuit.getListOfComponents().indexOf(gate)).setBackground(Color.RED);
            }
        });

        return simulate;
    }

    private void createButtonSubMenu(JButton button)
    {
        final JPopupMenu menu = new JPopupMenu("Menu");

        JMenuItem connect = new JMenuItem("Connect");

        if (circuit.getListOfComponents().get(gateButtons.indexOf(button)).getInputWires().size() == 0)
        {
            circuit.getListOfComponents().get(gateButtons.indexOf(button)).setInputWire(new Wire());
            circuit.getListOfComponents().get(gateButtons.indexOf(button)).setInputWire(new Wire());

            for (int index = 0; index < circuit.getListOfComponents().get(gateButtons.indexOf(button)).getNumberOfInputs(); index++) {
                JMenuItem setInput = new JMenuItem("Set Input " + (index + 1));

                int finalIndex = index;
                setInput.addActionListener(action ->
                {
                    JPopupMenu subMenuForSetInput = new JPopupMenu("Set inputs");
                    JMenuItem setToTrue = new JMenuItem("Set to 1");
                    subMenuForSetInput.add(setToTrue);
                    setToTrue.addActionListener(actionForTrue ->
                            circuit.getListOfComponents().get(gateButtons.indexOf(button)).getInputWires().get(finalIndex).setState(true));
                    JMenuItem setToFalse = new JMenuItem("Set to 0");
                    subMenuForSetInput.add(setToFalse);
                    setToFalse.addActionListener(actionForFalse ->
                            circuit.getListOfComponents().get(gateButtons.indexOf(button)).getInputWires().get(finalIndex).setState(false));
                    subMenuForSetInput.show(button, button.getWidth() / 2, button.getHeight() / 2);

                });

                menu.add(setInput);
            }
        }

        connect.addActionListener(actionEvent ->
        {
            JPopupMenu subMenuForConnect = new JPopupMenu("Click here to select button to connect to");

            for (JButton gate : gateButtons)
            {
                JMenuItem gateToChoose = new JMenuItem(gateButtons.get(gateButtons.indexOf(gate)).getText());
                gateToChoose.addActionListener(actionEvent1 ->
                {
                    if (circuit.getListOfComponents().get(gateButtons.indexOf(gate)).getInputWires().size() <= circuit.getListOfComponents().get(gateButtons.indexOf(gate)).getNumberOfInputs())
                    {
                        circuit.connectComponents(circuit.getListOfComponents().get(gateButtons.indexOf(button)), circuit.getListOfComponents().get(gateButtons.indexOf(gate)));
                        drawingPanel.updateConnections(circuit.getListOfComponents(), gateButtons);
                        drawingPanel.updateUI();
                    }
                });
                subMenuForConnect.add(gateToChoose);
            }
            subMenuForConnect.show(button, button.getWidth()/2, button.getHeight()/2);
        });

        menu.add(connect);
        button.addActionListener(actionEvent -> menu.show(button, button.getWidth()/2, button.getHeight()/2));
    }

    private JComboBox<String> createAddMenu()
    {
        JComboBox<String> addGate = new JComboBox<>(GateClassRetriever.retrieveClassList().toArray(new String[0]));

        addGate.addActionListener((ActionEvent event) -> {
            try {
                circuit.addComponent((Gate) Class.forName(addGate.getSelectedItem()
                                                                   .toString())
                                                   .newInstance());

                JButton button = new JButton(addGate.getSelectedItem().toString().substring(8) + " " + circuit.getListOfComponents().size());
                gateButtons.add(button);
                drawingPanel.add(button);
                DragListener drag = new DragListener();
                button.addMouseListener(drag);
                button.addMouseMotionListener(drag);
                createButtonSubMenu(button);
                updateUI();
            }
            catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {e.printStackTrace();}});

        return addGate;
    }

    private void updateUI()
    {
        drawingPanel.setLayout(new AbsoluteManager());
        super.add(drawingPanel);
        super.revalidate();
        super.repaint();
    }

    //Usage from tips4java.wordpress.com
    public class DragListener extends MouseInputAdapter
    {
        Point location;
        MouseEvent pressed;

        public void mousePressed(MouseEvent me)
        {
            pressed = me;
        }

        public void mouseDragged(MouseEvent me)
        {
            Component component = me.getComponent();
            location = component.getLocation(location);
            int x = location.x - pressed.getX() + me.getX();
            int y = location.y - pressed.getY() + me.getY();
            component.setLocation(x, y);
            drawingPanel.updateUI();
        }
    }

    //Layout Manager implemented from 'MadProgrammer' layout manager StackOverflow
    public class AbsoluteManager implements LayoutManager2 {

        @Override
        public void addLayoutComponent(Component comp, Object constraints) {}

        @Override
        public Dimension maximumLayoutSize(Container target) { return preferredLayoutSize(target); }

        @Override
        public float getLayoutAlignmentX(Container target) { return 0.5f; }

        @Override
        public float getLayoutAlignmentY(Container target) { return 0.5f; }

        @Override
        public void invalidateLayout(Container target) {}

        @Override
        public void addLayoutComponent(String name, Component comp) {}

        @Override
        public void removeLayoutComponent(Component comp) {}

        @Override
        public Dimension preferredLayoutSize(Container parent)
        {
            int maxX = 0;
            int maxY = 0;
            for (Component comp : parent.getComponents()) {
                Dimension size = comp.getPreferredSize();
                maxX = Math.max(comp.getX() + size.width, maxX);
                maxY = Math.max(comp.getY() + size.height, maxY);
            }

            return new Dimension(maxX, maxY);
        }

        @Override
        public Dimension minimumLayoutSize(Container parent) {
            return preferredLayoutSize(parent);
        }

        @Override
        public void layoutContainer(Container parent) {
            for (Component comp : parent.getComponents()) {
                Dimension size = comp.getPreferredSize();
                comp.setSize(size);
            }
        }
    }

    public static void main(String[] args) throws ClassNotFoundException
    {
        JFrame frame = new CircuitBuilder();
        frame.setSize(500, 250);
        frame.setVisible(true);
    }
}