package JavaFiles.Events;
/**
 *
 */

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import seniorproject.game.R;

/**
 * @author impaler
 * This is the main surface that handles the ontouch events and draws
 * the image to the screen.
 */
public class StageView extends SurfaceView implements
        SurfaceHolder.Callback {

    private static final String TAG = StageView.class.getSimpleName();

    private StageThread thread;
    private Sprite char1;
    private Sprite char2;
    private Sprite char3;
    private Sprite char4;
    private Sprite boss1;

    public StageView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        // adding the callback (this) to the surface holder to intercept events
        getHolder().addCallback(this);

        // create character 1 and load bitmap
        char1 = new Sprite(
                BitmapFactory.decodeResource(getResources(), R.drawable.knight)
                , 30, 400    // initial position
                , 60, 90    // width and height of sprite
                , 2, 2);    // FPS and number of frames in the animation

        // create character 2 and load bitmap
        char2 = new Sprite(
                BitmapFactory.decodeResource(getResources(), R.drawable.priest)
                , 110, 400    // initial position
                , 60, 90    // width and height of sprite
                , 1, 2);    // FPS and number of frames in the animation

        // create character 3 and load bitmap
        char3 = new Sprite(
                BitmapFactory.decodeResource(getResources(), R.drawable.wizard)
                , 190, 400    // initial position
                , 60, 90    // width and height of sprite
                , 5, 5);    // FPS and number of frames in the animation

        // create character 4 and load bitmap
        char4 = new Sprite(
                BitmapFactory.decodeResource(getResources(), R.drawable.rogue)
                , 270, 400    // initial position
                , 60, 90    // width and height of sprite
                , 2, 2);    // FPS and number of frames in the animation

        // create Boss character and load bitmap
        boss1 = new Sprite(
                BitmapFactory.decodeResource(getResources(), R.drawable.guard_boss)
                , 725, 400    // initial position
                , 64, 128    // width and height of sprite
                , 2, 2);    // FPS and number of frames in the animation


        // create the game loop thread
        thread = new StageThread(getHolder(), this);

        // make the GamePanel focusable so it can handle events
        setFocusable(true);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        // at this point the surface is created and
        // we can safely start the game loop
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.d(TAG, "Surface is being destroyed");
        // tell the thread to shut down and wait for it to finish
        // this is a clean shutdown
        boolean retry = true;
        while (retry) {
            try {
                thread.join();
                retry = false;
            } catch (InterruptedException e) {
                // try again shutting down the thread
            }
        }
        Log.d(TAG, "Thread was shut down cleanly");
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            // handle touch
        }
        return true;
    }

    public void render(Canvas canvas) {
        canvas.drawColor(Color.BLACK);
        char1.draw(canvas);
        char2.draw(canvas);
        char3.draw(canvas);
        char4.draw(canvas);
        boss1.draw(canvas);
    }

    /**
     * This is the game update method. It iterates through all the objects
     * and calls their update method if they have one or calls specific
     * engine's update method.
     */
    public void update() {
        char1.update(System.currentTimeMillis());
        char2.update(System.currentTimeMillis());
        char3.update(System.currentTimeMillis());
        char4.update(System.currentTimeMillis());
        boss1.update(System.currentTimeMillis());
    }

}
