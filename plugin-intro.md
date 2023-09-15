## Reasons for writing plugins
It's really simple. Just three things：
1. Many plugins that generate ORM code now charge a fee.
2. I want to customize something, now the open source is more public, my own side may be applicable to the company.
3. Improve yourself and help others.

## The function of the plugin is briefly introduced

In fact, there is also a little introduction on my plugin home page, you can refer to：
homepage：https://plugins.jetbrains.com/plugin/18891-code-generate-orm

Here is a detailed introduction, with graphics；

The function is roughly introduced：

> Can help you quickly generate basic additions and deletions to check the code, and then the corresponding generation directory,
> whether you need to generate swagger, service these have their own control；

In general terms of usage, there are two ways of generating it：

### Directly connect yourself to the data source
> It is not necessary to configure the data source connection in the database of idea. At present, I put the entry in different places
> 1. Select the project -> Right-click, at the bottom -> ORMCodeGenerate, and a pop-up window will pop up for you to configure; (Shortcut keys are not recommended for mac users)
> 2. Tools -> Bottom; (Shortcut keys are not recommended for Windows users)
> 3. control + 9，The shortcut key method is my most recommended.

**Here are a few operation diagrams: refer to the code inside: “images/插件用法.png”;**

Here is a brief introduction to those parameters：

1. classpath：Only the outermost items of your current idea will be selected；
2. yysimple,There, you are asked to enter the corresponding author of your project, which is the Javadoc @author yysimple；
3. File directory selection, the meaning is very clear, I will not say more here, here is just the selection of the generated directory,
   will be based on the following options to determine whether to generate,
   if you do not want to generate controller can not choose the path；
4. Data source information, here the way to configure the data source is currently only implemented by MySQL,
   and does not support other data sources (the supported way will be explained later).

   - table name：To make your data as it appears to support fuzzy queries, remember not to enter,
     you need to click on the previous select tables; The table will appear below；
   - test conn：It is to test whether the data source can be connected properly, and it will prompt the database version；

5. Meaning of option：

   - mybatisPlus：The class that will help your class inherit mybatisPlus; Dependencies need to be introduced.
   - is Create...：Is whether to help you generate Controller, Service, etc., the default is to generate PO, DAO,
     Mapper.xml, so these three file paths must be filled.
   - is create dir：What this means is that when you don't have a controller, service, etc directory in your project,
     it will automatically create the directory and corresponding classes for you.

6. All parameters have a cache function, as long as you have generated before, so when selecting the table,
   remember to clear the previous time (not clear because it will not be generated again).
   I'm also thinking about whether I need to optimize it, but I'm not going to cache it,
   because there's very little need to regenerate it; Get a todo. Optimize it next time；

### Select the database of idea directly to select table generation

> This is the way most ORMs are generated, supporting as many data sources as idea supports, but here is where each project needs to configure data sources,
> So there is the above way, of course, depending on personal preference, both ways are supported, I prefer to use the above when mysql generation，
> I don't like to connect data sources in the project, I specifically open a window to connect all data sources (some people also like to use DG, Navicat; Purely personal preference)

The operation is also relatively simple, but there are no shortcuts：

**Operation picture in：“images/table2Orm.png”;**

> 1. You need to select the table and right-click it.
> 2. The corresponding column type is not found in the middle, and it asks what type do you need to associate with java.
> 3. The last pop-up window is the above castration version, the function is the same, it is not introduced.
> 4. There is a small problem here, you must have a primary key in the table, if not, the mapper file, service file inside the method will not help you generate,
     > because one of the methods is byId.

## 总结

1. My Plugins：https://plugins.jetbrains.com/author/me
2. Plugin Address：https://plugins.jetbrains.com/plugin/18891-code-generate-orm
3. My Gitee：https://gitee.com/yysimple（It says so on which warehouse plugin）
4. My Github：https://github.com/yysimple（The code here might be time-sensitive, so I'll forget to push）
5. Please feel free to issue any questions or send me an email：1449697757@qq.com




  
    





  
    
