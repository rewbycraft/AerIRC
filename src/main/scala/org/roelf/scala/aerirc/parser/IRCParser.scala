package org.roelf.scala.aerirc.parser

import org.roelf.scala.aerirc.messages._
import org.roelf.scala.aerirc.user.{IRCUser, TUserPool}

import scala.collection.mutable.ArrayBuffer
import scala.language.implicitConversions

/**
 * The class that parses everything.
 * The privates are package private so the tests can get to them.
 *
 * Created by roelf on 10/7/14.
 */
object IRCParser {

	private[parser] def splitter(input: String): Array[String] =
	{
		val rc = new ArrayBuffer[String]
		var tempString = ""
		var tempStringWasEdited = false
		var keepWatchingForSpaces = true
		input.foreach(c =>
			c match
			{
				case ' ' =>
					if (keepWatchingForSpaces)
					{
						rc += tempString
						tempString = ""
						tempStringWasEdited = false
					}
					else
					{
						tempString += c
						tempStringWasEdited = true
					}
				case ':' =>
					if (keepWatchingForSpaces)
					{
						if (tempString.length == 0)
							keepWatchingForSpaces = false
						else
						{
							tempString += c
							tempStringWasEdited = true
						}
					}
					else
					{
						tempString += c
						tempStringWasEdited = true
					}

				case _ =>
					tempString += c
					tempStringWasEdited = true
			}
		)
		if (tempStringWasEdited)
			rc += tempString
		else
			rc(rc.size - 1) += " "

		rc.toArray
	}

	def parseMatch(_input: String, _pattern: String): Option[Array[AnyRef]] =
	{
		val input = splitter(_input)
		val pattern = splitter(_pattern.replace(":", ""))

		if (input.length != pattern.length)
			return None

		val rc = new ArrayBuffer[AnyRef]

		for (i <- 0 until input.length)
		{
			val a = input(i)
			val b = pattern(i)
			if (b.startsWith("%"))
				b.substring(1).charAt(0) match
				{
					case 's' =>
						rc += a
					case 'd' =>
						try
						{
							rc += Integer.parseInt(a).asInstanceOf[AnyRef]
						} catch
							{
								case e: NumberFormatException =>
							}
					case 'i' =>
					case '*' =>
					//ignored
					case _ => return None
				}
			else if (a != b)
				return None
		}
		Some(rc.toArray)
	}

	final def parse(_line: String, userPool: TUserPool): Option[Array[IRCMessage]] =
	{
		var line = _line
		//Sanity checks
		if (line == null)
			return None
		if (line.length == 0)
			return None

		//Stage 1: Extract sender
		var sender: IRCUser = null
		if (line.startsWith(":"))
		{
			val to = line.indexOf(' ')
			sender = userPool.userFromHostMask(line.substring(1, to).trim)
			line = line.substring(to + 1)
		}

		//Stage 2: Special case handling.
		{
			//Separate scope for cleanliness
			val to = line.indexOf(' ')
			val args = splitter(line.substring(to + 1))

			line.substring(0, to).trim.toUpperCase match
			{
				case "MODE" =>
					var curMode: Boolean = true
					val rc = new ArrayBuffer[IRCMessage]
					var namid = 0
					args(1).foreach
					{
						case '+' => curMode = true
						case '-' => curMode = false
						case c =>
							rc += MODE(sender, if (args.size > namid+1) args(2+namid) else null, args(0), c, curMode)
							namid += 1
					}
					if (rc.size > 0)
						return Some(rc.toArray)
					else
						return None

				case "JOIN" =>
					var keys: Array[String] = Array()
					if (args.size > 1)
						keys = args(1).split(",")
					val rc = new ArrayBuffer[IRCMessage]
					var i = 0
					args(0).split(",").foreach(chan =>
					{
						rc += JOIN(sender, chan, if (keys.size > i) keys(i) else null)
						i += 1
					})
					return Some(rc.toArray)

				case "PART" =>
					val message = if (args.size > 1) args(1) else null
					val rc = new ArrayBuffer[IRCMessage]
					args(0).split(",").foreach(chan => rc += PART(sender, chan, message))
					return Some(rc.toArray)

				case "352" =>
					val chan = args(1)
					val user = args(2)
					val host = args(3)
					val server = args(4)
					val nick = args(5)
					val something = args(6)
					val hopcount = args(7).split(" ")(0)
					val realName = args(7).split(" ").slice(1, args(7).split(" ").size).mkString(" ")
					return Some(Array(RPL_WHOREPLY(sender, chan, user, host, server, nick, something, hopcount.toInt, realName)))


				case "353" =>
					val chan = args(2)
					val nicks = args(3).split(" ")
					val rc = new ArrayBuffer[IRCMessage]
					for (nick <- nicks)
						rc += RPL_NAMREPLY(sender, chan, nick.replace("@", "").replace("+", ""), nick.contains("@"), nick.contains("+"))
					return Some(rc.toArray)

				case "004" => return Some(Array())
				case "005" => return Some(Array())
				case "265" => return Some(Array())
				case "266" => return Some(Array())

				case _ =>
			}
		}

		//Stage 3: Check agains defs
		{
			for (pdef <- IRCParserUtil.definitions; if pdef.length > 0; if !pdef.startsWith("#"))
			{
				val className = pdef.split("\t")(0)
				val pattern = pdef.split("\t")(1)
				val matched = parseMatch(line, pattern)
				if (matched.isDefined)
				{
					val cls = Class.forName("org.roelf.scala.aerirc.messages." + className)
					val args = matched.get.toBuffer //I know we like to be immutable, but in this context we need to actually add stuff to it.
					args.prepend(sender)
					val types = IRCParserUtil.getConstructorArgTypes(cls)

					//IMPORTANT: It is possible it should've been args.size+1. If it fails horrible, this is why.
					types.slice(args.size, types.size).map(o => o.getSimpleName.toLowerCase).foreach
					{
						case "int" => args += (-1).asInstanceOf[AnyRef]
						case "string" => args += null
					}

					return Some(Array(IRCParserUtil.makeObject(cls, args.toArray).asInstanceOf[IRCMessage]))
				}
			}
		}

		Some(Array(UNKNOWN(sender, line.substring(0, line.indexOf(' ')).trim.toUpperCase, splitter(line.substring(line.indexOf(' ') + 1)))))
	}
}
