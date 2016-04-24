/*
 * Copyright (c) 2013-2014 Christos KK Loverdos
 */

package mlpipe.ext

trait CastOps {
  final def unsafeTo[A]: (Any) ⇒ A = _.asInstanceOf[A]
}

object Cast extends CastOps
