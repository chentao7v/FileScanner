package me.neil.scanner

import java.io.File

/**
 * 文件清除器
 *
 * create by Neil on 2021-07-16.
 */
interface FileCleaner {

  /**
   * 清除满足指定要求的文件。
   *
   * @param original 按照修改时间从低到高排好序的文件列表
   * @return 返回清除指定要求的文件之后剩余的文件
   */
  fun clean(original: List<File>): List<File>

}