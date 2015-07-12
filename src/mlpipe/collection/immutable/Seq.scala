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
package immutable

import mlpipe.collection.ops.SeqOps

import scala.collection.{Seq ⇒ CSeq}
import scala.collection.generic.{CanBuildFrom, GenericCompanion, SeqFactory}
import scala.collection.immutable.{Seq ⇒ ImSeq}
import scala.language.higherKinds

/**
 *
 * @author Christos KK Loverdos <loverdos@gmail.com>
 */
object Seq extends SeqOps {
  final type SeqImpl[X] = ImSeq[X]

  private[this] object localSeqFactory extends SeqFactory[SeqImpl] {
    import scala.collection.mutable.{Builder, ListBuffer}

    implicit def canBuildFrom[A]: CanBuildFrom[Coll, A, ImSeq[A]] = ReusableCBF.asInstanceOf[GenericCanBuildFrom[A]]
    def newBuilder[A]: Builder[A, ImSeq[A]] = new ListBuffer
  }

  final implicit def canBuildFrom[A, B]: CanBuildFrom[CSeq[A], B, ImSeq[B]] = localSeqFactory.canBuildFrom.asInstanceOf[CanBuildFrom[CSeq[A], B, ImSeq[B]]]
  final val SeqImplC: GenericCompanion[ImSeq] = ImSeq
}
