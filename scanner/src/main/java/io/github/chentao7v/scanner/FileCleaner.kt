package io.github.chentao7v.scanner

import java.io.File

/**
 * 文件清除器
 */
interface FileCleaner {

  /**
   * 清除满足指定要求的文件。
   *
   * @param sources 按照修改时间从低到高排好序的文件列表
   * @return 返回清除指定要求的文件之后剩余的文件
   */
  fun clean(sources: List<File>): List<File>

}