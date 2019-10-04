import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

    public class Main extends Application {
        ValueCalculator valueCalculator = new ValueCalculator();
        Chart swingWorkerRealTime = new Chart();

        @Override
        public void start(Stage stage) throws Exception {
            stage.setTitle("Input Area");

            Label label = new Label("Sensitivity");
            Label label3 = new Label("Speed");
            Font font = new Font("Comic Sans MS", 16);
            Font bigFont = new Font("Comic Sans MS", 32);
            label.setFont(font);
            label3.setFont(font);

            TextField textField = new TextField();
            textField.setMaxWidth(100);

            Label label2 = new Label("Result");
            label2.setFont(bigFont);

            Button buttonCustom = new Button("Custom");
            buttonCustom.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    String sensitivity = textField.getText();
                    int sensitivityNum = Integer.parseInt(sensitivity);
                    swingWorkerRealTime.setCustomValue(sensitivityNum);
                    if(swingWorkerRealTime.getMyValue() == 1)
                        label2.setText("BUY");
                    else if(swingWorkerRealTime.getMyValue() == 0)
                        label2.setText("SELL");
                }
            });

            Button button = new Button("Default");
            button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    if(swingWorkerRealTime.getValue() == 1)
                    label2.setText("BUY");
                    else if(swingWorkerRealTime.getValue() == 0)
                        label2.setText("SELL");
                }
            });

            Button button2 = new Button("High");
            button2.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    if(swingWorkerRealTime.getHighValue() == 1)
                        label2.setText("BUY");
                    else if(swingWorkerRealTime.getHighValue() == 0)
                        label2.setText("SELL");
                }
            });

            Button button3 = new Button("Low");
            button3.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    if(swingWorkerRealTime.getLowValue() == 1)
                        label2.setText("BUY");
                    else if(swingWorkerRealTime.getLowValue() == 0)
                        label2.setText("SELL");
                }
            });

            Button button4 = new Button("Slower");
            button4.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                        swingWorkerRealTime.setSpeed(2);
                }
            });

            Button button5 = new Button("Faster");
            button5.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    swingWorkerRealTime.setSpeed(-2);
                }
            });

            VBox root = new VBox();
            root.setSpacing(20);
            root.setPadding(new Insets(40, 40, 40, 40));

            root.getChildren().addAll(label3, button4, button5, label, textField, buttonCustom, button, button2, button3, label2);

            // Create a scene
            Scene scene = new Scene(root, 400, 500);
            stage.setScene(scene);

            stage.show();

            // Create and Initialize Stock Chart **************************
            swingWorkerRealTime.go();

            if(swingWorkerRealTime.isOn())
                valueCalculator.calculate(swingWorkerRealTime.mySwingWorker.valueList());


        }
    }
