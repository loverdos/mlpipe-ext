/*
 * Copyright (c) 2013 Christos KK Loverdos
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package mlpipe.collection.mutable

import scala.collection.JavaConverters._
import java.{util ⇒ ju}

/**
 *
 * @author Christos KK Loverdos <loverdos@gmail.com>
 */
object JSet {
  final type Set[A] = ju.Set[A]

  @inline final def iter[A](f: (A) ⇒ Unit): ju.Set[A] ⇒ Unit = _.asScala.foreach(f)
  @inline final def empty[A]: ju.Set[A] = new ju.HashSet[A]()
  @inline final def ofSet[A]: scala.collection.Set[A] ⇒ ju.Set[A] = _.asJava
  @inline final def ofSeq[A]: scala.collection.Seq[A] ⇒ ju.Set[A] = _.toSet.asJava
}
