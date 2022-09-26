package sample.simulation.utils;

import sample.simulation.SimulationEvent;

import java.util.Comparator;

/******************************************************************************
 * Class implements method to compare two SimulationEvent objects by time.
 ******************************************************************************/
public class SortEventByTime implements Comparator<SimulationEvent> {
    public int compare(SimulationEvent o1, SimulationEvent o2) {
        if (o1.getTime() < o2.getTime())
            return 1;
        else if (o1.getTime() == o2.getTime())
            if ((o1.getType().equals(EventType.IN_QUEUE) || o1.getType().equals(EventType.OUT_QUEUE)) && (o2.getType().equals(EventType.SERVER_ON) || o2.getType().equals(EventType.SERVER_OFF)))
                return 1;
            else return -1;
        else return -1;
    }
}
