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

package mlpipe.collection.immutable

import scala.{Option ⇒ ScalaOption}
import scala.collection.{GenTraversable, Seq, Map, Set}

/**
 *
 * @author Christos KK Loverdos <loverdos@gmail.com>
 */
object Option {
  @inline final def filter[A](p: (A) ⇒ Boolean): ScalaOption[A] ⇒ ScalaOption[A] = _.filter(p)

  @inline final def getOr[A](default: ⇒A): ScalaOption[A] ⇒ A = _.getOrElse(default)

  @inline final def orElse[A, B >: A](f: ⇒ ScalaOption[B]): ScalaOption[A] ⇒ ScalaOption[B] =
    {
      case None ⇒ f
      case some ⇒ some
    }

  @inline final def map[A, B](f: (A) ⇒ B): ScalaOption[A] ⇒ ScalaOption[B] = _.map(f)

  @inline final def map_1[A]: ScalaOption[(A, _)] ⇒ ScalaOption[A] = _.map(_._1)

  @inline final def map_2[A]: ScalaOption[(_, A)] ⇒ ScalaOption[A] = _.map(_._2)

  @inline final def flatMap[A, B](f: (A) ⇒ ScalaOption[B]): ScalaOption[A] ⇒ ScalaOption[B] = _.flatMap(f)

  @inline final def foreach[A](f: (A) ⇒ Unit): ScalaOption[A] ⇒ Unit = _.foreach(f)

  @inline final def length[A]: ScalaOption[A] ⇒ Int = size(_)

  @inline final def size[A]: ScalaOption[A] ⇒ Int = x ⇒ if(x.isDefined) 1 else 0

  @inline final def first[A]: ScalaOption[A] ⇒ ScalaOption[A] = identity

//  @inline final def flatten[A]: Traversable[Traversable[A]] ⇒ ScalaOption[A] = _.flatten.headOption
//  @inline final def flatten[A]: ScalaOption[ScalaOption[A]] ⇒ ScalaOption[A] = _.flatten.headOption

  @inline final def flatten[A]: ScalaOption[GenTraversable[A]] ⇒ ScalaOption[A] = _.flatMap(_.headOption)

  @inline final def mkString[A](sep: String): ScalaOption[A] ⇒ String = _.mkString(sep)

  @inline final def mkString[A](start: String, sep: String, end: String): ScalaOption[A] ⇒ String = _.mkString(start, sep, end)

  // ML-ish
  @inline final def iter[A](f: (A) ⇒ Unit): ScalaOption[A] ⇒ Unit = _.foreach(f)

  @inline final def withEffect[A](f: (A) ⇒ Any): Option[A] ⇒ Option[A] = option ⇒ { option.foreach(f); option }

  @inline final def ofIterable[A]: Iterable[A] ⇒ ScalaOption[A] = _.headOption

  @inline final def ofSeq[A]: Seq[A] ⇒ ScalaOption[A] = _.headOption

  @inline final def ofSet[A]: Set[A] ⇒ ScalaOption[A] = _.headOption

  @inline final def ofList[A]: List[A] ⇒ ScalaOption[A] = _.headOption

  @inline final def ofArray[A]: Array[A] ⇒ ScalaOption[A] = _.headOption

  @inline final def ofMap[A, B]: Map[A, B] ⇒ ScalaOption[(A, B)] = _.headOption

  @inline final def ofAny[A]: A ⇒ ScalaOption[A] = ScalaOption.apply
}
