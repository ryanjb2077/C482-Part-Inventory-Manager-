package sample.module;

import javafx.beans.property.*;

public class InHouse extends Part
{
    private final IntegerProperty machine_id;

    public InHouse()
    {
        super();
        machine_id = new SimpleIntegerProperty();
    }

    public void machine_set_id(int newId)
    {
        this.machine_id.set(newId);
    }

    public Integer machine_get_id()
    {
        return this.machine_id.get();
    }
}

/*

- machineId : int
+ InHouse(id : int, name : String,
price : double, stock : int, min : int, max : int, machineId:int)
+ setMachineId(machineId:int):void
+ getMachineId():int



 */