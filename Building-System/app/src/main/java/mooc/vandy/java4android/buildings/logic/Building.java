package mooc.vandy.java4android.buildings.logic;

import androidx.annotation.NonNull;

/**
 * This is the Building class file.
 */
public class Building {

    private int mLength;
    private int mWidth;
    private int mLotLength;
    private int mLotWidth;

    public Building(int length, int width, int lotLength, int lotWidth) {
        this.mLength = length;
        this.mWidth = width;
        this.mLotLength = lotLength;
        this.mLotWidth = lotWidth;
    }

    public int getLength() {
        return mLength;
    }

    public int getWidth() {
        return mWidth;
    }

    public int getLotLength() {
        return mLotLength;
    }

    public int getLotWidth() {
        return mLotWidth;
    }

    public void setLength(int length) {
        this.mLength = length;
    }

    public void setWidth(int width) {
        this.mWidth = width;
    }

    public void setLotLength(int mLotLength) {
        this.mLotLength = mLotLength;
    }

    public void setLotWidth(int mLotWidth) {
        this.mLotWidth = mLotWidth;
    }

    public int calcBuildingArea() {
        return this.mWidth * this.mLength;
    }

    public int calcLotArea() {
        return this.mLotWidth * this.mLotLength;
    }

    @NonNull
    public String toString() {
        return this.mLength + "x" + this.mWidth + " building";
    }

}
