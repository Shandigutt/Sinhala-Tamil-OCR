/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sinhala_tamil_ocr;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.stage.Window;

/**
 *
 * @author Tharaka
 */
public class FXMLDocumentController implements Initializable {
    
    
    @FXML
    private Button loadImage;
    @FXML
    private Button process;
    @FXML
    private ImageView imageView;
    @FXML
    private ScrollPane textArea;
    private File imageFile;
    private Window parent;
    private String pathToImage;
    @FXML
    private ScrollPane imageScroll;
    @FXML
    private SplitPane splitPane;
    @FXML
    private AnchorPane imageArea;
    @FXML
    private AnchorPane textAreaAnchor;
    @FXML
    private TextArea output;
    ProcessBuilder builder;
    
    
    
    public void imageResize(){
        imageView.setFitWidth(imageScroll.getWidth()-5);
    }
    
    
    public void runCommand(String command) throws Exception { 
        builder = new ProcessBuilder(
            "cmd.exe", "/c", command); //C:\\Users\\Tharaka\\Desktop\\21.jpg
        builder.redirectErrorStream(true);
        Map<String, String> env = builder.environment();
        env.put("TESSDATA_PREFIX", System.getProperty("user.dir")+"\\Tesseract-OCR\\");
        System.out.println(System.getenv("PATH"));
        //String s = env.get("Path");
        //System.out.println(s);
        String newPath = System.getenv("PATH")+";"+System.getProperty("user.dir")+"\\Tesseract-OCR\\";
        env.replace("Path", newPath);
        System.out.println(newPath);
        //env.put("PATH=", System.getenv("PATH")+";C:\\Program Files\\Tesseract-OCR");
        
        Process p = builder.start();
        BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String line;
        while (true) {
            line = r.readLine();
            if (line == null) { break; }
            System.out.println(line);
        }
        
    }
    
    public void normalizeText() throws FileNotFoundException, IOException{
        File file = new File(System.getProperty("user.dir") + "\\output.txt");
        FileInputStream fin = new FileInputStream(file);
        BufferedReader ori = new BufferedReader(new InputStreamReader(fin, "UTF-8"));

        Normalizer nm = new Normalizer();

        String line = "";

        while ((line = ori.readLine()) != null) {
            line = line.trim();
            String segments[] = line.split(" ");

            for (int i = 0; i < segments.length; i++) {

                output.appendText(nm.TamilNormalize(segments[i]) + " ");

            }
            output.appendText("\n");

        }
    }

 
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //parent = loadImage.getScene().getWindow();
        
        
        loadImage.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Open Image File");
                fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif", "*.tif"));
                imageFile = fileChooser.showOpenDialog(parent);
                if (imageFile != null) {
                    pathToImage = imageFile.getAbsolutePath();
                    //System.out.println(System.getProperty("user.dir"));
                    imageView.setImage(new Image("file:" + pathToImage));
                    imageResize();
                    
                }
                
            }
        });
        
        imageArea.widthProperty().addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                imageResize();
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                
            }
        });
        
        process.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                try {
                    output.clear();
                    
                    //runCommand("set TESSDATA_PREFIX=C:\\Program Files\\Tesseract-OCR\\");//+System.getProperty("user.dir")
                    //runCommand("set PATH=C:\\Program Files\\Tesseract-OCR");//+System.getProperty("user.dir")
                    
                    runCommand("tesseract " + pathToImage + " " + System.getProperty("user.dir") + "\\output -l Ta.AkTneeS807-AllSizes");
                    normalizeText();
                    
                } catch (Exception ex) {
                    Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        
        
        
    }    
    
}
