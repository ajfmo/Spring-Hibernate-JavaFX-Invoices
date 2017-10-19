package com.utilities;

import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.util.converter.BigDecimalStringConverter;

import java.math.BigDecimal;

public class BigDecimalTextField extends TextField {

    private SimpleObjectProperty<BigDecimal> value;

    public BigDecimalTextField()
    {
        super();
        this.setTextFormatter(new TextFormatter<>(new BigDecimalStringConverter()));
        this.value = new SimpleObjectProperty<>();
    }

    public BigDecimal getValue()
    {
         return this.value.getValue();
    }

    public void setValue(BigDecimal val)
    {
        try {
            this.value.set(val);
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Conversion error");
            alert.setHeaderText("An error occurred while entering the value");
            alert.setContentText("It seems that the you have entered wrong type of the value");
            alert.showAndWait();
        }
    }

    public SimpleObjectProperty<BigDecimal> valueProperty() {
        return value;
    }
}
