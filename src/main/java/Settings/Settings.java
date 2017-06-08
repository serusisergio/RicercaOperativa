package Settings;

/**
 * Created by Sergio Serusi on 07/06/2017.
 */
public class Settings {

    private String instancesPath;

    public Settings(){
        if(System.getProperty("os.name").contains("Windows")){
            setInstancesPath("Instances\\");
        }else{
            setInstancesPath("Instances/");
        }
    }

    public String getInstancesPath() {
        return instancesPath;
    }

    protected void setInstancesPath(String instancesPath) {
        this.instancesPath = instancesPath;
    }
}
