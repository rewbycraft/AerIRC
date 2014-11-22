package org.roelf.scala.aerirc

import java.net.Socket

import akka.actor.{ActorSystem, Props}
import org.roelf.scala.aerirc.actors._
import org.roelf.scala.aerirc.handlers.{IRCPreJoinHandler, IRCNoticeHandler, IRCUnknownMessageHandler}
import org.roelf.scala.aerirc.messages.{JOIN, USER, NICK, IRCMessage}

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

/**
 * Created by roelf on 10/8/14.
 */
class IRCNetwork(val host: String, val port: Int, val nick: String) {

	private[aerirc] val system = ActorSystem(host.replace('.', '-') + "--" + port)

	private[aerirc] val socket = new Socket(host, port)

	private[aerirc] val connectionActorRef = system.actorOf(Props(classOf[ConnectionActor], this))

	private[aerirc] val socketActorRef = system.actorOf(Props(classOf[SocketActor], socket, connectionActorRef))

	private[aerirc] val networkActorRef = system.actorOf(Props(classOf[NetworkActor], this))

	private[aerirc] val dispatchActorRef = system.actorOf(Props(classOf[DispatchActor], this))

	private[aerirc] val joinedChannels = new ArrayBuffer[IRCChannel]

	object handlers {
		val unknownMessages = new Registry[IRCUnknownMessageHandler]
		val notices = new Registry[IRCNoticeHandler]
		val preJoins = new Registry[IRCPreJoinHandler]
	}

	final def sendRaw(msg: IRCMessage) = connectionActorRef ! msg

	sendRaw(NICK(null, nick, -1))

	sendRaw(USER(null, nick, "*", "*", nick))

	final def join(channel: String, key: String): Unit = sendRaw(JOIN(null, channel, key))

	final def join(channel: String): Unit = join(channel, null)

	final private def teardown(): Unit = {
		val actors = Array(connectionActorRef, socketActorRef, networkActorRef, dispatchActorRef)
		actors.foreach(actor => actor ! ExitMessage)

		//Dangerous: Only do this if absolutely needed.
		//actors.foreach(actor => if (!actor.isTerminated) system stop actor)
	}
}
