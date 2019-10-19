import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class TestFunc {


    private static final String base_url = "http://ws.audioscrobbler.com/2.0/";

    private static String format = "json";

    public static void main(String[] args) throws IOException {
        String methodAndParams = "chart.gettoptracks";
        String api_key = "200c24d888fc59ed3d619de2b9e2cfc3";
        String urlString = String.format("%s?method=%s&api_key=%s&format=%s", base_url, methodAndParams, api_key, format);
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        String readLine = null;

        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK){
            BufferedReader input = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuffer response = new StringBuffer();
            while((readLine = input.readLine()) != null){
                response.append(readLine);
            } input.close();
            System.out.println("JSON String Result\n" + response.toString());
        } else {
            System.out.println("You f'ed up");
        }

    }
}
