package fr.artutu.mumblelink.requests;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.logging.Level;

import javax.naming.SizeLimitExceededException;

import fr.artutu.mumblelink.MumbleLink;
import fr.artutu.mumblelink.config.PluginConfig;


public class TCPRequest {
	
	public static enum Method { GET, POST, DELETE }
	
	public static class TCPResult {
		
		private int statusCode = 500;
		private String body = "";
		public boolean ok = false;
		
		public TCPResult(int statusCode, String body) {
			this.statusCode = statusCode;
			this.body = body;
			this.ok = statusCode >= 200 && statusCode < 300;
		}
		
		public int getStatusCode() {
			return statusCode;
		}
		
		public String getBody() {
			return body;
		}
		
	}
	
	public static TCPResult execute(String relativeURL, Method method) {
		
		if(!relativeURL.startsWith("/")) {
			relativeURL = "/" + relativeURL;
		}
		
		if(PluginConfig.AES_UTILS == null) {
			return new TCPResult(500, "Erreur dans la configuration du plugin !");
		}
		
		
		long timeStamp = System.currentTimeMillis() / 1000;
		
		String message = method.toString() + "|" + timeStamp + "|" + relativeURL;
		
		String encrypted;
		
		try {
			encrypted = PluginConfig.AES_UTILS.encrypt(message);
		} catch (Exception e) {
			e.printStackTrace();
			return new TCPResult(500, "Erreur lors du chiffrement de la requête.");
		}
		
		try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(PluginConfig.BACKEND_IP, 20821));

            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println(encrypted);

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            try {
            	
            	StringBuilder str = new StringBuilder();
            	
            	String line;
            	
            	while ((line = in.readLine()) != null) {
            		str.append(line);
            	}
            	
                String response = str.toString();
                
                if (response != null) {
                	
                    String value = PluginConfig.AES_UTILS.decrypt(response);
                    
                    String[] splitted = value.split("\\|");
                    
                    if(splitted.length != 2) {
                    	try {
                    		socket.close();
                    	} catch(Exception _e) {}
                    	throw new SizeLimitExceededException("La taille de la réponse n'est pas correcte !");
                    }
                    
                    int code = Integer.parseInt(splitted[0]);
                    String body = splitted[1];
                    
                    try {
                		socket.close();
                	} catch(Exception _e) {}
                    
                    return new TCPResult(code, body);
                    
                    
                } else {
                	try {
                		socket.close();
                	} catch(Exception _e) {}
                    throw new NullPointerException("Réponse vide !");
                }
            } catch (Exception e) {
            	try {
            		socket.close();
            	} catch(Exception _e) {}
            	if(!(e instanceof IllegalArgumentException)) {
            		MumbleLink.getInstance().getLogger().log(Level.WARNING, "[TCPRequest:108] Il y a une erreur dans la réponse envoyée par le backend: " + e.getMessage());
            	}
            	return new TCPResult(500, e.getMessage());
            }

        } catch (Exception e) {
        	MumbleLink.getInstance().getLogger().log(Level.WARNING, "[TCPRequest:114] Il y a une erreur dans la réponse envoyée par le backend: " + e.getMessage());
        	return new TCPResult(500, e.getMessage());
        }

    }

}
