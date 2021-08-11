package me.neil.example

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import me.neil.scanner.AutoFileSizeCleaner
import me.neil.scanner.LogFileScanner
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    scan();
  }

  private fun scan() {
    thread {

      // 收集的目录
      // 注意，需要有权限
      val directory = cacheDir

      // 收集后的文件已按照修改时间排好序
      val files = LogFileScanner().scan(directory.absolutePath)

      // 清除符合条件的文件
      val result = AutoFileSizeCleaner().clean(files)

    }
  }
}