package com.chentao.simple.scanner

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import io.github.chentao7v.scanner.AllFileCleaner
import io.github.chentao7v.scanner.FileFilter
import io.github.chentao7v.scanner.FileScanner
import me.neil.example.R
import java.io.File
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

  companion object {

    private const val TAG = "MainActivity"

  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
  }

  fun generate(view: View) {

    val ends = listOf<String>(
      "png",
      "txt",
      "xlog"
    )

    val root = externalCacheDir ?: return

    Log.d(TAG, "root: " + root.absolutePath)

    val child1 = File(root, "simple1")
    val child2 = File(root, "simple2")
    val child3 = File(root, "simple3")

    val children = listOf(child1, child2, child3)

    // root 的子目录下添加我呢间
    for (child in children) {
      child.mkdirs()

      for ((i, end) in ends.withIndex()) {
        val temp = File(child, "${child.name}_${i}.${end}")
        temp.createNewFile()
      }
    }

    // root 目录下添加文件
    for ((i, end) in ends.withIndex()) {
      val temp = File(root, "root_${i}.${end}")
      temp.createNewFile()
    }

  }


  fun scanXlog(view: View) {
    // 异步线程处理
    thread {
      // 收集的目录
      // 注意，需要有权限
      val directory = externalCacheDir ?: return@thread

      // 收集后的文件已按照修改时间排好序

      val filter = object : FileFilter {
        override fun filter(file: File): Boolean {
          return file.name.endsWith(".xlog")
        }
      }

      val files = FileScanner(filter).scan(directory.absolutePath)
      showCountToast(files.size)
    }
  }

  fun scanImage(view: View) {
    thread {
      val directory = externalCacheDir ?: return@thread
      val filter = object : FileFilter {
        override fun filter(file: File): Boolean {
          return file.name.endsWith(".png")
              || file.name.endsWith(".jpeg")
              || file.name.endsWith(".jpg")
              || file.name.endsWith(".web")
        }
      }
      val files = FileScanner(filter).scan(directory.absolutePath)
      showCountToast(files.size)
    }

  }


  fun scanAll(view: View) {
    thread {
      val directory = externalCacheDir ?: return@thread
      val files = FileScanner().scan(directory.absolutePath)
      showCountToast(files.size)
    }
  }

  fun scanImageAndDelete(view: View) {
    // 异步线程处理
    thread {
      val directory = externalCacheDir ?: return@thread

      val filter = object : FileFilter {
        override fun filter(file: File): Boolean {
          return file.name.endsWith(".png")
              || file.name.endsWith(".jpeg")
              || file.name.endsWith(".jpg")
              || file.name.endsWith(".web")
        }
      }
      val files = FileScanner(filter).scan(directory.absolutePath)
      showCountToast(files.size)

      AllFileCleaner().clean(files)

      showToast("清除成功")
    }
  }

  private fun showCountToast(msg: Int) {
    runOnUiThread {
      Toast.makeText(applicationContext, "共 $msg 个满足条件的文件", Toast.LENGTH_SHORT).show()
    }
  }

  private fun showToast(msg: String) {
    runOnUiThread {
      Toast.makeText(applicationContext, "共 $msg 个满足条件的文件", Toast.LENGTH_SHORT).show()
    }
  }
}