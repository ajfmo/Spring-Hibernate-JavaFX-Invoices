package com.UI.controllers;

import com.entity.BoughtServices;
import com.entity.Customer;
import com.entity.ServiceEntity;
import com.service.IBoughtServicesService;
import com.service.ICustomerService;
import com.utilities.ChoiceServiceDialog;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class MainWindowPresenter
{
    @FXML private TableView<ServiceEntity> boughtServicesTableView;

    @FXML private TableColumn orderColumn;
    @FXML private TableColumn<ServiceEntity, String>  serviceNameColumn;
    @FXML private TableColumn<ServiceEntity, String>  symbolColumn;
    @FXML private TableColumn<ServiceEntity, String>  unitColumn;
    @FXML private TableColumn<BoughtServices, BigDecimal> quantityColumn;
    @FXML private TableColumn<ServiceEntity, BigDecimal>  unitPriceColumn;
    @FXML private TableColumn valWithoutTax;
    @FXML private TableColumn<ServiceEntity, Integer>  taxRateColumn;
    @FXML private TableColumn taxValColumn;
    @FXML private TableColumn valWithTaxColumn;

    @FXML private TableView<Customer> customersTableView;

    @FXML private TableColumn<Customer, String> customersCol;

    @FXML private Label contractorNameLabel;
    @FXML private Label companyNameLabel;
    @FXML private Label addressLabel;
    @FXML private Label cityLabel;
    @FXML private Label taxIDLabel;

    @FXML private Button serviceAddButton;
    @FXML private Button serviceDeleteButton;

    @Autowired
    private ICustomerService customerService;

    @Autowired
    private IBoughtServicesService boughtServicesService;

    @Autowired
    private ChoiceServiceDialog choiceServiceDialog;

    private BigDecimal withoutTax = new BigDecimal(0);
    private BigDecimal tax = new BigDecimal(0);
    private BigDecimal withtTax = new BigDecimal(0);

    private ObservableList<Customer> customerList = FXCollections.observableArrayList();
    private ObservableList<ServiceEntity> servicesList = FXCollections.observableArrayList();

    @FXML
    public void initialize()
    {
        configureServicesTable();
        configureCustomersTable();

        showCustomerDetails(customerService.findOne(1));
        populateBoughtServicesData(customerService.findOne(1));
        boughtServicesTableView.setItems(servicesList);

        configureButtons();
    }

    private void configureButtons()
    {
        serviceAddButton.setOnAction(e -> choiceServiceDialog.showDialog(customersTableView
                .getSelectionModel().getSelectedItem()));
        populateBoughtServicesData(customersTableView.getSelectionModel().getSelectedItem());
    }

    private void configureCustomersTable()
    {
        customersCol.setCellValueFactory(new PropertyValueFactory<>("alias"));

        customerList.addAll(customerService.findAll());

        customersTableView.setItems(customerList);
        customersTableView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldVal, newVal) -> {

            showCustomerDetails(newVal);
            populateBoughtServicesData(customersTableView.getSelectionModel().getSelectedItem());
        });

        customersTableView.getSelectionModel().select(0);
    }

    private void showCustomerDetails(Customer customer)
    {
        contractorNameLabel.setText(customer.getFirstName() + " " + customer.getLastName());
        companyNameLabel.setText(customer.getCompanyName());
        addressLabel.setText(customer.getAddress());
        cityLabel.setText(customer.getPostalCode() + " " + customer.getCity());
        taxIDLabel.setText(customer.getTaxIdentifier());
    }

    private void configureServicesTable()
    {
        //lp
        serviceNameColumn.setCellValueFactory(new PropertyValueFactory<>("serviceName"));
        symbolColumn.setCellValueFactory(new PropertyValueFactory<>("symbol"));
        unitColumn.setCellValueFactory(new PropertyValueFactory<>("unit"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        unitPriceColumn.setCellValueFactory(new PropertyValueFactory<>("netUnitPrice"));
        valWithoutTax.setCellValueFactory(new PropertyValueFactory<>("withoutTax"));
        taxRateColumn.setCellValueFactory(new PropertyValueFactory<>("vatTaxRate"));
        taxValColumn.setCellValueFactory(new PropertyValueFactory<>("tax"));
        valWithTaxColumn.setCellValueFactory(new PropertyValueFactory<>("withTax"));
    }

    private void populateBoughtServicesData(Customer customer)
    {
        servicesList.clear();
        for (BoughtServices boughtServices : boughtServicesService.findBoughtServicesByCustomer_Id(customer.getId()))
        {
            servicesList.add(boughtServices.getServiceEntity());
        }
    }
}