package org.roelf.scala.aerirc

import org.roelf.scala.aerirc.handlers._
import org.roelf.scala.aerirc.messages._

/**
 * Created by roelf on 10/8/14.
 */
object TestApp extends App {
	val network = new IRCNetwork
	network.host = "irc.spectrenet.cf"
	network.nick = "Test"

	network.handlers.unknownMessages.register(new IRCUnknownMessageHandler {
		override def handle(message: IRCMessage): EWasHandled =
		{
			println("Failed for: " + message)
			EWasHandled.YES
		}
	})

	network.handlers.privateMessages.register(new IRCPrivateMessageHandler {
		override def handle(message: PRIVMSG): EWasHandled = {
			println("Got a PM from " + message.sender + " to " + message.receiver + " saying " + message.message)
			EWasHandled.YES
		}
	})

	network.handlers.nickChanged.register(new IRCNickHandler {
		override def handle(message: NICKCHANGE): EWasHandled = {
			println(f"Nick change: ${message.oldNick}->${message.user.nickname}")
			EWasHandled.YES
		}
	})

	network.handlers.preJoins.register(new IRCPreJoinHandler {
		override def handle(channel: IRCChannel): EWasHandled = {
			channel.handlers.messages.register(new IRCChannelMessageHandler {
				override def handle(message: MESSAGE): EWasHandled = {
					println("[" + message.sender.nickname + "] " + message.message)
					println("Users: " + network.userPool.size)
					if (message.message.equalsIgnoreCase("quit"))
						network.quit("Gah!")
					if (message.message.startsWith("nick"))
						network.nick = message.message.split(" ")(1)
					EWasHandled.YES
				}
			})
			channel.handlers.weJoined.register(new IRCJoinedChannelMessageHandler {
				override def handle(message: JOINED): EWasHandled = {
					channel.say("Hello world!")
					EWasHandled.YES
				}
			})

			channel.handlers.joined.register(new IRCJoinMessageHandler {
				override def handle(message: JOIN): EWasHandled =
				{
					channel.say("Hello, " + message.sender.nickname + "!")
					if (channel.opList.contains(message.sender))
						channel.say("OMG, " + message.sender.nickname + "! You're an OP!")
					if (channel.voiceList.contains(message.sender))
						channel.say("Yay! " + message.sender.nickname + " has voice!")
					EWasHandled.YES
				}
			})

			channel.handlers.parted.register(new IRCPartMessageHandler {
				override def handle(message: PART): EWasHandled =
				{
					println(message.sender.nickname + " left")
					EWasHandled.YES
				}
			})
			channel.handlers.modeChanged.register(new IRCModeHandler {
				override def handle(message: MODECHANGE): EWasHandled = {
					println(message)
					if (!channel.network.isMe(message.user))
					{
						channel.say("Hey " + message.user.nickname + "! Mode changed!")
						if (channel.opList.contains(message.user))
							channel.say("OMG, " + message.user.nickname + "! You're an OP!")
						if (channel.voiceList.contains(message.user))
							channel.say("Yay! " + message.user.nickname + " has voice!")
					}
					else
					{
						channel.say("Hey! My mode changed!")
						if (channel.opList.contains(message.user))
							channel.say("OMG! I'm an OP!")
						if (channel.voiceList.contains(message.user))
							channel.say("Yay! I have voice!")
					}
					EWasHandled.YES
				}
			})
			EWasHandled.YES
		}
	})
	network.join("#AerIRC")
	network.connect()
}
