package org.roelf.scala.aerirc.generator

import org.roelf.scala.aerirc._
import org.roelf.scala.aerirc.messages._
import org.roelf.scala.aerirc.parser.IRCParserUtil

/**
 * Created by roelf on 10/8/14.
 */
object IRCGenerator {

	private def doSender(sender: String): String = if (sender != null) f":$sender " else ""

	final def generate(message: IRCMessage): Option[String] =
	{
		message match
		{
			case NICK(sender, nick, hops) => return Some(doSender(sender) + "NICK " + nick + (if (hops > -1) " " + hops else ""))
			case USER(sender, username, hostname, serverName, realName) => return Some(doSender(sender) + f"USER $username $hostname $serverName :$realName")
			case JOIN(sender, channel, key) => return Some(doSender(sender) + f"JOIN $channel" + (if (key != null) f" :$key" else ""))
			case _ =>
		}

		if (message.isInstanceOf[IRCMessage])
		{
			val fields = IRCGeneratorUtil.getFields(message).map(f => IRCGeneratorUtil.getFieldValue(message, f)).toArray
			val rules = IRCParserUtil.definitions.filter(r => r.split("\t")(0).equals(message.getClass.getSimpleName))
				.map(s => s.replace("%* ", "")).filter(r => "%.".r.findAllIn(r).size == fields.size-1)
			if (rules.size > 0)
			{
				val rule = rules.head
				var result = doSender(fields(0).asInstanceOf[String]) + rule.split("\t")(1)
				for (field <- fields.slice(1, fields.size))
					result = "%.".r.replaceFirstIn(result, if (field != null) field.toString.replace("\\", "\\\\") else "")
				return Some(result)
			}
		}

		None
	}
}
