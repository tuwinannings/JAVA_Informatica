
/* Model voor een Docent. 
 * 
 * schema for the modeltable academie.sql
 */
package model;

import component.Database;

/**
 * 
 * @author linda
 * 
 */
public class Sportweek {

    int code, weeknummer, jaar;
    String naam;

    public boolean insert() {
        boolean successful = false;
        String query = "Insert into sportweek(sportweekcode, weeknummer, naam, jaar) " +
                "values(" + code + ", " + weeknummer + ", '" + naam + "', " + jaar + ")";

        if (Database.executeInsertQuery(query) > 0) {
            successful = true;
        }
        return successful;
    }

    public boolean delete() {
        boolean successful = false;
        String query = "delete from sportweek where sportweekcode ='" + this.code + "'";
        if (Database.executeDeleteQuery(query) == 1) {
            successful = true;
        }
        return successful;
    }

    public boolean update() {
        boolean successful = false;
        String query = "update sportweek set naam = '" + naam + "', jaar = " + jaar + ", week = " + jaar + " where code = " + code;
        if (Database.executeUpdateQuery(query) == 1) {
            successful = true;
        }
        return successful;
    }
}