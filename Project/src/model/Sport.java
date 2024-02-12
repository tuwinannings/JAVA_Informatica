/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import component.Database;

/**
 *
 * @author Robin
 */
public class Sport {

    int code, prijs, groepsGrootte;
    String naam;

    public boolean insert() {
        boolean successful = false;
        String query = "Insert into sport(sportcode, naam, prijs, maximaleGroepsgrootte) "
                + "values(" + code + ", " + prijs + ", '" + naam + "', " + groepsGrootte + ")";

        if (Database.executeInsertQuery(query) > 0) {
            successful = true;
        }
        return successful;
    }

    public boolean delete() {
        boolean successful = false;
        String query = "delete from sport where sportcode ='" + this.code + "'";
        if (Database.executeDeleteQuery(query) == 1) {
            successful = true;
        }
        return successful;
    }

    public boolean update() {
        boolean successful = false;
        String query = "update sport set naam = '" + naam + "', prijs = " + prijs + ", groepsgrootte = " + groepsGrootte + " where code = " + code;
        if (Database.executeUpdateQuery(query) == 1) {
            successful = true;
        }
        return successful;
    }
}
