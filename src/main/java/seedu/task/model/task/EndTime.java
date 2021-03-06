package seedu.task.model.task;

import seedu.task.commons.exceptions.IllegalValueException;

/**
 * Represents a Task end time in the task manager.
 * Guarantees: immutable; is valid as declared in {@link #isValidEndTime(String)}
 */
public class EndTime {

    public static final String MESSAGE_ENDTIME_CONSTRAINTS = "Task end times should be in hh.mmam or hh.mmpm format";
    private static final String ENDTIME_VALIDATION_REGEX = "((1[012]|[1-9]).[0-5][0-9](\\s)?(?i)(am|pm)|(1[012]|[1-9])(\\s)?(?i)(am|pm))|(^.+)";
    private static final String NO_ENDTIME = "";

    public final String value;

    /**
     * Validates given end time.
     *
     * @throws IllegalValueException
     *             if given end time string is invalid.
     */
    public EndTime(String endTime) throws IllegalValueException {
        assert endTime != null;
        String newEndTime = endTime.trim();
        if (newEndTime.equals(NO_ENDTIME)) {
            this.value = newEndTime;
            return;
        }
        if (!isValidEndTime(newEndTime)) {
            throw new IllegalValueException(MESSAGE_ENDTIME_CONSTRAINTS);
        }
        this.value = newEndTime;
    }

    /**
     * Returns if a given string is a valid task end time.
     */
    public static boolean isValidEndTime(String test) {
        return test.matches(ENDTIME_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    /**
     * Checks if the two are the same and returns true if the EndTimes are equal
     * to each other and false otherwise
     * 
     * @param other
     *            The EndTime to check against.
     * @return true if the EndTimes are equal to each other and false otherwise
     */
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EndTime // instanceof handles nulls
                        && this.value.equals(((EndTime) other).value)); // state
                                                                        // check
    }

    /**
     * Generates a hash code for the EndTime parameter.
     * 
     * @return generated hash code
     */
    @Override
    public int hashCode() {
        return value.hashCode();
    }

    // @@author A0147944U
    /**
     * Compares the two EndTimes lexicographically.
     * 
     * @param anotherEndTime
     *            EndTime of another Task to compare to
     */
    public int compareTo(EndTime anotherEndTime) {
        return this.toString().compareTo(anotherEndTime.toString());
    }
    // @@author

}
