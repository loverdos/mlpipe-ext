/*
 * Copyright (c) 2013-2014 Christos KK Loverdos
 */

package mlpipe.collection.ops

import scala.collection.generic.{CanBuildFrom, GenericCompanion}
import scala.collection.{GenTraversableOnce, Seq ⇒ CSeq, Set ⇒ CSet, Map ⇒ CMap}
import scala.language.higherKinds

trait SeqOps {
  type SeqImpl[X] <: CSeq[X]
  val SeqImplC: GenericCompanion[SeqImpl]
  implicit def canBuildFrom[A, B]: CanBuildFrom[CSeq[A], B, SeqImpl[B]]

  final def apply[A](): SeqImpl[A] = SeqImplC.empty[A]

  final def empty[A]: SeqImpl[A] = SeqImplC.empty[A]

  final def apply[A](elems: A*): SeqImpl[A] = SeqImplC.apply(elems:_*)

  final def find[A](p: (A) ⇒ Boolean): SeqImpl[A] ⇒ Option[A] = _.find(p)

  final def exists[A](p: (A) ⇒ Boolean): SeqImpl[A] ⇒ Boolean = _.exists(p)

  final def map[A, B](f: (A) ⇒ B): SeqImpl[A] ⇒ SeqImpl[B] = _.map(f)

  final def mapFirst[A, B]: CSeq[(A, B)] ⇒ SeqImpl[A] = _.map(_._1)

  final def mapSecond[A, B]: CSeq[(A, B)] ⇒ SeqImpl[B] = _.map(_._2)

  final def flatMap[A, B](f: (A) ⇒ GenTraversableOnce[B]): SeqImpl[A] ⇒ SeqImpl[B] = _.flatMap(f)

  final def foreach[A, U](f: (A) ⇒ U): SeqImpl[A] ⇒ Unit = _.foreach(f)

  final def filter[A](p: (A) ⇒ Boolean): SeqImpl[A] ⇒ SeqImpl[A] = _.filter(p).asInstanceOf[SeqImpl[A]]

  final def partition[A](f: (A) ⇒ Boolean): SeqImpl[A] ⇒ (SeqImpl[A], SeqImpl[A]) = _.partition(f).asInstanceOf[(SeqImpl[A], SeqImpl[A])]

  final def sorted[A](implicit ord: Ordering[A]): SeqImpl[A] ⇒ SeqImpl[A] = _.sorted(ord).asInstanceOf[SeqImpl[A]]

  final def sortBy[A, B](f: (A) ⇒ B)(implicit ord: Ordering[B]): SeqImpl[A] ⇒ SeqImpl[A] = _.sortBy(f)(ord).asInstanceOf[SeqImpl[A]]

  final def sortWith[A](lessThan: (A, A) ⇒ Boolean): SeqImpl[A] ⇒ SeqImpl[A] = _.sortWith(lessThan).asInstanceOf[SeqImpl[A]]

  final def filterDefined[A]: SeqImpl[Option[A]] ⇒ SeqImpl[A] = _.withFilter(_.isDefined).map[A, SeqImpl[A]](_.get)

  final def length[A]: SeqImpl[A] ⇒ Int = _.length

  final def size[A]: SeqImpl[A] ⇒ Int = _.length

  final def headOption[A]: SeqImpl[A] ⇒ Option[A] = _.headOption

  final def mkString[A](sep: String): SeqImpl[A] ⇒ String = _.mkString(sep)

  final def mkString[A](start: String, sep: String, end: String): SeqImpl[A] ⇒ String = _.mkString(start, sep, end)

  // This is for debugging
  final def passThrough[A](f: (A) ⇒ Any): SeqImpl[A] ⇒ SeqImpl[A] = seq ⇒ { seq.foreach(f); seq }

  final def iter[A, U](f: (A) ⇒ U): SeqImpl[A] ⇒ Unit = foreach(f)

  final def ofOne[A](x: A): SeqImpl[A] = apply(x)

  final def of[A](xs: A*): SeqImpl[A] = apply(xs:_*)

  final def ofIterable[A]: Iterable[A] ⇒ SeqImpl[A] = _.to[SeqImpl]

  final def ofIterator[A]: Iterator[A] ⇒ SeqImpl[A] = _.to[SeqImpl]

  final def ofTraversable[A]: Traversable[A] ⇒ SeqImpl[A] = _.to[SeqImpl]

  final def ofList[A]: List[A] ⇒ SeqImpl[A] = _.to[SeqImpl]

  final def ofSet[A]: CSet[A] ⇒ SeqImpl[A] = _.to[SeqImpl]

  final def ofArray[A]: Array[A] ⇒ SeqImpl[A] = _.to[SeqImpl]

  final def ofMap[A, B]: CMap[A, B] ⇒ SeqImpl[(A, B)] = _.to[SeqImpl]

  final def ofOption[A]: Option[A] ⇒ SeqImpl[A] = {
    case Some(value) ⇒ apply(value)
    case None ⇒ apply()
  }

  final def ofJava[A]: java.util.Collection[A] ⇒ SeqImpl[A] = it ⇒ {
    import scala.collection.JavaConverters._
    it.asScala.to[SeqImpl]
  }

  final def ofMapValuesSortedBy[A, B, C](sortBy: (B) ⇒ C)(implicit ord: Ordering[C]): CMap[A, B] ⇒ SeqImpl[B] =
    map ⇒ apply(map.toSeq.sortBy(kv ⇒ sortBy(kv._2)).map(_._2):_*)

  final def unique[A]: SeqImpl[A] ⇒ SeqImpl[A] =
    seq ⇒ {
      val set = scala.collection.mutable.Set[A]()
      for(i ← seq if !set.contains(i)) yield {
        set += i
        i
      }
    }
}
