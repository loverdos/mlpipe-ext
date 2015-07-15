/*
 * Copyright (c) 2013-2014 Christos KK Loverdos
 */

package mlpipe.collection.ops

import scala.collection.generic.{CanBuildFrom, GenericCompanion}
import scala.collection.immutable.Stream
import scala.collection.{Map ⇒ CMap, Seq ⇒ CSeq, Set ⇒ CSet}
import scala.language.higherKinds

trait StreamOps extends SeqOps {
  type SeqImpl[X] = Stream[X]
  final val SeqImplC: GenericCompanion[Stream] = Stream
  final implicit def canBuildFrom[A, B] = Stream.canBuildFrom.asInstanceOf[CanBuildFrom[CSeq[A], B, SeqImpl[B]]]

  final def ofSeries[A](n0: Int): ((Int) ⇒ A) ⇒ Stream[A] = f ⇒ Stream.cons(f(n0), ofSeries(n0 + 1)(f))

}
