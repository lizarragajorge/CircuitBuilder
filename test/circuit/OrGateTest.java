package circuit;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class OrGateTest extends GateTest
{
    @Override
    Gate createComponent(){
        return new OrGate();
    }

    void setGateInputToReturnTrue()
    {
        gate.setInputWire(trueWire);
        gate.setInputWire(trueWire);
    }

    void setGateInputToReturnFalse()
    {
        gate.setInputWire(falseWire);
        gate.setInputWire(falseWire);
    }

    @Test
    public void orGateOperationReturnsFalseForAlternativeInputWhereFirstIsTrueAndSecondIsFalse()
    {
        gate.setInputWire(trueWire);
        gate.setInputWire(falseWire);
        gate.evaluateAndSet();
        assertTrue(gate.getOutputWire().getState());
    }

    @Test
    public void orGateOperationReturnsTrueForAlternativeInputWhereFirstIsFalseAndSecondIsTrue()
    {
        gate.setInputWire(falseWire);
        gate.setInputWire(trueWire);
        gate.evaluateAndSet();
        assertTrue(gate.getOutputWire().getState());
    }
}
