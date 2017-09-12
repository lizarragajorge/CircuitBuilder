package circuit;

import java.util.List;

public class NotGate extends Gate
{
    public int getNumberOfInputs() { return 1; }

    public boolean evaluate(List<Boolean> inputValues)
    {
        if (inputValues.isEmpty())
            return false;
        return !inputValues.get(0);
    }
}
