package io.github.chentao7v.scanner

import android.util.Log
import java.io.File

/**
 * 扫描指定目录下的所有文件
 *
 * 注意是文件而不是目录
 *
 * @param filter 文件的过滤规则
 */
open class FileScanner(
  /**
   * 文件过滤器
   */
  private val filter: FileFilter = NoneFileFilter()
) : Scanner {

  private companion object {
    private const val TAG = "FileScanner"
  }

  /**
   * 扫描指定目录下的文件，并对所有的文件按照修改时间进行从低到高的排序
   */
  override fun scan(directory: String): List<File> {
    val root = File(directory)
    return startScan(root)
  }

  private fun startScan(root: File): List<File> {
    val files = ArrayList<File>()

    // 使用队列替代递归
    val queue = ArrayDeque<File>()
    queue.addLast(root)

    // 广度优先遍历
    while (queue.isNotEmpty()) {
      val element = queue.removeLast()
      if (element.isDirectory) {
        val children = element.listFiles()
        if (children.isNullOrEmpty()) {
          continue
        }

        for (i in children.indices) {
          val child = children[i]
          if (child.isDirectory) {
            queue.addLast(child)
          } else {
            addFile(files, child)
          }
        }
      } else {
        addFile(files, element)
      }
    }

    // 对文件按照修改时间进行排序
    files.sortWith { o1, o2 ->
      if (o1.lastModified() > o2.lastModified()) 1 else -1
    }

    Log.d(TAG, "扫描结束，${root.absolutePath} 文件夹中一共 ${files.size} 个文件")
    return files
  }

  private fun addFile(files: ArrayList<File>, element: File) {
    if (filter.filter(element)) {
      files.add(element)
    }
  }
}