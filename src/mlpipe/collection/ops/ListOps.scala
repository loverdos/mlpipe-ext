/*
 * Copyright (c) 2013-2014 Christos KK Loverdos
 */

package mlpipe.collection.ops

import scala.collection.generic.{CanBuildFrom, GenericCompanion}
import scala.collection.immutable.List
import scala.collection.{Map ⇒ CMap, Seq ⇒ CSeq, Set ⇒ CSet}
import scala.language.higherKinds

trait ListOps extends SeqOps {
  type SeqImpl[X] = List[X]
  final val SeqImplC: GenericCompanion[List] = List
  final implicit def canBuildFrom[A, B] = List.canBuildFrom.asInstanceOf[CanBuildFrom[CSeq[A], B, SeqImpl[B]]]
}
