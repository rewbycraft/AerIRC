package org.roelf.scala.aerirc.actors

import akka.actor.Actor
import org.roelf.scala.aerirc.IRCNetwork

/**
 * Created by roelf on 10/15/14.
 */
class DispatchActor(network: IRCNetwork) extends Actor {
	override def receive: Receive = {

		//Internal stuff 'n things
		case ExitMessage => context stop self
		case msg => println("X Unknown message fed to dispatcher: " + msg)
	}
}
