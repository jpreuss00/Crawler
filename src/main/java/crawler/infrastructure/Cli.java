package src.main.java.crawler.infrastructure;

public class Cli {

    public Cli(String[] args) {
    }

    public String[] handleInput(String args[]) {
        if (args.length == 0) {
            return new String[] {"Please enter valid arguments! Formula: docker run [Host] [User] [Password]"};
        } else if (args.length < 3) {
            return new String[] { "Please enter 3 arguments! Formula: docker run [Host] [User] [Password]"};
        } else {
            String host = args[0];
            String user = args[1];
            String password = args[2];
            String database = "weltstore";
            return new String[] { host, user, password, database };
        }
    }
}