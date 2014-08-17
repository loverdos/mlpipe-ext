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

import scala.collection.GenTraversableOnce

/**
 *
 * @author Christos KK Loverdos <loverdos@gmail.com>
 */
object Traversable {
  @inline final def filter[A](p: (A) ⇒ Boolean): Traversable[A] ⇒ Traversable[A] = _.filter(p)

  @inline final def find[A](p: (A) ⇒ Boolean): Traversable[A] ⇒ Option[A] = _.find(p)

  @inline final def filterDefined[A]: Traversable[Option[A]] ⇒ Traversable[A] = _.withFilter(_.isDefined).map(_.get)

  @inline final def map[A, B](f: (A) ⇒ B): Traversable[A] ⇒ Traversable[B] = _.map(f)

  @inline final def flatMap[A, B](f: (A) ⇒ GenTraversableOnce[B]): Traversable[A] ⇒ Traversable[B] = _.flatMap(f)

  @inline final def map_1[A]: Set[(A, _)] ⇒ Traversable[A] = _.map(_._1)

  @inline final def map_2[A]: Set[(_, A)] ⇒ Traversable[A] = _.map(_._2)

  @inline final def foreach[A](f: (A) ⇒ Unit): Traversable[A] ⇒ Unit = _.foreach(f)

  @inline final def length[A]: Traversable[A] ⇒ Int = _.size

  @inline final def size[A]: Traversable[A] ⇒ Int = _.size

  @inline final def first[A]: Traversable[A] ⇒ Option[A] = _.headOption

  @inline final def partition[A](f: (A) ⇒ Boolean): Traversable[A] ⇒ (Traversable[A], Traversable[A]) = _.partition(f)

  @inline final def groupBy[A, K](f: (A) ⇒ K): Traversable[A] ⇒ Map[K, Traversable[A]] = _.groupBy(f)

  @inline final def mkString[A](sep: String): Traversable[A] ⇒ String = _.mkString(sep)

  @inline final def mkString[A](start: String, sep: String, end: String): Traversable[A] ⇒ String = _.mkString(start, sep, end)

  // This is for debugging
  @inline final def passThrough[A](f: (A) ⇒ Any): Traversable[A] ⇒ Traversable[A] = set ⇒ {
    set.foreach(f)
    set
  }

  // ML-ish
  @inline final def iter[A](f: (A) ⇒ Unit): Traversable[A] ⇒ Unit = _.foreach(f)
}
