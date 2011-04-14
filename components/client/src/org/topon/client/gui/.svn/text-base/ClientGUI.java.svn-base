/*
 * ClientGUI.java
 *
 * Created on July 26, 2007, 10:58 AM
 */

package org.topon.client.gui;

import org.topon.client.gui.DocumentInfoFrame;
import java.awt.Dimension;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.Properties;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.ListModel;
import javax.swing.event.ListDataListener;
import javax.xml.crypto.MarshalException;
import javax.xml.crypto.dsig.XMLSignatureException;
import javax.xml.parsers.ParserConfigurationException;
import org.topon.client.URLClientException;
import org.topon.configuration.Configuration;
import org.topon.configuration.identity.IdentityFactory;
import org.topon.configuration.identity.IdentityInterface;
import java.util.Vector;
import org.topon.client.URLClient;
import org.topon.database.DocumentModel;
import org.topon.database.SignatureModel;
import org.topon.signatures.XMLSignatureHelper;
import org.topon.signatures.jdk16.DetachedXMLSignatureJDK;
import org.w3c.dom.Document;
/**
 *
 * @author  topon
 */
public class ClientGUI extends javax.swing.JFrame {
    
    private URLClient urlClient;
    private Configuration configuration;
    private IdentityInterface identity;
    private Vector documentsToSignVector = new Vector();
    
    
    private Document xmlSignatureDoc;
    
    /** Creates new form ClientGUI */
    public ClientGUI(Configuration configuration) {
        this.configuration = configuration;
        initComponents();
        setUpConfigurationComponents();
        setUpGlobalIdentity();
        setUpURLClient();
        setUpDocuments();
    }

