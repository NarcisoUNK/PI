package pt.ipbeja.pi.piprojectGEO.util

/**
 * Utility class to convert numeric latitude and longitude to human readable coordinates
 */
class  Coordinates
private constructor() {
    init {
        throw AssertionError()
    }

    companion object {
        /**
         * Convert an angle to degrees/minutes/seconds format
         *
         * @param angle in degrees
         * @return angle to degrees/minutes/seconds format
         */
        fun angleDMS(angle: Double): String {
            var angle = angle
            val d = angle.toInt()
            angle = (angle - d) * 60.0

            val m = angle.toInt()
            angle = (angle - m) * 60.0

            val s = angle.toInt()

            return String.format("%d° %d' %d\"", d, m, s)
        }

        /**
         * Convert a pair of angles into human readable coordinates
         *
         * @param lat latitude in degrees
         * @param lng longitude in degrees
         * @return a string with coordinates in human readable format
         */
        fun anglesToDMS(lat: Double, lng: Double): String {
            var lat = lat
            var lng = lng
            val dLat = if ((lat > 0)) "N" else "S"
            val dLon = if ((lng > 0)) "E" else "W" // estava trocado! JPB (2018/08/09)

            if (lat < 0) lat = -lat
            if (lng < 0) lng = -lng

            return String.format(
                "%s %s %s %s",
                angleDMS(lat), dLat,
                angleDMS(lng), dLon
            )
        }
    }
}