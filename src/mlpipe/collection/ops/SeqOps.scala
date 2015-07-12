/*
 * Copyright (c) 2013-2014 Christos KK Loverdos
 */

package mlpipe.collection.ops

import scala.collection.generic.{CanBuildFrom, GenericCompanion}
import scala.collection.{GenTraversableOnce, Seq ⇒ CSeq}
import scala.language.higherKinds

trait SeqOps {
  type SeqImpl[X] <: Seq[X]
  val SeqImplC: GenericCompanion[SeqImpl]
  implicit def canBuildFrom[A, B]: CanBuildFrom[CSeq[A], B, SeqImpl[B]]

  @inline final def apply[A](): SeqImpl[A] = SeqImplC.empty[A]

  @inline final def empty[A]: SeqImpl[A] = SeqImplC.empty[A]

  @inline final def apply[A](elems: A*): SeqImpl[A] = SeqImplC.apply(elems:_*)

  @inline final def find[A](p: (A) ⇒ Boolean): SeqImpl[A] ⇒ Option[A] = _.find(p)

  @inline final def exists[A](p: (A) ⇒ Boolean): SeqImpl[A] ⇒ Boolean = _.exists(p)

  @inline final def map[A, B](f: (A) ⇒ B): SeqImpl[A] ⇒ SeqImpl[B] = _.map(f)

  @inline final def flatMap[A, B](f: (A) ⇒ GenTraversableOnce[B]): SeqImpl[A] ⇒ SeqImpl[B] = _.flatMap(f)

  @inline final def foreach[A, U](f: (A) ⇒ U): SeqImpl[A] ⇒ Unit = _.foreach(f)

  @inline final def filter[A](p: (A) ⇒ Boolean): SeqImpl[A] ⇒ SeqImpl[A] = _.filter(p).asInstanceOf[SeqImpl[A]]

  @inline final def partition[A](f: (A) ⇒ Boolean): SeqImpl[A] ⇒ (SeqImpl[A], SeqImpl[A]) = _.partition(f).asInstanceOf[(SeqImpl[A], SeqImpl[A])]

  @inline final def sorted[A](implicit ord: Ordering[A]): SeqImpl[A] ⇒ SeqImpl[A] = _.sorted(ord).asInstanceOf[SeqImpl[A]]

  @inline final def sortBy[A, B](f: (A) ⇒ B)(implicit ord: Ordering[B]): SeqImpl[A] ⇒ SeqImpl[A] = _.sortBy(f)(ord).asInstanceOf[SeqImpl[A]]

  @inline final def sortWith[A](lessThan: (A, A) ⇒ Boolean): SeqImpl[A] ⇒ SeqImpl[A] = _.sortWith(lessThan).asInstanceOf[SeqImpl[A]]

  @inline final def filterDefined[A]: SeqImpl[Option[A]] ⇒ SeqImpl[A] = _.withFilter(_.isDefined).map[A, SeqImpl[A]](_.get)

  @inline final def length[A]: SeqImpl[A] ⇒ Int = _.length

  @inline final def size[A]: SeqImpl[A] ⇒ Int = _.length

  @inline final def headOption[A]: SeqImpl[A] ⇒ Option[A] = _.headOption

  @inline final def mkString[A](sep: String): SeqImpl[A] ⇒ String = _.mkString(sep)

  @inline final def mkString[A](start: String, sep: String, end: String): SeqImpl[A] ⇒ String = _.mkString(start, sep, end)

  // This is for debugging
  @inline final def passThrough[A](f: (A) ⇒ Any): SeqImpl[A] ⇒ SeqImpl[A] = seq ⇒ { seq.foreach(f); seq }

  @inline final def iter[A, U](f: (A) ⇒ U): SeqImpl[A] ⇒ Unit = foreach(f)

  @inline final def ofOne[A](x: A): SeqImpl[A] = apply(x)

  @inline final def of[A](xs: A*): SeqImpl[A] = apply(xs:_*)

  @inline final def ofIterable[A]: Iterable[A] ⇒ SeqImpl[A] = _.to[SeqImpl]

  @inline final def ofIterator[A]: Iterator[A] ⇒ SeqImpl[A] = _.to[SeqImpl]

  @inline final def ofTraversable[A]: Traversable[A] ⇒ SeqImpl[A] = _.to[SeqImpl]

  @inline final def ofList[A]: List[A] ⇒ SeqImpl[A] = _.to[SeqImpl]

  @inline final def ofSet[A]: Set[A] ⇒ SeqImpl[A] = _.to[SeqImpl]

  @inline final def ofArray[A]: Array[A] ⇒ SeqImpl[A] = _.to[SeqImpl]

  @inline final def ofMap[A, B]: Map[A, B] ⇒ SeqImpl[(A, B)] = _.to[SeqImpl]

  @inline final def ofOption[A]: Option[A] ⇒ SeqImpl[A] = {
    case Some(value) ⇒ apply(value)
    case None ⇒ apply()
  }

  @inline final def ofJava[A]: java.util.Collection[A] ⇒ SeqImpl[A] = it ⇒ {
    import scala.collection.JavaConverters._
    it.asScala.to[SeqImpl]
  }

  @inline final def ofMapValuesSortedBy[A, B, C](sortBy: (B) ⇒ C)(implicit ord: Ordering[C]): Map[A, B] ⇒ SeqImpl[B] =
    map ⇒ apply(map.toSeq.sortBy(kv ⇒ sortBy(kv._2)).map(_._2):_*)

  @inline final def unique[A]: SeqImpl[A] ⇒ SeqImpl[A] =
    seq ⇒ {
      val set = scala.collection.mutable.Set[A]()
      for(i ← seq if !set.contains(i)) yield {
        set += i
        i
      }
    }
}
