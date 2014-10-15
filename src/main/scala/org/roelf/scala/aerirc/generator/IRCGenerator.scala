package org.roelf.scala.aerirc.generator

import org.roelf.scala.aerirc._

/**
 * Created by roelf on 10/8/14.
 */
object IRCGenerator {

	private def doSender(sender: String): String = if (sender != null) f":$sender " else ""

	def generate(message: IRCMessage): Option[String] = message match
	{
		case NICK(sender, nick, hops) => Some(doSender(sender) + "NICK " + nick + (if (hops > -1) " " + hops else ""))
		case USER(sender, username, hostname, serverName, realName) => Some(doSender(sender) + f"USER $username $hostname $serverName :$realName")
		case PONG(sender, msg) => Some(doSender(sender) + f"PONG :$msg")
		case PING(sender, msg) => Some(doSender(sender) + f"PING :$msg")
		case NOTICE(sender, nick, msg) => Some(doSender(sender) + f"NOTICE $nick :$msg")
		case JOIN(sender, channel, key) => Some(doSender(sender) + f"JOIN $channel" + (if (key != null) f" :$key" else ""))
		case RPL_MOTDSTART(sender, message) => Some(doSender(sender) + f"375 :$message")
		case RPL_MOTD(sender, to, message) => Some(doSender(sender) + f"372 $to :$message")

		case _ => None
	}
}
