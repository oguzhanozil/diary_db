package gunluk;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

import javax.print.attribute.standard.PrinterLocation;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws Exception
    {
        System.out.println("1 for new diary, 2 for edit existing diary,3 for delete an exising diary");
        Scanner s0 = new Scanner(System.in);
        int res0 = s0.nextInt();
            if (res0 == 1)
            {
                System.out.println("Enter the date: ");
                Scanner s1 = new Scanner(System.in);
                String date = s1.nextLine();
                System.out.println("Enter your day: ");
                Scanner s2 = new Scanner(System.in);
                String day = s2.nextLine();
                gunluk gunluk=new gunluk(date,day);
                insertInto(gunluk);
                s1.close();
                s2.close();

            }
            else if (res0==2)
            {
                
            System.out.println("Please enter the date you want to change !");
            Scanner s3= new Scanner(System.in);
            String changeDate = s3.nextLine();
            Connection c =  DriverManager.getConnection("jdbc:mysql://localhost:3306/gunluk","root","1234");
              //  System.out.println("Connection is succesful !");
                Statement s = (Statement) c.createStatement();
                ResultSet r = s.executeQuery("SELECT * FROM gunluk");
            while (r.next())
            {
               if (r.getString("tarih").equals(changeDate))
               {
                break;
               }
            }
            System.out.println(r.getString("gunun"));
            System.out.println("Plase enter the part you want to change");
            Scanner s4 = new Scanner(System.in);
            String oldGunun = s4.nextLine();
            System.out.println("Plase write the new part");
            Scanner s5 = new Scanner(System.in);
            String newGunun = s4.nextLine();

            String finalGunun = r.getString("gunun").replace(oldGunun,newGunun);
            String query = "update gunluk set gunun = ? where tarih = ?";
            PreparedStatement ps = c.prepareStatement(query);
            ps.setString(1,finalGunun);
            ps.setString(2, changeDate);
            ps.executeUpdate();

            s3.close();
              

            }
            else if (res0==3)
            {
                System.out.println("Please enter the date you want to DELETE !");
                Scanner s3= new Scanner(System.in);
                String deleteDate = s3.nextLine();
                Connection c =  DriverManager.getConnection("jdbc:mysql://localhost:3306/gunluk","root","1234");
                PreparedStatement pst = c.prepareStatement("DELETE FROM gunluk WHERE tarih=?");
                pst.setString(1, deleteDate);
                pst.executeUpdate();
            }
            else 
            {
                System.out.println("You entered wrong number !");
            }
        
    }
    public static void insertInto(gunluk gunluk) throws Exception
    {
        Connection c =  DriverManager.getConnection("jdbc:mysql://localhost:3306/gunluk","root","1234");
        String query="Insert into gunluk(tarih,gunun) values('"+gunluk.tarih+"','"+gunluk.gunun+"')";
        Statement s = (Statement) c.createStatement();
        s.execute(query);

    }
   
}

