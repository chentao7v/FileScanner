### 文件扫描器

项目中使用到了操作日志上传，因此将日志收集封装成了一个小模块。以达到快速收集文件、裁剪，以及其他后续处理的目的。代码结构比较简单。



##### 如何使用？

1. 首先确保要扫描的目录具备权限

2. 收集文件只需执行如下代码：

   ```kotlin
   val list : List<File> = FileScaner(filter).scan(); // 返回扫描的结果
   ```

3. 需要删除不必要的文件？执行如下代码：

   ```kotlin
   val result : List<File> = FileCleaner().clean(list); // 返回剩余的文件
   ```



##### 支持功能：

1. 支持收集特定格式的文件，如果有特殊要求，实现一个  [FileFilter](https://github.com/chentao7v/FileScanner/blob/master/scanner/src/main/java/me/neil/scanner/FileFilter.kt)，指明要过滤的文件类型。如：

   - 收集 `.xlog` 文件的过滤器 [LogFileFilter](https://github.com/chentao7v/FileScanner/blob/master/scanner/src/main/java/me/neil/scanner/LogFileFilter.kt#L10)
   - 收集所有文件的过滤器 [NoneFileFilter](https://github.com/chentao7v/FileScanner/blob/master/scanner/src/main/java/me/neil/scanner/LogFileFilter.kt#L19)

2. 支持对收集后的文件进行删除，目前已有两种默认的删除实现，也可以实现自己的删除逻辑。

   - 按照截止时间删除的规则 [DeadlineFileCleaner](https://github.com/chentao7v/FileScanner/blob/master/scanner/src/main/java/me/neil/scanner/DeadlineFileCleaner.kt)

   - 收集最近 8 天的日志，并尽量小于 15M 的规则 [AutoFileSizeCleaner](https://github.com/chentao7v/FileScanner/blob/master/scanner/src/main/java/me/neil/scanner/AutoFileSizeCleaner.kt)
   - 其他自定规则请实现 [FileCleaner](https://github.com/chentao7v/FileScanner/blob/master/scanner/src/main/java/me/neil/scanner/FileCleaner.kt)

