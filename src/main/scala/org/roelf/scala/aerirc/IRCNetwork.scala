package org.roelf.scala.aerirc

import java.net.Socket
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLSocketFactory

import akka.actor._
import org.roelf.scala.aerirc.actors._
import org.roelf.scala.aerirc.handlers._
import org.roelf.scala.aerirc.messages._
import org.roelf.scala.aerirc.user.{IRCUser, TUserPool}

import scala.collection.mutable.ArrayBuffer
import scala.concurrent.duration.Duration

/**
 * Created by roelf on 10/8/14.
 */
class IRCNetwork extends TUserPool {

	setNetwork(this) //for TUserPool

	private var _host: String = null
	private var _port: Int = 6667
	private var _ssl: Boolean = false
	private[aerirc] var _nick: String = null
	private var connected = false

	def host = _host
	def port = _port
	def ssl = _ssl
	def nick = _nick

	def host_=(i: String) = if (!connected) _host = i
	def port_=(i: Int) = if (!connected) _port = i
	def ssl_=(b: Boolean) = if (!connected) _ssl = b
	def nick_=(s: String) = if (!connected) _nick = s else nick(s)

	private[aerirc] var system: ActorSystem = ActorSystem(java.util.UUID.randomUUID().toString)

	private[aerirc] var socket: Socket = null

	private[aerirc] var connectionActorRef: ActorRef = system.actorOf(Props(classOf[ConnectionActor], this), "connectionactor")

	private[aerirc] var socketActorRef: ActorRef = null

	private[aerirc] var networkActorRef: ActorRef = null

	private[aerirc] var dispatchActorRef: ActorRef = null

	private[aerirc] var joinedChannels = new ArrayBuffer[IRCChannel]

	final def connect(): Unit = {
		if (ssl)
		socket = SSLSocketFactory.getDefault.createSocket(host, port)
		else
		socket = new Socket(host, port)
		connected = true
		socketActorRef = system.actorOf(Props(classOf[SocketActor], socket, connectionActorRef), "socketactor")
		networkActorRef = system.actorOf(Props(classOf[NetworkActor], this), "networkactor")
		dispatchActorRef = system.actorOf(Props(classOf[DispatchActor], this), "dispatchactor")
		sendRaw(NICK(null, nick, -1))
		sendRaw(USER(null, nick, "*", "*", nick))
	}

	object handlers {
		/**
		 * Called whenever we receive a message that we don't know what to do with. Tip: The message UNKNOWN means the parser didn't know what to do with it.
		 */
		val unknownMessages = new Registry[IRCUnknownMessageHandler]

		/**
		 * Called when a notice is received that is not channelbound
		 */
		val notices = new Registry[IRCNoticeMessageHandler]

		/**
		 * Called just before we join a channel. Use this to register channel hooks.
		 */
		val preJoins = new Registry[IRCPreJoinHandler]
		/**
		 * Called after parting a channel. Use this to clean up after yourself.
		 */
		val postParts = new Registry[IRCPostPartHandler]

		/**
		 * Called when we are connected.
		 */
		val connected = new Registry[IRCRegisteredMessageHandler]

		/**
		 * Called when a PM is received.
		 */
		val privateMessages = new Registry[IRCPrivateMessageHandler]

		/**
		 * Called when a user changes his or her nick.
		 */
		val nickChanged = new Registry[IRCNickHandler]

		/**
		 * Called on disconnect.
		 */
		val disconnected = new Registry[IRCDisconnectHandler]
	}

	final def sendRaw(msg: IRCMessage) = connectionActorRef ! msg

	final def sendPrivateMessage(target: String, text: String) = sendRaw(PRIVMSG(null, target, text))

	final def join(channel: String, key: String): Unit = sendRaw(JOIN(null, channel, key))

	final def join(channel: String): Unit = join(channel, null)

	final def part(channel: String, reason: String): Unit = sendRaw(PART(null, channel, reason))

	final def part(channel: String): Unit = part(channel, "Cave Johnson; We're done here.")

	final def part(channel: IRCChannel, reason: String): Unit = part(channel.channelName, reason)

	final def part(channel: IRCChannel): Unit = part(channel.channelName)

	private var timer: javax.swing.Timer = null

	final def quit(reason: String): Unit = {
		timer = Util.makeTimer(5000, repeats = false)(teardown _)
		sendRaw(QUIT(null, nick, reason))
	}

	final def quit(): Unit = quit("Cave Johnson; We're done here.")

	/**
	 * Use this to change your nickname
	 * @param newNick
	 */
	final def nick(newNick: String) = sendRaw(NICK(null, newNick, -1))

	final def notice(target: String, msg: String): Unit = sendRaw(NOTICE(null, target, msg))

	final private[aerirc] def teardown(): Unit = {
		if (timer != null)
			timer.stop()

		joinedChannels.toArray.foreach(channel => {
			channel.userList.foreach(u => channel.handlers.parted.handleAll(PART(u, channel.channelName, "Disconnecting")))
			channel.handlers.weParted.handleAll(PARTED(me))
			channel.network.handlers.postParts.handleAll(channel)
			joinedChannels -= channel
		})

		handlers.disconnected.handleAll(DISCONNECT())

		joinedChannels.foreach(channel => channel.actor ! ExitMessage)

		joinedChannels.clear()

		val actors = Array(connectionActorRef, socketActorRef, networkActorRef, dispatchActorRef)
		actors.foreach(actor => actor ! ExitMessage)

		try
		{
			system.awaitTermination(Duration(1, TimeUnit.SECONDS))
		}
		catch {
			case e: java.util.concurrent.TimeoutException =>
				//Ignore this exception
		}
		system.shutdown()
	}
}
