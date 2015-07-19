/*
 * Copyright (c) 2013-2014 Christos KK Loverdos
 */

package mlpipe.collection.ops

import scala.collection.generic.{CanBuildFrom, GenericCompanion}
import scala.collection.{GenTraversableOnce, Seq ⇒ CSeq, Set ⇒ CSet, Map ⇒ CMap}
import scala.language.higherKinds

trait SetOps {
  type SetImpl[X] <: CSet[X]
  val SetImplC: GenericCompanion[SetImpl]
  implicit def canBuildFrom[A, B]: CanBuildFrom[CSet[A], B, SetImpl[B]]

  final def apply[A](): SetImpl[A] = SetImplC.empty[A]

  final def empty[A]: SetImpl[A] = SetImplC.empty[A]

  final def apply[A](elems: A*): SetImpl[A] = SetImplC.apply(elems:_*)

  final def find[A](p: (A) ⇒ Boolean): SetImpl[A] ⇒ Option[A] = _.find(p)

  final def exists[A](p: (A) ⇒ Boolean): SetImpl[A] ⇒ Boolean = _.exists(p)

  final def map[A, B](f: (A) ⇒ B): SetImpl[A] ⇒ SetImpl[B] = _.map(f)

  final def mapFirst[A, B]: CSet[(A, B)] ⇒ SetImpl[A] = _.map(_._1)

  final def mapSecond[A, B]: CSet[(A, B)] ⇒ SetImpl[B] = _.map(_._2)

  final def flatMap[A, B](f: (A) ⇒ GenTraversableOnce[B]): SetImpl[A] ⇒ SetImpl[B] = _.flatMap(f)

  final def foreach[A, U](f: (A) ⇒ U): SetImpl[A] ⇒ Unit = _.foreach(f)

  final def filter[A](p: (A) ⇒ Boolean): SetImpl[A] ⇒ SetImpl[A] = _.filter(p).asInstanceOf[SetImpl[A]]

  final def partition[A](f: (A) ⇒ Boolean): SetImpl[A] ⇒ (SetImpl[A], SetImpl[A]) = _.partition(f).asInstanceOf[(SetImpl[A], SetImpl[A])]

  final def filterDefined[A]: SetImpl[Option[A]] ⇒ SetImpl[A] = _.withFilter(_.isDefined).map[A, SetImpl[A]](_.get)

  final def length[A]: SetImpl[A] ⇒ Int = _.size

  final def size[A]: SetImpl[A] ⇒ Int = _.size

  final def headOption[A]: SetImpl[A] ⇒ Option[A] = _.headOption

  final def mkString[A](sep: String): SetImpl[A] ⇒ String = _.mkString(sep)

  final def mkString[A](start: String, sep: String, end: String): SetImpl[A] ⇒ String = _.mkString(start, sep, end)

  // This is for debugging
  final def passThrough[A](f: (A) ⇒ Any): SetImpl[A] ⇒ SetImpl[A] = set ⇒ { set.foreach(f); set }

  final def iter[A, U](f: (A) ⇒ U): SetImpl[A] ⇒ Unit = foreach(f)

  final def ofOne[A](x: A): SetImpl[A] = apply(x)

  final def of[A](xs: A*): SetImpl[A] = apply(xs:_*)

  final def ofIterable[A]: Iterable[A] ⇒ SetImpl[A] = _.to[SetImpl]

  final def ofIterator[A]: Iterator[A] ⇒ SetImpl[A] = _.to[SetImpl]

  final def ofTraversable[A]: Traversable[A] ⇒ SetImpl[A] = _.to[SetImpl]

  final def ofList[A]: List[A] ⇒ SetImpl[A] = _.to[SetImpl]

  final def ofSeq[A]: CSeq[A] ⇒ SetImpl[A] = _.to[SetImpl]

  final def ofArray[A]: Array[A] ⇒ SetImpl[A] = _.to[SetImpl]

  final def ofMap[A, B]: CMap[A, B] ⇒ SetImpl[(A, B)] = _.to[SetImpl]

  final def ofOption[A]: Option[A] ⇒ SetImpl[A] = {
    case Some(value) ⇒ apply(value)
    case None ⇒ apply()
  }

  final def ofJCollection[A]: java.util.Collection[A] ⇒ SetImpl[A] = it ⇒ {
    import scala.collection.JavaConverters._
    it.asScala.to[SetImpl]
  }

  final def ofJEnum[A]: java.util.Enumeration[A] ⇒ SetImpl[A] = it ⇒ {
    import scala.collection.JavaConverters._
    it.asScala.to[SetImpl]
  }

  final def ofJEnumSet[E <: Enum[E]](cls: Class[E]): SetImpl[E] =
    ofJCollection(java.util.EnumSet.allOf(cls))

  final def ofMapValuesSortedBy[A, B, C](sortBy: (B) ⇒ C)(implicit ord: Ordering[C]): CMap[A, B] ⇒ SetImpl[B] =
    map ⇒ apply(map.toSeq.sortBy(kv ⇒ sortBy(kv._2)).map(_._2):_*)

}
