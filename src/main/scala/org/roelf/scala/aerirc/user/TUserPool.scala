package org.roelf.scala.aerirc.user

import org.roelf.scala.aerirc.IRCNetwork
import org.roelf.scala.aerirc.messages.WHO

import scala.collection.mutable
import scala.concurrent.Future

/**
 * Created by roelf on 11/22/14.
 */
trait TUserPool {
	private[user] val userPool = new mutable.HashSet[IRCUser]
	private var network: IRCNetwork = null

	private[aerirc] def setNetwork(n: IRCNetwork) = network = n

	private def parseMask(mask: String): (String, String, String) =
	{
		var nick: String = null
		val p2 = mask.split("!")(mask.split("!").size - 1)
		if (mask.split("!").size > 1)
			nick = mask.split("!")(0)
		val user = p2.split("@")(0)
		val host = p2.split("@")(1)
		(nick, user, host)
	}

	private def createNewUserFromMask(mask: String): IRCUser = {
		if (mask.contains("@"))
		{
			val m = parseMask(mask)
			val rc = new IRCUser
			rc._nickname = m._1
			rc._username = m._2
			rc._host = m._3
			rc
		}
		else
		{
			val rc = new IRCUser
			rc._host = mask
			rc
		}
	}

	private[aerirc] def removeUserFromPool(u: IRCUser) = userPool -= u

	def isMe(u: IRCUser): Boolean = u.nickname == network.nick

	def userFromUser(nick: String): IRCUser = {
		val filtered = userPool.filter(u => nick == u.nickname)
		if (filtered.size == 0)
			null
		else
			filtered.head
	}

	def userFromHostMask(mask: String): IRCUser = {
		val filtered = userPool.filter(u => mask.split("!")(mask.split("!").size-1) == u.matchAble)
		if (filtered.size == 0)
		{
			val user = createNewUserFromMask(mask)
			userPool += user
			user
		}
		else
		{
			val user = filtered.head
			val m = parseMask(mask)
			if (m._1 != null)
				user._nickname = m._1
			if (m._2 != null)
				user._username = m._2
			if (m._3 != null)
				user._host = m._3
			userPool -= filtered.head
			userPool += user
			user
		}
	}
}
