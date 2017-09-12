package circuit;

import java.util.ArrayList;
import java.util.List;


public class Circuit
{
    private List<Gate> componentsOnBoard = new ArrayList<>();

    public List<Gate> getListOfComponents(){ return componentsOnBoard; }

    public void evaluate() { componentsOnBoard.forEach(Gate::evaluateAndSet); }

    public void addComponent(Gate gateToAdd){ 
        componentsOnBoard.add(gateToAdd);
    }

    public void connectComponents(Gate gateToConnectFrom, Gate gateToConnectTo)
    {
        gateToConnectTo.setInputWire(gateToConnectFrom
                       .getOutputWire());
    }

    public void removeComponent(Gate gateToDelete)
    {
        gateToDelete.getOutputWire()
                    .setState(false);
        componentsOnBoard.remove(gateToDelete);
    }

    public void setComponentsOnBoard(List<Gate> newComponents){
        componentsOnBoard = newComponents;
    }
}
