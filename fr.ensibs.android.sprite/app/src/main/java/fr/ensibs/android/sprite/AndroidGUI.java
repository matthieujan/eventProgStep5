package fr.ensibs.android.sprite;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import fr.ensibs.android.fs.AndroidFileSystemFactory;
import fr.ensibs.android.sync.AndroidSchedulerFactory;
import fr.ensibs.conf.Configuration;
import fr.ensibs.fs.FileSystem;
import fr.ensibs.fs.FileSystemFactory;
import fr.ensibs.sync.Scheduler;
import fr.ensibs.sync.SchedulerFactory;

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

        // get {@link Scheduler} instance
        SchedulerFactory schedulerFactory = new AndroidSchedulerFactory(new Handler());
        Scheduler scheduler = schedulerFactory.makeScheduler(1000);

        // initialize the activity components
        this.imageView = (ImageView) findViewById(R.id.image);
        this.statusBar = new StatusBar(this, configuration.getInt("duration"), scheduler);

        // start trials: draw a background image
        AndroidTrials trials = new AndroidTrials(this, imageView, fs, configuration);
        trials.startTrial();
    }
}
