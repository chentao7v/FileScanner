# 文件扫描器
Android 开发中，有时候我们需要采集某个目录中的特定文件，如运行日志采集场景等。其涉及到文件的遍历，过滤与删除，代码量巨大。

`FileScanner` 可一行代码完成指定目录下所有目标文件的扫描，方便开发者使用。



## 如何使用
**1. 在 `build.gradle` 中添加如下依赖**

```groovy
implementation 'io.github.chentao7v:file-scanner:1.0.0'
```



**2. 自定义过滤器选择目标文件**

通过实现自定义的过滤器，以扫描目标文件。如收集 `xlog`  日志的场景：

```kotlin
val filter = object : FileFilter {
  override fun filter(file: File): Boolean {
    return file.name.endsWith(".xlog")
  }
}
```



**3. 给定目录执行扫描操作**

```kotlin
val sources : List<File> = FileScanner(filter).scan(directory);
```

扫描的结果即是满足要求的所有文件。



**4. 裁剪收集的文件**

如果想删除一部分特定结果，可以通过实现 `FileCleaner` 完成。比如想采集过去 8 天的日志，并且要求整个文件大小不大于 10M，可以通过如下代码完成：

```kotlin
AutoFileSizeCleaner(8,10).clean(sources)
```
