/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testing2;

/**
 *
 * @author shrey
 */
public class populate {
    DBMSUtils db=new DBMSUtils();
    public static void main(String args[])
    {
        Driver d=new Driver("5");
        d.loc="E";
        populate obj=new populate();
        obj.db.createNewUser(d);
    }
}
