package sample;

import com.jfoenix.controls.*;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import javafxdb.ModelTable;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.awt.*;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.*;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.apache.poi.ss.usermodel.HorizontalAlignment.*;

public class Controller implements Initializable {

    Statement st;
    PreparedStatement stp;


    public Connection getConnection() {
        Connection con;

        try {

            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/girraf?useTimezone=true&serverTimezone=UTC", "root", "689078");
            System.out.println("Action Succes!...");

            return con;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    ByteArrayOutputStream emailoutputstream = new ByteArrayOutputStream();

    @FXML
    private TableView<ModelTable> tableView;

    @FXML
    private TableColumn<ModelTable, Date> col_date;

    @FXML
    private TableColumn<ModelTable, Float> col_amount;

    @FXML
    private TableColumn<ModelTable, String> col_details;


    @FXML
    private TableColumn<ModelTable, String> col_receivenum;


    @FXML
    private JFXTextField mainsum;

    @FXML
    private JFXButton editmainsum;

    @FXML
    private JFXButton confirmainsum;


    @FXML
    private JFXTextField totalspaend;

    @FXML
    private JFXTextField leftmainsum;


    @FXML
    private JFXTextField datefield;


    @FXML
    private JFXTextField amountfield;


    @FXML
    private JFXTextArea detailsarea;


    @FXML
    private JFXTextField receivefield;

//@FXML
//private FontAwesomeIcon date;

    @FXML
    private FontAwesomeIcon shoppingsum;


    @FXML
    private FontAwesomeIcon itemsboghted;


    @FXML
    private FontAwesomeIcon receivicon;


    @FXML
    private FontAwesomeIcon shoppingsum1;

    @FXML
    private FontAwesomeIcon shoppingsum2;



    @FXML
    private JFXButton addbtn;

    @FXML
    private JFXButton updatebtn;

    @FXML
    private FontAwesomeIcon blal;

    @FXML
    private JFXButton deletebtn;

    @FXML
    private JFXButton clearbtn;

   @FXML
           private JFXButton exportexelbtn;

@FXML
private JFXButton deletetable;

@FXML
private JFXButton sendemailbtn;

@FXML
private JFXButton editexeltitile;

@FXML
private JFXButton confirmexeltitile;

@FXML
private JFXTextField textexeltitile;

@FXML
private JFXButton showpanemail;

@FXML
private AnchorPane emailveiw;


@FXML
private JFXTextField myemail;

    @FXML
    private JFXPasswordField mypass;

    @FXML
    private JFXTextField someonemail;


    @FXML
    private JFXTextField subjectemail;


    @FXML
    private JFXTextArea infoemail;

    @FXML
    private JFXButton sendemailmainbtn;

    @FXML
    private Hyperlink hyperlink;

    @FXML
        private JFXDatePicker datePicker;

    float numtotalspend;
    float sum;

    String afterconfirmexeltitile;
    File savefile;
    String path =null;
String confirmtoexel = "";
    Integer num;
    int count;






    private void clearFields() {
        datefield.setText(null);
        amountfield.setText(null);
        detailsarea.setText(null);
        receivefield.setText(null);
        addbtn.setDisable(true);
        updatebtn.setDisable(true);
    }


    @FXML
    private void setClearbtn(ActionEvent event) {
        clearFields();
    }

    @FXML
    private void setDeletetable (ActionEvent event) throws SQLException {

        Connection connection = getConnection();


        try {
            count();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("מחיקת טבלה");
            alert.setHeaderText("מחיקת כל הפריטים המופיעים בטבלה");
            alert.setContentText("האם  את/ה בטוח שברצונך למחוק את כל הפריטים המופיעים בטבלה לצמיתות?");

            Optional<ButtonType> result = alert.showAndWait();
            if(result.get()== ButtonType.OK){

                String query = "delete FROM managment where sheetname ='"+textexeltitile.getText()+"' order by emp_id desc limit "+num.toString()+"  ";
                PreparedStatement st;
                int rs;
                st = connection.prepareStatement(query);
                rs = st.executeUpdate();

                Update_table();
                setTotalspaend();
                setLeftmainsum();
                clearFields();

                mainsum.setText("");
                leftmainsum.setText("");
                totalspaend.setText("");
            }



        } catch (Exception e) {
            e.printStackTrace();

        }

    }



    @FXML
    private void setDeletebtn(ActionEvent event) {

        Connection connection = getConnection();


        try {

            ObservableList<ModelTable> model = (ObservableList<ModelTable>) tableView.getSelectionModel().getSelectedItems();


            ModelTable modelTable = (ModelTable) tableView.getSelectionModel().getSelectedItem();

            System.out.println(model);

            Integer g = modelTable.getEmp_id();


            String query = "delete from managment where emp_id in (?)";
            PreparedStatement st;
            int rs;
            st = connection.prepareStatement(query);
            st.setInt(1, g);


            rs = st.executeUpdate();

            Update_table();
            setTotalspaend();
            setLeftmainsum();

        } catch (Exception e) {
            e.printStackTrace();


        }

    }


    public void setUpdatebtn(ActionEvent event) {


        Connection connection = getConnection();


        String query = "update managment set date='" + datefield.getText() + "', amount='" + amountfield.getText() + "', details='" + detailsarea.getText() + "', receiv='"+ receivefield.getText() + "',filepath=?,sheetname='"+textexeltitile.getText()+ "' where emp_id=?";

        PreparedStatement stp;
        Statement st;
        int rs;
        try {
            ModelTable modelTable = (ModelTable) tableView.getSelectionModel().getSelectedItem();
            int emp_id = modelTable.getEmp_id();

            stp = connection.prepareStatement(query);

            stp.setString(1,path + "");
            stp.setInt(2, emp_id);


            rs = stp.executeUpdate();








































            updatebtn.setDisable(true);

            setTotalspaend();
        } catch (Exception e) {
            e.printStackTrace();


        }

        Update_table();
        // clearFields();
        setLeftmainsum();
    }


    @FXML
    public void setAddbtn(ActionEvent event) throws SQLException {

        Connection connection = getConnection();

        String query = "INSERT INTO managment(date, amount, details, receiv,mainbudget,filepath,sheetname) values(?,?,?,?,?,?,?)";
        int rs;
        PreparedStatement st = connection.prepareStatement(query);


        try {


            st.setString(1, datefield.getText());
            st.setString(2, amountfield.getText());
            st.setString(3, detailsarea.getText());
            st.setString(4,  receivefield.getText());
            st.setString(5, mainsum.getText());
            if (path == null){
                st.setString(6,savefile.getAbsolutePath()+ "");
            }else if(path!=null){
                st.setString(6,path + "");
            }

            st.setString(7, textexeltitile.getText());
            st.executeUpdate();

            setTotalspaend();

        } catch (Exception e) {

            e.printStackTrace();

        }

        Update_table();
        setLeftmainsum();
        clearFields();
        addbtn.setDisable(true);

    }





    @FXML
    private  void setAfterconfirmexeltitile(KeyEvent event){

        if (!textexeltitile.getText().isEmpty()){
            confirmexeltitile.setDisable(false);
        }else if (textexeltitile.getText().isEmpty()){

            confirmexeltitile.setDisable(true);
        }


    }




    @FXML
    private void setconfirmbutton(KeyEvent event) {



            if (!mainsum.getText().isEmpty()) {

                confirmainsum.setDisable(false);
            } else if (mainsum.getText().isEmpty()) {

                confirmainsum.setDisable(true);
            }



    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        showpanemail.setStyle("-fx-background-color: #A9A9A9");
        confirmexeltitile.setStyle("-fx-background-color: #A9A9A9");
        editexeltitile.setStyle("-fx-background-color: #A9A9A9");
        addbtn.setStyle("-fx-background-color: #A9A9A9");
        updatebtn.setStyle("-fx-background-color: #A9A9A9");
        deletebtn.setStyle("-fx-background-color: #A9A9A9");
        clearbtn.setStyle("-fx-background-color: #A9A9A9");
        exportexelbtn.setStyle("-fx-background-color: #A9A9A9");
        deletetable.setStyle("-fx-background-color: #A9A9A9");

        addbtn.setDisable(true);
        updatebtn.setDisable(true);





        //date.setIconName("CALENDAR");
        //shoppingsum.setIconName("SHEKEL");
       // itemsboghted.setIconName("SHOPPING_CART");
        //receivicon.setIconName("RECEIPT");
       // shoppingsum1.setIconName("SHEKEL");
       // shoppingsum2.setIconName("SHEKEL");


        ObservableList<ModelTable> oblist = FXCollections.observableArrayList();
        try {
            Connection connection = getConnection();
            ResultSet rs = connection.createStatement().executeQuery("select mainbudget from managment where emp_id >0 limit 1");
            while (rs.next()) {

                 sum =(rs.getFloat(1));

                 mainsum.setText(sum + "");

            }

            if (sum!=0){
                datefield.setDisable(false);
                amountfield.setDisable(false);
                detailsarea.setDisable(false);
                receivefield.setDisable(false);

            }else if (sum==0){
                datefield.setDisable(true);
                amountfield.setDisable(true);
                detailsarea.setDisable(true);
                receivefield.setDisable(true);
            }

            setTotalspaend ();
            setLeftmainsum();


        } catch (SQLException Ex) {
            Ex.printStackTrace();
            //Logger.getLogger(Controller.class.getName().log(Level.SEVERE,null,Ex));

        }
//////////////////////////////////////////////////////////////////////////////////////////////

        try {
            Connection connection = getConnection();
            ResultSet rs = connection.createStatement().executeQuery("select sheetname from managment where emp_id >0 limit 1");
            while (rs.next()) {

                String text= rs.getString(1);

                textexeltitile.setText(text + "");

            }




        } catch (SQLException Ex) {
            Ex.printStackTrace();
            //Logger.getLogger(Controller.class.getName().log(Level.SEVERE,null,Ex));

        }

/////////////////////////////////////////////////////////////////

        try {
            stp = getConnection().prepareStatement("select filepath from managment where emp_id>0 limit 1");


            ResultSet rs = stp.executeQuery();

            while (rs.next()) {


                    path = rs.getString(1);
                    // textArea.setText(path + "");

                }





            stp.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }




        confirmainsum.setDisable(true);
        editmainsum.setDisable(false);
        mainsum.setEditable(false);
        mainsum.setOpacity(0.5);

////////////////////////////////////////

        confirmexeltitile.setDisable(true);
        editexeltitile.setDisable(false);
        textexeltitile.setEditable(false);
        textexeltitile.setOpacity(0.7);


        try {
            Connection connection = getConnection();
            ResultSet rs = connection.createStatement().executeQuery("select * from managment");
            while (rs.next()) {
                oblist.add(new ModelTable(rs.getString("date"), rs.getFloat("amount"), rs.getString("details"), rs.getString("receiv"), rs.getInt("emp_id"), rs.getFloat("mainbudget"),rs.getString("filepath"),rs.getString("sheetname")));


            }

            setTotalspaend();

        } catch (SQLException Ex) {
            Ex.printStackTrace();
            //Logger.getLogger(Controller.class.getName().log(Level.SEVERE,null,Ex));

        }


        col_date.setCellValueFactory(new PropertyValueFactory<>("date"));
        col_amount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        col_details.setCellValueFactory(new PropertyValueFactory<>("details"));
        col_receivenum.setCellValueFactory(new PropertyValueFactory<>("receiv"));

        tableView.setItems(oblist);
        //Count_Table();
        fromtabletotextfield();


        //oblist.clear();


           addbtn.setOnMouseEntered(event -> {

               addbtn.setStyle("-fx-background-color: #42a5f5");
           });
           addbtn.setOnMouseExited(event -> {

               addbtn.setStyle("-fx-background-color: #A9A9A9");
           });

/////////////////////////////////////////////////////////

        updatebtn.setOnMouseEntered(event -> {

            updatebtn.setStyle("-fx-background-color: #42a5f5");
        });
        updatebtn.setOnMouseExited(event -> {

            updatebtn.setStyle("-fx-background-color: #A9A9A9");
        });


///////////////////////////////////////////////////////////

        deletebtn.setOnMouseEntered(event -> {

            deletebtn.setStyle("-fx-background-color: #42a5f5");

        });
        deletebtn.setOnMouseExited(event -> {

            deletebtn.setStyle("-fx-background-color: #A9A9A9");
        });
/////////////////////////////////////////////////////////////

        clearbtn.setOnMouseEntered(event -> {

            clearbtn.setStyle("-fx-background-color: #42a5f5");

        });
        clearbtn.setOnMouseExited(event -> {

            clearbtn.setStyle("-fx-background-color: #A9A9A9");
        });
////////////////////////////////////////////////////////////////
        exportexelbtn.setOnMouseEntered(event -> {

            exportexelbtn.setStyle("-fx-background-color: #42a5f5");

        });
        exportexelbtn.setOnMouseExited(event -> {

            exportexelbtn.setStyle("-fx-background-color: #A9A9A9");
        });
//////////////////////////////////////////////////////////////////
        deletetable.setOnMouseEntered(event -> {

            deletetable.setStyle("-fx-background-color: #42a5f5");

        });
        deletetable.setOnMouseExited(event -> {

            deletetable.setStyle("-fx-background-color: #A9A9A9");
        });
////////////////////////////////////////////////////////////////

        confirmexeltitile.setOnMouseEntered(event -> {

            confirmexeltitile.setStyle("-fx-background-color: #42a5f5");

        });
        confirmexeltitile.setOnMouseExited(event -> {

            confirmexeltitile.setStyle("-fx-background-color: #A9A9A9");
        });

 ////////////////////////////////////////////////////////////////////////////////

        editexeltitile.setOnMouseEntered(event -> {

            editexeltitile.setStyle("-fx-background-color: #42a5f5");

        });
        editexeltitile.setOnMouseExited(event -> {

            editexeltitile.setStyle("-fx-background-color: #A9A9A9");
        });

//////////////////////////////////////////////////////////////////////////////


        hyperlink.setOnMouseEntered(event -> {

            hyperlink.setStyle("-fx-background-color: #42a5f5");

        });
        hyperlink.setOnMouseExited(event -> {

            hyperlink.setTextFill(Paint.valueOf("#ffffff"));

            hyperlink.setStyle("-fx-background-color: #0");
        });
/////////////////////////////////////////////////////////////////////////////////////
        showpanemail.setOnMouseEntered(event -> {

            showpanemail.setStyle("-fx-background-color: #42a5f5");

        });
        showpanemail.setOnMouseExited(event -> {

            showpanemail.setStyle("-fx-background-color: #A9A9A9");
        });














        myemail.setOnKeyPressed(event -> {

             KeyCode keyCode = event.getCode();

             if (event.getCode() == KeyCode.ENTER && !myemail.getText().contains("@gmail.com")){

                 myemail.setText(myemail.getText() + "@gmail.com");

             }else if (myemail.getText().contains("@gmail.com")){

             }

         });



        someonemail.setOnKeyPressed(event -> {

            KeyCode keyCode = event.getCode();

            if (event.getCode() == KeyCode.ENTER && !someonemail.getText().contains("@gmail.com")){

                someonemail.setText(someonemail.getText() + "@gmail.com");

            }else if(someonemail.getText().contains("@gmail.com")){

            }
        });





        hyperlink.setOnAction(event -> {

                 try {
                     Desktop.getDesktop().browse(new URI("https://myaccount.google.com/lesssecureapps"));
                 } catch (IOException | URISyntaxException e) {
                     e.printStackTrace();
                 }

             });


           confirmexeltitile.setOnAction(event -> {

               textexeltitile.setEditable(false);
               textexeltitile.setOpacity(0.7);
               confirmtoexel = textexeltitile.getText();


               Connection connection = getConnection();


               String query = "update managment set sheetname='" + textexeltitile.getText() + "' where emp_id>0";

               PreparedStatement stp;
               Statement st;
               int rs;
               try {

                   stp = connection.prepareStatement(query);
                   rs = stp.executeUpdate();

               } catch (SQLException e){
                   e.printStackTrace();
               }



           });


           showpanemail.setOnMouseClicked(event -> {
               count++;

               if (count%2==1){

                   showpanemail.setText("Hide Email Sender");
                   emailveiw.setVisible(true);
                   Scene scene = showpanemail.getScene();

                   emailveiw.translateYProperty().set(scene.getHeight());

                   Timeline timeline = new Timeline();

                   KeyValue keyValue = new KeyValue(emailveiw.translateYProperty(),0, Interpolator.EASE_IN);
                   KeyFrame keyFrame = new KeyFrame(Duration.seconds(0.8),keyValue);
                   timeline.getKeyFrames().add(keyFrame);
                   timeline.play();

               }else if(count%2==0){

                   showpanemail.setText("Show Email Sender");
                   Scene scene = showpanemail.getScene();

                   emailveiw.translateYProperty().set(scene.getHeight());

                   Timeline timeline = new Timeline();
                   emailveiw.setTranslateY(0);
                   KeyValue keyValue = new KeyValue(emailveiw.translateYProperty(),800, Interpolator.EASE_IN);
                   KeyFrame keyFrame = new KeyFrame(Duration.seconds(0.5),keyValue);
                   timeline.getKeyFrames().add(keyFrame);

                   timeline.play();

                  // emailveiw.setVisible(false);
               }




           });













            editexeltitile.setOnAction(event -> {
                textexeltitile.setEditable(true);
                textexeltitile.setOpacity(1);


            });




        confirmainsum.setOnAction(event -> {

            mainsum.setOpacity(0.5);
            editmainsum.setDisable(false);
            mainsum.setEditable(false);

            datefield.setDisable(false);
            amountfield.setDisable(false);
            detailsarea.setDisable(false);
            receivefield.setDisable(false);
            addbtn.setDisable(true);
            updatebtn.setDisable(true);
            Connection connection = getConnection();


            String query = "update managment set mainbudget='" + mainsum.getText() + "' where emp_id>0";

            PreparedStatement stp;
            Statement st;
            int rs;
            try {

                stp = connection.prepareStatement(query);





                rs = stp.executeUpdate();



                setTotalspaend();
                setLeftmainsum();
            } catch (SQLException e){

                mainsum.setText("");
                e.printStackTrace();
            }

            Update_table();

        });


        datePicker.setOnAction(event -> {
            DateFormat dp = new SimpleDateFormat("dd/MM/yyyy");
            Date dpp = Date.valueOf(datePicker.getValue());
            datefield.setText(dp.format(dpp));


        });



        editmainsum.setOnAction(event -> {
            mainsum.setEditable(true);
            mainsum.setOpacity(1);
            addbtn.setDisable(true);
            updatebtn.setDisable(true);
        });











       exportexelbtn.setOnAction(event -> {

           try {
               Connection connection = getConnection();
               String query = "select * from managment";
               ResultSet rs;

               rs = connection.prepareStatement(query).executeQuery();


               XSSFWorkbook workbook = new XSSFWorkbook();
               XSSFSheet sheet = workbook.createSheet(textexeltitile.getText() + "");

               XSSFCellStyle styletitle = workbook.createCellStyle();
               Font fonttitle = workbook.createFont();
               fonttitle.setFontHeightInPoints((short)14);
               fonttitle.setFontName(HSSFFont.FONT_ARIAL);
               fonttitle.setUnderline(Font.U_DOUBLE);
               styletitle.setAlignment(CENTER);
               styletitle.setFont(fonttitle);

               XSSFRow header5 = sheet.createRow(1);
               header5.createCell(1).setCellValue(textexeltitile.getText() + "");
               sheet.addMergedRegion(new CellRangeAddress(1,1,1,2));

               XSSFRow rowtitle = sheet.getRow(1);
               rowtitle.getCell(1).setCellStyle(styletitle);






               ///////////////////////////////////////////////////////////////////////////////////////////////////


               XSSFCellStyle style2 = workbook.createCellStyle();
               Font font2 = workbook.createFont();
               font2.setBold(true);
               font2.setFontName(HSSFFont.FONT_ARIAL);
               style2.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
               style2.setFillPattern(FillPatternType.SOLID_FOREGROUND);
               style2.setAlignment(CENTER);
               style2.setFont(font2);

               XSSFCellStyle style22 = workbook.createCellStyle();
               style22.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
               style22.setFillPattern(FillPatternType.SOLID_FOREGROUND);
               style22.setAlignment(CENTER);



               XSSFRow header1 = sheet.createRow(2);
               header1.createCell(0).setCellValue("Budget:");
               header1.createCell(1).setCellValue(mainsum.getText() + "");

               XSSFRow row2 = sheet.getRow(2);
               row2.getCell(0).setCellStyle(style2);
               row2.getCell(1).setCellStyle(style22);

//////////////////////////////////////////////////////////////////////////////////////////////
               XSSFCellStyle style3 = workbook.createCellStyle();
               Font font3 = workbook.createFont();
               font3.setBold(true);
               font3.setFontName(HSSFFont.FONT_ARIAL);
               style3.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
               style3.setFillPattern(FillPatternType.SOLID_FOREGROUND);
               style3.setAlignment(CENTER);
               style3.setFont(font3);

               XSSFCellStyle style33 = workbook.createCellStyle();
               style33.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
               style33.setFillPattern(FillPatternType.SOLID_FOREGROUND);
               style33.setAlignment(CENTER);



               XSSFRow header2 = sheet.createRow(3);
               header2.createCell(0).setCellValue("Total Spending:");
               header2.createCell(1).setCellValue( totalspaend.getText()+ "");


               XSSFRow row3 = sheet.getRow(3);
               row3.getCell(0).setCellStyle(style3);
               row3.getCell(1).setCellStyle(style33);
            /////////////////////////////////////////////////////////////////////////////////////
               XSSFCellStyle style4 = workbook.createCellStyle();
               Font font4 = workbook.createFont();
               font4.setBold(true);
               font4.setFontName(HSSFFont.FONT_ARIAL);
               style4.setFillForegroundColor(IndexedColors.ORANGE.getIndex());
               style4.setFillPattern(FillPatternType.SOLID_FOREGROUND);
               style4.setAlignment(CENTER);
               style4.setFont(font4);

               XSSFCellStyle style44 = workbook.createCellStyle();
               style44.setFillForegroundColor(IndexedColors.ORANGE.getIndex());
               style44.setFillPattern(FillPatternType.SOLID_FOREGROUND);
               style44.setAlignment(CENTER);



               XSSFRow header3 = sheet.createRow(4);
               header3.createCell(0).setCellValue("Balance:");
               header3.createCell(1).setCellValue( leftmainsum.getText()+ "");

               XSSFRow row4 = sheet.getRow(4);
               row4.getCell(0).setCellStyle(style4);
               row4.getCell(1).setCellStyle(style44);

               //////////////////////////////////////////////////////////////////////////////
               XSSFCellStyle style6 = workbook.createCellStyle();
               Font font6 = workbook.createFont();
               font6.setBold(true);
               font6.setFontName(HSSFFont.FONT_ARIAL);
               font6.setColor(IndexedColors.WHITE.getIndex());
               style6.setFillForegroundColor(IndexedColors.BLUE.getIndex());
               style6.setFillPattern(FillPatternType.SOLID_FOREGROUND);
               style6.setAlignment(CENTER);
               style6.setFont(font6);



               XSSFRow header4 = sheet.createRow(6);
               header4.createCell(0).setCellValue("Spending Date");
               header4.createCell(1).setCellValue("Spending");
               header4.createCell(2).setCellValue("Item Name");
               header4.createCell(3).setCellValue("Receipt Number");

               XSSFRow row6 = sheet.getRow(6);
               row6.getCell(0).setCellStyle(style6);
               row6.getCell(1).setCellStyle(style6);
               row6.getCell(2).setCellStyle(style6);
               row6.getCell(3).setCellStyle(style6);
/////////////////////////////////////////////////////////////////////////////////////////////
               XSSFCellStyle styleindex = workbook.createCellStyle();
               Font fontindex = workbook.createFont();
               //fontindex.setBold(true);
               fontindex.setFontName(HSSFFont.FONT_ARIAL);
               //fontindex.setColor(IndexedColors.WHITE.getIndex());
               styleindex.setFillForegroundColor(IndexedColors.SKY_BLUE.getIndex());
               styleindex.setFillPattern(FillPatternType.SOLID_FOREGROUND);
               styleindex.setAlignment(CENTER);
               styleindex.setFont(fontindex);

               XSSFCellStyle styleindex1 = workbook.createCellStyle();
               Font fontindex1 = workbook.createFont();
               //fontindex1.setBold(true);
               fontindex1.setFontName(HSSFFont.FONT_ARIAL);
               //fontindex1.setColor(IndexedColors.WHITE.getIndex());
               styleindex1.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
               styleindex1.setFillPattern(FillPatternType.SOLID_FOREGROUND);
               styleindex1.setAlignment(CENTER);
               styleindex1.setFont(fontindex1);


               int index = 7;
               while (rs.next()){

                   if (index%2==1) {
                       XSSFRow row = sheet.createRow(index);

                       row.createCell(0).setCellValue(rs.getString("date"));
                       row.createCell(1).setCellValue(rs.getFloat("amount"));
                       row.createCell(2).setCellValue(rs.getString("details"));
                       row.createCell(3).setCellValue(rs.getString("receiv"));


                       XSSFRow rowindex = sheet.getRow(index);
                       rowindex.getCell(0).setCellStyle(styleindex);
                       rowindex.getCell(1).setCellStyle(styleindex);
                       rowindex.getCell(2).setCellStyle(styleindex);
                       rowindex.getCell(3).setCellStyle(styleindex);

                       index++;

                   }else if(index%2==0){
                       XSSFRow row = sheet.createRow(index);

                       row.createCell(0).setCellValue(rs.getString("date"));
                       row.createCell(1).setCellValue(rs.getFloat("amount"));
                       row.createCell(2).setCellValue(rs.getString("details"));
                       row.createCell(3).setCellValue(rs.getString("receiv"));


                       XSSFRow rowindex = sheet.getRow(index);
                       rowindex.getCell(0).setCellStyle(styleindex1);
                       rowindex.getCell(1).setCellStyle(styleindex1);
                       rowindex.getCell(2).setCellStyle(styleindex1);
                       rowindex.getCell(3).setCellStyle(styleindex1);


                       index++;
                   }

               }



               sheet.autoSizeColumn(0);
               sheet.autoSizeColumn(1);
               sheet.autoSizeColumn(2);
               sheet.autoSizeColumn(3);
               FileChooser fileChooser = new FileChooser();
               fileChooser.getExtensionFilters().addAll(

                       new FileChooser.ExtensionFilter("XLSX files(*.xlsx)","*.xlsx")
               );

               savefile = fileChooser.showSaveDialog(tableView.getScene().getWindow());

               FileOutputStream fileOutputStream = new FileOutputStream(" '"+textexeltitile.getText()+"'.xlsx");

               FileWriter F = new FileWriter(savefile);

               workbook.write(fileOutputStream);
              fileOutputStream = new FileOutputStream(savefile.getAbsolutePath() + "");
               workbook.write(fileOutputStream);

              fileOutputStream.close();







           } catch (SQLException | IOException Ex) {
               Ex.printStackTrace();
               //Logger.getLogger(Controller.class.getName().log(Level.SEVERE,null,Ex));

           }





       });


    }


    private void Update_table () {
            ObservableList<ModelTable> oblist = FXCollections.observableArrayList();

            oblist.clear();
            try {
                Connection connection = getConnection();
                ResultSet rs = connection.createStatement().executeQuery("select * from managment");
                while (rs.next()) {
                    oblist.add(new ModelTable(rs.getString("date"), rs.getFloat("amount"), rs.getString("details"), rs.getString("receiv"), rs.getInt("emp_id"), rs.getFloat("mainbudget"),rs.getString("filepath"),rs.getString("sheetname")));
                }
            } catch (SQLException Ex) {
                Ex.printStackTrace();
                //Logger.getLogger(Controller.class.getName().log(Level.SEVERE,null,Ex));

            }

            tableView.setItems(oblist);
            // Count_Table();
        }


        private void fromtabletotextfield () {
            tableView.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override

                public void handle(MouseEvent event) {
                    int mod = 2;
                    if (event.getClickCount() == 2) {
                        ModelTable modelTable = tableView.getItems().get(tableView.getSelectionModel().getSelectedIndex());
                        datefield.setText(modelTable.getDate());
                        amountfield.setText(String.valueOf(modelTable.getAmount()));
                        detailsarea.setText(modelTable.getDetails());
                        receivefield.setText(modelTable.getReceiv());


                        updatebtn.setDisable(false);
                        addbtn.setDisable(false);


                        // showimage(modelTable.getEmp_id());
                        // showfilepath(modelTable.getEmp_id());


                        //mod++;
                    } else if (event.getClickCount() == 1) {
                        // ObservableList<ModelTable> Model = tableView.getSelectionModel().getSelectedItems();

                        ModelTable modelTable = tableView.getItems().get(tableView.getSelectionModel().getSelectedIndex());

                        // showimage(modelTable.getEmp_id());
                        // showfilepath(modelTable.getEmp_id());


                        updatebtn.setDisable(true);
                        addbtn.setDisable(true);

                        datefield.setText("");
                        amountfield.setText("");

                        detailsarea.setText("");
                        receivefield.setText("");
                    }
                }

            });
        }


