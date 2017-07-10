package settings;

/**
 * Created by Sergio Serusi on 07/06/2017.
 */
public class Settings {

    public static final String instancesPathWithoutSolution = System.getProperty("os.name").contains("Windows") ? "Instances\\InstancesWithoutSolution\\" : "Instances/InstancesWithoutSolution/";
    public static final String instancesPathWithSolution = System.getProperty("os.name").contains("Windows") ? "Instances\\InstancesWithSolution\\Detailed_Solution_" : "Instances/InstancesWithSolution/Detailed_Solution_";

    public static final double SHUFFLE_OFFSET = 0.01;
    public static final double EPSILON = 0.01;


}
