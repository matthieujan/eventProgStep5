package fr.ensibs.android.sprite;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import fr.ensibs.json.JsonReader;
import fr.ensibs.sprite.Movie;
import fr.ensibs.sprite.json.MovieJsonConverter;
import android.content.Context;

import java.io.InputStream;
import java.nio.file.FileSystem;

import fr.ensibs.android.fs.AndroidFileSystemFactory;
import fr.ensibs.android.sync.AndroidSchedulerFactory;


import fr.ensibs.engines.SpriteEngine;
import fr.ensibs.engines.TimeObservable;
import fr.ensibs.engines.UserEngine;

import fr.ensibs.conf.Configuration;
import fr.ensibs.fs.FileSystem;
import fr.ensibs.fs.FileSystemFactory;
import fr.ensibs.graphic.Image;
import fr.ensibs.graphic.ImagesLoader;
import fr.ensibs.sync.Scheduler;
import fr.ensibs.sync.SchedulerFactory;

import java.util.Map;
import java.util.zip.ZipInputStream;
/**
 * The main activity for the android Sprite app.
 *
 * @author Pascale Launay
 * @version 4
 */
public class AndroidGUI extends Activity {

    private static final String PROPERTIES = "sprite";

    private ImageView imageView;   // the center view that contains the image
    private StatusBar statusBar;   // the bottom status bar
    private Canvas canvas;                    // graphic object
    private FileSystem fileSystem;            // the file system
    private Configuration configuration;      // the configuration properties
    private Movie movie;
    private SpriteEngine spriteEngine;
    private UserEngine userEngine;
    private AndroidGraphicEngine androidGraphicEngine;

    //---------------------------------------------------------------
    // Initialization
    //---------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_android_gui);

        // get {@link FileSystem} and {@link Configuration} instances
        FileSystemFactory fsFactory = new AndroidFileSystemFactory(this);
        FileSystem fs = fsFactory.makeFileSystem();
        Configuration configuration = new Configuration(fs, PROPERTIES);

        Map<String,Image> images= null;

        ImagesLoader loader = new ImagesLoader(new AndroidImageFactory());

        try (
                FileInputStream in = this.openFileInput(configuration.get("images"))) {
            if (in != null) {
                images = loader.loadImages(new ZipInputStream(in));
            }
        } catch (FileNotFoundException  e) {
            e.printStackTrace();
        }

        JsonReader<Movie> jsonReader = new JsonReader(new MovieJsonConverter(images));
        try {
            this.movie = jsonReader.readJson(fileSystem.getInputStream("movie.json"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        // get {@link Scheduler} instance
        SchedulerFactory schedulerFactory = new AndroidSchedulerFactory(new Handler());
        Scheduler scheduler = schedulerFactory.makeScheduler(1000);

        // initialize the activity components
        this.imageView = (ImageView) findViewById(R.id.image);
        this.statusBar = new StatusBar(this, configuration.getInt("duration"), scheduler);

        // start trials: draw a background image
        AndroidTrials trials = new AndroidTrials(this, imageView, fs, configuration);
        trials.startTrial();

        spriteEngine.start();
        userEngine.start();
        androidGraphicEngine.start();
    }
}
