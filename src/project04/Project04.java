/** *****************************************************************************
 * Program Name:           Project 04
 * Program Description:       This JFX Application creates a payroll GUI that allows the user to CRUD
 *                    Employees' information based on their employee number and job level.
 *
 *
 *Program Author:           Juan Valencia
 *Date Created:            12/12/2018
 ***************************************************************************** */
package project04;

//Imports
import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author JuanValencia
 */
public class Project04 extends Application {

  //Class variables
  private Label lblEmpnoInput = new Label("Employee Number: ");
  private Label lblPenetration = new Label("Penetration %: ");
  private Label lblEmpnoResult = new Label("Employee Number: ");
  private Label lblLastName = new Label("Last Name: ");
  private Label lblFirstName = new Label("First Name: ");
  private Label lblMI = new Label("M.I.: ");
  private Label lblState = new Label("State: ");
  private Label lblDept = new Label("Department: ");
  private Label lblJobLvl = new Label("Job Level: ");
  private Label lblSalary = new Label("Salary: ");
  private Label lblSalaryMin = new Label("Salary Minimum: ");
  private Label lblSalaryMax = new Label("Salary Maximum: ");
  private Label lblMessage = new Label("");

  private TextField txtEmpnoInput = new TextField("");
  private TextField txtPenetration = new TextField("");
  private TextField txtEmpnoResult = new TextField("");
  private TextField txtLastName = new TextField("");
  private TextField txtFirstName = new TextField("");
  private TextField txtMI = new TextField("");
  private TextField txtState = new TextField("");
  private TextField txtDept = new TextField("");
  private TextField txtJobLvl = new TextField("");
  private TextField txtSalary = new TextField("");
  private TextField txtSalaryMin = new TextField("");
  private TextField txtSalaryMax = new TextField("");

  private Button btnCreate = new Button("Create");
  private Button btnRead = new Button("Read");
  private Button btnUpdate = new Button("Update");
  private Button btnDelete = new Button("Delete");
  private Button btnClear = new Button("Clear");
  private Button btnExit = new Button("Exit");

