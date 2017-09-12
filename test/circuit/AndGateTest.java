package circuit;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertFalse;

public class AndGateTest extends GateTest
{
    @Override
    Gate createComponent(){
        return new AndGate();
    }

    void setGateInputToReturnTrue()
    {
        gate.setInputWire(trueWire);
        gate.setInputWire(trueWire);
    }

    void setGateInputToReturnFalse()
    {
        gate.setInputWire(trueWire);
        gate.setInputWire(falseWire);
    }

    @Test
    public void andGateOperationReturnsFalseForAlternativeInputWhereFirstIsTrueAndSecondIsFalse()
    {
        gate.setInputWire(trueWire);
        gate.setInputWire(falseWire);
        gate.evaluateAndSet();
        assertFalse(gate.getOutputWire().getState());
    }

    @Test
    public void andGateOperationReturnsFalseForAlternativeInputWhereFirstIsFalseAndSecondIsTrue()
    {
        gate.setInputWire(falseWire);
        gate.setInputWire(trueWire);
        gate.evaluateAndSet();
        assertFalse(gate.getOutputWire().getState());
    }

    @Test
    public void evaluateReturnsFalseWhenOnlyOneInputIsPassed(){
        List<Boolean> listWithOneValue = Arrays.asList(false);
        assertFalse(gate.evaluate(listWithOneValue));
    }
}
