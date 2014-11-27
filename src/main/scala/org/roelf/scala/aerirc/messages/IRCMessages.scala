package org.roelf.scala.aerirc.messages

import org.roelf.scala.aerirc.handlers.THandleAbleType
import org.roelf.scala.aerirc.user.IRCUser

/**
 *
 * Each subclass will have a sender associated with them. Note that this should always be null if you're creating the instance.
 * You can leave arguments as null if you don't know what they do. Usually. (-1 is equal to null for the Int params)
 *
 * Created by roelf on 10/7/14.
 */
abstract class IRCMessage extends THandleAbleType

/**
 * This is just an easier way to include the numeric id.
 */
abstract class IRCNumericMessage(val numeric: Int) extends IRCMessage

/*
 * Not directly IRC related messages. Mostly abstractions.
 */
case class MESSAGE(sender: IRCUser, message: String) extends IRCMessage
case class REGISTERED(sender: IRCUser) extends IRCMessage
case class JOINED(sender: IRCUser) extends IRCMessage
case class PARTED(sender: IRCUser) extends IRCMessage

/**
 * Automatic parser definition gen FTW!
 */
abstract class IRCAutoNumericMessage(_numeric: Int) extends IRCNumericMessage(_numeric)

case class UNKNOWN(sender: IRCUser, cmd: String, args: Array[String]) extends IRCMessage

case class PASS(sender: IRCUser, password: String) extends IRCMessage

case class NICK(sender: IRCUser, nick: String, hopcount: Int) extends IRCMessage

case class USER(sender: IRCUser, username: String, hostname: String, serverName: String, realName: String) extends IRCMessage

case class SERVER(sender: IRCUser, servername: String, hopcount: Int, info: String) extends IRCMessage

case class OPER(sender: IRCUser, user: String, password: String) extends IRCMessage

case class QUIT(sender: IRCUser, nickname: String, comment: String) extends IRCMessage

case class SQUIT(sender: IRCUser, server: String, comment: String) extends IRCMessage

case class JOIN(sender: IRCUser, channel: String, key: String) extends IRCMessage

case class PART(sender: IRCUser, channel: String, message: String) extends IRCMessage

case class MODE(sender: IRCUser, target: String, channel: String, mode: Char, wasApplied: Boolean) extends IRCMessage

case class TOPIC(sender: IRCUser, channel: String, topic: String) extends IRCMessage

case class NAMES(sender: IRCUser, channel: String) extends IRCMessage

case class LIST(sender: IRCUser, channel: String, server: String) extends IRCMessage

case class INVITE(sender: IRCUser, nickname: String, channel: String) extends IRCMessage

case class KICK(sender: IRCUser, channel: String, user: String, comment: String) extends IRCMessage

case class VERSION(sender: IRCUser, server: String) extends IRCMessage

case class STATS(sender: IRCUser, query: String, server: String) extends IRCMessage

case class LINKS(sender: IRCUser, remoteserver: String, servermask: String) extends IRCMessage

case class TIME(sender: IRCUser, server: String) extends IRCMessage

case class CONNECT(sender: IRCUser, targetserver: String, port: Int, remoteserver: String) extends IRCMessage

case class TRACE(sender: IRCUser, server: String) extends IRCMessage

case class ADMIN(sender: IRCUser, server: String) extends IRCMessage

case class INFO(sender: IRCUser, server: String) extends IRCMessage

case class PRIVMSG(sender: IRCUser, receiver: String, message: String) extends IRCMessage

case class NOTICE(sender: IRCUser, nickname: String, text: String) extends IRCMessage

case class WHO(sender: IRCUser, name: String, o: String) extends IRCMessage

case class WHOIS(sender: IRCUser, nickmask: String) extends IRCMessage

case class WHOWAS(sender: IRCUser, nickname: String, count: Int, server: String) extends IRCMessage

case class KILL(sender: IRCUser, nickname: String, comment: String) extends IRCMessage

case class PING(sender: IRCUser, payload: String) extends IRCMessage

case class PONG(sender: IRCUser, payload: String) extends IRCMessage

case class ERROR(sender: IRCUser, error: String) extends IRCMessage

case class AWAY(sender: IRCUser, message: String) extends IRCMessage

