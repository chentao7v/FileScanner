package io.github.chentao7v.scanner

import java.io.File


/**
 * 不过滤任何文件的过滤器
 */
class NoneFileFilter : FileFilter {

  override fun filter(file: File): Boolean {
    return true
  }

}
