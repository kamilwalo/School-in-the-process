package org.school.swing;

import org.hibernate.Session;
import org.school.DbConnector;

import javax.swing.*;
import java.awt.*;

public class Login extends JFrame{
    private JPasswordField passwordField;
    private JTextField loginField;
    private JButton loginButton;
    private JButton exitButton;
    private JPanel mainPanel;
    private JLabel titleText;
    private JLabel errorJLabel;

    DbConnector dbConnector = new DbConnector();
    Session session = dbConnector.getSf().getCurrentSession();

    public Login() {
        setContentPane(mainPanel);

        errorJLabel.setVisible(false);
        errorJLabel.setFont(new Font("Times New Roman",Font.BOLD,20));
        errorJLabel.setForeground(Color.red);

        titleText.setFont(new Font("Times New Roman",Font.BOLD,60));
        setSize(new Dimension(500, 350));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        loginField.setPreferredSize(new Dimension(300,25));
        passwordField.setPreferredSize(new Dimension(300,25));

        setVisible(true);
        loginButton.addActionListener(e -> {
            session = dbConnector.getSf().getCurrentSession();
            session.beginTransaction();
            Object result = null;
            try {   //this query checking if login and password is correctly
                result = session.createQuery("from Login where login=:login and password =:password")
                        .setParameter("login", loginField.getText())
                        .setParameter("password", String.valueOf(passwordField.getPassword()))
                        .getSingleResult();
            }catch(Exception ex){
                errorJLabel.setVisible(true);
            }
            session.getTransaction().commit();
            if(result!=null) {
                new MainWindow();
                dispose();
            }
        });
    }
}