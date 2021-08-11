package me.neil.scanner

import me.neil.scanner.AutoFileSizeCleaner.Companion.MAX_SIZE_MB
import java.io.File
import java.util.concurrent.TimeUnit

/**
 * 自动清除文件。
 *
 * 前清除 8 天前的文件，清除后判断是否小于 [MAX_SIZE_MB]，如果小于，则结束；
 * 如果不小于，则再往后清除 1 天的，依次循环判断，直到剩余 3 天的日志，
 * 如果仍然不满足，结束。
 *
 * create by Neil on 2021-07-16.
 */
class AutoFileSizeCleaner : FileCleaner {

  companion object {

    private const val TAG = "AutoFileSizeChecker"

    //  最大保存 15M
    private const val MAX_SIZE_MB = 15

    private const val MAX_SIZE = MAX_SIZE_MB * 1024 * 1024


  }

  override fun clean(original: List<File>): List<File> {
    val filesData = clearByDay(8, original)
    val result = filesData.files
    return result
  }

  /**
   * 按天清除日志
   */
  private fun clearByDay(days: Long, original: List<File>): Files {
    // 清除指定时间前的文件
    val files = DeadlineFileCleaner(days, TimeUnit.DAYS).clean(original)
    return when {
      filesSize(files) < MAX_SIZE -> {
        // 清除后文件大小满足要求，直接返回
        Files(files, false)
      }
      days > 3 -> {
        clearByDay(days - 1, files)
      }
      else -> {
        Files(files, true)
      }
    }
  }

  /**
   * 按小时清除日志
   */
  private fun clearByHours(hours: Long, original: List<File>): Files {
    val files = DeadlineFileCleaner(hours, TimeUnit.HOURS).clean(original)
    return when {
      filesSize(files) < MAX_SIZE -> {
        // 清除后文件大小满足要求，直接返回
        Files(files, false)
      }
      hours > 5 -> {
        // 时间去掉一半
        clearByHours(hours / 2, files)
      }
      hours > 1 -> {
        // 时间减去一小时
        clearByHours(hours - 1, files)
      }
      else -> {
        Files(files, true)
      }
    }
  }

  private fun filesSize(files: List<File>): Long {
    var size = 0L
    for (file in files) {
      if (file.isDirectory) {
        continue
      }
      size += file.length()
    }
    return size
  }

  class Files(val files: List<File>, val outOfLimit: Boolean)

}