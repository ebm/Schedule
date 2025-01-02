import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.zip.GZIPInputStream;

public class RefreshResults {
    public static String urlString;
    public static String filename;

    // Initializer
    public static boolean refresh(String link, String file) {
        urlString = link;
        filename = file;

        if (writeStringToFile(readUrl(), filename) == false) return false;
        return true;
    }

    // Reads string from URL
    private static String readUrl() {
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("Accept-Encoding", "gzip");
            InputStreamReader in = new InputStreamReader (new GZIPInputStream(connection.getInputStream()));
            BufferedReader reader = new BufferedReader(in);
            StringBuilder json = new StringBuilder();
            int c;
            while ((c = reader.read()) != -1) {
                json.append((char) c);
            }
            //System.out.println("URL Received Success!");
            return json.toString();
        } catch (IOException E) {
            //E.printStackTrace();
            return null;
        }
    }
    
    // Writes a string to a File
    public static boolean writeStringToFile(String string, String filename) {
        try {
            if (string == null) return false;
            FileWriter myWriter = new FileWriter(filename);
            myWriter.write(string);
            myWriter.close();
            //System.out.println("File write success!");
            return true;
        }
        catch (IOException E) {
            E.printStackTrace();
            return false;
        }
    }
}
