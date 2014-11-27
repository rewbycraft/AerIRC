package org.roelf.scala.aerirc.user

/**
 * Created by roelf on 11/22/14.
 */
class IRCUser {
	private[user] var _nickname: String = null
	private[user] var _username: String = null
	private[user] var _host: String = null
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

	def matchAble = (if (username != null) username else "") + (if (host != null) "@" else "") + (if (host != null) host else "")

	override def toString: String = mask
	override def hashCode(): Int = matchAble.hashCode
	override def equals(obj: scala.Any): Boolean = hashCode().equals(obj.hashCode())
}
