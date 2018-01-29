/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jaeohaserverAdmin;

import database.connection.DatabaseConnectionHandler;
import databaseclasses.Users;
import static java.lang.Thread.sleep;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;

/**
  * ServerFunctions class contains all methods and threads that need to get data 
  * from database and threads that is responsible for updateing data that is showed in 
  * admin dashboard 
  *  
 * @author omnia samy
 */

public class ServerFunctions implements Runnable {

    private ArrayList<String> online = new ArrayList<String>();
    private ArrayList<String> offline = new ArrayList<String>();
    private ArrayList<String> countries = new ArrayList<String>();
    private ArrayList<Integer> count = new ArrayList<Integer>();
    private ArrayList<String> countname = new ArrayList<String>();

    Connection conn = DatabaseConnectionHandler.getConnection();
    ResultSet rs = null;

    /**
     *
     */
    public final ObservableList<Users> data = FXCollections.observableArrayList();

    Thread t = new Thread(this);
    Thread t2;
    Thread t3;
    
    
    
    /**
     * table that will show users data
     */
    
    
    public TableView<Users> ta;
    
    /**
     * barchart which will display statistic in gender (nuber of female vs number of male) 
    */
    
    
    public BarChart<?, ?> bt;
    
    /**
     * piechart display number of users in each country
     */
    
    public PieChart pc;
    
    
    /**
     * label used to show number of online users in application
     */
    public Label lOnline;
    
    
    
    /**
     * label used to show number of offline users in application
     */
    public Label lOffline;
    
    
    /**
     * used to stop connection of database with end of application
    */
    
    public void stopConnection() {
        try {
            conn.close();
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    /**
      * function work with table users database to get offline users 
      * 
      * 
     * @return return arraylist of offline users in application
    */
    
    

    public ArrayList<String> getofflineusers() {
       offline.clear();
        try {
           PreparedStatement pst = conn.prepareStatement(" select * from users where active=0", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

            rs = pst.executeQuery();
            while (rs.next()) {
                offline.add(rs.getString(2));
            }
              pst.close();
              rs.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return offline;
    }
    
    
    
    /**
     * function work with table users in database to get number of online users 
     * @return return arraylist of online users in application
     */
    

    public ArrayList<String> getonlineusers() {
        online.clear();
        try {
            
           PreparedStatement pst = conn.prepareStatement(" select * from users where active=1", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

            rs = pst.executeQuery();
            while (rs.next()) {
                online.add(rs.getString(2));
            }
         pst.close();
          rs.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return online;
    }
    
    
    
    
    /**
     * function work with database to get number of females
     * @return return number of females users in application
     */

    public int numberOfFemale() {
        int female = 0;
        try {
            PreparedStatement pst = conn.prepareStatement(" select * from users where gender='female'", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

            rs = pst.executeQuery();
            while (rs.next()) {
                female++;
            }
            pst.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return female;
    }

    /**
     * function work with database to get number of male users
     * @return number of male users in application
     */
    
    public int numberOfmale() {
        int male = 0;
        try {
            PreparedStatement pst = conn.prepareStatement(" select * from users where gender='male'", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

            rs = pst.executeQuery();
            while (rs.next()) {
                male++;
            }
            pst.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return male;
    }
    
    
    
    
    /**
     *
     * @return number of online users in application
     */
    

    public int getNumberOfonLineUsers() {
        return getonlineusers().size();
    }
    
    
    /**
     *
     * @return number of offline users in application
     */

    public int getNumberOfOfflineUsers() {
        return getofflineusers().size();
    }

    /**
     *
     */
    public void userscountries() {
        countries.clear();
        try {
            PreparedStatement pst = conn.prepareStatement(" select * from users", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

            rs = pst.executeQuery();
            while (rs.next()) {
                countries.add(rs.getString(6));
            }
            pst.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    
    
    /**
     * calculate number of users in each country and put the number in count array 
     *   
     */
    
    
    public void countryCount() {
        userscountries();
        count.clear();
        countname.clear();
        Set<String> mySet = new HashSet<String>(countries);
        for (String s : mySet) {
            count.add(Collections.frequency(countries, s));
            countname.add(s);
        }
    }

    
    /**
     *
     * @return number of users in each country
     */
    
    public ArrayList<Integer> getcountrycount() {
        countryCount();
        return count;
    }

    
    /**
     *
     * @return names of countries users from in application 
     */
    
    
    public ArrayList<String> getcountryname() {
        return countname;
    }
    
    
    /**
     * take reference to from controller and set this reference and 
     * start thread that responsible for update data in table 
     * @param table reference to table that will view users data
     * @param lo  reference to label that will view number of online users
     * @param lf  reference to label that will view number of offline users  
     */

    public void table(TableView<Users> table,Label lo, Label lf) {
        this.ta = table;
        this.lOffline = lf;
        this.lOnline = lo;
        t.start();
    }
    
    

    @Override
    public void run() {
        try (Connection conn = DatabaseConnectionHandler.getConnection();
                PreparedStatement pst = conn.prepareStatement(" select * from users", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);) {
            ResultSet rs = null;
            while (true) {
                rs = pst.executeQuery();
                try {
                    t.sleep(100);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                ta.getItems().clear();
                while (rs.next()) {

                    data.add(new Users(rs.getString("userName"),
                            rs.getString("email"), rs.getString("phoneNo"),
                            rs.getString("gender"), rs.getString("country"),
                            rs.getString("status"), rs.getInt("active")));
                };
                rs.close();
                ta.setItems(data);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    
    /**
     * contains thread that is responsible for updating data in barchart
     * @param ta reference to barchart in controller 
     */
    
    
    public void xychart(BarChart ta) {
        this.bt = ta;
        t2 = new Thread(this) {
            @Override
            public void run() {
                while (true) {
                    try {

                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {

                                bt.getData().clear();
                                bt.setAnimated(false);

                                XYChart.Series series1 = new XYChart.Series();
                                series1.setName("females");
                                XYChart.Series series2 = new XYChart.Series();
                                series2.setName("males");
                                series1.getData().add(new XYChart.Data("Female", numberOfFemale()));
                                series2.getData().add(new XYChart.Data("male", numberOfmale()));
                                bt.getData().addAll(series1, series2);
                                
                                
                                int on = getNumberOfonLineUsers();
                                int of = getNumberOfOfflineUsers();
                                lOnline.setText(String.valueOf(on));
                                lOffline.setText(String.valueOf(of));
                            }
                        });
                        sleep(1000);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        };
        t2.start();
    }
    
    
    /**
     * contain thread that is responsible for update piechart data
     * @param p reference to piechart in controller
     */
    

    public void pieChart(PieChart p) {
        int j = 0;
        this.pc = p;
        ObservableList<PieChart.Data> data = FXCollections.observableArrayList();
        pc.setTitle("Countries");
        t3 = new Thread(this) {
            @Override
            public void run() {
                while (true) {
                    try {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                pc.getData().clear();
                                pc.setAnimated(false);
                                int p = 0;
                                for (int i = 0; i < getcountrycount().size(); i++) {
                                    p += getcountrycount().get(i) * 1.0;
                                }
                                for (int i = 0; i < getcountrycount().size(); i++) {
                                    double percent = (getcountrycount().get(i) * 1.0 / p) * 100;
                                    DecimalFormat df = new DecimalFormat("#.##");
                                    String dx = df.format(percent);
                                    data.add(new PieChart.Data(getcountryname().get(i) + "  " + dx, getcountrycount().get(i)));
                                }
                                pc.setData(data);
                            }
                        });
                        sleep(5000);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        };
        t3.start();
    }
}
