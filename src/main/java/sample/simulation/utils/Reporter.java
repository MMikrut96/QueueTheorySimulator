package sample.simulation.utils;

import sample.simulation.SimulationEvent;

import java.util.ArrayList;

/******************************************************************************
 * Class implements methods to calculate simulation results.
 ******************************************************************************/
public class Reporter {

    //Method creates results from given simulations samples.
    public ChartDataEntity getResults(double lambda, ArrayList<ArrayList<SimulationEvent>> simResultList, boolean working) {
        ArrayList<Double> avgDelay = new ArrayList<Double>();
        for (int i = 0; i < simResultList.size(); i++) {
            avgDelay.add(getAvgDelay(simResultList.get(i)));
        }
        return new ChartDataEntity(lambda, getConfidenceL(avgDelay, 0.05), getConfidenceH(avgDelay, 0.05), getMean(avgDelay), getAnalytical(lambda, working));
    }

    //Method returns average delay for simulation sample.
    private Double getAvgDelay(ArrayList<SimulationEvent> result) {
        final int statsDelay = 6;
        int counter = 0;
        double sum = 0;
        for (SimulationEvent event : result) {
            if (event.getClientNo() > statsDelay) {
                if (event.getType().equals(EventType.OUT_QUEUE)) {
                    sum += (event.getTime() - findInTime(result, event.getClientNo()));
                    counter++;
                }
            }
        }
        return sum / counter;
    }

    //Method returns input event time for clientId.
    private double findInTime(ArrayList<SimulationEvent> list, int clientId) {
        int k = 0;
        while (list.get(k).getClientNo() != clientId || list.get(k).getType().equals(EventType.OUT_QUEUE)) {
            k++;
        }
        return list.get(k).getTime();
    }

    //Method returns average delay result calculated analytical.
    private double getAnalytical(double lambda, boolean working) {
        double mi = 1 / 0.125;
        double result = 0;
        double probOn = 1 - Math.exp(-lambda * 40);
        double probOff = 1 - Math.exp(-lambda * 35);
        if (!working) {
            result = 1 / (mi - lambda);
        } else {
            double ro = lambda / (mi * probOn);
            result = (ro + (lambda * 35 * probOff)) / ((1 - ro) * lambda);
        }
        return result;
    }

    //Method returns first confidence level for given samples.
    private double getConfidenceL(ArrayList<Double> avgDelays, double alpha) {
        double mean = getMean(avgDelays);
        double sigma = getSigma(avgDelays, mean);
        return mean - ((Gaussian.inverseCDF(1 - (alpha / 2)) * sigma) / Math.sqrt(avgDelays.size()));
    }

    //Method returns second confidence level for given samples.
    private double getConfidenceH(ArrayList<Double> avgDelays, double alpha) {
        double mean = getMean(avgDelays);
        double sigma = getSigma(avgDelays, mean);
        return mean + ((Gaussian.inverseCDF(1 - (alpha / 2)) * sigma) / Math.sqrt(avgDelays.size()));
    }

    //Method returns sigma parameter for samples.
    private double getSigma(ArrayList<Double> avgDelays, double mean) {
        double squareSum = 0;
        for (double var : avgDelays) {
            squareSum += Math.pow((var - mean), 2);
        }
        return Math.sqrt(squareSum / avgDelays.size());
    }

    //Method returns mean value for samples.
    private double getMean(ArrayList<Double> avgDelays) {
        double sum = 0;
        for (double var : avgDelays) {
            sum += var;
        }
        return sum / avgDelays.size();
    }

}

