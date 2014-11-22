package org.roelf.scala.aerirc

/**
 * Created by roelf on 11/21/14.
 */
object Util {
	def isValidChannel(chan: String): Boolean = (chan.startsWith("#")||chan.startsWith("&")) && chan.substring(1).map(c => c.isLetterOrDigit).contains(false)
}
