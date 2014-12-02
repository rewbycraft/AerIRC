package org.roelf.scala.aerirc.actors

import akka.actor.{PoisonPill, Actor}
import org.roelf.scala.aerirc._
import org.roelf.scala.aerirc.handlers.MODECHANGE
import org.roelf.scala.aerirc.messages._

/**
 * Created by roelf on 11/22/14.
 */
class ChannelActor(channel: IRCChannel) extends Actor {
	override def receive =
	{

		case msg@JOIN(s, c, k) =>
			if (channel.network.isMe(s))
				channel.handlers.weJoined.handleAll(JOINED(s))
			else
				channel.handlers.joined.handleAll(msg)

		case PRIVMSG(s, c, msg) =>
			channel.handlers.messages.handleAll(MESSAGE(s, msg))

		case msg@NOTICE(s, n, t) =>
			channel.handlers.notices.handleAll(msg)

		case msg@PART(s, c, m) =>
			if (channel.network.isMe(s))
			{
				channel.userList.foreach(u => channel.handlers.parted.handleAll(PART(u, channel.channelName, "Disconnecting")))
				channel.handlers.weParted.handleAll(PARTED(s))
				channel.network.handlers.postParts.handleAll(channel)
				channel.network.joinedChannels -= channel
			}
			else
				channel.handlers.parted.handleAll(msg)

		case msg@MODE(sender, target, _channel, mode, wasApplied) =>
			val user = channel.network.userFromUser(target)
			if (user != null)
			{
				mode match
				{
					case 'v' =>
						if (wasApplied)
							channel._voiceList += user.matchable
						else
							channel._voiceList -= user.matchable
					case 'o' =>
						if (wasApplied)
							channel._opList += user.matchable
						else
							channel._opList -= user.matchable
				}
				channel.handlers.modeChanged.handleAll(MODECHANGE(user, mode, wasApplied))
			}

		//Internal stuff 'n things
		case ExitMessage => self ! PoisonPill
		case msg: IRCMessage => channel.handlers.unknownMessages.handleAll(msg)

	}
}
