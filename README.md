####效果图~

![效果图](http://upload-images.jianshu.io/upload_images/2099469-35b8ed08d249e23a.gif?imageMogr2/auto-orient/strip)

> 对于大多数的App而言，多状态的页面都是必备的。一般加载数据的时候都会出现：加载中、加载成功、加载异常、无数据等等情况
> 
> 一般在搭建项目的时候，就会先把这个页面写好，方便日后的开发。

##分析
实现多状态布局View的时候，一般都是把里面的第一个子View当做内容View。 其他各种状态要显示的View则在自定义View中通过代码动态的进行控制

然后提供公开的API可供外部调用，控制不同状态下自定义View的不同View的展示。
