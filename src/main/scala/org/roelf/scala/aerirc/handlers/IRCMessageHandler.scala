package org.roelf.scala.aerirc.handlers

import org.roelf.scala.aerirc.messages._

/**
 * Created by roelf on 11/21/14.
 */
trait IRCMessageHandler[T <: IRCMessage] extends IRCHandler[T]
trait IRCUnknownMessageHandler extends IRCMessageHandler[IRCMessage]
trait IRCNoticeHandler extends IRCMessageHandler[NOTICE]
trait IRCJoinHandler extends IRCMessageHandler[JOIN]
trait IRCChannelMessageHandler extends IRCMessageHandler[MESSAGE]
trait IRCPrivateMessageHandler extends IRCMessageHandler[PRIVMSG]

