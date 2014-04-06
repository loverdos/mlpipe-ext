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

package mlpipe.collection.generic

import scala.collection.{GenTraversableOnce, Map, Set}

/**
 *
 * @author Christos KK Loverdos <loverdos@gmail.com>
 */
object PSet {
  @inline final def filter[A](p: (A) ⇒ Boolean): Set[A] ⇒ Set[A] = _.filter(p)

  @inline final def find[A](p: (A) ⇒ Boolean): Set[A] ⇒ Option[A] = _.find(p)

  @inline final def filterDefined[A]: Set[Option[A]] ⇒ Set[A] = _.withFilter(_.isDefined).map(_.get)

  @inline final def map[A, B](f: (A) ⇒ B): Set[A] ⇒ Set[B] = _.map(f)

  @inline final def flatMap[A, B](f: (A) ⇒ GenTraversableOnce[B]): Set[A] ⇒ Set[B] = _.flatMap(f)

  @inline final def map_1[A]: Set[(A, _)] ⇒ Set[A] = _.map(_._1)

  @inline final def map_2[A]: Set[(_, A)] ⇒ Set[A] = _.map(_._2)

  @inline final def foreach[A](f: (A) ⇒ Unit): Set[A] ⇒ Unit = _.foreach(f)

  @inline final def length[A]: Set[A] ⇒ Int = _.size

  @inline final def size[A]: Set[A] ⇒ Int = _.size

  @inline final def first[A]: Set[A] ⇒ Option[A] = _.headOption

  @inline final def partition[A](f: (A) ⇒ Boolean): Set[A] ⇒ (Set[A], Set[A]) = _.partition(f)

  @inline final def groupBy[A, K](f: (A) ⇒ K): Set[A] ⇒ Map[K, Set[A]] = _.groupBy(f)

  @inline final def mkString[A](sep: String): Set[A] ⇒ String = _.mkString(sep)

  @inline final def mkString[A](start: String, sep: String, end: String): Set[A] ⇒ String = _.mkString(start, sep, end)

  // This is for debugging
  @inline final def passThrough[A](f: (A) ⇒ Any): Set[A] ⇒ Set[A] = set ⇒ {
    set.foreach(f)
    set
  }

  // ML-ish
  @inline final def iter[A](f: (A) ⇒ Unit): Set[A] ⇒ Unit = _.foreach(f)

  @inline final def ofOne[A](x: A): Set[A] = Set(x)

  @inline final def of[A](xs: A*): Set[A] = Set(xs:_*)

  @inline final def ofIterable[A]: Iterable[A] ⇒ Set[A] = _.toSet

  @inline final def ofIterator[A]: Iterator[A] ⇒ Set[A] = _.toSet

  @inline final def ofSeq[A]: Seq[A] ⇒ Set[A] = _.toSet

  @inline final def ofList[A]: List[A] ⇒ Set[A] = _.toSet

  @inline final def ofArray[A]: Array[A] ⇒ Set[A] = _.toSet

  @inline final def ofMap[A, B]: Map[A, B] ⇒ Set[(A, B)] = _.toSet

  @inline final def ofSet[A]: Set[A] ⇒ Set[A] = identity

  @inline final def ofJava[E]: java.util.Set[E] ⇒ Set[E] = it ⇒ {
    import scala.collection.JavaConverters._
    it.asScala.to[Set]
  }

  @inline final def ofEnumSet[E <: Enum[E]](cls: Class[E]): Set[E] =
    ofJava(java.util.EnumSet.allOf(cls))

  @inline final def ofOption[A]: Option[A] ⇒ Set[A] = {
    case Some(value) ⇒ Set(value)
    case None ⇒ Set()
  }
}
