package org.roelf.scala.aerirc

/**
 *
 * Each subclass will have a sender associated with them. Note that this should always be null if you're creating the instance.
 * You can leave arguments as null if you don't know what they do. Usually. (-1 is equal to null for the Int params)
 *
 * Created by roelf on 10/7/14.
 */
abstract class IRCMessage

/**
 * This is just an easier way to include the numeric id.
 */
abstract class IRCNumericMessage(val numeric: Int) extends IRCMessage

/**
 * Automatic parser definition gen FTW!
 */
abstract class IRCAutoNumericMessage(_numeric: Int) extends IRCNumericMessage(_numeric)

case class UNKNOWN(sender: String, cmd: String, args: Array[String]) extends IRCMessage

case class PASS(sender: String, password: String) extends IRCMessage

case class NICK(sender: String, nick: String, hopcount: Int) extends IRCMessage

case class USER(sender: String, username: String, hostname: String, serverName: String, realName: String) extends IRCMessage

case class SERVER(sender: String, servername: String, hopcount: Int, info: String) extends IRCMessage

case class OPER(sender: String, user: String, password: String) extends IRCMessage

case class QUIT(sender: String, nickname: String, comment: String) extends IRCMessage

case class SQUIT(sender: String, server: String, comment: String) extends IRCMessage

case class JOIN(sender: String, channel: String, key: String) extends IRCMessage

case class PART(sender: String, channel: String, message: String) extends IRCMessage

case class MODE(sender: String, target: String, mode: Char, wasApplied: Boolean, params: String) extends IRCMessage

case class TOPIC(sender: String, channel: String, topic: String) extends IRCMessage

case class NAMES(sender: String, channel: String) extends IRCMessage

case class LIST(sender: String, channel: String, server: String) extends IRCMessage

case class INVITE(sender: String, nickname: String, channel: String) extends IRCMessage

case class KICK(sender: String, channel: String, user: String, comment: String) extends IRCMessage

case class VERSION(sender: String, server: String) extends IRCMessage

case class STATS(sender: String, query: String, server: String) extends IRCMessage

case class LINKS(sender: String, remoteserver: String, servermask: String) extends IRCMessage

case class TIME(sender: String, server: String) extends IRCMessage

case class CONNECT(sender: String, targetserver: String, port: Int, remoteserver: String) extends IRCMessage

case class TRACE(sender: String, server: String) extends IRCMessage

case class ADMIN(sender: String, server: String) extends IRCMessage

case class INFO(sender: String, server: String) extends IRCMessage

case class PRIVMSG(sender: String, receiver: String, message: String) extends IRCMessage

case class NOTICE(sender: String, nickname: String, text: String) extends IRCMessage

case class WHO(sender: String, name: String, o: String) extends IRCMessage

case class WHOIS(sender: String, nickmask: String) extends IRCMessage

case class WHOWAS(sender: String, nickname: String, count: Int, server: String) extends IRCMessage

case class KILL(sender: String, nickname: String, comment: String) extends IRCMessage

case class PING(sender: String, payload: String) extends IRCMessage

case class PONG(sender: String, payload: String) extends IRCMessage

case class ERROR(sender: String, error: String) extends IRCMessage

case class AWAY(sender: String, message: String) extends IRCMessage

case class REHASH(sender: String) extends IRCMessage

case class RESTART(sender: String) extends IRCMessage

case class SUMMON(sender: String, user: String, server: String) extends IRCMessage

case class USERS(sender: String, server: String) extends IRCMessage

case class WALLOPS(sender: String, message: String) extends IRCMessage

case class USERHOST(sender: String, nickname: String) extends IRCMessage

case class ISON(sender: String, nickname: String) extends IRCMessage

case class SERVICE(sender: String, nickname: String, reserved: String, distribution: String, tpe: String, reserved2: String, info: String) extends IRCMessage

case class ERR_NOSUCHNICK(sender: String, nickname: String, message: String) extends IRCAutoNumericMessage(401)

case class ERR_NOSUCHSERVER(sender: String, server: String, message: String) extends IRCAutoNumericMessage(402)

case class ERR_NOSUCHCHANNEL(sender: String, channel: String, message: String) extends IRCAutoNumericMessage(403)

case class ERR_CANNOTSENDTOCHAN(sender: String, channel: String, message: String) extends IRCAutoNumericMessage(404)

case class ERR_TOOMANYCHANNELS(sender: String, channel: String, message: String) extends IRCAutoNumericMessage(405)

case class ERR_WASNOSUCHNICK(sender: String, nickname: String, message: String) extends IRCAutoNumericMessage(406)

case class ERR_TOOMANYTARGETS(sender: String, target: String, message: String) extends IRCAutoNumericMessage(407)