  private Statement statement;
  Stage primaryStage;

//GUI
  @Override
  public void start(Stage primaryStage) {
    primaryStage.setTitle("Project04");
    GridPane grid = new GridPane();
    grid.setAlignment(Pos.CENTER);
    grid.setHgap(10);
    grid.setVgap(10);
    grid.setPadding(new Insets(25, 25, 25, 25));
    Text sceneTitle = new Text("Payroll Administration");
    sceneTitle.setId("welcome-text");
    grid.add(sceneTitle, 0, 0, 2, 1);

    ImageView selectedImage = new ImageView();
    Image image1 = new Image(Project04.class.getResourceAsStream("payrollimg.png"));
    selectedImage.setImage(image1);
    selectedImage.setFitHeight(100);
    selectedImage.setFitWidth(100);
    grid.add(selectedImage, 4, 0);

    //Load the databse into the GUI
    initializeDB();

    //Description labels
    grid.add(lblEmpnoInput, 0, 1);
    lblEmpnoInput.setPrefWidth(150);
    grid.add(lblEmpnoResult, 0, 3);
    lblEmpnoResult.setPrefWidth(150);
    grid.add(lblLastName, 0, 4);
    lblLastName.setPrefWidth(150);
    grid.add(lblFirstName, 0, 5);
    lblFirstName.setPrefWidth(150);
    grid.add(lblMI, 0, 6);
    lblMI.setPrefWidth(150);
    grid.add(lblState, 0, 7);
    lblState.setPrefWidth(150);
    grid.add(lblDept, 0, 8);
    lblDept.setPrefWidth(150);
    grid.add(lblJobLvl, 0, 9);
    lblJobLvl.setPrefWidth(150);
    grid.add(lblSalary, 0, 10);
    lblSalary.setPrefWidth(150);
    grid.add(lblSalaryMin, 0, 11);
    lblSalaryMin.setPrefWidth(150);
    grid.add(lblSalaryMax, 0, 12);
    lblSalaryMax.setPrefWidth(150);
    grid.add(lblMessage, 0, 15);
    lblMessage.setPrefWidth(250);
    lblMessage.setTextFill(Color.CRIMSON);

    //Employee number input field
    grid.add(txtEmpnoInput, 1, 1);
    txtEmpnoInput.setMaxWidth(50);

    //Top buttons row set up
    HBox btnHbox = new HBox(10);
    btnHbox.setPrefWidth(350);
    btnCreate.setPrefWidth(75);
    btnRead.setPrefWidth(75);
    btnUpdate.setPrefWidth(75);
    btnDelete.setPrefWidth(75);

    grid.add(btnHbox, 0, 2, 3, 1);
    btnHbox.getChildren().addAll(btnCreate);
    btnHbox.getChildren().addAll(btnRead);
    btnHbox.getChildren().addAll(btnUpdate);
    btnHbox.getChildren().addAll(btnDelete);

    //Textfields setup
    grid.add(txtEmpnoResult, 1, 3);
    txtEmpnoResult.setMaxWidth(50);
    grid.add(txtLastName, 1, 4);
    grid.add(txtFirstName, 1, 5);
    grid.add(txtMI, 1, 6);
    txtMI.setMaxWidth(50);
    grid.add(txtState, 1, 7);
    txtState.setMaxWidth(50);
    grid.add(txtDept, 1, 8);
    txtDept.setMaxWidth(50);
    grid.add(txtJobLvl, 1, 9);
    txtJobLvl.setMaxWidth(50);
    grid.add(txtSalary, 1, 10);
    grid.add(txtSalaryMin, 1, 11);
    grid.add(txtSalaryMax, 1, 12);

    txtEmpnoResult.setEditable(false);
    txtPenetration.setEditable(false);
    txtEmpnoResult.setStyle("-fx-background-color: cornsilk;");
    txtPenetration.setStyle("-fx-background-color: cornsilk;");

    //Penetration label and fields setup
    VBox vbPenCal = new VBox(15);
    vbPenCal.getChildren().addAll(lblPenetration);
    vbPenCal.getChildren().addAll(txtPenetration);
    grid.add(vbPenCal, 3, 2);
    vbPenCal.setPrefWidth(150);
    lblPenetration.setMinWidth(100);
    txtPenetration.setMaxWidth(60);

    //Clear & Exit button setup
    HBox btnHbox2 = new HBox(10);
    btnHbox2.getChildren().addAll(btnClear);
    btnHbox2.getChildren().addAll(btnExit);
    btnExit.setId("btnExit");

    btnHbox2.setPrefWidth(150);
    btnClear.setPrefWidth(75);
    btnExit.setPrefWidth(75);
    grid.add(btnHbox2, 2, 13);

    //Stage show
    primaryStage.show();
    Scene scene = new Scene(grid, 850, 700);
    primaryStage.setScene(scene);

    //Buttons action listeners
    btnRead.setOnAction(e -> read());
    btnCreate.setOnAction(e -> insert());
    btnUpdate.setOnAction(e -> update());
    btnDelete.setOnAction(e -> delete());
    btnClear.setOnAction(e -> clear());
    btnExit.setOnAction(e -> close());

    //Stylesheet link
    scene.getStylesheets().add(Project04.class.getResource("Style.css").toExternalForm());

  }

  //Initialize the Database
  private void initializeDB() {
    try {
      //Connect to the database
      Connection connect = DriverManager.getConnection("jdbc:derby://localhost:1527/Project04;create=true;user=nbuser;password=nbuser");
      System.out.println("Database connected\n");
      lblMessage.setText("Database connected");
      statement = connect.createStatement();
    } catch (SQLException ex) {
      lblMessage.setText("Connection failed: " + ex);
      ex.printStackTrace();

    }
  }

