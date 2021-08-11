package me.neil.scanner

import java.io.File

/**
 * 清除提供的所有文件
 *
 * create by Neil on 2021-07-17.
 */
class AllFileCleaner : FileCleaner {
  override fun clean(original: List<File>): List<File> {
    original.onEach { file ->
      if (file.isFile) {
        file.delete()
      }
    }
    return emptyList()
  }
}