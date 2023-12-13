package cs1302.api;

import com.google.gson.annotations.SerializedName;

/**
 * Represents a result in a response from the APOD API. This is
 * used by Gson to create an object from the JSON response body.
 */
public class ApodResult {
    String url;
    // the rest of the result is intentionally omitted since we don't use it
} // ApodResult
