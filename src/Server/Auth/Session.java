package Server.Auth;

import java.time.LocalDateTime;
import java.util.UUID;

public class Session {
    private final String Username;
    private final int UserID;
    private final LocalDateTime ExpirationDate;

    public static String generateSessionID() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

    //Constructor
    public Session(String username, int UserID){
        this.Username = username;
        this.UserID = UserID;
        this.ExpirationDate = LocalDateTime.now().plusHours(3);
    }

    public String getUsername() {
        return Username;
    }

    public LocalDateTime getExpirationDate() {
        return ExpirationDate;
    }

    public int getUserID() {
        return UserID;
    }
}



