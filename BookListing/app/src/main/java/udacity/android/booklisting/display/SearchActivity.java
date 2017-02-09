package udacity.android.booklisting.display;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import udacity.android.booklisting.R;

/**
 * This is the activity for the search function. It displays an EditText
 * that allows the user to enter a search topic and a button that gets
 * the entered text and uses it to build a query url for the Google Books
 * API.
 *
 * @author Joseph Stewart
 * @version 1.1
 */
public class SearchActivity extends AppCompatActivity {

    private EditText searchText;

    /**
     * Called when the Activity is created. This method gets the
     * entered text and passed the built url to the BookActivity.
     *
     * @param savedInstanceState The saved instance state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchText = (EditText) findViewById(R.id.search_text);
        Button searchButton = (Button) findViewById(R.id.search_button);

        // Set action listener for pressing the enter key
        searchText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(keyCode == KeyEvent.KEYCODE_ENTER) {
                    startQuery();
                    return true;
                } else {
                    return false;
                }
            }
        });

        // Set action listener for clicking the search button
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startQuery();
            }
        });
    }

    /**
     * This method obtains the search text and uses it to build a url
     * string to be sent to the BookActivity.
     */
    private void startQuery() {
        // Get text entered by user
        String queryText = searchText.getText().toString().trim();

        if(!queryText.equals("")) {
            // Encode the queryText
            try {
                queryText = URLEncoder.encode(queryText, getString(R.string.encoding_scheme));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            // Build the query url
            String url = "https://www.googleapis.com/books/v1/volumes?q=" + queryText + "&maxResults=40";

            // Go to the BookActivity to display the results
            Intent intent = new Intent(SearchActivity.this, BookActivity.class);
            intent.putExtra("URL", url);
            startActivity(intent);
        } else {
            Toast.makeText(getApplicationContext(), getString(R.string.empty_search_term), Toast.LENGTH_LONG).show();
        }
    }

}
