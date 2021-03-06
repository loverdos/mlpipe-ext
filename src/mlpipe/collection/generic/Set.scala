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

import mlpipe.collection.ops.SetOps

import scala.collection.generic.{CanBuildFrom, GenericCompanion}
import scala.collection.{Set ⇒ CSet}

/**
 *
 * @author Christos KK Loverdos <loverdos@gmail.com>
 */
object Set extends SetOps {
  final type SetImpl[X] = CSet[X]

  final implicit def canBuildFrom[A, B] = CSet.canBuildFrom.asInstanceOf[CanBuildFrom[CSet[A], B, SetImpl[B]]]
  final val SetImplC: GenericCompanion[CSet] = CSet
}
