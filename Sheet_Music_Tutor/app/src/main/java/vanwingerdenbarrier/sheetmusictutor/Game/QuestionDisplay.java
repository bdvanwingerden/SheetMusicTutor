package vanwingerdenbarrier.sheetmusictutor.Game;

import vanwingerdenbarrier.sheetmusictutor.StaffStructure.Note;

/**
 * Created by Bronson VanWingerden on 11/23/2017.
 */

public interface QuestionDisplay{
    interface Display{
        void questionPressed(Note note);
    }
}
