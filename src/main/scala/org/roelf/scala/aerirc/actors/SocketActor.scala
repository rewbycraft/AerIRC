package org.roelf.scala.aerirc.actors

import java.io._
import java.net.Socket

import akka.actor.{Actor, ActorRef}

/**
 * Created by roelf on 10/8/14.
 */
class SocketActor(socket: Socket, target: ActorRef) extends Thread with Actor {
	private val out = new PrintStream(socket.getOutputStream, true)
	private val in = new BufferedReader(new InputStreamReader(socket.getInputStream))
	private var keepGoing = true
	private val logfile = new PrintWriter("/tmp/aerirclog.txt", "UTF-8")

	override def receive: Receive =
	{
		case SocketMessage(msg) =>
			try
			{
				//println("> " + msg)
				out.println(msg)
				logfile.println(msg)
				logfile.flush()
			} catch {
				case e: IOException =>
					e.printStackTrace()
					self ! ExitMessage
			}

		case ExitMessage =>
			if (keepGoing)
			{
				println("Stopped.")
				keepGoing = false
				try
				{
					logfile.close()
					socket.close()
				} catch {
					case e: IOException =>
						e.printStackTrace()
				}
				context stop self
			}
	}

	override def run(): Unit = while (keepGoing)
	{
		val line = in.readLine()
		if (line != null)
		{
			//println("< " + line)
			logfile.println(line)
			logfile.flush()
			target ! SocketMessage(line)
		}
		else
			self ! ExitMessage
	}

	start()
}
