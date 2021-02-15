/*
 * 
 */
package br.starter;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.AbstractCellEditor;
import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import org.apache.commons.io.FileUtils;

/**
 * Dialog de configuracaoo inicial da aplicacao, recurso visual para a configuracao de 
 * arquivos iniciais do sistema.
 * 
 * @author JohnAnderson 28/11/2020 
 */
public class StartFrame extends JFrame implements PropertyChangeListener, WindowListener, ActionListener {

    private static final long serialVersionUID = -1130342847618772236L;
    private JButton save, saveAs, close;
    private JTable tableLocalConfig;	
    private JTable tableIpedConfig;
    private JTable tableAdvancedConfig;
    private JTable tableAudioConfig;
    private JTable tableElasticSearchConfig;
    private JTable tableHtmlReportConfig;
    private JTable tableImageThumbnaillocalConfig;
    private JTable tablePhotoDNAConfig;
    private JTable tableRegexConfig;
    private JTable tableVideoThumbnailsConfig;
    
    private JPanel top, center, buttons;
    private JTabbedPane tabPanel;
    private JMenuItem menuItemSair, menuItemConfigIPED, menuItemIniciar;
    private JPanel ipedImg;
    
    private UTF8Properties localProperties;
    private UTF8Properties ipedProperties;
    private UTF8Properties advancedProperties;
    private UTF8Properties audioProperties;
    private UTF8Properties elasticSearchProperties;
    private UTF8Properties htmlReportProperties;
    private UTF8Properties imageThumbnaillocalProperties;
    private UTF8Properties photoDNAProperties;
    private UTF8Properties regexProperties;
    private UTF8Properties videoThumbnailsProperties;
    
    private String rootPath;
    private String locale  = "";
    private String caseConfig  = "";
    private List<RowModel> listLocalConfig;
    
    private List<RowModel> listIpedConfig;
    private List<RowModel> listAdvancedConfig;
    private List<RowModel> listAudioConfig;
    private List<RowModel> listElasticSearchConfig;
    private List<RowModel> listHtmlReportConfig;
    private List<RowModel> listImageThumbnailConfig;
    private List<RowModel> listPhotoDNAConfig;
    private List<RowModel> listRegexConfig;
    private List<RowModel> listVideoThumbnailsConfig;
    
    private JCheckBox check1;
    private JLabel name;
    private JTextField txtFileOrig; 
    private JButton btnFile;
    private JLabel mno; 
    private JTextField txtPathDest; 
    private JButton btnPath;
    private JPanel pBorder;
    private JCheckBox check2;
    private JLabel commandLabel; 
    private JTextArea command; 
    private JPanel pBorder2;
    private JButton btnCaseProcess;
    private JPanel formCaseProcess;
    private JFileChooser caseFileOrig = new JFileChooser();
    private JFileChooser casePathDest = new JFileChooser();
    		
    /*
     * Criacao da tela inicial da aplicacao, com load do arquivo de configuracao "LocalConfig.txt"
     * para carregamento inicial de informações pré-definidas
     */
    public StartFrame() {
        super("("+Messages.getString("StartFrame.title")+")");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        this.setBounds(0, 0, 1200, 800);
        this.setLocationRelativeTo(null);

        URL image = getClass().getResource("icon.png"); //$NON-NLS-1$
        this.setIconImage(new ImageIcon(image).getImage());
        
        try {
			this.rootPath = System.getProperty("user.dir");
        } catch (Exception e) {
			e.printStackTrace();
		}    
        
        this.createMenuBar();
    }
    
    private void createMenuBar() {
    	JMenuBar menuBar = new JMenuBar();
    	JMenu menu = new JMenu(Messages.getString("StartFrame.arquivo"));
    	
    	this.menuItemIniciar = new JMenuItem(Messages.getString("StartFrame.iniciarProcessamento"));
    	menuItemIniciar.addActionListener(this);
    	menu.add(menuItemIniciar);
    	
    	this.menuItemConfigIPED = new JMenuItem(Messages.getString("StartFrame.configurarIPED"));
    	menuItemConfigIPED.addActionListener(this);
    	menu.add(menuItemConfigIPED);
    	
    	this.menuItemSair = new JMenuItem(Messages.getString("StartFrame.sair"));
    	menuItemSair.addActionListener(this);
    	menu.add(menuItemSair);
    	
    	menuBar.add(menu);
    	
    	this.setJMenuBar(menuBar);
    	
    	try {                
    		URL image = getClass().getResource("iped.png"); //$NON-NLS-1$
            JLabel wIcon = new JLabel(new ImageIcon(image));
    		
    		this.ipedImg = new JPanel();
    		ipedImg.setLayout(new GridLayout(1,1));
    		ipedImg.add(wIcon);
            
            this.getContentPane().add(ipedImg, BorderLayout.NORTH);
            
         } catch (Exception ex) {
        	 System.out.println("Erro:"+ ex.getMessage());
              // handle exception...
         }
    }
    
    private void initCaseProcess() {
    	if(this.ipedImg != null) this.ipedImg.setVisible(false);
    	if(this.top != null) this.top.setVisible(false);
    	if(this.center != null) this.center.setVisible(false);
    	if(this.buttons != null) this.buttons.setVisible(false);
    	
    	Container c = getContentPane(); 
        //c.setLayout(null); 
  
        this.check1 = new JCheckBox();
        this.check1.setSize(20, 20); 
        this.check1.setLocation(95, 65); 
        this.check1.addActionListener(this);
        this.check1.setSelected(true);
        c.add(this.check1); 
        
        this.name = new JLabel("Arquivo de origem:"); 
        this.name.setFont(new Font("Arial", Font.PLAIN, 15)); 
        this.name.setSize(200, 20); 
        this.name.setLocation(100, 100); 
        c.add(this.name); 
  
        this.txtFileOrig = new JTextField(); 
        this.txtFileOrig.setFont(new Font("Arial", Font.PLAIN, 15)); 
        this.txtFileOrig.setSize(400, 25); 
        txtFileOrig.setLocation(250, 100); 
        c.add(this.txtFileOrig); 
        
        this.btnFile = new JButton();
        this.btnFile.setText("Arquivo");
        this.btnFile.setSize(100, 24); 
        this.btnFile.setLocation(649, 100); 
        this.btnFile.addActionListener(this);
        c.add(this.btnFile);
                
        this.mno = new JLabel("Pasta de destino:"); 
        this.mno.setFont(new Font("Arial", Font.PLAIN, 15)); 
        this.mno.setSize(200, 20); 
        this.mno.setLocation(100, 150); 
        c.add(this.mno); 
  
        this.txtPathDest = new JTextField(); 
        this.txtPathDest.setFont(new Font("Arial", Font.PLAIN, 15)); 
        this.txtPathDest.setSize(400, 25); 
        this.txtPathDest.setLocation(250, 150); 
        c.add(this.txtPathDest); 
        
        this.btnPath = new JButton();
        this.btnPath.setText("Arquivo");
        this.btnPath.setSize(100, 24); 
        this.btnPath.setLocation(649, 150); 
        this.btnPath.addActionListener(this);
        c.add(this.btnPath);
        
        Border noBorder = new TitledBorder(new LineBorder(Color.decode("#c0c0c0")), "");
        this.pBorder = new JPanel();
        this.pBorder.setBorder(noBorder);
        this.pBorder.setSize(750, 150); 
        this.pBorder.setLocation(80, 60); 
        c.add(this.pBorder);
        
        
        this.check2 = new JCheckBox();
        this.check2.setSize(20, 20); 
        this.check2.setLocation(95, 225); 
        this.check2.addActionListener(this);
        this.check2.setSelected(false);
        c.add(this.check2); 
        
        this.commandLabel = new JLabel("Comandos:"); 
        this.commandLabel.setFont(new Font("Arial", Font.PLAIN, 15)); 
        this.commandLabel.setSize(200, 20); 
        this.commandLabel.setLocation(100, 260); 
        c.add(this.commandLabel); 
  
        this.command = new JTextArea(); 
        this.command.setFont(new Font("Arial", Font.PLAIN, 15)); 
        this.command.setSize(490, 50);
        this.command.setBorder(new LineBorder(Color.decode("#c0c0c0")));
        this.command.setLocation(250, 260); 
        this.command.setEditable(false);
        
        c.add(this.command); 
        
        this.commandLabel = new JLabel("Ex: java -jar iped.jar -d [ARQUIVO] -o [DESTINO] "); 
        this.commandLabel.setFont(new Font("Arial", Font.PLAIN, 12)); 
        this.commandLabel.setSize(500, 20); 
        this.commandLabel.setLocation(250, 315); 
        c.add(this.commandLabel); 
  
        Border noBorder2 = new TitledBorder(new LineBorder(Color.decode("#c0c0c0")), "");
        this.pBorder2 = new JPanel();
        this.pBorder2.setBorder(noBorder2);
        this.pBorder2.setSize(750, 150); 
        this.pBorder2.setLocation(80, 220); 
        c.add(this.pBorder2);
        
        //-------------------
        this.btnCaseProcess = new JButton();
        this.btnCaseProcess.setText("Processar caso");
        this.btnCaseProcess.setSize(250, 24); 
        this.btnCaseProcess.setLocation(350, 400);
        this.btnCaseProcess.addActionListener(this);
        c.add(this.btnCaseProcess);
        
        Border titleBorder = new TitledBorder(new LineBorder(Color.decode("#c0c0c0")), Messages.getString("StartFrame.caseParams"));
        this.formCaseProcess = new JPanel();
        this.formCaseProcess.setBorder(titleBorder);
        this.formCaseProcess.setSize(800, 420); 
        this.formCaseProcess.setLocation(50, 30); 
        c.add(this.formCaseProcess);
        
        setVisible(true); 
    }
    
    private void hideFormCaseProcess() {
    	if(this.check1 != null) this.check1.setVisible(false);
    	if(this.check1 != null) this.name.setVisible(false);
    	if(this.check1 != null) this.txtFileOrig.setVisible(false);
    	if(this.check1 != null) this.btnFile.setVisible(false);
    	if(this.check1 != null) this.mno.setVisible(false);
    	if(this.check1 != null) this.txtPathDest.setVisible(false);
    	if(this.check1 != null) this.btnPath.setVisible(false);
    	if(this.check1 != null) this.pBorder.setVisible(false);
    	if(this.check1 != null) this.check2.setVisible(false);
    	if(this.check1 != null) this.commandLabel.setVisible(false); 
    	if(this.check1 != null) this.command.setVisible(false); 
    	if(this.check1 != null) this.pBorder2.setVisible(false);
    	if(this.check1 != null) this.btnCaseProcess.setVisible(false);
    	if(this.check1 != null) this.formCaseProcess.setVisible(false);
    	
    }
    
