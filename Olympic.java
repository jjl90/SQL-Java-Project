import java.sql.*;
import java.util.*;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
//compile with: javac -cp ojdbc7.jar Olympic.java
//run with: java -cp ojdbc7.jar:. Olympic

public class Olympic {

    private static boolean loggedIn;
    public static String role;
    private static int userID;
    private static final String username = "jjl90";
    private static final String password = "4111358";
    private static final String url = "jdbc:oracle:thin:@class3.cs.pitt.edu:1521:dbclass";

    public static String getRole(){
        return role;
    }

    public static int getID(){
        return userID;
    }

    public static void main(String args[]) throws SQLException {
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
        loggedIn = false;

        // login
        while(!loggedIn){
            Scanner in = new Scanner(System.in); 
            System.out.println("What operation would you like to do?\n1. Login\n2. Exit");
            int loginChoice = in.nextInt();
            if(loginChoice == 1){
                boolean response = false;
                while(!response){
                    Scanner in2 = new Scanner(System.in);
                    System.out.println("Please enter username:");
                    String username = in2.nextLine(); 
                    System.out.println("Please enter password:");
                    String password = in2.nextLine();
                    response = login(connection, username, password);
                }
            }else if(loginChoice == 2){
                break;
            }
            while(role != null){
                System.out.println(role);
                if(role.equals("Organizer")){
                    in = new Scanner(System.in); 
                    System.out.println("\nWhat operation you would like to do?\n1. Create User\n2. Drop User\n3. Create Event\n4. Add Event Outcome\n5. Display Sport\n6. Display Event\n7. Country Ranking\n8. Top K Athletes\n9. Athletes\n10. Logout  ");
                    int option = in.nextInt(); 
                    if(option == 1){ 
                        System.out.println("Creating User . . .\nPlease enter a username:");
                        String createUsername = in.nextLine();
                        System.out.println("Please enter a passkey:");
                        String createPasskey = in.nextLine();
                        System.out.println("Please choose a role for the new user:\n1. Organizer\n2. Coach\n3. Guest\n");
                        int roleID = in.nextInt();
                        createUser(connection, createUsername, createPasskey, roleID);
                    }else if(option == 2){
                        System.out.println("Drop User . . .\nPlease enter a user id:");
                        int droleropUserID = in.nextInt();
                        dropUser(connection, droleropUserID);
                    }else if(option == 3){
                        System.out.println("Create Event . . .\nPlease enter a sport id:");
                        int createSportID = in.nextInt();
                        System.out.println("Please enter a venue id:");
                        int createVenueID = in.nextInt();
                        System.out.println("Please enter the sex:");
                        String sex = in.nextLine();
                        System.out.println("Please enter the date:");
                        String date = in.nextLine();
                        createEvent(connection, createSportID, createVenueID, sex, date);
                    }else if(option == 4){
                        System.out.println("Add Event Outcome . . .\nPlease enter the olympic id:");
                        int olympicID = in.nextInt();
                        System.out.println("Please enter the team id:");
                        int teamID = in.nextInt();
                        System.out.println("Please enter the participant id:");
                        int participantID = in.nextInt();
                        System.out.println("Please enter the position:");
                        int position = in.nextInt();
                        System.out.println("Please enter the event id:");
                        int eventID = in.nextInt();
                        addEventOutcome(connection, olympicID, eventID, teamID, participantID, position);
                    }else if(option == 5){
                        System.out.println("Display Sport . . .");
                        System.out.println("Please enter the sport name: ");
                        String sportName = in.nextLine();
                        displaySport(connection, sportName);
                    }else if(option == 6){
                        System.out.println("Display Event . . .");
                        System.out.println("Please enter the olympic city: ");
                        String cityName = in.nextLine();
                        System.out.println("Please enter the olympic year: ");
                        String year = in.nextLine();
                        System.out.println("Please enter the event id: ");
                        int eventID = in.nextInt();
                        displayEvent(connection, cityName, year, eventID);
                    }else if(option == 7){
                        System.out.println("Country Ranking . . .");
                        System.out.println("Please enter the olympic id: ");
                        int olympicID = in.nextInt();
                        // countryRanking(connection, olympicID);
                    }else if(option == 8){
                        System.out.println("Top k Athletes . . .");
                        System.out.println("Please enter the olympic id: ");
                        int olympicID = in.nextInt();
                        System.out.println("Please enter the number of athletes you want to query:");
                        int athleteNum = in.nextInt();
                        topkAthletes(connection, olympicID, athleteNum);
                    }else if(option == 9){
                        System.out.println("Connected Athletes . . .");
                        System.out.println("Please enter an athlete's id:");
                        int athleteID = in.nextInt();
                        System.out.println("Please enter the olympic id:");
                        int olympicID = in.nextInt();
                        System.out.println("Please enter the number n:");
                        int n = in.nextInt();
                        // connectedAthles(athleteID, olympicID, n);
                    }else if(option == 10){
                        logout();
                    }
                }else if(role.equals("Coach")){
                    in = new Scanner(System.in); 
                    System.out.println("Please choose what operation you want to do:\n1.Create Team\n2. Register Team\n3. Add participant\n4. Add Team Member\n5. Drop Team Member\n6. Display Sport\n7. Display Event\n8. Country Ranking\n9. Top K Athletes\n10. Athletes\n11. Logout");
                    int option = in.nextInt();
                    if(option == 1){
                        System.out.println("Create Team . . . .");
                        System.out.println("Please enter the olympic id:");
                        int olympicID = in.nextInt();
                        System.out.println("Please enter the sport id:");
                        int sportID = in.nextInt();
                        System.out.println("Please enter the country id:");
                        int countryID = in.nextInt();
                        System.out.println("Please enter the team_name:");
                        String team_name = in.nextLine();
                        createTeam(connection, olympicID, sportID, countryID, team_name);
                    }else if(option == 2){
                        System.out.println("Register Team . . .");
                        System.out.println("Please enter the team id:");
                        int teamID = in.nextInt();
                        System.out.println("Please enter the event id:");
                        int eventID = in.nextInt();
                        registerTeam(connection, teamID, eventID);
                    }else if(option == 3){
                        System.out.println("Add Participant . . .");
                        System.out.println("Please enter the first name:");
                        String first_name = in.nextLine();
                        System.out.println("Please enter the last name:");
                        String last_name = in.nextLine();
                        System.out.println("Please enter the nationality:");
                        String nationality = in.nextLine();
                        System.out.println("Please enter the birth place:");
                        String birth_place = in.nextLine();
                        System.out.println("Please enter the date of birth:");
                        String dob = in.nextLine();
                        addParticipant(connection, first_name, last_name, nationality, birth_place, dob);
                    }else if(option == 4){
                        System.out.println("Add Team Member . . .");
                        System.out.println("Please enter the team id:");
                        int teamID = in.nextInt();
                        System.out.println("Please enter the participant id:");
                        int participantID = in.nextInt();
                        addTeamMember(connection, teamID, participantID);
                    }else if(option == 5){
                        System.out.println("Drop Team Member . . .");
                        System.out.println("Please enter the participant id:");
                        int participantID = in.nextInt();
                        dropTeamMember(connection, participantID);
                    }else if(option == 6){
                        System.out.println("Display Sport . . .");
                        System.out.println("Please enter the sport name: ");
                        String sportName = in.nextLine();
                        displaySport(connection, sportName);
                    }else if(option == 7){
                        System.out.println("Display Event . . .");
                        System.out.println("Please enter the olympic city: ");
                        String cityName = in.nextLine();
                        System.out.println("Please enter the olympic year: ");
                        String year = in.nextLine();
                        System.out.println("Please enter the event id: ");
                        int eventID = in.nextInt();
                        displayEvent(connection, cityName, year, eventID);
                    }else if(option == 8){
                        System.out.println("Country Ranking . . .");
                        System.out.println("Please enter the olympic id: ");
                        int olympicID = in.nextInt();
                        // countryRanking(connection, olympicID);
                    }else if(option == 9){
                        System.out.println("Top k Athletes . . .");
                        System.out.println("Please enter the olympic id: ");
                        int olympicID = in.nextInt();
                        System.out.println("Please enter the number of athletes you want to query:");
                        int athleteNum = in.nextInt();
                        topkAthletes(connection, olympicID, athleteNum);
                    }else if(option == 10){
                        System.out.println("Connected Athletes . . .");
                        System.out.println("Please enter an athlete's id:");
                        int athleteID = in.nextInt();
                        System.out.println("Please enter the olympic id:");
                        int olympicID = in.nextInt();
                        System.out.println("Please enter the number n:");
                        int n = in.nextInt();
                        // connectedAthles(athleteID, olympicID, n);
                    }else if(option == 11){
                        logout();
                    }
                }
            }
        }
        

    }

   
    public static void logout(){
        loggedIn = false;
        role = null;
    }

