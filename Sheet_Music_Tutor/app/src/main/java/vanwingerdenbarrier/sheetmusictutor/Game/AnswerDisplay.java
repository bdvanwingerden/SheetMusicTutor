package vanwingerdenbarrier.sheetmusictutor.Game;

import android.view.MotionEvent;

import vanwingerdenbarrier.sheetmusictutor.StaffStructure.Note;

/**
 * Interface Used for passing information between activities and containing fragments
 *
 * Created by Bronson VanWingerden on 11/23/2017.
 */

public interface AnswerDisplay {
    interface Display{
    void answerPressed(Note note, MotionEvent event);
    }
}
