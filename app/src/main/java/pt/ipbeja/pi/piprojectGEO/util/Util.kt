package pt.ipbeja.pi.piprojectGEO.util

object Util {
    fun removeSpaces(s: String): String {
        val descriptionNoMultipleSpaces = s.replace("^\\s*".toRegex(), "")
        return descriptionNoMultipleSpaces.replace("\\n( |\\t)*".toRegex(), "\n")
    }
}