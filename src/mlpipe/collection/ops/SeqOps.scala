/*
 * Copyright (c) 2013-2014 Christos KK Loverdos
 */

package mlpipe.collection
package ops

import mlpipe._

import scala.collection.generic.{CanBuildFrom, GenericCompanion}
import scala.collection.mutable.{Builder ⇒ MBuilder, Map ⇒ MMap, Set ⇒ MSet}
import scala.collection.{GenTraversableOnce, Map ⇒ CMap, Seq ⇒ CSeq, Set ⇒ CSet}
import scala.language.higherKinds

trait SeqOps {
  type SeqImpl[X] <: CSeq[X]

  // Our MapOps buddy. E.g. needed for groupBy
  protected val MapBuddy: MapOps
  type MapImpl[K, V] <: CMap[K, V] /*= MapBuddy.MapImpl[K, V]*/

  protected val SeqImplC: GenericCompanion[SeqImpl]
  protected implicit def canBuildFrom[A, B]: CanBuildFrom[CSeq[A], B, SeqImpl[B]]

  final def unapplySeq[A](seqImpl: SeqImpl[A]): Some[SeqImpl[A]] = Some(seqImpl)

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

  final def foldLeft[A, B](z: B)(f: (B, A) ⇒ B): SeqImpl[A] ⇒ B = _.foldLeft(z)(f)

  final def foldRight[A, B](z: B)(f: (A, B) ⇒ B): SeqImpl[A] ⇒ B = _.foldRight(z)(f)

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

  final def ofJCollection[A]: java.util.Collection[A] ⇒ SeqImpl[A] = it ⇒ {
    import scala.collection.JavaConverters._
    it.asScala.to[SeqImpl]
  }
  final def ofJava[A]: java.util.Collection[A] ⇒ SeqImpl[A] = ofJCollection

  final def ofJEnum[A]: java.util.Enumeration[A] ⇒ SeqImpl[A] = it ⇒ {
    import scala.collection.JavaConverters._
    it.asScala.to[SeqImpl]
  }

  final def ofMapValuesSortedBy[A, B, C](sortBy: (B) ⇒ C)(implicit ord: Ordering[C]): CMap[A, B] ⇒ SeqImpl[B] =
    map ⇒ apply(map.toSeq.sortBy(kv ⇒ sortBy(kv._2)).map(_._2):_*)

  final def unique[A]: SeqImpl[A] ⇒ SeqImpl[A] =
    seq ⇒ {
      val set = MSet[A]()
      for(i ← seq if !set.contains(i)) yield {
        set += i
        i
      }
    }

  def groupBy[A, K](f: (A) ⇒ K): (SeqImpl[A]) ⇒ MapImpl[K, SeqImpl[A]] = seq ⇒ {
    val mmap = scala.collection.mutable.Map.empty[K, MBuilder[A, SeqImpl[A]]]

    seq |> generic.Seq.iter { a ⇒
      val k = f(a)
      val builder = mmap.getOrElseUpdate(k, SeqImplC.newBuilder)
      builder += a
    }

    val resultMapBuilder = this.MapBuddy.newBuilder[K, SeqImpl[A]]
    mmap |> mutable.Map.iter { case (k, builder) ⇒
      resultMapBuilder += k → builder.result()
    }

    resultMapBuilder.result().asInstanceOf[MapImpl[K, SeqImpl[A]]]
  }
}
