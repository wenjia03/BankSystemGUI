# 非正式文档之前需要写的几点

## 1、测试账户

1    1     超级管理员账户（无MFA）

wenjiachen    1    超级管理员账户（有MFA）

123     123      无权限账户

## 2、本应用权限组

HIGH_ALL 所有高级权限 最顶级权限



NORMAL_DEPOSITOR_CREATE 标准储户创建

NORMAL_DEPOSITOR_DELETE 标注储户删除 需要验证储户密码

NORMAL_DEPOSITOR_UPDATE 修改储户信息

NORMAL_DEPOSITOR_FETCH 标准储户查询

HIGH_DEPOSITOR 高级权限 能看到全部信息



NORMAL_TRANS_CREATE 标准交易创建

NORMAL_TRANS_FETCH 标注交易查询

HIGH_TRANS 高级交易，无需验证储户密码 可以使用止付和退回功能



NORMAL_BUSINESS_DEPOSITE 标准业务存款

NORMAL_BUSINESS_WITHDRAW 标准业务取款 需要验证密码

HIGH_BUSINESS 高级业务 无需验证密码



HIGH_ADMIN 高级管理员权限，可以使用权限组功能和管理员管理功能。



系统**接受无权限用户组，无权限用户组拥有系统的基本登录权限，但是无权限访问任何功能**

 本来设计有权限树的，后面懒得做了，还是做单项权限。

---

## 3、MFA校验

MFA校验基于TOTP协议。

使用MFA校验，需要下载MFA验证器，推荐下载[Microsoft 移动电话身份验证器应用 | Microsoft 安全](https://www.microsoft.com/zh-cn/security/mobile-authenticator-app)

---

本程序使用到的开源项目：

-   OpenJFX
-   Alibaba Druid
-   Google Auth 
-   OpenJDK

## 4、StagePool 舞台池（窗口池）设计

我参考了互联网上大佬的博文，设计了一套管理应用程序窗口的设计架构——窗口池。

其将窗口的创建和销毁纳入窗口池管理，应用可以跨Controller调用或者启动其他窗口，无需考虑舞台。

应用程序在关闭时，还可以统一结束所有Stage。

窗口池的缺陷和可改进点有：

-   暂时无法控制窗口数量，可以加入控制数量。
-   可以封装Stage，使得窗口的Close动作完全地被窗口池监控。
-   封装Stage可以使得窗口池整体更加易用。

窗口使用窗口池，只需要实现Showable接口。当然，如果Application引入窗口池对象，并且将动作封装在主类作为类方法时，可以无需实现Showable接口，Showable为窗口控制类提供了控制应用全局窗口的能力。

**窗口池不实现Stage的hide方法，原因是对于JavaFX应用的生命周期来说，Hide和Close作用相同，且换用后者无任何区别。并非因为窗口Hide了导致JavaFX不结束了，所有的Stage消失仍然会使得JavaFX生命死亡。所以实现Hide无意义，窗口池不管理Hide状态。**