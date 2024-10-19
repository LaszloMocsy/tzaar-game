package tzaar.component;

public class SpaceLocation implements Comparable<SpaceLocation> {
    private final int x;
    private final int y;
    private final int z;

    public SpaceLocation(int x, int y) {
        // Check if the X and Y coordinates are valid
        if (x < 1 || x > 9 || y < 1 || y > 9 || (x == 5 && y == 5)) {
            throw new IllegalArgumentException(String.format("Invalid coordinates (x: %d, y: %d)", x, y));
        }

        this.x = x;
        this.y = y;
        this.z = calculateZ();
    }

    /// Check if the location is on the same axis as another location
    public boolean isOnSameAxis(SpaceLocation other) {
        if (this.x != 5 && this.y != 5 && this.z != 5 && other.x != 5 && other.y != 5 && other.z != 5) {
            return this.x == other.x || this.y == other.y || this.z == other.z;
        }

        if (this.x == 5) {
            return (this.y < 5 && other.y < 5 && this.z < 5 && other.z < 5) || (this.y > 5 && other.y > 5 && this.z > 5 && other.z > 5);
        } else if (this.y == 5) {
            return (this.x < 5 && other.x < 5 && this.z > 5 && other.z > 5) || (this.x > 5 && other.x > 5 && this.z < 5 && other.z < 5);
        } else {
            return (this.x < 5 && other.x < 5 && this.y < 5 && other.y < 5) || (this.x > 5 && other.x > 5 && this.y > 5 && other.y > 5);
        }
    }

    public int[] getCoordinates() {
        return new int[]{this.x, this.y, this.z};
    }

    @Override
    public int compareTo(SpaceLocation other) {
        if (this == other) {
            return 0;
        }

        // Compare based on x, then y, then z
        int result = Integer.compare(this.x, other.x);
        if (result == 0) {
            result = Integer.compare(this.y, other.y);
        }
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        SpaceLocation other = (SpaceLocation) obj;
        return this.x == other.x && this.y == other.y;
    }

    @Override
    public int hashCode() {
        return 31 * this.x + this.y;
    }

    @Override
    public String toString() {
        final String[] letters = {"A", "B", "C", "D", "E", "F", "G", "H", "I"};
        return String.format("%s%d", letters[this.x - 1], this.y);
    }

    /// The Z coordinate is calculated based on X and then increased based on Y
    private int calculateZ() {
        int locZ = 1;

        // Calculate Z based on X
        if (this.x < 5) {
            locZ = 6 - this.x;
        }

        // Increase Z based on Y
        if (this.x > 5) {
            int baseY = this.x - 4;
            int diff = this.y - baseY;
            locZ += diff;
        } else {
            locZ += this.y - 1;
        }

        return locZ;
    }
}