    public static void createUser(Connection connection, String username, String passkey, int role_id){
	    LocalDateTime ldt = LocalDateTime.now().plusDays(1);
        DateTimeFormatter formmat1 = DateTimeFormatter.ofPattern("d MMM uuuu");
        String formatDate = formmat1.format(ldt);
        try {
            connection.setAutoCommit(false);
            PreparedStatement stmt = connection.prepareStatement(
                "INSERT INTO USER_ACCOUNT values(USER_ROLE_SEQUENCE.NEXTVAL,?,?,?,?)"
            ); 
            stmt.setString(1, username);
            stmt.setString(2, passkey);
            stmt.setInt(3, role_id);
            stmt.setString(4, formatDate);
            stmt.executeUpdate();
            connection.commit();
        } catch (SQLException e1) {
            try {
                connection.rollback();
            } catch (SQLException e2) {
                System.out.println(e2.toString());
            }
        }

    }

    public static void dropUser(Connection connection, int userID){
        try {
            connection.setAutoCommit(false);
            PreparedStatement st = connection.prepareStatement("DELETE FROM USER_ACCOUNT WHERE user_id = ?");
            st.setInt(1, userID);
            st.executeUpdate();
            connection.commit();
        } catch (SQLException e1) {
            try {
                connection.rollback();
            } catch (SQLException e2) {
                System.out.println(e2.toString());
            }
        }
    }

