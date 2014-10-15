package org.roelf.scala.aerirc.test

import org.roelf.scala.aerirc.generator.IRCGenerator
import org.roelf.scala.aerirc.parser.IRCParser

/**
 * Created by roelf on 10/8/14.
 */
class ParserGeneratorCombinationSpec extends AbstractSpec {
	/*
	val session = scala.io.Source.fromURL(getClass.getResource("/verifysession.txt"))

	//This exists purely so 'it' can be used for the file since it gets spammy if not.
	"IRCParser + IRCGenerator" should "not fail true == true" in {
		true should be (true)
	}

	var linec = 1
	for (message <- session.getLines() if message.length > 0)
	{
		it should f"replicate the input for: verifysession.txt:$linec $message" in
		{
			val m = IRCParser.parse(message)
			m should not be None
			val m2 = IRCGenerator.generate(m.get)
			m2 should not be None
			m2.get should be(message)
		}
		linec += 1
	}
	*/
}
