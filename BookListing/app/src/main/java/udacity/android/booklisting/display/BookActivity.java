package udacity.android.booklisting.display;

import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import udacity.android.booklisting.R;
import udacity.android.booklisting.model.Book;
import udacity.android.booklisting.utility.BookAdapter;
import udacity.android.booklisting.utility.BookLoader;

/**
 * This is the Activity too display the books. It implement the LoaderCallbacks
 * interface to allow the http request to be conducted in a background thread.
 * The internet connection is checked and a BookLoader is  created.
 *
 * @author Joseph Stewart
 * @version 2.0
 */
public class BookActivity extends AppCompatActivity implements LoaderCallbacks<List<Book>> {

    private static final int BOOK_LOADER_ID = 1;
    private String url;

    private ListView listView;
    private TextView emptyStateTextView;
    private BookAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        // Get the url passed in as an extra
        url = getIntent().getExtras().getString(getString(R.string.intent_url_key));

        listView = (ListView) findViewById(R.id.list);

        // Set view for empty state text view
        emptyStateTextView = (TextView) findViewById(R.id.empty_view);
        listView.setEmptyView(emptyStateTextView);

        // Check the internet connection before created the new BookLoader
        ConnectivityManager cm =
                (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        if(isConnected) {
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(BOOK_LOADER_ID, null, this);
        } else {
            emptyStateTextView.setText(R.string.no_internet_connection);
        }
    }

    /**
     * This method creates a new BookAdapter that allows the
     * list of books to be displayed.
     *
     * @param books The list of books returned as a result of the query.
     */
    private void displayBooks(List<Book> books) {
        if (books != null && !books.isEmpty()) {
            // Set the adapter with the data list
            adapter = new BookAdapter(this, books);
            listView.setAdapter(adapter);
        }

        // Set empty state text to display "No earthquakes found."
        emptyStateTextView.setText(R.string.no_books);
    }

    @Override
    public Loader<List<Book>> onCreateLoader(int id, Bundle args) {
        // Create a new loader for the given URL
        return new BookLoader(this, url);
    }

    @Override
    public void onLoadFinished(Loader<List<Book>> loader, List<Book> books) {
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.loading_spinner);
        progressBar.setVisibility(View.GONE);
        displayBooks(books);
    }

    @Override
    public void onLoaderReset(Loader<List<Book>> loader) {
        loader = null;
    }

}
