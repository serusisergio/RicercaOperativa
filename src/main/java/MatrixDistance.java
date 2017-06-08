import java.util.ArrayList;

/**
 * Created by saimon on 08/06/17.
 */
public class MatrixDistance {
    private ArrayList<ClientNode> clientNodeArrayList;
    private WarehouseNode warehouseNode;
    public int matrix [][];

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
            for (int j = 0; j < listClient.size(); j++) {

            }
        }
    }
}
