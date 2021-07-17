package RWC.Bot;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;

/**
 * Utility class that manages communication with google sheets API
 * 
 * @author Rose Griffin
 *
 */
public final class SheetsUtil {
	
	private static final String APPLICATION_NAME = "Bot";
	private static final List<String> SCOPES = Collections.singletonList(SheetsScopes.SPREADSHEETS_READONLY);
	
	/**
	* Creates sheets object that allows for reading from a spreadsheet
	* @throws GeneralSecurityException
	* @throws IOException
	*/
	public static Sheets getService() throws GeneralSecurityException, IOException {
    	Sheets service = new Sheets.Builder(GoogleNetHttpTransport.newTrustedTransport(),
    			JacksonFactory.getDefaultInstance(), getCredentials())
    			.setApplicationName(APPLICATION_NAME)
    			.build();
    	return service;
    }
    
	/**
	* Creates and returns an authorized Credential object
	* @throws IOException If credentials file cannot be found
	* @throws GeneralSecurityException If access token cannot be obtained
	*/
    public static Credential getCredentials() throws IOException, GeneralSecurityException{
    	//Load client secrets from credentials file
        InputStream in = SheetsUtil.class.getResourceAsStream("/resources/credentials.json");
        if (in == null) {
            throw new FileNotFoundException("Credentials file not found");
        }
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JacksonFactory.getDefaultInstance(), new InputStreamReader(in));
        
        //Obtain an access token that will allow for API requests and store it in a folder named tokens
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(GoogleNetHttpTransport.newTrustedTransport(),
        		JacksonFactory.getDefaultInstance(), clientSecrets, SCOPES)
        		.setDataStoreFactory(new FileDataStoreFactory(new java.io.File("tokens")))
        		.setAccessType("offline")
        		.build();
        Credential credential = new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver())
        		.authorize("user");
        
        return credential;
    }
}
