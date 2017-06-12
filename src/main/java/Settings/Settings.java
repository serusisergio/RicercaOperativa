package Settings;

/**
 * Created by Sergio Serusi on 07/06/2017.
 */
public class Settings {

    public final String instancesPath = System.getProperty("os.name").contains("Windows") ? "Instances\\" : "Instances/";

    public Settings(){

    }

}
