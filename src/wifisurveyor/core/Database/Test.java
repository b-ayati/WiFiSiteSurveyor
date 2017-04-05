package wifisurveyor.core.Database;

/**
 * Created by alireza on 3/13/17.
 */
public class Test {
    public static void main(String args[]) {
        System.out.printf("ali%d",10);
//        Connection c = null;
//        Statement stmt = null;
//        try {
//            Class.forName("org.postgresql.Driver");
//            c = DriverManager
//                    .getConnection("jdbc:postgresql://git.ce.sharif.edu:5432/site_survey_db",
//                            "survey_app", "surveyMIGirim10");
//            c.setAutoCommit(false);
//            System.out.println("Opened database successfully");
//            stmt = c.createStatement();
//            String sql = "INSERT INTO survey_data (coordinate, log_time, plan, user_name, survey_name, mac, channel, ssid, readings) VALUES (point(0.12,0.1), '2017-03-12 13:56:42.75', 'floor-8', 'group-2', 'exp#1', macaddr('00-1A-2D-01-23-45'), 6, 'CE_WLAN', '{-25.5}');";
//            stmt.execute(sql);
//            c.commit();
//            ResultSet rs = stmt.executeQuery( "SELECT * FROM survey_data where coordinate = point(0.12,0.1);" );
//            while ( rs.next() ) {
//                String  name = rs.getString("coordinate");
//
//                System.out.println( "NAME = " + name );
//
//                System.out.println();
//            }
//            c.close();
//            stmt.close();
//        } catch (Exception e) {
//            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
//            System.exit(0);
//        }
//        System.out.println("Records created successfully");
    }
}
