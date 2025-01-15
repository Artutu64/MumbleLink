package fr.artutu.mumblelink.requests;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import fr.artutu.mumblelink.config.PluginData;
import fr.artutu.mumblelink.requests.WebRequest.Method;
import fr.artutu.mumblelink.requests.WebRequest.WebResult;

public class CheckBackend {
	
	public static String SHORTLINK = "http://0.0.0.0:8000/";

	public static void execute() {
		WebResult result = WebRequest.execute("servers/redirecturl", Method.GET);
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
