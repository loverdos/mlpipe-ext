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

import scala.collection.GenTraversableOnce
import scala.collection.immutable.{Set ⇒ ScalaSet}

/**
 *
 * @author Christos KK Loverdos <loverdos@gmail.com>
 */
object Set {

  @inline final def empty[A]: ScalaSet[A] = ScalaSet.empty[A]

  @inline final def filter[A](p: (A) ⇒ Boolean): ScalaSet[A] ⇒ ScalaSet[A] = _.filter(p)

  @inline final def find[A](p: (A) ⇒ Boolean): ScalaSet[A] ⇒ Option[A] = _.find(p)

  @inline final def filterDefined[A]: ScalaSet[Option[A]] ⇒ ScalaSet[A] = _.withFilter(_.isDefined).map(_.get)

  @inline final def map[A, B](f: (A) ⇒ B): ScalaSet[A] ⇒ ScalaSet[B] = _.map(f)

  @inline final def flatMap[A, B](f: (A) ⇒ GenTraversableOnce[B]): ScalaSet[A] ⇒ ScalaSet[B] = _.flatMap(f)

  @inline final def map_1[A]: ScalaSet[(A, _)] ⇒ ScalaSet[A] = _.map(_._1)

  @inline final def map_2[A]: ScalaSet[(_, A)] ⇒ ScalaSet[A] = _.map(_._2)

  @inline final def foreach[A](f: (A) ⇒ Unit): ScalaSet[A] ⇒ Unit = _.foreach(f)

  @inline final def length[A]: ScalaSet[A] ⇒ Int = _.size

  @inline final def size[A]: ScalaSet[A] ⇒ Int = _.size

  @inline final def first[A]: ScalaSet[A] ⇒ Option[A] = _.headOption

  @inline final def partition[A](f: (A) ⇒ Boolean): ScalaSet[A] ⇒ (ScalaSet[A], ScalaSet[A]) = _.partition(f)

  @inline final def groupBy[A, K](f: (A) ⇒ K): ScalaSet[A] ⇒ Map[K, ScalaSet[A]] = _.groupBy(f)

  @inline final def mkString[A](sep: String): ScalaSet[A] ⇒ String = _.mkString(sep)

  @inline final def mkString[A](start: String, sep: String, end: String): ScalaSet[A] ⇒ String = _.mkString(start, sep, end)

  // This is for debugging
  @inline final def passThrough[A](f: (A) ⇒ Any): ScalaSet[A] ⇒ ScalaSet[A] = set ⇒ {
    set.foreach(f)
    set
  }

  // ML-ish
  @inline final def iter[A](f: (A) ⇒ Unit): ScalaSet[A] ⇒ Unit = _.foreach(f)

  @inline final def ofOne[A](x: A): ScalaSet[A] = ScalaSet(x)

  @inline final def of[A](xs: A*): ScalaSet[A] = ScalaSet(xs:_*)

  @inline final def ofIterable[A]: Iterable[A] ⇒ ScalaSet[A] = _.toSet

  @inline final def ofIterator[A]: Iterator[A] ⇒ ScalaSet[A] = _.to[ScalaSet]

  @inline final def ofSeq[A]: scala.collection.Seq[A] ⇒ ScalaSet[A] = _.to[ScalaSet]

  @inline final def ofList[A]: List[A] ⇒ ScalaSet[A] = _.toSet

  @inline final def ofArray[A]: Array[A] ⇒ ScalaSet[A] = _.toSet

  @inline final def ofMap[A, B]: scala.collection.Map[A, B] ⇒ ScalaSet[(A, B)] = _.to[ScalaSet]

  @inline final def ofSet[A]: ScalaSet[A] ⇒ ScalaSet[A] = identity

  @inline final def ofJava[E]: java.util.Set[E] ⇒ ScalaSet[E] = it ⇒ {
    import scala.collection.JavaConverters._
    it.asScala.to[Set]
  }

  @inline final def ofEnumSet[E <: Enum[E]](cls: Class[E]): ScalaSet[E] =
     ofJava(java.util.EnumSet.allOf(cls))

  @inline final def ofOption[A]: Option[A] ⇒ ScalaSet[A] = {
    case Some(value) ⇒ ScalaSet(value)
    case None ⇒ ScalaSet()
  }
}
