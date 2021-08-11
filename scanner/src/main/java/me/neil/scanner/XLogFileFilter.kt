package me.neil.scanner

import java.io.File

/**
 * 日志文件过滤器
 *
 * create by Neil on 2021-07-17.
 */
class XLogFileFilter : FileFilter {
  override fun filter(file: File): Boolean {
    return file.name.endsWith(".xlog")
  }
}

/**
 * 不过滤任何文件的过滤器
 */
class NoneFileFilter : FileFilter {
  override fun filter(file: File): Boolean {
    return true
  }
}
