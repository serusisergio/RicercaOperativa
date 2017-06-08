/**
 * Created by Sergio Serusi on 07/06/2017.
 */
public class Settings {

    private String instancesPath;

    Settings(){
        if(System.getProperty("os.name").contains("Windows")){
            setInstancesPath("Instances\\");
        }else{
            setInstancesPath("Instances/");
        }
    }

    public String getInstancesPath() {
        return instancesPath;
    }

    private void setInstancesPath(String instancesPath) {
        this.instancesPath = instancesPath;
    }
}
