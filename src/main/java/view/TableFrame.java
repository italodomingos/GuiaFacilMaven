package view;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableModel;
import masks.Masks;
import validate.Validate;

public class TableFrame extends JFrame {
  Masks masks = new Masks();
  
  private JLabel jLabel1;
  
  private JLabel jLabel2;
  
  private JLabel jLabel3;
  
  private JScrollPane jScrollPane1;
  
  private JSeparator jSeparator1;
  
  private JButton jbInserir;
  
  private JFormattedTextField jftfCpfCnpj;
  
  private JFormattedTextField jftfUnidadeConsumidora;
  
  private JRadioButton jrbCnpj;
  
  private JRadioButton jrbCpf;
  
  private JTable jtData;
  
  private JTextField jtfTeste;
  
  public TableFrame() throws ParseException {
    initComponents();
    this.masks.cpfMask().install(this.jftfCpfCnpj);
    listenerRadioOn();
  }
  
  private void initComponents() {
    this.jScrollPane1 = new JScrollPane();
    this.jtData = new JTable();
    this.jLabel1 = new JLabel();
    this.jLabel2 = new JLabel();
    this.jLabel3 = new JLabel();
    this.jSeparator1 = new JSeparator();
    this.jbInserir = new JButton();
    this.jftfUnidadeConsumidora = new JFormattedTextField();
    this.jftfCpfCnpj = new JFormattedTextField();
    this.jrbCnpj = new JRadioButton();
    this.jrbCpf = new JRadioButton();
    this.jtfTeste = new JTextField();
    setDefaultCloseOperation(3);
    this.jtData.setModel(new DefaultTableModel(new Object[0][], (Object[])new String[] { "Unidade Consumidora", "CNPJ" }));
    this.jtData.setRequestFocusEnabled(false);
    this.jScrollPane1.setViewportView(this.jtData);
    this.jLabel1.setText("Unidade Consumidora: ");
    this.jLabel2.setText("Cnpj:");
    this.jLabel3.setFont(new Font("Tahoma", 0, 20));
    this.jLabel3.setText("Banco de dados");
    this.jbInserir.setText("Inserir");
    this.jbInserir.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent evt) {
            TableFrame.this.jbInserirActionPerformed(evt);
          }
        });
    this.jftfCpfCnpj.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent evt) {
            TableFrame.this.jftfCpfCnpjActionPerformed(evt);
          }
        });
    this.jrbCnpj.setText("Cnpj");
    this.jrbCpf.setText("Cpf");
    GroupLayout layout = new GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(layout
        .createParallelGroup(GroupLayout.Alignment.LEADING)
        .addComponent(this.jSeparator1)
        .addGroup(layout.createSequentialGroup()
          .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
              .addGap(215, 215, 215)
              .addComponent(this.jLabel3))
            .addGroup(layout.createSequentialGroup()
              .addContainerGap()
              .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(this.jScrollPane1, -2, 452, -2)
                .addGroup(layout.createSequentialGroup()
                  .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                      .addComponent(this.jLabel1)
                      .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                      .addComponent(this.jftfUnidadeConsumidora, -2, 139, -2))
                    .addGroup(layout.createSequentialGroup()
                      .addComponent(this.jrbCnpj)
                      .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                      .addComponent(this.jrbCpf)
                      .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, 32767)
                      .addComponent(this.jbInserir, -2, 100, -2))
                    .addGroup(layout.createSequentialGroup()
                      .addComponent(this.jLabel2, -2, 31, -2)
                      .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                      .addComponent(this.jftfCpfCnpj, -2, 219, -2)))
                  .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                  .addComponent(this.jtfTeste, -2, 219, -2)))))
          .addContainerGap(71, 32767)));
    layout.setVerticalGroup(layout
        .createParallelGroup(GroupLayout.Alignment.LEADING)
        .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
          .addGap(14, 14, 14)
          .addComponent(this.jLabel3)
          .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
          .addComponent(this.jSeparator1, -2, 10, -2)
          .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
          .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
            .addComponent(this.jLabel1)
            .addComponent(this.jftfUnidadeConsumidora, -2, -1, -2))
          .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
          .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
            .addComponent(this.jLabel2)
            .addComponent(this.jftfCpfCnpj, -2, -1, -2)
            .addComponent(this.jtfTeste, -2, -1, -2))
          .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
              .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, 32767)
              .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(this.jrbCpf)
                .addComponent(this.jrbCnpj))
              .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED))
            .addGroup(layout.createSequentialGroup()
              .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
              .addComponent(this.jbInserir)
              .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, 32767)))
          .addComponent(this.jScrollPane1, -2, 139, -2)
          .addGap(12, 12, 12)));
    pack();
  }
  
  private void jbInserirActionPerformed(ActionEvent evt) {
    if (Validate.isCnpj(this.jftfCpfCnpj.getText())) {
      JOptionPane.showMessageDialog(null, "Validado!!");
    } else {
      JOptionPane.showMessageDialog(null, "deu ruim");
    } 
  }
  
  private void jftfCpfCnpjActionPerformed(ActionEvent evt) {}
  
  public static void main(String[] args) {
    try {
      for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
        if ("Nimbus".equals(info.getName())) {
          UIManager.setLookAndFeel(info.getClassName());
          break;
        } 
      } 
    } catch (ClassNotFoundException ex) {
      Logger.getLogger(TableFrame.class.getName()).log(Level.SEVERE, (String)null, ex);
    } catch (InstantiationException ex) {
      Logger.getLogger(TableFrame.class.getName()).log(Level.SEVERE, (String)null, ex);
    } catch (IllegalAccessException ex) {
      Logger.getLogger(TableFrame.class.getName()).log(Level.SEVERE, (String)null, ex);
    } catch (UnsupportedLookAndFeelException ex) {
      Logger.getLogger(TableFrame.class.getName()).log(Level.SEVERE, (String)null, ex);
    } 
    EventQueue.invokeLater(new Runnable() {
          public void run() {
            try {
              (new TableFrame()).setVisible(true);
            } catch (ParseException ex) {
              Logger.getLogger(TableFrame.class.getName()).log(Level.SEVERE, (String)null, ex);
            } 
          }
        });
  }
  
  public void listenerRadioOn() {
    this.jrbCnpj.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            try {
              TableFrame.this.jrbCpf.setSelected(false);
              TableFrame.this.jftfCpfCnpj.setFormatterFactory((JFormattedTextField.AbstractFormatterFactory)null);
              TableFrame.this.masks.removeMask();
              TableFrame.this.masks.cnpjMask().install(TableFrame.this.jftfCpfCnpj);
            } catch (ParseException ex) {
              Logger.getLogger(TableFrame.class.getName()).log(Level.SEVERE, (String)null, ex);
            } 
          }
        });
    this.jrbCpf.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            try {
              TableFrame.this.jrbCnpj.setSelected(false);
              TableFrame.this.masks.removeMask();
              TableFrame.this.jftfCpfCnpj.setFormatterFactory((JFormattedTextField.AbstractFormatterFactory)null);
              TableFrame.this.masks.cpfMask().install(TableFrame.this.jftfCpfCnpj);
            } catch (ParseException ex) {
              Logger.getLogger(TableFrame.class.getName()).log(Level.SEVERE, (String)null, ex);
            } 
          }
        });
  }
}
