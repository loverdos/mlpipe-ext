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
package concurrent

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext}
import scala.concurrent.{Future ⇒ ScalaFuture}
import scala.concurrent.{Promise ⇒ ScalaPromise}
import mlpipe.collection.Seq

/**
 *
 * @author Christos KK Loverdos <loverdos@gmail.com>
 */
object Future {
  @inline final def filter[A](p: (A) ⇒ Boolean)
                    (implicit ec: ExecutionContext = ExecutionContext.Implicits.global): ScalaFuture[A] ⇒ ScalaFuture[A] =
    _.filter(p)

  @inline final def filterSeq[A](p: (A) ⇒ Boolean)
                    (implicit ec: ExecutionContext = ExecutionContext.Implicits.global): ScalaFuture[Seq[A]] ⇒ ScalaFuture[Seq[A]] =
    _.map(_.filter(p))

  @inline final def foreach[A](f: (A) ⇒ Unit)
                    (implicit ec: ExecutionContext = ExecutionContext.Implicits.global): ScalaFuture[A] ⇒ Unit =
    _.foreach(f)

  @inline final def foreachSeq[A](f: (A) ⇒ Unit)
                    (implicit ec: ExecutionContext = ExecutionContext.Implicits.global): ScalaFuture[Seq[A]] ⇒ Unit =
    _.foreach(_.foreach(f))

  @inline final def filterDefined[A]
                    (implicit ec: ExecutionContext = ExecutionContext.Implicits.global): ScalaFuture[Option[A]] ⇒ ScalaFuture[A] =
    _.withFilter(_.isDefined).map(_.get)

  @inline final def filterSeqDefined[A]
                    (implicit ec: ExecutionContext = ExecutionContext.Implicits.global): ScalaFuture[Seq[Option[A]]] ⇒ ScalaFuture[Seq[A]] =
    _.map(Seq.filterDefined)

  @inline final def map[A, B](f: (A) ⇒ B)
                    (implicit ec: ExecutionContext = ExecutionContext.Implicits.global): ScalaFuture[A] ⇒ ScalaFuture[B] =
    _.map(f)

  @inline final def mapSeq[A, B](f: (A) ⇒ B)
                    (implicit ec: ExecutionContext = ExecutionContext.Implicits.global): ScalaFuture[Seq[A]] ⇒ ScalaFuture[Seq[B]] =
    _.map(_.map(f))

  @inline final def flatMap[A, B](f: (A) ⇒ ScalaFuture[B])
                    (implicit ec: ExecutionContext = ExecutionContext.Implicits.global): ScalaFuture[A] ⇒ ScalaFuture[B] =
    _.flatMap(f)

  @inline final def awaitCompletion[A](atMost: Duration)
                    (implicit ec: ExecutionContext = ExecutionContext.Implicits.global): ScalaFuture[A] ⇒ ScalaFuture[A] =
    Await.ready(_, atMost)

  @inline final def awaitResult[A](atMost: Duration): ScalaFuture[A] ⇒ A =
    Await.result(_, atMost)

  @inline final def fold[Z, A](zero: Z)(f: (Z, A) ⇒ Z)
                    (implicit ec: ExecutionContext = ExecutionContext.Implicits.global): Seq[ScalaFuture[A]] ⇒ ScalaFuture[Z] =
    ScalaFuture.fold(_)(zero)(f)

  // ML-ish
  @inline final def ofValue[A]: A ⇒ ScalaFuture[A] = ScalaFuture.successful

  @inline final def ofComputation[A]
                    (implicit ec: ExecutionContext = ExecutionContext.Implicits.global): (⇒A) ⇒ ScalaFuture[A] =
    ScalaFuture(_)

  @inline final def ofPromise[A]: ScalaPromise[A] ⇒ ScalaFuture[A] = _.future

  @inline final def ofSeqFuture[A]
                    (implicit ec: ExecutionContext = ExecutionContext.Implicits.global): Seq[ScalaFuture[A]] ⇒ ScalaFuture[Seq[A]] =
    ScalaFuture.sequence[A, Seq]

  @inline final def iter[A](f: (A) ⇒ Unit)
                    (implicit ec: ExecutionContext = ExecutionContext.Implicits.global): ScalaFuture[A] ⇒ Unit =
    _.foreach(f)

  @inline final def iterSeq[A](f: (A) ⇒ Unit)
                    (implicit ec: ExecutionContext = ExecutionContext.Implicits.global): ScalaFuture[Seq[A]] ⇒ Unit =
    _.foreach(_.foreach(f))
}
