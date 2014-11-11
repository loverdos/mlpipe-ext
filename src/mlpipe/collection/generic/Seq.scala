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
import scala.collection.{Seq ⇒ ScalaSeq}

/**
 *
 * @author Christos KK Loverdos <loverdos@gmail.com>
 */
object Seq {
  @inline final def filter[A](p: (A) ⇒ Boolean): ScalaSeq[A] ⇒ ScalaSeq[A] = _.filter(p)

  @inline final def find[A](p: (A) ⇒ Boolean): ScalaSeq[A] ⇒ Option[A] = _.find(p)

  @inline final def exists[A](p: (A) ⇒ Boolean): ScalaSeq[A] ⇒ Boolean = _.exists(p)

  @inline final def filterDefined[A]: ScalaSeq[Option[A]] ⇒ ScalaSeq[A] = _.withFilter(_.isDefined).map(_.get)

  @inline final def map[A, B](f: (A) ⇒ B): ScalaSeq[A] ⇒ ScalaSeq[B] = _.map(f)

  @inline final def flatMap[A, B](f: (A) ⇒ GenTraversableOnce[B]): ScalaSeq[A] ⇒ ScalaSeq[B] = _.flatMap(f)

  @inline final def map_1[A]: ScalaSeq[(A, _)] ⇒ ScalaSeq[A] = _.map(_._1)

  @inline final def map_2[A]: ScalaSeq[(_, A)] ⇒ ScalaSeq[A] = _.map(_._2)

  @inline final def foreach[A](f: (A) ⇒ Unit): ScalaSeq[A] ⇒ Unit = _.foreach(f)

  @inline final def length[A]: ScalaSeq[A] ⇒ Int = _.length

  @inline final def size[A]: ScalaSeq[A] ⇒ Int = _.length

  @inline final def take[A](n: Int): ScalaSeq[A] ⇒ ScalaSeq[A] = _.take(n)

  @inline final def first[A]: ScalaSeq[A] ⇒ Option[A] = _.headOption

  @inline final def partition[A](f: (A) ⇒ Boolean): ScalaSeq[A] ⇒ (ScalaSeq[A], ScalaSeq[A]) = _.partition(f)

  @inline final def groupBy[A, K](f: (A) ⇒ K): ScalaSeq[A] ⇒ Map[K, ScalaSeq[A]] = _.groupBy(f)

  @inline final def mkString[A](sep: String): ScalaSeq[A] ⇒ String = _.mkString(sep)

  @inline final def mkString[A](start: String, sep: String, end: String): ScalaSeq[A] ⇒ String = _.mkString(start, sep, end)

  // This is for debugging
  @inline final def passThrough[A](f: (A) ⇒ Any): ScalaSeq[A] ⇒ ScalaSeq[A] = seq ⇒ {
    seq.foreach(f)
    seq
  }

  // ML-ish
  @inline final def iter[A](f: (A) ⇒ Unit): ScalaSeq[A] ⇒ Unit = _.foreach(f)

  @inline final def ofOne[A](x: A): ScalaSeq[A] = ScalaSeq(x)

  @inline final def of[A](xs: A*): ScalaSeq[A] = ScalaSeq(xs:_*)

  @inline final def ofIterable[A]: Iterable[A] ⇒ ScalaSeq[A] = _.to[ScalaSeq]

  @inline final def ofTraversable[A]: Traversable[A] ⇒ ScalaSeq[A] = _.to[ScalaSeq]

  @inline final def ofIterator[A]: Iterator[A] ⇒ ScalaSeq[A] = _.to[ScalaSeq]

  @inline final def ofList[A]: List[A] ⇒ ScalaSeq[A] = _.to[ScalaSeq]

  @inline final def ofSet[A]: Set[A] ⇒ ScalaSeq[A] = _.to[ScalaSeq]

  @inline final def ofArray[A]: Array[A] ⇒ ScalaSeq[A] = _.to[ScalaSeq]

  @inline final def ofMap[A, B]: Map[A, B] ⇒ ScalaSeq[(A, B)] = _.to[ScalaSeq]

  @inline final def ofMapSortedValuesBy[A, B, C](sortBy: (B) ⇒ C)(implicit ord: Ordering[C]): Map[A, B] ⇒ ScalaSeq[B] =
    it ⇒ ScalaSeq(it.toSeq.sortBy(kv ⇒ sortBy(kv._2)).map(_._2):_*)

  @inline final def ofMapFilteredValuesByKey[A, B](p: (A) ⇒ Boolean): Map[A, B] ⇒ ScalaSeq[B] =
    it ⇒ (for((k, v) ← it if p(k)) yield v).toSeq

  @inline final def ofMapValues[A, B]: Map[A, B] ⇒ ScalaSeq[B] = _.values.toSeq

  @inline final def ofJava[E]: java.util.Collection[E] ⇒ ScalaSeq[E] = it ⇒ {
    import scala.collection.JavaConverters._
    it.asScala.to[ScalaSeq]
  }

  @inline final def ofOption[A]: Option[A] ⇒ ScalaSeq[A] = {
    case Some(value) ⇒ ScalaSeq(value)
    case None ⇒ ScalaSeq()
  }
}
