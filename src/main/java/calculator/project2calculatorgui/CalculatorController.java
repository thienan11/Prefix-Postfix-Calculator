package calculator.project2calculatorgui;

import javafx.beans.binding.BooleanExpression;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;

import java.util.*;

/**
 * Controller for calculator program
 *
 * @author Thien An Tran
 * @version May 24, 2023
 */
public class CalculatorController {
    @FXML
    private TextField expressionText;
    @FXML
    private RadioButton prefixRadioButton;
    @FXML
    private RadioButton postfixRadioButton;
    @FXML
    private GridPane gridPaneNum;
    @FXML
    private GridPane gridPaneOp;
    @FXML
    private TextField varNameText;
    @FXML
    private TextField varValText;
    @FXML
    private Button varButton;
    @FXML
    private TableView<String> historyTableView;
    @FXML
    private TableColumn<String, String> historyTableColumn;
    @FXML
    private ListView<String> nameList;
    @FXML
    private ListView<Double> valList;
    private Parser parser;
    private ParseStrategy prefixStrategy;
    private ParseStrategy postfixStrategy;
    private History history;
    private Map<String, Double> env;
    private Alert alert;
    private int caretPosition = 0;
    private Set<String> historyStringSet;

    /**
     * Initializes all components of the calculator
     */
    @FXML
    public void initialize() {
        history = new History();
        env = new HashMap<>();
        parser = new Parser();

        historyStringSet = new LinkedHashSet<>();
        alert = new Alert(Alert.AlertType.INFORMATION);
        prefixStrategy = new PrefixParser();
        postfixStrategy = new PostfixParser();
        ToggleGroup parserMethods = new ToggleGroup();

        prefixRadioButton.setToggleGroup(parserMethods);
        postfixRadioButton.setToggleGroup(parserMethods);
        prefixRadioButton.setSelected(true);

        parser.setParseStrategy(prefixStrategy);

        gridPaneNum.getChildren().stream()
                .filter(Button.class::isInstance)
                .map(Button.class::cast)
                .forEach(button -> button.setOnAction(handleGridClick));

        gridPaneOp.getChildren().stream()
                .filter(Button.class::isInstance)
                .map(Button.class::cast)
                .forEach(button -> button.setOnAction(handleGridClick));

        expressionText.setOnMouseClicked(event ->
                        caretPosition = expressionText.getText().length() -
                                        expressionText.getCaretPosition());

        expressionText.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                String input = expressionText.getText();
                processInput(input);
            }
        });

        prefixRadioButton.setOnAction(handleParseRadioButtonClick);
        postfixRadioButton.setOnAction(handleParseRadioButtonClick);

        varButton.disableProperty().bind(BooleanExpression.booleanExpression(
                varValText.textProperty().isEmpty().or(varNameText.textProperty().isEmpty())
        ));
        varButton.setOnAction(handleAddAndEditVarButtonClick);

        historyTableColumn.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue()));

        historyTableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2 && !historyTableView.getSelectionModel().isEmpty()) {
                String cellValue = historyTableColumn.getCellData(
                        historyTableView.getSelectionModel().getSelectedIndex());

                expressionText.clear();
                caretPosition = 0;
                insertTextAtCursor(cellValue);
            }
        });

        nameList.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2 && !nameList.getSelectionModel().isEmpty()) {
                insertTextAtCursor(nameList.getSelectionModel().getSelectedItem());
            }
        });
    }

    /**
     * Handles clicks for the add/edit variable binding button
     */
    EventHandler<ActionEvent> handleAddAndEditVarButtonClick = e -> {
        try {
            if (!varNameText.getText().isEmpty() && !varValText.getText().isEmpty()) {
                String name = varNameText.getText();
                Double value = Double.parseDouble(varValText.getText());
                env.put(name, value);

                nameList.setItems(FXCollections.observableArrayList(env.keySet()));
                valList.setItems(FXCollections.observableArrayList(env.values()));
            }
        } catch (NumberFormatException n) {
            alert.setHeaderText("Invalid input");
            alert.showAndWait();
        }
    };

    /**
     * Handles clicks on radio buttons
     */
    EventHandler<ActionEvent> handleParseRadioButtonClick = e -> {
        if (prefixRadioButton.isSelected()) {
            parser.setParseStrategy(prefixStrategy);
            updateHistory();
        }
        if (postfixRadioButton.isSelected()) {
            parser.setParseStrategy(postfixStrategy);
            updateHistory();
        }
    };

    /**
     * Handles all the button clicks that is use for typing in the expression text field
     */
    EventHandler<ActionEvent> handleGridClick = e -> {
        Button clicked = (Button) e.getSource(); // get the thing that was clicked
        String buttonText = clicked.getText();

        switch (buttonText) {
            case "7", "8", "9", "4", "5", "6", "1", "2", "3", "0", ".",
                    "+", "-", "*", "/", "^", "%"->
                insertTextAtCursor(buttonText);
            case "space" ->
                insertTextAtCursor(" ");
            case "clear" ->{
                expressionText.clear();
                caretPosition = 0;
            }
            case "=" ->{
                String input = expressionText.getText();
                processInput(input);
            }
            default ->
                expressionText.appendText("");
        }
    };

    /**
     * Inserts the given text at the position of the cursor in the expression text field
     *
     * @param textToInsert a string
     */
    public void insertTextAtCursor(String textToInsert){
        if (caretPosition >= 0) {
            String text = expressionText.getText();
            String newText = text.substring(0, text.length() - caretPosition) +
                    textToInsert + text.substring(text.length() - caretPosition);

            expressionText.setText(newText);

            expressionText.positionCaret(caretPosition + textToInsert.length());
        }
    }
    /**
     * Interprets the user's inputs
     *
     * @param input a string of the user's input
     * @throws NumberFormatException when result of expression is NaN or infinity
     * @throws IllegalArgumentException when the input is not in prefix or postfix notation
     * @throws ArithmeticException when trying to divide by zero
     * @throws NoSuchElementException when trying to access a variable that is not in the environment
     */
    public void processInput(String input) {
        alert.setTitle("Error!!!");
        alert.setContentText("Try again.");
        try {
            Expression ast = parser.parse(input, env);
            double result = checkValidResult(ast.evaluate());

            String exprStr = changeStringForm(ast);

            List<String> expressionsList = history.getExpressions().stream()
                    .map(this::changeStringForm)
                    .toList();

            if (!historyTableView.getItems().contains(exprStr)){
                history.addExpression(ast);
                updateHistory();
            } else {
                if (expressionsList.contains(exprStr)) {
                    int indexInHistory = expressionsList.indexOf(exprStr);
                    history.removeExpression(indexInHistory);
                    history.addExpression(ast);
                }
                updateHistory();
            }

            expressionText.clear();
            caretPosition = 0;
            insertTextAtCursor(Double.toString(result));

        } catch (NumberFormatException e){
            alert.setHeaderText("Result is either NaN or infinity");
            alert.showAndWait();
        } catch (IllegalArgumentException e) {
            alert.setHeaderText("Input is invalid.");
            alert.showAndWait();
        } catch (ArithmeticException e) {
            alert.setHeaderText("Cannot divide by zero");
            alert.showAndWait();
        } catch (NoSuchElementException e){
            alert.setHeaderText("Variable not found");
            alert.show();
        }
    }

    /**
     * Checks if the result of an expression tree is correct (not NaN or Infinity)
     *
     * @param res the result of an expression tree
     * @return the result if it is valid
     * @throws NumberFormatException when the result is NaN or infinite
     */
    public double checkValidResult(double res) {
        if (Double.isNaN(res) || Double.isInfinite(res) || res > Double.MAX_VALUE) {
            throw new NumberFormatException();
        } else {
            return res;
        }
    }

    /**
     * Checks if the given string is a valid number
     *
     * @param token a string
     * @return true if the string is a valid number, false otherwise
     */
    public boolean isValidNumber(String token) {
        return token.matches("^[-+]?(\\d+(\\.(\\d+)?)?)?$");
    }

    /**
     * Checks if the given string is a valid variable (not a number)
     *
     * @param token a string
     * @return true if the string is a valid variable, false otherwise
     */
    public boolean isValidVariable(String token) {
        return token.matches("[a-zA-Z]\\w*");
    }

    /**
     * Updates the history table view based on what's current in history
     */
    public void updateHistory() {
        List<String> expressionsList = history.getExpressions().stream()
                .map(this::changeStringForm)
                .toList();
        historyStringSet.clear();
        historyStringSet.addAll(expressionsList);
        historyTableView.setItems(FXCollections.observableArrayList(historyStringSet));
    }

    /**
     * checks if a name can be typed in the variable name text field
     */
    @FXML
    public void checkVarName(){
        varNameText.setTextFormatter(new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            if (isValidVariable(newText) || newText.isEmpty()){
                return change;
            } else{
                return null;
            }
        }));
    }

    /**
     * checks if a value can be typed in the variable value text field
     */
    @FXML
    public void checkVarVal(){
        varValText.setTextFormatter(new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            if (isValidNumber(newText) || newText.isEmpty()){
                return change;
            } else{
                return null;
            }
        }));
    }

    /**
     * Returns the expression in the correct toString form based on which mode the calculator is using
     *
     * @param expr an Expression
     * @return a string representing the expression
     */
    public String changeStringForm(Expression expr) {
        if(prefixRadioButton.isSelected()) {
            return expr.toPrefixString();
        } else {
            return expr.toPostfixString();
        }
    }
}