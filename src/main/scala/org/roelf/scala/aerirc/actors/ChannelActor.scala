package org.roelf.scala.aerirc.actors

import akka.actor.Actor
import org.roelf.scala.aerirc._
import org.roelf.scala.aerirc.messages.{MESSAGE, PRIVMSG, JOIN}

/**
 * Created by roelf on 11/22/14.
 */
class ChannelActor(channel: IRCChannel) extends Actor{
	override def receive = {
		case PRIVMSG(s, c, msg) =>
			channel.handlers.messages.handleAll(MESSAGE(s, msg))

		case _ =>
	}
}