    private void initConfigIPED() {
    	this.ipedImg.setVisible(false);
    	this.hideFormCaseProcess();
    	//Inicializacao
        this.loadLocalConfigFile();
        this.loadListLocalConfig();
        
        
        this.tableLocalConfig = this.createTable(this.listLocalConfig, true); 
        
        this.tabPanel = this.createTabPanel();
        
        this.buttons = new JPanel(); 
        
        save = new JButton(Messages.getString("StartFrame.save"));
        save.addActionListener(this);
        
        saveAs = new JButton(Messages.getString("StartFrame.saveAs"));
        saveAs.addActionListener(this);
        
        close = new JButton(Messages.getString("StartFrame.close"));
        close.addActionListener(this);
        
        buttons.add(save);
        buttons.add(saveAs);
        buttons.add(close);
        
        Border titleBorder = new TitledBorder(new LineBorder(Color.decode("#c0c0c0")), Messages.getString("StartFrame.configLocal"));
        top = new JPanel();
        top.setLayout(new GridLayout(1,1));
        top.setBorder(titleBorder);
        
        JScrollPane scrollFrame = new JScrollPane(this.tableLocalConfig);
        this.tableLocalConfig.setAutoscrolls(true);
        scrollFrame.setPreferredSize(new Dimension( 800,260));
        
        top.add(scrollFrame);
        
        center = new JPanel();
        center.setLayout(new GridLayout(1,1));
        center.add(tabPanel);
        
        this.getContentPane().add(top, BorderLayout.NORTH);
        this.getContentPane().add(center, BorderLayout.CENTER);
        this.getContentPane().add(buttons, BorderLayout.SOUTH);
        
        this.getContentPane().setVisible(true);
    }
    
    
    
    //LOAD DE ARQUIVOS DE CONFIGURACAO
    private void loadListLocalConfig() {
    	this.locale = getLocalConfig("locale");
    	this.caseConfig = getLocalConfig("indexTemp");
    	
    	this.listLocalConfig = new ArrayList<StartFrame.RowModel>();
    	this.listLocalConfig.add(new RowModel(0, "locale", this.locale, Messages.getString("StartFrame.idioma"), new ComboBoxEditor(new String[]{ "en", "pt-BR"})));
    	this.listLocalConfig.add(new RowModel(1, "indexTemp",this.caseConfig , Messages.getString("StartFrame.caseConfig"), new ComboBoxEditor(getListConfigs())));
    	this.listLocalConfig.add(new RowModel(2,  "indexTempOnSSD", getLocalConfig("indexTempOnSSD"), Messages.getString("StartFrame.enableIfSSDDisk"), new CheckBoxEditor()));
    	this.listLocalConfig.add(new RowModel(3,  "outputOnSSD", getLocalConfig("outputOnSSD"), Messages.getString("StartFrame.enableOutputOnSSD"), new CheckBoxEditor()));
    	this.listLocalConfig.add(new RowModel(4,  "numThreads", getLocalConfig("numThreads"), Messages.getString("StartFrame.numeroThreads"), new DefaultCellEditor(new JTextField())));
    	this.listLocalConfig.add(new RowModel(5,  "kffDb", getLocalConfig("kffDb"), Messages.getString("StartFrame.fullPathIndexDatabase"), new FileChooserCellEditor()));
    	this.listLocalConfig.add(new RowModel(6,  "ledWkffPath", getLocalConfig("ledWkffPath"), Messages.getString("StartFrame.fullPathChildPornHashDatabase"), new FileChooserCellEditor()));
    	this.listLocalConfig.add(new RowModel(7,  "projectVicHashSetPath", getLocalConfig("projectVicHashSetPath"), Messages.getString("StartFrame.fullPathChildPornHashDatabase2"),new FileChooserCellEditor()));
    	this.listLocalConfig.add(new RowModel(8,  "photoDNAHashDatabase", getLocalConfig("photoDNAHashDatabase"), Messages.getString("StartFrame.pathPhotoDNA"), new FileChooserCellEditor()));
    	this.listLocalConfig.add(new RowModel(9,  "ledDie", getLocalConfig("ledDie"), Messages.getString("StartFrame.ledDie"), new FileChooserCellEditor()));
    	this.listLocalConfig.add(new RowModel(10, "ledWkffPath", getLocalConfig("ledWkffPath"), Messages.getString("StartFrame.fullPathSleuthkit"), new FileChooserCellEditor()));
    	this.listLocalConfig.add(new RowModel(11, "mplayerPath", getLocalConfig("mplayerPath"), Messages.getString("StartFrame.fullPathMPlayer"), new FileChooserCellEditor()));
    	this.listLocalConfig.add(new RowModel(12, "optional_jars", getLocalConfig("optional_jars"), Messages.getString("StartFrame.pluginFolderLibs"), new FileChooserCellEditor()));
    	this.listLocalConfig.add(new RowModel(13, "regripperFolder", getLocalConfig("regripperFolder"), Messages.getString("StartFrame.pathRegRipper"), new FileChooserCellEditor()));
    }
    
    private void loadListIpedConfig() {
    	this.listIpedConfig = new ArrayList<StartFrame.RowModel>();
    	this.listIpedConfig.add(new RowModel(0, "hash", this.getPropertiesConfig(this.ipedProperties, "hash"), Messages.getString("StartFrame.hashConfig"), new DefaultCellEditor(new JTextField())));
    	this.listIpedConfig.add(new RowModel(1, "enablePhotoDNA", this.getPropertiesConfig(this.ipedProperties, "enablePhotoDNA"), Messages.getString("StartFrame.enablePhotoDNAConfig"), new CheckBoxEditor()));
    	this.listIpedConfig.add(new RowModel(2, "enableKff", this.getPropertiesConfig(this.ipedProperties, "enableKff"), Messages.getString("StartFrame.enableKffConfig"), new CheckBoxEditor()));
    	this.listIpedConfig.add(new RowModel(3, "enableLedWkff", this.getPropertiesConfig(this.ipedProperties, "enableLedWkff"), Messages.getString("StartFrame.enableKffConfig"), new CheckBoxEditor()));
    	this.listIpedConfig.add(new RowModel(4, "enableProjectVicHashLookup", this.getPropertiesConfig(this.ipedProperties, "enableProjectVicHashLookup"), Messages.getString("StartFrame.enableProjectVicHashLookupConfig"), new CheckBoxEditor()));
    	this.listIpedConfig.add(new RowModel(5, "enableLedDie", this.getPropertiesConfig(this.ipedProperties, "enableLedDie"), Messages.getString("StartFrame.enableLedDieConfig"), new CheckBoxEditor()));
    	this.listIpedConfig.add(new RowModel(6, "excludeKffIgnorable", this.getPropertiesConfig(this.ipedProperties, "excludeKffIgnorable"), Messages.getString("StartFrame.excludeKffIgnorableConfig"), new CheckBoxEditor()));
    	this.listIpedConfig.add(new RowModel(7, "ignoreDuplicates", this.getPropertiesConfig(this.ipedProperties, "ignoreDuplicates"), Messages.getString("StartFrame.ignoreDuplicatesConfig"), new CheckBoxEditor()));
    	this.listIpedConfig.add(new RowModel(8, "exportFileProps", this.getPropertiesConfig(this.ipedProperties, "exportFileProps"), Messages.getString("StartFrame.exportFilePropsConfig"), new CheckBoxEditor()));
    	this.listIpedConfig.add(new RowModel(9, "processFileSignatures", this.getPropertiesConfig(this.ipedProperties, "processFileSignatures"), Messages.getString("StartFrame.processFileSignaturesConfig"), new CheckBoxEditor()));
    	this.listIpedConfig.add(new RowModel(10, "enableFileParsing", this.getPropertiesConfig(this.ipedProperties, "enableFileParsing"), Messages.getString("StartFrame.enableFileParsingConfig"), new CheckBoxEditor()));
    	this.listIpedConfig.add(new RowModel(11, "expandContainers", this.getPropertiesConfig(this.ipedProperties, "expandContainers"), Messages.getString("StartFrame.expandContainersConfig"), new CheckBoxEditor()));
    	this.listIpedConfig.add(new RowModel(12, "enableRegexSearch", this.getPropertiesConfig(this.ipedProperties, "enableRegexSearch"), Messages.getString("StartFrame.enableRegexSearchConfig"), new CheckBoxEditor()));
    	this.listIpedConfig.add(new RowModel(13, "enableLanguageDetect", this.getPropertiesConfig(this.ipedProperties, "enableLanguageDetect"), Messages.getString("StartFrame.enableLanguageDetectConfig"), new CheckBoxEditor()));
    	this.listIpedConfig.add(new RowModel(14, "enableNamedEntityRecogniton", this.getPropertiesConfig(this.ipedProperties, "enableNamedEntityRecogniton"), Messages.getString("StartFrame.enableNamedEntityRecognitonConfig"), new CheckBoxEditor()));
    	this.listIpedConfig.add(new RowModel(15, "enableGraphGeneration", this.getPropertiesConfig(this.ipedProperties, "enableGraphGeneration"), Messages.getString("StartFrame.enableGraphGenerationConfig"), new CheckBoxEditor()));
    	this.listIpedConfig.add(new RowModel(16, "indexFileContents", this.getPropertiesConfig(this.ipedProperties, "indexFileContents"), Messages.getString("StartFrame.indexFileContentsConfig"), new CheckBoxEditor()));
    	this.listIpedConfig.add(new RowModel(17, "indexUnknownFiles", this.getPropertiesConfig(this.ipedProperties, "indexUnknownFiles"), Messages.getString("StartFrame.indexUnknownFilesConfig"), new CheckBoxEditor()));
    	this.listIpedConfig.add(new RowModel(18, "indexCorruptedFiles", this.getPropertiesConfig(this.ipedProperties, "indexCorruptedFiles"), Messages.getString("StartFrame.indexCorruptedFilesConfig"), new CheckBoxEditor()));
    	this.listIpedConfig.add(new RowModel(19, "enableOCR", this.getPropertiesConfig(this.ipedProperties, "enableOCR"), Messages.getString("StartFrame.enableOCRConfig"), new CheckBoxEditor()));
    	this.listIpedConfig.add(new RowModel(20, "enableAudioTranscription", this.getPropertiesConfig(this.ipedProperties, "enableAudioTranscription"), Messages.getString("StartFrame.enableAudioTranscriptionConfig"), new CheckBoxEditor()));
    	this.listIpedConfig.add(new RowModel(21, "addFileSlacks", this.getPropertiesConfig(this.ipedProperties, "addFileSlacks"), Messages.getString("StartFrame.addFileSlacksConfig"), new CheckBoxEditor()));
    	this.listIpedConfig.add(new RowModel(22, "addUnallocated", this.getPropertiesConfig(this.ipedProperties, "addUnallocated"), Messages.getString("StartFrame.addUnallocatedConfig"), new CheckBoxEditor()));
    	this.listIpedConfig.add(new RowModel(23, "indexUnallocated", this.getPropertiesConfig(this.ipedProperties, "indexUnallocated"), Messages.getString("StartFrame.indexUnallocatedConfig"), new CheckBoxEditor()));
    	this.listIpedConfig.add(new RowModel(24, "enableCarving", this.getPropertiesConfig(this.ipedProperties, "enableCarving"), Messages.getString("StartFrame.enableCarvingConfig"), new CheckBoxEditor()));
    	this.listIpedConfig.add(new RowModel(25, "enableKFFCarving", this.getPropertiesConfig(this.ipedProperties, "enableKFFCarving"), Messages.getString("StartFrame.enableKFFCarvingConfig"), new CheckBoxEditor()));
    	this.listIpedConfig.add(new RowModel(26, "enableKnownMetCarving", this.getPropertiesConfig(this.ipedProperties, "enableKnownMetCarving"), Messages.getString("StartFrame.enableKnownMetCarvingConfig"), new CheckBoxEditor()));
    	this.listIpedConfig.add(new RowModel(27, "enableImageThumbs", this.getPropertiesConfig(this.ipedProperties, "enableImageThumbs"), Messages.getString("StartFrame.enableImageThumbsConfig"), new CheckBoxEditor()));
    	this.listIpedConfig.add(new RowModel(28, "enableImageSimilarity", this.getPropertiesConfig(this.ipedProperties, "enableImageSimilarity"), Messages.getString("StartFrame.enableImageSimilarityConfig"), new CheckBoxEditor()));
    	this.listIpedConfig.add(new RowModel(29, "enableVideoThumbs", this.getPropertiesConfig(this.ipedProperties, "enableVideoThumbs"), Messages.getString("StartFrame.enableVideoThumbsConfig"), new CheckBoxEditor()));
    	this.listIpedConfig.add(new RowModel(30, "enableHTMLReport", this.getPropertiesConfig(this.ipedProperties, "enableHTMLReport"), Messages.getString("StartFrame.enableVideoThumbsConfig"), new CheckBoxEditor()));
    }

