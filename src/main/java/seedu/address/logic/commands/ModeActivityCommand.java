package seedu.address.logic.commands;

import seedu.address.commons.core.AppMode;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;

/**
 * Changes AppMode to ACTIVITY
 */
public class ModeActivityCommand extends Command {

    public static final String COMMAND_WORD = "modeActivity";

    public static final String MESSAGE_EXIT_ACKNOWLEDGEMENT = "Changing to Activity Management Mode";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        AppMode.setModeActivity();
        return new CommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT, false, false, true);
    }

}
