/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testing2;
import java.awt.CardLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.swing.ImageIcon;
import javax.imageio.ImageIO;
import java.io.*;
/**
 *
 * @author shrey
 */
public class MainFrame extends javax.swing.JFrame {
    String prev="initPanel";
    boolean isInTrip=false;
    Customer c1;
    DBMSUtils db = new DBMSUtils();
    String posA="",posB="";
    Driver prevDriver=null;
    int refreshes = 0;
    /*
     * Creates new form MainFrame
     */
    public MainFrame() {
        initComponents();
    }
    private void callInitFrame()
    {
        CardLayout cards=(CardLayout)mainPanel.getLayout();
        cards.show(mainPanel, "initPanel");
    }
    private void callSignInPanel()
    {
        err_signin_label.setVisible(false);
        user_field.setText("");
        pass_field.setText("");
        CardLayout cards=(CardLayout)mainPanel.getLayout();
        cards.show(mainPanel, "signinPanel");
    }
    private void callRegisterPanel()
    {
        err_reg_label.setVisible(false);
        name_text.setText("");
        email_text.setText("");
        pass_reg_field.setText("");
        retypepass_reg_field.setText("");
        CardLayout cards=(CardLayout)mainPanel.getLayout();
        cards.show(mainPanel, "registerPanel");
    }
    private void callReviewPanel()
    {
        CardLayout cards=(CardLayout)mainPanel.getLayout();
        cards.show(mainPanel, "reviewPanel");
    }
    private void callHomePanel()
    {
//        if((++refreshes % 11) == 0 && refreshes != 0)
//        {
//            refreshes = 0;
//            db.redistributeDrivers();
//        }
        username_label.setText(getUsername());
        boolean prevIsInTrip = c1.isInTrip;
        if(prevIsInTrip==true)
        {
            prevDriver=c1.assignedDriver;
        }
//        System.out.println(db.getTripStatus(c1));
        if(db.getTripStatus(c1))
        {
            wallet_status_label.setText(getWallet());            
            deets_home_button.setVisible(true);
            home_book_button.setEnabled(false); 
            String msg="<html><b><font size=4 face=\"Roboto\" color=\"white\">Trip ongoing...</font></b></html>";
            home_err_label.setText(msg);
            home_err_label.setVisible(true);
        }
        else{
        if(prevIsInTrip==true)
        {
            callReviewPanel();   
            return;
        }
        wallet_status_label.setText(getWallet());
        double x=Double.parseDouble(getWallet());
        if(x<=300)
        {
            deets_home_button.setVisible(false);
            home_book_button.setEnabled(false); 
            String msg="<html><b><font size=4 face=\"Roboto\" color=\"white\">Less Money</font></b></html>";
            home_err_label.setText(msg);
            home_err_label.setVisible(true);
        }
        else
        {
            deets_home_button.setVisible(false);
            home_book_button.setEnabled(true); 
            home_err_label.setVisible(false);            
        }}
        CardLayout cards=(CardLayout)mainPanel.getLayout();
        cards.show(mainPanel, "homePanel");
        
    }
    private void callHomePanel(String msg)
    {
//        if((++refreshes % 11) == 0 && refreshes != 0)
//        {
//            refreshes = 0;
//            db.redistributeDrivers();
//        }
        username_label.setText(getUsername());
        String msg2="<html><b><font size=4 face=\"Roboto\" color=\"white\">"+msg+"</font></b></html>";
//        System.out.println("excey");
        boolean prevIsInTrip = c1.isInTrip;
        if(prevIsInTrip==true)
        {
            prevDriver=c1.assignedDriver;
        }
        System.out.println(db.getTripStatus(c1));
        if(db.getTripStatus(c1))
        {
            wallet_status_label.setText(getWallet());
            deets_home_button.setVisible(true);
            home_book_button.setEnabled(false); 
            home_err_label.setText(msg2);
            home_err_label.setVisible(true);
        }
        else{
        if(prevIsInTrip==true)
        {
            callReviewPanel();   
            return;
        }
        wallet_status_label.setText(getWallet());
        double x=Double.parseDouble(getWallet());
        if(x<=300)
        {
            deets_home_button.setVisible(false);
            home_book_button.setEnabled(false); 
            home_err_label.setText(msg2);
            home_err_label.setVisible(true);
        }
        else
        {
            deets_home_button.setVisible(false);
            home_book_button.setEnabled(true); 
            home_err_label.setText(msg2);
            home_err_label.setVisible(true);            
        }}
        CardLayout cards=(CardLayout)mainPanel.getLayout();
        cards.show(mainPanel, "homePanel");
    }
    
    private void callWalletPanel()
    {
        err_wallet_label.setVisible(false);
        bal_wallet_field.setText(getWallet());
        toAdd_wallet_field.setText("0");
        pass_wallet_field.setText("");
        CardLayout cards=(CardLayout)mainPanel.getLayout();
        cards.show(mainPanel, "walletPanel");        
    }
    
    private void callCabBookPanel()
    {
        err_book_label.setVisible(false);
        CardLayout cards=(CardLayout)mainPanel.getLayout();
        cards.show(mainPanel, "cabBookPanel");        
    }
    
