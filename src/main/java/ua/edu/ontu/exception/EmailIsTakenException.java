package ua.edu.ontu.exception;

public class EmailIsTakenException extends Throwable {

    private String email;

    public EmailIsTakenException(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

}
