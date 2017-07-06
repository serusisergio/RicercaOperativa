package settings;

/**
 * Created by Sergio Serusi on 07/06/2017.
 */
public class Settings {

    public final String instancesPathWithoutSolution = System.getProperty("os.name").contains("Windows") ? "Instances\\InstancesWithoutSolution\\" : "Instances/InstancesWithoutSolution/";
    public final String instancesPathWithSolution = System.getProperty("os.name").contains("Windows") ? "Instances\\InstancesWithSolution\\Detailed_Solution_" : "Instances/InstancesWithSolution/Detailed_Solution_";
    public Settings(){

    }

}
