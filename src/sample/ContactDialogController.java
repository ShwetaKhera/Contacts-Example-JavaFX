package sample;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import sample.contact.Contact;
import sample.contact.ContactData;

public class ContactDialogController {

    @FXML TextField first_name;
    @FXML TextField last_name;
    @FXML TextField phone_number;
    @FXML TextArea new_notes;

    public Contact processResult(){
        String firstName = first_name.getText().trim();
        String lastName = last_name.getText().trim();
        String phoneNumber = phone_number.getText().trim();
        String notes = new_notes.getText().trim();

        return new Contact(firstName,lastName,phoneNumber,notes);
    }

    public void setDataToDialog(Contact contact){
        this.first_name.setText(contact.getFirstName());
        this.last_name.setText(contact.getLastName());
        this.phone_number.setText(contact.getPhoneNumber());
        this.new_notes.setText(contact.getNotes());
    }
}
