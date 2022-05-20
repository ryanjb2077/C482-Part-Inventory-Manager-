package sample.module;

import javafx.collections.*;

public class Inventory
{
    private static ObservableList<Part> part_inventory = FXCollections.observableArrayList();
    private static ObservableList<Product> product_inventory = FXCollections.observableArrayList();

    // primary key counter
    private static int part_count_id = 0;
    private static int product_count_id = 0;

    public static  void part_add(Part part)
    {
        part_inventory.add(part);
    }

    public static int part_look_up(String look_up)
    {
        boolean found = false;
        int index = 0;

        if(is_string_int(look_up))
        {
            for (int i = 0; i < part_inventory.size(); i++)
            {
                if (Integer.parseInt(look_up) == part_inventory.get(i).part_get_id())
                {
                    index = 1;
                    found = true;
                }
            }
        }
        else
        {
            for(int i = 0; i < part_inventory.size(); i++)
            {
                look_up = look_up.toLowerCase();
                if(look_up.equals(part_inventory.get(i).part_get_name().toLowerCase()))
                {
                    index = i;
                    found = true;
                }
            }
        }

        if (found == true) {
            return index;
        }
        else {
            System.out.println("No parts found in data base");
            return -1;
        }
    }

    public static void part_update(int index, Part part)
    {
        part_inventory.set(index, part);
    }

    public static  void part_delete(Part part) {
        part_inventory.remove(part);
    }


    public static ObservableList<sample.module.Part> part_get_inventory()
    {
        return part_inventory;
    }

    public static int part_get_count_id()
    {
        part_count_id++;
        return part_count_id;
    }
    

    public static boolean part_validate_delete(sample.module.Part part) {
        boolean found = false;
        for (int i = 0; i < product_inventory.size(); i++)
        {
            if (product_inventory.get(i).product_get_parts().contains(part))
            {
                found = true;
            }
        }
        return found;
    }



    //Products


    public static  void product_add(Product product)
    {
        product_inventory.add(product);
    }

    public static int product_look_up(String look_up)
    {
        boolean found = false;
        int index = 0;

        if(is_string_int(look_up))
        {
            for (int i = 0; i < product_inventory.size(); i++)
            {
                if (Integer.parseInt(look_up) == product_inventory.get(i).product_get_id())
                {
                    index = 1;
                    found = true;
                }
            }
        }
        else
        {
            for(int i = 0; i < product_inventory.size(); i++)
            {
                look_up = look_up.toLowerCase();
                if(look_up.equals(product_inventory.get(i).product_get_name().toLowerCase()))
                {
                    index = i;
                    found = true;
                }
            }
        }

        if (found == true) {
            return index;
        }
        else {
            System.out.println("No product found in data base");
            return -1;
        }
    }

    public static void product_update(int index, Product product)
    {
        product_inventory.set(index,product);
    }

    public static  void product_delete(Product product)
    {
        product_inventory.remove(product);
    }

    public static ObservableList<sample.module.Product> product_get_inventory()
    {
        return product_inventory;
    }

    public static int product_get_count_id()
    {
        product_count_id++;
        return product_count_id;
    }
    
     public static int product_get_count_id_modify()
    {
        return product_count_id;
    }

    public static boolean is_string_int(String s)
    {
        try
        {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException ex)
        {
            return false;
        }
    }

    public static boolean product_validate_delete(sample.module.Product product) {
        boolean found = false;
        int productID = product.product_get_id();
        for (int i=0; i < product_inventory.size(); i++)
        {
            if (product_inventory.get(i).product_get_id() == productID)
            {
                if (!product_inventory.get(i).product_get_parts().isEmpty())
                {
                    found = true;
                }
            }
        }
        return found;
    }


}

/*  requirements

- allParts:ObservableList<Part>
- allProducts:ObservableList<Product>
+ addPart(newPart:Part):void
        + addProduct(newProduct:Product):void
        + lookupPart(partId:int):Part
        + lookupProduct(productId:int):Product
        + lookupPart(partName:String):ObservableList<Part>
+ lookupProduct(productName:String):ObservableList<Product>
+ updatePart(index:int, selectedPart:Part):void
        + updateProduct(index:int, newProduct:Product):void
        + deletePart(selectedPart:Part):boolean
        + deleteProduct(selectedProduct:Product):boolean
        + getAllParts():ObservableList<Part>
+ getAllProducts():ObservableList<Product>
*/