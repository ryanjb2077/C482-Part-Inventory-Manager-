package sample.module;

import javafx.beans.property.*;

public abstract class Part
{
    private final IntegerProperty part_id;
    private final StringProperty name;
    private final DoubleProperty price;
    private final IntegerProperty quantity;
    private final IntegerProperty min;
    private final IntegerProperty max;

    public Part()
    {
        part_id = new SimpleIntegerProperty();
        name = new SimpleStringProperty();
        price = new SimpleDoubleProperty();
        quantity = new SimpleIntegerProperty();
        min = new SimpleIntegerProperty();
        max = new SimpleIntegerProperty();
    }

    //setters
    public void part_set_id(Integer partID)
    {
        this.part_id.set(partID);
    }

    public void part_set_name(String name)
    {
        this.name.set(name);
    }

    public void part_set_price(Double price)
    {
        this.price.set(price);
    }

    public void part_set_quantity(Integer quantity)
    {
        this.quantity.set(quantity);
    }

    public void part_set_min(Integer min)
    {
        this.min.set(min);
    }

    public void part_set_max(Integer max)
    {
        this.max.set(max);
    }


    //getters


    public  int part_get_id()
    {
        return this.part_id.get();
    }

    public String part_get_name()
    {
        return this.name.get();
    }

    public double part_get_price()
    {
        return this.price.get();
    }

    public int part_get_quantity()
    {
        return this.quantity.get();
    }

    public int part_get_min()
    {
        return this.min.get();
    }

    public int part_get_max()
    {
        return this.max.get();
    }


    public IntegerProperty part_property_id() {
        return part_id;

    }

    public StringProperty part_property_name() {
        return name;
    }

    public DoubleProperty part_property_price() {
        return price;
    }

    public IntegerProperty part_property_quantity() {
        return quantity;
    }

    public IntegerProperty part_property_min() {
        return min;
    }

    public IntegerProperty part_property_max() {
        return max;
    }


    public static String part_validate(String name, double price, int quantity, int min, int max, String error)
    {
        if(name == null)
        {
            error = error + "Name field is required ";
        }

        if(price <= 0)
        {
            error = error + "Price can not be negative ";
        }

        if(quantity < min || quantity > max)
        {
            error = error + "Quantity does not fall within specified min or max value ";
        }

        if(min > max)
        {
            error = error + "Min QTY is larger then max QTY ";
        }

        return error;
    }


}


/*

d : int
- name : String
- price : double
- stock : int
- min : int
- max : int
+ Part(id : int, name : String,
price : double, stock : int, min : int, max : int)
+ setId(id:int):void
+ setName(name:String):void
+ setPrice(price:double):void
+ setStock(stock:int):void
+ setMin(min:int):void
+ setMax(max:int):void
+ getId():int
+ getName():String
+ getPrice():double
+ getStock():int
+ getMin():int
+ getMax():int


 */
