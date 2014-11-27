package org.roelf.scala.aerirc.user

import org.roelf.scala.aerirc.test.AbstractSpec

/**
 *
 * Tests to see if the userpool system works.
 * 
 * Created by roelf on 11/23/14.
 */
class UserPoolSpec extends AbstractSpec{
	"TUserPool" should "only contain one entry when queried twice with the same hostmask" in {
		object pool extends TUserPool
		val mask = "test!tester@test.com"
		val u1 = pool.userFromHostMask(mask)
		val u2 = pool.userFromHostMask(mask)
		pool.userPool.size should be (1)
	}
	it should "return the same object for the same mask" in {
		object pool extends TUserPool
		val mask = "test!tester@test.com"
		val u1 = pool.userFromHostMask(mask)
		val u2 = pool.userFromHostMask(mask)
		u1 should be (u2)
	}

	it should "return the same thing for the nick and the mask" in {

	}
}
