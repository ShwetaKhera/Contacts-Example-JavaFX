package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import sample.contact.Contact;
import sample.contact.ContactData;

import java.util.Optional;


public class Controller {
    @FXML TableView<Contact> tableView;
    @FXML BorderPane mainBorderPane;

    private ContactData data;

    public void initialize(){
        data = new ContactData();
        data.loadContacts();
        tableView.setItems(data.getContacts());
    }

    @FXML public void addNewContact(){
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainBorderPane.getScene().getWindow());
        dialog.setTitle("Add New Contact");

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("contactDialog.fxml"));

        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (Exception ex){
            ex.printStackTrace();
        }

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        Optional<ButtonType> result = dialog.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK){
            ContactDialogController contactDialogController = fxmlLoader.getController();
            Contact contact = contactDialogController.processResult();
            data.addNewContact(contact);
            data.saveContacts();
        }
    }

    @FXML public void updateExistingContact(){
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainBorderPane.getScene().getWindow());
        dialog.setTitle("Update Existing Contact");

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("contactDialog.fxml"));

        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (Exception ex){
            ex.printStackTrace();
        }


        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        ContactDialogController contactDialogController = fxmlLoader.getController();
        Contact oldContact = data.getContacts().get(tableView.getSelectionModel().getSelectedIndex());
        contactDialogController.setDataToDialog(oldContact);

        Optional<ButtonType> result = dialog.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK){

            Contact updatedContact = contactDialogController.processResult();
            data.updateContact(tableView.getSelectionModel().getSelectedIndex(),updatedContact);
            data.saveContacts();
            tableView.refresh();
        }
    }

    @FXML public void deleteContact(){

        Contact contact = data.getContacts().get(tableView.getSelectionModel().getSelectedIndex());

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete ToDo Item");
        alert.setHeaderText("Delete Item:" + contact.getFirstName() + " " + contact.getLastName());
        alert.setContentText("Are you sure? Press Ok to confirm, or cancel to Back out.");

        Optional<ButtonType> result = alert.showAndWait();

        if(result.isPresent() && result.get() == ButtonType.OK) {
            data.getContacts().remove(tableView.getSelectionModel().getSelectedIndex());
            data.saveContacts();
            tableView.refresh();
        }
    }

}
