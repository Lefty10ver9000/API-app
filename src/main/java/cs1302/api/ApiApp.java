package cs1302.api;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.net.http.HttpClient;
import java.net.URL;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.net.http.HttpRequest;
import java.io.FileInputStream;
import java.util.Properties;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import java.time.Month;

import com.google.gson.annotations.SerializedName;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * REPLACE WITH NON-SHOUTING DESCRIPTION OF YOUR APP.
 */
public class ApiApp extends Application {
    /** HTTP client. */
    public static final HttpClient HTTP_CLIENT = HttpClient.newBuilder()
        .version(HttpClient.Version.HTTP_2)           // uses HTTP protocol version 2 where possible
        .followRedirects(HttpClient.Redirect.NORMAL)  // always redirects, except from HTTPS to HTTP
        .build();                                     // builds and returns a HttpClient object

    /** Google {@code Gson} object for parsing JSON-formatted strings. */
    public static Gson GSON = new GsonBuilder()
        .setPrettyPrinting()                          // enable nice output when printing
        .create();                                    // builds and returns a Gson object


    public static final String MOVIE_API = "http://www.omdbapi.com/?apikey=3498e0d5&";
    public static final String APOD_API = "https://api.nasa.gov/planetary/apod?api_key=DEMO_KEY&";

    Stage stage;
    Scene scene;
    VBox vbox;

    HBox headerLayer;

    Text search;
    TextField searchField;
    Button find;

    Text variable;
    Text apodText;
    ImageView apod;

    String query;
    String date;
    String formattedDate;
    String url;

    String[] months = {"January","February","March",
        "April","May","June","July",
        "August","September","October","November","December"};

    private static final String DEFAULT_IMG = "resources/readme-banner.png";
    private static final Image DEF_IMG = new Image("file:" + DEFAULT_IMG);


    /**
     * Constructs an {@code ApiApp} object. This default (i.e., no argument)
     * constructor is executed in Step 2 of the JavaFX Application Life-Cycle.
     */
    public ApiApp() {
        vbox = new VBox();
        headerLayer = new HBox(8);
        search = new Text("Search:");
        searchField = new TextField("Barbie");
        find = new Button("Get Release Date!");
        variable = new Text("Please enter a movie with a release date after June 16, 1995");
        apodText = new Text("And you'll get the Astronomy Picture that NASA released on that day!");
        apod = new ImageView(DEF_IMG);
        query = "";
        date = "";
        url = "";
    } // ApiApp

    /** {@inheritDoc} */
    @Override
    public void init() {
        vbox.getChildren().addAll(headerLayer, variable, apodText, apod);
        headerLayer.getChildren().addAll(search, searchField, find);
        HBox.setHgrow(search, Priority.ALWAYS);
        find.setOnAction(event -> this.load());
    }

    /** {@inheritDoc} */
    @Override
    public void start(Stage stage) {
        this.stage = stage;
        scene = new Scene(vbox);
        stage.setTitle("ApiApp!");
        stage.setScene(scene);
        stage.setOnCloseRequest(event -> Platform.exit());
        stage.sizeToScene();
        stage.show();

    } // start

    /** Method pulled from Example 3 of HTTP Reading.
        @param t term to be searched
    */
    public void movieHandler(String t) {
        try {
            // form URI
            String t1 = URLEncoder.encode(t, StandardCharsets.UTF_8);
            query = String.format("t=%s", t1);
            String uri = MOVIE_API + query;
            // build request
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .build();
            // send request / receive response in the form of a String
            HttpResponse<String> response = HTTP_CLIENT
                .send(request, BodyHandlers.ofString());
            // ensure the request is okay
            if (response.statusCode() != 200) {
                throw new IOException(response.toString());
            } // if
              // get request body (the content we requested)
            String jsonString = response.body();
            // parse the JSON-formatted string using GSON
            MovieResult movieResponse = GSON
                .fromJson(jsonString, MovieResult.class);
            storeDate(movieResponse);
        } catch (IOException | InterruptedException e) {
            // either:
            // 1. an I/O error occurred when sending or receiving;
            // 2. the operation was interrupted; or
            // 3. the Image class could not load the image.
            System.err.println(e);
            e.printStackTrace();
        } // try
    }

