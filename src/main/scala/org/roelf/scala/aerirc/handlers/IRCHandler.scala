package org.roelf.scala.aerirc.handlers

import org.roelf.scala.aerirc.IRCChannel
import org.roelf.scala.aerirc.user.IRCUser

/**
 * Created by roelf on 11/21/14.
 */

private[aerirc] trait THandleAbleType

private[aerirc] trait IRCHandler[T <: THandleAbleType] {
	def handle(message: T): EWasHandled
	private[aerirc] final def handle(message: Any): EWasHandled = handle(message.asInstanceOf[T])
}

case class MODECHANGE(user: IRCUser, mode: Char, wasApplied: Boolean) extends THandleAbleType
case class NICKCHANGE(user: IRCUser, oldNick: String) extends THandleAbleType
case class DISCONNECT() extends THandleAbleType

trait IRCPreJoinHandler extends IRCHandler[IRCChannel]
trait IRCPostPartHandler extends IRCHandler[IRCChannel]
trait IRCModeHandler extends IRCHandler[MODECHANGE]
trait IRCNickHandler extends IRCHandler[NICKCHANGE]
trait IRCDisconnectHandler extends IRCHandler[DISCONNECT]
