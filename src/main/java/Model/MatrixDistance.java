package Model;

import java.util.ArrayList;
import Model.*;

/**
 * Created by saimon on 08/06/17.
 */
public class MatrixDistance {
    private ArrayList<ClientNode> clientNodeArrayList;
    private WarehouseNode warehouseNode;
    public int matrixDelivery [][];
    public int matrixPU [][];
    public ArrayList<ClientNode> DeliveryList;
    public ArrayList<ClientNode> PUList;

    public MatrixDistance(ArrayList<ClientNode> clientNodeArrayList,WarehouseNode warehouseNode){
        this.clientNodeArrayList=clientNodeArrayList;
        this.warehouseNode=warehouseNode;
    }

    public ArrayList<ClientNode> getClientNodeArrayList() {
        return clientNodeArrayList;
    }

    public void getMatrix(){
        ArrayList<ClientNode> listClient=getClientNodeArrayList();
        for (int i = 0; i < listClient.size(); i++) {
            if(listClient.get(i).isDelivery()){
                DeliveryList.add(listClient.get(i));
            }else {
                PUList.add(listClient.get(i));
            }
        }

        for (int i = 0; i < DeliveryList.size(); i++) {
            for (int j = 0; j < DeliveryList.size(); j++) {
                int dist=((DeliveryList.get(i).getX()-DeliveryList.get(j).getX())^2 + (DeliveryList.get(i).getY()-DeliveryList.get(j).getY())^2)^(1/2);
                matrixDelivery[i][j]=dist;
            }
        }
        for (int i = 0; i < PUList.size(); i++) {
            for (int j = 0; j < PUList.size(); j++) {
                int dist=((PUList.get(i).getX()-PUList.get(j).getX())^2 + (PUList.get(i).getY()-PUList.get(j).getY())^2)^(1/2);
                matrixPU[i][j]=dist;
            }
        }
    }
}
