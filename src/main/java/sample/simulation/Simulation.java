package sample.simulation;

import sample.simulation.utils.EventType;
import sample.simulation.utils.SortEventByTime;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/******************************************************************************
 * Class implements simulation.
 ******************************************************************************/
public class Simulation {
    double avgServiceTime = 0.125;
    double simulationTime = 250;
    Server simServer;
    List<SimulationEvent> eventList;
    ArrayList<SimulationEvent> simResultList;
    SimulationEvent current;

    //Constructor create Server and runs simulation with parameters.
    public Simulation(double lambda, boolean onOffEvents) {
        this.simServer = new Server(0, 0, 35, 40);
        generateIncomingEvent(lambda, onOffEvents);
        this.simResultList = new ArrayList<SimulationEvent>();
        startSimulation();
    }

    //Method to generate SimulationEvent objects to fill eventList. If Simulation contains on/off server event, this method create first off event.
    private void generateIncomingEvent(double lambda, boolean onOffEvents) {
        List<SimulationEvent> events = new ArrayList<SimulationEvent>();
        double time = 0;
        while (time < this.simulationTime) {
            time += getExp(lambda);
            events.add(new SimulationEvent(time, EventType.IN_QUEUE));
        }
        double avgOn = getExp(1 / this.simServer.avgOnTime);
        if (this.simulationTime > avgOn && onOffEvents) {
            events.add(new SimulationEvent(avgOn, EventType.SERVER_OFF, -1));
        }
        Collections.sort(events, new SortEventByTime());
        this.eventList = events;
    }

    //Method to run Simulation and fill simulation result list.
    private void startSimulation() {
        while (this.eventList.size() != 0) {
            handleSimEvent();
            if (current != null)
                this.simResultList.add(current);
        }
    }

    //Method to handle event, and create next ones. Ex. input event create output event with proper time.
    private void handleSimEvent() {
        this.current = eventList.get(eventList.size() - 1);
        eventList.remove(current);
        double serviceTime = getExp(1 / this.avgServiceTime);
        //Put OUT_QUEUE event in event list. Set clientNo in IN_QUEUE event.
        if (current.getType().equals(EventType.IN_QUEUE))
            if (this.simServer.currentClientNo == 0) {
                current.setClientNo(this.simServer.addClient());
                if (!this.simServer.working)
                    eventList.add(new SimulationEvent(current.time + this.simServer.offTime + serviceTime, EventType.OUT_QUEUE, current.clientNo));
                else
                    eventList.add(new SimulationEvent(current.time + serviceTime, EventType.OUT_QUEUE, current.clientNo));
            } else
                current.setClientNo(this.simServer.addClient());
            //Handle OUT_QUEUE event from event list.
        else if (current.getType().equals(EventType.OUT_QUEUE)) {
            //Put new OUT_QUEUE event time for this client.
            if (!this.simServer.working) {
                eventList.add(new SimulationEvent(current.time + this.simServer.offTime, EventType.OUT_QUEUE, current.clientNo));
                current = null;
            } else {
                this.simServer.clientOut();
                if (this.simServer.currentClientNo > 0)
                    eventList.add(new SimulationEvent(current.time + serviceTime, EventType.OUT_QUEUE, current.clientNo + 1));
            }
            //Handle SERVER_ON event, put SERVER_OFF event to event list.
        } else if (current.getType().equals(EventType.SERVER_ON)) {
            this.simServer.working = true;
            eventList.add(new SimulationEvent(current.time + getExp(1 / this.simServer.avgOnTime), EventType.SERVER_OFF, -1));
            //Handle SERVER_OFF event, put SERVER_ON event to event list.
        } else if (current.getType().equals(EventType.SERVER_OFF)) {
            double offTime = getExp(1 / this.simServer.avgOffTime);
            if (eventList.size() != 0) {
                eventList.add(new SimulationEvent(current.time + offTime, EventType.SERVER_ON, -1));
                this.simServer.working = false;
                this.simServer.offTime = offTime;
            }
        }
        //Sort event list by time.
        Collections.sort(eventList, new SortEventByTime());
    }

    //Method to generate random number from exponential distribution with parameter.
    private double getExp(double param) {
        return -(Math.log(1 - Math.random()) / param);
    }

    //Method sort event list by simulation time.
    public ArrayList<SimulationEvent> getSimResultList() {
        return simResultList;
    }
}
