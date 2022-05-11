package calendar.helper;

public class Message {

    public static String SUCCESS = "Success";

    public static String AUTH_SUCCESS = "Authorization completed successfully";

    public static String AUTH_ERROR = "Invalid access token";

    public static String USER_INVALID_PASSWORD = "Invalid password";

    public static String EVENT_CREATED = "Successfully created event";

    public static String EVENT_INVALID_TIME = "Invalid time parameters for event";

    public static String EVENT_OVERLAP = "Events must not overlap each over";

    public static String EVENT_MATRIX_INVALID_TIME = "Request start time is not a Monday";

    public static String eventDeleted(Long id) {
        return String.format("Successfully deleted event with ID %d", id);
    }

    public static String eventNotFound(Long id) {
        return String.format("Event with ID %d was not found", id);
    }

    public static String userNotFound(Long id) {
        return String.format("User with ID %d was not found", id);
    }

    public static String userNotFound(String login) {
        return String.format("User with login \"%d\" was not found", login);
    }
}
