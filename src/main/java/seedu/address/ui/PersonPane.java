package seedu.address.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import seedu.address.model.person.Person;

//Solution below inspired by ChatGPT
/**
 * A UI component that displays detailed information of a selected {@code Person}.
 */
public class PersonPane extends UiPart<VBox> {

    private static final String FXML = "PersonPane.fxml";

    public final Person person;

    @FXML
    private VBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private FlowPane tags;
    @FXML
    private Label remark;
    @FXML
    private Label listings;
    @FXML
    private Label tagsLabel;

    /**
     * Creates a {@code PersonPane} with the given {@code Person} to display.
     */
    public PersonPane(Person person) {
        super(FXML);
        this.person = person;
        displayPersonDetails();
    }

    /**
     * Default constructor for an empty pane.
     */
    public PersonPane() {
        super(FXML);
        this.person = null;
        emptyPane(); // Call to set the UI to an empty state
    }

    /**
     * Displays the details of the selected {@code Person}.
     */
    private void displayPersonDetails() {
        if (person != null) {
            setNameAndContactDetails();
            renderTags();
            setRemark();
            displayListings();
        }
    }

    /**
     * Sets the name and contact details (phone, address, email) of the {@code Person}.
     */
    private void setNameAndContactDetails() {
        name.setText("Name: " + person.getName().fullName);
        phone.setText("Phone: " + person.getPhone().value);
        address.setText("Address: " + person.getAddress().value);
        email.setText("Email: " + person.getEmail().value);
    }

    /**
     * Renders the tags associated with the {@code Person}.
     */
    // Solution below adapted from ChatGPT
    private void renderTags() {
        tags.getChildren().clear();
        if (!person.getTags().isEmpty()) {
            tagsLabel.setManaged(true);
            tagsLabel.setVisible(true);

            person.getTags().stream()
                    .sorted(Comparator.comparing(tag -> tag.tagName))
                    .forEach(tag -> {
                        Label tagLabel = new Label(tag.tagName);
                        if (tag.getTagType().equals("property")) {
                            tagLabel.getStyleClass().add("tag-property");
                        }

                        tags.getChildren().add(tagLabel);
                    });
        } else {
            tagsLabel.setManaged(false);
            tagsLabel.setVisible(false);
        }
    }

    /**
     * Sets the remark text for the {@code PersonPane}.
     * If no remark is present, the label is hidden.
     */
    private void setRemark() {
        String remarkValue = person.getRemark().value;
        if (remarkValue != null && !remarkValue.trim().isEmpty()) {
            remark.setText("Remark: " + remarkValue);
            remark.setManaged(true);
        } else {
            remark.setManaged(false);
        }
    }

    /**
     * Displays the listings associated with the {@code Person}.
     * If no listings are available, the label is hidden.
     */
    //@@author tayxuenye-reused
    // Written by ChatGPT
    // Clear and display listings
    private void displayListings() {
        if (!person.getListings().isEmpty()) {
            StringBuilder listingsText = new StringBuilder("Listings:\n");
            person.getListings().forEach(listing -> listingsText.append(listing.toString()).append("\n"));
            listings.setText(listingsText.toString());
            listings.setManaged(true);
            listings.setVisible(true);
        } else {
            listings.setText(""); // Clear the listings text if there are no listings
            listings.setManaged(false);
            listings.setVisible(false);
        }
    }
    //@@author

    /**
     * Empties pane when no person is selected.
     */
    public void emptyPane() {
        name.setText("");
        phone.setText("");
        address.setText("");
        email.setText("");
        tags.getChildren().clear();
        remark.setText("");
        remark.setManaged(false);
        listings.setText("");
        listings.setManaged(false);
        listings.setVisible(false);
    }
}
