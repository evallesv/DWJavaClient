package solutions.vpf.docuware;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.String;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.StringJoiner;
import java.util.logging.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import solutions.vpf.docuware.common.Organizations;

import java.util.HashMap;
import java.util.List;

/**
 * @author evall
 * Implementación del Api REST de DocuWare en JAVA "Vanilla" compatible con JDK 1.6 o superior
 */
public class VanillaDwClient {
	
	private static HttpURLConnection connection;
	private Logger logger;
	private String Url;
	private String cookies = "";
	
	public VanillaDwClient(String BaseApi) throws MalformedURLException {
		logger = Logger.getLogger(VanillaDwClient.class.getName());
		Url = BaseApi + "/DocuWare/Platform/";
	}
	
	public boolean Login(String User, String Password) {
		Map<String, String> prop = new HashMap<>();
		prop.put("UserName", User);
		prop.put("Password", Password);
		prop.put("LicenseType", "PlatformService");
		prop.put("RedirectToMyselfInCaseOfError", "false");
		prop.put("RememberMe", "false");
		
		try {
			logger.info(Url + DW_LOGIN);
    		URL url = new URL(Url+ DW_LOGIN);
			connection = (HttpURLConnection) url.openConnection();
			setCommonFormProps(connection);
			
			StringJoiner sj = new StringJoiner("&");
			for(Map.Entry<String,String> entry : prop.entrySet())
			    sj.add(URLEncoder.encode(entry.getKey(), "UTF-8") + "=" + URLEncoder.encode(entry.getValue(), "UTF-8"));
			
			byte[] out = sj.toString().getBytes(StandardCharsets.UTF_8);
			
			try(OutputStream os = connection.getOutputStream()) {
			    os.write(out);
			}
			
			int status = connection.getResponseCode();
			logger.info("Status Code:" + status);
		    
			if(status > 299) {
				cookies = "";
			}else {
			    if(connection.getHeaderField("Set-Cookie") != null) {
			    	Map<String, List<String>> headers = connection.getHeaderFields();
			    	
			    	for (String value : headers.get("Set-Cookie")) {
						if(!value.contains("=;")) {
							if(cookies.isBlank()) {
								cookies = value;
							}else {
								cookies += "; " + value;	
							}
						}
					}
			    	logger.info(cookies);
			    }
			}
		} catch (Exception e) {
			e.printStackTrace();
			cookies = "";
		}
		return !cookies.isBlank();
	}
	
	public void Logoff() {
		Get(DW_LOGOFF);
	}
	
	public Organizations GetOrganizations(){
		String orgResponse = Get(DW_ORGS);
		ObjectMapper objectMapper = new ObjectMapper();
	    try {
			return objectMapper.readValue(orgResponse, Organizations.class);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	    return null;
	}
	
	public String Get(String resource) {
    	try {
    		logger.info(Url + resource);
    		URL url = new URL(Url + resource);
			connection = (HttpURLConnection) url.openConnection();
			setCommonGetProps(connection);
			connection.connect();
			
			int status = connection.getResponseCode();
			String line;
			BufferedReader reader;
			StringBuffer response = new StringBuffer();
			
			if(status > 299) {
				reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
				while ((line = reader.readLine()) != null) {
					response.append(line);
				}
				logger.warning("Error (Code:"+status+"):" + response.toString());
				return "";
			}else {
				reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				while ((line = reader.readLine()) != null) {
					response.append(line);
				}
				logger.info("Respuesta:" + response.toString());
				return response.toString();
			}
		} catch (MalformedURLException e) {
			logger.severe("La URL a la API no esta configurada correctamente.");
		} catch (IOException e) {
			logger.warning("Error al acceder al servidor, verifique el acceso o que la configuración de la API sea correcta");
		}
    	
    	return "";
	}

	
	private void setCommonGetProps(HttpURLConnection connection) {
		try {
			connection.setRequestMethod("GET");
			connection.setInstanceFollowRedirects(true);
			connection.setRequestProperty("Accept", "application/json");
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			connection.setRequestProperty("Cookie", cookies);
			connection.setConnectTimeout(1000);
			connection.setReadTimeout(10000);
		} catch (ProtocolException e) {
			e.printStackTrace();
		}
	}
	
	private void setCommonFormProps(HttpURLConnection connection) {
		try {
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
			connection.setRequestProperty("Accept", "application/json");
			connection.setDoOutput(true);
			connection.setConnectTimeout(10000);
			connection.setReadTimeout(10000);
		} catch (ProtocolException e) {
			e.printStackTrace();
		}
	}
	
	private void setCommonDefaultProps(HttpURLConnection connection) {
		try {
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
			connection.setRequestProperty("Accept", "application/json");
			connection.setDoOutput(true);
			connection.setConnectTimeout(10000);
			connection.setReadTimeout(10000);
		} catch (ProtocolException e) {
			e.printStackTrace();
		}
	}
	
	private String DW_LOGIN = "Account/Logon";
	private String DW_LOGOFF = "Account/Logoff";
	private String DW_ORGS = "Organizations";
	private String DW_CABINETS = "FileCabinets?orgid=%s";
//	private String DW_DOCUMENTS = "FileCabinets/%s/Documents";
//	private String DW_DOCUMENT = "FileCabinets/%s/Documents/%d";
//	private String DW_DOCUMENT_UPDATE = "FileCabinets/%s/Documents/%d/Fields";
//	private String DW_DIALOGS = "FileCabinets/%s/Dialogs";
//	private String DW_SEARCH_DIALOG = "FileCabinets/%s/Dialogs/%s?dialogType=Search";
//	private String DW_BUILD_SEARCH = "FileCabinets/%s/Query/DialogExpressionLink?sortOrder=%s&fields=%s&dialogId=%s";
}
