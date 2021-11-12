package mooc.vandy.java4android.buildings.logic;

import androidx.annotation.NonNull;

/**
 * This is the office class file, it is a subclass of Building.
 */
public class Office
       extends Building {

    private String mBusinessName;
    private int mParkingSpaces;
    private static int sTotalOffices = 0;

    public Office(int length, int width, int lotLength, int lotWidth) {
        super(length, width, lotLength, lotWidth);
        this.mBusinessName = null;
        this.mParkingSpaces = 0;
        sTotalOffices++;
    }

    public Office(int length, int width, int lotLength, int lotWidth, String businessName) {
        super(length, width, lotLength, lotWidth);
        this.mBusinessName = businessName;
        sTotalOffices++;
    }

    public Office(int length, int width, int lotLength, int lotWidth, String businessName, int parkingSpaces) {
        super(length, width, lotLength, lotWidth);
        this.mBusinessName = businessName;
        this.mParkingSpaces = parkingSpaces;
        sTotalOffices++;
    }

    public String getBusinessName() {
        return mBusinessName;
    }

    public int getParkingSpaces() {
        return mParkingSpaces;
    }

    public static int getTotalOffices() {
        return sTotalOffices;
    }

    public void setBusinessName(String mBusinessName) {
        this.mBusinessName = mBusinessName;
    }

    public void setParkingSpaces(int mParkingSpaces) {
        this.mParkingSpaces = mParkingSpaces;
    }

    @NonNull
    public String toString() {
        String res = "Business: ";
        if (getBusinessName() == null) {
            res += "unoccupied";
        } else {
            res += this.mBusinessName;
        }
        if (getParkingSpaces() != 0) {
            res += "; has " + getParkingSpaces() + " parking spaces";
        }
        res += " (total offices: " + getTotalOffices() + ")";
        return res;
    }

    public boolean equals(Object object) {
        int area = getLength() * getWidth();
        int parkingSpaces = getParkingSpaces();
        if (object instanceof Office) {
            Office temp = (Office) object;
            int tempArea = temp.getLength() * temp.getWidth();
            int tempParkingSpaces = temp.getParkingSpaces();
            return area == tempArea && parkingSpaces == tempParkingSpaces;
        }
        return false;
    }
}
