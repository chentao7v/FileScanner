package me.neil.scanner

/**
 * 日志文件扫描器
 *
 * create by Neil on 2021-08-12.
 */
class LogFileScanner : FileScanner(LogFileFilter())

/**
 * 所有文件扫描器
 */
class AllFileScanner : FileScanner(NoneFileFilter())