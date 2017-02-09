# Android-Book-Listing

  This project was completed as part of the Udacity Android Basics Nanodegree. The user is prompted to enter a search term which is used to build a query for the Google Books API. A JSON response holding a collection of books returned is displayed in a list view.
  The ViewHolder pattern is used for efficiency to avoid many avoidable calls to the findViewById method and the network communications take place on a background thread to avoid slowing the UI thread.
