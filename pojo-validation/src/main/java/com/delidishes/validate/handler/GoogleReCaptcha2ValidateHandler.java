package com.delidishes.validate.handler;

import com.delidishes.validate.handler.pojo.GoogleReCaptcha2;
import org.apache.log4j.Logger;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.delidishes.validate.ValidateStringUtils.isEmpty;
import static com.delidishes.validate.ValidateStringUtils.isNotEmpty;
import static java.nio.charset.StandardCharsets.UTF_8;

public class GoogleReCaptcha2ValidateHandler implements IValidateHandler<GoogleReCaptcha2> {

	private static final Logger LOG = Logger.getLogger(GoogleReCaptcha2ValidateHandler.class);

	private static final String VERIFY_GOOGLE_URL = "https://www.google.com/recaptcha/api/siteverify";
	private static final String USER_AGENT = "User-Agent";
	private static final String USER_AGENT_NAME = "Mozilla/5.0";
	private static final String POST_METHOD = "POST";
	private static final String POST_PARAM = "secret=%s&response=%s";

	@Override
	public boolean verify(GoogleReCaptcha2 value) {
		if (isEmpty(value.getAppSecret())) {
			throw new IllegalArgumentException("AppSecret is not set in your bean");
		}
		if (isNotEmpty(value.getValue())) {
			try {
				HttpURLConnection connection = (HttpURLConnection) new URL(VERIFY_GOOGLE_URL).openConnection();
				connection.setRequestMethod(POST_METHOD);
				connection.setRequestProperty(USER_AGENT, USER_AGENT_NAME);
				connection.setDoOutput(true);
				fillParams(connection.getOutputStream(), value);
				int responseCode = connection.getResponseCode();
				if(HttpURLConnection.HTTP_OK == responseCode) {
					String response = readResponse(connection.getInputStream());
					LOG.info("VALIDATION: ReCaptcha2 response => " + response);
					return validateResponse(response);
				} else {
					LOG.debug(String.format("VALIDATION: ReCaptcha2 response code is [%d%n]", responseCode));
				}
			} catch (IOException e) {
				LOG.error("VALIDATION: ReCaptcha2 validate error", e);
				return false;
			}
		}
		return false;
	}

	private static boolean validateResponse(String response) {
		return response.contains(String.valueOf(true));
	}

	private static String readResponse(InputStream inputStream) throws IOException {
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
		String line;
		StringBuilder response = new StringBuilder();
		while((line = bufferedReader.readLine()) != null){
			response.append(line);
		}
		bufferedReader.close();
		return response.toString();
	}

	private static void fillParams(OutputStream outputStream, GoogleReCaptcha2 value) throws IOException {
		outputStream.write(String.format(POST_PARAM, value.getAppSecret(), value.getValue()).getBytes(UTF_8));
		outputStream.flush();
		outputStream.close();
	}
}
