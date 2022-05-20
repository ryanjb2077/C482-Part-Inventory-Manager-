/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.view;

import javafx.collections.*;
import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.stage.*;
import sample.module.*;

import java.io.*;
import java.net.*;
import java.util.*;

import static sample.module.Inventory.*;

/**
 * FXML Controller class
 *
 * @author ryan
 */
public class Product_add_controller implements Initializable {

    @FXML
    private TextField product_field_id;
    @FXML
    private TextField product_field_name;
    @FXML
    private TextField product_field_quantity;
    @FXML
    private TextField product_field_price;
    @FXML
    private TextField product_field_min;
    @FXML
    private TextField product_field_max;
    @FXML
    private Button product_save_button;
    @FXML
    private Button product_cancel_button;
    @FXML
    private TableView<Part> product_table_add;
    @FXML
    private TableColumn<Part, Integer> add_table_id;
    @FXML
    private TableColumn<Part, String> add_table_name;
    @FXML
    private TableColumn<Part, Integer> add_table_quantity;
    @FXML
    private TableColumn<Part, Double> add_table_price;
    @FXML
    private TextField product_search_bar;
    @FXML
    private Button product_search_button;
    @FXML
    private Button product_delete_button;
    @FXML
    private Button product_add_button;
    @FXML
    private TableView<Part> product_table_delete;
    @FXML
    private TableColumn<Part, Integer> delete_table_id;
    @FXML
    private TableColumn<Part, String> delete_table_name;
    @FXML
    private TableColumn<Part, Integer> delete_table_quantity;
    @FXML
    private TableColumn<Part, Double> delete_table_price;


    private String exception_throw = new String();;
    private  int product_id;
    private ObservableList<sample.module.Part> parts_current = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        add_table_id.setCellValueFactory(cellData -> cellData.getValue().part_property_id().asObject());
        add_table_name.setCellValueFactory(cellData -> cellData.getValue().part_property_name());
        add_table_quantity.setCellValueFactory(cellData -> cellData.getValue().part_property_quantity().asObject());
        add_table_price.setCellValueFactory(cellData -> cellData.getValue().part_property_price().asObject());
        delete_table_id.setCellValueFactory(cellData -> cellData.getValue().part_property_id().asObject());
        delete_table_name.setCellValueFactory(cellData -> cellData.getValue().part_property_name());
        delete_table_quantity.setCellValueFactory(cellData -> cellData.getValue().part_property_quantity().asObject());
        delete_table_price.setCellValueFactory(cellData -> cellData.getValue().part_property_price().asObject());
        add_table_update();
        delete_table_update();
        product_id = sample.module.Inventory.product_get_count_id();
        product_field_id.setText("Primary Key:  " + product_id);
    }

    public void add_table_update() {
        product_table_add.setItems(part_get_inventory());
    }

    public void delete_table_update() {
        product_table_delete.setItems(parts_current);
    }

    @FXML
    private void product_save(ActionEvent event) throws IOException
    {
        String product_name = product_field_name.getText();
        String product_quantity = product_field_quantity.getText();
        String product_price = product_field_price.getText();
        String product_min = product_field_min.getText();
        String product_max = product_field_max.getText();

        try
        {
            exception_throw = sample.module.Part.part_validate(product_name, Double.parseDouble(product_price),Integer.parseInt(product_quantity), Integer.parseInt(product_min), Integer.parseInt(product_max), exception_throw);
            if (exception_throw.length() > 0)
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText("Error Adding Product");
                alert.setContentText(exception_throw);
                alert.showAndWait();
                exception_throw = "";
            }
            else
            {
                System.out.println(product_name);
                Product in_product = new Product();
                in_product.product_set_id(product_id);
                in_product.product_set_name(product_name);
                in_product.product_set_price(Double.parseDouble(product_price));
                in_product.product_set_quantity(Integer.parseInt(product_quantity));
                in_product.product_set_min(Integer.parseInt(product_min));
                in_product.product_set_max(Integer.parseInt(product_max));
                in_product.product_set_part(parts_current);
                sample.module.Inventory.product_add(in_product);
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
            alert.setHeaderText("Error Adding Product");
            alert.setContentText("Contains blank fields");
            alert.showAndWait();
        }
    }

    @FXML
    private void product_cancel(ActionEvent event) throws IOException
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle("Cancel Confirmation");
        alert.setHeaderText("Cancel Confirmation");
        alert.setContentText("Stop adding products and return to the main menu?");
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

    @FXML
    private void product_search(ActionEvent event)
    {
        String product_search_barText  = product_search_bar.getText();
        int index = -1;

        if(Inventory.part_look_up(product_search_barText) == -1)
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Search failed");
            alert.setHeaderText("Product not found");
            alert.setContentText("There are no matching parts in the data base");
            alert.showAndWait();
        }
        else
        {
            index = Inventory.part_look_up(product_search_barText);
            Part temp = Inventory.part_get_inventory().get(index);
            ObservableList<Part> temp_list = FXCollections.observableArrayList();
            temp_list.add(temp);
            product_table_add.setItems(temp_list);
        }
    }

    @FXML
    private void product_delete(ActionEvent event)
    {
        Part part = product_table_delete.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle("Product Deletion");
        alert.setHeaderText("Confirm Deletion");
        alert.setContentText("Are you sure? " + part.part_get_name() + "  Will be removed from the product");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            System.out.println(part.part_get_name() + " Was removed from the product");
            parts_current.remove(part);
        }
        else {
            System.out.println(part.part_get_name() + " Was not removed from the product");
        }
    }

    @FXML
    private void product_add(ActionEvent event)
    {
       Part part = product_table_add.getSelectionModel().getSelectedItem();
       parts_current.add(part);
       add_table_update();
    }
}
