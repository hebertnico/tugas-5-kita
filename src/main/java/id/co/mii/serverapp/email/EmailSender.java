package id.co.mii.serverapp.email;

public interface EmailSender {
    void send(String to, String email);
}
