package org.roelf.scala.aerirc

/**
 *
 * Just some random utils.
 *
 * Created by roelf on 11/21/14.
 */
object Util {
	def isValidChannel(chan: String): Boolean = "([#&][^\\x07\\x2C\\s]{0,200})".r.findFirstIn(chan).isDefined

	def makeTimer(interval: Int, repeats: Boolean = true)(op: => Unit): javax.swing.Timer =
	{
		val timeOut = new javax.swing.AbstractAction() {
			def actionPerformed(e: java.awt.event.ActionEvent) = op
		}
		val t = new javax.swing.Timer(interval, timeOut)
		t.setRepeats(repeats)
		t.start()
		t
	}
}
