package org.roelf.scala.aerirc.generator

import org.roelf.scala.aerirc.IRCMessage

import scala.reflect.runtime.universe
import scala.reflect.runtime.universe._

/**
 * Created by roelf on 11/21/14.
 */
object IRCGeneratorUtil {

	val mirror = universe.runtimeMirror(getClass.getClassLoader)

	def getFields(clazz: IRCMessage) = mirror.reflect(clazz).symbol.asType.typeSignature.members.sorted.collect
	{
		case m: MethodSymbol if m.isCaseAccessor => m
	}.toArray

	def getFieldValue(clazz: IRCMessage, field: MethodSymbol) = mirror.reflect(clazz).reflectField(field).get
}
