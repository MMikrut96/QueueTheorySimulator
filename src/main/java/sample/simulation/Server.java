package sample.simulation;

/******************************************************************************
 * Class implements server in simulation.
 ******************************************************************************/
public class Server {
    //Number of all clients appeared in server queue.
    int clientNo;
    //Number of current clients in server queue.
    int currentClientNo;
    //Average server off time.
    double avgOffTime;
    //Average server on time.
    double avgOnTime;
    //Server state(on/off).
    boolean working;
    //Total time in off state.
    double offTime;

    //Constructor saving parameters in fields.
    public Server(int clientNo, int currentClientNo, double avgOffTime, double avgOnTime) {
        this.clientNo = clientNo;
        this.currentClientNo = currentClientNo;
        this.avgOffTime = avgOffTime;
        this.avgOnTime = avgOnTime;
        this.working = true;
        this.offTime = 0;
    }

    //Increasing number of all clients and current number of clients in server queue.
    public int addClient(){
        this.clientNo++;
        this.currentClientNo++;
        return this.clientNo;
    }

    //Decreasing current number of clients in server queue.
    public void clientOut(){
        this.currentClientNo--;
    }
}