    public static void createEvent(Connection connection, int sportID, int venueID, String sex, String eDate){
        java.sql.Date ourJavaDateObject = new java.sql.Date(Calendar.getInstance().getTime().getTime());
        try {
            connection.setAutoCommit(false);
            PreparedStatement stmt = connection.prepareStatement(
                "INSERT INTO EVENT values(EVENT_SEQUENCE.NEXTVAL,?,?,?,?)"
            );
            stmt.setInt(1, sportID);
            stmt.setInt(2, venueID);
            stmt.setString(3,sex);
            stmt.setString(4, eDate);
            stmt.executeUpdate();
            connection.commit();
        } catch (SQLException e1) {
            try {
                connection.rollback();
            } catch (SQLException e2) {
                System.out.println(e2.toString());
            }
        }
    }

    public static void addEventOutcome(Connection connection, int olympicID, int eventID, int teamID, int participantID, int position){
        java.sql.Date ourJavaDateObject = new java.sql.Date(Calendar.getInstance().getTime().getTime());
        try {
            connection.setAutoCommit(false);
            PreparedStatement stmt = connection.prepareStatement(
                "INSERT INTO SCOREBOARD values(?,?,?,?,?,Null)"
            );
            stmt.setInt(1, olympicID);
            stmt.setInt(2, eventID);
            stmt.setInt(3, participantID);
            stmt.setInt(4, teamID);
            stmt.setInt(5, position);
            stmt.executeUpdate();
            connection.commit();
        } catch (SQLException e1) {
            try {
                connection.rollback();
            } catch (SQLException e2) {
                System.out.println(e2.toString());
            }
        }
    }

