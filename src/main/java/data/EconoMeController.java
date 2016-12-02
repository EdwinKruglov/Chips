package main.java.data;

import javafx.collections.FXCollections;
import javafx.collections.ObservableArrayBase;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.VBox;

import java.awt.*;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.*;

public class EconoMeController implements Initializable
{
    private ObservableList<Country> countryList;
    private ObservableList<CountryIndicator> countryIndicatorList;
    private ObservableSet<String> countryNames = FXCollections.observableSet();
    private ObservableSet<String> regionNames = FXCollections.observableSet();
    private ObservableSet<String> incomeLevels = FXCollections.observableSet();
    private ObservableSet<String> lendingTypes = FXCollections.observableSet();
    private ObservableSet<String> capitalCities = FXCollections.observableSet();

    @FXML
    private VBox result;

    @FXML
    private Button btnCountryName;
    @FXML
    private ListView<String> listView;
    @FXML
    private LineChart<Number, Number> GDP_CURRENT_$US;

    @FXML
    private void handleCountryNameAction(ActionEvent event)
    {
        listView.setItems(FXCollections.observableArrayList(countryNames));
    }

    @FXML
    private void handleRegionNameAction(ActionEvent event)
    {

        listView.setItems(FXCollections.observableArrayList(regionNames));
    }

    @FXML
    private void handleIncomeLevelAction(ActionEvent event)
    {
        listView.setItems(FXCollections.observableArrayList(incomeLevels));
    }

    @FXML
    private void handleLendingTypeAction(ActionEvent event)
    {
        listView.setItems(FXCollections.observableArrayList(lendingTypes));
    }

    @FXML
    private void handleCapitalCityAction(ActionEvent event)
    {
        listView.setItems(FXCollections.observableArrayList(capitalCities));
    }

    @FXML
    private void handleIndicatorAction(ActionEvent event)
    {

    }

    @FXML
    private void handleSettingsAction(ActionEvent event)
    {

    }
    @FXML
    private void handleListAction(MouseEvent arg0){
        result.getChildren().clear();
        String selectedItem = listView.getSelectionModel().getSelectedItem();
        ArrayList<CountryIndicator> results = new ArrayList<>();
        for(CountryIndicator c: countryIndicatorList){
            if(c.getCountryValue().equals(selectedItem)){
                results.add(c);
            }
        }

        final NumberAxis xAxis = new NumberAxis(1970, 2016, 10);
        final NumberAxis yAxis = new NumberAxis();

        yAxis.setMinorTickCount(500);
        LineChart lineChart = new LineChart<Number,Number>(xAxis,yAxis);
        lineChart.setTitle("GDP_CURRENT_$US");
        xAxis.setLabel("Year");
        XYChart.Series series = new XYChart.Series();

        for(CountryIndicator c: results){
            if(c.getGDP_CURRENT_$US() != 0.0){
                series.getData().add(new XYChart.Data<>(c.getDate(), c.getGDP_CURRENT_$US()));
            }
        }
        lineChart.getData().add(series);
        result.getChildren().add(lineChart);

    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        this.countryIndicatorList = new CountryIndicatorList().getCountryIndicators();
        CountryList list = new CountryList();
        countryList = list.getCountries();

        TreeSet<String> countries = new TreeSet<>();
        TreeSet<String> regions = new TreeSet<>();
        TreeSet<String> levels = new TreeSet<>();
        TreeSet<String> types = new TreeSet<>();

        for (Country c : countryList)
        {
            countries.add(c.getName());
            regions.add(c.getRegionName());
            levels.add(c.getIncomeLevel());
            types.add(c.getLendingType());
        }

        ArrayList<String> remove = new ArrayList<>();

        remove.addAll(regions);
        remove.addAll(levels);
        remove.addAll(types);

        countries.removeAll(remove);

        for (String c : countries)
            System.out.println(c + ",");

        countryNames = FXCollections.observableSet(countries);
        regionNames = FXCollections.observableSet(regions);
        incomeLevels = FXCollections.observableSet(levels);
        lendingTypes = FXCollections.observableSet(types);
    }
}