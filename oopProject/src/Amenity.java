public class Amenity {
    private String amenity;

    public Amenity(String amenity) {
        this.amenity = amenity;
    }

    @Override
    public String toString() {
        return amenity; // أو return "Amenity Name: " + name;
    }

    public void setamenity( String amenity) {
        this.amenity=amenity;
    }
    public String getAmenity() {
        return this.amenity;
    }
}
