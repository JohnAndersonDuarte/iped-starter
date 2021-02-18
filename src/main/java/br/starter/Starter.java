/*
 * 
 */
package br.starter;

import java.io.File;
import java.util.List;
import javax.swing.SwingWorker;

/**
 * Ponto de entrada do programa para inicializar a interface gráfica de configuração 
 * inicial antes do efetivo processamento 
 */
public class Starter extends SwingWorker<Boolean, Integer> {

    String rootPath, configPath;
    String profile, locale;
    File palavrasChave;
    List<File> dataSource;
    File output;
    File logFile;
    
    /**
     * Contrutor utilizado pela execucao via linha de comando
     */
    public Starter(String[] args) {
        super();
    }

    /**
     * Entrada principal da aplicacao para processamento de evidÃªncias
     */
    public static void main(String[] args) {
    	StartFrame startFrame = new StartFrame();
    	startFrame.setVisible(true);
    }

	@Override
	protected Boolean doInBackground() throws Exception {
		return null;
	}

   
}
