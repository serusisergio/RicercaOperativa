import java.util.ArrayList;

/**
 * Created by saimon on 08/06/17.
 */
public class MatrixDistance {
    private ArrayList<ClientNode> clientNodeArrayList;
    private WarehouseNode warehouseNode;

    public MatrixDistance(ArrayList<ClientNode> clientNodeArrayList,WarehouseNode warehouseNode){
        this.clientNodeArrayList=clientNodeArrayList;
        this.warehouseNode=warehouseNode;
    }

    public ArrayList<ClientNode> getClientNodeArrayList() {
        return clientNodeArrayList;
    }
    public void getMatrix(){
        ArrayList<ClientNode> listClient=getClientNodeArrayList();
        for (ClientNode client:listClient) {

        }
    }
}
