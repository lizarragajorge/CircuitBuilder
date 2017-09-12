package circuit;


import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

abstract class GateTest {
    public Gate gate;
    public Wire trueWire = new Wire();
    public Wire falseWire = new Wire();

    @Before
    public void setUp()
    {
        gate = createComponent();
        trueWire.setState(true);
        falseWire.setState(false);
    }

    abstract Gate createComponent();
    abstract void setGateInputToReturnTrue();
    abstract void setGateInputToReturnFalse();

    @Test
    public void componentDefaultStateIsFalse()
    {
        assertFalse(gate.getOutputWire().getState());
    }

    @Test
    public void componentsEvaluationSetsOutputWireToTrue()
    {
        setGateInputToReturnTrue();
        gate.evaluateAndSet();
        assertTrue(gate.getOutputWire().getState());
    }

    @Test
    public void componentOperationSetOutputWireToFalse()
    {
        setGateInputToReturnFalse();
        gate.evaluateAndSet();
        assertFalse(gate.getOutputWire().getState());
    }

    @Test
    public void evaluateReturnsFalseForEmptyInputList(){
        assertFalse(gate.evaluate(new ArrayList<>()));

    }
}