    /**
     * Must be run after calling Constructor
     */
    public  void run() {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                setVisible(true);
            }
        });
    }
    
    private DefaultListModel sampleModel;
    private DocumentModelListModel docModListMod;
    private void setUpDocuments() {
        documentsToSignVector = getDocumentsToSign();
        sampleModel = new DefaultListModel();
        docModListMod = new DocumentModelListModel(documentsToSignVector);
        /*
        for (int i = 0; i < docuementsToSignVector.size();i++ ) {
            DocumentModel docMod = (DocumentModel)docuementsToSignVector.get(i);
            sampleModel.add(i,docMod.getDocURL());
        }
*/        
        
        //documentsjList1.setModel(sampleModel);
        documentsjList1.setModel(docModListMod);
        documentsjList1.setSelectedIndex(0);
    }
    
    
    private Vector getDocumentsToSign() {
        return urlClient.getDocumentsToSignFromServer();
    }
    
    private void setUpURLClient() {
        urlClient = new URLClient(identity);
    }
    
    /**
     * Set up Global Identity from Configuration object
     */
    private void setUpGlobalIdentity() {
        this.identity = IdentityFactory.createIdentity(configuration);
        aliasIdentityjTextField1.setText(identity.getAlias());
        X509Certificate cert = identity.getX509Cetificate();
        issuerjTextField.setText(cert.getIssuerDN().getName());
        subjectjTextField.setText(cert.getSubjectDN().getName());
        cert.getSigAlgName();
    }
    private void setUpConfigurationComponents() {
        keyStorejTextField1.setText(configuration.getKeystore());
        keyStoreTypejTextField2.setText(configuration.getKeystoreType());
        aliasjTextField3.setText(configuration.getAlias());
        passwordjTextField4.setText(configuration.getPassword());
        signatureStoreURLjTextField1.setText(configuration.getSignatureStoreURL());
        documentsStoreURLjTextField2.setText(configuration.getDocumentsStoreURL());
    }

    private void loadGlobalConfigurationFromTextFields() {
        configuration.setKeystore(keyStorejTextField1.getText());
        configuration.setKeystoreType(keyStoreTypejTextField2.getText());
        configuration.setAlias(aliasjTextField3.getText());
        configuration.setPassword(passwordjTextField4.getText());
        configuration.setSignatureStoreURL(signatureStoreURLjTextField1.getText());
        configuration.setDocumentsStoreURL(documentsStoreURLjTextField2.getText());
    }


    private void errorOption(String message, String name) {
        JOptionPane.showMessageDialog(this,
                message,
                name,
                JOptionPane.ERROR_MESSAGE);
        
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jMenuBar1 = new javax.swing.JMenuBar();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        signaturesCardjPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        signaturesInfoJPanel = new javax.swing.JPanel();
        loginSignaturejTextField1 = new javax.swing.JTextField();
        CNSignaturejTextField2 = new javax.swing.JTextField();
        urlSignaturejTextField3 = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        xmlSignaturejTextArea1 = new javax.swing.JTextArea();
        loginSignaturejLabel6 = new javax.swing.JLabel();
        subjectSignaturejLabel6 = new javax.swing.JLabel();
        urlSignatureJLabel = new javax.swing.JLabel();
        xmlSignaurejLabel7 = new javax.swing.JLabel();
        actionSignaturesjPanel10 = new javax.swing.JPanel();
        storeSignatureButtonjPanel8 = new javax.swing.JPanel();
        storejButton = new javax.swing.JButton();
        pathToStoreSignatureJField = new javax.swing.JTextField();
        pathToLoadSignatureJField = new javax.swing.JTextField();
        loadJButton1 = new javax.swing.JButton();
        browseStorejButton2 = new javax.swing.JButton();
        browseLoadjButton3 = new javax.swing.JButton();
        signSendButtonjPanel = new javax.swing.JPanel();
        signjButton = new javax.swing.JButton();
        sendjButton = new javax.swing.JButton();
        documentsCardjPanel2 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        documentsjList1 = new javax.swing.JList();
        jPanel5 = new javax.swing.JPanel();
        selectURLjButton1 = new javax.swing.JButton();
        infojButton1 = new javax.swing.JButton();
        identityCardjPanel7 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        configurationjPanel5 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        keystoreTypeLabel = new javax.swing.JLabel();
        aliasLabel = new javax.swing.JLabel();
        paswordLabel = new javax.swing.JLabel();
        keyStorejTextField1 = new javax.swing.JTextField();
        keyStoreTypejTextField2 = new javax.swing.JTextField();
        aliasjTextField3 = new javax.swing.JTextField();
        passwordjTextField4 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        signatureStoreURLjTextField1 = new javax.swing.JTextField();
        documentsStoreURLjTextField2 = new javax.swing.JTextField();
        identityJPanel = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        aliasIdentityjTextField1 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        issuerjTextField = new javax.swing.JTextField();
        subjectjTextField = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        ReloadConfigurationjButton1 = new javax.swing.JButton();
        saveConfigurationjButton1 = new javax.swing.JButton();
        openConfigurationjButton1 = new javax.swing.JButton();
        urlToSignJTextField = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jMenuBar2 = new javax.swing.JMenuBar();
        filejMenu1 = new javax.swing.JMenu();
        exitMenuItem1 = new javax.swing.JMenuItem();
        signaturesJmenu = new javax.swing.JMenu();
        Sign = new javax.swing.JMenuItem();
        Send = new javax.swing.JMenuItem();
        StoreJmenuItem = new javax.swing.JMenuItem();
        loadjMenuItem2 = new javax.swing.JMenuItem();
        documentsjMenu2 = new javax.swing.JMenu();
        selectURLjMenuItem2 = new javax.swing.JMenuItem();
        identityjMenu1 = new javax.swing.JMenu();
        saveConfigjMenuItem2 = new javax.swing.JMenuItem();
        loadConfigjMenuItem3 = new javax.swing.JMenuItem();
        ReloadConfigjMenuItem4 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        jTabbedPane1.setName("Signatures");
        signaturesCardjPanel1.setLayout(new java.awt.CardLayout());

        signaturesCardjPanel1.setName("signatures");
        jPanel3.setLayout(new java.awt.BorderLayout());

        jPanel3.setMinimumSize(new java.awt.Dimension(800, 600));
        signaturesInfoJPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Actual Signed URL"));
        loginSignaturejTextField1.setEditable(false);

        CNSignaturejTextField2.setEditable(false);

        urlSignaturejTextField3.setEditable(false);

        xmlSignaturejTextArea1.setColumns(20);
        xmlSignaturejTextArea1.setEditable(false);
        xmlSignaturejTextArea1.setRows(5);
        jScrollPane2.setViewportView(xmlSignaturejTextArea1);

        loginSignaturejLabel6.setText("User");

        subjectSignaturejLabel6.setText("Subject");

        urlSignatureJLabel.setText("SignedURL");

        xmlSignaurejLabel7.setText("XML Signature");

        org.jdesktop.layout.GroupLayout signaturesInfoJPanelLayout = new org.jdesktop.layout.GroupLayout(signaturesInfoJPanel);
        signaturesInfoJPanel.setLayout(signaturesInfoJPanelLayout);
        signaturesInfoJPanelLayout.setHorizontalGroup(
            signaturesInfoJPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(signaturesInfoJPanelLayout.createSequentialGroup()
                .add(112, 112, 112)
                .add(signaturesInfoJPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(urlSignatureJLabel)
                    .add(xmlSignaurejLabel7)
                    .add(subjectSignaturejLabel6)
                    .add(loginSignaturejLabel6))
                .add(17, 17, 17)
                .add(signaturesInfoJPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(urlSignaturejTextField3, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 801, Short.MAX_VALUE)
                    .add(CNSignaturejTextField2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 801, Short.MAX_VALUE)
                    .add(loginSignaturejTextField1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 801, Short.MAX_VALUE)
                    .add(jScrollPane2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 801, Short.MAX_VALUE))
                .addContainerGap())
        );
        signaturesInfoJPanelLayout.setVerticalGroup(
            signaturesInfoJPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(signaturesInfoJPanelLayout.createSequentialGroup()
                .add(37, 37, 37)
                .add(signaturesInfoJPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(loginSignaturejLabel6)
                    .add(loginSignaturejTextField1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(28, 28, 28)
                .add(signaturesInfoJPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(subjectSignaturejLabel6)
                    .add(CNSignaturejTextField2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(28, 28, 28)
                .add(signaturesInfoJPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(urlSignatureJLabel)
                    .add(urlSignaturejTextField3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(27, 27, 27)
                .add(signaturesInfoJPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(xmlSignaurejLabel7)
                    .add(jScrollPane2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 145, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3.add(signaturesInfoJPanel, java.awt.BorderLayout.NORTH);

        actionSignaturesjPanel10.setLayout(new java.awt.BorderLayout());

        actionSignaturesjPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder("Actions"));
        storeSignatureButtonjPanel8.setLayout(new java.awt.GridBagLayout());

        storeSignatureButtonjPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder("Store/Load Signature"));
        storejButton.setText("store");
        storejButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                storejButtonActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 25;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 16);
        storeSignatureButtonjPanel8.add(storejButton, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 250;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 10);
        storeSignatureButtonjPanel8.add(pathToStoreSignatureJField, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 251;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 10);
        storeSignatureButtonjPanel8.add(pathToLoadSignatureJField, gridBagConstraints);

        loadJButton1.setText("load");
        loadJButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadJButton1ActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 25;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 16);
        storeSignatureButtonjPanel8.add(loadJButton1, gridBagConstraints);

        browseStorejButton2.setText("Browse");
        browseStorejButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                browseStorejButton2ActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 24;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(5, 24, 5, 24);
        storeSignatureButtonjPanel8.add(browseStorejButton2, gridBagConstraints);

        browseLoadjButton3.setText("Browse");
        browseLoadjButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                browseLoadjButton3ActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(5, 24, 5, 24);
        storeSignatureButtonjPanel8.add(browseLoadjButton3, gridBagConstraints);

        actionSignaturesjPanel10.add(storeSignatureButtonjPanel8, java.awt.BorderLayout.SOUTH);

        signSendButtonjPanel.setLayout(new java.awt.GridBagLayout());

        signSendButtonjPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("sign/send"));
        signjButton.setText("Sign");
        signjButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                signjButtonActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 20;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 16, 5, 16);
        signSendButtonjPanel.add(signjButton, gridBagConstraints);

        sendjButton.setText("Send");
        sendjButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendjButtonActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 20;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(5, 16, 5, 16);
        signSendButtonjPanel.add(sendjButton, gridBagConstraints);

        actionSignaturesjPanel10.add(signSendButtonjPanel, java.awt.BorderLayout.NORTH);

        jPanel3.add(actionSignaturesjPanel10, java.awt.BorderLayout.CENTER);

        signaturesCardjPanel1.add(jPanel3, "card2");

        jTabbedPane1.addTab("signatures", signaturesCardjPanel1);

        documentsCardjPanel2.setLayout(new java.awt.CardLayout());

        jPanel6.setLayout(new java.awt.BorderLayout());

        jPanel2.setLayout(new java.awt.GridLayout());

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Documents to Sign"));
        documentsjList1.setSelectedIndex(1);
        documentsjList1.setVisibleRowCount(16);
        documentsjList1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                documentsjList1MouseClicked(evt);
            }
        });

        jScrollPane1.setViewportView(documentsjList1);

        jPanel2.add(jScrollPane1);

        jPanel6.add(jPanel2, java.awt.BorderLayout.CENTER);

        jPanel5.setLayout(new java.awt.GridBagLayout());

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Actions button"));
        selectURLjButton1.setText("Select URL");
        selectURLjButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectURLjButton1ActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.ipadx = 25;
        gridBagConstraints.insets = new java.awt.Insets(7, 20, 7, 20);
        jPanel5.add(selectURLjButton1, gridBagConstraints);

        infojButton1.setText("info");
        infojButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                infojButton1ActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 52;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 20);
        jPanel5.add(infojButton1, gridBagConstraints);

        jPanel6.add(jPanel5, java.awt.BorderLayout.SOUTH);

        documentsCardjPanel2.add(jPanel6, "card2");

        jTabbedPane1.addTab("documents", documentsCardjPanel2);

        identityCardjPanel7.setLayout(new java.awt.CardLayout());

        jPanel4.setLayout(new java.awt.BorderLayout());

        configurationjPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Configuration"));
        jLabel1.setText("Keystore Path");

        keystoreTypeLabel.setText("Keystore Type");

        aliasLabel.setText("Alias");

        paswordLabel.setText("KeyStore password");

        jLabel6.setText("Signature Store");

        jLabel7.setText("Documents Store");

        org.jdesktop.layout.GroupLayout configurationjPanel5Layout = new org.jdesktop.layout.GroupLayout(configurationjPanel5);
        configurationjPanel5.setLayout(configurationjPanel5Layout);
        configurationjPanel5Layout.setHorizontalGroup(
            configurationjPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(configurationjPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .add(configurationjPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jLabel1)
                    .add(jLabel6)
                    .add(jLabel7)
                    .add(keystoreTypeLabel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 131, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(paswordLabel)
                    .add(aliasLabel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 111, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(39, 39, 39)
                .add(configurationjPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                    .add(keyStorejTextField1)
                    .add(documentsStoreURLjTextField2)
                    .add(signatureStoreURLjTextField1)
                    .add(passwordjTextField4)
                    .add(aliasjTextField3)
                    .add(keyStoreTypejTextField2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 703, Short.MAX_VALUE))
                .addContainerGap(125, Short.MAX_VALUE))
        );
        configurationjPanel5Layout.setVerticalGroup(
            configurationjPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(configurationjPanel5Layout.createSequentialGroup()
                .add(configurationjPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel1)
                    .add(keyStorejTextField1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(configurationjPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(configurationjPanel5Layout.createSequentialGroup()
                        .add(32, 32, 32)
                        .add(keystoreTypeLabel))
                    .add(configurationjPanel5Layout.createSequentialGroup()
                        .add(18, 18, 18)
                        .add(keyStoreTypejTextField2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .add(17, 17, 17)
                .add(configurationjPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(aliasLabel)
                    .add(aliasjTextField3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(14, 14, 14)
                .add(configurationjPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(paswordLabel)
                    .add(passwordjTextField4, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(16, 16, 16)
                .add(configurationjPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel6)
                    .add(signatureStoreURLjTextField1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(22, 22, 22)
                .add(configurationjPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel7)
                    .add(documentsStoreURLjTextField2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(218, Short.MAX_VALUE))
        );
        jPanel4.add(configurationjPanel5, java.awt.BorderLayout.CENTER);

        identityJPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Loaded Identity"));
        jLabel2.setText("IdentityAlias");

        aliasIdentityjTextField1.setEnabled(false);

        jLabel3.setText("X509 Certificate Issuer");

        jLabel4.setText("X509 Certificate Owner");

        issuerjTextField.setEnabled(false);

        subjectjTextField.setEnabled(false);

        org.jdesktop.layout.GroupLayout identityJPanelLayout = new org.jdesktop.layout.GroupLayout(identityJPanel);
        identityJPanel.setLayout(identityJPanelLayout);
        identityJPanelLayout.setHorizontalGroup(
            identityJPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(identityJPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(identityJPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jLabel2)
                    .add(jLabel3)
                    .add(jLabel4))
                .add(58, 58, 58)
                .add(identityJPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                    .add(subjectjTextField)
                    .add(issuerjTextField)
                    .add(aliasIdentityjTextField1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 569, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(259, Short.MAX_VALUE))
        );
        identityJPanelLayout.setVerticalGroup(
            identityJPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(identityJPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(identityJPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel2)
                    .add(aliasIdentityjTextField1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(15, 15, 15)
                .add(identityJPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel3)
                    .add(issuerjTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(18, 18, 18)
                .add(identityJPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(jLabel4)
                    .add(subjectjTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(31, Short.MAX_VALUE))
        );
        jPanel4.add(identityJPanel, java.awt.BorderLayout.NORTH);

        jPanel1.setLayout(new java.awt.GridBagLayout());

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("reload configuration"));
        ReloadConfigurationjButton1.setText("Reload");
        ReloadConfigurationjButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ReloadConfigurationjButton1ActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 30;
        gridBagConstraints.insets = new java.awt.Insets(15, 5, 15, 5);
        jPanel1.add(ReloadConfigurationjButton1, gridBagConstraints);

        saveConfigurationjButton1.setText("Save");
        saveConfigurationjButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveConfigurationjButton1ActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 30;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 5, 15);
        jPanel1.add(saveConfigurationjButton1, gridBagConstraints);

        openConfigurationjButton1.setText("open");
        openConfigurationjButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openConfigurationjButton1ActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.ipadx = 30;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 5, 15);
        jPanel1.add(openConfigurationjButton1, gridBagConstraints);

        jPanel4.add(jPanel1, java.awt.BorderLayout.SOUTH);

        identityCardjPanel7.add(jPanel4, "card2");

        jTabbedPane1.addTab("Identity", identityCardjPanel7);

        jTabbedPane1.getAccessibleContext().setAccessibleName("Signatures");

        urlToSignJTextField.setEditable(false);

        jLabel5.setText("URL to Sign");

        filejMenu1.setText("File");
        exitMenuItem1.setText("Exit");
        exitMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitMenuItem1ActionPerformed(evt);
            }
        });

        filejMenu1.add(exitMenuItem1);

        jMenuBar2.add(filejMenu1);

        signaturesJmenu.setText("Signatures");
        Sign.setText("Sign");
        Sign.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SignActionPerformed(evt);
            }
        });

        signaturesJmenu.add(Sign);

        Send.setText("Send");
        Send.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SendActionPerformed(evt);
            }
        });

        signaturesJmenu.add(Send);

        StoreJmenuItem.setText("Store");
        StoreJmenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                StoreJmenuItemActionPerformed(evt);
            }
        });

        signaturesJmenu.add(StoreJmenuItem);

        loadjMenuItem2.setText("Load");
        loadjMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadjMenuItem2ActionPerformed(evt);
            }
        });

        signaturesJmenu.add(loadjMenuItem2);

        jMenuBar2.add(signaturesJmenu);

        documentsjMenu2.setText("Documents");
        selectURLjMenuItem2.setText("Select URL");
        selectURLjMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectURLjMenuItem2ActionPerformed(evt);
            }
        });

        documentsjMenu2.add(selectURLjMenuItem2);

        jMenuBar2.add(documentsjMenu2);

        identityjMenu1.setText("Identity");
        saveConfigjMenuItem2.setText("Save Config");
        saveConfigjMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveConfigjMenuItem2ActionPerformed(evt);
            }
        });

        identityjMenu1.add(saveConfigjMenuItem2);

        loadConfigjMenuItem3.setText("Open Config");
        loadConfigjMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadConfigjMenuItem3ActionPerformed(evt);
            }
        });

        identityjMenu1.add(loadConfigjMenuItem3);

        ReloadConfigjMenuItem4.setText("Reload config");
        ReloadConfigjMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ReloadConfigjMenuItem4ActionPerformed(evt);
            }
        });

        identityjMenu1.add(ReloadConfigjMenuItem4);

        jMenuBar2.add(identityjMenu1);

        setJMenuBar(jMenuBar2);

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createSequentialGroup()
                        .add(49, 49, 49)
                        .add(jLabel5)
                        .add(22, 22, 22)
                        .add(urlToSignJTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 493, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(layout.createSequentialGroup()
                        .add(28, 28, 28)
                        .add(jTabbedPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 1025, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(21, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                .add(26, 26, 26)
                .add(jTabbedPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 724, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 23, Short.MAX_VALUE)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(urlToSignJTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel5, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 19, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(36, 36, 36))
        );
        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void ReloadConfigjMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ReloadConfigjMenuItem4ActionPerformed
        ReloadConfigurationjButton1ActionPerformed(evt);
    }//GEN-LAST:event_ReloadConfigjMenuItem4ActionPerformed

    private void loadConfigjMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadConfigjMenuItem3ActionPerformed
        openConfigurationjButton1ActionPerformed(evt);
    }//GEN-LAST:event_loadConfigjMenuItem3ActionPerformed

    private void saveConfigjMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveConfigjMenuItem2ActionPerformed
        saveConfigurationjButton1ActionPerformed(evt);
    }//GEN-LAST:event_saveConfigjMenuItem2ActionPerformed

    private void selectURLjMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectURLjMenuItem2ActionPerformed
        selectURLjButton1ActionPerformed(evt);
    }//GEN-LAST:event_selectURLjMenuItem2ActionPerformed

    private void loadjMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadjMenuItem2ActionPerformed
        loadJButton1ActionPerformed(evt);
    }//GEN-LAST:event_loadjMenuItem2ActionPerformed

    private void StoreJmenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_StoreJmenuItemActionPerformed
        storejButtonActionPerformed(evt);
    }//GEN-LAST:event_StoreJmenuItemActionPerformed

    private void SendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SendActionPerformed
        sendjButtonActionPerformed(evt);
    }//GEN-LAST:event_SendActionPerformed

    private void SignActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SignActionPerformed
        signjButtonActionPerformed(evt);
    }//GEN-LAST:event_SignActionPerformed

    private void exitMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitMenuItem1ActionPerformed
        dispose();
    }//GEN-LAST:event_exitMenuItem1ActionPerformed

    private void infojButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_infojButton1ActionPerformed
        DocumentModel docMod = (DocumentModel)documentsjList1.getSelectedValue();
        if(docMod != null) {
        DocumentInfoFrame docInfr = new DocumentInfoFrame(docMod);
        docInfr.setTitle("Document To Sign information");
        docInfr.setSize(700,450);
        Dimension screenSize = new Dimension();
        screenSize = docInfr.getToolkit().getScreenSize();
        Dimension wizardSize = docInfr.getSize();
        docInfr.setLocation((screenSize.width - wizardSize.width) / 2,
                (screenSize.height - wizardSize.height) / 2);
        //docInfr.show();
        docInfr.pack();
        docInfr.setVisible(true);
        } else {
            errorOption("No document selected to view info","Selection Error");
        }