case class REHASH(sender: IRCUser) extends IRCMessage

case class RESTART(sender: IRCUser) extends IRCMessage

case class SUMMON(sender: IRCUser, user: String, server: String) extends IRCMessage

case class USERS(sender: IRCUser, server: String) extends IRCMessage

case class WALLOPS(sender: IRCUser, message: String) extends IRCMessage

case class USERHOST(sender: IRCUser, nickname: String) extends IRCMessage

case class ISON(sender: IRCUser, nickname: String) extends IRCMessage

case class SERVICE(sender: IRCUser, nickname: String, reserved: String, distribution: String, tpe: String, reserved2: String, info: String) extends IRCMessage

case class ERR_NOSUCHNICK(sender: IRCUser, nickname: String, message: String) extends IRCAutoNumericMessage(401)

case class ERR_NOSUCHSERVER(sender: IRCUser, server: String, message: String) extends IRCAutoNumericMessage(402)

case class ERR_NOSUCHCHANNEL(sender: IRCUser, channel: String, message: String) extends IRCAutoNumericMessage(403)

case class ERR_CANNOTSENDTOCHAN(sender: IRCUser, channel: String, message: String) extends IRCAutoNumericMessage(404)

case class ERR_TOOMANYCHANNELS(sender: IRCUser, channel: String, message: String) extends IRCAutoNumericMessage(405)

case class ERR_WASNOSUCHNICK(sender: IRCUser, nickname: String, message: String) extends IRCAutoNumericMessage(406)

case class ERR_TOOMANYTARGETS(sender: IRCUser, target: String, message: String) extends IRCAutoNumericMessage(407)

case class ERR_NOORIGIN(sender: IRCUser, message: String) extends IRCAutoNumericMessage(409)

case class ERR_NORECIPIENT(sender: IRCUser, message: String) extends IRCAutoNumericMessage(411)

case class ERR_NOTEXTTOSEND(sender: IRCUser, message: String) extends IRCAutoNumericMessage(412)

case class ERR_NOTOPLEVEL(sender: IRCUser, mask: String, message: String) extends IRCAutoNumericMessage(413)

case class ERR_WILDTOPLEVEL(sender: IRCUser, mask: String, message: String) extends IRCAutoNumericMessage(414)

case class ERR_UNKNOWNCOMMAND(sender: IRCUser, command: String, message: String) extends IRCAutoNumericMessage(421)

case class ERR_NOMOTD(sender: IRCUser, message: String) extends IRCAutoNumericMessage(422)

case class ERR_NOADMININFO(sender: IRCUser, server: String, message: String) extends IRCAutoNumericMessage(423)

case class ERR_FILEERROR(sender: IRCUser, message: String) extends IRCAutoNumericMessage(424)

case class ERR_NONICKNAMEGIVEN(sender: IRCUser, message: String) extends IRCAutoNumericMessage(431)

case class ERR_ERRONEUSNICKNAME(sender: IRCUser, message: String) extends IRCAutoNumericMessage(432)

case class ERR_NICKNAMEINUSE(sender: IRCUser, nickname: String, message: String) extends IRCAutoNumericMessage(433)

case class ERR_NICKCOLLISION(sender: IRCUser, nickname: String, message: String) extends IRCAutoNumericMessage(436)

case class ERR_USERNOTINCHANNEL(sender: IRCUser, nickname: String, channel: String, message: String) extends IRCAutoNumericMessage(441)

case class ERR_NOTONCHANNEL(sender: IRCUser, channel: String, message: String) extends IRCAutoNumericMessage(442)

case class ERR_USERONCHANNEL(sender: IRCUser, user: String, channel: String, message: String) extends IRCAutoNumericMessage(443)

case class ERR_NOLOGIN(sender: IRCUser, user: String, message: String) extends IRCAutoNumericMessage(444)

case class ERR_SUMMONDISABLED(sender: IRCUser, message: String) extends IRCAutoNumericMessage(445)

case class ERR_USERSDISABLED(sender: IRCUser, message: String) extends IRCAutoNumericMessage(446)

case class ERR_NOTREGISTERED(sender: IRCUser, message: String) extends IRCAutoNumericMessage(451)