    public static void createTeam(Connection connection, int olympicID, int sportID, int countryID, String team_name){
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
            stmt.setInt(5, getID());
            stmt.executeUpdate();
            connection.commit();

            addTeamCoach(connection,"TEAM_SEQUENCE.CURRVAL", getID());

        } catch (SQLException e1) {
            try {
                connection.rollback();
            } catch (SQLException e2) {
                System.out.println(e2.toString());
            }
        }



    }

    public static void addTeamCoach(Connection connection, String teamID, int participantID){
        try {
            connection.setAutoCommit(false);
            PreparedStatement stmt = connection.prepareStatement(
                "INSERT INTO TEAM_MEMBER values(?,?)"
            );
            stmt.setString(1,teamID);
            stmt.setInt(2,participantID);
            stmt.executeUpdate();
            connection.commit();
        } catch (SQLException e1) {
            try {
                connection.rollback();
            } catch (SQLException e2) {
                System.out.println(e2.toString());
            }
        }
    }

    public static void registerTeam(Connection connection, int teamID, int eventID){
        try {
            connection.setAutoCommit(false);
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO EVENT_PARTICIPATION values(?,?,?)");
            stmt.setInt(2, teamID);
            stmt.setInt(1, eventID);
            stmt.setString(3,"e");
            stmt.executeUpdate();
            connection.commit();
        } catch (SQLException e1) {
            try {
                connection.rollback();
            } catch (SQLException e2) {
                System.out.println(e2.toString());
            }
        }
    }

    public static void addParticipant(Connection connection, String first_name, String last_name, String nationality, String birth_place, String dob){
        try {
            connection.setAutoCommit(false);
            PreparedStatement stmt = connection.prepareStatement(
                "INSERT INTO PARTICIPANT values(PARTICIPANT_SEQUENCE.NEXTVAL,?,?,?,?,?)"
            );
            stmt.setString(1,first_name);
            stmt.setString(2,last_name);
            stmt.setString(3,nationality);
            stmt.setString(4,birth_place);
            stmt.setString(5,dob);
            stmt.executeUpdate();
            connection.commit();
        } catch (SQLException e1) {
            try {
                connection.rollback();
            } catch (SQLException e2) {
                System.out.println(e2.toString());
            }
        }
    }

    public static void addTeamMember(Connection connection, int teamID, int participantID){
        try {
            connection.setAutoCommit(false);
            PreparedStatement stmt = connection.prepareStatement(
                "INSERT INTO TEAM_MEMBER values(?,?)"
            );
            stmt.setInt(1,teamID);
            stmt.setInt(2,participantID);
            stmt.executeUpdate();
            connection.commit();
        } catch (SQLException e1) {
            try {
                connection.rollback();
            } catch (SQLException e2) {
                System.out.println(e2.toString());
            }
        }
    }

    public static void dropTeamMember(Connection connection, int participantID){
        try {
            connection.setAutoCommit(false);
            PreparedStatement st = connection.prepareStatement(
                "DELETE FROM TEAM_MEMBER WHERE participant_id = ?"
            );
            st.setInt(1,participantID);
            st.executeUpdate();
            connection.commit();
        } catch (SQLException e1) {
            try {
                connection.rollback();
            } catch (SQLException e2) {
                System.out.println(e2.toString());
            }
        }
    }

    public static boolean login(Connection connection, String username, String passkey){

        try {
            connection.setAutoCommit(false);
            
            PreparedStatement stmt = connection.prepareStatement(
                "SELECT USER_ACCOUNT.user_id, USER_ROLE.role_name FROM USER_ACCOUNT INNER JOIN USER_ROLE ON USER_ACCOUNT.role_id = USER_ROLE.role_id WHERE USER_ACCOUNT.username=? AND USER_ACCOUNT.passkey=?"
            );
            stmt.setString(1, username);
            stmt.setString(2, passkey);
            
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                loggedIn = true;
                role = rs.getString("role_name");
                System.out.println("Successful Authentication");
            return true;
            } else {
                System.out.println("Username and/or password not recognized");
                return false;
            }
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

    public static void displaySport(Connection connection, String sport_name){
        try {
            connection.setAutoCommit(false);
            
            PreparedStatement stmt = connection.prepareStatement("SELECT SPORT.dob, EVENT.event_id, EVENT.gender, MEDAL.medal_title, COUNTRY.country FROM EVENT INNER JOIN SPORT ON SPORT.sport_id = EVENT.sport_id INNER JOIN SCOREBOARD ON EVENT.event_id = SCOREBOARD.event_id INNER JOIN MEDAL ON SCOREBOARD.medal_id = MEDAL.medal_id INNER JOIN TEAM ON SCOREBOARD.team_id = TEAM.team_id INNER JOIN COUNTRY ON TEAM.country_id = COUNTRY.country_id WHERE SPORT.sport_name = ?");
            stmt.setString(1, sport_name);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String dob = rs.getString("dob");
                int event_id = rs.getInt("event_id");
                String gender = rs.getString("gender");
                String medal_title = rs.getString("medal_title");
                String country = rs.getString("country");
                System.out.println("Dob: "+dob+" Event ID: "+event_id+" Gender: "+gender+" Medal Title: "+medal_title+" Country:"+country);
            } 
        }
        catch (SQLException e1) {
            try {
                connection.rollback();
            } catch (SQLException e2) {
                System.out.println(e2.toString());
            }
        }
    }

    public static void displayEvent(Connection connection, String olympic_city, String olympic_year, int eventID){
        try {
            connection.setAutoCommit(false);
            
            PreparedStatement stmt = connection.prepareStatement("SELECT OLYMPICS.host_city, EVENT.event_id, SPORT.sport_name, concat(concat(PARTICIPANT.fname,' '),PARTICIPANT.lname) AS  first_last, SCOREBOARD.position, MEDAL.medal_title FROM SCOREBOARD INNER JOIN PARTICIPANT ON SCOREBOARD.participant_id = PARTICIPANT.participant_id INNER JOIN OLYMPICS ON OLYMPICS.olympic_id = SCOREBOARD.olympic_id INNER JOIN EVENT ON EVENT.event_id = SCOREBOARD.event_id INNER JOIN SPORT ON SPORT.sport_id = EVENT.sport_id INNER JOIN MEDAL ON SCOREBOARD.medal_id = MEDAL.medal_ID WHERE OLYMPICS.host_city = ? AND EVENT.event_id = ?");
            stmt.setString(1, olympic_city);
            stmt.setInt(2, eventID);

            
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                String host_city = rs.getString("host_city");
                int event_id = rs.getInt("event_id");
                String sport_name = rs.getString("sport_name");
                String first_last = rs.getString("first_last");
                String position = rs.getString("position");
                String medal_title = rs.getString("medal_title");
                System.out.println("Host City: "+ host_city +" Event ID: "+event_id+" Sport Name: "+sport_name+" Name: "+first_last+" Position: "+position+" Title: "+medal_title);
            }
        }
        catch (SQLException e1) {
            try {
                connection.rollback();
            } catch (SQLException e2) {
                System.out.println(e2.toString());
            }
        }
    }

    public static void countryRanking(Connection connection, int olympic_id){
        try {
            connection.setAutoCommit(false);
            
            PreparedStatement stmt_crank = connection.prepareStatement("SELECT distinct ctry.country_code, ctry.country_id, sum(onceperteam) as country_total, oyear FROM TEAM tm JOIN COUNTRY ctry on tm.country_id=ctry.country_id JOIN (SELECT distinct s.event_id, s.team_id as sttt, m.points as onceperteam FROM SCOREBOARD s JOIN MEDAL m on s.medal_id=m.medal_id) on sttt=tm.team_id JOIN (SELECT cid, EXTRACT(YEAR FROM oly.opening_date) as oyear FROM OLYMPICS oly JOIN (SELECT tm2.country_id as cid, min(tm2.olympic_id) as firstoly FROM TEAM tm2 group by tm2.country_id) on oly.olympic_id=firstoly) on ctry.country_id=cid WHERE tm.olympic_id=? group by ctry.country_id, ctry.country_code, oyear ORDER BY country_total desc");
            stmt_crank.setInt(1, olympic_id);
            PreparedStatement stmt_goldmedal = connection.prepareStatement("SELECT count(MEDAL.medal_id) as gold_medal_count FROM SCOREBOARD INNER JOIN PARTICIPANT ON SCOREBOARD.participant_id = PARTICIPANT.participant_id INNER JOIN TEAM ON TEAM.team_id = SCOREBOARD.team_id INNER JOIN COUNTRY ON COUNTRY.country_id = TEAM.country_id INNER JOIN MEDAL ON SCOREBOARD.medal_id = MEDAL.medal_id INNER JOIN OLYMPICS ON OLYMPICS.olympic_id = SCOREBOARD.olympic_id WHERE MEDAL.medal_id = 1 AND OLYMPICS.olympic_id = ? AND COUNTRY.country_id = ?");
            stmt_goldmedal.setInt(1, olympic_id);
            PreparedStatement stmt_silvermedal = connection.prepareStatement("SELECT count(MEDAL.medal_id) as silver_medal_count FROM SCOREBOARD INNER JOIN PARTICIPANT ON SCOREBOARD.participant_id = PARTICIPANT.participant_id INNER JOIN TEAM ON TEAM.team_id = SCOREBOARD.team_id INNER JOIN COUNTRY ON COUNTRY.country_id = TEAM.country_id INNER JOIN MEDAL ON SCOREBOARD.medal_id = MEDAL.medal_id INNER JOIN OLYMPICS ON OLYMPICS.olympic_id = SCOREBOARD.olympic_id WHERE MEDAL.medal_id = 2 AND OLYMPICS.olympic_id = ? AND COUNTRY.country_id = ?");
            stmt_silvermedal.setInt(1, olympic_id);
            PreparedStatement stmt_bronzemedal = connection.prepareStatement("SELECT count(MEDAL.medal_id) as bronze_medal_count FROM SCOREBOARD INNER JOIN PARTICIPANT ON SCOREBOARD.participant_id = PARTICIPANT.participant_id INNER JOIN TEAM ON TEAM.team_id = SCOREBOARD.team_id INNER JOIN COUNTRY ON COUNTRY.country_id = TEAM.country_id INNER JOIN MEDAL ON SCOREBOARD.medal_id = MEDAL.medal_id INNER JOIN OLYMPICS ON OLYMPICS.olympic_id = SCOREBOARD.olympic_id WHERE MEDAL.medal_id = 3 AND OLYMPICS.olympic_id = ? AND COUNTRY.country_id = ?");
            stmt_bronzemedal.setInt(1, olympic_id);
            ResultSet rs = stmt_crank.executeQuery();

            while(rs.next()) {
                int country_id = rs.getInt("COUNTRY_ID");
                int country_total = rs.getInt("COUNTRY_TOTAL");
                int olympic_year = rs.getInt("OYEAR");
                String cou = rs.getString("COUNTRY_CODE");

                stmt_goldmedal.setInt(2, country_id);
                stmt_silvermedal.setInt(2, country_id);
                stmt_bronzemedal.setInt(2, country_id);

                int gold_medal_count = 0;
                int silver_medal_count = 0;
                int bronze_medal_count = 0;
                
                ResultSet rs2 = stmt_goldmedal.executeQuery();
                                                
                if (rs2.next()) {
                    gold_medal_count = rs2.getInt("gold_medal_count");
                }
                ResultSet rs3 = stmt_silvermedal.executeQuery();
                
                if (rs3.next()) {
                    silver_medal_count = rs3.getInt("silver_medal_count");
                }
                ResultSet rs4 = stmt_bronzemedal.executeQuery();
                if (rs4.next()) {
                    bronze_medal_count = rs4.getInt("bronze_medal_count");
                }
                System.out.println("Country Abreviation: "+cou+" Year: "+olympic_year+" Total Medals: "+country_total+" Gold Medal Count: "+ gold_medal_count+" Bronze Medal Count: "+ bronze_medal_count+" Silver Medal Count: "+ silver_medal_count);
            }
        }
        catch (SQLException e1) {
            try {
                connection.rollback();
            } catch (SQLException e2) {
                System.out.println(e2.toString());
            }
        }
    }

    public static void topkAthletes(Connection connection, int olympic_id, int k){
        try {
            connection.setAutoCommit(false);
            PreparedStatement stmt_init = connection.prepareStatement("SELECT PARTICIPANT.participant_id, concat(concat(PARTICIPANT.fname,' '),PARTICIPANT.lname) AS  first_last, sum(MEDAL.POINTS) AS medal_points FROM SCOREBOARD INNER JOIN PARTICIPANT ON SCOREBOARD.participant_id = PARTICIPANT.participant_id INNER JOIN MEDAL ON SCOREBOARD.medal_id = MEDAL.medal_id INNER JOIN OLYMPICS ON OLYMPICS.olympic_id = SCOREBOARD.olympic_id WHERE OLYMPICS.olympic_id = ? GROUP BY PARTICIPANT.participant_id, concat(concat(PARTICIPANT.fname,' '),PARTICIPANT.lname) ORDER BY medal_points DESC FETCH FIRST ? ROWS ONLY");
            stmt_init.setInt(1, olympic_id);
            stmt_init.setInt(2, k);

            PreparedStatement stmt_goldmedal = connection.prepareStatement("SELECT count(MEDAL.medal_id) as gold_medal_count FROM SCOREBOARD INNER JOIN PARTICIPANT ON SCOREBOARD.participant_id = PARTICIPANT.participant_id INNER JOIN MEDAL ON SCOREBOARD.medal_id = MEDAL.medal_id INNER JOIN OLYMPICS ON OLYMPICS.olympic_id = SCOREBOARD.olympic_id WHERE MEDAL.medal_id = 1 AND OLYMPICS.olympic_id = ? AND PARTICIPANT.participant_id = ?");
            stmt_goldmedal.setInt(1, olympic_id);

            PreparedStatement stmt_silvermedal = connection.prepareStatement("SELECT count(MEDAL.medal_id) as silver_medal_count FROM SCOREBOARD INNER JOIN PARTICIPANT ON SCOREBOARD.participant_id = PARTICIPANT.participant_id INNER JOIN MEDAL ON SCOREBOARD.medal_id = MEDAL.medal_id INNER JOIN OLYMPICS ON OLYMPICS.olympic_id = SCOREBOARD.olympic_id WHERE MEDAL.medal_id = 2 AND OLYMPICS.olympic_id = ? AND PARTICIPANT.participant_id = ?");
            stmt_silvermedal.setInt(1, olympic_id);

            PreparedStatement stmt_bronzemedal = connection.prepareStatement("SELECT count(MEDAL.medal_id) as bronze_medal_count FROM SCOREBOARD INNER JOIN PARTICIPANT ON SCOREBOARD.participant_id = PARTICIPANT.participant_id INNER JOIN MEDAL ON SCOREBOARD.medal_id = MEDAL.medal_id INNER JOIN OLYMPICS ON OLYMPICS.olympic_id = SCOREBOARD.olympic_id WHERE MEDAL.medal_id = 3 AND OLYMPICS.olympic_id = ? AND PARTICIPANT.participant_id = ?");
            stmt_bronzemedal.setInt(1, olympic_id);

            ResultSet rs = stmt_init.executeQuery();
            while (rs.next()) {
                String name = rs.getString("first_last"); 
                int participant_id = rs.getInt("participant_id");
                int medal_points = rs.getInt("medal_points");

                stmt_goldmedal.setInt(2, participant_id);
                stmt_silvermedal.setInt(2, participant_id);
                stmt_bronzemedal.setInt(2, participant_id);
                int gold_medal_count = 0;
                int silver_medal_count = 0;
                int bronze_medal_count = 0;
                ResultSet rs2 = stmt_goldmedal.executeQuery();
               
                if (rs2.next()) {
                    gold_medal_count = rs2.getInt("gold_medal_count");
                }
                ResultSet rs3 = stmt_silvermedal.executeQuery();
                
                if (rs3.next()) {
                    silver_medal_count = rs3.getInt("silver_medal_count");
                }
                ResultSet rs4 = stmt_bronzemedal.executeQuery();
                if (rs4.next()) {
                    bronze_medal_count = rs4.getInt("bronze_medal_count");
                }
                System.out.println("Name:"+name+" || Points: "+ medal_points+" Gold Medal Count: "+ gold_medal_count+" Bronze Medal Count: "+ bronze_medal_count+" Silver Medal Count: "+ silver_medal_count);
            }
        }
        catch (SQLException e1) {
            try {
                connection.rollback();
            } catch (SQLException e2) {
                System.out.println(e2.toString());
            }
        }
    }

    // public static void connectedAthles(Connection connection, int participant_id, int olympic_id, int n){
    //     try {
    //         connection.setAutoCommit(false);
            
    //         PreparedStatement stmt = connection.prepareStatement("SELECT COUNTRY.country_code, "
            
    //         );
            
            
    //         ResultSet rs = stmt.executeQuery();
    //         if (rs.next()) {

    //         }
    //     }
    //     catch (SQLException e1) {
    //         try {
    //             connection.rollback();
    //         } catch (SQLException e2) {
    //             System.out.println(e2.toString());
    //         }
    //     }
    // }

}