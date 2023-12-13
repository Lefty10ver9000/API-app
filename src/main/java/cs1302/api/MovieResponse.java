package cs1302.api;

import com.google.gson.annotations.SerializedName;

/**
 * Represents a response from the Open Movie Database API. This is used by Gson to
 * create an object from the JSON response body. This class is provided with
 * project's starter code, and the instance variables are intentionally set
 * to package private visibility.
 */
public class MovieResponse {
    MovieResult results;
} // MovieResponse
