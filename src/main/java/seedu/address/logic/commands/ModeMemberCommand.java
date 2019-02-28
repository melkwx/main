package seedu.address.logic.commands;

import seedu.address.commons.core.AppMode;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;

/**
 * Changes AppMode to MEMBER
 */
public class ModeMemberCommand extends Command {

    public static final String COMMAND_WORD = "modeMember";

    public static final String MESSAGE_EXIT_ACKNOWLEDGEMENT = "Changing to Member Management Mode";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        AppMode.setModeMember();
        return new CommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT, false, false, true);
    }

}
