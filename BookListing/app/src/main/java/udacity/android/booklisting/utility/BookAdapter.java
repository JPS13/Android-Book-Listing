package udacity.android.booklisting.utility;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import udacity.android.booklisting.R;
import udacity.android.booklisting.model.Book;

/**
 * This is a custom extension of the ArrayAdapter which allows a list of
 * books from the Google Books API to be displayed in a list view.
 *
 * @author Joseph Stewart
 * @version 1.2
 */
public class BookAdapter extends ArrayAdapter<Book> {

    /**
     * Inner class to implement the ViewHolder pattern to
     * maintain references to the views to be displayed.
     */
    private static class ViewHolder {
        TextView titleTextView;
        TextView authorsTextView;
        ImageView internetIcon;
    }

    /** Constructor */
    public BookAdapter(Activity context, List<Book> books) {
        super(context, 0, books);
    }

    /**
     * This method sets up the view for the current Book.
     *
     * @param position The current position in the list.
     * @param convertView The current view being set up.
     * @param parent The parent view.
     * @return The completed view.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final Book currentBook = getItem(position);
        ViewHolder holder;

        // Check if the existing view is being reused, otherwise inflate the view
        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);

            holder = new ViewHolder();
            holder.titleTextView = (TextView) convertView.findViewById(R.id.title_text_view);
            holder.authorsTextView = (TextView) convertView.findViewById(R.id.author_text_view);
            holder.internetIcon = (ImageView) convertView.findViewById(R.id.internet_icon);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // Set the view data
        holder.titleTextView.setText(currentBook.getTitle());

        if(currentBook.getAuthors() != null && currentBook.getAuthors().size() > 0) {
            StringBuilder builder = new StringBuilder();

            for(String author: currentBook.getAuthors()) {
                builder.append(author).append(" ");
            }

            holder.authorsTextView.setText(builder.toString().trim());
        } else {
            holder.authorsTextView.setText(R.string.no_authors);
        }

        // Navigate to the website of the book description when clicked
        if(currentBook.getDescriptionUrl() != null) {

            holder.internetIcon.setImageResource(R.drawable.ic_open_in_new_white_24dp);

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Uri uri = Uri.parse(currentBook.getDescriptionUrl());
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    getContext().startActivity(intent);
                }
            });
        }

        return convertView;
    }

}
