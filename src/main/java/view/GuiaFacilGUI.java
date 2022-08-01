/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;

import com.formdev.flatlaf.FlatIntelliJLaf;
import com.itextpdf.text.DocumentException;
import control.comands.EnelComands;
import control.comands.IptuComands;
import control.comands.SaneagoComands;
import control.dueDate.DueDate;
import java.awt.AWTException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;
import logs.Logs;

/**
 *
 * @author italo.domingos
 */
public class GuiaFacilGUI extends javax.swing.JFrame {

    /**
     * Creates new form GuiaFacilGUI
     */
    private String[] args;
    private Thread enelThread;
    private Thread saneagoThread;
    private Thread iptuThread;
    private Date dataPagamento;
    private int selectedFunction;
    private int dataPagamentoIndex;
    private int aux;
    private Logs logs;
    
    public GuiaFacilGUI(String[] args) {
        this.args = args;
        initComponents();
        visilibity(false);
        setIconImage((new ImageIcon("icon/icon.png")).getImage());
        setLocationRelativeTo(null);
        this.jrbAnalise.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                GuiaFacilGUI.this.jrbBoletos.setSelected(false);
                GuiaFacilGUI.this.selectedFunction = 0;
            }
        });
        this.jrbBoletos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                GuiaFacilGUI.this.jrbAnalise.setSelected(false);
                GuiaFacilGUI.this.selectedFunction = 1;
            }
        });
    }
    
    public void visilibity(boolean mode) {
//        this.jlDataPagamento.setVisible(mode);
//        this.jcbDataPagamento.setVisible(mode);
//        this.jbOk.setVisible(mode);
//        this.jbCancelar.setVisible(mode);
//        this.jrbAnalise.setVisible(mode);
//        this.jrbBoletos.setVisible(mode);
        this.jpIPTU.setVisible(mode);
    }
    
     public void selectionMode(int tipoGuia) {
        JFileChooser fc = new JFileChooser();
        FileNameExtensionFilter fileFilter = new FileNameExtensionFilter("XLS", new String[]{"XLSX"});
        fc.setFileFilter(fileFilter);
        fc.setApproveButtonText("Selecionar");
        fc.setDialogTitle("Selecione a planilha de dados: ");
        int returnVal = fc.showOpenDialog(null);
        if (returnVal == 0) {
            final File file = fc.getSelectedFile();
            switch (tipoGuia) {
                case 1:
                    this.enelThread = new Thread() {
                        public void run() {
                            EnelComands cmd1 = new EnelComands();
                            try {
                                cmd1.faturaEnel(file, GuiaFacilGUI.this.jtaActions);
                            } catch (IOException ex) {
                                Logger.getLogger(GuiaFacilGUI.class.getName()).log(Level.SEVERE, (String) null, ex);
                            }
                        }
                    };
                    this.enelThread.start();
                    break;
                case 2:
                    this.saneagoThread = new Thread() {
                        public void run() {
                        new SaneagoComands(GuiaFacilGUI.this.jtaActions, file);
                        }
                        
                    };
                    this.saneagoThread.start();
                    break;
                case 3:
                    this.iptuThread = new Thread() {
                        public void run() {
                            IptuComands cmd3 = new IptuComands(GuiaFacilGUI.this.jtaActions);
                            try {
                                cmd3.faturaIptu(file, GuiaFacilGUI.this.dataPagamentoIndex, GuiaFacilGUI.this.dataPagamento, GuiaFacilGUI.this.selectedFunction);
                            } catch (IOException ex) {
                                Logger.getLogger(GuiaFacilGUI.class.getName()).log(Level.SEVERE, (String) null, ex);
                            } catch (InterruptedException ex) {
                                Logger.getLogger(GuiaFacilGUI.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (ParseException ex) {
                                Logger.getLogger(GuiaFacilGUI.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (AWTException ex) {
                                Logger.getLogger(GuiaFacilGUI.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (DocumentException ex) {
                                Logger.getLogger(GuiaFacilGUI.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    };
                    this.iptuThread.start();
                    break;
            }
        } else {
            System.out.println("Cancelado");
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jRadioButton1 = new javax.swing.JRadioButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jbEnel = new javax.swing.JButton();
        jbSaneago = new javax.swing.JButton();
        jbIptu = new javax.swing.JButton();
        jbStop = new javax.swing.JButton();
        jpIPTU = new javax.swing.JPanel();
        jrbAnalise = new javax.swing.JRadioButton();
        jrbBoletos = new javax.swing.JRadioButton();
        jlDataPagamento = new javax.swing.JLabel();
        jcbDataPagamento = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jbCancelar = new javax.swing.JButton();
        jbOk = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtaActions = new javax.swing.JTextArea();

        jRadioButton1.setText("jRadioButton1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Guia Fácil");
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(233, 233, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 255)));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText("Automação");

        jbEnel.setBackground(new java.awt.Color(0, 0, 255));
        jbEnel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jbEnel.setForeground(new java.awt.Color(255, 255, 255));
        jbEnel.setText("Enel");
        jbEnel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jbEnel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbEnelActionPerformed(evt);
            }
        });

        jbSaneago.setBackground(new java.awt.Color(0, 0, 255));
        jbSaneago.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jbSaneago.setForeground(new java.awt.Color(255, 255, 255));
        jbSaneago.setText("Saneago");
        jbSaneago.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jbSaneago.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbSaneagoActionPerformed(evt);
            }
        });

        jbIptu.setBackground(new java.awt.Color(0, 0, 255));
        jbIptu.setForeground(new java.awt.Color(255, 255, 255));
        jbIptu.setText("IPTU");
        jbIptu.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jbIptu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbIptuActionPerformed(evt);
            }
        });

        jbStop.setBackground(new java.awt.Color(204, 0, 0));
        jbStop.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jbStop.setForeground(new java.awt.Color(255, 255, 255));
        jbStop.setText("PARAR AUTOMAÇÃO");
        jbStop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbStopActionPerformed(evt);
            }
        });

        jpIPTU.setBackground(new java.awt.Color(233, 233, 255));
        jpIPTU.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jrbAnalise.setText("Analise");
        jrbAnalise.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jrbAnaliseActionPerformed(evt);
            }
        });

        jrbBoletos.setText("Boletos");

        jlDataPagamento.setText("Data do pagamento:");

        jLabel4.setText("Tipo de busca:");

        jbCancelar.setText("Cancelar");
        jbCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbCancelarActionPerformed(evt);
            }
        });

        jbOk.setText("OK");
        jbOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbOkActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpIPTULayout = new javax.swing.GroupLayout(jpIPTU);
        jpIPTU.setLayout(jpIPTULayout);
        jpIPTULayout.setHorizontalGroup(
            jpIPTULayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpIPTULayout.createSequentialGroup()
                .addGroup(jpIPTULayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpIPTULayout.createSequentialGroup()
                        .addComponent(jbCancelar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                        .addComponent(jbOk))
                    .addGroup(jpIPTULayout.createSequentialGroup()
                        .addGroup(jpIPTULayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jlDataPagamento)
                            .addGroup(jpIPTULayout.createSequentialGroup()
                                .addComponent(jrbAnalise)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jrbBoletos))
                            .addComponent(jLabel4))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addComponent(jcbDataPagamento, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jpIPTULayout.setVerticalGroup(
            jpIPTULayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpIPTULayout.createSequentialGroup()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpIPTULayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jrbAnalise)
                    .addComponent(jrbBoletos))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jlDataPagamento)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jcbDataPagamento, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
                .addGroup(jpIPTULayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbCancelar)
                    .addComponent(jbOk))
                .addGap(0, 0, 0))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(31, 31, 31))
                    .addComponent(jbEnel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jbSaneago, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jbIptu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jpIPTU, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbStop, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(30, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addGap(29, 29, 29)
                .addComponent(jbEnel, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jbSaneago, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jbIptu, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(jpIPTU, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 44, Short.MAX_VALUE)
                .addComponent(jbStop)
                .addContainerGap())
        );

        jPanel2.setBackground(new java.awt.Color(233, 233, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 255)));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel3.setText("Ações");

        jtaActions.setColumns(20);
        jtaActions.setRows(5);
        jtaActions.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jScrollPane1.setViewportView(jtaActions);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(240, 240, 240)
                .addComponent(jLabel3)
                .addContainerGap(218, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jbEnelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbEnelActionPerformed
        this.aux = 1;
        selectionMode(1);
    }//GEN-LAST:event_jbEnelActionPerformed

    private void jbSaneagoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbSaneagoActionPerformed
        this.aux = 2;
        selectionMode(2);
    }//GEN-LAST:event_jbSaneagoActionPerformed

    private void jbIptuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbIptuActionPerformed
        DueDate due = new DueDate();
        try {
            this.jcbDataPagamento.setModel(new DefaultComboBoxModel(due.workingDays15().toArray()));
        } catch (ParseException ex) {
            Logger.getLogger(GuiaFacilGUI.class.getName()).log(Level.SEVERE, (String) null, ex);
        } catch (NoClassDefFoundError e) {
            e.printStackTrace();
        }
        if (!this.jrbAnalise.isSelected()) {
            this.jrbAnalise.doClick();
        }
        visilibity(true);
        JOptionPane.showMessageDialog(null, "Informe a data de pagamento");
    }//GEN-LAST:event_jbIptuActionPerformed

    private void jrbAnaliseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jrbAnaliseActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jrbAnaliseActionPerformed

    private void jbStopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbStopActionPerformed
        try {
            this.logs = new Logs(this.jtaActions);
            switch (this.aux) {
                case 1:
                    this.enelThread.stop();
                    this.aux = 0;
                    this.logs.setLog("Automação parada");
                    return;
                case 2:
                    this.saneagoThread.stop();
                    this.aux = 0;
                    this.logs.setLog("Automação parada");
                    return;
                case 3:
                    this.iptuThread.stop();
                    this.aux = 0;
                    this.logs.setLog("Automação parada");
                    return;
            }
            JOptionPane.showMessageDialog(null, "Nenhuma automação foi inicializada", "Atenção", 2);
        } catch (IOException ex) {
            Logger.getLogger(GuiaFacilGUI.class.getName()).log(Level.SEVERE, (String) null, ex);
        }
    }//GEN-LAST:event_jbStopActionPerformed

    private void jbOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbOkActionPerformed
        if (!this.jrbAnalise.isSelected() && !this.jrbBoletos.isSelected()) {
            JOptionPane.showMessageDialog(null, "Favor selecionar um tipo de procedimento", "Error", 0);
        } else {
            this.aux = 3;
            DateFormat dateFormat = new SimpleDateFormat((String) this.jcbDataPagamento.getSelectedItem());
            this.dataPagamento = new Date();
            dateFormat.format(this.dataPagamento);
            this.dataPagamentoIndex = this.jcbDataPagamento.getSelectedIndex();
            selectionMode(3);
            visilibity(false);
        }
    }//GEN-LAST:event_jbOkActionPerformed

    private void jbCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbCancelarActionPerformed
        visilibity(false);
    }//GEN-LAST:event_jbCancelarActionPerformed

    /**
     * @param args the command line arguments
     */
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
            java.util.logging.Logger.getLogger(GuiaFacilGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GuiaFacilGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GuiaFacilGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GuiaFacilGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                 try {
                    UIManager.setLookAndFeel(new FlatIntelliJLaf());
                    new GuiaFacilGUI(args).setVisible(true);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e.getMessage(), "Error", 0);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton jbCancelar;
    private javax.swing.JButton jbEnel;
    private javax.swing.JButton jbIptu;
    private javax.swing.JButton jbOk;
    private javax.swing.JButton jbSaneago;
    private javax.swing.JButton jbStop;
    private javax.swing.JComboBox<String> jcbDataPagamento;
    private javax.swing.JLabel jlDataPagamento;
    private javax.swing.JPanel jpIPTU;
    private javax.swing.JRadioButton jrbAnalise;
    private javax.swing.JRadioButton jrbBoletos;
    private javax.swing.JTextArea jtaActions;
    // End of variables declaration//GEN-END:variables
}
