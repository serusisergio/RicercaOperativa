import java.io.*;

/**
 * Created by Sergio Serusi on 07/06/2017.
 */
public class FileManager {
    private FileWriter fileWriter     = null;
    private BufferedReader fileReader = null;
    private String SPLIT = "   ";
    private int numberCustomers;
    private int numberVehicles;
    private int capacityVehicles;
    private WarehouseNode warehouseNode;

    FileManager(){
        numberCustomers  = 0;
        numberVehicles   = 0;
        capacityVehicles = 0;
        warehouseNode   = null;
    }


    public void readIstance() {
        try {
            String line;
            fileReader = new BufferedReader(new FileReader("D:\\Documenti\\UNIVERSITA'\\lezioni universita\\Quinto anno\\RO\\Tesina\\Instances\\A1.txt"));
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
                        System.out.println("NumeroClienti: "+numberCustomers);
                        break;

                    case 1: // Nella riga 1 è indicato il numero dei depositi, non ci serve
                        break;

                    case 2:
                        numberVehicles  = Integer.parseInt(line);
                        System.out.println("Numeroveicoli: "+numberVehicles);
                        break;

                    case 3: //è la riga del deposito
                        String[] colWarehouse = line.split(SPLIT);
                        x = Integer.parseInt(colWarehouse[0]);
                        y = Integer.parseInt(colWarehouse[1]);
                        capacityVehicles = Integer.parseInt(colWarehouse[3]);
                        warehouseNode = new WarehouseNode(x,y,capacityVehicles);
                        System.out.println("X:"+warehouseNode.getX()+"   Y:"+warehouseNode.getY()+"   CapacitàVeicolo:"+warehouseNode.getCapacityVehicles());
                        break;

                    default:
                        String[] colCustomer = line.split(SPLIT);
                        x = Integer.parseInt(colCustomer[0]);
                        y = Integer.parseInt(colCustomer[1]);
                        delivery = Integer.parseInt(colCustomer[2]);
                        pick_up  = Integer.parseInt(colCustomer[3]);
                        System.out.println("X:"+x+"   Y:"+y+"   Delivery:"+delivery+"    Pick-up:"+pick_up);
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
    }
}