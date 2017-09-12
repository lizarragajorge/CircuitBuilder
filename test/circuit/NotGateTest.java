package circuit;

public class NotGateTest extends GateTest
{

    void setGateInputToReturnTrue() {gate.setInputWire(falseWire); }

    void setGateInputToReturnFalse()
    {
        gate.setInputWire(trueWire);
    }

    Gate createComponent()
    {
        return new NotGate();
    }

}
