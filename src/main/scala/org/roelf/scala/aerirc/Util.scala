package org.roelf.scala.aerirc

/**
 *
 * Just some random utils.
 *
 * Created by roelf on 11/21/14.
 */
object Util {
	def isValidChannel(chan: String): Boolean = "([#&][^\\x07\\x2C\\s]{0,200})".r.findFirstIn(chan).isDefined
}
