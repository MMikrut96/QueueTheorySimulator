package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import sample.simulation.Simulation;
import sample.simulation.SimulationEvent;
import sample.simulation.utils.ChartDataEntity;
import sample.simulation.utils.Reporter;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/******************************************************************************
 * Main controller class. Runs simulations, and create result plots.
 ******************************************************************************/
public class Controller implements Initializable {
    @FXML
    BorderPane firstPlot;
    @FXML
    BorderPane secondPlot;
    @FXML
    Button startButton;

    private LineChart first;
    private LineChart second;
    private List<ChartDataEntity> firstSimulationResults;
    private List<ChartDataEntity> secondSimulationResults;

    //Action for button click.
    public void startClicked() {
        startButton.setVisible(false);
        showCharts();
        firstPlot.setCenter(first);
        secondPlot.setCenter(second);
    }

    //Create plots for both simulations.
    private void showCharts() {
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        final NumberAxis xAxis2 = new NumberAxis();
        final NumberAxis yAxis2 = new NumberAxis();
        xAxis.setLabel("Lambda [1/s]");
        yAxis.setLabel("Average delay [s]");
        xAxis2.setLabel("Lambda [1/s]");
        yAxis2.setLabel("Average delay [s]");
        final LineChart<Number, Number> lineChart = new LineChart<Number, Number>(xAxis, yAxis);
        XYChart.Series confidenceL1 = new XYChart.Series();
        XYChart.Series confidenceH1 = new XYChart.Series();
        XYChart.Series average1 = new XYChart.Series();
        XYChart.Series analytical1 = new XYChart.Series();
        for (ChartDataEntity data : this.firstSimulationResults) {
            confidenceL1.getData().add(new XYChart.Data(data.getLambda(), data.getConfidenceL()));
            confidenceH1.getData().add(new XYChart.Data(data.getLambda(), data.getConfidenceH()));
            average1.getData().add(new XYChart.Data(data.getLambda(), data.getAvg()));
            analytical1.getData().add(new XYChart.Data(data.getLambda(), data.getAnalyticalResult()));
        }
        confidenceL1.setName("Confidence Level");
        confidenceH1.setName("Confidence Level");
        average1.setName("Average");
        analytical1.setName("Analytical");
        lineChart.setPrefSize(800, 800);
        lineChart.getData().add(confidenceL1);
        lineChart.getData().add(confidenceH1);
        lineChart.getData().add(average1);
        lineChart.getData().add(analytical1);
        lineChart.setTitle("Simulation 1: No Breaks");
        first = lineChart;


        final LineChart<Number, Number> lineChart2 = new LineChart<Number, Number>(xAxis2, yAxis2);
        XYChart.Series confidenceL2 = new XYChart.Series();
        XYChart.Series confidenceH2 = new XYChart.Series();
        XYChart.Series average2 = new XYChart.Series();
        XYChart.Series analytical2 = new XYChart.Series();
        for (ChartDataEntity data : this.secondSimulationResults) {
            confidenceL2.getData().add(new XYChart.Data(data.getLambda(), data.getConfidenceL()));
            confidenceH2.getData().add(new XYChart.Data(data.getLambda(), data.getConfidenceH()));
            average2.getData().add(new XYChart.Data(data.getLambda(), data.getAvg()));
            analytical2.getData().add(new XYChart.Data(data.getLambda(), data.getAnalyticalResult()));
        }
        confidenceL2.setName("Confidence Level");
        confidenceH2.setName("Confidence Level");
        average2.setName("Average");
        analytical2.setName("Analytical");
        lineChart2.setPrefSize(800, 800);
        lineChart2.getData().add(confidenceL2);
        lineChart2.getData().add(confidenceH2);
        lineChart2.getData().add(average2);
        lineChart2.getData().add(analytical2);
        lineChart2.setTitle("Simulation 2: With Breaks");
        second = lineChart2;
    }

    //Initialize fields objects. Run simulation 1 and simulation 2, print all results.
    public void initialize(URL location, ResourceBundle resources) {
        this.firstSimulationResults = new ArrayList<ChartDataEntity>();
        this.secondSimulationResults = new ArrayList<ChartDataEntity>();
        ArrayList<ArrayList<SimulationEvent>> firstSimClearResult = new ArrayList<ArrayList<SimulationEvent>>();
        ArrayList<ArrayList<SimulationEvent>> secondSimClearResult = new ArrayList<ArrayList<SimulationEvent>>();
        System.out.println("*********************************************************************************\n" +
                "First Simulation: Server Without Breaks");
        //Run simulation 1.
        double lambda = 0.5;
        while (lambda <= 6) {
            for (int i = 0; i < 50; i++) {
                firstSimClearResult.add(new Simulation(lambda, false).getSimResultList());
            }
            this.firstSimulationResults.add(new Reporter().getResults(lambda, firstSimClearResult, false));
            firstSimClearResult.clear();
            lambda += 0.25;
        }
        for (ChartDataEntity entity : firstSimulationResults) {
            System.out.println("Lambda " + entity.getLambda() + ", " +
                    "Confidence Level " + entity.getConfidenceL() + ", " +
                    "Mean " + entity.getAvg() + ", " +
                    "Confidence Level " + entity.getConfidenceH() + ", " +
                    "Analytical " + entity.getAnalyticalResult());
        }
        System.out.println("*********************************************************************************\n" +
                "Second Simulation: Server Breaks");
        //Run simulation 2.
        lambda = 0.5;
        while (lambda <= 4) {
            for (int i = 0; i < 50; i++) {
                secondSimClearResult.add(new Simulation(lambda, true).getSimResultList());
            }
            this.secondSimulationResults.add(new Reporter().getResults(lambda, secondSimClearResult, true));
            secondSimClearResult.clear();
            lambda += 0.25;
        }
        for (ChartDataEntity entity : secondSimulationResults) {
            System.out.println("Lambda " + entity.getLambda() + ", " +
                    "Confidence Level " + entity.getConfidenceL() + ", " +
                    "Mean " + entity.getAvg() + ", " +
                    "Confidence Level " + entity.getConfidenceH() + ", " +
                    "Analytical " + entity.getAnalyticalResult());
        }
    }
}
