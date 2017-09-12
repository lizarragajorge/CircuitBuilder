package circuit;

import java.util.ArrayList;
import java.util.List;
import static java.util.stream.Collectors.*;

public abstract class Gate
{
    private List<Wire> inputWires = new ArrayList<>();
    private Wire outputWire = new Wire();

    public abstract int getNumberOfInputs();
    public abstract boolean evaluate(List<Boolean> inputValues);

    public void evaluateAndSet()
    {
        List<Boolean> inputValues = inputWires.stream()
                  .map(Wire::getState)
                  .collect(toList());
                  
        outputWire.setState(evaluate(inputValues));
    }

    public void setInputWire(Wire inputWire)
    {
        if (inputWires.size() < getNumberOfInputs())
            inputWires.add(inputWire);
        else
            inputWires.remove(0);
            inputWires.add(inputWire);
    }

    public Wire getOutputWire()
    {
        return outputWire;
    }

    public List<Wire> getInputWires()
    {
        return inputWires;
    }


}
