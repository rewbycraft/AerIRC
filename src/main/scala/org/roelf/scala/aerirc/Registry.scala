package org.roelf.scala.aerirc

import org.roelf.scala.aerirc.handlers.{EWasHandled, IRCHandler, THandleAbleType}

import scala.collection.immutable.HashSet

/**
 * Created by roelf on 11/21/14.
 */
class Registry[T <: IRCHandler[_ <: THandleAbleType]] {
	private var registry = new HashSet[T]

	final def register(thing: T) =
	{
		if (!registry.contains(thing))
			registry = registry ++ List(thing)
	}

	final def unregister(thing: T) =
	{
		if (registry.contains(thing))
			registry = registry -- List(thing)
	}

	final def getRegistered = registry.toList

	final private[aerirc] def handleAll(msg: Any) = getRegistered.takeWhile(h => {
		assert(msg.isInstanceOf[THandleAbleType])
		val rc = h.handle(msg)
		rc == EWasHandled.NO
	})
}