    /** Method pulled from Example 3 of HTTP Reading.
        @param date term to be searched
    */
    public void apodHandler(String date) {
        try {
            // form URI
            String date1 = URLEncoder.encode(date, StandardCharsets.UTF_8);
            query = String.format("date=%s", date1);
            String uri = APOD_API + query;
            // build request
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .build();
            // send request / receive response in the form of a String
            HttpResponse<String> response = HTTP_CLIENT
                .send(request, BodyHandlers.ofString());
            // ensure the request is okay
            if (response.statusCode() != 200) {
                throw new IOException(response.toString());
            } // if
            // get request body (the content we requested)
            String jsonString = response.body();
            // parse the JSON-formatted string using GSON
            ApodResult apodResponse = GSON
                .fromJson(jsonString, ApodResult.class);
            storeImageLink(apodResponse);
        } catch (IOException | InterruptedException e) {
            // either:
            // 1. an I/O error occurred when sending or receiving;
            // 2. the operation was interrupted; or
            // 3. the Image class could not load the image.
            System.err.println(e);
            e.printStackTrace();
        } // try
    }

    /**
     * Stores the date from the Open Movie Database search result.
     * @param movie the response that holds the result.
     */
    private void storeDate(MovieResult movie) {
        date = movie.released;
    }

    /**
     * Stores the image link from the APOD search result.
     * @param apod the response that holds the result.
     */
    private void storeImageLink(ApodResult apod) {
        url = apod.url;
    }

    /**
     * Aquires, saves, and loads the release date into the app.
     */
    private void load() {
        try {
            this.movieHandler(searchField.getText());
            if (date == null) {
                variable.setText("Try again");
                throw new IllegalArgumentException(
                    "The movie you entered was not found in the database");
            } else if (date.equals("N/A")) {
                variable.setText("Try again");
                throw new IllegalArgumentException(
                     "The movie you entered does not have a confirmed release date");
            } else if (formatDate(date)) {
                variable.setText(searchField.getText() + " was released on " + date);
                this.apodHandler(date);
                apod.setImage(this.fetchImage(url));
            } else {
                throw new IllegalArgumentException(
                    "This movie's release date is before June 16, 1995");
            }
        } catch (IllegalArgumentException e) {
            alertError(e);
        }
    }

    /**
     * Turns the url into an image.
     *
     * @param url the link to the image.
     * @return the image the url links to.
     */
    private Image fetchImage(String url) {
        try {
            Image newImg = new Image(url, 720, 1280, true, false);
            if (newImg.isError()) {
                throw new IOException(newImg.getException());
            } // if
            return newImg;
        } catch (IOException | IllegalArgumentException e) {
            alertError(e);
        } // try
        return null;
    } // loadImage

    /**
     * Show a modal error alert based on {@code cause}.
     * @param cause a {@link java.lang.Throwable Throwable} that caused the alert
     */
    public void alertError(Throwable cause) {
        TextArea text = new TextArea(cause.toString());
        text.setEditable(false);
        Alert alert = new Alert(AlertType.ERROR);
        alert.getDialogPane().setContent(text);
        alert.setResizable(true);
        alert.showAndWait();
    } // alertError


    /**
     * Formats a date into a YYYY-MM-DD format.
     * @param d the date that is being formatted.
     * @return a boolean to determine if the date is valid for the
     * APOD search query.
     */
    private boolean formatDate(String d) {
        boolean format = true;
        String day = d.substring(0, 2);
        String year = d.substring(d.lastIndexOf(" ") + 1);
        String month = d.substring(d.indexOf(" ") + 1, d.lastIndexOf(" "));
        for (String i : months) {
            if (i.substring(0,3).equalsIgnoreCase(month)) {
                month = i;
                break;
            }
        }
        Month m = Month.valueOf(month.toUpperCase());
        int mo = m.getValue();
        int y = Integer.parseInt(year);
        int da = Integer.parseInt(day);
        if (y < 1995) {
            format = false;
        } else if (y == 1995) {
            if (mo < 6) {
                format = false;
            } else if (mo == 6) {
                if (da < 16) {
                    format = false;
                }
            }
        }
        if (mo < 10) {
            month = "0" + month.valueOf(mo);
        } else {
            month = month.valueOf(mo);
        }
        date = year + "-" + month + "-" + day;
        return format;
    }

} // ApiApp
