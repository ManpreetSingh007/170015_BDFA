package screening;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Userinterface {

	/**********************************************************************************************
	 * 
	 * Attributes
	 * 
	 **********************************************************************************************/

	/*
	 * Constants used to parameterize the graphical user interface. We do not use a
	 * layout manager for this application. Rather we manually control the location
	 * of each graphical element for exact control of the look and feel.
	 */
	private final double BUTTON_WIDTH = 60;
	private final double BUTTON_OFFSET = BUTTON_WIDTH / 2;

	// These are the application values required by the user interface
	private Label label_Doublemainline = new Label("Admin Function");

	String years[] = { "year1", "year2", "year3", "year4" }; // String for combo box

	ComboBox<String> combo_box = new ComboBox<String>(FXCollections.observableArrayList(years)); // combo box for
																									// student_master

	String semester[] = { "semester1", "semester2", "semester3", "semester4", "semester5", "semester6", "semester7",
			"semester8" }; // String for combo box1
	ComboBox<String> combo_box1 = new ComboBox<String>(FXCollections.observableArrayList(semester)); // combo box1 for
																										// student_master

	String student[] = { "170001", "170002", "170003", "170004", "170005", "170006", "170007", "170008", "170009",
			"170010", "170011", "170012", "170013", "170014", "170015", "170016", "170017", "170018", "170019",
			"170020", "170021", "170022", "170023", "170024", "170025", "170026", "170027", "170028", "170029",
			"170030", "170031", "170032", "170033", "170034", "170035", "170036", "170037", "170038", "170039",
			"170040", "170041", "170042", "170043", "170044", "170045", "170046", "170047", "170048", "170049",
			"170050", "170051", "170052", "170053", "1700054"

	}; // String for combo box1
	ComboBox<String> combo_box2 = new ComboBox<String>(FXCollections.observableArrayList(student)); // combo box1 for
																									// student_master

	String course[] = { "DBCS", "Big Data", };
	ComboBox<String> combo_box3 = new ComboBox<String>(FXCollections.observableArrayList(course));

	private Button Student_master = new Button("Enter Record For New Student");
	private Button ind_score = new Button(" Modify Existing Record");
	private Button Subject_master = new Button("Delete Student records");
	private Button Gradebook = new Button("Gradebook");
	private Button Reports = new Button("Display all existing student record");
	//private Button Res_generation = new Button("Result Generation");
	Label year = new Label("Year");
	Label students = new Label("Select the rollno from combobox below");
	Label sem = new Label("Semesters");
	Label courses = new Label("Courses");
	TextField DataSource1 = new TextField();
	String s;
	String y;
	Connection con;

	private double buttonSpace;

	public Userinterface(Pane theRoot, Stage Stage) {

		// There are five gaps. Compute the button space accordingly.
		buttonSpace = mainline.WINDOW_WIDTH / 5;

		// Label theScene with the name of the mainline, centered at the top of the pane
		setupLabelUI(label_Doublemainline, "Arial", 24, mainline.WINDOW_WIDTH, Pos.CENTER, 0, 10);

		// Button UI
		setupButtonUI(ind_score, "Symbol", 16, BUTTON_WIDTH, Pos.BASELINE_CENTER, 2.3 * buttonSpace - BUTTON_OFFSET,
				300);

		setupButtonUI(Student_master, "Symbol", 16, BUTTON_WIDTH, Pos.BASELINE_CENTER,
				2.3 * buttonSpace - BUTTON_OFFSET, 50);

		setupButtonUI(Subject_master, "Symbol", 16, BUTTON_WIDTH, Pos.BASELINE_LEFT, 2.3 * buttonSpace - BUTTON_OFFSET,
				100);

		setupButtonUI(Gradebook, "Symbol", 16, BUTTON_WIDTH + 75, Pos.BASELINE_LEFT, 2.3 * buttonSpace - BUTTON_OFFSET,
				150);

		setupButtonUI(Reports, "Symbol", 16, BUTTON_WIDTH + 75, Pos.BASELINE_LEFT, 2.3 * buttonSpace - BUTTON_OFFSET,
				200);

		//setupButtonUI(Res_generation, "Symbol", 16, BUTTON_WIDTH - 25, Pos.BASELINE_LEFT,
			//	2.3 * buttonSpace - BUTTON_OFFSET, 250);

		// set on actions

		Student_master.setOnAction((event) -> {
			try {
				studentmaster(Stage);
			} catch (FileNotFoundException e) {

				e.printStackTrace();
			}

		});

		Subject_master.setOnAction((event) -> {
			try {
				subjectmaster(Stage);
			} catch (FileNotFoundException e) {

				e.printStackTrace();
			}

		});

		Gradebook.setOnAction((event) -> {
			try {
				grade(Stage);
			} catch (FileNotFoundException e) {

				e.printStackTrace();
			}

		});
		Reports.setOnAction((event) -> {
			try {
				Report(Stage);
			} catch (FileNotFoundException e) {

				e.printStackTrace();
			}

		});

	//	Res_generation.setOnAction((event) -> {
	//		try {
	//			ReportGen(Stage);
	//		} catch (FileNotFoundException e) {
//
//				e.printStackTrace();
//			}

//		});

		ind_score.setOnAction((event) -> {
			try {
				studentscore(Stage);
			} catch (FileNotFoundException e) {

				e.printStackTrace();
			}

		});

		// Place all of the just-initialized GUI elements into the pane
		theRoot.getChildren().addAll(label_Doublemainline, Student_master, Subject_master, Reports, ind_score);

	}

	private void setupTextUI(TextField t, String ff, double f, double w, Pos p, double x, double y, boolean e) {
		t.setFont(Font.font(ff, f));
		t.setMinWidth(w);
		t.setMaxWidth(w);
		t.setAlignment(p);
		t.setLayoutX(x);
		t.setLayoutY(y);
		t.setEditable(e);
	}

	private void setupLabelUI(Label l, String ff, double f, double w, Pos p, double x, double y) {
		l.setFont(Font.font(ff, f));
		l.setMinWidth(w);
		l.setAlignment(p);
		l.setLayoutX(x);
		l.setLayoutY(y);
	}

	private void setupButtonUI(Button b, String ff, double f, double w, Pos p, double x, double y) {
		b.setFont(Font.font(ff, f));
		b.setMinWidth(w);
		b.setAlignment(p);
		b.setLayoutX(x);
		b.setLayoutY(y);
	}

	/**********************************************************************************************
	 * 
	 * User Interface Actions
	 * 
	 **********************************************************************************************/

	private void studentscore(Stage ssstage) throws FileNotFoundException { // set stage
		ssstage.setTitle("Student Individual Result Panel");

		Pane theRoot = new Pane(); // create pane

		setupLabelUI(students, "Arial", 18, 50, Pos.BASELINE_LEFT, 50, 75); // label

		setupLabelUI(sem, "Arial", 18, 50, Pos.BASELINE_LEFT, 50, 125);

		combo_box2.setLayoutX(130);
		combo_box2.setLayoutY(130);
		combo_box2.setMinWidth(100);

		Button button_Submit = new Button("Click to view result");
		button_Submit.setLayoutX(125); // setting the layout for the browse button
		button_Submit.setLayoutY(175);
		button_Submit.setOnAction((event) -> {
			try {
				total();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		});

		Scene scene = new Scene(theRoot, 400, 350); // Creating a scene object

		ssstage.setScene(scene); // Adding scene to the stage
		theRoot.getChildren().addAll(combo_box2, students, button_Submit);

		ssstage.show(); // Displaying the contents of the stage
	};

	private void total() throws SQLException, ClassNotFoundException {

		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con1 = DriverManager
				.getConnection("jdbc:mysql://localhost/dbcsproject?autoReconnect=true&useSSL=false", "root", "Scooby@100200");
		String y = combo_box2.getSelectionModel().getSelectedItem();
		Statement stmt = con1.createStatement();

		ResultSet rs = stmt.executeQuery(
				"select rollno,6weekbdresult.totalscoreobtained,6weekdbcsresult.totalscoreobtained from student,6weekbdresult,6weekdbcsresult where "
						+ "rollno = " + y + " limit 1");

		while (rs.next()) {
			String coffeeName = rs.getString("rollno");
			String supplierID = rs.getString("6weekbdresult.totalscoreobtained");
			String price = rs.getString("6weekdbcsresult.totalscoreobtained");

			System.out.println(coffeeName + "\t" + supplierID + "\t" + price + "\t");
		}
	}

	private void studentmaster(Stage theStage) throws FileNotFoundException { // set stage
		theStage.setTitle("Student Master");

		Pane theRoot = new Pane(); // create pane

		setupLabelUI(year, "Arial", 18, 50, Pos.BASELINE_LEFT, 50, 75); // label

		setupLabelUI(sem, "Arial", 18, 50, Pos.BASELINE_LEFT, 50, 125);

		
		Button button_Browse = new Button("Browse");
		button_Browse.setLayoutX(120); // setting the layout for the browse button
		button_Browse.setLayoutY(225);

		Button button_Submit = new Button("Submit");
		button_Submit.setLayoutX(225); // setting the layout for the browse button
		button_Submit.setLayoutY(225);
		button_Submit.setOnAction((event) -> {
			submit();

		});

		button_Browse.setOnAction((event) -> {
			browse1(theStage);
		});

		// Create a combo box for year
		setupTextUI(DataSource1, "Arial", 16, 300, Pos.BASELINE_LEFT, 50, 175, true);
		combo_box.setLayoutX(150);
		combo_box.setLayoutY(75);

		// Create a combo box for semester

		combo_box1.setLayoutX(150);
		combo_box1.setLayoutY(125);

		Scene scene = new Scene(theRoot, 400, 350); // Creating a scene object

		theStage.setScene(scene); // Adding scene to the stage
		theRoot.getChildren().addAll(combo_box, year, combo_box1, sem, button_Browse, DataSource1, button_Submit);

		theStage.show(); // Displaying the contents of the stage
	}

	private void browse1(Stage theStage) {

		FileChooser FChooser = new FileChooser(); // this is the code for selecting the excel file by clicking on the
													// browse button)

		File selectedFile = FChooser.showOpenDialog(theStage); // This code will only select the file with .xlsx format

		String fileName = selectedFile.getName().toString();
		if (fileName.toLowerCase().endsWith(".xlsx") && !fileName.toLowerCase().startsWith("~$")) {
			DataSource1.setText(selectedFile.getAbsolutePath());
		} else {
			DataSource1.setText("");

		}

		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e1) {

			e1.printStackTrace();
		}

		try {

			con = DriverManager.getConnection("jdbc:mysql://localhost/dbcsproject?autoReconnect=true&useSSL=false",
					"root", "309919");
		} catch (SQLException e) {

			e.printStackTrace();
		}
		try {
			con.setAutoCommit(false);
		} catch (SQLException e) {

			e.printStackTrace();
		}
		PreparedStatement pstm = null;

		String sql = "TRUNCATE STUDENT";
		try {
			pstm = (PreparedStatement) con.prepareStatement(sql);
		} catch (SQLException e) {

			e.printStackTrace();
		}
		try {
			pstm.execute();
		} catch (SQLException e) {

			e.printStackTrace();
		}
		System.out.println("Table Dropped");

		try {
			con.commit();
		} catch (SQLException e) {

			e.printStackTrace();
		}
		try {
			pstm.close();
		} catch (SQLException e) {

			e.printStackTrace();
		}
		try {
			con.close();
		} catch (SQLException e) {

			e.printStackTrace();
		}

		System.out.println("data truncated");

	}

	private void submit() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(
					"jdbc:mysql://localhost/dbcsproject?autoReconnect=true&useSSL=false", "root", "Scooby@100200");
			con.setAutoCommit(false);
			PreparedStatement pstm = null;
			String input = "C://Users/hp/Downloads/Student_Master.xlsx";

			@SuppressWarnings("resource")
			XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(input));

			XSSFSheet sheet = wb.getSheetAt(0);
			Row row;
			y = combo_box.getSelectionModel().getSelectedItem().toString();
			s = combo_box1.getSelectionModel().getSelectedItem();
			for (int i = 1; i <= 52; i++) {
				row = sheet.getRow(i);
				int srno = (int) row.getCell(0).getNumericCellValue();
				int rollno = (int) row.getCell(1).getNumericCellValue();
				String studentname = row.getCell(2).getStringCellValue();

				String fathername = row.getCell(3).getStringCellValue();

				String sql = "INSERT INTO student VALUES('" + srno + "',+'" + y + "','" + s + "','" + rollno + "','"
						+ studentname + "','" + fathername + "')";
				pstm = (PreparedStatement) con.prepareStatement(sql);
				pstm.execute();
				System.out.println("Import rows " + i);
			}
			con.commit();
			pstm.close();
			con.close();

			System.out.println("Success import excel to mysql table");
		} catch (ClassNotFoundException e) {
			System.out.println(e);
		} catch (SQLException ex) {
			System.out.println(ex);
		} catch (IOException ioe) {
			System.out.println(ioe);
		}

	}

	// -------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------
	// Subject master

	private void subjectmaster(Stage Pstage) throws FileNotFoundException {

		Pstage.setTitle("Subject Master");

		Label gb = new Label("Gradebook");
		setupLabelUI(gb, "Arial", 18, 50, Pos.BASELINE_LEFT, 150, 15);

		Pane theRoot = new Pane();

		setupLabelUI(year, "Arial", 18, 50, Pos.BASELINE_LEFT, 50, 75);

		setupLabelUI(sem, "Arial", 18, 50, Pos.BASELINE_LEFT, 50, 125);

		Button Browse_sub = new Button("Browse");
		Browse_sub.setLayoutX(120); // setting the layout for the browse button
		Browse_sub.setLayoutY(225);

		Button Submit = new Button("Submit");
		Submit.setLayoutX(225); // setting the layout for the browse button
		Submit.setLayoutY(225);

		Submit.setOnAction((event) -> {

			Sub_submit();

		});

		TextField DataSource2 = new TextField();
		setupTextUI(DataSource2, "Arial", 16, 300, Pos.BASELINE_LEFT, 50, 175, true);

		Browse_sub.setOnAction((event) -> {

			subject(Pstage);

		});

		// Create a combo box for year

		combo_box.setLayoutX(150);
		combo_box.setLayoutY(75);

		// Create a combo box for semester

		combo_box1.setLayoutX(150);
		combo_box1.setLayoutY(125);
		Scene scene = new Scene(theRoot, 400, 350); // Creating a scene object

		Pstage.setScene(scene); // Adding scene to the stage
		theRoot.getChildren().addAll(combo_box, year, combo_box1, sem, Browse_sub, DataSource2, Submit, gb);

	}

	private void subject(Stage Pstage) {
		FileChooser FChooser = new FileChooser(); // this is the code for selecting the excel file by clicking on the
		// browse button)

		File selectedFile = FChooser.showOpenDialog(Pstage); // This code will only select the file with .xlsx format

		String fileName = selectedFile.getName().toString();
		if (fileName.toLowerCase().endsWith(".xlsx") && !fileName.toLowerCase().startsWith("~$")) {
			DataSource1.setText(selectedFile.getAbsolutePath());
		} else {
			DataSource1.setText("");

		}

		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e1) {

			e1.printStackTrace();
		}

		try {

			con = DriverManager.getConnection("jdbc:mysql://localhost/dbcsproject?autoReconnect=true&useSSL=false",
					"root", "309919");
		} catch (SQLException e) {

			e.printStackTrace();
		}
		try {
			con.setAutoCommit(false);
		} catch (SQLException e) {

			e.printStackTrace();
		}
		PreparedStatement pstm = null;

		String sql = " TRUNCATE Subject";
		try {
			pstm = (PreparedStatement) con.prepareStatement(sql);
		} catch (SQLException e) {

			e.printStackTrace();
		}
		try {
			pstm.execute();
		} catch (SQLException e) {

			e.printStackTrace();
		}

		try {
			con.commit();
		} catch (SQLException e) {

			e.printStackTrace();
		}
		try {
			pstm.close();
		} catch (SQLException e) {

			e.printStackTrace();
		}
		try {
			con.close();
		} catch (SQLException e) {

			e.printStackTrace();
		}

		System.out.println("data truncated");

	}

	private void Sub_submit() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(
					"jdbc:mysql://localhost/dbcsproject?autoReconnect=true&useSSL=false", "root", "Scooby@100200");
			con.setAutoCommit(false);
			PreparedStatement pstm = null;
			String input = "C://Users/hp/Downloads/Subject_Master.xlsx";

			@SuppressWarnings("resource")
			XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(input));

			XSSFSheet sheet = wb.getSheetAt(0);
			Row row;
			y = combo_box.getSelectionModel().getSelectedItem().toString();
			s = combo_box1.getSelectionModel().getSelectedItem();
			for (int i = 1; i <= 7; i++) {
				row = sheet.getRow(i);
				String subject_code = row.getCell(0).getStringCellValue();

				String subjectname = row.getCell(1).getStringCellValue();

				int Max_marks = (int) row.getCell(2).getNumericCellValue();

				String sql = "INSERT INTO Subject VALUES('" + y + "','" + s + "','" + subject_code + "','" + subjectname
						+ "','" + Max_marks + "')";
				pstm = (PreparedStatement) con.prepareStatement(sql);
				pstm.execute();
				System.out.println("Import rows " + i);
			}
			con.commit();
			pstm.close();
			con.close();

			System.out.println("Success import excel to mysql table");
		} catch (ClassNotFoundException e) {
			System.out.println(e);
		} catch (SQLException ex) {
			System.out.println(ex);
		} catch (IOException ioe) {
			System.out.println(ioe);
		}

	}

	private void grade(Stage Gstage) throws FileNotFoundException {

		Gstage.setTitle("Gradebook");

		Label gb = new Label("Gradebook");
		setupLabelUI(gb, "Arial", 18, 50, Pos.BASELINE_LEFT, 150, 15);

		Pane theRoot = new Pane();

		setupLabelUI(year, "Arial", 18, 50, Pos.BASELINE_LEFT, 50, 75);

		setupLabelUI(sem, "Arial", 18, 50, Pos.BASELINE_LEFT, 50, 125);

		setupLabelUI(courses, "Arial", 18, 50, Pos.BASELINE_LEFT, 50, 175);

		Button button_Brow = new Button("Browse");
		button_Brow.setLayoutX(120); // setting the layout for the browse button
		button_Brow.setLayoutY(275);

		Button button_Submitt = new Button("Submit");
		button_Submitt.setLayoutX(225); // setting the layout for the browse button
		button_Submitt.setLayoutY(275);

		TextField DataSource3 = new TextField();
		setupTextUI(DataSource3, "Arial", 16, 300, Pos.BASELINE_LEFT, 50, 225, true);

		// Create a combo box for year
		combo_box.setLayoutX(150);
		combo_box.setLayoutY(75);

		// Create a combo box for semester
		combo_box1.setLayoutX(150);
		combo_box1.setLayoutY(125);

		combo_box3.setLayoutX(150);
		combo_box3.setLayoutY(175);
		Scene scene = new Scene(theRoot, 400, 350); // Creating a scene object

		button_Brow.setOnAction((event) -> {

			grade_browse(Gstage);

		});

		button_Submitt.setOnAction((event) -> {

			Grade_submit();

		});

		Gstage.setScene(scene); // Adding scene to the stage
		theRoot.getChildren().addAll(combo_box, year, combo_box1, sem, button_Brow, DataSource3, button_Submitt, gb,
				combo_box3, courses);

	}

	private void grade_browse(Stage Gstage) {
		FileChooser FChooser = new FileChooser(); // this is the code for selecting the excel file by clicking on the
		// browse button)

		File selectedFile = FChooser.showOpenDialog(Gstage); // This code will only select the file with .xlsx format

		String fileName = selectedFile.getName().toString();
		if (fileName.toLowerCase().endsWith(".xlsx") && !fileName.toLowerCase().startsWith("~$")) {
			DataSource1.setText(selectedFile.getAbsolutePath());
		} else {
			DataSource1.setText("");

		}

		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e1) {

			e1.printStackTrace();
		}

		try {

			con = DriverManager.getConnection("jdbc:mysql://localhost/dbcsproject?autoReconnect=true&useSSL=false",
					"root", "309919");
		} catch (SQLException e) {

			e.printStackTrace();
		}
		try {
			con.setAutoCommit(false);
		} catch (SQLException e) {

			e.printStackTrace();
		}
		PreparedStatement pstm = null;

		String sql = " TRUNCATE Gradebook";
		try {
			pstm = (PreparedStatement) con.prepareStatement(sql);
		} catch (SQLException e) {

			e.printStackTrace();
		}
		try {
			pstm.execute();
		} catch (SQLException e) {

			e.printStackTrace();
		}

		try {
			con.commit();
		} catch (SQLException e) {

			e.printStackTrace();
		}
		try {
			pstm.close();
		} catch (SQLException e) {

			e.printStackTrace();
		}
		try {
			con.close();
		} catch (SQLException e) {

			e.printStackTrace();
		}

		System.out.println("data truncated");

	}

	private void Grade_submit() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(
					"jdbc:mysql://localhost/dbcsproject?autoReconnect=true&useSSL=false", "root", "Scooby@100200");
			con.setAutoCommit(false);
			PreparedStatement pstm = null;
			String input = "C://Users/hp/Downloads/Gradebook.xlsx";

			@SuppressWarnings("resource")
			XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(input));

			XSSFSheet sheet = wb.getSheetAt(0);
			Row row;
			y = combo_box.getSelectionModel().getSelectedItem().toString();
			s = combo_box1.getSelectionModel().getSelectedItem();
			for (int i = 1; i <= 2; i++) {
				row = sheet.getRow(i);
				String subject_code = row.getCell(0).getStringCellValue();

				String subjectname = row.getCell(1).getStringCellValue();

				int totalsh = (int) row.getCell(2).getNumericCellValue();

				int totalenb = (int) row.getCell(3).getNumericCellValue();

				int shweightage = (int) row.getCell(4).getNumericCellValue();

				int enbweightage = (int) row.getCell(5).getNumericCellValue();

				int project = (int) row.getCell(6).getNumericCellValue();

				int finalassessment = (int) row.getCell(7).getNumericCellValue();

				int coursetotal = (int) row.getCell(8).getNumericCellValue();

				String sql = "INSERT INTO Gradebook VALUES('" + y + "','" + s + "','" + subject_code + "','"
						+ subjectname + "','" + totalsh + "','" + totalenb + "','" + shweightage + "','" + enbweightage
						+ "','" + project + "','" + finalassessment + "','" + coursetotal + "')";
				pstm = (PreparedStatement) con.prepareStatement(sql);
				pstm.execute();
				System.out.println("Import rows " + i);
			}
			con.commit();
			pstm.close();
			con.close();

			System.out.println("Success import excel to mysql table");
		} catch (ClassNotFoundException e) {
			System.out.println(e);
		} catch (SQLException ex) {
			System.out.println(ex);
		} catch (IOException ioe) {
			System.out.println(ioe);
		}

	}

	/*
	 * --------------------------------------
	 * --------------------------------------- ----------------------------------
	 */
	private void Report(Stage Rstage) throws FileNotFoundException {

		Rstage.setTitle("Report Generation");

		Label rb = new Label("Report Generation");
		setupLabelUI(rb, "Arial", 18, 50, Pos.BASELINE_LEFT, 150, 15);

		Pane theRoot = new Pane();

		setupLabelUI(year, "Arial", 18, 50, Pos.BASELINE_LEFT, 50, 75);

		setupLabelUI(sem, "Arial", 18, 50, Pos.BASELINE_LEFT, 50, 125);

		Button overall = new Button("Generate overall Report");
		overall.setLayoutX(150); // setting the layout for the browse button
		overall.setLayoutY(140);

		Scene scene = new Scene(theRoot, 500, 350); // Creating a scene object

		Rstage.setScene(scene); // Adding scene to the stage
		theRoot.getChildren().addAll(overall, rb);

	}

	/*
	 * --------------------------------------
	 * --------------------------------------- ----------------------------------
	 */
	private void ReportGen(Stage Repstage) throws FileNotFoundException {

		Repstage.setTitle("Report Generation");

		Label rep = new Label("Report Generation");
		setupLabelUI(rep, "Arial", 18, 50, Pos.BASELINE_LEFT, 250, 15);

		Pane theRoot = new Pane();

		setupLabelUI(year, "Arial", 18, 50, Pos.BASELINE_LEFT, 50, 75);

		setupLabelUI(sem, "Arial", 18, 50, Pos.BASELINE_LEFT, 50, 125);

		Button week2 = new Button(" Generate Report for Week 2");
		week2.setLayoutX(50); // setting the layout for the browse button
		week2.setLayoutY(75);

		Button week4 = new Button("Generate Report for Week 4");
		week4.setLayoutX(50); // setting the layout for the browse button
		week4.setLayoutY(125);

		Button week6 = new Button("Generate Report for Week 6");
		week6.setLayoutX(50); // setting the layout for the browse button
		week6.setLayoutY(175);

		Button resweek2 = new Button(" View Grades of Big Data");
		resweek2.setLayoutX(225);
		resweek2.setLayoutY(75);
		resweek2.setVisible(false);

		Button resweek4 = new Button(" View Grades of Big Data");
		resweek4.setLayoutX(225);
		resweek4.setLayoutY(125);
		resweek4.setVisible(false);

		Button resweek6 = new Button(" View Grades of Big Data");
		resweek6.setLayoutX(225);
		resweek6.setLayoutY(175);
		resweek6.setVisible(false);

		Button dbsweek2 = new Button(" View Grades of Dbcs");
		dbsweek2.setLayoutX(380);
		dbsweek2.setLayoutY(75);
		dbsweek2.setVisible(false);

		Button dbsweek4 = new Button(" View Grades of Dbcs ");
		dbsweek4.setLayoutX(380);
		dbsweek4.setLayoutY(125);
		dbsweek4.setVisible(false);

		Button dbsweek6 = new Button(" View Grades of Dbcs ");
		dbsweek6.setLayoutX(380);
		dbsweek6.setLayoutY(175);
		dbsweek6.setVisible(false);

		week2.setOnAction((event) -> {
			resweek2.setVisible(true);
			dbsweek2.setVisible(true);
			bigdataresult();
			dbcsresult();

		});

		resweek2.setOnAction((event) -> {

			try {
				bd1();
			} catch (FileNotFoundException | ClassNotFoundException | DocumentException | SQLException e) {

				e.printStackTrace();
			}

		});

		dbsweek2.setOnAction((event) -> {

			try {
				db1();
			} catch (FileNotFoundException | ClassNotFoundException | DocumentException | SQLException e) {

				e.printStackTrace();
			}

		});

		dbsweek4.setOnAction((event) -> {

			try {
				db2();
			} catch (FileNotFoundException | ClassNotFoundException | DocumentException | SQLException e) {

				e.printStackTrace();
			}

		});

		dbsweek6.setOnAction((event) -> {

			try {
				db3();
			} catch (FileNotFoundException | ClassNotFoundException | DocumentException | SQLException e) {

				e.printStackTrace();
			}

		});

		resweek4.setOnAction((event) -> {

			try {
				bd2();
			} catch (FileNotFoundException | ClassNotFoundException | DocumentException | SQLException e) {

				e.printStackTrace();
			}

		});

		resweek6.setOnAction((event) -> {

			try {
				bd3();
			} catch (FileNotFoundException | ClassNotFoundException | DocumentException | SQLException e) {

				e.printStackTrace();
			}

		});

		week4.setOnAction((event) -> {

			resweek4.setVisible(true);
			dbsweek4.setVisible(true);

		});

		week6.setOnAction((event) -> {

			resweek6.setVisible(true);
			dbsweek6.setVisible(true);

		});

		Scene scene = new Scene(theRoot, 600, 350); // Creating a scene object

		Repstage.setScene(scene); // Adding scene to the stage
		theRoot.getChildren().addAll(week2, week4, week6, rep, resweek2, dbsweek2, resweek4, dbsweek4, resweek6,
				dbsweek6);

	}

	private void bd2() throws FileNotFoundException, DocumentException, ClassNotFoundException, SQLException {
		Document document = new Document();
		PdfWriter.getInstance(document, new FileOutputStream("E:/week4 Progress Report.pdf"));
		document.open();
		double totalofenb = 22 / 30;
		double totalofsh = 12 / 10;
		double twoWeeksENB = totalofenb * 4.0;
		double twoWeeksSH = totalofsh * 8.0;
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con1 = DriverManager
				.getConnection("jdbc:mysql://localhost/dbcsproject?autoReconnect=true&useSSL=false", "root", "Scooby@100200");

		String insert = "Select * from bigdata;";
		Statement stmt = con1.createStatement();
		ResultSet rs = stmt.executeQuery(insert);
		@SuppressWarnings("unused")
		String rollNo;
		String week1sh1;
		String week1sh2;
		String week2sh1;
		String week2sh2;
		String week1enb;
		String week2enb;
		String week3, week4;
		String week3_sh1_sh2;
		String week4_sh1_sh2;

		while (rs.next()) {

			rollNo = rs.getString("rollno");
			week1sh1 = rs.getString("week1sh1");
			week1sh2 = rs.getString("week1sh2");
			week2sh1 = rs.getString("week2sh1");
			week2sh2 = rs.getString("week2sh2");
			week3_sh1_sh2 = rs.getString("week3_sh1_sh2");
			week4_sh1_sh2 = rs.getString("week4_sh1_sh2");
			week1enb = rs.getString("week1");
			week2enb = rs.getString("week2");
			week3 = rs.getString("week3");
			week4 = rs.getString("week4");

			int totalscoreofsh = Integer.parseInt(week2sh2) + Integer.parseInt(week2sh1) + Integer.parseInt(week1sh2)
					+ Integer.parseInt(week1sh1) + Integer.parseInt(week3_sh1_sh2) + Integer.parseInt(week4_sh1_sh2);
			double totalGrade = (totalscoreofsh * twoWeeksSH) / 80;

			int totalscoreofenb = Integer.parseInt(week2enb) + Integer.parseInt(week1enb) + Integer.parseInt(week3)
					+ Integer.parseInt(week4);
			double totalGradeENB = (totalscoreofenb * twoWeeksENB) / 400;

			double totalSOFBIGDATA = twoWeeksENB + twoWeeksSH;
			double hun = 100.0 / totalSOFBIGDATA;

			double finalscore = (totalGrade + totalGradeENB) * hun;

			Statement stmtfinal = con1.createStatement();

			String insertfinal = "INSERT INTO 4weekbdresult(totalscoreobtained)  VALUES ('" + finalscore + "');";
			stmtfinal.executeUpdate(insertfinal);

			PdfPTable table = new PdfPTable(7);
			table.addCell("Sr.No");
			table.addCell("Roll No.");
			table.addCell("Name");
			table.addCell("Father's Name");
			table.addCell("Subject Code");
			table.addCell("Subject Name");
			table.addCell("Grades %");
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection(
					"jdbc:mysql://localhost/dbcsproject?autoReconnect=true&useSSL=false", "root", "Scooby@100200");

			Statement st = con.createStatement();

			ResultSet r = st.executeQuery(
					"select student.srno,student.rollno,student.studentname,student.fathername,subject.subject_code,subject.subjectname,4weekbdresult.totalscoreobtained from student,subject,4weekbdresult where subjectname='Big Data Analytics' and student.srno=4weekbdresult.id limit 59");

			while (r.next()) {

				table.addCell(r.getString("srno"));
				table.addCell(r.getString("rollno"));
				table.addCell(r.getString("studentname"));
				table.addCell(r.getString("fathername"));
				table.addCell(r.getString("subject_code"));
				table.addCell(r.getString("subjectname"));
				table.addCell(r.getString("totalscoreobtained"));

			}

			document.add(table);
			break;
		}
		document.close();

	}

	private void bd1() throws FileNotFoundException, DocumentException, ClassNotFoundException, SQLException {
		Document document = new Document();
		PdfWriter.getInstance(document, new FileOutputStream("E:/week2 Progress Report.pdf"));
		document.open();
		double totalofenb = 22 / 30;
		double totalofsh = 12 / 10;
		double twoWeeksENB = totalofenb * 4.0;
		double twoWeeksSH = totalofsh * 8.0;
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con1 = DriverManager
				.getConnection("jdbc:mysql://localhost/dbcsproject?autoReconnect=true&useSSL=false", "root", "Scooby@100200");
		String insert = "Select * from bigdata;";
		Statement stmt = con1.createStatement();
		ResultSet rs = stmt.executeQuery(insert);

		String rollNo;
		String week1sh1;
		String week1sh2;
		String week2sh1;
		String week2sh2;
		String week1enb;
		String week2enb;

		while (rs.next()) {

			rollNo = rs.getString("rollno");
			week1sh1 = rs.getString("week1sh1");
			week1sh2 = rs.getString("week1sh2");
			week2sh1 = rs.getString("week2sh1");
			week2sh2 = rs.getString("week2sh2");
			week1enb = rs.getString("week1");
			week2enb = rs.getString("week2");
			int totalscoreofsh = Integer.parseInt(week2sh2) + Integer.parseInt(week2sh1) + Integer.parseInt(week1sh2)
					+ Integer.parseInt(week1sh1);
			double totalGrade = (totalscoreofsh * twoWeeksSH) / 40;

			int totalscoreofenb = Integer.parseInt(week2enb) + Integer.parseInt(week1enb);
			double totalGradeENB = (totalscoreofenb * twoWeeksENB) / 200;

			double totalSOFBIGDATA = twoWeeksENB + twoWeeksSH;
			double hun = 100.0 / totalSOFBIGDATA;

			double finalscore = (totalGrade + totalGradeENB) * hun;

			System.out.println(finalscore);
			Statement stmtfinal = con1.createStatement();

			String insertfinal = "INSERT INTO 2weekbdresult(totalscoreobtained)  VALUES ('" + finalscore + "');";

			stmtfinal.executeUpdate(insertfinal);
			
			
		}
		PdfPTable table = new PdfPTable(7);
		table.addCell("Sr.No");
		table.addCell("Roll No.");
		table.addCell("Name");
		table.addCell("Father's Name");
		table.addCell("Subject Code");
		table.addCell("Subject Name");
		table.addCell("Grades %");
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con = DriverManager
				.getConnection("jdbc:mysql://localhost/dbcsproject?autoReconnect=true&useSSL=false", "root", "Scooby@100200");

		Statement st = con.createStatement();

		ResultSet r = st.executeQuery(
				"select student.srno,student.rollno,student.studentname,student.fathername,subject.subject_code,subject.subjectname,2weekbdresult.totalscoreobtained from student,subject,2weekbdresult where subjectname='Big Data Analytics' and student.srno=2weekbdresult.id limit 59");

		while (r.next()) {

			table.addCell(r.getString("srno"));
			table.addCell(r.getString("rollno"));
			table.addCell(r.getString("studentname"));
			table.addCell(r.getString("fathername"));
			table.addCell(r.getString("subject_code"));
			table.addCell(r.getString("subjectname"));
			table.addCell(r.getString("totalscoreobtained"));

		}

		document.add(table);
		document.close();

	}

	private void db1() throws FileNotFoundException, DocumentException, ClassNotFoundException, SQLException {
		Document document = new Document();
		PdfWriter.getInstance(document, new FileOutputStream("E:/week2 Database Progress Report.pdf"));
		document.open();
		double totalofenb = 22 / 30;
		double totalofsh = 12 / 10;
		double twoWeeksENB = totalofenb * 2.0;
		double twoWeeksSH = totalofsh * 4.0;
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con1 = DriverManager
				.getConnection("jdbc:mysql://localhost/dbcsproject?autoReconnect=true&useSSL=false", "root", "Scooby@100200");
		String insert = "Select * from dbcs;";
		Statement stmt = con1.createStatement();
		ResultSet rs = stmt.executeQuery(insert);

		String rollNo;
		String week1sh1;
		String week1sh2;
		String week2sh1;
		String week2sh2;
		String week1enb;
		String week2enb;

		while (rs.next()) {

			rollNo = rs.getString("rollno");
			week1sh1 = rs.getString("week1sh1");
			week1sh2 = rs.getString("week1sh2");
			week2sh1 = rs.getString("week2sh1");
			week2sh2 = rs.getString("week2sh2");
			week1enb = rs.getString("week1");
			week2enb = rs.getString("week2");
			int totalscoreofsh = Integer.parseInt(week2sh2) + Integer.parseInt(week2sh1) + Integer.parseInt(week1sh2)
					+ Integer.parseInt(week1sh1);
			double totalGrade = (totalscoreofsh * twoWeeksSH) / 40;

			int totalscoreofenb = Integer.parseInt(week2enb) + Integer.parseInt(week1enb);
			double totalGradeENB = (totalscoreofenb * twoWeeksENB) / 200;

			double totalSOFBIGDATA = twoWeeksENB + twoWeeksSH;
			double hun = 100.0 / totalSOFBIGDATA;

			double final1 = (totalGrade + totalGradeENB) * hun;

			Statement stmtfinal = con1.createStatement();

			String insertfinal = "INSERT INTO 2weekdbcsresult(totalscoreobtained)  VALUES ('" + final1 + "');";
			System.out.println(final1);
			stmtfinal.executeUpdate(insertfinal);
		}
		PdfPTable table = new PdfPTable(7);
		table.addCell("Sr.No");
		table.addCell("Roll No.");
		table.addCell("Name");
		table.addCell("Father's Name");
		table.addCell("Subject Code");
		table.addCell("Subject Name");
		table.addCell("Grades %");
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con = DriverManager
				.getConnection("jdbc:mysql://localhost/dbcsproject?autoReconnect=true&useSSL=false", "root", "Scooby@100200");

		Statement st = con.createStatement();

		ResultSet r = st.executeQuery(
				"select student.srno,student.rollno,student.studentname,student.fathername,subject.subject_code,subject.subjectname,2weekdbcsresult.totalscoreobtained from student,subject,2weekdbcsresult where subjectname='Database & Client Server' and student.srno=2weekdbcsresult.id limit 59");

		while (r.next()) {

			table.addCell(r.getString("srno"));
			table.addCell(r.getString("rollno"));
			table.addCell(r.getString("studentname"));
			table.addCell(r.getString("fathername"));
			table.addCell(r.getString("subject_code"));
			table.addCell(r.getString("subjectname"));
			table.addCell(r.getString("totalscoreobtained"));

		}

		document.add(table);
		document.close();

	}

	private void db2() throws FileNotFoundException, DocumentException, ClassNotFoundException, SQLException {
		Document document = new Document();
		PdfWriter.getInstance(document, new FileOutputStream("E:/week4 Database Progress Report.pdf"));
		document.open();
		double totalofenb = 22 / 30;
		double totalofsh = 12 / 10;
		double twoWeeksENB = totalofenb * 4.0;
		double twoWeeksSH = totalofsh * 8.0;
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con1 = DriverManager
				.getConnection("jdbc:mysql://localhost/dbcsproject?autoReconnect=true&useSSL=false", "root", "Scooby@100200");

		String insert = "Select * from dbcs;";
		Statement stmt = con1.createStatement();
		ResultSet rs = stmt.executeQuery(insert);
		@SuppressWarnings("unused")
		String rollNo;
		String week1sh1;
		String week1sh2;
		String week2sh1;
		String week2sh2;
		String week1enb;
		String week2enb;
		String week3, week4;
		String week3_sh1_sh2;
		String week4sh1, week4sh2;

		while (rs.next()) {

			rollNo = rs.getString("rollno");
			week1sh1 = rs.getString("week1sh1");
			week1sh2 = rs.getString("week1sh2");
			week2sh1 = rs.getString("week2sh1");
			week2sh2 = rs.getString("week2sh2");
			week3_sh1_sh2 = rs.getString("week3_sh1_sh2");
			week4sh1 = rs.getString("week4sh1");
			week4sh2 = rs.getString("week4sh2");
			week1enb = rs.getString("week1");
			week2enb = rs.getString("week2");
			week3 = rs.getString("week3");
			week4 = rs.getString("week4");

			Double totalscoreofsh = Integer.parseInt(week2sh2) + Integer.parseInt(week2sh1) + Integer.parseInt(week1sh2)
					+ Integer.parseInt(week1sh1) + Double.parseDouble(week3_sh1_sh2) + Double.parseDouble(week4sh1)
					+ Double.parseDouble(week4sh2);
			double totalGrade = (totalscoreofsh * twoWeeksSH) / 80;

			int totalscoreofenb = Integer.parseInt(week2enb) + Integer.parseInt(week1enb) + Integer.parseInt(week3)
					+ Integer.parseInt(week4);
			double totalGradeENB = (totalscoreofenb * twoWeeksENB) / 400;

			double totalSOFBIGDATA = twoWeeksENB + twoWeeksSH;
			double hun = 100.0 / totalSOFBIGDATA;

			double finalscore = (totalGrade + totalGradeENB) * hun;

			Statement stmtfinal = con1.createStatement();

			String insertfinal = "INSERT INTO 4weekdbcsresult(totalscoreobtained)  VALUES ('" + finalscore + "');";
			stmtfinal.executeUpdate(insertfinal);

			PdfPTable table = new PdfPTable(7);
			table.addCell("Sr.No");
			table.addCell("Roll No.");
			table.addCell("Name");
			table.addCell("Father's Name");
			table.addCell("Subject Code");
			table.addCell("Subject Name");
			table.addCell("Grades %");
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection(
					"jdbc:mysql://localhost/dbcsproject?autoReconnect=true&useSSL=false", "root", "Scooby@100200");

			Statement st = con.createStatement();

			ResultSet r = st.executeQuery(
					"select student.srno,student.rollno,student.studentname,student.fathername,subject.subject_code,subject.subjectname,4weekdbcsresult.totalscoreobtained from student,subject,4weekdbcsresult where subjectname='Database & Client Server' and student.srno=4weekdbcsresult.id limit 59");

			while (r.next()) {

				table.addCell(r.getString("srno"));
				table.addCell(r.getString("rollno"));
				table.addCell(r.getString("studentname"));
				table.addCell(r.getString("fathername"));
				table.addCell(r.getString("subject_code"));
				table.addCell(r.getString("subjectname"));
				table.addCell(r.getString("totalscoreobtained"));

			}

			document.add(table);
			break;
		}
		document.close();

	}

	private void bd3() throws FileNotFoundException, DocumentException, ClassNotFoundException, SQLException {
		Document document = new Document();
		PdfWriter.getInstance(document, new FileOutputStream("E:/week6 Big Data Progress Report.pdf"));
		document.open();
		double totalofenb = 22 / 30;
		double totalofsh = 12 / 10;
		double twoWeeksENB = totalofenb * 6.0;
		double twoWeeksSH = totalofsh * 12.0;
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con1 = DriverManager
				.getConnection("jdbc:mysql://localhost/dbcsproject?autoReconnect=true&useSSL=false", "root", "Scooby@100200");

		String insert = "Select * from bigdata;";
		Statement stmt = con1.createStatement();
		ResultSet rs = stmt.executeQuery(insert);
		@SuppressWarnings("unused")
		String rollNo;
		String week1sh1;
		String week1sh2;
		String week2sh1;
		String week2sh2;
		String week1enb;
		String week2enb;
		String week3, week4, week5, week6;
		String week3_sh1_sh2;
		String week4_sh1_sh2;
		String week5sh1;
		String week5sh2;
		String week6sh1;
		String week6sh2;

		while (rs.next()) {

			rollNo = rs.getString("rollno");
			week1sh1 = rs.getString("week1sh1");
			week1sh2 = rs.getString("week1sh2");
			week2sh1 = rs.getString("week2sh1");
			week2sh2 = rs.getString("week2sh2");
			week3_sh1_sh2 = rs.getString("week3_sh1_sh2");
			week4_sh1_sh2 = rs.getString("week4_sh1_sh2");
			week5sh1 = rs.getString("week5sh1");
			week5sh2 = rs.getString("week5sh2");
			week6sh1 = rs.getString("week6sh1");
			week6sh2 = rs.getString("week6sh2");
			week1enb = rs.getString("week1");
			week2enb = rs.getString("week2");
			week3 = rs.getString("week3");
			week4 = rs.getString("week4");
			week5 = rs.getString("week5");
			week6 = rs.getString("week6");

			double totalscoreofsh = Double.parseDouble(week2sh2) + Double.parseDouble(week2sh1)
					+ Double.parseDouble(week1sh2) + Double.parseDouble(week1sh1) + Double.parseDouble(week3_sh1_sh2)
					+ Double.parseDouble(week4_sh1_sh2) + Double.parseDouble(week5sh2) + Double.parseDouble(week5sh1)
					+ Double.parseDouble(week6sh2) + Double.parseDouble(week6sh1);
			double totalGrade = (totalscoreofsh * twoWeeksSH) / 120;

			int totalscoreofenb = Integer.parseInt(week2enb) + Integer.parseInt(week1enb) + Integer.parseInt(week3)
					+ Integer.parseInt(week4) + +Integer.parseInt(week5) + Integer.parseInt(week6);
			double totalGradeENB = (totalscoreofenb * twoWeeksENB) / 600;

			double totalSOFBIGDATA = twoWeeksENB + twoWeeksSH;
			double hun = 100.0 / totalSOFBIGDATA;

			double finalscore = (totalGrade + totalGradeENB) * hun;

			Statement stmtfinal = con1.createStatement();

			String insertfinal = "INSERT INTO 6weekbdresult(totalscoreobtained)  VALUES ('" + finalscore + "');";
			stmtfinal.executeUpdate(insertfinal);
			
		}
		PdfPTable table = new PdfPTable(7);
		table.addCell("Sr.No");
		table.addCell("Roll No.");
		table.addCell("Name");
		table.addCell("Father's Name");
		table.addCell("Subject Code");
		table.addCell("Subject Name");
		table.addCell("Grades %");
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con = DriverManager
				.getConnection("jdbc:mysql://localhost/dbcsproject?autoReconnect=true&useSSL=false", "root", "Scooby@100200");

		Statement st = con.createStatement();

		ResultSet r = st.executeQuery(
				"select student.srno,student.rollno,student.studentname,student.fathername,subject.subject_code,subject.subjectname,6weekbdresult.totalscoreobtained from student,subject,6weekbdresult where subjectname='Big Data Analytics' and student.srno=6weekbdresult.id limit 59");

		while (r.next()) {

			table.addCell(r.getString("srno"));
			table.addCell(r.getString("rollno"));
			table.addCell(r.getString("studentname"));
			table.addCell(r.getString("fathername"));
			table.addCell(r.getString("subject_code"));
			table.addCell(r.getString("subjectname"));
			table.addCell(r.getString("totalscoreobtained"));

		}

		document.add(table);
		document.close();
	}

	private void db3() throws FileNotFoundException, DocumentException, ClassNotFoundException, SQLException {
		Document document = new Document();
		PdfWriter.getInstance(document, new FileOutputStream("E:/week6 DBCS Progress Report.pdf"));
		document.open();
		double totalofenb = 22 / 30;
		double totalofsh = 12 / 10;
		double twoWeeksENB = totalofenb * 6.0;
		double twoWeeksSH = totalofsh * 12.0;
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con1 = DriverManager
				.getConnection("jdbc:mysql://localhost/dbcsproject?autoReconnect=true&useSSL=false", "root", "Scooby@100200");

		String insert = "Select * from dbcs;";
		Statement stmt = con1.createStatement();
		ResultSet rs = stmt.executeQuery(insert);
		String rollNo;
		String week1sh1;
		String week1sh2;
		String week2sh1;
		String week2sh2;
		String week1enb;
		String week2enb;
		String week3, week4, week5, week6;
		String week3_sh1_sh2;
		String week4sh1, week4sh2;
		String week5sh1, week5sh2;
		String week6sh1, week6sh2;

		while (rs.next()) {

			rollNo = rs.getString("rollno");
			week1sh1 = rs.getString("week1sh1");
			week1sh2 = rs.getString("week1sh2");
			week2sh1 = rs.getString("week2sh1");
			week2sh2 = rs.getString("week2sh2");
			week5sh1 = rs.getString("week5sh1");
			week5sh2 = rs.getString("week5sh2");
			week6sh1 = rs.getString("week6sh1");
			week6sh2 = rs.getString("week6sh2");
			week3_sh1_sh2 = rs.getString("week3_sh1_sh2");
			week4sh1 = rs.getString("week4sh1");
			week4sh2 = rs.getString("week4sh2");
			week1enb = rs.getString("week1");
			week2enb = rs.getString("week2");
			week3 = rs.getString("week3");
			week4 = rs.getString("week4");
			week5 = rs.getString("week5");
			week6 = rs.getString("week6");

			double totalscoreofsh = Double.parseDouble(week2sh2) + Double.parseDouble(week2sh1)
					+ Double.parseDouble(week1sh2) + Double.parseDouble(week1sh1) + Double.parseDouble(week3_sh1_sh2)
					+ Double.parseDouble(week4sh1) + Double.parseDouble(week4sh2) + Double.parseDouble(week5sh1)
					+ +Double.parseDouble(week5sh2) + Double.parseDouble(week6sh2) + Double.parseDouble(week6sh1);
			double totalGrade = (totalscoreofsh * twoWeeksSH) / 120;

			int totalscoreofenb = Integer.parseInt(week2enb) + Integer.parseInt(week1enb) + Integer.parseInt(week3)
					+ Integer.parseInt(week4) + +Integer.parseInt(week5) + Integer.parseInt(week6);
			double totalGradeENB = (totalscoreofenb * twoWeeksENB) / 600;

			double totalSOFBIGDATA = twoWeeksENB + twoWeeksSH;
			double hun = 100.0 / totalSOFBIGDATA;

			double finalscore = (totalGrade + totalGradeENB) * hun;
			System.out.println(finalscore);
			Statement stmtfinal = con1.createStatement();

			String insertfinal = "INSERT INTO 6weekdbcsresult(totalscoreobtained)  VALUES ('" + finalscore + "');";
			stmtfinal.executeUpdate(insertfinal);
			
			Statement stmtfinal1 = con1.createStatement();

			String insertfinal1 = "INSERT INTO overall,totalscore)  VALUES ('" + finalscore+ "' );";

			stmtfinal1.executeUpdate(insertfinal1);

		}
		PdfPTable table = new PdfPTable(7);
		table.addCell("Sr.No");
		table.addCell("Roll No.");
		table.addCell("Name");
		table.addCell("Father's Name");
		table.addCell("Subject Code");
		table.addCell("Subject Name");
		table.addCell("Grades %");
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con = DriverManager
				.getConnection("jdbc:mysql://localhost/dbcsproject?autoReconnect=true&useSSL=false", "root", "Scooby@100200");

		Statement st = con.createStatement();

		ResultSet r = st.executeQuery(
				"select student.srno,student.rollno,student.studentname,student.fathername,subject.subject_code,subject.subjectname,6weekdbcsresult.totalscoreobtained from student,subject,6weekdbcsresult where subjectname='Database & Client Server' and student.srno=6weekdbcsresult.id limit 59");

		while (r.next()) {

			table.addCell(r.getString("srno"));
			table.addCell(r.getString("rollno"));
			table.addCell(r.getString("studentname"));
			table.addCell(r.getString("fathername"));
			table.addCell(r.getString("subject_code"));
			table.addCell(r.getString("subjectname"));
			table.addCell(r.getString("totalscoreobtained"));

		}

		document.add(table);

		document.close();
	}

	private void bigdataresult() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(
					"jdbc:mysql://localhost/dbcsproject?autoReconnect=true&useSSL=false", "root", "Scooby@100200");
			con.setAutoCommit(false);
			PreparedStatement pstm = null;
			String input = "C://Users/hp/Downloads/Internal_Score_BigData.xlsx";

			@SuppressWarnings("resource")
			XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(input));

			XSSFSheet sheet = wb.getSheetAt(0);
			Row row;
			for (int i = 1; i <= 52; i++) {
				row = sheet.getRow(i);

				int rollno = (int) row.getCell(0).getNumericCellValue();
				int week1sh1 = (int) row.getCell(1).getNumericCellValue();
				int week1sh2 = (int) row.getCell(2).getNumericCellValue();
				int week2sh1 = (int) row.getCell(3).getNumericCellValue();
				int Week2sh2 = (int) row.getCell(4).getNumericCellValue();
				int week3_sh1_sh2 = (int) row.getCell(5).getNumericCellValue();
				int week4_sh1_sh2 = (int) row.getCell(6).getNumericCellValue();
				int week5_sh1 = (int) row.getCell(7).getNumericCellValue();
				double week5_sh2 = (double) row.getCell(8).getNumericCellValue();
				double week6_sh1 = (double) row.getCell(9).getNumericCellValue();
				double week6_sh2 = (double) row.getCell(10).getNumericCellValue();
				int week1 = (int) row.getCell(11).getNumericCellValue();
				int week2 = (int) row.getCell(12).getNumericCellValue();
				int week3 = (int) row.getCell(13).getNumericCellValue();
				int week4 = (int) row.getCell(14).getNumericCellValue();
				int week5 = (int) row.getCell(15).getNumericCellValue();
				int week6 = (int) row.getCell(16).getNumericCellValue();
				double total = (double) row.getCell(17).getNumericCellValue();

				String sql = "INSERT INTO bigdata VALUES('" + rollno + "','" + week1sh1 + "','" + week1sh2 + "','"
						+ week2sh1 + "','" + Week2sh2 + "','" + week3_sh1_sh2 + "','" + week4_sh1_sh2 + "','"
						+ week5_sh1 + "','" + week5_sh2 + "','" + week6_sh1 + "','" + week6_sh2 + "','" + week1 + "','"
						+ week2 + "','" + week3 + "','" + week4 + "','" + week5 + "','" + week6 + "','" + total + "')";
				pstm = (PreparedStatement) con.prepareStatement(sql);
				pstm.execute();
				System.out.println("Import rows " + i);
			}
			con.commit();
			pstm.close();
			con.close();

			System.out.println("Success import excel to mysql table");
		} catch (ClassNotFoundException e) {
			System.out.println(e);
		} catch (SQLException ex) {
			System.out.println(ex);
		} catch (IOException ioe) {
			System.out.println(ioe);
		}

	}

	private void dbcsresult() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(
					"jdbc:mysql://localhost/dbcsproject?autoReconnect=true&useSSL=false", "root", "Scooby@100200");
			con.setAutoCommit(false);
			PreparedStatement pstm = null;
			String input = "C://Users/hp/Documents/book1.xlsx";
			@SuppressWarnings("resource")
			XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(input));

			XSSFSheet sheet = wb.getSheetAt(0);
			Row row;
			for (int i = 1; i <= 52; i++) {
				row = sheet.getRow(i);

				int rollno = (int) row.getCell(0).getNumericCellValue();
				int week1sh1 = (int) row.getCell(1).getNumericCellValue();
				int week1sh2 = (int) row.getCell(2).getNumericCellValue();
				int week2sh1 = (int) row.getCell(3).getNumericCellValue();
				int Week2sh2 = (int) row.getCell(4).getNumericCellValue();
				int week3_sh1_sh2 = (int) row.getCell(5).getNumericCellValue();
				double week4sh1 = (double) row.getCell(6).getNumericCellValue();
				double week4sh2 = (double) row.getCell(7).getNumericCellValue();
				double week5_sh1 = (double) row.getCell(8).getNumericCellValue();
				double week5_sh2 = (double) row.getCell(9).getNumericCellValue();
				double week6_sh1 = (double) row.getCell(10).getNumericCellValue();
				double week6_sh2 = (double) row.getCell(11).getNumericCellValue();
				int week1 = (int) row.getCell(12).getNumericCellValue();
				int week2 = (int) row.getCell(13).getNumericCellValue();
				int week3 = (int) row.getCell(14).getNumericCellValue();
				int week4 = (int) row.getCell(15).getNumericCellValue();
				int week5 = (int) row.getCell(16).getNumericCellValue();
				int week6 = (int) row.getCell(17).getNumericCellValue();
				double total = (double) row.getCell(18).getNumericCellValue();

				String sql = "INSERT INTO dbcs VALUES('" + rollno + "','" + week1sh1 + "','" + week1sh2 + "','"
						+ week2sh1 + "','" + Week2sh2 + "','" + week3_sh1_sh2 + "','" + week4sh1 + "','" + week4sh2
						+ "','" + week5_sh1 + "','" + week5_sh2 + "','" + week6_sh1 + "','" + week6_sh2 + "','" + week1
						+ "','" + week2 + "','" + week3 + "','" + week4 + "','" + week5 + "','" + week6 + "','" + total
						+ "')";
				pstm = (PreparedStatement) con.prepareStatement(sql);
				pstm.execute();
				System.out.println("Import rows " + i);
			}
			con.commit();
			pstm.close();
			con.close();

			System.out.println("Success import excel to mysql table");
		} catch (ClassNotFoundException e) {
			System.out.println(e);
		} catch (SQLException ex) {
			System.out.println(ex);
		} catch (IOException ioe) {
			System.out.println(ioe);
		}

	}

}