import java.sql.*;
import java.util.*;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

//compile with: javac -cp ojdbc7.jar Olympic.java Driver.java
//run with: java -cp ojdbc7.jar:. Driver
//NOTE: YOU MUST RUN ALL SQL FILES BEFORE RUNNING THE Driver.java

public class Driver {

    private static final String username = "jjl90";
    private static final String password = "4111358";
    private static final String url = "jdbc:oracle:thin:@class3.cs.pitt.edu:1521:dbclass";

    public static void main(String[] args) {
        Olympic olympic = new Olympic();

        Connection connection = null;
        try {
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            connection = DriverManager.getConnection(url, username, password);
            connection.setAutoCommit(true);
            connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
        } catch (Exception e) {
            System.out.println(
                    "Error connecting to database. Printing stack trace: ");
            e.printStackTrace();
        }
        //Create User
        System.out.println("Creating User . . .");
        olympic.createUser(connection, "Driver Test User","passkeyz", 1);
        displayUser(connection);

        //Drop User
        System.out.println("\nDroping Second User . . .");
        olympic.dropUser(connection,2);
        displayUser(connection);

        //Create Event
        System.out.println("\nCreating Event . . .");
        System.out.println("\nBefore:");
        displayEvent(connection);
        olympic.createEvent(connection, 3, 2, "M", "14-Aug-2004");
        System.out.println("\nAfter:");
        displayEvent(connection);
        
        //Add Event Outcome
        System.out.println("\nAdding Event Outcome . . .");
        System.out.println("\nBefore:");
        displayScoreboard(connection);
        olympic.addEventOutcome(connection, 1, 1, 1, 1, 1);
        System.out.println("\nAfter:");
        displayScoreboard(connection);

        //Create Team
        System.out.println("\nCreating Team . . .");
        System.out.println("\nBefore:");
        displayTeam(connection);
        createTeam(olympic, connection, 1,3,1,"Driver Test Team");
        System.out.println("\nAfter:");
        displayTeam(connection);

        //Register Team not working
        System.out.println("\nRegistering Team . . .");
        System.out.println("\nBefore:");
        displayRegisterTeam(connection);
        olympic.registerTeam(connection, 1, 1);
        System.out.println("\nAfter:");
        displayRegisterTeam(connection);

        //Add Participant
        System.out.println("\nAdding Participant . . .");
        System.out.println("\nBefore:");
        displayParticipant(connection);
        olympic.addParticipant(connection, "Driver Test","Name", "nationality", "Driver Place", "14-Aug-1999");
        System.out.println("\nAfter:");
        displayParticipant(connection);

        //Add Team Member
        System.out.println("\nAdding Team Member . . .");
        olympic.addTeamMember(connection, 1, 1);
        displayAddTeamMember(connection);

        //Drop Team Member
        System.out.println("\nDropping Team Member . . .");
        olympic.dropTeamMember(connection, 1);
        displayAddTeamMember(connection);

        //Log In
        System.out.println("\nLogging In . . .");
        olympic.login(connection, "testusername", "password");

        //Displaying Sport
        System.out.println("\nDisplaying Sport . . .");
        olympic.displaySport(connection, "Volleyball");

        //Display Event
        System.out.println("\nDisplaying Event . . .");
        olympic.displayEvent(connection, "Athens", "2004", 1);

        //Display Country Ranking
        System.out.println("\nDisplay Country Ranking . . .");
        olympic.countryRanking(connection, 1);

        //Display Top K Athletes
        System.out.println("\nDisplay Top K Athletes . . .");
        olympic.topkAthletes(connection, 1, 2);

        //Display Connected Athletes
        System.out.println("\nDisplaying Connected Athletes . . . ");
        System.out.println("oof :(");
    }

