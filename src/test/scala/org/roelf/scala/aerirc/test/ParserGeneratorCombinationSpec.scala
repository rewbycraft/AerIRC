package org.roelf.scala.aerirc.test

import org.roelf.scala.aerirc.generator.IRCGenerator
import org.roelf.scala.aerirc.parser.IRCParser
import org.scalatest.Ignore

/**
 *
 * This test is outdated.
 * I'll probably rewrite it to first parse, then generate and then parse the message. The first parse and second parse results should match.
 *
 * Created by roelf on 10/8/14.
 */
//@Ignore
class ParserGeneratorCombinationSpec extends AbstractSpec {

	val session = scala.io.Source.fromURL(getClass.getResource("/verifysession.txt"))

	//This exists purely so 'it' can be used for the file since it gets spammy if not.
	"IRCParser + IRCGenerator" should "not fail true == true" in {
		true should be (true)
	}

	var linec = 1
	for (message <- session.getLines() if message.length > 0)
	{
		it should f"not return None the input for: verifysession.txt:$linec $message" in
		{
			val m = IRCParser.parse(message)
			m should not be None
			m.get.foreach(m1 =>
			{
				val m2 = IRCGenerator.generate(m1)
				m2 should not be None
			})
		}
		linec += 1
	}

}