case class ERR_NEEDMOREPARAMS(sender: IRCUser, command: String, message: String) extends IRCAutoNumericMessage(461)

case class ERR_ALREADYREGISTERED(sender: IRCUser, message: String) extends IRCAutoNumericMessage(462)

case class ERR_NOPERMFORHOST(sender: IRCUser, message: String) extends IRCAutoNumericMessage(463)

case class ERR_PASSWDMISMATCH(sender: IRCUser, message: String) extends IRCAutoNumericMessage(464)

case class ERR_YOUREBANNEDCREEP(sender: IRCUser, message: String) extends IRCAutoNumericMessage(465)

case class ERR_KEYSET(sender: IRCUser, channel: String, message: String) extends IRCAutoNumericMessage(467)

case class ERR_CHANNELISFULL(sender: IRCUser, channel: String, message: String) extends IRCAutoNumericMessage(471)

case class ERR_UNKNOWNMODE(sender: IRCUser, mode: Char, message: String) extends IRCNumericMessage(472)

case class ERR_INVITEONLYCHAN(sender: IRCUser, channel: String, message: String) extends IRCAutoNumericMessage(473)

case class ERR_BANNEDFROMCHAN(sender: IRCUser, channel: String, message: String) extends IRCAutoNumericMessage(474)

case class ERR_BADCHANNELKEY(sender: IRCUser, channel: String, message: String) extends IRCAutoNumericMessage(475)

case class ERR_NOPRIVILEGES(sender: IRCUser, message: String) extends IRCAutoNumericMessage(481)

case class ERR_CHANOPRIVSNEEDED(sender: IRCUser, channel: String, message: String) extends IRCAutoNumericMessage(482)

case class ERR_CANTKILLSERVER(sender: IRCUser, message: String) extends IRCAutoNumericMessage(483)

case class ERR_NOOPERHOST(sender: IRCUser, message: String) extends IRCAutoNumericMessage(491)

case class ERR_UMODEUNKNOWNFLAG(sender: IRCUser, message: String) extends IRCAutoNumericMessage(501)

case class ERR_USERSDONTMATCH(sender: IRCUser, message: String) extends IRCAutoNumericMessage(502)

case class RPL_WELCOME(sender: IRCUser, message: String) extends IRCAutoNumericMessage(1)

case class RPL_YOURHOST(sender: IRCUser, message: String) extends IRCAutoNumericMessage(2)

case class RPL_CREATED(sender: IRCUser, message: String) extends IRCAutoNumericMessage(3)

case class RPL_NONE(sender: IRCUser) extends IRCAutoNumericMessage(300)

case class RPL_USERHOST(sender: IRCUser, nickname: String, hostname: String, away: Boolean, oper: Boolean) extends IRCNumericMessage(302)

case class RPL_ISON(sender: IRCUser, nickname: String) extends IRCAutoNumericMessage(303)

case class RPL_AWAY(sender: IRCUser, nickname: String) extends IRCAutoNumericMessage(304)

case class RPL_UNAWAY(sender: IRCUser, message: String) extends IRCAutoNumericMessage(305)

case class RPL_NOWAWAY(sender: IRCUser, message: String) extends IRCAutoNumericMessage(306)

case class RPL_WHOISUSER(sender: IRCUser, nickname: String, user: String, host: String, realname: String) extends IRCAutoNumericMessage(311)

case class RPL_WHOISSERVER(sender: IRCUser, nickname: String, server: String, serverinfo: String) extends IRCAutoNumericMessage(312)

case class RPL_WHOISOPERATOR(sender: IRCUser, nickname: String, message: String) extends IRCAutoNumericMessage(313)

case class RPL_WHOISIDLE(sender: IRCUser, nickname: String, time: Int, message: String) extends IRCAutoNumericMessage(317)

case class RPL_ENDOFWHIOS(sender: IRCUser, nickname: String, message: String) extends IRCAutoNumericMessage(318)

case class RPL_WHOISCHANNELS(sender: IRCUser, nickname: String, channel: String, op: Boolean, voice: Boolean) extends IRCNumericMessage(319)

case class RPL_WHOWASUSER(sender: IRCUser, nickname: String, user: String, host: String, realname: String) extends IRCAutoNumericMessage(314)

