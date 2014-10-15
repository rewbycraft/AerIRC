package org.roelf.scala.aerirc.exceptions

/**
 * Created by roelf on 10/13/14.
 */
class AerIRCWrappedException(cause: Throwable) extends RuntimeException {

	private val message = getClass.getName + " wrapped " + cause.getClass.getName + ": " + cause.getMessage

	override def getMessage: String = message

	override def getLocalizedMessage: String = message

	override def getCause: Throwable = cause
}
