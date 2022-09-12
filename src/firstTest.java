//import java.sql.*;
//
///**
// *
// * @author www.luv2code.com
// *
// */
//public class firstTest {
//
//    public static void main(String[] args) throws SQLException {
//
//        Connection myConn = null;
//        Statement myStmt = null;
//        ResultSet myRs = null;
//
//        try {
//            // 1. Get a connection to database
//            myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/demo", "test" , "mariam123456789");
//
//            System.out.println("Database connection successful!\n");
//
//            // 2. Create a statement
//            myStmt = myConn.createStatement();
//
//            // 3. Execute SQL query
////             int m = myStmt.executeUpdate("insert into employees (last_name,first_name,email,department,salary) values ('mohamed','mariam','memo12@gmail.com','hr',36000)");
////            int m = myStmt.executeUpdate("delete from employees where last_name ='mohamed'");
//            myRs = myStmt.executeQuery("select * from employees");
//
//            // 4. Process the result set
//            while (myRs.next()) {
//                System.out.println(myRs.getString("last_name") + ", " + myRs.getString("first_name"));
//            }
//        }
//        catch (Exception exc) {
//            exc.printStackTrace();
//        }
//        finally {
//            if (myRs != null) {
//                myRs.close();
//            }
//
//            if (myStmt != null) {
//                myStmt.close();
//            }
//
//            if (myConn != null) {
//                myConn.close();
//            }
//        }
//    }
//
//}
//######################################################################################################################
//
//PreparedStatement
import java.io.FileInputStream;
import java.sql.*;
import java.util.Properties;

public class firstTest {

    public static void main(String[] args) throws SQLException {

        Connection myConn = null;
        PreparedStatement myStmt = null;
        ResultSet myRs = null;

        try {
            // 1. Get a connection to database
            Properties props = new Properties();
            props.load(new FileInputStream("demo.properties"));


            String theUser = props.getProperty("user");
            String thePassword = props.getProperty("password");
            String theDburl = props.getProperty("dburl");


            // 3. Get a connection to database
            myConn = DriverManager.getConnection(theDburl, theUser, thePassword);

            // 2. Prepare statement
            myStmt = myConn.prepareStatement("select * from employees where salary > ? and department=?");

            // 3. Set the parameters
            myStmt.setDouble(1, 80000);
            myStmt.setString(2, "Legal");

            // 4. Execute SQL query
            myRs = myStmt.executeQuery();

            // 5. Display the result set
            display(myRs);

            //
            // Reuse the prepared statement:  salary > 25000,  department = HR
            //

            System.out.println("\n\nReuse the prepared statement:  salary > 25000,  department = HR");

            // 6. Set the parameters
            myStmt.setDouble(1, 25000);
            myStmt.setString(2, "HR");

            // 7. Execute SQL query
            myRs = myStmt.executeQuery();

            // 8. Display the result set
            display(myRs);


        }
        catch (Exception exc) {
            exc.printStackTrace();
        }
        finally {
            if (myRs != null) {
                myRs.close();
            }

            if (myStmt != null) {
                myStmt.close();
            }

            if (myConn != null) {
                myConn.close();
            }
        }
    }

    private static void display(ResultSet myRs) throws SQLException {
        while (myRs.next()) {
            String lastName = myRs.getString("last_name");
            String firstName = myRs.getString("first_name");
            double salary = myRs.getDouble("salary");
            String department = myRs.getString("department");

            System.out.printf("%s, %s, %.2f, %s\n", lastName, firstName, salary, department);
        }
    }
}
