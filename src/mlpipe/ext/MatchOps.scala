/*
 * Copyright (c) 2013-2014 Christos KK Loverdos
 */

package mlpipe.ext

trait MatchOps {
  final def apply[I, O](pf: PartialFunction[I, O]): (I) ⇒ O = pf(_)
}

object Match extends MatchOps
