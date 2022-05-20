package sample.module;

import javafx.beans.property.*;
import javafx.collections.*;

public class Product
{
    private ObservableList<Part> parts = FXCollections.observableArrayList();
    private final IntegerProperty product_id;
    private final StringProperty name;
    private final DoubleProperty price;
    private final IntegerProperty quantity;
    private final IntegerProperty min;
    private final IntegerProperty max;

    public Product()
    {
        product_id = new SimpleIntegerProperty();
        name = new SimpleStringProperty();
        price = new SimpleDoubleProperty();
        quantity = new SimpleIntegerProperty();
        min = new SimpleIntegerProperty();
        max = new SimpleIntegerProperty();
    }

    //setters
    public void product_set_id(Integer productID)
    {
        this.product_id.set(productID);
    }

    public void product_set_name(String name)
    {
        this.name.set(name);
    }

    public void product_set_price(Double price)
    {
        this.price.set(price);
    }

    public void product_set_quantity(Integer quantity)
    {
        this.quantity.set(quantity);
    }

    public void product_set_min(Integer min)
    {
        this.min.set(min);
    }

    public void product_set_max(Integer max)
    {
        this.max.set(max);
    }

    public void product_set_part(ObservableList<Part> parts) {
        this.parts = parts;
    }




    //getters
    public  int product_get_id()
    {
        return this.product_id.get();
    }

    public String product_get_name()
    {
        return this.name.get();
    }

    public double product_get_price()
    {
        return this.price.get();
    }

    public int product_get_quantity()
    {
        return this.quantity.get();
    }

    public int product_get_min()
    {
        return this.min.get();
    }

    public int product_get_max()
    {
        return this.max.get();
    }

    public ObservableList product_get_parts()
    {
        return parts;
    }


    public IntegerProperty product_property_id() {
        return product_id;
    }

    public StringProperty product_property_name() {
        return name;
    }

    public DoubleProperty product_property_price() {
        return price;
    }

    public IntegerProperty product_property_quantity() {
        return quantity;
    }

    public IntegerProperty product_property_min() {
        return min;
    }

    public IntegerProperty product_property_max() {
        return max;
    }



    public static String validateProduct(String name, double price, int stock, int min, int max, String error)
    {
        if(name == null)
        {
            error = error + "Name field is required ";
        }

        if(price <= 0)
        {
            error = error + "Price can not be negative ";
        }

        if(stock < min || stock > max)
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

 associatedParts:ObservableList<Part>
- id : int
- name : String
- price : double
- stock : int
- min : int
- max : int
+ Product(id : int, name : String,
price : double, stock : int, min : int, max : int)
+ setId(id:int):void
+ setName(name:String):void
+ setPrice(price:double):void
+ setStock(stock:int):void
+ setMin(min:int):void
+ setMax(max:int):void
+ setPrice(max:int):void
+ getId():int
+ getName():String
+ getPrice():double
+ getStock():int
+ getMin():int
+ getMax():int
+ addAssociatedPart(part:Part):void
+ deleteAssociatedPart(selectedAspart:Part):boolean
+ getAllAssociatedParts():ObservableList<Part>


 */