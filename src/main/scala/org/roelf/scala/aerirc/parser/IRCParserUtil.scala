package org.roelf.scala.aerirc.parser

import org.reflections.Reflections
import org.roelf.scala.aerirc.IRCAutoNumericMessage
import org.roelf.scala.aerirc.exceptions.AerIRCWrappedException

import scala.collection.mutable.ArrayBuffer

/**
 * Created by roelf on 10/13/14.
 */
object IRCParserUtil {
	def makeObject(cls: Class[_], args: Array[AnyRef]): AnyRef =
	{
		try
		{
			//Reflection hax ftw.
			cls.getConstructors()(0).newInstance(args: _*).asInstanceOf[Object]
		} catch
			{
				case e: Throwable => throw new AerIRCWrappedException(e)
			}
	}

	def getConstructorArgCount(cls: Class[_]): Int = getConstructorArgTypes(cls).size

	def getConstructorArgTypes(cls: Class[_]): Array[Class[_]] = cls.getDeclaredConstructors.apply(0).getParameterTypes

	private var _definitions: Array[String] = null

	def definitions: Array[String] =
	{
		if (_definitions == null)
			init() //Build definitions if null.
		_definitions
	}

	def init(): Unit =
	{
		val definitions = scala.io.Source.fromURL(getClass.getResource("/parserdefs.txt")).getLines().toBuffer

		//Dynamic parser definition generator
		val reflections = new Reflections("org.roelf.scala.aerirc")
		val classes = reflections.getSubTypesOf(classOf[IRCAutoNumericMessage]).toArray
		for (sub <- classes.map(a => a.asInstanceOf[Class[_ <: IRCAutoNumericMessage]]))
		{
			//Step one: Create an empty instance to get the numeric out.
			val cons = sub.getConstructors()(0).getParameterTypes
			val args = new ArrayBuffer[AnyRef]
			getConstructorArgTypes(sub).map(o => o.getSimpleName.toLowerCase).foreach
			{
				case "int" => args += (-1).asInstanceOf[AnyRef]
				case "string" => args += null
			}
			val m = makeObject(sub, args.toArray).asInstanceOf[IRCAutoNumericMessage]

			//Step two: Use this information to build parser definitions.
			def tokenazer(prefix: String): Unit =
			{
				var line = prefix
				for (i <- 1 until cons.size)
				{
					cons(i).getSimpleName.toLowerCase match
					{
						case "string" =>
							if (i < cons.size - 1)
								line += " %s"
							else
								line += " :%s"
						case "int" =>
							line += " %d"
					}
					definitions += line
				}
			}
			//tokenazer(sub.getSimpleName + "\t" + m.numeric.formatted("%03d")) //Unneeded because I derped up reading the rfc.
			tokenazer(sub.getSimpleName + "\t" + m.numeric.formatted("%03d") + " %*")
		}
		_definitions = definitions.toArray
		//println("Generated/found " + _definitions.size + " parser definitions.")
		//_definitions.foreach(d => println("-->" + d + "<--"))
	}
}
