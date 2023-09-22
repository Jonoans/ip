package com.gpt.dumpgpt.action.impl;

import com.gpt.dumpgpt.command.Command;
import com.gpt.dumpgpt.shared.DukeException;
import com.gpt.dumpgpt.task.Task;
import com.gpt.dumpgpt.task.TaskManager;

public class DeleteTask extends AddTask {
    private static final String SUCCESS_PROMPT = "I've deleted the following task:";
    private static final String ACTION_VERB = "delete";

    public DeleteTask(Command command) {
        super(command, ACTION_VERB);
    }

    @Override
    protected String[] getAliases() {
        return null;
    }

    protected void execute() throws DukeException {
        TaskManager taskManager = new TaskManager();
        Task task = taskManager.getTask(getCommand());
        throwIfInvalidTask(task);
        if (taskManager.deleteTask(task)) {
            printSuccess(SUCCESS_PROMPT, taskManager, task);
            return;
        }

        // Something unexpected happened
        throw new DukeException("Failed to delete task...");
    }

    private static void throwIfInvalidTask(Task task) throws DukeException {
        if (task == null) {
            throw new DukeException("Invalid task number provided for deletion...");
        }
    }
}