case class ERR_NOORIGIN(sender: String, message: String) extends IRCAutoNumericMessage(409)

case class ERR_NORECIPIENT(sender: String, message: String) extends IRCAutoNumericMessage(411)

case class ERR_NOTEXTTOSEND(sender: String, message: String) extends IRCAutoNumericMessage(412)

case class ERR_NOTOPLEVEL(sender: String, mask: String, message: String) extends IRCAutoNumericMessage(413)

case class ERR_WILDTOPLEVEL(sender: String, mask: String, message: String) extends IRCAutoNumericMessage(414)

case class ERR_UNKNOWNCOMMAND(sender: String, command: String, message: String) extends IRCAutoNumericMessage(421)

case class ERR_NOMOTD(sender: String, message: String) extends IRCAutoNumericMessage(422)

case class ERR_NOADMININFO(sender: String, server: String, message: String) extends IRCAutoNumericMessage(423)

case class ERR_FILEERROR(sender: String, message: String) extends IRCAutoNumericMessage(424)

case class ERR_NONICKNAMEGIVEN(sender: String, message: String) extends IRCAutoNumericMessage(431)

case class ERR_ERRONEUSNICKNAME(sender: String, message: String) extends IRCAutoNumericMessage(432)

case class ERR_NICKNAMEINUSE(sender: String, nickname: String, message: String) extends IRCAutoNumericMessage(433)

case class ERR_NICKCOLLISION(sender: String, nickname: String, message: String) extends IRCAutoNumericMessage(436)

case class ERR_USERNOTINCHANNEL(sender: String, nickname: String, channel: String, message: String) extends IRCAutoNumericMessage(441)

case class ERR_NOTONCHANNEL(sender: String, channel: String, message: String) extends IRCAutoNumericMessage(442)

case class ERR_USERONCHANNEL(sender: String, user: String, channel: String, message: String) extends IRCAutoNumericMessage(443)

case class ERR_NOLOGIN(sender: String, user: String, message: String) extends IRCAutoNumericMessage(444)

case class ERR_SUMMONDISABLED(sender: String, message: String) extends IRCAutoNumericMessage(445)

case class ERR_USERSDISABLED(sender: String, message: String) extends IRCAutoNumericMessage(446)

case class ERR_NOTREGISTERED(sender: String, message: String) extends IRCAutoNumericMessage(451)

case class ERR_NEEDMOREPARAMS(sender: String, command: String, message: String) extends IRCAutoNumericMessage(461)

case class ERR_ALREADYREGISTERED(sender: String, message: String) extends IRCAutoNumericMessage(462)

case class ERR_NOPERMFORHOST(sender: String, message: String) extends IRCAutoNumericMessage(463)

case class ERR_PASSWDMISMATCH(sender: String, message: String) extends IRCAutoNumericMessage(464)

case class ERR_YOUREBANNEDCREEP(sender: String, message: String) extends IRCAutoNumericMessage(465)

case class ERR_KEYSET(sender: String, channel: String, message: String) extends IRCAutoNumericMessage(467)

case class ERR_CHANNELISFULL(sender: String, channel: String, message: String) extends IRCAutoNumericMessage(471)

case class ERR_UNKNOWNMODE(sender: String, mode: Char, message: String) extends IRCNumericMessage(472)

case class ERR_INVITEONLYCHAN(sender: String, channel: String, message: String) extends IRCAutoNumericMessage(473)

case class ERR_BANNEDFROMCHAN(sender: String, channel: String, message: String) extends IRCAutoNumericMessage(474)

case class ERR_BADCHANNELKEY(sender: String, channel: String, message: String) extends IRCAutoNumericMessage(475)

case class ERR_NOPRIVILEGES(sender: String, message: String) extends IRCAutoNumericMessage(481)

case class ERR_CHANOPRIVSNEEDED(sender: String, channel: String, message: String) extends IRCAutoNumericMessage(482)

case class ERR_CANTKILLSERVER(sender: String, message: String) extends IRCAutoNumericMessage(483)

case class ERR_NOOPERHOST(sender: String, message: String) extends IRCAutoNumericMessage(491)

case class ERR_UMODEUNKNOWNFLAG(sender: String, message: String) extends IRCAutoNumericMessage(501)

case class ERR_USERSDONTMATCH(sender: String, message: String) extends IRCAutoNumericMessage(502)

case class RPL_WELCOME(sender: String, message: String) extends IRCAutoNumericMessage(1)

case class RPL_YOURHOST(sender: String, message: String) extends IRCAutoNumericMessage(2)

case class RPL_CREATED(sender: String, message: String) extends IRCAutoNumericMessage(3)

