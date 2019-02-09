####

大家好，我叫magiclu，是一名退坑mc的小白，由于自己日常的无聊（因为我除了mc，就没有其他想玩的游戏了），因此
就在日常写点简单的趣味的小插件，也是自己想拿着玩。
代码看看更新日志就知道非常简单，没有逻辑性，但希望大家玩的开心

作为一名java爱好者，十分热爱nukkit这一款核心，希望今后能够倔。。。。起

插件支持api 1.0.7，Minecraft PE 1.8版本以上
####
updated代表工程结束，error有未知bug，待修复
    ##预计更新日志
    v0.1
        >服务器指令使用检测 (updated)0.1.1
        >进服提示 (updated)0.1.2
        >进服标题头 (updated error)0.1.3
        >进服小标题头 (updated)0.1.4
        >检测玩家飞行(updated)0.1.4.1
        >TPS检测(Updated)0.1.4.2
        >QQ获取(Updated)0.1.4.3
    v0.1.5
        >打开和关闭方块更新(Updated)0.1.5.1
        >飞行拽回采用配置文件方式（UPDATED)0.1.5.2
        >广播(Updated)0.1.6
        >用tip将服务器进入信息发送个每个人(Updated)0.1.7
        >屏蔽敏感词语<updated>0.1.8
        >称号,名字空格可以用&nbsp;代替<updated>
        >玩家破坏方块记录<updated>８５０２７８５９ｙｈｎ
        >死亡信息自定义0.1.9
              -已经创建好事件<updated>
        >进入退出信息自定义，第一次进入信息自定义
        >注解更新定位ＢＵＧ方法位置
        >网页访问服务器信息<updated error>
                   -外接扩展，配置文件添加servlet组件并将servlet组件注册到ＣＬＡＳＳＰＡＴＨ
    v0.2
        >*进入退出信息自定义，第一次进入信息自定义
        >*获取网站端口指令
        >*网站servlet组件注册
        >*监听器扩展外接注册
        >*java.lang.NoClassDefFoundError: org/dom4j/io/SAXReader解决
        >玩家吃食物的小事件
        >NPC
        >消息自定义
        >进服gui提示
        >检测名字合法
        >语言检测
        >修改玩家状态检测
        >给予op检测,op给予记录单
        >破坏方块检测，开出各玩家破坏方块的详细记录单
        >服务器效能检测
        >天气变化导致的日常生活变化
        >跑酷游戏
            －设置跑酷范围，进入游戏可以获得高速度状态或者高跳，在第四个砖块会触发
    v0.3
        >纵火检测
        >TNT爆炸检测，提供爆炸点
        >液体流动检测
        >玩家死亡检测
        >记录拉人
        >玩家可以自己查找称号
        >项目1
            随机传送插件
            指令:/wild
            配置文件:
            允许随机传送的世界:
            - world
            - survive
            随机传送半径（出生点为圆心）:800
            消耗游戏币:false或数值
            传送后玩家不能卡在方块里面，不能在半空中
        >网站显示玩家人数和服务器状态
    v0.4
        0.4版本可能会内置更新一个小游戏，希望期待
    v0.5
        gui举报系统
        在线奖励
        签到
        红包功能
    ##指令：
    args表示支持带空格的字符串
    op:
    1./checkoff 关掉指令检测
    2./checkon 打开指令检测
    3./modmsg args 修改进服提示
    4./modtitle args 修改大标题
    5./modtip args 修改小标题
    6./flyoff 关闭飞行检测
    7./flyon 打开飞行检测
    ....
# MBasicAggregate For Nukkit
####[请关注我的个人博客][1]magic.noyark.net
------