    public static boolean displayUser(Connection connection){
        System.out.println("Displaying User Table:");
        try {
            connection.setAutoCommit(false);
            
            PreparedStatement stmt = connection.prepareStatement(
                "SELECT USER_ACCOUNT.username FROM USER_ACCOUNT"
            );
            
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String username = rs.getString("username");
                System.out.println("Username: "+username);
            }
            return true;
        }
        catch (SQLException e1) {
            try {
                connection.rollback();
            } catch (SQLException e2) {
                System.out.println(e2.toString());
            }
            return false;
        }
    }

    public static boolean displayEvent(Connection connection){
        System.out.println("Displaying Event Table:");
        try {
            connection.setAutoCommit(false);
            
            PreparedStatement stmt = connection.prepareStatement(
                "SELECT EVENT.event_id FROM EVENT"
            );
            
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String eventID = rs.getString("event_id");
                System.out.println("Event ID: "+eventID);
            }
            return true;
        }
        catch (SQLException e1) {
            try {
                connection.rollback();
            } catch (SQLException e2) {
                System.out.println(e2.toString());
            }
            return false;
        }
    }

    public static boolean displayScoreboard(Connection connection){
        System.out.println("Displaying Scoreboard Table:");
        try {
            connection.setAutoCommit(false);
            
            PreparedStatement stmt = connection.prepareStatement(
                "SELECT SCOREBOARD.olympic_id, SCOREBOARD.participant_id, SCOREBOARD.event_id, SCOREBOARD.team_id FROM SCOREBOARD"
            );
            
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String olympicID = rs.getString("olympic_id");
                String particiapntID = rs.getString("participant_id");
                String eventID = rs.getString("event_id");
                String teamID = rs.getString("team_id");
                System.out.println("Olympic ID:"+olympicID+" Participant ID: "+particiapntID+" Event ID: "+eventID+" Team ID: "+teamID);
            }
            return true;
        }
        catch (SQLException e1) {
            try {
                connection.rollback();
            } catch (SQLException e2) {
                System.out.println(e2.toString());
            }
            return false;
        }
    }

    public static boolean displayTeam(Connection connection){
        System.out.println("Displaying Team Table:");
        try {
            connection.setAutoCommit(false);
            
            PreparedStatement stmt = connection.prepareStatement(
                "SELECT TEAM.team_id FROM TEAM"
            );
            
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String teamID = rs.getString("team_id");
                System.out.println("Team ID: "+teamID);
            }
            return true;
        }
        catch (SQLException e1) {
            try {
                connection.rollback();
            } catch (SQLException e2) {
                System.out.println(e2.toString());
            }
            return false;
        }
    }

    //createTeam from Olmypic.java involves the participantID from the logged in user therefore I copied it here and replaced the value
    public static void createTeam(Olympic olympic, Connection connection, int olympicID, int sportID, int countryID, String team_name){
        java.sql.Date ourJavaDateObject = new java.sql.Date(Calendar.getInstance().getTime().getTime());
        try {
            connection.setAutoCommit(false);
            PreparedStatement stmt = connection.prepareStatement(
                "INSERT INTO TEAM values(TEAM_SEQUENCE.NEXTVAL,?,?,?,?,?)"
            );
            stmt.setInt(1, olympicID);
            stmt.setString(2, team_name);
            stmt.setInt(3, countryID);
            stmt.setInt(4, sportID);
            // stmt.setInt(5, getID());
            stmt.setInt(5, 1);
            stmt.executeUpdate();
            connection.commit();

            olympic.addTeamCoach(connection,"TEAM_SEQUENCE.CURRVAL", 1);

        } catch (SQLException e1) {
            try {
                connection.rollback();
            } catch (SQLException e2) {
                System.out.println(e2.toString());
            }
        }



    }

    public static boolean displayRegisterTeam(Connection connection){
        System.out.println("Displaying Register Team:");
        try {
            connection.setAutoCommit(false);
            
            PreparedStatement stmt = connection.prepareStatement("SELECT EVENT_PARTICIPATION.event_id, EVENT_PARTICIPATION.team_id, EVENT_PARTICIPATION.status FROM EVENT_PARTICIPATION");
            
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String eventID = rs.getString("event_id");
                String teamID = rs.getString("team_id");
                String status = rs.getString("status");
                System.out.println("Team ID: "+teamID+" Event ID: "+eventID+" Status: "+status);
            }
            return true;
        }
        catch (SQLException e1) {
            try {
                connection.rollback();
            } catch (SQLException e2) {
                System.out.println(e2.toString());
            }
            return false;
        }
    }

    public static boolean displayParticipant(Connection connection){
        System.out.println("Displaying Participant Table:");
        try {
            connection.setAutoCommit(false);
            
            PreparedStatement stmt = connection.prepareStatement(
                "SELECT PARTICIPANT.fname FROM PARTICIPANT"
            );
            
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String fname = rs.getString("fname");
                System.out.println("Name: "+fname);
            }
            return true;
        }
        catch (SQLException e1) {
            try {
                connection.rollback();
            } catch (SQLException e2) {
                System.out.println(e2.toString());
            }
            return false;
        }
    }

    public static boolean displayAddTeamMember(Connection connection){
        System.out.println("Displaying Team Member:");
        try {
            connection.setAutoCommit(false);
            
            PreparedStatement stmt = connection.prepareStatement(
                "SELECT TEAM_MEMBER.team_id, TEAM_MEMBER.participant_id FROM TEAM_MEMBER"
            );
            
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String participantID = rs.getString("participant_id");
                String teamID = rs.getString("team_id");
                System.out.println("Team ID: "+teamID+" Participant ID: "+participantID);
            }
            return true;
        }
        catch (SQLException e1) {
            try {
                connection.rollback();
            } catch (SQLException e2) {
                System.out.println(e2.toString());
            }
            return false;
        }
    }

}