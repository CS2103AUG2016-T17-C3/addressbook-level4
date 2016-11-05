// @@author A0147335E
package seedu.task.logic.commands;

import java.util.ArrayList;

import seedu.task.commons.core.Messages;
import seedu.task.commons.core.UnmodifiableObservableList;
import seedu.task.logic.RollBackCommand;
import seedu.task.model.task.ReadOnlyTask;
import seedu.task.model.task.Task;
import seedu.task.model.task.UniqueTaskList;
import seedu.task.model.task.UniqueTaskList.TaskNotFoundException;


/**
 * Favorite a task from the task manager.
 */
public class FavoriteCommand extends Command {

    public static final String COMMAND_WORD = "fav";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Favorite the task identified by the index number used in the last task listing.\n"
            + "Parameters: INDEX TASKNAME\n"
            + "Example: " + COMMAND_WORD
            + " 4";

    public static final String MESSAGE_FAVORITE_TASK_SUCCESS = "Favorite Task: %1$s";

    public static final String MESSAGE_ALREADY_FAVORITED = "Task has already been favorited!";

    public final int targetIndex;

    public FavoriteCommand(int targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(boolean isUndo) {
        assert model != null;
        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyTask currentTask = lastShownList.get(targetIndex - 1);
        boolean oldStatus = currentTask.getStatus().getFavoriteStatus();

        try {
            model.deleteTask(currentTask);
        } catch (TaskNotFoundException tnfe) {
            assert false : "The target task cannot be missing";
        }

        Task taskToFavorite = new Task(currentTask);
        taskToFavorite.getStatus().setFavoriteStatus(true);

        try {
            model.addTask(targetIndex - 1, taskToFavorite);
        } catch (UniqueTaskList.DuplicateTaskException e) {}

        if (oldStatus == taskToFavorite.getStatus().getFavoriteStatus()) {
            return new CommandResult(MESSAGE_ALREADY_FAVORITED);
        }

        if (!isUndo) {
            getUndoList().add(new RollBackCommand(COMMAND_WORD, taskToFavorite, null));
        }
        // @@author A0147944U
        // Sorts updated list of tasks
        model.autoSortBasedOnCurrentSortPreference();
        // @@author A0147335E
        return new CommandResult(String.format(MESSAGE_FAVORITE_TASK_SUCCESS, taskToFavorite.getName()));
    }

	private ArrayList<RollBackCommand> getUndoList() {
		return history.getUndoList();
	}

    @Override
    public CommandResult execute(int index) {
        return null;
    }
}
