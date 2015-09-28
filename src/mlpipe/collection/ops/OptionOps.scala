/*
 * Copyright (c) 2013-2014 Christos KK Loverdos
 */

package mlpipe.collection.ops

import scala.collection.{GenTraversable, Seq ⇒ CSeq, Map ⇒ CMap, Set ⇒ CSet}

/**
 *
 * @author Christos KK Loverdos <loverdos@gmail.com>
 */
trait OptionOps {
  final def filter[A](p: (A) ⇒ Boolean): Option[A] ⇒ Option[A] = _.filter(p)

  final def getOr[A](default: ⇒A): Option[A] ⇒ A = _.getOrElse(default)

  final def orElse[A, B >: A](f: ⇒ Option[B]): Option[A] ⇒ Option[B] = {
    case None ⇒ f
    case some ⇒ some
  }

  final def fold[A, B](ifNone: ⇒B)(ifSome: (A) ⇒ B): Option[A] ⇒ B = _.fold(ifNone)(ifSome)
  
  final def map[A, B](f: (A) ⇒ B): Option[A] ⇒ Option[B] = _.map(f)

  final def mapFirst[A]: Option[(A, _)] ⇒ Option[A] = _.map(_._1)

  final def mapSecond[A]: Option[(_, A)] ⇒ Option[A] = _.map(_._2)

  final def flatMap[A, B](f: (A) ⇒ Option[B]): Option[A] ⇒ Option[B] = _.flatMap(f)

  final def foreach[A](f: (A) ⇒ Unit): Option[A] ⇒ Unit = _.foreach(f)

  final def length[A]: Option[A] ⇒ Int = size(_)

  final def size[A]: Option[A] ⇒ Int = x ⇒ if(x.isDefined) 1 else 0

  final def first[A]: Option[A] ⇒ Option[A] = identity

  final def flatten[A]: Option[GenTraversable[A]] ⇒ Option[A] = _.flatMap(_.headOption)

  final def mkString[A](sep: String): Option[A] ⇒ String = _.mkString(sep)

  final def mkString[A](start: String, sep: String, end: String): Option[A] ⇒ String = _.mkString(start, sep, end)

  final def iter[A](f: (A) ⇒ Unit): Option[A] ⇒ Unit = _.foreach(f)

  final def passThrough[A](f: (A) ⇒ Any): Option[A] ⇒ Option[A] = option ⇒ { option.foreach(f); option }

  final def ofIterable[A]: Iterable[A] ⇒ Option[A] = _.headOption

  final def ofSeq[A]: CSeq[A] ⇒ Option[A] = _.headOption

  final def ofSet[A]: CSet[A] ⇒ Option[A] = _.headOption

  final def ofMap[A, B]: CMap[A, B] ⇒ Option[(A, B)] = _.headOption

  final def ofList[A]: List[A] ⇒ Option[A] = _.headOption

  final def ofArray[A]: Array[A] ⇒ Option[A] = _.headOption

  final def apply[A]: A ⇒ Option[A] = Option(_)

  final def of[A]: A ⇒ Option[A] = Option(_)
}

