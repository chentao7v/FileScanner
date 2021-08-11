package me.neil.scanner

import java.io.File

/**
 * 文件扫描器
 *
 * create by Neil on 2021-07-16.
 */
interface Scanner {

  fun scan(directory: String): List<File>

}