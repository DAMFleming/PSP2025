package unidad4.ejemploclienteapirest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.stream.Collectors;

import com.google.gson.Gson;

public class Main {

	public static void main(String[] args) throws URISyntaxException, IOException {
		URL url = new URI("https://dogapi.dog/api/v1/facts?number=5").toURL();
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("GET");
		int responseCode = con.getResponseCode();
		if (responseCode == HttpURLConnection.HTTP_OK) {
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			Gson gson = new Gson();
			String json = in.lines().collect(Collectors.joining());
			Resultado resultado = gson.fromJson(json, Resultado.class);
			con.disconnect();
			for (String s: resultado.facts)
				System.out.println(s);
		}
		else
			System.out.println(responseCode);
	}

}
