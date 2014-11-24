package org.roelf.scala.aerirc.parser

import org.roelf.scala.aerirc.messages.UNKNOWN
import org.roelf.scala.aerirc.test.AbstractSpec
import org.roelf.scala.aerirc.user.TUserPool

/**
 * Created by roelf on 10/7/14.
 */
class ParserSpec extends AbstractSpec {
	object pool extends TUserPool

	"IRCParser" should "return None if given null" in
	{
		IRCParser.parse(null, pool) should be(None)
	}

	it should "return None if given an empty string" in
	{
		IRCParser.parse("", pool) should be(None)
	}

	val session = scala.io.Source.fromURL(getClass.getResource("/verifysession.txt"))

	var ln = 1
	for (line <- session.getLines())
	{
		it should f"parse: verifysession.txt:$ln $line" in
		{
			val p = IRCParser.parse(line, pool)
			p.isDefined should be(right = true)
		}
		ln += 1
	}
}
