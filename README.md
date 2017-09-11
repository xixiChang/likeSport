# likeSport
爱运动

０.项目构建需要访问外网！如果Android Studio版本不是2.2.3的，需要将工程目录下（不是app）的gradle版本改成自己的；

１.BaseActivity 现有setToolBar方法，用于设置Activity ToolBar,需要传入toolbar实例和toolbar上显示的文字的string资源ｉd;
　　ｐｓ：在布局ｘｍｌ文件中可使用customui 包下的ToolBar,即可使用toolbar标题居中显示；

2.讲道理所有Activity可继承BaseActivity,如果有可以提炼的方法可放在BaseActivity中；

３.诸如角标、多样式的按钮、多样式的弹窗可使用依赖库里的控件，简单易行，极大加快速率；ps:可以留给张西做；

４.多次使用的string，dimen可以放在xml，统一格式，防止后面修改到处找；

５.customui包下有我找的用于RecyclerView画分割线的工具，可直接使用；

６.666