case class RPL_ENDOFWHOWAS(sender: IRCUser, nickname: String, message: String) extends IRCAutoNumericMessage(369)

case class RPL_LISTSTART(sender: IRCUser, message: String) extends IRCAutoNumericMessage(321)

case class RPL_LIST(sender: IRCUser, channel: String, visible: Int, topic: String) extends IRCAutoNumericMessage(322)

case class RPL_LISTEND(sender: IRCUser, message: String) extends IRCAutoNumericMessage(323)

case class RPL_CHANNELMODEIS(sender: IRCUser, channel: String, mode: String, modeparams: String) extends IRCAutoNumericMessage(324)

case class RPL_NOTOPIC(sender: IRCUser, channel: String, message: String) extends IRCAutoNumericMessage(331)

case class RPL_TOPIC(sender: IRCUser, channel: String, topic: String) extends IRCAutoNumericMessage(332)

case class RPL_INVITING(sender: IRCUser, channel: String, nickname: String) extends IRCAutoNumericMessage(341)

case class RPL_SUMMONING(sender: IRCUser, user: String, message: String) extends IRCAutoNumericMessage(342)

case class RPL_VERSION(sender: IRCUser, version: String, server: String, comment: String) extends IRCAutoNumericMessage(351)

case class RPL_WHOREPLY(sender: IRCUser, channel: String, user: String, host: String, server: String, nickname: String, something: String, hopcount: Int, realname: String) extends IRCAutoNumericMessage(352)

case class RPL_ENDOFWHO(sender: IRCUser, name: String, message: String) extends IRCAutoNumericMessage(315)

case class RPL_NAMREPLY(sender: IRCUser, channel: String, nickname: String, op: Boolean, voice: Boolean) extends IRCNumericMessage(353)

case class RPL_ENDOFNAMES(sender: IRCUser, channel: String, message: String) extends IRCAutoNumericMessage(366)

case class RPL_LINKS(sender: IRCUser, mask: String, server: String, hopcount: Int, serverinfo: String) extends IRCAutoNumericMessage(364)

case class RPL_ENDOFLINKS(sender: IRCUser, mask: String, message: String) extends IRCAutoNumericMessage(365)

case class RPL_BANLIST(sender: IRCUser, channel: String, banid: String) extends IRCAutoNumericMessage(367)

case class RPL_ENDOFBANLIST(sender: IRCUser, channel: String, message: String) extends IRCAutoNumericMessage(368)

case class RPL_INFO(sender: IRCUser, message: String) extends IRCAutoNumericMessage(371)

case class RPL_ENDOFINFO(sender: IRCUser, message: String) extends IRCAutoNumericMessage(374)

case class RPL_MOTDSTART(sender: IRCUser, message: String) extends IRCAutoNumericMessage(375)

case class RPL_MOTD(sender: IRCUser, to: String, message: String) extends IRCAutoNumericMessage(372)

case class RPL_ENDOFMOTD(sender: IRCUser, message: String) extends IRCAutoNumericMessage(376)

case class RPL_YOUREOPER(sender: IRCUser, message: String) extends IRCAutoNumericMessage(381)

case class RPL_REHASHING(sender: IRCUser, message: String) extends IRCAutoNumericMessage(382)

case class RPL_TIME(sender: IRCUser, server: String, time: String) extends IRCAutoNumericMessage(391)

case class RPL_USERSSTART(sender: IRCUser, message: String) extends IRCAutoNumericMessage(392)

case class RPL_USERS(sender: IRCUser, message: String) extends IRCAutoNumericMessage(393)

case class RPL_ENDOFUSERS(sender: IRCUser, message: String) extends IRCAutoNumericMessage(394)

case class RPL_NOUSERS(sender: IRCUser, message: String) extends IRCAutoNumericMessage(395)

case class RPL_TRACELINK(sender: IRCUser, version: String, destination: String, nextserver: String) extends IRCAutoNumericMessage(200)

case class RPL_TRACECONNECTING(sender: IRCUser, clazz: String, server: String) extends IRCAutoNumericMessage(201)

case class RPL_TRACEHANDSHAKE(sender: IRCUser, clazz: String, server: String) extends IRCAutoNumericMessage(202)