    private void callConfirmPanel()
    {
        if(posA.equals("")||posB.equals(""))
        {
            posA=c1.old_loc;
            posB=c1.loc;
        }
        int dist = getDistance(posA,posB);
        double price = dist*100;
        long eta= dist*30000;
        boolean prevIsInTrip = c1.isInTrip;
        if(prevIsInTrip==true)
        {
            prevDriver=c1.assignedDriver;
        }
        if(db.getTripStatus(c1))
        {
            cabname_confirm_label.setVisible(true);
//            Driver d = db.getBestDriver();
            cabname_confirm_field.setText(c1.assignedDriver.username);
            rating_confirm_label.setVisible(true);
            rating_confirm_field.setText(""+c1.assignedDriver.rating);
            rating_confirm_field.setVisible(true);
            cabname_confirm_field.setVisible(true);
            time_confirm_field.setText(""+(((double)eta/60000))+" minutes");
            cost_confirm_field.setText(""+price);
            confirm_confirm_button.setVisible(false);
            err_confirm_label.setText("<html><b><font size=4 face=\"Roboto\" color=\"white\">Tripping...</font></b></html>");
            err_confirm_label.setVisible(true);
        }
        else
        {
            if(prevIsInTrip==true)
            {
                callReviewPanel();   
                return;
            }
            rating_confirm_label.setVisible(false);
            rating_confirm_field.setVisible(false);
            cabname_confirm_label.setVisible(false);
            cabname_confirm_field.setVisible(false);
            time_confirm_field.setText(""+(((double)eta/60000))+" minutes");
            cost_confirm_field.setText(""+price);    
            confirm_confirm_button.setVisible(true);
            err_confirm_label.setVisible(false);
        }
        bal_confirm_field.setText(""+c1.w.getMoney());
        bal_confirm_field.setVisible(true);
        bal_confirm_panel.setVisible(true);
        
//        cabname_confirm_field.setText("CABBI");
//        time_confirm_field.setText("180 days");
//        cost_confirm_field.setText("10000000 USD");
        CardLayout cards=(CardLayout)mainPanel.getLayout();
        cards.show(mainPanel, "confirmPanel");        
    }
    
    private String eta()
    {
        return "yeetus";
    }
    private boolean isInTrip()
    {
        return db.getTripStatus(c1);
    }
    private String getUsername()
    {
        return c1.username;
    }
    private String getWallet()
    {
        System.out.println(""+c1.w.getMoney());
        return ""+c1.w.getMoney();
    }
    private boolean isRegValid(String username, String email, String pass)
    {
        if(username.equals("")||email.equals("")||pass.equals(""))
        {
            return false;
        }
        else
        {
            return true;
        }
    }
    private boolean cabAvail()
    {
        return true;
    }
    private boolean canBook()
    {
        return true;
    }
    private String[] getPlaces()
    {
        String arr[]=new String[5];
        arr[0]="A";
        arr[1]="B";
        arr[2]="C";
        arr[3]="D";
        arr[4]="E";
        return arr;
    }
    
