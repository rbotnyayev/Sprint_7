import lombok.Data;

@Data
public class Courier {
    private String login;
    private String password;

    public Courier(String login, String password) {
        this.login = login;
        this.password = password;
    }
}
