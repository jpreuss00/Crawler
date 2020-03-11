package crawler;

public class User {

    private final String keyphrase;
    private final String username;
    private final String password;

    public User(String keyphrase, String username, String password) {
        this.keyphrase = keyphrase;
        this.username = username;
        this.password = password;
    }

    public String getKeyphrase() {
        return keyphrase;
    }

    public String getUsername(){
        return username;
    }

    public String getPassword(){
        return password;
    }

}
