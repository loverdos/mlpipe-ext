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

import mlpipe.collection.Seq

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext ⇒ EC, Future ⇒ SFuture, Promise ⇒ SPromise}

/**
 *
 * @author Christos KK Loverdos <loverdos@gmail.com>
 */
// FIXME Make a FutureOps
object Future {
  private[this] val global = EC.Implicits.global

  final def filter[A](p: (A) ⇒ Boolean)(implicit ec: EC = global): SFuture[A] ⇒ SFuture[A] = _.filter(p)

  final def filterSeq[A](p: (A) ⇒ Boolean)(implicit ec: EC = global): SFuture[Seq[A]] ⇒ SFuture[Seq[A]] = _.map(_.filter(p))

  final def foreach[A](f: (A) ⇒ Unit)(implicit ec: EC = global): SFuture[A] ⇒ Unit = _.foreach(f)

  final def foreachSeq[A](f: (A) ⇒ Unit)(implicit ec: EC = global): SFuture[Seq[A]] ⇒ Unit = _.foreach(_.foreach(f))

  final def filterDefined[A](implicit ec: EC = global): SFuture[Option[A]] ⇒ SFuture[A] = _.withFilter(_.isDefined).map(_.get)

  final def filterSeqDefined[A](implicit ec: EC = global): SFuture[Seq[Option[A]]] ⇒ SFuture[Seq[A]] = _.map(Seq.filterDefined)

  final def map[A, B](f: (A) ⇒ B)(implicit ec: EC = global): SFuture[A] ⇒ SFuture[B] = _.map(f)

  final def mapSeq[A, B](f: (A) ⇒ B)(implicit ec: EC = global): SFuture[Seq[A]] ⇒ SFuture[Seq[B]] = _.map(_.map(f))

  final def flatMap[A, B](f: (A) ⇒ SFuture[B])(implicit ec: EC = global): SFuture[A] ⇒ SFuture[B] = _.flatMap(f)

  final def awaitCompletion[A](atMost: Duration)(implicit ec: EC = global): SFuture[A] ⇒ SFuture[A] = Await.ready(_, atMost)

  final def awaitResult[A](atMost: Duration): SFuture[A] ⇒ A = Await.result(_, atMost)

  final def fold[Z, A](zero: Z)(f: (Z, A) ⇒ Z)(implicit ec: EC = global): Seq[SFuture[A]] ⇒ SFuture[Z] = SFuture.fold(_)(zero)(f)

  final def ofValue[A]: A ⇒ SFuture[A] = SFuture.successful

  final def ofComputation[A](implicit ec: EC = global): (⇒A) ⇒ SFuture[A] = SFuture(_)

  final def ofPromise[A]: SPromise[A] ⇒ SFuture[A] = _.future

  final def ofSeqFuture[A](implicit ec: EC = global): Seq[SFuture[A]] ⇒ SFuture[Seq[A]] = SFuture.sequence[A, Seq]

  final def iter[A](f: (A) ⇒ Unit)(implicit ec: EC = global): SFuture[A] ⇒ Unit = _.foreach(f)

  final def iterSeq[A](f: (A) ⇒ Unit)(implicit ec: EC = global): SFuture[Seq[A]] ⇒ Unit = _.foreach(_.foreach(f))
}
