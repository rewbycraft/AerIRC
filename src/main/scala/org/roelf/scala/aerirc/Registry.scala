package org.roelf.scala.aerirc

import org.roelf.scala.aerirc.handlers.{EWasHandled, IRCHandler, THandleAbleType}

import scala.collection.immutable.HashMap

/**
 * Created by roelf on 11/21/14.
 */
class Registry[T <: IRCHandler[_ <: THandleAbleType]] {
	private var registry = new HashMap[T, Int]

	final def register(thing: T): Unit = register(thing, 5000)

	final def register(thing: T, priority: Int): Unit =
	{
		if (!registry.contains(thing))
			registry = registry + (thing -> priority)
	}

	final def unregister(thing: T): Unit =
	{
		if (registry.contains(thing))
			registry = registry - thing
	}

	final def getRegistered = registry.toSeq.sortBy(_._2).map(h => h._1).toList

	final private[aerirc] def handleAll(thing: Any): Unit =
	{
		assert(thing.isInstanceOf[THandleAbleType])
		getRegistered.takeWhile(h =>
		{
			val rc = h.handle(thing)
			rc != EWasHandled.DONTHANDLE
		})
	}
}
