package mlpipe

/**
 *
 * @author Christos KK Loverdos <loverdos@gmail.com>
 */
package object collection {

  // let (|>) x f = f x
  implicit final class MLPipe[T](val x: T) extends AnyVal {
    def |>[B](f: (T) ⇒ B) = f(x)
  }

  // Fix Predef
  final type Seq[+A] = scala.collection.immutable.Seq[A]
  final val Seq = scala.collection.immutable.Seq

  final val JList = mutable.JList
  final val JSet = mutable.JSet

  final val PArray = collection.mutable.PArray

  final val IOption = immutable.POption

  final val IFuture = concurrent.PFuture
  final val IPromise = concurrent.PPromise

  final val ISeq    = immutable.PSeq
  final val IStream = immutable.PStream
  final val ISet    = immutable.PSet
  final val IMap    = immutable.PMap
  final val ITraversable = generic.PTraversable

  final val MSeq    = mutable.PSeq
  final val MSet    = mutable.PSet

}
