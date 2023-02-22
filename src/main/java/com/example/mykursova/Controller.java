package com.example.mykursova;

import com.example.mykursova.db.DataBase;
import com.example.mykursova.taxes.Income;
import com.example.mykursova.taxes.Taxes;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

public class Controller  implements Initializable {

    @FXML
    public Label totalSum;
    @FXML
    private Label WriteToDataBase;
    @FXML
    private ListView<String> setTaxes;

    @FXML
    private TableView<Income> table = new TableView<>();

    @FXML
    private TableColumn<Income, String> name //
            = new TableColumn<>("Назва");

    @FXML
    private TableColumn<Income, Double> size//
            = new TableColumn<>("Розмір");

    @FXML
    private TableColumn<Income, Double> tax//
            = new TableColumn<>("Податок");

    @FXML
    private TableColumn<Income, Double> percentage//
            = new TableColumn<>("Відсоток");

    @FXML
    private ChoiceBox<String> choiceBox;

    @FXML
    private TextField rangeA;
    @FXML
    private TextField rangeB;
    @FXML
    private TextField sizeIncome;
    private final String[] nameTaxes = {"головний дохід", "додатковий дохід", "авторські винагороди",
            "переказ з-за кордону", "фінансова допомога", "продаж майна", "кошти в подарунок"};

    Double a,b;
    String nameTax;
    Taxes taxes = new Taxes();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<Income> taxes1 = taxes.getTaxes();

        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        size.setCellValueFactory(new PropertyValueFactory<>("size"));
        percentage.setCellValueFactory(new PropertyValueFactory<>("percentage"));
        tax.setCellValueFactory(new PropertyValueFactory<>("tax"));


        name.setSortType(TableColumn.SortType.ASCENDING);

        ObservableList<Income> list = FXCollections.observableArrayList(taxes1);
        table.setItems(list);

        UpdateTotalSumTax();
        UpdateSet();
        choiceBox.getItems().addAll(nameTaxes);
        choiceBox.setOnAction(this::getTaxType);

        setDisableOrVisibleLabel(false, true);

    }

    public void setDisableOrVisibleLabel(boolean visible,boolean disable){
        WriteToDataBase.setVisible(visible);
        WriteToDataBase.setDisable(disable);
    }
    public void getTaxType(javafx.event.ActionEvent e){
        nameTax = choiceBox.getValue();
    }
    public void addTax(){

        double income = Double.parseDouble(sizeIncome.getText());
        Income inc1 = new Income(nameTax, income);
        taxes.addIncome(taxes.getTaxes(),inc1);
        UpdateTable(taxes.getTaxes());
        UpdateTotalSumTax();
        UpdateSet();
    }


    @FXML
    public void submit(){
        a = Double.parseDouble(rangeA.getText());
        b = Double.parseDouble(rangeB.getText());
    }
    @FXML
    public void searchTax(){
        submit();
        List<Income> person = taxes.searchTaxes("Податок", a, b );
        UpdateTable(person);

        UpdateTotalSumTax();
        UpdateSet();
    }
    @FXML
    public void searchInc(){
        submit();
        List<Income> person = taxes.searchTaxes("Дохід",a,b );
        UpdateTable(person);
        UpdateTotalSumTax();
        UpdateSet();
    }
    @FXML
    public void sortAsc(){

        List<Income> person = taxes.sortTaxes("За зростанням");
        UpdateTable(person);
        UpdateTotalSumTax();
        UpdateSet();
    }
    @FXML
    public void sortDesc(){

        List<Income> person = taxes.sortTaxes("За спаданням");
        UpdateTable(person);
        UpdateTotalSumTax();
        UpdateSet();
    }
    @FXML
    public void writeToDataBase(){

        UpdateTable(taxes.getTaxes());
        UpdateTotalSumTax();
        UpdateSet();
        DataBase.writeTaxesToDatabase(taxes.getTaxes());
        setDisableOrVisibleLabel(true, false);
        System.out.println(" Дані про податки успішно записані до бази даних!");
    }
    public void UpdateTotalSumTax(){
        setDisableOrVisibleLabel(false, true);
        double totalSumTax = taxes.SumTaxes();
        totalSum.setText("Сума податків: " + totalSumTax);
    }
    public void UpdateTable(List<Income> taxes){
        table.getItems().clear();

        ObservableList<Income> list = FXCollections.observableArrayList(taxes);
        table.setItems(list);
    }
    public void UpdateSet(){
        setDisableOrVisibleLabel(false, true);
        Set<Double> taxesSet = taxes.SetTaxes();
        setTaxes.getItems().clear();

        if (taxes.getTaxes().isEmpty()){
            setTaxes.getItems().add("Це не є податки!");
        } else
            for (Double tax : taxesSet) {
                setTaxes.getItems().addAll(tax.toString());
            }
    }


}