    public int getDistance(String src, String dest)
    {
        char S = src.charAt(0);
        char D = dest.charAt(0);
        int distance = 0;
        BufferedReader  reader;
        try{
        reader = new BufferedReader(new FileReader("D:\\shrey\\Documents\\JavaProjs\\OOPS\\src\\main\\java\\pathgen\\Path.txt"));
        int numlines = (((int)S)- 65)*5 + (((int)D)-65);
        String line="-1 -1 1";
        for(int i = 0;i<=numlines;i++)
        {
            line = reader.readLine();
        }
        String[] arrOfStr = line.split(" ", 3);
        distance =  Integer.parseInt(arrOfStr[2]);
        }
        catch(Exception e)
        {
           e.printStackTrace();
        }
        return distance;
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel11 = new javax.swing.JLabel();
        mainPanel = new javax.swing.JPanel();
        initPanel = new javax.swing.JPanel();
        ExistingUserButton = new javax.swing.JButton();
        NewUserButton = new javax.swing.JButton();
        img = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        sideImg = new javax.swing.JLabel();
        BufferedImage i2=null;
        try{i2=ImageIO.read(new File("D:\\shrey\\Documents\\JavaProjs\\OOPS_Project\\GUI_Netbeans_Project\\src\\main\\java\\GUIv2\\bg1.png"));}catch(Exception e){System.out.println("Failed import");}
        Image scaled2=i2.getScaledInstance(700,700,Image.SCALE_SMOOTH);
        ImageIcon icon2=new ImageIcon(scaled2);
        sideImg.setIcon(icon2);
        sideImg.setVisible(true);
        signinPanel = new javax.swing.JPanel();
        user_field = new javax.swing.JTextField();
        user_label = new javax.swing.JLabel();
        pass_label = new javax.swing.JLabel();
        pass_field = new javax.swing.JPasswordField();
        signIn = new javax.swing.JButton();
        reset = new javax.swing.JButton();
        err_signin_label = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        registerPanel = new javax.swing.JPanel();
        name_text = new javax.swing.JTextField();
        email_text = new javax.swing.JTextField();
        name_label = new javax.swing.JLabel();
        email_label = new javax.swing.JLabel();
        pass_reg_label = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        pass_reg_field = new javax.swing.JPasswordField();
        retypepass_reg_field = new javax.swing.JPasswordField();
        reg_button = new javax.swing.JButton();
        reset_reg_button = new javax.swing.JButton();
        err_reg_label = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        confirmPanel = new javax.swing.JPanel();
        cabname_confirm_label = new javax.swing.JLabel();
        cabname_confirm_field = new javax.swing.JTextField();
        time_confirm_label = new javax.swing.JLabel();
        cost_confirm_label = new javax.swing.JLabel();
        time_confirm_field = new javax.swing.JTextField();
        cost_confirm_field = new javax.swing.JTextField();
        home_confirm_button = new javax.swing.JButton();
        wallet_confirm_button = new javax.swing.JButton();
        confirm_confirm_button = new javax.swing.JButton();
        bal_confirm_panel = new javax.swing.JLabel();
        bal_confirm_field = new javax.swing.JTextField();
        err_confirm_label = new javax.swing.JLabel();
        rating_confirm_label = new javax.swing.JLabel();
        rating_confirm_field = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        homePanel = new javax.swing.JPanel();
        home_book_button = new javax.swing.JButton();
        add_home_button = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        username_label = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        wallet_status_label = new javax.swing.JTextField();
        home_err_label = new javax.swing.JLabel();
        deets_home_button = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        walletPanel = new javax.swing.JPanel();
        bal_wallet_label = new javax.swing.JLabel();
        bal_wallet_field = new javax.swing.JTextField();
        toAdd_wallet_label = new javax.swing.JLabel();
        toAdd_wallet_field = new javax.swing.JTextField();
        pass_wallet_label = new javax.swing.JLabel();
        pass_wallet_field = new javax.swing.JPasswordField();
        add_wallet_button = new javax.swing.JButton();
        home_wallet_button = new javax.swing.JButton();
        err_wallet_label = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        cabBookPanel = new javax.swing.JPanel();
        from_combobox = new javax.swing.JComboBox<>();
        to_combobox = new javax.swing.JComboBox<>();
        from_book_label = new javax.swing.JLabel();
        to_book_label = new javax.swing.JLabel();
        book_book_button = new javax.swing.JButton();
        home_bookPanel_button = new javax.swing.JButton();
        err_book_label = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        reviewPanel = new javax.swing.JPanel();
        slider = new javax.swing.JSlider();
        heading_review_label = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        sign_out_menuitem = new javax.swing.JMenuItem();

        jLabel11.setText("jLabel11");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        mainPanel.setBackground(new java.awt.Color(255, 255, 255));
        mainPanel.setLayout(new java.awt.CardLayout());

        ExistingUserButton.setBackground(new java.awt.Color(0, 0, 0));
        ExistingUserButton.setForeground(new java.awt.Color(255, 255, 255));
        ExistingUserButton.setText("<html><b><font size=5 face=\"Roboto\">Existing User</font></b></html>");
        ExistingUserButton.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        ExistingUserButton.setContentAreaFilled(false);
        ExistingUserButton.setBorder(null);
        ExistingUserButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ExistingUserButtonMouseClicked(evt);
            }
        });
        ExistingUserButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ExistingUserButtonActionPerformed(evt);
            }
        });

        NewUserButton.setBackground(new java.awt.Color(0, 255, 255));
        NewUserButton.setForeground(new java.awt.Color(255, 255, 255));
        NewUserButton.setText("<html><b><font size=5 face=\"Roboto\">New User</font></b></html>");
        NewUserButton.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        NewUserButton.setContentAreaFilled(false);
        NewUserButton.setBorder(null);
        NewUserButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                NewUserButtonMouseClicked(evt);
            }
        });

        img.setBackground(new java.awt.Color(255, 255, 255));
        img.setForeground(java.awt.SystemColor.controlHighlight);
        BufferedImage i=null;
        try{i=ImageIO.read(new File("D:\\shrey\\Documents\\JavaProjs\\OOPS_Project\\GUI_Netbeans_Project\\src\\main\\java\\GUIv2\\uberola.png"));}catch(Exception e){System.out.println("Failed import");}
        Image scaled=i.getScaledInstance(131,60,Image.SCALE_SMOOTH);
        ImageIcon icon=new ImageIcon(scaled);
        img.setIcon(icon);
        img.setVisible(true);

        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("<html><b><font size=4 face=\"Proxima Nova\" color=\"White\">Book a ride without breaking a stride!!!!!</font></b></html>");

        sideImg.setFocusable(false);

        javax.swing.GroupLayout initPanelLayout = new javax.swing.GroupLayout(initPanel);
        initPanel.setLayout(initPanelLayout);
        initPanelLayout.setHorizontalGroup(
            initPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(initPanelLayout.createSequentialGroup()
                .addGap(120, 120, 120)
                .addComponent(ExistingUserButton, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(initPanelLayout.createSequentialGroup()
                .addGap(120, 120, 120)
                .addComponent(NewUserButton, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(initPanelLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(img, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(initPanelLayout.createSequentialGroup()
                .addGap(147, 147, 147)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(sideImg, javax.swing.GroupLayout.PREFERRED_SIZE, 480, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        initPanelLayout.setVerticalGroup(
            initPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(initPanelLayout.createSequentialGroup()
                .addGap(100, 100, 100)
                .addComponent(ExistingUserButton, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(NewUserButton, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(initPanelLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(img, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(initPanelLayout.createSequentialGroup()
                .addGap(1, 1, 1)
                .addComponent(sideImg, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        mainPanel.add(initPanel, "initPanel");

        signinPanel.setLayout(null);
        signinPanel.add(user_field);
        user_field.setBounds(140, 70, 150, 32);

        user_label.setText("<html><b><font size=4 face=\"Roboto\" color=\"white\">Username</font></b></html>");
        signinPanel.add(user_label);
        user_label.setBounds(50, 70, 70, 32);

        pass_label.setText("<html><b><font size=4 face=\"Roboto\" color=\"white\">Password</font></b></html>");
        signinPanel.add(pass_label);
        pass_label.setBounds(51, 120, 70, 32);

        pass_field.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pass_fieldActionPerformed(evt);
            }
        });
        signinPanel.add(pass_field);
        pass_field.setBounds(140, 120, 150, 32);

        signIn.setText("<html><b><font size=5 face=\"Roboto\" color=\"white\">Sign In</font></b></html>");
        signIn.setContentAreaFilled(false);
        signIn.setBorder(null);
        signIn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                signInMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                signInMouseEntered(evt);
            }
        });
        signinPanel.add(signIn);
        signIn.setBounds(80, 200, 100, 40);

        reset.setText("<html><b><font size=5 face=\"Roboto\" color=\"white\">Reset</font></b></html>");
        reset.setContentAreaFilled(false);
        reset.setBorder(null);
        reset.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                resetMouseClicked(evt);
            }
        });
        signinPanel.add(reset);
        reset.setBounds(206, 200, 100, 38);

        err_signin_label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        err_signin_label.setText("<html><b><font size=5 face=\"Roboto\" color=\"white\">Error</font></b></html>");
        err_signin_label.setVisible(false);
        signinPanel.add(err_signin_label);
        err_signin_label.setBounds(140, 240, 129, 69);

        jLabel5.setIcon(icon2);
        jLabel5.setVisible(true);
        signinPanel.add(jLabel5);
        jLabel5.setBounds(0, 0, 480, 340);

        mainPanel.add(signinPanel, "signinPanel");

        registerPanel.setLayout(null);

        name_text.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                name_textActionPerformed(evt);
            }
        });
        registerPanel.add(name_text);
        name_text.setBounds(110, 30, 250, 30);
        registerPanel.add(email_text);
        email_text.setBounds(110, 70, 250, 30);

        name_label.setText("<html><b><font size=4 face=\"Roboto\" color=\"white\">Username</font></b></html>");
        registerPanel.add(name_label);
        name_label.setBounds(20, 30, 64, 30);

        email_label.setText("<html><b><font size=4 face=\"Roboto\" color=\"white\">Email</font></b></html>");
        registerPanel.add(email_label);
        email_label.setBounds(20, 70, 50, 30);

        pass_reg_label.setText("<html><b><font size=4 face=\"Roboto\" color=\"white\">Password</font></b></html>");
        registerPanel.add(pass_reg_label);
        pass_reg_label.setBounds(20, 110, 64, 30);

        jLabel4.setText("<html><b><font size=4 face=\"Roboto\" color=\"white\">Retype Password</font></b></html>");
        registerPanel.add(jLabel4);
        jLabel4.setBounds(20, 150, 90, 30);
        registerPanel.add(pass_reg_field);
        pass_reg_field.setBounds(110, 110, 250, 30);
        registerPanel.add(retypepass_reg_field);
        retypepass_reg_field.setBounds(110, 150, 250, 30);

        reg_button.setText("<html><b><font size=5 face=\"Roboto\" color=\"white\">Register</font></b></html>");
        reg_button.setContentAreaFilled(false);
        reg_button.setBorder(null);
        reg_button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                reg_buttonMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                reg_buttonMouseEntered(evt);
            }
        });
        registerPanel.add(reg_button);
        reg_button.setBounds(110, 200, 100, 50);

        reset_reg_button.setText("<html><b><font size=5 face=\"Roboto\" color=\"white\">Reset</font></b></html>");
        reset_reg_button.setContentAreaFilled(false);
        reset.setBorder(null);
        reset_reg_button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                reset_reg_buttonMouseClicked(evt);
            }
        });
        registerPanel.add(reset_reg_button);
        reset_reg_button.setBounds(250, 200, 100, 50);

        err_reg_label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        err_reg_label.setText("<html><b><font size=5 face=\"Roboto\" color=\"white\">Error</font></b></html>");
        err_reg_label.setVisible(false);
        registerPanel.add(err_reg_label);
        err_reg_label.setBounds(100, 270, 247, 40);

        jLabel6.setIcon(icon2);
        jLabel6.setVisible(true);
        registerPanel.add(jLabel6);
        jLabel6.setBounds(0, 0, 480, 340);

        mainPanel.add(registerPanel, "registerPanel");

        confirmPanel.setLayout(null);

        cabname_confirm_label.setText("<html><b><font size=4 face=\"Roboto\" color=\"white\">Cabbie</font></b></html>");
        confirmPanel.add(cabname_confirm_label);
        cabname_confirm_label.setBounds(20, 10, 103, 39);

        cabname_confirm_field.setEditable(false);
        confirmPanel.add(cabname_confirm_field);
        cabname_confirm_field.setBounds(150, 10, 170, 30);

        time_confirm_label.setText("<html><b><font size=4 face=\"Roboto\" color=\"white\">Duration</font></b></html>");
        confirmPanel.add(time_confirm_label);
        time_confirm_label.setBounds(20, 50, 110, 30);

        cost_confirm_label.setText("<html><b><font size=4 face=\"Roboto\" color=\"white\">Cost</font></b></html>");
        confirmPanel.add(cost_confirm_label);
        cost_confirm_label.setBounds(20, 90, 80, 30);

        time_confirm_field.setEditable(false);
        confirmPanel.add(time_confirm_field);
        time_confirm_field.setBounds(150, 50, 170, 30);

        cost_confirm_field.setEditable(false);
        confirmPanel.add(cost_confirm_field);
        cost_confirm_field.setBounds(150, 90, 170, 30);

        home_confirm_button.setText("<html><b><font size=5 face=\"Roboto\" color=\"white\">Home</font></b></html>");
        home_confirm_button.setContentAreaFilled(false);
        home_confirm_button.setBorder(null);
        home_confirm_button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                home_confirm_buttonMouseClicked(evt);
            }
        });
        home_confirm_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                home_confirm_buttonActionPerformed(evt);
            }
        });
        confirmPanel.add(home_confirm_button);
        home_confirm_button.setBounds(20, 220, 90, 50);

        wallet_confirm_button.setText("<html><b><font size=5 face=\"Roboto\" color=\"white\">Wallet</font></b></html>");
        wallet_confirm_button.setContentAreaFilled(false);
        wallet_confirm_button.setBorder(null);
        wallet_confirm_button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                wallet_confirm_buttonMouseClicked(evt);
            }
        });
        confirmPanel.add(wallet_confirm_button);
        wallet_confirm_button.setBounds(260, 220, 100, 50);

        confirm_confirm_button.setText("<html><b><font size=5 face=\"Roboto\" color=\"white\">Confirm</font></b></html>");
        confirm_confirm_button.setContentAreaFilled(false);
        confirm_confirm_button.setBorder(null);
        confirm_confirm_button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                confirm_confirm_buttonMouseClicked(evt);
            }
        });
        confirmPanel.add(confirm_confirm_button);
        confirm_confirm_button.setBounds(140, 220, 100, 50);

        bal_confirm_panel.setText("<html><b><font size=4 face=\"Roboto\" color=\"white\">Balance</font></b></html>");
        bal_confirm_panel.setVisible(false);
        confirmPanel.add(bal_confirm_panel);
        bal_confirm_panel.setBounds(20, 130, 90, 30);

        bal_confirm_field.setEditable(false);
        bal_confirm_field.setVisible(false);
        bal_confirm_field.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bal_confirm_fieldActionPerformed(evt);
            }
        });
        confirmPanel.add(bal_confirm_field);
        bal_confirm_field.setBounds(150, 130, 170, 30);

        err_confirm_label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        err_confirm_label.setText("ERROR");
        err_confirm_label.setVisible(false);
        confirmPanel.add(err_confirm_label);
        err_confirm_label.setBounds(90, 290, 210, 30);

        rating_confirm_label.setText("<html><b><font size=4 face=\"Roboto\" color=\"white\">Rating</font></b></html>");
        confirmPanel.add(rating_confirm_label);
        rating_confirm_label.setBounds(20, 170, 70, 30);

        rating_confirm_field.setEditable(false);
        confirmPanel.add(rating_confirm_field);
        rating_confirm_field.setBounds(150, 170, 170, 30);

        jLabel7.setIcon(icon2);
        jLabel7.setVisible(true);
        confirmPanel.add(jLabel7);
        jLabel7.setBounds(0, 0, 480, 340);

        mainPanel.add(confirmPanel, "confirmPanel");

        homePanel.setLayout(null);

        home_book_button.setText("<html><b><font size=5 face=\"Roboto\" color=\"white\">Book Cab</font></b></html>");
        home_book_button.setContentAreaFilled(false);
        home_book_button.setBorder(null);
        home_book_button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                home_book_buttonMouseClicked(evt);
            }
        });
        home_book_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                home_book_buttonActionPerformed(evt);
            }
        });
        homePanel.add(home_book_button);
        home_book_button.setBounds(154, 164, 145, 44);

        add_home_button.setText("<html><b><font size=5 face=\"Roboto\" color=\"white\">Add Money</font></b></html>");
        add_home_button.setContentAreaFilled(false);
        add_home_button.setBorder(null);
        add_home_button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                add_home_buttonMouseClicked(evt);
            }
        });
        homePanel.add(add_home_button);
        add_home_button.setBounds(154, 218, 145, 45);

        jLabel1.setText("<html><b><font size=4 face=\"Roboto\" color=\"white\">Username</font></b></html>");
        homePanel.add(jLabel1);
        jLabel1.setBounds(113, 20, 63, 32);

        username_label.setEditable(false);
        homePanel.add(username_label);
        username_label.setBounds(194, 20, 139, 32);

        jLabel2.setText("<html><b><font size=4 face=\"Roboto\" color=\"white\">Wallet</font></b></html>");
        homePanel.add(jLabel2);
        jLabel2.setBounds(113, 70, 63, 38);

        wallet_status_label.setEditable(false);
        homePanel.add(wallet_status_label);
        wallet_status_label.setBounds(194, 70, 139, 30);

        home_err_label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        home_err_label.setText("MoneyProblems");
        home_err_label.setVisible(false);
        homePanel.add(home_err_label);
        home_err_label.setBounds(159, 114, 140, 40);

        deets_home_button.setText("<html><b><font size=5 face=\"Roboto\" color=\"white\">Check Details</font></b></html>");
        deets_home_button.setContentAreaFilled(false);
        deets_home_button.setBorder(null);
        deets_home_button.setVisible(false);
        deets_home_button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                deets_home_buttonMouseClicked(evt);
            }
        });
        deets_home_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deets_home_buttonActionPerformed(evt);
            }
        });
        homePanel.add(deets_home_button);
        deets_home_button.setBounds(154, 273, 145, 41);

        jLabel8.setIcon(icon2);
        jLabel8.setVisible(true);
        homePanel.add(jLabel8);
        jLabel8.setBounds(0, 0, 490, 340);

        mainPanel.add(homePanel, "homePanel");

        walletPanel.setLayout(null);

        bal_wallet_label.setText("<html><b><font size=4 face=\"Roboto\" color=\"white\">Balance</font></b></html>");
        walletPanel.add(bal_wallet_label);
        bal_wallet_label.setBounds(24, 24, 55, 33);

        bal_wallet_field.setEditable(false);
        walletPanel.add(bal_wallet_field);
        bal_wallet_field.setBounds(134, 29, 158, 26);

        toAdd_wallet_label.setText("<html><b><font size=4 face=\"Roboto\" color=\"white\">Money to add</font></b></html>");
        walletPanel.add(toAdd_wallet_label);
        toAdd_wallet_label.setBounds(24, 72, 92, 37);

        toAdd_wallet_field.setText("0");
        walletPanel.add(toAdd_wallet_field);
        toAdd_wallet_field.setBounds(134, 78, 158, 27);

        pass_wallet_label.setText("<html><b><font size=4 face=\"Roboto\" color=\"white\">Password</font></b></html>");
        walletPanel.add(pass_wallet_label);
        pass_wallet_label.setBounds(24, 133, 69, 23);
        walletPanel.add(pass_wallet_field);
        pass_wallet_field.setBounds(134, 131, 158, 29);

        add_wallet_button.setText("<html><b><font size=5 face=\"Roboto\" color=\"white\">Add Money</font></b></html>");
        add_wallet_button.setContentAreaFilled(false);
        add_wallet_button.setBorder(null);
        add_wallet_button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                add_wallet_buttonMouseClicked(evt);
            }
        });
        walletPanel.add(add_wallet_button);
        add_wallet_button.setBounds(54, 193, 125, 49);

        home_wallet_button.setText("<html><b><font size=5 face=\"Roboto\" color=\"white\">Home</font></b></html>");
        home_wallet_button.setContentAreaFilled(false);
        home_wallet_button.setBorder(null);
        home_wallet_button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                home_wallet_buttonMouseClicked(evt);
            }
        });
        walletPanel.add(home_wallet_button);
        home_wallet_button.setBounds(212, 193, 118, 49);

        err_wallet_label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        err_wallet_label.setText("Error");
        err_wallet_label.setVisible(false);
        walletPanel.add(err_wallet_label);
        err_wallet_label.setBounds(132, 260, 136, 58);

        jLabel9.setIcon(icon2);
        jLabel9.setVisible(true);
        walletPanel.add(jLabel9);
        jLabel9.setBounds(0, 0, 480, 340);

        mainPanel.add(walletPanel, "walletPanel");

        cabBookPanel.setLayout(null);

        from_combobox.setModel(new javax.swing.DefaultComboBoxModel(getPlaces()));
        from_combobox.setOpaque(false);
        from_combobox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                from_comboboxActionPerformed(evt);
            }
        });
        cabBookPanel.add(from_combobox);
        from_combobox.setBounds(188, 51, 106, 21);

        to_combobox.setModel(new javax.swing.DefaultComboBoxModel(getPlaces()));
        cabBookPanel.add(to_combobox);
        to_combobox.setBounds(188, 105, 106, 21);

        from_book_label.setText("<html><b><font size=5 face=\"Roboto\" color=\"white\">From</font></b></html>");
        cabBookPanel.add(from_book_label);
        from_book_label.setBounds(104, 41, 55, 36);

        to_book_label.setText("<html><b><font size=5 face=\"Roboto\" color=\"white\">To</font></b></html>");
        cabBookPanel.add(to_book_label);
        to_book_label.setBounds(113, 96, 46, 34);

        book_book_button.setText("<html><b><font size=5 face=\"Roboto\" color=\"white\">Book</font></b></html>");
        book_book_button.setContentAreaFilled(false);
        book_book_button.setBorder(null);
        book_book_button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                book_book_buttonMouseClicked(evt);
            }
        });
        cabBookPanel.add(book_book_button);
        book_book_button.setBounds(60, 181, 129, 80);

        home_bookPanel_button.setText("<html><b><font size=5 face=\"Roboto\" color=\"white\">Home</font></b></html>");
        home_bookPanel_button.setContentAreaFilled(false);
        home_bookPanel_button.setBorder(null);
        home_bookPanel_button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                home_bookPanel_buttonMouseClicked(evt);
            }
        });
        cabBookPanel.add(home_bookPanel_button);
        home_bookPanel_button.setBounds(244, 181, 142, 80);

        err_book_label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        err_book_label.setText("ERROR");
        err_book_label.setVisible(false);
        cabBookPanel.add(err_book_label);
        err_book_label.setBounds(61, 279, 320, 50);

        jLabel10.setIcon(icon2);
        jLabel10.setVisible(true);
        cabBookPanel.add(jLabel10);
        jLabel10.setBounds(0, 0, 480, 340);

        mainPanel.add(cabBookPanel, "cabBookPanel");

        reviewPanel.setLayout(null);

        slider.setMaximum(5);
        slider.setMinimum(1);
        slider.setPaintLabels(true);
        slider.setPaintTicks(true);
        slider.setPaintTrack(true);
        slider.setSnapToTicks(true);
        slider.setValue(3);
        reviewPanel.add(slider);
        slider.setBounds(117, 132, 213, 42);

        heading_review_label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        heading_review_label.setText("<html><b><font size=5 face=\"Roboto\" color=\"white\">Trip has ended!</font></b></html>");
        reviewPanel.add(heading_review_label);
        heading_review_label.setBounds(129, 10, 187, 52);

        jLabel12.setText("<html><b><font size=4 face=\"Roboto\" color=\"white\">Please leave a rating</font></b></html>");
        reviewPanel.add(jLabel12);
        jLabel12.setBounds(159, 80, 128, 17);

        jButton1.setText("<html><b><font size=5 face=\"Roboto\" color=\"white\">Submit</font></b></html>");
        jButton1.setContentAreaFilled(false);
        jButton1.setBorder(null);
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });
        reviewPanel.add(jButton1);
        jButton1.setBounds(177, 192, 101, 63);

        jLabel13.setIcon(icon2);
        jLabel13.setVisible(true);
        reviewPanel.add(jLabel13);
        jLabel13.setBounds(0, 0, 480, 340);

        mainPanel.add(reviewPanel, "reviewPanel");

        jMenuBar1.setBackground(java.awt.SystemColor.controlHighlight);
        jMenuBar1.setForeground(java.awt.SystemColor.controlShadow);

        jMenu1.setText("File");
        jMenu1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenu1MouseClicked(evt);
            }
        });

        jMenuItem1.setText("Home");
        jMenuItem1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenuItem1MouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jMenuItem1MousePressed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        sign_out_menuitem.setText("Sign Out");
        sign_out_menuitem.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                sign_out_menuitemMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                sign_out_menuitemMousePressed(evt);
            }
        });
        jMenu1.add(sign_out_menuitem);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void sign_out_menuitemMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sign_out_menuitemMouseClicked
