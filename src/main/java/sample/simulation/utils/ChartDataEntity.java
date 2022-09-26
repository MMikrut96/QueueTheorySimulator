package sample.simulation.utils;

/******************************************************************************
 * Class contains calculated results.
 ******************************************************************************/
public class ChartDataEntity {
    double lambda;
    double confidenceL;
    double confidenceH;
    double avg;
    double analyticalResult;

    public ChartDataEntity(double lambda, double confidenceL, double confidenceH, double avg, double analyticalResult) {
        this.lambda = lambda;
        this.confidenceL = confidenceL;
        this.confidenceH = confidenceH;
        this.avg = avg;
        this.analyticalResult = analyticalResult;
    }

    /**********************
    *Getters for all values.
    ***********************/
    public double getLambda() {
        return lambda;
    }

    public double getConfidenceL() {
        return confidenceL;
    }

    public double getConfidenceH() {
        return confidenceH;
    }

    public double getAvg() {
        return avg;
    }

    public double getAnalyticalResult() {
        return analyticalResult;
    }
}
