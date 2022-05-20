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


public class main_menu_controller implements Initializable {

    private static sample.module.Part part_modify;
    private static int part_modify_index;
    private static sample.module.Product product_modify;
    private static int product_modify_index;

    @FXML
    private TableView<Part> part_table;
    @FXML
    private TableColumn<Part, Integer> part_table_id;
    @FXML
    private TableColumn<Part, String> part_table_name;
    @FXML
    private TableColumn<Part, Integer> part_table_quantity;
    @FXML
    private TableColumn<Part, Double> part_table_price;
    @FXML
    private TableView<Product> product_table;
    @FXML
    private TableColumn<Product, Integer> product_table_id;
    @FXML
    private TableColumn<Product, String> product_table_name;
    @FXML
    private TableColumn<Product, Integer> product_table_quantity;
    @FXML
    private TableColumn<Product, Double> product_table_price;
    @FXML
    private TextField part_search_bar;
    @FXML
    private TextField product_search_bar;
    @FXML
    private Button part_search_button;
    @FXML
    private Button product_search_button;
    @FXML
    private Button part_delete_button;
    @FXML
    private Button part_modify_button;
    @FXML
    private Button part_add_button;
    @FXML
    private Button product_delete_button;
    @FXML
    private Button product_modify_button;
    @FXML
    private Button product_add_button;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        part_table_id.setCellValueFactory(cellData -> cellData.getValue().part_property_id().asObject());
        part_table_name.setCellValueFactory(cellData -> cellData.getValue().part_property_name());
        part_table_quantity.setCellValueFactory(cellData -> cellData.getValue().part_property_quantity().asObject());
        part_table_price.setCellValueFactory(cellData -> cellData.getValue().part_property_price().asObject());
        product_table_id.setCellValueFactory(cellData -> cellData.getValue().product_property_id().asObject());
        product_table_name.setCellValueFactory(cellData -> cellData.getValue().product_property_name());
        product_table_quantity.setCellValueFactory(cellData -> cellData.getValue().product_property_quantity().asObject());
        product_table_price.setCellValueFactory(cellData -> cellData.getValue().product_property_price().asObject());
        part_table_update();
        product_table_update();
    }

    public static int part_get_modify_index() {
        return part_modify_index;
    }

    public static int product_get_modify_index() {
        return product_modify_index;
    }


    public void part_table_update()
    {
        part_table.setItems(part_get_inventory());
    }

    public void product_table_update()
    {
        product_table.setItems(product_get_inventory());
    }

    @FXML
    private void part_search(ActionEvent event)
    {
        String part_search_barText  = part_search_bar.getText();
        int index = -1;

        if(Inventory.part_look_up(part_search_barText) == -1)
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Search failed");
            alert.setHeaderText("Part not found");
            alert.setContentText("There are no matching parts in the data base");
            alert.showAndWait();
        }
        else
        {
            index = Inventory.part_look_up(part_search_barText);
            Part temp = Inventory.part_get_inventory().get(index);
            ObservableList<Part> temp_list = FXCollections.observableArrayList();
            temp_list.add(temp);
            part_table.setItems(temp_list);
        }
    }

    @FXML
    private void product_search(ActionEvent event)
    {
        String product_search_barText  = product_search_bar.getText();
        int index = -1;

        if(Inventory.product_look_up(product_search_barText) == -1)
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Search failed");
            alert.setHeaderText("Product not found");
            alert.setContentText("There are no matching parts in the data base");
            alert.showAndWait();
        }
        else
        {
            index = Inventory.product_look_up(product_search_barText);
            Product temp = Inventory.product_get_inventory().get(index);
            ObservableList<Product> temp_list = FXCollections.observableArrayList();
            temp_list.add(temp);
            product_table.setItems(temp_list);
        }
    }

    @FXML
    private void part_handle_delete(ActionEvent event)
    {
        sample.module.Part part = part_table.getSelectionModel().getSelectedItem();
        if(part_validate_delete(part))
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Deletion Failure");
            alert.setHeaderText("Part can not be deleted");
            alert.setContentText("Part is being used by a product");
            alert.showAndWait();
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.initModality(Modality.NONE);
            alert.setTitle("Part Deletion");
            alert.setHeaderText("Confirm Deletion");
            alert.setContentText("Are you sure?" + part.part_get_name() + "  Will be deleted");
            Optional<ButtonType> result = alert.showAndWait();

            if(result.get() == ButtonType.OK)
            {
                part_delete(part);
                part_table_update();
                System.out.println(part.part_get_name() + " Was removed from the date base");
            }

            else
            {
                System.out.println(part.part_get_name() + " Was not removed from the date base");
            }
        }
    }

    @FXML
    private void part_handle_modify(ActionEvent event) throws IOException
    {
        if(0 == part_get_inventory().size())
        {  
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Error Modifying Product");
            alert.setContentText("Part must be added before modifying.");
            alert.showAndWait();
        }
        else
        {
        part_modify = part_table.getSelectionModel().getSelectedItem();
        part_modify_index = part_get_inventory().indexOf(part_modify);
        Parent part_modify_parent = FXMLLoader.load(getClass().getResource("part_modify.fxml"));
        Scene part_modify_scene = new Scene(part_modify_parent);
        Stage part_modify_stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        part_modify_stage.setScene(part_modify_scene);
        part_modify_stage.show();
        }
    }

    @FXML
    private void part_handle_add(ActionEvent event) throws IOException
    {
        Parent part_add_parent = FXMLLoader.load(getClass().getResource("part_add.fxml"));
        Scene part_add_scene = new Scene(part_add_parent);
        Stage part_add_stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        part_add_stage.setScene(part_add_scene);
        part_add_stage.show();
    }

    @FXML
    private void product_handle_delete(ActionEvent event)
    {
        sample.module.Product product = product_table.getSelectionModel().getSelectedItem();
        if(product_validate_delete(product))
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Deletion Failure");
            alert.setHeaderText("Product can not be deleted");
            alert.setContentText("Product is being used by a part");
            alert.showAndWait();
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.initModality(Modality.NONE);
            alert.setTitle("Product Deletion");
            alert.setHeaderText("Confirm Deletion");
            alert.setContentText("Are you sure?" + product.product_get_name() + "  Will be deleted");
            Optional<ButtonType> result = alert.showAndWait();

            if(result.get() == ButtonType.OK)
            {
                product_delete(product);
                product_table_update();
                System.out.println(product.product_get_name() + " Was removed from the date base");
            }

            else
            {
                System.out.println(product.product_get_name() + " Was not removed from the date base");
            }
        }
    }

    @FXML
    private void product_handle_modify(ActionEvent event) throws IOException
    {
        if(0 == product_get_inventory().size())
        {
                  
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Error Modifying Product");
            alert.setContentText("Product must be added before modifying.");
            alert.showAndWait();
        }
        else
        {
            product_modify = product_table.getSelectionModel().getSelectedItem();
            product_modify_index = product_get_inventory().indexOf(product_modify);
            Parent product_modify_parent = FXMLLoader.load(getClass().getResource("product_modify.fxml"));
            Scene product_modify_scene = new Scene(product_modify_parent);
            Stage product_modify_stage = (Stage)((Node) event.getSource()).getScene().getWindow();
            product_modify_stage.setScene(product_modify_scene);
            product_modify_stage.show();
        }
  
        
    }

    @FXML
    private void product_handle_add(ActionEvent event) throws IOException
    {
        Parent product_add_parent = FXMLLoader.load(getClass().getResource("product_add.fxml"));
        Scene product_add_scene = new Scene(product_add_parent);
        Stage product_add_stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        product_add_stage.setScene(product_add_scene);
        product_add_stage.show();
    }
    
}
