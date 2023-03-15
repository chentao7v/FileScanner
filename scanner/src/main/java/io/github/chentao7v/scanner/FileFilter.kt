package io.github.chentao7v.scanner

import java.io.File

/**
 * 过滤器
 */
interface FileFilter {

  /**
   * 过滤条件
   *
   * @param file 当前遍历的文件
   * @return true - 当前 [File] 会被添加到结果集中。
   */
  fun filter(file: File): Boolean

}