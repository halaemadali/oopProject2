

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        RoomType t1 =new RoomType("single",1,200);
        System.out.println(  t1.getNO3());
        Amenity wifi = new Amenity("WiFi");
        Room r1=new Room();
        r1.addAmenity(wifi);
        System.out.println( r1.getAmenities().get(0));


    }
}
