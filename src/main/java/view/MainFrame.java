package view;

import com.formdev.flatlaf.FlatIntelliJLaf;
import com.itextpdf.text.DocumentException;
import control.comands.EnelComands;
import control.comands.IptuComands;
import control.comands.SaneagoComands;
import control.dueDate.DueDate;
import dev.botcity.maestro_sdk.BotExecutor;
import java.awt.AWTException;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
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
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.LayoutStyle;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileNameExtensionFilter;
import logs.Logs;

public final class MainFrame extends JFrame {

    private int aux;

    private int dataPagamento;

    private int selectedFunction;

    private Thread enelThread;

    private Thread saneagoThread;

    private Thread iptuThread;

    private Logs logs;

    private Date dataPagament;

    private JButton JbStop;

    private ButtonGroup buttonGroup1;

    private Box.Filler filler1;

    private JComboBox jComboBox1;

    private JLabel jLabel1;

    private JLabel jLabel2;

    private JPanel jPanel1;

    private JPanel jPanel2;

    private JScrollPane jScrollPane1;

    private JButton jbCancelar;

    private JButton jbEnel;

    private JButton jbIptu;

    private JButton jbOk;

    private JButton jbSaneago;

    private JComboBox<String> jcbDataPagamento;

    private JLabel jlDataPagamento;

    private JRadioButton jrbAnalise;

    private JRadioButton jrbBoletos;

    private JTextArea jtaActions;
    
    private String[] args;

