package me.neil.scanner

import java.io.File

/**
 * 过滤器
 *
 * create by Neil on 2021-07-17.
 */
interface FileFilter {

  fun filter(file: File): Boolean

}