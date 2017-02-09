package udacity.android.booklisting.utility;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

import udacity.android.booklisting.model.Book;

/**
 * This class allows the http request to the Google Books API to
 * be run in a background thread. It takes in the query url and
 * sends it to the QueryUtility.extractBooks method to query the API.
 *
 * @author Joseph Stewart
 * @version 1.0
 */
public class BookLoader extends AsyncTaskLoader<List<Book>> {

    // The user-defined url
    private String url;

    /**
     * Constructor that accepts the calling context and the query url.
     *
     * @param context The Activity creating this BookLoader.
     * @param url The query url defined by the user.
     */
    public BookLoader(Context context, String url) {
        super(context);
        this.url = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Book> loadInBackground() {
        if(url == null) {
            return null;
        }
        return QueryUtility.extractBooks(url, getContext());
    }

}