MBasicAggregate是基于MFun的代码优化扩展系列,未来会在此增加更多功能**MagicLu** 是一名普普通通的中国高中生,爱好编程.热爱MineCraft
这个插件,可以让您享受到最新基本功能：
> * 服务器指令使用检测
> * 进服提示
> * 进服标题头
> * 进服小标题头
> * 检测玩家飞行
> * TPS检测
> * QQ获取
> * 打开和关闭方块更新
> * 飞行拽回采用配置文件方式
> * 广播
> * 用tip将服务器进入信息发送个每个人
> * 屏蔽敏感词语
> * 称号,名字空格可以用&nbsp;代替
> * 玩家破坏方块记录
> * 监听器扩展(实验)
> * 死亡信息自定义0.1.9
> * 网页访问服务器信息(实验　目前完成外接部分，尚未解决运行错误的ｂｕｇ)
> * 红包系统
````
以及后期更新的功能:
````
> * 获取网站端口指令
> * 网站servlet组件注册
> * 监听器扩展外接注册
> * java.lang.NoClassDefFoundError: org/dom4j/io/SAXReader解决
> * 玩家吃食物的小事件
> * NPC
> * 进服gui提示
> * 检测名字合法
> * 语言检测
> * 修改玩家状态检测
> * 给予op检测,op给予记录单
> * 破坏方块检测，开出各玩家破坏方块的详细记录单
> * 服务器效能检测
> * 天气变化导致的日常生活变化
> * 跑酷游戏
> * 设置跑酷范围，进入游戏可以获得高速度状态或者高跳，在第四个砖块会触发
> * 纵火检测
> * TNT爆炸检测，提供爆炸点
> * 液体流动检测
> * 玩家死亡检测
> * 记录拉人
> * 玩家可以自己查找称号
> * 项目1
> * 随机传送插件
> * 指令:/wild
> * 配置文件:
> * 允许随机传送的世界:
> * world
> * survive
> * 随机传送半径（出生点为圆心）:800
> * 消耗游戏币:false或数值
> * 传送后玩家不能卡在方块里面，不能在半空中
> * 网站显示玩家人数和服务器状态
> * 0.4版本可能会内置更新一个小游戏，希望期待
> * gui举报系统
> * 在线奖励
> * 签到
> * 红包功能

## MBasicAggregate 扩展型　

#### 1.  傻瓜式控制　[ＳＢ　Ｃｏｎｔｒｏｌ](＃)

- [x] set.yml控制面板，reset式重置文档
```ymal
server:
#是否开启指令检测
  check-command: true
#检测tps的时间间隔 s
  tps-reload: 60
#是否打开广播
  broadcast-ifopen: true
#轮播广播数
  broadcast-number: 3
#每个广播的间隔　ms
  broadcast-second-one: 10000
#玩家的进服小标题提示
  people-tip: ''
#方块更新事件
  updated: false
#重置所有文件，改成true重启服务器就会重置文件，重置后会还原为false
  reset: false
#脏话检测
  badword-check: true
#检测飞行后拖回的位置
fly-catch-to:
  x: 0.0
  y: 0.0
  z: 0.0
player:
#打开飞行检测
  fly-check: false
#三个提示
  com-Message: 欢迎加入服务器
  com-Title: 欢迎加入服务器
  com-Tip: 欢迎加入服务器
```
- [x] 信息动态形式注册监听器和玩家信息
```ymal
#Listener.yml 注册的监听器
Listeners:
- net.noyark.www.Listener.BlockListener
- net.noyark.www.Listener.PlayerListener
- net.noyark.www.Listener.ServerListener
```
### 2. Gulesberry 工作室[^LaTeX]

$$＠try->catch->willdone$$


### ３. 插件运行流程 [流程图]
![此处输入图片的描述][2]


### 8. 其他详细点

插件即将会有更多更新，敬请期待



作者 [@MagicLu][5]
2019 年 02月 03日





  [1]: magic.noyark.net
  [2]: http://www.noyark.net/%E5%9B%BE%E7%89%87.png
  [3]: https://www.zybuluo.com/mdeditor?url=https://www.zybuluo.com/static/editor/md-help.markdown
  [4]: https://www.zybuluo.com/mdeditor?url=https://www.zybuluo.com/static/editor/md-help.markdown#cmd-markdown-高阶语法手册
  [5]: http://weibo.com/ghosert
  [6]: http://meta.math.stackexchange.com/questions/5020/mathjax-basic-tutorial-and-quick-reference




