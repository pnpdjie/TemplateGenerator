## 编辑build.fxbuild

E(fx)clipse使用build.fxbuild文件生成一个被Ant编译工具使用的文件。(如果你没有一个build.fxbuid文件，在Eclipse中创建一个新的Java FX项目，并且拷贝生成的文件过来。

从项目的根目录下打开build.fxbuild。

填写包含一个星号的字段。对于MacOS：在应用程序标题中不能使用空格，因为好像会产生问题。 

fxbuild设置：

Build Directory: ${project}/run

Vendor name: ONAP

Application title: TemplateGenerator

Application version: 1.0

Application class: com.onap.template.Main

Toolkit Type: fx

Packaging Format: exe

在Windows下Packaging Format选择exe，MacOS下选择dmg，Linux下选择rpm

点击Generate ant build.xml only的连接(在右边可以找到)。 生成ant编译

验证是否创建一个新的build目录和文件build.xml

## 添加安装程序的图标

TemplateGenerator.ico 安装文件图标

TemplateGenerator-setup-icon.bmp 安装启动画面图标

TemplateGenerator.icns Mac安装程序图标

在run目录下创建下面的子目录:

run/package/windows (只用于Windows)

run/package/macos (只用于macos)

拷贝上面的相关图标到这些目录中

重要：图标的名称必须精确匹配build.fxbuild中指定的Application的标题名：

TemplateGenerator.ico

TemplateGenerator-setup-icon.bmp

TemplateGenerator.icns

## 添加资源

config目录不能自动拷贝。我们必须手动添加它到build目录下:

在run目录下创建下面的子目录:

run/dist

拷贝config目录(包含我们应用的图标)到build/dist.

## 编辑build.xml包含图标

E(fx)clipse生成的build/build.xml文件(准备使用Ant执行)。我们的安装器图标和资源图像不能正常工作。

当e(fx)clipse没有告诉它包含其它资源，例如config目录和上面添加的安装文件图标时，我们必须手动编辑build.xml文件。

打开build.xml文件，找到路径fxant。添加一行到${basedir}(将让我们安装器图标可用)。

build.xml - 添加"basedir"

```
<path id="fxant">
  <filelist>
    <file name="${java.home}\..\lib\ant-javafx.jar"/>
    <file name="${java.home}\lib\jfxrt.jar"/>
    <file name="${basedir}"/>
  </filelist>
</path>
```

找到块fx:resources id="appRes"，文件的更下面位置。为resources添加一行：

build.xml - 添加"resources"

```
<fx:resources id="appRes">
    <fx:fileset dir="dist" includes="TemplateGenerator.jar"/>
    <fx:fileset dir="dist" includes="libs/*"/>
    <fx:fileset dir="dist" includes="config/**"/>
</fx:resources> 
```

有时候，版本数不能添加到fx:application中，使得安装器总是缺省的版本1.0(在注释中很多人指出这个问题)。为了修复它，手动添加版本号(感谢Marc找到解决办法)。 解决):

build.xml - 添加 "version"

```
<fx:application id="fxApplication"
    name="TemplateGenerator"
    mainClass="com.onap.template.Main"
    version="1.0"
/>
```

现在，我们已经能够使用ant编译运行build.xml了。这将会生成一个可运行的项目jar文件。但是我们希望更进一步，创建一个很好的安装器。

## (Windows) - Windows exe安装器

Windows下AddressApp

使用Inno Setup，我们能为我们的应用程序创建一个单独.exe文件的Windows安装器。生成的.exe执行用户级别的安装(无需管理员权限)。也创建一个快捷方式(菜单和桌面)。

下载[Inno Setup 5](http://www.jrsoftware.org/isdl.php)以后版本，安装Inno程序到你的计算机上。我们的Ant脚本将使用它自动生成安装器。

告诉Windows Inno程序的安装路径(例如：C:\Program Files (x86)\Inno Setup 5)。添加Inno安装路径到Path环境变量中。如果你不知道哪里可以找到它.

重启Eclipse，并且继续第6步。

## 运行build.xml

最后一步，我们使用ant运行build.xml，右击 build.xml文件| Run As | Ant Build。

运行Ant编译

编译将运行一会(在我的计算机上大概1分钟)。

如果一切都成功，你应该在build/deploy/bundles目录下找到本地打包。

文件TemplateGenerator-1.0.exe可以用作为单个文件安装应用。该安装程序将拷贝打包到C:/Users/[yourname]/AppData/Local/AddressApp目录下。