package org.roelf.scala.aerirc.handlers

import org.roelf.scala.aerirc.messages._

/**
 * Created by roelf on 11/21/14.
 */
trait IRCMessageHandler[T <: IRCMessage] extends IRCHandler[T]
trait IRCUnknownMessageHandler extends IRCMessageHandler[IRCMessage]
trait IRCNoticeMessageHandler extends IRCMessageHandler[NOTICE]
trait IRCJoinMessageHandler extends IRCMessageHandler[JOIN]
trait IRCChannelMessageHandler extends IRCMessageHandler[MESSAGE]
trait IRCPrivateMessageHandler extends IRCMessageHandler[PRIVMSG]
trait IRCJoinedChannelMessageHandler extends IRCMessageHandler[JOINED]
trait IRCRegisteredMessageHandler extends IRCMessageHandler[REGISTERED]
trait IRCPartedChannelMessageHandler extends IRCMessageHandler[PARTED]
trait IRCPartMessageHandler extends IRCMessageHandler[PART]
trait IRCWhoMessageHandler extends IRCMessageHandler[RPL_WHOREPLY]
