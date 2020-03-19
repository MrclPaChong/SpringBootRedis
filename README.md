Redis是一个非常快速的、开源的、使用ANSI C语言编写、支持网络、可基于内存亦可持久化的日志型、非关系类型的、Key-Value数据库，并提供多种语言的API
Redis支持数据的持久化，可以将内存中的数据保存在磁盘中，重启的时候可以再次加载进行使用。
Redis不仅仅支持简单的key-value类型的数据，同时还提供list，set，zset，hash等数据结构的存储。
Redis支持数据的备份，即master-slave模式的数据备份。


String: ke value 可以存储任何数据
      存储对象信息
      
List: 有序 值可重复
      商品列表存储
      多线程 用户邮件群发
      
Set：无序 唯一 (值不可重复)
      用户注册重复提交
      APP提交--随机返回同一时间内不重复的问题
      获取随机，乱序的试题
      
Zset: Set+List 结合   有序、 元素唯一
      话费充值
      话费排行榜
      扩展：游戏充值排行榜、积分排行榜、微博热搜...
 
Hash：一对多 map里面套map
     // key{
          --数据
            Field{
                    A:lisi
                    B:wangwu 
                    C:posk
            }
          --数据
      //}
     id	type	        name	code	   value order_by	is_active	    create_time
     1	ReviewStatus	审核状态	Passed	    通过	1	            1	    2019/10/25 22:03:02
     2	ReviewStatus	审核状态	NotPassed	不通过	2	            1	    2019/10/25 22:03:02
     3	Sex	            性别	Female	    女性	1	            1	    2019/10/25 22:03:02
     4	Sex	            性别	Male	    男性	2	            1	    2019/10/25 22:03:02
     5	Color	        颜色	Red	        红色	1	            1	    2019/10/25 22:54:02
     6	Color	        颜色	Black	    黑色	2	            1	    2019/10/25 22:54:38
     7	Color	        颜色	White	    白色	3	            1	    2019/10/25 22:54:58
     8	Color	        颜色	Pink	    粉色	4	            1	    2019/10/25 22:55:09
     9	Color	        颜色	brond	    棕色	5	            1	    2019/10/31 21:34:13
     10	HouseType	    户型	ThreeTwo	三房两厅	1	            1	    2019/10/31 21:42:28
     11	HouseType	    户型	TwoOne	    两房一厅	2	            1	    2019/10/31 21:43:05

      
布隆过滤器  位数组的形势+多层hash函数

Redis的Hash   Map = key -- value
                                -- value对
              key -- map (field -- value对)
                                -- value对
                                
前端：下拉框：颜色Color：
                红色：red 
                黑色：black