  //Read data from tables method
  private void read() {
    String query = "SELECT e.EMPNO, \n"
            + "e.LASTNAME, \n"
            + "e.FIRSTNAME, \n"
            + "e.MI, \n"
            + "e.STATE,\n"
            + "s.DEPT_NO, \n"
            + "s.SALARY_AMOUNT, \n"
            + "s.JOB_LEVEL, \n"
            + "sl.SALARY_MINIMUM, \n"
            + "sl.SALARY_MAXIMUM \n"
            + "FROM NBUSER.EMPLOYEE e, NBUSER.SALARY s, NBUSER.SALARY_LEVEL sl WHERE e.EMPNO = s.EMPNO"
            + " and s.JOB_LEVEL = sl.JOB_LEVEL and e.EMPNO =" + txtEmpnoInput.getText().trim();

    try {
      ResultSet rs = statement.executeQuery(query);
      loadToTextFields(rs);
    } catch (SQLException ex) {
      lblMessage.setText("Select Failed");
    }
  }

  //Load data into textfields method
  private void loadToTextFields(ResultSet rs) throws SQLException {

    DecimalFormat df = new DecimalFormat("###,###,##0.00");
    if (rs.next()) {

      String salary = rs.getString(7);
      String salaryMax = rs.getString(10);
      String salaryMin = rs.getString(9);

      txtEmpnoResult.setText(rs.getString(1));
      txtLastName.setText(rs.getString(2));
      txtFirstName.setText(rs.getString(3));
      txtMI.setText(rs.getString(4));
      txtState.setText(rs.getString(5));
      txtDept.setText(rs.getString(6));
      //Must set Salary to currency format
      txtSalary.setText("$" + df.format(Double.parseDouble(rs.getString(7))));
      txtJobLvl.setText(rs.getString(8));
      //Must set min & max salary to currency format
      txtSalaryMin.setText("$" + df.format(Double.parseDouble(rs.getString(9))));
      txtSalaryMax.setText("$" + df.format(Double.parseDouble(rs.getString(10))));
      lblMessage.setText("Record found");
      txtPenetration.setStyle("-fx-background-color: white;");
      txtPenetration.setEditable(false);
      txtEmpnoResult.setEditable(false);
      txtSalaryMin.setEditable(false);
      txtSalaryMax.setEditable(false);
      lblMessage.setText("Record Fetched.");

      double penetrationPct = (Double.parseDouble(salary) - (Double.parseDouble(salaryMin)))
              / (Double.parseDouble(salaryMax) - (Double.parseDouble(salaryMin))) * 100;
      int num = Integer.parseInt(String.valueOf(penetrationPct).split("\\.")[0]);
      txtPenetration.setText(num + "");
      if (penetrationPct >= 90 || penetrationPct <= 0) {
        txtPenetration.setStyle("-fx-background-color: red;");

      }
    } else {
      txtPenetration.setStyle("-fx-background-color: white;");
      lblMessage.setText("Record not found");

    }

  }

  //Insert data from the GUI method
  private void insert() {
    txtSalaryMin.setEditable(true);
    txtSalaryMax.setEditable(true);
    txtPenetration.setEditable(false);
    txtEmpnoResult.setEditable(false);
    String insertStmtEmp = "INSERT INTO Employee (EMPNO, LASTNAME, FIRSTNAME, MI, STATE)"
            + "VALUES (" + txtEmpnoInput.getText().trim() + ",'"
            + txtLastName.getText().trim() + "','"
            + txtFirstName.getText().trim() + "','"
            + txtMI.getText().trim() + "','"
            + txtState.getText().trim() + "')";
    String insertStmtSalary = "INSERT INTO Salary (EMPNO, DEPT_NO, JOB_LEVEL, SALARY_AMOUNT)"
            + "VALUES (" + txtEmpnoInput.getText().trim() + ",'"
            + txtDept.getText().trim() + "','"
            + txtJobLvl.getText().trim() + "',"
            + txtSalary.getText().trim().replace("$", "").replace(",", "") + ")";
    String insertStmtSalaryLvl = "INSERT INTO Salary_Level (JOB_LEVEL, SALARY_MINIMUM, SALARY_MAXIMUM)"
            + "VALUES (" + "'" + txtJobLvl.getText().trim() + "',"
            + txtSalaryMin.getText().trim().replace("$", "").replace(",", "") + ","
            + txtSalaryMax.getText().trim().replace("$", "").replace(",", "") + ")";

    try {
      System.out.println(insertStmtEmp);
      System.out.println(insertStmtSalary);
      System.out.println(insertStmtSalaryLvl);
      statement.executeUpdate(insertStmtEmp);
      statement.executeUpdate(insertStmtSalary);
      statement.executeUpdate(insertStmtSalaryLvl);
      lblMessage.setText("Record Inserted");

    } catch (SQLException ex) {
      lblMessage.setText("Insertion Failed");
      ex.printStackTrace();
    }

  }

