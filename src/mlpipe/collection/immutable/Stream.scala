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

package mlpipe
package collection.immutable

import scala.collection.immutable.{Stream ⇒ ScalaStream, Map ⇒ ImMap}

import scala.collection.GenTraversableOnce

/**
 *
 * @author Christos KK Loverdos <loverdos@gmail.com>
 */
object Stream {
  @inline final def apply[A](): ScalaStream[A] = ScalaStream.empty[A]

  @inline final def empty[A]: ScalaStream[A] = ScalaStream.empty[A]

  @inline final def filter[A](p: (A) ⇒ Boolean): ScalaStream[A] ⇒ ScalaStream[A] = _.filter(p)

  @inline final def filterDefined[A]: Stream[Option[A]] ⇒ ScalaStream[A] = _.withFilter(_.isDefined).map(_.get)

  @inline final def map[A, B](f: (A) ⇒ B): ScalaStream[A] ⇒ Stream[B] = _.map(f)

  @inline final def flatMap[A, B](f: (A) ⇒ GenTraversableOnce[B]): ScalaStream[A] ⇒ Stream[B] = _.flatMap(f)

  @inline final def map_1[A]: Stream[(A, _)] ⇒ ScalaStream[A] = _.map(_._1)

  @inline final def map_2[A]: Stream[(_, A)] ⇒ ScalaStream[A] = _.map(_._2)

  @inline final def foreach[A](f: (A) ⇒ Unit): ScalaStream[A] ⇒ Unit = _.foreach(f)

  @inline final def length[A]: ScalaStream[A] ⇒ Int = _.length

  @inline final def size[A]: ScalaStream[A] ⇒ Int = _.length

  @inline final def first[A]: ScalaStream[A] ⇒ Option[A] = _.headOption

  @inline final def partition[A](f: (A) ⇒ Boolean): ScalaStream[A] ⇒ (ScalaStream[A], ScalaStream[A]) = _.partition(f)

  @inline final def groupBy[A, K](f: (A) ⇒ K): ScalaStream[A] ⇒ ImMap[K, ScalaStream[A]] = _.groupBy(f)

  @inline final def mkString[A](sep: String): ScalaStream[A] ⇒ String = _.mkString(sep)

  @inline final def mkString[A](start: String, sep: String, end: String): ScalaStream[A] ⇒ String = _.mkString(start, sep, end)

  // ML-ish
  @inline final def iter[A](f: (A) ⇒ Unit): ScalaStream[A] ⇒ Unit = _.foreach(f)

  @inline final def ofOne[A](x: A): ScalaStream[A] = ScalaStream(x)

  @inline final def of[A](xs: A*): ScalaStream[A] = ScalaStream(xs:_*)

  @inline final def ofIterable[A]: Iterable[A] ⇒ ScalaStream[A] = _.toStream

  @inline final def ofSeq[A]: Seq[A] ⇒ ScalaStream[A] = _.toStream

  @inline final def concat[A](all: Traversable[A]*): ScalaStream[A] =
    all.toStream.flatMap(_.toStream)

  @inline final def ofList[A]: List[A] ⇒ ScalaStream[A] = _.toStream

  @inline final def ofArray[A]: Array[A] ⇒ ScalaStream[A] = _.toStream

  @inline final def ofMap[A, B]: ImMap[A, B] ⇒ Stream[(A, B)] = _.toStream

  @inline final def ofMapValuesSortedBy[A, B, C](sortBy: (B) ⇒ C)(implicit ord: Ordering[C]): ImMap[A, B] ⇒ Stream[B] =
    it ⇒ it |> Seq.ofMapValuesSortedBy(sortBy) |> Stream.ofSeq

  @inline final def ofMapFilteredValuesByKey[A, B](p: (A) ⇒ Boolean): ImMap[A, B] ⇒ Stream[B] =
    it ⇒ (for((k, v) ← it if p(k)) yield v).toStream

  @inline final def ofMapValues[A, B]: ImMap[A, B] ⇒ Stream[B] = it ⇒ it.values.toStream

  @inline final def ofJava[E]: java.util.Collection[E] ⇒ Stream[E] = it ⇒ {
    import scala.collection.JavaConverters._
    it.asScala.toStream
  }

  @inline final def ofSeries[A](n0: Int): ((Int) ⇒ A) ⇒ ScalaStream[A] =
    f ⇒ ScalaStream.cons(f(n0), ofSeries(n0 + 1)(f))

  @inline final def ofOption[A]: Option[A] ⇒ ScalaStream[A] = {
    case Some(value) ⇒ ScalaStream(value)
    case None ⇒ ScalaStream()
  }
}