    private void loadListAdvancedConfig() {
    	this.listAdvancedConfig = new ArrayList<StartFrame.RowModel>();
    	this.listAdvancedConfig.add(new RowModel(0, "robustImageReading", this.getPropertiesConfig(this.advancedProperties, "robustImageReading"), Messages.getString("StartFrame.robustImageReadingConfig"), new CheckBoxEditor()));
    	this.listAdvancedConfig.add(new RowModel(1, "enableExternalParsing", this.getPropertiesConfig(this.advancedProperties, "enableExternalParsing"), Messages.getString("StartFrame.enableExternalParsingConfig"), new CheckBoxEditor()));
    	this.listAdvancedConfig.add(new RowModel(2, "numImageReaders", this.getPropertiesConfig(this.advancedProperties, "numImageReaders"), Messages.getString("StartFrame.numImageReadersConfig"), new DefaultCellEditor(new JTextField())));
    	this.listAdvancedConfig.add(new RowModel(3, "externalParsingMaxMem", this.getPropertiesConfig(this.advancedProperties, "externalParsingMaxMem"), Messages.getString("StartFrame.externalParsingMaxMemConfig"), new CheckBoxEditor()));
    	this.listAdvancedConfig.add(new RowModel(4, "phoneParsersToUse", this.getPropertiesConfig(this.advancedProperties, "phoneParsersToUse"), Messages.getString("StartFrame.phoneParsersToUseConfig"), new DefaultCellEditor(new JTextField())));
    	this.listAdvancedConfig.add(new RowModel(5, "forceMerge", this.getPropertiesConfig(this.advancedProperties, "forceMerge"), Messages.getString("StartFrame.forceMergeConfig"), new CheckBoxEditor()));
    	this.listAdvancedConfig.add(new RowModel(6, "timeOut", this.getPropertiesConfig(this.advancedProperties, "timeOut"), Messages.getString("StartFrame.timeOutConfig"), new DefaultCellEditor(new JTextField())));
    	this.listAdvancedConfig.add(new RowModel(7, "timeOutPerMB", this.getPropertiesConfig(this.advancedProperties, "timeOutPerMB"), Messages.getString("StartFrame.timeOutPerMBConfig"), new CheckBoxEditor()));
    	this.listAdvancedConfig.add(new RowModel(8, "embutirLibreOffice", this.getPropertiesConfig(this.advancedProperties, "embutirLibreOffice"), Messages.getString("StartFrame.robustImageReadingConfig"), new CheckBoxEditor()));
    	this.listAdvancedConfig.add(new RowModel(9, "sortPDFChars", this.getPropertiesConfig(this.advancedProperties, "sortPDFChars"), Messages.getString("StartFrame.robustImageReadingConfig"), new CheckBoxEditor()));
    	this.listAdvancedConfig.add(new RowModel(10, "entropyTest", this.getPropertiesConfig(this.advancedProperties, "entropyTest"), Messages.getString("StartFrame.entropyTestConfig"), new CheckBoxEditor()));
    	this.listAdvancedConfig.add(new RowModel(11, "minRawStringSize", this.getPropertiesConfig(this.advancedProperties, "minRawStringSize"), Messages.getString("StartFrame.minRawStringSizeConfig"), new DefaultCellEditor(new JTextField())));
    	this.listAdvancedConfig.add(new RowModel(12, "extraCharsToIndex", this.getPropertiesConfig(this.advancedProperties, "extraCharsToIndex"), Messages.getString("StartFrame.extraCharsToIndexConfig"), new DefaultCellEditor(new JTextField())));
    	this.listAdvancedConfig.add(new RowModel(13, "convertCharsToLowerCase", this.getPropertiesConfig(this.advancedProperties, "convertCharsToLowerCase"), Messages.getString("StartFrame.convertCharsToLowerCaseConfig"), new CheckBoxEditor()));
    	this.listAdvancedConfig.add(new RowModel(14, "filterNonLatinChars", this.getPropertiesConfig(this.advancedProperties, "filterNonLatinChars"), Messages.getString("StartFrame.filterNonLatinCharsConfig"), new CheckBoxEditor()));
    	this.listAdvancedConfig.add(new RowModel(15, "convertCharsToAscii", this.getPropertiesConfig(this.advancedProperties, "convertCharsToAscii"), Messages.getString("StartFrame.convertCharsToAsciiConfig"), new CheckBoxEditor()));
    	this.listAdvancedConfig.add(new RowModel(16, "ignoreHardLinks", this.getPropertiesConfig(this.advancedProperties, "ignoreHardLinks"), Messages.getString("StartFrame.ignoreHardLinksConfig"), new CheckBoxEditor()));
    	this.listAdvancedConfig.add(new RowModel(17, "minOrphanSizeToIgnore", this.getPropertiesConfig(this.advancedProperties, "minOrphanSizeToIgnore"), Messages.getString("StartFrame.minOrphanSizeToIgnoreConfig"), new DefaultCellEditor(new JTextField())));
    	this.listAdvancedConfig.add(new RowModel(18, "unallocatedFragSize", this.getPropertiesConfig(this.advancedProperties, "unallocatedFragSize"), Messages.getString("StartFrame.unallocatedFragSizeConfig"), new DefaultCellEditor(new JTextField())));
    	this.listAdvancedConfig.add(new RowModel(19, "minItemSizeToFragment", this.getPropertiesConfig(this.advancedProperties, "minItemSizeToFragment"), Messages.getString("StartFrame.minItemSizeToFragmentConfig"), new DefaultCellEditor(new JTextField())));
    	this.listAdvancedConfig.add(new RowModel(20, "textSplitSize", this.getPropertiesConfig(this.advancedProperties, "textSplitSize"), Messages.getString("StartFrame.textSplitSizeConfig"), new DefaultCellEditor(new JTextField())));
    	this.listAdvancedConfig.add(new RowModel(21, "useNIOFSDirectory", this.getPropertiesConfig(this.advancedProperties, "useNIOFSDirectory"), Messages.getString("StartFrame.useNIOFSDirectoryConfig"), new CheckBoxEditor()));
    	this.listAdvancedConfig.add(new RowModel(22, "commitIntervalSeconds", this.getPropertiesConfig(this.advancedProperties, "commitIntervalSeconds"), Messages.getString("StartFrame.commitIntervalSecondsConfig"), new DefaultCellEditor(new JTextField())));
    	this.listAdvancedConfig.add(new RowModel(23, "OCRLanguage", this.getPropertiesConfig(this.advancedProperties, "OCRLanguage"), Messages.getString("StartFrame.OCRLanguageConfig"), new DefaultCellEditor(new JTextField())));
    	this.listAdvancedConfig.add(new RowModel(24, "pageSegMode", this.getPropertiesConfig(this.advancedProperties, "pageSegMode"), Messages.getString("StartFrame.pageSegModeConfig"), new DefaultCellEditor(new JTextField())));
    	this.listAdvancedConfig.add(new RowModel(25, "minFileSize2OCR", this.getPropertiesConfig(this.advancedProperties, "minFileSize2OCR"), Messages.getString("StartFrame.minFileSize2OCRConfig"), new DefaultCellEditor(new JTextField())));
    	this.listAdvancedConfig.add(new RowModel(26, "maxFileSize2OCR", this.getPropertiesConfig(this.advancedProperties, "maxFileSize2OCR"), Messages.getString("StartFrame.maxFileSize2OCRConfig"), new DefaultCellEditor(new JTextField())));
    	this.listAdvancedConfig.add(new RowModel(27, "OCRLanguage", this.getPropertiesConfig(this.advancedProperties, "OCRLanguage"), Messages.getString("StartFrame.OCRLanguageConfig"), new DefaultCellEditor(new JTextField())));
    	this.listAdvancedConfig.add(new RowModel(28, "OCRLanguage", this.getPropertiesConfig(this.advancedProperties, "OCRLanguage"), Messages.getString("StartFrame.OCRLanguageConfig"), new DefaultCellEditor(new JTextField())));
    	this.listAdvancedConfig.add(new RowModel(29, "OCRLanguage", this.getPropertiesConfig(this.advancedProperties, "OCRLanguage"), Messages.getString("StartFrame.OCRLanguageConfig"), new DefaultCellEditor(new JTextField())));
    	this.listAdvancedConfig.add(new RowModel(30, "pdfToImgResolution", this.getPropertiesConfig(this.advancedProperties, "pdfToImgResolution"), Messages.getString("StartFrame.pdfToImgResolutionConfig"), new DefaultCellEditor(new JTextField())));
    	this.listAdvancedConfig.add(new RowModel(31, "maxPDFTextSize2OCR", this.getPropertiesConfig(this.advancedProperties, "maxPDFTextSize2OCR"), Messages.getString("StartFrame.maxPDFTextSize2OCRConfig"), new DefaultCellEditor(new JTextField())));
    	this.listAdvancedConfig.add(new RowModel(32, "pdfToImgLib", this.getPropertiesConfig(this.advancedProperties, "pdfToImgLib"), Messages.getString("StartFrame.pdfToImgLibConfig"), new DefaultCellEditor(new JTextField())));
    	this.listAdvancedConfig.add(new RowModel(33, "externalPdfToImgConv", this.getPropertiesConfig(this.advancedProperties, "externalPdfToImgConv"), Messages.getString("StartFrame.externalPdfToImgConvConfig"), new CheckBoxEditor()));
    	this.listAdvancedConfig.add(new RowModel(34, "externalConvMaxMem", this.getPropertiesConfig(this.advancedProperties, "externalConvMaxMem"), Messages.getString("StartFrame.externalConvMaxMemConfig"), new CheckBoxEditor()));
    	this.listAdvancedConfig.add(new RowModel(35, "processImagesInPDFs", this.getPropertiesConfig(this.advancedProperties, "processImagesInPDFs"), Messages.getString("StartFrame.processImagesInPDFsConfig"), new CheckBoxEditor()));
    	this.listAdvancedConfig.add(new RowModel(36, "searchThreads", this.getPropertiesConfig(this.advancedProperties, "searchThreads"), Messages.getString("StartFrame.searchThreadsConfig"), new DefaultCellEditor(new JTextField())));
    	this.listAdvancedConfig.add(new RowModel(37, "maxBackups", this.getPropertiesConfig(this.advancedProperties, "maxBackups"), Messages.getString("StartFrame.maxBackupsConfig"), new DefaultCellEditor(new JTextField())));
    	this.listAdvancedConfig.add(new RowModel(38, "backupInterval", this.getPropertiesConfig(this.advancedProperties, "backupInterval"), Messages.getString("StartFrame.backupIntervalConfig"), new DefaultCellEditor(new JTextField())));
    	this.listAdvancedConfig.add(new RowModel(39, "autoManageCols", this.getPropertiesConfig(this.advancedProperties, "autoManageCols"), Messages.getString("StartFrame.autoManageColsConfig"), new CheckBoxEditor()));
    	this.listAdvancedConfig.add(new RowModel(40, "preOpenImagesOnSleuth", this.getPropertiesConfig(this.advancedProperties, "preOpenImagesOnSleuth"), Messages.getString("StartFrame.preOpenImagesOnSleuthConfig"), new CheckBoxEditor()));
    	this.listAdvancedConfig.add(new RowModel(41, "openImagesCacheWarmUpEnabled", this.getPropertiesConfig(this.advancedProperties, "openImagesCacheWarmUpEnabled"), Messages.getString("StartFrame.openImagesCacheWarmUpEnabledConfig"), new CheckBoxEditor()));
    	this.listAdvancedConfig.add(new RowModel(42, "openImagesCacheWarmUpThreads", this.getPropertiesConfig(this.advancedProperties, "openImagesCacheWarmUpThreads"), Messages.getString("StartFrame.openImagesCacheWarmUpThreadsConfig"), new DefaultCellEditor(new JTextField())));
    	this.listAdvancedConfig.add(new RowModel(43, "openWithDoubleClick", this.getPropertiesConfig(this.advancedProperties, "openWithDoubleClick"), Messages.getString("StartFrame.openWithDoubleClickConfig"), new DefaultCellEditor(new JTextField())));
    	
    }
    
