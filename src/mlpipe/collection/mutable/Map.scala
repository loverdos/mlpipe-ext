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

package mlpipe.collection.mutable

import mlpipe.collection.ops.MapOps

import scala.collection.generic.CanBuildFrom
import scala.collection.mutable.{Map ⇒ MMap}
import scala.collection.{Map ⇒ CMap, Seq ⇒ CSeq, Set ⇒ CSet}

/**
 *
 * @author Christos KK Loverdos <loverdos@gmail.com>
 */
object Map extends MapOps {
  final type MapImpl[K, V] = MMap[K, V]

  protected implicit def canBuildFrom[A, B] = MMap.canBuildFrom.asInstanceOf[CanBuildFrom[CMap[A, B], (A, B), MapImpl[A, B]]]

//  final val MapImplF = MMap
  def empty[A, B] = MMap.empty[A, B]
}