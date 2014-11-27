package org.roelf.scala.aerirc.actors

import akka.actor.Actor
import org.roelf.scala.aerirc._
import org.roelf.scala.aerirc.messages._

import scala.collection.mutable

/**
 * Created by roelf on 10/15/14.
 */
class DispatchActor(network: IRCNetwork) extends Actor {

	case class WHOQUEUEITEM(nickname: String, channel: String, op: Boolean, voice: Boolean)

	val whoQueue = mutable.HashSet[WHOQUEUEITEM]()

	override def receive: Receive =
	{
		case msg@NOTICE(sender, target, text) =>
			if (Util.isValidChannel(target))
				network.joinedChannels.find(c => c.channelName.equalsIgnoreCase(target)).foreach(c => c.actor ! msg)
			else
				network.handlers.notices.handleAll(msg)

		case msg@PRIVMSG(sender, target, text) =>
			if (Util.isValidChannel(target))
				network.joinedChannels.find(c => c.channelName.equalsIgnoreCase(target)).foreach(c => c.actor ! msg)
			else
				network.handlers.privateMessages.handleAll(msg)

		case msg@JOIN(sender, channel, key) =>
			if (network.isMe(sender))
			{
				//We joined a channel.
				val chan = new IRCChannel(network, channel)
				network.joinedChannels += chan
				network.handlers.preJoins.handleAll(chan)
				chan.actor ! msg
			}
			else
				network.joinedChannels.find(c => c.channelName.equalsIgnoreCase(channel)).foreach(c => c.actor ! msg)

		case msg@PART(sender, channel, message) =>
			//We will handle removing the channel from the network on the ChannelActor so it can handle it's breakdown before getting removed
			network.joinedChannels.find(c => c.channelName.equalsIgnoreCase(channel)).foreach(c => c.actor ! msg)

		case msg@RPL_NAMREPLY(sender, channel, nickname, op, voice) =>
			println(msg)
			val user = network.userFromUser(nickname)
			if (user == null)
			{
				whoQueue += WHOQUEUEITEM(nickname, channel, op, voice)
				network.sendRaw(WHO(null, nickname, channel))
			}
			else
			{
				if (op)
					network.joinedChannels.find(c => c.channelName == channel).foreach(c => c._opList = c._opList + user.matchAble)
				if (voice)
					network.joinedChannels.find(c => c.channelName == channel).foreach(c => c._voiceList = c._voiceList + user.matchAble)
			}

		case RPL_WHOREPLY(s, c, username, host, server, nick, something, hops, rName) =>
			val mask = nick + "!" + username + "@" + host
			val user = network.userFromHostMask(mask)
			val todo = whoQueue.filter(w => w.nickname.equalsIgnoreCase(nick))
			for (w <- todo)
			{
				if (w.op)
					network.joinedChannels.find(c => c.channelName == w.channel).foreach(c => c._opList = c._opList + user.matchAble)
				if (w.voice)
					network.joinedChannels.find(c => c.channelName == w.channel).foreach(c => c._voiceList = c._voiceList + user.matchAble)
				self ! JOIN(user, w.channel, null)
			}

		case ERROR(sender, reason) =>
			network.teardown()

		//Internal stuff 'n things
		case ExitMessage => context stop self
		case msg: IRCMessage => network.handlers.unknownMessages.handleAll(msg)

	}
}
