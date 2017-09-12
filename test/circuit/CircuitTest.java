package circuit;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class CircuitTest {

    Circuit circuit;
    Gate notGate;
    Gate orGate;
    Wire trueWire = new Wire();
    Wire falseWire = new Wire();

    @Before
    public void setUp()
    {
        circuit = new Circuit();
        notGate = new NotGate();
        orGate = new OrGate();
        trueWire.setState(true);
        falseWire.setState(false);
    }

    @Test
    public void addComponentAddsComponent(){
        circuit.addComponent(new OrGate());
        assertEquals(1, circuit.getListOfComponents().size());
    }

    @Test
    public void circuitListIsEmptyAfterRemovingAddedGate()
    {
        OrGate myOrGate = new OrGate();
        circuit.addComponent(myOrGate);
        circuit.removeComponent(myOrGate);
        assertEquals(0, circuit.getListOfComponents().size());
    }

    @Test
    public void connectComponentsResultsInAPositiveOutput()
    {
        circuit.addComponent(notGate);
        circuit.addComponent(orGate);
        circuit.connectComponents(orGate, notGate);
        circuit.evaluate();
        assertEquals(true, notGate.getOutputWire().getState());
    }

    @Test
    public void connectComponentsResultsInASharedWireBetweenTheTwoComponents()
    {
        circuit.addComponent(notGate);
        circuit.addComponent(orGate);
        circuit.connectComponents(notGate, orGate);
        assertEquals(orGate.getInputWires().get(0), notGate.getOutputWire());
    }

    @Test
    public void removeComponentsSetsInputWireReliantOnRemovedComponentToFalse(){
        circuit.addComponent(notGate);
        circuit.addComponent(orGate);
        circuit.connectComponents(notGate, orGate);
        circuit.removeComponent(notGate);
        assertEquals(false, orGate.getInputWires().get(0).getState());
    }

    @Test
    public void setComponentsOnBoardSetsTheComponentsOnBoardToTheInputList(){
        List<Gate> listOfGatesToSetTo = Arrays.asList(new OrGate(), new AndGate(), new NotGate());
        circuit.setComponentsOnBoard(listOfGatesToSetTo);
        assertEquals(circuit.getListOfComponents(), listOfGatesToSetTo);
    }
}
