//package pt.ipbeja.pi.piproject.util;
//
///**
// * Utility class to convert numeric latitude and longitude to human readable coordinates
// */
//public final class Coordinates
//{
//    private Coordinates () {
//        throw new AssertionError();
//    }
//
//    /**
//     * Convert an angle to degrees/minutes/seconds format
//     *
//     * @param angle in degrees
//     * @return angle to degrees/minutes/seconds format
//     */
//    public static String angleDMS(double angle)
//    {
//        int d = (int) angle;
//        angle = (angle - d) * 60.0;
//
//        int m = (int) angle;
//        angle = (angle - m) * 60.0;
//
//        int s = (int) angle;
//
//        return String.format("%d° %d' %d\"", d, m, s);
//    }
//
//    /**
//     * Convert a pair of angles into human readable coordinates
//     *
//     * @param lat latitude in degrees
//     * @param lng longitude in degrees
//     * @return a string with coordinates in human readable format
//     */
//    public static String anglesToDMS(double lat, double lng)
//    {
//        String dLat = (lat > 0) ? "N" : "S";
//        String dLon = (lng > 0) ? "E" : "W"; // estava trocado! JPB (2018/08/09)
//
//        if (lat < 0) lat = -lat;
//        if (lng < 0) lng = -lng;
//
//        return String.format("%s %s %s %s",
//                angleDMS(lat), dLat,
//                angleDMS(lng), dLon
//        );
//    }
//}
