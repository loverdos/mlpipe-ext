/*
 * Copyright (c) 2013 Christos KK Loverdos
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package mlpipe.collection
package generic

import mlpipe.collection.ops.{MapOps, SeqOps}

import scala.collection.generic.{CanBuildFrom, GenericCompanion}
import scala.collection.{Seq ⇒ CSeq, Map ⇒ CMap}

/**
 *
 * @author Christos KK Loverdos <loverdos@gmail.com>
 */
object Seq extends SeqOps {
  final type SeqImpl[X] = CSeq[X]
  final type MapImpl[K, V] = CMap[K, V]

  protected val MapBuddy: MapOps = generic.Map
  protected final implicit def canBuildFrom[A, B] = CSeq.canBuildFrom.asInstanceOf[CanBuildFrom[CSeq[A], B, SeqImpl[B]]]
  protected final val SeqImplC: GenericCompanion[CSeq] = CSeq
}
