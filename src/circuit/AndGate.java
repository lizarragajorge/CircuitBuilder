package circuit;

import java.util.List;

public class AndGate extends Gate
{
    public int getNumberOfInputs() { return 2; }
    
    public boolean evaluate(List<Boolean> inputValues)
    {
        boolean result;

        if (inputValues.isEmpty() || inputValues.size() == 1)
            result = false;
        else
        {
            result = true;
            for (Boolean inputValue : inputValues)
            {
                 if (!inputValue)
                 result = false;
            }
        }

        return result;
    }
}
