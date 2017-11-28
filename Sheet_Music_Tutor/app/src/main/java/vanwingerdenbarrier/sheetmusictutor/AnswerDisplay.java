package vanwingerdenbarrier.sheetmusictutor;

import android.view.MotionEvent;

import vanwingerdenbarrier.sheetmusictutor.StaffStructure.Note;

/**
 * Created by Bronson VanWingerden on 11/27/2017.
 */

public interface AnswerDisplay {
    interface Display{
    void answerPressed(Note note, MotionEvent event);
    }
}
