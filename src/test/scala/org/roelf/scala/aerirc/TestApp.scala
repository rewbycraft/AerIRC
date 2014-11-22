package org.roelf.scala.aerirc

import org.roelf.scala.aerirc.handlers.{IRCChannelMessageHandler, IRCPreJoinHandler, EWasHandled, IRCUnknownMessageHandler}
import org.roelf.scala.aerirc.messages.{MESSAGE, IRCMessage}

/**
 * Created by roelf on 10/8/14.
 */
object TestApp extends App {
	val network = new IRCNetwork("galaxy.spectrenet.cf", 6667, "Test")
	network.handlers.unknownMessages.register(new IRCUnknownMessageHandler {
		override def handle(message: IRCMessage): EWasHandled =
		{
			println("Failed for: " + message)
			EWasHandled.YES
		}
	})

	network.handlers.preJoins.register(new IRCPreJoinHandler {
		override def handle(message: IRCChannel): EWasHandled = {
			message.handlers.messages.register(new IRCChannelMessageHandler {
				override def handle(message: MESSAGE): EWasHandled = {
					println("[" + message.from + "] " + message.message)
					EWasHandled.YES
				}
			})
			EWasHandled.YES
		}
	})
	network.join("#Aeris")
}
