package io.github.chentao7v.scanner

import java.io.File
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * 过期文件清除器。清除指定"时间段"前的日志
 *
 * @param time 时间
 * @param unit 时间单位
 */
class DeadlineFileCleaner(time: Long, unit: TimeUnit = TimeUnit.DAYS) : FileCleaner {

  /**
   * 有效日志时间截止点
   */
  private val deadlineInMillis: Long

  init {
    // 计算出时间界址点
    val now = Calendar.getInstance()
    val nowMillis = now.timeInMillis
    val timeMills = unit.toMillis(time)
    now.timeInMillis = nowMillis - timeMills
    deadlineInMillis = now.timeInMillis
  }

  override fun clean(sources: List<File>): List<File> {

    var breakIndex = -1
    for (i in sources.indices) {
      val file = sources[i]
      if (file.isDirectory) {
        continue
      }

      val lastModified = file.lastModified()
      // 删除过期文件
      if (lastModified < deadlineInMillis) {
        file.delete()
        continue
      }
      // 由于之前按照修改时间从低到高排好序了
      // 因此剩余的文件都是未过期的
      breakIndex = i
      break
    }

    // 有效文件
    val validFiles = ArrayList<File>()
    if (breakIndex >= 0 && breakIndex <= sources.size - 1) {
      validFiles.addAll(sources.subList(breakIndex, sources.size))
    }

    return validFiles
  }

}