    private void loadListAudioConfig() {
    	this.listAudioConfig = new ArrayList<StartFrame.RowModel>();
    	this.listAudioConfig.add(new RowModel(0, "implementationClass", this.getPropertiesConfig(this.audioProperties, "implementationClass"), Messages.getString("StartFrame.implementationClassConfig"), new DefaultCellEditor(new JTextField())));
    	this.listAudioConfig.add(new RowModel(1, "serviceRegion", this.getPropertiesConfig(this.audioProperties, "serviceRegion"), Messages.getString("StartFrame.serviceRegionConfig"), new DefaultCellEditor(new JTextField())));
    	this.listAudioConfig.add(new RowModel(2, "language", this.getPropertiesConfig(this.audioProperties, "language"), Messages.getString("StartFrame.languageConfig"), new DefaultCellEditor(new JTextField())));
    	this.listAudioConfig.add(new RowModel(3, "maxConcurrentRequests", this.getPropertiesConfig(this.audioProperties, "maxConcurrentRequests"), Messages.getString("StartFrame.maxConcurrentRequestsConfig"), new DefaultCellEditor(new JTextField())));
    	this.listAudioConfig.add(new RowModel(4, "requestIntervalMillis", this.getPropertiesConfig(this.audioProperties, "requestIntervalMillis"), Messages.getString("StartFrame.requestIntervalMillisConfig"), new DefaultCellEditor(new JTextField())));
    	this.listAudioConfig.add(new RowModel(5, "convertCommand", this.getPropertiesConfig(this.audioProperties, "convertCommand"), Messages.getString("StartFrame.convertCommandConfig"), new DefaultCellEditor(new JTextField())));
    	this.listAudioConfig.add(new RowModel(6, "mimesToProcess", this.getPropertiesConfig(this.audioProperties, "mimesToProcess"), Messages.getString("StartFrame.mimesToProcessConfig"), new DefaultCellEditor(new JTextField())));
    	this.listAudioConfig.add(new RowModel(7, "timeout", this.getPropertiesConfig(this.audioProperties, "timeout"), Messages.getString("StartFrame.timeoutConfig"), new DefaultCellEditor(new JTextField())));
    }
    
    private void loadListElasticSearchConfig() {
    	this.listElasticSearchConfig = new ArrayList<StartFrame.RowModel>();
    	this.listElasticSearchConfig.add(new RowModel(0, "enable", this.getPropertiesConfig(this.elasticSearchProperties, "enable"), Messages.getString("StartFrame.enableConfig"), new CheckBoxEditor()));
    	this.listElasticSearchConfig.add(new RowModel(1, "host", this.getPropertiesConfig(this.elasticSearchProperties, "host"), Messages.getString("StartFrame.hostConfig"), new DefaultCellEditor(new JTextField())));
    	this.listElasticSearchConfig.add(new RowModel(2, "port", this.getPropertiesConfig(this.elasticSearchProperties, "port"), Messages.getString("StartFrame.portConfig"), new DefaultCellEditor(new JTextField())));
    	this.listElasticSearchConfig.add(new RowModel(3, "protocol", this.getPropertiesConfig(this.elasticSearchProperties, "protocol"), Messages.getString("StartFrame.protocolConfig"), new DefaultCellEditor(new JTextField())));
    	this.listElasticSearchConfig.add(new RowModel(4, "index.number_of_shards", this.getPropertiesConfig(this.elasticSearchProperties, "index.number_of_shards"), Messages.getString("StartFrame.index.number_of_shardsConfig"), new DefaultCellEditor(new JTextField())));
    	this.listElasticSearchConfig.add(new RowModel(5, "index.number_of_replicas", this.getPropertiesConfig(this.elasticSearchProperties, "index.number_of_replicas"), Messages.getString("StartFrame.number_of_replicasConfig"), new DefaultCellEditor(new JTextField())));
    	this.listElasticSearchConfig.add(new RowModel(6, "index.lifecycle.name", this.getPropertiesConfig(this.elasticSearchProperties, "index.lifecycle.name"), Messages.getString("StartFrame.index.lifecycle.nameConfig"), new DefaultCellEditor(new JTextField())));
    	this.listElasticSearchConfig.add(new RowModel(7, "min_bulk_size", this.getPropertiesConfig(this.elasticSearchProperties, "min_bulk_size"), Messages.getString("StartFrame.min_bulk_sizeConfig"), new DefaultCellEditor(new JTextField())));
    	this.listElasticSearchConfig.add(new RowModel(8, "max_async_requests", this.getPropertiesConfig(this.elasticSearchProperties, "max_async_requests"), Messages.getString("StartFrame.max_async_requestsConfig"), new DefaultCellEditor(new JTextField())));
    	this.listElasticSearchConfig.add(new RowModel(9, "timeout_millis", this.getPropertiesConfig(this.elasticSearchProperties, "timeout_millis"), Messages.getString("StartFrame.timeout_millisConfig"), new DefaultCellEditor(new JTextField())));
    	this.listElasticSearchConfig.add(new RowModel(10, "connect_timeout_millis", this.getPropertiesConfig(this.elasticSearchProperties, "connect_timeout_millis"), Messages.getString("StartFrame.connect_timeout_millisConfig"), new DefaultCellEditor(new JTextField())));
    	this.listElasticSearchConfig.add(new RowModel(11, "index.mapping.total_fields.limit", this.getPropertiesConfig(this.elasticSearchProperties, "index.mapping.total_fields.limit"), Messages.getString("StartFrame.index.mapping.total_fields.limitConfig"), new DefaultCellEditor(new JTextField())));
    	this.listElasticSearchConfig.add(new RowModel(12, "useCustomAnalyzer", this.getPropertiesConfig(this.elasticSearchProperties, "useCustomAnalyzer"), Messages.getString("StartFrame.implementationClassConfig"), new CheckBoxEditor()));
    }
    