//        
    }//GEN-LAST:event_sign_out_menuitemMouseClicked

    private void jMenu1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenu1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenu1MouseClicked

    private void sign_out_menuitemMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sign_out_menuitemMousePressed
        callInitFrame();
//        System.out.println("clicked");
//        CardLayout cards=(CardLayout)mainPanel.getLayout();
//        err_reg_label.setVisible(false);
//        err_signin_label.setVisible(false);
//        cards.show(mainPanel, "initPanel");
    }//GEN-LAST:event_sign_out_menuitemMousePressed

    private void reset_reg_buttonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_reset_reg_buttonMouseClicked
        name_text.setText("");
        email_text.setText("");
        pass_reg_field.setText("");
        retypepass_reg_field.setText("");
    }//GEN-LAST:event_reset_reg_buttonMouseClicked

    private void resetMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_resetMouseClicked
        user_field.setText("");
        pass_field.setText("");
    }//GEN-LAST:event_resetMouseClicked

    private void signInMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_signInMouseClicked
        String str=user_field.getText();
//        System.out.println("Clickedddddd");
//        callHomePanel();
//        System.out.println(str);
        if(user_field.getText().equals("")||new String(pass_field.getPassword()).equals(""))
        {
            String msg="<html><b><font size=5 face=Roboto color=white>Empty Fields</font></b></html>";
            err_signin_label.setText(msg);
            err_signin_label.setVisible(true);
//            CardLayout cards=(CardLayout)mainPanel.getLayout();
//            errLabel.setText("Hiii !Shreyam");
//            prev="signinPanel";
//            cards.show(mainPanel, "errPanel");
        }
        else
        {
            String password = new String(pass_field.getPassword());
            try{
                password  = Customer.HashPassword(password);
                System.out.println(password);
            }
            catch(Exception nsae)
            {
                System.out.println("No such algorithm");
            }
            String username = user_field.getText();
            try
            {
//                password = Customer.HashPassword(password);
                Customer c = db.loginUser(username, password);
                
                if(c==null)
                {
                    String msg="<html><b><font size=5 face=Roboto color=white>Wrong Creds</font></b></html>";
                    err_signin_label.setText(msg);
                    err_signin_label.setVisible(true);   
                    return;
                }
                else
                {
                    c1=c;
//                    System.out.println("HomePanelERR");
                    callHomePanel("Welcome");
                }
               
            }
            catch(Exception e)
            {
                System.out.println("Err hereeee");
                System.out.println(e);
                String msg="<html><b><font size=5 face=Roboto color=white>OOPS! Please try again</font></b></html>";
                err_signin_label.setText(msg);
                err_signin_label.setVisible(true);
            }
            /*
            Customer tempCustomer = DBMSUtils.checkLogin(username,password);
            if(tempCustomer != NULL)
            {
                callHomePanel("Welcome!!!!");
                c1 = tempCustomer;
            }
            else
            {
            err_signin_label.setText("Wrong user");
            err_signin_label.setVisible(true);
            }
            
            */
        }
    }//GEN-LAST:event_signInMouseClicked

    private void reg_buttonMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_reg_buttonMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_reg_buttonMouseEntered

    
    private void reg_buttonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_reg_buttonMouseClicked
        String username = name_text.getText();
        String email = email_text.getText();
        String pass = new String(pass_reg_field.getPassword());
        String retypedPass = new String(retypepass_reg_field.getPassword());
        if(username.equals("")||email.equals("")||pass.equals("")||retypedPass.equals(""))
        {
            err_reg_label.setText("<html><b><font size=5 face=\"Roboto\" color=\"white\">Blank fields</font></b></html>");
//            System.out.println("Wrong retyped password "+pass+" "+retypedPass);
            err_reg_label.setVisible(true);  
            return;            
        }
        if(!pass.equals(retypedPass))
        {
            err_reg_label.setText("<html><b><font size=5 face=\"Roboto\" color=\"white\">Wrong retyped password</font></b></html>");
//            System.out.println("Wrong retyped password "+pass+" "+retypedPass);
            err_reg_label.setVisible(true);  
            return;
        }
        Customer c = new Customer(username, pass);
        System.out.println(c.password);
        if(!db.createNewUser(c))
        {
            err_reg_label.setText("<html><b><font size=5 face=\"Roboto\" color=\"white\">Error registering</font></b></html>");
            err_reg_label.setVisible(true);            
        }
        else
        {
//            
            c1 = c;
            callHomePanel("Registered You. Welcome!!!!");
        }
    }//GEN-LAST:event_reg_buttonMouseClicked

    private void add_wallet_buttonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_add_wallet_buttonMouseClicked
        try{
            double toAdd = Double.parseDouble(toAdd_wallet_field.getText());
            if(toAdd<=0)
            {
                throw new Exception();
            }
            String pass=String.valueOf(pass_wallet_field.getPassword());
            try{
            pass = Customer.HashPassword(pass);
            }
            catch(Exception e)
            {
                System.out.println("Exception thrown");
            }
            if(pass.equals(c1.password))
            {
                db.addMoney(c1,toAdd);
                callHomePanel("Money Added");
            }
            else
            {
                String msg="<html><b><font size=4 face=\"Roboto\" color=\"white\">Wrong Password</font></b></html>";
                err_wallet_label.setText(msg);
                err_wallet_label.setVisible(true);
            }
        }
        catch(Exception e)
        {
            String msg="<html><b><font size=4 face=\"Roboto\" color=\"white\">Please enter valid money</font></b></html>";
            err_wallet_label.setText(msg);  
            err_wallet_label.setVisible(true);
        }
    }//GEN-LAST:event_add_wallet_buttonMouseClicked

    private void home_wallet_buttonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_home_wallet_buttonMouseClicked
        callHomePanel();
    }//GEN-LAST:event_home_wallet_buttonMouseClicked

    private void from_comboboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_from_comboboxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_from_comboboxActionPerformed

    private void add_home_buttonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_add_home_buttonMouseClicked
        callWalletPanel();
    }//GEN-LAST:event_add_home_buttonMouseClicked

    private void home_book_buttonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_home_book_buttonMouseClicked
        if(home_book_button.isEnabled())
        callCabBookPanel();
        else
        {
            
        }
    }//GEN-LAST:event_home_book_buttonMouseClicked

    private void deets_home_buttonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_deets_home_buttonMouseClicked
        callConfirmPanel();
    }//GEN-LAST:event_deets_home_buttonMouseClicked

    private void home_confirm_buttonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_home_confirm_buttonMouseClicked
        callHomePanel();
    }//GEN-LAST:event_home_confirm_buttonMouseClicked

    private void wallet_confirm_buttonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_wallet_confirm_buttonMouseClicked
        callWalletPanel();
    }//GEN-LAST:event_wallet_confirm_buttonMouseClicked

    private void home_book_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_home_book_buttonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_home_book_buttonActionPerformed

    private void home_bookPanel_buttonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_home_bookPanel_buttonMouseClicked
        callHomePanel();
    }//GEN-LAST:event_home_bookPanel_buttonMouseClicked

    private void book_book_buttonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_book_book_buttonMouseClicked
        String src=(String)from_combobox.getSelectedItem();
        String dest=(String)to_combobox.getSelectedItem();
        if(src.equals(dest))
        {
            String msg="<html><b><font size=4 face=\"Roboto\" color=\"white\">Please choose different src and dest!!</font></b></html>";
            err_book_label.setText(msg);
            err_book_label.setVisible(true);
            //return;
        }
        else
        {
            posA=src;
            posB=dest;
            callConfirmPanel();
        }
    }//GEN-LAST:event_book_book_buttonMouseClicked

    private void confirm_confirm_buttonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_confirm_confirm_buttonMouseClicked
        System.out.println("CLicked confirm");
        System.out.println(posA+" "+posB);
        Driver d=db.getBestDriver(posA);
        if(d==null)
        {
            String msg="<html><b><font size=4 face=\"Roboto\" color=\"white\">Drivers not available</font></b></html>";
            err_confirm_label.setText(msg);
            err_confirm_label.setVisible(true);
            return;
        }
        else
        {
            System.out.println("Got a driver rye");
//            err_confirm_label.setText("No cabs pls");
//            err_confirm_label.setVisible(true);
//            return;
        }
        int dist = getDistance(posA,posB);
        double price = dist*100;
        long eta= dist*30000;
        if(price<=c1.w.money)
        {
            d.assignedCustomer = c1;
            if(db.startTrip(d, price, eta, posB, posA))
            {
                System.out.println("Got driverssss");
                callConfirmPanel();                
            }
            else
            {
                String msg="<html><b><font size=4 face=\"Roboto\" color=\"white\">Some error happened. Please book again</font></b></html>";
                err_confirm_label.setText(msg);
                err_confirm_label.setVisible(true);                
            }
//            callHomePanel("Cab Booked. CHeck deets");
        }
        else
        {
            String msg="<html><b><font size=4 face=\"Roboto\" color=\"white\">Not enough money</font></b></html>";            
            err_confirm_label.setText(msg);
            err_confirm_label.setVisible(true);
        }
    }//GEN-LAST:event_confirm_confirm_buttonMouseClicked

    private void NewUserButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_NewUserButtonMouseClicked
        callRegisterPanel();
        //        CardLayout cards=(CardLayout)mainPanel.getLayout();
        //        cards.show(mainPanel, "registerPanel");
    }//GEN-LAST:event_NewUserButtonMouseClicked

    private void ExistingUserButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ExistingUserButtonMouseClicked
