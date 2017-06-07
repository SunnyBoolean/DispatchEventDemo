# DispatchEventDemo
#事件分发流程分析

  智能手机是没有实体功能键盘的，所有的操作都靠触摸屏幕来完成，这中操作得益于触摸屏技术。

##1、触摸屏分类
  触摸屏分为电阻屏和电容屏，前者是靠压力来感应，也即，任何物体对触摸屏施加压力屏幕都能感应到，但是电容屏则是通过生物体热量感应的，现在手机都是电容感应（我用的华为荣耀系列都是压力感应）。所以，以下直介绍电容屏的工作原理。
##2、电容屏工作原理
手机电容屏应用了人体热感应工作原理，总的来说他只能用手指等具有热感的物体来触摸，从屋里结构上看它有四层符合玻璃屏，在玻璃屏的内表面和夹层都涂了一层薄薄的ITO，最外层是我们几乎看不到的稀土玻璃保护层，内存ITO为屏层以保证整个电容屏的工作环境稳定。在人没有与触摸屏碰触时，这四个屏内电极电位相同，一旦用手指点击电容触摸屏，人体电厂、手指尖、导体层三者之间会产生耦合电容，四个角的点极会逐一放射电流并统一流向触电。
##3、触摸事件传递
  当触摸屏收到触摸感应后就会通过触摸屏驱动程序传递给Android系统，系统最后从framework一直分发到Activity和View。

##4、牛逼的dispatchTouchEvent ()
该方法可以看作是一个控制器，它决定了如何发送触摸事件，dispatchTouchEvent（）是事件的入口点。
对于View.dispatchTouchEvent()来说，它会将触摸事件传递到OnTouchListener.onTouch()方法（如果注册了的话）或者是onTouchEvent（）方法。
对于ViewGroup.dispatchEvent()来说，要复杂很多。它基本的功能就是要将事件传递给哪一个子View，如果一个ViewGroup有多个子View，那么他会通过碰撞测试的方法来找到触摸的坐标在哪一个子View的边界范围内，从而调用该子View的dispatchTouchEvent()方法来传递事件。但是在它派发消息给他的子View之前，该ViewGroup的父容器也可以拦截所有的事件。这就是onInterceptTouchEvent（）的用法。
在ViewGroup通过碰撞测试寻找合适的子View来传递事件之前会盗用onInterceptTouchEvent（）方法来判断事件是否被拦截，如果发现该方法返回的true则表明事件被拦截了，那么dispatchTouchEvent（）就会发送一个ACTION_CANCEL到他的子View，这样子View就可以放弃自己的触摸事件处理了。
##5、打劫的onInterceptTouchEvent()
     ViewGroup的onInterceptTouchEvent（）方法是用于拦截事件的，它是在dispatchTouchEvent()中被调用的。如果onInterceptTouchEvent()返回了true，就说明事件被拦截了，不再下发，如果返回false就说明不拦截事件，继续下发给子View，也就是调用子View的相关方法。只有ViewGroup有onInterceptTouchEvent（）方法。毕竟儿子哪有什么权利去决定他爹的事情。。。
##6、事件分发的正确姿势
  明白以onInterceptTouchEvent（）的作用后我们就分两种方式来理解事件派发流程。当屏幕的根ViewGroup收到点击事件时，调用了它的dispatchTouchEvent()方法，在这个dispatchTouchEvent（）里头然后调用了它的onInterceptTouchEvent（）方法，根据该方法的返回值进行判断，有以下两种情况：

###1、当onInterceptTouchEvent()返回为true
  当返回true的时候就表明ViewGroup不要再将事件派发给它的一些子View了，而是由它自己决定该如何处理。这时候有两种情况：
####（1）如果该ViewGroup注册了OnTouchListener了的话，那么事件就会传递给OnTouchListener的onTouch（）方法了，调用onTouch（）之后又有两种情况：
    A 、如果onTouch（）返回为true的话，那么说明事件已经被ViewGroup给处理掉了，再就不会调用onTouchEvent（）了，然后dispatchTouchEvent（）也执行完毕并且返回true了。
    B、第二，如果onTouch()返回false的话，说明他没有处理掉事件，那此时就会调用到ViewGroup的onTouchEvent（）方法了。在onTouchEvent（）调用完毕dispatchTouchEvent（）也执行完了，并且onTouchEvent（）的返回值决定了dispatchTouchEvent（）的返回值，并且始终保持一致的，如果为true就说明事件被消费，如果为false事件就没有被消费，如果o'n'TouchEvent()还返回false，那最终就会将事件交给Activity的onTouchEvent（）来处理了。
####（2）如果该ViewGroup没有注册onTouchListener的话，事件就直接交给onTouchEvent（）来处理了，然后流程就和（1）中的B是一样的。

###2、当onInterceptTouchEvent返回false
 当返回false的时候，dispatchTouchEvent（）里面就会通过碰撞的方法找到合适的子View，并且调用该子View的dispatchTouchEvent（）方法，该子View的dispatchTouchEvent（）方法被调用后的执行流程和1是一样的。要记住一点的就是最先被调用的方法会在最后才结束，这很容易理解，因为方法是层层调用的，所以最先结束的是onTouchEvent()方法，最后结束的是dispatchTouchEvent（）方法。这种回溯的好处在于可以根据返回值来确定某一件事情是否已经做完了，比如如果子View的onTouchEvent（）返回为false的话那他的父容器就知道这个事情没有做，需要它亲自取处理了，然后就调用他自己的onTouchEvent（）来处理了。

____
#     以上就是事件的流程分析。
