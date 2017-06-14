---
title: {title}
---

{% capture article %}

## 标题

请在`{% capture article %}`和`{% endcapture %}`两个标签之间编辑正文。

## 操作指南

### 修改文件名

首先修改当前文件名，然后在`{leftTreePath}`中找到当前页面路径，替换原文件名为修改后的文件名。

### 创建新文件

首先在当前目录创建文件，然后将创建的文件路径加到`{leftTreePath}`中。

{% endcapture %}

{% include templates/home.md %}
