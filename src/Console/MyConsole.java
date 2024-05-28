package Console;

import Controller.Habitat;
import Rabbits.AlbinosRabbit;
import Rabbits.Rabbit;
import Template.Singleton;

import javax.swing.*;
import java.awt.*;
import java.util.Scanner;

public class MyConsole {
    private JTextArea jTextArea;
    public static JTextField jTextField;
    private JDialog jDialog;
    private Scanner scanner;

    public MyConsole() {
        jDialog = new JDialog();
        jTextArea = new JTextArea();
        jTextArea.setBackground(Color.BLACK);
        jTextArea.setForeground(Color.GREEN);

        jTextField = new JTextField(">");
        jTextField.setBackground(Color.BLACK);
        jTextField.setForeground(Color.WHITE);

        jTextArea.setEditable(false);
        jTextField.requestFocusInWindow();
        jDialog.add(jTextArea);
        jDialog.add(jTextField, BorderLayout.SOUTH);

        jTextField.addActionListener(e -> {
            String text = jTextField.getText();
            inputText(text);
            jTextField.setText(">");
        });
        jDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        jDialog.setSize(600,400);
        jDialog.setLocationRelativeTo(null);
    }

    public void inputText(String text){
        if(text.startsWith(">Сократить число кроликов-альбиносов на ")){
            double prosent = Double.parseDouble(text.split(" ")[4].split("%")[0]);
            int cnt = 0;
            for (Rabbit rabbit: Singleton.getInstance().getVectorList()){
                if(rabbit instanceof AlbinosRabbit){
                    cnt++;
                }
            }
            int sockr = (int) (cnt * (1.0 - (prosent/100)));
            for (int i = 0; i < Singleton.getInstance().getVectorList().size(); i++) {
                if(cnt > sockr){
                    Singleton.getInstance().removeObj(Singleton.getInstance().getVectorList().get(i));
                    System.out.println("удалил кролика");
                    cnt--;
                }
            }
        }else{
            jTextArea.append("Такой команды не существует." + "\n"
            + "Существует одна команда:" + "\n"
            + "Сократить число кроликов-альбиносов на N%" + "\n");
        }
    }
    public void showConsole(){
        jDialog.setVisible(true);
    }
}