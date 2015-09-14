package bjnech.androidpostjson;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class JsonPostHandler extends AsyncTask<String, String, String> {

    @Override
    // String... means undetermined number of parameters
    protected String doInBackground(String... jsonStringsForPost) {
        // Get the first string in json strings for post in this
        String jsonString = jsonStringsForPost[0];
        String responseString = null;
        // Create a new http handler object
        HttpHandler httpHandler = new HttpHandler();
        // Call post json string to server method form http handler
        responseString = httpHandler.postJsonStringToServer(jsonString);
        // Print Response string
        Log.v("json", responseString);

        return responseString;
    }

    // Http handler
    private class HttpHandler {

        // Constructor
        public HttpHandler() {}

        // Post json method
        public String postJsonStringToServer(String jsonString) {
            // Confirmation string from server response
            String confirmString = null;
            // Your url string
            String urlString = "http://yourserverhost/ReceiveAndPrintJson.php";
            try {
                // Create URL object with url
                URL url = new URL(urlString);
                // Open a connection by using the URL object above (No network I/O yet)
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                // Do not get response code before any set, otherwise, the connection will be duplicated
                // Set up the connection
                // Set up request method to "POST"
                connection.setRequestMethod("POST");
                // Set up do output, if output stream is needed then set true, otherwise, set false
                connection.setDoOutput(true);
                // Set up do input, if input stream is needed then set true, otherwise, set false
                connection.setDoInput(true);
                // Set up caches usage
                connection.setUseCaches(false);
                // Set up request property
                connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                // Connect to the server (Network I/O starts)
                connection.connect();
                // Set up output steam
                // Output stream is used for "POST" content
                OutputStream outputStream = connection.getOutputStream();
                // Set up output stream writer
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, "UTF-8");
                // Write into the writer ("json=" must same as isset($_POST["json"]) in php)
                outputStreamWriter.write("json="+jsonString);
                // Flush to output stream
                outputStreamWriter.flush();
                // Close writer
                outputStreamWriter.close();
                // Close output stream
                outputStream.close();
                // Get response
                // Create a BufferedInputStream for incoming data
                InputStream in = new BufferedInputStream(connection.getInputStream());
                // Read InputStream
                BufferedReader br = new BufferedReader(new InputStreamReader(in));
                // Create a StringBuilder
                StringBuilder stringbu = new StringBuilder();
                // Each character
                int c;
                // Read each character form BufferedReader to StringBuilder
                while (true){
                    c = br.read();
                    // if br.read() reaches the end, then break
                    if (c == -1){
                        break;
                    }
                    // Append every character in StringBuilder
                    stringbu.append((char)c);
                }
                // Get confirm string
                confirmString = stringbu.toString();
                // Destroy connection
                connection.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return confirmString;
        }

    }

}