    private void loadListHtmlReportConfig() {
    	this.listHtmlReportConfig = new ArrayList<StartFrame.RowModel>();
    	this.listHtmlReportConfig.add(new RowModel(0, "Header", this.getPropertiesConfig(this.htmlReportProperties, "Header"), Messages.getString("StartFrame.RequestDateConfig"), new DefaultCellEditor(new JTextField())));
    	this.listHtmlReportConfig.add(new RowModel(1, "RequestDate", this.getPropertiesConfig(this.htmlReportProperties, "RequestDate"), Messages.getString("StartFrame.RequestDateConfig"), new DefaultCellEditor(new JTextField())));
    	this.listHtmlReportConfig.add(new RowModel(2, "ReportDate", this.getPropertiesConfig(this.htmlReportProperties, "ReportDate"), Messages.getString("StartFrame.ReportDateConfig"), new DefaultCellEditor(new JTextField())));
    	this.listHtmlReportConfig.add(new RowModel(3, "RecordDate", this.getPropertiesConfig(this.htmlReportProperties, "RecordDate"), Messages.getString("StartFrame.RecordDateConfig"), new DefaultCellEditor(new JTextField())));
    	this.listHtmlReportConfig.add(new RowModel(4, "RequestDoc", this.getPropertiesConfig(this.htmlReportProperties, "RequestDoc"), Messages.getString("StartFrame.RequestDocConfig"), new DefaultCellEditor(new JTextField())));
    	this.listHtmlReportConfig.add(new RowModel(5, "Investigation", this.getPropertiesConfig(this.htmlReportProperties, "Investigation"), Messages.getString("StartFrame.InvestigationConfig"), new DefaultCellEditor(new JTextField())));
    	this.listHtmlReportConfig.add(new RowModel(6, "Report", this.getPropertiesConfig(this.htmlReportProperties, "Report"), Messages.getString("StartFrame.ReportConfig"), new DefaultCellEditor(new JTextField())));
    	this.listHtmlReportConfig.add(new RowModel(7, "Material", this.getPropertiesConfig(this.htmlReportProperties, "Material"), Messages.getString("StartFrame.MaterialConfig"), new DefaultCellEditor(new JTextField())));
    	this.listHtmlReportConfig.add(new RowModel(8, "ExaminerID", this.getPropertiesConfig(this.htmlReportProperties, "ExaminerID"), Messages.getString("StartFrame.ExaminerIDConfig"), new DefaultCellEditor(new JTextField())));
    	this.listHtmlReportConfig.add(new RowModel(9, "Examiner", this.getPropertiesConfig(this.htmlReportProperties, "Examiner"), Messages.getString("StartFrame.ExaminerConfig"), new DefaultCellEditor(new JTextField())));
    	this.listHtmlReportConfig.add(new RowModel(10, "Record", this.getPropertiesConfig(this.htmlReportProperties, "Record"), Messages.getString("StartFrame.enableConfig"), new DefaultCellEditor(new JTextField())));
    	this.listHtmlReportConfig.add(new RowModel(11, "Requester", this.getPropertiesConfig(this.htmlReportProperties, "Requester"), Messages.getString("StartFrame.RequesterConfig"), new DefaultCellEditor(new JTextField())));
    	this.listHtmlReportConfig.add(new RowModel(12, "ItemsPerPage", this.getPropertiesConfig(this.htmlReportProperties, "ItemsPerPage"), Messages.getString("StartFrame.enableConfig"), new DefaultCellEditor(new JTextField())));
    	this.listHtmlReportConfig.add(new RowModel(13, "EnableImageThumbs", this.getPropertiesConfig(this.htmlReportProperties, "EnableImageThumbs"), Messages.getString("StartFrame.EnableImageThumbsConfig"), new CheckBoxEditor()));
    	this.listHtmlReportConfig.add(new RowModel(14, "ThumbSize", this.getPropertiesConfig(this.htmlReportProperties, "ThumbSize"), Messages.getString("StartFrame.ThumbSizeConfig"), new DefaultCellEditor(new JTextField())));
    	this.listHtmlReportConfig.add(new RowModel(15, "EnableVideoThumbs", this.getPropertiesConfig(this.htmlReportProperties, "EnableVideoThumbs"), Messages.getString("StartFrame.EnableVideoThumbsConfig"), new DefaultCellEditor(new JTextField())));
    	this.listHtmlReportConfig.add(new RowModel(16, "VideoStripeWidth", this.getPropertiesConfig(this.htmlReportProperties, "VideoStripeWidth"), Messages.getString("StartFrame.VideoStripeWidthConfig"), new CheckBoxEditor()));
    	this.listHtmlReportConfig.add(new RowModel(17, "FramesPerStripe", this.getPropertiesConfig(this.htmlReportProperties, "FramesPerStripe"), Messages.getString("StartFrame.FramesPerStripeConfig"), new DefaultCellEditor(new JTextField())));
    	this.listHtmlReportConfig.add(new RowModel(18, "EnableCategoriesList", this.getPropertiesConfig(this.htmlReportProperties, "EnableCategoriesList"), Messages.getString("StartFrame.EnableCategoriesListConfig"), new CheckBoxEditor()));
    	this.listHtmlReportConfig.add(new RowModel(19, "EnableThumbsGallery", this.getPropertiesConfig(this.htmlReportProperties, "EnableThumbsGallery"), Messages.getString("StartFrame.EnableThumbsGalleryConfig"), new CheckBoxEditor()));
    	this.listHtmlReportConfig.add(new RowModel(20, "ThumbsPerPage", this.getPropertiesConfig(this.htmlReportProperties, "ThumbsPerPage"), Messages.getString("StartFrame.ThumbsPerPageConfig"), new DefaultCellEditor(new JTextField())));
    
    }
   
    private void loadListImageThumbnailConfig() {
    	this.listImageThumbnailConfig = new ArrayList<StartFrame.RowModel>();
    	
    	this.listImageThumbnailConfig.add(new RowModel(0, "externalConversionTool", this.getPropertiesConfig(this.imageThumbnaillocalProperties, "externalConversionTool"), Messages.getString("StartFrame.externalConversionToolConfig"), new DefaultCellEditor(new JTextField())));
    	this.listImageThumbnailConfig.add(new RowModel(1, "imgConvTimeout", this.getPropertiesConfig(this.imageThumbnaillocalProperties, "imgConvTimeout"), Messages.getString("StartFrame.imgConvTimeoutConfig"), new DefaultCellEditor(new JTextField())));
    	this.listImageThumbnailConfig.add(new RowModel(2, "imgConvTimeoutPerMB", this.getPropertiesConfig(this.imageThumbnaillocalProperties, "imgConvTimeoutPerMB"), Messages.getString("StartFrame.imgConvTimeoutPerMBConfig"), new DefaultCellEditor(new JTextField())));
    	this.listImageThumbnailConfig.add(new RowModel(3, "imgThumbSize", this.getPropertiesConfig(this.imageThumbnaillocalProperties, "imgThumbSize"), Messages.getString("StartFrame.imgThumbSizeConfig"), new DefaultCellEditor(new JTextField())));
    	this.listImageThumbnailConfig.add(new RowModel(4, "extractThumb", this.getPropertiesConfig(this.imageThumbnaillocalProperties, "extractThumb"), Messages.getString("StartFrame.extractThumbConfig"), new CheckBoxEditor()));
    	this.listImageThumbnailConfig.add(new RowModel(5, "galleryThreads", this.getPropertiesConfig(this.imageThumbnaillocalProperties, "galleryThreads"), Messages.getString("StartFrame.galleryThreadsConfig"), new DefaultCellEditor(new JTextField())));
    	this.listImageThumbnailConfig.add(new RowModel(6, "logGalleryRendering", this.getPropertiesConfig(this.imageThumbnaillocalProperties, "logGalleryRendering"), Messages.getString("StartFrame.RequestDateConfig"), new CheckBoxEditor()));
    }
    
    private void loadListPhotoDNAConfig() {
    	this.listPhotoDNAConfig = new ArrayList<StartFrame.RowModel>();
    	
    	this.listPhotoDNAConfig.add(new RowModel(0, "computeFromThumbnail", this.getPropertiesConfig(this.photoDNAProperties, "computeFromThumbnail"), Messages.getString("StartFrame.computeFromThumbnailConfig"), new CheckBoxEditor()));
    	this.listPhotoDNAConfig.add(new RowModel(1, "minFileSize", this.getPropertiesConfig(this.photoDNAProperties, "minFileSize"), Messages.getString("StartFrame.minFileSizeConfig"), new DefaultCellEditor(new JTextField())));
    	this.listPhotoDNAConfig.add(new RowModel(2, "skipKffFiles", this.getPropertiesConfig(this.photoDNAProperties, "skipKffFiles"), Messages.getString("StartFrame.skipKffFilesConfig"), new CheckBoxEditor()));
    	this.listPhotoDNAConfig.add(new RowModel(3, "maxSimilarityDistance", this.getPropertiesConfig(this.photoDNAProperties, "maxSimilarityDistance"), Messages.getString("StartFrame.maxSimilarityDistanceConfig"), new DefaultCellEditor(new JTextField())));
    	this.listPhotoDNAConfig.add(new RowModel(4, "searchRotatedAndFlipped", this.getPropertiesConfig(this.photoDNAProperties, "searchRotatedAndFlipped"), Messages.getString("StartFrame.searchRotatedAndFlippedConfig"), new CheckBoxEditor()));
    }
    
    private void loadListRegexConfig() {
    	this.listRegexConfig = new ArrayList<StartFrame.RowModel>();
    	
    			
    	/*
    	 
    	  
    	  
    	 */
    }
    
    private void loadListVideoThumbnailsConfig() {
    	this.listVideoThumbnailsConfig = new ArrayList<StartFrame.RowModel>();
    	
    	this.listVideoThumbnailsConfig.add(new RowModel(0, "Layout", this.getPropertiesConfig(this.videoThumbnailsProperties, "Layout"), Messages.getString("StartFrame.LayoutConfig"), new DefaultCellEditor(new JTextField())));
    	this.listVideoThumbnailsConfig.add(new RowModel(0, "Timeouts", this.getPropertiesConfig(this.videoThumbnailsProperties, "Timeouts"), Messages.getString("StartFrame.TimeoutsConfig"), new DefaultCellEditor(new JTextField())));
    	this.listVideoThumbnailsConfig.add(new RowModel(0, "Verbose", this.getPropertiesConfig(this.videoThumbnailsProperties, "Verbose"), Messages.getString("StartFrame.VerboseConfig"), new CheckBoxEditor()));
    	this.listVideoThumbnailsConfig.add(new RowModel(0, "GalleryThumbs", this.getPropertiesConfig(this.videoThumbnailsProperties, "GalleryThumbs"), Messages.getString("StartFrame.GalleryThumbsConfig"), new DefaultCellEditor(new JTextField())));
    		
    }
    
