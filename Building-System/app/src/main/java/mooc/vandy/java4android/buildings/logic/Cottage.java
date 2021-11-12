package mooc.vandy.java4android.buildings.logic;

import androidx.annotation.NonNull;

/**
 * This is the cottage class file.  It is a subclass of House.
 */
public class Cottage
       extends House {

    private boolean mSecondFloor;

    public Cottage(int dimension, int lotLength, int lotWidth) {
        super(dimension, dimension, lotLength, lotWidth);
    }

    public Cottage(int dimension, int lotLength, int lotWidth, String owner, boolean secondFloor) {
        super(dimension, dimension, lotLength, lotWidth, owner);
        this.mSecondFloor = secondFloor;
    }

    public boolean hasSecondFloor() {
        return this.mSecondFloor;
    }

    @NonNull
    public String toString() {
        int area = getWidth() * getLength();
        int lotArea = getLotLength() * getLotWidth();
        if (area < lotArea && hasSecondFloor() && hasPool()) {
            return "Owner: " + getOwner() + "; has a pool;" + " is a two story cottage";
        } else if (area < lotArea && !hasPool() && hasSecondFloor()) {
            return "Owner: " + getOwner() + "; has a big open space;" + " is a two story cottage";
        } else if (hasPool()){
            return "Owner: " + getOwner() + "; has a pool";
        } else {
            return "Owner: " + getOwner() + "; is a cottage";
        }
     }

}

