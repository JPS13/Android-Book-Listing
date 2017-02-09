package udacity.android.booklisting.model;

import java.util.List;

/**
 * This class represents a Book from the Google Books API. It
 * contains data for the title, list of authors, and a url string
 * for a description.
 *
 * @author Joseph Stewart
 * @version 1.2
 *
 */
public class Book {

    private String title;
    private List<String> authors;
    private String descriptionUrl;

    /** Constructors */

    public Book() {

    }

    public Book(String title, List<String> authors) {
        this.title = title;
        this.authors = authors;
    }

    public Book(String title, List<String> authors, String descriptionUrl) {
        this.title = title;
        this.authors = authors;
        this.descriptionUrl = descriptionUrl;
    }

    /**
     * This method returns the Book's title.
     *
     * @return The title.
     */
    public String getTitle() {
        return title;
    }

    /**
     * This method sets the title for the Book.
     *
     * @param title The Book's title.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * This method returns the list of authors for the Book.
     *
     * @return The list of authors.
     */
    public List<String> getAuthors() {
        return authors;
    }

    /**
     * This method sets the Book's author(s).
     *
     * @param authors The list of authors.
     */
    public void setAuthors(List<String> authors) {
        this.authors = authors;
    }

    /**
     * This method returns the description url.
     *
     * @return The url for the Book's description.
     */
    public String getDescriptionUrl() {
        return descriptionUrl;
    }

    /**
     * This method sets the url for the book description.
     *
     * @param descriptionUrl The description url.
     */
    public void setDescriptionUrl(String descriptionUrl) {
        this.descriptionUrl = descriptionUrl;
    }

    /**
     * This method provides a String representation fo this Book.
     *
     * @return The String representation.
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("Title: " + title + " Author: ");

        if(authors != null && authors.size() > 0) {
            for (String author : authors) {
                builder.append(author + " ");
            }
        }

        builder.append(" URL: " + descriptionUrl);

        return builder.toString().trim();
    }
}
