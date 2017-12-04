package vanwingerdenbarrier.sheetmusictutor.Game;

import android.view.MotionEvent;

import vanwingerdenbarrier.sheetmusictutor.StaffStructure.Note;

/**
 * Interface within an interface used to facilitate call-backs to the main game activity from the
 * fragment
 *
 * @author Bronson VanWingerden
 */
public interface AnswerDisplay {
    interface Display{
    void answerPressed(Note note, MotionEvent event);
    }
}
