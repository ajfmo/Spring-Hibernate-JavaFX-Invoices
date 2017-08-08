package com.utilities;

import org.springframework.stereotype.Component;

@Component
public class ChoiceServiceDialog {
    /*@Autowired
    private IServicesEntityService servicesEntityService;

    @Autowired
    private IBoughtServicesService boughtServicesService;
    private ObservableList<ServiceEntity> servicesList;
    private TableView<ServiceEntity> table;

    public void showDialog(Customer customer) {
        servicesList = FXCollections.observableArrayList();
        new Thread(() -> servicesList.addAll(servicesEntityService.findAll())).start();
        Dialog<ButtonType> dialog = new Dialog<>();
        initGUI(dialog);
        Optional<ButtonType> result = dialog.showAndWait();
        result.ifPresent(c ->
        {
            for (ServiceEntity service : table.getSelectionModel().getSelectedItems()) {
                BoughtServices bs = new BoughtServices(customer, service, BigDecimal.ZERO);
                boughtServicesService.save(bs);
                customer.addBoughtServices(bs);
            }
        });
    }

    private void initGUI(Dialog dialog) {
        ButtonType cancelButton = new ButtonType("Anuluj", ButtonBar.ButtonData.CANCEL_CLOSE);
        ButtonType acceptButton = new ButtonType("Dodaj wybrane", ButtonBar.ButtonData.OK_DONE);
        dialog.setTitle("Wybierz usługę do dodania");
        dialog.getDialogPane().getButtonTypes().addAll(acceptButton, cancelButton);
        table = new TableView<>();
        table.setEditable(false);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        TableColumn<ServiceEntity, String> column = new TableColumn<>("Nazwa usługi");
        column.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getServiceName()));
        table.getColumns().add(column);
        table.setItems(servicesList);

        VBox vBox = new VBox(table);
        vBox.setSpacing(5d);
        vBox.setPadding(new Insets(5, 5, 5, 5));
        dialog.getDialogPane().setContent(vBox);
    }*/
}