package io.github.chentao7v.scanner

import java.io.File

/**
 * 文件扫描器
 */
interface Scanner {

  fun scan(directory: String): List<File>

}