package org.roelf.scala.aerirc.user

import org.roelf.scala.aerirc.IRCNetwork

import scala.collection.mutable

/**
 * Created by roelf on 11/22/14.
 */
private[aerirc] trait TUserPool {
	private[aerirc] val userPool = new mutable.HashSet[IRCUser]
	private var network: IRCNetwork = null

	private[aerirc] def setNetwork(n: IRCNetwork) = network = n

	private def parseMask(mask: String): (String, String, String) =
	{
		if (!mask.contains("@"))
			return (null, null, mask)
		var nick: String = null
		val p2 = mask.split("!")(mask.split("!").size - 1)
		if (mask.split("!").size > 1)
			nick = mask.split("!")(0)
		val user = p2.split("@")(0)
		val host = p2.split("@")(1)
		(nick, user, host)
	}

	private def createNewUserFromMask(mask: String): IRCUser =
	{
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

	private[aerirc] def removeUserFromPool(u: IRCUser) = userPool.synchronized
	{
		userPool -= u
	}

	def isMe(u: IRCUser): Boolean = u.nickname.equalsIgnoreCase(network.nick)

	def me: IRCUser = userFromUser(network.nick)

	def userFromUser(nick: String): IRCUser = userPool.synchronized
	{
		userPool.find(u => nick == u.nickname).orNull
	}

	def userFromHostMask(mask: String): IRCUser = userPool.synchronized
	{
		val filtered = userPool.filter(u => mask.split("!")(mask.split("!").size - 1) == u.matchable)
		if (filtered.size == 0)
		{
			val user = createNewUserFromMask(mask)
			userPool += user
			return user
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
			return user
		}
	}

	def userFromMatchable(matchable: String): IRCUser = userPool.synchronized {
		userPool.find(u => u.matchable.equalsIgnoreCase(matchable)).orNull
	}

	private[aerirc] def setUserNick(u: IRCUser, nick: String): Unit = userPool.synchronized
	{
		val oldUser = userPool.find(user => user.matchable == u.matchable).orNull
		val newUser = oldUser
		newUser._nickname = nick
		userPool -= oldUser
		userPool += newUser
	}
}
