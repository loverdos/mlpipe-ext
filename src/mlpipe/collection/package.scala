package mlpipe

/**
 *
 * @author Christos KK Loverdos <loverdos@gmail.com>
 */
package object collection {
  // Fix Predef
  final type Seq[+A] = scala.collection.immutable.Seq[A]
  final type Set[A] = scala.collection.immutable.Set[A]
  final type Map[A, +B] = scala.collection.immutable.Map[A, B]
  final type Traversable[+A] = scala.collection.Traversable[A]

  final val Seq = immutable.Seq
  final val Set = immutable.Set
  final val Map = immutable.Map
  final val Stream = immutable.Stream
  final val Traversable = generic.Traversable

  final val JList = mutable.JList
  final val JSet = mutable.JSet

  final val Array = collection.mutable.Array

  final val Option = immutable.Option

  final val Future = concurrent.Future
  final val Promise = concurrent.Promise
}