case class RPL_TRACEUNKNOWN(sender: IRCUser, clazz: String, ip: String) extends IRCAutoNumericMessage(203)

case class RPL_TRACEOPERATOR(sender: IRCUser, clazz: String, nickname: String) extends IRCAutoNumericMessage(204)

case class RPL_TRACEUSER(sender: IRCUser, clazz: String, nickname: String) extends IRCAutoNumericMessage(205)

case class RPL_TRACESERVER(sender: IRCUser, clazz: String, s: Int, c: Int, server: String, person: String) extends IRCAutoNumericMessage(206)

case class RPL_TRACENEWTYPE(sender: IRCUser, newtype: String, clientname: String) extends IRCAutoNumericMessage(208)

case class RPL_TRACELOG(sender: IRCUser, logfile: String, debuglevel: String) extends IRCAutoNumericMessage(261)

case class RPL_STATSLINKINFO(sender: IRCUser, linkname: String, sendq: String, sentmessages: Int, sentbytes: Int, receivedmessages: Int, receivedbytes: Int, timeopen: String) extends IRCAutoNumericMessage(211)

case class RPL_STATSCOMMANDS(sender: IRCUser, command: String, count: Int) extends IRCAutoNumericMessage(212)

case class RPL_STATSCLINE(sender: IRCUser, host: String, name: String, port: Int, clazz: String) extends IRCAutoNumericMessage(213)

case class RPL_STATSNLINE(sender: IRCUser, host: String, name: String, port: Int, clazz: String) extends IRCAutoNumericMessage(214)

case class RPL_STATSILINE(sender: IRCUser, host: String, name: String, port: Int, clazz: String) extends IRCAutoNumericMessage(215)

case class RPL_STATSKLINE(sender: IRCUser, host: String, name: String, port: Int, clazz: String) extends IRCAutoNumericMessage(216)

case class RPL_STATSYLINE(sender: IRCUser, clazz: String, pingfreq: Int, connectfreq: Int, maxsendq: Int) extends IRCAutoNumericMessage(218)

case class RPL_ENDOFSTATS(sender: IRCUser, stat: Char, message: String) extends IRCNumericMessage(219)

case class RPL_STATSLLINE(sender: IRCUser, host: String, name: String, port: Int, clazz: String) extends IRCAutoNumericMessage(241)

case class RPL_STATSUPTIME(sender: IRCUser, message: String) extends IRCAutoNumericMessage(242)

case class RPL_STATSOLINE(sender: IRCUser, hostmask: String, name: String) extends IRCAutoNumericMessage(243)

case class RPL_STATSHLINE(sender: IRCUser, hostmask: String, servername: String) extends IRCAutoNumericMessage(244)

case class RPL_UMODEIS(sender: IRCUser, modestring: String) extends IRCAutoNumericMessage(221)

case class RPL_LUSERCLIENT(sender: IRCUser, usercount: Int, invisibleusers: Int, servers: Int, message: String) extends IRCAutoNumericMessage(251)

case class RPL_LUSEROP(sender: IRCUser, opers: Int, message: String) extends IRCAutoNumericMessage(252)

case class RPL_LUSERUNKNOWN(sender: IRCUser, unknownconnections: Int, message: String) extends IRCAutoNumericMessage(253)

case class RPL_LUSERCHANNELS(sender: IRCUser, channels: Int, message: String) extends IRCAutoNumericMessage(254)

case class RPL_LUSERME(sender: IRCUser, clients: Int, servers: Int, message: String) extends IRCAutoNumericMessage(255)

case class RPL_ADMINME(sender: IRCUser, server: String, message: String) extends IRCAutoNumericMessage(256)

case class RPL_ADMINLOC1(sender: IRCUser, message: String) extends IRCAutoNumericMessage(257)

case class RPL_ADMINLOC2(sender: IRCUser, message: String) extends IRCAutoNumericMessage(258)

case class RPL_ADMINEMAIL(sender: IRCUser, message: String) extends IRCAutoNumericMessage(259)

case class RPL_UNIQUEID(sender: IRCUser, id: String, message: String) extends IRCAutoNumericMessage(42)