//        createDrivers();
        callSignInPanel();
        //        CardLayout cards=(CardLayout)mainPanel.getLayout();
        //        cards.show(mainPanel, "signinPanel");
    }//GEN-LAST:event_ExistingUserButtonMouseClicked

    private void ExistingUserButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ExistingUserButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ExistingUserButtonActionPerformed

    private void pass_fieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pass_fieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_pass_fieldActionPerformed

    private void signInMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_signInMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_signInMouseEntered

    private void deets_home_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deets_home_buttonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_deets_home_buttonActionPerformed

    private void name_textActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_name_textActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_name_textActionPerformed

    private void home_confirm_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_home_confirm_buttonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_home_confirm_buttonActionPerformed

    private void bal_confirm_fieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bal_confirm_fieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_bal_confirm_fieldActionPerformed

    private void jMenuItem1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenuItem1MousePressed
        if(c1==null)
        {
            return;
        }
        callHomePanel();
    }//GEN-LAST:event_jMenuItem1MousePressed

    private void jMenuItem1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenuItem1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem1MouseClicked

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
        int x=slider.getValue();
        boolean var=db.updateDriverRating(prevDriver, x);
        System.out.println("Updated driver rating rey");
        callHomePanel("Rating updated. Thanks!");
    }//GEN-LAST:event_jButton1MouseClicked

    
    public void createDrivers()
    {
        Driver d0=new Driver("Asura103");
        d0.loc="C";
//        Driver d1=new Driver("B");
//        Driver d2=new Driver("C");
//        Driver d3=new Driver("D");
//        Driver d4=new Driver("E");
        if(db.createNewUser(d0)){System.out.println("Created "+d0.username);}else{System.out.println("A");}
//        if(db.createNewUser(d1)){}else{System.out.println("B");}
//        if(db.createNewUser(d1)){}else{System.out.println("C");}
//        if(db.createNewUser(d1)){}else{System.out.println("D");}
//        if(db.createNewUser(d1)){}else{System.out.println("E");}
        System.out.println("created new drivers");
    }
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ExistingUserButton;
    private javax.swing.JButton NewUserButton;
    private javax.swing.JButton add_home_button;
    private javax.swing.JButton add_wallet_button;
    private javax.swing.JTextField bal_confirm_field;
    private javax.swing.JLabel bal_confirm_panel;
    private javax.swing.JTextField bal_wallet_field;
    private javax.swing.JLabel bal_wallet_label;
    private javax.swing.JButton book_book_button;
    private javax.swing.JPanel cabBookPanel;
    private javax.swing.JTextField cabname_confirm_field;
    private javax.swing.JLabel cabname_confirm_label;
    private javax.swing.JPanel confirmPanel;
    private javax.swing.JButton confirm_confirm_button;
    private javax.swing.JTextField cost_confirm_field;
    private javax.swing.JLabel cost_confirm_label;
    private javax.swing.JButton deets_home_button;
    private javax.swing.JLabel email_label;
    private javax.swing.JTextField email_text;
    private javax.swing.JLabel err_book_label;
    private javax.swing.JLabel err_confirm_label;
    private javax.swing.JLabel err_reg_label;
    private javax.swing.JLabel err_signin_label;
    private javax.swing.JLabel err_wallet_label;
    private javax.swing.JLabel from_book_label;
    private javax.swing.JComboBox<String> from_combobox;
    private javax.swing.JLabel heading_review_label;
    private javax.swing.JPanel homePanel;
    private javax.swing.JButton home_bookPanel_button;
    private javax.swing.JButton home_book_button;
    private javax.swing.JButton home_confirm_button;
    private javax.swing.JLabel home_err_label;
    private javax.swing.JButton home_wallet_button;
    private javax.swing.JLabel img;
    private javax.swing.JPanel initPanel;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JLabel name_label;
    private javax.swing.JTextField name_text;
    private javax.swing.JPasswordField pass_field;
    private javax.swing.JLabel pass_label;
    private javax.swing.JPasswordField pass_reg_field;
    private javax.swing.JLabel pass_reg_label;
    private javax.swing.JPasswordField pass_wallet_field;
    private javax.swing.JLabel pass_wallet_label;
    private javax.swing.JTextField rating_confirm_field;
    private javax.swing.JLabel rating_confirm_label;
    private javax.swing.JButton reg_button;
    private javax.swing.JPanel registerPanel;
    private javax.swing.JButton reset;
    private javax.swing.JButton reset_reg_button;
    private javax.swing.JPasswordField retypepass_reg_field;
    private javax.swing.JPanel reviewPanel;
    private javax.swing.JLabel sideImg;
    private javax.swing.JButton signIn;
    private javax.swing.JMenuItem sign_out_menuitem;
    private javax.swing.JPanel signinPanel;
    private javax.swing.JSlider slider;
    private javax.swing.JTextField time_confirm_field;
    private javax.swing.JLabel time_confirm_label;
    private javax.swing.JTextField toAdd_wallet_field;
    private javax.swing.JLabel toAdd_wallet_label;
    private javax.swing.JLabel to_book_label;
    private javax.swing.JComboBox<String> to_combobox;
    private javax.swing.JTextField user_field;
    private javax.swing.JLabel user_label;
    private javax.swing.JTextField username_label;
    private javax.swing.JPanel walletPanel;
    private javax.swing.JButton wallet_confirm_button;
    private javax.swing.JTextField wallet_status_label;
    // End of variables declaration//GEN-END:variables
}
