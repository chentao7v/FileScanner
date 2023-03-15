package io.github.chentao7v.scanner

import java.io.File

/**
 * 清除提供的所有文件
 */
class AllFileCleaner : FileCleaner {

  override fun clean(sources: List<File>): List<File> {
    sources.onEach { file ->
      if (file.isFile) {
        file.delete()
      }
    }
    return emptyList()
  }

}