package seedu.address.logic;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_MODE;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

import javafx.beans.property.ReadOnlyProperty;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.ModeCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.AddressBookParser;
import seedu.address.logic.parser.exceptions.InvalidCommandModeException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.activity.Activity;
import seedu.address.model.person.Person;
import seedu.address.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final CommandHistory history;
    private final AddressBookParser addressBookParser;
    private boolean addressBookModified;

    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        history = new CommandHistory();
        addressBookParser = new AddressBookParser();

        // Set addressBookModified to true whenever the models' address book is modified.
        model.getAddressBook().addListener(observable -> addressBookModified = true);
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException,
            ParseException, InvalidCommandModeException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        addressBookModified = false;

        CommandResult commandResult;
        try {
            Command command = addressBookParser.parseCommand(commandText);
            if (!(command.isValidMode(model.getAddressBookMode()))) {
                throw new InvalidCommandModeException(
                        String.format(MESSAGE_INVALID_COMMAND_MODE, command.allowedModeListAsString(),
                                ModeCommand.MESSAGE_USAGE_CHANGE_MODE)
                );
            }
            commandResult = command.execute(model, history);
        } finally {
            history.add(commandText);
        }

        if (addressBookModified) {
            logger.info("Address book modified, saving to file.");
            try {
                storage.saveAddressBook(model.getAddressBook());
            } catch (IOException ioe) {
                throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
            }
        }

        return commandResult;
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return model.getAddressBook();
    }

    @Override
    public ObservableList<Activity> getFilteredActivityList() {
        return model.getFilteredActivityList();
    }

    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return model.getFilteredPersonList();
    }


    @Override
    public ObservableList<String> getHistory() {
        return history.getHistory();
    }

    @Override
    public Path getAddressBookFilePath() {
        return model.getAddressBookFilePath();
    }

    @Override
    public GuiSettings getGuiSettings() {
        return model.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        model.setGuiSettings(guiSettings);
    }


    @Override
    public ReadOnlyProperty<Person> selectedPersonProperty() {
        return model.selectedPersonProperty();
    }

    @Override
    public void setSelectedPerson(Person person) {
        model.setSelectedPerson(person);
    }

    @Override
    public ReadOnlyProperty<Activity> selectedActivityProperty() {
        return model.selectedActivityProperty();
    }

    @Override
    public void setSelectedActivity(Activity activity) {
        model.setSelectedActivity(activity);
    }

    @Override
    public void callAllListFn() {
        model.resetLists();
    }

    @Override
    public boolean modeHasChange_isCurrModeMember() {
        return model.addressBookModeIsMember();
    }

    @Override
    public boolean modeHasChange_isCurrModeActivity() {
        return model.addressBookModeIsActivity();
    }

    @Override
    public ObservableList<Person> getAttendingOfSelectedActivity() {
        return model.getAttendingOfSelectedActivity();
    }

    @Override
    public ObservableList<Person> getPersonNotInSelectedActivity() {
        return model.getPersonNotInSelectedActivity();
    }


    @Override
    public ObservableList<Activity> getActivitiesOfPerson() {
        return model.getActivitiesOfPerson();
    }

    @Override
    public int getAttendedActivitiesCounter(Person person) {
        return model.getAttendedActivitiesCounter(person);
    }

    @Override
    public int getParticipationRate(Person person) {
        return model.getParticipationRate(person);
    }
}
