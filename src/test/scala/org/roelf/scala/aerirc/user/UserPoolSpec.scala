package org.roelf.scala.aerirc.user

import org.roelf.scala.aerirc.test.AbstractSpec

/**
 *
 * Tests to see if the userpool system works.
 * 
 * Created by roelf on 11/23/14.
 */
class UserPoolSpec extends AbstractSpec{
	object pool extends TUserPool

	"TUserPool" should "only contain one entry when queried twice with the same hostmask" in {
		val mask = "test!tester@test.com"
		val u1 = pool.userFromHostMask(mask)
		val u2 = pool.userFromHostMask(mask)
		u1 should be (u2)
		pool.userPool.size should be (1)
	}
}
