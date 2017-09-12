package circuit;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GateClassRetrieverTest
{
    @Test
    public void retrieveClassList()
    {
        GateClassRetriever gateClassRetriever = new GateClassRetriever();
        assertEquals(3, gateClassRetriever.retrieveClassList().size());
    }
}
