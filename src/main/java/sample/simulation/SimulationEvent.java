package sample.simulation;

import sample.simulation.utils.EventType;

/******************************************************************************
 * Class contains simulation event parameters.
 ******************************************************************************/
public class SimulationEvent {
    double time;
    EventType type;
    int clientNo;

    //Constructor creates simulation event with client number.
    public SimulationEvent(double time, EventType type, int clientNo) {
        this.time = time;
        this.type = type;
        this.clientNo = clientNo;
    }
    //Constructor creates simulation event without client number.
    public SimulationEvent(double time, EventType type) {
        this.time = time;
        this.type = type;
        this.clientNo = 0;
    }

    /******************************************************************************
     * Getters and Setter used in app.
     ******************************************************************************/
    public double getTime() {
        return time;
    }

    public EventType getType() {
        return type;
    }

    public void setClientNo(int clientNo) {
        this.clientNo = clientNo;
    }

    public int getClientNo() {
        return clientNo;
    }

    public void setTime(double time) {
        this.time = time;
    }
}
