import java.io.*;
import java.util.ArrayList;

/**
 * Created by Sergio Serusi on 07/06/2017.
 */
public class FileManager {
    private FileWriter fileWriter;
    private BufferedReader fileReader;
    private String SPLIT;


    FileManager(){
        fileReader = null;
        fileWriter = null;
        SPLIT      = "   ";//il file è formattato con 3 tab
    }


    public Instance readInstance(String nameInstance) {
        ArrayList<ClientNode> clientNodeArrayList = new ArrayList<ClientNode>();
        int numberCustomers  = 0;
        int numberVehicles   = 0;
        int capacityVehicles = 0;
        WarehouseNode warehouseNode = null;
        Instance instance = null;

        try {
            String line;
            fileReader = new BufferedReader(new FileReader(Settings.instancesPath+nameInstance+".txt"));
            int i=0;
            while ((line = fileReader.readLine()) != null) {
                //System.out.println(line);
                int x = 0;
                int y = 0;
                int delivery = 0;
                int pick_up  = 0;

                switch (i){
                    case 0:
                        numberCustomers = Integer.parseInt(line);
                        //System.out.println("NumeroClienti: "+numberCustomers);
                        break;

                    case 1: // Nella riga 1 è indicato il numero dei depositi, non ci serve
                        break;

                    case 2:
                        numberVehicles  = Integer.parseInt(line);
                        //System.out.println("Numeroveicoli: "+numberVehicles);
                        break;

                    case 3: //è la riga del deposito
                        String[] colWarehouse = line.split(SPLIT);
                        x = Integer.parseInt(colWarehouse[0]);
                        y = Integer.parseInt(colWarehouse[1]);
                        capacityVehicles = Integer.parseInt(colWarehouse[3]);
                        warehouseNode = new WarehouseNode(x,y);
                        //System.out.println("X:"+warehouseNode.getX()+"   Y:"+warehouseNode.getY()+"   CapacitàVeicolo:");
                        break;

                    default:

                        String[] colCustomer = line.split(SPLIT);
                        x = Integer.parseInt(colCustomer[0]);
                        y = Integer.parseInt(colCustomer[1]);
                        delivery = Integer.parseInt(colCustomer[2]);
                        pick_up  = Integer.parseInt(colCustomer[3]);
                        ClientNode clientNode = new ClientNode(x,y,delivery,pick_up);
                        clientNodeArrayList.add(clientNode);
                        /*
                        if(clientNode.isDelivery()) {
                            System.out.println("X:" + clientNode.getX() + "   Y:" + clientNode.getY() + "   Delivery:" + clientNode.getDelivery());
                        }else{
                            System.out.println("X:" + clientNode.getX() + "   Y:" + clientNode.getY() + "   Pick-up:" + clientNode.getPick_up());
                        }
                        */
                }
                i++;
            }
        }catch (FileNotFoundException e) {
            System.out.println("Error in FileReader!");
            //e.printStackTrace();
        } catch (IOException e) {
            //e.printStackTrace();
        } finally {
            try {
                fileReader.close();
            } catch (IOException e) {
                System.out.println("Error while flushing/closing fileReader!");
                e.printStackTrace();
            }
        }
        instance = new Instance(capacityVehicles,numberCustomers,numberVehicles,warehouseNode,nameInstance,clientNodeArrayList);
        return instance;
    }
}