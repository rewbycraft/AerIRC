package org.roelf.scala.aerirc

import akka.actor.Props
import org.roelf.scala.aerirc.actors.ChannelActor
import org.roelf.scala.aerirc.handlers._
import org.roelf.scala.aerirc.user.IRCUser

import scala.collection.immutable.HashSet

/**
 *
 * It extends THandleAbleType to allow it being used for handlers.
 *
 * Created by roelf on 11/21/14.
 */
class IRCChannel(val network: IRCNetwork, val channelName: String) extends THandleAbleType{

	private[aerirc] val actor = network.system.actorOf(Props(classOf[ChannelActor], this))

	private[aerirc] var _opList = new HashSet[String]

	private[aerirc] var _voiceList = new HashSet[String]

	private[aerirc] var _userlist = new HashSet[String]

	def opList = _opList.map(u => network.userFromHostMask(u))

	def voiceList = _voiceList.map(u => network.userFromHostMask(u))

	def userList = _userlist.map(u => network.userFromHostMask(u))

	object handlers {
		val messages = new Registry[IRCChannelMessageHandler]
		val notices = new Registry[IRCNoticeMessageHandler]
		val weJoined = new Registry[IRCJoinedChannelMessageHandler]
		val joined = new Registry[IRCJoinMessageHandler]
		val weParted = new Registry[IRCPartedChannelMessageHandler]
		val parted = new Registry[IRCPartMessageHandler]
	}

	final def say(text: String) = network.sendPrivateMessage(channelName, text)
}