// TODO add your handling code here:
    }//GEN-LAST:event_infojButton1ActionPerformed

    private void openConfigurationjButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openConfigurationjButton1ActionPerformed
        String startDir = System.getProperty("user.dir");
        JFileChooser fc = new JFileChooser(new File(startDir));
        fc.showOpenDialog(this);
        File selectedFile = fc.getSelectedFile();
        Properties props = new Properties();
        JOptionPane.showMessageDialog(this,
                "Configuration was succesfully loaded from \n" + selectedFile.getAbsolutePath());
        
        try {
            props.load(new FileInputStream(selectedFile));
            configuration = configuration.reloadConfiguration(props);
            setUpConfigurationComponents();
            setUpGlobalIdentity();
        } catch (FileNotFoundException ex) {
            errorOption("Cannot load configuration from \n" + selectedFile.getAbsolutePath(),"Save Configuration Error");
        } catch (IOException ex) {
            errorOption("Cannot load configuration from \n" + selectedFile.getAbsolutePath(),"Save Configuration Error");
        }
    }//GEN-LAST:event_openConfigurationjButton1ActionPerformed

    private void saveConfigurationjButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveConfigurationjButton1ActionPerformed
        String startDir = System.getProperty("user.dir");
        JFileChooser fc = new JFileChooser(new File(startDir));
        fc.showSaveDialog(this);
        File selectedFile = fc.getSelectedFile();
        boolean rewrite = true;
        if(selectedFile.exists()) {
            Object[] options = {"Yes","No"};
            int i = JOptionPane.showOptionDialog(this,
                    "Selected file already exist, do you want to overwrite it?",
                    "OverWrite Selected file ",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,options, options[1]);
            if(i == 0 ) {
                rewrite = true;
            } else {
                rewrite = false;
            }
        }
        boolean saved = false;
        if(rewrite) {
            loadGlobalConfigurationFromTextFields();
            saved = configuration.saveConfiguration(selectedFile.getAbsolutePath());
        }
        if(saved) {
            JOptionPane.showMessageDialog(this,
                    "Configuration was succesfully saved into \n" + selectedFile.getAbsolutePath());
            
        } else {
            errorOption("Cannot save actual configuration into \n" + selectedFile.getAbsolutePath(),
                    "Save Configuration Error");
                    
            
        }
    }//GEN-LAST:event_saveConfigurationjButton1ActionPerformed

    
    
    private void ReloadConfigurationjButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ReloadConfigurationjButton1ActionPerformed
        loadGlobalConfigurationFromTextFields();
        setUpGlobalIdentity();
    }//GEN-LAST:event_ReloadConfigurationjButton1ActionPerformed

    private void loadJButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadJButton1ActionPerformed
        if(pathToLoadSignatureJField.getText().equals("")) {
            errorOption("No input file for loading xml signature was selected",
                    "No file selected Error");
            
        } else {
            boolean loaded = false;
            String pathToLoad = pathToLoadSignatureJField.getText();
            Document document = XMLSignatureHelper.getXMLSignatureFromXMLFile(pathToLoad);
            this.xmlSignatureDoc = document;
            loaded = XMLSignatureHelper.verifyXMLDocumentContainsXMLSignature(xmlSignatureDoc);
            if (loaded) {
                setActualSignature(document);
                JOptionPane.showMessageDialog(this, "XML Signature was succesfully loaded from \n" + pathToLoad);
                pathToLoadSignatureJField.setText("");
                
            }
        }
        
    }//GEN-LAST:event_loadJButton1ActionPerformed

    private void browseLoadjButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_browseLoadjButton3ActionPerformed
        String startDir = System.getProperty("user.dir");
        JFileChooser fc = new JFileChooser(new File(startDir));
        fc.showOpenDialog(this);
        File selectedFile = fc.getSelectedFile();
        if(selectedFile != null) {
            pathToLoadSignatureJField.setText(selectedFile.getAbsolutePath());
//            XMLSignatureHelper.storeSignatureToXMLFile(xmlSignatureDoc,selectedFile);
        }
        
    }//GEN-LAST:event_browseLoadjButton3ActionPerformed

    private void browseStorejButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_browseStorejButton2ActionPerformed
        String startDir = System.getProperty("user.dir");
        JFileChooser fc = new JFileChooser(new File(startDir));
        fc.showSaveDialog(this);
        File selectedFile = fc.getSelectedFile();
        if(selectedFile != null) {
            pathToStoreSignatureJField.setText(selectedFile.getAbsolutePath());
        }

        

    }//GEN-LAST:event_browseStorejButton2ActionPerformed

    private void documentsjList1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_documentsjList1MouseClicked
        // TODO dodelat otevreni podrobnych informaci od dokumentu
 
    }//GEN-LAST:event_documentsjList1MouseClicked

    private void selectURLjButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectURLjButton1ActionPerformed
        urlToSignJTextField.setText(((DocumentModel)documentsjList1.getSelectedValue()).getDocURL());
    }//GEN-LAST:event_selectURLjButton1ActionPerformed

    private void storejButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_storejButtonActionPerformed
        if(pathToStoreSignatureJField.getText().equals("")) {
            errorOption("No output file for storing xml signature was selected",
                    "No file selected Error");
            
        } else { 
            File outputFile = new File(pathToStoreSignatureJField.getText());
            boolean rewrite = true;
            if(outputFile.exists()) {
                Object[] options = {"Yes","No"};
                int i = JOptionPane.showOptionDialog(this,
                        "Selected file already exist, do you want to overwrite it?",
                        "OverWrite Selected file ",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,options, options[1]);
                if(i == 0 ) {
                    rewrite = true;
                } else {
                    rewrite = false;
                }
                
            }
            
            if(rewrite) { //rewrite default value is true.
                boolean stored = false;
                if(xmlSignatureDoc != null) {
                     stored = XMLSignatureHelper.storeSignatureToXMLFile(xmlSignatureDoc,outputFile.getAbsolutePath());
                } else {
                    errorOption("No document was signed",
                        "No Signature to store Error");
                    
                }
                if(stored) {
                    JOptionPane.showMessageDialog(this, "XML Signature was succesfully stored");
                    pathToStoreSignatureJField.setText("");
                } else {
                    errorOption("Program cannot store XML Signature document into the filesystem",
                            "Storing Error");
                }
                
            }
        }
    }//GEN-LAST:event_storejButtonActionPerformed

    private void sendjButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendjButtonActionPerformed
        if(this.xmlSignatureDoc != null) {
            int respCode = 0;
            try {
                respCode =  urlClient.sendXMLSignatureToServer(xmlSignatureDoc);
                if (respCode != 200) {
                    errorOption("Cannot send xml document on the server\n" +
                            "response code: " + respCode,
                            "Storing Error");
                    
                } else {
                    JOptionPane.showMessageDialog(this, "XML Signature was succesfully send to the Server\n " +
                            "response code " + respCode);
                    
                }
                
            } catch (URLClientException ex) {
                //TODO Show some error window
                //ex.printStackTrace();
                errorOption("Cannot send xml document on the server\n" +
                        "response code: " + respCode,
                        "Storing Error");
            }
        } else {
            errorOption("No Signature was created!\n Please select URL of document and sign it.", "No Signature created Error");
        }
    }//GEN-LAST:event_sendjButtonActionPerformed


    /**
     * Use to set text fields in signature Panel
     */
    private void setActualSignature(Document xmlSignatureDocument) {
        SignatureModel actualSignature;
        actualSignature = new SignatureModel(xmlSignatureDoc);
        loginSignaturejTextField1.setText(actualSignature.getLogin());
        CNSignaturejTextField2.setText(actualSignature.getCN());
        urlSignaturejTextField3.setText(actualSignature.getDocumentURL());
        xmlSignaturejTextArea1.setText(actualSignature.getSignatureXMLString());
        
    }
    
    
    private void signjButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_signjButtonActionPerformed
        if(!urlToSignJTextField.getText().equals("")) {
           
            DetachedXMLSignatureJDK detXMLSig = new DetachedXMLSignatureJDK();
            try {
                xmlSignatureDoc = detXMLSig.createDetachedSignature(
                        urlToSignJTextField.getText(),
                        identity.getKeyPair().getPrivate(),
                        identity.getX509Cetificate());
                setActualSignature(xmlSignatureDoc);
                urlToSignJTextField.setText("");
                //TODO when catch exception show Error window not, write to console
            } catch (NoSuchAlgorithmException ex) {
                ex.printStackTrace();
            } catch (InvalidAlgorithmParameterException ex) {
                ex.printStackTrace();
            } catch (MarshalException ex) {
                ex.printStackTrace();
            } catch (XMLSignatureException ex) {
                ex.printStackTrace();
            } catch (ParserConfigurationException ex) {
                ex.printStackTrace();
            }
        } else {
            errorOption("Please, insert url of document you want to sign", "No URL to sign selected" );
        }
    }//GEN-LAST:event_signjButtonActionPerformed
    
    /**
     * @param args the command line arguments
     */
