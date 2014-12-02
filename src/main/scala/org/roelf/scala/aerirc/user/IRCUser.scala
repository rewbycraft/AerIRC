package org.roelf.scala.aerirc.user

import org.roelf.scala.aerirc.handlers.THandleAbleType

/**
 * IRCUser represents an unique user on a network. Please do not store this object, it is switched out rather often. Please store IRCUser.matchable instead. If you need to retrieve this user, please call TUserPool.userFromMatchable
 */
class IRCUser private[user] () extends THandleAbleType {
	private[aerirc] var _nickname: String = null
	private[aerirc] var _username: String = null
	private[aerirc] var _host: String = null
	def nickname: String = _nickname
	def username: String = _username
	def host: String = _host

	def mask = {
		var rc = ""
		if (nickname != null)
			rc += nickname
		if (username != null)
			rc += (if (nickname != null) "!" else "") + username
		if (host != null)
			rc += (if (username != null) "@" else "") + host
		rc
	}

	/**
	 * Please only store the value of this, rather than the IRCUser object itself.
	 */
	def matchable = (if (username != null) username else "") + (if ((username != null) && (host != null)) "@" else "") + (if (host != null) host else "")

	override def toString: String = mask
	override def hashCode(): Int = matchable.hashCode
	override def equals(obj: scala.Any): Boolean = hashCode().equals(obj.hashCode())
}
