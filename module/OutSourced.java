package sample.module;

import javafx.beans.property.*;

public class OutSourced extends Part
{
    private final StringProperty company_name;

    public OutSourced()
    {
        super();
        company_name = new SimpleStringProperty();
    }

    public void company_set_name(String newName)
    {
        this.company_name.set(newName);
    }

    public String company_get_name()
    {
        return this.company_name.get();
    }
}

/*
- companyName : String
+ Outsourced(id : int, name : String,
price : double, stock : int, min : int, max : int,
companyName:String)
+ setCompanyName(companyName:String):void
+ getCompanyName():String

 */
