package component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.PreparedStatement;

/**
 * @author wvvliet / pvvliet
 * heavily refactored in december 2008 by linda
 */
public class Database {

  private static Connection con = null;
  private static boolean closeOnError = false,  connectionActive = false;


  /**
   *  Open een connectie naar een database. Deze connectie hoeft maar 1 keer
   *  tot stand worden gebracht en kan het gehele programma lang actief
   *  blijven.
   *  @param database De naam van de database zoals hij onder ODBC gekend staat
   *  @param user De gebruikersnaam zoals bekend binnen de database
   *  @param password Het wachtwoord zoals bekend binnen de database
   *  @return de status van de verbinding (true = geopend, false = gesloten)
   */
  public static boolean openConnection(String database, String user, String password) {
    if (!connectionActive) {
      try {

//        // odbc connectie:        
//        Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
//        con = DriverManager.getConnection( "jdbc:odbc:"+database, user, password );

        // mysql connectie:        
        Class.forName("com.mysql.jdbc.Driver");
        con = DriverManager.getConnection("jdbc:mysql://localhost/" + database + "?" +
                "user=" + user + "&password=" + password);

        connectionActive = true;
      } catch (SQLException e) {
        System.out.println(e.getMessage() + "\n" + e.getSQLState() + "\n" + e.getErrorCode());
        if (closeOnError) {
          System.exit(100);
        }
      } catch (ClassNotFoundException e) {
        System.out.println("driver voor database " + database + " is niet gevonden.");
        System.out.println(e.getMessage());
        if (closeOnError) {
          System.exit(100);
        }
      }
    }
    return connectionActive;
  }

  /**
   *  Geeft aan of er een verbinding is met de database of niet.
   *  @return de status van de verbinding (true = geopend, false = gesloten)
   */
  public static boolean getDatabaseStatus() {
    return connectionActive;
  }

  /**
   *  De applicatie afsluiten bij een database connectie probleem?
   *  @param value default waarde is false
   */
  public static void setCloseOnError(boolean value) {
    closeOnError = value;
  }

  /**
   *  Sluit de verbinding met de database. Dit is meestal pas verstandig bij
   *  het afsluiten van de applicatie. Eerder sluiten kan problemen veroor-
   *  zaken.
   *  @return de status van de actie  (true = gelukt, false = mislukt)
   */
  public static boolean closeConnection() {
    if (connectionActive) {
      try {
        con.close();
        connectionActive = false;
      } catch (SQLException e) {
        System.out.println(e.getMessage() + "\n" + e.getSQLState() + "\n" + e.getErrorCode());
        System.out.println("Database connectie niet beschikbaar om te sluiten");
      }
    }
    return !connectionActive;
  }
  
  /**
   * 
   * @param query
   * @return returns a String representation of the first column 
   * of the first record returned by this query
   */
  public static String executeOneColumnSelectQuery(String query) {
      String value = null;
    try {
      ResultSet r = executeSelectQuery(query);
      r.next();
      value = r.getString(1);
    } catch (SQLException e) {
      System.out.println("Database: executeOneColumnSelectQuery: " + e.getMessage());
    }
      return value;
  }

  /**
   *  Uitvoeren van een SELECT query.
   *  @param query de SQL query die uitgevoerd moet worden
   *  @return Een java.sql.resultset met het resultaat van de query
   */
  public static ResultSet executeSelectQuery(String query) {
    PreparedStatement stmt;
    try {
      stmt = con.prepareStatement(query);
      return stmt.executeQuery();
    } catch (SQLException e) {
      System.out.println("Method executeSelectQuery: Query bevat een fout.\nQuery: " + query);
      System.out.println(e.getMessage() + "\n" + e.getSQLState() + "\n" + e.getErrorCode());
    }
    return null;
  }

  /**
   *  Uitvoeren van een INSERT statement met een eigen samengestelde query.
   *  @param query De SQL query die uitgevoerd moet worden.
   *  @return -1 wanneer insert mislukt is, 0 wanneer er niets ingevoerd is.
   */
  public static int executeInsertQuery(String query) {
    return executeUpdateQuery(query);
  }

  /**
   *  Uitvoeren van een UPDATE statement met een eigen samengestelde query
   *  @param query de SQL query die uitgevoerd moet worden
   *  @return -1 wanneer update niet lukt en 0 wanneer update geen regels heeft veranderd
   */
  public static int executeUpdateQuery(String query) {
    PreparedStatement stmt;
    try {
      stmt = con.prepareStatement(query);
      return stmt.executeUpdate();
    } catch (SQLException e) {
      System.out.println("Method executeUpdateQuery: Query bevat een fout.\nQuery: " + query);
      System.out.println(e.getMessage() + "\n" + e.getSQLState() + "\n" + e.getErrorCode());
    }
    return -1;
  }

  /**
   *  Uitvoeren van een DELETE statement met een eigen samengestelde query
   *  @param query de SQL query die uitgevoerd moet worden
   *  @return -1 wanneer update niet lukt en 0 wanneer update geen regels heeft verwijderd
   */
  public static int executeDeleteQuery(String query) {
    return executeUpdateQuery(query);
  }

  
}