    /*
     * Retorna o objeto JTable principal apresentado na tela. 
     */
    private JTable createTable(List<RowModel> listRowModel, final boolean isLocalConfig) {
    	String[] columnNames = {Messages.getString("StartFrame.ativar"),Messages.getString("StartFrame.config"),Messages.getString("StartFrame.valor")};
        Object[][] data = getRowsData(listRowModel);
        
    	DefaultTableModel model = new DefaultTableModel(data, columnNames);
        JTable table = new JTable(model){
            private static final long serialVersionUID = 1L;

            @SuppressWarnings({ "unchecked", "rawtypes" })
			@Override
            public Class getColumnClass(int column) {
                switch (column) {
                    case 0:
                        return Boolean.class;
                    case 1:
                        return String.class;
                    case 2:
                        return String.class;
                    default:
                        return String.class;
                }
            }
            
            @Override
            public boolean isCellEditable(int row, int column) {  
            	if(isLocalConfig) {
            		return (column == 0 && (row == 0 || row == 1)) || (column == 1) ? false : true;
            	} 
            	
            	return true;
            };
        };
        
        //Monta os campos da coluna de valor de acordo com a linha de confioguracao
        CustomTableCellEditor  customTableCellEditor = new CustomTableCellEditor();
        customTableCellEditor.isLocalConfig = isLocalConfig;
        customTableCellEditor.listConfig = listRowModel;
        
        table.getColumnModel().getColumn(2).setCellEditor(customTableCellEditor);
        table.getColumnModel().getColumn(0).setMaxWidth(40);
        
        for(int i =0; i < 3; i++) {
        	table.setRowHeight(i, 20);	
        }
        return table;
    }
    
    /*
     * Retorna a lista de informações contidas no arquivo "LocalConfig.txt"
     * em formado de array para carregamento no JTable principal da tela.
     */
    private Object[][] getRowsData(List<RowModel> listRowModel) {
    	
    	Object[][] data = new Object[listRowModel.size()][3];
    	for(int i = 0; i < listRowModel.size(); i++) {
    		RowModel rowModel = listRowModel.get(i);
    		String index = (i) < 9 ? "0"+(i+1) : (i+1)+"";
    		
    		boolean check = !"".equalsIgnoreCase(rowModel.getValue());
    		rowModel.setCheck(check);
    		
    		data[i] = new Object[] {check, "  ["+(index)+"] "+rowModel.getTitle(), rowModel.getValue()};
    	}
    	return data;
    }
    
    /*
     * Método que cria os campos de configuracao de acordo com cada linha de configuracao
     */
    @SuppressWarnings("serial")
	public class CustomTableCellEditor extends AbstractCellEditor implements TableCellEditor {
        private TableCellEditor editor;
        public List<RowModel> listConfig = new ArrayList<RowModel>();
        public boolean isLocalConfig = false;
        public Object getCellEditorValue() {
            if (editor != null) {
                return editor.getCellEditorValue();
            }

            return null;
        }

        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        	boolean editorOK = false;
        	for(RowModel rowModel : listConfig) {
        		if(row == rowModel.getRow()) {
        			editor = rowModel.getEditor();
        			editorOK = true;
        			if(isLocalConfig) {
	        			if(row == 0) {//Identifica o campo locale 
	        				((ComboBoxEditor)editor).setId("locale");
	        			} else if(row == 1) {//Identifica o campo de configuracao do caso pois a partir dele as demais configurações são carregadas 
	        				((ComboBoxEditor)editor).setId("caseConfig");
	        			}
        			}
        			break;
        		}
        	}
        	
            if (!editorOK) {
                editor = new DefaultCellEditor(new JTextField());
            }