        private void setTotalspaend () {

            ObservableList<ModelTable> oblist = FXCollections.observableArrayList();
            //oblist.clear();
            try {
                Connection connection = getConnection();
                ResultSet rs = connection.createStatement().executeQuery("select sum(amount) from managment");

                String num;
                while (rs.next()) {
                    numtotalspend = (rs.getFloat(1));

                    totalspaend.setText(numtotalspend + "");


                }
            } catch (SQLException Ex) {
                Ex.printStackTrace();

            }


        }


        private void setLeftmainsum(){

        float sum = Float.parseFloat(mainsum.getText());
        float left = Math.abs(sum - numtotalspend);


        leftmainsum.setText(left + "");


            if (sum < numtotalspend){

            leftmainsum.setText("value cant be bigger/<0 then " + sum);
            totalspaend.setText("value cant be bigger then " + sum);



            }



        }

    public void count(){
        try {
            Connection connection = getConnection();
            ResultSet rs = connection.createStatement().executeQuery("select count(emp_id) AS count FROM managment");


            while (rs.next()) {
                num = Integer.valueOf(rs.getString(1));
                num = num - 1;
                // count.setText("Number Of Users: "+num);
            }
            rs.close();
        } catch (SQLException Ex) {
            Ex.printStackTrace();
            //Logger.getLogger(Controller.class.getName().log(Level.SEVERE,null,Ex));

        }

    }




