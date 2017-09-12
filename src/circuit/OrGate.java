package circuit;

import java.util.List;

public class OrGate extends Gate
{
    public int getNumberOfInputs() { return 2; }

    public boolean evaluate(List<Boolean> inputValues)
    {
        return inputValues.stream()
                          .anyMatch(input -> input);
    }
}
