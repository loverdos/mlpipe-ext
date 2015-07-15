/*
 * Copyright (c) 2013-2014 Christos KK Loverdos
 */

package mlpipe.collection.ops

import scala.collection.generic.{CanBuildFrom, GenMapFactory}
import scala.collection.{GenMapLike, GenTraversableOnce, Map ⇒ CMap, Seq ⇒ CSeq, Set ⇒ CSet}
import scala.language.higherKinds

trait MapOps {
  type MapImpl[K, V] <: CMap[K, V] with GenMapLike[K, V, MapImpl[K, V]]
  val MapImplF: GenMapFactory[MapImpl]
  implicit def canBuildFrom[A, B]: CanBuildFrom[CMap[A, B], (A, B), MapImpl[A, B]]

  final def empty[A, B]: MapImpl[A, B] = MapImplF.empty[A, B]

  final def apply[A, B](): MapImpl[A, B] = MapImplF.empty[A, B]

  final def apply[A, B](elems: (A, B)*): MapImpl[A, B] = MapImplF.apply[A, B](elems:_*)

  final def filter[A, B](p: ((A, B)) ⇒ Boolean): MapImpl[A, B] ⇒ MapImpl[A, B] = _.filter(p).asInstanceOf[MapImpl[A, B]]

  final def filterValues[A, B](p: ((A, B)) ⇒ Boolean): MapImpl[A, B] ⇒ Seq[B] = _.filter(p).values.to[Seq]

  final def filterByKey[A, B](p: (A) ⇒ Boolean): MapImpl[A, B] ⇒ MapImpl[A, B] = _.filter { case (k, _) ⇒ p(k) }.asInstanceOf[MapImpl[A, B]]

  final def filterByValue[A, B](p: (B) ⇒ Boolean): MapImpl[A, B] ⇒ MapImpl[A, B] = _.filter { case (k, v) ⇒ p(v) }.asInstanceOf[MapImpl[A, B]]

  final def find[A, B](p: ((A, B)) ⇒ Boolean): MapImpl[A, B] ⇒ Option[(A, B)] = _.find(p)

  final def exists[A, B](p: ((A, B)) ⇒ Boolean): MapImpl[A, B] ⇒ Boolean = _.exists(p)

  final def findValue[A, B](p: ((A, B)) ⇒ Boolean): MapImpl[A, B] ⇒ Option[B] = _.find(p).map(_._2)

  final def findByKey[A, B](p: (A) ⇒ Boolean): MapImpl[A, B] ⇒ Option[(A, B)] = _.find { case (k, _) ⇒ p(k) }

  final def findValueByKey[A, B](p: (A) ⇒ Boolean): MapImpl[A, B] ⇒ Option[B] = _.find { case (k, _) ⇒ p(k) }. map(_._2)

  final def map[A, B, BB](f: (A, B) ⇒ BB): MapImpl[A, B] ⇒ MapImpl[A, BB] = map ⇒ {
    val builder = MapImplF.newBuilder[A, BB]
    for {
      (a, b) ← map
    } {
      val c = f(a, b)
      builder += a → c
    }
    builder.result()
  }

  final def flatMap[A, B, C](f: ((A, B)) ⇒ GenTraversableOnce[C]): MapImpl[A, B] ⇒ Iterable[C] = _.flatMap(f)

  final def foreach[A, B](f: ((A, B)) ⇒ Unit): MapImpl[A, B] ⇒ Unit = _.foreach(f)

  final def foreachKey[A, B](f: (A) ⇒ Unit): MapImpl[A, B] ⇒ Unit = _.keysIterator.foreach(f)

  final def foreachValue[A, B](f: (B) ⇒ Unit): MapImpl[A, B] ⇒ Unit = _.valuesIterator.foreach(f)

  final def length[A, B]: MapImpl[A, B] ⇒ Int = _.size

  final def size[A, B]: MapImpl[A, B] ⇒ Int = _.size

  final def keySet[A, B]: MapImpl[A, B] ⇒ CSet[A] = _.keySet
  final def values[A, B]: MapImpl[A, B] ⇒ Iterable[B] = _.values

  final def iter[A, B](f: ((A, B)) ⇒ Unit): MapImpl[A, B] ⇒ Unit = _.foreach(f)
  
  final def ofSeq[A, B]: CSeq[(A, B)] ⇒ MapImpl[A, B] = { type Col[AB] = MapImpl[A, B]; _.to[Col] }

  final def ofSet[A, B]: CSet[(A, B)] ⇒ MapImpl[A, B] = { type Col[AB] = MapImpl[A, B]; _.to[Col] }

  final def ofList[A, B]: List[(A, B)] ⇒ MapImpl[A, B] = { type Col[AB] = MapImpl[A, B]; _.to[Col] }

  final def ofIterable[A, B]: Iterable[(A, B)] ⇒ MapImpl[A, B] = { type Col[AB] = MapImpl[A, B]; _.to[Col] }

  final def ofTraversable[A, B]: Traversable[(A, B)] ⇒ MapImpl[A, B] = { type Col[AB] = MapImpl[A, B]; _.to[Col] }

  final def ofIterator[A, B]: Iterator[(A, B)] ⇒ MapImpl[A, B] = { type Col[AB] = MapImpl[A, B]; _.to[Col] }

  final def ofArray[A, B]: Array[(A, B)] ⇒ MapImpl[A, B] = { type Col[AB] = MapImpl[A, B]; _.to[Col] }

  final def ofJava[A, B]: java.util.Map[A, B] ⇒ MapImpl[A, B] = it ⇒ {
    import scala.collection.JavaConverters._
    type Col[AB] = MapImpl[A, B]
    it.asScala.to[Col]
  }

  final def ofJava[A, B, U](implicit ev: U <:< (A, B)): java.util.Collection[U] ⇒ MapImpl[A, B] = it ⇒ {
    import scala.collection.JavaConverters._
    type Col[AB] = MapImpl[A, B]
    it.asScala.asInstanceOf[Iterable[(A, B)]].to[Col]
  }
}
