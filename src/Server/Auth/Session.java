package Server.Auth;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

public class Session {
    private final String Username;
    private final LocalDateTime ExpirationDate;

    public static String generateSessionID() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

    public Session(String username){
        this.Username = username;
        this.ExpirationDate = LocalDateTime.now().plusHours(3);
    }

    public String getUsername() {
        return Username;
    }

    public LocalDateTime getExpirationDate() {
        return ExpirationDate;
    }
}



