package org.roelf.scala.aerirc.actors

import akka.actor.Actor
import org.roelf.scala.aerirc._
import org.roelf.scala.aerirc.messages._

/**
 * Created by roelf on 10/15/14.
 */
class DispatchActor(network: IRCNetwork) extends Actor {
	override def receive: Receive =
	{
		case msg@NOTICE(sender, nickname, text) =>
			if (Util.isValidChannel(nickname))
				network.joinedChannels.find(c => c.channelName.equalsIgnoreCase(nickname)).foreach(c => c.actor ! msg)
			else
				network.handlers.notices.handleAll(msg)

		case msg@PRIVMSG(sender, nickname, text) =>
			if (Util.isValidChannel(nickname))
				network.joinedChannels.find(c => c.channelName.equalsIgnoreCase(nickname)).foreach(c => c.actor ! msg)
			else
				network.handlers.notices.handleAll(msg)

		case msg@JOIN(sender, channel, key) =>
			//We joined a channel.
			val chan = new IRCChannel(network, channel)
			network.joinedChannels += chan
			network.handlers.preJoins.handleAll(chan)
			chan.actor ! msg

		//Internal stuff 'n things
		case ExitMessage => context stop self
		case msg: IRCMessage => network.handlers.unknownMessages.handleAll(msg)

	}
}
