package org.roelf.scala.aerirc.generator

import org.roelf.scala.aerirc.test.AbstractSpec

/**
 * Created by roelf on 10/8/14.
 */
class GeneratorSpec extends AbstractSpec {
	"IRCGenerator" should "return None if given null" in
	{
		IRCGenerator.generate(null) should be(None)
	}

}
