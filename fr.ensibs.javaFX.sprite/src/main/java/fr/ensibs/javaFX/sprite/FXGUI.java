package fr.ensibs.javaFX.sprite;

import fr.ensibs.conf.Configuration;
import fr.ensibs.fs.FileSystem;
import fr.ensibs.javaFX.fs.FXFileSystem;
import fr.ensibs.javaFX.sync.FXSchedulerFactory;
import fr.ensibs.sync.Scheduler;
import fr.ensibs.sync.SchedulerFactory;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * The JavaFX GUI system: a JavaFX Application composed of a Pane with a main
 * zone to display images and a status bar for user inputs and to display the
 * time
 *
 * @author Pascale Launay
 * @version 4
 */
public class FXGUI extends Application {

    // the program constants
    private static final String DEFAULT_CONF = "conf/default.properties";
    private static final String APP_TITLE = "Sprites";

    private Canvas canvas;                    // graphic object
    private FileSystem fileSystem;            // the file system
    private Configuration configuration;      // the configuration properties

    //---------------------------------------------------------------
    // Main
    //---------------------------------------------------------------
    /**
     * Launch the JavaFX application
     *
     * @param args NONE
     */
    public static void main(String[] args) {

        FXGUI.launch();
    }

    //---------------------------------------------------------------
    // Start
    //---------------------------------------------------------------
    /**
     * Make the application stage's components and display it
     *
     * @param stage the stage to be displayed
     */
    @Override
    public void start(Stage stage) {

        // initialize the stage title
        stage.setTitle(APP_TITLE);

        // display the stage
        Parent root = makeRootPane();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        // trial: show the background image
        FXTrials trials = new FXTrials(fileSystem, canvas, configuration);
        trials.startTrial();
    }

    //---------------------------------------------------------------
    // Private methods
    //---------------------------------------------------------------
    /**
     * Make the root pane composed of a main {@link Canvas} and a
     * {@link StatusBar}
     *
     * @return the root pane
     */
    private Parent makeRootPane() {

        // get configuration properties
        this.fileSystem = new FXFileSystem();
        this.configuration = new Configuration(fileSystem, DEFAULT_CONF);
        int width = configuration.getInt("width");
        int height = configuration.getInt("height");
        long duration = configuration.getLong("duration");
        long period = configuration.getLong("period");

        // make the main center pane
        Pane moviePane = new Pane();
        moviePane.setPrefSize(width, height);
        this.canvas = new Canvas(width, height);
        moviePane.getChildren().add(canvas);

        // make the bottom status bar
        SchedulerFactory schedulerFactory = new FXSchedulerFactory();
        Scheduler scheduler = schedulerFactory.makeScheduler(period);
        StatusBar statusBar = new StatusBar(duration, scheduler);

        // make the parent root pane
        BorderPane rootPane = new BorderPane();
        rootPane.setCenter(moviePane);
        rootPane.setBottom(statusBar);

        return rootPane;
    }
}
