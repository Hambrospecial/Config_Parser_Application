import com.configparse.ConfigParser;

public class Main {

    public static void main(String[] args) {

        try {
            if (args.length == 0 || args[0].equalsIgnoreCase("production")) {
                ConfigParser config = new ConfigParser("src/main/resources/Config.txt");
                config.readConfig();
            }
            if (args[0].equalsIgnoreCase("development")) {
                ConfigParser config = new ConfigParser("src/main/resources/Config.txt.dev");
                config.readConfig();
            }
            if (args[0].equalsIgnoreCase("staging")) {
                ConfigParser config = new ConfigParser("src/main/resources/Config.txt.staging");
                config.readConfig();
            }
            else {
                System.out.println("File does not exist!");
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Set argument in the environment!");
        }
    }
}
