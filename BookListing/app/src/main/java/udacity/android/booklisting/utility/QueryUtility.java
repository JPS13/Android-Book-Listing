package udacity.android.booklisting.utility;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import udacity.android.booklisting.model.Book;
import udacity.android.booklisting.R;

/**
 * This is a utility class to accommodate http requests for the Google
 * Books API. The extractBooks method receives the query url and the
 * calling Activity and returns a list of Books retrieved as a result
 * of the request.
 *
 * @author Joseph Stewart
 * @version 1.2
 */
public final class QueryUtility {

    private static final String LOG_TAG = QueryUtility.class.getSimpleName();

    /**
     * Private constructor to avoid instantiation.
     */
    private QueryUtility() {
        throw new AssertionError("QueryUtility class cannot be instantiated.");
    }

    /**
     * Return a list of Books that has been built up from
     * parsing a JSON response.
     */
    public static List<Book> extractBooks(String urlString, Context context) {

        // Create an empty ArrayList to hold books
        ArrayList<Book> books = new ArrayList<>();

        try {
            // Create URL object from string
            URL urlQuery = createUrl(urlString);

            // Perform HTTP request to the URL and receive a JSON response back
            String jsonResponse = null;
            try {
                jsonResponse = makeHttpRequest(urlQuery);
            } catch (IOException e) {
                Log.e(LOG_TAG, "Error closing input stream", e);
            }

            if(!jsonResponse.equals("")) {

                JSONObject response = new JSONObject(jsonResponse);

                JSONArray itemsArray = response.getJSONArray(context.getString(R.string.items));

                for(int i = 0; i < itemsArray.length(); i++) {
                    // Get the properties object from the earthquake object
                    JSONObject volumeInfo = itemsArray.getJSONObject(i).getJSONObject(context.getString(R.string.volumeInfo));

                    String title = volumeInfo.getString(context.getString(R.string.title));
                    List<String> authors = new ArrayList<>();

                    // Some books don's have author info so check first
                    if(volumeInfo.has(context.getString(R.string.authors))) {
                        JSONArray authorArray = volumeInfo.getJSONArray(context.getString(R.string.authors));

                        if(authorArray != null && authorArray.length() > 0) {
                            for(int index = 0; index < authorArray.length(); index++) {
                                authors.add((String)authorArray.get(index));
                            }
                        }
                    }

                    String descriptionUrl = volumeInfo.getString("previewLink");

                    Book book = new Book(title, authors, descriptionUrl);
                    books.add(book);
            }

            }
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Problem parsing the JSON results", e);
        }

        // Return the list of books
        return books;
    }

    /**
     * Returns new URL object from the given string URL.
     */
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error creating URL ", e);
        }
        return url;
    }

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    private static String makeHttpRequest(URL url)  throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * Convert the InputStream into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

}
