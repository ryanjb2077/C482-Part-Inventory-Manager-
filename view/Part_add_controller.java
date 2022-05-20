/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.view;

import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.stage.*;
import sample.module.*;

import java.io.*;
import java.net.*;
import java.util.*;

/**
 * FXML Controller class
 *
 * @author ryan
 */
public class Part_add_controller implements Initializable {

    @FXML
    private RadioButton radio_house_button;
    @FXML
    private ToggleGroup one;
    @FXML
    private RadioButton radio_out_button;
    @FXML
    private TextField part_field_id;
    @FXML
    private TextField part_field_name;
    @FXML
    private TextField part_field_quantity;
    @FXML
    private TextField part_field_price;
    @FXML
    private TextField part_field_min;
    @FXML
    private TextField part_field_max;
    @FXML
    private TextField part_field_dynamic;
    @FXML
    private Button part_save_button;
    @FXML
    private Button part_cancel_button;
    @FXML
    private Label part_name_dynamic;

    private boolean out_sourced;
    private String exception_throw = new String();;
    private  int part_id;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        part_id = Inventory.part_get_count_id();
        part_field_id.setText("Primary key: " + part_id);
    }    

    @FXML
    private void radio_house(ActionEvent event)
    {
        out_sourced = false;
        part_name_dynamic.setText("Machine ID");
        part_field_dynamic.setPromptText("Machine ID");
        radio_out_button.setSelected(false);
    }

    @FXML
    private void radio_out(ActionEvent event)
    {
        out_sourced = true;
        part_name_dynamic.setText("Company ID");
        part_field_dynamic.setPromptText("Company ID");
        radio_house_button.setSelected(false);
    }

    @FXML
    private void part_save(ActionEvent event)throws IOException
    {
        String part_name = part_field_name.getText();
        String part_quantity = part_field_quantity.getText();
        String part_price = part_field_price.getText();
        String part_min = part_field_min.getText();
        String part_max = part_field_max.getText();
        String part_dynamic = part_field_dynamic.getText();

        try
        {
            exception_throw = sample.module.Part.part_validate(part_name, Double.parseDouble(part_price),Integer.parseInt(part_quantity), Integer.parseInt(part_min), Integer.parseInt(part_max), exception_throw);
            if (exception_throw.length() > 0)
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText("Error Adding Part");
                alert.setContentText(exception_throw);
                alert.showAndWait();
                exception_throw = "";
            }
            else
            {
                if (out_sourced == false)
                {
                    System.out.println(part_name);
                    InHouse in_part = new InHouse();
                    in_part.part_set_id(part_id);
                    in_part.part_set_name(part_name);
                    in_part.part_set_price(Double.parseDouble(part_price));
                    in_part.part_set_quantity(Integer.parseInt(part_quantity));
                    in_part.part_set_min(Integer.parseInt(part_min));
                    in_part.part_set_max(Integer.parseInt(part_max));
                    in_part.machine_set_id(Integer.parseInt(part_dynamic));
                    sample.module.Inventory.part_add(in_part);
                }
                else
                    {
                    System.out.println(part_name);
                    OutSourced out_part = new OutSourced();
                    out_part.part_set_id(part_id);
                    out_part.part_set_name(part_name);
                    out_part.part_set_price(Double.parseDouble(part_price));
                    out_part.part_set_quantity(Integer.parseInt(part_quantity));
                    out_part.part_set_min(Integer.parseInt(part_min));
                    out_part.part_set_max(Integer.parseInt(part_max));
                    out_part.company_set_name(part_dynamic);
                    sample.module.Inventory.part_add(out_part);
                    }
            }
            Parent product_add_parent = FXMLLoader.load(getClass().getResource("main_menu.fxml"));
            Scene product_add_scene = new Scene(product_add_parent);
            Stage product_add_stage = (Stage)((Node) event.getSource()).getScene().getWindow();
            product_add_stage.setScene(product_add_scene);
            product_add_stage.show();

        }
            catch(NumberFormatException e)
            {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Error Adding Part");
            alert.setContentText("Contains blank fields");
            alert.showAndWait();
            }
    }


    @FXML
    private void part_cancel(ActionEvent event) throws IOException
    {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle("Cancel Confirmation");
        alert.setHeaderText("Cancel Confirmation");
        alert.setContentText("Stop adding parts and return to the main menu?");
        Optional<ButtonType> result = alert.showAndWait();

        if(result.get() == ButtonType.OK)
        {
            Parent menu_add_parent = FXMLLoader.load(getClass().getResource("main_menu.fxml"));
            Scene menu_add_scene = new Scene(menu_add_parent);
            Stage menu_add_stage = (Stage)((Node) event.getSource()).getScene().getWindow();
            menu_add_stage.setScene(menu_add_scene);
            menu_add_stage.show();
        }
        else
        {
            System.out.println("Cancel clicked");
        }
    }
}
