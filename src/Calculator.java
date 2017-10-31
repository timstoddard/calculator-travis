import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.geometry.*;

// travis
public class Calculator extends Application {

    final int MAX_INPUT_LENGTH = Integer.MAX_VALUE;

    private Label screen;
    private String input;
    private boolean resetInput;
    private int opcode;
    private double result;

    @Override
    public void start(Stage stage) {
        initVariables();
        initStage(stage, "Calculator");
    }

    private void initVariables() {
        input = "";
        resetInput = false;
        opcode = 0;
        result = 0.0;
    }

    private void updateCurrentValue(int opcode) {
        if (!resetInput) {
            double currentInput = Double.parseDouble(input);
            System.out.println(result + ", " + currentInput + ", " + this.opcode);
            switch (this.opcode) {
                case 0:
                    result = currentInput;
                    break;
                case 1:
                    result = OperationFactory.getOperation(Operation.ADD).getResult(result, currentInput);
                    break;
                case 2:
                    result = OperationFactory.getOperation(Operation.SUBTRACT).getResult(result, currentInput);
                    break;
                case 3:
                    result = OperationFactory.getOperation(Operation.MULTIPLY).getResult(result, currentInput);
                    break;
                case 4:
                    result = OperationFactory.getOperation(Operation.DIVIDE).getResult(result, currentInput);
                    break;
            }
            input = formatResult(result);
            screen.setText(input);
        }
        this.opcode = opcode;
        resetInput = true;
    }

    private String formatResult(double d) {
        String s = Double.toString(result);
        return s.length() > 10 ? s.substring(0, 10) : s;
    }

    private void updateInput(String s) {
        if (resetInput) {
            input = "";
            resetInput = false;
        }
        input += s;
        screen.setText(input);
    }

    /*** UI Setup Methods ***/

    private void initStage(Stage stage, String name) {
        Scene scene = new Scene(createRootPane(), 300, 250);
        stage.setTitle(name);
        stage.setScene(scene);
        stage.show();
    }

    private GridPane createRootPane() {
        GridPane root = new GridPane();
        root.setAlignment(Pos.CENTER);
        root.setHgap(10);
        root.setVgap(10);
        root.setPadding(new Insets(25, 25, 25, 25));

        root.add(setUpScreen(), 0, 0);
        root.add(setUpButtons(), 0, 1);

        return root;
    }

    private Label setUpScreen() {
        screen = new Label();
        screen.setBorder(new Border(
                new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        screen.setMaxWidth(200);
        return screen;
    }

    private GridPane setUpButtons() {
        GridPane buttons = new GridPane();

        // row 1
        addNumberButton(buttons, "7", 0, 1, 1, 1);
        addNumberButton(buttons, "8", 1, 1, 1, 1);
        addNumberButton(buttons, "9", 2, 1, 1, 1);
        addButton(buttons, "+", 3, 1, 1, 1).setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                updateCurrentValue(1);
            }
        });

        // row 2
        addNumberButton(buttons, "4", 0, 2, 1, 1);
        addNumberButton(buttons, "5", 1, 2, 1, 1);
        addNumberButton(buttons, "6", 2, 2, 1, 1);
        addButton(buttons, "-", 3, 2, 1, 1).setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                updateCurrentValue(2);
            }
        });

        // row 3
        addNumberButton(buttons, "1", 0, 3, 1, 1);
        addNumberButton(buttons, "2", 1, 3, 1, 1);
        addNumberButton(buttons, "3", 2, 3, 1, 1);
        addButton(buttons, "x", 3, 3, 1, 1).setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                updateCurrentValue(3);
            }
        });

        // row 4
        addNumberButton(buttons, "0", 0, 4, 1, 1);
        addButton(buttons, ".", 1, 4, 1, 1).setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if (resetInput || (input.length() < MAX_INPUT_LENGTH && input.indexOf('.') == -1)) {
                    updateInput(".");
                }
            }
        });
        addButton(buttons, "=", 2, 4, 1, 1).setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                updateCurrentValue(0);
            }
        });
        addButton(buttons, "/", 3, 4, 1, 1).setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                updateCurrentValue(4);
            }
        });

        // row 5
        addButton(buttons, "Clear", 0, 5, 4, 1).setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                result = 0;
                input = "";
                screen.setText(input);
                opcode = 0;
            }
        });

        return buttons;
    }

    private void addNumberButton(GridPane pane, String name, int column, int row, int columnSpan, int rowSpan) {
        Button button = new Button(name);
        button.setMaxWidth(Double.MAX_VALUE);
        pane.add(button, column, row, columnSpan, rowSpan);

        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if (resetInput || (input.length() < MAX_INPUT_LENGTH)) {
                    updateInput(name);
                }
            }
        });
    }

    private Button addButton(GridPane pane, String name, int column, int row, int columnSpan, int rowSpan) {
        Button button = new Button(name);
        button.setMaxWidth(Double.MAX_VALUE);
        pane.add(button, column, row, columnSpan, rowSpan);
        return button;
    }

    /*** Main Method ***/

    public static void main(String[] args) {
        launch(args);
    }
}
