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

package mlpipe.collection

import java.{util ⇒ ju}

/**
 *
 * @author Christos KK Loverdos <loverdos@gmail.com>
 */
object JEnum {
  final def iter[A](f: (A) ⇒ Unit): ju.Enumeration[A] ⇒ Unit =
    enum ⇒ {
      while(enum.hasMoreElements) {
        f(enum.nextElement())
      }
    }

  def filterIter[A](p: (A) ⇒ Boolean)(f: (A) ⇒ Unit): ju.Enumeration[A] ⇒ Unit =
    enum ⇒ {
      while(enum.hasMoreElements) {
        val elem = enum.nextElement()
        if(p(elem)) {
          f(elem)
        }
      }
    }
}
