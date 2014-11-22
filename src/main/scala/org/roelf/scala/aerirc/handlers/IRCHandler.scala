package org.roelf.scala.aerirc.handlers

import org.roelf.scala.aerirc.IRCChannel

/**
 * Created by roelf on 11/21/14.
 */

trait THandleAbleType

trait IRCHandler[T <: THandleAbleType] {
	def handle(message: T): EWasHandled
	private[aerirc] final def handle(message: Any): EWasHandled = handle(message.asInstanceOf[T])
}

trait IRCPreJoinHandler extends IRCHandler[IRCChannel]
