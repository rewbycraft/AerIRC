package org.roelf.scala.aerirc

import akka.actor.Props
import org.roelf.scala.aerirc.actors.ChannelActor
import org.roelf.scala.aerirc.handlers.{IRCChannelMessageHandler, THandleAbleType, IRCNoticeHandler}

/**
 *
 * It extends THandleAbleType to allow it being used for handlers.
 *
 * Created by roelf on 11/21/14.
 */
class IRCChannel(private[aerirc] val network: IRCNetwork, private[aerirc] val channelName: String) extends THandleAbleType{

	private[aerirc] val actor = network.system.actorOf(Props(classOf[ChannelActor], this))

	object handlers {
		val messages = new Registry[IRCChannelMessageHandler]
		val notices = new Registry[IRCNoticeHandler]
	}
}
