package de.domjos.HalloJavaFX.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    private static final String PLUS = "+";
    private static final String MINUS = "-";
    private static final String MUL = "*";
    private static final String DIV = "/";

    private @FXML TextField txtNumber1;
    private @FXML TextField txtNumber2;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    public void enterCalculation(ActionEvent actionEvent) {
        if(actionEvent.getSource() instanceof Button) {
            Button parent = (Button) actionEvent.getSource();
            String value = parent.getText().replace(",", ".");

            String content1 = this.txtNumber1.getText().trim();
            String content2 = this.txtNumber2.getText().trim();

            if(content1.endsWith(PLUS) || content1.endsWith(MINUS) || content1.endsWith(MUL) || content1.endsWith(DIV)) {
                if(content2.endsWith(PLUS) || content2.endsWith(MINUS) || content2.endsWith(MUL) || content2.endsWith(DIV)) {
                    this.txtNumber1.setText(this.txtNumber1.getText() + this.txtNumber2.getText());
                    this.txtNumber2.setText(value);
                } else {
                    this.txtNumber2.setText(this.txtNumber2.getText() + value);
                }
            } else {
                this.txtNumber1.setText(this.txtNumber1.getText() + value);
            }
        }
    }

    @FXML
    public void calculate() {
        String content = this.txtNumber1.getText().trim() + this.txtNumber2.getText().trim();

        Double number = getNumber(content);
        while(number == null) {
            content = this.calculate(content);
            number = getNumber(content);
        }
        this.txtNumber1.setText(String.valueOf(number));
        this.txtNumber2.setText("");
    }

    private Double getNumber(String content) {
        try {
            return Double.parseDouble(content);
        } catch (Exception ignored) {}
        return null;
    }

    private String calculate(String content) {
        String operator;
        if(content.contains(MUL)) {
            operator = MUL;
        } else if(content.contains(DIV)) {
            operator = DIV;
        } else if(content.contains(MINUS)) {
            operator = MINUS;
        } else {
            operator = PLUS;
        }

        String firstPart = content.substring(0, content.lastIndexOf(operator));
        String lastPart = content.substring(content.lastIndexOf(operator) + 1);

        int index = indexOf(firstPart, false);
        if(index==firstPart.length()) {
            index = 0;
        }
        String number1 = firstPart.substring(index);
        String number2 = lastPart.substring(0, indexOf(lastPart, true));

        Double n1 = getNumber(number1);
        Double n2 = getNumber(number2);
        if(n1 != null && n2 != null) {
            double result;
            switch (operator) {
                case "*":
                    result = n1 * n2;
                    break;
                case "/":
                    result = n1 / n2;
                    break;
                case "-":
                    result = n1 - n2;
                    break;
                default:
                    result = n1 + n2;
            }
            return content.replace(number1 + operator + number2, String.valueOf(result));
        }
        return content;
    }

    private int indexOf(String content, boolean first) {
        int index;
        int tmp;
        if(first) {
            index = content.indexOf(PLUS);

            tmp = content.indexOf(MINUS);
            if(index < tmp) {
                index = tmp;
            }
            tmp = content.indexOf(MUL);
            if(index < tmp) {
                index = tmp;
            }
            tmp = content.indexOf(DIV);
            if(index < tmp) {
                index = tmp;
            }
        } else {
            index = content.lastIndexOf(PLUS);

            tmp = content.lastIndexOf(MINUS);
            if(index > tmp && tmp != -1) {
                index = tmp;
            }
            tmp = content.lastIndexOf(MUL);
            if(index > tmp && tmp != -1) {
                index = tmp;
            }
            tmp = content.lastIndexOf(DIV);
            if(index > tmp && tmp != -1) {
                index = tmp;
            }
        }
        if(index!=-1) {
            return index + 1;
        } else {
            return content.length();
        }
    }

    @FXML
    public void back() {
        if(!this.txtNumber2.getText().trim().isEmpty()) {
            this.txtNumber2.setText(this.txtNumber2.getText(0, this.txtNumber2.getText().length()-1));
        } else {
            this.txtNumber1.setText(this.txtNumber1.getText(0, this.txtNumber1.getText().length()-1));
        }
    }
}