case class RPL_NONE(sender: String) extends IRCAutoNumericMessage(300)

case class RPL_USERHOST(sender: String, nickname: String, hostname: String, away: Boolean, oper: Boolean) extends IRCNumericMessage(302)

case class RPL_ISON(sender: String, nickname: String) extends IRCAutoNumericMessage(303)

case class RPL_AWAY(sender: String, nickname: String) extends IRCAutoNumericMessage(304)

case class RPL_UNAWAY(sender: String, message: String) extends IRCAutoNumericMessage(305)

case class RPL_NOWAWAY(sender: String, message: String) extends IRCAutoNumericMessage(306)

case class RPL_WHOISUSER(sender: String, nickname: String, user: String, host: String, realname: String) extends IRCAutoNumericMessage(311)

case class RPL_WHOISSERVER(sender: String, nickname: String, server: String, serverinfo: String) extends IRCAutoNumericMessage(312)

case class RPL_WHOISOPERATOR(sender: String, nickname: String, message: String) extends IRCAutoNumericMessage(313)

case class RPL_WHOISIDLE(sender: String, nickname: String, time: Int, message: String) extends IRCAutoNumericMessage(317)

case class RPL_ENDOFWHIOS(sender: String, nickname: String, message: String) extends IRCAutoNumericMessage(318)

case class RPL_WHOISCHANNELS(sender: String, nickname: String, channel: String, op: Boolean, voice: Boolean) extends IRCNumericMessage(319)

case class RPL_WHOWASUSER(sender: String, nickname: String, user: String, host: String, realname: String) extends IRCAutoNumericMessage(314)

case class RPL_ENDOFWHOWAS(sender: String, nickname: String, message: String) extends IRCAutoNumericMessage(369)

case class RPL_LISTSTART(sender: String, message: String) extends IRCAutoNumericMessage(321)

case class RPL_LIST(sender: String, channel: String, visible: Int, topic: String) extends IRCAutoNumericMessage(322)

case class RPL_LISTEND(sender: String, message: String) extends IRCAutoNumericMessage(323)

case class RPL_CHANNELMODEIS(sender: String, channel: String, mode: String, modeparams: String) extends IRCAutoNumericMessage(324)

case class RPL_NOTOPIC(sender: String, channel: String, message: String) extends IRCAutoNumericMessage(331)

case class RPL_TOPIC(sender: String, channel: String, topic: String) extends IRCAutoNumericMessage(332)

case class RPL_INVITING(sender: String, channel: String, nickname: String) extends IRCAutoNumericMessage(341)

case class RPL_SUMMONING(sender: String, user: String, message: String) extends IRCAutoNumericMessage(342)

case class RPL_VERSION(sender: String, version: String, server: String, comment: String) extends IRCAutoNumericMessage(351)

case class RPL_WHOREPLY(sender: String, channel: String, user: String, host: String, server: String, nickname: String, something: String, something2: String, hopcount: Int, realname: String) extends IRCAutoNumericMessage(352)

case class RPL_ENDOFWHO(sender: String, name: String, message: String) extends IRCAutoNumericMessage(315)

case class RPL_NAMREPLY(sender: String, channel: String, nickname: String, op: Boolean, voice: Boolean) extends IRCNumericMessage(353)

case class RPL_ENDOFNAMES(sender: String, channel: String, message: String) extends IRCAutoNumericMessage(366)

case class RPL_LINKS(sender: String, mask: String, server: String, hopcount: Int, serverinfo: String) extends IRCAutoNumericMessage(364)

case class RPL_ENDOFLINKS(sender: String, mask: String, message: String) extends IRCAutoNumericMessage(365)

case class RPL_BANLIST(sender: String, channel: String, banid: String) extends IRCAutoNumericMessage(367)

case class RPL_ENDOFBANLIST(sender: String, channel: String, message: String) extends IRCAutoNumericMessage(368)

case class RPL_INFO(sender: String, message: String) extends IRCAutoNumericMessage(371)

case class RPL_ENDOFINFO(sender: String, message: String) extends IRCAutoNumericMessage(374)

case class RPL_MOTDSTART(sender: String, message: String) extends IRCAutoNumericMessage(375)

case class RPL_MOTD(sender: String, to: String, message: String) extends IRCAutoNumericMessage(372)

case class RPL_ENDOFMOTD(sender: String, message: String) extends IRCAutoNumericMessage(376)

case class RPL_YOUREOPER(sender: String, message: String) extends IRCAutoNumericMessage(381)

case class RPL_REHASHING(sender: String, message: String) extends IRCAutoNumericMessage(382)

