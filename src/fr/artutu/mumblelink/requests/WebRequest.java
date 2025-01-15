package fr.artutu.mumblelink.requests;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import fr.artutu.mumblelink.config.PluginConfig;

public class WebRequest {
	
	public static enum Method { GET, POST, DELETE }
	
	public static class WebResult {
		
		private int statusCode = 500;
		private String body = "";
		public boolean ok = false;
		
		public WebResult(int statusCode, String body) {
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
	
	public static WebResult execute(String relativeURL, Method method) {
		return executeWithoutRelative(PluginConfig.getAdresse() + relativeURL, method, "");
	}
	
	public static WebResult execute(String relativeURL, Method method, String payload) {
		return executeWithoutRelative(PluginConfig.getAdresse() + relativeURL, method, payload);
	}
	
	public static WebResult executeWithoutRelative(String target, Method method, String payload) {
        HttpURLConnection connection = null;
        try {
            URL url = new URL(target);
            connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod(method.name());
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            if (method == Method.POST) {
                connection.setDoOutput(true);
                try (OutputStream os = connection.getOutputStream()) {
                    os.write(payload.getBytes());
                    os.flush();
                }
            }

            int statusCode = connection.getResponseCode();
            BufferedReader reader;
            if (statusCode >= 200 && statusCode < 300) {
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            } else {
                reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
            }

            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            return new WebResult(statusCode, response.toString());

        } catch (Exception e) {
            return new WebResult(500, e.getMessage());
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

}
