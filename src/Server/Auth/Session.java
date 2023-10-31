package Server.Auth;

import java.util.Date;
import java.util.UUID;

public class Session {
    private final String Username;
    private final Date ExpirationDate;

    public static String generateSessionID() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

    public Session(String username){
        this.Username = username;
        this.ExpirationDate = new Date();
    }

    public String getUsername() {
        return Username;
    }

    public Date getExpirationDate() {
        return ExpirationDate;
    }
}



