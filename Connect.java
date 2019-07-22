package excepetion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Scanner;

import javax.naming.NameNotFoundException;

public class Connect {
	Connect() {

		connect();
	}

	public Connection con;

	public void connect() {

		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/polling", "root", "root");
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	static void validName(String name) throws Exception {
		for (int i = 0; i < name.length(); i++) {
			char c = name.charAt(i);
			if (!(c >= 'A' && c <= 'Z') && !(c >= 'a' && c <= 'z'))
				throw new Exception("name is correct");
			else
				System.out.println("re-enter the name");
		}
	}

	static void validAge(int age) throws Exception {

		if (age >= 15 && age <= 25)

			throw new Exception("your age is correct");
		else

			System.out.println("Age is not between 15 and 25. please ReEnter the Age");
	}

	public void addStudent(Student student) {

		try {

			validName(student.getName());

			validAge(student.getAge());

			String query = " insert into student(rollno,name,age,course)" + " values (?, ?, ?, ? )";
			System.out.println(" records updated");
			PreparedStatement pStmt = con.prepareStatement(query);
			pStmt.setString(1, student.getRollno());
			pStmt.setString(2, student.getName());
			pStmt.setInt(3, student.getAge());
			pStmt.setString(4, student.getCourse());
			pStmt.execute();
			con.close();

		} catch (Exception e) {
			System.err.println("Got an exception!");
			System.err.println(e.getMessage());
		}
	}

	public void upStudent(Student student) {
		try {
			PreparedStatement pStmt = con.prepareStatement("update student set name=?,age=?,course=? where rollno=?");

			pStmt.setString(4, student.getRollno());
			pStmt.setString(1, student.getName());
			pStmt.setInt(2, student.getAge());
			pStmt.setString(3, student.getCourse());
			pStmt.executeUpdate();
			System.out.println(" records updated");
		} catch (Exception e) {
			System.err.println("Got an exception! ");
			System.err.println(e.getMessage());
		}
	}

	public void delStudent(String rollno) {

		try {

			PreparedStatement ps = con.prepareStatement("delete from student where rollno=?");

			ps.setString(1, rollno);

			ps.executeUpdate();
		} catch (Exception e) {
			System.err.println("Got an exception! ");
			System.err.println(e.getMessage());
		}
	}
}