  //Update data from the GUI method
  private void update() {
    System.out.println("Entering 2");
    txtSalaryMin.setEditable(true);
    txtSalaryMax.setEditable(true);
    txtPenetration.setEditable(false);
    txtEmpnoResult.setEditable(false);
    String updateEmployee = "UPDATE Employee "
            + "SET "
            + "LASTNAME = '" + txtLastName.getText().trim() + "',"
            + "FIRSTNAME = '" + txtFirstName.getText().trim() + "',"
            + "MI = '" + txtMI.getText().trim() + "',"
            + "STATE = '" + txtState.getText().trim() + "'"
            + " WHERE EMPNO = " + txtEmpnoInput.getText().trim() + "";
    String updateSalary = "UPDATE Salary "
            + "SET "
            + "DEPT_NO = '" + txtDept.getText().trim() + "',"
            + "JOB_LEVEL = '" + txtJobLvl.getText().trim() + "',"
            + "SALARY_AMOUNT = " + txtSalary.getText().trim().replace("$", "").replace(",", "") + ""
            + " WHERE EMPNO = " + txtEmpnoInput.getText().trim() + "";

    try {
      System.out.println(updateSalary);
      statement.executeUpdate(updateEmployee);
      statement.executeUpdate(updateSalary);
      lblMessage.setText("Record Updated");

    } catch (SQLException ex) {
      lblMessage.setText("Update failed");
      ex.printStackTrace();
    }
  }

  //Clear all fields method
  private void clear() {
    txtEmpnoInput.setText(null);
    txtEmpnoResult.setText(null);
    txtLastName.setText(null);
    txtFirstName.setText(null);
    txtMI.setText(null);
    txtState.setText(null);
    txtDept.setText(null);
    txtJobLvl.setText(null);
    txtSalary.setText(null);
    txtSalaryMin.setText(null);
    txtSalaryMax.setText(null);
    txtPenetration.setText(null);
    txtPenetration.setStyle("-fx-background-color: white;");
    txtSalaryMin.setEditable(true);
    txtSalaryMax.setEditable(true);
    txtPenetration.setEditable(false);
    txtEmpnoResult.setEditable(false);
    lblMessage.setText("Fields Cleared.");

  }

  //Delete a single record method
  private void delete() {
    String deleteEmp = "delete from  Employee where EMPNO = " + txtEmpnoInput.getText().trim();
    String deleteSalary = "delete from  Salary where EMPNO = " + txtEmpnoInput.getText().trim();
    String deleteSalaryLvl = "delete from  Salary_Level where Job_Level = '" + txtJobLvl.getText().trim() + "'";

    try {
      statement.executeUpdate(deleteEmp);
      statement.executeUpdate(deleteSalary);
      statement.executeUpdate(deleteSalaryLvl);

      statement.close();
      clear();
      lblMessage.setText("Record Deleted.");
    } catch (SQLException sqlExcept) {
      sqlExcept.printStackTrace();
      lblMessage.setText("Error Deleting Record.");

    }

  }

  //Close application method
  private void close() {
    Stage stage = (Stage) btnExit.getScene().getWindow();
    stage.close();
    System.out.println("Application Closed.");
  }

  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) {
    launch(args);
  }

}
