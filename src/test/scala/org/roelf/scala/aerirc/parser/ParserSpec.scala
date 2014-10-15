package org.roelf.scala.aerirc.parser

import org.roelf.scala.aerirc.UNKNOWN
import org.roelf.scala.aerirc.test.AbstractSpec

/**
 * Created by roelf on 10/7/14.
 */
class ParserSpec extends AbstractSpec {

	"IRCParser" should "return None if given null" in
	{
		IRCParser.parse(null) should be(None)
	}

	it should "return None if given an empty string" in
	{
		IRCParser.parse("") should be(None)
	}

	val session = scala.io.Source.fromURL(getClass.getResource("/verifysession.txt"))

	var ln = 1
	for (line <- session.getLines())
	{
		it should f"parse: verifysession.txt:$ln $line" in
		{
			val p = IRCParser.parse(line)
			p.isDefined should be(right = true)
		}
		ln += 1
	}
}
