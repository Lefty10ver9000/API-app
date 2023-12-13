package cs1302.api;

import com.google.gson.annotations.SerializedName;

/**
 * Represents a result in a response from the Open Movie Database API. This is
 * used by Gson to create an object from the JSON response body.
*/
public class MovieResult {
    @SerializedName("Released")
    String released;
    // the rest of the result is intentionally omitted since we don't use it
} // MovieResult
