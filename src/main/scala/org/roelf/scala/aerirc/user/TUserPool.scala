package org.roelf.scala.aerirc.user

import scala.collection.mutable

/**
 * Created by roelf on 11/22/14.
 */
trait TUserPool {
	private[user] val userPool = new mutable.HashSet[IRCUser]

	private def createNewUserFromMask(mask: String): IRCUser = {
		if (mask.contains("@"))
		{
			var nick: String = null
			val p2 = mask.split("!")(mask.split("!").size - 1)
			if (mask.split("!").size > 1)
				nick = mask.split("!")(0)
			val user = p2.split("@")(0)
			val host = p2.split("@")(1)
			val rc = new IRCUser
			rc._host = host
			rc._nickname = nick
			rc._username = user
			rc
		}
		else
		{
			val rc = new IRCUser
			rc._host = mask
			rc
		}
	}

	def userFromHostMask(mask: String): IRCUser = {
		val filtered = userPool.filter(u => mask.split("!")(mask.split("!").length-1) == u.toString)
		if (filtered.size == 0)
		{
			val user = createNewUserFromMask(mask)
			userPool += user
			user
		}
		else
			filtered.head
	}
}
