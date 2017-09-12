package circuit;


import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;

public class WireTest
{
    Wire wireTotest;

    @Before
    public void setUp() {
        wireTotest = new Wire();
    }

    @Test
    public void wireStateIsInitializedToZero() { assertFalse(wireTotest.getState()); }
}