    public MainFrame(String[] args) {
        this.args = args;
        initComponents();
        visilibity(false);
        setIconImage((new ImageIcon("icon/icon.png")).getImage());
        setLocationRelativeTo(null);
        this.jrbAnalise.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                MainFrame.this.jrbBoletos.setSelected(false);
                MainFrame.this.selectedFunction = 0;
            }
        });
        this.jrbBoletos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                MainFrame.this.jrbAnalise.setSelected(false);
                MainFrame.this.selectedFunction = 1;
            }
        });
    }

    public void visilibity(boolean mode) {
        this.jlDataPagamento.setVisible(mode);
        this.jcbDataPagamento.setVisible(mode);
        this.jbOk.setVisible(mode);
        this.jbCancelar.setVisible(mode);
        this.jrbAnalise.setVisible(mode);
        this.jrbBoletos.setVisible(mode);
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
                                cmd1.faturaEnel(file, MainFrame.this.jtaActions);
                            } catch (IOException ex) {
                                Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, (String) null, ex);
                            }
                        }
                    };
                    this.enelThread.start();
                    break;
                case 2:
                    this.saneagoThread = new Thread() {
                        public void run() {
                        BotExecutor.run(new SaneagoComands(MainFrame.this.jtaActions, file), args);
                        }
                        
                    };
                    this.saneagoThread.start();
                    break;
                case 3:
                    this.iptuThread = new Thread() {
                        public void run() {
                            IptuComands cmd3 = new IptuComands(MainFrame.this.jtaActions);
                            try {
                                cmd3.faturaIptu(file, MainFrame.this.dataPagamento, MainFrame.this.dataPagament, MainFrame.this.selectedFunction);
                            } catch (IOException ex) {
                                Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, (String) null, ex);
                            } catch (InterruptedException ex) {
                                Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (ParseException ex) {
                                Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (AWTException ex) {
                                Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (DocumentException ex) {
                                Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
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

    private void initComponents() {
        this.jComboBox1 = new JComboBox();
        this.buttonGroup1 = new ButtonGroup();
        this.filler1 = new Box.Filler(new Dimension(0, 10), new Dimension(0, 10), new Dimension(32767, 10));
        this.jPanel1 = new JPanel();
        this.jLabel2 = new JLabel();
        this.jScrollPane1 = new JScrollPane();
        this.jtaActions = new JTextArea();
        this.jPanel2 = new JPanel();
        this.jbEnel = new JButton();
        this.jbSaneago = new JButton();
        this.jLabel1 = new JLabel();
        this.jbIptu = new JButton();
        this.JbStop = new JButton();
        this.jlDataPagamento = new JLabel();
        this.jbOk = new JButton();
        this.jbCancelar = new JButton();
        this.jrbAnalise = new JRadioButton();
        this.jrbBoletos = new JRadioButton();
        this.jcbDataPagamento = new JComboBox<>();
        this.jComboBox1.setModel(new DefaultComboBoxModel<>(new String[]{"Item 1", "Item 2", "Item 3", "Item 4"}));
        setDefaultCloseOperation(3);
        setTitle("GuiaFácil");
        setIconImage((new ImageIcon("icon.png")).getImage());
        setResizable(false);
        this.jPanel1.setBackground(new Color(233, 233, 255));
        this.jPanel1.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 255)));
        this.jLabel2.setFont(new Font("Tahoma", 1, 15));
        this.jLabel2.setText("Ações");
        this.jtaActions.setEditable(false);
        this.jtaActions.setColumns(20);
        this.jtaActions.setRows(5);
        this.jScrollPane1.setViewportView(this.jtaActions);
        GroupLayout jPanel1Layout = new GroupLayout(this.jPanel1);
        this.jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(jPanel1Layout
                .createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(this.jScrollPane1)
                        .addContainerGap())
                .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(230, 230, 230)
                        .addComponent(this.jLabel2)
                        .addContainerGap(230, 32767)));
        jPanel1Layout.setVerticalGroup(jPanel1Layout
                .createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(this.jLabel2)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(this.jScrollPane1)));
        this.jPanel2.setBackground(new Color(233, 233, 255));
        this.jPanel2.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 255)));
        this.jbEnel.setBackground(new Color(0, 0, 255));
        this.jbEnel.setText("Enel");
        this.jbEnel.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
        this.jbEnel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                MainFrame.this.jbEnelActionPerformed(evt);
            }
        });
        this.jbSaneago.setBackground(new Color(0, 0, 255));
        this.jbSaneago.setText("Saneago");
        this.jbSaneago.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
        this.jbSaneago.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                MainFrame.this.jbSaneagoActionPerformed(evt);
            }
        });
        this.jLabel1.setFont(new Font("Tahoma", 1, 15));
        this.jLabel1.setHorizontalAlignment(0);
        this.jLabel1.setText("Automação");
        this.jbIptu.setBackground(new Color(0, 0, 255));
        this.jbIptu.setText("IPTU");
        this.jbIptu.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
        this.jbIptu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                MainFrame.this.jbIptuActionPerformed(evt);
            }
        });
        this.JbStop.setBackground(new Color(255, 0, 0));
        this.JbStop.setFont(new Font("Tahoma", 1, 11));
        this.JbStop.setText("PARAR AUTOMAÇÃO");
        this.JbStop.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                MainFrame.this.JbStopActionPerformed(evt);
            }
        });
        this.jlDataPagamento.setText("Data do pagamento:");
        this.jbOk.setText("OK");
        this.jbOk.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                MainFrame.this.jbOkActionPerformed(evt);
            }
        });
        this.jbCancelar.setText("Cancelar");
        this.jbCancelar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                MainFrame.this.jbCancelarActionPerformed(evt);
            }
        });
        this.jrbAnalise.setBackground(new Color(233, 233, 255));
        this.jrbAnalise.setText("Analise");
        this.jrbBoletos.setBackground(new Color(233, 233, 255));
        this.jrbBoletos.setText("Boletos");
        this.jcbDataPagamento.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                MainFrame.this.jcbDataPagamentoActionPerformed(evt);
            }
        });
        GroupLayout jPanel2Layout = new GroupLayout(this.jPanel2);
        this.jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(jPanel2Layout
                .createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addContainerGap(20, 32767)
                        .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(this.jrbAnalise)
                                        .addGap(18, 18, 18)
                                        .addComponent(this.jrbBoletos))
                                .addGroup(GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                        .addComponent(this.jbCancelar, -1, -1, 32767)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(this.jbOk, -2, 59, -2))
                                .addComponent(this.jlDataPagamento)
                                .addComponent(this.JbStop, GroupLayout.Alignment.TRAILING, -1, -1, 32767)
                                .addComponent(this.jbIptu, GroupLayout.Alignment.TRAILING, -1, -1, 32767)
                                .addComponent(this.jbSaneago, GroupLayout.Alignment.TRAILING, -1, -1, 32767)
                                .addComponent(this.jbEnel, GroupLayout.Alignment.TRAILING, -1, -1, 32767)
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGap(20, 20, 20)
                                        .addComponent(this.jLabel1))
                                .addComponent(this.jcbDataPagamento, 0, -1, 32767))
                        .addGap(20, 20, 20)));
        jPanel2Layout.setVerticalGroup(jPanel2Layout
                .createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(this.jLabel1)
                        .addGap(34, 34, 34)
                        .addComponent(this.jbEnel, -2, 24, -2)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(this.jbSaneago, -2, 24, -2)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(this.jbIptu, -2, 24, -2)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(this.jrbAnalise)
                                .addComponent(this.jrbBoletos))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(this.jlDataPagamento)
                        .addGap(8, 8, 8)
                        .addComponent(this.jcbDataPagamento, -2, -1, -2)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(this.jbOk)
                                .addComponent(this.jbCancelar))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 22, 32767)
                        .addComponent(this.JbStop)
                        .addContainerGap()));
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout
                .createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                        .addComponent(this.jPanel2, -2, -1, -2)
                        .addGap(0, 0, 0)
                        .addComponent(this.filler1, -2, -1, -2)
                        .addGap(0, 0, 0)
                        .addComponent(this.jPanel1, -2, -1, -2)));
        layout.setVerticalGroup(layout
                .createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                        .addComponent(this.filler1, -2, -1, -2)
                        .addContainerGap(-1, 32767))
                .addComponent(this.jPanel1, -1, -1, 32767)
                .addComponent(this.jPanel2, -1, -1, 32767));
        pack();
    }

    private void jbEnelActionPerformed(ActionEvent evt) {
        this.aux = 1;
        selectionMode(1);
    }

    private void jbSaneagoActionPerformed(ActionEvent evt) {
        this.aux = 2;
        selectionMode(2);
    }

    private void jbIptuActionPerformed(ActionEvent evt) {
        DueDate due = new DueDate();
        try {
            this.jcbDataPagamento.setModel(new DefaultComboBoxModel(due.workingDays15().toArray()));
        } catch (ParseException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, (String) null, ex);
        } catch (NoClassDefFoundError e) {
            e.printStackTrace();
        }
        if (!this.jrbAnalise.isSelected()) {
            this.jrbAnalise.doClick();
        }
        visilibity(true);
        JOptionPane.showMessageDialog(null, "Informe a data de pagamento");
    }

    private void JbStopActionPerformed(ActionEvent evt) {
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
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, (String) null, ex);
        }
    }

    private void jbOkActionPerformed(ActionEvent evt) {
        if (!this.jrbAnalise.isSelected() && !this.jrbBoletos.isSelected()) {
            JOptionPane.showMessageDialog(null, "Favor selecionar um tipo de procedimento", "Error", 0);
        } else {
            this.aux = 3;
            DateFormat dateFormat = new SimpleDateFormat((String) this.jcbDataPagamento.getSelectedItem());
            this.dataPagament = new Date();
            dateFormat.format(this.dataPagament);
            this.dataPagamento = this.jcbDataPagamento.getSelectedIndex();
            selectionMode(3);
            visilibity(false);
        }
    }

    private void jbCancelarActionPerformed(ActionEvent evt) {
        visilibity(false);
    }

    private void jcbDataPagamentoActionPerformed(ActionEvent evt) {
    }

    public static void main(String[] args) {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, (String) null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, (String) null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, (String) null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, (String) null, ex);
        }
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    UIManager.setLookAndFeel(new FlatIntelliJLaf());
                    (new MainFrame(args)).setVisible(true);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e.getMessage(), "Error", 0);
                }
            }
        });
    }
}
