package vanwingerdenbarrier.sheetmusictutor.StaffStructure;

import java.util.ArrayList;

/**
 * Created by Bronson VanWingerden on 10/12/2017.
 */

public class Bar {
    int numOfBeats;

    ArrayList<Beat> beats;

    public Bar(int[] timeSig) {
        this.numOfBeats = numOfBeats;
        beats = new ArrayList<>();

        for (int i = 0; i < timeSig[0]; i++){
            beats.add(new Beat());
            numOfBeats++;
        }
    }

    public ArrayList<Beat> getBeats() {
        return beats;
    }

    public int getNumOfBeats(){
        return beats.size();
    }
}
