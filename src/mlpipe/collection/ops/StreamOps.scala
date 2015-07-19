/*
 * Copyright (c) 2013-2014 Christos KK Loverdos
 */

package mlpipe.collection
package ops

import scala.collection.generic.{CanBuildFrom, GenericCompanion}
import scala.collection.immutable.{Stream, Map ⇒ ImMap}
import scala.collection.{Map ⇒ CMap, Seq ⇒ CSeq, Set ⇒ CSet}
import scala.language.higherKinds

trait StreamOps extends SeqOps {
  final type SeqImpl[X] = Stream[X]
  final type MapImpl[K, V] = ImMap[K, V]

  protected val MapBuddy: MapOps = immutable.Map
  protected final val SeqImplC: GenericCompanion[Stream] = Stream
  protected final implicit def canBuildFrom[A, B] = Stream.canBuildFrom.asInstanceOf[CanBuildFrom[CSeq[A], B, SeqImpl[B]]]

  final def ofSeries[A](n0: Int): ((Int) ⇒ A) ⇒ Stream[A] = f ⇒ Stream.cons(f(n0), ofSeries(n0 + 1)(f))
}
