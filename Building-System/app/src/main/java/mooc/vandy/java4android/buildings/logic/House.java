package mooc.vandy.java4android.buildings.logic;

import androidx.annotation.NonNull;

/**
 * This is the House class file that extends Building.
 */
public class House
       extends Building {

    private String mOwner;
    private boolean mPool;

    public House(int length, int width, int lotLength, int lotWidth) {
        super(length, width, lotLength, lotWidth);
        this.mOwner = "n/a";
        this.mPool = false;
    }
    public House(int length, int width, int lotLength, int lotWidth, String owner) {
        super(length, width, lotLength, lotWidth);
        this.mOwner = owner;
        this.mPool = false;
    }

    public House(int length, int width, int lotLength, int lotWidth, String owner, boolean pool) {
        super(length, width, lotLength, lotWidth);
        this.mOwner = owner;
        this.mPool = pool;
    }

    public String getOwner() {
        return mOwner;
    }

    public boolean hasPool() {
        return mPool;
    }

    public void setOwner(String owner) {
        this.mOwner = owner;
    }

    public void setPool(boolean pool) {
        this.mPool = pool;
    }

    @NonNull
    public String toString() {
        String res = "Owner: ";
        if (mOwner != null) {
            res += getOwner();
        } else {
            res += "n/a";
        }
        if (mPool) {
            res += "; has a pool";

        }
        if (this.calcLotArea() > 2 * this.calcBuildingArea()) {
            res += "; has a big open space";
        }
        return res;
    }

    // Maybe we redefine the object itself.
    public boolean equals(Object object) {
        int area = getLength() * getWidth();
        boolean stat = this.mPool;
        if (object instanceof House) {
            House tempObject = (House) object;
            int tempArea = tempObject.getLength() * tempObject.getWidth();
            boolean tempStat = tempObject.mPool;
            return area == tempArea && stat == tempStat;
        }
        return false;
    }

}
