package org.roelf.scala.aerirc.actors

import akka.actor.Actor
import org.roelf.scala.aerirc._
import org.roelf.scala.aerirc.generator.IRCGenerator
import org.roelf.scala.aerirc.messages._
import org.roelf.scala.aerirc.parser.IRCParser

import scala.collection.mutable

/**
 * Created by roelf on 10/8/14.
 */
class ConnectionActor(network: IRCNetwork) extends Actor {
	private var isRegistered = false
	private var msgQueue = new mutable.Queue[IRCMessage]

	private def send(msg: IRCMessage) =
	{
		val m = IRCGenerator.generate(msg)
		if (m.isDefined)
			network.socketActorRef ! SocketMessage(m.get)
		else
			println("X Failed to generate for: " + msg.getClass.getName)

	}

	override def receive: Receive =
	{
		//It came from the moon!
		case SocketMessage(line) =>
			val parsed = IRCParser.parse(line, network)
			if (!parsed.isDefined)
				println(f"X Parser failure for: $line")
			else
				parsed.get.foreach
				{
					//Ping is always handled at this level to avoid timeouts if other parts are delayed.
					case PING(sender, msg) => self ! PONG(null, msg)

					//Registration is complete when this is received.
					// Mark us as registered, then empty the queue and proceed.
					case msg@RPL_WELCOME(_, _) =>
						isRegistered = true
						msgQueue foreach send
						network.dispatchActorRef forward msg

					//Everything else can go to the dispatcher
					case msg => network.dispatchActorRef forward msg
				}

		//We're supposed to be sending a message.
		// These first couple specific cases are for messages used during registration.
		// Once registration is complete (see the case for SocketMessage) everything else
		//  will become essentially the same.
		case msg@USER(_, _, _, _, _) => send(msg)
		case msg@NICK(_, _, _) => send(msg)
		case msg@PONG(_, _) => send(msg)

		//Catch-all for IRCMessages. Send directly if ready, otherwise add it to the send queue.
		case msg: IRCMessage => if (!isRegistered) msgQueue += msg else send(msg)


		//Internal stuff 'n things
		case ExitMessage => context stop self
	}

}
