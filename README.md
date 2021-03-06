# AerIRC - Project Aeris IRC Library

[![Build Status](https://travis-ci.org/rewbycraft/AerIRC.svg?branch=master)](https://travis-ci.org/rewbycraft/AerIRC)

## What is this?

This is the repository for AerIRC.
AerIRC is the IRC library for Aeris.
At the moment, AerIRC is unfinished and completely broken in most cases.

## What is it written in?

AerIRC is written in Scala and uses the Akka framework.
The goal is for this library to be fully capable of communicating over the IRC protocol, RFC2812.
At the moment the compatibility leaves a lot to be desired.
I will attempt to implement as much as I can find the energy to do.

## Usage/contribution

If you're interested in using this library and/or contributing, great!
Shoot me a PM and I'll do my best to help you set up the library and fix bugs/implement missing functionality.
There's a file called TestApp.scala in src/test/scala which shows basic usage.

##Documentation
There are some docs at:
http://doc.roelf.org/AerIRC
http://scaladoc.roelf.org/AerIRC


## Licensing

This code is under MIT license. See LICENSE.

## Downloads/maven

See the snapshot repository at:
https://nexus.roelf.org/nexus/

## Unsupported stuff:

IRC numberics: 004, 005, 265, 266 as they are not important at all.

## Special thanks

* *[Zarthus](http://zarth.us/)* for helping me out and fielding questions regarding the IRC protocol and being an overal good friend.
* *[GitHub](https://www.github.com/)* for hosting the code.
* *[JetBrains](https://www.jetbrains.com/)* for making the best java/scala/whatever editor ever (Intellij Idea).
