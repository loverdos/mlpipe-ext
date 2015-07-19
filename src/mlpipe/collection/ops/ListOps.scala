/*
 * Copyright (c) 2013-2014 Christos KK Loverdos
 */

package mlpipe.collection
package ops

import scala.collection.generic.{CanBuildFrom, GenericCompanion}
import scala.collection.immutable.{List, Map ⇒ ImMap}
import scala.collection.{Map ⇒ CMap, Seq ⇒ CSeq, Set ⇒ CSet}
import scala.language.higherKinds

trait ListOps extends SeqOps {
  final type SeqImpl[X] = List[X]
  final type MapImpl[K, V] = ImMap[K, V]

  protected val MapBuddy: MapOps = immutable.Map
  protected final val SeqImplC: GenericCompanion[List] = List
  protected final implicit def canBuildFrom[A, B] = List.canBuildFrom.asInstanceOf[CanBuildFrom[CSeq[A], B, SeqImpl[B]]]
}