case class RPL_TIME(sender: String, server: String, time: String) extends IRCAutoNumericMessage(391)

case class RPL_USERSSTART(sender: String, message: String) extends IRCAutoNumericMessage(392)

case class RPL_USERS(sender: String, message: String) extends IRCAutoNumericMessage(393)

case class RPL_ENDOFUSERS(sender: String, message: String) extends IRCAutoNumericMessage(394)

case class RPL_NOUSERS(sender: String, message: String) extends IRCAutoNumericMessage(395)

case class RPL_TRACELINK(sender: String, version: String, destination: String, nextserver: String) extends IRCAutoNumericMessage(200)

case class RPL_TRACECONNECTING(sender: String, clazz: String, server: String) extends IRCAutoNumericMessage(201)

case class RPL_TRACEHANDSHAKE(sender: String, clazz: String, server: String) extends IRCAutoNumericMessage(202)

case class RPL_TRACEUNKNOWN(sender: String, clazz: String, ip: String) extends IRCAutoNumericMessage(203)

case class RPL_TRACEOPERATOR(sender: String, clazz: String, nickname: String) extends IRCAutoNumericMessage(204)

case class RPL_TRACEUSER(sender: String, clazz: String, nickname: String) extends IRCAutoNumericMessage(205)

case class RPL_TRACESERVER(sender: String, clazz: String, s: Int, c: Int, server: String, person: String) extends IRCAutoNumericMessage(206)

case class RPL_TRACENEWTYPE(sender: String, newtype: String, clientname: String) extends IRCAutoNumericMessage(208)

case class RPL_TRACELOG(sender: String, logfile: String, debuglevel: String) extends IRCAutoNumericMessage(261)

case class RPL_STATSLINKINFO(sender: String, linkname: String, sendq: String, sentmessages: Int, sentbytes: Int, receivedmessages: Int, receivedbytes: Int, timeopen: String) extends IRCAutoNumericMessage(211)

case class RPL_STATSCOMMANDS(sender: String, command: String, count: Int) extends IRCAutoNumericMessage(212)

case class RPL_STATSCLINE(sender: String, host: String, name: String, port: Int, clazz: String) extends IRCAutoNumericMessage(213)

case class RPL_STATSNLINE(sender: String, host: String, name: String, port: Int, clazz: String) extends IRCAutoNumericMessage(214)

case class RPL_STATSILINE(sender: String, host: String, name: String, port: Int, clazz: String) extends IRCAutoNumericMessage(215)

case class RPL_STATSKLINE(sender: String, host: String, name: String, port: Int, clazz: String) extends IRCAutoNumericMessage(216)

case class RPL_STATSYLINE(sender: String, clazz: String, pingfreq: Int, connectfreq: Int, maxsendq: Int) extends IRCAutoNumericMessage(218)

case class RPL_ENDOFSTATS(sender: String, stat: Char, message: String) extends IRCNumericMessage(219)

case class RPL_STATSLLINE(sender: String, host: String, name: String, port: Int, clazz: String) extends IRCAutoNumericMessage(241)

case class RPL_STATSUPTIME(sender: String, message: String) extends IRCAutoNumericMessage(242)

case class RPL_STATSOLINE(sender: String, hostmask: String, name: String) extends IRCAutoNumericMessage(243)

case class RPL_STATSHLINE(sender: String, hostmask: String, servername: String) extends IRCAutoNumericMessage(244)

case class RPL_UMODEIS(sender: String, modestring: String) extends IRCAutoNumericMessage(221)

case class RPL_LUSERCLIENT(sender: String, usercount: Int, invisibleusers: Int, servers: Int, message: String) extends IRCAutoNumericMessage(251)

case class RPL_LUSEROP(sender: String, opers: Int, message: String) extends IRCAutoNumericMessage(252)

case class RPL_LUSERUNKNOWN(sender: String, unknownconnections: Int, message: String) extends IRCAutoNumericMessage(253)

case class RPL_LUSERCHANNELS(sender: String, channels: Int, message: String) extends IRCAutoNumericMessage(254)

case class RPL_LUSERME(sender: String, clients: Int, servers: Int, message: String) extends IRCAutoNumericMessage(255)

case class RPL_ADMINME(sender: String, server: String, message: String) extends IRCAutoNumericMessage(256)

case class RPL_ADMINLOC1(sender: String, message: String) extends IRCAutoNumericMessage(257)

case class RPL_ADMINLOC2(sender: String, message: String) extends IRCAutoNumericMessage(258)

case class RPL_ADMINEMAIL(sender: String, message: String) extends IRCAutoNumericMessage(259)

case class RPL_UNIQUEID(sender: String, id: String, message: String) extends IRCAutoNumericMessage(42)
