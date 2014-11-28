#Tutorial

##What this tutorial aims to do.

This tutorial aims to show you the basic principles of AerIRC.
You will have to use the scaladoc to fill in the rest of your knowledge.
The examples will not bother themselves with imports and other extra code.
It is assumed that you have an IDE capable of fixing the imports.
This is our 'empty' file:
````
object Main extends App {
	//The code in the tutorial goes here.
}
````

Okay then! Let's get started!

##Connecting to a network

Let's start with the basics.

````
val network = new IRCNetwork
network.setHost("irc.spectrenet.cf")
network.setNick("AerIRC|Tutorial")

network.connect()
````

Simple, right?

TIP: Check the scaladoc for IRCNetwork, it contains extra set* functions for various things like the real name and such.

##Registering handlers

However, our example doesn't do anything yet. Let's fix that!

````
val network = new IRCNetwork
network.setHost("irc.spectrenet.cf")
network.setNick("AerIRC|Tutorial")

network.handlers.registered.register(new IRCRegisteredHandler {
	def handle(message: REGISTERED): EWasHandled = {
		//We have connected!
		//Let's join a channel!
		network.join("#AerIRC")
	}
})

network.connect()
````

If you are confused as to what is happening on line 5, it is a simple trick that allows you to inline-implement/extend a trait (just like java can do this with interfaces).
Anyhow...

In this example we introduce the concept of handlers. In the end any handler type you extend/implement is a derivative from IRCHandler[T], as such the code is common.

AerIRC uses the handlers to communicate back to you about events that happen.

You can define them inline like in the example or you can simply create a class that extends the handler to implement it (whichever suits your approach better).

TIP: If you go with the latter approach; the handler types are all traits, you can implement multiple ones in the same class as long as their message-types don't clash.