/*
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ClientGUI().setVisible(true);
            }
        });
    }
*/  
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField CNSignaturejTextField2;
    private javax.swing.JMenuItem ReloadConfigjMenuItem4;
    private javax.swing.JButton ReloadConfigurationjButton1;
    private javax.swing.JMenuItem Send;
    private javax.swing.JMenuItem Sign;
    private javax.swing.JMenuItem StoreJmenuItem;
    private javax.swing.JPanel actionSignaturesjPanel10;
    private javax.swing.JTextField aliasIdentityjTextField1;
    private javax.swing.JLabel aliasLabel;
    private javax.swing.JTextField aliasjTextField3;
    private javax.swing.JButton browseLoadjButton3;
    private javax.swing.JButton browseStorejButton2;
    private javax.swing.JPanel configurationjPanel5;
    private javax.swing.JPanel documentsCardjPanel2;
    private javax.swing.JTextField documentsStoreURLjTextField2;
    private javax.swing.JList documentsjList1;
    private javax.swing.JMenu documentsjMenu2;
    private javax.swing.JMenuItem exitMenuItem1;
    private javax.swing.JMenu filejMenu1;
    private javax.swing.JPanel identityCardjPanel7;
    private javax.swing.JPanel identityJPanel;
    private javax.swing.JMenu identityjMenu1;
    private javax.swing.JButton infojButton1;
    private javax.swing.JTextField issuerjTextField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuBar jMenuBar2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextField keyStoreTypejTextField2;
    private javax.swing.JTextField keyStorejTextField1;
    private javax.swing.JLabel keystoreTypeLabel;
    private javax.swing.JMenuItem loadConfigjMenuItem3;
    private javax.swing.JButton loadJButton1;
    private javax.swing.JMenuItem loadjMenuItem2;
    private javax.swing.JLabel loginSignaturejLabel6;
    private javax.swing.JTextField loginSignaturejTextField1;
    private javax.swing.JButton openConfigurationjButton1;
    private javax.swing.JTextField passwordjTextField4;
    private javax.swing.JLabel paswordLabel;
    private javax.swing.JTextField pathToLoadSignatureJField;
    private javax.swing.JTextField pathToStoreSignatureJField;
    private javax.swing.JMenuItem saveConfigjMenuItem2;
    private javax.swing.JButton saveConfigurationjButton1;
    private javax.swing.JButton selectURLjButton1;
    private javax.swing.JMenuItem selectURLjMenuItem2;
    private javax.swing.JButton sendjButton;
    private javax.swing.JPanel signSendButtonjPanel;
    private javax.swing.JTextField signatureStoreURLjTextField1;
    private javax.swing.JPanel signaturesCardjPanel1;
    private javax.swing.JPanel signaturesInfoJPanel;
    private javax.swing.JMenu signaturesJmenu;
    private javax.swing.JButton signjButton;
    private javax.swing.JPanel storeSignatureButtonjPanel8;
    private javax.swing.JButton storejButton;
    private javax.swing.JLabel subjectSignaturejLabel6;
    private javax.swing.JTextField subjectjTextField;
    private javax.swing.JLabel urlSignatureJLabel;
    private javax.swing.JTextField urlSignaturejTextField3;
    private javax.swing.JTextField urlToSignJTextField;
    private javax.swing.JTextArea xmlSignaturejTextArea1;
    private javax.swing.JLabel xmlSignaurejLabel7;
    // End of variables declaration//GEN-END:variables
    
}



class DocumentModelListModel implements ListModel {
  private Vector vector;
  
  public DocumentModelListModel(Vector documentsModelVector) {
    this.vector = documentsModelVector;
  }

  public Object getElementAt(int index) {
    return(vector.get(index));
  }

  public int getSize() {
    return(vector.size());
  }

  public void addListDataListener(ListDataListener l) {}

  public void removeListDataListener(ListDataListener l) {}
}
