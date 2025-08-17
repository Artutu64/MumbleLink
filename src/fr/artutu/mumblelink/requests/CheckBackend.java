package fr.artutu.mumblelink.requests;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import fr.artutu.mumblelink.config.PluginData;
import fr.artutu.mumblelink.requests.TCPRequest.Method;
import fr.artutu.mumblelink.requests.TCPRequest.TCPResult;

public class CheckBackend {
	
	public static String SHORTLINK = "http://0.0.0.0:8000/";

	public static void execute() {
		TCPResult result = TCPRequest.execute("servers/redirecturl", Method.GET);
		PluginData.backendOnline = result.ok;
		if(result.ok) {
			try {
				JsonObject jsonObject = (new JsonParser()).parse(result.getBody()).getAsJsonObject();
				String link = jsonObject.get("url").getAsString();
				SHORTLINK = link;
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}

}
