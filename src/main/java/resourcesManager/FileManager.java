package resourcesManager;


import java.io.*;
import java.util.ArrayList;

import settings.*;
import core.model.*;


/**
 * Created by Sergio Serusi on 07/06/2017.
 */
public class FileManager {
    /*
    * Questa classe si occupa di gestire i file, in lettura e scrittura
     */
    private FileWriter fileWriter;
    private BufferedReader fileReader;
    private String SPLIT;
    private File file;


    public FileManager() {
        fileReader = null;
        fileWriter = null;
        file = null;
        SPLIT = "   ";//il file è formattato con 3 tab
    }


    /**
    * Questo metodo legge la singola instanza nameInstance
    *@param nameInstance Contiene il node dell'istanza da leggere
     *@return Instance contenente creata leggendo il file nameInstance
     */
    public Instance readInstance(String nameInstance) {
        ArrayList<Node> nodesList = new ArrayList<>();
        int numberCustomers = 0;
        int numberVehicles = 0;
        int capacityVehicles = 0;
        WarehouseNode warehouseNode = null;

        try {
            String line;
            fileReader = new BufferedReader(new FileReader(Settings.instancesPathWithoutSolution + nameInstance));
            int i = 0;
            while ((line = fileReader.readLine()) != null) {
                //System.out.println(line);
                int x = 0;
                int y = 0;
                int delivery = 0;
                int pickup = 0;

                switch (i) {
                    case 0://la riga 0 contiene il numero dei clieti
                        numberCustomers = Integer.parseInt(line);
                        //System.out.println("NumeroClienti: "+numberCustomers);
                        break;

                    case 1: // Nella riga 1 è indicato il numero dei depositi, non ci serve
                        break;

                    case 2:// la riga 2 contiene il numero di veicoli
                        numberVehicles = Integer.parseInt(line);
                        //System.out.println("Numeroveicoli: "+numberVehicles);
                        break;

                    case 3: //è la riga del deposito
                        String[] colWarehouse = line.split(SPLIT);
                        x = Integer.parseInt(colWarehouse[0]);
                        y = Integer.parseInt(colWarehouse[1]);
                        capacityVehicles = Integer.parseInt(colWarehouse[3]);
                        warehouseNode = new WarehouseNode(x, y, i-3);
                        //System.out.println("X:"+warehouseNode.getX()+"   Y:"+warehouseNode.getY()+"   CapacitàVeicolo:");
                        break;

                    default://tutte le altre righe
                        String[] colCustomer = line.split(SPLIT);
                        x = Integer.parseInt(colCustomer[0]);
                        y = Integer.parseInt(colCustomer[1]);
                        delivery = Integer.parseInt(colCustomer[2]);
                        pickup = Integer.parseInt(colCustomer[3]);

                        if (delivery > 0) {
                            nodesList.add(new DeliveryNode(x, y, i-3, delivery));
                        } else {
                            nodesList.add(new PickupNode(x, y, i-3, pickup));
                        }
                }
                i++;
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error in FileReader!");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fileReader.close();
            } catch (IOException e) {
                System.out.println("Error while flushing/closing fileReader!");
                e.printStackTrace();
            }
        }
        double bestSolution = getBestInstanceSolution(nameInstance);
        return new Instance(capacityVehicles, numberCustomers, numberVehicles, warehouseNode, nameInstance, nodesList, bestSolution);
    }

    //Questo metodo legge tutte le instanza e le carica su un ArrayList, restituendolo al chiamante
    public ArrayList<Instance> readInstances() {
        ArrayList<Instance> instances = new ArrayList<Instance>();

        for (String nameInstance : getListNameInstance()) {
            instances.add(readInstance(nameInstance));
        }
        return instances;
    }

    /**
     * @return A list contains the names of the instances
     */
    public String[] getListNameInstance() {
        file = new File(Settings.instancesPathWithoutSolution);
        return file.list();
    }

    /**
     * Questo metodo riceve in ingresso il nome della istanza e restituisce la best solution o best known
     * @param nameInstance
     * @return
     */
    public double getBestInstanceSolution(String nameInstance){
        double bestSolution = Integer.MAX_VALUE;
        try {
            String line;
            fileReader = new BufferedReader(new FileReader(Settings.instancesPathWithSolution + nameInstance));
            for(int i=0;i<8;i++)fileReader.readLine();
            line = fileReader.readLine();
            bestSolution = Double.parseDouble(line.split("Total Cost =")[1]);

        } catch (FileNotFoundException e) {
            System.out.println("Error in FileReader!");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fileReader.close();
            } catch (IOException e) {
                System.out.println("Error while flushing/closing fileReader!");
                e.printStackTrace();
            }
        }
        return bestSolution;
    }

}