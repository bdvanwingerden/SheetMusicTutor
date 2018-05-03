package edu.wcu.ddbarrier1.paintmeister;

/**
 *
 * Class that defines a line that will be used for painting an image on the canvas
 *
 * @Author - Dorian Barrier
 */
public class Line {

    /**Starting and ending x & y coordinates in a line*/
    public float x1,y1,x2,y2;

    /**
     * Default constructor for Line
     * @param x1 - first x coordinate
     * @param y1 - first y coordinate
     * @param x2 - second x coordinate
     * @param y2 - second y coordinate
     */
    public Line(float x1, float y1, float x2, float y2) {
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
    }

}
