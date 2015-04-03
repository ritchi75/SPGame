package JavaFiles.Events;

/**
 * Created by sor on 4/2/2015.
 */
        import java.text.DecimalFormat;

        import android.graphics.Canvas;
        import android.util.Log;
        import android.view.SurfaceHolder;


/**
 * @author impaler
 *
 * The Main thread which contains the game loop. The thread must have access to
 * the surface view and holder to trigger events every game tick.
 */
public class StageThread extends Thread {

    private static final String TAG = StageThread.class.getSimpleName();

    // desired fps
    private final static int     MAX_FPS = 50;
    // maximum number of frames to be skipped
    private final static int    MAX_FRAME_SKIPS = 5;
    // the frame period
    private final static int    FRAME_PERIOD = 1000 / MAX_FPS;

    /* Stuff for stats */
    private DecimalFormat df = new DecimalFormat("0.##");  // 2 dp
    // we'll be reading the stats every second
    private final static int     STAT_INTERVAL = 1000; //ms
    // the average will be calculated by storing
    // the last n FPSs
    private final static int    FPS_HISTORY_NR = 10;
    // the status time counter
    private long statusIntervalTimer    = 0l;
    // number of frames skipped since the game started
    private long totalFramesSkipped            = 0l;
    // number of frames skipped in a store cycle (1 sec)
    private long framesSkippedPerStatCycle     = 0l;

    // number of rendered frames in an interval
    private int frameCountPerStatCycle = 0;
    // private long totalFrameCount = 0l;
    // the last FPS values
    private double     fpsStore[];
    // the number of times the stat has been read
    private long     statsCount = 0;
    // the average FPS since the game started
    private double     averageFps = 0.0;

    // Surface holder that can access the physical surface
    private SurfaceHolder surfaceHolder;
    // The actual view that handles inputs
    // and draws to the surface
    private StageView gameStage;

    // flag to hold game state
    private boolean running;
    public void setRunning(boolean running) {
        this.running = running;
    }

    public StageThread(SurfaceHolder surfaceHolder, StageView gameStage) {
        super();
        this.surfaceHolder = surfaceHolder;
        this.gameStage = gameStage;
    }

    @Override
    public void run() {
        Canvas canvas;
        Log.d(TAG, "Starting game loop");
        // initialise timing elements for stat gathering
        initTimingElements();

        long beginTime;        // the time when the cycle begun
        long timeDiff;        // the time it took for the cycle to execute
        int sleepTime;        // ms to sleep (<0 if we're behind)
        int framesSkipped;    // number of frames being skipped

        sleepTime = 0;

        while (running) {
            canvas = null;
            // try locking the canvas for exclusive pixel editing
            // in the surface
            try {
                canvas = this.surfaceHolder.lockCanvas();
                synchronized (surfaceHolder) {
                    beginTime = System.currentTimeMillis();
                    framesSkipped = 0;    // resetting the frames skipped
                    // update game state
                    this.gameStage.update();
                    // render state to the screen
                    // draws the canvas on the panel
                    this.gameStage.render(canvas);
                    // calculate how long did the cycle take
                    timeDiff = System.currentTimeMillis() - beginTime;
                    // calculate sleep time
                    sleepTime = (int)(FRAME_PERIOD - timeDiff);

                    if (sleepTime > 0) {
                        // if sleepTime > 0 we're OK
                        try {
                            // send the thread to sleep for a short period
                            // very useful for battery saving
                            Thread.sleep(sleepTime);
                        } catch (InterruptedException e) {}
                    }

                    while (sleepTime < 0 && framesSkipped < MAX_FRAME_SKIPS) {
                        // we need to catch up
                        this.gameStage.update(); // update without rendering
                        sleepTime += FRAME_PERIOD;    // add frame period to check if in next frame
                        framesSkipped++;
                    }

                    // for statistics
                    framesSkippedPerStatCycle += framesSkipped;
                    // calling the routine to store the gathered statistics
                    // storeStats();
                }
            } finally {
                // in case of an exception the surface is not left in
                // an inconsistent state
                if (canvas != null) {
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }    // end finally
        }
    }

    private void initTimingElements() {
        // initialise timing elements
        fpsStore = new double[FPS_HISTORY_NR];
        for (int i = 0; i < FPS_HISTORY_NR; i++) {
            fpsStore[i] = 0.0;
        }
        Log.d(TAG + ".initTimingElements()", "Timing elements for stats initialised");
    }

}