            return editor.getTableCellEditorComponent(table, value, isSelected, row, column);
        }
    }
    
    private String getLocalConfig(String key) {
    	if(this.localProperties.containsKey(key)) {
    		return this.localProperties.getProperty(key);
    	}
    	return "";
    }
    
    private String getPropertiesConfig(UTF8Properties props, String key) {
    	if(props.containsKey(key)) {
    		return props.getProperty(key);
    	}
    	return "";
    }
    
    public class RowModel {
    	private String key;
    	private String value;
    	private String title;
    	private DefaultCellEditor editor;
    	private int row;
    	private boolean check;
    	
    	public RowModel(int row, String key, String value, String title, DefaultCellEditor editor) {
    		this.row = row;
    		this.key = key;
    		this.value = value;
    		this.title = title + " ("+key+")";
    		this.editor = editor;
    	}
    	
		public String getKey() {
			return key;
		}
		public String getValue() {
			return value;
		}
		public String getTitle() {
			return title;
		}
		
		public DefaultCellEditor getEditor() {
			return editor;
		}
		public int getRow() {
			return row;
		}

		public void setKey(String key) {
			this.key = key;
		}

		public void setValue(String value) {
			this.value = value;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public void setEditor(DefaultCellEditor editor) {
			this.editor = editor;
		}

		public void setRow(int row) {
			this.row = row;
		}

		public boolean getCheck() {
			return check;
		}

		public void setCheck(boolean check) {
			this.check = check;
		}
    }
    
    /*
     * Monta o conteúdo do campo CaseConfig de acordo com os nomes
     * das pastas do diret�rio /profiles/[locale]/, esse diret�rio 
     * é utilizado para mantes as configurações de diferentes formatos
     * de casos
     */
    private String[] getListConfigs() {
    	File file = new File(this.rootPath+"//profiles//"+this.locale+"//");
    	File afile[] = file.listFiles();
    	int i = 0;
    	String[] preConfigs = new String[afile.length];
    	
    	for (int j = afile.length; i < j; i++) {
    		File arquivos = afile[i];
    		preConfigs[i] = arquivos.getName();
    	}
    	if(preConfigs.length == 0) {
    		preConfigs = new String[0];
    		preConfigs[0] = "default";
    	}
    	
    	return preConfigs;
    }
    
    /*
     * Faz o carregamento inicial do arquivo LocalConfig.txt para um objeto de properties
     */
    private void loadLocalConfigFile() {
    	this.localProperties = new UTF8Properties();
    	
    	try {
	        UTF8Properties props = new UTF8Properties();
	        props.load(new File(this.rootPath, Configuration.LOCAL_CONFIG));
	        props.load(new File(this.rootPath, ""));
	        this.localProperties = props;
        
    	} catch(Exception e) {
    		this.loadLocalConfigFile(0);
    		//throw new Exception("LocalConfig not found " + rootPath); 	
    	}
    }
    
    private void loadLocalConfigFile(int seq) {
    	this.localProperties = new UTF8Properties();
    	
    	try {
    		UTF8Properties props = new UTF8Properties();
    		
    		//Busca em alguns possiveis locais as configuracoes
    		//pode estar dentro do proprio workspace do projeto
    		//ou pode estar sendo execurado por dentro da pasta lib 
    		//do IPED, ao encontrar o arquivo LocalConfig o rootPath 
    		//sera definido corretamento
    		if(seq == 0) {
    			this.rootPath = this.rootPath + "\\release";
    	        	
    		} else if(seq == 1) {
    			this.rootPath = rootPath.substring(0, this.rootPath.lastIndexOf("\\"));
    			
    		}
    		
    		props.load(new File(this.rootPath, Configuration.LOCAL_CONFIG));
	        this.localProperties = props;
        
    	} catch(Exception e) {
    		if(seq==0) {
    			seq++;
    			this.loadLocalConfigFile(seq);
    		} else {
    			e.printStackTrace();
    		}
    		//throw new Exception("LocalConfig not found " + rootPath); 	
    	}
    }
    
    
    //Editors
    public class ComboBoxEditor extends DefaultCellEditor {
		private static final long serialVersionUID = 1L;
		@SuppressWarnings("rawtypes")
		private JComboBox comboBox;
		private String id;
		
		@SuppressWarnings({ "unchecked", "rawtypes" })
		public ComboBoxEditor(String[] items) {
            super(new JComboBox(items));
            comboBox = (JComboBox)this.getComponent();
            
            comboBox.addActionListener (new ActionListener () {
                public void actionPerformed(ActionEvent e) {
                	if("locale".equalsIgnoreCase(id)) {
                		listLocalConfig.get(0).setValue((String)comboBox.getSelectedItem());
                		loadTabsConfig(false);
                	} else if("caseConfig".equalsIgnoreCase(id)) {
                		listLocalConfig.get(1).setValue((String)comboBox.getSelectedItem());
                		loadTabsConfig(false);
                	}
                }
            });
        }

		public void setId(String id) {
			this.id = id;
		}
    }

    public class CheckBoxEditor extends DefaultCellEditor {
		private static final long serialVersionUID = 1L;

		public CheckBoxEditor() {
            super(new JCheckBox());
        }
    }
    
    @SuppressWarnings("serial")
	public class FileChooserCellEditor extends DefaultCellEditor implements TableCellEditor {

        /** Number of clicks to start editing */
        private static final int CLICK_COUNT_TO_START = 2;
        /** Editor component */
        private JButton button;
        /** File chooser */
        private JFileChooser fileChooser;
        /** Selected file */
        private String file = "";

        /**
         * Constructor.
         */
        public FileChooserCellEditor() {
            super(new JTextField());
            setClickCountToStart(CLICK_COUNT_TO_START);

            // Using a JButton as the editor component
            button = new JButton();
            button.setBackground(Color.white);
            button.setFont(button.getFont().deriveFont(Font.PLAIN));
            button.setBorder(null);
            
            // Dialog which will do the actual editing
            fileChooser = new JFileChooser();
            
        }

        @Override
        public Object getCellEditorValue() {
            return file;
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            file = value.toString();
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    fileChooser.setSelectedFile(new File(file));
                    if (fileChooser.showOpenDialog(button) == JFileChooser.APPROVE_OPTION) {
                        file = fileChooser.getSelectedFile().getAbsolutePath();
                    }
                    fireEditingStopped();
                }
            });
            button.setText(file);
            return button;
        }
    }
    
    //AÇÕES DA TELA
    
    /*
     * Método que controla o evento do botão salvar, esse método faz a leitura das configurações
     * que o usuario fez e altera os arquivos de configuracao de forma a trocar apenas os valores 
     * de cada configuracao, sem alterar os demais conteúdos, para isso a rotina percorre linha a 
     * linha procurando as chaves que devem serem alteras.
     */
    private void saveConfig() {
    	try {
	    	/*String path = this.rootPath + "" + Configuration.LOCAL_CONFIG;
	    	
	    	//LocalConfig
	    	this.loadValuesFromJTable(this.tableLocalConfig, this.listLocalConfig);
	    	this.saveFile(path, this.listLocalConfig);

	    	//IPED Config
	    	this.loadValuesFromJTable(this.tableIpedConfig, this.listIpedConfig);
	    	String pathIpedConfig = this.rootPath + "profiles/"+ this.locale + "/" + this.caseConfig + "/" + Configuration.CONFIG_FILE;
	    	this.saveFile(pathIpedConfig, this.listIpedConfig);

	    	
	    	JOptionPane.showMessageDialog(null, Messages.getString("StartFrame.saveSuccess"));
	    	*/
	    	
    	} catch(Exception e) {
    		JOptionPane.showMessageDialog(null, "Erro: " + e.getMessage(), Messages.getString("StartFrame.warning"),  JOptionPane.WARNING_MESSAGE);
    	}
    }
    
    private void loadValuesFromJTable(JTable table, List<RowModel> listRowModel) {
    	
    	for(int i = 0; i< listRowModel.size(); i++ ) {
    		RowModel rowModel = listRowModel.get(i);
    		Object val = table.getModel().getValueAt(i, 2);
    		String value = "";
    		if(val != null) {
	    		if(val instanceof String) {
	    			value = (String)val;
	    		} else if(val instanceof Boolean) {
	    			value = ((Boolean)val).toString();
	    		} 
    		}
    		rowModel.setValue(value);
    	}
    }
    
    /*
     *	Método que faz uma leitura linha a linha do arquivo de configuracao e 
     *  altera somente o conteúdo da variavel alterada pela tela, assim mantemos
     *  o padrão dos arquivos de configuracao e evitamos de perder informações
     *  como comentarios ou chaves comentadas. 
     */
    private void saveFile(String path, List<RowModel> listRowModel) {
    	try {
    		
    		//Mantém todas as chaves encontradas no arquivo;
    		Map<String, String> mapKeys = new HashMap<String, String>();
    		for(RowModel rowModel : listRowModel) {
    			mapKeys.put(rowModel.getKey(), "N");
    		}
    		
    		
	    	File file = new File(path);      
	    	FileReader fr = new FileReader(file);  
	    	BufferedReader br = new BufferedReader(fr);  
	    	StringBuffer sb= new StringBuffer();  
	    	String line;  
	    	while((line=br.readLine())!=null)  {
	    		String lineTxt = replaceValueLine(line, listRowModel, mapKeys);
	    		sb.append(lineTxt);        
	    		sb.append("\n");        
	    	}  
	    	fr.close();
	    	
	    	String fileContent = sb.toString();
	    	
	    	//Ap�s percorrer o arquivo alterando os registros existentes é necessario incluir
	    	//as configurações queestão marcadas na tela mas não estão salvas no arquivo.
	    	String newLinesTxt = "";
	    	for(RowModel rowModel : listRowModel) {
	    		boolean insertKey = !"Y".equalsIgnoreCase(mapKeys.get(rowModel.getKey())) && !"Y".equalsIgnoreCase(mapKeys.get(rowModel.getKey()).replace("#", "")); 
	    		if(insertKey) {
	    			newLinesTxt = newLinesTxt + rowModel.getKey() + "=" + rowModel.getValue() + "\n";
	    			mapKeys.put(rowModel.getKey(), "Y");
	    		}
	    	}
	    	fileContent = fileContent+newLinesTxt;
	    	
	    	FileUtils.writeStringToFile(new File(path), fileContent, "UTF-8");
	    	
	    	
	    } catch(Exception e) {
	    	JOptionPane.showMessageDialog(null, "Erro: " + e.getMessage(),Messages.getString("StartFrame.warning"), JOptionPane.WARNING_MESSAGE);
		}
    }
    
    private String replaceValueLine(String lineTxt, List<RowModel> listRowModel, Map<String, String> mapKeys) {
    	for(RowModel rowModel : listRowModel) {
			if(lineTxt.indexOf(rowModel.getKey()) != -1) {//Encontrou a chave equivalente na linha
				String keyValue[] = lineTxt.split("=");
				if(keyValue.length == 2) { //Encontrou a chave com o valor
					String key = keyValue[0].trim();
					if(key.equalsIgnoreCase(rowModel.getKey()) || (key.replace("#", "")).equalsIgnoreCase(rowModel.getKey())) {
						//Verifica se o check da tela define se a configuracao fica comentada ou ativa no arquivo
						if(rowModel.getCheck()) {
							if(key.indexOf("#") != -1) {
								key = key.replace("#", "");
							}
							lineTxt = key + "=" + rowModel.getValue();//Altera o valor da chave na linha do arquivo	
						} else {
							if(key.indexOf("#") == -1) {
								key = "#" + key;
							}
						}
						mapKeys.put(key.replace("#", ""), "Y");
						lineTxt = key + "=" + rowModel.getValue();//Altera o valor da chave na linha do arquivo
						break;
					}
				}
			}
		}
    	return lineTxt;
    }
    
    //MONTAGEM DAS TABS DA TELA
    private JTabbedPane createTabPanel() {
    	this.loadTabsConfig(true);//Carrega todos os arquivos de configuracao da aplicacao 
    	this.loadListIpedConfig();//Carrega a lista de RowModel para configurações do IPED
    	
    	this.loadListAdvancedConfig();
    	this.loadListAudioConfig();
    	this.loadListElasticSearchConfig();
    	this.loadListHtmlReportConfig();
    	this.loadListImageThumbnailConfig();
    	this.loadListPhotoDNAConfig();
    	this.loadListRegexConfig();
    	this.loadListVideoThumbnailsConfig();
    	
    	this.tableIpedConfig = this.createTable(this.listIpedConfig, false);
    	
    	this.tableAdvancedConfig = this.createTable(this.listAdvancedConfig, false);
    	this.tableAudioConfig = this.createTable(this.listAudioConfig, false);
    	this.tableElasticSearchConfig = this.createTable(this.listElasticSearchConfig, false);
    	this.tableHtmlReportConfig = this.createTable(this.listHtmlReportConfig, false);
    	this.tableImageThumbnaillocalConfig = this.createTable(this.listImageThumbnailConfig, false);
    	this.tablePhotoDNAConfig = this.createTable(this.listPhotoDNAConfig, false);
    	this.tableRegexConfig = this.createTable(this.listRegexConfig, false);
    	this.tableVideoThumbnailsConfig = this.createTable(this.listVideoThumbnailsConfig, false);
    	
    	JTabbedPane tabbedPane = new JTabbedPane();

    	JScrollPane scrollFrame = new JScrollPane(this.tableIpedConfig);
    	this.tableIpedConfig.setAutoscrolls(true);
    	this.tableIpedConfig.setPreferredSize(new Dimension( 2000,550));
        scrollFrame.setPreferredSize(new Dimension( 800,260));
        
    	JPanel panel1 = new JPanel();
    	panel1.setLayout(new GridLayout(1,1));
    	panel1.add(scrollFrame);
    	tabbedPane.addTab(Messages.getString("StartFrame.ipedConfig"), panel1);
    	
    	JScrollPane scrollFrame2 = new JScrollPane(this.tableAdvancedConfig);
    	this.tableAdvancedConfig.setAutoscrolls(true);
    	this.tableAdvancedConfig.setPreferredSize(new Dimension( 2000,550));
        scrollFrame2.setPreferredSize(new Dimension( 800,260));
        
    	JPanel panel2 = new JPanel();
    	panel2.setLayout(new GridLayout(1,1));
    	panel2.add(scrollFrame2);
    	tabbedPane.addTab(Messages.getString("StartFrame.advanced"), panel2);
    	
    	
    	JScrollPane scrollFrame3 = new JScrollPane(this.tableAudioConfig);
    	this.tableAudioConfig.setAutoscrolls(true);
    	this.tableAudioConfig.setPreferredSize(new Dimension( 2000,550));
        scrollFrame3.setPreferredSize(new Dimension( 800,260));
        
    	JPanel panel3 = new JPanel();
    	panel3.setLayout(new GridLayout(1,1));
    	panel3.add(scrollFrame3);
    	tabbedPane.addTab(Messages.getString("StartFrame.audio"), panel3);
    	
    
    	JScrollPane scrollFrame4 = new JScrollPane(this.tableElasticSearchConfig);
    	this.tableElasticSearchConfig.setAutoscrolls(true);
    	this.tableElasticSearchConfig.setPreferredSize(new Dimension( 2000,550));
        scrollFrame4.setPreferredSize(new Dimension( 800,260));
        
    	JPanel panel4 = new JPanel();
    	panel4.setLayout(new GridLayout(1,1));
    	panel4.add(scrollFrame4);
    	tabbedPane.addTab(Messages.getString("StartFrame.elasticSearch"), panel4);
    

    	JScrollPane scrollFrame5 = new JScrollPane(this.tableHtmlReportConfig);
    	this.tableHtmlReportConfig.setAutoscrolls(true);
    	this.tableHtmlReportConfig.setPreferredSize(new Dimension( 2000,550));
        scrollFrame5.setPreferredSize(new Dimension( 800,260));
        
    	JPanel panel5 = new JPanel();
    	panel5.setLayout(new GridLayout(1,1));
    	panel5.add(scrollFrame5);
    	tabbedPane.addTab(Messages.getString("StartFrame.htmlReport"), panel5);
    
    	
    	JScrollPane scrollFrame6 = new JScrollPane(this.tableImageThumbnaillocalConfig);
    	this.tableImageThumbnaillocalConfig.setAutoscrolls(true);
    	this.tableImageThumbnaillocalConfig.setPreferredSize(new Dimension( 2000,550));
        scrollFrame6.setPreferredSize(new Dimension( 800,260));
        
    	JPanel panel6 = new JPanel();
    	panel6.setLayout(new GridLayout(1,1));
    	panel6.add(scrollFrame6);
    	tabbedPane.addTab(Messages.getString("StartFrame.imageThumbnail"), panel6);
    	
    	
    	JScrollPane scrollFrame7 = new JScrollPane(this.tablePhotoDNAConfig);
    	this.tablePhotoDNAConfig.setAutoscrolls(true);
    	this.tablePhotoDNAConfig.setPreferredSize(new Dimension( 2000,550));
        scrollFrame7.setPreferredSize(new Dimension( 800,260));
        
    	JPanel panel7 = new JPanel();
    	panel7.setLayout(new GridLayout(1,1));
    	panel7.add(scrollFrame7);
    	tabbedPane.addTab(Messages.getString("StartFrame.PhotoDNA"), panel7);
    	
    	
    	JScrollPane scrollFrame8 = new JScrollPane(this.tableRegexConfig);
    	this.tableRegexConfig.setAutoscrolls(true);
    	this.tableRegexConfig.setPreferredSize(new Dimension( 2000,550));
        scrollFrame8.setPreferredSize(new Dimension( 800,260));
        
    	JPanel panel8 = new JPanel();
    	panel8.setLayout(new GridLayout(1,1));
    	panel8.add(scrollFrame8);
    	tabbedPane.addTab(Messages.getString("StartFrame.regex"), panel8);
    	
    	
    	JScrollPane scrollFrame9 = new JScrollPane(this.tableVideoThumbnailsConfig);
    	this.tableVideoThumbnailsConfig.setAutoscrolls(true);
    	this.tableVideoThumbnailsConfig.setPreferredSize(new Dimension( 2000,550));
        scrollFrame9.setPreferredSize(new Dimension( 800,260));
        
    	JPanel panel9 = new JPanel();
    	panel9.setLayout(new GridLayout(1,1));
    	panel9.add(scrollFrame9);
    	tabbedPane.addTab(Messages.getString("StartFrame.videoThumbnails"), panel9);
    	
    	return tabbedPane;
    }
    
    /*
     * Método responsavel por realizar a leitura dos arquivos de configuracao de 
     * acordo com a configuracao de caso escolhida, essa rotina percorre o diret�rio correspondente
     * e obtém os dados dos arquivos IPEDConfig.txt, AdvancedConfig.txt, AudioTranscriptConfig.txt,
     * ElasticSearchConfig.txt, HTMLReportConfig.txt, ImageThumbsConfig.txt, KeywordsToExport.txt,
     * PhotoDNAConfig.txt, RegexConfig.txt, VideoThumbsConfig.txt.
     */
    private void loadTabsConfig(boolean forceExecute) {
    	String localTableRow = this.listLocalConfig.get(0).getValue();
    	String caseConfigTableRow = this.listLocalConfig.get(1).getValue();
    	
    	boolean isChange = false;
    	if(!this.locale.equalsIgnoreCase(localTableRow)) {
    		this.locale = localTableRow;
    		isChange = true;
    	}
    	if(!this.caseConfig.equalsIgnoreCase(caseConfigTableRow)) {
    		this.caseConfig = caseConfigTableRow;
    		isChange = true;
    	}
    	
    	if(isChange || forceExecute) {
	    //	String pathIpedConfig = "profiles/"+ this.locale + "/" + this.caseConfig + "/" + Configuration.CONFIG_FILE;
	    	String pathAdvancedConfig =  "profiles/"+this.locale + "/" + this.caseConfig + "/conf/AdvancedConfig.txt";
	    	String pathAudioConfig =  "profiles/"+this.locale + "/" + this.caseConfig + "/conf/AudioTranscriptConfig.txt";
	    	String pathElasticSearchConfig =  "profiles/"+this.locale + "/" + this.caseConfig + "/conf/ElasticSearchConfig.txt";
	    	String pathHtmlReportConfig =  "profiles/"+this.locale + "/" + this.caseConfig + "/conf/HTMLReportConfig.txt";
	    	String pathImageThumbnailConfig =  "profiles/"+this.locale + "/" + this.caseConfig + "/conf/ImageThumbsConfig.txt";
	    	String pathPhotoDNAConfig =  "profiles/"+this.locale + "/" + this.caseConfig + "/conf/PhotoDNAConfig.txt";
	    	String pathRegexConfig =  "profiles/"+this.locale + "/" + this.caseConfig + "/conf/RegexConfig.txt";
	    	String pathVideoThumbnailsConfig =  "/profiles/"+this.locale + "/" + this.caseConfig + "/conf/VideoThumbsConfig.txt";
	        
	    	if(this.ipedProperties != null && !forceExecute) {
	    		int dialogButton = JOptionPane.YES_NO_OPTION;
	    		int dialogResult = JOptionPane.showConfirmDialog (null, Messages.getString("StartFrame.confirmChangeConfig"), Messages.getString("StartFrame.warning"), dialogButton);
	    		if(dialogResult == JOptionPane.YES_OPTION){
	    			this.center.remove(this.tabPanel);
	    			this.center.revalidate();
	    			this.center.repaint();
	    			
	    			this.tabPanel = this.createTabPanel();
	    			this.center.add(this.tabPanel);
	    		}
	     	} else {
	     		this.ipedProperties = new UTF8Properties();
	     		//this.loadConfigFile(this.ipedProperties, pathIpedConfig);
	     		
	     		this.advancedProperties = new UTF8Properties();
	     		this.loadConfigFile(this.advancedProperties, pathAdvancedConfig);
	     		
	     		this.audioProperties = new UTF8Properties();
	     		this.loadConfigFile(this.audioProperties, pathAudioConfig);
	     		
	     		this.elasticSearchProperties = new UTF8Properties();
	     		this.loadConfigFile(this.elasticSearchProperties, pathElasticSearchConfig);
	     		
	     		this.htmlReportProperties = new UTF8Properties();
	     		this.loadConfigFile(this.htmlReportProperties, pathHtmlReportConfig);
	     		
	     		this.imageThumbnaillocalProperties = new UTF8Properties();
	     		this.loadConfigFile(this.imageThumbnaillocalProperties, pathImageThumbnailConfig);
	     		
	     		this.photoDNAProperties = new UTF8Properties();
	     		this.loadConfigFile(this.photoDNAProperties, pathPhotoDNAConfig);
	     		
	     		this.regexProperties = new UTF8Properties();
	     		this.loadConfigFile(this.regexProperties, pathRegexConfig);
	     		
	     		this.videoThumbnailsProperties = new UTF8Properties();
	     		this.loadConfigFile(this.videoThumbnailsProperties, pathVideoThumbnailsConfig);
	     	}
    	}
    }
    
    private void loadConfigFile(UTF8Properties props, String fileConfig) {
    	try {
		    props.load(new File(this.rootPath, fileConfig));
    	} catch(Exception e) {
    		//throw new IPEDException("LocalConfig not found " + rootPath); 	
    	}
    }
    
    public void propertyChange(PropertyChangeEvent evt) {
    }

    public void windowActivated(WindowEvent arg0) {
        // TODO Auto-generated method stub
    }

    public void windowClosed(WindowEvent arg0) {
    }

    public void windowClosing(WindowEvent arg0) {
    }

    public void windowDeactivated(WindowEvent arg0) {
        // TODO Auto-generated method stub
    }

    public void windowDeiconified(WindowEvent arg0) {
        // TODO Auto-generated method stub
    }

    public void windowIconified(WindowEvent arg0) {
        // TODO Auto-generated method stub
    }

    public void windowOpened(WindowEvent arg0) {
        // TODO Auto-generated method stub
    }

    public void actionPerformed(ActionEvent e) {
    	//JOptionPane.showMessageDialog(null, "e.getSource():"+e.getSource());
        if (e.getSource().equals(save)) {
        	this.saveConfig();
        	
        } else if(e.getSource().equals(menuItemConfigIPED)) {
        	this.initConfigIPED();
        } else if(e.getSource().equals(menuItemIniciar)) {        	
        	this.initCaseProcess();
        } else if(e.getSource().equals(check1)) {
        	this.check2.setSelected(false);
        	this.command.setEditable(false);
        	this.txtFileOrig.setEnabled(true);
        	this.btnFile.setEnabled(true);
        	this.txtPathDest.setEnabled(true);
        	this.btnPath.setEnabled(true);
        	
        } else if(e.getSource().equals(check2)) {
        	this.check1.setSelected(false);
        	this.command.setEditable(true);
        	this.txtFileOrig.setEnabled(false);
        	this.btnFile.setEnabled(false);
        	this.txtPathDest.setEnabled(false);
        	this.btnPath.setEnabled(false);
        
        } else if(e.getSource().equals(this.btnFile)) {
        	this.caseFileOrig = new JFileChooser();
            
            this.caseFileOrig.setFileSelectionMode(JFileChooser.FILES_ONLY);
    	    int i= this.caseFileOrig.showSaveDialog(null);
    	    if (i==1){
    	    	this.txtFileOrig.setText("");
    	    } else {
    	         File arquivo = this.caseFileOrig.getSelectedFile();
    	         this.txtFileOrig.setText(arquivo.getPath());
    	    }
        } else if(e.getSource().equals(this.btnPath)) {
        	this.casePathDest = new JFileChooser();
            
            this.casePathDest.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    	    int i= this.casePathDest.showSaveDialog(null);
    	    if (i==1){
    	    	this.txtPathDest.setText("");
    	    } else {
    	         File arquivo = this.casePathDest.getSelectedFile();
    	         this.txtPathDest.setText(arquivo.getPath());
    	    }
        } else if(e.getSource().equals(btnCaseProcess)) {
        	boolean isValid = true;
        	if(this.check1.isSelected()) {
	        	if(this.txtFileOrig.getText() == null  || this.txtFileOrig.getText().trim().length() == 0) {
	        		JOptionPane.showMessageDialog(null, Messages.getString("StartFrame.fileOrigMsg"), Messages.getString("StartFrame.warning"),  JOptionPane.WARNING_MESSAGE);
	        		isValid = false;
	        	} else if(this.txtPathDest.getText() == null  || this.txtPathDest.getText().trim().length() == 0) {
	        		JOptionPane.showMessageDialog(null, Messages.getString("StartFrame.pathDestMsg"), Messages.getString("StartFrame.warning"),  JOptionPane.WARNING_MESSAGE);
	        		isValid = false;
	        	}
        	} else if(this.check1.isSelected()) {
        		if(this.command.getText() == null  || this.command.getText().trim().length() == 0) {
        			JOptionPane.showMessageDialog(null, Messages.getString("StartFrame.txtCommando"), Messages.getString("StartFrame.warning"),  JOptionPane.WARNING_MESSAGE);
        			isValid = false;
	        	}
        	}
        	
        	if(isValid) {
        		String fileOrig = "";
        		String pathDest = "";
        		if(this.check1.isSelected()) {
        			fileOrig = this.txtFileOrig.getText();
        			pathDest = this.txtPathDest.getText();
        		} else if(this.check2.isSelected()) {
        			String commands[] = this.command.getText().split(" ");
        			fileOrig = commands[1];
        			pathDest = commands[3];
        		}
        		
        		String[] args = new String[4];
        		args[0] = " -d ";
        		args[1] = fileOrig;
        		args[2] = " -o ";
        		args[3] = pathDest;
        		
        		String commandLine =  " java -jar "+this.rootPath+"iped.jar -d " + fileOrig + " -o " + pathDest;
        		try {
					Runtime.getRuntime().exec(commandLine);
					
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(null, "Erro:"+e1.getMessage(), Messages.getString("StartFrame.warning"),  JOptionPane.WARNING_MESSAGE);
				}
        	}
        	
        	
        } else if(e.getSource().equals(close) || e.getSource().equals(menuItemSair)) {
        	System.exit(0);
        } 
    }
    
}
