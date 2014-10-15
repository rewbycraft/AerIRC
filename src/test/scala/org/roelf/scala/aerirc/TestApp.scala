package org.roelf.scala.aerirc

/**
 * Created by roelf on 10/8/14.
 */
object TestApp extends App {
	val network = new IRCNetwork("galaxy.spectrenet.cf", 6667, "Test")
	network.join("#yay")
}
