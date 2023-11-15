import java.io.File
import java.math.BigInteger
import java.security.MessageDigest

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = File("src", "$name.txt")
    .readLines()

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')

/**
 * The cleaner shorthand for printing output.
 */
fun Any?.println() = println(this)

/**
 * Swap elements inside MutableList
 */
fun MutableList<Any>.swap(a: Int, b: Int) {
    this[a] = this[b].also { this[b] = this[a] }
}

/**
 * Vector
 */
data class Vec2D(val x: Int, val y: Int) {
    operator fun plus(o: Vec2D) = Vec2D(x + o.x, y + o.y)
    operator fun minus(o: Vec2D) = Vec2D(x - o.x, y - o.y)
}

data class Vec3D(val x: Int, val y: Int, val z: Int) {
    operator fun plus(o: Vec3D) = Vec3D(x + o.x, y + o.y, z + o.z)
    operator fun minus(o: Vec3D) = Vec3D(x - o.x, y - o.y, z - o.z)
}
