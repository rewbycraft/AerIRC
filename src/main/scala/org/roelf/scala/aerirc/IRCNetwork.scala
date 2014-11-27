package org.roelf.scala.aerirc

import java.net.Socket
import javax.net.ssl.SSLSocketFactory

import akka.actor.{ActorRef, ActorSystem, Props}
import org.roelf.scala.aerirc.actors._
import org.roelf.scala.aerirc.handlers._
import org.roelf.scala.aerirc.messages._
import org.roelf.scala.aerirc.user.{IRCUser, TUserPool}

import scala.collection.mutable.ArrayBuffer

/**
 * Created by roelf on 10/8/14.
 */
class IRCNetwork extends TUserPool {

	setNetwork(this) //for TUserPool

	private var _host: String = null
	private var _port: Int = 6667
	private var _ssl: Boolean = false
	private var _nick: String = null
	private var connected = false

	def host = _host
	def port = _port
	def ssl = _ssl
	def nick = _nick

	def host_=(i: String) = if (!connected) _host = i
	def port_=(i: Int) = if (!connected) _port = i
	def ssl_=(b: Boolean) = if (!connected) _ssl = b
	def nick_=(s: String) = if (!connected) _nick = s

	private[aerirc] var system: ActorSystem = ActorSystem(java.util.UUID.randomUUID().toString)

	private[aerirc] var socket: Socket = null

	private[aerirc] var connectionActorRef: ActorRef = system.actorOf(Props(classOf[ConnectionActor], this))

	private[aerirc] var socketActorRef: ActorRef = null

	private[aerirc] var networkActorRef: ActorRef = null

	private[aerirc] var dispatchActorRef: ActorRef = null

	private[aerirc] var joinedChannels = new ArrayBuffer[IRCChannel]

	final def connect(): Unit = {
		if (ssl)
			socket = SSLSocketFactory.getDefault.createSocket(host, port)
		else
			socket = new Socket(host, port)
		socketActorRef = system.actorOf(Props(classOf[SocketActor], socket, connectionActorRef))
		networkActorRef = system.actorOf(Props(classOf[NetworkActor], this))
		dispatchActorRef = system.actorOf(Props(classOf[DispatchActor], this))
		sendRaw(NICK(null, nick, -1))
		sendRaw(USER(null, nick, "*", "*", nick))
		connected = true
	}

	object handlers {
		val unknownMessages = new Registry[IRCUnknownMessageHandler]
		val notices = new Registry[IRCNoticeMessageHandler]
		val preJoins = new Registry[IRCPreJoinHandler]
		val postParts = new Registry[IRCPostPartHandler]
		val connected = new Registry[IRCRegisteredMessageHandler]
		val privateMessages = new Registry[IRCPrivateMessageHandler]
		val whoReplies = new Registry[IRCWhoMessageHandler]
	}

	final def sendRaw(msg: IRCMessage) = connectionActorRef ! msg

	final def sendPrivateMessage(target: String, text: String) = sendRaw(PRIVMSG(null, target, text))

	final def join(channel: String, key: String): Unit = sendRaw(JOIN(null, channel, key))

	final def join(channel: String): Unit = join(channel, null)

	final def part(channel: String, reason: String): Unit = sendRaw(PART(null, channel, reason))

	final def part(channel: String): Unit = part(channel, "Cave Johnson; We're done here.")

	final def part(channel: IRCChannel, reason: String): Unit = part(channel.channelName, reason)

	final def part(channel: IRCChannel): Unit = part(channel.channelName)

	final def quit(reason: String): Unit = sendRaw(QUIT(null, nick, reason))

	final def quit(): Unit = quit("Cave Johnson; We're done here.")

	final private[aerirc] def teardown(): Unit = {
		val actors = Array(connectionActorRef, socketActorRef, networkActorRef, dispatchActorRef)
		actors.foreach(actor => actor ! ExitMessage)

		//Dangerous: Only do this if absolutely needed.
		//actors.foreach(actor => if (!actor.isTerminated) system stop actor)
		system.shutdown()
		system.awaitTermination()
	}
}
