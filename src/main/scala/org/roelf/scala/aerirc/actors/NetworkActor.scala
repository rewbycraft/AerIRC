package org.roelf.scala.aerirc.actors

import akka.actor.{PoisonPill, Actor}
import org.roelf.scala.aerirc.handlers.EWasHandled
import org.roelf.scala.aerirc.IRCNetwork

/**
 * Created by roelf on 10/15/14.
 */
class NetworkActor(network: IRCNetwork) extends Actor {
	override def receive: Receive = {
		//Internal stuff 'n things
		case ExitMessage => self ! PoisonPill
		case msg => println("X Unknown message fed to NetworkActor: " + msg)
	}
}
