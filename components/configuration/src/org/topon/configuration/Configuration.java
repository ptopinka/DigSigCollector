package org.topon.configuration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class Configuration {

	private String keystore;
	private String alias;
	private String password;
	private String keystoreType;

	private String signatureStoreURL;
	private String documentsStoreURL;
	
	private Properties props;
	private static Configuration config;

	private Configuration() {
		
	}
	
	public static Configuration getInstance() {
		if(config == null) {
			config = new Configuration();
			config.loadConfiguration();
		}
		
		return config;
	}
	

	private void  loadConfiguration() {
		props = new Properties();
		try {
			props.load(new FileInputStream("conf/identity.properties"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(props == null) System.out.println("je to kurva null");
		getProperties(props);
	}

        
        public boolean saveConfiguration(String configPath) {
            boolean saved = false;
            FileOutputStream fous;
            try {
                fous = new FileOutputStream(new File(configPath));
                
                props.store(fous,"");
                fous.flush();
                fous.close();
                saved = true;
                
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            return saved;
        }
        
        
        /**
	 * Get properties from initialized Properties
	 * @param props
	 */
	private void getProperties(Properties props) {
		keystore = props.getProperty("user.keystore");
		keystoreType = props.getProperty("keystore.type");
		password = props.getProperty("keystore.password");
		alias = props.getProperty("keystore.alias");
		signatureStoreURL = props.getProperty("server.url.signatures.store");
		documentsStoreURL = props.getProperty("server.url.documents.store");

	}
        
        /**
         * Change internal configuration by given properties 
         */
        public Configuration reloadConfiguration(Properties props) {
            getProperties(props);
            return config;
        }
        
        

	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public String getKeystore() {
		return keystore;
	}
	public void setKeystore(String keystore) {
		this.keystore = keystore;
	}
	public String getKeystoreType() {
		return keystoreType;
	}
	public void setKeystoreType(String keystoreType) {
		this.keystoreType = keystoreType;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}





	public String getDocumentsStoreURL() {
		return documentsStoreURL;
	}

	public void setDocumentsStoreURL(String documentsStoreURL) {
		this.documentsStoreURL = documentsStoreURL;
	}

	public String getSignatureStoreURL() {
		return signatureStoreURL;
	}

	public void setSignatureStoreURL(String signatureStoreURL) {
		this.signatureStoreURL = signatureStoreURL;
	}


}