    @FXML
    private void setFnamev(KeyEvent event){

        if (datefield.getText().isEmpty()||amountfield.getText().isEmpty()||detailsarea.getText().isEmpty()||receivefield.getText().isEmpty()){
            addbtn.setDisable(true);
        }else {
            addbtn.setDisable(false);
        }

    }




@FXML
    public void setSendEmail(ActionEvent event){

    try {
        Connection connection = getConnection();
        String query = "select * from managment";
        ResultSet rs;

        rs = connection.prepareStatement(query).executeQuery();


        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet(textexeltitile.getText() + "");

        XSSFCellStyle styletitle = workbook.createCellStyle();
        Font fonttitle = workbook.createFont();
        fonttitle.setFontHeightInPoints((short)14);
        fonttitle.setFontName(HSSFFont.FONT_ARIAL);
        fonttitle.setUnderline(Font.U_DOUBLE);
        styletitle.setAlignment(CENTER);
        styletitle.setFont(fonttitle);

        XSSFRow header5 = sheet.createRow(1);
        header5.createCell(1).setCellValue(textexeltitile.getText() + "");
        sheet.addMergedRegion(new CellRangeAddress(1,1,1,2));

        XSSFRow rowtitle = sheet.getRow(1);
        rowtitle.getCell(1).setCellStyle(styletitle);






        ///////////////////////////////////////////////////////////////////////////////////////////////////


        XSSFCellStyle style2 = workbook.createCellStyle();
        Font font2 = workbook.createFont();
        font2.setBold(true);
        font2.setFontName(HSSFFont.FONT_ARIAL);
        style2.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
        style2.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style2.setAlignment(CENTER);
        style2.setFont(font2);

        XSSFCellStyle style22 = workbook.createCellStyle();
        style22.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
        style22.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style22.setAlignment(CENTER);



        XSSFRow header1 = sheet.createRow(2);
        header1.createCell(0).setCellValue("Budget:");
        header1.createCell(1).setCellValue(mainsum.getText() + "");

        XSSFRow row2 = sheet.getRow(2);
        row2.getCell(0).setCellStyle(style2);
        row2.getCell(1).setCellStyle(style22);

//////////////////////////////////////////////////////////////////////////////////////////////
        XSSFCellStyle style3 = workbook.createCellStyle();
        Font font3 = workbook.createFont();
        font3.setBold(true);
        font3.setFontName(HSSFFont.FONT_ARIAL);
        style3.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
        style3.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style3.setAlignment(CENTER);
        style3.setFont(font3);

        XSSFCellStyle style33 = workbook.createCellStyle();
        style33.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
        style33.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style33.setAlignment(CENTER);



        XSSFRow header2 = sheet.createRow(3);
        header2.createCell(0).setCellValue("Total Spending:");
        header2.createCell(1).setCellValue( totalspaend.getText()+ "");


        XSSFRow row3 = sheet.getRow(3);
        row3.getCell(0).setCellStyle(style3);
        row3.getCell(1).setCellStyle(style33);
        /////////////////////////////////////////////////////////////////////////////////////
        XSSFCellStyle style4 = workbook.createCellStyle();
        Font font4 = workbook.createFont();
        font4.setBold(true);
        font4.setFontName(HSSFFont.FONT_ARIAL);
        style4.setFillForegroundColor(IndexedColors.ORANGE.getIndex());
        style4.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style4.setAlignment(CENTER);
        style4.setFont(font4);

        XSSFCellStyle style44 = workbook.createCellStyle();
        style44.setFillForegroundColor(IndexedColors.ORANGE.getIndex());
        style44.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style44.setAlignment(CENTER);



        XSSFRow header3 = sheet.createRow(4);
        header3.createCell(0).setCellValue("Balance:");
        header3.createCell(1).setCellValue( leftmainsum.getText()+ "");

        XSSFRow row4 = sheet.getRow(4);
        row4.getCell(0).setCellStyle(style4);
        row4.getCell(1).setCellStyle(style44);

        //////////////////////////////////////////////////////////////////////////////
        XSSFCellStyle style6 = workbook.createCellStyle();
        Font font6 = workbook.createFont();
        font6.setBold(true);
        font6.setFontName(HSSFFont.FONT_ARIAL);
        font6.setColor(IndexedColors.WHITE.getIndex());
        style6.setFillForegroundColor(IndexedColors.BLUE.getIndex());
        style6.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style6.setAlignment(CENTER);
        style6.setFont(font6);



        XSSFRow header4 = sheet.createRow(6);
        header4.createCell(0).setCellValue("Spending Date");
        header4.createCell(1).setCellValue("Spending");
        header4.createCell(2).setCellValue("Item Name");
        header4.createCell(3).setCellValue("Receipt Number");

        XSSFRow row6 = sheet.getRow(6);
        row6.getCell(0).setCellStyle(style6);
        row6.getCell(1).setCellStyle(style6);
        row6.getCell(2).setCellStyle(style6);
        row6.getCell(3).setCellStyle(style6);
/////////////////////////////////////////////////////////////////////////////////////////////
        XSSFCellStyle styleindex = workbook.createCellStyle();
        Font fontindex = workbook.createFont();
        //fontindex.setBold(true);
        fontindex.setFontName(HSSFFont.FONT_ARIAL);
        //fontindex.setColor(IndexedColors.WHITE.getIndex());
        styleindex.setFillForegroundColor(IndexedColors.SKY_BLUE.getIndex());
        styleindex.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        styleindex.setAlignment(CENTER);
        styleindex.setFont(fontindex);

        XSSFCellStyle styleindex1 = workbook.createCellStyle();
        Font fontindex1 = workbook.createFont();
        //fontindex1.setBold(true);
        fontindex1.setFontName(HSSFFont.FONT_ARIAL);
        //fontindex1.setColor(IndexedColors.WHITE.getIndex());
        styleindex1.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
        styleindex1.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        styleindex1.setAlignment(CENTER);
        styleindex1.setFont(fontindex1);


        int index = 7;
        while (rs.next()){

            if (index%2==1) {
                XSSFRow row = sheet.createRow(index);

                row.createCell(0).setCellValue(rs.getString("date"));
                row.createCell(1).setCellValue(rs.getFloat("amount"));
                row.createCell(2).setCellValue(rs.getString("details"));
                row.createCell(3).setCellValue(rs.getString("receiv"));


                XSSFRow rowindex = sheet.getRow(index);
                rowindex.getCell(0).setCellStyle(styleindex);
                rowindex.getCell(1).setCellStyle(styleindex);
                rowindex.getCell(2).setCellStyle(styleindex);
                rowindex.getCell(3).setCellStyle(styleindex);

                index++;

            }else if(index%2==0){
                XSSFRow row = sheet.createRow(index);

                row.createCell(0).setCellValue(rs.getString("date"));
                row.createCell(1).setCellValue(rs.getFloat("amount"));
                row.createCell(2).setCellValue(rs.getString("details"));
                row.createCell(3).setCellValue(rs.getString("receiv"));


                XSSFRow rowindex = sheet.getRow(index);
                rowindex.getCell(0).setCellStyle(styleindex1);
                rowindex.getCell(1).setCellStyle(styleindex1);
                rowindex.getCell(2).setCellStyle(styleindex1);
                rowindex.getCell(3).setCellStyle(styleindex1);


                index++;
            }

        }



        sheet.autoSizeColumn(0);
        sheet.autoSizeColumn(1);
        sheet.autoSizeColumn(2);
        sheet.autoSizeColumn(3);


        FileOutputStream fileOutputStream1 = new FileOutputStream(path + "");


        workbook.write(fileOutputStream1);






    } catch (SQLException | IOException Ex) {
        Ex.printStackTrace();
        //Logger.getLogger(Controller.class.getName().log(Level.SEVERE,null,Ex));

    }
///////////////////////////////////////////////////////////////////////////






  //////////////////////////////////////////////////////////////////////////////////////



       // String to = "murdehai60@gmail.com";

       // String from = "yuvalbentzur1996@gmail.com";

    Properties properties = new Properties();
        properties.put("mail.smtp.socketFactory.fallback","false");
        properties.put("mail.smtp.debug","true");
        properties.put("mail.smtp.user",myemail.getText() + "");
    properties.put("mail.smtp.host","smtp.gmail.com");
    properties.put("mail.smtp.socketFactory.port","465");
    properties.put("mail.smtp.socketFactory.class",
            "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.auth","true");
    properties.put("mail.smtp.port","465");


    Session session = Session.getInstance(properties,new javax.mail.Authenticator() {

     protected PasswordAuthentication getPasswordAuthentication(){
         return new PasswordAuthentication(myemail.getText() + "",mypass.getText() + "");
     }


    });
    try{

        MimeMessage message = new MimeMessage(session);

        message.setFrom(new InternetAddress(myemail.getText() + ""));
        message.addRecipients(Message.RecipientType.TO,InternetAddress.parse(someonemail.getText() + ""));
        message.setSubject(subjectemail.getText() + "");
        BodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setText(infoemail.getText() + "");
        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);
        messageBodyPart = new MimeBodyPart();

        if (path==null){
            DataSource dataSource = new FileDataSource(savefile.getAbsolutePath() + "");
            messageBodyPart.setDataHandler(new DataHandler(dataSource));

        }
        DataSource dataSource1 = new FileDataSource(path + "");
        messageBodyPart.setDataHandler(new DataHandler(dataSource1));


       // ByteArrayOutputStream email = new ByteArrayOutputStream();

       // message.writeTo(emailoutputstream);
        //DataSource dataSource = (DataSource) new ByteArrayInputStream(emailoutputstream.toByteArray());



        messageBodyPart.setFileName("Budget_Report.xlsx");
        multipart.addBodyPart(messageBodyPart);
        message.setContent(multipart);
        Transport.send(message);
        System.out.println("sent message successfully!!!");

    } catch (MessagingException e) {
        e.printStackTrace();
    }

}

}











