package pizzashop;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import pizzashop.controller.MainGUIController;
import pizzashop.gui.KitchenGUI;
import pizzashop.model.PaymentType;
import pizzashop.repository.MenuRepository;
import pizzashop.repository.PaymentRepository;
import pizzashop.service.PaymentService;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        MenuRepository repoMenu=new MenuRepository();
        PaymentRepository payRepo= new PaymentRepository();
        PaymentService service = new PaymentService(repoMenu, payRepo);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/mainFXML.fxml"));
        //VBox box = loader.load();
        Parent box = loader.load();
        MainGUIController ctrl = loader.getController();
        ctrl.setService(service);
        primaryStage.setTitle("PizzeriaX");
        primaryStage.setResizable(false);
        primaryStage.setAlwaysOnTop(false);
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                Alert exitAlert = new Alert(Alert.AlertType.CONFIRMATION, "Would you like to exit the Main window?", ButtonType.YES, ButtonType.NO);
                Optional<ButtonType> result = exitAlert.showAndWait();
                if (result.get() == ButtonType.YES){
                    //Stage stage = (Stage) this.getScene().getWindow();
                    double cash = service.getTotalAmount(PaymentType.Cash);
                    double card = service.getTotalAmount(PaymentType.Card);

                    System.out.println("Incasari cash: "+ cash);
                    System.out.println("Incasari card: "+ card);

                    String header = "Incasari cash, Incasari card, Data";

                    String csvFilePath = "data/register.csv";
                    try(FileWriter csvWriter = new FileWriter(csvFilePath, true)) {
                        try (BufferedReader reader = new BufferedReader(new FileReader(csvFilePath))) {
                            String line = reader.readLine();
                            if (line != null) {
                                if (!line.contains(header)) {
                                    csvWriter.write(header + "\n");
                                }
                            } else {
                                csvWriter.write(header + "\n");
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        csvWriter.write("" + cash + "," + card + "," + LocalDateTime.now().toString().substring(0,10) + "\n");

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    primaryStage.close();
                }
                // consume event
                else if (result.get() == ButtonType.NO){
                    event.consume();
                }
                else {
                    event.consume();

                }

            }
        });
        primaryStage.setScene(new Scene(box));
        primaryStage.show();
        KitchenGUI kitchenGUI = new KitchenGUI();
        kitchenGUI.KitchenGUI();
    }

    public static void writeToCsv() {

    }

    public static void main(String[] args) { launch(args);
    }
}
